

package me.TechsCode.EnderPermissions.events.permission;

import me.TechsCode.EnderPermissions.storage.objects.Permission;
import me.TechsCode.EnderPermissions.events.UPEvent;

public class PermissionEvent extends UPEvent
{
    private final Permission permission;
    
    public PermissionEvent(final Permission permission) {
        this.permission = permission;
    }
    
    public Permission getPermission() {
        return this.permission;
    }
}
