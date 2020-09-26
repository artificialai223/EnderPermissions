

package me.TechsCode.EnderPermissions.base.networking;

import com.google.gson.JsonObject;
import java.util.UUID;

public class NPlayer
{
    private UUID uuid;
    private String name;
    private NServer server;
    
    public NPlayer(final UUID uuid, final String name, final NServer server) {
        this.uuid = uuid;
        this.name = name;
        this.server = server;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public String getName() {
        return this.name;
    }
    
    public NServer getServer() {
        return this.server;
    }
    
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uuid", this.uuid.toString());
        jsonObject.addProperty("name", this.name);
        return jsonObject;
    }
    
    public static NPlayer fromJsonObject(final JsonObject jsonObject, final NServer nServer) {
        return new NPlayer(UUID.fromString(jsonObject.get("uuid").getAsString()), jsonObject.get("name").getAsString(), nServer);
    }
}
