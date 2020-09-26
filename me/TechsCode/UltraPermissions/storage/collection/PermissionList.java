

package me.TechsCode.EnderPermissions.storage.collection;

import java.util.Optional;
import java.util.Arrays;
import me.TechsCode.EnderPermissions.storage.objects.Holder;
import java.util.List;
import java.util.stream.Collector;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.Collection;
import me.TechsCode.EnderPermissions.storage.objects.Permission;
import java.util.ArrayList;

public class PermissionList extends ArrayList<Permission>
{
    public PermissionList(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public PermissionList() {
    }
    
    public PermissionList(final Collection<? extends Permission> c) {
        super(c);
    }
    
    public PermissionList name(final String anotherString) {
        return this.stream().filter(permission -> permission.getName().equalsIgnoreCase(anotherString)).collect((Collector<? super Permission, ?, PermissionList>)Collectors.toCollection((Supplier<R>)PermissionList::new));
    }
    
    public PermissionList holder(final List<Holder> list) {
        return this.stream().filter(permission -> list.contains(permission.getHolder())).collect((Collector<? super Permission, ?, PermissionList>)Collectors.toCollection((Supplier<R>)PermissionList::new));
    }
    
    public PermissionList holder(final Holder... a) {
        return this.holder(Arrays.asList(a));
    }
    
    public PermissionList bungee() {
        return this.stream().filter(Permission::isBungeePermission).collect((Collector<? super Permission, ?, PermissionList>)Collectors.toCollection((Supplier<R>)PermissionList::new));
    }
    
    public PermissionList expired() {
        return this.stream().filter(Permission::isExpired).collect((Collector<? super Permission, ?, PermissionList>)Collectors.toCollection((Supplier<R>)PermissionList::new));
    }
    
    public PermissionList worlds(final boolean b, final List<String> list) {
        final Optional<String> optional;
        return this.stream().filter(permission -> {
            permission.getWorld();
            return (b && !optional.isPresent()) || (optional.isPresent() && list.contains(optional.get()));
        }).collect((Collector<? super Permission, ?, PermissionList>)Collectors.toCollection((Supplier<R>)PermissionList::new));
    }
    
    public PermissionList worlds(final boolean b, final String... a) {
        return this.worlds(b, Arrays.asList(a));
    }
    
    public PermissionList servers(final boolean b, final List<String> list) {
        final Optional<String> optional;
        return this.stream().filter(permission -> {
            permission.getServer();
            return (b && !optional.isPresent()) || (optional.isPresent() && list.contains(optional.get()));
        }).collect((Collector<? super Permission, ?, PermissionList>)Collectors.toCollection((Supplier<R>)PermissionList::new));
    }
    
    public PermissionList servers(final boolean b, final String... a) {
        return this.servers(b, Arrays.asList(a));
    }
}
