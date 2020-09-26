

package me.TechsCode.EnderPermissions.dependencies.commons.lang.exception;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.ClassUtils;
import java.util.StringTokenizer;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.SystemUtils;
import java.io.Writer;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.sql.SQLException;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.ArrayUtils;
import java.util.Collection;
import java.util.Arrays;
import java.lang.reflect.InvocationTargetException;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.NullArgumentException;
import java.util.ArrayList;
import java.util.List;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.StringUtils;
import java.lang.reflect.Method;

public class ExceptionUtils
{
    static final String WRAPPED_MARKER = " [wrapped] ";
    private static final Object CAUSE_METHOD_NAMES_LOCK;
    private static String[] CAUSE_METHOD_NAMES;
    private static final Method THROWABLE_CAUSE_METHOD;
    private static final Method THROWABLE_INITCAUSE_METHOD;
    
    public static void addCauseMethodName(final String s) {
        if (StringUtils.isNotEmpty(s) && !isCauseMethodName(s)) {
            final ArrayList causeMethodNameList = getCauseMethodNameList();
            if (causeMethodNameList.add(s)) {
                synchronized (ExceptionUtils.CAUSE_METHOD_NAMES_LOCK) {
                    ExceptionUtils.CAUSE_METHOD_NAMES = toArray(causeMethodNameList);
                }
            }
        }
    }
    
    public static void removeCauseMethodName(final String s) {
        if (StringUtils.isNotEmpty(s)) {
            final ArrayList causeMethodNameList = getCauseMethodNameList();
            if (causeMethodNameList.remove(s)) {
                synchronized (ExceptionUtils.CAUSE_METHOD_NAMES_LOCK) {
                    ExceptionUtils.CAUSE_METHOD_NAMES = toArray(causeMethodNameList);
                }
            }
        }
    }
    
    public static boolean setCause(final Throwable t, final Throwable t2) {
        if (t == null) {
            throw new NullArgumentException("target");
        }
        final Object[] array = { t2 };
        boolean b = false;
        if (ExceptionUtils.THROWABLE_INITCAUSE_METHOD != null) {
            try {
                ExceptionUtils.THROWABLE_INITCAUSE_METHOD.invoke(t, array);
                b = true;
            }
            catch (IllegalAccessException ex) {}
            catch (InvocationTargetException ex2) {}
        }
        try {
            t.getClass().getMethod("setCause", Throwable.class).invoke(t, array);
            b = true;
        }
        catch (NoSuchMethodException ex3) {}
        catch (IllegalAccessException ex4) {}
        catch (InvocationTargetException ex5) {}
        return b;
    }
    
    private static String[] toArray(final List list) {
        return list.toArray(new String[list.size()]);
    }
    
    private static ArrayList getCauseMethodNameList() {
        synchronized (ExceptionUtils.CAUSE_METHOD_NAMES_LOCK) {
            return new ArrayList((Collection<? extends E>)Arrays.asList(ExceptionUtils.CAUSE_METHOD_NAMES));
        }
    }
    
    public static boolean isCauseMethodName(final String s) {
        synchronized (ExceptionUtils.CAUSE_METHOD_NAMES_LOCK) {
            return ArrayUtils.indexOf(ExceptionUtils.CAUSE_METHOD_NAMES, s) >= 0;
        }
    }
    
    public static Throwable getCause(final Throwable t) {
        synchronized (ExceptionUtils.CAUSE_METHOD_NAMES_LOCK) {
            return getCause(t, ExceptionUtils.CAUSE_METHOD_NAMES);
        }
    }
    
    public static Throwable getCause(final Throwable t, String[] cause_METHOD_NAMES) {
        if (t == null) {
            return null;
        }
        Throwable t2 = getCauseUsingWellKnownTypes(t);
        if (t2 == null) {
            if (cause_METHOD_NAMES == null) {
                synchronized (ExceptionUtils.CAUSE_METHOD_NAMES_LOCK) {
                    cause_METHOD_NAMES = ExceptionUtils.CAUSE_METHOD_NAMES;
                }
            }
            for (int i = 0; i < cause_METHOD_NAMES.length; ++i) {
                final String s = cause_METHOD_NAMES[i];
                if (s != null) {
                    t2 = getCauseUsingMethodName(t, s);
                    if (t2 != null) {
                        break;
                    }
                }
            }
            if (t2 == null) {
                t2 = getCauseUsingFieldName(t, "detail");
            }
        }
        return t2;
    }
    
    public static Throwable getRootCause(final Throwable t) {
        final List throwableList = getThrowableList(t);
        return (throwableList.size() < 2) ? null : throwableList.get(throwableList.size() - 1);
    }
    
