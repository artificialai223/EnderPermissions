

package me.TechsCode.EnderPermissions.dependencies.commons.lang.mutable;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.BooleanUtils;
import java.io.Serializable;

public class MutableBoolean implements Mutable, Serializable, Comparable
{
    private static final long serialVersionUID = -4830728138360036487L;
    private boolean value;
    
    public MutableBoolean() {
    }
    
    public MutableBoolean(final boolean value) {
        this.value = value;
    }
    
    public MutableBoolean(final Boolean b) {
        this.value = b;
    }
    
    public Object getValue() {
        return BooleanUtils.toBooleanObject(this.value);
    }
    
    public void setValue(final boolean value) {
        this.value = value;
    }
    
    public void setValue(final Object o) {
        this.setValue((boolean)o);
    }
    
    public boolean isTrue() {
        return this.value;
    }
    
    public boolean isFalse() {
        return !this.value;
    }
    
    public boolean booleanValue() {
        return this.value;
    }
    
    public Boolean toBoolean() {
        return BooleanUtils.toBooleanObject(this.value);
    }
    
    public boolean equals(final Object o) {
        return o instanceof MutableBoolean && this.value == ((MutableBoolean)o).booleanValue();
    }
    
    public int hashCode() {
        return this.value ? Boolean.TRUE.hashCode() : Boolean.FALSE.hashCode();
    }
    
    public int compareTo(final Object o) {
        return (this.value == ((MutableBoolean)o).value) ? 0 : (this.value ? 1 : -1);
    }
    
    public String toString() {
        return String.valueOf(this.value);
    }
}
