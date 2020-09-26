

package me.TechsCode.EnderPermissions.dependencies.commons.lang.builder;

import java.util.HashSet;
import java.util.Collection;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.ArrayUtils;
import java.lang.reflect.AccessibleObject;
import java.util.Set;

public class HashCodeBuilder
{
    private static final ThreadLocal REGISTRY;
    private final int iConstant;
    private int iTotal;
    static /* synthetic */ Class class$org$apache$commons$lang$builder$HashCodeBuilder;
    
    static Set getRegistry() {
        return HashCodeBuilder.REGISTRY.get();
    }
    
    static boolean isRegistered(final Object o) {
        final Set registry = getRegistry();
        return registry != null && registry.contains(new IDKey(o));
    }
    
    private static void reflectionAppend(final Object obj, final Class clazz, final HashCodeBuilder hashCodeBuilder, final boolean b, final String[] array) {
        if (isRegistered(obj)) {
            return;
        }
        try {
            register(obj);
            final Field[] declaredFields = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(declaredFields, true);
            for (int i = 0; i < declaredFields.length; ++i) {
                final Field field = declaredFields[i];
                if (!ArrayUtils.contains(array, field.getName()) && field.getName().indexOf(36) == -1 && (b || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers())) {
                    try {
                        hashCodeBuilder.append(field.get(obj));
                    }
                    catch (IllegalAccessException ex) {
                        throw new InternalError("Unexpected IllegalAccessException");
                    }
                }
            }
        }
        finally {
            unregister(obj);
        }
    }
    
    public static int reflectionHashCode(final int n, final int n2, final Object o) {
        return reflectionHashCode(n, n2, o, false, null, null);
    }
    
    public static int reflectionHashCode(final int n, final int n2, final Object o, final boolean b) {
        return reflectionHashCode(n, n2, o, b, null, null);
    }
    
    public static int reflectionHashCode(final int n, final int n2, final Object o, final boolean b, final Class clazz) {
        return reflectionHashCode(n, n2, o, b, clazz, null);
    }
    
    public static int reflectionHashCode(final int n, final int n2, final Object o, final boolean b, final Class clazz, final String[] array) {
        if (o == null) {
            throw new IllegalArgumentException("The object to build a hash code for must not be null");
        }
        final HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(n, n2);
        Class<?> clazz2 = o.getClass();
        reflectionAppend(o, clazz2, hashCodeBuilder, b, array);
        while (clazz2.getSuperclass() != null && clazz2 != clazz) {
            clazz2 = clazz2.getSuperclass();
            reflectionAppend(o, clazz2, hashCodeBuilder, b, array);
        }
        return hashCodeBuilder.toHashCode();
    }
    
    public static int reflectionHashCode(final Object o) {
        return reflectionHashCode(17, 37, o, false, null, null);
    }
    
    public static int reflectionHashCode(final Object o, final boolean b) {
        return reflectionHashCode(17, 37, o, b, null, null);
    }
    
    public static int reflectionHashCode(final Object o, final Collection collection) {
        return reflectionHashCode(o, ReflectionToStringBuilder.toNoNullStringArray(collection));
    }
    
    public static int reflectionHashCode(final Object o, final String[] array) {
        return reflectionHashCode(17, 37, o, false, null, array);
    }
    
    static void register(final Object o) {
        Class class$;
        Class class$org$apache$commons$lang$builder$HashCodeBuilder;
        if (HashCodeBuilder.class$org$apache$commons$lang$builder$HashCodeBuilder == null) {
            class$org$apache$commons$lang$builder$HashCodeBuilder = (HashCodeBuilder.class$org$apache$commons$lang$builder$HashCodeBuilder = (class$ = class$("me.TechsCode.EnderPermissions.dependencies.commons.lang.builder.HashCodeBuilder")));
        }
        else {
            class$ = (class$org$apache$commons$lang$builder$HashCodeBuilder = HashCodeBuilder.class$org$apache$commons$lang$builder$HashCodeBuilder);
        }
        final Class clazz = class$org$apache$commons$lang$builder$HashCodeBuilder;
        synchronized (class$) {
            if (getRegistry() == null) {
                HashCodeBuilder.REGISTRY.set(new HashSet());
            }
        }
        getRegistry().add(new IDKey(o));
    }
    
    static void unregister(final Object o) {
        final Set registry = getRegistry();
        if (registry != null) {
            registry.remove(new IDKey(o));
            Class class$;
            Class class$org$apache$commons$lang$builder$HashCodeBuilder;
            if (HashCodeBuilder.class$org$apache$commons$lang$builder$HashCodeBuilder == null) {
                class$org$apache$commons$lang$builder$HashCodeBuilder = (HashCodeBuilder.class$org$apache$commons$lang$builder$HashCodeBuilder = (class$ = class$("me.TechsCode.EnderPermissions.dependencies.commons.lang.builder.HashCodeBuilder")));
            }
            else {
                class$ = (class$org$apache$commons$lang$builder$HashCodeBuilder = HashCodeBuilder.class$org$apache$commons$lang$builder$HashCodeBuilder);
            }
            final Class clazz = class$org$apache$commons$lang$builder$HashCodeBuilder;
            synchronized (class$) {
                final Set registry2 = getRegistry();
                if (registry2 != null && registry2.isEmpty()) {
                    HashCodeBuilder.REGISTRY.set(null);
                }
            }
        }
    }
    
    public HashCodeBuilder() {
        this.iTotal = 0;
        this.iConstant = 37;
        this.iTotal = 17;
    }
    
