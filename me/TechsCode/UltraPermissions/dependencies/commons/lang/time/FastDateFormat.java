

package me.TechsCode.EnderPermissions.dependencies.commons.lang.time;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.Validate;
import java.util.HashMap;
import java.io.ObjectInputStream;
import java.text.ParsePosition;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.text.FieldPosition;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.text.StrBuilder;
import java.util.ArrayList;
import java.text.DateFormatSymbols;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Map;
import java.text.Format;

public class FastDateFormat extends Format
{
    private static final long serialVersionUID = 1L;
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    private static String cDefaultPattern;
    private static final Map cInstanceCache;
    private static final Map cDateInstanceCache;
    private static final Map cTimeInstanceCache;
    private static final Map cDateTimeInstanceCache;
    private static final Map cTimeZoneDisplayCache;
    private final String mPattern;
    private final TimeZone mTimeZone;
    private final boolean mTimeZoneForced;
    private final Locale mLocale;
    private final boolean mLocaleForced;
    private transient Rule[] mRules;
    private transient int mMaxLengthEstimate;
    
    public static FastDateFormat getInstance() {
        return getInstance(getDefaultPattern(), null, null);
    }
    
    public static FastDateFormat getInstance(final String s) {
        return getInstance(s, null, null);
    }
    
    public static FastDateFormat getInstance(final String s, final TimeZone timeZone) {
        return getInstance(s, timeZone, null);
    }
    
    public static FastDateFormat getInstance(final String s, final Locale locale) {
        return getInstance(s, null, locale);
    }
    
    public static synchronized FastDateFormat getInstance(final String s, final TimeZone timeZone, final Locale locale) {
        final FastDateFormat fastDateFormat = new FastDateFormat(s, timeZone, locale);
        FastDateFormat fastDateFormat2 = FastDateFormat.cInstanceCache.get(fastDateFormat);
        if (fastDateFormat2 == null) {
            fastDateFormat2 = fastDateFormat;
            fastDateFormat2.init();
            FastDateFormat.cInstanceCache.put(fastDateFormat2, fastDateFormat2);
        }
        return fastDateFormat2;
    }
    
    public static FastDateFormat getDateInstance(final int n) {
        return getDateInstance(n, null, null);
    }
    
    public static FastDateFormat getDateInstance(final int n, final Locale locale) {
        return getDateInstance(n, null, locale);
    }
    
    public static FastDateFormat getDateInstance(final int n, final TimeZone timeZone) {
        return getDateInstance(n, timeZone, null);
    }
    
    public static synchronized FastDateFormat getDateInstance(final int n, final TimeZone timeZone, Locale default1) {
        Object o = new Integer(n);
        if (timeZone != null) {
            o = new Pair(o, timeZone);
        }
        if (default1 == null) {
            default1 = Locale.getDefault();
        }
        final Pair pair = new Pair(o, default1);
        FastDateFormat instance = FastDateFormat.cDateInstanceCache.get(pair);
        if (instance == null) {
            try {
                instance = getInstance(((SimpleDateFormat)DateFormat.getDateInstance(n, default1)).toPattern(), timeZone, default1);
                FastDateFormat.cDateInstanceCache.put(pair, instance);
            }
            catch (ClassCastException ex) {
                throw new IllegalArgumentException("No date pattern for locale: " + default1);
            }
        }
        return instance;
    }
    
    public static FastDateFormat getTimeInstance(final int n) {
        return getTimeInstance(n, null, null);
    }
    
    public static FastDateFormat getTimeInstance(final int n, final Locale locale) {
        return getTimeInstance(n, null, locale);
    }
    
    public static FastDateFormat getTimeInstance(final int n, final TimeZone timeZone) {
        return getTimeInstance(n, timeZone, null);
    }
    
