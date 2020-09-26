

package me.TechsCode.EnderPermissions.dependencies.slf4j.spi;

import me.TechsCode.EnderPermissions.dependencies.slf4j.IMarkerFactory;
import me.TechsCode.EnderPermissions.dependencies.slf4j.ILoggerFactory;

public interface SLF4JServiceProvider
{
    ILoggerFactory getLoggerFactory();
    
    IMarkerFactory getMarkerFactory();
    
    MDCAdapter getMDCAdapter();
    
    String getRequesteApiVersion();
    
    void initialize();
}
