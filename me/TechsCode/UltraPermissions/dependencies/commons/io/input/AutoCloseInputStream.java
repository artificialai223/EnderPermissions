

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.io.InputStream;

public class AutoCloseInputStream extends ProxyInputStream
{
    public AutoCloseInputStream(final InputStream inputStream) {
        super(inputStream);
    }
    
    @Override
    public void close() {
        this.in.close();
        this.in = new ClosedInputStream();
    }
    
    @Override
    protected void afterRead(final int n) {
        if (n == -1) {
            this.close();
        }
    }
    
    @Override
    protected void finalize() {
        this.close();
        super.finalize();
    }
}
