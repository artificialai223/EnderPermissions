

package me.TechsCode.EnderPermissions.storage;

import me.TechsCode.EnderPermissions.storage.objects.Permission;
import me.TechsCode.EnderPermissions.storage.objects.Holder;

public class PermissionCreator
{
    private PermissionStorage storage;
    private String name;
    private Holder holder;
    private String world;
    private String server;
    private boolean positive;
    private long expiration;
    
    public PermissionCreator(final PermissionStorage storage, final String name, final Holder holder) {
        this.positive = true;
        this.storage = storage;
        this.name = name;
        this.holder = holder;
        if (name.startsWith("-")) {
            this.name = name.substring(1);
            this.positive = false;
        }
        this.name = this.name.replace(" ", ".");
    }
    
    public PermissionCreator setServer(final String server) {
        this.server = server;
        return this;
    }
    
    public PermissionCreator setWorld(final String world) {
        this.world = world;
        return this;
    }
    
    public PermissionCreator setPositive(final boolean positive) {
        this.positive = positive;
        return this;
    }
    
    public PermissionCreator setExpiration(final long expiration) {
        this.expiration = expiration;
        return this;
    }
    
    public Permission create() {
        return this.storage.create(new Permission(this.storage.getNextNumericId(), this.holder, this.world, this.server, this.name, this.positive, this.expiration));
    }
}
