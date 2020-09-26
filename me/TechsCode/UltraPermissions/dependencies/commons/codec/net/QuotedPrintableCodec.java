

package me.TechsCode.EnderPermissions.dependencies.commons.codec.net;

import me.TechsCode.EnderPermissions.dependencies.commons.codec.EncoderException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.StringUtils;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.DecoderException;
import java.io.ByteArrayOutputStream;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.Charsets;
import java.util.BitSet;
import java.nio.charset.Charset;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringDecoder;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringEncoder;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.BinaryDecoder;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.BinaryEncoder;

public class QuotedPrintableCodec implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder
{
    private final Charset charset;
    private final boolean strict;
    private static final BitSet PRINTABLE_CHARS;
    private static final byte ESCAPE_CHAR = 61;
    private static final byte TAB = 9;
    private static final byte SPACE = 32;
    private static final byte CR = 13;
    private static final byte LF = 10;
    private static final int SAFE_LENGTH = 73;
    
    public QuotedPrintableCodec() {
        this(Charsets.UTF_8, false);
    }
    
    public QuotedPrintableCodec(final boolean b) {
        this(Charsets.UTF_8, b);
    }
    
    public QuotedPrintableCodec(final Charset charset) {
        this(charset, false);
    }
    
    public QuotedPrintableCodec(final Charset charset, final boolean strict) {
        this.charset = charset;
        this.strict = strict;
    }
    
    public QuotedPrintableCodec(final String charsetName) {
        this(Charset.forName(charsetName), false);
    }
    
    private static final int encodeQuotedPrintable(final int n, final ByteArrayOutputStream byteArrayOutputStream) {
        byteArrayOutputStream.write(61);
        final char hexDigit = Utils.hexDigit(n >> 4);
        final char hexDigit2 = Utils.hexDigit(n);
        byteArrayOutputStream.write(hexDigit);
        byteArrayOutputStream.write(hexDigit2);
        return 3;
    }
    
    private static int getUnsignedOctet(final int n, final byte[] array) {
        int n2 = array[n];
        if (n2 < 0) {
            n2 += 256;
        }
        return n2;
    }
    
    private static int encodeByte(final int b, final boolean b2, final ByteArrayOutputStream byteArrayOutputStream) {
        if (b2) {
            return encodeQuotedPrintable(b, byteArrayOutputStream);
        }
        byteArrayOutputStream.write(b);
        return 1;
    }
    
    private static boolean isWhitespace(final int n) {
        return n == 32 || n == 9;
    }
    
    public static final byte[] encodeQuotedPrintable(final BitSet set, final byte[] array) {
        return encodeQuotedPrintable(set, array, false);
    }
    
    public static final byte[] encodeQuotedPrintable(BitSet printable_CHARS, final byte[] array, final boolean b) {
        if (array == null) {
            return null;
        }
        if (printable_CHARS == null) {
            printable_CHARS = QuotedPrintableCodec.PRINTABLE_CHARS;
        }
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (b) {
            int n = 1;
            for (int i = 0; i < array.length - 3; ++i) {
                final int unsignedOctet = getUnsignedOctet(i, array);
                if (n < 73) {
                    n += encodeByte(unsignedOctet, !printable_CHARS.get(unsignedOctet), byteArrayOutputStream);
                }
                else {
                    encodeByte(unsignedOctet, !printable_CHARS.get(unsignedOctet) || isWhitespace(unsignedOctet), byteArrayOutputStream);
                    byteArrayOutputStream.write(61);
                    byteArrayOutputStream.write(13);
                    byteArrayOutputStream.write(10);
                    n = 1;
                }
            }
            final int unsignedOctet2 = getUnsignedOctet(array.length - 3, array);
            if (n + encodeByte(unsignedOctet2, !printable_CHARS.get(unsignedOctet2) || (isWhitespace(unsignedOctet2) && n > 68), byteArrayOutputStream) > 71) {
                byteArrayOutputStream.write(61);
                byteArrayOutputStream.write(13);
                byteArrayOutputStream.write(10);
            }
            for (int j = array.length - 2; j < array.length; ++j) {
                final int unsignedOctet3 = getUnsignedOctet(j, array);
                encodeByte(unsignedOctet3, !printable_CHARS.get(unsignedOctet3) || (j > array.length - 2 && isWhitespace(unsignedOctet3)), byteArrayOutputStream);
            }
        }
        else {
            for (int n2 : array) {
                if (n2 < 0) {
                    n2 += 256;
                }
                if (printable_CHARS.get(n2)) {
                    byteArrayOutputStream.write(n2);
                }
                else {
                    encodeQuotedPrintable(n2, byteArrayOutputStream);
                }
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    public static final byte[] decodeQuotedPrintable(final byte[] array) {
        if (array == null) {
            return null;
        }
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i = 0; i < array.length; ++i) {
            final byte b = array[i];
            if (b == 61) {
                try {
                    if (array[++i] == 13) {
                        continue;
                    }
                    byteArrayOutputStream.write((char)((Utils.digit16(array[i]) << 4) + Utils.digit16(array[++i])));
                    continue;
                }
                catch (ArrayIndexOutOfBoundsException ex) {
                    throw new DecoderException("Invalid quoted-printable encoding", ex);
                }
            }
            if (b != 13 && b != 10) {
                byteArrayOutputStream.write(b);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    @Override
    public byte[] encode(final byte[] array) {
        return encodeQuotedPrintable(QuotedPrintableCodec.PRINTABLE_CHARS, array, this.strict);
    }
    
    @Override
    public byte[] decode(final byte[] array) {
        return decodeQuotedPrintable(array);
    }
    
    @Override
    public String encode(final String s) {
        return this.encode(s, this.getCharset());
    }
    
    public String decode(final String s, final Charset charset) {
        if (s == null) {
            return null;
        }
        return new String(this.decode(StringUtils.getBytesUsAscii(s)), charset);
    }
    
    public String decode(final String s, final String charsetName) {
        if (s == null) {
            return null;
        }
        return new String(this.decode(StringUtils.getBytesUsAscii(s)), charsetName);
    }
    
    @Override
    public String decode(final String s) {
        return this.decode(s, this.getCharset());
    }
    
    @Override
    public Object encode(final Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof byte[]) {
            return this.encode((byte[])o);
        }
        if (o instanceof String) {
            return this.encode((String)o);
        }
        throw new EncoderException("Objects of type " + o.getClass().getName() + " cannot be quoted-printable encoded");
    }
    
    @Override
    public Object decode(final Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof byte[]) {
            return this.decode((byte[])o);
        }
        if (o instanceof String) {
            return this.decode((String)o);
        }
        throw new DecoderException("Objects of type " + o.getClass().getName() + " cannot be quoted-printable decoded");
    }
    
    public Charset getCharset() {
        return this.charset;
    }
    
    public String getDefaultCharset() {
        return this.charset.name();
    }
    
    public String encode(final String s, final Charset charset) {
        if (s == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(this.encode(s.getBytes(charset)));
    }
    
    public String encode(final String s, final String charsetName) {
        if (s == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(this.encode(s.getBytes(charsetName)));
    }
    
    static {
        PRINTABLE_CHARS = new BitSet(256);
        for (int i = 33; i <= 60; ++i) {
            QuotedPrintableCodec.PRINTABLE_CHARS.set(i);
        }
        for (int j = 62; j <= 126; ++j) {
            QuotedPrintableCodec.PRINTABLE_CHARS.set(j);
        }
        QuotedPrintableCodec.PRINTABLE_CHARS.set(9);
        QuotedPrintableCodec.PRINTABLE_CHARS.set(32);
    }
}
