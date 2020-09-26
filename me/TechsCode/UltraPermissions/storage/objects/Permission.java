

package me.TechsCode.EnderPermissions.storage.objects;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.internal.Node;
import java.util.Optional;
import me.TechsCode.EnderPermissions.base.storage.Storable;

public class Permission extends Storable
{
    private int id;
    private Holder holder;
    private String world;
    private String server;
    private String permission;
    private boolean positive;
    private long expiration;
    
    public Permission(final int id, final Holder holder, final String world, final String server, final String permission, final boolean positive, final long expiration) {
        this.id = id;
        this.holder = holder;
        this.world = world;
        this.server = server;
        this.permission = permission;
        this.positive = positive;
        this.expiration = expiration;
    }
    
    public int getId() {
        return this.id;
    }
    
    public Optional<String> getServer() {
        return Optional.ofNullable(this.server);
    }
    
    public Optional<String> getWorld() {
        return Optional.ofNullable(this.world);
    }
    
    public Holder getHolder() {
        return this.holder;
    }
    
    public String getName() {
        return this.permission;
    }
    
    public void setName(final String permission) {
        this.permission = permission;
        this.sync();
    }
    
    public void setWorld(final String world) {
        this.world = world;
        this.sync();
    }
    
    public void setServer(final String server) {
        this.server = server;
        this.sync();
    }
    
    public String getDisplayName() {
        return (this.isPositive() ? "" : "-") + this.getName();
    }
    
    public boolean isPositive() {
        return this.positive;
    }
    
    public void setPositive(final boolean positive) {
        this.positive = positive;
        this.sync();
    }
    
    public long getExpiration() {
        return this.expiration;
    }
    
    public void setExpiration(final long expiration) {
        this.expiration = expiration;
        this.sync();
    }
    
    public void remove() {
        this.destroy();
    }
    
    public boolean isBungeePermission() {
        return this.server != null && this.server.equals("BungeeCord");
    }
    
    public boolean isExpired() {
        return this.expiration != 0L && this.expiration < System.currentTimeMillis();
    }
    
    public void copyTo(final PermissionHolder permissionHolder) {
        permissionHolder.newPermission(this.permission).setServer(this.server).setExpiration(this.expiration).setWorld(this.world).setPositive(this.positive).create();
    }
    
    public Node asNode() {
        return new Node(this.permission);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Permission && ((Permission)o).id == this.id;
    }
    
    public Permission createCopy() {
        return new Permission(this.id, this.holder, this.world, this.server, this.permission, this.positive, this.expiration);
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
        jsonObject.addProperty("holder", this.holder.toStored().getKey());
        if (this.world != null) {
            jsonObject.addProperty("world", this.world);
        }
        if (this.server != null) {
            jsonObject.addProperty("serverId", this.server);
        }
        jsonObject.addProperty("permission", this.permission);
        jsonObject.addProperty("positive", Boolean.valueOf(this.positive));
        jsonObject.addProperty("expiry", (Number)this.expiration);
        return jsonObject;
    }
    
    @Override
    public void setState(final JsonObject jsonObject, final TechPlugin techPlugin) {
        this.holder = Holder.fromKey(jsonObject.get("holder"), techPlugin);
        this.world = (jsonObject.has("world") ? jsonObject.get("world").getAsString() : null);
        this.server = ((jsonObject.has("serverId") && !jsonObject.get("serverId").isJsonNull()) ? jsonObject.get("serverId").getAsString() : null);
        this.permission = jsonObject.get("permission").getAsString();
        this.positive = jsonObject.get("positive").getAsBoolean();
        this.expiration = jsonObject.get("expiry").getAsLong();
    }
    
    @Override
    public void onMount(final TechPlugin techPlugin) {
    }
    
    public void _justDestroy() {
        this.destroy();
    }
}
