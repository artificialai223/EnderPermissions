

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import java.util.HashMap;
import java.util.Collection;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.text.StrBuilder;
import java.util.Map;

public class ClassUtils
{
    public static final char PACKAGE_SEPARATOR_CHAR = '.';
    public static final String PACKAGE_SEPARATOR;
    public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
    public static final String INNER_CLASS_SEPARATOR;
    private static final Map primitiveWrapperMap;
    private static final Map wrapperPrimitiveMap;
    private static final Map abbreviationMap;
    private static final Map reverseAbbreviationMap;
    
    private static void addAbbreviation(final String s, final String s2) {
        ClassUtils.abbreviationMap.put(s, s2);
        ClassUtils.reverseAbbreviationMap.put(s2, s);
    }
    
    public static String getShortClassName(final Object o, final String s) {
        if (o == null) {
            return s;
        }
        return getShortClassName(o.getClass());
    }
    
    public static String getShortClassName(final Class clazz) {
        if (clazz == null) {
            return "";
        }
        return getShortClassName(clazz.getName());
    }
    
    public static String getShortClassName(String s) {
        if (s == null) {
            return "";
        }
        if (s.length() == 0) {
            return "";
        }
        final StrBuilder obj = new StrBuilder();
        if (s.startsWith("[")) {
            while (s.charAt(0) == '[') {
                s = s.substring(1);
                obj.append("[]");
            }
            if (s.charAt(0) == 'L' && s.charAt(s.length() - 1) == ';') {
                s = s.substring(1, s.length() - 1);
            }
        }
        if (ClassUtils.reverseAbbreviationMap.containsKey(s)) {
            s = ClassUtils.reverseAbbreviationMap.get(s);
        }
        final int lastIndex = s.lastIndexOf(46);
        final int index = s.indexOf(36, (lastIndex == -1) ? 0 : (lastIndex + 1));
        String str = s.substring(lastIndex + 1);
        if (index != -1) {
            str = str.replace('$', '.');
        }
        return str + obj;
    }
    
    public static String getPackageName(final Object o, final String s) {
        if (o == null) {
            return s;
        }
        return getPackageName(o.getClass());
    }
    
    public static String getPackageName(final Class clazz) {
        if (clazz == null) {
            return "";
        }
        return getPackageName(clazz.getName());
    }
    
    public static String getPackageName(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        while (s.charAt(0) == '[') {
            s = s.substring(1);
        }
        if (s.charAt(0) == 'L' && s.charAt(s.length() - 1) == ';') {
            s = s.substring(1);
        }
        final int lastIndex = s.lastIndexOf(46);
        if (lastIndex == -1) {
            return "";
        }
        return s.substring(0, lastIndex);
    }
    
    public static List getAllSuperclasses(final Class clazz) {
        if (clazz == null) {
            return null;
        }
        final ArrayList<Class> list = new ArrayList<Class>();
        for (Class clazz2 = clazz.getSuperclass(); clazz2 != null; clazz2 = clazz2.getSuperclass()) {
            list.add(clazz2);
        }
        return list;
    }
    
    public static List getAllInterfaces(final Class clazz) {
        if (clazz == null) {
            return null;
        }
        final ArrayList list = new ArrayList();
        getAllInterfaces(clazz, list);
        return list;
    }
    
    private static void getAllInterfaces(Class superclass, final List list) {
        while (superclass != null) {
            final Class[] interfaces = superclass.getInterfaces();
            for (int i = 0; i < interfaces.length; ++i) {
                if (!list.contains(interfaces[i])) {
                    list.add(interfaces[i]);
                    getAllInterfaces(interfaces[i], list);
                }
            }
            superclass = superclass.getSuperclass();
        }
    }
    
    public static List convertClassNamesToClasses(final List list) {
        if (list == null) {
            return null;
        }
        final ArrayList<Class<?>> list2 = new ArrayList<Class<?>>(list.size());
        for (final String className : list) {
            try {
                list2.add(Class.forName(className));
            }
            catch (Exception ex) {
                list2.add(null);
            }
        }
        return list2;
    }
    
    public static List convertClassesToClassNames(final List list) {
        if (list == null) {
            return null;
        }
        final ArrayList<String> list2 = new ArrayList<String>(list.size());
        for (final Class clazz : list) {
            if (clazz == null) {
                list2.add(null);
            }
            else {
                list2.add(clazz.getName());
            }
        }
        return list2;
    }
    
    public static boolean isAssignable(final Class[] array, final Class[] array2) {
        return isAssignable(array, array2, false);
    }
    
