

package me.TechsCode.EnderPermissions.storage;

import me.TechsCode.EnderPermissions.base.storage.Storable;
import java.util.function.Consumer;
import me.TechsCode.EnderPermissions.tpl.SkinTexture;
import java.util.Map;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.base.storage.Stored;
import java.util.HashMap;
import me.TechsCode.EnderPermissions.EnderPermissionsAPI;
import java.util.UUID;
import me.TechsCode.EnderPermissions.storage.collection.UserList;
import me.TechsCode.EnderPermissions.StorageController;
import me.TechsCode.EnderPermissions.base.storage.StorageImplementation;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.base.storage.Storage;

public class UserStorage extends Storage<User>
{
    public UserStorage(final TechPlugin techPlugin, final Class<? extends StorageImplementation> clazz) {
        super(techPlugin, "Users", User.class, clazz, true);
    }
    
    @Override
    public void onMount(final User user) {
    }
    
    @Override
    public void onCreation(final User user) {
        ((StorageController)this.plugin).onDataModification();
    }
    
    @Override
    public void onDestroy(final User user) {
        ((StorageController)this.plugin).onDataModification();
    }
    
    @Override
    public void onChange(final User user) {
        ((StorageController)this.plugin).onDataModification();
    }
    
    @Override
    public void onDataSynchronization() {
        ((StorageController)this.plugin).onDataModification();
    }
    
    public UserList getUsers() {
        final UserList list = new UserList();
        list.addAll(this.get());
        return list;
    }
    
    public User registerUser(final UUID uuid, final String s, final boolean b) {
        final EnderPermissionsAPI EnderPermissionsAPI = (EnderPermissionsAPI)this.plugin;
        final User user = this.create(new User(uuid, s, false, new HashMap<Stored<Group>, Long>(), null, null, null));
        if (b) {
            EnderPermissionsAPI.getGroups().defaults(true).forEach(user::addGroup);
        }
        return user;
    }
}
