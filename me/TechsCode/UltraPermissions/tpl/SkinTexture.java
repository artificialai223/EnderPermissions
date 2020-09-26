

package me.TechsCode.EnderPermissions.tpl;

import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.Base64;
import java.io.IOException;
import me.TechsCode.EnderPermissions.dependencies.commons.io.IOUtils;
import java.nio.charset.Charset;
import java.net.URL;
import java.util.UUID;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import java.util.NoSuchElementException;
import com.mojang.authlib.properties.Property;
import me.TechsCode.EnderPermissions.tpl.utils.PlayerUtils;
import org.bukkit.entity.Player;

public class SkinTexture
{
    private String url;
    private long createdAt;
    
    public static SkinTexture fromPlayer(final Player player) {
        final GameProfile gameProfile = PlayerUtils.getGameProfile(player);
        try {
            final String base64ToUrl = base64ToUrl(gameProfile.getProperties().get((Object)"textures").iterator().next().getValue());
            if (base64ToUrl == null || base64ToUrl.equals("null")) {
                return null;
            }
            return new SkinTexture(base64ToUrl, System.currentTimeMillis());
        }
        catch (NoSuchElementException ex) {
            return null;
        }
    }
    
    public static SkinTexture fromMojangAPI(final String str) {
        final String response = getResponse("https://api.mojang.com/users/profiles/minecraft/" + str);
        try {
            return fromMojangAPI(UUID.fromString(new StringBuffer(new JsonParser().parse(response).getAsJsonObject().get("id").getAsString()).insert(8, "-").insert(13, "-").insert(18, "-").insert(23, "-").toString()));
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static SkinTexture fromMojangAPI(final UUID uuid) {
        final String response = getResponse("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "") + "?unsigned=false");
        try {
            return new SkinTexture(base64ToUrl(new JsonParser().parse(response).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString()), System.currentTimeMillis());
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    private static String getResponse(final String spec) {
        try {
            return IOUtils.toString(new URL(spec).openStream(), Charset.forName("UTF-8"));
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    private static String base64ToUrl(String x) {
        try {
            if (!x.contains("{")) {
                x = new String(Base64.decodeBase64(x.getBytes()));
            }
            final JsonObject asJsonObject = ((JsonObject)new JsonParser().parse(x.trim())).getAsJsonObject("textures").getAsJsonObject("SKIN");
            if (asJsonObject == null) {
                return null;
            }
            return asJsonObject.get("url").getAsString();
        }
        catch (Exception ex) {
            System.out.println("Could not retrieve Url from Base64");
            System.out.println(x);
            System.out.println("Trace:");
            ex.printStackTrace();
            return null;
        }
    }
    
    public SkinTexture(final String url, final long createdAt) {
        this.url = url;
        this.createdAt = createdAt;
    }
    
    public SkinTexture(final String s) {
        this.url = s.split(":split:")[0];
        this.createdAt = Long.valueOf(s.split(":split:")[1]);
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public long getCreatedAt() {
        return this.createdAt;
    }
    
    @Override
    public String toString() {
        return this.url + ":split:" + this.createdAt;
    }
}
