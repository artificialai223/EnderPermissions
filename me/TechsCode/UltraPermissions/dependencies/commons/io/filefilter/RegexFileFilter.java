

package me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter;

import java.io.File;
import me.TechsCode.EnderPermissions.dependencies.commons.io.IOCase;
import java.util.regex.Pattern;
import java.io.Serializable;

public class RegexFileFilter extends AbstractFileFilter implements Serializable
{
    private static final long serialVersionUID = 4269646126155225062L;
    private final Pattern pattern;
    
    public RegexFileFilter(final String regex) {
        if (regex == null) {
            throw new IllegalArgumentException("Pattern is missing");
        }
        this.pattern = Pattern.compile(regex);
    }
    
    public RegexFileFilter(final String regex, final IOCase ioCase) {
        if (regex == null) {
            throw new IllegalArgumentException("Pattern is missing");
        }
        int flags = 0;
        if (ioCase != null && !ioCase.isCaseSensitive()) {
            flags = 2;
        }
        this.pattern = Pattern.compile(regex, flags);
    }
    
    public RegexFileFilter(final String regex, final int flags) {
        if (regex == null) {
            throw new IllegalArgumentException("Pattern is missing");
        }
        this.pattern = Pattern.compile(regex, flags);
    }
    
    public RegexFileFilter(final Pattern pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern is missing");
        }
        this.pattern = pattern;
    }
    
    @Override
    public boolean accept(final File file, final String input) {
        return this.pattern.matcher(input).matches();
    }
}
