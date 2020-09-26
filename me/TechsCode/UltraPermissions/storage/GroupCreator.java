

package me.TechsCode.EnderPermissions.storage;

import java.util.Set;
import me.TechsCode.EnderPermissions.base.storage.Stored;
import java.util.HashSet;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.base.item.XMaterial;

public class GroupCreator
{
    private final GroupStorage storage;
    private final String name;
    private int id;
    private String server;
    private String world;
    private String prefix;
    private String suffix;
    private boolean default_;
    private int priority;
    private XMaterial icon;
    
    public GroupCreator(final GroupStorage storage, final String name) {
        this.priority = 1;
        this.icon = XMaterial.YELLOW_STAINED_GLASS_PANE;
        this.storage = storage;
        this.name = name;
    }
    
    public GroupCreator setDefault(final boolean default_) {
        this.default_ = default_;
        return this;
    }
    
    public GroupCreator setServer(final String server) {
        this.server = server;
        return this;
    }
    
    public GroupCreator setWorld(final String world) {
        this.world = world;
        return this;
    }
    
    public GroupCreator setPrefix(final String prefix) {
        this.prefix = prefix;
        return this;
    }
    
    public GroupCreator setSuffix(final String suffix) {
        this.suffix = suffix;
        return this;
    }
    
    public GroupCreator id(final int id) {
        this.id = id;
        return this;
    }
    
    public GroupCreator setPriority(final int priority) {
        this.priority = priority;
        return this;
    }
    
    public GroupCreator setIcon(final XMaterial icon) {
        this.icon = icon;
        return this;
    }
    
    public Group create() {
        return this.storage.create(new Group((this.id == 0) ? this.storage.getNextNumericId() : this.id, this.name, this.world, this.server, this.priority, new HashSet<Stored<Group>>(), this.default_, this.prefix, this.suffix, this.icon.name()));
    }
}
