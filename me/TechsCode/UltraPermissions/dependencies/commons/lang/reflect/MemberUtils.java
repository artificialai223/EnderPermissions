

package me.TechsCode.EnderPermissions.dependencies.commons.lang.reflect;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.ArrayUtils;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.SystemUtils;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.ClassUtils;
import java.lang.reflect.Modifier;
import java.lang.reflect.Member;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

abstract class MemberUtils
{
    private static final int ACCESS_TEST = 7;
    private static final Method IS_SYNTHETIC;
    private static final Class[] ORDERED_PRIMITIVE_TYPES;
    
    static void setAccessibleWorkaround(final AccessibleObject accessibleObject) {
        if (accessibleObject == null || accessibleObject.isAccessible()) {
            return;
        }
        final Member member = (Member)accessibleObject;
        if (Modifier.isPublic(member.getModifiers()) && isPackageAccess(member.getDeclaringClass().getModifiers())) {
            try {
                accessibleObject.setAccessible(true);
            }
            catch (SecurityException ex) {}
        }
    }
    
    static boolean isPackageAccess(final int n) {
        return (n & 0x7) == 0x0;
    }
    
    static boolean isAccessible(final Member member) {
        return member != null && Modifier.isPublic(member.getModifiers()) && !isSynthetic(member);
    }
    
    static boolean isSynthetic(final Member obj) {
        if (MemberUtils.IS_SYNTHETIC != null) {
            try {
                return (boolean)MemberUtils.IS_SYNTHETIC.invoke(obj, (Object[])null);
            }
            catch (Exception ex) {}
        }
        return false;
    }
    
    static int compareParameterTypes(final Class[] array, final Class[] array2, final Class[] array3) {
        final float totalTransformationCost = getTotalTransformationCost(array3, array);
        final float totalTransformationCost2 = getTotalTransformationCost(array3, array2);
        return (totalTransformationCost < totalTransformationCost2) ? -1 : (totalTransformationCost2 < totalTransformationCost);
    }
    
    private static float getTotalTransformationCost(final Class[] array, final Class[] array2) {
        float n = 0.0f;
        for (int i = 0; i < array.length; ++i) {
            n += getObjectTransformationCost(array[i], array2[i]);
        }
        return n;
    }
    
    private static float getObjectTransformationCost(Class superclass, final Class clazz) {
        if (clazz.isPrimitive()) {
            return getPrimitivePromotionCost(superclass, clazz);
        }
        float n = 0.0f;
        while (superclass != null && !clazz.equals(superclass)) {
            if (clazz.isInterface() && ClassUtils.isAssignable(superclass, clazz)) {
                n += 0.25f;
                break;
            }
            ++n;
            superclass = superclass.getSuperclass();
        }
        if (superclass == null) {
            n += 1.5f;
        }
        return n;
    }
    
    private static float getPrimitivePromotionCost(final Class clazz, final Class clazz2) {
        float n = 0.0f;
        Class wrapperToPrimitive = clazz;
        if (!wrapperToPrimitive.isPrimitive()) {
            n += 0.1f;
            wrapperToPrimitive = ClassUtils.wrapperToPrimitive(wrapperToPrimitive);
        }
        for (int n2 = 0; wrapperToPrimitive != clazz2 && n2 < MemberUtils.ORDERED_PRIMITIVE_TYPES.length; ++n2) {
            if (wrapperToPrimitive == MemberUtils.ORDERED_PRIMITIVE_TYPES[n2]) {
                n += 0.1f;
                if (n2 < MemberUtils.ORDERED_PRIMITIVE_TYPES.length - 1) {
                    wrapperToPrimitive = MemberUtils.ORDERED_PRIMITIVE_TYPES[n2 + 1];
                }
            }
        }
        return n;
    }
    
    static {
        Method method = null;
        if (SystemUtils.isJavaVersionAtLeast(1.5f)) {
            try {
                method = Member.class.getMethod("isSynthetic", (Class[])ArrayUtils.EMPTY_CLASS_ARRAY);
            }
            catch (Exception ex) {}
        }
        IS_SYNTHETIC = method;
        ORDERED_PRIMITIVE_TYPES = new Class[] { Byte.TYPE, Short.TYPE, Character.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE };
    }
}
