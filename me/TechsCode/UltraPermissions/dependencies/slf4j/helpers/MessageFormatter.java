

package me.TechsCode.EnderPermissions.dependencies.slf4j.helpers;

import java.util.Map;
import java.util.HashMap;

public final class MessageFormatter
{
    static final char DELIM_START = '{';
    static final char DELIM_STOP = '}';
    static final String DELIM_STR = "{}";
    private static final char ESCAPE_CHAR = '\\';
    
    public static final FormattingTuple format(final String s, final Object o) {
        return arrayFormat(s, new Object[] { o });
    }
    
    public static final FormattingTuple format(final String s, final Object o, final Object o2) {
        return arrayFormat(s, new Object[] { o, o2 });
    }
    
    public static final FormattingTuple arrayFormat(final String s, final Object[] array) {
        final Throwable throwableCandidate = getThrowableCandidate(array);
        Object[] trimmedCopy = array;
        if (throwableCandidate != null) {
            trimmedCopy = trimmedCopy(array);
        }
        return arrayFormat(s, trimmedCopy, throwableCandidate);
    }
    
    public static final String basicArrayFormat(final String s, final Object[] array) {
        return arrayFormat(s, array, null).getMessage();
    }
    
    public static String basicArrayFormat(final NormalizedParameters normalizedParameters) {
        return basicArrayFormat(normalizedParameters.getMessage(), normalizedParameters.getArguments());
    }
    
    public static final FormattingTuple arrayFormat(final String s, final Object[] array, final Throwable t) {
        if (s == null) {
            return new FormattingTuple(null, array, t);
        }
        if (array == null) {
            return new FormattingTuple(s);
        }
        int n = 0;
        final StringBuilder sb = new StringBuilder(s.length() + 50);
        int i = 0;
        while (i < array.length) {
            final int index = s.indexOf("{}", n);
            if (index == -1) {
                if (n == 0) {
                    return new FormattingTuple(s, array, t);
                }
                sb.append(s, n, s.length());
                return new FormattingTuple(sb.toString(), array, t);
            }
            else {
                if (isEscapedDelimeter(s, index)) {
                    if (!isDoubleEscaped(s, index)) {
                        --i;
                        sb.append(s, n, index - 1);
                        sb.append('{');
                        n = index + 1;
                    }
                    else {
                        sb.append(s, n, index - 1);
                        deeplyAppendParameter(sb, array[i], new HashMap<Object[], Object>());
                        n = index + 2;
                    }
                }
                else {
                    sb.append(s, n, index);
                    deeplyAppendParameter(sb, array[i], new HashMap<Object[], Object>());
                    n = index + 2;
                }
                ++i;
            }
        }
        sb.append(s, n, s.length());
        return new FormattingTuple(sb.toString(), array, t);
    }
    
    static final boolean isEscapedDelimeter(final String s, final int n) {
        return n != 0 && s.charAt(n - 1) == '\\';
    }
    
    static final boolean isDoubleEscaped(final String s, final int n) {
        return n >= 2 && s.charAt(n - 2) == '\\';
    }
    
    private static void deeplyAppendParameter(final StringBuilder sb, final Object o, final Map<Object[], Object> map) {
        if (o == null) {
            sb.append("null");
            return;
        }
        if (!o.getClass().isArray()) {
            safeObjectAppend(sb, o);
        }
        else if (o instanceof boolean[]) {
            booleanArrayAppend(sb, (boolean[])o);
        }
        else if (o instanceof byte[]) {
            byteArrayAppend(sb, (byte[])o);
        }
        else if (o instanceof char[]) {
            charArrayAppend(sb, (char[])o);
        }
        else if (o instanceof short[]) {
            shortArrayAppend(sb, (short[])o);
        }
        else if (o instanceof int[]) {
            intArrayAppend(sb, (int[])o);
        }
        else if (o instanceof long[]) {
            longArrayAppend(sb, (long[])o);
        }
        else if (o instanceof float[]) {
            floatArrayAppend(sb, (float[])o);
        }
        else if (o instanceof double[]) {
            doubleArrayAppend(sb, (double[])o);
        }
        else {
            objectArrayAppend(sb, (Object[])o, map);
        }
    }
    
    private static void safeObjectAppend(final StringBuilder sb, final Object o) {
        try {
            sb.append(o.toString());
        }
        catch (Throwable t) {
            Util.report("SLF4J: Failed toString() invocation on an object of type [" + o.getClass().getName() + "]", t);
            sb.append("[FAILED toString()]");
        }
    }
    
    private static void objectArrayAppend(final StringBuilder sb, final Object[] array, final Map<Object[], Object> map) {
        sb.append('[');
        if (!map.containsKey(array)) {
            map.put(array, null);
            for (int length = array.length, i = 0; i < length; ++i) {
                deeplyAppendParameter(sb, array[i], map);
                if (i != length - 1) {
                    sb.append(", ");
                }
            }
            map.remove(array);
        }
        else {
            sb.append("...");
        }
        sb.append(']');
    }
    
    private static void booleanArrayAppend(final StringBuilder sb, final boolean[] array) {
        sb.append('[');
        for (int length = array.length, i = 0; i < length; ++i) {
            sb.append(array[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }
    
    private static void byteArrayAppend(final StringBuilder sb, final byte[] array) {
        sb.append('[');
        for (int length = array.length, i = 0; i < length; ++i) {
            sb.append(array[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }
    
    private static void charArrayAppend(final StringBuilder sb, final char[] array) {
        sb.append('[');
        for (int length = array.length, i = 0; i < length; ++i) {
            sb.append(array[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }
    
    private static void shortArrayAppend(final StringBuilder sb, final short[] array) {
        sb.append('[');
        for (int length = array.length, i = 0; i < length; ++i) {
            sb.append(array[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }
    
    private static void intArrayAppend(final StringBuilder sb, final int[] array) {
        sb.append('[');
        for (int length = array.length, i = 0; i < length; ++i) {
            sb.append(array[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }
    
    private static void longArrayAppend(final StringBuilder sb, final long[] array) {
        sb.append('[');
        for (int length = array.length, i = 0; i < length; ++i) {
            sb.append(array[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }
    
    private static void floatArrayAppend(final StringBuilder sb, final float[] array) {
        sb.append('[');
        for (int length = array.length, i = 0; i < length; ++i) {
            sb.append(array[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }
    
    private static void doubleArrayAppend(final StringBuilder sb, final double[] array) {
        sb.append('[');
        for (int length = array.length, i = 0; i < length; ++i) {
            sb.append(array[i]);
            if (i != length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }
    
    public static Throwable getThrowableCandidate(final Object[] array) {
        return NormalizedParameters.getThrowableCandidate(array);
    }
    
    public static Object[] trimmedCopy(final Object[] array) {
        return NormalizedParameters.trimmedCopy(array);
    }
}
