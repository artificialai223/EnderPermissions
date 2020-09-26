

package me.TechsCode.EnderPermissions.base.registry;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import com.google.gson.JsonObject;

public abstract class RegistryStorable
{
    private String key;
    private RegistryStorageEntry storageEntry;
    
    public RegistryStorable(final String key) {
        this.key = key;
    }
    
    public void setStorageEntry(final RegistryStorageEntry storageEntry) {
        (this.storageEntry = storageEntry).setStorable(this);
    }
    
    public String getKey() {
        return this.key;
    }
    
    public abstract void setState(final JsonObject p0);
    
    public abstract JsonObject getState();
    
    public void sync() {
        this.storageEntry.setState(this.getState(), null);
        this.storageEntry.sync();
    }
}
