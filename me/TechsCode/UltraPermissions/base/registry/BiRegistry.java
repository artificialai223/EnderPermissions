

package me.TechsCode.EnderPermissions.base.registry;

public class BiRegistry<T extends RegistryStorable>
{
    protected T local;
    protected T global;
    
    public BiRegistry(final T local, final T global) {
        this.local = local;
        this.global = global;
    }
    
    public T local() {
        return this.local;
    }
    
    public T global() {
        return this.global;
    }
    
    public T globalPreferred() {
        return this.hasGlobal() ? this.global : this.local;
    }
    
    public boolean hasGlobal() {
        return this.global != null;
    }
}
