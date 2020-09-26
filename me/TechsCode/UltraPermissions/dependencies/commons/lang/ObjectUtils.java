

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.exception.CloneFailedException;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.reflect.MethodUtils;
import java.lang.reflect.Array;

public class ObjectUtils
{
    public static final Null NULL;
    
    public static Object defaultIfNull(final Object o, final Object o2) {
        return (o != null) ? o : o2;
    }
    
    public static boolean equals(final Object o, final Object obj) {
        return o == obj || (o != null && obj != null && o.equals(obj));
    }
    
    public static boolean notEqual(final Object o, final Object o2) {
        return !equals(o, o2);
    }
    
    public static int hashCode(final Object o) {
        return (o == null) ? 0 : o.hashCode();
    }
    
    public static String identityToString(final Object o) {
        if (o == null) {
            return null;
        }
        final StringBuffer sb = new StringBuffer();
        identityToString(sb, o);
        return sb.toString();
    }
    
    public static void identityToString(final StringBuffer sb, final Object o) {
        if (o == null) {
            throw new NullPointerException("Cannot get the toString of a null identity");
        }
        sb.append(o.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(o)));
    }
    
    public static StringBuffer appendIdentityToString(StringBuffer sb, final Object o) {
        if (o == null) {
            return null;
        }
        if (sb == null) {
            sb = new StringBuffer();
        }
        return sb.append(o.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(o)));
    }
    
    public static String toString(final Object o) {
        return (o == null) ? "" : o.toString();
    }
    
    public static String toString(final Object o, final String s) {
        return (o == null) ? s : o.toString();
    }
    
    public static Object min(final Comparable comparable, final Comparable comparable2) {
        return (compare(comparable, comparable2, true) <= 0) ? comparable : comparable2;
    }
    
    public static Object max(final Comparable comparable, final Comparable comparable2) {
        return (compare(comparable, comparable2, false) >= 0) ? comparable : comparable2;
    }
    
    public static int compare(final Comparable comparable, final Comparable comparable2) {
        return compare(comparable, comparable2, false);
    }
    
    public static int compare(final Comparable comparable, final Comparable comparable2, final boolean b) {
        if (comparable == comparable2) {
            return 0;
        }
        if (comparable == null) {
            return b ? 1 : -1;
        }
        if (comparable2 == null) {
            return b ? -1 : 1;
        }
        return comparable.compareTo(comparable2);
    }
    
    public static Object clone(final Object o) {
        if (o instanceof Cloneable) {
            Object o2;
            if (o.getClass().isArray()) {
                final Class<?> componentType = o.getClass().getComponentType();
                if (!componentType.isPrimitive()) {
                    o2 = ((Object[])o).clone();
                }
                else {
                    int length = Array.getLength(o);
                    o2 = Array.newInstance(componentType, length);
                    while (length-- > 0) {
                        Array.set(o2, length, Array.get(o, length));
                    }
                }
            }
            else {
                try {
                    o2 = MethodUtils.invokeMethod(o, "clone", null);
                }
                catch (NoSuchMethodException ex) {
                    throw new CloneFailedException("Cloneable type " + o.getClass().getName() + " has no clone method", ex);
                }
                catch (IllegalAccessException ex2) {
                    throw new CloneFailedException("Cannot clone Cloneable type " + o.getClass().getName(), ex2);
                }
                catch (InvocationTargetException ex3) {
                    throw new CloneFailedException("Exception cloning Cloneable type " + o.getClass().getName(), ex3.getTargetException());
                }
            }
            return o2;
        }
        return null;
    }
    
    public static Object cloneIfPossible(final Object o) {
        final Object clone = clone(o);
        return (clone == null) ? o : clone;
    }
    
    static {
        NULL = new Null();
    }
    
    public static class Null implements Serializable
    {
        private static final long serialVersionUID = 7092611880189329093L;
        
        Null() {
        }
        
        private Object readResolve() {
            return ObjectUtils.NULL;
        }
    }
}
