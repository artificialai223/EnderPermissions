

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import java.io.PrintWriter;
import java.io.PrintStream;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.exception.NestableDelegate;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.exception.Nestable;

public class NotImplementedException extends UnsupportedOperationException implements Nestable
{
    private static final String DEFAULT_MESSAGE = "Code is not implemented";
    private static final long serialVersionUID = -6894122266938754088L;
    private NestableDelegate delegate;
    private Throwable cause;
    
    public NotImplementedException() {
        super("Code is not implemented");
        this.delegate = new NestableDelegate(this);
    }
    
    public NotImplementedException(final String s) {
        super((s == null) ? "Code is not implemented" : s);
        this.delegate = new NestableDelegate(this);
    }
    
    public NotImplementedException(final Throwable cause) {
        super("Code is not implemented");
        this.delegate = new NestableDelegate(this);
        this.cause = cause;
    }
    
    public NotImplementedException(final String s, final Throwable cause) {
        super((s == null) ? "Code is not implemented" : s);
        this.delegate = new NestableDelegate(this);
        this.cause = cause;
    }
    
    public NotImplementedException(final Class obj) {
        super((obj == null) ? "Code is not implemented" : ("Code is not implemented in " + obj));
        this.delegate = new NestableDelegate(this);
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
