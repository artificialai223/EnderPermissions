

package me.TechsCode.EnderPermissions.dependencies.commons.codec.net;

import me.TechsCode.EnderPermissions.dependencies.commons.codec.DecoderException;
import java.io.UnsupportedEncodingException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.EncoderException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.Base64;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.Charsets;
import java.nio.charset.Charset;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringDecoder;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringEncoder;

public class BCodec extends RFC1522Codec implements StringEncoder, StringDecoder
{
    private final Charset charset;
    
    public BCodec() {
        this(Charsets.UTF_8);
    }
    
    public BCodec(final Charset charset) {
        this.charset = charset;
    }
    
    public BCodec(final String charsetName) {
        this(Charset.forName(charsetName));
    }
    
    @Override
    protected String getEncoding() {
        return "B";
    }
    
    @Override
    protected byte[] doEncoding(final byte[] array) {
        if (array == null) {
            return null;
        }
        return Base64.encodeBase64(array);
    }
    
    @Override
    protected byte[] doDecoding(final byte[] array) {
        if (array == null) {
            return null;
        }
        return Base64.decodeBase64(array);
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
        throw new EncoderException("Objects of type " + o.getClass().getName() + " cannot be encoded using BCodec");
    }
    
    @Override
    public Object decode(final Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return this.decode((String)o);
        }
        throw new DecoderException("Objects of type " + o.getClass().getName() + " cannot be decoded using BCodec");
    }
    
    public Charset getCharset() {
        return this.charset;
    }
    
    public String getDefaultCharset() {
        return this.charset.name();
    }
}
