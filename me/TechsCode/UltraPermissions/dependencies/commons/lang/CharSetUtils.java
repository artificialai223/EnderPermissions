

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.text.StrBuilder;

public class CharSetUtils
{
    public static CharSet evaluateSet(final String[] array) {
        if (array == null) {
            return null;
        }
        return new CharSet(array);
    }
    
    public static String squeeze(final String s, final String s2) {
        if (StringUtils.isEmpty(s) || StringUtils.isEmpty(s2)) {
            return s;
        }
        return squeeze(s, new String[] { s2 });
    }
    
    public static String squeeze(final String s, final String[] array) {
        if (StringUtils.isEmpty(s) || ArrayUtils.isEmpty(array)) {
            return s;
        }
        final CharSet instance = CharSet.getInstance(array);
        final StrBuilder strBuilder = new StrBuilder(s.length());
        final char[] charArray = s.toCharArray();
        final int length = charArray.length;
        char c = ' ';
        for (int i = 0; i < length; ++i) {
            final char c2 = charArray[i];
            if (!instance.contains(c2) || c2 != c || i == 0) {
                strBuilder.append(c2);
                c = c2;
            }
        }
        return strBuilder.toString();
    }
    
    public static int count(final String s, final String s2) {
        if (StringUtils.isEmpty(s) || StringUtils.isEmpty(s2)) {
            return 0;
        }
        return count(s, new String[] { s2 });
    }
    
    public static int count(final String s, final String[] array) {
        if (StringUtils.isEmpty(s) || ArrayUtils.isEmpty(array)) {
            return 0;
        }
        final CharSet instance = CharSet.getInstance(array);
        int n = 0;
        final char[] charArray = s.toCharArray();
        for (int length = charArray.length, i = 0; i < length; ++i) {
            if (instance.contains(charArray[i])) {
                ++n;
            }
        }
        return n;
    }
    
    public static String keep(final String s, final String s2) {
        if (s == null) {
            return null;
        }
        if (s.length() == 0 || StringUtils.isEmpty(s2)) {
            return "";
        }
        return keep(s, new String[] { s2 });
    }
    
    public static String keep(final String s, final String[] array) {
        if (s == null) {
            return null;
        }
        if (s.length() == 0 || ArrayUtils.isEmpty(array)) {
            return "";
        }
        return modify(s, array, true);
    }
    
    public static String delete(final String s, final String s2) {
        if (StringUtils.isEmpty(s) || StringUtils.isEmpty(s2)) {
            return s;
        }
        return delete(s, new String[] { s2 });
    }
    
    public static String delete(final String s, final String[] array) {
        if (StringUtils.isEmpty(s) || ArrayUtils.isEmpty(array)) {
            return s;
        }
        return modify(s, array, false);
    }
    
    private static String modify(final String s, final String[] array, final boolean b) {
        final CharSet instance = CharSet.getInstance(array);
        final StrBuilder strBuilder = new StrBuilder(s.length());
        final char[] charArray = s.toCharArray();
        for (int length = charArray.length, i = 0; i < length; ++i) {
            if (instance.contains(charArray[i]) == b) {
                strBuilder.append(charArray[i]);
            }
        }
        return strBuilder.toString();
    }
    
    public static String translate(final String s, final String s2, final String s3) {
        if (StringUtils.isEmpty(s)) {
            return s;
        }
        final StrBuilder strBuilder = new StrBuilder(s.length());
        final char[] charArray = s.toCharArray();
        final char[] charArray2 = s3.toCharArray();
        final int length = charArray.length;
        final int n = s3.length() - 1;
        for (int i = 0; i < length; ++i) {
            int index = s2.indexOf(charArray[i]);
            if (index != -1) {
                if (index > n) {
                    index = n;
                }
                strBuilder.append(charArray2[index]);
            }
            else {
                strBuilder.append(charArray[i]);
            }
        }
        return strBuilder.toString();
    }
}
