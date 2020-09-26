

package me.TechsCode.EnderPermissions.dependencies.commons.io;

import java.io.IOException;

@Deprecated
public class IOExceptionWithCause extends IOException
{
    private static final long serialVersionUID = 1L;
    
    public IOExceptionWithCause(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public IOExceptionWithCause(final Throwable cause) {
        super(cause);
    }
}
