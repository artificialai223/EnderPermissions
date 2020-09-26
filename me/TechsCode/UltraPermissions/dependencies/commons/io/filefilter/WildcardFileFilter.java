

package me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter;

import me.TechsCode.EnderPermissions.dependencies.commons.io.FilenameUtils;
import java.io.File;
import java.util.List;
import me.TechsCode.EnderPermissions.dependencies.commons.io.IOCase;
import java.io.Serializable;

public class WildcardFileFilter extends AbstractFileFilter implements Serializable
{
    private static final long serialVersionUID = -7426486598995782105L;
    private final String[] wildcards;
    private final IOCase caseSensitivity;
    
    public WildcardFileFilter(final String s) {
        this(s, IOCase.SENSITIVE);
    }
    
    public WildcardFileFilter(final String s, final IOCase ioCase) {
        if (s == null) {
            throw new IllegalArgumentException("The wildcard must not be null");
        }
        this.wildcards = new String[] { s };
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public WildcardFileFilter(final String[] array) {
        this(array, IOCase.SENSITIVE);
    }
    
    public WildcardFileFilter(final String[] array, final IOCase ioCase) {
        if (array == null) {
            throw new IllegalArgumentException("The wildcard array must not be null");
        }
        System.arraycopy(array, 0, this.wildcards = new String[array.length], 0, array.length);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public WildcardFileFilter(final List<String> list) {
        this(list, IOCase.SENSITIVE);
    }
    
    public WildcardFileFilter(final List<String> list, final IOCase ioCase) {
        if (list == null) {
            throw new IllegalArgumentException("The wildcard list must not be null");
        }
        this.wildcards = list.toArray(new String[list.size()]);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        final String[] wildcards = this.wildcards;
        for (int length = wildcards.length, i = 0; i < length; ++i) {
            if (FilenameUtils.wildcardMatch(s, wildcards[i], this.caseSensitivity)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean accept(final File file) {
        final String name = file.getName();
        final String[] wildcards = this.wildcards;
        for (int length = wildcards.length, i = 0; i < length; ++i) {
            if (FilenameUtils.wildcardMatch(name, wildcards[i], this.caseSensitivity)) {
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
        if (this.wildcards != null) {
            for (int i = 0; i < this.wildcards.length; ++i) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(this.wildcards[i]);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
