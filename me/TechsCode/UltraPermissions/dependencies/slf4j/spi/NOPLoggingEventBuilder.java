

package me.TechsCode.EnderPermissions.dependencies.slf4j.spi;

import java.util.function.Supplier;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Marker;

public class NOPLoggingEventBuilder implements LoggingEventBuilder
{
    static final NOPLoggingEventBuilder SINGLETON;
    
    public static LoggingEventBuilder singleton() {
        return NOPLoggingEventBuilder.SINGLETON;
    }
    
    @Override
    public LoggingEventBuilder addMarker(final Marker marker) {
        return singleton();
    }
    
    @Override
    public LoggingEventBuilder addArgument(final Object o) {
        return singleton();
    }
    
    @Override
    public LoggingEventBuilder addArgument(final Supplier<Object> supplier) {
        return singleton();
    }
    
    @Override
    public LoggingEventBuilder addKeyValue(final String s, final Object o) {
        return singleton();
    }
    
    @Override
    public LoggingEventBuilder addKeyValue(final String s, final Supplier<Object> supplier) {
        return singleton();
    }
    
    @Override
    public LoggingEventBuilder setCause(final Throwable t) {
        return singleton();
    }
    
    @Override
    public void log(final String s) {
    }
    
    @Override
    public void log(final Supplier<String> supplier) {
    }
    
    @Override
    public void log(final String s, final Object o) {
    }
    
    @Override
    public void log(final String s, final Object o, final Object o2) {
    }
    
    @Override
    public void log(final String s, final Object... array) {
    }
    
    static {
        SINGLETON = new NOPLoggingEventBuilder();
    }
}