    public static synchronized FastDateFormat getTimeInstance(final int n, final TimeZone timeZone, Locale default1) {
        Object o = new Integer(n);
        if (timeZone != null) {
            o = new Pair(o, timeZone);
        }
        if (default1 != null) {
            o = new Pair(o, default1);
        }
        FastDateFormat instance = FastDateFormat.cTimeInstanceCache.get(o);
        if (instance == null) {
            if (default1 == null) {
                default1 = Locale.getDefault();
            }
            try {
                instance = getInstance(((SimpleDateFormat)DateFormat.getTimeInstance(n, default1)).toPattern(), timeZone, default1);
                FastDateFormat.cTimeInstanceCache.put(o, instance);
            }
            catch (ClassCastException ex) {
                throw new IllegalArgumentException("No date pattern for locale: " + default1);
            }
        }
        return instance;
    }
    
    public static FastDateFormat getDateTimeInstance(final int n, final int n2) {
        return getDateTimeInstance(n, n2, null, null);
    }
    
    public static FastDateFormat getDateTimeInstance(final int n, final int n2, final Locale locale) {
        return getDateTimeInstance(n, n2, null, locale);
    }
    
    public static FastDateFormat getDateTimeInstance(final int n, final int n2, final TimeZone timeZone) {
        return getDateTimeInstance(n, n2, timeZone, null);
    }
    
    public static synchronized FastDateFormat getDateTimeInstance(final int n, final int n2, final TimeZone timeZone, Locale default1) {
        Pair pair = new Pair(new Integer(n), new Integer(n2));
        if (timeZone != null) {
            pair = new Pair(pair, timeZone);
        }
        if (default1 == null) {
            default1 = Locale.getDefault();
        }
        final Pair pair2 = new Pair(pair, default1);
        FastDateFormat instance = FastDateFormat.cDateTimeInstanceCache.get(pair2);
        if (instance == null) {
            try {
                instance = getInstance(((SimpleDateFormat)DateFormat.getDateTimeInstance(n, n2, default1)).toPattern(), timeZone, default1);
                FastDateFormat.cDateTimeInstanceCache.put(pair2, instance);
            }
            catch (ClassCastException ex) {
                throw new IllegalArgumentException("No date time pattern for locale: " + default1);
            }
        }
        return instance;
    }
    
    static synchronized String getTimeZoneDisplay(final TimeZone timeZone, final boolean daylight, final int style, final Locale locale) {
        final TimeZoneDisplayKey timeZoneDisplayKey = new TimeZoneDisplayKey(timeZone, daylight, style, locale);
        String displayName = FastDateFormat.cTimeZoneDisplayCache.get(timeZoneDisplayKey);
        if (displayName == null) {
            displayName = timeZone.getDisplayName(daylight, style, locale);
            FastDateFormat.cTimeZoneDisplayCache.put(timeZoneDisplayKey, displayName);
        }
        return displayName;
    }
    
    private static synchronized String getDefaultPattern() {
        if (FastDateFormat.cDefaultPattern == null) {
            FastDateFormat.cDefaultPattern = new SimpleDateFormat().toPattern();
        }
        return FastDateFormat.cDefaultPattern;
    }
    
    protected FastDateFormat(final String mPattern, TimeZone default1, Locale default2) {
        if (mPattern == null) {
            throw new IllegalArgumentException("The pattern must not be null");
        }
        this.mPattern = mPattern;
        this.mTimeZoneForced = (default1 != null);
        if (default1 == null) {
            default1 = TimeZone.getDefault();
        }
        this.mTimeZone = default1;
        this.mLocaleForced = (default2 != null);
        if (default2 == null) {
            default2 = Locale.getDefault();
        }
        this.mLocale = default2;
    }
    
    protected void init() {
        final List pattern = this.parsePattern();
        this.mRules = pattern.toArray(new Rule[pattern.size()]);
        int mMaxLengthEstimate = 0;
        int length = this.mRules.length;
        while (--length >= 0) {
            mMaxLengthEstimate += this.mRules[length].estimateLength();
        }
        this.mMaxLengthEstimate = mMaxLengthEstimate;
    }
    
