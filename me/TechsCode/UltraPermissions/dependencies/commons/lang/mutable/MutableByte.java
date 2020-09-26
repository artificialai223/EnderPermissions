

package me.TechsCode.EnderPermissions.dependencies.commons.lang.mutable;

public class MutableByte extends Number implements Comparable, Mutable
{
    private static final long serialVersionUID = -1585823265L;
    private byte value;
    
    public MutableByte() {
    }
    
    public MutableByte(final byte value) {
        this.value = value;
    }
    
    public MutableByte(final Number n) {
        this.value = n.byteValue();
    }
    
    public MutableByte(final String s) {
        this.value = Byte.parseByte(s);
    }
    
    public Object getValue() {
        return new Byte(this.value);
    }
    
    public void setValue(final byte value) {
        this.value = value;
    }
    
    public void setValue(final Object o) {
        this.setValue(((Number)o).byteValue());
    }
    
    public void increment() {
        ++this.value;
    }
    
    public void decrement() {
        --this.value;
    }
    
    public void add(final byte b) {
        this.value += b;
    }
    
    public void add(final Number n) {
        this.value += n.byteValue();
    }
    
    public void subtract(final byte b) {
        this.value -= b;
    }
    
    public void subtract(final Number n) {
        this.value -= n.byteValue();
    }
    
    public byte byteValue() {
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
    
    public Byte toByte() {
        return new Byte(this.byteValue());
    }
    
    public boolean equals(final Object o) {
        return o instanceof MutableByte && this.value == ((MutableByte)o).byteValue();
    }
    
    public int hashCode() {
        return this.value;
    }
    
    public int compareTo(final Object o) {
        final byte value = ((MutableByte)o).value;
        return (this.value < value) ? -1 : ((this.value == value) ? false : true);
    }
    
    public String toString() {
        return String.valueOf(this.value);
    }
}
