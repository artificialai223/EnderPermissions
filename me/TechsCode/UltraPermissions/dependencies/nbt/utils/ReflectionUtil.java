

package me.TechsCode.EnderPermissions.dependencies.nbt.utils;

import java.lang.reflect.Method;
import me.TechsCode.EnderPermissions.dependencies.nbt.NbtApiException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;

public final class ReflectionUtil
{
    private static Field field_modifiers;
    
    public static Field makeNonFinal(final Field obj) {
        final int modifiers = obj.getModifiers();
        if (Modifier.isFinal(modifiers)) {
            ReflectionUtil.field_modifiers.set(obj, modifiers & 0xFFFFFFEF);
        }
        return obj;
    }
    
    public static void setFinal(final Object obj, Field nonFinal, final Object value) {
        nonFinal.setAccessible(true);
        nonFinal = makeNonFinal(nonFinal);
        nonFinal.set(obj, value);
    }
    
    static {
        try {
            (ReflectionUtil.field_modifiers = Field.class.getDeclaredField("modifiers")).setAccessible(true);
        }
        catch (NoSuchFieldException ex2) {
            try {
                final Method declaredMethod = Class.class.getDeclaredMethod("getDeclaredFields0", Boolean.TYPE);
                declaredMethod.setAccessible(true);
                for (final Field field_modifiers : (Field[])declaredMethod.invoke(Field.class, false)) {
                    if (field_modifiers.getName().equals("modifiers")) {
                        (ReflectionUtil.field_modifiers = field_modifiers).setAccessible(true);
                        break;
                    }
                }
            }
            catch (Exception ex) {
                throw new NbtApiException(ex);
            }
        }
        if (ReflectionUtil.field_modifiers == null) {
            throw new NbtApiException("Unable to init the modifiers Field.");
        }
    }
}
