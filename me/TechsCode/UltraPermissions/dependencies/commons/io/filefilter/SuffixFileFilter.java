

package me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter;

import java.io.File;
import java.util.List;
import me.TechsCode.EnderPermissions.dependencies.commons.io.IOCase;
import java.io.Serializable;

public class SuffixFileFilter extends AbstractFileFilter implements Serializable
{
    private static final long serialVersionUID = -3389157631240246157L;
    private final String[] suffixes;
    private final IOCase caseSensitivity;
    
    public SuffixFileFilter(final String s) {
        this(s, IOCase.SENSITIVE);
    }
    
    public SuffixFileFilter(final String s, final IOCase ioCase) {
        if (s == null) {
            throw new IllegalArgumentException("The suffix must not be null");
        }
        this.suffixes = new String[] { s };
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public SuffixFileFilter(final String[] array) {
        this(array, IOCase.SENSITIVE);
    }
    
    public SuffixFileFilter(final String[] array, final IOCase ioCase) {
        if (array == null) {
            throw new IllegalArgumentException("The array of suffixes must not be null");
        }
        System.arraycopy(array, 0, this.suffixes = new String[array.length], 0, array.length);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public SuffixFileFilter(final List<String> list) {
        this(list, IOCase.SENSITIVE);
    }
    
    public SuffixFileFilter(final List<String> list, final IOCase ioCase) {
        if (list == null) {
            throw new IllegalArgumentException("The list of suffixes must not be null");
        }
        this.suffixes = list.toArray(new String[list.size()]);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    @Override
    public boolean accept(final File file) {
        final String name = file.getName();
        final String[] suffixes = this.suffixes;
        for (int length = suffixes.length, i = 0; i < length; ++i) {
            if (this.caseSensitivity.checkEndsWith(name, suffixes[i])) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        final String[] suffixes = this.suffixes;
        for (int length = suffixes.length, i = 0; i < length; ++i) {
            if (this.caseSensitivity.checkEndsWith(s, suffixes[i])) {
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
        if (this.suffixes != null) {
            for (int i = 0; i < this.suffixes.length; ++i) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(this.suffixes[i]);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
