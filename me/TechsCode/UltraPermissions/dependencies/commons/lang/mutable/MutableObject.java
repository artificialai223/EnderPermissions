

package me.TechsCode.EnderPermissions.dependencies.commons.lang.mutable;

import java.io.Serializable;

public class MutableObject implements Mutable, Serializable
{
    private static final long serialVersionUID = 86241875189L;
    private Object value;
    
    public MutableObject() {
    }
    
    public MutableObject(final Object value) {
        this.value = value;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    public void setValue(final Object value) {
        this.value = value;
    }
    
    public boolean equals(final Object o) {
        if (o instanceof MutableObject) {
            final Object value = ((MutableObject)o).value;
            return this.value == value || (this.value != null && this.value.equals(value));
        }
        return false;
    }
    
    public int hashCode() {
        return (this.value == null) ? 0 : this.value.hashCode();
    }
    
    public String toString() {
        return (this.value == null) ? "null" : this.value.toString();
    }
}
