

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.io.Reader;

public class BoundedReader extends Reader
{
    private static final int INVALID = -1;
    private final Reader target;
    private int charsRead;
    private int markedAt;
    private int readAheadLimit;
    private final int maxCharsFromTargetReader;
    
    public BoundedReader(final Reader target, final int maxCharsFromTargetReader) {
        this.charsRead = 0;
        this.markedAt = -1;
        this.target = target;
        this.maxCharsFromTargetReader = maxCharsFromTargetReader;
    }
    
    @Override
    public void close() {
        this.target.close();
    }
    
    @Override
    public void reset() {
        this.charsRead = this.markedAt;
        this.target.reset();
    }
    
    @Override
    public void mark(final int readAheadLimit) {
        this.readAheadLimit = readAheadLimit - this.charsRead;
        this.markedAt = this.charsRead;
        this.target.mark(readAheadLimit);
    }
    
    @Override
    public int read() {
        if (this.charsRead >= this.maxCharsFromTargetReader) {
            return -1;
        }
        if (this.markedAt >= 0 && this.charsRead - this.markedAt >= this.readAheadLimit) {
            return -1;
        }
        ++this.charsRead;
        return this.target.read();
    }
    
    @Override
    public int read(final char[] array, final int n, final int n2) {
        for (int i = 0; i < n2; ++i) {
            final int read = this.read();
            if (read == -1) {
                return (i == 0) ? -1 : i;
            }
            array[n + i] = (char)read;
        }
        return n2;
    }
}
