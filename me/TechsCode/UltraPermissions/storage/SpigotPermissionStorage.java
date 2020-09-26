

package me.TechsCode.EnderPermissions.storage;

import me.TechsCode.EnderPermissions.base.storage.Storable;
import me.TechsCode.EnderPermissions.events.permission.PermissionDeletionEvent;
import org.bukkit.event.Event;
import me.TechsCode.EnderPermissions.events.permission.PermissionCreationEvent;
import me.TechsCode.EnderPermissions.storage.objects.Permission;
import me.TechsCode.EnderPermissions.base.storage.StorageImplementation;
import me.TechsCode.EnderPermissions.base.TechPlugin;

public class SpigotPermissionStorage extends PermissionStorage
{
    public SpigotPermissionStorage(final TechPlugin techPlugin, final Class<? extends StorageImplementation> clazz) {
        super(techPlugin, clazz);
    }
    
    @Override
    public void onCreation(final Permission permission) {
        super.onCreation(permission);
        final PermissionCreationEvent permissionCreationEvent = new PermissionCreationEvent(permission);
        permissionCreationEvent.call(this.plugin, permissionCreationEvent);
    }
    
    @Override
    public void onDestroy(final Permission permission) {
        super.onDestroy(permission);
        final PermissionDeletionEvent permissionDeletionEvent = new PermissionDeletionEvent(permission);
        permissionDeletionEvent.call(this.plugin, permissionDeletionEvent);
    }
    
    @Override
    public void onChange(final Permission permission) {
        super.onChange(permission);
    }
}
