

package me.TechsCode.EnderPermissions.events.group;

import me.TechsCode.EnderPermissions.storage.objects.Group;

public class GroupSuffixChangeEvent extends GroupEvent
{
    private final String oldPrefix;
    private final String newPrefix;
    
    public GroupSuffixChangeEvent(final Group group, final String oldPrefix, final String newPrefix) {
        super(group);
        this.oldPrefix = oldPrefix;
        this.newPrefix = newPrefix;
    }
    
    public String getOldPrefix() {
        return this.oldPrefix;
    }
    
    public String getNewPrefix() {
        return this.newPrefix;
    }
}
