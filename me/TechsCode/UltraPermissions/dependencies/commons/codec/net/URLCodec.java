

package me.TechsCode.EnderPermissions.dependencies.commons.codec.net;

import java.io.UnsupportedEncodingException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.EncoderException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.StringUtils;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.DecoderException;
import java.io.ByteArrayOutputStream;
import java.util.BitSet;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringDecoder;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringEncoder;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.BinaryDecoder;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.BinaryEncoder;

public class URLCodec implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder
{
    @Deprecated
    protected volatile String charset;
    protected static final byte ESCAPE_CHAR = 37;
    @Deprecated
    protected static final BitSet WWW_FORM_URL;
    private static final BitSet WWW_FORM_URL_SAFE;
    
    public URLCodec() {
        this("UTF-8");
    }
    
    public URLCodec(final String charset) {
        this.charset = charset;
    }
    
    public static final byte[] encodeUrl(BitSet www_FORM_URL_SAFE, final byte[] array) {
        if (array == null) {
            return null;
        }
        if (www_FORM_URL_SAFE == null) {
            www_FORM_URL_SAFE = URLCodec.WWW_FORM_URL_SAFE;
        }
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int n : array) {
            if (n < 0) {
                n += 256;
            }
            if (www_FORM_URL_SAFE.get(n)) {
                if (n == 32) {
                    n = 43;
                }
                byteArrayOutputStream.write(n);
            }
            else {
                byteArrayOutputStream.write(37);
                final char hexDigit = Utils.hexDigit(n >> 4);
                final char hexDigit2 = Utils.hexDigit(n);
                byteArrayOutputStream.write(hexDigit);
                byteArrayOutputStream.write(hexDigit2);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    public static final byte[] decodeUrl(final byte[] array) {
        if (array == null) {
            return null;
        }
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i = 0; i < array.length; ++i) {
            final byte b = array[i];
            if (b == 43) {
                byteArrayOutputStream.write(32);
            }
            else {
                if (b == 37) {
                    try {
                        byteArrayOutputStream.write((char)((Utils.digit16(array[++i]) << 4) + Utils.digit16(array[++i])));
                        continue;
                    }
                    catch (ArrayIndexOutOfBoundsException ex) {
                        throw new DecoderException("Invalid URL encoding: ", ex);
                    }
                }
                byteArrayOutputStream.write(b);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    @Override
    public byte[] encode(final byte[] array) {
        return encodeUrl(URLCodec.WWW_FORM_URL_SAFE, array);
    }
    
    @Override
    public byte[] decode(final byte[] array) {
        return decodeUrl(array);
    }
    
    public String encode(final String s, final String charsetName) {
        if (s == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(this.encode(s.getBytes(charsetName)));
    }
    
    @Override
    public String encode(final String s) {
        if (s == null) {
            return null;
        }
        try {
            return this.encode(s, this.getDefaultCharset());
        }
        catch (UnsupportedEncodingException ex) {
            throw new EncoderException(ex.getMessage(), ex);
        }
    }
    
    public String decode(final String s, final String charsetName) {
        if (s == null) {
            return null;
        }
        return new String(this.decode(StringUtils.getBytesUsAscii(s)), charsetName);
    }
    
    @Override
    public String decode(final String s) {
        if (s == null) {
            return null;
        }
        try {
            return this.decode(s, this.getDefaultCharset());
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
        if (o instanceof byte[]) {
            return this.encode((byte[])o);
        }
        if (o instanceof String) {
            return this.encode((String)o);
        }
        throw new EncoderException("Objects of type " + o.getClass().getName() + " cannot be URL encoded");
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
        throw new DecoderException("Objects of type " + o.getClass().getName() + " cannot be URL decoded");
    }
    
    public String getDefaultCharset() {
        return this.charset;
    }
    
    @Deprecated
    public String getEncoding() {
        return this.charset;
    }
    
    static {
        WWW_FORM_URL_SAFE = new BitSet(256);
        for (int i = 97; i <= 122; ++i) {
            URLCodec.WWW_FORM_URL_SAFE.set(i);
        }
        for (int j = 65; j <= 90; ++j) {
            URLCodec.WWW_FORM_URL_SAFE.set(j);
        }
        for (int k = 48; k <= 57; ++k) {
            URLCodec.WWW_FORM_URL_SAFE.set(k);
        }
        URLCodec.WWW_FORM_URL_SAFE.set(45);
        URLCodec.WWW_FORM_URL_SAFE.set(95);
        URLCodec.WWW_FORM_URL_SAFE.set(46);
        URLCodec.WWW_FORM_URL_SAFE.set(42);
        URLCodec.WWW_FORM_URL_SAFE.set(32);
        WWW_FORM_URL = (BitSet)URLCodec.WWW_FORM_URL_SAFE.clone();
    }
}
