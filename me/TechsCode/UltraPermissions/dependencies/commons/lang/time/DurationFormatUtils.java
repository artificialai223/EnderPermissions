

package me.TechsCode.EnderPermissions.dependencies.commons.lang.time;

import java.util.ArrayList;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.text.StrBuilder;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.StringUtils;

public class DurationFormatUtils
{
    public static final String ISO_EXTENDED_FORMAT_PATTERN = "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'";
    static final Object y;
    static final Object M;
    static final Object d;
    static final Object H;
    static final Object m;
    static final Object s;
    static final Object S;
    
    public static String formatDurationHMS(final long n) {
        return formatDuration(n, "H:mm:ss.SSS");
    }
    
    public static String formatDurationISO(final long n) {
        return formatDuration(n, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'", false);
    }
    
    public static String formatDuration(final long n, final String s) {
        return formatDuration(n, s, true);
    }
    
    public static String formatDuration(long n, final String s, final boolean b) {
        final Token[] lexx = lexx(s);
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        if (Token.containsTokenWithValue(lexx, DurationFormatUtils.d)) {
            n2 = (int)(n / 86400000L);
            n -= n2 * 86400000L;
        }
        if (Token.containsTokenWithValue(lexx, DurationFormatUtils.H)) {
            n3 = (int)(n / 3600000L);
            n -= n3 * 3600000L;
        }
        if (Token.containsTokenWithValue(lexx, DurationFormatUtils.m)) {
            n4 = (int)(n / 60000L);
            n -= n4 * 60000L;
        }
        if (Token.containsTokenWithValue(lexx, DurationFormatUtils.s)) {
            n5 = (int)(n / 1000L);
            n -= n5 * 1000L;
        }
        if (Token.containsTokenWithValue(lexx, DurationFormatUtils.S)) {
            n6 = (int)n;
        }
        return format(lexx, 0, 0, n2, n3, n4, n5, n6, b);
    }
    
    public static String formatDurationWords(final long n, final boolean b, final boolean b2) {
        String s = formatDuration(n, "d' days 'H' hours 'm' minutes 's' seconds'");
        if (b) {
            s = " " + s;
            final String replaceOnce = StringUtils.replaceOnce(s, " 0 days", "");
            if (replaceOnce.length() != s.length()) {
                s = replaceOnce;
                final String replaceOnce2 = StringUtils.replaceOnce(s, " 0 hours", "");
                if (replaceOnce2.length() != s.length()) {
                    final String s2 = s = StringUtils.replaceOnce(replaceOnce2, " 0 minutes", "");
                    if (s2.length() != s.length()) {
                        s = StringUtils.replaceOnce(s2, " 0 seconds", "");
                    }
                }
            }
            if (s.length() != 0) {
                s = s.substring(1);
            }
        }
        if (b2) {
            final String replaceOnce3 = StringUtils.replaceOnce(s, " 0 seconds", "");
            if (replaceOnce3.length() != s.length()) {
                s = replaceOnce3;
                final String replaceOnce4 = StringUtils.replaceOnce(s, " 0 minutes", "");
                if (replaceOnce4.length() != s.length()) {
                    s = replaceOnce4;
                    final String replaceOnce5 = StringUtils.replaceOnce(s, " 0 hours", "");
                    if (replaceOnce5.length() != s.length()) {
                        s = StringUtils.replaceOnce(replaceOnce5, " 0 days", "");
                    }
                }
            }
        }
        return StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.replaceOnce(" " + s, " 1 seconds", " 1 second"), " 1 minutes", " 1 minute"), " 1 hours", " 1 hour"), " 1 days", " 1 day").trim();
    }
    
    public static String formatPeriodISO(final long n, final long n2) {
        return formatPeriod(n, n2, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'", false, TimeZone.getDefault());
    }
    
    public static String formatPeriod(final long n, final long n2, final String s) {
        return formatPeriod(n, n2, s, true, TimeZone.getDefault());
    }
    
    public static String formatPeriod(final long date, final long date2, final String s, final boolean b, final TimeZone timeZone) {
        final Token[] lexx = lexx(s);
        final Calendar instance = Calendar.getInstance(timeZone);
        instance.setTime(new Date(date));
        final Calendar instance2 = Calendar.getInstance(timeZone);
        instance2.setTime(new Date(date2));
        int i = instance2.get(14) - instance.get(14);
        int j = instance2.get(13) - instance.get(13);
        int k = instance2.get(12) - instance.get(12);
        int l = instance2.get(11) - instance.get(11);
        int n = instance2.get(5) - instance.get(5);
        int n2 = instance2.get(2) - instance.get(2);
        int n3 = instance2.get(1) - instance.get(1);
        while (i < 0) {
            i += 1000;
            --j;
        }
        while (j < 0) {
            j += 60;
            --k;
        }
        while (k < 0) {
            k += 60;
            --l;
        }
        while (l < 0) {
            l += 24;
            --n;
        }
        if (Token.containsTokenWithValue(lexx, DurationFormatUtils.M)) {
            while (n < 0) {
                n += instance.getActualMaximum(5);
                --n2;
                instance.add(2, 1);
            }
            while (n2 < 0) {
                n2 += 12;
                --n3;
            }
            if (!Token.containsTokenWithValue(lexx, DurationFormatUtils.y) && n3 != 0) {
                while (n3 != 0) {
                    n2 += 12 * n3;
                    n3 = 0;
                }
            }
        }
        else {
            if (!Token.containsTokenWithValue(lexx, DurationFormatUtils.y)) {
                int value = instance2.get(1);
                if (n2 < 0) {
                    --value;
                }
                while (instance.get(1) != value) {
                    int n4 = n + (instance.getActualMaximum(6) - instance.get(6));
                    if (instance instanceof GregorianCalendar && instance.get(2) == 1 && instance.get(5) == 29) {
                        ++n4;
                    }
                    instance.add(1, 1);
                    n = n4 + instance.get(6);
                }
                n3 = 0;
            }
            while (instance.get(2) != instance2.get(2)) {
                n += instance.getActualMaximum(5);
                instance.add(2, 1);
            }
            n2 = 0;
            while (n < 0) {
                n += instance.getActualMaximum(5);
                --n2;
                instance.add(2, 1);
            }
        }
        if (!Token.containsTokenWithValue(lexx, DurationFormatUtils.d)) {
            l += 24 * n;
            n = 0;
        }
        if (!Token.containsTokenWithValue(lexx, DurationFormatUtils.H)) {
            k += 60 * l;
            l = 0;
        }
        if (!Token.containsTokenWithValue(lexx, DurationFormatUtils.m)) {
            j += 60 * k;
            k = 0;
        }
        if (!Token.containsTokenWithValue(lexx, DurationFormatUtils.s)) {
            i += 1000 * j;
            j = 0;
        }
        return format(lexx, n3, n2, n, l, k, j, i, b);
    }
    
    static String format(final Token[] array, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, int n7, final boolean b) {
        final StrBuilder strBuilder = new StrBuilder();
        int n8 = 0;
        for (final Token token : array) {
            final Object value = token.getValue();
            final int count = token.getCount();
            if (value instanceof StringBuffer) {
                strBuilder.append(value.toString());
            }
            else if (value == DurationFormatUtils.y) {
                strBuilder.append(b ? StringUtils.leftPad(Integer.toString(n), count, '0') : Integer.toString(n));
                n8 = 0;
            }
            else if (value == DurationFormatUtils.M) {
                strBuilder.append(b ? StringUtils.leftPad(Integer.toString(n2), count, '0') : Integer.toString(n2));
                n8 = 0;
            }
            else if (value == DurationFormatUtils.d) {
                strBuilder.append(b ? StringUtils.leftPad(Integer.toString(n3), count, '0') : Integer.toString(n3));
                n8 = 0;
            }
            else if (value == DurationFormatUtils.H) {
                strBuilder.append(b ? StringUtils.leftPad(Integer.toString(n4), count, '0') : Integer.toString(n4));
                n8 = 0;
            }
            else if (value == DurationFormatUtils.m) {
                strBuilder.append(b ? StringUtils.leftPad(Integer.toString(n5), count, '0') : Integer.toString(n5));
                n8 = 0;
            }
            else if (value == DurationFormatUtils.s) {
                strBuilder.append(b ? StringUtils.leftPad(Integer.toString(n6), count, '0') : Integer.toString(n6));
                n8 = 1;
            }
            else if (value == DurationFormatUtils.S) {
                if (n8 != 0) {
                    n7 += 1000;
                    strBuilder.append((b ? StringUtils.leftPad(Integer.toString(n7), count, '0') : Integer.toString(n7)).substring(1));
                }
                else {
                    strBuilder.append(b ? StringUtils.leftPad(Integer.toString(n7), count, '0') : Integer.toString(n7));
                }
                n8 = 0;
            }
        }
        return strBuilder.toString();
    }
    
    static Token[] lexx(final String s) {
        final char[] charArray = s.toCharArray();
        final ArrayList list = new ArrayList<Token>(charArray.length);
        int n = 0;
        StringBuffer sb = null;
        Token token = null;
        for (final char c : charArray) {
            if (n != 0 && c != '\'') {
                sb.append(c);
            }
            else {
                Object o = null;
                switch (c) {
                    case '\'': {
                        if (n != 0) {
                            sb = null;
                            n = 0;
                            break;
                        }
                        sb = new StringBuffer();
                        list.add(new Token(sb));
                        n = 1;
                        break;
                    }
                    case 'y': {
                        o = DurationFormatUtils.y;
                        break;
                    }
                    case 'M': {
                        o = DurationFormatUtils.M;
                        break;
                    }
                    case 'd': {
                        o = DurationFormatUtils.d;
                        break;
                    }
                    case 'H': {
                        o = DurationFormatUtils.H;
                        break;
                    }
                    case 'm': {
                        o = DurationFormatUtils.m;
                        break;
                    }
                    case 's': {
                        o = DurationFormatUtils.s;
                        break;
                    }
                    case 'S': {
                        o = DurationFormatUtils.S;
                        break;
                    }
                    default: {
                        if (sb == null) {
                            sb = new StringBuffer();
                            list.add(new Token(sb));
                        }
                        sb.append(c);
                        break;
                    }
                }
                if (o != null) {
                    if (token != null && token.getValue() == o) {
                        token.increment();
                    }
                    else {
                        final Token e = new Token(o);
                        list.add(e);
                        token = e;
                    }
                    sb = null;
                }
            }
        }
        return list.toArray(new Token[list.size()]);
    }
    
    static {
        y = "y";
        M = "M";
        d = "d";
        H = "H";
        m = "m";
        s = "s";
        S = "S";
    }
    
    static class Token
    {
        private Object value;
        private int count;
        
        static boolean containsTokenWithValue(final Token[] array, final Object o) {
            for (int length = array.length, i = 0; i < length; ++i) {
                if (array[i].getValue() == o) {
                    return true;
                }
            }
            return false;
        }
        
        Token(final Object value) {
            this.value = value;
            this.count = 1;
        }
        
        Token(final Object value, final int count) {
            this.value = value;
            this.count = count;
        }
        
        void increment() {
            ++this.count;
        }
        
        int getCount() {
            return this.count;
        }
        
        Object getValue() {
            return this.value;
        }
        
        public boolean equals(final Object o) {
            if (!(o instanceof Token)) {
                return false;
            }
            final Token token = (Token)o;
            if (this.value.getClass() != token.value.getClass()) {
                return false;
            }
            if (this.count != token.count) {
                return false;
            }
            if (this.value instanceof StringBuffer) {
                return this.value.toString().equals(token.value.toString());
            }
            if (this.value instanceof Number) {
                return this.value.equals(token.value);
            }
            return this.value == token.value;
        }
        
        public int hashCode() {
            return this.value.hashCode();
        }
        
        public String toString() {
            return StringUtils.repeat(this.value.toString(), this.count);
        }
    }
}
