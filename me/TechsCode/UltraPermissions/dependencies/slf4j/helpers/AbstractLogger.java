

package me.TechsCode.EnderPermissions.dependencies.slf4j.helpers;

import me.TechsCode.EnderPermissions.dependencies.slf4j.Marker;
import me.TechsCode.EnderPermissions.dependencies.slf4j.event.Level;
import me.TechsCode.EnderPermissions.dependencies.slf4j.LoggerFactory;
import java.io.Serializable;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;

public abstract class AbstractLogger implements Logger, Serializable
{
    private static final long serialVersionUID = -2529255052481744503L;
    protected String name;
    
    @Override
    public String getName() {
        return this.name;
    }
    
    protected Object readResolve() {
        return LoggerFactory.getLogger(this.getName());
    }
    
    @Override
    public void trace(final String s) {
        if (this.isTraceEnabled()) {
            this.handle_0ArgsCall(Level.TRACE, null, s, null);
        }
    }
    
    @Override
    public void trace(final String s, final Object o) {
        if (this.isTraceEnabled()) {
            this.handle_1ArgsCall(Level.TRACE, null, s, o);
        }
    }
    
    @Override
    public void trace(final String s, final Object o, final Object o2) {
        if (this.isTraceEnabled()) {
            this.handle2ArgsCall(Level.TRACE, null, s, o, o2);
        }
    }
    
    @Override
    public void trace(final String s, final Object... array) {
        if (this.isTraceEnabled()) {
            this.handleArgArrayCall(Level.TRACE, null, s, array);
        }
    }
    
