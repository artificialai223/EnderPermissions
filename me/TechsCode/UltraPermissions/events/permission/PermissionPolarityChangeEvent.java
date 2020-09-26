

package me.TechsCode.EnderPermissions.events.permission;

import me.TechsCode.EnderPermissions.storage.objects.Permission;

public class PermissionPolarityChangeEvent extends PermissionEvent
{
    private final boolean oldPolarity;
    private final boolean newPolarity;
    
    public PermissionPolarityChangeEvent(final Permission permission, final boolean oldPolarity, final boolean newPolarity) {
        super(permission);
        this.oldPolarity = oldPolarity;
        this.newPolarity = newPolarity;
    }
    
    public boolean getNewPolarity() {
        return this.newPolarity;
    }
    
    public boolean getOldPolarity() {
        return this.oldPolarity;
    }
}
