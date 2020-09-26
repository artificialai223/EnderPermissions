

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import java.util.Random;

public class RandomStringUtils
{
    private static final Random RANDOM;
    
    public static String random(final int n) {
        return random(n, false, false);
    }
    
    public static String randomAscii(final int n) {
        return random(n, 32, 127, false, false);
    }
    
    public static String randomAlphabetic(final int n) {
        return random(n, true, false);
    }
    
    public static String randomAlphanumeric(final int n) {
        return random(n, true, true);
    }
    
    public static String randomNumeric(final int n) {
        return random(n, false, true);
    }
    
    public static String random(final int n, final boolean b, final boolean b2) {
        return random(n, 0, 0, b, b2);
    }
    
    public static String random(final int n, final int n2, final int n3, final boolean b, final boolean b2) {
        return random(n, n2, n3, b, b2, null, RandomStringUtils.RANDOM);
    }
    
    public static String random(final int n, final int n2, final int n3, final boolean b, final boolean b2, final char[] array) {
        return random(n, n2, n3, b, b2, array, RandomStringUtils.RANDOM);
    }
    
    public static String random(int i, int n, int n2, final boolean b, final boolean b2, final char[] array, final Random random) {
        if (i == 0) {
            return "";
        }
        if (i < 0) {
            throw new IllegalArgumentException("Requested random string length " + i + " is less than 0.");
        }
        if (n == 0 && n2 == 0) {
            n2 = 123;
            n = 32;
            if (!b && !b2) {
                n = 0;
                n2 = Integer.MAX_VALUE;
            }
        }
        final char[] value = new char[i];
        final int n3 = n2 - n;
        while (i-- != 0) {
            char c;
            if (array == null) {
                c = (char)(random.nextInt(n3) + n);
            }
            else {
                c = array[random.nextInt(n3) + n];
            }
            if ((b && Character.isLetter(c)) || (b2 && Character.isDigit(c)) || (!b && !b2)) {
                if (c >= '\udc00' && c <= '\udfff') {
                    if (i == 0) {
                        ++i;
                    }
                    else {
                        value[i] = c;
                        --i;
                        value[i] = (char)(55296 + random.nextInt(128));
                    }
                }
                else if (c >= '\ud800' && c <= '\udb7f') {
                    if (i == 0) {
                        ++i;
                    }
                    else {
                        value[i] = (char)(56320 + random.nextInt(128));
                        --i;
                        value[i] = c;
                    }
                }
                else if (c >= '\udb80' && c <= '\udbff') {
                    ++i;
                }
                else {
                    value[i] = c;
                }
            }
            else {
                ++i;
            }
        }
        return new String(value);
    }
    
    public static String random(final int n, final String s) {
        if (s == null) {
            return random(n, 0, 0, false, false, null, RandomStringUtils.RANDOM);
        }
        return random(n, s.toCharArray());
    }
    
    public static String random(final int n, final char[] array) {
        if (array == null) {
            return random(n, 0, 0, false, false, null, RandomStringUtils.RANDOM);
        }
        return random(n, 0, array.length, false, false, array, RandomStringUtils.RANDOM);
    }
    
    static {
        RANDOM = new Random();
    }
}
