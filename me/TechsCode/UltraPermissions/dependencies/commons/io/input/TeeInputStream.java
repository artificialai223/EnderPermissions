

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.io.InputStream;
import java.io.OutputStream;

public class TeeInputStream extends ProxyInputStream
{
    private final OutputStream branch;
    private final boolean closeBranch;
    
    public TeeInputStream(final InputStream inputStream, final OutputStream outputStream) {
        this(inputStream, outputStream, false);
    }
    
    public TeeInputStream(final InputStream inputStream, final OutputStream branch, final boolean closeBranch) {
        super(inputStream);
        this.branch = branch;
        this.closeBranch = closeBranch;
    }
    
    @Override
    public void close() {
        try {
            super.close();
        }
        finally {
            if (this.closeBranch) {
                this.branch.close();
            }
        }
    }
    
    @Override
    public int read() {
        final int read = super.read();
        if (read != -1) {
            this.branch.write(read);
        }
        return read;
    }
    
    @Override
    public int read(final byte[] b, final int off, final int n) {
        final int read = super.read(b, off, n);
        if (read != -1) {
            this.branch.write(b, off, read);
        }
        return read;
    }
    
    @Override
    public int read(final byte[] b) {
        final int read = super.read(b);
        if (read != -1) {
            this.branch.write(b, 0, read);
        }
        return read;
    }
}
