

package me.TechsCode.EnderPermissions.dependencies.commons.lang.exception;

public class CloneFailedException extends NestableRuntimeException
{
    private static final long serialVersionUID = 20091223L;
    
    public CloneFailedException(final String s) {
        super(s);
    }
    
    public CloneFailedException(final Throwable t) {
        super(t);
    }
    
    public CloneFailedException(final String s, final Throwable t) {
        super(s, t);
    }
}
