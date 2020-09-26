

package me.TechsCode.EnderPermissions.events.permission;

import me.TechsCode.EnderPermissions.storage.objects.Permission;

public class PermissionCreationEvent extends PermissionEvent
{
    public PermissionCreationEvent(final Permission permission) {
        super(permission);
    }
}
