

package me.TechsCode.EnderPermissions;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.storage.collection.PermissionList;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.storage.collection.UserList;
import me.TechsCode.EnderPermissions.storage.collection.GroupList;
import me.TechsCode.EnderPermissions.storage.objects.Permission;
import me.TechsCode.EnderPermissions.storage.objects.UserRankup;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.tpl.task.UpdateTime;
import me.TechsCode.EnderPermissions.tpl.task.UpdateEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import java.util.Collection;
import java.util.Optional;
import org.bukkit.permissions.PermissibleBase;
import me.TechsCode.EnderPermissions.internal.PermissibleInjector;
import me.TechsCode.EnderPermissions.storage.objects.User;
import org.bukkit.plugin.Plugin;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.internal.ModifiedPermissible;
import org.bukkit.entity.Player;
import java.util.HashMap;
import me.TechsCode.EnderPermissions.permissionlogger.PermissionLogger;
import org.bukkit.event.Listener;

public class PluginEvents implements Listener
{
    private final EnderPermissions plugin;
    private final PermissionLogger permissionLogger;
    private HashMap<Player, ModifiedPermissible> permissibleHashMap;
    
    public PluginEvents(final EnderPermissions plugin, final PermissionLogger permissionLogger) {
        this.plugin = plugin;
        this.permissionLogger = permissionLogger;
        this.permissibleHashMap = new HashMap<Player, ModifiedPermissible>();
        Bukkit.getOnlinePlayers().forEach(this::login);
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)((TechPlugin<Plugin>)plugin).getBootstrap());
    }
    
    public void login(final Player key) {
        final Optional<User> uuid = this.plugin.getUsers().uuid(key.getUniqueId());
        if (uuid.isPresent()) {
            if (!uuid.get().getName().equals(key.getName())) {
                uuid.get().setPlayerName(key.getName());
            }
        }
        else {
            this.plugin.registerUser(key.getUniqueId(), key.getName(), true);
        }
        try {
            final ModifiedPermissible value = new ModifiedPermissible(key, this.plugin, this.permissionLogger);
            this.permissibleHashMap.put(key, value);
            PermissibleInjector.inject(key, value);
        }
        catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
    
    public Collection<ModifiedPermissible> getPermissibles() {
        return this.permissibleHashMap.values();
    }
    
    @EventHandler(priority = EventPriority.LOW)
    public void login(final PlayerLoginEvent playerLoginEvent) {
        if (playerLoginEvent.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST) {
            return;
        }
        this.login(playerLoginEvent.getPlayer());
    }
    
    @EventHandler
    public void quit(final PlayerQuitEvent playerQuitEvent) {
        this.permissibleHashMap.remove(playerQuitEvent.getPlayer());
    }
    
    @EventHandler
    public void updateData(final UpdateEvent updateEvent) {
        if (updateEvent.getUpdateTime() != UpdateTime.SEC) {
            return;
        }
        final GroupList list;
        final UserList list2;
        final Iterator<User> iterator;
        User user;
        final DefaultGroupAssignOption defaultGroupAssignOption;
        boolean b;
        boolean b2;
        final GroupList list3;
        final Iterator<Group> iterator2;
        Group group;
        final Iterator<UserRankup> iterator3;
        UserRankup userRankup;
        final Iterator<Group> iterator4;
        Group group2;
        final Iterator<Group> iterator5;
        Group group3;
        boolean b3;
        boolean b4;
        final PermissionList list4;
        this.plugin.getScheduler().runAsync(() -> {
            this.plugin.getGroups();
            this.plugin.getUsers();
            this.plugin.getPermissions();
            list.defaults(true);
            list2.iterator();
            while (iterator.hasNext()) {
                user = iterator.next();
                this.plugin.getDefaultGroupAssignOption();
                b = (user.getGroups().isEmpty() && defaultGroupAssignOption == DefaultGroupAssignOption.NO_GROUP);
                b2 = (defaultGroupAssignOption == DefaultGroupAssignOption.ALWAYS_ASSIGN);
                if (b || b2) {
                    list3.iterator();
                    while (iterator2.hasNext()) {
                        group = iterator2.next();
                        if (!user.getGroups().contains(group.toStored())) {
                            user.addGroup(group);
                        }
                    }
                }
                user.getRankups().iterator();
                while (iterator3.hasNext()) {
                    userRankup = iterator3.next();
                    if (userRankup.isExpired()) {
                        userRankup.remove();
                    }
                }
            }
            list.iterator();
            while (iterator4.hasNext()) {
                group2 = iterator4.next();
                group2.getActiveInheritedGroups().iterator();
                while (iterator5.hasNext()) {
                    group3 = iterator5.next();
                    b3 = !group2.getServer().equals(group3.getServer());
                    b4 = !group2.getWorld().equals(group3.getWorld());
                    if (b3 || b4) {
                        group2.removeInheritance(group3);
                    }
                }
            }
            list4.expired().forEach(Permission::remove);
        });
    }
}
