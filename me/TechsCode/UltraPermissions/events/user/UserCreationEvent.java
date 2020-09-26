

package me.TechsCode.EnderPermissions.events.user;

import me.TechsCode.EnderPermissions.storage.objects.User;

public class UserCreationEvent extends UserEvent
{
    public UserCreationEvent(final User user) {
        super(user);
    }
}
