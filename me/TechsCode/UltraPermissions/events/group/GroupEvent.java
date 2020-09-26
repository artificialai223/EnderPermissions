

package me.TechsCode.EnderPermissions.events.group;

import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.events.UPEvent;

public abstract class GroupEvent extends UPEvent
{
    public final Group group;
    
    public GroupEvent(final Group group) {
        this.group = group;
    }
    
    public Group getGroup() {
        return this.group;
    }
}
