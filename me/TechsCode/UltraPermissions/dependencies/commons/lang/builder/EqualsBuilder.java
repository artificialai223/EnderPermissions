

package me.TechsCode.EnderPermissions.dependencies.commons.lang.builder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.ArrayUtils;
import java.lang.reflect.AccessibleObject;
import java.util.Collection;

public class EqualsBuilder
{
    private boolean isEquals;
    
    public EqualsBuilder() {
        this.isEquals = true;
    }
    
    public static boolean reflectionEquals(final Object o, final Object o2) {
        return reflectionEquals(o, o2, false, null, null);
    }
    
    public static boolean reflectionEquals(final Object o, final Object o2, final Collection collection) {
        return reflectionEquals(o, o2, ReflectionToStringBuilder.toNoNullStringArray(collection));
    }
    
    public static boolean reflectionEquals(final Object o, final Object o2, final String[] array) {
        return reflectionEquals(o, o2, false, null, array);
    }
    
    public static boolean reflectionEquals(final Object o, final Object o2, final boolean b) {
        return reflectionEquals(o, o2, b, null, null);
    }
    
    public static boolean reflectionEquals(final Object o, final Object o2, final boolean b, final Class clazz) {
        return reflectionEquals(o, o2, b, clazz, null);
    }
    
    public static boolean reflectionEquals(final Object o, final Object o2, final boolean b, final Class clazz, final String[] array) {
        if (o == o2) {
            return true;
        }
        if (o == null || o2 == null) {
            return false;
        }
        final Class<?> class1 = o.getClass();
        final Class<?> class2 = o2.getClass();
        Class<?> superclass;
        if (class1.isInstance(o2)) {
            superclass = class1;
            if (!class2.isInstance(o)) {
                superclass = class2;
            }
        }
        else {
            if (!class2.isInstance(o)) {
                return false;
            }
            superclass = class2;
            if (!class1.isInstance(o2)) {
                superclass = class1;
            }
        }
        final EqualsBuilder equalsBuilder = new EqualsBuilder();
        try {
            reflectionAppend(o, o2, superclass, equalsBuilder, b, array);
            while (superclass.getSuperclass() != null && superclass != clazz) {
                superclass = superclass.getSuperclass();
                reflectionAppend(o, o2, superclass, equalsBuilder, b, array);
            }
        }
        catch (IllegalArgumentException ex) {
            return false;
        }
        return equalsBuilder.isEquals();
    }
    
    private static void reflectionAppend(final Object obj, final Object obj2, final Class clazz, final EqualsBuilder equalsBuilder, final boolean b, final String[] array) {
        final Field[] declaredFields = clazz.getDeclaredFields();
        AccessibleObject.setAccessible(declaredFields, true);
        for (int n = 0; n < declaredFields.length && equalsBuilder.isEquals; ++n) {
            final Field field = declaredFields[n];
            if (!ArrayUtils.contains(array, field.getName()) && field.getName().indexOf(36) == -1 && (b || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers())) {
                try {
                    equalsBuilder.append(field.get(obj), field.get(obj2));
                }
                catch (IllegalAccessException ex) {
                    throw new InternalError("Unexpected IllegalAccessException");
                }
            }
        }
    }
    
