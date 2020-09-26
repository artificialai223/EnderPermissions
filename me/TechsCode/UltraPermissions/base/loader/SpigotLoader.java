

package me.TechsCode.EnderPermissions.base.loader;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotLoader extends JavaPlugin
{
    private Loader loader;
    
    public void onEnable() {
        // invokedynamic(DirectLeaks:()V)
        this.loader = new Loader((Object)this, (Class<? extends TechPlugin<?>>)SpigotTechPlugin.class, invokedynamic(DirectLeaks:(Ljava/lang/Object;)Ljava/lang/ClassLoader;, this));
    }
    
    public void onDisable() {
        this.loader.unload();
    }
}
