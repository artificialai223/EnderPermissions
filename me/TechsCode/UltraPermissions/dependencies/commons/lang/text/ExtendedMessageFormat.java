

package me.TechsCode.EnderPermissions.dependencies.commons.lang.text;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.ObjectUtils;
import java.util.Iterator;
import java.util.Collection;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.Validate;
import java.text.ParsePosition;
import java.text.Format;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.text.MessageFormat;

public class ExtendedMessageFormat extends MessageFormat
{
    private static final long serialVersionUID = -2362048321261811743L;
    private static final int HASH_SEED = 31;
    private static final String DUMMY_PATTERN = "";
    private static final String ESCAPED_QUOTE = "''";
    private static final char START_FMT = ',';
    private static final char END_FE = '}';
    private static final char START_FE = '{';
    private static final char QUOTE = '\'';
    private String toPattern;
    private final Map registry;
    
    public ExtendedMessageFormat(final String s) {
        this(s, Locale.getDefault());
    }
    
    public ExtendedMessageFormat(final String s, final Locale locale) {
        this(s, locale, null);
    }
    
    public ExtendedMessageFormat(final String s, final Map map) {
        this(s, Locale.getDefault(), map);
    }
    
    public ExtendedMessageFormat(final String s, final Locale locale, final Map registry) {
        super("");
        this.setLocale(locale);
        this.registry = registry;
        this.applyPattern(s);
    }
    
    public String toPattern() {
        return this.toPattern;
    }
    
    public final void applyPattern(final String pattern) {
        if (this.registry == null) {
            super.applyPattern(pattern);
            this.toPattern = super.toPattern();
            return;
        }
        final ArrayList list = new ArrayList<Format>();
        final ArrayList list2 = new ArrayList<String>();
        final StrBuilder strBuilder = new StrBuilder(pattern.length());
        final ParsePosition parsePosition = new ParsePosition(0);
        final char[] charArray = pattern.toCharArray();
        int n = 0;
        while (parsePosition.getIndex() < pattern.length()) {
            switch (charArray[parsePosition.getIndex()]) {
                case '\'': {
                    this.appendQuotedString(pattern, parsePosition, strBuilder, true);
                    continue;
                }
                case '{': {
                    ++n;
                    this.seekNonWs(pattern, parsePosition);
                    final int index = parsePosition.getIndex();
                    strBuilder.append('{').append(this.readArgumentIndex(pattern, this.next(parsePosition)));
                    this.seekNonWs(pattern, parsePosition);
                    Format format = null;
                    String formatDescription = null;
                    if (charArray[parsePosition.getIndex()] == ',') {
                        formatDescription = this.parseFormatDescription(pattern, this.next(parsePosition));
                        format = this.getFormat(formatDescription);
                        if (format == null) {
                            strBuilder.append(',').append(formatDescription);
                        }
                    }
                    list.add(format);
                    list2.add((format == null) ? null : formatDescription);
                    Validate.isTrue(list.size() == n);
                    Validate.isTrue(list2.size() == n);
                    if (charArray[parsePosition.getIndex()] != '}') {
                        throw new IllegalArgumentException("Unreadable format element at position " + index);
                    }
                    break;
                }
            }
            strBuilder.append(charArray[parsePosition.getIndex()]);
            this.next(parsePosition);
        }
        super.applyPattern(strBuilder.toString());
        this.toPattern = this.insertFormats(super.toPattern(), list2);
        if (this.containsElements(list)) {
            final Format[] formats = this.getFormats();
            int n2 = 0;
            for (final Format format2 : list) {
                if (format2 != null) {
                    formats[n2] = format2;
                }
                ++n2;
            }
            super.setFormats(formats);
        }
    }
    
    public void setFormat(final int n, final Format format) {
        throw new UnsupportedOperationException();
    }
    
    public void setFormatByArgumentIndex(final int n, final Format format) {
        throw new UnsupportedOperationException();
    }
    
    public void setFormats(final Format[] array) {
        throw new UnsupportedOperationException();
    }
    
