

package me.TechsCode.EnderPermissions.dependencies.commons.lang.exception;

import java.io.PrintWriter;
import java.io.PrintStream;

public class NestableException extends Exception implements Nestable
{
    private static final long serialVersionUID = 1L;
    protected NestableDelegate delegate;
    private Throwable cause;
    
    public NestableException() {
        this.delegate = new NestableDelegate(this);
        this.cause = null;
    }
    
    public NestableException(final String message) {
        super(message);
        this.delegate = new NestableDelegate(this);
        this.cause = null;
    }
    
    public NestableException(final Throwable cause) {
        this.delegate = new NestableDelegate(this);
        this.cause = null;
        this.cause = cause;
    }
    
    public NestableException(final String message, final Throwable cause) {
        super(message);
        this.delegate = new NestableDelegate(this);
        this.cause = null;
        this.cause = cause;
    }
    
    public Throwable getCause() {
        return this.cause;
    }
    
    public String getMessage() {
        if (super.getMessage() != null) {
            return super.getMessage();
        }
        if (this.cause != null) {
            return this.cause.toString();
        }
        return null;
    }
    
    public String getMessage(final int n) {
        if (n == 0) {
            return super.getMessage();
        }
        return this.delegate.getMessage(n);
    }
    
    public String[] getMessages() {
        return this.delegate.getMessages();
    }
    
    public Throwable getThrowable(final int n) {
        return this.delegate.getThrowable(n);
    }
    
    public int getThrowableCount() {
        return this.delegate.getThrowableCount();
    }
    
    public Throwable[] getThrowables() {
        return this.delegate.getThrowables();
    }
    
    public int indexOfThrowable(final Class clazz) {
        return this.delegate.indexOfThrowable(clazz, 0);
    }
    
    public int indexOfThrowable(final Class clazz, final int n) {
        return this.delegate.indexOfThrowable(clazz, n);
    }
    
    public void printStackTrace() {
        this.delegate.printStackTrace();
    }
    
    public void printStackTrace(final PrintStream printStream) {
        this.delegate.printStackTrace(printStream);
    }
    
    public void printStackTrace(final PrintWriter printWriter) {
        this.delegate.printStackTrace(printWriter);
    }
    
    public final void printPartialStackTrace(final PrintWriter s) {
        super.printStackTrace(s);
    }
}
