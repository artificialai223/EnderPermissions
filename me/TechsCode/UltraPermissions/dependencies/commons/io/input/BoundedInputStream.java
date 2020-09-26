

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.io.InputStream;

public class BoundedInputStream extends InputStream
{
    private final InputStream in;
    private final long max;
    private long pos;
    private long mark;
    private boolean propagateClose;
    
    public BoundedInputStream(final InputStream in, final long max) {
        this.pos = 0L;
        this.mark = -1L;
        this.propagateClose = true;
        this.max = max;
        this.in = in;
    }
    
    public BoundedInputStream(final InputStream inputStream) {
        this(inputStream, -1L);
    }
    
    @Override
    public int read() {
        if (this.max >= 0L && this.pos >= this.max) {
            return -1;
        }
        final int read = this.in.read();
        ++this.pos;
        return read;
    }
    
    @Override
    public int read(final byte[] array) {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public int read(final byte[] b, final int off, final int n) {
        if (this.max >= 0L && this.pos >= this.max) {
            return -1;
        }
        final int read = this.in.read(b, off, (int)((this.max >= 0L) ? Math.min(n, this.max - this.pos) : n));
        if (read == -1) {
            return -1;
        }
        this.pos += read;
        return read;
    }
    
    @Override
    public long skip(final long a) {
        final long skip = this.in.skip((this.max >= 0L) ? Math.min(a, this.max - this.pos) : a);
        this.pos += skip;
        return skip;
    }
    
    @Override
    public int available() {
        if (this.max >= 0L && this.pos >= this.max) {
            return 0;
        }
        return this.in.available();
    }
    
    @Override
    public String toString() {
        return this.in.toString();
    }
    
    @Override
    public void close() {
        if (this.propagateClose) {
            this.in.close();
        }
    }
    
    @Override
    public synchronized void reset() {
        this.in.reset();
        this.pos = this.mark;
    }
    
    @Override
    public synchronized void mark(final int readlimit) {
        this.in.mark(readlimit);
        this.mark = this.pos;
    }
    
    @Override
    public boolean markSupported() {
        return this.in.markSupported();
    }
    
    public boolean isPropagateClose() {
        return this.propagateClose;
    }
    
    public void setPropagateClose(final boolean propagateClose) {
        this.propagateClose = propagateClose;
    }
}
