

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public class BrokenOutputStream extends OutputStream
{
    private final IOException exception;
    
    public BrokenOutputStream(final IOException exception) {
        this.exception = exception;
    }
    
    public BrokenOutputStream() {
        this(new IOException("Broken output stream"));
    }
    
    @Override
    public void write(final int n) {
        throw this.exception;
    }
    
    @Override
    public void flush() {
        throw this.exception;
    }
    
    @Override
    public void close() {
        throw this.exception;
    }
}
