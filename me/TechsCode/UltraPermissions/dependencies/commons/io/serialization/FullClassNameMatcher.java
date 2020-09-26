

package me.TechsCode.EnderPermissions.dependencies.commons.io.serialization;

import java.util.Collections;
import java.util.Collection;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

final class FullClassNameMatcher implements ClassNameMatcher
{
    private final Set<String> classesSet;
    
    public FullClassNameMatcher(final String... a) {
        this.classesSet = Collections.unmodifiableSet((Set<? extends String>)new HashSet<String>(Arrays.asList(a)));
    }
    
    @Override
    public boolean matches(final String s) {
        return this.classesSet.contains(s);
    }
}
