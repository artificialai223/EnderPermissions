

package me.TechsCode.EnderPermissions.base.storage;

import java.util.function.ToIntFunction;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.tpl.utils.ClassInstanceCreator;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import me.TechsCode.EnderPermissions.base.storage.syncing.SyncingAgent;
import me.TechsCode.EnderPermissions.base.TechPlugin;

public abstract class Storage<STORABLE extends Storable>
{
    protected TechPlugin plugin;
    private Class<? extends Storable> storable;
    private StorageImplementation implementation;
    private SyncingAgent syncingAgent;
    private Map<String, STORABLE> instances;
    
    public Storage(final TechPlugin plugin, final String s, final Class<? extends Storable> storable, final Class<? extends StorageImplementation> clazz, final boolean b) {
        this.plugin = plugin;
        this.storable = storable;
        try {
            this.implementation = (StorageImplementation)clazz.getConstructor(TechPlugin.class, String.class).newInstance(plugin, s);
        }
        catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            final Throwable t;
            t.printStackTrace();
            return;
        }
        (this.syncingAgent = ((this.implementation.hasMultiServerSupport() && b) ? plugin.getSyncingAgent() : new SyncingAgent.EmptyAgent())).register(this);
        this.instances = new HashMap<String, STORABLE>();
        this.implementation.read("*", new ReadCallback() {
            @Override
            public void onSuccess(final HashMap<String, JsonObject> hashMap) {
                Storage.this.createInstances(hashMap);
            }
            
            @Override
            public void onFailure(final Exception ex) {
                plugin.log("§cCould not retrieve data from Storage '" + storable.getName() + "' :");
                plugin.log("§c" + ex.getMessage());
            }
        });
    }
    
    private void createInstances(final Map<String, JsonObject> map) {
        for (final Map.Entry<String, JsonObject> entry : map.entrySet()) {
            final Storable storable = (Storable)ClassInstanceCreator.create(this.storable);
            try {
                storable.onMount(this.plugin);
                storable.setKey(entry.getKey());
                storable.setState(entry.getValue(), this.plugin);
                storable.setLastSyncedState(entry.getValue());
                storable.setStorage(this);
                this.onMount((STORABLE)storable);
                this.instances.put(storable.getKey(), (STORABLE)storable);
            }
            catch (Exception ex) {
                this.plugin.log(entry.getValue().toString());
                this.plugin.log("§7Error with Storage " + this.getModelName() + " with key " + entry.getKey() + ":");
                throw ex;
            }
        }
    }
    
    public void syncFromDatabase(final String s) {
        this.plugin.getScheduler().runAsync(() -> this.implementation.read(s, new ReadCallback() {
            final /* synthetic */ String val$key;
            
            @Override
            public void onSuccess(final HashMap<String, JsonObject> hashMap) {
                if (this.val$key.equalsIgnoreCase("*")) {
                    Storage.this.createInstances(hashMap.entrySet().stream().filter(entry -> !Storage.this.instances.containsKey(entry.getKey())).collect(Collectors.toMap((Function<? super Object, ?>)Map.Entry::getKey, (Function<? super Object, ?>)Map.Entry::getValue)));
                    for (final Map.Entry<Object, Object> entry4 : hashMap.entrySet().stream().filter(entry2 -> Storage.this.instances.containsKey(entry2.getKey())).collect(Collectors.toMap((Function<? super Object, ?>)Map.Entry::getKey, (Function<? super Object, ?>)Map.Entry::getValue)).entrySet()) {
                        final Storable storable = Storage.this.instances.get(entry4.getKey());
                        if (!storable.getState().toString().equalsIgnoreCase(entry4.getValue().toString())) {
                            storable.setState(entry4.getValue(), Storage.this.plugin);
                            storable.setLastSyncedState(entry4.getValue());
                        }
                    }
                    final Iterator<Map.Entry<Object, V>> iterator2 = ((Map)Storage.this.instances.entrySet().stream().filter(entry3 -> !hashMap.containsKey(entry3.getKey())).collect(Collectors.toMap((Function<? super Object, ?>)Map.Entry::getKey, (Function<? super Object, ?>)Map.Entry::getValue))).entrySet().iterator();
                    while (iterator2.hasNext()) {
                        Storage.this.instances.remove(iterator2.next().getKey());
                    }
                }
                else if (!hashMap.isEmpty() && !Storage.this.instances.containsKey(this.val$key)) {
                    Storage.this.createInstances(hashMap);
                }
                else if (!hashMap.isEmpty() && Storage.this.instances.containsKey(this.val$key)) {
                    Storage.this.instances.get(this.val$key).setState(hashMap.get(this.val$key), Storage.this.plugin);
                    Storage.this.instances.get(this.val$key).setLastSyncedState(hashMap.get(this.val$key));
                }
                else if (hashMap.isEmpty() && Storage.this.instances.containsKey(this.val$key)) {
                    Storage.this.instances.remove(this.val$key);
                }
                Storage.this.onDataSynchronization();
            }
            
            @Override
            public void onFailure(final Exception ex) {
                Storage.this.plugin.log("§cCould not update Storage Instances of Storage '" + Storage.this.storable.getName() + "' :");
                Storage.this.plugin.log("§c" + ex.getMessage());
            }
        }));
    }
    
    public Collection<STORABLE> get() {
        return this.instances.values();
    }
    
    void update(final Storable storable, final JsonObject jsonObject) {
        this.onChange(storable);
        this.implementation.update(storable.getKey(), jsonObject, new WriteCallback() {
            @Override
            public void onSuccess() {
                Storage.this.syncingAgent.notifyNewDataChanges(Storage.this, storable.getKey());
            }
            
            @Override
            public void onFailure(final Exception ex) {
                Storage.this.plugin.log("§cCould not sync '" + storable.getKey() + "' of '" + Storage.this.storable.getName() + "' :");
                Storage.this.plugin.log("§c" + ex.getMessage());
                Storage.this.plugin.log("§cJson Data:");
                Storage.this.plugin.log(storable.getState().toString());
                Storage.this.plugin.log("§cIt is strongly advised to restart the Server!");
            }
        });
    }
    
    void destroy(final Storable storable) {
        this.instances.remove(storable.getKey());
        this.onDestroy(storable);
        this.implementation.destroy(storable.getKey(), new WriteCallback() {
            @Override
            public void onSuccess() {
                Storage.this.syncingAgent.notifyNewDataChanges(Storage.this, storable.getKey());
            }
            
            @Override
            public void onFailure(final Exception ex) {
                Storage.this.plugin.log("§cCould not destroy '" + storable.getKey() + "' of '" + Storage.this.storable.getName() + "' :");
                Storage.this.plugin.log("§c" + ex.getMessage());
                Storage.this.plugin.log("§cJson Data:");
                Storage.this.plugin.log(storable.getState().toString());
                Storage.this.plugin.log("§cIt is strongly advised to restart the Server!");
            }
        });
    }
    
    public STORABLE create(final STORABLE storable) {
        storable.onMount(this.plugin);
        storable.setStorage(this);
        this.onMount(storable);
        this.instances.put(storable.getKey(), storable);
        this.onCreation(storable);
        this.implementation.create(storable.getKey(), storable.getState(), new WriteCallback() {
            @Override
            public void onSuccess() {
                Storage.this.syncingAgent.notifyNewDataChanges(Storage.this, storable.getKey());
            }
            
            @Override
            public void onFailure(final Exception ex) {
                Storage.this.plugin.log("§cCould not create '" + storable.getKey() + "' of '" + Storage.this.storable.getName() + "' :");
                Storage.this.plugin.log("§c" + ex.getMessage());
                Storage.this.plugin.log("§cJson Data:");
                Storage.this.plugin.log(storable.getState().toString());
                Storage.this.plugin.log("§cIt is strongly advised to restart the Server!");
            }
        });
        return storable;
    }
    
    public int getNextNumericId() {
        return this.instances.keySet().stream().mapToInt((ToIntFunction<? super Object>)Integer::parseInt).max().orElse(0) + 1;
    }
    
    public Class<? extends Storable> getModel() {
        return this.storable;
    }
    
    public String getModelName() {
        return this.getModel().getName();
    }
    
    public abstract void onMount(final STORABLE p0);
    
    public abstract void onCreation(final STORABLE p0);
    
    public abstract void onChange(final STORABLE p0);
    
    public abstract void onDestroy(final STORABLE p0);
    
    public abstract void onDataSynchronization();
}
