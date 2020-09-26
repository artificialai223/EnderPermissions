

package me.TechsCode.EnderPermissions.migration;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.storage.objects.Permission;
import me.TechsCode.EnderPermissions.storage.objects.User;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.EnderPermissions;
import org.bukkit.event.Listener;

public abstract class MigrationAssistant implements Listener
{
    private static boolean migrating;
    
    public static boolean isMigrating() {
        return MigrationAssistant.migrating;
    }
    
    public abstract String getPluginName();
    
    protected abstract void migrate(final EnderPermissions p0, final Runnable p1);
    
    public void doMigration(final EnderPermissions EnderPermissions) {
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)((TechPlugin<Plugin>)EnderPermissions).getBootstrap());
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("§7Please remove §e" + this.getPluginName() + " §7after the server has stopped"));
        final Runnable runnable;
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)((TechPlugin<Plugin>)EnderPermissions).getBootstrap(), () -> {
            EnderPermissions.getUsers().forEach(User::remove);
            EnderPermissions.getPermissions().forEach(Permission::remove);
            EnderPermissions.getGroups().forEach(Group::remove);
            runnable = (() -> new BukkitRunnable() {
                public void run() {
                    MigrationAssistant.migrating = false;
                    Bukkit.shutdown();
                }
            }.runTaskLater((Plugin)((TechPlugin<Plugin>)EnderPermissions).getBootstrap(), 60L));
            MigrationAssistant.migrating = true;
            this.migrate(EnderPermissions, runnable);
        });
    }
    
    @EventHandler
    public void login(final PlayerLoginEvent playerLoginEvent) {
        playerLoginEvent.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "§7The Server will be back in a few minutes");
    }
}
