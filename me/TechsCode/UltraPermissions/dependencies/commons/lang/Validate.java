

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import java.util.Iterator;
import java.util.Map;
import java.util.Collection;

public class Validate
{
    public static void isTrue(final boolean b, final String str, final Object obj) {
        if (!b) {
            throw new IllegalArgumentException(str + obj);
        }
    }
    
    public static void isTrue(final boolean b, final String str, final long lng) {
        if (!b) {
            throw new IllegalArgumentException(str + lng);
        }
    }
    
    public static void isTrue(final boolean b, final String str, final double d) {
        if (!b) {
            throw new IllegalArgumentException(str + d);
        }
    }
    
    public static void isTrue(final boolean b, final String s) {
        if (!b) {
            throw new IllegalArgumentException(s);
        }
    }
    
    public static void isTrue(final boolean b) {
        if (!b) {
            throw new IllegalArgumentException("The validated expression is false");
        }
    }
    
    public static void notNull(final Object o) {
        notNull(o, "The validated object is null");
    }
    
    public static void notNull(final Object o, final String s) {
        if (o == null) {
            throw new IllegalArgumentException(s);
        }
    }
    
    public static void notEmpty(final Object[] array, final String s) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(s);
        }
    }
    
    public static void notEmpty(final Object[] array) {
        notEmpty(array, "The validated array is empty");
    }
    
    public static void notEmpty(final Collection collection, final String s) {
        if (collection == null || collection.size() == 0) {
            throw new IllegalArgumentException(s);
        }
    }
    
    public static void notEmpty(final Collection collection) {
        notEmpty(collection, "The validated collection is empty");
    }
    
    public static void notEmpty(final Map map, final String s) {
        if (map == null || map.size() == 0) {
            throw new IllegalArgumentException(s);
        }
    }
    
    public static void notEmpty(final Map map) {
        notEmpty(map, "The validated map is empty");
    }
    
    public static void notEmpty(final String s, final String s2) {
        if (s == null || s.length() == 0) {
            throw new IllegalArgumentException(s2);
        }
    }
    
    public static void notEmpty(final String s) {
        notEmpty(s, "The validated string is empty");
    }
    
    public static void noNullElements(final Object[] array, final String s) {
        notNull(array);
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == null) {
                throw new IllegalArgumentException(s);
            }
        }
    }
    
    public static void noNullElements(final Object[] array) {
        notNull(array);
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == null) {
                throw new IllegalArgumentException("The validated array contains null element at index: " + i);
            }
        }
    }
    
    public static void noNullElements(final Collection collection, final String s) {
        notNull(collection);
        final Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == null) {
                throw new IllegalArgumentException(s);
            }
        }
    }
    
    public static void noNullElements(final Collection collection) {
        notNull(collection);
        int i = 0;
        final Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == null) {
                throw new IllegalArgumentException("The validated collection contains null element at index: " + i);
            }
            ++i;
        }
    }
    
    public static void allElementsOfType(final Collection collection, final Class clazz, final String s) {
        notNull(collection);
        notNull(clazz);
        final Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (!clazz.isInstance(iterator.next())) {
                throw new IllegalArgumentException(s);
            }
        }
    }
    
    public static void allElementsOfType(final Collection collection, final Class clazz) {
        notNull(collection);
        notNull(clazz);
        int i = 0;
        final Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (!clazz.isInstance(iterator.next())) {
                throw new IllegalArgumentException("The validated collection contains an element not of type " + clazz.getName() + " at index: " + i);
            }
            ++i;
        }
    }
}
