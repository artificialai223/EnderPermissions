

package me.TechsCode.EnderPermissions.dependencies.slf4j.event;

import me.TechsCode.EnderPermissions.dependencies.slf4j.Marker;
import java.util.List;

public interface LoggingEvent
{
    Level getLevel();
    
    String getLoggerName();
    
    String getMessage();
    
    List<Object> getArguments();
    
    Object[] getArgumentArray();
    
    List<Marker> getMarkers();
    
    List<KeyValuePair> getKeyValuePairs();
    
    Throwable getThrowable();
    
    long getTimeStamp();
    
    String getThreadName();
}
