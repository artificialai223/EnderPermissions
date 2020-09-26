

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.Charset;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.io.InputStream;

public class CharSequenceInputStream extends InputStream
{
    private static final int BUFFER_SIZE = 2048;
    private static final int NO_MARK = -1;
    private final CharsetEncoder encoder;
    private final CharBuffer cbuf;
    private final ByteBuffer bbuf;
    private int mark_cbuf;
    private int mark_bbuf;
    
    public CharSequenceInputStream(final CharSequence csq, final Charset charset, final int n) {
        this.encoder = charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        final float maxBytesPerChar = this.encoder.maxBytesPerChar();
        if (n < maxBytesPerChar) {
            throw new IllegalArgumentException("Buffer size " + n + " is less than maxBytesPerChar " + maxBytesPerChar);
        }
        (this.bbuf = ByteBuffer.allocate(n)).flip();
        this.cbuf = CharBuffer.wrap(csq);
        this.mark_cbuf = -1;
        this.mark_bbuf = -1;
    }
    
    public CharSequenceInputStream(final CharSequence charSequence, final String charsetName, final int n) {
        this(charSequence, Charset.forName(charsetName), n);
    }
    
    public CharSequenceInputStream(final CharSequence charSequence, final Charset charset) {
        this(charSequence, charset, 2048);
    }
    
    public CharSequenceInputStream(final CharSequence charSequence, final String s) {
        this(charSequence, s, 2048);
    }
    
    private void fillBuffer() {
        this.bbuf.compact();
        final CoderResult encode = this.encoder.encode(this.cbuf, this.bbuf, true);
        if (encode.isError()) {
            encode.throwException();
        }
        this.bbuf.flip();
    }
    
    @Override
    public int read(final byte[] dst, int n, int i) {
        if (dst == null) {
            throw new NullPointerException("Byte array is null");
        }
        if (i < 0 || n + i > dst.length) {
            throw new IndexOutOfBoundsException("Array Size=" + dst.length + ", offset=" + n + ", length=" + i);
        }
        if (i == 0) {
            return 0;
        }
        if (!this.bbuf.hasRemaining() && !this.cbuf.hasRemaining()) {
            return -1;
        }
        int n2 = 0;
        while (i > 0) {
            if (this.bbuf.hasRemaining()) {
                final int min = Math.min(this.bbuf.remaining(), i);
                this.bbuf.get(dst, n, min);
                n += min;
                i -= min;
                n2 += min;
            }
            else {
                this.fillBuffer();
                if (!this.bbuf.hasRemaining() && !this.cbuf.hasRemaining()) {
                    break;
                }
                continue;
            }
        }
        return (n2 == 0 && !this.cbuf.hasRemaining()) ? -1 : n2;
    }
    
    @Override
    public int read() {
        while (!this.bbuf.hasRemaining()) {
            this.fillBuffer();
            if (!this.bbuf.hasRemaining() && !this.cbuf.hasRemaining()) {
                return -1;
            }
        }
        return this.bbuf.get() & 0xFF;
    }
    
    @Override
    public int read(final byte[] array) {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public long skip(long n) {
        long n2;
        for (n2 = 0L; n > 0L && this.available() > 0; --n, ++n2) {
            this.read();
        }
        return n2;
    }
    
    @Override
    public int available() {
        return this.bbuf.remaining() + this.cbuf.remaining();
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public synchronized void mark(final int n) {
        this.mark_cbuf = this.cbuf.position();
        this.mark_bbuf = this.bbuf.position();
        this.cbuf.mark();
        this.bbuf.mark();
    }
    
    @Override
    public synchronized void reset() {
        if (this.mark_cbuf != -1) {
            if (this.cbuf.position() != 0) {
                this.encoder.reset();
                this.cbuf.rewind();
                this.bbuf.rewind();
                this.bbuf.limit(0);
                while (this.cbuf.position() < this.mark_cbuf) {
                    this.bbuf.rewind();
                    this.bbuf.limit(0);
                    this.fillBuffer();
                }
            }
            if (this.cbuf.position() != this.mark_cbuf) {
                throw new IllegalStateException("Unexpected CharBuffer postion: actual=" + this.cbuf.position() + " expected=" + this.mark_cbuf);
            }
            this.bbuf.position(this.mark_bbuf);
            this.mark_cbuf = -1;
            this.mark_bbuf = -1;
        }
    }
    
    @Override
    public boolean markSupported() {
        return true;
    }
}
