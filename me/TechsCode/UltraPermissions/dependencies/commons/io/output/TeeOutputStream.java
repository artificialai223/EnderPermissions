

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.OutputStream;

public class TeeOutputStream extends ProxyOutputStream
{
    protected OutputStream branch;
    
    public TeeOutputStream(final OutputStream outputStream, final OutputStream branch) {
        super(outputStream);
        this.branch = branch;
    }
    
    @Override
    public synchronized void write(final byte[] b) {
        super.write(b);
        this.branch.write(b);
    }
    
    @Override
    public synchronized void write(final byte[] b, final int off, final int len) {
        super.write(b, off, len);
        this.branch.write(b, off, len);
    }
    
    @Override
    public synchronized void write(final int n) {
        super.write(n);
        this.branch.write(n);
    }
    
    @Override
    public void flush() {
        super.flush();
        this.branch.flush();
    }
    
    @Override
    public void close() {
        try {
            super.close();
        }
        finally {
            this.branch.close();
        }
    }
}
