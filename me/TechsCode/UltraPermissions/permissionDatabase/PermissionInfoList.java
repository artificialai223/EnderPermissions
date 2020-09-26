

package me.TechsCode.EnderPermissions.permissionDatabase;

import java.util.stream.Collector;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Iterator;
import java.util.Optional;
import java.util.Collection;
import java.util.ArrayList;

public class PermissionInfoList extends ArrayList<PermissionInfo>
{
    public PermissionInfoList(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public PermissionInfoList() {
    }
    
    public PermissionInfoList(final Collection<? extends PermissionInfo> c) {
        super(c);
    }
    
    public Optional<PermissionInfo> find(final String s) {
        for (final PermissionInfo value : this) {
            if (value.isThisPermission(s)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
    
    public PermissionInfoList plugins(final List<String> list) {
        return this.stream().filter(permissionInfo -> list.contains(permissionInfo.getPlugin())).collect((Collector<? super PermissionInfo, ?, PermissionInfoList>)Collectors.toCollection((Supplier<R>)PermissionInfoList::new));
    }
}
