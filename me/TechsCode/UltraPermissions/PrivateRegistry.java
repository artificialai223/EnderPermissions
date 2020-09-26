

package me.TechsCode.EnderPermissions;

import com.google.gson.JsonObject;
import java.util.UUID;
import me.TechsCode.EnderPermissions.base.registry.RegistryStorable;

public class PrivateRegistry extends RegistryStorable
{
    private UUID serverIdentifier;
    
    public PrivateRegistry() {
        super("private");
        this.serverIdentifier = UUID.randomUUID();
    }
    
    @Override
    public void setState(final JsonObject jsonObject) {
        this.serverIdentifier = UUID.fromString(jsonObject.get("serverIdentifier").getAsString());
    }
    
    @Override
    public JsonObject getState() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("serverIdentifier", this.serverIdentifier.toString());
        return jsonObject;
    }
    
    public UUID getServerIdentifier() {
        return this.serverIdentifier;
    }
    
    public void setServerIdentifier(final UUID serverIdentifier) {
        this.serverIdentifier = serverIdentifier;
        this.sync();
    }
}
