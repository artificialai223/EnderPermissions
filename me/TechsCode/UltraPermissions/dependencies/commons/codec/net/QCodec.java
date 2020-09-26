

package me.TechsCode.EnderPermissions.dependencies.commons.codec.net;

import me.TechsCode.EnderPermissions.dependencies.commons.codec.DecoderException;
import java.io.UnsupportedEncodingException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.EncoderException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.Charsets;
import java.util.BitSet;
import java.nio.charset.Charset;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringDecoder;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringEncoder;

public class QCodec extends RFC1522Codec implements StringEncoder, StringDecoder
{
    private final Charset charset;
    private static final BitSet PRINTABLE_CHARS;
    private static final byte SPACE = 32;
    private static final byte UNDERSCORE = 95;
    private boolean encodeBlanks;
    
    public QCodec() {
        this(Charsets.UTF_8);
    }
    
    public QCodec(final Charset charset) {
        this.encodeBlanks = false;
        this.charset = charset;
    }
    
    public QCodec(final String charsetName) {
        this(Charset.forName(charsetName));
    }
    
    @Override
    protected String getEncoding() {
        return "Q";
    }
    
    @Override
    protected byte[] doEncoding(final byte[] array) {
        if (array == null) {
            return null;
        }
        final byte[] encodeQuotedPrintable = QuotedPrintableCodec.encodeQuotedPrintable(QCodec.PRINTABLE_CHARS, array);
        if (this.encodeBlanks) {
            for (int i = 0; i < encodeQuotedPrintable.length; ++i) {
                if (encodeQuotedPrintable[i] == 32) {
                    encodeQuotedPrintable[i] = 95;
                }
            }
        }
        return encodeQuotedPrintable;
    }
    
    @Override
    protected byte[] doDecoding(final byte[] array) {
        if (array == null) {
            return null;
        }
        boolean b = false;
        for (int length = array.length, i = 0; i < length; ++i) {
            if (array[i] == 95) {
                b = true;
                break;
            }
        }
        if (b) {
            final byte[] array2 = new byte[array.length];
            for (int j = 0; j < array.length; ++j) {
                final byte b2 = array[j];
                if (b2 != 95) {
                    array2[j] = b2;
                }
                else {
                    array2[j] = 32;
                }
            }
            return QuotedPrintableCodec.decodeQuotedPrintable(array2);
        }
        return QuotedPrintableCodec.decodeQuotedPrintable(array);
    }
    
    public String encode(final String s, final Charset charset) {
        if (s == null) {
            return null;
        }
        return this.encodeText(s, charset);
    }
    
    public String encode(final String s, final String s2) {
        if (s == null) {
            return null;
        }
        try {
            return this.encodeText(s, s2);
        }
        catch (UnsupportedEncodingException ex) {
            throw new EncoderException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public String encode(final String s) {
        if (s == null) {
            return null;
        }
        return this.encode(s, this.getCharset());
    }
    
    @Override
    public String decode(final String s) {
        if (s == null) {
            return null;
        }
        try {
            return this.decodeText(s);
        }
        catch (UnsupportedEncodingException ex) {
            throw new DecoderException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public Object encode(final Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return this.encode((String)o);
        }
        throw new EncoderException("Objects of type " + o.getClass().getName() + " cannot be encoded using Q codec");
    }
    
    @Override
    public Object decode(final Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return this.decode((String)o);
        }
        throw new DecoderException("Objects of type " + o.getClass().getName() + " cannot be decoded using Q codec");
    }
    
    public Charset getCharset() {
        return this.charset;
    }
    
    public String getDefaultCharset() {
        return this.charset.name();
    }
    
    public boolean isEncodeBlanks() {
        return this.encodeBlanks;
    }
    
    public void setEncodeBlanks(final boolean encodeBlanks) {
        this.encodeBlanks = encodeBlanks;
    }
    
    static {
        (PRINTABLE_CHARS = new BitSet(256)).set(32);
        QCodec.PRINTABLE_CHARS.set(33);
        QCodec.PRINTABLE_CHARS.set(34);
        QCodec.PRINTABLE_CHARS.set(35);
        QCodec.PRINTABLE_CHARS.set(36);
        QCodec.PRINTABLE_CHARS.set(37);
        QCodec.PRINTABLE_CHARS.set(38);
        QCodec.PRINTABLE_CHARS.set(39);
        QCodec.PRINTABLE_CHARS.set(40);
        QCodec.PRINTABLE_CHARS.set(41);
        QCodec.PRINTABLE_CHARS.set(42);
        QCodec.PRINTABLE_CHARS.set(43);
        QCodec.PRINTABLE_CHARS.set(44);
        QCodec.PRINTABLE_CHARS.set(45);
        QCodec.PRINTABLE_CHARS.set(46);
        QCodec.PRINTABLE_CHARS.set(47);
        for (int i = 48; i <= 57; ++i) {
            QCodec.PRINTABLE_CHARS.set(i);
        }
        QCodec.PRINTABLE_CHARS.set(58);
        QCodec.PRINTABLE_CHARS.set(59);
        QCodec.PRINTABLE_CHARS.set(60);
        QCodec.PRINTABLE_CHARS.set(62);
        QCodec.PRINTABLE_CHARS.set(64);
        for (int j = 65; j <= 90; ++j) {
            QCodec.PRINTABLE_CHARS.set(j);
        }
        QCodec.PRINTABLE_CHARS.set(91);
        QCodec.PRINTABLE_CHARS.set(92);
        QCodec.PRINTABLE_CHARS.set(93);
        QCodec.PRINTABLE_CHARS.set(94);
        QCodec.PRINTABLE_CHARS.set(96);
        for (int k = 97; k <= 122; ++k) {
            QCodec.PRINTABLE_CHARS.set(k);
        }
        QCodec.PRINTABLE_CHARS.set(123);
        QCodec.PRINTABLE_CHARS.set(124);
        QCodec.PRINTABLE_CHARS.set(125);
        QCodec.PRINTABLE_CHARS.set(126);
    }
}
