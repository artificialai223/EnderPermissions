

package me.TechsCode.EnderPermissions.dependencies.commons.io.comparator;

import java.util.List;
import java.io.File;
import java.util.Comparator;
import java.io.Serializable;

public class LastModifiedFileComparator extends AbstractFileComparator implements Serializable
{
    private static final long serialVersionUID = 7372168004395734046L;
    public static final Comparator<File> LASTMODIFIED_COMPARATOR;
    public static final Comparator<File> LASTMODIFIED_REVERSE;
    
    @Override
    public int compare(final File file, final File file2) {
        final long n = file.lastModified() - file2.lastModified();
        if (n < 0L) {
            return -1;
        }
        if (n > 0L) {
            return 1;
        }
        return 0;
    }
    
    static {
        LASTMODIFIED_COMPARATOR = new LastModifiedFileComparator();
        LASTMODIFIED_REVERSE = new ReverseComparator(LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
    }
}
