

package me.TechsCode.EnderPermissions.base;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.function.Consumer;
import java.util.Comparator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.util.List;

public abstract class Collection<OBJECT>
{
    private final List<OBJECT> list;
    
    public Collection(final List<OBJECT> list) {
        this.list = list;
    }
    
    public abstract Collection<OBJECT> invokeConstructor(final List<OBJECT> p0);
    
    protected Collection<OBJECT> filter(final Predicate<OBJECT> predicate) {
        return (Collection<OBJECT>)this.invokeConstructor(this.list.stream().filter((Predicate<? super Object>)predicate).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
    }
    
    protected Collection<OBJECT> sorted(final Comparator<OBJECT> comparator) {
        return (Collection<OBJECT>)this.invokeConstructor(this.list.stream().sorted((Comparator<? super Object>)comparator).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
    }
    
    public Collection<OBJECT> forEach(final Consumer<OBJECT> consumer) {
        this.stream().forEach(consumer);
        return this;
    }
    
    public Collection<OBJECT> combine(final Collection<OBJECT> collection) {
        return this.invokeConstructor(Stream.concat(this.stream(), (Stream<?>)collection.stream()).collect((Collector<? super Object, ?, List<OBJECT>>)Collectors.toList()));
    }
    
    public Optional<OBJECT> first() {
        return this.stream().findFirst();
    }
    
    public Stream<OBJECT> stream() {
        return this.list.stream();
    }
    
    public List<OBJECT> get() {
        return Collections.unmodifiableList((List<? extends OBJECT>)this.list);
    }
    
    public Optional<OBJECT> get(final int n) {
        return Optional.ofNullable(this.list.get(n));
    }
    
    public Collection<OBJECT> subCollection(final int n, final int n2) {
        return this.invokeConstructor(this.list.subList(n, n2));
    }
    
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
    
    public OBJECT[] toArray(final OBJECT[] array) {
        return this.get().toArray(array);
    }
    
    public boolean contains(final OBJECT object) {
        return this.list.contains(object);
    }
    
    public int size() {
        return this.list.size();
    }
}
