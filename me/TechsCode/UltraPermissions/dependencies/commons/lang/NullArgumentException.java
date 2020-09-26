

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

public class NullArgumentException extends IllegalArgumentException
{
    private static final long serialVersionUID = 1174360235354917591L;
    
    public NullArgumentException(final String s) {
        super(((s == null) ? "Argument" : s) + " must not be null.");
    }
}