    @Override
    public void trace(final String s, final Throwable t) {
        if (this.isTraceEnabled()) {
            this.handle_0ArgsCall(Level.TRACE, null, s, t);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String s) {
        if (this.isTraceEnabled(marker)) {
            this.handle_0ArgsCall(Level.TRACE, marker, s, null);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String s, final Object o) {
        if (this.isTraceEnabled(marker)) {
            this.handle_1ArgsCall(Level.TRACE, marker, s, o);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String s, final Object o, final Object o2) {
        if (this.isTraceEnabled(marker)) {
            this.handle2ArgsCall(Level.TRACE, marker, s, o, o2);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String s, final Object... array) {
        if (this.isTraceEnabled(marker)) {
            this.handleArgArrayCall(Level.TRACE, marker, s, array);
        }
    }
    
    @Override
    public void trace(final Marker marker, final String s, final Throwable t) {
        if (this.isTraceEnabled(marker)) {
            this.handle_0ArgsCall(Level.TRACE, marker, s, t);
        }
    }
    
    @Override
    public void debug(final String s) {
        if (this.isDebugEnabled()) {
            this.handle_0ArgsCall(Level.DEBUG, null, s, null);
        }
    }
    
    @Override
    public void debug(final String s, final Object o) {
        if (this.isDebugEnabled()) {
            this.handle_1ArgsCall(Level.DEBUG, null, s, o);
        }
    }
    
    @Override
    public void debug(final String s, final Object o, final Object o2) {
        if (this.isDebugEnabled()) {
            this.handle2ArgsCall(Level.DEBUG, null, s, o, o2);
        }
    }
    
    @Override
    public void debug(final String s, final Object... array) {
        if (this.isDebugEnabled()) {
            this.handleArgArrayCall(Level.DEBUG, null, s, array);
        }
    }
    
    @Override
    public void debug(final String s, final Throwable t) {
        if (this.isDebugEnabled()) {
            this.handle_0ArgsCall(Level.DEBUG, null, s, t);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String s) {
        if (this.isDebugEnabled(marker)) {
            this.handle_0ArgsCall(Level.DEBUG, marker, s, null);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String s, final Object o) {
        if (this.isDebugEnabled(marker)) {
            this.handle_1ArgsCall(Level.DEBUG, marker, s, o);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String s, final Object o, final Object o2) {
        if (this.isDebugEnabled(marker)) {
            this.handle2ArgsCall(Level.DEBUG, marker, s, o, o2);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String s, final Object... array) {
        if (this.isDebugEnabled(marker)) {
            this.handleArgArrayCall(Level.DEBUG, marker, s, array);
        }
    }
    
    @Override
    public void debug(final Marker marker, final String s, final Throwable t) {
        if (this.isDebugEnabled(marker)) {
            this.handle_0ArgsCall(Level.DEBUG, marker, s, t);
        }
    }
    
    @Override
    public void info(final String s) {
        if (this.isInfoEnabled()) {
            this.handle_0ArgsCall(Level.INFO, null, s, null);
        }
    }
    
    @Override
    public void info(final String s, final Object o) {
        if (this.isInfoEnabled()) {
            this.handle_1ArgsCall(Level.INFO, null, s, o);
        }
    }
    
    @Override
    public void info(final String s, final Object o, final Object o2) {
        if (this.isInfoEnabled()) {
            this.handle2ArgsCall(Level.INFO, null, s, o, o2);
        }
    }
    
    @Override
    public void info(final String s, final Object... array) {
        if (this.isInfoEnabled()) {
            this.handleArgArrayCall(Level.INFO, null, s, array);
        }
    }
    
    @Override
    public void info(final String s, final Throwable t) {
        if (this.isInfoEnabled()) {
            this.handle_0ArgsCall(Level.INFO, null, s, t);
        }
    }
    
    @Override
    public void info(final Marker marker, final String s) {
        if (this.isInfoEnabled(marker)) {
            this.handle_0ArgsCall(Level.INFO, marker, s, null);
        }
    }
    
    @Override
    public void info(final Marker marker, final String s, final Object o) {
        if (this.isInfoEnabled(marker)) {
            this.handle_1ArgsCall(Level.INFO, marker, s, o);
        }
    }
    
    @Override
    public void info(final Marker marker, final String s, final Object o, final Object o2) {
        if (this.isInfoEnabled(marker)) {
            this.handle2ArgsCall(Level.INFO, marker, s, o, o2);
        }
    }
    
    @Override
    public void info(final Marker marker, final String s, final Object... array) {
        if (this.isInfoEnabled(marker)) {
            this.handleArgArrayCall(Level.INFO, marker, s, array);
        }
    }
    
    @Override
    public void info(final Marker marker, final String s, final Throwable t) {
        if (this.isInfoEnabled(marker)) {
            this.handle_0ArgsCall(Level.INFO, marker, s, t);
        }
    }
    
    @Override
    public void warn(final String s) {
        if (this.isWarnEnabled()) {
            this.handle_0ArgsCall(Level.WARN, null, s, null);
        }
    }
    
    @Override
    public void warn(final String s, final Object o) {
        if (this.isWarnEnabled()) {
            this.handle_1ArgsCall(Level.WARN, null, s, o);
        }
    }
    
    @Override
    public void warn(final String s, final Object o, final Object o2) {
        if (this.isWarnEnabled()) {
            this.handle2ArgsCall(Level.WARN, null, s, o, o2);
        }
    }
    
    @Override
    public void warn(final String s, final Object... array) {
        if (this.isWarnEnabled()) {
            this.handleArgArrayCall(Level.WARN, null, s, array);
        }
    }
    
    @Override
    public void warn(final String s, final Throwable t) {
        if (this.isWarnEnabled()) {
            this.handle_0ArgsCall(Level.WARN, null, s, t);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String s) {
        if (this.isWarnEnabled(marker)) {
            this.handle_0ArgsCall(Level.WARN, marker, s, null);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String s, final Object o) {
        if (this.isWarnEnabled(marker)) {
            this.handle_1ArgsCall(Level.WARN, marker, s, o);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String s, final Object o, final Object o2) {
        if (this.isWarnEnabled(marker)) {
            this.handle2ArgsCall(Level.WARN, marker, s, o, o2);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String s, final Object... array) {
        if (this.isWarnEnabled(marker)) {
            this.handleArgArrayCall(Level.WARN, marker, s, array);
        }
    }
    
    @Override
    public void warn(final Marker marker, final String s, final Throwable t) {
        if (this.isWarnEnabled(marker)) {
            this.handle_0ArgsCall(Level.WARN, marker, s, t);
        }
    }
    
    @Override
    public void error(final String s) {
        if (this.isErrorEnabled()) {
            this.handle_0ArgsCall(Level.ERROR, null, s, null);
        }
    }
    
    @Override
    public void error(final String s, final Object o) {
        if (this.isErrorEnabled()) {
            this.handle_1ArgsCall(Level.ERROR, null, s, o);
        }
    }
    
    @Override
    public void error(final String s, final Object o, final Object o2) {
        if (this.isErrorEnabled()) {
            this.handle2ArgsCall(Level.ERROR, null, s, o, o2);
        }
    }
    
    @Override
    public void error(final String s, final Object... array) {
        if (this.isErrorEnabled()) {
            this.handleArgArrayCall(Level.ERROR, null, s, array);
        }
    }
    
    @Override
    public void error(final String s, final Throwable t) {
        if (this.isErrorEnabled()) {
            this.handle_0ArgsCall(Level.ERROR, null, s, t);
        }
    }
    
    @Override
    public void error(final Marker marker, final String s) {
        if (this.isErrorEnabled(marker)) {
            this.handle_0ArgsCall(Level.ERROR, marker, s, null);
        }
    }
    
    @Override
    public void error(final Marker marker, final String s, final Object o) {
        if (this.isErrorEnabled(marker)) {
            this.handle_1ArgsCall(Level.ERROR, marker, s, o);
        }
    }
    
    @Override
    public void error(final Marker marker, final String s, final Object o, final Object o2) {
        if (this.isErrorEnabled(marker)) {
            this.handle2ArgsCall(Level.ERROR, marker, s, o, o2);
        }
    }
    
    @Override
    public void error(final Marker marker, final String s, final Object... array) {
        if (this.isErrorEnabled(marker)) {
            this.handleArgArrayCall(Level.ERROR, marker, s, array);
        }
    }
    
    @Override
    public void error(final Marker marker, final String s, final Throwable t) {
        if (this.isErrorEnabled(marker)) {
            this.handle_0ArgsCall(Level.ERROR, marker, s, t);
        }
    }
    
    private void handle_0ArgsCall(final Level level, final Marker marker, final String s, final Throwable t) {
        this.handleNormalizedLoggingCall(level, marker, s, null, t);
    }
    
    private void handle_1ArgsCall(final Level level, final Marker marker, final String s, final Object o) {
        this.handleNormalizedLoggingCall(level, marker, s, new Object[] { o }, null);
    }
    
    private void handle2ArgsCall(final Level level, final Marker marker, final String s, final Object o, final Object o2) {
        if (o2 instanceof Throwable) {
            this.handleNormalizedLoggingCall(level, marker, s, new Object[] { o }, (Throwable)o2);
        }
        else {
            this.handleNormalizedLoggingCall(level, marker, s, new Object[] { o, o2 }, null);
        }
    }
    
    private void handleArgArrayCall(final Level level, final Marker marker, final String s, final Object[] array) {
        final Throwable throwableCandidate = MessageFormatter.getThrowableCandidate(array);
        if (throwableCandidate != null) {
            this.handleNormalizedLoggingCall(level, marker, s, MessageFormatter.trimmedCopy(array), throwableCandidate);
        }
        else {
            this.handleNormalizedLoggingCall(level, marker, s, array, null);
        }
    }
    
    protected abstract String getFullyQualifiedCallerName();
    
    protected abstract void handleNormalizedLoggingCall(final Level p0, final Marker p1, final String p2, final Object[] p3, final Throwable p4);
}
