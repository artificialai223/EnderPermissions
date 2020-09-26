

package me.TechsCode.EnderPermissions.dependencies.slf4j.helpers;

import me.TechsCode.EnderPermissions.dependencies.slf4j.LoggerFactory;
import java.io.Serializable;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;

abstract class NamedLoggerBase implements Logger, Serializable
{
    private static final long serialVersionUID = 7535258609338176893L;
    protected String name;
    
    @Override
    public String getName() {
        return this.name;
    }
    
    protected Object readResolve() {
        return LoggerFactory.getLogger(this.getName());
    }
}
