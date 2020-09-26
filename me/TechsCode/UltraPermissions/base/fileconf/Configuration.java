

package me.TechsCode.EnderPermissions.base.fileconf;

import java.util.Set;

public interface Configuration
{
    Object get(final String p0);
    
    int getInt(final String p0);
    
    String getString(final String p0);
    
    boolean getBoolean(final String p0);
    
    Set<String> getKeys(final boolean p0);
    
    boolean contains(final String p0);
    
    void set(final String p0, final Object p1);
    
    void save();
}
