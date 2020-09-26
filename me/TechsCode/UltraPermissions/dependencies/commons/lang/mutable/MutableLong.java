

package me.TechsCode.EnderPermissions.dependencies.commons.lang.mutable;

public class MutableLong extends Number implements Comparable, Mutable
{
    private static final long serialVersionUID = 62986528375L;
    private long value;
    
    public MutableLong() {
    }
    
    public MutableLong(final long value) {
        this.value = value;
    }
    
    public MutableLong(final Number n) {
        this.value = n.longValue();
    }
    
    public MutableLong(final String s) {
        this.value = Long.parseLong(s);
    }
    
    public Object getValue() {
        return new Long(this.value);
    }
    
    public void setValue(final long value) {
        this.value = value;
    }
    
    public void setValue(final Object o) {
        this.setValue(((Number)o).longValue());
    }
    
    public void increment() {
        ++this.value;
    }
    
    public void decrement() {
        --this.value;
    }
    
    public void add(final long n) {
        this.value += n;
    }
    
    public void add(final Number n) {
        this.value += n.longValue();
    }
    
    public void subtract(final long n) {
        this.value -= n;
    }
    
    public void subtract(final Number n) {
        this.value -= n.longValue();
    }
    
    public int intValue() {
        return (int)this.value;
    }
    
    public long longValue() {
        return this.value;
    }
    
    public float floatValue() {
        return (float)this.value;
    }
    
    public double doubleValue() {
        return (double)this.value;
    }
    
    public Long toLong() {
        return new Long(this.longValue());
    }
    
    public boolean equals(final Object o) {
        return o instanceof MutableLong && this.value == ((MutableLong)o).longValue();
    }
    
    public int hashCode() {
        return (int)(this.value ^ this.value >>> 32);
    }
    
    public int compareTo(final Object o) {
        final long value = ((MutableLong)o).value;
        return (this.value < value) ? -1 : ((this.value == value) ? false : true);
    }
    
    public String toString() {
        return String.valueOf(this.value);
    }
}
