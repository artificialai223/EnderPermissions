

package me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter;

import java.io.File;
import java.util.List;
import me.TechsCode.EnderPermissions.dependencies.commons.io.IOCase;
import java.io.Serializable;

public class PrefixFileFilter extends AbstractFileFilter implements Serializable
{
    private static final long serialVersionUID = 8533897440809599867L;
    private final String[] prefixes;
    private final IOCase caseSensitivity;
    
    public PrefixFileFilter(final String s) {
        this(s, IOCase.SENSITIVE);
    }
    
    public PrefixFileFilter(final String s, final IOCase ioCase) {
        if (s == null) {
            throw new IllegalArgumentException("The prefix must not be null");
        }
        this.prefixes = new String[] { s };
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public PrefixFileFilter(final String[] array) {
        this(array, IOCase.SENSITIVE);
    }
    
    public PrefixFileFilter(final String[] array, final IOCase ioCase) {
        if (array == null) {
            throw new IllegalArgumentException("The array of prefixes must not be null");
        }
        System.arraycopy(array, 0, this.prefixes = new String[array.length], 0, array.length);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public PrefixFileFilter(final List<String> list) {
        this(list, IOCase.SENSITIVE);
    }
    
    public PrefixFileFilter(final List<String> list, final IOCase ioCase) {
        if (list == null) {
            throw new IllegalArgumentException("The list of prefixes must not be null");
        }
        this.prefixes = list.toArray(new String[list.size()]);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    @Override
    public boolean accept(final File file) {
        final String name = file.getName();
        final String[] prefixes = this.prefixes;
        for (int length = prefixes.length, i = 0; i < length; ++i) {
            if (this.caseSensitivity.checkStartsWith(name, prefixes[i])) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        final String[] prefixes = this.prefixes;
        for (int length = prefixes.length, i = 0; i < length; ++i) {
            if (this.caseSensitivity.checkStartsWith(s, prefixes[i])) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("(");
        if (this.prefixes != null) {
            for (int i = 0; i < this.prefixes.length; ++i) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(this.prefixes[i]);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
