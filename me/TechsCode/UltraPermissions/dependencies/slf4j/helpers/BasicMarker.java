

package me.TechsCode.EnderPermissions.dependencies.slf4j.helpers;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Marker;

public class BasicMarker implements Marker
{
    private static final long serialVersionUID = -2849567615646933777L;
    private final String name;
    private List<Marker> referenceList;
    private static String OPEN;
    private static String CLOSE;
    private static String SEP;
    
    BasicMarker(final String name) {
        this.referenceList = new CopyOnWriteArrayList<Marker>();
        if (name == null) {
            throw new IllegalArgumentException("A marker name cannot be null");
        }
        this.name = name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public void add(final Marker marker) {
        if (marker == null) {
            throw new IllegalArgumentException("A null value cannot be added to a Marker as reference.");
        }
        if (this.contains(marker)) {
            return;
        }
        if (marker.contains(this)) {
            return;
        }
        this.referenceList.add(marker);
    }
    
    @Override
    public boolean hasReferences() {
        return this.referenceList.size() > 0;
    }
    
    @Deprecated
    @Override
    public boolean hasChildren() {
        return this.hasReferences();
    }
    
    @Override
    public Iterator<Marker> iterator() {
        return this.referenceList.iterator();
    }
    
    @Override
    public boolean remove(final Marker marker) {
        return this.referenceList.remove(marker);
    }
    
    @Override
    public boolean contains(final Marker marker) {
        if (marker == null) {
            throw new IllegalArgumentException("Other cannot be null");
        }
        if (this.equals(marker)) {
            return true;
        }
        if (this.hasReferences()) {
            final Iterator<Marker> iterator = this.referenceList.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().contains(marker)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean contains(final String anObject) {
        if (anObject == null) {
            throw new IllegalArgumentException("Other cannot be null");
        }
        if (this.name.equals(anObject)) {
            return true;
        }
        if (this.hasReferences()) {
            final Iterator<Marker> iterator = this.referenceList.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().contains(anObject)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && o instanceof Marker && this.name.equals(((Marker)o).getName()));
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
    @Override
    public String toString() {
        if (!this.hasReferences()) {
            return this.getName();
        }
        final Iterator<Marker> iterator = this.iterator();
        final StringBuilder sb = new StringBuilder(this.getName());
        sb.append(' ').append(BasicMarker.OPEN);
        while (iterator.hasNext()) {
            sb.append(iterator.next().getName());
            if (iterator.hasNext()) {
                sb.append(BasicMarker.SEP);
            }
        }
        sb.append(BasicMarker.CLOSE);
        return sb.toString();
    }
    
    static {
        BasicMarker.OPEN = "[ ";
        BasicMarker.CLOSE = " ]";
        BasicMarker.SEP = ", ";
    }
}
