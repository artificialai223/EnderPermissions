

package me.TechsCode.EnderPermissions.internal;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.bukkit.permissions.PermissionAttachmentInfo;
import java.util.Set;
import org.bukkit.permissions.Permissible;
import me.TechsCode.EnderPermissions.internal.lookup.LookupOutcome;
import me.TechsCode.EnderPermissions.internal.lookup.checks.DefinedPermissionCheck;
import me.TechsCode.EnderPermissions.internal.lookup.checks.AttachedPermissionCheck;
import me.TechsCode.EnderPermissions.internal.lookup.checks.BlankCheck;
import me.TechsCode.EnderPermissions.internal.lookup.checks.DefaultPermissionCheck;
import org.bukkit.OfflinePlayer;
import me.TechsCode.EnderPermissions.internal.lookup.checks.OperatorLookupCheck;
import me.TechsCode.EnderPermissions.internal.lookup.LookupCheck;
import me.TechsCode.EnderPermissions.internal.lookup.PermissionLookup;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import org.bukkit.permissions.ServerOperator;
import me.TechsCode.EnderPermissions.CachedPlayerPermissionProvider;
import org.bukkit.permissions.PermissionAttachment;
import java.util.List;
import me.TechsCode.EnderPermissions.permissionlogger.PermissionLogger;
import me.TechsCode.EnderPermissions.EnderPermissions;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissibleBase;

public class ModifiedPermissible extends PermissibleBase implements Listener
{
    private final Player p;
    private final EnderPermissions plugin;
    private final PermissionLogger permissionLogger;
    private final boolean defaultPerms;
    private final List<PermissionAttachment> attachments;
    private final CachedPlayerPermissionProvider permissionProvider;
    
    public ModifiedPermissible(final Player p3, final EnderPermissions plugin, final PermissionLogger permissionLogger) {
        super((ServerOperator)p3);
        this.p = p3;
        this.plugin = plugin;
        this.permissionLogger = permissionLogger;
        this.defaultPerms = plugin.isDefaultPermissionsEnabled();
        this.attachments = new ArrayList<PermissionAttachment>();
        this.permissionProvider = new CachedPlayerPermissionProvider(plugin);
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)((TechPlugin<Plugin>)plugin).getBootstrap());
    }
    
    public void clearCache() {
        this.permissionProvider.clearCache();
    }
    
    public boolean isPermissionSet(final String s) {
        return this.hasPermission(s);
    }
    
    public boolean isPermissionSet(final Permission permission) {
        if (this.isPermissionSet(permission.getName())) {
            return true;
        }
        if (this.defaultPerms) {
            return permission.getDefault().getValue(this.isOp());
        }
        return Permission.DEFAULT_PERMISSION.getValue(this.isOp());
    }
    
    public boolean hasPermission(final Permission permission) {
        return this.hasPermission(permission.getName());
    }
    
    public boolean hasPermission(final String s) {
        if (s == null) {
            return false;
        }
        final LookupOutcome perform = new PermissionLookup(s) {
            @Override
            public LookupCheck[] getChecks() {
                return new LookupCheck[] { new OperatorLookupCheck((OfflinePlayer)ModifiedPermissible.this.p), ModifiedPermissible.this.defaultPerms ? new DefaultPermissionCheck((OfflinePlayer)ModifiedPermissible.this.p) : new BlankCheck(), new AttachedPermissionCheck() {
                        @Override
                        public List<PermissionAttachment> getAttachments() {
                            return ModifiedPermissible.this.attachments;
                        }
                    }, new DefinedPermissionCheck() {
                        @Override
                        public List<me.TechsCode.EnderPermissions.storage.objects.Permission> getDefinedPermissions() {
                            return ModifiedPermissible.this.permissionProvider.retrieve(ModifiedPermissible.this.p.getUniqueId(), ModifiedPermissible.this.p.getWorld().getName());
                        }
                    } };
            }
        }.perform();
        this.permissionLogger.log(this.p, s, perform.getResult(), perform.getSource());
        return perform.getResult();
    }
    
    public PermissionAttachment addAttachment(final Plugin plugin) {
        final PermissionAttachment permissionAttachment = new PermissionAttachment(plugin, (Permissible)this);
        this.attachments.add(permissionAttachment);
        return permissionAttachment;
    }
    
    public PermissionAttachment addAttachment(final Plugin plugin, final String s, final boolean b) {
        final PermissionAttachment addAttachment = this.addAttachment(plugin);
        addAttachment.setPermission(s, b);
        return addAttachment;
    }
    
    public PermissionAttachment addAttachment(final Plugin plugin, final int n) {
        final PermissionAttachment addAttachment = this.addAttachment(plugin);
        Bukkit.getScheduler().runTaskLater(plugin, addAttachment::remove, (long)n);
        return addAttachment;
    }
    
    public PermissionAttachment addAttachment(final Plugin plugin, final String s, final boolean b, final int n) {
        final PermissionAttachment addAttachment = this.addAttachment(plugin, n);
        addAttachment.setPermission(s, b);
        return addAttachment;
    }
    
    public void removeAttachment(final PermissionAttachment permissionAttachment) {
        this.attachments.remove(permissionAttachment);
    }
    
    public void recalculatePermissions() {
    }
    
    public synchronized void clearPermissions() {
        this.attachments.clear();
    }
    
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return this.permissionProvider.retrieve(this.p.getUniqueId(), this.p.getWorld().getName()).stream().map(permission -> new PermissionAttachmentInfo((Permissible)this, permission.getName(), (PermissionAttachment)null, permission.isPositive())).collect((Collector<? super Object, ?, Set<PermissionAttachmentInfo>>)Collectors.toSet());
    }
}
