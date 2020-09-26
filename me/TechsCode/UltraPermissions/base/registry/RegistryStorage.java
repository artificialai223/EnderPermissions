

package me.TechsCode.EnderPermissions.base.registry;

import java.util.Iterator;
import me.TechsCode.EnderPermissions.base.storage.StorageImplementation;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.base.storage.SimpleStorage;

public class RegistryStorage extends SimpleStorage<RegistryStorageEntry>
{
    public RegistryStorage(final TechPlugin techPlugin, final Class<? extends StorageImplementation> clazz) {
        super(techPlugin, "Registry", RegistryStorageEntry.class, clazz, true);
    }
    
    public RegistryStorageEntry retrieve(final RegistryStorageEntry registryStorageEntry) {
        for (final RegistryStorageEntry registryStorageEntry2 : this.get()) {
            if (registryStorageEntry2.getKey().equalsIgnoreCase(registryStorageEntry.getKey())) {
                return registryStorageEntry2;
            }
        }
        return this.create(registryStorageEntry);
    }
}
