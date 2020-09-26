

package me.TechsCode.EnderPermissions.dependencies.commons.lang.mutable;

public class MutableShort extends Number implements Comparable, Mutable
{
    private static final long serialVersionUID = -2135791679L;
    private short value;
    
    public MutableShort() {
    }
    
    public MutableShort(final short value) {
        this.value = value;
    }
    
    public MutableShort(final Number n) {
        this.value = n.shortValue();
    }
    
    public MutableShort(final String s) {
        this.value = Short.parseShort(s);
    }
    
    public Object getValue() {
        return new Short(this.value);
    }
    
    public void setValue(final short value) {
        this.value = value;
    }
    
    public void setValue(final Object o) {
        this.setValue(((Number)o).shortValue());
    }
    
    public void increment() {
        ++this.value;
    }
    
    public void decrement() {
        --this.value;
    }
    
    public void add(final short n) {
        this.value += n;
    }
    
    public void add(final Number n) {
        this.value += n.shortValue();
    }
    
    public void subtract(final short n) {
        this.value -= n;
    }
    
    public void subtract(final Number n) {
        this.value -= n.shortValue();
    }
    
    public short shortValue() {
        return this.value;
    }
    
    public int intValue() {
        return this.value;
    }
    
    public long longValue() {
        return this.value;
    }
    
    public float floatValue() {
        return this.value;
    }
    
    public double doubleValue() {
        return this.value;
    }
    
    public Short toShort() {
        return new Short(this.shortValue());
    }
    
    public boolean equals(final Object o) {
        return o instanceof MutableShort && this.value == ((MutableShort)o).shortValue();
    }
    
    public int hashCode() {
        return this.value;
    }
    
    public int compareTo(final Object o) {
        final short value = ((MutableShort)o).value;
        return (this.value < value) ? -1 : ((this.value == value) ? false : true);
    }
    
    public String toString() {
        return String.valueOf(this.value);
    }
}
