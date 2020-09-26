

package me.TechsCode.EnderPermissions.base.registry;

import me.TechsCode.EnderPermissions.base.storage.implementations.MySQL;
import me.TechsCode.EnderPermissions.base.storage.StorageImplementation;
import me.TechsCode.EnderPermissions.base.storage.implementations.LocalFile;
import me.TechsCode.EnderPermissions.base.TechPlugin;

public class Registry
{
    private TechPlugin plugin;
    private RegistryStorage local;
    private RegistryStorage global;
    
    public Registry(final TechPlugin plugin) {
        this.plugin = plugin;
        this.local = new RegistryStorage(plugin, LocalFile.class);
    }
    
    private void startRemoteRegistry() {
        if (this.global != null) {
            return;
        }
        this.global = ((this.plugin.getMySQLManager() != null && this.plugin.getMySQLManager().isEnabled()) ? new RegistryStorage(this.plugin, MySQL.class) : null);
    }
    
    public <T extends RegistryStorable> BiRegistry<T> register(final Class<? extends T> clazz) {
        try {
            return this.register((T)clazz.newInstance());
        }
        catch (InstantiationException | IllegalAccessException ex) {
            final Throwable t;
            t.printStackTrace();
            return null;
        }
    }
    
    public <T extends RegistryStorable> BiRegistry<T> register(final T t) {
        return new BiRegistry<T>(this.register(t, RegistrationChoice.LOCAL), this.register(t, RegistrationChoice.GLOBAL));
    }
    
    public <T extends RegistryStorable> T register(final Class<? extends T> clazz, final RegistrationChoice registrationChoice) {
        try {
            return this.register((T)clazz.newInstance(), registrationChoice);
        }
        catch (InstantiationException | IllegalAccessException ex) {
            final Throwable t;
            t.printStackTrace();
            return null;
        }
    }
    
    public <T extends RegistryStorable> T register(final T t, final RegistrationChoice registrationChoice) {
        this.startRemoteRegistry();
        Registration local = null;
        switch (registrationChoice) {
            case LOCAL: {
                local = Registration.LOCAL;
                break;
            }
            case GLOBAL: {
                local = ((this.global != null) ? Registration.GLOBAL : null);
                break;
            }
            case GLOBAL_IF_AVAILABLE: {
                local = ((this.global != null) ? Registration.GLOBAL : Registration.LOCAL);
                break;
            }
        }
        if (local == null) {
            return null;
        }
        final RegistryStorageEntry retrieve = ((local == Registration.LOCAL) ? this.local : this.global).retrieve(new RegistryStorageEntry(t.getKey(), t.getState()));
        t.setStorageEntry(retrieve);
        t.setState(retrieve.getState());
        return t;
    }
}
