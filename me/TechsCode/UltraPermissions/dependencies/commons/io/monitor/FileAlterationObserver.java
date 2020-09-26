

package me.TechsCode.EnderPermissions.dependencies.commons.io.monitor;

import java.util.Arrays;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.dependencies.commons.io.FileUtils;
import me.TechsCode.EnderPermissions.dependencies.commons.io.comparator.NameFileComparator;
import java.util.concurrent.CopyOnWriteArrayList;
import me.TechsCode.EnderPermissions.dependencies.commons.io.IOCase;
import java.io.File;
import java.util.Comparator;
import java.io.FileFilter;
import java.util.List;
import java.io.Serializable;

public class FileAlterationObserver implements Serializable
{
    private static final long serialVersionUID = 1185122225658782848L;
    private final List<FileAlterationListener> listeners;
    private final FileEntry rootEntry;
    private final FileFilter fileFilter;
    private final Comparator<File> comparator;
    
    public FileAlterationObserver(final String pathname) {
        this(new File(pathname));
    }
    
    public FileAlterationObserver(final String pathname, final FileFilter fileFilter) {
        this(new File(pathname), fileFilter);
    }
    
    public FileAlterationObserver(final String pathname, final FileFilter fileFilter, final IOCase ioCase) {
        this(new File(pathname), fileFilter, ioCase);
    }
    
    public FileAlterationObserver(final File file) {
        this(file, null);
    }
    
    public FileAlterationObserver(final File file, final FileFilter fileFilter) {
        this(file, fileFilter, null);
    }
    
    public FileAlterationObserver(final File file, final FileFilter fileFilter, final IOCase ioCase) {
        this(new FileEntry(file), fileFilter, ioCase);
    }
    
    protected FileAlterationObserver(final FileEntry rootEntry, final FileFilter fileFilter, final IOCase ioCase) {
        this.listeners = new CopyOnWriteArrayList<FileAlterationListener>();
        if (rootEntry == null) {
            throw new IllegalArgumentException("Root entry is missing");
        }
        if (rootEntry.getFile() == null) {
            throw new IllegalArgumentException("Root directory is missing");
        }
        this.rootEntry = rootEntry;
        this.fileFilter = fileFilter;
        if (ioCase == null || ioCase.equals(IOCase.SYSTEM)) {
            this.comparator = NameFileComparator.NAME_SYSTEM_COMPARATOR;
        }
        else if (ioCase.equals(IOCase.INSENSITIVE)) {
            this.comparator = NameFileComparator.NAME_INSENSITIVE_COMPARATOR;
        }
        else {
            this.comparator = NameFileComparator.NAME_COMPARATOR;
        }
    }
    
    public File getDirectory() {
        return this.rootEntry.getFile();
    }
    
    public FileFilter getFileFilter() {
        return this.fileFilter;
    }
    
    public void addListener(final FileAlterationListener fileAlterationListener) {
        if (fileAlterationListener != null) {
            this.listeners.add(fileAlterationListener);
        }
    }
    
    public void removeListener(final FileAlterationListener fileAlterationListener) {
        if (fileAlterationListener != null) {
            while (this.listeners.remove(fileAlterationListener)) {}
        }
    }
    
    public Iterable<FileAlterationListener> getListeners() {
        return this.listeners;
    }
    
    public void initialize() {
        this.rootEntry.refresh(this.rootEntry.getFile());
        this.rootEntry.setChildren(this.doListFiles(this.rootEntry.getFile(), this.rootEntry));
    }
    
    public void destroy() {
    }
    
