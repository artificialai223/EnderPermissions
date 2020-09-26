

package me.TechsCode.EnderPermissions.dependencies.commons.io.comparator;

import java.util.List;
import me.TechsCode.EnderPermissions.dependencies.commons.io.FilenameUtils;
import me.TechsCode.EnderPermissions.dependencies.commons.io.IOCase;
import java.io.File;
import java.util.Comparator;
import java.io.Serializable;

public class ExtensionFileComparator extends AbstractFileComparator implements Serializable
{
    private static final long serialVersionUID = 1928235200184222815L;
    public static final Comparator<File> EXTENSION_COMPARATOR;
    public static final Comparator<File> EXTENSION_REVERSE;
    public static final Comparator<File> EXTENSION_INSENSITIVE_COMPARATOR;
    public static final Comparator<File> EXTENSION_INSENSITIVE_REVERSE;
    public static final Comparator<File> EXTENSION_SYSTEM_COMPARATOR;
    public static final Comparator<File> EXTENSION_SYSTEM_REVERSE;
    private final IOCase caseSensitivity;
    
    public ExtensionFileComparator() {
        this.caseSensitivity = IOCase.SENSITIVE;
    }
    
    public ExtensionFileComparator(final IOCase ioCase) {
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    @Override
    public int compare(final File file, final File file2) {
        return this.caseSensitivity.checkCompareTo(FilenameUtils.getExtension(file.getName()), FilenameUtils.getExtension(file2.getName()));
    }
    
    @Override
    public String toString() {
        return super.toString() + "[caseSensitivity=" + this.caseSensitivity + "]";
    }
    
    static {
        EXTENSION_COMPARATOR = new ExtensionFileComparator();
        EXTENSION_REVERSE = new ReverseComparator(ExtensionFileComparator.EXTENSION_COMPARATOR);
        EXTENSION_INSENSITIVE_COMPARATOR = new ExtensionFileComparator(IOCase.INSENSITIVE);
        EXTENSION_INSENSITIVE_REVERSE = new ReverseComparator(ExtensionFileComparator.EXTENSION_INSENSITIVE_COMPARATOR);
        EXTENSION_SYSTEM_COMPARATOR = new ExtensionFileComparator(IOCase.SYSTEM);
        EXTENSION_SYSTEM_REVERSE = new ReverseComparator(ExtensionFileComparator.EXTENSION_SYSTEM_COMPARATOR);
    }
}
