

package me.TechsCode.EnderPermissions.dependencies.commons.lang.math;

public class IEEE754rUtils
{
    public static double min(final double[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        double min = array[0];
        for (int i = 1; i < array.length; ++i) {
            min = min(array[i], min);
        }
        return min;
    }
    
    public static float min(final float[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        float min = array[0];
        for (int i = 1; i < array.length; ++i) {
            min = min(array[i], min);
        }
        return min;
    }
    
    public static double min(final double n, final double n2, final double n3) {
        return min(min(n, n2), n3);
    }
    
    public static double min(final double n, final double n2) {
        if (Double.isNaN(n)) {
            return n2;
        }
        if (Double.isNaN(n2)) {
            return n;
        }
        return Math.min(n, n2);
    }
    
    public static float min(final float n, final float n2, final float n3) {
        return min(min(n, n2), n3);
    }
    
    public static float min(final float n, final float n2) {
        if (Float.isNaN(n)) {
            return n2;
        }
        if (Float.isNaN(n2)) {
            return n;
        }
        return Math.min(n, n2);
    }
    
    public static double max(final double[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        double max = array[0];
        for (int i = 1; i < array.length; ++i) {
            max = max(array[i], max);
        }
        return max;
    }
    
    public static float max(final float[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        float max = array[0];
        for (int i = 1; i < array.length; ++i) {
            max = max(array[i], max);
        }
        return max;
    }
    
    public static double max(final double n, final double n2, final double n3) {
        return max(max(n, n2), n3);
    }
    
    public static double max(final double n, final double n2) {
        if (Double.isNaN(n)) {
            return n2;
        }
        if (Double.isNaN(n2)) {
            return n;
        }
        return Math.max(n, n2);
    }
    
    public static float max(final float n, final float n2, final float n3) {
        return max(max(n, n2), n3);
    }
    
    public static float max(final float n, final float n2) {
        if (Float.isNaN(n)) {
            return n2;
        }
        if (Float.isNaN(n2)) {
            return n;
        }
        return Math.max(n, n2);
    }
}
