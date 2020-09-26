

package me.TechsCode.EnderPermissions.dependencies.nbt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.Collection;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ReflectionMethod;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.MinecraftVersion;
import java.util.List;

public abstract class NBTList<T> implements List<T>
{
    private String listName;
    private NBTCompound parent;
    private NBTType type;
    protected Object listObject;
    
    protected NBTList(final NBTCompound parent, final String listName, final NBTType type, final Object listObject) {
        this.parent = parent;
        this.listName = listName;
        this.type = type;
        this.listObject = listObject;
    }
    
    public String getName() {
        return this.listName;
    }
    
    public NBTCompound getParent() {
        return this.parent;
    }
    
    protected void save() {
        this.parent.set(this.listName, this.listObject);
    }
    
    protected abstract Object asTag(final T p0);
    
    @Override
    public boolean add(final T t) {
        try {
            this.parent.getWriteLock().lock();
            if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_14_R1.getVersionId()) {
                ReflectionMethod.LIST_ADD.run(this.listObject, this.size(), this.asTag(t));
            }
            else {
                ReflectionMethod.LEGACY_LIST_ADD.run(this.listObject, this.asTag(t));
            }
            this.save();
            return true;
        }
        catch (Exception ex) {
            throw new NbtApiException(ex);
        }
        finally {
            this.parent.getWriteLock().unlock();
        }
    }
    
    @Override
    public void add(final int i, final T t) {
        try {
            this.parent.getWriteLock().lock();
            if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_14_R1.getVersionId()) {
                ReflectionMethod.LIST_ADD.run(this.listObject, i, this.asTag(t));
            }
            else {
                ReflectionMethod.LEGACY_LIST_ADD.run(this.listObject, this.asTag(t));
            }
            this.save();
        }
        catch (Exception ex) {
            throw new NbtApiException(ex);
        }
        finally {
            this.parent.getWriteLock().unlock();
        }
    }
    
    @Override
    public T set(final int i, final T t) {
        try {
            this.parent.getWriteLock().lock();
            final T value = this.get(i);
            ReflectionMethod.LIST_SET.run(this.listObject, i, this.asTag(t));
            this.save();
            return value;
        }
        catch (Exception ex) {
            throw new NbtApiException(ex);
        }
        finally {
            this.parent.getWriteLock().unlock();
        }
    }
    
    @Override
    public T remove(final int i) {
        try {
            this.parent.getWriteLock().lock();
            final T value = this.get(i);
            ReflectionMethod.LIST_REMOVE_KEY.run(this.listObject, i);
            this.save();
            return value;
        }
        catch (Exception ex) {
            throw new NbtApiException(ex);
        }
        finally {
            this.parent.getWriteLock().unlock();
        }
    }
    
    @Override
    public int size() {
        try {
            this.parent.getReadLock().lock();
            return (int)ReflectionMethod.LIST_SIZE.run(this.listObject, new Object[0]);
        }
        catch (Exception ex) {
            throw new NbtApiException(ex);
        }
        finally {
            this.parent.getReadLock().unlock();
        }
    }
    
    public NBTType getType() {
        return this.type;
    }
    
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    @Override
    public void clear() {
        while (!this.isEmpty()) {
            this.remove(0);
        }
    }
    
    @Override
    public boolean contains(final Object o) {
        try {
            this.parent.getReadLock().lock();
            for (int i = 0; i < this.size(); ++i) {
                if (o.equals(this.get(i))) {
                    return true;
                }
            }
            return false;
        }
        finally {
            this.parent.getReadLock().unlock();
        }
    }
    
    @Override
    public int indexOf(final Object o) {
        try {
            this.parent.getReadLock().lock();
            for (int i = 0; i < this.size(); ++i) {
                if (o.equals(this.get(i))) {
                    return i;
                }
            }
            return -1;
        }
        finally {
            this.parent.getReadLock().unlock();
        }
    }
    
    @Override
    public boolean addAll(final Collection<? extends T> collection) {
        try {
            this.parent.getWriteLock().lock();
            final int size = this.size();
            final Iterator<? extends T> iterator = collection.iterator();
            while (iterator.hasNext()) {
                this.add(iterator.next());
            }
            return size != this.size();
        }
        finally {
            this.parent.getWriteLock().unlock();
        }
    }
    
    @Override
    public boolean addAll(int n, final Collection<? extends T> collection) {
        try {
            this.parent.getWriteLock().lock();
            final int size = this.size();
            final Iterator<? extends T> iterator = collection.iterator();
            while (iterator.hasNext()) {
                this.add(n++, iterator.next());
            }
            return size != this.size();
        }
        finally {
            this.parent.getWriteLock().unlock();
        }
    }
    
    @Override
    public boolean containsAll(final Collection<?> collection) {
        try {
            this.parent.getReadLock().lock();
            final Iterator<?> iterator = collection.iterator();
            while (iterator.hasNext()) {
                if (!this.contains(iterator.next())) {
                    return false;
                }
            }
            return true;
        }
        finally {
            this.parent.getReadLock().unlock();
        }
    }
    
    @Override
    public int lastIndexOf(final Object o) {
        try {
            this.parent.getReadLock().lock();
            int n = -1;
            for (int i = 0; i < this.size(); ++i) {
                if (o.equals(this.get(i))) {
                    n = i;
                }
            }
            return n;
        }
        finally {
            this.parent.getReadLock().unlock();
        }
    }
    
    @Override
    public boolean removeAll(final Collection<?> collection) {
        try {
            this.parent.getWriteLock().lock();
            final int size = this.size();
            final Iterator<?> iterator = collection.iterator();
            while (iterator.hasNext()) {
                this.remove(iterator.next());
            }
            return size != this.size();
        }
        finally {
            this.parent.getWriteLock().unlock();
        }
    }
    
    @Override
    public boolean retainAll(final Collection<?> collection) {
        try {
            this.parent.getWriteLock().lock();
            final int size = this.size();
            for (final Object next : collection) {
                for (int i = 0; i < this.size(); ++i) {
                    if (!next.equals(this.get(i))) {
                        this.remove(i--);
                    }
                }
            }
            return size != this.size();
        }
        finally {
            this.parent.getWriteLock().unlock();
        }
    }
    
    @Override
    public boolean remove(final Object o) {
        try {
            this.parent.getWriteLock().lock();
            final int size = this.size();
            int index;
            while ((index = this.indexOf(o)) != -1) {
                this.remove(index);
            }
            return size != this.size();
        }
        finally {
            this.parent.getWriteLock().unlock();
        }
    }
    
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = -1;
            
            @Override
            public boolean hasNext() {
                return NBTList.this.size() > this.index + 1;
            }
            
            @Override
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return NBTList.this.get(++this.index);
            }
            
            @Override
            public void remove() {
                NBTList.this.remove(this.index);
                --this.index;
            }
        };
    }
    
    @Override
    public ListIterator<T> listIterator() {
        return this.listIterator(0);
    }
    
    @Override
    public ListIterator<T> listIterator(final int n) {
        return new ListIterator<T>() {
            int index = n - 1;
            
            @Override
            public void add(final T t) {
                NBTList.this.add(this.index, t);
            }
            
            @Override
            public boolean hasNext() {
                return NBTList.this.size() > this.index + 1;
            }
            
            @Override
            public boolean hasPrevious() {
                return this.index >= 0 && this.index <= NBTList.this.size();
            }
            
            @Override
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return NBTList.this.get(++this.index);
            }
            
            @Override
            public int nextIndex() {
                return this.index + 1;
            }
            
            @Override
            public T previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException("Id: " + (this.index - 1));
                }
                return NBTList.this.get(this.index--);
            }
            
            @Override
            public int previousIndex() {
                return this.index - 1;
            }
            
            @Override
            public void remove() {
                NBTList.this.remove(this.index);
                --this.index;
            }
            
            @Override
            public void set(final T t) {
                NBTList.this.set(this.index, t);
            }
        };
    }
    
    @Override
    public Object[] toArray() {
        try {
            this.parent.getReadLock().lock();
            final Object[] array = new Object[this.size()];
            for (int i = 0; i < this.size(); ++i) {
                array[i] = this.get(i);
            }
            return array;
        }
        finally {
            this.parent.getReadLock().unlock();
        }
    }
    
    @Override
    public <E> E[] toArray(final E[] original) {
        try {
            this.parent.getReadLock().lock();
            final E[] copy = Arrays.copyOf(original, this.size());
            Arrays.fill(copy, null);
            final Class<?> componentType = original.getClass().getComponentType();
            for (int i = 0; i < this.size(); ++i) {
                if (!componentType.isInstance(this.get(i))) {
                    throw new ArrayStoreException("The array does not match the objects stored in the List.");
                }
                copy[i] = (E)this.get(i);
            }
            return copy;
        }
        finally {
            this.parent.getReadLock().unlock();
        }
    }
    
    @Override
    public List<T> subList(final int n, final int n2) {
        try {
            this.parent.getReadLock().lock();
            final ArrayList<T> list = new ArrayList<T>();
            for (int i = n; i < n2; ++i) {
                list.add(this.get(i));
            }
            return list;
        }
        finally {
            this.parent.getReadLock().unlock();
        }
    }
    
    @Override
    public String toString() {
        try {
            this.parent.getReadLock().lock();
            return this.listObject.toString();
        }
        finally {
            this.parent.getReadLock().unlock();
        }
    }
}
