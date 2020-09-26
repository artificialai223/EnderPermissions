

package me.TechsCode.EnderPermissions.tpl.utils;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class ClassInstanceCreator
{
    static Unsafe unsafe;
    
    public static Object create(final Class<?> cls) {
        try {
            return ClassInstanceCreator.unsafe.allocateInstance(cls);
        }
        catch (InstantiationException ex) {
            return null;
        }
    }
    
    static {
        try {
            final Field declaredField = Unsafe.class.getDeclaredField("theUnsafe");
            declaredField.setAccessible(true);
            ClassInstanceCreator.unsafe = (Unsafe)declaredField.get(null);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
