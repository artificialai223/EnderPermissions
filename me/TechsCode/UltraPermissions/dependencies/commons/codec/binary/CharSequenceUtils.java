

package me.TechsCode.EnderPermissions.dependencies.commons.codec.binary;

public class CharSequenceUtils
{
    static boolean regionMatches(final CharSequence charSequence, final boolean ignoreCase, final int toffset, final CharSequence charSequence2, final int ooffset, final int len) {
        if (charSequence instanceof String && charSequence2 instanceof String) {
            return ((String)charSequence).regionMatches(ignoreCase, toffset, (String)charSequence2, ooffset, len);
        }
        int n = toffset;
        int n2 = ooffset;
        int n3 = len;
        while (n3-- > 0) {
            final char char1 = charSequence.charAt(n++);
            final char char2 = charSequence2.charAt(n2++);
            if (char1 == char2) {
                continue;
            }
            if (!ignoreCase) {
                return false;
            }
            if (Character.toUpperCase(char1) != Character.toUpperCase(char2) && Character.toLowerCase(char1) != Character.toLowerCase(char2)) {
                return false;
            }
        }
        return true;
    }
}
