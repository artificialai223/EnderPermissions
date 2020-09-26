

package me.TechsCode.EnderPermissions;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.storage.objects.User;
import org.bukkit.event.EventHandler;
import me.TechsCode.EnderPermissions.tpl.SkinTexture;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class HeadPreloader implements Listener
{
    private EnderPermissions plugin;
    
    public HeadPreloader(final EnderPermissions plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)((TechPlugin<Plugin>)plugin).getBootstrap());
    }
    
    @EventHandler
    public void login(final PlayerJoinEvent playerJoinEvent) {
        final SkinTexture skinTexture;
        this.plugin.getScheduler().runTaskLaterAsync(() -> {
            skinTexture = (Bukkit.getOnlineMode() ? SkinTexture.fromPlayer(playerJoinEvent.getPlayer()) : SkinTexture.fromMojangAPI(playerJoinEvent.getPlayer().getName()));
            if (skinTexture != null && skinTexture.getUrl() != null) {
                this.plugin.getUsers().uuid(playerJoinEvent.getPlayer().getUniqueId()).ifPresent(user -> user.setSkinTexture(skinTexture));
            }
        }, 90L);
    }
}
