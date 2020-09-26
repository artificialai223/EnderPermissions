

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.math.NumberUtils;

public class BooleanUtils
{
    public static Boolean negate(final Boolean b) {
        if (b == null) {
            return null;
        }
        return ((boolean)b) ? Boolean.FALSE : Boolean.TRUE;
    }
    
    public static boolean isTrue(final Boolean b) {
        return b != null && b;
    }
    
    public static boolean isNotTrue(final Boolean b) {
        return !isTrue(b);
    }
    
    public static boolean isFalse(final Boolean b) {
        return b != null && !b;
    }
    
    public static boolean isNotFalse(final Boolean b) {
        return !isFalse(b);
    }
    
    public static Boolean toBooleanObject(final boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }
    
    public static boolean toBoolean(final Boolean b) {
        return b != null && b;
    }
    
    public static boolean toBooleanDefaultIfNull(final Boolean b, final boolean b2) {
        if (b == null) {
            return b2;
        }
        return b;
    }
    
    public static boolean toBoolean(final int n) {
        return n != 0;
    }
    
    public static Boolean toBooleanObject(final int n) {
        return (n == 0) ? Boolean.FALSE : Boolean.TRUE;
    }
    
    public static Boolean toBooleanObject(final Integer n) {
        if (n == null) {
            return null;
        }
        return (n == 0) ? Boolean.FALSE : Boolean.TRUE;
    }
    
    public static boolean toBoolean(final int n, final int n2, final int n3) {
        if (n == n2) {
            return true;
        }
        if (n == n3) {
            return false;
        }
        throw new IllegalArgumentException("The Integer did not match either specified value");
    }
    
    public static boolean toBoolean(final Integer n, final Integer obj, final Integer obj2) {
        if (n == null) {
            if (obj == null) {
                return true;
            }
            if (obj2 == null) {
                return false;
            }
        }
        else {
            if (n.equals(obj)) {
                return true;
            }
            if (n.equals(obj2)) {
                return false;
            }
        }
        throw new IllegalArgumentException("The Integer did not match either specified value");
    }
    
    public static Boolean toBooleanObject(final int n, final int n2, final int n3, final int n4) {
        if (n == n2) {
            return Boolean.TRUE;
        }
        if (n == n3) {
            return Boolean.FALSE;
        }
        if (n == n4) {
            return null;
        }
        throw new IllegalArgumentException("The Integer did not match any specified value");
    }
    
    public static Boolean toBooleanObject(final Integer n, final Integer obj, final Integer obj2, final Integer obj3) {
        if (n == null) {
            if (obj == null) {
                return Boolean.TRUE;
            }
            if (obj2 == null) {
                return Boolean.FALSE;
            }
            if (obj3 == null) {
                return null;
            }
        }
        else {
            if (n.equals(obj)) {
                return Boolean.TRUE;
            }
            if (n.equals(obj2)) {
                return Boolean.FALSE;
            }
            if (n.equals(obj3)) {
                return null;
            }
        }
        throw new IllegalArgumentException("The Integer did not match any specified value");
    }
    
    public static int toInteger(final boolean b) {
        return b ? 1 : 0;
    }
    
    public static Integer toIntegerObject(final boolean b) {
        return b ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO;
    }
    
    public static Integer toIntegerObject(final Boolean b) {
        if (b == null) {
            return null;
        }
        return b ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO;
    }
    
    public static int toInteger(final boolean b, final int n, final int n2) {
        return b ? n : n2;
    }
    
    public static int toInteger(final Boolean b, final int n, final int n2, final int n3) {
        if (b == null) {
            return n3;
        }
        return b ? n : n2;
    }
    
    public static Integer toIntegerObject(final boolean b, final Integer n, final Integer n2) {
        return b ? n : n2;
    }
    
    public static Integer toIntegerObject(final Boolean b, final Integer n, final Integer n2, final Integer n3) {
        if (b == null) {
            return n3;
        }
        return b ? n : n2;
    }
    
