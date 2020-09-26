

package me.TechsCode.EnderPermissions.storage.objects;

import me.TechsCode.EnderPermissions.storage.PermissionCreator;
import me.TechsCode.EnderPermissions.storage.collection.PermissionList;

public interface PermissionHolder
{
    String getName();
    
    PermissionList getPermissions();
    
    PermissionList getAdditionalPermissions();
    
    PermissionCreator newPermission(final String p0);
}
