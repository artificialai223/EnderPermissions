

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.io.IOException;
import me.TechsCode.EnderPermissions.dependencies.commons.io.TaggedIOException;
import java.util.UUID;
import java.io.InputStream;
import java.io.Serializable;

public class TaggedInputStream extends ProxyInputStream
{
    private final Serializable tag;
    
    public TaggedInputStream(final InputStream inputStream) {
        super(inputStream);
        this.tag = UUID.randomUUID();
    }
    
    public boolean isCauseOf(final Throwable t) {
        return TaggedIOException.isTaggedWith(t, this.tag);
    }
    
    public void throwIfCauseOf(final Throwable t) {
        TaggedIOException.throwCauseIfTaggedWith(t, this.tag);
    }
    
    @Override
    protected void handleIOException(final IOException ex) {
        throw new TaggedIOException(ex, this.tag);
    }
}
