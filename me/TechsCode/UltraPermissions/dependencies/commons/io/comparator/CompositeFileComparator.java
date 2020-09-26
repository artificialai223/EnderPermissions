

package me.TechsCode.EnderPermissions.dependencies.commons.io.comparator;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.File;
import java.util.Comparator;
import java.io.Serializable;

public class CompositeFileComparator extends AbstractFileComparator implements Serializable
{
    private static final long serialVersionUID = -2224170307287243428L;
    private static final Comparator<?>[] NO_COMPARATORS;
    private final Comparator<File>[] delegates;
    
    public CompositeFileComparator(final Comparator<File>... array) {
        if (array == null) {
            this.delegates = (Comparator<File>[])CompositeFileComparator.NO_COMPARATORS;
        }
        else {
            System.arraycopy(array, 0, this.delegates = (Comparator<File>[])new Comparator[array.length], 0, array.length);
        }
    }
    
    public CompositeFileComparator(final Iterable<Comparator<File>> iterable) {
        if (iterable == null) {
            this.delegates = (Comparator<File>[])CompositeFileComparator.NO_COMPARATORS;
        }
        else {
            final ArrayList<Comparator<File>> list = new ArrayList<Comparator<File>>();
            final Iterator<Comparator<File>> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
            this.delegates = list.toArray(new Comparator[list.size()]);
        }
    }
    
    @Override
    public int compare(final File file, final File file2) {
        int compare = 0;
        final Comparator<File>[] delegates = this.delegates;
        for (int length = delegates.length, i = 0; i < length; ++i) {
            compare = delegates[i].compare(file, file2);
            if (compare != 0) {
                break;
            }
        }
        return compare;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append('{');
        for (int i = 0; i < this.delegates.length; ++i) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(this.delegates[i]);
        }
        sb.append('}');
        return sb.toString();
    }
    
    static {
        NO_COMPARATORS = new Comparator[0];
    }
}
