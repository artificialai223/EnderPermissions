

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.nio.CharBuffer;
import java.io.IOException;
import java.io.Reader;
import java.io.FilterReader;

public abstract class ProxyReader extends FilterReader
{
    public ProxyReader(final Reader in) {
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
    public int read(final char[] cbuf) {
        try {
            this.beforeRead((cbuf != null) ? cbuf.length : 0);
            final int read = this.in.read(cbuf);
            this.afterRead(read);
            return read;
        }
        catch (IOException ex) {
            this.handleIOException(ex);
            return -1;
        }
    }
    
    @Override
    public int read(final char[] array, final int n, final int n2) {
        try {
            this.beforeRead(n2);
            final int read = this.in.read(array, n, n2);
            this.afterRead(read);
            return read;
        }
        catch (IOException ex) {
            this.handleIOException(ex);
            return -1;
        }
    }
    
    @Override
    public int read(final CharBuffer target) {
        try {
            this.beforeRead((target != null) ? target.length() : 0);
            final int read = this.in.read(target);
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
    public boolean ready() {
        try {
            return this.in.ready();
        }
        catch (IOException ex) {
            this.handleIOException(ex);
            return false;
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
    public synchronized void mark(final int readAheadLimit) {
        try {
            this.in.mark(readAheadLimit);
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
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