    protected List parsePattern() {
        final DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(this.mLocale);
        final ArrayList<Object> list = new ArrayList<Object>();
        final String[] eras = dateFormatSymbols.getEras();
        final String[] months = dateFormatSymbols.getMonths();
        final String[] shortMonths = dateFormatSymbols.getShortMonths();
        final String[] weekdays = dateFormatSymbols.getWeekdays();
        final String[] shortWeekdays = dateFormatSymbols.getShortWeekdays();
        final String[] amPmStrings = dateFormatSymbols.getAmPmStrings();
        final int length = this.mPattern.length();
        final int[] array = { 0 };
        for (int i = 0; i < length; ++i) {
            array[0] = i;
            final String token = this.parseToken(this.mPattern, array);
            i = array[0];
            final int length2 = token.length();
            if (length2 == 0) {
                break;
            }
            Object o = null;
            switch (token.charAt(0)) {
                case 'G': {
                    o = new TextField(0, eras);
                    break;
                }
                case 'y': {
                    if (length2 >= 4) {
                        o = this.selectNumberRule(1, length2);
                        break;
                    }
                    o = TwoDigitYearField.INSTANCE;
                    break;
                }
                case 'M': {
                    if (length2 >= 4) {
                        o = new TextField(2, months);
                        break;
                    }
                    if (length2 == 3) {
                        o = new TextField(2, shortMonths);
                        break;
                    }
                    if (length2 == 2) {
                        o = TwoDigitMonthField.INSTANCE;
                        break;
                    }
                    o = UnpaddedMonthField.INSTANCE;
                    break;
                }
                case 'd': {
                    o = this.selectNumberRule(5, length2);
                    break;
                }
                case 'h': {
                    o = new TwelveHourField(this.selectNumberRule(10, length2));
                    break;
                }
                case 'H': {
                    o = this.selectNumberRule(11, length2);
                    break;
                }
                case 'm': {
                    o = this.selectNumberRule(12, length2);
                    break;
                }
                case 's': {
                    o = this.selectNumberRule(13, length2);
                    break;
                }
                case 'S': {
                    o = this.selectNumberRule(14, length2);
                    break;
                }
                case 'E': {
                    o = new TextField(7, (length2 < 4) ? shortWeekdays : weekdays);
                    break;
                }
                case 'D': {
                    o = this.selectNumberRule(6, length2);
                    break;
                }
                case 'F': {
                    o = this.selectNumberRule(8, length2);
                    break;
                }
                case 'w': {
                    o = this.selectNumberRule(3, length2);
                    break;
                }
                case 'W': {
                    o = this.selectNumberRule(4, length2);
                    break;
                }
                case 'a': {
                    o = new TextField(9, amPmStrings);
                    break;
                }
                case 'k': {
                    o = new TwentyFourHourField(this.selectNumberRule(11, length2));
                    break;
                }
                case 'K': {
                    o = this.selectNumberRule(10, length2);
                    break;
                }
                case 'z': {
                    if (length2 >= 4) {
                        o = new TimeZoneNameRule(this.mTimeZone, this.mTimeZoneForced, this.mLocale, 1);
                        break;
                    }
                    o = new TimeZoneNameRule(this.mTimeZone, this.mTimeZoneForced, this.mLocale, 0);
                    break;
                }
                case 'Z': {
                    if (length2 == 1) {
                        o = TimeZoneNumberRule.INSTANCE_NO_COLON;
                        break;
                    }
                    o = TimeZoneNumberRule.INSTANCE_COLON;
                    break;
                }
                case '\'': {
                    final String substring = token.substring(1);
                    if (substring.length() == 1) {
                        o = new CharacterLiteral(substring.charAt(0));
                        break;
                    }
                    o = new StringLiteral(substring);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Illegal pattern component: " + token);
                }
            }
            list.add(o);
        }
        return list;
    }
    
