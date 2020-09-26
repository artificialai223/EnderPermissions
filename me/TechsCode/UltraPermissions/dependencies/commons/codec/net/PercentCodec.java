

package me.TechsCode.EnderPermissions.dependencies.commons.codec.net;

import me.TechsCode.EnderPermissions.dependencies.commons.codec.EncoderException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.DecoderException;
import java.nio.ByteBuffer;
import java.util.BitSet;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.BinaryDecoder;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.BinaryEncoder;

public class PercentCodec implements BinaryEncoder, BinaryDecoder
{
    private final byte ESCAPE_CHAR = 37;
    private final BitSet alwaysEncodeChars;
    private final boolean plusForSpace;
    private int alwaysEncodeCharsMin;
    private int alwaysEncodeCharsMax;
    
    public PercentCodec() {
        this.alwaysEncodeChars = new BitSet();
        this.alwaysEncodeCharsMin = Integer.MAX_VALUE;
        this.alwaysEncodeCharsMax = Integer.MIN_VALUE;
        this.plusForSpace = false;
        this.insertAlwaysEncodeChar((byte)37);
    }
    
    public PercentCodec(final byte[] array, final boolean plusForSpace) {
        this.alwaysEncodeChars = new BitSet();
        this.alwaysEncodeCharsMin = Integer.MAX_VALUE;
        this.alwaysEncodeCharsMax = Integer.MIN_VALUE;
        this.plusForSpace = plusForSpace;
        this.insertAlwaysEncodeChars(array);
    }
    
    private void insertAlwaysEncodeChars(final byte[] array) {
        if (array != null) {
            for (int length = array.length, i = 0; i < length; ++i) {
                this.insertAlwaysEncodeChar(array[i]);
            }
        }
        this.insertAlwaysEncodeChar((byte)37);
    }
    
    private void insertAlwaysEncodeChar(final byte alwaysEncodeCharsMax) {
        this.alwaysEncodeChars.set(alwaysEncodeCharsMax);
        if (alwaysEncodeCharsMax < this.alwaysEncodeCharsMin) {
            this.alwaysEncodeCharsMin = alwaysEncodeCharsMax;
        }
        if (alwaysEncodeCharsMax > this.alwaysEncodeCharsMax) {
            this.alwaysEncodeCharsMax = alwaysEncodeCharsMax;
        }
    }
    
    @Override
    public byte[] encode(final byte[] array) {
        if (array == null) {
            return null;
        }
        final int expectedEncodingBytes = this.expectedEncodingBytes(array);
        final boolean b = expectedEncodingBytes != array.length;
        if (b || (this.plusForSpace && this.containsSpace(array))) {
            return this.doEncode(array, expectedEncodingBytes, b);
        }
        return array;
    }
    
    private byte[] doEncode(final byte[] array, final int capacity, final boolean b) {
        final ByteBuffer allocate = ByteBuffer.allocate(capacity);
        for (final byte b2 : array) {
            if (b && this.canEncode(b2)) {
                byte b3 = b2;
                if (b3 < 0) {
                    b3 += 256;
                }
                final char hexDigit = Utils.hexDigit(b3 >> 4);
                final char hexDigit2 = Utils.hexDigit(b3);
                allocate.put((byte)37);
                allocate.put((byte)hexDigit);
                allocate.put((byte)hexDigit2);
            }
            else if (this.plusForSpace && b2 == 32) {
                allocate.put((byte)43);
            }
            else {
                allocate.put(b2);
            }
        }
        return allocate.array();
    }
    
    private int expectedEncodingBytes(final byte[] array) {
        int n = 0;
        for (int length = array.length, i = 0; i < length; ++i) {
            n += (this.canEncode(array[i]) ? 3 : 1);
        }
        return n;
    }
    
    private boolean containsSpace(final byte[] array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            if (array[i] == 32) {
                return true;
            }
        }
        return false;
    }
    
    private boolean canEncode(final byte bitIndex) {
        return !this.isAsciiChar(bitIndex) || (this.inAlwaysEncodeCharsRange(bitIndex) && this.alwaysEncodeChars.get(bitIndex));
    }
    
    private boolean inAlwaysEncodeCharsRange(final byte b) {
        return b >= this.alwaysEncodeCharsMin && b <= this.alwaysEncodeCharsMax;
    }
    
    private boolean isAsciiChar(final byte b) {
        return b >= 0;
    }
    
    @Override
    public byte[] decode(final byte[] array) {
        if (array == null) {
            return null;
        }
        final ByteBuffer allocate = ByteBuffer.allocate(this.expectedDecodingBytes(array));
        for (int i = 0; i < array.length; ++i) {
            final byte b = array[i];
            if (b == 37) {
                try {
                    allocate.put((byte)((Utils.digit16(array[++i]) << 4) + Utils.digit16(array[++i])));
                    continue;
                }
                catch (ArrayIndexOutOfBoundsException ex) {
                    throw new DecoderException("Invalid percent decoding: ", ex);
                }
            }
            if (this.plusForSpace && b == 43) {
                allocate.put((byte)32);
            }
            else {
                allocate.put(b);
            }
        }
        return allocate.array();
    }
    
    private int expectedDecodingBytes(final byte[] array) {
        int n = 0;
        for (int i = 0; i < array.length; i += ((array[i] == 37) ? 3 : 1), ++n) {}
        return n;
    }
    
    @Override
    public Object encode(final Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof byte[]) {
            return this.encode((byte[])o);
        }
        throw new EncoderException("Objects of type " + o.getClass().getName() + " cannot be Percent encoded");
    }
    
    @Override
    public Object decode(final Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof byte[]) {
            return this.decode((byte[])o);
        }
        throw new DecoderException("Objects of type " + o.getClass().getName() + " cannot be Percent decoded");
    }
}
