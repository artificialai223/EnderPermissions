

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.OutputStream;
import java.io.FilterOutputStream;

public class ChunkedOutputStream extends FilterOutputStream
{
    private static final int DEFAULT_CHUNK_SIZE = 4096;
    private final int chunkSize;
    
    public ChunkedOutputStream(final OutputStream out, final int chunkSize) {
        super(out);
        if (chunkSize <= 0) {
            throw new IllegalArgumentException();
        }
        this.chunkSize = chunkSize;
    }
    
    public ChunkedOutputStream(final OutputStream outputStream) {
        this(outputStream, 4096);
    }
    
    @Override
    public void write(final byte[] b, final int n, final int n2) {
        int min;
        for (int i = n2, off = n; i > 0; i -= min, off += min) {
            min = Math.min(i, this.chunkSize);
            this.out.write(b, off, min);
        }
    }
}