    protected String parseToken(final String s, final int[] array) {
        final StrBuilder strBuilder = new StrBuilder();
        int i = array[0];
        final int length = s.length();
        final char char1 = s.charAt(i);
        if ((char1 >= 'A' && char1 <= 'Z') || (char1 >= 'a' && char1 <= 'z')) {
            strBuilder.append(char1);
            while (i + 1 < length && s.charAt(i + 1) == char1) {
                strBuilder.append(char1);
                ++i;
            }
        }
        else {
            strBuilder.append('\'');
            boolean b = false;
            while (i < length) {
                final char char2 = s.charAt(i);
                if (char2 == '\'') {
                    if (i + 1 < length && s.charAt(i + 1) == '\'') {
                        ++i;
                        strBuilder.append(char2);
                    }
                    else {
                        b = !b;
                    }
                }
                else {
                    if (!b && ((char2 >= 'A' && char2 <= 'Z') || (char2 >= 'a' && char2 <= 'z'))) {
                        --i;
                        break;
                    }
                    strBuilder.append(char2);
                }
                ++i;
            }
        }
        array[0] = i;
        return strBuilder.toString();
    }
    
    protected NumberRule selectNumberRule(final int n, final int n2) {
        switch (n2) {
            case 1: {
                return new UnpaddedNumberField(n);
            }
            case 2: {
                return new TwoDigitNumberField(n);
            }
            default: {
                return new PaddedNumberField(n, n2);
            }
        }
    }
    
