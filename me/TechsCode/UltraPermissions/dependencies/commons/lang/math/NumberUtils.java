

package me.TechsCode.EnderPermissions.dependencies.commons.lang.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.StringUtils;

public class NumberUtils
{
    public static final Long LONG_ZERO;
    public static final Long LONG_ONE;
    public static final Long LONG_MINUS_ONE;
    public static final Integer INTEGER_ZERO;
    public static final Integer INTEGER_ONE;
    public static final Integer INTEGER_MINUS_ONE;
    public static final Short SHORT_ZERO;
    public static final Short SHORT_ONE;
    public static final Short SHORT_MINUS_ONE;
    public static final Byte BYTE_ZERO;
    public static final Byte BYTE_ONE;
    public static final Byte BYTE_MINUS_ONE;
    public static final Double DOUBLE_ZERO;
    public static final Double DOUBLE_ONE;
    public static final Double DOUBLE_MINUS_ONE;
    public static final Float FLOAT_ZERO;
    public static final Float FLOAT_ONE;
    public static final Float FLOAT_MINUS_ONE;
    
    public static int stringToInt(final String s) {
        return toInt(s);
    }
    
    public static int toInt(final String s) {
        return toInt(s, 0);
    }
    
    public static int stringToInt(final String s, final int n) {
        return toInt(s, n);
    }
    
