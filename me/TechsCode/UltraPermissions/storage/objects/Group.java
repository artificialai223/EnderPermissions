

package me.TechsCode.EnderPermissions.storage.objects;

import java.util.stream.Stream;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashSet;
import me.TechsCode.EnderPermissions.base.storage.Storage;
import me.TechsCode.EnderPermissions.base.misc.Getter;
import java.util.Arrays;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import java.util.function.Function;
import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.storage.collection.UserList;
import me.TechsCode.EnderPermissions.storage.PermissionCreator;
import me.TechsCode.EnderPermissions.storage.collection.PermissionList;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import java.util.function.Supplier;
import java.util.function.Predicate;
import java.util.Objects;
import me.TechsCode.EnderPermissions.storage.collection.GroupList;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import me.TechsCode.EnderPermissions.base.storage.Stored;
import java.util.Set;
import me.TechsCode.EnderPermissions.StorageController;
import me.TechsCode.EnderPermissions.base.storage.Storable;

public class Group extends Storable implements PermissionHolder
{
    private StorageController controller;
    private int id;
    private String name;
    private String world;
    private String server;
    private int priority;
    private Set<Stored<Group>> inheritedGroups;
    private boolean isDefault;
    private String prefix;
    private String suffix;
    private String icon;
    
    public Group(final int id, final String name, final String world, final String server, final int priority, final Set<Stored<Group>> inheritedGroups, final boolean isDefault, final String prefix, final String suffix, final String icon) {
        this.id = id;
        this.name = name;
        this.world = world;
        this.server = server;
        this.priority = priority;
        this.inheritedGroups = inheritedGroups;
        this.isDefault = isDefault;
        this.prefix = prefix;
        this.suffix = suffix;
        this.icon = icon;
    }
    
    public List<UserRankup> getRankups() {
        return this.controller.getUserStorage().getUsers().stream().flatMap(user -> user.getRankups().stream()).filter(userRankup -> userRankup.getGroup().equals(this.toStored())).collect((Collector<? super Object, ?, List<UserRankup>>)Collectors.toList());
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
        this.sync();
    }
    
    public Optional<String> getWorld() {
        return Optional.ofNullable(this.world);
    }
    
    public void setWorld(final String world) {
        this.world = world;
        this.sync();
    }
    
    public Optional<String> getServer() {
        return Optional.ofNullable(this.server);
    }
    
    public void setServer(final String server) {
        this.server = server;
        this.sync();
    }
    
    public int getPriority() {
        return this.priority;
    }
    
    public void setPriority(final int priority) {
        this.priority = priority;
        this.sync();
    }
    
    public Set<Stored<Group>> getInheritedGroups() {
        return this.inheritedGroups;
    }
    
    public GroupList getActiveInheritedGroups() {
        return this.inheritedGroups.stream().map(stored -> stored.get().orElse(null)).filter(Objects::nonNull).collect((Collector<? super Object, ?, GroupList>)Collectors.toCollection((Supplier<R>)GroupList::new));
    }
    
    public void addInheritance(final Group group) {
        this.inheritedGroups.add(group.toStored());
        this.sync();
    }
    
    public void removeInheritance(final Group group) {
        this.inheritedGroups.remove(group.toStored());
        this.sync();
    }
    
    public boolean isDefault() {
        return this.isDefault;
    }
    
    public void setDefault(final boolean isDefault) {
        this.isDefault = isDefault;
        this.sync();
    }
    
