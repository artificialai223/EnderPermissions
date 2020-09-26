

package me.TechsCode.EnderPermissions.dependencies.commons.lang.enums;

import java.lang.reflect.InvocationTargetException;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.ClassUtils;
import java.util.Iterator;

public abstract class ValuedEnum extends Enum
{
    private static final long serialVersionUID = -7129650521543789085L;
    private final int iValue;
    
    protected ValuedEnum(final String s, final int iValue) {
        super(s);
        this.iValue = iValue;
    }
    
    protected static Enum getEnum(final Class clazz, final int n) {
        if (clazz == null) {
            throw new IllegalArgumentException("The Enum Class must not be null");
        }
        for (final ValuedEnum valuedEnum : Enum.getEnumList(clazz)) {
            if (valuedEnum.getValue() == n) {
                return valuedEnum;
            }
        }
        return null;
    }
    
    public final int getValue() {
        return this.iValue;
    }
    
    public int compareTo(final Object o) {
        if (o == this) {
            return 0;
        }
        if (o.getClass() == this.getClass()) {
            return this.iValue - ((ValuedEnum)o).iValue;
        }
        if (o.getClass().getName().equals(this.getClass().getName())) {
            return this.iValue - this.getValueInOtherClassLoader(o);
        }
        throw new ClassCastException("Different enum class '" + ClassUtils.getShortClassName(o.getClass()) + "'");
    }
    
    private int getValueInOtherClassLoader(final Object obj) {
        try {
            return (int)obj.getClass().getMethod("getValue", (Class<?>[])null).invoke(obj, (Object[])null);
        }
        catch (NoSuchMethodException ex) {}
        catch (IllegalAccessException ex2) {}
        catch (InvocationTargetException ex3) {}
        throw new IllegalStateException("This should not happen");
    }
    
    public String toString() {
        if (this.iToString == null) {
            this.iToString = ClassUtils.getShortClassName(this.getEnumClass()) + "[" + this.getName() + "=" + this.getValue() + "]";
        }
        return this.iToString;
    }
}
