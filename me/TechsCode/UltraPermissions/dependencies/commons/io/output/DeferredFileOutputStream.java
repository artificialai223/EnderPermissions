

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.InputStream;
import me.TechsCode.EnderPermissions.dependencies.commons.io.IOUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import me.TechsCode.EnderPermissions.dependencies.commons.io.FileUtils;
import java.io.File;
import java.io.OutputStream;

public class DeferredFileOutputStream extends ThresholdingOutputStream
{
    private ByteArrayOutputStream memoryOutputStream;
    private OutputStream currentOutputStream;
    private File outputFile;
    private final String prefix;
    private final String suffix;
    private final File directory;
    private boolean closed;
    
    public DeferredFileOutputStream(final int n, final File file) {
        this(n, file, null, null, null, 1024);
    }
    
    public DeferredFileOutputStream(final int n, final int n2, final File file) {
        this(n, file, null, null, null, n2);
        if (n2 < 0) {
            throw new IllegalArgumentException("Initial buffer size must be atleast 0.");
        }
    }
    
    public DeferredFileOutputStream(final int n, final String s, final String s2, final File file) {
        this(n, null, s, s2, file, 1024);
        if (s == null) {
            throw new IllegalArgumentException("Temporary file prefix is missing");
        }
    }
    
    public DeferredFileOutputStream(final int n, final int n2, final String s, final String s2, final File file) {
        this(n, null, s, s2, file, n2);
        if (s == null) {
            throw new IllegalArgumentException("Temporary file prefix is missing");
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("Initial buffer size must be atleast 0.");
        }
    }
    
    private DeferredFileOutputStream(final int n, final File outputFile, final String prefix, final String suffix, final File directory, final int n2) {
        super(n);
        this.closed = false;
        this.outputFile = outputFile;
        this.prefix = prefix;
        this.suffix = suffix;
        this.directory = directory;
        this.memoryOutputStream = new ByteArrayOutputStream(n2);
        this.currentOutputStream = this.memoryOutputStream;
    }
    
    @Override
    protected OutputStream getStream() {
        return this.currentOutputStream;
    }
    
    @Override
    protected void thresholdReached() {
        if (this.prefix != null) {
            this.outputFile = File.createTempFile(this.prefix, this.suffix, this.directory);
        }
        FileUtils.forceMkdirParent(this.outputFile);
        final FileOutputStream currentOutputStream = new FileOutputStream(this.outputFile);
        try {
            this.memoryOutputStream.writeTo(currentOutputStream);
        }
        catch (IOException ex) {
            currentOutputStream.close();
            throw ex;
        }
        this.currentOutputStream = currentOutputStream;
        this.memoryOutputStream = null;
    }
    
    public boolean isInMemory() {
        return !this.isThresholdExceeded();
    }
    
    public byte[] getData() {
        if (this.memoryOutputStream != null) {
            return this.memoryOutputStream.toByteArray();
        }
        return null;
    }
    
    public File getFile() {
        return this.outputFile;
    }
    
    @Override
    public void close() {
        super.close();
        this.closed = true;
    }
    
    public void writeTo(final OutputStream outputStream) {
        if (!this.closed) {
            throw new IOException("Stream not closed");
        }
        if (this.isInMemory()) {
            this.memoryOutputStream.writeTo(outputStream);
        }
        else {
            try (final FileInputStream fileInputStream = new FileInputStream(this.outputFile)) {
                IOUtils.copy(fileInputStream, outputStream);
            }
        }
    }
}