    public static Boolean toBooleanObject(final String s) {
        if (s == "true") {
            return Boolean.TRUE;
        }
        if (s == null) {
            return null;
        }
        switch (s.length()) {
            case 1: {
                final char char1 = s.charAt(0);
                if (char1 == 'y' || char1 == 'Y' || char1 == 't' || char1 == 'T') {
                    return Boolean.TRUE;
                }
                if (char1 == 'n' || char1 == 'N' || char1 == 'f' || char1 == 'F') {
                    return Boolean.FALSE;
                }
                break;
            }
            case 2: {
                final char char2 = s.charAt(0);
                final char char3 = s.charAt(1);
                if ((char2 == 'o' || char2 == 'O') && (char3 == 'n' || char3 == 'N')) {
                    return Boolean.TRUE;
                }
                if ((char2 == 'n' || char2 == 'N') && (char3 == 'o' || char3 == 'O')) {
                    return Boolean.FALSE;
                }
                break;
            }
            case 3: {
                final char char4 = s.charAt(0);
                final char char5 = s.charAt(1);
                final char char6 = s.charAt(2);
                if ((char4 == 'y' || char4 == 'Y') && (char5 == 'e' || char5 == 'E') && (char6 == 's' || char6 == 'S')) {
                    return Boolean.TRUE;
                }
                if ((char4 == 'o' || char4 == 'O') && (char5 == 'f' || char5 == 'F') && (char6 == 'f' || char6 == 'F')) {
                    return Boolean.FALSE;
                }
                break;
            }
            case 4: {
                final char char7 = s.charAt(0);
                final char char8 = s.charAt(1);
                final char char9 = s.charAt(2);
                final char char10 = s.charAt(3);
                if ((char7 == 't' || char7 == 'T') && (char8 == 'r' || char8 == 'R') && (char9 == 'u' || char9 == 'U') && (char10 == 'e' || char10 == 'E')) {
                    return Boolean.TRUE;
                }
                break;
            }
            case 5: {
                final char char11 = s.charAt(0);
                final char char12 = s.charAt(1);
                final char char13 = s.charAt(2);
                final char char14 = s.charAt(3);
                final char char15 = s.charAt(4);
                if ((char11 == 'f' || char11 == 'F') && (char12 == 'a' || char12 == 'A') && (char13 == 'l' || char13 == 'L') && (char14 == 's' || char14 == 'S') && (char15 == 'e' || char15 == 'E')) {
                    return Boolean.FALSE;
                }
                break;
            }
        }
        return null;
    }
    
    public static Boolean toBooleanObject(final String s, final String anObject, final String anObject2, final String anObject3) {
        if (s == null) {
            if (anObject == null) {
                return Boolean.TRUE;
            }
            if (anObject2 == null) {
                return Boolean.FALSE;
            }
            if (anObject3 == null) {
                return null;
            }
        }
        else {
            if (s.equals(anObject)) {
                return Boolean.TRUE;
            }
            if (s.equals(anObject2)) {
                return Boolean.FALSE;
            }
            if (s.equals(anObject3)) {
                return null;
            }
        }
        throw new IllegalArgumentException("The String did not match any specified value");
    }
    
    public static boolean toBoolean(final String s) {
        return toBoolean(toBooleanObject(s));
    }
    
    public static boolean toBoolean(final String s, final String anObject, final String anObject2) {
        if (s == null) {
            if (anObject == null) {
                return true;
            }
            if (anObject2 == null) {
                return false;
            }
        }
        else {
            if (s.equals(anObject)) {
                return true;
            }
            if (s.equals(anObject2)) {
                return false;
            }
        }
        throw new IllegalArgumentException("The String did not match either specified value");
    }
    
    public static String toStringTrueFalse(final Boolean b) {
        return toString(b, "true", "false", null);
    }
    
    public static String toStringOnOff(final Boolean b) {
        return toString(b, "on", "off", null);
    }
    
    public static String toStringYesNo(final Boolean b) {
        return toString(b, "yes", "no", null);
    }
    
    public static String toString(final Boolean b, final String s, final String s2, final String s3) {
        if (b == null) {
            return s3;
        }
        return b ? s : s2;
    }
    
    public static String toStringTrueFalse(final boolean b) {
        return toString(b, "true", "false");
    }
    
    public static String toStringOnOff(final boolean b) {
        return toString(b, "on", "off");
    }
    
    public static String toStringYesNo(final boolean b) {
        return toString(b, "yes", "no");
    }
    
    public static String toString(final boolean b, final String s, final String s2) {
        return b ? s : s2;
    }
    
    public static boolean xor(final boolean[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (array[i]) {
                if (n >= 1) {
                    return false;
                }
                ++n;
            }
        }
        return n == 1;
    }
    
    public static Boolean xor(final Boolean[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        boolean[] primitive;
        try {
            primitive = ArrayUtils.toPrimitive(array);
        }
        catch (NullPointerException ex) {
            throw new IllegalArgumentException("The array must not contain any null elements");
        }
        return xor(primitive) ? Boolean.TRUE : Boolean.FALSE;
    }
}
