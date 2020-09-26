

package me.TechsCode.EnderPermissions.dependencies.commons.io;

import java.nio.charset.Charset;
import java.io.OutputStream;

public class HexDump
{
    public static final String EOL;
    private static final char[] _hexcodes;
    private static final int[] _shifts;
    
    public static void dump(final byte[] array, final long n, final OutputStream outputStream, final int i) {
        if (i < 0 || i >= array.length) {
            throw new ArrayIndexOutOfBoundsException("illegal index: " + i + " into array of length " + array.length);
        }
        if (outputStream == null) {
            throw new IllegalArgumentException("cannot write to nullstream");
        }
        long n2 = n + i;
        final StringBuilder sb = new StringBuilder(74);
        for (int j = i; j < array.length; j += 16) {
            int n3 = array.length - j;
            if (n3 > 16) {
                n3 = 16;
            }
            dump(sb, n2).append(' ');
            for (int k = 0; k < 16; ++k) {
                if (k < n3) {
                    dump(sb, array[k + j]);
                }
                else {
                    sb.append("  ");
                }
                sb.append(' ');
            }
            for (int l = 0; l < n3; ++l) {
                if (array[l + j] >= 32 && array[l + j] < 127) {
                    sb.append((char)array[l + j]);
                }
                else {
                    sb.append('.');
                }
            }
            sb.append(HexDump.EOL);
            outputStream.write(sb.toString().getBytes(Charset.defaultCharset()));
            outputStream.flush();
            sb.setLength(0);
            n2 += n3;
        }
    }
    
    private static StringBuilder dump(final StringBuilder sb, final long n) {
        for (int i = 0; i < 8; ++i) {
            sb.append(HexDump._hexcodes[(int)(n >> HexDump._shifts[i]) & 0xF]);
        }
        return sb;
    }
    
    private static StringBuilder dump(final StringBuilder sb, final byte b) {
        for (int i = 0; i < 2; ++i) {
            sb.append(HexDump._hexcodes[b >> HexDump._shifts[i + 6] & 0xF]);
        }
        return sb;
    }
    
    static {
        EOL = System.getProperty("line.separator");
        _hexcodes = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        _shifts = new int[] { 28, 24, 20, 16, 12, 8, 4, 0 };
    }
}
