

package me.TechsCode.EnderPermissions.dependencies.slf4j.helpers;

public class NOPLogger extends MarkerIgnoringBase
{
    private static final long serialVersionUID = -517220405410904473L;
    public static final NOPLogger NOP_LOGGER;
    
    protected NOPLogger() {
    }
    
    @Override
    public String getName() {
        return "NOP";
    }
    
    @Override
    public final boolean isTraceEnabled() {
        return false;
    }
    
    @Override
    public final void trace(final String s) {
    }
    
    @Override
    public final void trace(final String s, final Object o) {
    }
    
    @Override
    public final void trace(final String s, final Object o, final Object o2) {
    }
    
    @Override
    public final void trace(final String s, final Object... array) {
    }
    
    @Override
    public final void trace(final String s, final Throwable t) {
    }
    
    @Override
    public final boolean isDebugEnabled() {
        return false;
    }
    
    @Override
    public final void debug(final String s) {
    }
    
    @Override
    public final void debug(final String s, final Object o) {
    }
    
    @Override
    public final void debug(final String s, final Object o, final Object o2) {
    }
    
    @Override
    public final void debug(final String s, final Object... array) {
    }
    
    @Override
    public final void debug(final String s, final Throwable t) {
    }
    
    @Override
    public final boolean isInfoEnabled() {
        return false;
    }
    
    @Override
    public final void info(final String s) {
    }
    
    @Override
    public final void info(final String s, final Object o) {
    }
    
    @Override
    public final void info(final String s, final Object o, final Object o2) {
    }
    
    @Override
    public final void info(final String s, final Object... array) {
    }
    
    @Override
    public final void info(final String s, final Throwable t) {
    }
    
    @Override
    public final boolean isWarnEnabled() {
        return false;
    }
    
    @Override
    public final void warn(final String s) {
    }
    
    @Override
    public final void warn(final String s, final Object o) {
    }
    
    @Override
    public final void warn(final String s, final Object o, final Object o2) {
    }
    
    @Override
    public final void warn(final String s, final Object... array) {
    }
    
    @Override
    public final void warn(final String s, final Throwable t) {
    }
    
    @Override
    public final boolean isErrorEnabled() {
        return false;
    }
    
    @Override
    public final void error(final String s) {
    }
    
    @Override
    public final void error(final String s, final Object o) {
    }
    
    @Override
    public final void error(final String s, final Object o, final Object o2) {
    }
    
    @Override
    public final void error(final String s, final Object... array) {
    }
    
    @Override
    public final void error(final String s, final Throwable t) {
    }
    
    static {
        NOP_LOGGER = new NOPLogger();
    }
}