    private static Throwable getCauseUsingWellKnownTypes(final Throwable t) {
        if (t instanceof Nestable) {
            return ((Nestable)t).getCause();
        }
        if (t instanceof SQLException) {
            return ((SQLException)t).getNextException();
        }
        if (t instanceof InvocationTargetException) {
            return ((InvocationTargetException)t).getTargetException();
        }
        return null;
    }
    
    private static Throwable getCauseUsingMethodName(final Throwable obj, final String name) {
        Method method = null;
        try {
            method = obj.getClass().getMethod(name, (Class<?>[])null);
        }
        catch (NoSuchMethodException ex) {}
        catch (SecurityException ex2) {}
        if (method != null && Throwable.class.isAssignableFrom(method.getReturnType())) {
            try {
                return (Throwable)method.invoke(obj, ArrayUtils.EMPTY_OBJECT_ARRAY);
            }
            catch (IllegalAccessException ex3) {}
            catch (IllegalArgumentException ex4) {}
            catch (InvocationTargetException ex5) {}
        }
        return null;
    }
    
    private static Throwable getCauseUsingFieldName(final Throwable obj, final String name) {
        Field field = null;
        try {
            field = obj.getClass().getField(name);
        }
        catch (NoSuchFieldException ex) {}
        catch (SecurityException ex2) {}
        if (field != null && Throwable.class.isAssignableFrom(field.getType())) {
            try {
                return (Throwable)field.get(obj);
            }
            catch (IllegalAccessException ex3) {}
            catch (IllegalArgumentException ex4) {}
        }
        return null;
    }
    
    public static boolean isThrowableNested() {
        return ExceptionUtils.THROWABLE_CAUSE_METHOD != null;
    }
    
    public static boolean isNestedThrowable(final Throwable t) {
        if (t == null) {
            return false;
        }
        if (t instanceof Nestable) {
            return true;
        }
        if (t instanceof SQLException) {
            return true;
        }
        if (t instanceof InvocationTargetException) {
            return true;
        }
        if (isThrowableNested()) {
            return true;
        }
        final Class<? extends Throwable> class1 = t.getClass();
        synchronized (ExceptionUtils.CAUSE_METHOD_NAMES_LOCK) {
            for (int i = 0; i < ExceptionUtils.CAUSE_METHOD_NAMES.length; ++i) {
                try {
                    final Method method = class1.getMethod(ExceptionUtils.CAUSE_METHOD_NAMES[i], (Class[])null);
                    if (method != null && Throwable.class.isAssignableFrom(method.getReturnType())) {
                        return true;
                    }
                }
                catch (NoSuchMethodException ex) {}
                catch (SecurityException ex2) {}
            }
        }
        try {
            if (class1.getField("detail") != null) {
                return true;
            }
        }
        catch (NoSuchFieldException ex3) {}
        catch (SecurityException ex4) {}
        return false;
    }
    
    public static int getThrowableCount(final Throwable t) {
        return getThrowableList(t).size();
    }
    
    public static Throwable[] getThrowables(final Throwable t) {
        final List throwableList = getThrowableList(t);
        return throwableList.toArray(new Throwable[throwableList.size()]);
    }
    
    public static List getThrowableList(Throwable cause) {
        ArrayList list;
        for (list = new ArrayList<Throwable>(); cause != null && !list.contains(cause); cause = getCause(cause)) {
            list.add(cause);
        }
        return list;
    }
    
    public static int indexOfThrowable(final Throwable t, final Class clazz) {
        return indexOf(t, clazz, 0, false);
    }
    
    public static int indexOfThrowable(final Throwable t, final Class clazz, final int n) {
        return indexOf(t, clazz, n, false);
    }
    
    public static int indexOfType(final Throwable t, final Class clazz) {
        return indexOf(t, clazz, 0, true);
    }
    
    public static int indexOfType(final Throwable t, final Class clazz, final int n) {
        return indexOf(t, clazz, n, true);
    }
    
