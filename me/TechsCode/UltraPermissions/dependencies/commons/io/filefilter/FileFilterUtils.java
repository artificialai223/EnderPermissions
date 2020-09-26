

package me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter;

import java.util.Date;
import java.io.FilenameFilter;
import java.io.FileFilter;
import me.TechsCode.EnderPermissions.dependencies.commons.io.IOCase;
import java.util.Iterator;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class FileFilterUtils
{
    private static final IOFileFilter cvsFilter;
    private static final IOFileFilter svnFilter;
    
    public static File[] filter(final IOFileFilter ioFileFilter, final File... array) {
        if (ioFileFilter == null) {
            throw new IllegalArgumentException("file filter is null");
        }
        if (array == null) {
            return new File[0];
        }
        final ArrayList<File> list = new ArrayList<File>();
        for (final File file : array) {
            if (file == null) {
                throw new IllegalArgumentException("file array contains null");
            }
            if (ioFileFilter.accept(file)) {
                list.add(file);
            }
        }
        return list.toArray(new File[list.size()]);
    }
    
    public static File[] filter(final IOFileFilter ioFileFilter, final Iterable<File> iterable) {
        final List<File> filterList = filterList(ioFileFilter, iterable);
        return filterList.toArray(new File[filterList.size()]);
    }
    
    public static List<File> filterList(final IOFileFilter ioFileFilter, final Iterable<File> iterable) {
        return filter(ioFileFilter, iterable, new ArrayList<File>());
    }
    
    public static List<File> filterList(final IOFileFilter ioFileFilter, final File... array) {
        return Arrays.asList(filter(ioFileFilter, array));
    }
    
    public static Set<File> filterSet(final IOFileFilter ioFileFilter, final File... array) {
        return new HashSet<File>(Arrays.asList(filter(ioFileFilter, array)));
    }
    
    public static Set<File> filterSet(final IOFileFilter ioFileFilter, final Iterable<File> iterable) {
        return filter(ioFileFilter, iterable, new HashSet<File>());
    }
    
    private static <T extends Collection<File>> T filter(final IOFileFilter ioFileFilter, final Iterable<File> iterable, final T t) {
        if (ioFileFilter == null) {
            throw new IllegalArgumentException("file filter is null");
        }
        if (iterable != null) {
            for (final File file : iterable) {
                if (file == null) {
                    throw new IllegalArgumentException("file collection contains null");
                }
                if (!ioFileFilter.accept(file)) {
                    continue;
                }
                t.add(file);
            }
        }
        return t;
    }
    
    public static IOFileFilter prefixFileFilter(final String s) {
        return new PrefixFileFilter(s);
    }
    
    public static IOFileFilter prefixFileFilter(final String s, final IOCase ioCase) {
        return new PrefixFileFilter(s, ioCase);
    }
    
    public static IOFileFilter suffixFileFilter(final String s) {
        return new SuffixFileFilter(s);
    }
    
    public static IOFileFilter suffixFileFilter(final String s, final IOCase ioCase) {
        return new SuffixFileFilter(s, ioCase);
    }
    
    public static IOFileFilter nameFileFilter(final String s) {
        return new NameFileFilter(s);
    }
    
    public static IOFileFilter nameFileFilter(final String s, final IOCase ioCase) {
        return new NameFileFilter(s, ioCase);
    }
    
    public static IOFileFilter directoryFileFilter() {
        return DirectoryFileFilter.DIRECTORY;
    }
    
    public static IOFileFilter fileFileFilter() {
        return FileFileFilter.FILE;
    }
    
    @Deprecated
    public static IOFileFilter andFileFilter(final IOFileFilter ioFileFilter, final IOFileFilter ioFileFilter2) {
        return new AndFileFilter(ioFileFilter, ioFileFilter2);
    }
    
    @Deprecated
    public static IOFileFilter orFileFilter(final IOFileFilter ioFileFilter, final IOFileFilter ioFileFilter2) {
        return new OrFileFilter(ioFileFilter, ioFileFilter2);
    }
    
    public static IOFileFilter and(final IOFileFilter... array) {
        return new AndFileFilter(toList(array));
    }
    
    public static IOFileFilter or(final IOFileFilter... array) {
        return new OrFileFilter(toList(array));
    }
    
    public static List<IOFileFilter> toList(final IOFileFilter... array) {
        if (array == null) {
            throw new IllegalArgumentException("The filters must not be null");
        }
        final ArrayList<IOFileFilter> list = new ArrayList<IOFileFilter>(array.length);
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == null) {
                throw new IllegalArgumentException("The filter[" + i + "] is null");
            }
            list.add(array[i]);
        }
        return list;
    }
    
    public static IOFileFilter notFileFilter(final IOFileFilter ioFileFilter) {
        return new NotFileFilter(ioFileFilter);
    }
    
    public static IOFileFilter trueFileFilter() {
        return TrueFileFilter.TRUE;
    }
    
    public static IOFileFilter falseFileFilter() {
        return FalseFileFilter.FALSE;
    }
    
    public static IOFileFilter asFileFilter(final FileFilter fileFilter) {
        return new DelegateFileFilter(fileFilter);
    }
    
    public static IOFileFilter asFileFilter(final FilenameFilter filenameFilter) {
        return new DelegateFileFilter(filenameFilter);
    }
    
    public static IOFileFilter ageFileFilter(final long n) {
        return new AgeFileFilter(n);
    }
    
    public static IOFileFilter ageFileFilter(final long n, final boolean b) {
        return new AgeFileFilter(n, b);
    }
    
    public static IOFileFilter ageFileFilter(final Date date) {
        return new AgeFileFilter(date);
    }
    
    public static IOFileFilter ageFileFilter(final Date date, final boolean b) {
        return new AgeFileFilter(date, b);
    }
    
    public static IOFileFilter ageFileFilter(final File file) {
        return new AgeFileFilter(file);
    }
    
    public static IOFileFilter ageFileFilter(final File file, final boolean b) {
        return new AgeFileFilter(file, b);
    }
    
    public static IOFileFilter sizeFileFilter(final long n) {
        return new SizeFileFilter(n);
    }
    
    public static IOFileFilter sizeFileFilter(final long n, final boolean b) {
        return new SizeFileFilter(n, b);
    }
    
    public static IOFileFilter sizeRangeFileFilter(final long n, final long n2) {
        return new AndFileFilter(new SizeFileFilter(n, true), new SizeFileFilter(n2 + 1L, false));
    }
    
    public static IOFileFilter magicNumberFileFilter(final String s) {
        return new MagicNumberFileFilter(s);
    }
    
    public static IOFileFilter magicNumberFileFilter(final String s, final long n) {
        return new MagicNumberFileFilter(s, n);
    }
    
    public static IOFileFilter magicNumberFileFilter(final byte[] array) {
        return new MagicNumberFileFilter(array);
    }
    
    public static IOFileFilter magicNumberFileFilter(final byte[] array, final long n) {
        return new MagicNumberFileFilter(array, n);
    }
    
    public static IOFileFilter makeCVSAware(final IOFileFilter ioFileFilter) {
        if (ioFileFilter == null) {
            return FileFilterUtils.cvsFilter;
        }
        return and(ioFileFilter, FileFilterUtils.cvsFilter);
    }
    
    public static IOFileFilter makeSVNAware(final IOFileFilter ioFileFilter) {
        if (ioFileFilter == null) {
            return FileFilterUtils.svnFilter;
        }
        return and(ioFileFilter, FileFilterUtils.svnFilter);
    }
    
    public static IOFileFilter makeDirectoryOnly(final IOFileFilter ioFileFilter) {
        if (ioFileFilter == null) {
            return DirectoryFileFilter.DIRECTORY;
        }
        return new AndFileFilter(DirectoryFileFilter.DIRECTORY, ioFileFilter);
    }
    
    public static IOFileFilter makeFileOnly(final IOFileFilter ioFileFilter) {
        if (ioFileFilter == null) {
            return FileFileFilter.FILE;
        }
        return new AndFileFilter(FileFileFilter.FILE, ioFileFilter);
    }
    
    static {
        cvsFilter = notFileFilter(and(directoryFileFilter(), nameFileFilter("CVS")));
        svnFilter = notFileFilter(and(directoryFileFilter(), nameFileFilter(".svn")));
    }
}
