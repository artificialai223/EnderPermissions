

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

public class CharUtils
{
    private static final String CHAR_STRING = "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u007f";
    private static final String[] CHAR_STRING_ARRAY;
    private static final Character[] CHAR_ARRAY;
    public static final char LF = '\n';
    public static final char CR = '\r';
    
    public static Character toCharacterObject(final char value) {
        if (value < CharUtils.CHAR_ARRAY.length) {
            return CharUtils.CHAR_ARRAY[value];
        }
        return new Character(value);
    }
    
    public static Character toCharacterObject(final String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        return toCharacterObject(s.charAt(0));
    }
    
    public static char toChar(final Character c) {
        if (c == null) {
            throw new IllegalArgumentException("The Character must not be null");
        }
        return c;
    }
    
    public static char toChar(final Character c, final char c2) {
        if (c == null) {
            return c2;
        }
        return c;
    }
    
    public static char toChar(final String s) {
        if (StringUtils.isEmpty(s)) {
            throw new IllegalArgumentException("The String must not be empty");
        }
        return s.charAt(0);
    }
    
    public static char toChar(final String s, final char c) {
        if (StringUtils.isEmpty(s)) {
            return c;
        }
        return s.charAt(0);
    }
    
    public static int toIntValue(final char c) {
        if (!isAsciiNumeric(c)) {
            throw new IllegalArgumentException("The character " + c + " is not in the range '0' - '9'");
        }
        return c - '0';
    }
    
    public static int toIntValue(final char c, final int n) {
        if (!isAsciiNumeric(c)) {
            return n;
        }
        return c - '0';
    }
    
    public static int toIntValue(final Character c) {
        if (c == null) {
            throw new IllegalArgumentException("The character must not be null");
        }
        return toIntValue((char)c);
    }
    
    public static int toIntValue(final Character c, final int n) {
        if (c == null) {
            return n;
        }
        return toIntValue((char)c, n);
    }
    
    public static String toString(final char c) {
        if (c < '\u0080') {
            return CharUtils.CHAR_STRING_ARRAY[c];
        }
        return new String(new char[] { c });
    }
    
    public static String toString(final Character c) {
        if (c == null) {
            return null;
        }
        return toString((char)c);
    }
    
    public static String unicodeEscaped(final char c) {
        if (c < '\u0010') {
            return "\\u000" + Integer.toHexString(c);
        }
        if (c < '\u0100') {
            return "\\u00" + Integer.toHexString(c);
        }
        if (c < '\u1000') {
            return "\\u0" + Integer.toHexString(c);
        }
        return "\\u" + Integer.toHexString(c);
    }
    
    public static String unicodeEscaped(final Character c) {
        if (c == null) {
            return null;
        }
        return unicodeEscaped((char)c);
    }
    
    public static boolean isAscii(final char c) {
        return c < '\u0080';
    }
    
    public static boolean isAsciiPrintable(final char c) {
        return c >= ' ' && c < '\u007f';
    }
    
    public static boolean isAsciiControl(final char c) {
        return c < ' ' || c == '\u007f';
    }
    
    public static boolean isAsciiAlpha(final char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }
    
    public static boolean isAsciiAlphaUpper(final char c) {
        return c >= 'A' && c <= 'Z';
    }
    
    public static boolean isAsciiAlphaLower(final char c) {
        return c >= 'a' && c <= 'z';
    }
    
    public static boolean isAsciiNumeric(final char c) {
        return c >= '0' && c <= '9';
    }
    
    public static boolean isAsciiAlphanumeric(final char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
    }
    
    static boolean isHighSurrogate(final char c) {
        return '\ud800' <= c && '\udbff' >= c;
    }
    
    static {
        CHAR_STRING_ARRAY = new String[128];
        CHAR_ARRAY = new Character[128];
        for (int i = 127; i >= 0; --i) {
            CharUtils.CHAR_STRING_ARRAY[i] = "\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u007f".substring(i, i + 1);
            CharUtils.CHAR_ARRAY[i] = new Character((char)i);
        }
    }
}
