

package me.TechsCode.EnderPermissions.transfer;

import me.TechsCode.EnderPermissions.base.storage.StorageImplementation;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.base.storage.Storage;
import me.TechsCode.EnderPermissions.base.storage.Storable;

public class TransferStorage<S extends Storable> extends Storage<S>
{
    private long lastWrite;
    
    public TransferStorage(final TechPlugin techPlugin, final String s, final Class<? extends Storable> clazz, final Class<? extends StorageImplementation> clazz2, final boolean b) {
        super(techPlugin, s, clazz, clazz2, b);
        this.lastWrite = System.currentTimeMillis();
    }
    
    @Override
    public void onMount(final S n) {
        this.lastWrite = System.currentTimeMillis();
    }
    
    @Override
    public void onCreation(final S n) {
        this.lastWrite = System.currentTimeMillis();
    }
    
    @Override
    public void onDestroy(final S n) {
        this.lastWrite = System.currentTimeMillis();
    }
    
    @Override
    public void onChange(final S n) {
        this.lastWrite = System.currentTimeMillis();
    }
    
    public long getLastWrite() {
        return this.lastWrite;
    }
    
    @Override
    public void onDataSynchronization() {
    }
}