    public EqualsBuilder appendSuper(final boolean isEquals) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = isEquals;
        return this;
    }
    
    public EqualsBuilder append(final Object o, final Object obj) {
        if (!this.isEquals) {
            return this;
        }
        if (o == obj) {
            return this;
        }
        if (o == null || obj == null) {
            this.setEquals(false);
            return this;
        }
        if (!o.getClass().isArray()) {
            this.isEquals = o.equals(obj);
        }
        else if (o.getClass() != obj.getClass()) {
            this.setEquals(false);
        }
        else if (o instanceof long[]) {
            this.append((long[])o, (long[])obj);
        }
        else if (o instanceof int[]) {
            this.append((int[])o, (int[])obj);
        }
        else if (o instanceof short[]) {
            this.append((short[])o, (short[])obj);
        }
        else if (o instanceof char[]) {
            this.append((char[])o, (char[])obj);
        }
        else if (o instanceof byte[]) {
            this.append((byte[])o, (byte[])obj);
        }
        else if (o instanceof double[]) {
            this.append((double[])o, (double[])obj);
        }
        else if (o instanceof float[]) {
            this.append((float[])o, (float[])obj);
        }
        else if (o instanceof boolean[]) {
            this.append((boolean[])o, (boolean[])obj);
        }
        else {
            this.append((Object[])o, (Object[])obj);
        }
        return this;
    }
    
    public EqualsBuilder append(final long n, final long n2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = (n == n2);
        return this;
    }
    
    public EqualsBuilder append(final int n, final int n2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = (n == n2);
        return this;
    }
    
    public EqualsBuilder append(final short n, final short n2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = (n == n2);
        return this;
    }
    
    public EqualsBuilder append(final char c, final char c2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = (c == c2);
        return this;
    }
    
    public EqualsBuilder append(final byte b, final byte b2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = (b == b2);
        return this;
    }
    
    public EqualsBuilder append(final double value, final double value2) {
        if (!this.isEquals) {
            return this;
        }
        return this.append(Double.doubleToLongBits(value), Double.doubleToLongBits(value2));
    }
    
    public EqualsBuilder append(final float value, final float value2) {
        if (!this.isEquals) {
            return this;
        }
        return this.append(Float.floatToIntBits(value), Float.floatToIntBits(value2));
    }
    
    public EqualsBuilder append(final boolean b, final boolean b2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = (b == b2);
        return this;
    }
    
    public EqualsBuilder append(final Object[] array, final Object[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        for (int n = 0; n < array.length && this.isEquals; ++n) {
            this.append(array[n], array2[n]);
        }
        return this;
    }
    
    public EqualsBuilder append(final long[] array, final long[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        for (int n = 0; n < array.length && this.isEquals; ++n) {
            this.append(array[n], array2[n]);
        }
        return this;
    }
    
    public EqualsBuilder append(final int[] array, final int[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        for (int n = 0; n < array.length && this.isEquals; ++n) {
            this.append(array[n], array2[n]);
        }
        return this;
    }
    
    public EqualsBuilder append(final short[] array, final short[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        for (int n = 0; n < array.length && this.isEquals; ++n) {
            this.append(array[n], array2[n]);
        }
        return this;
    }
    
    public EqualsBuilder append(final char[] array, final char[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        for (int n = 0; n < array.length && this.isEquals; ++n) {
            this.append(array[n], array2[n]);
        }
        return this;
    }
    
    public EqualsBuilder append(final byte[] array, final byte[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        for (int n = 0; n < array.length && this.isEquals; ++n) {
            this.append(array[n], array2[n]);
        }
        return this;
    }
    
    public EqualsBuilder append(final double[] array, final double[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        for (int n = 0; n < array.length && this.isEquals; ++n) {
            this.append(array[n], array2[n]);
        }
        return this;
    }
    
    public EqualsBuilder append(final float[] array, final float[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        for (int n = 0; n < array.length && this.isEquals; ++n) {
            this.append(array[n], array2[n]);
        }
        return this;
    }
    
    public EqualsBuilder append(final boolean[] array, final boolean[] array2) {
        if (!this.isEquals) {
            return this;
        }
        if (array == array2) {
            return this;
        }
        if (array == null || array2 == null) {
            this.setEquals(false);
            return this;
        }
        if (array.length != array2.length) {
            this.setEquals(false);
            return this;
        }
        for (int n = 0; n < array.length && this.isEquals; ++n) {
            this.append(array[n], array2[n]);
        }
        return this;
    }
    
    public boolean isEquals() {
        return this.isEquals;
    }
    
    protected void setEquals(final boolean isEquals) {
        this.isEquals = isEquals;
    }
    
    public void reset() {
        this.isEquals = true;
    }
}
