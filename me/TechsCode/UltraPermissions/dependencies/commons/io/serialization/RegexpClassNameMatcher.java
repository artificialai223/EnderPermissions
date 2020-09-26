

package me.TechsCode.EnderPermissions.dependencies.commons.io.serialization;

import java.util.regex.Pattern;

final class RegexpClassNameMatcher implements ClassNameMatcher
{
    private final Pattern pattern;
    
    public RegexpClassNameMatcher(final String regex) {
        this(Pattern.compile(regex));
    }
    
    public RegexpClassNameMatcher(final Pattern pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Null pattern");
        }
        this.pattern = pattern;
    }
    
    @Override
    public boolean matches(final String input) {
        return this.pattern.matcher(input).matches();
    }
}
