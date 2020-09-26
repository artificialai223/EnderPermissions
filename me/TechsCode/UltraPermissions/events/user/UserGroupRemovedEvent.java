

package me.TechsCode.EnderPermissions.events.user;

import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.base.storage.Stored;

public class UserGroupRemovedEvent extends UserEvent
{
    private final Stored<Group> group;
    
    public UserGroupRemovedEvent(final User user, final Stored<Group> group) {
        super(user);
        this.group = group;
    }
    
    public Stored<Group> getGroup() {
        return this.group;
    }
}
