

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.OutputStream;

public class DemuxOutputStream extends OutputStream
{
    private final InheritableThreadLocal<OutputStream> outputStreamThreadLocal;
    
    public DemuxOutputStream() {
        this.outputStreamThreadLocal = new InheritableThreadLocal<OutputStream>();
    }
    
    public OutputStream bindStream(final OutputStream value) {
        final OutputStream outputStream = this.outputStreamThreadLocal.get();
        this.outputStreamThreadLocal.set(value);
        return outputStream;
    }
    
    @Override
    public void close() {
        final OutputStream outputStream = this.outputStreamThreadLocal.get();
        if (null != outputStream) {
            outputStream.close();
        }
    }
    
    @Override
    public void flush() {
        final OutputStream outputStream = this.outputStreamThreadLocal.get();
        if (null != outputStream) {
            outputStream.flush();
        }
    }
    
    @Override
    public void write(final int n) {
        final OutputStream outputStream = this.outputStreamThreadLocal.get();
        if (null != outputStream) {
            outputStream.write(n);
        }
    }
}