    public HashCodeBuilder(final int iTotal, final int iConstant) {
        this.iTotal = 0;
        if (iTotal == 0) {
            throw new IllegalArgumentException("HashCodeBuilder requires a non zero initial value");
        }
        if (iTotal % 2 == 0) {
            throw new IllegalArgumentException("HashCodeBuilder requires an odd initial value");
        }
        if (iConstant == 0) {
            throw new IllegalArgumentException("HashCodeBuilder requires a non zero multiplier");
        }
        if (iConstant % 2 == 0) {
            throw new IllegalArgumentException("HashCodeBuilder requires an odd multiplier");
        }
        this.iConstant = iConstant;
        this.iTotal = iTotal;
    }
    
    public HashCodeBuilder append(final boolean b) {
        this.iTotal = this.iTotal * this.iConstant + (b ? 0 : 1);
        return this;
    }
    
    public HashCodeBuilder append(final boolean[] array) {
        if (array == null) {
            this.iTotal *= this.iConstant;
        }
        else {
            for (int i = 0; i < array.length; ++i) {
                this.append(array[i]);
            }
        }
        return this;
    }
    
    public HashCodeBuilder append(final byte b) {
        this.iTotal = this.iTotal * this.iConstant + b;
        return this;
    }
    
    public HashCodeBuilder append(final byte[] array) {
        if (array == null) {
            this.iTotal *= this.iConstant;
        }
        else {
            for (int i = 0; i < array.length; ++i) {
                this.append(array[i]);
            }
        }
        return this;
    }
    
    public HashCodeBuilder append(final char c) {
        this.iTotal = this.iTotal * this.iConstant + c;
        return this;
    }
    
    public HashCodeBuilder append(final char[] array) {
        if (array == null) {
            this.iTotal *= this.iConstant;
        }
        else {
            for (int i = 0; i < array.length; ++i) {
                this.append(array[i]);
            }
        }
        return this;
    }
    
    public HashCodeBuilder append(final double value) {
        return this.append(Double.doubleToLongBits(value));
    }
    
    public HashCodeBuilder append(final double[] array) {
        if (array == null) {
            this.iTotal *= this.iConstant;
        }
        else {
            for (int i = 0; i < array.length; ++i) {
                this.append(array[i]);
            }
        }
        return this;
    }
    
    public HashCodeBuilder append(final float value) {
        this.iTotal = this.iTotal * this.iConstant + Float.floatToIntBits(value);
        return this;
    }
    
    public HashCodeBuilder append(final float[] array) {
        if (array == null) {
            this.iTotal *= this.iConstant;
        }
        else {
            for (int i = 0; i < array.length; ++i) {
                this.append(array[i]);
            }
        }
        return this;
    }
    
    public HashCodeBuilder append(final int n) {
        this.iTotal = this.iTotal * this.iConstant + n;
        return this;
    }
    
    public HashCodeBuilder append(final int[] array) {
        if (array == null) {
            this.iTotal *= this.iConstant;
        }
        else {
            for (int i = 0; i < array.length; ++i) {
                this.append(array[i]);
            }
        }
        return this;
    }
    
    public HashCodeBuilder append(final long n) {
        this.iTotal = this.iTotal * this.iConstant + (int)(n ^ n >> 32);
        return this;
    }
    
    public HashCodeBuilder append(final long[] array) {
        if (array == null) {
            this.iTotal *= this.iConstant;
        }
        else {
            for (int i = 0; i < array.length; ++i) {
                this.append(array[i]);
            }
        }
        return this;
    }
    
    public HashCodeBuilder append(final Object o) {
        if (o == null) {
            this.iTotal *= this.iConstant;
        }
        else if (o.getClass().isArray()) {
            if (o instanceof long[]) {
                this.append((long[])o);
            }
            else if (o instanceof int[]) {
                this.append((int[])o);
            }
            else if (o instanceof short[]) {
                this.append((short[])o);
            }
            else if (o instanceof char[]) {
                this.append((char[])o);
            }
            else if (o instanceof byte[]) {
                this.append((byte[])o);
            }
            else if (o instanceof double[]) {
                this.append((double[])o);
            }
            else if (o instanceof float[]) {
                this.append((float[])o);
            }
            else if (o instanceof boolean[]) {
                this.append((boolean[])o);
            }
            else {
                this.append((Object[])o);
            }
        }
        else {
            this.iTotal = this.iTotal * this.iConstant + o.hashCode();
        }
        return this;
    }
    
    public HashCodeBuilder append(final Object[] array) {
        if (array == null) {
            this.iTotal *= this.iConstant;
        }
        else {
            for (int i = 0; i < array.length; ++i) {
                this.append(array[i]);
            }
        }
        return this;
    }
    
    public HashCodeBuilder append(final short n) {
        this.iTotal = this.iTotal * this.iConstant + n;
        return this;
    }
    
    public HashCodeBuilder append(final short[] array) {
        if (array == null) {
            this.iTotal *= this.iConstant;
        }
        else {
            for (int i = 0; i < array.length; ++i) {
                this.append(array[i]);
            }
        }
        return this;
    }
    
    public HashCodeBuilder appendSuper(final int n) {
        this.iTotal = this.iTotal * this.iConstant + n;
        return this;
    }
    
    public int toHashCode() {
        return this.iTotal;
    }
    
    public int hashCode() {
        return this.toHashCode();
    }
    
    static /* synthetic */ Class class$(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    static {
        REGISTRY = new ThreadLocal();
    }
}
