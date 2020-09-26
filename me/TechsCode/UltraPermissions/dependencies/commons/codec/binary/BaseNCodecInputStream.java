

package me.TechsCode.EnderPermissions.dependencies.commons.codec.binary;

import java.io.IOException;
import java.io.InputStream;
import java.io.FilterInputStream;

public class BaseNCodecInputStream extends FilterInputStream
{
    private final BaseNCodec baseNCodec;
    private final boolean doEncode;
    private final byte[] singleByte;
    private final BaseNCodec.Context context;
    
    protected BaseNCodecInputStream(final InputStream in, final BaseNCodec baseNCodec, final boolean doEncode) {
        super(in);
        this.singleByte = new byte[1];
        this.context = new BaseNCodec.Context();
        this.doEncode = doEncode;
        this.baseNCodec = baseNCodec;
    }
    
    @Override
    public int available() {
        return this.context.eof ? 0 : 1;
    }
    
    @Override
    public synchronized void mark(final int n) {
    }
    
    @Override
    public boolean markSupported() {
        return false;
    }
    
    @Override
    public int read() {
        int i;
        for (i = this.read(this.singleByte, 0, 1); i == 0; i = this.read(this.singleByte, 0, 1)) {}
        if (i > 0) {
            final byte b = this.singleByte[0];
            return (b < 0) ? (256 + b) : b;
        }
        return -1;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n > array.length || n + n2 > array.length) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return 0;
        }
        int i;
        for (i = 0; i == 0; i = this.baseNCodec.readResults(array, n, n2, this.context)) {
            if (!this.baseNCodec.hasData(this.context)) {
                final byte[] b = new byte[this.doEncode ? 4096 : 8192];
                final int read = this.in.read(b);
                if (this.doEncode) {
                    this.baseNCodec.encode(b, 0, read, this.context);
                }
                else {
                    this.baseNCodec.decode(b, 0, read, this.context);
                }
            }
        }
        return i;
    }
    
    @Override
    public synchronized void reset() {
        throw new IOException("mark/reset not supported");
    }
    
    @Override
    public long skip(final long lng) {
        if (lng < 0L) {
            throw new IllegalArgumentException("Negative skip length: " + lng);
        }
        final byte[] array = new byte[512];
        long b;
        int read;
        for (b = lng; b > 0L; b -= read) {
            read = this.read(array, 0, (int)Math.min(array.length, b));
            if (read == -1) {
                break;
            }
        }
        return lng - b;
    }
}
