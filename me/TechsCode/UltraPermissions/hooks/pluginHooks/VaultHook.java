

package me.TechsCode.EnderPermissions.hooks.pluginHooks;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import org.bukkit.plugin.ServicesManager;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.permission.Permission;
import me.TechsCode.EnderPermissions.EnderPermissions;

public class VaultHook
{
    private final EnderPermissions plugin;
    private final VaultPermissionHook permissionHook;
    private final VaultChatHook vaultChatHook;
    
    public VaultHook(final EnderPermissions plugin) {
        this.plugin = plugin;
        this.permissionHook = new VaultPermissionHook(plugin);
        this.vaultChatHook = new VaultChatHook(this.permissionHook, plugin);
        this.hook();
    }
    
    public void hook() {
        final ServicesManager servicesManager = this.plugin.getBootstrap().getServer().getServicesManager();
        servicesManager.register((Class)Permission.class, (Object)this.permissionHook, (Plugin)((TechPlugin<Plugin>)this.plugin).getBootstrap(), ServicePriority.High);
        servicesManager.register((Class)Chat.class, (Object)this.vaultChatHook, (Plugin)((TechPlugin<Plugin>)this.plugin).getBootstrap(), ServicePriority.High);
    }
    
    public void unhook() {
        this.plugin.getBootstrap().getServer().getServicesManager().unregister((Class)Permission.class, (Object)this.permissionHook);
    }
    
    public void clearCache() {
        this.permissionHook.clearCache();
        this.vaultChatHook.clearCache();
    }
}
