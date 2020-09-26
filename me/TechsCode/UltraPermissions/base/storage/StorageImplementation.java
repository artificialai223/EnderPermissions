

package me.TechsCode.EnderPermissions.base.storage;

import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.base.TechPlugin;

public abstract class StorageImplementation
{
    protected TechPlugin plugin;
    protected String name;
    private boolean multiServerSupport;
    
    public StorageImplementation(final TechPlugin plugin, final String name, final boolean multiServerSupport) {
        this.plugin = plugin;
        this.name = name;
        this.multiServerSupport = multiServerSupport;
    }
    
    public abstract void destroy(final String p0, final WriteCallback p1);
    
    public abstract void create(final String p0, final JsonObject p1, final WriteCallback p2);
    
    public abstract void update(final String p0, final JsonObject p1, final WriteCallback p2);
    
    public abstract void read(final String p0, final ReadCallback p1);
    
    public boolean hasMultiServerSupport() {
        return this.multiServerSupport;
    }
}
