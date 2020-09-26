

package me.TechsCode.EnderPermissions.migration;

import java.util.UUID;
import me.TechsCode.EnderPermissions.storage.objects.User;
import java.util.Iterator;
import me.lucko.luckperms.api.caching.MetaData;
import me.lucko.luckperms.api.LuckPermsApi;
import java.util.concurrent.ExecutionException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import me.lucko.luckperms.api.Node;
import java.util.List;
import me.TechsCode.EnderPermissions.storage.objects.PermissionHolder;
import me.lucko.luckperms.api.Contexts;
import java.util.Arrays;
import me.lucko.luckperms.api.Group;
import java.util.HashMap;
import me.lucko.luckperms.LuckPerms;
import me.TechsCode.EnderPermissions.EnderPermissions;

public class LuckPermsMigration extends MigrationAssistant
{
    public void migrate(final EnderPermissions EnderPermissions, final Runnable runnable) {
        final LuckPermsApi api = LuckPerms.getApi();
        final HashMap<Group, me.TechsCode.EnderPermissions.storage.objects.Group> hashMap = new HashMap<Group, me.TechsCode.EnderPermissions.storage.objects.Group>();
        final Group[] a = api.getGroups().toArray(new Group[api.getGroups().size()]);
        Arrays.sort(a, (group, group2) -> {
            if (!group.getWeight().isPresent() || !group2.getWeight().isPresent()) {
                return 0;
            }
            else {
                return group.getWeight().getAsInt() - group2.getWeight().getAsInt();
            }
        });
        int priority = 1;
        for (final Group key : a) {
            final MetaData metaData = key.getCachedData().getMetaData(Contexts.allowAll());
            final me.TechsCode.EnderPermissions.storage.objects.Group create = EnderPermissions.newGroup(key.getName()).setDefault(key.getName().equals("default")).setPrefix(metaData.getPrefix()).setSuffix(metaData.getSuffix()).setPriority(priority).create();
            this.addNodes(create, key.getOwnNodes());
            hashMap.put(key, create);
            ++priority;
        }
        final LuckPermsApi luckPermsApi;
        final Iterator<Group> iterator;
        Group key2;
        final HashMap<K, me.TechsCode.EnderPermissions.storage.objects.Group> hashMap2;
        hashMap.forEach((group3, group5) -> {
            luckPermsApi.getGroups().iterator();
            while (iterator.hasNext()) {
                key2 = iterator.next();
                if (group3.inheritsGroup(key2) && !key2.getName().equals(group3.getName())) {
                    group5.addInheritance(hashMap2.get(key2));
                }
            }
            return;
        });
        final AtomicInteger atomicInteger = new AtomicInteger();
        try {
            final User user2;
            final HashMap hashMap3;
            final User user3;
            final Set set;
            final AtomicInteger atomicInteger2;
            ((Set)api.getUserManager().getUniqueUsers().get()).forEach(uuid -> api.getUserManager().loadUser(uuid).whenComplete((user, p6) -> {
                EnderPermissions.registerUser(user.getUuid(), user.getName(), false);
                this.addNodes(user2, user.getOwnNodes());
                hashMap3.forEach((group4, group6) -> {
                    if (user.inheritsGroup(group4)) {
                        user3.addGroup(group6);
                    }
                    return;
                });
                if (set.size() == atomicInteger2.incrementAndGet()) {
                    runnable.run();
                }
            }));
        }
        catch (InterruptedException | ExecutionException ex) {
            final Throwable t;
            t.printStackTrace();
        }
    }
    
    private void addNodes(final PermissionHolder permissionHolder, final List<Node> list) {
        list.forEach(node -> permissionHolder.newPermission(node.getPermission()).setServer(node.getServer().orElse(null)).setWorld(node.getWorld().orElse(null)).setPositive(!node.isNegated()).setExpiration(node.isTemporary() ? node.getExpiryUnixTime() : 0L).create());
    }
    
    @Override
    public String getPluginName() {
        return "Luck Perms";
    }
}
