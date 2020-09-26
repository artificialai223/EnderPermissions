

package me.TechsCode.EnderPermissions.dependencies.commons.lang.math;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.text.StrBuilder;
import java.io.Serializable;

public final class DoubleRange extends Range implements Serializable
{
    private static final long serialVersionUID = 71849363892740L;
    private final double min;
    private final double max;
    private transient Double minObject;
    private transient Double maxObject;
    private transient int hashCode;
    private transient String toString;
    
    public DoubleRange(final double max) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (Double.isNaN(max)) {
            throw new IllegalArgumentException("The number must not be NaN");
        }
        this.min = max;
        this.max = max;
    }
    
    public DoubleRange(final Number n) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (n == null) {
            throw new IllegalArgumentException("The number must not be null");
        }
        this.min = n.doubleValue();
        this.max = n.doubleValue();
        if (Double.isNaN(this.min) || Double.isNaN(this.max)) {
            throw new IllegalArgumentException("The number must not be NaN");
        }
        if (n instanceof Double) {
            this.minObject = (Double)n;
            this.maxObject = (Double)n;
        }
    }
    
    public DoubleRange(final double min, final double max) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (Double.isNaN(min) || Double.isNaN(max)) {
            throw new IllegalArgumentException("The numbers must not be NaN");
        }
        if (max < min) {
            this.min = max;
            this.max = min;
        }
        else {
            this.min = min;
            this.max = max;
        }
    }
    
    public DoubleRange(final Number n, final Number n2) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (n == null || n2 == null) {
            throw new IllegalArgumentException("The numbers must not be null");
        }
        final double doubleValue = n.doubleValue();
        final double doubleValue2 = n2.doubleValue();
        if (Double.isNaN(doubleValue) || Double.isNaN(doubleValue2)) {
            throw new IllegalArgumentException("The numbers must not be NaN");
        }
        if (doubleValue2 < doubleValue) {
            this.min = doubleValue2;
            this.max = doubleValue;
            if (n2 instanceof Double) {
                this.minObject = (Double)n2;
            }
            if (n instanceof Double) {
                this.maxObject = (Double)n;
            }
        }
        else {
            this.min = doubleValue;
            this.max = doubleValue2;
            if (n instanceof Double) {
                this.minObject = (Double)n;
            }
            if (n2 instanceof Double) {
                this.maxObject = (Double)n2;
            }
        }
    }
    
    public Number getMinimumNumber() {
        if (this.minObject == null) {
            this.minObject = new Double(this.min);
        }
        return this.minObject;
    }
    
    public long getMinimumLong() {
        return (long)this.min;
    }
    
    public int getMinimumInteger() {
        return (int)this.min;
    }
    
    public double getMinimumDouble() {
        return this.min;
    }
    
    public float getMinimumFloat() {
        return (float)this.min;
    }
    
    public Number getMaximumNumber() {
        if (this.maxObject == null) {
            this.maxObject = new Double(this.max);
        }
        return this.maxObject;
    }
    
    public long getMaximumLong() {
        return (long)this.max;
    }
    
    public int getMaximumInteger() {
        return (int)this.max;
    }
    
    public double getMaximumDouble() {
        return this.max;
    }
    
    public float getMaximumFloat() {
        return (float)this.max;
    }
    
    public boolean containsNumber(final Number n) {
        return n != null && this.containsDouble(n.doubleValue());
    }
    
    public boolean containsDouble(final double n) {
        return n >= this.min && n <= this.max;
    }
    
    public boolean containsRange(final Range range) {
        return range != null && this.containsDouble(range.getMinimumDouble()) && this.containsDouble(range.getMaximumDouble());
    }
    
    public boolean overlapsRange(final Range range) {
        return range != null && (range.containsDouble(this.min) || range.containsDouble(this.max) || this.containsDouble(range.getMinimumDouble()));
    }
    
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DoubleRange)) {
            return false;
        }
        final DoubleRange doubleRange = (DoubleRange)o;
        return Double.doubleToLongBits(this.min) == Double.doubleToLongBits(doubleRange.min) && Double.doubleToLongBits(this.max) == Double.doubleToLongBits(doubleRange.max);
    }
    
    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = 17;
            this.hashCode = 37 * this.hashCode + this.getClass().hashCode();
            final long doubleToLongBits = Double.doubleToLongBits(this.min);
            this.hashCode = 37 * this.hashCode + (int)(doubleToLongBits ^ doubleToLongBits >> 32);
            final long doubleToLongBits2 = Double.doubleToLongBits(this.max);
            this.hashCode = 37 * this.hashCode + (int)(doubleToLongBits2 ^ doubleToLongBits2 >> 32);
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
}
