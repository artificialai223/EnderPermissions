

package me.TechsCode.EnderPermissions.dependencies.commons.codec.net;

import me.TechsCode.EnderPermissions.dependencies.commons.codec.DecoderException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.StringUtils;
import java.nio.charset.Charset;

abstract class RFC1522Codec
{
    protected static final char SEP = '?';
    protected static final String POSTFIX = "?=";
    protected static final String PREFIX = "=?";
    
    protected String encodeText(final String s, final Charset charset) {
        if (s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("=?");
        sb.append(charset);
        sb.append('?');
        sb.append(this.getEncoding());
        sb.append('?');
        sb.append(StringUtils.newStringUsAscii(this.doEncoding(s.getBytes(charset))));
        sb.append("?=");
        return sb.toString();
    }
    
    protected String encodeText(final String s, final String charsetName) {
        if (s == null) {
            return null;
        }
        return this.encodeText(s, Charset.forName(charsetName));
    }
    
    protected String decodeText(final String s) {
        if (s == null) {
            return null;
        }
        if (!s.startsWith("=?") || !s.endsWith("?=")) {
            throw new DecoderException("RFC 1522 violation: malformed encoded content");
        }
        final int n = s.length() - 2;
        final int n2 = 2;
        final int index = s.indexOf(63, n2);
        if (index == n) {
            throw new DecoderException("RFC 1522 violation: charset token not found");
        }
        final String substring = s.substring(n2, index);
        if (substring.equals("")) {
            throw new DecoderException("RFC 1522 violation: charset not specified");
        }
        final int n3 = index + 1;
        final int index2 = s.indexOf(63, n3);
        if (index2 == n) {
            throw new DecoderException("RFC 1522 violation: encoding token not found");
        }
        final String substring2 = s.substring(n3, index2);
        if (!this.getEncoding().equalsIgnoreCase(substring2)) {
            throw new DecoderException("This codec cannot decode " + substring2 + " encoded content");
        }
        final int n4 = index2 + 1;
        return new String(this.doDecoding(StringUtils.getBytesUsAscii(s.substring(n4, s.indexOf(63, n4)))), substring);
    }
    
    protected abstract String getEncoding();
    
    protected abstract byte[] doEncoding(final byte[] p0);
    
    protected abstract byte[] doDecoding(final byte[] p0);
}
