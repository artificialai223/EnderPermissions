

package me.TechsCode.EnderPermissions.dependencies.slf4j.spi;

import java.util.Iterator;
import me.TechsCode.EnderPermissions.dependencies.slf4j.event.KeyValuePair;
import me.TechsCode.EnderPermissions.dependencies.slf4j.event.LoggingEventAware;
import me.TechsCode.EnderPermissions.dependencies.slf4j.event.LoggingEvent;
import java.util.function.Supplier;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Marker;
import me.TechsCode.EnderPermissions.dependencies.slf4j.event.Level;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;
import me.TechsCode.EnderPermissions.dependencies.slf4j.event.DefaultLoggingEvent;

public class DefaultLoggingEventBuilder implements LoggingEventBuilder
{
    DefaultLoggingEvent loggingEvent;
    Logger logger;
    
    public DefaultLoggingEventBuilder(final Logger logger, final Level level) {
        this.logger = logger;
        this.loggingEvent = new DefaultLoggingEvent(level, logger);
    }
    
    @Override
    public LoggingEventBuilder addMarker(final Marker marker) {
        this.loggingEvent.addMarker(marker);
        return this;
    }
    
    @Override
    public LoggingEventBuilder setCause(final Throwable throwable) {
        this.loggingEvent.setThrowable(throwable);
        return this;
    }
    
    @Override
    public LoggingEventBuilder addArgument(final Object o) {
        this.loggingEvent.addArgument(o);
        return this;
    }
    
    @Override
    public LoggingEventBuilder addArgument(final Supplier<Object> supplier) {
        this.loggingEvent.addArgument(supplier.get());
        return this;
    }
    
    @Override
    public void log(final String message) {
        this.loggingEvent.setMessage(message);
        this.innerLog(this.loggingEvent);
    }
    
    @Override
    public void log(final String message, final Object o) {
        this.loggingEvent.setMessage(message);
        this.loggingEvent.addArgument(o);
        this.innerLog(this.loggingEvent);
    }
    
    @Override
    public void log(final String message, final Object o, final Object o2) {
        this.loggingEvent.setMessage(message);
        this.loggingEvent.addArgument(o);
        this.loggingEvent.addArgument(o2);
        this.innerLog(this.loggingEvent);
    }
    
    @Override
    public void log(final String message, final Object... array) {
        this.loggingEvent.setMessage(message);
        this.loggingEvent.addArguments(array);
        this.innerLog(this.loggingEvent);
    }
    
    private void innerLog(final LoggingEvent loggingEvent) {
        if (this.logger instanceof LoggingEventAware) {
            ((LoggingEventAware)this.logger).log(loggingEvent);
        }
        else {
            this.logViaPublicLoggerAPI(loggingEvent);
        }
    }
    
    private void logViaPublicLoggerAPI(final LoggingEvent loggingEvent) {
        final Object[] argumentArray = loggingEvent.getArgumentArray();
        final int n = (argumentArray == null) ? 0 : argumentArray.length;
        final Throwable throwable = loggingEvent.getThrowable();
        final int n2 = (throwable != null) ? 1 : 0;
        final String message = loggingEvent.getMessage();
        final Object[] array = new Object[n + n2];
        if (argumentArray != null) {
            System.arraycopy(argumentArray, 0, array, 0, n);
        }
        if (throwable != null) {
            array[n] = throwable;
        }
        final String mergeMarkersAndKeyValuePairs = this.mergeMarkersAndKeyValuePairs(loggingEvent, message);
        switch (loggingEvent.getLevel()) {
            case TRACE: {
                this.logger.trace(mergeMarkersAndKeyValuePairs, array);
                break;
            }
            case DEBUG: {
                this.logger.debug(mergeMarkersAndKeyValuePairs, array);
                break;
            }
            case INFO: {
                this.logger.info(mergeMarkersAndKeyValuePairs, array);
                break;
            }
            case WARN: {
                this.logger.warn(mergeMarkersAndKeyValuePairs, array);
                break;
            }
            case ERROR: {
                this.logger.error(mergeMarkersAndKeyValuePairs, array);
                break;
            }
        }
    }
    
    private String mergeMarkersAndKeyValuePairs(final LoggingEvent loggingEvent, final String str) {
        StringBuilder sb = null;
        if (this.loggingEvent.getMarkers() != null) {
            sb = new StringBuilder();
            final Iterator<Marker> iterator = loggingEvent.getMarkers().iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next());
                sb.append(' ');
            }
        }
        if (loggingEvent.getKeyValuePairs() != null) {
            if (sb == null) {
                sb = new StringBuilder();
            }
            for (final KeyValuePair keyValuePair : loggingEvent.getKeyValuePairs()) {
                sb.append(keyValuePair.key);
                sb.append('=');
                sb.append(keyValuePair.value);
                sb.append(' ');
            }
        }
        if (sb != null) {
            sb.append(str);
            return sb.toString();
        }
        return str;
    }
    
    @Override
    public void log(final Supplier<String> supplier) {
        if (supplier == null) {
            this.log((String)null);
        }
        else {
            this.log(supplier.get());
        }
    }
    
    @Override
    public LoggingEventBuilder addKeyValue(final String s, final Object o) {
        this.loggingEvent.addKeyValue(s, o);
        return this;
    }
    
    @Override
    public LoggingEventBuilder addKeyValue(final String s, final Supplier<Object> supplier) {
        this.loggingEvent.addKeyValue(s, supplier.get());
        return this;
    }
}
