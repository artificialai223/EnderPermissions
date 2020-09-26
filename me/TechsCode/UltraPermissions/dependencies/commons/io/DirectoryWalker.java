

package me.TechsCode.EnderPermissions.dependencies.commons.io;

import java.io.IOException;
import java.util.Collection;
import java.io.File;
import me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter.FileFilterUtils;
import me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter.TrueFileFilter;
import me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter.IOFileFilter;
import java.io.FileFilter;

public abstract class DirectoryWalker<T>
{
    private final FileFilter filter;
    private final int depthLimit;
    
    protected DirectoryWalker() {
        this(null, -1);
    }
    
    protected DirectoryWalker(final FileFilter filter, final int depthLimit) {
        this.filter = filter;
        this.depthLimit = depthLimit;
    }
    
    protected DirectoryWalker(IOFileFilter directoryOnly, IOFileFilter fileOnly, final int depthLimit) {
        if (directoryOnly == null && fileOnly == null) {
            this.filter = null;
        }
        else {
            directoryOnly = ((directoryOnly != null) ? directoryOnly : TrueFileFilter.TRUE);
            fileOnly = ((fileOnly != null) ? fileOnly : TrueFileFilter.TRUE);
            directoryOnly = FileFilterUtils.makeDirectoryOnly(directoryOnly);
            fileOnly = FileFilterUtils.makeFileOnly(fileOnly);
            this.filter = FileFilterUtils.or(directoryOnly, fileOnly);
        }
        this.depthLimit = depthLimit;
    }
    
    protected final void walk(final File file, final Collection<T> collection) {
        if (file == null) {
            throw new NullPointerException("Start Directory is null");
        }
        try {
            this.handleStart(file, collection);
            this.walk(file, 0, collection);
            this.handleEnd(collection);
        }
        catch (CancelException ex) {
            this.handleCancelled(file, collection, ex);
        }
    }
    
    private void walk(final File file, final int n, final Collection<T> collection) {
        this.checkIfCancelled(file, n, collection);
        if (this.handleDirectory(file, n, collection)) {
            this.handleDirectoryStart(file, n, collection);
            final int n2 = n + 1;
            if (this.depthLimit < 0 || n2 <= this.depthLimit) {
                this.checkIfCancelled(file, n, collection);
                final File[] filterDirectoryContents = this.filterDirectoryContents(file, n, (this.filter == null) ? file.listFiles() : file.listFiles(this.filter));
                if (filterDirectoryContents == null) {
                    this.handleRestricted(file, n2, collection);
                }
                else {
                    for (final File file2 : filterDirectoryContents) {
                        if (file2.isDirectory()) {
                            this.walk(file2, n2, collection);
                        }
                        else {
                            this.checkIfCancelled(file2, n2, collection);
                            this.handleFile(file2, n2, collection);
                            this.checkIfCancelled(file2, n2, collection);
                        }
                    }
                }
            }
            this.handleDirectoryEnd(file, n, collection);
        }
        this.checkIfCancelled(file, n, collection);
    }
    
    protected final void checkIfCancelled(final File file, final int n, final Collection<T> collection) {
        if (this.handleIsCancelled(file, n, collection)) {
            throw new CancelException(file, n);
        }
    }
    
    protected boolean handleIsCancelled(final File file, final int n, final Collection<T> collection) {
        return false;
    }
    
    protected void handleCancelled(final File file, final Collection<T> collection, final CancelException ex) {
        throw ex;
    }
    
    protected void handleStart(final File file, final Collection<T> collection) {
    }
    
    protected boolean handleDirectory(final File file, final int n, final Collection<T> collection) {
        return true;
    }
    
    protected void handleDirectoryStart(final File file, final int n, final Collection<T> collection) {
    }
    
    protected File[] filterDirectoryContents(final File file, final int n, final File[] array) {
        return array;
    }
    
    protected void handleFile(final File file, final int n, final Collection<T> collection) {
    }
    
    protected void handleRestricted(final File file, final int n, final Collection<T> collection) {
    }
    
    protected void handleDirectoryEnd(final File file, final int n, final Collection<T> collection) {
    }
    
    protected void handleEnd(final Collection<T> collection) {
    }
    
    public static class CancelException extends IOException
    {
        private static final long serialVersionUID = 1347339620135041008L;
        private final File file;
        private final int depth;
        
        public CancelException(final File file, final int n) {
            this("Operation Cancelled", file, n);
        }
        
        public CancelException(final String message, final File file, final int depth) {
            super(message);
            this.file = file;
            this.depth = depth;
        }
        
        public File getFile() {
            return this.file;
        }
        
        public int getDepth() {
            return this.depth;
        }
    }
}
