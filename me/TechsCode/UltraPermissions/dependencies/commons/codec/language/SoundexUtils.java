

package me.TechsCode.EnderPermissions.dependencies.commons.codec.language;

import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringEncoder;
import java.util.Locale;

final class SoundexUtils
{
    static String clean(final String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        final int length = s.length();
        final char[] value = new char[length];
        int count = 0;
        for (int i = 0; i < length; ++i) {
            if (Character.isLetter(s.charAt(i))) {
                value[count++] = s.charAt(i);
            }
        }
        if (count == length) {
            return s.toUpperCase(Locale.ENGLISH);
        }
        return new String(value, 0, count).toUpperCase(Locale.ENGLISH);
    }
    
    static int difference(final StringEncoder stringEncoder, final String s, final String s2) {
        return differenceEncoded(stringEncoder.encode(s), stringEncoder.encode(s2));
    }
    
    static int differenceEncoded(final String s, final String s2) {
        if (s == null || s2 == null) {
            return 0;
        }
        final int min = Math.min(s.length(), s2.length());
        int n = 0;
        for (int i = 0; i < min; ++i) {
            if (s.charAt(i) == s2.charAt(i)) {
                ++n;
            }
        }
        return n;
    }
}
