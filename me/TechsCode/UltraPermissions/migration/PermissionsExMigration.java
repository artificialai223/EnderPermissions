

package me.TechsCode.EnderPermissions.migration;

import java.lang.reflect.InvocationTargetException;
import ru.tehkode.permissions.PermissionsData;
import java.util.List;
import java.util.Map;
import me.TechsCode.EnderPermissions.storage.objects.User;
import java.util.Iterator;
import ru.tehkode.permissions.PermissionManager;
import org.bukkit.Bukkit;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionEntity;
import me.TechsCode.EnderPermissions.storage.objects.PermissionHolder;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import java.util.HashMap;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import me.TechsCode.EnderPermissions.EnderPermissions;
import java.lang.reflect.Method;

public class PermissionsExMigration extends MigrationAssistant
{
    private static final Method GET_DATA_METHOD;
    
    @Override
    public String getPluginName() {
        return "Permissions Ex";
    }
    
    @Override
    protected void migrate(final EnderPermissions EnderPermissions, final Runnable runnable) {
        final PermissionManager permissionManager = PermissionsEx.getPermissionManager();
        final HashMap<Object, Group> hashMap = new HashMap<Object, Group>();
        final Group value;
        final HashMap<String, Group> hashMap2;
        permissionManager.getGroupList().forEach(permissionGroup -> {
            EnderPermissions.newGroup(permissionGroup.getName()).setPrefix(permissionGroup.getOwnPrefix()).setSuffix(permissionGroup.getOwnSuffix()).setDefault(permissionGroup.isDefault("world") || permissionGroup.getName().equals("default")).setPriority(permissionGroup.getRank() + 1).create();
            EnderPermissions.log("Created Group " + value.getName());
            this.addPermissions(EnderPermissions, value, (PermissionEntity)permissionGroup);
            hashMap2.put(permissionGroup.getName(), value);
            return;
        });
        final PermissionManager permissionManager2;
        final Iterator<PermissionGroup> iterator;
        PermissionGroup permissionGroup2;
        final HashMap<K, Group> hashMap3;
        hashMap.forEach((s, group) -> {
            permissionManager2.getGroup(s).getParents().iterator();
            while (iterator.hasNext()) {
                permissionGroup2 = iterator.next();
                if (hashMap3.containsKey(permissionGroup2.getName())) {
                    group.addInheritance(hashMap3.get(permissionGroup2.getName()));
                    EnderPermissions.log("Added Inherited Group " + permissionGroup2.getName() + " to " + group.getName());
                }
            }
            return;
        });
        for (final PermissionUser permissionUser : permissionManager.getUsers()) {
            final User registerUser = EnderPermissions.registerUser(Bukkit.getOfflinePlayer(permissionUser.getName()).getUniqueId(), permissionUser.getName(), false);
            for (final PermissionGroup permissionGroup3 : permissionUser.getGroups()) {
                if (hashMap.containsKey(permissionGroup3.getName())) {
                    registerUser.addGroup(hashMap.get(permissionGroup3.getName()));
                }
            }
            registerUser.setPrefix(permissionUser.getOwnPrefix());
            registerUser.setSuffix(permissionUser.getOwnSuffix());
            EnderPermissions.log("Added User " + registerUser.getName());
            this.addPermissions(EnderPermissions, registerUser, (PermissionEntity)permissionUser);
        }
        runnable.run();
    }
    
    private void addPermissions(final EnderPermissions EnderPermissions, final PermissionHolder permissionHolder, final PermissionEntity permissionEntity) {
        for (final Map.Entry<String, List<String>> entry : getPermanentPermissions(permissionEntity).entrySet()) {
            String world = null;
            if (entry.getKey() != null && !entry.getKey().isEmpty()) {
                world = entry.getKey();
            }
            for (final String str : entry.getValue()) {
                if (str.isEmpty()) {
                    continue;
                }
                permissionHolder.newPermission(str).setWorld(world).create();
                EnderPermissions.log("Added Permission " + str + " to " + permissionHolder.getName());
            }
        }
    }
    
    private static Map<String, List<String>> getPermanentPermissions(final PermissionEntity obj) {
        try {
            return (Map<String, List<String>>)((PermissionsData)PermissionsExMigration.GET_DATA_METHOD.invoke(obj, new Object[0])).getPermissionsMap();
        }
        catch (IllegalAccessException | InvocationTargetException ex) {
            final Object cause;
            throw new RuntimeException((Throwable)cause);
        }
    }
    
    static {
        try {
            (GET_DATA_METHOD = PermissionEntity.class.getDeclaredMethod("getData", (Class<?>[])new Class[0])).setAccessible(true);
        }
        catch (NoSuchMethodException thrown) {
            throw new ExceptionInInitializerError(thrown);
        }
    }
}
