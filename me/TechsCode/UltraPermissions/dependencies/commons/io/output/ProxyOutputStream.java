

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;
import java.io.FilterOutputStream;

public class ProxyOutputStream extends FilterOutputStream
{
    public ProxyOutputStream(final OutputStream out) {
        super(out);
    }
    
    @Override
    public void write(final int n) {
        try {
            this.beforeWrite(1);
            this.out.write(n);
            this.afterWrite(1);
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
    }
    
    @Override
    public void write(final byte[] b) {
        try {
            final int n = (b != null) ? b.length : 0;
            this.beforeWrite(n);
            this.out.write(b);
            this.afterWrite(n);
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
    }
    
    @Override
    public void write(final byte[] b, final int off, final int len) {
        try {
            this.beforeWrite(len);
            this.out.write(b, off, len);
            this.afterWrite(len);
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
    }
    
    @Override
    public void flush() {
        try {
            this.out.flush();
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
    }
    
    @Override
    public void close() {
        try {
            this.out.close();
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
    }
    
    protected void beforeWrite(final int n) {
    }
    
    protected void afterWrite(final int n) {
    }
    
    protected void handleIOException(final IOException ex) {
        throw ex;
    }
}
