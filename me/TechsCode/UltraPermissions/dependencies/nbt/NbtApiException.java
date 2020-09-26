

package me.TechsCode.EnderPermissions.dependencies.nbt;

public class NbtApiException extends RuntimeException
{
    private static final long serialVersionUID = -993309714559452334L;
    
    public NbtApiException() {
    }
    
    public NbtApiException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public NbtApiException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public NbtApiException(final String message) {
        super(message);
    }
    
    public NbtApiException(final Throwable cause) {
        super(cause);
    }
}
