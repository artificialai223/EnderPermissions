

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import java.util.Arrays;

public class IncompleteArgumentException extends IllegalArgumentException
{
    private static final long serialVersionUID = 4954193403612068178L;
    
    public IncompleteArgumentException(final String str) {
        super(str + " is incomplete.");
    }
    
    public IncompleteArgumentException(final String str, final String[] array) {
        super(str + " is missing the following items: " + safeArrayToString(array));
    }
    
    private static final String safeArrayToString(final Object[] a) {
        return (a == null) ? null : Arrays.asList(a).toString();
    }
}