    public void checkAndNotify() {
        final Iterator<FileAlterationListener> iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onStart(this);
        }
        final File file = this.rootEntry.getFile();
        if (file.exists()) {
            this.checkAndNotify(this.rootEntry, this.rootEntry.getChildren(), this.listFiles(file));
        }
        else if (this.rootEntry.isExists()) {
            this.checkAndNotify(this.rootEntry, this.rootEntry.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
        }
        final Iterator<FileAlterationListener> iterator2 = this.listeners.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().onStop(this);
        }
    }
    
    private void checkAndNotify(final FileEntry fileEntry, final FileEntry[] array, final File[] array2) {
        int i = 0;
        final FileEntry[] children = (array2.length > 0) ? new FileEntry[array2.length] : FileEntry.EMPTY_ENTRIES;
        for (final FileEntry fileEntry2 : array) {
            while (i < array2.length && this.comparator.compare(fileEntry2.getFile(), array2[i]) > 0) {
                this.doCreate(children[i] = this.createFileEntry(fileEntry, array2[i]));
                ++i;
            }
            if (i < array2.length && this.comparator.compare(fileEntry2.getFile(), array2[i]) == 0) {
                this.doMatch(fileEntry2, array2[i]);
                this.checkAndNotify(fileEntry2, fileEntry2.getChildren(), this.listFiles(array2[i]));
                children[i] = fileEntry2;
                ++i;
            }
            else {
                this.checkAndNotify(fileEntry2, fileEntry2.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
                this.doDelete(fileEntry2);
            }
        }
        while (i < array2.length) {
            this.doCreate(children[i] = this.createFileEntry(fileEntry, array2[i]));
            ++i;
        }
        fileEntry.setChildren(children);
    }
    
    private FileEntry createFileEntry(final FileEntry fileEntry, final File file) {
        final FileEntry childInstance = fileEntry.newChildInstance(file);
        childInstance.refresh(file);
        childInstance.setChildren(this.doListFiles(file, childInstance));
        return childInstance;
    }
    
    private FileEntry[] doListFiles(final File file, final FileEntry fileEntry) {
        final File[] listFiles = this.listFiles(file);
        final FileEntry[] array = (listFiles.length > 0) ? new FileEntry[listFiles.length] : FileEntry.EMPTY_ENTRIES;
        for (int i = 0; i < listFiles.length; ++i) {
            array[i] = this.createFileEntry(fileEntry, listFiles[i]);
        }
        return array;
    }
    
    private void doCreate(final FileEntry fileEntry) {
        for (final FileAlterationListener fileAlterationListener : this.listeners) {
            if (fileEntry.isDirectory()) {
                fileAlterationListener.onDirectoryCreate(fileEntry.getFile());
            }
            else {
                fileAlterationListener.onFileCreate(fileEntry.getFile());
            }
        }
        final FileEntry[] children = fileEntry.getChildren();
        for (int length = children.length, i = 0; i < length; ++i) {
            this.doCreate(children[i]);
        }
    }
    
    private void doMatch(final FileEntry fileEntry, final File file) {
        if (fileEntry.refresh(file)) {
            for (final FileAlterationListener fileAlterationListener : this.listeners) {
                if (fileEntry.isDirectory()) {
                    fileAlterationListener.onDirectoryChange(file);
                }
                else {
                    fileAlterationListener.onFileChange(file);
                }
            }
        }
    }
    
    private void doDelete(final FileEntry fileEntry) {
        for (final FileAlterationListener fileAlterationListener : this.listeners) {
            if (fileEntry.isDirectory()) {
                fileAlterationListener.onDirectoryDelete(fileEntry.getFile());
            }
            else {
                fileAlterationListener.onFileDelete(fileEntry.getFile());
            }
        }
    }
    
    private File[] listFiles(final File file) {
        File[] empty_FILE_ARRAY = null;
        if (file.isDirectory()) {
            empty_FILE_ARRAY = ((this.fileFilter == null) ? file.listFiles() : file.listFiles(this.fileFilter));
        }
        if (empty_FILE_ARRAY == null) {
            empty_FILE_ARRAY = FileUtils.EMPTY_FILE_ARRAY;
        }
        if (this.comparator != null && empty_FILE_ARRAY.length > 1) {
            Arrays.sort(empty_FILE_ARRAY, this.comparator);
        }
        return empty_FILE_ARRAY;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append("[file='");
        sb.append(this.getDirectory().getPath());
        sb.append('\'');
        if (this.fileFilter != null) {
            sb.append(", ");
            sb.append(this.fileFilter.toString());
        }
        sb.append(", listeners=");
        sb.append(this.listeners.size());
        sb.append("]");
        return sb.toString();
    }
}
