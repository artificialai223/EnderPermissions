

package me.TechsCode.EnderPermissions.dependencies.commons.lang.reflect;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.ClassUtils;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Modifier;
import java.lang.reflect.Member;
import java.lang.reflect.Constructor;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.ArrayUtils;

public class ConstructorUtils
{
    public static Object invokeConstructor(final Class clazz, final Object o) {
        return invokeConstructor(clazz, new Object[] { o });
    }
    
    public static Object invokeConstructor(final Class clazz, Object[] empty_OBJECT_ARRAY) {
        if (null == empty_OBJECT_ARRAY) {
            empty_OBJECT_ARRAY = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        final Class[] array = new Class[empty_OBJECT_ARRAY.length];
        for (int i = 0; i < empty_OBJECT_ARRAY.length; ++i) {
            array[i] = empty_OBJECT_ARRAY[i].getClass();
        }
        return invokeConstructor(clazz, empty_OBJECT_ARRAY, array);
    }
    
    public static Object invokeConstructor(final Class clazz, Object[] empty_OBJECT_ARRAY, Class[] empty_CLASS_ARRAY) {
        if (empty_CLASS_ARRAY == null) {
            empty_CLASS_ARRAY = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        if (empty_OBJECT_ARRAY == null) {
            empty_OBJECT_ARRAY = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        final Constructor matchingAccessibleConstructor = getMatchingAccessibleConstructor(clazz, empty_CLASS_ARRAY);
        if (null == matchingAccessibleConstructor) {
            throw new NoSuchMethodException("No such accessible constructor on object: " + clazz.getName());
        }
        return matchingAccessibleConstructor.newInstance(empty_OBJECT_ARRAY);
    }
    
    public static Object invokeExactConstructor(final Class clazz, final Object o) {
        return invokeExactConstructor(clazz, new Object[] { o });
    }
    
    public static Object invokeExactConstructor(final Class clazz, Object[] empty_OBJECT_ARRAY) {
        if (null == empty_OBJECT_ARRAY) {
            empty_OBJECT_ARRAY = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        final int length = empty_OBJECT_ARRAY.length;
        final Class[] array = new Class[length];
        for (int i = 0; i < length; ++i) {
            array[i] = empty_OBJECT_ARRAY[i].getClass();
        }
        return invokeExactConstructor(clazz, empty_OBJECT_ARRAY, array);
    }
    
    public static Object invokeExactConstructor(final Class clazz, Object[] empty_OBJECT_ARRAY, Class[] empty_CLASS_ARRAY) {
        if (empty_OBJECT_ARRAY == null) {
            empty_OBJECT_ARRAY = ArrayUtils.EMPTY_OBJECT_ARRAY;
        }
        if (empty_CLASS_ARRAY == null) {
            empty_CLASS_ARRAY = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Constructor accessibleConstructor = getAccessibleConstructor(clazz, empty_CLASS_ARRAY);
        if (null == accessibleConstructor) {
            throw new NoSuchMethodException("No such accessible constructor on object: " + clazz.getName());
        }
        return accessibleConstructor.newInstance(empty_OBJECT_ARRAY);
    }
    
    public static Constructor getAccessibleConstructor(final Class clazz, final Class clazz2) {
        return getAccessibleConstructor(clazz, new Class[] { clazz2 });
    }
    
    public static Constructor getAccessibleConstructor(final Class clazz, final Class[] parameterTypes) {
        try {
            return getAccessibleConstructor(clazz.getConstructor((Class[])parameterTypes));
        }
        catch (NoSuchMethodException ex) {
            return null;
        }
    }
    
    public static Constructor getAccessibleConstructor(final Constructor constructor) {
        return (MemberUtils.isAccessible(constructor) && Modifier.isPublic(constructor.getDeclaringClass().getModifiers())) ? constructor : null;
    }
    
    public static Constructor getMatchingAccessibleConstructor(final Class clazz, final Class[] parameterTypes) {
        try {
            final Constructor constructor = clazz.getConstructor((Class[])parameterTypes);
            MemberUtils.setAccessibleWorkaround(constructor);
            return constructor;
        }
        catch (NoSuchMethodException ex) {
            Constructor constructor2 = null;
            final Constructor[] constructors = clazz.getConstructors();
            for (int i = 0; i < constructors.length; ++i) {
                if (ClassUtils.isAssignable(parameterTypes, (Class[])constructors[i].getParameterTypes(), true)) {
                    final Constructor accessibleConstructor = getAccessibleConstructor(constructors[i]);
                    if (accessibleConstructor != null) {
                        MemberUtils.setAccessibleWorkaround(accessibleConstructor);
                        if (constructor2 == null || MemberUtils.compareParameterTypes(accessibleConstructor.getParameterTypes(), constructor2.getParameterTypes(), parameterTypes) < 0) {
                            constructor2 = accessibleConstructor;
                        }
                    }
                }
            }
            return constructor2;
        }
    }
}
