

package me.TechsCode.EnderPermissions.dependencies.commons.codec.language;

import me.TechsCode.EnderPermissions.dependencies.commons.codec.EncoderException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringEncoder;

public abstract class AbstractCaverphone implements StringEncoder
{
    @Override
    public Object encode(final Object o) {
        if (!(o instanceof String)) {
            throw new EncoderException("Parameter supplied to Caverphone encode is not of type java.lang.String");
        }
        return this.encode((String)o);
    }
    
    public boolean isEncodeEqual(final String s, final String s2) {
        return this.encode(s).equals(this.encode(s2));
    }
}
