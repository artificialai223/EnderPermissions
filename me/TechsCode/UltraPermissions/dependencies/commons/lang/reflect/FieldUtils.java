

package me.TechsCode.EnderPermissions.dependencies.commons.lang.reflect;

import java.lang.reflect.Member;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.ClassUtils;
import java.lang.reflect.Modifier;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

public class FieldUtils
{
    public static Field getField(final Class clazz, final String s) {
        final Field field = getField(clazz, s, false);
        MemberUtils.setAccessibleWorkaround(field);
        return field;
    }
    
    public static Field getField(final Class obj, final String str, final boolean b) {
        if (obj == null) {
            throw new IllegalArgumentException("The class must not be null");
        }
        if (str == null) {
            throw new IllegalArgumentException("The field name must not be null");
        }
        for (Class superclass = obj; superclass != null; superclass = superclass.getSuperclass()) {
            try {
                final Field declaredField = superclass.getDeclaredField(str);
                if (!Modifier.isPublic(declaredField.getModifiers())) {
                    if (!b) {
                        continue;
                    }
                    declaredField.setAccessible(true);
                }
                return declaredField;
            }
            catch (NoSuchFieldException ex) {}
        }
        Field field = null;
        final Iterator<Class> iterator = (Iterator<Class>)ClassUtils.getAllInterfaces(obj).iterator();
        while (iterator.hasNext()) {
            try {
                final Field field2 = iterator.next().getField(str);
                if (field != null) {
                    throw new IllegalArgumentException("Reference to field " + str + " is ambiguous relative to " + obj + "; a matching field exists on two or more implemented interfaces.");
                }
                field = field2;
            }
            catch (NoSuchFieldException ex2) {}
        }
        return field;
    }
    
    public static Field getDeclaredField(final Class clazz, final String s) {
        return getDeclaredField(clazz, s, false);
    }
    
