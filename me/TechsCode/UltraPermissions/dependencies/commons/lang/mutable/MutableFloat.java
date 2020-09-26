

package me.TechsCode.EnderPermissions.dependencies.commons.lang.mutable;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.math.NumberUtils;

public class MutableFloat extends Number implements Comparable, Mutable
{
    private static final long serialVersionUID = 5787169186L;
    private float value;
    
    public MutableFloat() {
    }
    
    public MutableFloat(final float value) {
        this.value = value;
    }
    
    public MutableFloat(final Number n) {
        this.value = n.floatValue();
    }
    
    public MutableFloat(final String s) {
        this.value = Float.parseFloat(s);
    }
    
    public Object getValue() {
        return new Float(this.value);
    }
    
    public void setValue(final float value) {
        this.value = value;
    }
    
    public void setValue(final Object o) {
        this.setValue(((Number)o).floatValue());
    }
    
    public boolean isNaN() {
        return Float.isNaN(this.value);
    }
    
    public boolean isInfinite() {
        return Float.isInfinite(this.value);
    }
    
    public void increment() {
        ++this.value;
    }
    
    public void decrement() {
        --this.value;
    }
    
    public void add(final float n) {
        this.value += n;
    }
    
    public void add(final Number n) {
        this.value += n.floatValue();
    }
    
    public void subtract(final float n) {
        this.value -= n;
    }
    
    public void subtract(final Number n) {
        this.value -= n.floatValue();
    }
    
    public int intValue() {
        return (int)this.value;
    }
    
    public long longValue() {
        return (long)this.value;
    }
    
    public float floatValue() {
        return this.value;
    }
    
    public double doubleValue() {
        return this.value;
    }
    
    public Float toFloat() {
        return new Float(this.floatValue());
    }
    
    public boolean equals(final Object o) {
        return o instanceof MutableFloat && Float.floatToIntBits(((MutableFloat)o).value) == Float.floatToIntBits(this.value);
    }
    
    public int hashCode() {
        return Float.floatToIntBits(this.value);
    }
    
    public int compareTo(final Object o) {
        return NumberUtils.compare(this.value, ((MutableFloat)o).value);
    }
    
    public String toString() {
        return String.valueOf(this.value);
    }
}
