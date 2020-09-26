

package me.TechsCode.EnderPermissions.dependencies.commons.lang.enums;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EnumUtils
{
    public static Enum getEnum(final Class clazz, final String s) {
        return Enum.getEnum(clazz, s);
    }
    
    public static ValuedEnum getEnum(final Class clazz, final int n) {
        return (ValuedEnum)ValuedEnum.getEnum(clazz, n);
    }
    
    public static Map getEnumMap(final Class clazz) {
        return Enum.getEnumMap(clazz);
    }
    
    public static List getEnumList(final Class clazz) {
        return Enum.getEnumList(clazz);
    }
    
    public static Iterator iterator(final Class clazz) {
        return Enum.getEnumList(clazz).iterator();
    }
}
