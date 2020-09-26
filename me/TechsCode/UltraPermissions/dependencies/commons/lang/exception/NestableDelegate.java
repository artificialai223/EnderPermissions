

package me.TechsCode.EnderPermissions.dependencies.commons.lang.exception;

import java.util.Collection;
import java.util.Arrays;
import java.io.Writer;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.io.Serializable;

public class NestableDelegate implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final transient String MUST_BE_THROWABLE = "The Nestable implementation passed to the NestableDelegate(Nestable) constructor must extend java.lang.Throwable";
    private Throwable nestable;
    public static boolean topDown;
    public static boolean trimStackFrames;
    public static boolean matchSubclasses;
    
    public NestableDelegate(final Nestable nestable) {
        this.nestable = null;
        if (nestable instanceof Throwable) {
            this.nestable = (Throwable)nestable;
            return;
        }
        throw new IllegalArgumentException("The Nestable implementation passed to the NestableDelegate(Nestable) constructor must extend java.lang.Throwable");
    }
    
    public String getMessage(final int n) {
        final Throwable throwable = this.getThrowable(n);
        if (Nestable.class.isInstance(throwable)) {
            return ((Nestable)throwable).getMessage(0);
        }
        return throwable.getMessage();
    }
    
    public String getMessage(final String str) {
        final Throwable cause = ExceptionUtils.getCause(this.nestable);
        final String str2 = (cause == null) ? null : cause.getMessage();
        if (cause == null || str2 == null) {
            return str;
        }
        if (str == null) {
            return str2;
        }
        return str + ": " + str2;
    }
    
    public String[] getMessages() {
        final Throwable[] throwables = this.getThrowables();
        final String[] array = new String[throwables.length];
        for (int i = 0; i < throwables.length; ++i) {
            array[i] = (Nestable.class.isInstance(throwables[i]) ? ((Nestable)throwables[i]).getMessage(0) : throwables[i].getMessage());
        }
        return array;
    }
    
    public Throwable getThrowable(final int n) {
        if (n == 0) {
            return this.nestable;
        }
        return this.getThrowables()[n];
    }
    
    public int getThrowableCount() {
        return ExceptionUtils.getThrowableCount(this.nestable);
    }
    
    public Throwable[] getThrowables() {
        return ExceptionUtils.getThrowables(this.nestable);
    }
    
    public int indexOfThrowable(final Class clazz, final int n) {
        if (clazz == null) {
            return -1;
        }
        if (n < 0) {
            throw new IndexOutOfBoundsException("The start index was out of bounds: " + n);
        }
        final Throwable[] throwables = ExceptionUtils.getThrowables(this.nestable);
        if (n >= throwables.length) {
            throw new IndexOutOfBoundsException("The start index was out of bounds: " + n + " >= " + throwables.length);
        }
        if (NestableDelegate.matchSubclasses) {
            for (int i = n; i < throwables.length; ++i) {
                if (clazz.isAssignableFrom(throwables[i].getClass())) {
                    return i;
                }
            }
        }
        else {
            for (int j = n; j < throwables.length; ++j) {
                if (clazz.equals(throwables[j].getClass())) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    public void printStackTrace() {
        this.printStackTrace(System.err);
    }
    
    public void printStackTrace(final PrintStream out) {
        synchronized (out) {
            final PrintWriter printWriter = new PrintWriter(out, false);
            this.printStackTrace(printWriter);
            printWriter.flush();
        }
    }
    
    public void printStackTrace(final PrintWriter s) {
        Throwable t = this.nestable;
        if (ExceptionUtils.isThrowableNested()) {
            if (t instanceof Nestable) {
                ((Nestable)t).printPartialStackTrace(s);
            }
            else {
                t.printStackTrace(s);
            }
            return;
        }
        final ArrayList<String[]> list = new ArrayList<String[]>();
        while (t != null) {
            list.add(this.getStackFrames(t));
            t = ExceptionUtils.getCause(t);
        }
        String s2 = "Caused by: ";
        if (!NestableDelegate.topDown) {
            s2 = "Rethrown as: ";
            Collections.reverse(list);
        }
        if (NestableDelegate.trimStackFrames) {
            this.trimStackFrames(list);
        }
        synchronized (s) {
            final Iterator<Object> iterator = list.iterator();
            while (iterator.hasNext()) {
                final String[] array = iterator.next();
                for (int i = 0; i < array.length; ++i) {
                    s.println(array[i]);
                }
                if (iterator.hasNext()) {
                    s.print(s2);
                }
            }
        }
    }
    
    protected String[] getStackFrames(final Throwable t) {
        final StringWriter out = new StringWriter();
        final PrintWriter s = new PrintWriter(out, true);
        if (t instanceof Nestable) {
            ((Nestable)t).printPartialStackTrace(s);
        }
        else {
            t.printStackTrace(s);
        }
        return ExceptionUtils.getStackFrames(out.getBuffer().toString());
    }
    
    protected void trimStackFrames(final List list) {
        for (int i = list.size() - 1; i > 0; --i) {
            final String[] a = list.get(i);
            final String[] a2 = list.get(i - 1);
            final ArrayList list2 = new ArrayList<String>(Arrays.asList(a));
            ExceptionUtils.removeCommonFrames(list2, new ArrayList(Arrays.asList(a2)));
            final int j = a.length - list2.size();
            if (j > 0) {
                list2.add("\t... " + j + " more");
                list.set(i, list2.toArray((Object[])new String[list2.size()]));
            }
        }
    }
    
    static {
        NestableDelegate.topDown = true;
        NestableDelegate.trimStackFrames = true;
        NestableDelegate.matchSubclasses = true;
    }
}
