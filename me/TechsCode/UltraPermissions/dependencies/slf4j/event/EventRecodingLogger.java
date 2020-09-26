

package me.TechsCode.EnderPermissions.dependencies.slf4j.event;

import me.TechsCode.EnderPermissions.dependencies.slf4j.Marker;
import java.util.Queue;
import me.TechsCode.EnderPermissions.dependencies.slf4j.helpers.SubstituteLogger;
import me.TechsCode.EnderPermissions.dependencies.slf4j.helpers.LegacyAbstractLogger;

public class EventRecodingLogger extends LegacyAbstractLogger
{
    private static final long serialVersionUID = -176083308134819629L;
    String name;
    SubstituteLogger logger;
    Queue<SubstituteLoggingEvent> eventQueue;
    static final boolean RECORD_ALL_EVENTS = true;
    
    public EventRecodingLogger(final SubstituteLogger logger, final Queue<SubstituteLoggingEvent> eventQueue) {
        this.logger = logger;
        this.name = logger.getName();
        this.eventQueue = eventQueue;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public boolean isTraceEnabled() {
        return true;
    }
    
    @Override
    public boolean isDebugEnabled() {
        return true;
    }
    
    @Override
    public boolean isInfoEnabled() {
        return true;
    }
    
    @Override
    public boolean isWarnEnabled() {
        return true;
    }
    
    @Override
    public boolean isErrorEnabled() {
        return true;
    }
    
    @Override
    protected void handleNormalizedLoggingCall(final Level level, final Marker marker, final String message, final Object[] argumentArray, final Throwable throwable) {
        final SubstituteLoggingEvent substituteLoggingEvent = new SubstituteLoggingEvent();
        substituteLoggingEvent.setTimeStamp(System.currentTimeMillis());
        substituteLoggingEvent.setLevel(level);
        substituteLoggingEvent.setLogger(this.logger);
        substituteLoggingEvent.setLoggerName(this.name);
        if (marker != null) {
            substituteLoggingEvent.addMarker(marker);
        }
        substituteLoggingEvent.setMessage(message);
        substituteLoggingEvent.setThreadName(Thread.currentThread().getName());
        substituteLoggingEvent.setArgumentArray(argumentArray);
        substituteLoggingEvent.setThrowable(throwable);
        this.eventQueue.add(substituteLoggingEvent);
    }
    
    @Override
    protected String getFullyQualifiedCallerName() {
        return null;
    }
}
