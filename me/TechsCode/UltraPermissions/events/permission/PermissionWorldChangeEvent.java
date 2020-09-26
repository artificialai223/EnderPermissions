

package me.TechsCode.EnderPermissions.events.permission;

import me.TechsCode.EnderPermissions.storage.objects.Permission;
import java.util.Optional;

public class PermissionWorldChangeEvent extends PermissionEvent
{
    private final Optional<String> oldWorld;
    private final Optional<String> newWorld;
    
    public PermissionWorldChangeEvent(final Permission permission, final Optional<String> oldWorld, final Optional<String> newWorld) {
        super(permission);
        this.oldWorld = oldWorld;
        this.newWorld = newWorld;
    }
    
    public Optional<String> getOldWorld() {
        return this.oldWorld;
    }
    
    public Optional<String> getNewWorld() {
        return this.newWorld;
    }
}
