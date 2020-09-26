

package me.TechsCode.EnderPermissions.events.user;

import javax.annotation.Nullable;
import me.TechsCode.EnderPermissions.storage.objects.User;

public class UserSuffixChangeEvent extends UserEvent
{
    private final String oldSuffix;
    private final String newSuffix;
    
    public UserSuffixChangeEvent(final User user, @Nullable final String oldSuffix, @Nullable final String newSuffix) {
        super(user);
        this.oldSuffix = oldSuffix;
        this.newSuffix = newSuffix;
    }
    
    public String getOldSuffix() {
        return this.oldSuffix;
    }
    
    public String getNewSuffix() {
        return this.newSuffix;
    }
}