    public StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        if (o instanceof Date) {
            return this.format((Date)o, sb);
        }
        if (o instanceof Calendar) {
            return this.format((Calendar)o, sb);
        }
        if (o instanceof Long) {
            return this.format((long)o, sb);
        }
        throw new IllegalArgumentException("Unknown class: " + ((o == null) ? "<null>" : o.getClass().getName()));
    }
    
    public String format(final long date) {
        return this.format(new Date(date));
    }
    
    public String format(final Date time) {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar(this.mTimeZone, this.mLocale);
        gregorianCalendar.setTime(time);
        return this.applyRules(gregorianCalendar, new StringBuffer(this.mMaxLengthEstimate)).toString();
    }
    
    public String format(final Calendar calendar) {
        return this.format(calendar, new StringBuffer(this.mMaxLengthEstimate)).toString();
    }
    
    public StringBuffer format(final long date, final StringBuffer sb) {
        return this.format(new Date(date), sb);
    }
    
    public StringBuffer format(final Date time, final StringBuffer sb) {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar(this.mTimeZone);
        gregorianCalendar.setTime(time);
        return this.applyRules(gregorianCalendar, sb);
    }
    
    public StringBuffer format(Calendar calendar, final StringBuffer sb) {
        if (this.mTimeZoneForced) {
            calendar.getTime();
            calendar = (Calendar)calendar.clone();
            calendar.setTimeZone(this.mTimeZone);
        }
        return this.applyRules(calendar, sb);
    }
    
    protected StringBuffer applyRules(final Calendar calendar, final StringBuffer sb) {
        final Rule[] mRules = this.mRules;
        for (int length = this.mRules.length, i = 0; i < length; ++i) {
            mRules[i].appendTo(sb, calendar);
        }
        return sb;
    }
    
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        parsePosition.setIndex(0);
        parsePosition.setErrorIndex(0);
        return null;
    }
    
    public String getPattern() {
        return this.mPattern;
    }
    
    public TimeZone getTimeZone() {
        return this.mTimeZone;
    }
    
    public boolean getTimeZoneOverridesCalendar() {
        return this.mTimeZoneForced;
    }
    
    public Locale getLocale() {
        return this.mLocale;
    }
    
    public int getMaxLengthEstimate() {
        return this.mMaxLengthEstimate;
    }
    
    public boolean equals(final Object o) {
        if (!(o instanceof FastDateFormat)) {
            return false;
        }
        final FastDateFormat fastDateFormat = (FastDateFormat)o;
        return (this.mPattern == fastDateFormat.mPattern || this.mPattern.equals(fastDateFormat.mPattern)) && (this.mTimeZone == fastDateFormat.mTimeZone || this.mTimeZone.equals(fastDateFormat.mTimeZone)) && (this.mLocale == fastDateFormat.mLocale || this.mLocale.equals(fastDateFormat.mLocale)) && this.mTimeZoneForced == fastDateFormat.mTimeZoneForced && this.mLocaleForced == fastDateFormat.mLocaleForced;
    }
    
    public int hashCode() {
        return 0 + this.mPattern.hashCode() + this.mTimeZone.hashCode() + (this.mTimeZoneForced ? 1 : 0) + this.mLocale.hashCode() + (this.mLocaleForced ? 1 : 0);
    }
    
    public String toString() {
        return "FastDateFormat[" + this.mPattern + "]";
    }
    
    private void readObject(final ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        this.init();
    }
    
    static {
        cInstanceCache = new HashMap(7);
        cDateInstanceCache = new HashMap(7);
        cTimeInstanceCache = new HashMap(7);
        cDateTimeInstanceCache = new HashMap(7);
        cTimeZoneDisplayCache = new HashMap(7);
    }
    
    private static class CharacterLiteral implements Rule
    {
        private final char mValue;
        
        CharacterLiteral(final char mValue) {
            this.mValue = mValue;
        }
        
        public int estimateLength() {
            return 1;
        }
        
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            sb.append(this.mValue);
        }
    }
    
    private static class StringLiteral implements Rule
    {
        private final String mValue;
        
        StringLiteral(final String mValue) {
            this.mValue = mValue;
        }
        
        public int estimateLength() {
            return this.mValue.length();
        }
        
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            sb.append(this.mValue);
        }
    }
    
    private static class TextField implements Rule
    {
        private final int mField;
        private final String[] mValues;
        
        TextField(final int mField, final String[] mValues) {
            this.mField = mField;
            this.mValues = mValues;
        }
        
        public int estimateLength() {
            int n = 0;
            int length = this.mValues.length;
            while (--length >= 0) {
                final int length2 = this.mValues[length].length();
                if (length2 > n) {
                    n = length2;
                }
            }
            return n;
        }
        
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            sb.append(this.mValues[calendar.get(this.mField)]);
        }
    }
    
    private static class UnpaddedNumberField implements NumberRule
    {
        private final int mField;
        
        UnpaddedNumberField(final int mField) {
            this.mField = mField;
        }
        
        public int estimateLength() {
            return 4;
        }
        
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            this.appendTo(sb, calendar.get(this.mField));
        }
        
        public final void appendTo(final StringBuffer sb, final int i) {
            if (i < 10) {
                sb.append((char)(i + 48));
            }
            else if (i < 100) {
                sb.append((char)(i / 10 + 48));
                sb.append((char)(i % 10 + 48));
            }
            else {
                sb.append(Integer.toString(i));
            }
        }
    }
    
    private static class UnpaddedMonthField implements NumberRule
    {
        static final UnpaddedMonthField INSTANCE;
        
        UnpaddedMonthField() {
        }
        
        public int estimateLength() {
            return 2;
        }
        
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            this.appendTo(sb, calendar.get(2) + 1);
        }
        
        public final void appendTo(final StringBuffer sb, final int n) {
            if (n < 10) {
                sb.append((char)(n + 48));
            }
            else {
                sb.append((char)(n / 10 + 48));
                sb.append((char)(n % 10 + 48));
            }
        }
        
        static {
            INSTANCE = new UnpaddedMonthField();
        }
    }
    
    private static class PaddedNumberField implements NumberRule
    {
        private final int mField;
        private final int mSize;
        
        PaddedNumberField(final int mField, final int mSize) {
            if (mSize < 3) {
                throw new IllegalArgumentException();
            }
            this.mField = mField;
            this.mSize = mSize;
        }
        
        public int estimateLength() {
            return 4;
        }
        
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            this.appendTo(sb, calendar.get(this.mField));
        }
        
        public final void appendTo(final StringBuffer sb, final int n) {
            if (n < 100) {
                int mSize = this.mSize;
                while (--mSize >= 2) {
                    sb.append('0');
                }
                sb.append((char)(n / 10 + 48));
                sb.append((char)(n % 10 + 48));
            }
            else {
                int length;
                if (n < 1000) {
                    length = 3;
                }
                else {
                    Validate.isTrue(n > -1, "Negative values should not be possible", n);
                    length = Integer.toString(n).length();
                }
                int mSize2 = this.mSize;
                while (--mSize2 >= length) {
                    sb.append('0');
                }
                sb.append(Integer.toString(n));
            }
        }
    }
    
    private static class TwoDigitNumberField implements NumberRule
    {
        private final int mField;
        
        TwoDigitNumberField(final int mField) {
            this.mField = mField;
        }
        
        public int estimateLength() {
            return 2;
        }
        
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            this.appendTo(sb, calendar.get(this.mField));
        }
        
        public final void appendTo(final StringBuffer sb, final int i) {
            if (i < 100) {
                sb.append((char)(i / 10 + 48));
                sb.append((char)(i % 10 + 48));
            }
            else {
                sb.append(Integer.toString(i));
            }
        }
    }
    
    private static class TwoDigitYearField implements NumberRule
    {
        static final TwoDigitYearField INSTANCE;
        
        TwoDigitYearField() {
        }
        
        public int estimateLength() {
            return 2;
        }
        
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            this.appendTo(sb, calendar.get(1) % 100);
        }
        
        public final void appendTo(final StringBuffer sb, final int n) {
            sb.append((char)(n / 10 + 48));
            sb.append((char)(n % 10 + 48));
        }
        
        static {
            INSTANCE = new TwoDigitYearField();
        }
    }
    
    private static class TwoDigitMonthField implements NumberRule
    {
        static final TwoDigitMonthField INSTANCE;
        
        TwoDigitMonthField() {
        }
        
        public int estimateLength() {
            return 2;
        }
        
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            this.appendTo(sb, calendar.get(2) + 1);
        }
        
        public final void appendTo(final StringBuffer sb, final int n) {
            sb.append((char)(n / 10 + 48));
            sb.append((char)(n % 10 + 48));
        }
        
        static {
            INSTANCE = new TwoDigitMonthField();
        }
    }
    
    private static class TwelveHourField implements NumberRule
    {
        private final NumberRule mRule;
        
        TwelveHourField(final NumberRule mRule) {
            this.mRule = mRule;
        }
        
        public int estimateLength() {
            return this.mRule.estimateLength();
        }
        
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            int value = calendar.get(10);
            if (value == 0) {
                value = calendar.getLeastMaximum(10) + 1;
            }
            this.mRule.appendTo(sb, value);
        }
        
        public void appendTo(final StringBuffer sb, final int n) {
            this.mRule.appendTo(sb, n);
        }
    }
    
    private static class TwentyFourHourField implements NumberRule
    {
        private final NumberRule mRule;
        
        TwentyFourHourField(final NumberRule mRule) {
            this.mRule = mRule;
        }
        
        public int estimateLength() {
            return this.mRule.estimateLength();
        }
        
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            int value = calendar.get(11);
            if (value == 0) {
                value = calendar.getMaximum(11) + 1;
            }
            this.mRule.appendTo(sb, value);
        }
        
        public void appendTo(final StringBuffer sb, final int n) {
            this.mRule.appendTo(sb, n);
        }
    }
    
    private static class TimeZoneNameRule implements Rule
    {
        private final TimeZone mTimeZone;
        private final boolean mTimeZoneForced;
        private final Locale mLocale;
        private final int mStyle;
        private final String mStandard;
        private final String mDaylight;
        
        TimeZoneNameRule(final TimeZone mTimeZone, final boolean mTimeZoneForced, final Locale mLocale, final int mStyle) {
            this.mTimeZone = mTimeZone;
            this.mTimeZoneForced = mTimeZoneForced;
            this.mLocale = mLocale;
            this.mStyle = mStyle;
            if (mTimeZoneForced) {
                this.mStandard = FastDateFormat.getTimeZoneDisplay(mTimeZone, false, mStyle, mLocale);
                this.mDaylight = FastDateFormat.getTimeZoneDisplay(mTimeZone, true, mStyle, mLocale);
            }
            else {
                this.mStandard = null;
                this.mDaylight = null;
            }
        }
        
        public int estimateLength() {
            if (this.mTimeZoneForced) {
                return Math.max(this.mStandard.length(), this.mDaylight.length());
            }
            if (this.mStyle == 0) {
                return 4;
            }
            return 40;
        }
        
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            if (this.mTimeZoneForced) {
                if (this.mTimeZone.useDaylightTime() && calendar.get(16) != 0) {
                    sb.append(this.mDaylight);
                }
                else {
                    sb.append(this.mStandard);
                }
            }
            else {
                final TimeZone timeZone = calendar.getTimeZone();
                if (timeZone.useDaylightTime() && calendar.get(16) != 0) {
                    sb.append(FastDateFormat.getTimeZoneDisplay(timeZone, true, this.mStyle, this.mLocale));
                }
                else {
                    sb.append(FastDateFormat.getTimeZoneDisplay(timeZone, false, this.mStyle, this.mLocale));
                }
            }
        }
    }
    
    private static class TimeZoneNumberRule implements Rule
    {
        static final TimeZoneNumberRule INSTANCE_COLON;
        static final TimeZoneNumberRule INSTANCE_NO_COLON;
        final boolean mColon;
        
        TimeZoneNumberRule(final boolean mColon) {
            this.mColon = mColon;
        }
        
        public int estimateLength() {
            return 5;
        }
        
        public void appendTo(final StringBuffer sb, final Calendar calendar) {
            int n = calendar.get(15) + calendar.get(16);
            if (n < 0) {
                sb.append('-');
                n = -n;
            }
            else {
                sb.append('+');
            }
            final int n2 = n / 3600000;
            sb.append((char)(n2 / 10 + 48));
            sb.append((char)(n2 % 10 + 48));
            if (this.mColon) {
                sb.append(':');
            }
            final int n3 = n / 60000 - 60 * n2;
            sb.append((char)(n3 / 10 + 48));
            sb.append((char)(n3 % 10 + 48));
        }
        
        static {
            INSTANCE_COLON = new TimeZoneNumberRule(true);
            INSTANCE_NO_COLON = new TimeZoneNumberRule(false);
        }
    }
    
    private static class TimeZoneDisplayKey
    {
        private final TimeZone mTimeZone;
        private final int mStyle;
        private final Locale mLocale;
        
        TimeZoneDisplayKey(final TimeZone mTimeZone, final boolean b, int mStyle, final Locale mLocale) {
            this.mTimeZone = mTimeZone;
            if (b) {
                mStyle |= Integer.MIN_VALUE;
            }
            this.mStyle = mStyle;
            this.mLocale = mLocale;
        }
        
        public int hashCode() {
            return this.mStyle * 31 + this.mLocale.hashCode();
        }
        
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o instanceof TimeZoneDisplayKey) {
                final TimeZoneDisplayKey timeZoneDisplayKey = (TimeZoneDisplayKey)o;
                return this.mTimeZone.equals(timeZoneDisplayKey.mTimeZone) && this.mStyle == timeZoneDisplayKey.mStyle && this.mLocale.equals(timeZoneDisplayKey.mLocale);
            }
            return false;
        }
    }
    
    private static class Pair
    {
        private final Object mObj1;
        private final Object mObj2;
        
        public Pair(final Object mObj1, final Object mObj2) {
            this.mObj1 = mObj1;
            this.mObj2 = mObj2;
        }
        
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Pair)) {
                return false;
            }
            final Pair pair = (Pair)o;
            if (this.mObj1 == null) {
                if (pair.mObj1 != null) {
                    return false;
                }
            }
            else if (!this.mObj1.equals(pair.mObj1)) {
                return false;
            }
            if ((this.mObj2 != null) ? this.mObj2.equals(pair.mObj2) : (pair.mObj2 == null)) {
                return true;
            }
            return false;
        }
        
        public int hashCode() {
            return ((this.mObj1 == null) ? 0 : this.mObj1.hashCode()) + ((this.mObj2 == null) ? 0 : this.mObj2.hashCode());
        }
        
        public String toString() {
            return "[" + this.mObj1 + ':' + this.mObj2 + ']';
        }
    }
    
    private interface Rule
    {
        int estimateLength();
        
        void appendTo(final StringBuffer p0, final Calendar p1);
    }
    
    private interface NumberRule extends Rule
    {
        void appendTo(final StringBuffer p0, final int p1);
    }
}
