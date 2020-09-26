

package me.TechsCode.EnderPermissions.events.user;

import javax.annotation.Nullable;
import me.TechsCode.EnderPermissions.storage.objects.User;

public class UserPrefixChangeEvent extends UserEvent
{
    private final String oldPrefix;
    private final String newPrefix;
    
    public UserPrefixChangeEvent(final User user, @Nullable final String oldPrefix, @Nullable final String newPrefix) {
        super(user);
        this.oldPrefix = oldPrefix;
        this.newPrefix = newPrefix;
    }
    
    public String getNewPrefix() {
        return this.newPrefix;
    }
    
    public String getOldPrefix() {
        return this.oldPrefix;
    }
}
