

package me.TechsCode.EnderPermissions.dependencies.slf4j.helpers;

import me.TechsCode.EnderPermissions.dependencies.slf4j.Marker;

public abstract class LegacyAbstractLogger extends AbstractLogger
{
    private static final long serialVersionUID = -7041884104854048950L;
    
    @Override
    public boolean isTraceEnabled(final Marker marker) {
        return this.isTraceEnabled();
    }
    
    @Override
    public boolean isDebugEnabled(final Marker marker) {
        return this.isDebugEnabled();
    }
    
    @Override
    public boolean isInfoEnabled(final Marker marker) {
        return this.isInfoEnabled();
    }
    
    @Override
    public boolean isWarnEnabled(final Marker marker) {
        return this.isWarnEnabled();
    }
    
    @Override
    public boolean isErrorEnabled(final Marker marker) {
        return this.isErrorEnabled();
    }
}
