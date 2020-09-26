

package me.TechsCode.EnderPermissions.migration;

import java.util.Iterator;
import java.util.Optional;
import me.TechsCode.EnderPermissions.storage.objects.Permission;
import me.TechsCode.EnderPermissions.base.networking.NServer;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import java.util.UUID;
import me.TechsCode.EnderPermissions.base.networking.SpigotNetworkManager;
import me.TechsCode.EnderPermissions.PrivateRegistry;
import me.TechsCode.EnderPermissions.EnderPermissions;

public class ServerIdentifierMigration
{
    public static void migrate(final EnderPermissions EnderPermissions, final PrivateRegistry privateRegistry, final SpigotNetworkManager spigotNetworkManager) {
        if (EnderPermissions.getLegacySystemStorage().contains("serverIdentifier")) {
            privateRegistry.setServerIdentifier(UUID.fromString(EnderPermissions.getLegacySystemStorage().get("serverIdentifier").toString()));
        }
        IdentifierConversionTask(EnderPermissions, privateRegistry, spigotNetworkManager);
    }
    
    private static void IdentifierConversionTask(final EnderPermissions EnderPermissions, final PrivateRegistry privateRegistry, final SpigotNetworkManager spigotNetworkManager) {
        final Optional<NServer> optional;
        final Iterator<Group> iterator;
        Group group;
        final String s;
        final Iterator<Permission> iterator2;
        Permission permission;
        EnderPermissions.getScheduler().runTaskLater(() -> {
            spigotNetworkManager.getThisServer();
            if (!optional.isPresent()) {
                IdentifierConversionTask(EnderPermissions, privateRegistry, spigotNetworkManager);
            }
            else {
                privateRegistry.getServerIdentifier().toString();
                EnderPermissions.getGroups().iterator();
                while (iterator.hasNext()) {
                    group = iterator.next();
                    if (group.getServer().isPresent() && group.getServer().get().equals(s)) {
                        group.setServer(optional.get().getName());
                    }
                }
                EnderPermissions.getPermissions().iterator();
                while (iterator2.hasNext()) {
                    permission = iterator2.next();
                    if (permission.getServer().isPresent() && permission.getServer().get().equals(s)) {
                        permission.setServer(optional.get().getName());
                    }
                }
            }
        }, 200L);
    }
}
