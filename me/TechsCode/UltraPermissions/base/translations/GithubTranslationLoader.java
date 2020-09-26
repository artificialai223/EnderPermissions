

package me.TechsCode.EnderPermissions.base.translations;

import java.util.Collection;
import java.util.Iterator;
import java.net.URISyntaxException;
import java.io.IOException;
import me.TechsCode.EnderPermissions.dependencies.commons.io.FileUtils;
import java.net.URL;
import java.io.File;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.dependencies.commons.io.IOUtils;
import java.net.URI;
import com.google.gson.JsonArray;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import com.google.gson.JsonParser;

public class GithubTranslationLoader
{
    private static final JsonParser parser;
    
    public static void loadAll(final SpigotTechPlugin spigotTechPlugin, final String str, final String str2, final String str3) {
        final String str4;
        final Iterator<JsonElement> iterator;
        JsonObject jsonObject;
        final String str5;
        final Collection collection;
        final File file2;
        final String spec;
        spigotTechPlugin.getScheduler().runAsync(() -> {
            spigotTechPlugin.log("Loading Translations from the Web...");
            spigotTechPlugin.getTranslationManager().getTemplateNames();
            new StringBuilder().append("https://api.github.com/repos/").append(str).append("/").append(str2).append("/contents/").append(str3).toString();
            try {
                ((JsonArray)GithubTranslationLoader.parser.parse(IOUtils.toString(new URI(str4)))).iterator();
                while (iterator.hasNext()) {
                    jsonObject = (JsonObject)iterator.next();
                    jsonObject.get("name").getAsString();
                    if (str5.endsWith(".lang")) {
                        if (!str5.contains("_")) {
                            continue;
                        }
                        else {
                            jsonObject.get("download_url").getAsString();
                            if (collection.contains(str5.split("_")[0])) {
                                new File(spigotTechPlugin.getTranslationManager().getFolder().getAbsolutePath() + "/" + str5);
                                file2.delete();
                                FileUtils.copyURLToFile(new URL(spec), file2);
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
            }
            catch (IOException | URISyntaxException ex) {
                spigotTechPlugin.log("Could not load translations from github");
            }
        });
    }
    
    static {
        parser = new JsonParser();
    }
}
