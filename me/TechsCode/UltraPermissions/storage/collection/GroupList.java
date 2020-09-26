

package me.TechsCode.EnderPermissions.storage.collection;

import java.util.Iterator;
import me.TechsCode.EnderPermissions.storage.objects.Holder;
import java.util.function.Function;
import java.util.Comparator;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Collection;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import java.util.ArrayList;

public class GroupList extends ArrayList<Group>
{
    public GroupList(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public GroupList() {
    }
    
    public GroupList(final Collection<? extends Group> c) {
        super(c);
    }
    
    public GroupList servers(final boolean b, final List<String> list) {
        final Optional<String> optional;
        return this.stream().filter(group -> {
            group.getServer();
            return (b && !optional.isPresent()) || (optional.isPresent() && list.contains(optional.get()));
        }).collect((Collector<? super Group, ?, GroupList>)Collectors.toCollection((Supplier<R>)GroupList::new));
    }
    
    public GroupList worlds(final boolean b, final List<String> list) {
        final Optional<String> optional;
        return this.stream().filter(group -> {
            group.getWorld();
            return (b && !optional.isPresent()) || (optional.isPresent() && list.contains(optional.get()));
        }).collect((Collector<? super Group, ?, GroupList>)Collectors.toCollection((Supplier<R>)GroupList::new));
    }
    
    public GroupList servers(final boolean b, final String... a) {
        return this.servers(b, Arrays.asList(a));
    }
    
    public GroupList worlds(final boolean b, final String... a) {
        return this.worlds(b, Arrays.asList(a));
    }
    
    public GroupList worstToBest() {
        this.sort(Comparator.comparing((Function<? super Object, ?>)Group::getPriority, Comparator.reverseOrder()).thenComparing((Function<? super Object, ? extends Comparable>)Group::getKey));
        return this;
    }
    
    public GroupList bestToWorst() {
        this.sort(Comparator.comparing((Function<? super Object, ? extends Comparable>)Group::getPriority).thenComparing((Function<? super Object, ? extends Comparable>)Group::getKey));
        return this;
    }
    
    public GroupList defaults(final boolean b) {
        return this.stream().filter(group -> group.isDefault() == b).collect((Collector<? super Group, ?, GroupList>)Collectors.toCollection((Supplier<R>)GroupList::new));
    }
    
    public Optional<Group> name(final String anotherString) {
        return this.stream().filter(group -> group.getName().equalsIgnoreCase(anotherString)).findFirst();
    }
    
    public List<Holder> holders() {
        return this.stream().map((Function<? super Group, ?>)Holder::fromGroup).collect((Collector<? super Object, ?, List<Holder>>)Collectors.toList());
    }
    
    public GroupList getWithRecursiveInherits() {
        final GroupList list = new GroupList(this);
        final Iterator<Group> iterator = this.iterator();
        while (iterator.hasNext()) {
            this.collectGroups(list, iterator.next());
        }
        return list;
    }
    
    private void collectGroups(final List<Group> list, final Group group) {
        for (final Group group2 : group.getActiveInheritedGroups()) {
            if (!list.contains(group2)) {
                list.add(group2);
                this.collectGroups(list, group2);
            }
        }
    }
}
