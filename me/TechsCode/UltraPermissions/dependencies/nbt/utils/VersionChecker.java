

package me.TechsCode.EnderPermissions.dependencies.nbt.utils;

import com.google.gson.JsonElement;
import me.TechsCode.EnderPermissions.dependencies.nbt.NBTItem;
import java.util.logging.Level;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.io.Reader;
import java.io.InputStreamReader;
import com.google.gson.JsonParser;
import java.net.URL;
import java.net.HttpURLConnection;

public class VersionChecker
{
    private static final String USER_AGENT = "nbt-api Version check";
    private static final String REQUEST_URL = "https://api.spiget.org/v2/resources/7939/versions?size=100";
    
    protected static void checkForUpdates() {
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL("https://api.spiget.org/v2/resources/7939/versions?size=100").openConnection();
        httpURLConnection.addRequestProperty("User-Agent", "nbt-api Version check");
        final JsonElement parse = new JsonParser().parse((Reader)new InputStreamReader(httpURLConnection.getInputStream()));
        if (parse.isJsonArray()) {
            final JsonArray jsonArray = (JsonArray)parse;
            final JsonObject jsonObject = (JsonObject)jsonArray.get(jsonArray.size() - 1);
            final int versionDifference = getVersionDifference(jsonObject.get("name").getAsString());
            if (versionDifference == -1) {
                MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI] The NBT-API at '" + NBTItem.class.getPackage() + "' seems to be outdated!");
                MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI] Current Version: '2.5.0' Newest Version: " + jsonObject.get("name").getAsString() + "'");
                MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI] Please update the nbt-api or the plugin that contains the api!");
            }
            else if (versionDifference == 0) {
                MinecraftVersion.logger.log(Level.INFO, "[NBTAPI] The NBT-API seems to be up-to-date!");
            }
            else if (versionDifference == 1) {
                MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI] The NBT-API at '" + NBTItem.class.getPackage() + "' seems to be a future Version, not yet released on Spigot!");
                MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI] Current Version: '2.5.0' Newest Version: " + jsonObject.get("name").getAsString() + "'");
            }
        }
        else {
            MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI] Error when looking for Updates! Got non Json Array: '" + parse.toString() + "'");
        }
    }
    
    private static int getVersionDifference(final String anObject) {
        final String s = "2.5.0";
        if (s.equals(anObject)) {
            return 0;
        }
        final String s2 = "\\.";
        if (s.split(s2).length != 3 || anObject.split(s2).length != 3) {
            return -1;
        }
        final int int1 = Integer.parseInt(s.split(s2)[0]);
        final int int2 = Integer.parseInt(s.split(s2)[1]);
        final String s3 = s.split(s2)[2];
        final int int3 = Integer.parseInt(anObject.split(s2)[0]);
        final int int4 = Integer.parseInt(anObject.split(s2)[1]);
        final String s4 = anObject.split(s2)[2];
        if (int1 < int3) {
            return -1;
        }
        if (int1 > int3) {
            return 1;
        }
        if (int2 < int4) {
            return -1;
        }
        if (int2 > int4) {
            return 1;
        }
        final int int5 = Integer.parseInt(s3.split("-")[0]);
        final int int6 = Integer.parseInt(s4.split("-")[0]);
        if (int5 < int6) {
            return -1;
        }
        if (int5 > int6) {
            return 1;
        }
        if (!s4.contains("-") && s3.contains("-")) {
            return -1;
        }
        if (s4.contains("-") && s3.contains("-")) {
            return 0;
        }
        return 1;
    }
}
