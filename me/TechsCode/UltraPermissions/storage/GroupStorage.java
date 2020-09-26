

package me.TechsCode.EnderPermissions.storage;

import me.TechsCode.EnderPermissions.base.storage.Storable;
import me.TechsCode.EnderPermissions.storage.collection.GroupList;
import me.TechsCode.EnderPermissions.StorageController;
import me.TechsCode.EnderPermissions.base.storage.StorageImplementation;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.base.storage.Storage;

public class GroupStorage extends Storage<Group>
{
    public GroupStorage(final TechPlugin techPlugin, final Class<? extends StorageImplementation> clazz) {
        super(techPlugin, "Groups", Group.class, clazz, true);
    }
    
    @Override
    public void onMount(final Group group) {
    }
    
    @Override
    public void onCreation(final Group group) {
        ((StorageController)this.plugin).onDataModification();
    }
    
    @Override
    public void onDestroy(final Group group) {
        ((StorageController)this.plugin).onDataModification();
    }
    
    @Override
    public void onChange(final Group group) {
        ((StorageController)this.plugin).onDataModification();
    }
    
    @Override
    public void onDataSynchronization() {
        ((StorageController)this.plugin).onDataModification();
    }
    
    public GroupList getGroups() {
        final GroupList list = new GroupList();
        list.addAll(this.get());
        list.bestToWorst();
        return list;
    }
    
    public GroupCreator newGroup(final String s) {
        return new GroupCreator(this, s);
    }
}
