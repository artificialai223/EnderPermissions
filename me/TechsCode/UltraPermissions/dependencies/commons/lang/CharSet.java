

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.io.Serializable;

public class CharSet implements Serializable
{
    private static final long serialVersionUID = 5947847346149275958L;
    public static final CharSet EMPTY;
    public static final CharSet ASCII_ALPHA;
    public static final CharSet ASCII_ALPHA_LOWER;
    public static final CharSet ASCII_ALPHA_UPPER;
    public static final CharSet ASCII_NUMERIC;
    protected static final Map COMMON;
    private final Set set;
    
    public static CharSet getInstance(final String s) {
        final CharSet value = CharSet.COMMON.get(s);
        if (value != null) {
            return value;
        }
        return new CharSet(s);
    }
    
    public static CharSet getInstance(final String[] array) {
        if (array == null) {
            return null;
        }
        return new CharSet(array);
    }
    
    protected CharSet(final String s) {
        this.set = Collections.synchronizedSet(new HashSet<Object>());
        this.add(s);
    }
    
    protected CharSet(final String[] array) {
        this.set = Collections.synchronizedSet(new HashSet<Object>());
        for (int length = array.length, i = 0; i < length; ++i) {
            this.add(array[i]);
        }
    }
    
    protected void add(final String s) {
        if (s == null) {
            return;
        }
        final int length = s.length();
        int i = 0;
        while (i < length) {
            final int n = length - i;
            if (n >= 4 && s.charAt(i) == '^' && s.charAt(i + 2) == '-') {
                this.set.add(CharRange.isNotIn(s.charAt(i + 1), s.charAt(i + 3)));
                i += 4;
            }
            else if (n >= 3 && s.charAt(i + 1) == '-') {
                this.set.add(CharRange.isIn(s.charAt(i), s.charAt(i + 2)));
                i += 3;
            }
            else if (n >= 2 && s.charAt(i) == '^') {
                this.set.add(CharRange.isNot(s.charAt(i + 1)));
                i += 2;
            }
            else {
                this.set.add(CharRange.is(s.charAt(i)));
                ++i;
            }
        }
    }
    
    public CharRange[] getCharRanges() {
        return this.set.toArray(new CharRange[this.set.size()]);
    }
    
    public boolean contains(final char c) {
        final Iterator<CharRange> iterator = this.set.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().contains(c)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean equals(final Object o) {
        return o == this || (o instanceof CharSet && this.set.equals(((CharSet)o).set));
    }
    
    public int hashCode() {
        return 89 + this.set.hashCode();
    }
    
    public String toString() {
        return this.set.toString();
    }
    
    static {
        EMPTY = new CharSet((String)null);
        ASCII_ALPHA = new CharSet("a-zA-Z");
        ASCII_ALPHA_LOWER = new CharSet("a-z");
        ASCII_ALPHA_UPPER = new CharSet("A-Z");
        ASCII_NUMERIC = new CharSet("0-9");
        (COMMON = Collections.synchronizedMap(new HashMap<Object, Object>())).put(null, CharSet.EMPTY);
        CharSet.COMMON.put("", CharSet.EMPTY);
        CharSet.COMMON.put("a-zA-Z", CharSet.ASCII_ALPHA);
        CharSet.COMMON.put("A-Za-z", CharSet.ASCII_ALPHA);
        CharSet.COMMON.put("a-z", CharSet.ASCII_ALPHA_LOWER);
        CharSet.COMMON.put("A-Z", CharSet.ASCII_ALPHA_UPPER);
        CharSet.COMMON.put("0-9", CharSet.ASCII_NUMERIC);
    }
}
