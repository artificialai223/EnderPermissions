

package me.TechsCode.EnderPermissions.dependencies.commons.codec.language;

import me.TechsCode.EnderPermissions.dependencies.commons.codec.EncoderException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringEncoder;

public class Soundex implements StringEncoder
{
    public static final char SILENT_MARKER = '-';
    public static final String US_ENGLISH_MAPPING_STRING = "01230120022455012623010202";
    private static final char[] US_ENGLISH_MAPPING;
    public static final Soundex US_ENGLISH;
    public static final Soundex US_ENGLISH_SIMPLIFIED;
    public static final Soundex US_ENGLISH_GENEALOGY;
    @Deprecated
    private int maxLength;
    private final char[] soundexMapping;
    private final boolean specialCaseHW;
    
    public Soundex() {
        this.maxLength = 4;
        this.soundexMapping = Soundex.US_ENGLISH_MAPPING;
        this.specialCaseHW = true;
    }
    
    public Soundex(final char[] array) {
        this.maxLength = 4;
        System.arraycopy(array, 0, this.soundexMapping = new char[array.length], 0, array.length);
        this.specialCaseHW = !this.hasMarker(this.soundexMapping);
    }
    
    private boolean hasMarker(final char[] array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            if (array[i] == '-') {
                return true;
            }
        }
        return false;
    }
    
    public Soundex(final String s) {
        this.maxLength = 4;
        this.soundexMapping = s.toCharArray();
        this.specialCaseHW = !this.hasMarker(this.soundexMapping);
    }
    
    public Soundex(final String s, final boolean specialCaseHW) {
        this.maxLength = 4;
        this.soundexMapping = s.toCharArray();
        this.specialCaseHW = specialCaseHW;
    }
    
    public int difference(final String s, final String s2) {
        return SoundexUtils.difference(this, s, s2);
    }
    
    @Override
    public Object encode(final Object o) {
        if (!(o instanceof String)) {
            throw new EncoderException("Parameter supplied to Soundex encode is not of type java.lang.String");
        }
        return this.soundex((String)o);
    }
    
    @Override
    public String encode(final String s) {
        return this.soundex(s);
    }
    
    @Deprecated
    public int getMaxLength() {
        return this.maxLength;
    }
    
    private char map(final char c) {
        final int i = c - 'A';
        if (i < 0 || i >= this.soundexMapping.length) {
            throw new IllegalArgumentException("The character is not mapped: " + c + " (index=" + i + ")");
        }
        return this.soundexMapping[i];
    }
    
    @Deprecated
    public void setMaxLength(final int maxLength) {
        this.maxLength = maxLength;
    }
    
    public String soundex(String clean) {
        if (clean == null) {
            return null;
        }
        clean = SoundexUtils.clean(clean);
        if (clean.length() == 0) {
            return clean;
        }
        final char[] value = { '0', '0', '0', '0' };
        int n = 0;
        final char char1 = clean.charAt(0);
        value[n++] = char1;
        char map = this.map(char1);
        for (int index = 1; index < clean.length() && n < value.length; ++index) {
            final char char2 = clean.charAt(index);
            if (this.specialCaseHW) {
                if (char2 == 'H') {
                    continue;
                }
                if (char2 == 'W') {
                    continue;
                }
            }
            final char map2 = this.map(char2);
            if (map2 != '-') {
                if (map2 != '0' && map2 != map) {
                    value[n++] = map2;
                }
                map = map2;
            }
        }
        return new String(value);
    }
    
    static {
        US_ENGLISH_MAPPING = "01230120022455012623010202".toCharArray();
        US_ENGLISH = new Soundex();
        US_ENGLISH_SIMPLIFIED = new Soundex("01230120022455012623010202", false);
        US_ENGLISH_GENEALOGY = new Soundex("-123-12--22455-12623-1-2-2");
    }
}
