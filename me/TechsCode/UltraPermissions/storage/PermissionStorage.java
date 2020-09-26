

package me.TechsCode.EnderPermissions.storage;

import me.TechsCode.EnderPermissions.base.storage.Storable;
import me.TechsCode.EnderPermissions.storage.objects.Holder;
import java.util.function.Function;
import java.util.Comparator;
import me.TechsCode.EnderPermissions.storage.collection.PermissionList;
import me.TechsCode.EnderPermissions.StorageController;
import me.TechsCode.EnderPermissions.base.storage.StorageImplementation;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.storage.objects.Permission;
import me.TechsCode.EnderPermissions.base.storage.Storage;

public class PermissionStorage extends Storage<Permission>
{
    public PermissionStorage(final TechPlugin techPlugin, final Class<? extends StorageImplementation> clazz) {
        super(techPlugin, "Permissions", Permission.class, clazz, true);
    }
    
    @Override
    public void onMount(final Permission permission) {
    }
    
    @Override
    public void onCreation(final Permission permission) {
        ((StorageController)this.plugin).onDataModification();
    }
    
    @Override
    public void onDestroy(final Permission permission) {
        ((StorageController)this.plugin).onDataModification();
    }
    
    @Override
    public void onChange(final Permission permission) {
        ((StorageController)this.plugin).onDataModification();
    }
    
    @Override
    public void onDataSynchronization() {
        ((StorageController)this.plugin).onDataModification();
    }
    
    public PermissionList getPermissions() {
        final PermissionList list = new PermissionList();
        list.addAll(this.get());
        list.sort(Comparator.comparing((Function<? super Permission, ? extends Comparable>)Permission::getId));
        return list;
    }
    
    public PermissionCreator newPermission(final String s, final Holder holder) {
        return new PermissionCreator(this, s, holder);
    }
}
