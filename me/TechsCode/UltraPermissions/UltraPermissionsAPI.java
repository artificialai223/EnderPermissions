

package me.TechsCode.EnderPermissions;

import me.TechsCode.EnderPermissions.storage.collection.UserList;
import me.TechsCode.EnderPermissions.storage.collection.GroupList;
import me.TechsCode.EnderPermissions.storage.collection.PermissionList;
import me.TechsCode.EnderPermissions.storage.PermissionCreator;
import me.TechsCode.EnderPermissions.storage.objects.Holder;
import me.TechsCode.EnderPermissions.storage.GroupCreator;

public interface EnderPermissionsAPI
{
    GroupCreator newGroup(final String p0);
    
    PermissionCreator newPermission(final String p0, final Holder p1);
    
    PermissionList getPermissions();
    
    GroupList getGroups();
    
    UserList getUsers();
}
