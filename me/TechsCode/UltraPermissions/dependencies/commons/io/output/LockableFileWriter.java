

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import me.TechsCode.EnderPermissions.dependencies.commons.io.Charsets;
import java.io.IOException;
import me.TechsCode.EnderPermissions.dependencies.commons.io.FileUtils;
import java.nio.charset.Charset;
import java.io.File;
import java.io.Writer;

public class LockableFileWriter extends Writer
{
    private static final String LCK = ".lck";
    private final Writer out;
    private final File lockFile;
    
    public LockableFileWriter(final String s) {
        this(s, false, null);
    }
    
    public LockableFileWriter(final String s, final boolean b) {
        this(s, b, null);
    }
    
    public LockableFileWriter(final String pathname, final boolean b, final String s) {
        this(new File(pathname), b, s);
    }
    
    public LockableFileWriter(final File file) {
        this(file, false, null);
    }
    
    public LockableFileWriter(final File file, final boolean b) {
        this(file, b, null);
    }
    
    @Deprecated
    public LockableFileWriter(final File file, final boolean b, final String s) {
        this(file, Charset.defaultCharset(), b, s);
    }
    
    public LockableFileWriter(final File file, final Charset charset) {
        this(file, charset, false, null);
    }
    
    public LockableFileWriter(final File file, final String s) {
        this(file, s, false, null);
    }
    
    public LockableFileWriter(File absoluteFile, final Charset charset, final boolean b, String property) {
        absoluteFile = absoluteFile.getAbsoluteFile();
        if (absoluteFile.getParentFile() != null) {
            FileUtils.forceMkdir(absoluteFile.getParentFile());
        }
        if (absoluteFile.isDirectory()) {
            throw new IOException("File specified is a directory");
        }
        if (property == null) {
            property = System.getProperty("java.io.tmpdir");
        }
        final File parent = new File(property);
        FileUtils.forceMkdir(parent);
        this.testLockDir(parent);
        this.lockFile = new File(parent, absoluteFile.getName() + ".lck");
        this.createLock();
        this.out = this.initWriter(absoluteFile, charset, b);
    }
    
    public LockableFileWriter(final File file, final String s, final boolean b, final String s2) {
        this(file, Charsets.toCharset(s), b, s2);
    }
    
    private void testLockDir(final File file) {
        if (!file.exists()) {
            throw new IOException("Could not find lockDir: " + file.getAbsolutePath());
        }
        if (!file.canWrite()) {
            throw new IOException("Could not write to lockDir: " + file.getAbsolutePath());
        }
    }
    
    private void createLock() {
        synchronized (LockableFileWriter.class) {
            if (!this.lockFile.createNewFile()) {
                throw new IOException("Can't write file, lock " + this.lockFile.getAbsolutePath() + " exists");
            }
            this.lockFile.deleteOnExit();
        }
    }
    
    private Writer initWriter(final File file, final Charset charset, final boolean append) {
        final boolean exists = file.exists();
        try {
            return new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath(), append), Charsets.toCharset(charset));
        }
        catch (IOException | RuntimeException ex) {
            final Object o2;
            final Object o = o2;
            FileUtils.deleteQuietly(this.lockFile);
            if (!exists) {
                FileUtils.deleteQuietly(file);
            }
            throw o;
        }
    }
    
    @Override
    public void close() {
        try {
            this.out.close();
        }
        finally {
            this.lockFile.delete();
        }
    }
    
    @Override
    public void write(final int c) {
        this.out.write(c);
    }
    
    @Override
    public void write(final char[] cbuf) {
        this.out.write(cbuf);
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) {
        this.out.write(array, n, n2);
    }
    
    @Override
    public void write(final String str) {
        this.out.write(str);
    }
    
    @Override
    public void write(final String str, final int off, final int len) {
        this.out.write(str, off, len);
    }
    
    @Override
    public void flush() {
        this.out.flush();
    }
}
