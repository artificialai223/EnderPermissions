

package me.TechsCode.spigot;

import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;

public class SpigotPurchaseInfo
{
    private static String USER_ID;
    private static String USERNAME;
    
    public static Optional<String> getUserId() {
        return SpigotPurchaseInfo.USER_ID.contains("__USER__") ? Optional.empty() : Optional.of(SpigotPurchaseInfo.USER_ID);
    }
    
    public static Optional<String> getUsername() {
        if (SpigotPurchaseInfo.USERNAME != null) {
            return Optional.of(SpigotPurchaseInfo.USERNAME);
        }
        final Optional<String> userId = getUserId();
        if (userId.isPresent()) {
            SpigotPurchaseInfo.USERNAME = fetchUsername(userId.get());
            return Optional.of(SpigotPurchaseInfo.USERNAME);
        }
        return Optional.empty();
    }
    
    private static String fetchUsername(final String str) {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL("https://api.spigotmc.org/simple/0.1/index.php?action=getAuthor&id=" + str).openConnection().getInputStream()));
            final StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            final JsonObject jsonObject = (JsonObject)new JsonParser().parse(sb.toString());
            if (!jsonObject.has("username")) {
                return "Unknown";
            }
            return jsonObject.get("username").getAsString();
        }
        catch (Exception ex) {
            return "Unknown";
        }
    }
    
    static {
        SpigotPurchaseInfo.USER_ID = "9514";
        SpigotPurchaseInfo.USERNAME = null;
    }
}
