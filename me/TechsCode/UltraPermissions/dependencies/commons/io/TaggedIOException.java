

package me.TechsCode.EnderPermissions.dependencies.commons.io;

import java.io.IOException;
import java.io.Serializable;

public class TaggedIOException extends IOExceptionWithCause
{
    private static final long serialVersionUID = -6994123481142850163L;
    private final Serializable tag;
    
    public static boolean isTaggedWith(final Throwable t, final Object o) {
        return o != null && t instanceof TaggedIOException && o.equals(((TaggedIOException)t).tag);
    }
    
    public static void throwCauseIfTaggedWith(final Throwable t, final Object o) {
        if (isTaggedWith(t, o)) {
            throw ((TaggedIOException)t).getCause();
        }
    }
    
    public TaggedIOException(final IOException ex, final Serializable tag) {
        super(ex.getMessage(), ex);
        this.tag = tag;
    }
    
    public Serializable getTag() {
        return this.tag;
    }
    
    @Override
    public IOException getCause() {
        return (IOException)super.getCause();
    }
}
