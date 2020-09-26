

package me.TechsCode.EnderPermissions.dependencies.commons.lang.mutable;

public class MutableInt extends Number implements Comparable, Mutable
{
    private static final long serialVersionUID = 512176391864L;
    private int value;
    
    public MutableInt() {
    }
    
    public MutableInt(final int value) {
        this.value = value;
    }
    
    public MutableInt(final Number n) {
        this.value = n.intValue();
    }
    
    public MutableInt(final String s) {
        this.value = Integer.parseInt(s);
    }
    
    public Object getValue() {
        return new Integer(this.value);
    }
    
    public void setValue(final int value) {
        this.value = value;
    }
    
    public void setValue(final Object o) {
        this.setValue(((Number)o).intValue());
    }
    
    public void increment() {
        ++this.value;
    }
    
    public void decrement() {
        --this.value;
    }
    
    public void add(final int n) {
        this.value += n;
    }
    
    public void add(final Number n) {
        this.value += n.intValue();
    }
    
    public void subtract(final int n) {
        this.value -= n;
    }
    
    public void subtract(final Number n) {
        this.value -= n.intValue();
    }
    
    public int intValue() {
        return this.value;
    }
    
    public long longValue() {
        return this.value;
    }
    
    public float floatValue() {
        return (float)this.value;
    }
    
    public double doubleValue() {
        return this.value;
    }
    
    public Integer toInteger() {
        return new Integer(this.intValue());
    }
    
    public boolean equals(final Object o) {
        return o instanceof MutableInt && this.value == ((MutableInt)o).intValue();
    }
    
    public int hashCode() {
        return this.value;
    }
    
    public int compareTo(final Object o) {
        final int value = ((MutableInt)o).value;
        return (this.value < value) ? -1 : ((this.value == value) ? false : true);
    }
    
    public String toString() {
        return String.valueOf(this.value);
    }
}
