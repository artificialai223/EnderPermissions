

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.FilterInputStream;

public abstract class ProxyInputStream extends FilterInputStream
{
    public ProxyInputStream(final InputStream in) {
        super(in);
    }
    
    @Override
    public int read() {
        try {
            this.beforeRead(1);
            final int read = this.in.read();
            this.afterRead((read != -1) ? 1 : -1);
            return read;
        }
        catch (IOException ex) {
            this.handleIOException(ex);
            return -1;
        }
    }
    
    @Override
    public int read(final byte[] b) {
        try {
            this.beforeRead((b != null) ? b.length : 0);
            final int read = this.in.read(b);
            this.afterRead(read);
            return read;
        }
        catch (IOException ex) {
            this.handleIOException(ex);
            return -1;
        }
    }
    
    @Override
    public int read(final byte[] b, final int off, final int len) {
        try {
            this.beforeRead(len);
            final int read = this.in.read(b, off, len);
            this.afterRead(read);
            return read;
        }
        catch (IOException ex) {
            this.handleIOException(ex);
            return -1;
        }
    }
    
    @Override
    public long skip(final long n) {
        try {
            return this.in.skip(n);
        }
        catch (IOException ex) {
            this.handleIOException(ex);
            return 0L;
        }
    }
    
    @Override
    public int available() {
        try {
            return super.available();
        }
        catch (IOException ex) {
            this.handleIOException(ex);
            return 0;
        }
    }
    
    @Override
    public void close() {
        try {
            this.in.close();
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
    }
    
    @Override
    public synchronized void mark(final int readlimit) {
        this.in.mark(readlimit);
    }
    
    @Override
    public synchronized void reset() {
        try {
            this.in.reset();
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
    }
    
    @Override
    public boolean markSupported() {
        return this.in.markSupported();
    }
    
    protected void beforeRead(final int n) {
    }
    
    protected void afterRead(final int n) {
    }
    
    protected void handleIOException(final IOException ex) {
        throw ex;
    }
}
