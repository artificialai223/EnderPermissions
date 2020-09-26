

package me.TechsCode.EnderPermissions.dependencies.nbt.utils.annotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.invoke.SerializedLambda;
import java.util.function.Function;
import java.lang.reflect.Method;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.annotations.ref.MethodRefrence2;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.annotations.ref.MethodRefrence1;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.annotations.ref.MethodRefrence;
import java.util.HashSet;

public class FAUtil
{
    private static HashSet<String> cache;
    
    public static <T> T getAnnotation(final MethodRefrence methodRefrence, final Class<T> annotationClass) {
        return getInternalMethod(methodRefrence).getAnnotation(annotationClass);
    }
    
    public static <T, Z> T getAnnotation(final MethodRefrence1<Z> methodRefrence1, final Class<T> annotationClass) {
        return getInternalMethod(methodRefrence1).getAnnotation(annotationClass);
    }
    
    public static <T, Z, X> T getAnnotation(final MethodRefrence2<Z, X> methodRefrence2, final Class<T> annotationClass) {
        return getInternalMethod(methodRefrence2).getAnnotation(annotationClass);
    }
    
    public static Method getMethod(final MethodRefrence methodRefrence) {
        return getInternalMethod(methodRefrence);
    }
    
    public static <Z> Method getMethod(final MethodRefrence1<Z> methodRefrence1) {
        return getInternalMethod(methodRefrence1);
    }
    
    public static <T, Z> Method getMethod(final MethodRefrence2<T, Z> methodRefrence2) {
        return getInternalMethod(methodRefrence2);
    }
    
    public static void check(final MethodRefrence methodRefrence, final Function<Method, Boolean> function) {
        checkLambda(methodRefrence, function);
    }
    
    public static <T> T check(final MethodRefrence1<T> methodRefrence1, final Function<Method, Boolean> function) {
        checkLambda(methodRefrence1, function);
        return null;
    }
    
    public static <T, Z> T check(final MethodRefrence2<T, Z> methodRefrence2, final Function<Method, Boolean> function) {
        checkLambda(methodRefrence2, function);
        return null;
    }
    
    private static void checkLambda(final Object o, final Function<Method, Boolean> function) {
        if (FAUtil.cache.contains(o.toString().split("/")[0])) {
            return;
        }
        final Method internalMethod = getInternalMethod(o);
        if (internalMethod != null && function.apply(internalMethod)) {
            FAUtil.cache.add(o.toString().split("/")[0]);
        }
        FAUtil.cache.add(o.toString().split("/")[0]);
    }
    
    private static Method getInternalMethod(final Object obj) {
        for (Class<?> clazz = obj.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                final Method declaredMethod = clazz.getDeclaredMethod("writeReplace", (Class<?>[])new Class[0]);
                declaredMethod.setAccessible(true);
                final Object invoke = declaredMethod.invoke(obj, new Object[0]);
                if (!(invoke instanceof SerializedLambda)) {
                    break;
                }
                final SerializedLambda serializedLambda = (SerializedLambda)invoke;
                for (final Method method : Class.forName(serializedLambda.getImplClass().replace('/', '.')).getDeclaredMethods()) {
                    if (method.getName().equals(serializedLambda.getImplMethodName())) {
                        return method;
                    }
                }
            }
            catch (IllegalAccessException | InvocationTargetException ex) {
                break;
            }
            catch (Exception ex2) {}
        }
        return null;
    }
    
    static {
        FAUtil.cache = new HashSet<String>();
    }
}
