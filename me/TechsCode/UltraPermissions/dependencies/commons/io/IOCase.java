

package me.TechsCode.EnderPermissions.dependencies.commons.io;

import java.io.Serializable;

public enum IOCase implements Serializable
{
    SENSITIVE("Sensitive", true), 
    INSENSITIVE("Insensitive", false), 
    SYSTEM("System", !FilenameUtils.isSystemWindows());
    
    private static final long serialVersionUID = -6343169151696340687L;
    private final String name;
    private final transient boolean sensitive;
    
    public static IOCase forName(final String s) {
        for (final IOCase ioCase : values()) {
            if (ioCase.getName().equals(s)) {
                return ioCase;
            }
        }
        throw new IllegalArgumentException("Invalid IOCase name: " + s);
    }
    
    private IOCase(final String name2, final boolean sensitive) {
        this.name = name2;
        this.sensitive = sensitive;
    }
    
    private Object readResolve() {
        return forName(this.name);
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isCaseSensitive() {
        return this.sensitive;
    }
    
    public int checkCompareTo(final String s, final String s2) {
        if (s == null || s2 == null) {
            throw new NullPointerException("The strings must not be null");
        }
        return this.sensitive ? s.compareTo(s2) : s.compareToIgnoreCase(s2);
    }
    
    public boolean checkEquals(final String s, final String s2) {
        if (s == null || s2 == null) {
            throw new NullPointerException("The strings must not be null");
        }
        return this.sensitive ? s.equals(s2) : s.equalsIgnoreCase(s2);
    }
    
    public boolean checkStartsWith(final String s, final String other) {
        return s.regionMatches(!this.sensitive, 0, other, 0, other.length());
    }
    
    public boolean checkEndsWith(final String s, final String other) {
        final int length = other.length();
        return s.regionMatches(!this.sensitive, s.length() - length, other, 0, length);
    }
    
    public int checkIndexOf(final String s, final int n, final String s2) {
        final int n2 = s.length() - s2.length();
        if (n2 >= n) {
            for (int i = n; i <= n2; ++i) {
                if (this.checkRegionMatches(s, i, s2)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public boolean checkRegionMatches(final String s, final int toffset, final String other) {
        return s.regionMatches(!this.sensitive, toffset, other, 0, other.length());
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
