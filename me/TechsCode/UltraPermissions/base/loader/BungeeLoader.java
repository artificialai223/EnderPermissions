

package me.TechsCode.EnderPermissions.base.loader;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.base.BungeeTechPlugin;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeLoader extends Plugin
{
    private Loader loader;
    
    public void onEnable() {
        this.loader = new Loader((Object)this, (Class<? extends TechPlugin<?>>)BungeeTechPlugin.class, invokedynamic(DirectLeaks:(Ljava/lang/Object;)Ljava/lang/ClassLoader;, invokedynamic(DirectLeaks:(Ljava/lang/Object;)Ljava/lang/Class;, this)));
    }
    
    public void onDisable() {
        this.loader.unload();
    }
}
