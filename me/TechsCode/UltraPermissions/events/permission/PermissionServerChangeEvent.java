

package me.TechsCode.EnderPermissions.events.permission;

import me.TechsCode.EnderPermissions.storage.objects.Permission;
import java.util.Optional;

public class PermissionServerChangeEvent extends PermissionEvent
{
    private final Optional<String> oldServer;
    private final Optional<String> newServer;
    
    public PermissionServerChangeEvent(final Permission permission, final Optional<String> oldServer, final Optional<String> newServer) {
        super(permission);
        this.oldServer = oldServer;
        this.newServer = newServer;
    }
    
    public Optional<String> getOldServer() {
        return this.oldServer;
    }
    
    public Optional<String> getNewServer() {
        return this.newServer;
    }
}
