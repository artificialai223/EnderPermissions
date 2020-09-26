

package me.TechsCode.EnderPermissions.events.user;

import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.base.storage.Stored;

public class UserGroupAddedEvent extends UserEvent
{
    private final Stored<Group> group;
    private final long expiry;
    
    public UserGroupAddedEvent(final User user, final Stored<Group> group, final long expiry) {
        super(user);
        this.group = group;
        this.expiry = expiry;
    }
    
    public Stored<Group> getGroup() {
        return this.group;
    }
    
    public long getExpiry() {
        return this.expiry;
    }
}
