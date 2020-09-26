

package me.TechsCode.EnderPermissions.dependencies.commons.io.serialization;

import me.TechsCode.EnderPermissions.dependencies.commons.io.FilenameUtils;

final class WildcardClassNameMatcher implements ClassNameMatcher
{
    private final String pattern;
    
    public WildcardClassNameMatcher(final String pattern) {
        this.pattern = pattern;
    }
    
    @Override
    public boolean matches(final String s) {
        return FilenameUtils.wildcardMatch(s, this.pattern);
    }
}
