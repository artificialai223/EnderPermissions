

package me.TechsCode.EnderPermissions.storage.objects;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.base.storage.Storage;
import me.TechsCode.EnderPermissions.base.misc.Getter;
import java.util.HashMap;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.storage.collection.PermissionList;
import me.TechsCode.EnderPermissions.storage.PermissionCreator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.Objects;
import me.TechsCode.EnderPermissions.storage.collection.GroupList;
import java.util.Set;
import java.util.stream.Collector;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import me.TechsCode.EnderPermissions.storage.collection.RankupList;
import me.TechsCode.EnderPermissions.tpl.SkinTexture;
import me.TechsCode.EnderPermissions.base.storage.Stored;
import java.util.Map;
import java.util.UUID;
import me.TechsCode.EnderPermissions.StorageController;
import me.TechsCode.EnderPermissions.base.storage.Storable;

public class User extends Storable implements PermissionHolder
{
    private StorageController controller;
    private UUID uuid;
    private String playerName;
    private boolean superadmin;
    private Map<Stored<Group>, Long> rankups;
    private String prefix;
    private String suffix;
    private SkinTexture skinTexture;
    
    public User(final UUID uuid, final String playerName, final boolean superadmin, final Map<Stored<Group>, Long> rankups, final String prefix, final String suffix, final SkinTexture skinTexture) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.superadmin = superadmin;
        this.rankups = rankups;
        this.prefix = prefix;
        this.suffix = suffix;
        this.skinTexture = skinTexture;
    }
    
    public RankupList getRankups() {
        return this.rankups.entrySet().stream().map(entry -> new UserRankup(this, entry.getKey(), (long)entry.getValue())).collect((Collector<? super Object, ?, RankupList>)Collectors.toCollection((Supplier<R>)RankupList::new)).bestToWorst();
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    @Override
    public String getName() {
        return this.playerName;
    }
    
    public void setPlayerName(final String playerName) {
        this.playerName = playerName;
        this.sync();
    }
    
    public boolean isSuperadmin() {
        return this.superadmin;
    }
    
    public void setSuperadmin(final boolean superadmin) {
        this.superadmin = superadmin;
        this.sync();
    }
    
    public void addGroup(final Group group) {
        this.rankups.put(group.toStored(), 0L);
        this.sync();
    }
    
    public void addGroup(final Group group, final long l) {
        this.rankups.put(group.toStored(), l);
        this.sync();
    }
    
    public long getGroupExpiry(final Stored<Group> stored) {
        return this.rankups.get(stored);
    }
    
    public Set<Stored<Group>> getGroups() {
        return this.rankups.keySet();
    }
    
    public GroupList getActiveGroups() {
        return this.rankups.keySet().stream().map(stored -> stored.get().orElse(null)).filter(Objects::nonNull).collect((Collector<? super Object, ?, GroupList>)Collectors.toCollection((Supplier<R>)GroupList::new)).bestToWorst();
    }
    
    public Optional<SkinTexture> getSkinTexture() {
        return Optional.ofNullable(this.skinTexture);
    }
    
    public void setSkinTexture(final SkinTexture skinTexture) {
        if (skinTexture == null || (this.skinTexture != null && this.skinTexture.getUrl().equalsIgnoreCase(skinTexture.getUrl()))) {
            return;
        }
        this.skinTexture = skinTexture;
        this.sync();
    }
    
    public void removeGroup(final Group group) {
        if (!this.rankups.containsKey(group.toStored())) {
            return;
        }
        this.rankups.remove(group.toStored());
        this.sync();
    }
    
    public boolean hasPrefix() {
        return this.getPrefix().isPresent();
    }
    
    public Optional<String> getPrefix() {
        return Optional.ofNullable(this.prefix);
    }
    
    public boolean hasSuffix() {
        return this.getSuffix().isPresent();
    }
    
    public Optional<String> getSuffix() {
        return Optional.ofNullable(this.suffix);
    }
    
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
        this.sync();
    }
    
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
        this.sync();
    }
    
    @Override
    public PermissionCreator newPermission(final String s) {
        return this.controller.getPermissionStorage().newPermission(s, Holder.fromUser(this));
    }
    
    @Override
    public PermissionList getPermissions() {
        return this.controller.getPermissionStorage().getPermissions().holder(Holder.fromUser(this));
    }
    
    public void remove() {
        this.getPermissions().forEach(Permission::remove);
        this.destroy();
    }
    
    @Override
    public PermissionList getAdditionalPermissions() {
        return this.controller.getPermissionStorage().getPermissions().holder(this.getActiveGroups().getWithRecursiveInherits().holders());
    }
    
    public User createCopy() {
        return new User(this.uuid, this.playerName, this.superadmin, this.rankups, this.prefix, this.suffix, this.skinTexture);
    }
    
    @Override
    public String getKey() {
        return this.uuid.toString();
    }
    
    @Override
    public void setKey(final String name) {
        this.uuid = UUID.fromString(name);
    }
    
    @Override
    public JsonObject getState() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", this.playerName);
        jsonObject.addProperty("superadmin", Boolean.valueOf(this.superadmin));
        final JsonObject jsonObject2 = new JsonObject();
        this.rankups.forEach((stored, n) -> jsonObject2.addProperty(stored.getKey(), (Number)n));
        if (this.rankups.size() != 0) {
            jsonObject.add("groups", (JsonElement)jsonObject2);
        }
        if (this.prefix != null) {
            jsonObject.addProperty("prefix", this.prefix.replace("§", "&"));
        }
        if (this.suffix != null) {
            jsonObject.addProperty("suffix", this.suffix.replace("§", "&"));
        }
        if (this.skinTexture != null) {
            jsonObject.addProperty("skull", this.skinTexture.toString());
        }
        return jsonObject;
    }
    
    @Override
    public void setState(final JsonObject jsonObject, final TechPlugin techPlugin) {
        final StorageController storageController = (StorageController)techPlugin;
        this.playerName = jsonObject.get("name").getAsString();
        this.superadmin = jsonObject.get("superadmin").getAsBoolean();
        this.rankups = new HashMap<Stored<Group>, Long>();
        if (jsonObject.has("groups")) {
            jsonObject.get("groups").getAsJsonObject().entrySet().forEach(entry -> this.rankups.put(new Stored<Group>(entry.getKey(), (Getter<Storage<Storable>>)storageController::getGroupStorage), ((JsonElement)entry.getValue()).getAsLong()));
        }
        this.prefix = (jsonObject.has("prefix") ? jsonObject.get("prefix").getAsString() : null);
        this.suffix = (jsonObject.has("suffix") ? jsonObject.get("suffix").getAsString() : null);
        this.skinTexture = ((jsonObject.has("skull") && !jsonObject.get("skull").getAsString().contains("null:split:")) ? new SkinTexture(jsonObject.get("skull").getAsString()) : null);
    }
    
    @Override
    public void onMount(final TechPlugin techPlugin) {
        this.controller = (StorageController)techPlugin;
    }
    
    public void clean() {
        for (final Stored stored : new ArrayList<Stored>(this.rankups.keySet())) {
            if (!stored.isPresent()) {
                this.rankups.remove(stored);
                this.sync();
            }
        }
    }
    
    public Stored<User> toStored() {
        return new Stored<User>(this.getKey(), () -> this.storage);
    }
    
    public void _justDestroy() {
        this.destroy();
    }
}
