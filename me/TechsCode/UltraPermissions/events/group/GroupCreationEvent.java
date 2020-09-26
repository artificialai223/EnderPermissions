

package me.TechsCode.EnderPermissions.events.group;

import me.TechsCode.EnderPermissions.storage.objects.Group;

public class GroupCreationEvent extends GroupEvent
{
    public GroupCreationEvent(final Group group) {
        super(group);
    }
}
