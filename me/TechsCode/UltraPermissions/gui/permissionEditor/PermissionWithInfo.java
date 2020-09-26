

package me.TechsCode.EnderPermissions.gui.permissionEditor;

import me.TechsCode.EnderPermissions.permissionDatabase.PermissionInfo;

public class PermissionWithInfo
{
    private final String permission;
    private final PermissionInfo info;
    
    public PermissionWithInfo(final String permission, final PermissionInfo info) {
        this.permission = permission;
        this.info = info;
    }
    
    public boolean hasInfo() {
        return this.info != null;
    }
    
    public String getName() {
        return this.permission;
    }
    
    public PermissionInfo getInfo() {
        return this.info;
    }
    
    public boolean isThisPermission(final String anotherString) {
        return this.hasInfo() ? this.info.isThisPermission(anotherString) : this.permission.equalsIgnoreCase(anotherString);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof PermissionWithInfo && this.permission.equalsIgnoreCase(((PermissionWithInfo)o).permission);
    }
    
    @Override
    public int hashCode() {
        return this.permission.hashCode();
    }
}
