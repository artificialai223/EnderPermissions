

package me.TechsCode.EnderPermissions.base.storage;

import me.TechsCode.EnderPermissions.base.TechPlugin;

public class SimpleStorage<S extends Storable> extends Storage<S>
{
    public SimpleStorage(final TechPlugin techPlugin, final String s, final Class<? extends Storable> clazz, final Class<? extends StorageImplementation> clazz2, final boolean b) {
        super(techPlugin, s, clazz, clazz2, b);
    }
    
    @Override
    public void onMount(final S n) {
    }
    
    @Override
    public void onCreation(final S n) {
    }
    
    @Override
    public void onChange(final S n) {
    }
    
    @Override
    public void onDestroy(final S n) {
    }
    
    @Override
    public void onDataSynchronization() {
    }
}
