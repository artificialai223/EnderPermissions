

package me.TechsCode.EnderPermissions.base.command;

import java.util.List;

public abstract class Argument<T>
{
    public boolean hasMatch(final String s) {
        return this.get(s).getValidity() != Validity.NO_MATCH;
    }
    
    public abstract ArgumentValue<T> get(final String p0);
    
    public abstract String getUsage();
    
    public abstract List<String> getTabCompletions();
}
