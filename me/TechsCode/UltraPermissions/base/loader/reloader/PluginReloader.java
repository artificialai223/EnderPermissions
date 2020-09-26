

package me.TechsCode.EnderPermissions.base.loader.reloader;

import me.TechsCode.EnderPermissions.base.TechPlugin;

public abstract class PluginReloader<PLUGIN extends TechPlugin<?>>
{
    protected PLUGIN plugin;
    
    public PluginReloader(final PLUGIN plugin) {
        this.plugin = plugin;
    }
    
    public abstract void unload();
    
    public abstract void load();
}
