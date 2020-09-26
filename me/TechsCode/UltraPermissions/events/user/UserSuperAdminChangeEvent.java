

package me.TechsCode.EnderPermissions.events.user;

import me.TechsCode.EnderPermissions.storage.objects.User;

public class UserSuperAdminChangeEvent extends UserEvent
{
    private final boolean oldSuperadmin;
    private final boolean newSuperadmin;
    
    public UserSuperAdminChangeEvent(final User user, final boolean oldSuperadmin, final boolean newSuperadmin) {
        super(user);
        this.oldSuperadmin = oldSuperadmin;
        this.newSuperadmin = newSuperadmin;
    }
    
    public boolean isNowSuperAdmin() {
        return this.newSuperadmin;
    }
    
    public boolean wasSuperAdmin() {
        return this.oldSuperadmin;
    }
}
