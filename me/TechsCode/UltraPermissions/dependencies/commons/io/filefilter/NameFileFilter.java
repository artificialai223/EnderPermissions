

package me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter;

import java.io.File;
import java.util.List;
import me.TechsCode.EnderPermissions.dependencies.commons.io.IOCase;
import java.io.Serializable;

public class NameFileFilter extends AbstractFileFilter implements Serializable
{
    private static final long serialVersionUID = 176844364689077340L;
    private final String[] names;
    private final IOCase caseSensitivity;
    
    public NameFileFilter(final String s) {
        this(s, null);
    }
    
    public NameFileFilter(final String s, final IOCase ioCase) {
        if (s == null) {
            throw new IllegalArgumentException("The wildcard must not be null");
        }
        this.names = new String[] { s };
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public NameFileFilter(final String[] array) {
        this(array, null);
    }
    
    public NameFileFilter(final String[] array, final IOCase ioCase) {
        if (array == null) {
            throw new IllegalArgumentException("The array of names must not be null");
        }
        System.arraycopy(array, 0, this.names = new String[array.length], 0, array.length);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    public NameFileFilter(final List<String> list) {
        this(list, null);
    }
    
    public NameFileFilter(final List<String> list, final IOCase ioCase) {
        if (list == null) {
            throw new IllegalArgumentException("The list of names must not be null");
        }
        this.names = list.toArray(new String[list.size()]);
        this.caseSensitivity = ((ioCase == null) ? IOCase.SENSITIVE : ioCase);
    }
    
    @Override
    public boolean accept(final File file) {
        final String name = file.getName();
        final String[] names = this.names;
        for (int length = names.length, i = 0; i < length; ++i) {
            if (this.caseSensitivity.checkEquals(name, names[i])) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        final String[] names = this.names;
        for (int length = names.length, i = 0; i < length; ++i) {
            if (this.caseSensitivity.checkEquals(s, names[i])) {
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
        if (this.names != null) {
            for (int i = 0; i < this.names.length; ++i) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(this.names[i]);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
