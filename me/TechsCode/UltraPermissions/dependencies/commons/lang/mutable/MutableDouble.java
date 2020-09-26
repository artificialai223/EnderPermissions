

package me.TechsCode.EnderPermissions.dependencies.commons.lang.mutable;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.math.NumberUtils;

public class MutableDouble extends Number implements Comparable, Mutable
{
    private static final long serialVersionUID = 1587163916L;
    private double value;
    
    public MutableDouble() {
    }
    
    public MutableDouble(final double value) {
        this.value = value;
    }
    
    public MutableDouble(final Number n) {
        this.value = n.doubleValue();
    }
    
    public MutableDouble(final String s) {
        this.value = Double.parseDouble(s);
    }
    
    public Object getValue() {
        return new Double(this.value);
    }
    
    public void setValue(final double value) {
        this.value = value;
    }
    
    public void setValue(final Object o) {
        this.setValue(((Number)o).doubleValue());
    }
    
    public boolean isNaN() {
        return Double.isNaN(this.value);
    }
    
    public boolean isInfinite() {
        return Double.isInfinite(this.value);
    }
    
    public void increment() {
        ++this.value;
    }
    
    public void decrement() {
        --this.value;
    }
    
    public void add(final double n) {
        this.value += n;
    }
    
    public void add(final Number n) {
        this.value += n.doubleValue();
    }
    
    public void subtract(final double n) {
        this.value -= n;
    }
    
    public void subtract(final Number n) {
        this.value -= n.doubleValue();
    }
    
    public int intValue() {
        return (int)this.value;
    }
    
    public long longValue() {
        return (long)this.value;
    }
    
    public float floatValue() {
        return (float)this.value;
    }
    
    public double doubleValue() {
        return this.value;
    }
    
    public Double toDouble() {
        return new Double(this.doubleValue());
    }
    
    public boolean equals(final Object o) {
        return o instanceof MutableDouble && Double.doubleToLongBits(((MutableDouble)o).value) == Double.doubleToLongBits(this.value);
    }
    
    public int hashCode() {
        final long doubleToLongBits = Double.doubleToLongBits(this.value);
        return (int)(doubleToLongBits ^ doubleToLongBits >>> 32);
    }
    
    public int compareTo(final Object o) {
        return NumberUtils.compare(this.value, ((MutableDouble)o).value);
    }
    
    public String toString() {
        return String.valueOf(this.value);
    }
}
