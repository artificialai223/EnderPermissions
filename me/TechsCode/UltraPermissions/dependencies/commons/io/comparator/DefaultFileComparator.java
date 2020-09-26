

package me.TechsCode.EnderPermissions.dependencies.commons.io.comparator;

import java.util.List;
import java.io.File;
import java.util.Comparator;
import java.io.Serializable;

public class DefaultFileComparator extends AbstractFileComparator implements Serializable
{
    private static final long serialVersionUID = 3260141861365313518L;
    public static final Comparator<File> DEFAULT_COMPARATOR;
    public static final Comparator<File> DEFAULT_REVERSE;
    
    @Override
    public int compare(final File file, final File pathname) {
        return file.compareTo(pathname);
    }
    
    static {
        DEFAULT_COMPARATOR = new DefaultFileComparator();
        DEFAULT_REVERSE = new ReverseComparator(DefaultFileComparator.DEFAULT_COMPARATOR);
    }
}
