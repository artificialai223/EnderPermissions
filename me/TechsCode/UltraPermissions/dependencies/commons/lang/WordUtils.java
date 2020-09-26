

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

public class WordUtils
{
    public static String wrap(final String s, final int n) {
        return wrap(s, n, null, false);
    }
    
    public static String wrap(final String s, int n, String line_SEPARATOR, final boolean b) {
        if (s == null) {
            return null;
        }
        if (line_SEPARATOR == null) {
            line_SEPARATOR = SystemUtils.LINE_SEPARATOR;
        }
        if (n < 1) {
            n = 1;
        }
        final int length = s.length();
        int n2 = 0;
        final StringBuffer sb = new StringBuffer(length + 32);
        while (length - n2 > n) {
            if (s.charAt(n2) == ' ') {
                ++n2;
            }
            else {
                final int lastIndex = s.lastIndexOf(32, n + n2);
                if (lastIndex >= n2) {
                    sb.append(s.substring(n2, lastIndex));
                    sb.append(line_SEPARATOR);
                    n2 = lastIndex + 1;
                }
                else if (b) {
                    sb.append(s.substring(n2, n + n2));
                    sb.append(line_SEPARATOR);
                    n2 += n;
                }
                else {
                    final int index = s.indexOf(32, n + n2);
                    if (index >= 0) {
                        sb.append(s.substring(n2, index));
                        sb.append(line_SEPARATOR);
                        n2 = index + 1;
                    }
                    else {
                        sb.append(s.substring(n2));
                        n2 = length;
                    }
                }
            }
        }
        sb.append(s.substring(n2));
        return sb.toString();
    }
    
    public static String capitalize(final String s) {
        return capitalize(s, null);
    }
    
    public static String capitalize(final String s, final char[] array) {
        final int n = (array == null) ? -1 : array.length;
        if (s == null || s.length() == 0 || n == 0) {
            return s;
        }
        final int length = s.length();
        final StringBuffer sb = new StringBuffer(length);
        int n2 = 1;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (isDelimiter(char1, array)) {
                sb.append(char1);
                n2 = 1;
            }
            else if (n2 != 0) {
                sb.append(Character.toTitleCase(char1));
                n2 = 0;
            }
            else {
                sb.append(char1);
            }
        }
        return sb.toString();
    }
    
    public static String capitalizeFully(final String s) {
        return capitalizeFully(s, null);
    }
    
    public static String capitalizeFully(String lowerCase, final char[] array) {
        final int n = (array == null) ? -1 : array.length;
        if (lowerCase == null || lowerCase.length() == 0 || n == 0) {
            return lowerCase;
        }
        lowerCase = lowerCase.toLowerCase();
        return capitalize(lowerCase, array);
    }
    
    public static String uncapitalize(final String s) {
        return uncapitalize(s, null);
    }
    
    public static String uncapitalize(final String s, final char[] array) {
        final int n = (array == null) ? -1 : array.length;
        if (s == null || s.length() == 0 || n == 0) {
            return s;
        }
        final int length = s.length();
        final StringBuffer sb = new StringBuffer(length);
        int n2 = 1;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (isDelimiter(char1, array)) {
                sb.append(char1);
                n2 = 1;
            }
            else if (n2 != 0) {
                sb.append(Character.toLowerCase(char1));
                n2 = 0;
            }
            else {
                sb.append(char1);
            }
        }
        return sb.toString();
    }
    
    public static String swapCase(final String s) {
        final int length;
        if (s == null || (length = s.length()) == 0) {
            return s;
        }
        final StringBuffer sb = new StringBuffer(length);
        boolean whitespace = true;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            char c;
            if (Character.isUpperCase(char1)) {
                c = Character.toLowerCase(char1);
            }
            else if (Character.isTitleCase(char1)) {
                c = Character.toLowerCase(char1);
            }
            else if (Character.isLowerCase(char1)) {
                if (whitespace) {
                    c = Character.toTitleCase(char1);
                }
                else {
                    c = Character.toUpperCase(char1);
                }
            }
            else {
                c = char1;
            }
            sb.append(c);
            whitespace = Character.isWhitespace(char1);
        }
        return sb.toString();
    }
    
    public static String initials(final String s) {
        return initials(s, null);
    }
    
    public static String initials(final String s, final char[] array) {
        if (s == null || s.length() == 0) {
            return s;
        }
        if (array != null && array.length == 0) {
            return "";
        }
        final int length = s.length();
        final char[] value = new char[length / 2 + 1];
        int count = 0;
        int n = 1;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (isDelimiter(char1, array)) {
                n = 1;
            }
            else if (n != 0) {
                value[count++] = char1;
                n = 0;
            }
        }
        return new String(value, 0, count);
    }
    
    private static boolean isDelimiter(final char ch, final char[] array) {
        if (array == null) {
            return Character.isWhitespace(ch);
        }
        for (int i = 0; i < array.length; ++i) {
            if (ch == array[i]) {
                return true;
            }
        }
        return false;
    }
    
    public static String abbreviate(final String s, int length, int length2, final String s2) {
        if (s == null) {
            return null;
        }
        if (s.length() == 0) {
            return "";
        }
        if (length > s.length()) {
            length = s.length();
        }
        if (length2 == -1 || length2 > s.length()) {
            length2 = s.length();
        }
        if (length2 < length) {
            length2 = length;
        }
        final StringBuffer sb = new StringBuffer();
        final int index = StringUtils.indexOf(s, " ", length);
        if (index == -1) {
            sb.append(s.substring(0, length2));
            if (length2 != s.length()) {
                sb.append(StringUtils.defaultString(s2));
            }
        }
        else if (index > length2) {
            sb.append(s.substring(0, length2));
            sb.append(StringUtils.defaultString(s2));
        }
        else {
            sb.append(s.substring(0, index));
            sb.append(StringUtils.defaultString(s2));
        }
        return sb.toString();
    }
}
