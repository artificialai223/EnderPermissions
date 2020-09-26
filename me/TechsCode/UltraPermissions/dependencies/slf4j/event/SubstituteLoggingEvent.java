

package me.TechsCode.EnderPermissions.dependencies.slf4j.event;

import java.util.Arrays;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.dependencies.slf4j.helpers.SubstituteLogger;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Marker;
import java.util.List;

public class SubstituteLoggingEvent implements LoggingEvent
{
    Level level;
    List<Marker> markers;
    String loggerName;
    SubstituteLogger logger;
    String threadName;
    String message;
    Object[] argArray;
    List<KeyValuePair> keyValuePairList;
    long timeStamp;
    Throwable throwable;
    
    @Override
    public Level getLevel() {
        return this.level;
    }
    
    public void setLevel(final Level level) {
        this.level = level;
    }
    
    @Override
    public List<Marker> getMarkers() {
        return this.markers;
    }
    
    public void addMarker(final Marker marker) {
        if (marker == null) {
            return;
        }
        if (this.markers == null) {
            this.markers = new ArrayList<Marker>(2);
        }
        this.markers.add(marker);
    }
    
    @Override
    public String getLoggerName() {
        return this.loggerName;
    }
    
    public void setLoggerName(final String loggerName) {
        this.loggerName = loggerName;
    }
    
    public SubstituteLogger getLogger() {
        return this.logger;
    }
    
    public void setLogger(final SubstituteLogger logger) {
        this.logger = logger;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    @Override
    public Object[] getArgumentArray() {
        return this.argArray;
    }
    
    public void setArgumentArray(final Object[] argArray) {
        this.argArray = argArray;
    }
    
    @Override
    public List<Object> getArguments() {
        if (this.argArray == null) {
            return null;
        }
        return Arrays.asList(this.argArray);
    }
    
    @Override
    public long getTimeStamp() {
        return this.timeStamp;
    }
    
    public void setTimeStamp(final long timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    @Override
    public String getThreadName() {
        return this.threadName;
    }
    
    public void setThreadName(final String threadName) {
        this.threadName = threadName;
    }
    
    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }
    
    public void setThrowable(final Throwable throwable) {
        this.throwable = throwable;
    }
    
    @Override
    public List<KeyValuePair> getKeyValuePairs() {
        return this.keyValuePairList;
    }
}
