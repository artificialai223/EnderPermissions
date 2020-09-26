

package me.TechsCode.EnderPermissions.hooks;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.util.Collection;
import java.util.HashSet;
import me.TechsCode.EnderPermissions.hooks.pluginHooks.NametagEditHook;
import me.TechsCode.EnderPermissions.hooks.pluginHooks.VaultHook;
import me.TechsCode.EnderPermissions.hooks.pluginHooks.MvdwPlaceholderAPIHook;
import me.TechsCode.EnderPermissions.hooks.pluginHooks.PlaceholderAPIHook;
import java.util.HashMap;
import me.TechsCode.EnderPermissions.EnderPermissions;
import org.bukkit.event.Listener;

public class PluginHookManager implements Listener
{
    private final EnderPermissions plugin;
    private final HashMap<String, Runnable> hooks;
    private PlaceholderAPIHook placeholderAPIHook;
    private MvdwPlaceholderAPIHook mvdwPlaceholderAPIHook;
    private VaultHook vaultHook;
    private NametagEditHook nametagEditHook;
    
    public PluginHookManager(final EnderPermissions plugin) {
        this.plugin = plugin;
        (this.hooks = new HashMap<String, Runnable>()).put("NametagEdit", () -> this.nametagEditHook = new NametagEditHook(plugin));
        this.hooks.put("Vault", () -> this.vaultHook = new VaultHook(plugin));
        this.hooks.put("PlaceholderAPI", () -> this.placeholderAPIHook = new PlaceholderAPIHook(plugin));
        this.hooks.put("MVdWPlaceholderAPI", () -> this.mvdwPlaceholderAPIHook = new MvdwPlaceholderAPIHook(plugin));
        new HashSet(this.hooks.keySet()).forEach(s -> this.tryHook(false, s));
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)((TechPlugin<Plugin>)plugin).getBootstrap());
    }
    
    public void tryHook(final boolean b, final String s) {
        if (!this.hooks.containsKey(s)) {
            return;
        }
        if (b || Bukkit.getPluginManager().isPluginEnabled(s)) {
            this.hooks.get(s).run();
            this.hooks.remove(s);
            this.plugin.log("Hooked into " + s);
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLoad(final PluginEnableEvent pluginEnableEvent) {
        this.tryHook(true, pluginEnableEvent.getPlugin().getName());
    }
    
    public String replacePlaceholders(final Player player, String s) {
        if (this.placeholderAPIHook != null) {
            s = this.placeholderAPIHook.replace((OfflinePlayer)player, s);
        }
        if (this.mvdwPlaceholderAPIHook != null) {
            s = this.mvdwPlaceholderAPIHook.replace(player, s);
        }
        return s;
    }
    
    public void clearCaches() {
        if (this.vaultHook != null) {
            this.vaultHook.clearCache();
        }
    }
}