    public static int toInt(final String s, final int n) {
        if (s == null) {
            return n;
        }
        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException ex) {
            return n;
        }
    }
    
    public static long toLong(final String s) {
        return toLong(s, 0L);
    }
    
    public static long toLong(final String s, final long n) {
        if (s == null) {
            return n;
        }
        try {
            return Long.parseLong(s);
        }
        catch (NumberFormatException ex) {
            return n;
        }
    }
    
    public static float toFloat(final String s) {
        return toFloat(s, 0.0f);
    }
    
    public static float toFloat(final String s, final float n) {
        if (s == null) {
            return n;
        }
        try {
            return Float.parseFloat(s);
        }
        catch (NumberFormatException ex) {
            return n;
        }
    }
    
    public static double toDouble(final String s) {
        return toDouble(s, 0.0);
    }
    
    public static double toDouble(final String s, final double n) {
        if (s == null) {
            return n;
        }
        try {
            return Double.parseDouble(s);
        }
        catch (NumberFormatException ex) {
            return n;
        }
    }
    
    public static byte toByte(final String s) {
        return toByte(s, (byte)0);
    }
    
    public static byte toByte(final String s, final byte b) {
        if (s == null) {
            return b;
        }
        try {
            return Byte.parseByte(s);
        }
        catch (NumberFormatException ex) {
            return b;
        }
    }
    
    public static short toShort(final String s) {
        return toShort(s, (short)0);
    }
    
    public static short toShort(final String s, final short n) {
        if (s == null) {
            return n;
        }
        try {
            return Short.parseShort(s);
        }
        catch (NumberFormatException ex) {
            return n;
        }
    }
    
    public static Number createNumber(final String str) {
        if (str == null) {
            return null;
        }
        if (StringUtils.isBlank(str)) {
            throw new NumberFormatException("A blank string is not a valid number");
        }
        if (str.startsWith("--")) {
            return null;
        }
        if (str.startsWith("0x") || str.startsWith("-0x")) {
            return createInteger(str);
        }
        final char char1 = str.charAt(str.length() - 1);
        final int index = str.indexOf(46);
        final int n = str.indexOf(101) + str.indexOf(69) + 1;
        String s;
        String s2;
        if (index > -1) {
            if (n > -1) {
                if (n < index || n > str.length()) {
                    throw new NumberFormatException(str + " is not a valid number.");
                }
                s = str.substring(index + 1, n);
            }
            else {
                s = str.substring(index + 1);
            }
            s2 = str.substring(0, index);
        }
        else {
            if (n > -1) {
                if (n > str.length()) {
                    throw new NumberFormatException(str + " is not a valid number.");
                }
                s2 = str.substring(0, n);
            }
            else {
                s2 = str;
            }
            s = null;
        }
        if (!Character.isDigit(char1) && char1 != '.') {
            String substring;
            if (n > -1 && n < str.length() - 1) {
                substring = str.substring(n + 1, str.length() - 1);
            }
            else {
                substring = null;
            }
            final String substring2 = str.substring(0, str.length() - 1);
            final boolean b = isAllZeros(s2) && isAllZeros(substring);
            switch (char1) {
                case 'L':
                case 'l': {
                    if (s == null && substring == null) {
                        if (substring2.charAt(0) != '-' || !isDigits(substring2.substring(1))) {
                            if (!isDigits(substring2)) {
                                throw new NumberFormatException(str + " is not a valid number.");
                            }
                        }
                        try {
                            return createLong(substring2);
                        }
                        catch (NumberFormatException ex) {
                            return createBigInteger(substring2);
                        }
                    }
                    throw new NumberFormatException(str + " is not a valid number.");
                }
                case 'F':
                case 'f': {
                    try {
                        final Float float1 = createFloat(substring2);
                        if (!float1.isInfinite() && (float1 != 0.0f || b)) {
                            return float1;
                        }
                    }
                    catch (NumberFormatException ex2) {}
                }
                case 'D':
                case 'd': {
                    try {
                        final Double double1 = createDouble(substring2);
                        if (!double1.isInfinite() && (double1.floatValue() != 0.0 || b)) {
                            return double1;
                        }
                    }
                    catch (NumberFormatException ex3) {}
                    try {
                        return createBigDecimal(substring2);
                    }
                    catch (NumberFormatException ex4) {}
                    break;
                }
            }
            throw new NumberFormatException(str + " is not a valid number.");
        }
        String substring3;
        if (n > -1 && n < str.length() - 1) {
            substring3 = str.substring(n + 1, str.length());
        }
        else {
            substring3 = null;
        }
        if (s == null && substring3 == null) {
            try {
                return createInteger(str);
            }
            catch (NumberFormatException ex5) {
                try {
                    return createLong(str);
                }
                catch (NumberFormatException ex6) {
                    return createBigInteger(str);
                }
            }
        }
        final boolean b2 = isAllZeros(s2) && isAllZeros(substring3);
        try {
            final Float float2 = createFloat(str);
            if (!float2.isInfinite() && (float2 != 0.0f || b2)) {
                return float2;
            }
        }
        catch (NumberFormatException ex7) {}
        try {
            final Double double2 = createDouble(str);
            if (!double2.isInfinite() && (double2 != 0.0 || b2)) {
                return double2;
            }
        }
        catch (NumberFormatException ex8) {}
        return createBigDecimal(str);
    }
    
    private static boolean isAllZeros(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = s.length() - 1; i >= 0; --i) {
            if (s.charAt(i) != '0') {
                return false;
            }
        }
        return s.length() > 0;
    }
    
    public static Float createFloat(final String s) {
        if (s == null) {
            return null;
        }
        return Float.valueOf(s);
    }
    
    public static Double createDouble(final String s) {
        if (s == null) {
            return null;
        }
        return Double.valueOf(s);
    }
    
    public static Integer createInteger(final String nm) {
        if (nm == null) {
            return null;
        }
        return Integer.decode(nm);
    }
    
    public static Long createLong(final String s) {
        if (s == null) {
            return null;
        }
        return Long.valueOf(s);
    }
    
    public static BigInteger createBigInteger(final String val) {
        if (val == null) {
            return null;
        }
        return new BigInteger(val);
    }
    
    public static BigDecimal createBigDecimal(final String val) {
        if (val == null) {
            return null;
        }
        if (StringUtils.isBlank(val)) {
            throw new NumberFormatException("A blank string is not a valid number");
        }
        return new BigDecimal(val);
    }
    
    public static long min(final long[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        long n = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (array[i] < n) {
                n = array[i];
            }
        }
        return n;
    }
    
    public static int min(final int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        int n = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (array[i] < n) {
                n = array[i];
            }
        }
        return n;
    }
    
    public static short min(final short[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        short n = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (array[i] < n) {
                n = array[i];
            }
        }
        return n;
    }
    
    public static byte min(final byte[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        byte b = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (array[i] < b) {
                b = array[i];
            }
        }
        return b;
    }
    
    public static double min(final double[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        double n = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (Double.isNaN(array[i])) {
                return Double.NaN;
            }
            if (array[i] < n) {
                n = array[i];
            }
        }
        return n;
    }
    
    public static float min(final float[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        float n = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (Float.isNaN(array[i])) {
                return Float.NaN;
            }
            if (array[i] < n) {
                n = array[i];
            }
        }
        return n;
    }
    
    public static long max(final long[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        long n = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (array[i] > n) {
                n = array[i];
            }
        }
        return n;
    }
    
    public static int max(final int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        int n = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (array[i] > n) {
                n = array[i];
            }
        }
        return n;
    }
    
    public static short max(final short[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        short n = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (array[i] > n) {
                n = array[i];
            }
        }
        return n;
    }
    
    public static byte max(final byte[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        byte b = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (array[i] > b) {
                b = array[i];
            }
        }
        return b;
    }
    
    public static double max(final double[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        double n = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (Double.isNaN(array[i])) {
                return Double.NaN;
            }
            if (array[i] > n) {
                n = array[i];
            }
        }
        return n;
    }
    
    public static float max(final float[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        float n = array[0];
        for (int i = 1; i < array.length; ++i) {
            if (Float.isNaN(array[i])) {
                return Float.NaN;
            }
            if (array[i] > n) {
                n = array[i];
            }
        }
        return n;
    }
    
    public static long min(long n, final long n2, final long n3) {
        if (n2 < n) {
            n = n2;
        }
        if (n3 < n) {
            n = n3;
        }
        return n;
    }
    
    public static int min(int n, final int n2, final int n3) {
        if (n2 < n) {
            n = n2;
        }
        if (n3 < n) {
            n = n3;
        }
        return n;
    }
    
    public static short min(short n, final short n2, final short n3) {
        if (n2 < n) {
            n = n2;
        }
        if (n3 < n) {
            n = n3;
        }
        return n;
    }
    
    public static byte min(byte b, final byte b2, final byte b3) {
        if (b2 < b) {
            b = b2;
        }
        if (b3 < b) {
            b = b3;
        }
        return b;
    }
    
    public static double min(final double a, final double b, final double b2) {
        return Math.min(Math.min(a, b), b2);
    }
    
    public static float min(final float a, final float b, final float b2) {
        return Math.min(Math.min(a, b), b2);
    }
    
    public static long max(long n, final long n2, final long n3) {
        if (n2 > n) {
            n = n2;
        }
        if (n3 > n) {
            n = n3;
        }
        return n;
    }
    
    public static int max(int n, final int n2, final int n3) {
        if (n2 > n) {
            n = n2;
        }
        if (n3 > n) {
            n = n3;
        }
        return n;
    }
    
    public static short max(short n, final short n2, final short n3) {
        if (n2 > n) {
            n = n2;
        }
        if (n3 > n) {
            n = n3;
        }
        return n;
    }
    
    public static byte max(byte b, final byte b2, final byte b3) {
        if (b2 > b) {
            b = b2;
        }
        if (b3 > b) {
            b = b3;
        }
        return b;
    }
    
    public static double max(final double a, final double b, final double b2) {
        return Math.max(Math.max(a, b), b2);
    }
    
    public static float max(final float a, final float b, final float b2) {
        return Math.max(Math.max(a, b), b2);
    }
    
    public static int compare(final double value, final double value2) {
        if (value < value2) {
            return -1;
        }
        if (value > value2) {
            return 1;
        }
        final long doubleToLongBits = Double.doubleToLongBits(value);
        final long doubleToLongBits2 = Double.doubleToLongBits(value2);
        if (doubleToLongBits == doubleToLongBits2) {
            return 0;
        }
        if (doubleToLongBits < doubleToLongBits2) {
            return -1;
        }
        return 1;
    }
    
    public static int compare(final float value, final float value2) {
        if (value < value2) {
            return -1;
        }
        if (value > value2) {
            return 1;
        }
        final int floatToIntBits = Float.floatToIntBits(value);
        final int floatToIntBits2 = Float.floatToIntBits(value2);
        if (floatToIntBits == floatToIntBits2) {
            return 0;
        }
        if (floatToIntBits < floatToIntBits2) {
            return -1;
        }
        return 1;
    }
    
    public static boolean isDigits(final String s) {
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        for (int i = 0; i < s.length(); ++i) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNumber(final String s) {
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        final char[] charArray = s.toCharArray();
        int length = charArray.length;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        boolean b = false;
        final int n4 = (charArray[0] == '-') ? 1 : 0;
        if (length > n4 + 1 && charArray[n4] == '0' && charArray[n4 + 1] == 'x') {
            int i = n4 + 2;
            if (i == length) {
                return false;
            }
            while (i < charArray.length) {
                if ((charArray[i] < '0' || charArray[i] > '9') && (charArray[i] < 'a' || charArray[i] > 'f') && (charArray[i] < 'A' || charArray[i] > 'F')) {
                    return false;
                }
                ++i;
            }
            return true;
        }
        else {
            --length;
            int n5;
            for (n5 = n4; n5 < length || (n5 < length + 1 && n3 != 0 && !b); ++n5) {
                if (charArray[n5] >= '0' && charArray[n5] <= '9') {
                    b = true;
                    n3 = 0;
                }
                else if (charArray[n5] == '.') {
                    if (n2 != 0 || n != 0) {
                        return false;
                    }
                    n2 = 1;
                }
                else if (charArray[n5] == 'e' || charArray[n5] == 'E') {
                    if (n != 0) {
                        return false;
                    }
                    if (!b) {
                        return false;
                    }
                    n = 1;
                    n3 = 1;
                }
                else {
                    if (charArray[n5] != '+' && charArray[n5] != '-') {
                        return false;
                    }
                    if (n3 == 0) {
                        return false;
                    }
                    n3 = 0;
                    b = false;
                }
            }
            if (n5 >= charArray.length) {
                return n3 == 0 && b;
            }
            if (charArray[n5] >= '0' && charArray[n5] <= '9') {
                return true;
            }
            if (charArray[n5] == 'e' || charArray[n5] == 'E') {
                return false;
            }
            if (charArray[n5] == '.') {
                return n2 == 0 && n == 0 && b;
            }
            if (n3 == 0 && (charArray[n5] == 'd' || charArray[n5] == 'D' || charArray[n5] == 'f' || charArray[n5] == 'F')) {
                return b;
            }
            return (charArray[n5] == 'l' || charArray[n5] == 'L') && b && n == 0;
        }
    }
    
    static {
        LONG_ZERO = new Long(0L);
        LONG_ONE = new Long(1L);
        LONG_MINUS_ONE = new Long(-1L);
        INTEGER_ZERO = new Integer(0);
        INTEGER_ONE = new Integer(1);
        INTEGER_MINUS_ONE = new Integer(-1);
        SHORT_ZERO = new Short((short)0);
        SHORT_ONE = new Short((short)1);
        SHORT_MINUS_ONE = new Short((short)(-1));
        BYTE_ZERO = new Byte((byte)0);
        BYTE_ONE = new Byte((byte)1);
        BYTE_MINUS_ONE = new Byte((byte)(-1));
        DOUBLE_ZERO = new Double(0.0);
        DOUBLE_ONE = new Double(1.0);
        DOUBLE_MINUS_ONE = new Double(-1.0);
        FLOAT_ZERO = new Float(0.0f);
        FLOAT_ONE = new Float(1.0f);
        FLOAT_MINUS_ONE = new Float(-1.0f);
    }
}
