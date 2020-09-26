

package me.TechsCode.EnderPermissions.base.registry;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.base.storage.Storable;

public class RegistryStorageEntry extends Storable
{
    private String key;
    private JsonObject value;
    private RegistryStorable storable;
    
    public RegistryStorageEntry(final String key, final JsonObject value) {
        this.key = key;
        this.value = value;
    }
    
    public void setStorable(final RegistryStorable storable) {
        this.storable = storable;
    }
    
    @Override
    public String getKey() {
        return this.key;
    }
    
    @Override
    public void setKey(final String key) {
        this.key = key;
    }
    
    @Override
    public JsonObject getState() {
        return this.value;
    }
    
    @Override
    public void setState(final JsonObject jsonObject, final TechPlugin techPlugin) {
        this.value = jsonObject;
        if (this.storable != null) {
            this.storable.setState(jsonObject);
        }
    }
    
    @Override
    public void onMount(final TechPlugin techPlugin) {
    }
    
    @Override
    protected void sync() {
        super.sync();
    }
}
