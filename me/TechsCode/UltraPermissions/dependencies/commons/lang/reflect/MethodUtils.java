

package me.TechsCode.EnderPermissions.dependencies.commons.lang.reflect;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.ClassUtils;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Modifier;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.ArrayUtils;

public class MethodUtils
{
    public static Object invokeMethod(final Object o, final String s, final Object o2) {
        return invokeMethod(o, s, new Object[] { o2 });
    }
    
    public static Object invokeMethod(final Object o, final String s, Object[] empty_OBJECT_ARRAY) {
        if (empty_OBJECT_ARRAY == null) {
            empty_OBJECT_ARRAY = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        final int length = empty_OBJECT_ARRAY.length;
        final Class[] array = new Class[length];
        for (int i = 0; i < length; ++i) {
            array[i] = empty_OBJECT_ARRAY[i].getClass();
        }
        return invokeMethod(o, s, empty_OBJECT_ARRAY, array);
    }
    
    public static Object invokeMethod(final Object obj, final String str, Object[] empty_OBJECT_ARRAY, Class[] empty_CLASS_ARRAY) {
        if (empty_CLASS_ARRAY == null) {
            empty_CLASS_ARRAY = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        if (empty_OBJECT_ARRAY == null) {
            empty_OBJECT_ARRAY = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        final Method matchingAccessibleMethod = getMatchingAccessibleMethod(obj.getClass(), str, empty_CLASS_ARRAY);
        if (matchingAccessibleMethod == null) {
            throw new NoSuchMethodException("No such accessible method: " + str + "() on object: " + obj.getClass().getName());
        }
        return matchingAccessibleMethod.invoke(obj, empty_OBJECT_ARRAY);
    }
    
    public static Object invokeExactMethod(final Object o, final String s, final Object o2) {
        return invokeExactMethod(o, s, new Object[] { o2 });
    }
    
    public static Object invokeExactMethod(final Object o, final String s, Object[] empty_OBJECT_ARRAY) {
        if (empty_OBJECT_ARRAY == null) {
            empty_OBJECT_ARRAY = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        final int length = empty_OBJECT_ARRAY.length;
        final Class[] array = new Class[length];
        for (int i = 0; i < length; ++i) {
            array[i] = empty_OBJECT_ARRAY[i].getClass();
        }
        return invokeExactMethod(o, s, empty_OBJECT_ARRAY, array);
    }
    
    public static Object invokeExactMethod(final Object obj, final String str, Object[] empty_OBJECT_ARRAY, Class[] empty_CLASS_ARRAY) {
        if (empty_OBJECT_ARRAY == null) {
            empty_OBJECT_ARRAY = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        if (empty_CLASS_ARRAY == null) {
            empty_CLASS_ARRAY = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Method accessibleMethod = getAccessibleMethod((Class)obj.getClass(), str, empty_CLASS_ARRAY);
        if (accessibleMethod == null) {
            throw new NoSuchMethodException("No such accessible method: " + str + "() on object: " + obj.getClass().getName());
        }
        return accessibleMethod.invoke(obj, empty_OBJECT_ARRAY);
    }
    
    public static Object invokeExactStaticMethod(final Class clazz, final String str, Object[] empty_OBJECT_ARRAY, Class[] empty_CLASS_ARRAY) {
        if (empty_OBJECT_ARRAY == null) {
            empty_OBJECT_ARRAY = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        if (empty_CLASS_ARRAY == null) {
            empty_CLASS_ARRAY = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Method accessibleMethod = getAccessibleMethod(clazz, str, empty_CLASS_ARRAY);
        if (accessibleMethod == null) {
            throw new NoSuchMethodException("No such accessible method: " + str + "() on class: " + clazz.getName());
        }
        return accessibleMethod.invoke(null, empty_OBJECT_ARRAY);
    }
    
    public static Object invokeStaticMethod(final Class clazz, final String s, final Object o) {
        return invokeStaticMethod(clazz, s, new Object[] { o });
    }
    
    public static Object invokeStaticMethod(final Class clazz, final String s, Object[] empty_OBJECT_ARRAY) {
        if (empty_OBJECT_ARRAY == null) {
            empty_OBJECT_ARRAY = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        final int length = empty_OBJECT_ARRAY.length;
        final Class[] array = new Class[length];
        for (int i = 0; i < length; ++i) {
            array[i] = empty_OBJECT_ARRAY[i].getClass();
        }
        return invokeStaticMethod(clazz, s, empty_OBJECT_ARRAY, array);
    }
    
    public static Object invokeStaticMethod(final Class clazz, final String str, Object[] empty_OBJECT_ARRAY, Class[] empty_CLASS_ARRAY) {
        if (empty_CLASS_ARRAY == null) {
            empty_CLASS_ARRAY = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        if (empty_OBJECT_ARRAY == null) {
            empty_OBJECT_ARRAY = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        final Method matchingAccessibleMethod = getMatchingAccessibleMethod(clazz, str, empty_CLASS_ARRAY);
        if (matchingAccessibleMethod == null) {
            throw new NoSuchMethodException("No such accessible method: " + str + "() on class: " + clazz.getName());
        }
        return matchingAccessibleMethod.invoke(null, empty_OBJECT_ARRAY);
    }
    
    public static Object invokeExactStaticMethod(final Class clazz, final String s, final Object o) {
        return invokeExactStaticMethod(clazz, s, new Object[] { o });
    }
    
    public static Object invokeExactStaticMethod(final Class clazz, final String s, Object[] empty_OBJECT_ARRAY) {
        if (empty_OBJECT_ARRAY == null) {
            empty_OBJECT_ARRAY = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        final int length = empty_OBJECT_ARRAY.length;
        final Class[] array = new Class[length];
        for (int i = 0; i < length; ++i) {
            array[i] = empty_OBJECT_ARRAY[i].getClass();
        }
        return invokeExactStaticMethod(clazz, s, empty_OBJECT_ARRAY, array);
    }
    
    public static Method getAccessibleMethod(final Class clazz, final String s, final Class clazz2) {
        return getAccessibleMethod(clazz, s, new Class[] { clazz2 });
    }
    
    public static Method getAccessibleMethod(final Class clazz, final String name, final Class[] parameterTypes) {
        try {
            return getAccessibleMethod(clazz.getMethod(name, (Class[])parameterTypes));
        }
        catch (NoSuchMethodException ex) {
            return null;
        }
    }
    
    public static Method getAccessibleMethod(Method method) {
        if (!MemberUtils.isAccessible(method)) {
            return null;
        }
        final Class<?> declaringClass = method.getDeclaringClass();
        if (Modifier.isPublic(declaringClass.getModifiers())) {
            return method;
        }
        final String name = method.getName();
        final Class<?>[] parameterTypes = method.getParameterTypes();
        method = getAccessibleMethodFromInterfaceNest(declaringClass, name, parameterTypes);
        if (method == null) {
            method = getAccessibleMethodFromSuperclass(declaringClass, name, parameterTypes);
        }
        return method;
    }
    
    private static Method getAccessibleMethodFromSuperclass(final Class clazz, final String name, final Class[] parameterTypes) {
        for (Class clazz2 = clazz.getSuperclass(); clazz2 != null; clazz2 = clazz2.getSuperclass()) {
            if (Modifier.isPublic(clazz2.getModifiers())) {
                try {
                    return clazz2.getMethod(name, (Class[])parameterTypes);
                }
                catch (NoSuchMethodException ex) {
                    return null;
                }
            }
        }
        return null;
    }
    
    private static Method getAccessibleMethodFromInterfaceNest(Class superclass, final String name, final Class[] parameterTypes) {
        Method method = null;
        while (superclass != null) {
            final Class[] interfaces = superclass.getInterfaces();
            for (int i = 0; i < interfaces.length; ++i) {
                if (Modifier.isPublic(interfaces[i].getModifiers())) {
                    try {
                        method = interfaces[i].getDeclaredMethod(name, (Class[])parameterTypes);
                    }
                    catch (NoSuchMethodException ex) {}
                    if (method != null) {
                        break;
                    }
                    method = getAccessibleMethodFromInterfaceNest(interfaces[i], name, parameterTypes);
                    if (method != null) {
                        break;
                    }
                }
            }
            superclass = superclass.getSuperclass();
        }
        return method;
    }
    
    public static Method getMatchingAccessibleMethod(final Class clazz, final String s, final Class[] parameterTypes) {
        try {
            final Method method = clazz.getMethod(s, (Class[])parameterTypes);
            MemberUtils.setAccessibleWorkaround(method);
            return method;
        }
        catch (NoSuchMethodException ex) {
            Method accessibleWorkaround = null;
            final Method[] methods = clazz.getMethods();
            for (int i = 0; i < methods.length; ++i) {
                if (methods[i].getName().equals(s) && ClassUtils.isAssignable(parameterTypes, (Class[])methods[i].getParameterTypes(), true)) {
                    final Method accessibleMethod = getAccessibleMethod(methods[i]);
                    if (accessibleMethod != null && (accessibleWorkaround == null || MemberUtils.compareParameterTypes(accessibleMethod.getParameterTypes(), accessibleWorkaround.getParameterTypes(), parameterTypes) < 0)) {
                        accessibleWorkaround = accessibleMethod;
                    }
                }
            }
            if (accessibleWorkaround != null) {
                MemberUtils.setAccessibleWorkaround(accessibleWorkaround);
            }
            return accessibleWorkaround;
        }
    }
}
