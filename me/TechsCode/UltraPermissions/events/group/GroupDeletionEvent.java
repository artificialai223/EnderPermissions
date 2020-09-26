

package me.TechsCode.EnderPermissions.events.group;

import me.TechsCode.EnderPermissions.storage.objects.Group;

public class GroupDeletionEvent extends GroupEvent
{
    public GroupDeletionEvent(final Group group) {
        super(group);
    }
}
