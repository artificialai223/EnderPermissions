

package me.TechsCode.EnderPermissions.dependencies.commons.io;

import java.io.Serializable;

public class ByteOrderMark implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static final ByteOrderMark UTF_8;
    public static final ByteOrderMark UTF_16BE;
    public static final ByteOrderMark UTF_16LE;
    public static final ByteOrderMark UTF_32BE;
    public static final ByteOrderMark UTF_32LE;
    public static final char UTF_BOM = '\ufeff';
    private final String charsetName;
    private final int[] bytes;
    
    public ByteOrderMark(final String charsetName, final int... array) {
        if (charsetName == null || charsetName.isEmpty()) {
            throw new IllegalArgumentException("No charsetName specified");
        }
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("No bytes specified");
        }
        this.charsetName = charsetName;
        System.arraycopy(array, 0, this.bytes = new int[array.length], 0, array.length);
    }
    
    public String getCharsetName() {
        return this.charsetName;
    }
    
    public int length() {
        return this.bytes.length;
    }
    
    public int get(final int n) {
        return this.bytes[n];
    }
    
    public byte[] getBytes() {
        final byte[] array = new byte[this.bytes.length];
        for (int i = 0; i < this.bytes.length; ++i) {
            array[i] = (byte)this.bytes[i];
        }
        return array;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof ByteOrderMark)) {
            return false;
        }
        final ByteOrderMark byteOrderMark = (ByteOrderMark)o;
        if (this.bytes.length != byteOrderMark.length()) {
            return false;
        }
        for (int i = 0; i < this.bytes.length; ++i) {
            if (this.bytes[i] != byteOrderMark.get(i)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hashCode = this.getClass().hashCode();
        final int[] bytes = this.bytes;
        for (int length = bytes.length, i = 0; i < length; ++i) {
            hashCode += bytes[i];
        }
        return hashCode;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append('[');
        sb.append(this.charsetName);
        sb.append(": ");
        for (int i = 0; i < this.bytes.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("0x");
            sb.append(Integer.toHexString(0xFF & this.bytes[i]).toUpperCase());
        }
        sb.append(']');
        return sb.toString();
    }
    
    static {
        UTF_8 = new ByteOrderMark("UTF-8", new int[] { 239, 187, 191 });
        UTF_16BE = new ByteOrderMark("UTF-16BE", new int[] { 254, 255 });
        UTF_16LE = new ByteOrderMark("UTF-16LE", new int[] { 255, 254 });
        UTF_32BE = new ByteOrderMark("UTF-32BE", new int[] { 0, 0, 254, 255 });
        UTF_32LE = new ByteOrderMark("UTF-32LE", new int[] { 255, 254, 0, 0 });
    }
}
