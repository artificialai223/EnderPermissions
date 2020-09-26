

package me.TechsCode.EnderPermissions.transfer;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.EnderPermissions;
import org.bukkit.event.Listener;

public class TransferAssistant implements Listener
{
    public TransferAssistant(final EnderPermissions EnderPermissions, final boolean b) {
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("§7EnderPermissions will now convert data. The server will shut down when complete"));
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)((TechPlugin<Plugin>)EnderPermissions).getBootstrap());
        final TransferStorageGroup transferStorageGroup;
        final TransferStorageGroup transferStorageGroup2;
        final TransferStorageGroup transferStorageGroup3;
        final TransferStorageGroup transferStorageGroup4;
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)((TechPlugin<Plugin>)EnderPermissions).getBootstrap(), () -> {
            EnderPermissions.log("Transferring from " + (b ? "File to MySQL" : "MySQL to File"));
            transferStorageGroup = new TransferStorageGroup(EnderPermissions, false);
            transferStorageGroup2 = new TransferStorageGroup(EnderPermissions, true);
            transferStorageGroup3 = (b ? transferStorageGroup : transferStorageGroup2);
            transferStorageGroup4 = (b ? transferStorageGroup2 : transferStorageGroup);
            transferStorageGroup3.copyTo(transferStorageGroup4);
            EnderPermissions.getScheduler().runTaskTimer(() -> {
                if (System.currentTimeMillis() - transferStorageGroup4.getLastWrite() > 3000L) {
                    Bukkit.shutdown();
                }
            }, 0L, 1200L);
        });
    }
    
    @EventHandler
    public void login(final PlayerLoginEvent playerLoginEvent) {
        playerLoginEvent.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§7Ultra Permissions is currently transferring. Wait until the server is stopped.");
    }
}