    public static boolean isAssignable(Class[] empty_CLASS_ARRAY, Class[] empty_CLASS_ARRAY2, final boolean b) {
        if (!ArrayUtils.isSameLength(empty_CLASS_ARRAY, empty_CLASS_ARRAY2)) {
            return false;
        }
        if (empty_CLASS_ARRAY == null) {
            empty_CLASS_ARRAY = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        if (empty_CLASS_ARRAY2 == null) {
            empty_CLASS_ARRAY2 = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        for (int i = 0; i < empty_CLASS_ARRAY.length; ++i) {
            if (!isAssignable(empty_CLASS_ARRAY[i], empty_CLASS_ARRAY2[i], b)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isAssignable(final Class clazz, final Class clazz2) {
        return isAssignable(clazz, clazz2, false);
    }
    
    public static boolean isAssignable(Class clazz, final Class clazz2, final boolean b) {
        if (clazz2 == null) {
            return false;
        }
        if (clazz == null) {
            return !clazz2.isPrimitive();
        }
        if (b) {
            if (clazz.isPrimitive() && !clazz2.isPrimitive()) {
                clazz = primitiveToWrapper(clazz);
                if (clazz == null) {
                    return false;
                }
            }
            if (clazz2.isPrimitive() && !clazz.isPrimitive()) {
                clazz = wrapperToPrimitive(clazz);
                if (clazz == null) {
                    return false;
                }
            }
        }
        if (clazz.equals(clazz2)) {
            return true;
        }
        if (!clazz.isPrimitive()) {
            return clazz2.isAssignableFrom(clazz);
        }
        if (!clazz2.isPrimitive()) {
            return false;
        }
        if (Integer.TYPE.equals(clazz)) {
            return Long.TYPE.equals(clazz2) || Float.TYPE.equals(clazz2) || Double.TYPE.equals(clazz2);
        }
        if (Long.TYPE.equals(clazz)) {
            return Float.TYPE.equals(clazz2) || Double.TYPE.equals(clazz2);
        }
        if (Boolean.TYPE.equals(clazz)) {
            return false;
        }
        if (Double.TYPE.equals(clazz)) {
            return false;
        }
        if (Float.TYPE.equals(clazz)) {
            return Double.TYPE.equals(clazz2);
        }
        if (Character.TYPE.equals(clazz)) {
            return Integer.TYPE.equals(clazz2) || Long.TYPE.equals(clazz2) || Float.TYPE.equals(clazz2) || Double.TYPE.equals(clazz2);
        }
        if (Short.TYPE.equals(clazz)) {
            return Integer.TYPE.equals(clazz2) || Long.TYPE.equals(clazz2) || Float.TYPE.equals(clazz2) || Double.TYPE.equals(clazz2);
        }
        return Byte.TYPE.equals(clazz) && (Short.TYPE.equals(clazz2) || Integer.TYPE.equals(clazz2) || Long.TYPE.equals(clazz2) || Float.TYPE.equals(clazz2) || Double.TYPE.equals(clazz2));
    }
    
    public static Class primitiveToWrapper(final Class clazz) {
        Class clazz2 = clazz;
        if (clazz != null && clazz.isPrimitive()) {
            clazz2 = ClassUtils.primitiveWrapperMap.get(clazz);
        }
        return clazz2;
    }
    
    public static Class[] primitivesToWrappers(final Class[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return array;
        }
        final Class[] array2 = new Class[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = primitiveToWrapper(array[i]);
        }
        return array2;
    }
    
    public static Class wrapperToPrimitive(final Class clazz) {
        return ClassUtils.wrapperPrimitiveMap.get(clazz);
    }
    
    public static Class[] wrappersToPrimitives(final Class[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return array;
        }
        final Class[] array2 = new Class[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = wrapperToPrimitive(array[i]);
        }
        return array2;
    }
    
    public static boolean isInnerClass(final Class clazz) {
        return clazz != null && clazz.getName().indexOf(36) >= 0;
    }
    
    public static Class getClass(final ClassLoader classLoader, final String s, final boolean b) {
        try {
            Class<?> clazz;
            if (ClassUtils.abbreviationMap.containsKey(s)) {
                clazz = Class.forName("[" + ClassUtils.abbreviationMap.get(s), b, classLoader).getComponentType();
            }
            else {
                clazz = Class.forName(toCanonicalName(s), b, classLoader);
            }
            return clazz;
        }
        catch (ClassNotFoundException ex) {
            final int lastIndex = s.lastIndexOf(46);
            if (lastIndex != -1) {
                try {
                    return getClass(classLoader, s.substring(0, lastIndex) + '$' + s.substring(lastIndex + 1), b);
                }
                catch (ClassNotFoundException ex2) {}
            }
            throw ex;
        }
    }
    
    public static Class getClass(final ClassLoader classLoader, final String s) {
        return getClass(classLoader, s, true);
    }
    
    public static Class getClass(final String s) {
        return getClass(s, true);
    }
    
    public static Class getClass(final String s, final boolean b) {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        return getClass((contextClassLoader == null) ? ClassUtils.class.getClassLoader() : contextClassLoader, s, b);
    }
    
    public static Method getPublicMethod(final Class clazz, final String str, final Class[] array) {
        final Method method = clazz.getMethod(str, (Class[])array);
        if (Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
            return method;
        }
        final ArrayList<Class> list = new ArrayList<Class>();
        list.addAll((Collection<?>)getAllInterfaces(clazz));
        list.addAll((Collection<?>)getAllSuperclasses(clazz));
        for (final Class clazz2 : list) {
            if (!Modifier.isPublic(clazz2.getModifiers())) {
                continue;
            }
            Method method2;
            try {
                method2 = clazz2.getMethod(str, (Class[])array);
            }
            catch (NoSuchMethodException ex) {
                continue;
            }
            if (Modifier.isPublic(method2.getDeclaringClass().getModifiers())) {
                return method2;
            }
        }
        throw new NoSuchMethodException("Can't find a public method for " + str + " " + ArrayUtils.toString(array));
    }
    
    private static String toCanonicalName(String s) {
        s = StringUtils.deleteWhitespace(s);
        if (s == null) {
            throw new NullArgumentException("className");
        }
        if (s.endsWith("[]")) {
            final StrBuilder strBuilder = new StrBuilder();
            while (s.endsWith("[]")) {
                s = s.substring(0, s.length() - 2);
                strBuilder.append("[");
            }
            final String s2 = ClassUtils.abbreviationMap.get(s);
            if (s2 != null) {
                strBuilder.append(s2);
            }
            else {
                strBuilder.append("L").append(s).append(";");
            }
            s = strBuilder.toString();
        }
        return s;
    }
    
    public static Class[] toClass(final Object[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        final Class[] array2 = new Class[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = ((array[i] == null) ? null : array[i].getClass());
        }
        return array2;
    }
    
    public static String getShortCanonicalName(final Object o, final String s) {
        if (o == null) {
            return s;
        }
        return getShortCanonicalName(o.getClass().getName());
    }
    
    public static String getShortCanonicalName(final Class clazz) {
        if (clazz == null) {
            return "";
        }
        return getShortCanonicalName(clazz.getName());
    }
    
    public static String getShortCanonicalName(final String s) {
        return getShortClassName(getCanonicalName(s));
    }
    
    public static String getPackageCanonicalName(final Object o, final String s) {
        if (o == null) {
            return s;
        }
        return getPackageCanonicalName(o.getClass().getName());
    }
    
    public static String getPackageCanonicalName(final Class clazz) {
        if (clazz == null) {
            return "";
        }
        return getPackageCanonicalName(clazz.getName());
    }
    
    public static String getPackageCanonicalName(final String s) {
        return getPackageName(getCanonicalName(s));
    }
    
    private static String getCanonicalName(String s) {
        s = StringUtils.deleteWhitespace(s);
        if (s == null) {
            return null;
        }
        int n = 0;
        while (s.startsWith("[")) {
            ++n;
            s = s.substring(1);
        }
        if (n < 1) {
            return s;
        }
        if (s.startsWith("L")) {
            s = s.substring(1, s.endsWith(";") ? (s.length() - 1) : s.length());
        }
        else if (s.length() > 0) {
            s = ClassUtils.reverseAbbreviationMap.get(s.substring(0, 1));
        }
        final StrBuilder strBuilder = new StrBuilder(s);
        for (int i = 0; i < n; ++i) {
            strBuilder.append("[]");
        }
        return strBuilder.toString();
    }
    
    static {
        PACKAGE_SEPARATOR = String.valueOf('.');
        INNER_CLASS_SEPARATOR = String.valueOf('$');
        (primitiveWrapperMap = new HashMap()).put(Boolean.TYPE, Boolean.class);
        ClassUtils.primitiveWrapperMap.put(Byte.TYPE, Byte.class);
        ClassUtils.primitiveWrapperMap.put(Character.TYPE, Character.class);
        ClassUtils.primitiveWrapperMap.put(Short.TYPE, Short.class);
        ClassUtils.primitiveWrapperMap.put(Integer.TYPE, Integer.class);
        ClassUtils.primitiveWrapperMap.put(Long.TYPE, Long.class);
        ClassUtils.primitiveWrapperMap.put(Double.TYPE, Double.class);
        ClassUtils.primitiveWrapperMap.put(Float.TYPE, Float.class);
        ClassUtils.primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
        wrapperPrimitiveMap = new HashMap();
        for (final Class clazz : ClassUtils.primitiveWrapperMap.keySet()) {
            final Class obj = ClassUtils.primitiveWrapperMap.get(clazz);
            if (!clazz.equals(obj)) {
                ClassUtils.wrapperPrimitiveMap.put(obj, clazz);
            }
        }
        abbreviationMap = new HashMap();
        reverseAbbreviationMap = new HashMap();
        addAbbreviation("int", "I");
        addAbbreviation("boolean", "Z");
        addAbbreviation("float", "F");
        addAbbreviation("long", "J");
        addAbbreviation("short", "S");
        addAbbreviation("byte", "B");
        addAbbreviation("double", "D");
        addAbbreviation("char", "C");
    }
}