    public Optional<String> getPrefix() {
        return Optional.ofNullable(this.prefix);
    }
    
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
        this.sync();
    }
    
    public Optional<String> getSuffix() {
        return Optional.ofNullable(this.suffix);
    }
    
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
        this.sync();
    }
    
    public XMaterial getIcon() {
        return XMaterial.valueOf(this.icon);
    }
    
    public void setIcon(final XMaterial xMaterial) {
        this.icon = xMaterial.name();
        this.sync();
    }
    
    @Override
    public PermissionList getPermissions() {
        return this.controller.getPermissionStorage().getPermissions().holder(Holder.fromGroup(this));
    }
    
    @Override
    public PermissionCreator newPermission(final String s) {
        return this.controller.getPermissionStorage().newPermission(s, Holder.fromGroup(this));
    }
    
    public UserList getUsers() {
        return this.controller.getUserStorage().getUsers().usersInGroup(this.toStored());
    }
    
    public void remove() {
        this.controller.getGroupStorage().getGroups().forEach(group -> group.removeInheritance(this));
        this.getUsers().forEach(user -> user.removeGroup(this));
        this.getPermissions().forEach(Permission::remove);
        this.destroy();
    }
    
    public PermissionList getInheritedPermissions() {
        return this.controller.getPermissionStorage().getPermissions().holder(this.getActiveInheritedGroups().getWithRecursiveInherits().holders());
    }
    
    @Override
    public PermissionList getAdditionalPermissions() {
        return this.getInheritedPermissions();
    }
    
    public Group createCopy() {
        return new Group(this.id, this.name, this.world, this.server, this.priority, this.inheritedGroups, this.isDefault, this.prefix, this.suffix, this.icon);
    }
    
    @Override
    public String getKey() {
        return this.id + "";
    }
    
    @Override
    public void setKey(final String s) {
        this.id = Integer.parseInt(s);
    }
    
    @Override
    public JsonObject getState() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", this.name);
        if (this.world != null) {
            jsonObject.addProperty("world", this.world);
        }
        if (this.server != null) {
            jsonObject.addProperty("serverId", this.server);
        }
        jsonObject.addProperty("priority", (Number)this.priority);
        if (this.inheritedGroups.size() != 0) {
            jsonObject.addProperty("inherits", (String)this.inheritedGroups.stream().map((Function<? super Object, ?>)Stored::getKey).collect((Collector<? super Object, ?, String>)Collectors.joining(";")));
        }
        jsonObject.addProperty("default", Boolean.valueOf(this.isDefault));
        if (this.prefix != null) {
            jsonObject.addProperty("prefix", this.prefix.replace("§", "&"));
        }
        if (this.suffix != null) {
            jsonObject.addProperty("suffix", this.suffix.replace("§", "&"));
        }
        if (this.icon != null) {
            jsonObject.addProperty("icon", this.icon);
        }
        return jsonObject;
    }
    
    @Override
    public void setState(final JsonObject jsonObject, final TechPlugin techPlugin) {
        final StorageController storageController = (StorageController)techPlugin;
        this.name = jsonObject.get("name").getAsString();
        this.world = (jsonObject.has("world") ? (jsonObject.get("world").isJsonNull() ? null : jsonObject.get("world").getAsString()) : null);
        this.server = (jsonObject.has("serverId") ? (jsonObject.get("serverId").isJsonNull() ? null : jsonObject.get("serverId").getAsString()) : null);
        this.priority = jsonObject.get("priority").getAsInt();
        if (jsonObject.has("inherits")) {
            this.inheritedGroups = Arrays.stream(jsonObject.get("inherits").getAsString().split(";")).filter(s -> s.length() != 0).map(s2 -> new Stored(s2, (Getter<Storage<Storable>>)storageController::getGroupStorage)).collect((Collector<? super Object, ?, Set<Stored<Group>>>)Collectors.toSet());
        }
        else {
            this.inheritedGroups = new HashSet<Stored<Group>>();
        }
        this.isDefault = jsonObject.get("default").getAsBoolean();
        this.prefix = (jsonObject.has("prefix") ? jsonObject.get("prefix").getAsString() : null);
        this.suffix = (jsonObject.has("suffix") ? jsonObject.get("suffix").getAsString() : null);
        this.icon = jsonObject.get("icon").getAsString();
    }
    
    @Override
    public void onMount(final TechPlugin techPlugin) {
        this.controller = (StorageController)techPlugin;
    }
    
    public void clean() {
        for (final Stored stored : new ArrayList<Stored>(this.inheritedGroups)) {
            if (!stored.isPresent()) {
                this.inheritedGroups.remove(stored);
                this.sync();
            }
        }
    }
    
    public Stored<Group> toStored() {
        return new Stored<Group>(this.getKey(), () -> this.storage);
    }
    
    public void _justDestroy() {
        this.destroy();
    }
}
