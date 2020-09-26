

package me.TechsCode.EnderPermissions.gui.permissionEditor;

import me.TechsCode.EnderPermissions.storage.objects.Permission;

public class PermissionCopy
{
    private final Permission permission;
    private final boolean inherited;
    
    public PermissionCopy(final Permission permission, final boolean inherited) {
        this.permission = permission;
        this.inherited = inherited;
    }
    
    public Permission getPermission() {
        return this.permission;
    }
    
    public boolean isInherited() {
        return this.inherited;
    }
}
