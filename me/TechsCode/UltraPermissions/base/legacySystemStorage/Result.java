

package me.TechsCode.EnderPermissions.base.legacySystemStorage;

import java.util.List;

public class Result
{
    private Object object;
    
    public Result(final Object object) {
        this.object = object;
    }
    
    @Override
    public String toString() {
        return this.object.toString();
    }
    
    public int toInt() {
        return Integer.valueOf(this.toString());
    }
    
    public long toLong() {
        return Long.valueOf(this.toString());
    }
    
    public boolean toBoolean() {
        return this.object != null && Boolean.valueOf(this.toString());
    }
    
    public boolean isNull() {
        return this.object == null || this.object.toString().contains("MemorySection");
    }
    
    public List<String> toStringList() {
        return (List<String>)this.object;
    }
    
    public Object asObject() {
        return this.object;
    }
}
