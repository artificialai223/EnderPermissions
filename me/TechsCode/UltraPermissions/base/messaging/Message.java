

package me.TechsCode.EnderPermissions.base.messaging;

import com.google.gson.JsonParser;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.Base64;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Message
{
    private String key;
    private JsonObject data;
    
    public Message(final String key, final JsonObject data) {
        this.key = key;
        this.data = data;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public JsonObject getData() {
        return this.data;
    }
    
    public String encode() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("key", this.key);
        jsonObject.add("data", (JsonElement)this.data);
        return Base64.encodeBase64String(jsonObject.toString().replace("'", "\\'").getBytes(StandardCharsets.UTF_8));
    }
    
    public static Message decode(final String s) {
        final JsonObject asJsonObject = new JsonParser().parse(new String(Base64.decodeBase64(s), StandardCharsets.UTF_8)).getAsJsonObject();
        return new Message(asJsonObject.get("key").getAsString(), asJsonObject.get("data").getAsJsonObject());
    }
}
