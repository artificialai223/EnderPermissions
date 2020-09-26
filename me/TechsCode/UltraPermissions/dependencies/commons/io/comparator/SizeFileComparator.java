

package me.TechsCode.EnderPermissions.dependencies.commons.io.comparator;

import java.util.List;
import me.TechsCode.EnderPermissions.dependencies.commons.io.FileUtils;
import java.io.File;
import java.util.Comparator;
import java.io.Serializable;

public class SizeFileComparator extends AbstractFileComparator implements Serializable
{
    private static final long serialVersionUID = -1201561106411416190L;
    public static final Comparator<File> SIZE_COMPARATOR;
    public static final Comparator<File> SIZE_REVERSE;
    public static final Comparator<File> SIZE_SUMDIR_COMPARATOR;
    public static final Comparator<File> SIZE_SUMDIR_REVERSE;
    private final boolean sumDirectoryContents;
    
    public SizeFileComparator() {
        this.sumDirectoryContents = false;
    }
    
    public SizeFileComparator(final boolean sumDirectoryContents) {
        this.sumDirectoryContents = sumDirectoryContents;
    }
    
    @Override
    public int compare(final File file, final File file2) {
        long length;
        if (file.isDirectory()) {
            length = ((this.sumDirectoryContents && file.exists()) ? FileUtils.sizeOfDirectory(file) : 0L);
        }
        else {
            length = file.length();
        }
        long length2;
        if (file2.isDirectory()) {
            length2 = ((this.sumDirectoryContents && file2.exists()) ? FileUtils.sizeOfDirectory(file2) : 0L);
        }
        else {
            length2 = file2.length();
        }
        final long n = length - length2;
        if (n < 0L) {
            return -1;
        }
        if (n > 0L) {
            return 1;
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return super.toString() + "[sumDirectoryContents=" + this.sumDirectoryContents + "]";
    }
    
    static {
        SIZE_COMPARATOR = new SizeFileComparator();
        SIZE_REVERSE = new ReverseComparator(SizeFileComparator.SIZE_COMPARATOR);
        SIZE_SUMDIR_COMPARATOR = new SizeFileComparator(true);
        SIZE_SUMDIR_REVERSE = new ReverseComparator(SizeFileComparator.SIZE_SUMDIR_COMPARATOR);
    }
}
