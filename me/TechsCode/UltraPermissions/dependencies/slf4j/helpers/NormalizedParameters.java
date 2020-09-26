

package me.TechsCode.EnderPermissions.dependencies.slf4j.helpers;

import me.TechsCode.EnderPermissions.dependencies.slf4j.event.LoggingEvent;

public class NormalizedParameters
{
    final String message;
    final Object[] arguments;
    final Throwable throwable;
    
    public NormalizedParameters(final String message, final Object[] arguments, final Throwable throwable) {
        this.message = message;
        this.arguments = arguments;
        this.throwable = throwable;
    }
    
    public NormalizedParameters(final String s, final Object[] array) {
        this(s, array, null);
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public Object[] getArguments() {
        return this.arguments;
    }
    
    public Throwable getThrowable() {
        return this.throwable;
    }
    
    public static Throwable getThrowableCandidate(final Object[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        final Object o = array[array.length - 1];
        if (o instanceof Throwable) {
            return (Throwable)o;
        }
        return null;
    }
    
    public static Object[] trimmedCopy(final Object[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalStateException("non-sensical empty or null argument array");
        }
        final int n = array.length - 1;
        final Object[] array2 = new Object[n];
        if (n > 0) {
            System.arraycopy(array, 0, array2, 0, n);
        }
        return array2;
    }
    
    public static NormalizedParameters normalize(final String s, final Object[] array, final Throwable t) {
        if (t != null) {
            return new NormalizedParameters(s, array, t);
        }
        if (array == null || array.length == 0) {
            return new NormalizedParameters(s, array, t);
        }
        final Throwable throwableCandidate = getThrowableCandidate(array);
        if (throwableCandidate != null) {
            return new NormalizedParameters(s, MessageFormatter.trimmedCopy(array), throwableCandidate);
        }
        return new NormalizedParameters(s, array);
    }
    
    public static NormalizedParameters normalize(final LoggingEvent loggingEvent) {
        return normalize(loggingEvent.getMessage(), loggingEvent.getArgumentArray(), loggingEvent.getThrowable());
    }
}
