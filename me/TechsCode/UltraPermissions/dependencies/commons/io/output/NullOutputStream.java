

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.OutputStream;

public class NullOutputStream extends OutputStream
{
    public static final NullOutputStream NULL_OUTPUT_STREAM;
    
    @Override
    public void write(final byte[] array, final int n, final int n2) {
    }
    
    @Override
    public void write(final int n) {
    }
    
    @Override
    public void write(final byte[] array) {
    }
    
    static {
        NULL_OUTPUT_STREAM = new NullOutputStream();
    }
}