    private static int indexOf(final Throwable t, final Class clazz, int n, final boolean b) {
        if (t == null || clazz == null) {
            return -1;
        }
        if (n < 0) {
            n = 0;
        }
        final Throwable[] throwables = getThrowables(t);
        if (n >= throwables.length) {
            return -1;
        }
        if (b) {
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
    
    public static void printRootCauseStackTrace(final Throwable t) {
        printRootCauseStackTrace(t, System.err);
    }
    
    public static void printRootCauseStackTrace(final Throwable t, final PrintStream printStream) {
        if (t == null) {
            return;
        }
        if (printStream == null) {
            throw new IllegalArgumentException("The PrintStream must not be null");
        }
        final String[] rootCauseStackTrace = getRootCauseStackTrace(t);
        for (int i = 0; i < rootCauseStackTrace.length; ++i) {
            printStream.println(rootCauseStackTrace[i]);
        }
        printStream.flush();
    }
    
    public static void printRootCauseStackTrace(final Throwable t, final PrintWriter printWriter) {
        if (t == null) {
            return;
        }
        if (printWriter == null) {
            throw new IllegalArgumentException("The PrintWriter must not be null");
        }
        final String[] rootCauseStackTrace = getRootCauseStackTrace(t);
        for (int i = 0; i < rootCauseStackTrace.length; ++i) {
            printWriter.println(rootCauseStackTrace[i]);
        }
        printWriter.flush();
    }
    
    public static String[] getRootCauseStackTrace(final Throwable t) {
        if (t == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final Throwable[] throwables = getThrowables(t);
        final int length = throwables.length;
        final ArrayList<String> list = new ArrayList<String>();
        List list2 = getStackFrameList(throwables[length - 1]);
        int n = length;
        while (--n >= 0) {
            final List list3 = list2;
            if (n != 0) {
                list2 = getStackFrameList(throwables[n - 1]);
                removeCommonFrames(list3, list2);
            }
            if (n == length - 1) {
                list.add(throwables[n].toString());
            }
            else {
                list.add(" [wrapped] " + throwables[n].toString());
            }
            for (int i = 0; i < list3.size(); ++i) {
                list.add(list3.get(i));
            }
        }
        return list.toArray(new String[0]);
    }
    
    public static void removeCommonFrames(final List list, final List list2) {
        if (list == null || list2 == null) {
            throw new IllegalArgumentException("The List must not be null");
        }
        for (int n = list.size() - 1, n2 = list2.size() - 1; n >= 0 && n2 >= 0; --n, --n2) {
            if (list.get(n).equals(list2.get(n2))) {
                list.remove(n);
            }
        }
    }
    
    public static String getFullStackTrace(final Throwable t) {
        final StringWriter out = new StringWriter();
        final PrintWriter s = new PrintWriter(out, true);
        final Throwable[] throwables = getThrowables(t);
        for (int i = 0; i < throwables.length; ++i) {
            throwables[i].printStackTrace(s);
            if (isNestedThrowable(throwables[i])) {
                break;
            }
        }
        return out.getBuffer().toString();
    }
    
    public static String getStackTrace(final Throwable t) {
        final StringWriter out = new StringWriter();
        t.printStackTrace(new PrintWriter(out, true));
        return out.getBuffer().toString();
    }
    
    public static String[] getStackFrames(final Throwable t) {
        if (t == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return getStackFrames(getStackTrace(t));
    }
    
    static String[] getStackFrames(final String str) {
        final StringTokenizer stringTokenizer = new StringTokenizer(str, SystemUtils.LINE_SEPARATOR);
        final ArrayList<String> list = new ArrayList<String>();
        while (stringTokenizer.hasMoreTokens()) {
            list.add(stringTokenizer.nextToken());
        }
        return toArray(list);
    }
    
    static List getStackFrameList(final Throwable t) {
        final StringTokenizer stringTokenizer = new StringTokenizer(getStackTrace(t), SystemUtils.LINE_SEPARATOR);
        final ArrayList<String> list = new ArrayList<String>();
        boolean b = false;
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            final int index = nextToken.indexOf("at");
            if (index != -1 && nextToken.substring(0, index).trim().length() == 0) {
                b = true;
                list.add(nextToken);
            }
            else {
                if (b) {
                    break;
                }
                continue;
            }
        }
        return list;
    }
    
    public static String getMessage(final Throwable t) {
        if (t == null) {
            return "";
        }
        return ClassUtils.getShortClassName(t, null) + ": " + StringUtils.defaultString(t.getMessage());
    }
    
    public static String getRootCauseMessage(final Throwable t) {
        final Throwable rootCause = getRootCause(t);
        return getMessage((rootCause == null) ? t : rootCause);
    }
    
    static {
        CAUSE_METHOD_NAMES_LOCK = new Object();
        ExceptionUtils.CAUSE_METHOD_NAMES = new String[] { "getCause", "getNextException", "getTargetException", "getException", "getSourceException", "getRootCause", "getCausedByException", "getNested", "getLinkedException", "getNestedException", "getLinkedCause", "getThrowable" };
        Method method;
        try {
            method = Throwable.class.getMethod("getCause", (Class[])null);
        }
        catch (Exception ex) {
            method = null;
        }
        THROWABLE_CAUSE_METHOD = method;
        Method method2;
        try {
            method2 = Throwable.class.getMethod("initCause", Throwable.class);
        }
        catch (Exception ex2) {
            method2 = null;
        }
        THROWABLE_INITCAUSE_METHOD = method2;
    }
}
