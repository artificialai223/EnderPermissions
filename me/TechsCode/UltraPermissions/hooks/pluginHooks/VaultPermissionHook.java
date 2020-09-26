

package me.TechsCode.EnderPermissions.hooks.pluginHooks;

import org.bukkit.command.CommandSender;
import java.util.function.Function;
import org.bukkit.World;
import me.TechsCode.EnderPermissions.storage.collection.PermissionList;
import java.util.Collection;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import com.google.common.base.Preconditions;
import me.TechsCode.EnderPermissions.storage.objects.PermissionHolder;
import org.bukkit.entity.Player;
import org.bukkit.OfflinePlayer;
import java.util.Collections;
import me.TechsCode.EnderPermissions.storage.objects.User;
import java.util.List;
import me.TechsCode.EnderPermissions.internal.lookup.checks.DefinedPermissionCheck;
import me.TechsCode.EnderPermissions.internal.lookup.checks.DefaultPermissionCheck;
import me.TechsCode.EnderPermissions.internal.lookup.checks.BlankCheck;
import me.TechsCode.EnderPermissions.internal.lookup.checks.OperatorLookupCheck;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.internal.lookup.LookupCheck;
import me.TechsCode.EnderPermissions.internal.lookup.PermissionLookup;
import java.util.UUID;
import me.TechsCode.EnderPermissions.CachedPlayerPermissionProvider;
import me.TechsCode.EnderPermissions.base.networking.NServer;
import java.util.Optional;
import me.TechsCode.EnderPermissions.EnderPermissions;
import net.milkbowl.vault.permission.Permission;

public class VaultPermissionHook extends Permission
{
    private final EnderPermissions plugin;
    private final Optional<NServer> thisServer;
    private final CachedPlayerPermissionProvider permissionProvider;
    
    public VaultPermissionHook(final EnderPermissions plugin) {
        this.plugin = plugin;
        this.thisServer = plugin.getThisServer();
        this.permissionProvider = new CachedPlayerPermissionProvider(plugin);
    }
    
    public void clearCache() {
        this.permissionProvider.clearCache();
    }
    
    public String getName() {
        return "EnderPermissions";
    }
    
    public boolean isEnabled() {
        return true;
    }
    
    public boolean hasSuperPermsCompat() {
        return true;
    }
    
    public boolean playerHas(String string, final String s, final String s2) {
        if (string == null) {
            string = UUID.randomUUID().toString();
        }
        return new PermissionLookup(s2) {
            @Override
            public LookupCheck[] getChecks() {
                final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(s);
                return new LookupCheck[] { (offlinePlayer != null) ? new OperatorLookupCheck(offlinePlayer) : new BlankCheck(), (VaultPermissionHook.this.plugin.isDefaultPermissionsEnabled() && offlinePlayer != null) ? new DefaultPermissionCheck(offlinePlayer) : new BlankCheck(), new DefinedPermissionCheck() {
                        @Override
                        public List<me.TechsCode.EnderPermissions.storage.objects.Permission> getDefinedPermissions() {
                            final Optional<User> user = VaultPermissionHook.this.user(s);
                            return (List<me.TechsCode.EnderPermissions.storage.objects.Permission>)(user.isPresent() ? VaultPermissionHook.this.permissionProvider.retrieve(user.get().getUuid(), string) : Collections.emptyList());
                        }
                    } };
            }
        }.perform().getResult();
    }
    
    public boolean playerHas(final String s, final OfflinePlayer offlinePlayer, final String s2) {
        return this.playerHas(s, offlinePlayer.getName(), s2);
    }
    
    public boolean playerHas(final Player player, final String s) {
        return player.hasPermission(s);
    }
    
    public boolean playerAdd(final String s, final String s2, final String s3) {
        return this.add(s, this.user(s2).orElse(null), s3);
    }
    
    public boolean playerAdd(final String s, final OfflinePlayer offlinePlayer, final String s2) {
        return this.playerAdd(s, offlinePlayer.getName(), s2);
    }
    
    public boolean playerAdd(final Player player, final String s) {
        return this.playerAdd(player.getWorld().getName(), player.getName(), s);
    }
    
    public boolean playerRemove(final String s, final String s2, final String s3) {
        return this.remove(s, this.user(s2).orElse(null), s3);
    }
    
    public boolean playerRemove(final String s, final OfflinePlayer offlinePlayer, final String s2) {
        return this.playerRemove(s, offlinePlayer.getName(), s2);
    }
    
    public boolean playerRemove(final Player player, final String s) {
        return this.playerRemove(player.getWorld().getName(), player.getName(), s);
    }
    
    public boolean groupHas(final String s, final String s2, final String s3) {
        Preconditions.checkArgument(s2 != null, (Object)"Group Name has to be defined");
        return new PermissionLookup(s3) {
            final /* synthetic */ Optional val$group = VaultPermissionHook.this.group(s, s2);
            
            @Override
            public LookupCheck[] getChecks() {
                return new LookupCheck[] { this.val$group.isPresent() ? new DefinedPermissionCheck() {
                        @Override
                        public List<me.TechsCode.EnderPermissions.storage.objects.Permission> getDefinedPermissions() {
                            final PermissionList permissions = PermissionLookup.this.val$group.get().getPermissions();
                            permissions.addAll(PermissionLookup.this.val$group.get().getInheritedPermissions());
                            return permissions;
                        }
                    } : new BlankCheck() };
            }
        }.getOutcome().getResult();
    }
    
    public boolean groupHas(final World world, final String s, final String s2) {
        return this.groupHas(world.getName(), s, s2);
    }
    
    public boolean groupAdd(final String s, final String s2, final String s3) {
        return this.add(s, this.group(s, s2).orElse(null), s3);
    }
    
