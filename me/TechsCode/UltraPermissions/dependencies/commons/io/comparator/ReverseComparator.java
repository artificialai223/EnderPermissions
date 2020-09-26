

package me.TechsCode.EnderPermissions.dependencies.commons.io.comparator;

import java.io.File;
import java.util.Comparator;
import java.io.Serializable;

class ReverseComparator extends AbstractFileComparator implements Serializable
{
    private static final long serialVersionUID = -4808255005272229056L;
    private final Comparator<File> delegate;
    
    public ReverseComparator(final Comparator<File> delegate) {
        if (delegate == null) {
            throw new IllegalArgumentException("Delegate comparator is missing");
        }
        this.delegate = delegate;
    }
    
    @Override
    public int compare(final File file, final File file2) {
        return this.delegate.compare(file2, file);
    }
    
    @Override
    public String toString() {
        return super.toString() + "[" + this.delegate.toString() + "]";
    }
}
