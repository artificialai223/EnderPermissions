

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.Writer;
import java.io.FilterWriter;

public class ChunkedWriter extends FilterWriter
{
    private static final int DEFAULT_CHUNK_SIZE = 4096;
    private final int chunkSize;
    
    public ChunkedWriter(final Writer out, final int chunkSize) {
        super(out);
        if (chunkSize <= 0) {
            throw new IllegalArgumentException();
        }
        this.chunkSize = chunkSize;
    }
    
    public ChunkedWriter(final Writer writer) {
        this(writer, 4096);
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) {
        int min;
        for (int i = n2, n3 = n; i > 0; i -= min, n3 += min) {
            min = Math.min(i, this.chunkSize);
            this.out.write(array, n3, min);
        }
    }
}
