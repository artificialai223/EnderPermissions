

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.OutputStream;

public class CloseShieldOutputStream extends ProxyOutputStream
{
    public CloseShieldOutputStream(final OutputStream outputStream) {
        super(outputStream);
    }
    
    @Override
    public void close() {
        this.out = new ClosedOutputStream();
    }
}
