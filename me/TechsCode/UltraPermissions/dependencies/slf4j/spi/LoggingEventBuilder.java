

package me.TechsCode.EnderPermissions.dependencies.slf4j.spi;

import java.util.function.Supplier;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Marker;

public interface LoggingEventBuilder
{
    LoggingEventBuilder setCause(final Throwable p0);
    
    LoggingEventBuilder addMarker(final Marker p0);
    
    LoggingEventBuilder addArgument(final Object p0);
    
    LoggingEventBuilder addArgument(final Supplier<Object> p0);
    
    LoggingEventBuilder addKeyValue(final String p0, final Object p1);
    
    LoggingEventBuilder addKeyValue(final String p0, final Supplier<Object> p1);
    
    void log(final String p0);
    
    void log(final String p0, final Object p1);
    
    void log(final String p0, final Object p1, final Object p2);
    
    void log(final String p0, final Object... p1);
    
    void log(final Supplier<String> p0);
}
