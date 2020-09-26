

package me.TechsCode.EnderPermissions.bungee;

import me.TechsCode.EnderPermissions.storage.collection.UserList;
import me.TechsCode.EnderPermissions.storage.collection.GroupList;
import me.TechsCode.EnderPermissions.storage.GroupCreator;
import me.TechsCode.EnderPermissions.storage.PermissionCreator;
import me.TechsCode.EnderPermissions.storage.objects.Holder;
import java.util.Optional;
import java.util.Collection;
import me.TechsCode.EnderPermissions.storage.objects.User;
import net.md_5.bungee.event.EventHandler;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.storage.objects.Permission;
import java.util.List;
import me.TechsCode.EnderPermissions.internal.lookup.checks.DefinedPermissionCheck;
import me.TechsCode.EnderPermissions.internal.lookup.LookupCheck;
import me.TechsCode.EnderPermissions.internal.lookup.PermissionLookup;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.ProxyServer;
import me.TechsCode.EnderPermissions.base.storage.StorageImplementation;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.base.storage.implementations.MySQL;
import me.TechsCode.EnderPermissions.base.networking.BungeeNetworkManager;
import net.md_5.bungee.api.plugin.Plugin;
import me.TechsCode.EnderPermissions.storage.UserStorage;
import me.TechsCode.EnderPermissions.storage.PermissionStorage;
import me.TechsCode.EnderPermissions.storage.GroupStorage;
import me.TechsCode.EnderPermissions.storage.collection.PermissionList;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import java.util.HashMap;
import me.TechsCode.EnderPermissions.StorageController;
import me.TechsCode.EnderPermissions.EnderPermissionsAPI;
import net.md_5.bungee.api.plugin.Listener;
import me.TechsCode.EnderPermissions.base.BungeeTechPlugin;

public class EnderPermissionsBungee extends BungeeTechPlugin implements Listener, EnderPermissionsAPI, StorageController
{
    private static EnderPermissionsAPI api;
    private boolean loaded;
    private HashMap<ProxiedPlayer, PermissionList> permissionsCache;
    private GroupStorage groupStorage;
    private PermissionStorage permissionStorage;
    private UserStorage userStorage;
    
    public EnderPermissionsBungee(final Plugin plugin) {
        super(plugin);
    }
    
    protected void onEnable() {
        EnderPermissionsBungee.api = this;
        this.loaded = false;
        new BungeeNetworkManager(this);
        if (this.getMySQLManager().isEnabled()) {
            this.onLoad();
        }
        else {
            this.log("Waiting for MySQL Credentials...");
        }
        this.getScheduler().runTaskTimer(() -> {
            if (!this.loaded && this.getMySQLManager().isEnabled()) {
                this.onLoad();
            }
        }, 20L, 20L);
    }
    
    private void onLoad() {
        this.loaded = true;
        this.permissionsCache = new HashMap<ProxiedPlayer, PermissionList>();
        this.groupStorage = new GroupStorage(this, MySQL.class);
        this.permissionStorage = new PermissionStorage(this, MySQL.class);
        this.userStorage = new UserStorage(this, MySQL.class);
        ProxyServer.getInstance().getPluginManager().registerListener((Plugin)this.getBootstrap(), (Listener)this);
    }
    
    protected void onDisable() {
    }
    
    @EventHandler
    public void check(final PermissionCheckEvent permissionCheckEvent) {
        if (permissionCheckEvent.getSender() instanceof ProxiedPlayer) {
            permissionCheckEvent.setHasPermission(new PermissionLookup(permissionCheckEvent.getPermission()) {
                final /* synthetic */ ProxiedPlayer val$pp = (ProxiedPlayer)permissionCheckEvent.getSender();
                
                @Override
                public LookupCheck[] getChecks() {
                    return new LookupCheck[] { new DefinedPermissionCheck() {
                            @Override
                            public List<Permission> getDefinedPermissions() {
                                final PermissionList permissions = EnderPermissionsBungee.this.getPermissions(PermissionLookup.this.val$pp);
                                return (permissions == null) ? new ArrayList<Permission>() : permissions;
                            }
                        } };
                }
            }.perform().getResult());
        }
    }
    
    public PermissionList getPermissions(final ProxiedPlayer key) {
        if (this.permissionsCache.containsKey(key)) {
            return this.permissionsCache.get(key);
        }
        final Optional<User> uuid = this.userStorage.getUsers().uuid(key.getUniqueId());
        if (!uuid.isPresent()) {
            this.log("Cannot find the user");
            return null;
        }
        final PermissionList bungee = uuid.get().getPermissions().bungee();
        bungee.addAll(uuid.get().getAdditionalPermissions().bungee());
        this.permissionsCache.put(key, bungee);
        return bungee;
    }
    
    public boolean isAllowingInvalidMySQL() {
        return true;
    }
    
    public PermissionCreator newPermission(final String s, final Holder holder) {
        return this.permissionStorage.newPermission(s, holder);
    }
    
    public GroupCreator newGroup(final String s) {
        return this.groupStorage.newGroup(s);
    }
    
    public PermissionList getPermissions() {
        return this.permissionStorage.getPermissions();
    }
    
    public GroupList getGroups() {
        return this.groupStorage.getGroups();
    }
    
    public UserList getUsers() {
        return this.userStorage.getUsers();
    }
    
    public static EnderPermissionsAPI getAPI() {
        return EnderPermissionsBungee.api;
    }
    
    public void onDataModification() {
        this.permissionsCache.clear();
    }
    
    public GroupStorage getGroupStorage() {
        return this.groupStorage;
    }
    
    public PermissionStorage getPermissionStorage() {
        return this.permissionStorage;
    }
    
    public UserStorage getUserStorage() {
        return this.userStorage;
    }
}
