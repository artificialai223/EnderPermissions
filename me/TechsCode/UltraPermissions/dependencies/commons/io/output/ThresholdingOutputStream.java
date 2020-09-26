

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public abstract class ThresholdingOutputStream extends OutputStream
{
    private final int threshold;
    private long written;
    private boolean thresholdExceeded;
    
    public ThresholdingOutputStream(final int threshold) {
        this.threshold = threshold;
    }
    
    @Override
    public void write(final int n) {
        this.checkThreshold(1);
        this.getStream().write(n);
        ++this.written;
    }
    
    @Override
    public void write(final byte[] b) {
        this.checkThreshold(b.length);
        this.getStream().write(b);
        this.written += b.length;
    }
    
    @Override
    public void write(final byte[] b, final int off, final int len) {
        this.checkThreshold(len);
        this.getStream().write(b, off, len);
        this.written += len;
    }
    
    @Override
    public void flush() {
        this.getStream().flush();
    }
    
    @Override
    public void close() {
        try {
            this.flush();
        }
        catch (IOException ex) {}
        this.getStream().close();
    }
    
    public int getThreshold() {
        return this.threshold;
    }
    
    public long getByteCount() {
        return this.written;
    }
    
    public boolean isThresholdExceeded() {
        return this.written > this.threshold;
    }
    
    protected void checkThreshold(final int n) {
        if (!this.thresholdExceeded && this.written + n > this.threshold) {
            this.thresholdExceeded = true;
            this.thresholdReached();
        }
    }
    
    protected void resetByteCount() {
        this.thresholdExceeded = false;
        this.written = 0L;
    }
    
    protected void setByteCount(final long written) {
        this.written = written;
    }
    
    protected abstract OutputStream getStream();
    
    protected abstract void thresholdReached();
}
