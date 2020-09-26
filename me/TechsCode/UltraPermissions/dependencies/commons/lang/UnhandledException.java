

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.exception.NestableRuntimeException;

public class UnhandledException extends NestableRuntimeException
{
    private static final long serialVersionUID = 1832101364842773720L;
    
    public UnhandledException(final Throwable t) {
        super(t);
    }
    
    public UnhandledException(final String s, final Throwable t) {
        super(s, t);
    }
}
