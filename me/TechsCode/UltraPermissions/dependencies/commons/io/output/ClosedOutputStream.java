

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public class ClosedOutputStream extends OutputStream
{
    public static final ClosedOutputStream CLOSED_OUTPUT_STREAM;
    
    @Override
    public void write(final int i) {
        throw new IOException("write(" + i + ") failed: stream is closed");
    }
    
    @Override
    public void flush() {
        throw new IOException("flush() failed: stream is closed");
    }
    
    static {
        CLOSED_OUTPUT_STREAM = new ClosedOutputStream();
    }
}