    public boolean groupAdd(final World world, final String s, final String s2) {
        return this.groupAdd(world.getName(), s, s2);
    }
    
    public boolean groupRemove(final String s, final String s2, final String s3) {
        return this.remove(s, this.group(s, s2).orElse(null), s3);
    }
    
    public boolean groupRemove(final World world, final String s, final String s2) {
        return this.groupRemove(world.getName(), s, s2);
    }
    
    public boolean playerInGroup(final String s, final String s2, final String s3) {
        final Optional<User> user = this.user(s2);
        final Optional<Group> group = this.group(s, s3);
        return user.isPresent() && group.isPresent() && user.get().getGroups().contains(group.get().toStored());
    }
    
    public boolean playerInGroup(final World world, final String s, final String s2) {
        return this.playerInGroup(world.getName(), s, s2);
    }
    
    public boolean playerInGroup(final String s, final OfflinePlayer offlinePlayer, final String s2) {
        return this.playerInGroup(s, offlinePlayer.getName(), s2);
    }
    
    public boolean playerInGroup(final Player player, final String s) {
        return this.playerInGroup(player.getWorld().getName(), player.getName(), s);
    }
    
    public boolean playerAddGroup(final String s, final String s2, final String s3) {
        final Optional<User> user = this.user(s2);
        final Optional<Group> group = this.group(s, s3);
        if (!user.isPresent() || !group.isPresent()) {
            return false;
        }
        user.get().addGroup(group.get());
        return true;
    }
    
    public boolean playerAddGroup(final String s, final OfflinePlayer offlinePlayer, final String s2) {
        return this.playerAddGroup(s, offlinePlayer.getName(), s2);
    }
    
    public boolean playerAddGroup(final Player player, final String s) {
        return this.playerAddGroup(player.getWorld().getName(), player.getName(), s);
    }
    
    public boolean playerRemoveGroup(final String s, final String s2, final String s3) {
        final Optional<User> user = this.user(s2);
        final Optional<Group> group = this.group(s, s3);
        if (!user.isPresent() || !group.isPresent()) {
            return false;
        }
        if (!user.get().getGroups().contains(group.get().toStored())) {
            return false;
        }
        user.get().removeGroup(group.get());
        return true;
    }
    
    public boolean playerRemoveGroup(final String s, final OfflinePlayer offlinePlayer, final String s2) {
        return this.playerRemoveGroup(s, offlinePlayer.getName(), s2);
    }
    
    public boolean playerRemoveGroup(final Player player, final String s) {
        return this.playerRemoveGroup(player.getWorld().getName(), player.getName(), s);
    }
    
    public String[] getPlayerGroups(final String s, final String s2) {
        return this.user(s2).map(user -> user.getActiveGroups().servers(true, this.plugin.getThisServer().map((Function<? super NServer, ? extends String>)NServer::getName).orElse(null)).worlds(true, (s == null || s.isEmpty()) ? "world" : s).stream().map((Function<? super Group, ?>)Group::getName).toArray(String[]::new)).orElseGet(() -> new String[0]);
    }
    
    public String[] getPlayerGroups(final String s, final OfflinePlayer offlinePlayer) {
        return this.getPlayerGroups(s, offlinePlayer.getName());
    }
    
    public String[] getPlayerGroups(final Player player) {
        return this.getPlayerGroups(player.getWorld().getName(), player.getName());
    }
    
    public String getPrimaryGroup(final String s, final String s2) {
        final String[] playerGroups = this.getPlayerGroups(s, s2);
        return (playerGroups.length == 0) ? null : playerGroups[0];
    }
    
    public String getPrimaryGroup(final String s, final OfflinePlayer offlinePlayer) {
        return this.getPrimaryGroup(s, offlinePlayer.getName());
    }
    
    public String getPrimaryGroup(final Player player) {
        return this.getPrimaryGroup(player.getWorld().getName(), player.getName());
    }
    
    public String[] getGroups() {
        return this.plugin.getGroups().servers(true, this.plugin.getThisServer().map((Function<? super NServer, ? extends String>)NServer::getName).orElse(null)).stream().map((Function<? super Group, ?>)Group::getName).toArray(String[]::new);
    }
    
    public boolean hasGroupSupport() {
        return true;
    }
    
    public boolean has(final Player player, final String s) {
        return player.hasPermission(s);
    }
    
    public boolean has(final CommandSender commandSender, final String s) {
        return commandSender.hasPermission(s);
    }
    
    public Optional<User> user(final String s) {
        return this.plugin.getUsers().name(s);
    }
    
    public Optional<Group> group(final String s, final String s2) {
        return this.plugin.getGroups().servers(true, this.plugin.getThisServer().map((Function<? super NServer, ? extends String>)NServer::getName).orElse(null)).worlds(true, s).name(s2);
    }
    
    public boolean add(final String s, final PermissionHolder permissionHolder, final String s2) {
        if (permissionHolder == null) {
            return false;
        }
        permissionHolder.newPermission(s2).setWorld((s == null || s.isEmpty()) ? "world" : s).setServer(this.thisServer.map((Function<? super NServer, ? extends String>)NServer::getName).orElse(null)).create();
        return true;
    }
    
    public boolean remove(final String s, final PermissionHolder permissionHolder, final String s2) {
        if (permissionHolder == null) {
            return false;
        }
        permissionHolder.getPermissions().name(s2).servers(true, this.plugin.getThisServer().map((Function<? super NServer, ? extends String>)NServer::getName).orElse(null)).worlds(true, (s == null || s.isEmpty()) ? "world" : s).forEach(me.TechsCode.EnderPermissions.storage.objects.Permission::remove);
        return true;
    }
}
