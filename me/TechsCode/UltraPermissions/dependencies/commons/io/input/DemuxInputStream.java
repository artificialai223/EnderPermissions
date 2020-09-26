

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.io.InputStream;

public class DemuxInputStream extends InputStream
{
    private final InheritableThreadLocal<InputStream> m_streams;
    
    public DemuxInputStream() {
        this.m_streams = new InheritableThreadLocal<InputStream>();
    }
    
    public InputStream bindStream(final InputStream value) {
        final InputStream inputStream = this.m_streams.get();
        this.m_streams.set(value);
        return inputStream;
    }
    
    @Override
    public void close() {
        final InputStream inputStream = this.m_streams.get();
        if (null != inputStream) {
            inputStream.close();
        }
    }
    
    @Override
    public int read() {
        final InputStream inputStream = this.m_streams.get();
        if (null != inputStream) {
            return inputStream.read();
        }
        return -1;
    }
}
