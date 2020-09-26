

package me.TechsCode.EnderPermissions.dependencies.commons.lang.math;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.text.StrBuilder;
import java.io.Serializable;

public final class IntRange extends Range implements Serializable
{
    private static final long serialVersionUID = 71849363892730L;
    private final int min;
    private final int max;
    private transient Integer minObject;
    private transient Integer maxObject;
    private transient int hashCode;
    private transient String toString;
    
    public IntRange(final int n) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        this.min = n;
        this.max = n;
    }
    
    public IntRange(final Number n) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (n == null) {
            throw new IllegalArgumentException("The number must not be null");
        }
        this.min = n.intValue();
        this.max = n.intValue();
        if (n instanceof Integer) {
            this.minObject = (Integer)n;
            this.maxObject = (Integer)n;
        }
    }
    
    public IntRange(final int n, final int n2) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (n2 < n) {
            this.min = n2;
            this.max = n;
        }
        else {
            this.min = n;
            this.max = n2;
        }
    }
    
    public IntRange(final Number n, final Number n2) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (n == null || n2 == null) {
            throw new IllegalArgumentException("The numbers must not be null");
        }
        final int intValue = n.intValue();
        final int intValue2 = n2.intValue();
        if (intValue2 < intValue) {
            this.min = intValue2;
            this.max = intValue;
            if (n2 instanceof Integer) {
                this.minObject = (Integer)n2;
            }
            if (n instanceof Integer) {
                this.maxObject = (Integer)n;
            }
        }
        else {
            this.min = intValue;
            this.max = intValue2;
            if (n instanceof Integer) {
                this.minObject = (Integer)n;
            }
            if (n2 instanceof Integer) {
                this.maxObject = (Integer)n2;
            }
        }
    }
    
    public Number getMinimumNumber() {
        if (this.minObject == null) {
            this.minObject = new Integer(this.min);
        }
        return this.minObject;
    }
    
    public long getMinimumLong() {
        return this.min;
    }
    
    public int getMinimumInteger() {
        return this.min;
    }
    
    public double getMinimumDouble() {
        return this.min;
    }
    
    public float getMinimumFloat() {
        return (float)this.min;
    }
    
    public Number getMaximumNumber() {
        if (this.maxObject == null) {
            this.maxObject = new Integer(this.max);
        }
        return this.maxObject;
    }
    
    public long getMaximumLong() {
        return this.max;
    }
    
    public int getMaximumInteger() {
        return this.max;
    }
    
    public double getMaximumDouble() {
        return this.max;
    }
    
    public float getMaximumFloat() {
        return (float)this.max;
    }
    
    public boolean containsNumber(final Number n) {
        return n != null && this.containsInteger(n.intValue());
    }
    
    public boolean containsInteger(final int n) {
        return n >= this.min && n <= this.max;
    }
    
    public boolean containsRange(final Range range) {
        return range != null && this.containsInteger(range.getMinimumInteger()) && this.containsInteger(range.getMaximumInteger());
    }
    
    public boolean overlapsRange(final Range range) {
        return range != null && (range.containsInteger(this.min) || range.containsInteger(this.max) || this.containsInteger(range.getMinimumInteger()));
    }
    
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IntRange)) {
            return false;
        }
        final IntRange intRange = (IntRange)o;
        return this.min == intRange.min && this.max == intRange.max;
    }
    
    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = 17;
            this.hashCode = 37 * this.hashCode + this.getClass().hashCode();
            this.hashCode = 37 * this.hashCode + this.min;
            this.hashCode = 37 * this.hashCode + this.max;
        }
        return this.hashCode;
    }
    
    public String toString() {
        if (this.toString == null) {
            final StrBuilder strBuilder = new StrBuilder(32);
            strBuilder.append("Range[");
            strBuilder.append(this.min);
            strBuilder.append(',');
            strBuilder.append(this.max);
            strBuilder.append(']');
            this.toString = strBuilder.toString();
        }
        return this.toString;
    }
    
    public int[] toArray() {
        final int[] array = new int[this.max - this.min + 1];
        for (int i = 0; i < array.length; ++i) {
            array[i] = this.min + i;
        }
        return array;
    }
}
