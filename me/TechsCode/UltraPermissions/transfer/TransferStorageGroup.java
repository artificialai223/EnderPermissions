

package me.TechsCode.EnderPermissions.transfer;

import java.util.Collections;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.Collection;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.base.storage.StorageImplementation;
import me.TechsCode.EnderPermissions.base.storage.Storable;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.base.storage.implementations.LocalFile;
import me.TechsCode.EnderPermissions.base.storage.implementations.MySQL;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.storage.objects.Permission;
import me.TechsCode.EnderPermissions.storage.objects.User;

class TransferStorageGroup
{
    TransferStorage<User> userStorage;
    TransferStorage<Permission> permissionStorage;
    TransferStorage<Group> groupStorage;
    
    public TransferStorageGroup(final EnderPermissions EnderPermissions, final boolean b) {
        this.userStorage = new TransferStorage<User>(EnderPermissions, "Users", User.class, (Class<? extends StorageImplementation>)(b ? MySQL.class : LocalFile.class), false);
        this.permissionStorage = new TransferStorage<Permission>(EnderPermissions, "Permissions", Permission.class, (Class<? extends StorageImplementation>)(b ? MySQL.class : LocalFile.class), false);
        this.groupStorage = new TransferStorage<Group>(EnderPermissions, "Groups", Group.class, (Class<? extends StorageImplementation>)(b ? MySQL.class : LocalFile.class), false);
    }
    
    public void copyTo(final TransferStorageGroup transferStorageGroup) {
        new ArrayList(transferStorageGroup.userStorage.get()).forEach((Consumer)User::_justDestroy);
        new ArrayList(transferStorageGroup.permissionStorage.get()).forEach((Consumer)Permission::_justDestroy);
        new ArrayList(transferStorageGroup.groupStorage.get()).forEach((Consumer)Group::_justDestroy);
        final Group group2;
        this.groupStorage.get().forEach(group -> group2 = transferStorageGroup.groupStorage.create(group.createCopy()));
        final Permission permission2;
        this.permissionStorage.get().forEach(permission -> permission2 = transferStorageGroup.permissionStorage.create(permission.createCopy()));
        final User user2;
        this.userStorage.get().forEach(user -> user2 = transferStorageGroup.userStorage.create(user.createCopy()));
    }
    
    public long getLastWrite() {
        return Collections.max((Collection<? extends Long>)Arrays.asList(this.userStorage.getLastWrite(), this.permissionStorage.getLastWrite(), this.groupStorage.getLastWrite()));
    }
}
