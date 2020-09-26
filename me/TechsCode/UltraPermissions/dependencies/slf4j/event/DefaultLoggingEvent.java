

package me.TechsCode.EnderPermissions.dependencies.slf4j.event;

import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Marker;
import java.util.List;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;

public class DefaultLoggingEvent implements LoggingEvent
{
    Logger logger;
    Level level;
    String message;
    List<Marker> markers;
    List<Object> arguments;
    List<KeyValuePair> keyValuePairs;
    Throwable throwable;
    String threadName;
    long timeStamp;
    
    public DefaultLoggingEvent(final Level level, final Logger logger) {
        this.logger = logger;
        this.level = level;
    }
    
    public void addMarker(final Marker marker) {
        if (this.markers == null) {
            this.markers = new ArrayList<Marker>(2);
        }
        this.markers.add(marker);
    }
    
    @Override
    public List<Marker> getMarkers() {
        return this.markers;
    }
    
    public void addArgument(final Object o) {
        this.getNonNullArguments().add(o);
    }
    
    public void addArguments(final Object... a) {
        this.getNonNullArguments().addAll(Arrays.asList(a));
    }
    
    private List<Object> getNonNullArguments() {
        if (this.arguments == null) {
            this.arguments = new ArrayList<Object>(3);
        }
        return this.arguments;
    }
    
    @Override
    public List<Object> getArguments() {
        return this.arguments;
    }
    
    @Override
    public Object[] getArgumentArray() {
        if (this.arguments == null) {
            return null;
        }
        return this.arguments.toArray();
    }
    
    public void addKeyValue(final String s, final Object o) {
        this.getNonnullKeyValuePairs().add(new KeyValuePair(s, o));
    }
    
    private List<KeyValuePair> getNonnullKeyValuePairs() {
        if (this.keyValuePairs == null) {
            this.keyValuePairs = new ArrayList<KeyValuePair>(4);
        }
        return this.keyValuePairs;
    }
    
    @Override
    public List<KeyValuePair> getKeyValuePairs() {
        return this.keyValuePairs;
    }
    
    public void setThrowable(final Throwable throwable) {
        this.throwable = throwable;
    }
    
    @Override
    public Level getLevel() {
        return this.level;
    }
    
    @Override
    public String getLoggerName() {
        return this.logger.getName();
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }
    
    @Override
    public String getThreadName() {
        return this.threadName;
    }
    
    @Override
    public long getTimeStamp() {
        return this.timeStamp;
    }
}
