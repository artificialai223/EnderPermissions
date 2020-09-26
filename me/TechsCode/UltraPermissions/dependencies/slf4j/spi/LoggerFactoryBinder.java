

package me.TechsCode.EnderPermissions.dependencies.slf4j.spi;

import me.TechsCode.EnderPermissions.dependencies.slf4j.ILoggerFactory;

public interface LoggerFactoryBinder
{
    ILoggerFactory getLoggerFactory();
    
    String getLoggerFactoryClassStr();
}
