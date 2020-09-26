

package me.TechsCode.EnderPermissions.dependencies.slf4j.helpers;

import me.TechsCode.EnderPermissions.dependencies.slf4j.Marker;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;

public abstract class MarkerIgnoringBase extends NamedLoggerBase implements Logger
{
    private static final long serialVersionUID = 9044267456635152283L;
    
    @Override
    public boolean isTraceEnabled(final Marker marker) {
        return this.isTraceEnabled();
    }
    
    @Override
    public void trace(final Marker marker, final String s) {
        this.trace(s);
    }
    
    @Override
    public void trace(final Marker marker, final String s, final Object o) {
        this.trace(s, o);
    }
    
    @Override
    public void trace(final Marker marker, final String s, final Object o, final Object o2) {
        this.trace(s, o, o2);
    }
    
    @Override
    public void trace(final Marker marker, final String s, final Object... array) {
        this.trace(s, array);
    }
    
    @Override
    public void trace(final Marker marker, final String s, final Throwable t) {
        this.trace(s, t);
    }
    
    @Override
    public boolean isDebugEnabled(final Marker marker) {
        return this.isDebugEnabled();
    }
    
    @Override
    public void debug(final Marker marker, final String s) {
        this.debug(s);
    }
    
    @Override
    public void debug(final Marker marker, final String s, final Object o) {
        this.debug(s, o);
    }
    
    @Override
    public void debug(final Marker marker, final String s, final Object o, final Object o2) {
        this.debug(s, o, o2);
    }
    
    @Override
    public void debug(final Marker marker, final String s, final Object... array) {
        this.debug(s, array);
    }
    
    @Override
    public void debug(final Marker marker, final String s, final Throwable t) {
        this.debug(s, t);
    }
    
    @Override
    public boolean isInfoEnabled(final Marker marker) {
        return this.isInfoEnabled();
    }
    
    @Override
    public void info(final Marker marker, final String s) {
        this.info(s);
    }
    
    @Override
    public void info(final Marker marker, final String s, final Object o) {
        this.info(s, o);
    }
    
    @Override
    public void info(final Marker marker, final String s, final Object o, final Object o2) {
        this.info(s, o, o2);
    }
    
    @Override
    public void info(final Marker marker, final String s, final Object... array) {
        this.info(s, array);
    }
    
    @Override
    public void info(final Marker marker, final String s, final Throwable t) {
        this.info(s, t);
    }
    
    @Override
    public boolean isWarnEnabled(final Marker marker) {
        return this.isWarnEnabled();
    }
    
    @Override
    public void warn(final Marker marker, final String s) {
        this.warn(s);
    }
    
    @Override
    public void warn(final Marker marker, final String s, final Object o) {
        this.warn(s, o);
    }
    
    @Override
    public void warn(final Marker marker, final String s, final Object o, final Object o2) {
        this.warn(s, o, o2);
    }
    
    @Override
    public void warn(final Marker marker, final String s, final Object... array) {
        this.warn(s, array);
    }
    
    @Override
    public void warn(final Marker marker, final String s, final Throwable t) {
        this.warn(s, t);
    }
    
    @Override
    public boolean isErrorEnabled(final Marker marker) {
        return this.isErrorEnabled();
    }
    
    @Override
    public void error(final Marker marker, final String s) {
        this.error(s);
    }
    
    @Override
    public void error(final Marker marker, final String s, final Object o) {
        this.error(s, o);
    }
    
    @Override
    public void error(final Marker marker, final String s, final Object o, final Object o2) {
        this.error(s, o, o2);
    }
    
    @Override
    public void error(final Marker marker, final String s, final Object... array) {
        this.error(s, array);
    }
    
    @Override
    public void error(final Marker marker, final String s, final Throwable t) {
        this.error(s, t);
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "(" + this.getName() + ")";
    }
}
