

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.nio.charset.CodingErrorAction;
import java.nio.charset.Charset;
import java.nio.charset.CoderResult;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.io.Reader;
import java.io.InputStream;

public class ReaderInputStream extends InputStream
{
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final Reader reader;
    private final CharsetEncoder encoder;
    private final CharBuffer encoderIn;
    private final ByteBuffer encoderOut;
    private CoderResult lastCoderResult;
    private boolean endOfInput;
    
    public ReaderInputStream(final Reader reader, final CharsetEncoder charsetEncoder) {
        this(reader, charsetEncoder, 1024);
    }
    
    public ReaderInputStream(final Reader reader, final CharsetEncoder encoder, final int capacity) {
        this.reader = reader;
        this.encoder = encoder;
        (this.encoderIn = CharBuffer.allocate(capacity)).flip();
        (this.encoderOut = ByteBuffer.allocate(128)).flip();
    }
    
    public ReaderInputStream(final Reader reader, final Charset charset, final int n) {
        this(reader, charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE), n);
    }
    
    public ReaderInputStream(final Reader reader, final Charset charset) {
        this(reader, charset, 1024);
    }
    
    public ReaderInputStream(final Reader reader, final String charsetName, final int n) {
        this(reader, Charset.forName(charsetName), n);
    }
    
    public ReaderInputStream(final Reader reader, final String s) {
        this(reader, s, 1024);
    }
    
    @Deprecated
    public ReaderInputStream(final Reader reader) {
        this(reader, Charset.defaultCharset());
    }
    
    private void fillBuffer() {
        if (!this.endOfInput && (this.lastCoderResult == null || this.lastCoderResult.isUnderflow())) {
            this.encoderIn.compact();
            final int position = this.encoderIn.position();
            final int read = this.reader.read(this.encoderIn.array(), position, this.encoderIn.remaining());
            if (read == -1) {
                this.endOfInput = true;
            }
            else {
                this.encoderIn.position(position + read);
            }
            this.encoderIn.flip();
        }
        this.encoderOut.compact();
        this.lastCoderResult = this.encoder.encode(this.encoderIn, this.encoderOut, this.endOfInput);
        this.encoderOut.flip();
    }
    
    @Override
    public int read(final byte[] dst, int n, int i) {
        if (dst == null) {
            throw new NullPointerException("Byte array must not be null");
        }
        if (i < 0 || n < 0 || n + i > dst.length) {
            throw new IndexOutOfBoundsException("Array Size=" + dst.length + ", offset=" + n + ", length=" + i);
        }
        int n2 = 0;
        if (i == 0) {
            return 0;
        }
        while (i > 0) {
            if (this.encoderOut.hasRemaining()) {
                final int min = Math.min(this.encoderOut.remaining(), i);
                this.encoderOut.get(dst, n, min);
                n += min;
                i -= min;
                n2 += min;
            }
            else {
                this.fillBuffer();
                if (this.endOfInput && !this.encoderOut.hasRemaining()) {
                    break;
                }
                continue;
            }
        }
        return (n2 == 0 && this.endOfInput) ? -1 : n2;
    }
    
    @Override
    public int read(final byte[] array) {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public int read() {
        while (!this.encoderOut.hasRemaining()) {
            this.fillBuffer();
            if (this.endOfInput && !this.encoderOut.hasRemaining()) {
                return -1;
            }
        }
        return this.encoderOut.get() & 0xFF;
    }
    
    @Override
    public void close() {
        this.reader.close();
    }
}
