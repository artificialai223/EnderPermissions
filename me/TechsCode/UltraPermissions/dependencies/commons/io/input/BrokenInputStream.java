

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

public class BrokenInputStream extends InputStream
{
    private final IOException exception;
    
    public BrokenInputStream(final IOException exception) {
        this.exception = exception;
    }
    
    public BrokenInputStream() {
        this(new IOException("Broken input stream"));
    }
    
    @Override
    public int read() {
        throw this.exception;
    }
    
    @Override
    public int available() {
        throw this.exception;
    }
    
    @Override
    public long skip(final long n) {
        throw this.exception;
    }
    
    @Override
    public synchronized void reset() {
        throw this.exception;
    }
    
    @Override
    public void close() {
        throw this.exception;
    }
}
