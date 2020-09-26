

package me.TechsCode.EnderPermissions.base.registry;

public class SwitchableBiRegistry<T extends RegistryStorable>
{
    private BiRegistry<T> registry;
    
    public SwitchableBiRegistry(final BiRegistry<T> registry) {
        this.registry = registry;
    }
    
    public T get() {
        return ((Switchable)this.registry.local).isSwitchedTo() ? this.registry.local : ((this.registry.global != null) ? this.registry.global : this.registry.local);
    }
    
    public void switchToLocal() {
        ((Switchable)this.registry.local).setSwitchedTo(true);
    }
    
    public void switchToGlobal() {
        ((Switchable)this.registry.local).setSwitchedTo(false);
    }
    
    public T local() {
        return this.registry.local;
    }
    
    public T global() {
        return this.registry.global;
    }
    
    public boolean hasGlobal() {
        return this.registry.global != null;
    }
}
