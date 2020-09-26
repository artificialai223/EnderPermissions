

package me.TechsCode.EnderPermissions.utility;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashSet;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import java.util.Set;

public class BukkitChildrenResolver
{
    private static BukkitChildrenResolver i;
    private Set<Permission> permissions;
    
    public static BukkitChildrenResolver getInstance() {
        if (BukkitChildrenResolver.i == null) {
            BukkitChildrenResolver.i = new BukkitChildrenResolver();
        }
        return BukkitChildrenResolver.i;
    }
    
    public BukkitChildrenResolver() {
        this.permissions = (Set<Permission>)Bukkit.getPluginManager().getPermissions();
    }
    
    public HashSet<String> retrieveChildPermissions(final String anotherString) {
        final Permission permission2 = this.permissions.stream().filter(permission -> permission.getName().equalsIgnoreCase(anotherString)).findFirst().orElse(null);
        if (permission2 == null) {
            return new HashSet<String>();
        }
        final HashSet<String> set = new HashSet<String>();
        final AbstractCollection<Object> collection;
        permission2.getChildren().forEach((e, b) -> {
            if (b) {
                ((HashSet<String>)collection).add(e);
                collection.addAll(this.retrieveChildPermissions(e));
            }
            return;
        });
        return set;
    }
}
