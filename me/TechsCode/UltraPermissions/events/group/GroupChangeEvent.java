

package me.TechsCode.EnderPermissions.events.group;

import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.events.UPEvent;

public class GroupChangeEvent extends UPEvent
{
    private final Group oldGroup;
    private final Group newGroup;
    
    public GroupChangeEvent(final Group oldGroup, final Group newGroup) {
        this.oldGroup = oldGroup;
        this.newGroup = newGroup;
    }
    
    public Group getNewGroup() {
        return this.newGroup;
    }
    
    public Group getOldGroup() {
        return this.oldGroup;
    }
}
