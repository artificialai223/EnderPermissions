

package me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter;

import me.TechsCode.EnderPermissions.dependencies.commons.io.FilenameUtils;
import java.io.File;
import java.util.List;
import java.io.Serializable;

@Deprecated
public class WildcardFilter extends AbstractFileFilter implements Serializable
{
    private static final long serialVersionUID = -5037645902506953517L;
    private final String[] wildcards;
    
    public WildcardFilter(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("The wildcard must not be null");
        }
        this.wildcards = new String[] { s };
    }
    
    public WildcardFilter(final String[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The wildcard array must not be null");
        }
        System.arraycopy(array, 0, this.wildcards = new String[array.length], 0, array.length);
    }
    
    public WildcardFilter(final List<String> list) {
        if (list == null) {
            throw new IllegalArgumentException("The wildcard list must not be null");
        }
        this.wildcards = list.toArray(new String[list.size()]);
    }
    
    @Override
    public boolean accept(final File parent, final String child) {
        if (parent != null && new File(parent, child).isDirectory()) {
            return false;
        }
        final String[] wildcards = this.wildcards;
        for (int length = wildcards.length, i = 0; i < length; ++i) {
            if (FilenameUtils.wildcardMatch(child, wildcards[i])) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean accept(final File file) {
        if (file.isDirectory()) {
            return false;
        }
        final String[] wildcards = this.wildcards;
        for (int length = wildcards.length, i = 0; i < length; ++i) {
            if (FilenameUtils.wildcardMatch(file.getName(), wildcards[i])) {
                return true;
            }
        }
        return false;
    }
}
