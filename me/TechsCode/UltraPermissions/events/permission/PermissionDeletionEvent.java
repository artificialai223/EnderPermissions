

package me.TechsCode.EnderPermissions.events.permission;

import me.TechsCode.EnderPermissions.storage.objects.Permission;

public class PermissionDeletionEvent extends PermissionEvent
{
    public PermissionDeletionEvent(final Permission permission) {
        super(permission);
    }
}