    public void setFormatsByArgumentIndex(final Format[] array) {
        throw new UnsupportedOperationException();
    }
    
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (ObjectUtils.notEqual(this.getClass(), obj.getClass())) {
            return false;
        }
        final ExtendedMessageFormat extendedMessageFormat = (ExtendedMessageFormat)obj;
        return !ObjectUtils.notEqual(this.toPattern, extendedMessageFormat.toPattern) && !ObjectUtils.notEqual(this.registry, extendedMessageFormat.registry);
    }
    
    public int hashCode() {
        return 31 * (31 * super.hashCode() + ObjectUtils.hashCode(this.registry)) + ObjectUtils.hashCode(this.toPattern);
    }
    
    private Format getFormat(final String s) {
        if (this.registry != null) {
            String trim = s;
            String trim2 = null;
            final int index = s.indexOf(44);
            if (index > 0) {
                trim = s.substring(0, index).trim();
                trim2 = s.substring(index + 1).trim();
            }
            final FormatFactory formatFactory = this.registry.get(trim);
            if (formatFactory != null) {
                return formatFactory.getFormat(trim, trim2, this.getLocale());
            }
        }
        return null;
    }
    
    private int readArgumentIndex(final String s, final ParsePosition parsePosition) {
        final int index = parsePosition.getIndex();
        this.seekNonWs(s, parsePosition);
        final StrBuilder strBuilder = new StrBuilder();
        int n = 0;
        while (n == 0 && parsePosition.getIndex() < s.length()) {
            char c = s.charAt(parsePosition.getIndex());
            Label_0149: {
                if (Character.isWhitespace(c)) {
                    this.seekNonWs(s, parsePosition);
                    c = s.charAt(parsePosition.getIndex());
                    if (c != ',' && c != '}') {
                        n = 1;
                        break Label_0149;
                    }
                }
                if ((c == ',' || c == '}') && strBuilder.length() > 0) {
                    try {
                        return Integer.parseInt(strBuilder.toString());
                    }
                    catch (NumberFormatException ex) {}
                }
                n = (Character.isDigit(c) ? 0 : 1);
                strBuilder.append(c);
            }
            this.next(parsePosition);
        }
        if (n != 0) {
            throw new IllegalArgumentException("Invalid format argument index at position " + index + ": " + s.substring(index, parsePosition.getIndex()));
        }
        throw new IllegalArgumentException("Unterminated format element at position " + index);
    }
    
    private String parseFormatDescription(final String s, final ParsePosition parsePosition) {
        final int index = parsePosition.getIndex();
        this.seekNonWs(s, parsePosition);
        final int index2 = parsePosition.getIndex();
        int n = 1;
        while (parsePosition.getIndex() < s.length()) {
            switch (s.charAt(parsePosition.getIndex())) {
                case '{': {
                    ++n;
                    break;
                }
                case '}': {
                    if (--n == 0) {
                        return s.substring(index2, parsePosition.getIndex());
                    }
                    break;
                }
                case '\'': {
                    this.getQuotedString(s, parsePosition, false);
                    break;
                }
            }
            this.next(parsePosition);
        }
        throw new IllegalArgumentException("Unterminated format element at position " + index);
    }
    
    private String insertFormats(final String s, final ArrayList list) {
        if (!this.containsElements(list)) {
            return s;
        }
        final StrBuilder strBuilder = new StrBuilder(s.length() * 2);
        final ParsePosition parsePosition = new ParsePosition(0);
        int index = -1;
        int n = 0;
        while (parsePosition.getIndex() < s.length()) {
            final char char1 = s.charAt(parsePosition.getIndex());
            switch (char1) {
                case 39: {
                    this.appendQuotedString(s, parsePosition, strBuilder, false);
                    continue;
                }
                case 123: {
                    if (++n == 1) {
                        ++index;
                        strBuilder.append('{').append(this.readArgumentIndex(s, this.next(parsePosition)));
                        final String s2 = list.get(index);
                        if (s2 == null) {
                            continue;
                        }
                        strBuilder.append(',').append(s2);
                        continue;
                    }
                    continue;
                }
                case 125: {
                    --n;
                    break;
                }
            }
            strBuilder.append(char1);
            this.next(parsePosition);
        }
        return strBuilder.toString();
    }
    
    private void seekNonWs(final String s, final ParsePosition parsePosition) {
        final char[] charArray = s.toCharArray();
        int match;
        do {
            match = StrMatcher.splitMatcher().isMatch(charArray, parsePosition.getIndex());
            parsePosition.setIndex(parsePosition.getIndex() + match);
        } while (match > 0 && parsePosition.getIndex() < s.length());
    }
    
    private ParsePosition next(final ParsePosition parsePosition) {
        parsePosition.setIndex(parsePosition.getIndex() + 1);
        return parsePosition;
    }
    
    private StrBuilder appendQuotedString(final String s, final ParsePosition parsePosition, final StrBuilder strBuilder, final boolean b) {
        final int index = parsePosition.getIndex();
        final char[] charArray = s.toCharArray();
        if (b && charArray[index] == '\'') {
            this.next(parsePosition);
            return (strBuilder == null) ? null : strBuilder.append('\'');
        }
        int index2 = index;
        for (int i = parsePosition.getIndex(); i < s.length(); ++i) {
            if (b && s.substring(i).startsWith("''")) {
                strBuilder.append(charArray, index2, parsePosition.getIndex() - index2).append('\'');
                parsePosition.setIndex(i + "''".length());
                index2 = parsePosition.getIndex();
            }
            else {
                switch (charArray[parsePosition.getIndex()]) {
                    case '\'': {
                        this.next(parsePosition);
                        return (strBuilder == null) ? null : strBuilder.append(charArray, index2, parsePosition.getIndex() - index2);
                    }
                    default: {
                        this.next(parsePosition);
                        break;
                    }
                }
            }
        }
        throw new IllegalArgumentException("Unterminated quoted string at position " + index);
    }
    
    private void getQuotedString(final String s, final ParsePosition parsePosition, final boolean b) {
        this.appendQuotedString(s, parsePosition, null, b);
    }
    
    private boolean containsElements(final Collection collection) {
        if (collection == null || collection.size() == 0) {
            return false;
        }
        final Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() != null) {
                return true;
            }
        }
        return false;
    }
}
