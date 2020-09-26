

package me.TechsCode.EnderPermissions.dependencies.commons.codec.binary;

import me.TechsCode.EnderPermissions.dependencies.commons.codec.Charsets;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.EncoderException;
import java.nio.ByteBuffer;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.DecoderException;
import java.nio.charset.Charset;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.BinaryDecoder;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.BinaryEncoder;

public class Hex implements BinaryEncoder, BinaryDecoder
{
    public static final Charset DEFAULT_CHARSET;
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";
    private static final char[] DIGITS_LOWER;
    private static final char[] DIGITS_UPPER;
    private final Charset charset;
    
    public static byte[] decodeHex(final String s) {
        return decodeHex(s.toCharArray());
    }
    
    public static byte[] decodeHex(final char[] array) {
        final int length = array.length;
        if ((length & 0x1) != 0x0) {
            throw new DecoderException("Odd number of characters.");
        }
        final byte[] array2 = new byte[length >> 1];
        int n2;
        int n3;
        for (int n = 0, i = 0; i < length; ++i, n3 = (n2 | toDigit(array[i], i)), ++i, array2[n] = (byte)(n3 & 0xFF), ++n) {
            n2 = toDigit(array[i], i) << 4;
        }
        return array2;
    }
    
    public static char[] encodeHex(final byte[] array) {
        return encodeHex(array, true);
    }
    
    public static char[] encodeHex(final ByteBuffer byteBuffer) {
        return encodeHex(byteBuffer, true);
    }
    
    public static char[] encodeHex(final byte[] array, final boolean b) {
        return encodeHex(array, b ? Hex.DIGITS_LOWER : Hex.DIGITS_UPPER);
    }
    
    public static char[] encodeHex(final ByteBuffer byteBuffer, final boolean b) {
        return encodeHex(byteBuffer, b ? Hex.DIGITS_LOWER : Hex.DIGITS_UPPER);
    }
    
    protected static char[] encodeHex(final byte[] array, final char[] array2) {
        final int length = array.length;
        final char[] array3 = new char[length << 1];
        int i = 0;
        int n = 0;
        while (i < length) {
            array3[n++] = array2[(0xF0 & array[i]) >>> 4];
            array3[n++] = array2[0xF & array[i]];
            ++i;
        }
        return array3;
    }
    
    protected static char[] encodeHex(final ByteBuffer byteBuffer, final char[] array) {
        return encodeHex(byteBuffer.array(), array);
    }
    
    public static String encodeHexString(final byte[] array) {
        return new String(encodeHex(array));
    }
    
    public static String encodeHexString(final byte[] array, final boolean b) {
        return new String(encodeHex(array, b));
    }
    
    public static String encodeHexString(final ByteBuffer byteBuffer) {
        return new String(encodeHex(byteBuffer));
    }
    
    public static String encodeHexString(final ByteBuffer byteBuffer, final boolean b) {
        return new String(encodeHex(byteBuffer, b));
    }
    
    protected static int toDigit(final char c, final int i) {
        final int digit = Character.digit(c, 16);
        if (digit == -1) {
            throw new DecoderException("Illegal hexadecimal character " + c + " at index " + i);
        }
        return digit;
    }
    
    public Hex() {
        this.charset = Hex.DEFAULT_CHARSET;
    }
    
    public Hex(final Charset charset) {
        this.charset = charset;
    }
    
    public Hex(final String charsetName) {
        this(Charset.forName(charsetName));
    }
    
    @Override
    public byte[] decode(final byte[] bytes) {
        return decodeHex(new String(bytes, this.getCharset()).toCharArray());
    }
    
    public byte[] decode(final ByteBuffer byteBuffer) {
        return decodeHex(new String(byteBuffer.array(), this.getCharset()).toCharArray());
    }
    
    @Override
    public Object decode(final Object o) {
        if (o instanceof String) {
            return this.decode(((String)o).toCharArray());
        }
        if (o instanceof byte[]) {
            return this.decode((byte[])o);
        }
        if (o instanceof ByteBuffer) {
            return this.decode((ByteBuffer)o);
        }
        try {
            return decodeHex((char[])o);
        }
        catch (ClassCastException ex) {
            throw new DecoderException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public byte[] encode(final byte[] array) {
        return encodeHexString(array).getBytes(this.getCharset());
    }
    
    public byte[] encode(final ByteBuffer byteBuffer) {
        return encodeHexString(byteBuffer).getBytes(this.getCharset());
    }
    
    @Override
    public Object encode(final Object o) {
        byte[] array;
        if (o instanceof String) {
            array = ((String)o).getBytes(this.getCharset());
        }
        else if (o instanceof ByteBuffer) {
            array = ((ByteBuffer)o).array();
        }
        else {
            try {
                array = (byte[])o;
            }
            catch (ClassCastException ex) {
                throw new EncoderException(ex.getMessage(), ex);
            }
        }
        return encodeHex(array);
    }
    
    public Charset getCharset() {
        return this.charset;
    }
    
    public String getCharsetName() {
        return this.charset.name();
    }
    
    @Override
    public String toString() {
        return super.toString() + "[charsetName=" + this.charset + "]";
    }
    
    static {
        DEFAULT_CHARSET = Charsets.UTF_8;
        DIGITS_LOWER = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        DIGITS_UPPER = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    }
}
