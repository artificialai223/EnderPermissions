

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.exception.NestableRuntimeException;

public class SerializationException extends NestableRuntimeException
{
    private static final long serialVersionUID = 4029025366392702726L;
    
    public SerializationException() {
    }
    
    public SerializationException(final String s) {
        super(s);
    }
    
    public SerializationException(final Throwable t) {
        super(t);
    }
    
    public SerializationException(final String s, final Throwable t) {
        super(s, t);
    }
}
