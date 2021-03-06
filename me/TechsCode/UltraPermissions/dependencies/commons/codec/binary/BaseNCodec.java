

package me.TechsCode.EnderPermissions.dependencies.commons.codec.binary;

import java.util.Arrays;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.DecoderException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.EncoderException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.BinaryDecoder;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.BinaryEncoder;

public abstract class BaseNCodec implements BinaryEncoder, BinaryDecoder
{
    static final int EOF = -1;
    public static final int MIME_CHUNK_SIZE = 76;
    public static final int PEM_CHUNK_SIZE = 64;
    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    protected static final int MASK_8BITS = 255;
    protected static final byte PAD_DEFAULT = 61;
    @Deprecated
    protected final byte PAD = 61;
    protected final byte pad;
    private final int unencodedBlockSize;
    private final int encodedBlockSize;
    protected final int lineLength;
    private final int chunkSeparatorLength;
    
    protected BaseNCodec(final int n, final int n2, final int n3, final int n4) {
        this(n, n2, n3, n4, (byte)61);
    }
    
    protected BaseNCodec(final int unencodedBlockSize, final int encodedBlockSize, final int n, final int chunkSeparatorLength, final byte pad) {
        this.unencodedBlockSize = unencodedBlockSize;
        this.encodedBlockSize = encodedBlockSize;
        this.lineLength = ((n > 0 && chunkSeparatorLength > 0) ? (n / encodedBlockSize * encodedBlockSize) : 0);
        this.chunkSeparatorLength = chunkSeparatorLength;
        this.pad = pad;
    }
    
    boolean hasData(final Context context) {
        return context.buffer != null;
    }
    
    int available(final Context context) {
        return (context.buffer != null) ? (context.pos - context.readPos) : 0;
    }
    
    protected int getDefaultBufferSize() {
        return 8192;
    }
    
    private byte[] resizeBuffer(final Context context) {
        if (context.buffer == null) {
            context.buffer = new byte[this.getDefaultBufferSize()];
            context.pos = 0;
            context.readPos = 0;
        }
        else {
            final byte[] buffer = new byte[context.buffer.length * 2];
            System.arraycopy(context.buffer, 0, buffer, 0, context.buffer.length);
            context.buffer = buffer;
        }
        return context.buffer;
    }
    
    protected byte[] ensureBufferSize(final int n, final Context context) {
        if (context.buffer == null || context.buffer.length < context.pos + n) {
            return this.resizeBuffer(context);
        }
        return context.buffer;
    }
    
    int readResults(final byte[] array, final int n, final int b, final Context context) {
        if (context.buffer != null) {
            final int min = Math.min(this.available(context), b);
            System.arraycopy(context.buffer, context.readPos, array, n, min);
            context.readPos += min;
            if (context.readPos >= context.pos) {
                context.buffer = null;
            }
            return min;
        }
        return context.eof ? -1 : 0;
    }
    
    protected static boolean isWhiteSpace(final byte b) {
        switch (b) {
            case 9:
            case 10:
            case 13:
            case 32: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    @Override
    public Object encode(final Object o) {
        if (!(o instanceof byte[])) {
            throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
        }
        return this.encode((byte[])o);
    }
    
    public String encodeToString(final byte[] array) {
        return StringUtils.newStringUtf8(this.encode(array));
    }
    
    public String encodeAsString(final byte[] array) {
        return StringUtils.newStringUtf8(this.encode(array));
    }
    
    @Override
    public Object decode(final Object o) {
        if (o instanceof byte[]) {
            return this.decode((byte[])o);
        }
        if (o instanceof String) {
            return this.decode((String)o);
        }
        throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
    }
    
    public byte[] decode(final String s) {
        return this.decode(StringUtils.getBytesUtf8(s));
    }
    
    @Override
    public byte[] decode(final byte[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        final Context context = new Context();
        this.decode(array, 0, array.length, context);
        this.decode(array, 0, -1, context);
        final byte[] array2 = new byte[context.pos];
        this.readResults(array2, 0, array2.length, context);
        return array2;
    }
    
    @Override
    public byte[] encode(final byte[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        return this.encode(array, 0, array.length);
    }
    
    public byte[] encode(final byte[] array, final int n, final int n2) {
        if (array == null || array.length == 0) {
            return array;
        }
        final Context context = new Context();
        this.encode(array, n, n2, context);
        this.encode(array, n, -1, context);
        final byte[] array2 = new byte[context.pos - context.readPos];
        this.readResults(array2, 0, array2.length, context);
        return array2;
    }
    
    abstract void encode(final byte[] p0, final int p1, final int p2, final Context p3);
    
    abstract void decode(final byte[] p0, final int p1, final int p2, final Context p3);
    
    protected abstract boolean isInAlphabet(final byte p0);
    
    public boolean isInAlphabet(final byte[] array, final boolean b) {
        for (final byte b2 : array) {
            if (!this.isInAlphabet(b2) && (!b || (b2 != this.pad && !isWhiteSpace(b2)))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isInAlphabet(final String s) {
        return this.isInAlphabet(StringUtils.getBytesUtf8(s), true);
    }
    
    protected boolean containsAlphabetOrPad(final byte[] array) {
        if (array == null) {
            return false;
        }
        for (final byte b : array) {
            if (this.pad == b || this.isInAlphabet(b)) {
                return true;
            }
        }
        return false;
    }
    
    public long getEncodedLength(final byte[] array) {
        long n = (array.length + this.unencodedBlockSize - 1) / this.unencodedBlockSize * (long)this.encodedBlockSize;
        if (this.lineLength > 0) {
            n += (n + this.lineLength - 1L) / this.lineLength * this.chunkSeparatorLength;
        }
        return n;
    }
    
    static class Context
    {
        int ibitWorkArea;
        long lbitWorkArea;
        byte[] buffer;
        int pos;
        int readPos;
        boolean eof;
        int currentLinePos;
        int modulus;
        
        @Override
        public String toString() {
            return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, ibitWorkArea=%s, lbitWorkArea=%s, modulus=%s, pos=%s, readPos=%s]", this.getClass().getSimpleName(), Arrays.toString(this.buffer), this.currentLinePos, this.eof, this.ibitWorkArea, this.lbitWorkArea, this.modulus, this.pos, this.readPos);
        }
    }
}
