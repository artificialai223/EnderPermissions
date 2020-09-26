

package me.TechsCode.EnderPermissions.dependencies.commons.lang.math;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.text.StrBuilder;
import java.io.Serializable;

public final class LongRange extends Range implements Serializable
{
    private static final long serialVersionUID = 71849363892720L;
    private final long min;
    private final long max;
    private transient Long minObject;
    private transient Long maxObject;
    private transient int hashCode;
    private transient String toString;
    
    public LongRange(final long n) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        this.min = n;
        this.max = n;
    }
    
    public LongRange(final Number n) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (n == null) {
            throw new IllegalArgumentException("The number must not be null");
        }
        this.min = n.longValue();
        this.max = n.longValue();
        if (n instanceof Long) {
            this.minObject = (Long)n;
            this.maxObject = (Long)n;
        }
    }
    
    public LongRange(final long n, final long n2) {
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
    
    public LongRange(final Number n, final Number n2) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (n == null || n2 == null) {
            throw new IllegalArgumentException("The numbers must not be null");
        }
        final long longValue = n.longValue();
        final long longValue2 = n2.longValue();
        if (longValue2 < longValue) {
            this.min = longValue2;
            this.max = longValue;
            if (n2 instanceof Long) {
                this.minObject = (Long)n2;
            }
            if (n instanceof Long) {
                this.maxObject = (Long)n;
            }
        }
        else {
            this.min = longValue;
            this.max = longValue2;
            if (n instanceof Long) {
                this.minObject = (Long)n;
            }
            if (n2 instanceof Long) {
                this.maxObject = (Long)n2;
            }
        }
    }
    
    public Number getMinimumNumber() {
        if (this.minObject == null) {
            this.minObject = new Long(this.min);
        }
        return this.minObject;
    }
    
    public long getMinimumLong() {
        return this.min;
    }
    
    public int getMinimumInteger() {
        return (int)this.min;
    }
    
    public double getMinimumDouble() {
        return (double)this.min;
    }
    
    public float getMinimumFloat() {
        return (float)this.min;
    }
    
    public Number getMaximumNumber() {
        if (this.maxObject == null) {
            this.maxObject = new Long(this.max);
        }
        return this.maxObject;
    }
    
    public long getMaximumLong() {
        return this.max;
    }
    
    public int getMaximumInteger() {
        return (int)this.max;
    }
    
    public double getMaximumDouble() {
        return (double)this.max;
    }
    
    public float getMaximumFloat() {
        return (float)this.max;
    }
    
    public boolean containsNumber(final Number n) {
        return n != null && this.containsLong(n.longValue());
    }
    
    public boolean containsLong(final long n) {
        return n >= this.min && n <= this.max;
    }
    
    public boolean containsRange(final Range range) {
        return range != null && this.containsLong(range.getMinimumLong()) && this.containsLong(range.getMaximumLong());
    }
    
    public boolean overlapsRange(final Range range) {
        return range != null && (range.containsLong(this.min) || range.containsLong(this.max) || this.containsLong(range.getMinimumLong()));
    }
    
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LongRange)) {
            return false;
        }
        final LongRange longRange = (LongRange)o;
        return this.min == longRange.min && this.max == longRange.max;
    }
    
    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = 17;
            this.hashCode = 37 * this.hashCode + this.getClass().hashCode();
            this.hashCode = 37 * this.hashCode + (int)(this.min ^ this.min >> 32);
            this.hashCode = 37 * this.hashCode + (int)(this.max ^ this.max >> 32);
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
    
    public long[] toArray() {
        final long[] array = new long[(int)(this.max - this.min + 1L)];
        for (int i = 0; i < array.length; ++i) {
            array[i] = this.min + i;
        }
        return array;
    }
}
