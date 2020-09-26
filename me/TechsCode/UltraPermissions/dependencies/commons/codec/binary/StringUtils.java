

package me.TechsCode.EnderPermissions.dependencies.commons.codec.binary;

import java.io.UnsupportedEncodingException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.Charsets;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class StringUtils
{
    public static boolean equals(final CharSequence charSequence, final CharSequence obj) {
        if (charSequence == obj) {
            return true;
        }
        if (charSequence == null || obj == null) {
            return false;
        }
        if (charSequence instanceof String && obj instanceof String) {
            return charSequence.equals(obj);
        }
        return charSequence.length() == obj.length() && CharSequenceUtils.regionMatches(charSequence, false, 0, obj, 0, charSequence.length());
    }
    
    private static byte[] getBytes(final String s, final Charset charset) {
        if (s == null) {
            return null;
        }
        return s.getBytes(charset);
    }
    
    private static ByteBuffer getByteBuffer(final String s, final Charset charset) {
        if (s == null) {
            return null;
        }
        return ByteBuffer.wrap(s.getBytes(charset));
    }
    
    public static ByteBuffer getByteBufferUtf8(final String s) {
        return getByteBuffer(s, Charsets.UTF_8);
    }
    
    public static byte[] getBytesIso8859_1(final String s) {
        return getBytes(s, Charsets.ISO_8859_1);
    }
    
    public static byte[] getBytesUnchecked(final String s, final String charsetName) {
        if (s == null) {
            return null;
        }
        try {
            return s.getBytes(charsetName);
        }
        catch (UnsupportedEncodingException ex) {
            throw newIllegalStateException(charsetName, ex);
        }
    }
    
    public static byte[] getBytesUsAscii(final String s) {
        return getBytes(s, Charsets.US_ASCII);
    }
    
    public static byte[] getBytesUtf16(final String s) {
        return getBytes(s, Charsets.UTF_16);
    }
    
    public static byte[] getBytesUtf16Be(final String s) {
        return getBytes(s, Charsets.UTF_16BE);
    }
    
    public static byte[] getBytesUtf16Le(final String s) {
        return getBytes(s, Charsets.UTF_16LE);
    }
    
    public static byte[] getBytesUtf8(final String s) {
        return getBytes(s, Charsets.UTF_8);
    }
    
    private static IllegalStateException newIllegalStateException(final String str, final UnsupportedEncodingException obj) {
        return new IllegalStateException(str + ": " + obj);
    }
    
    private static String newString(final byte[] bytes, final Charset charset) {
        return (bytes == null) ? null : new String(bytes, charset);
    }
    
    public static String newString(final byte[] bytes, final String charsetName) {
        if (bytes == null) {
            return null;
        }
        try {
            return new String(bytes, charsetName);
        }
        catch (UnsupportedEncodingException ex) {
            throw newIllegalStateException(charsetName, ex);
        }
    }
    
    public static String newStringIso8859_1(final byte[] array) {
        return newString(array, Charsets.ISO_8859_1);
    }
    
    public static String newStringUsAscii(final byte[] array) {
        return newString(array, Charsets.US_ASCII);
    }
    
    public static String newStringUtf16(final byte[] array) {
        return newString(array, Charsets.UTF_16);
    }
    
    public static String newStringUtf16Be(final byte[] array) {
        return newString(array, Charsets.UTF_16BE);
    }
    
    public static String newStringUtf16Le(final byte[] array) {
        return newString(array, Charsets.UTF_16LE);
    }
    
    public static String newStringUtf8(final byte[] array) {
        return newString(array, Charsets.UTF_8);
    }
}
