

package me.TechsCode.EnderPermissions.dependencies.commons.codec.binary;

import java.io.OutputStream;
import java.io.FilterOutputStream;

public class BaseNCodecOutputStream extends FilterOutputStream
{
    private final boolean doEncode;
    private final BaseNCodec baseNCodec;
    private final byte[] singleByte;
    private final BaseNCodec.Context context;
    
    public BaseNCodecOutputStream(final OutputStream out, final BaseNCodec baseNCodec, final boolean doEncode) {
        super(out);
        this.singleByte = new byte[1];
        this.context = new BaseNCodec.Context();
        this.baseNCodec = baseNCodec;
        this.doEncode = doEncode;
    }
    
    @Override
    public void write(final int n) {
        this.singleByte[0] = (byte)n;
        this.write(this.singleByte, 0, 1);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) {
        if (array == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n > array.length || n + n2 > array.length) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 > 0) {
            if (this.doEncode) {
                this.baseNCodec.encode(array, n, n2, this.context);
            }
            else {
                this.baseNCodec.decode(array, n, n2, this.context);
            }
            this.flush(false);
        }
    }
    
    private void flush(final boolean b) {
        final int available = this.baseNCodec.available(this.context);
        if (available > 0) {
            final byte[] b2 = new byte[available];
            final int results = this.baseNCodec.readResults(b2, 0, available, this.context);
            if (results > 0) {
                this.out.write(b2, 0, results);
            }
        }
        if (b) {
            this.out.flush();
        }
    }
    
    @Override
    public void flush() {
        this.flush(true);
    }
    
    @Override
    public void close() {
        this.eof();
        this.flush();
        this.out.close();
    }
    
    public void eof() {
        if (this.doEncode) {
            this.baseNCodec.encode(this.singleByte, 0, -1, this.context);
        }
        else {
            this.baseNCodec.decode(this.singleByte, 0, -1, this.context);
        }
    }
}
