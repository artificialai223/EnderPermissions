

package me.TechsCode.EnderPermissions.dependencies.commons.lang.math;

import java.io.Serializable;

public final class FloatRange extends Range implements Serializable
{
    private static final long serialVersionUID = 71849363892750L;
    private final float min;
    private final float max;
    private transient Float minObject;
    private transient Float maxObject;
    private transient int hashCode;
    private transient String toString;
    
    public FloatRange(final float max) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (Float.isNaN(max)) {
            throw new IllegalArgumentException("The number must not be NaN");
        }
        this.min = max;
        this.max = max;
    }
    
    public FloatRange(final Number n) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (n == null) {
            throw new IllegalArgumentException("The number must not be null");
        }
        this.min = n.floatValue();
        this.max = n.floatValue();
        if (Float.isNaN(this.min) || Float.isNaN(this.max)) {
            throw new IllegalArgumentException("The number must not be NaN");
        }
        if (n instanceof Float) {
            this.minObject = (Float)n;
            this.maxObject = (Float)n;
        }
    }
    
    public FloatRange(final float min, final float max) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (Float.isNaN(min) || Float.isNaN(max)) {
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
    
    public FloatRange(final Number n, final Number n2) {
        this.minObject = null;
        this.maxObject = null;
        this.hashCode = 0;
        this.toString = null;
        if (n == null || n2 == null) {
            throw new IllegalArgumentException("The numbers must not be null");
        }
        final float floatValue = n.floatValue();
        final float floatValue2 = n2.floatValue();
        if (Float.isNaN(floatValue) || Float.isNaN(floatValue2)) {
            throw new IllegalArgumentException("The numbers must not be NaN");
        }
        if (floatValue2 < floatValue) {
            this.min = floatValue2;
            this.max = floatValue;
            if (n2 instanceof Float) {
                this.minObject = (Float)n2;
            }
            if (n instanceof Float) {
                this.maxObject = (Float)n;
            }
        }
        else {
            this.min = floatValue;
            this.max = floatValue2;
            if (n instanceof Float) {
                this.minObject = (Float)n;
            }
            if (n2 instanceof Float) {
                this.maxObject = (Float)n2;
            }
        }
    }
    
    public Number getMinimumNumber() {
        if (this.minObject == null) {
            this.minObject = new Float(this.min);
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
        return this.min;
    }
    
    public Number getMaximumNumber() {
        if (this.maxObject == null) {
            this.maxObject = new Float(this.max);
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
        return this.max;
    }
    
    public boolean containsNumber(final Number n) {
        return n != null && this.containsFloat(n.floatValue());
    }
    
    public boolean containsFloat(final float n) {
        return n >= this.min && n <= this.max;
    }
    
    public boolean containsRange(final Range range) {
        return range != null && this.containsFloat(range.getMinimumFloat()) && this.containsFloat(range.getMaximumFloat());
    }
    
    public boolean overlapsRange(final Range range) {
        return range != null && (range.containsFloat(this.min) || range.containsFloat(this.max) || this.containsFloat(range.getMinimumFloat()));
    }
    
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FloatRange)) {
            return false;
        }
        final FloatRange floatRange = (FloatRange)o;
        return Float.floatToIntBits(this.min) == Float.floatToIntBits(floatRange.min) && Float.floatToIntBits(this.max) == Float.floatToIntBits(floatRange.max);
    }
    
    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = 17;
            this.hashCode = 37 * this.hashCode + this.getClass().hashCode();
            this.hashCode = 37 * this.hashCode + Float.floatToIntBits(this.min);
            this.hashCode = 37 * this.hashCode + Float.floatToIntBits(this.max);
        }
        return this.hashCode;
    }
    
    public String toString() {
        if (this.toString == null) {
            final StringBuffer sb = new StringBuffer(32);
            sb.append("Range[");
            sb.append(this.min);
            sb.append(',');
            sb.append(this.max);
            sb.append(']');
            this.toString = sb.toString();
        }
        return this.toString;
    }
}
