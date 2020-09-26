

package me.TechsCode.EnderPermissions;

import me.TechsCode.EnderPermissions.storage.UserStorage;
import me.TechsCode.EnderPermissions.storage.PermissionStorage;
import me.TechsCode.EnderPermissions.storage.GroupStorage;

public interface StorageController
{
    void onDataModification();
    
    GroupStorage getGroupStorage();
    
    PermissionStorage getPermissionStorage();
    
    UserStorage getUserStorage();
}
