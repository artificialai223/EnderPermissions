

package me.TechsCode.EnderPermissions.gui.permissionEditor;

import me.TechsCode.EnderPermissions.storage.objects.Permission;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import me.TechsCode.EnderPermissions.storage.collection.PermissionList;
import java.util.Collection;
import java.util.ArrayList;

public class PermissionCopyList extends ArrayList<PermissionCopy>
{
    public PermissionCopyList(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public PermissionCopyList() {
    }
    
    public PermissionCopyList(final Collection<? extends PermissionCopy> c) {
        super(c);
    }
    
    public PermissionCopyList(final PermissionList list, final PermissionList list2) {
        list.forEach(permission -> this.add(new PermissionCopy(permission, false)));
        list2.forEach(permission2 -> this.add(new PermissionCopy(permission2, true)));
    }
    
    public List<PermissionCopy> inheritedCopies() {
        return this.stream().filter(PermissionCopy::isInherited).collect((Collector<? super PermissionCopy, ?, List<PermissionCopy>>)Collectors.toList());
    }
    
    public List<PermissionCopy> regularCopies() {
        return this.stream().filter(permissionCopy -> !permissionCopy.isInherited()).collect((Collector<? super PermissionCopy, ?, List<PermissionCopy>>)Collectors.toList());
    }
}