    public static Field getDeclaredField(final Class clazz, final String name, final boolean b) {
        if (clazz == null) {
            throw new IllegalArgumentException("The class must not be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("The field name must not be null");
        }
        try {
            final Field declaredField = clazz.getDeclaredField(name);
            if (!MemberUtils.isAccessible(declaredField)) {
                if (!b) {
                    return null;
                }
                declaredField.setAccessible(true);
            }
            return declaredField;
        }
        catch (NoSuchFieldException ex) {
            return null;
        }
    }
    
    public static Object readStaticField(final Field field) {
        return readStaticField(field, false);
    }
    
    public static Object readStaticField(final Field field, final boolean b) {
        if (field == null) {
            throw new IllegalArgumentException("The field must not be null");
        }
        if (!Modifier.isStatic(field.getModifiers())) {
            throw new IllegalArgumentException("The field '" + field.getName() + "' is not static");
        }
        return readField(field, (Object)null, b);
    }
    
    public static Object readStaticField(final Class clazz, final String s) {
        return readStaticField(clazz, s, false);
    }
    
    public static Object readStaticField(final Class obj, final String str, final boolean b) {
        final Field field = getField(obj, str, b);
        if (field == null) {
            throw new IllegalArgumentException("Cannot locate field " + str + " on " + obj);
        }
        return readStaticField(field, false);
    }
    
    public static Object readDeclaredStaticField(final Class clazz, final String s) {
        return readDeclaredStaticField(clazz, s, false);
    }
    
    public static Object readDeclaredStaticField(final Class clazz, final String str, final boolean b) {
        final Field declaredField = getDeclaredField(clazz, str, b);
        if (declaredField == null) {
            throw new IllegalArgumentException("Cannot locate declared field " + clazz.getName() + "." + str);
        }
        return readStaticField(declaredField, false);
    }
    
    public static Object readField(final Field field, final Object o) {
        return readField(field, o, false);
    }
    
    public static Object readField(final Field accessibleWorkaround, final Object obj, final boolean b) {
        if (accessibleWorkaround == null) {
            throw new IllegalArgumentException("The field must not be null");
        }
        if (b && !accessibleWorkaround.isAccessible()) {
            accessibleWorkaround.setAccessible(true);
        }
        else {
            MemberUtils.setAccessibleWorkaround(accessibleWorkaround);
        }
        return accessibleWorkaround.get(obj);
    }
    
    public static Object readField(final Object o, final String s) {
        return readField(o, s, false);
    }
    
    public static Object readField(final Object o, final String str, final boolean b) {
        if (o == null) {
            throw new IllegalArgumentException("target object must not be null");
        }
        final Class<?> class1 = o.getClass();
        final Field field = getField(class1, str, b);
        if (field == null) {
            throw new IllegalArgumentException("Cannot locate field " + str + " on " + class1);
        }
        return readField(field, o);
    }
    
    public static Object readDeclaredField(final Object o, final String s) {
        return readDeclaredField(o, s, false);
    }
    
    public static Object readDeclaredField(final Object o, final String str, final boolean b) {
        if (o == null) {
            throw new IllegalArgumentException("target object must not be null");
        }
        final Class<?> class1 = o.getClass();
        final Field declaredField = getDeclaredField(class1, str, b);
        if (declaredField == null) {
            throw new IllegalArgumentException("Cannot locate declared field " + class1.getName() + "." + str);
        }
        return readField(declaredField, o);
    }
    
    public static void writeStaticField(final Field field, final Object o) {
        writeStaticField(field, o, false);
    }
    
    public static void writeStaticField(final Field field, final Object o, final boolean b) {
        if (field == null) {
            throw new IllegalArgumentException("The field must not be null");
        }
        if (!Modifier.isStatic(field.getModifiers())) {
            throw new IllegalArgumentException("The field '" + field.getName() + "' is not static");
        }
        writeField(field, (Object)null, o, b);
    }
    
    public static void writeStaticField(final Class clazz, final String s, final Object o) {
        writeStaticField(clazz, s, o, false);
    }
    
    public static void writeStaticField(final Class obj, final String str, final Object o, final boolean b) {
        final Field field = getField(obj, str, b);
        if (field == null) {
            throw new IllegalArgumentException("Cannot locate field " + str + " on " + obj);
        }
        writeStaticField(field, o);
    }
    
    public static void writeDeclaredStaticField(final Class clazz, final String s, final Object o) {
        writeDeclaredStaticField(clazz, s, o, false);
    }
    
    public static void writeDeclaredStaticField(final Class clazz, final String str, final Object o, final boolean b) {
        final Field declaredField = getDeclaredField(clazz, str, b);
        if (declaredField == null) {
            throw new IllegalArgumentException("Cannot locate declared field " + clazz.getName() + "." + str);
        }
        writeField(declaredField, (Object)null, o);
    }
    
    public static void writeField(final Field field, final Object o, final Object o2) {
        writeField(field, o, o2, false);
    }
    
    public static void writeField(final Field accessibleWorkaround, final Object obj, final Object value, final boolean b) {
        if (accessibleWorkaround == null) {
            throw new IllegalArgumentException("The field must not be null");
        }
        if (b && !accessibleWorkaround.isAccessible()) {
            accessibleWorkaround.setAccessible(true);
        }
        else {
            MemberUtils.setAccessibleWorkaround(accessibleWorkaround);
        }
        accessibleWorkaround.set(obj, value);
    }
    
    public static void writeField(final Object o, final String s, final Object o2) {
        writeField(o, s, o2, false);
    }
    
    public static void writeField(final Object o, final String str, final Object o2, final boolean b) {
        if (o == null) {
            throw new IllegalArgumentException("target object must not be null");
        }
        final Class<?> class1 = o.getClass();
        final Field field = getField(class1, str, b);
        if (field == null) {
            throw new IllegalArgumentException("Cannot locate declared field " + class1.getName() + "." + str);
        }
        writeField(field, o, o2);
    }
    
    public static void writeDeclaredField(final Object o, final String s, final Object o2) {
        writeDeclaredField(o, s, o2, false);
    }
    
    public static void writeDeclaredField(final Object o, final String str, final Object o2, final boolean b) {
        if (o == null) {
            throw new IllegalArgumentException("target object must not be null");
        }
        final Class<?> class1 = o.getClass();
        final Field declaredField = getDeclaredField(class1, str, b);
        if (declaredField == null) {
            throw new IllegalArgumentException("Cannot locate declared field " + class1.getName() + "." + str);
        }
        writeField(declaredField, o, o2);
    }
}
