

package me.TechsCode.EnderPermissions.dependencies.commons.lang.time;

import java.util.NoSuchElementException;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.StringUtils;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils
{
    public static final TimeZone UTC_TIME_ZONE;
    public static final long MILLIS_PER_SECOND = 1000L;
    public static final long MILLIS_PER_MINUTE = 60000L;
    public static final long MILLIS_PER_HOUR = 3600000L;
    public static final long MILLIS_PER_DAY = 86400000L;
    public static final int SEMI_MONTH = 1001;
    private static final int[][] fields;
    public static final int RANGE_WEEK_SUNDAY = 1;
    public static final int RANGE_WEEK_MONDAY = 2;
    public static final int RANGE_WEEK_RELATIVE = 3;
    public static final int RANGE_WEEK_CENTER = 4;
    public static final int RANGE_MONTH_SUNDAY = 5;
    public static final int RANGE_MONTH_MONDAY = 6;
    private static final int MODIFY_TRUNCATE = 0;
    private static final int MODIFY_ROUND = 1;
    private static final int MODIFY_CEILING = 2;
    public static final int MILLIS_IN_SECOND = 1000;
    public static final int MILLIS_IN_MINUTE = 60000;
    public static final int MILLIS_IN_HOUR = 3600000;
    public static final int MILLIS_IN_DAY = 86400000;
    
    public static boolean isSameDay(final Date time, final Date time2) {
        if (time == null || time2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        final Calendar instance2 = Calendar.getInstance();
        instance2.setTime(time2);
        return isSameDay(instance, instance2);
    }
    
    public static boolean isSameDay(final Calendar calendar, final Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.get(0) == calendar2.get(0) && calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6);
    }
    
    public static boolean isSameInstant(final Date date, final Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return date.getTime() == date2.getTime();
    }
    
    public static boolean isSameInstant(final Calendar calendar, final Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.getTime().getTime() == calendar2.getTime().getTime();
    }
    
    public static boolean isSameLocalTime(final Calendar calendar, final Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar.get(14) == calendar2.get(14) && calendar.get(13) == calendar2.get(13) && calendar.get(12) == calendar2.get(12) && calendar.get(10) == calendar2.get(10) && calendar.get(6) == calendar2.get(6) && calendar.get(1) == calendar2.get(1) && calendar.get(0) == calendar2.get(0) && calendar.getClass() == calendar2.getClass();
    }
    
    public static Date parseDate(final String s, final String[] array) {
        return parseDateWithLeniency(s, array, true);
    }
    
    public static Date parseDateStrictly(final String s, final String[] array) {
        return parseDateWithLeniency(s, array, false);
    }
    
    private static Date parseDateWithLeniency(final String str, final String[] array, final boolean lenient) {
        if (str == null || array == null) {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.setLenient(lenient);
        final ParsePosition pos = new ParsePosition(0);
        for (int i = 0; i < array.length; ++i) {
            String substring = array[i];
            if (array[i].endsWith("ZZ")) {
                substring = substring.substring(0, substring.length() - 1);
            }
            simpleDateFormat.applyPattern(substring);
            pos.setIndex(0);
            String reformatTimezone = str;
            if (array[i].endsWith("ZZ")) {
                for (int j = indexOfSignChars(reformatTimezone, 0); j >= 0; j = indexOfSignChars(reformatTimezone, ++j)) {
                    reformatTimezone = reformatTimezone(reformatTimezone, j);
                }
            }
            final Date parse = simpleDateFormat.parse(reformatTimezone, pos);
            if (parse != null && pos.getIndex() == reformatTimezone.length()) {
                return parse;
            }
        }
        throw new ParseException("Unable to parse the date: " + str, -1);
    }
    
    private static int indexOfSignChars(final String s, final int n) {
        int n2 = StringUtils.indexOf(s, '+', n);
        if (n2 < 0) {
            n2 = StringUtils.indexOf(s, '-', n);
        }
        return n2;
    }
    
    private static String reformatTimezone(final String s, final int n) {
        String string = s;
        if (n >= 0 && n + 5 < s.length() && Character.isDigit(s.charAt(n + 1)) && Character.isDigit(s.charAt(n + 2)) && s.charAt(n + 3) == ':' && Character.isDigit(s.charAt(n + 4)) && Character.isDigit(s.charAt(n + 5))) {
            string = s.substring(0, n + 3) + s.substring(n + 4);
        }
        return string;
    }
    
    public static Date addYears(final Date date, final int n) {
        return add(date, 1, n);
    }
    
    public static Date addMonths(final Date date, final int n) {
        return add(date, 2, n);
    }
    
    public static Date addWeeks(final Date date, final int n) {
        return add(date, 3, n);
    }
    
    public static Date addDays(final Date date, final int n) {
        return add(date, 5, n);
    }
    
    public static Date addHours(final Date date, final int n) {
        return add(date, 11, n);
    }
    
    public static Date addMinutes(final Date date, final int n) {
        return add(date, 12, n);
    }
    
    public static Date addSeconds(final Date date, final int n) {
        return add(date, 13, n);
    }
    
    public static Date addMilliseconds(final Date date, final int n) {
        return add(date, 14, n);
    }
    
    public static Date add(final Date time, final int n, final int n2) {
        if (time == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        instance.add(n, n2);
        return instance.getTime();
    }
    
    public static Date setYears(final Date date, final int n) {
        return set(date, 1, n);
    }
    
    public static Date setMonths(final Date date, final int n) {
        return set(date, 2, n);
    }
    
    public static Date setDays(final Date date, final int n) {
        return set(date, 5, n);
    }
    
    public static Date setHours(final Date date, final int n) {
        return set(date, 11, n);
    }
    
    public static Date setMinutes(final Date date, final int n) {
        return set(date, 12, n);
    }
    
    public static Date setSeconds(final Date date, final int n) {
        return set(date, 13, n);
    }
    
    public static Date setMilliseconds(final Date date, final int n) {
        return set(date, 14, n);
    }
    
    private static Date set(final Date time, final int field, final int value) {
        if (time == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setLenient(false);
        instance.setTime(time);
        instance.set(field, value);
        return instance.getTime();
    }
    
    public static Calendar toCalendar(final Date time) {
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        return instance;
    }
    
    public static Date round(final Date time, final int n) {
        if (time == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        modify(instance, n, 1);
        return instance.getTime();
    }
    
    public static Calendar round(final Calendar calendar, final int n) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar calendar2 = (Calendar)calendar.clone();
        modify(calendar2, n, 1);
        return calendar2;
    }
    
    public static Date round(final Object obj, final int n) {
        if (obj == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (obj instanceof Date) {
            return round((Date)obj, n);
        }
        if (obj instanceof Calendar) {
            return round((Calendar)obj, n).getTime();
        }
        throw new ClassCastException("Could not round " + obj);
    }
    
    public static Date truncate(final Date time, final int n) {
        if (time == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        modify(instance, n, 0);
        return instance.getTime();
    }
    
    public static Calendar truncate(final Calendar calendar, final int n) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar calendar2 = (Calendar)calendar.clone();
        modify(calendar2, n, 0);
        return calendar2;
    }
    
    public static Date truncate(final Object obj, final int n) {
        if (obj == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (obj instanceof Date) {
            return truncate((Date)obj, n);
        }
        if (obj instanceof Calendar) {
            return truncate((Calendar)obj, n).getTime();
        }
        throw new ClassCastException("Could not truncate " + obj);
    }
    
    public static Date ceiling(final Date time, final int n) {
        if (time == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        modify(instance, n, 2);
        return instance.getTime();
    }
    
    public static Calendar ceiling(final Calendar calendar, final int n) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar calendar2 = (Calendar)calendar.clone();
        modify(calendar2, n, 2);
        return calendar2;
    }
    
    public static Date ceiling(final Object o, final int n) {
        if (o == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (o instanceof Date) {
            return ceiling((Date)o, n);
        }
        if (o instanceof Calendar) {
            return ceiling((Calendar)o, n).getTime();
        }
        throw new ClassCastException("Could not find ceiling of for type: " + o.getClass());
    }
    
    private static void modify(final Calendar calendar, final int i, final int n) {
        if (calendar.get(1) > 280000000) {
            throw new ArithmeticException("Calendar value too large for accurate calculations");
        }
        if (i == 14) {
            return;
        }
        final Date time = calendar.getTime();
        long time2 = time.getTime();
        int n2 = 0;
        final int value = calendar.get(14);
        if (0 == n || value < 500) {
            time2 -= value;
        }
        if (i == 13) {
            n2 = 1;
        }
        final int value2 = calendar.get(13);
        if (n2 == 0 && (0 == n || value2 < 30)) {
            time2 -= value2 * 1000L;
        }
        if (i == 12) {
            n2 = 1;
        }
        final int value3 = calendar.get(12);
        if (n2 == 0 && (0 == n || value3 < 30)) {
            time2 -= value3 * 60000L;
        }
        if (time.getTime() != time2) {
            time.setTime(time2);
            calendar.setTime(time);
        }
        boolean b = false;
        for (int j = 0; j < DateUtils.fields.length; ++j) {
            for (int k = 0; k < DateUtils.fields[j].length; ++k) {
                if (DateUtils.fields[j][k] == i) {
                    if (n == 2 || (n == 1 && b)) {
                        if (i == 1001) {
                            if (calendar.get(5) == 1) {
                                calendar.add(5, 15);
                            }
                            else {
                                calendar.add(5, -15);
                                calendar.add(2, 1);
                            }
                        }
                        else if (i == 9) {
                            if (calendar.get(11) == 0) {
                                calendar.add(11, 12);
                            }
                            else {
                                calendar.add(11, -12);
                                calendar.add(5, 1);
                            }
                        }
                        else {
                            calendar.add(DateUtils.fields[j][0], 1);
                        }
                    }
                    return;
                }
            }
            int value4 = 0;
            boolean b2 = false;
            switch (i) {
                case 1001: {
                    if (DateUtils.fields[j][0] == 5) {
                        value4 = calendar.get(5) - 1;
                        if (value4 >= 15) {
                            value4 -= 15;
                        }
                        b = (value4 > 7);
                        b2 = true;
                        break;
                    }
                    break;
                }
                case 9: {
                    if (DateUtils.fields[j][0] == 11) {
                        value4 = calendar.get(11);
                        if (value4 >= 12) {
                            value4 -= 12;
                        }
                        b = (value4 >= 6);
                        b2 = true;
                        break;
                    }
                    break;
                }
            }
            if (!b2) {
                final int actualMinimum = calendar.getActualMinimum(DateUtils.fields[j][0]);
                final int actualMaximum = calendar.getActualMaximum(DateUtils.fields[j][0]);
                value4 = calendar.get(DateUtils.fields[j][0]) - actualMinimum;
                b = (value4 > (actualMaximum - actualMinimum) / 2);
            }
            if (value4 != 0) {
                calendar.set(DateUtils.fields[j][0], calendar.get(DateUtils.fields[j][0]) - value4);
            }
        }
        throw new IllegalArgumentException("The field " + i + " is not supported");
    }
    
    public static Iterator iterator(final Date time, final int n) {
        if (time == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        return iterator(instance, n);
    }
    
    public static Iterator iterator(final Calendar calendar, final int i) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        int value = 1;
        int n = 7;
        Calendar calendar2 = null;
        Calendar truncate = null;
        switch (i) {
            case 5:
            case 6: {
                calendar2 = truncate(calendar, 2);
                truncate = (Calendar)calendar2.clone();
                truncate.add(2, 1);
                truncate.add(5, -1);
                if (i == 6) {
                    value = 2;
                    n = 1;
                    break;
                }
                break;
            }
            case 1:
            case 2:
            case 3:
            case 4: {
                calendar2 = truncate(calendar, 5);
                truncate = truncate(calendar, 5);
                switch (i) {
                    case 2: {
                        value = 2;
                        n = 1;
                        break;
                    }
                    case 3: {
                        value = calendar.get(7);
                        n = value - 1;
                        break;
                    }
                    case 4: {
                        value = calendar.get(7) - 3;
                        n = calendar.get(7) + 3;
                        break;
                    }
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("The range style " + i + " is not valid.");
            }
        }
        if (value < 1) {
            value += 7;
        }
        if (value > 7) {
            value -= 7;
        }
        if (n < 1) {
            n += 7;
        }
        if (n > 7) {
            n -= 7;
        }
        while (calendar2.get(7) != value) {
            calendar2.add(5, -1);
        }
        while (truncate.get(7) != n) {
            truncate.add(5, 1);
        }
        return new DateIterator(calendar2, truncate);
    }
    
    public static Iterator iterator(final Object obj, final int n) {
        if (obj == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (obj instanceof Date) {
            return iterator((Date)obj, n);
        }
        if (obj instanceof Calendar) {
            return iterator((Calendar)obj, n);
        }
        throw new ClassCastException("Could not iterate based on " + obj);
    }
    
    public static long getFragmentInMilliseconds(final Date date, final int n) {
        return getFragment(date, n, 14);
    }
    
    public static long getFragmentInSeconds(final Date date, final int n) {
        return getFragment(date, n, 13);
    }
    
    public static long getFragmentInMinutes(final Date date, final int n) {
        return getFragment(date, n, 12);
    }
    
    public static long getFragmentInHours(final Date date, final int n) {
        return getFragment(date, n, 11);
    }
    
    public static long getFragmentInDays(final Date date, final int n) {
        return getFragment(date, n, 6);
    }
    
    public static long getFragmentInMilliseconds(final Calendar calendar, final int n) {
        return getFragment(calendar, n, 14);
    }
    
    public static long getFragmentInSeconds(final Calendar calendar, final int n) {
        return getFragment(calendar, n, 13);
    }
    
    public static long getFragmentInMinutes(final Calendar calendar, final int n) {
        return getFragment(calendar, n, 12);
    }
    
    public static long getFragmentInHours(final Calendar calendar, final int n) {
        return getFragment(calendar, n, 11);
    }
    
    public static long getFragmentInDays(final Calendar calendar, final int n) {
        return getFragment(calendar, n, 6);
    }
    
    private static long getFragment(final Date time, final int n, final int n2) {
        if (time == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar instance = Calendar.getInstance();
        instance.setTime(time);
        return getFragment(instance, n, n2);
    }
    
    private static long getFragment(final Calendar calendar, final int i, final int n) {
        if (calendar == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final long millisPerUnit = getMillisPerUnit(n);
        long n2 = 0L;
        switch (i) {
            case 1: {
                n2 += calendar.get(6) * 86400000L / millisPerUnit;
                break;
            }
            case 2: {
                n2 += calendar.get(5) * 86400000L / millisPerUnit;
                break;
            }
        }
        switch (i) {
            case 1:
            case 2:
            case 5:
            case 6: {
                n2 += calendar.get(11) * 3600000L / millisPerUnit;
            }
            case 11: {
                n2 += calendar.get(12) * 60000L / millisPerUnit;
            }
            case 12: {
                n2 += calendar.get(13) * 1000L / millisPerUnit;
            }
            case 13: {
                n2 += calendar.get(14) * 1 / millisPerUnit;
                break;
            }
            case 14: {
                break;
            }
            default: {
                throw new IllegalArgumentException("The fragment " + i + " is not supported");
            }
        }
        return n2;
    }
    
    public static boolean truncatedEquals(final Calendar calendar, final Calendar calendar2, final int n) {
        return truncatedCompareTo(calendar, calendar2, n) == 0;
    }
    
    public static boolean truncatedEquals(final Date date, final Date date2, final int n) {
        return truncatedCompareTo(date, date2, n) == 0;
    }
    
    public static int truncatedCompareTo(final Calendar calendar, final Calendar calendar2, final int n) {
        return truncate(calendar, n).getTime().compareTo(truncate(calendar2, n).getTime());
    }
    
    public static int truncatedCompareTo(final Date date, final Date date2, final int n) {
        return truncate(date, n).compareTo(truncate(date2, n));
    }
    
    private static long getMillisPerUnit(final int i) {
        long n = 0L;
        switch (i) {
            case 5:
            case 6: {
                n = 86400000L;
                break;
            }
            case 11: {
                n = 3600000L;
                break;
            }
            case 12: {
                n = 60000L;
                break;
            }
            case 13: {
                n = 1000L;
                break;
            }
            case 14: {
                n = 1L;
                break;
            }
            default: {
                throw new IllegalArgumentException("The unit " + i + " cannot be represented is milleseconds");
            }
        }
        return n;
    }
    
    static {
        UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");
        fields = new int[][] { { 14 }, { 13 }, { 12 }, { 11, 10 }, { 5, 5, 9 }, { 2, 1001 }, { 1 }, { 0 } };
    }
    
    static class DateIterator implements Iterator
    {
        private final Calendar endFinal;
        private final Calendar spot;
        
        DateIterator(final Calendar spot, final Calendar endFinal) {
            this.endFinal = endFinal;
            (this.spot = spot).add(5, -1);
        }
        
        public boolean hasNext() {
            return this.spot.before(this.endFinal);
        }
        
        public Object next() {
            if (this.spot.equals(this.endFinal)) {
                throw new NoSuchElementException();
            }
            this.spot.add(5, 1);
            return this.spot.clone();
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
