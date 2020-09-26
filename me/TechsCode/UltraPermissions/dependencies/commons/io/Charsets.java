

package me.TechsCode.EnderPermissions.dependencies.commons.io;

import java.util.Collections;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.SortedMap;
import java.nio.charset.Charset;

public class Charsets
{
    @Deprecated
    public static final Charset ISO_8859_1;
    @Deprecated
    public static final Charset US_ASCII;
    @Deprecated
    public static final Charset UTF_16;
    @Deprecated
    public static final Charset UTF_16BE;
    @Deprecated
    public static final Charset UTF_16LE;
    @Deprecated
    public static final Charset UTF_8;
    
    public static SortedMap<String, Charset> requiredCharsets() {
        final TreeMap<String, Charset> m = new TreeMap<String, Charset>(String.CASE_INSENSITIVE_ORDER);
        m.put(StandardCharsets.ISO_8859_1.name(), StandardCharsets.ISO_8859_1);
        m.put(StandardCharsets.US_ASCII.name(), StandardCharsets.US_ASCII);
        m.put(StandardCharsets.UTF_16.name(), StandardCharsets.UTF_16);
        m.put(StandardCharsets.UTF_16BE.name(), StandardCharsets.UTF_16BE);
        m.put(StandardCharsets.UTF_16LE.name(), StandardCharsets.UTF_16LE);
        m.put(StandardCharsets.UTF_8.name(), StandardCharsets.UTF_8);
        return (SortedMap<String, Charset>)Collections.unmodifiableSortedMap((SortedMap<String, ?>)m);
    }
    
    public static Charset toCharset(final Charset charset) {
        return (charset == null) ? Charset.defaultCharset() : charset;
    }
    
    public static Charset toCharset(final String charsetName) {
        return (charsetName == null) ? Charset.defaultCharset() : Charset.forName(charsetName);
    }
    
    static {
        ISO_8859_1 = StandardCharsets.ISO_8859_1;
        US_ASCII = StandardCharsets.US_ASCII;
        UTF_16 = StandardCharsets.UTF_16;
        UTF_16BE = StandardCharsets.UTF_16BE;
        UTF_16LE = StandardCharsets.UTF_16LE;
        UTF_8 = StandardCharsets.UTF_8;
    }
}
