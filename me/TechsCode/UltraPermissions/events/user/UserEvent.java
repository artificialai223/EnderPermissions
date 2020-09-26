

package me.TechsCode.EnderPermissions.events.user;

import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.events.UPEvent;

public abstract class UserEvent extends UPEvent
{
    public final User user;
    
    public UserEvent(final User user) {
        this.user = user;
    }
    
    public User getUser() {
        return this.user;
    }
}
