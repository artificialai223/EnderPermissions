

package me.TechsCode.EnderPermissions.permissionlogger;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import org.bukkit.event.EventHandler;
import java.util.Iterator;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.function.Function;
import java.util.Comparator;
import java.util.Arrays;
import java.util.Collection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.EnderPermissions;
import java.util.ArrayList;
import org.bukkit.event.Listener;

public class PermissionLogger implements Listener
{
    private ArrayList<LoggedPermission> list;
    
    public PermissionLogger(final EnderPermissions EnderPermissions) {
        this.list = new ArrayList<LoggedPermission>();
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)((TechPlugin<Plugin>)EnderPermissions).getBootstrap());
    }
    
    public void log(final Player player, final String s, final boolean b, final String s2) {
        final LoggedPermission e = new LoggedPermission(player, s, b, System.currentTimeMillis(), s2);
        try {
            final LoggedPermission loggedPermission2;
            new ArrayList(this.list).stream().filter(loggedPermission -> loggedPermission != null).forEach(o -> {
                if (o.getPermission().equals(loggedPermission2.getPermission()) && o.getPlayer().equals(loggedPermission2.getPlayer())) {
                    this.list.remove(o);
                }
                return;
            });
            if (this.list.size() > 500) {
                this.list.remove(0);
            }
            this.list.add(e);
        }
        catch (Exception ex) {}
    }
    
    public LoggedPermission[] getLoggedPermissions() {
        return Arrays.stream((Object[])this.list.toArray((T[])new LoggedPermission[this.list.size()])).filter(loggedPermission -> loggedPermission != null).sorted(Comparator.comparing((Function<? super Object, ?>)LoggedPermission::getTime, Comparator.reverseOrder()).thenComparing((Function<? super Object, ? extends Comparable>)LoggedPermission::getPermission)).toArray(LoggedPermission[]::new);
    }
    
    @EventHandler
    public void clear(final PlayerQuitEvent playerQuitEvent) {
        try {
            for (final LoggedPermission o : new ArrayList<LoggedPermission>(this.list)) {
                if (o == null) {
                    continue;
                }
                if (o.getPlayer() == null) {
                    continue;
                }
                if (!o.getPlayer().equals(playerQuitEvent.getPlayer())) {
                    continue;
                }
                this.list.remove(o);
            }
        }
        catch (Exception ex) {}
    }
}
