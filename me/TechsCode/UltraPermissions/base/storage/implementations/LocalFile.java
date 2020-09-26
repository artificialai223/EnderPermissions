

package me.TechsCode.EnderPermissions.base.storage.implementations;

import java.util.Iterator;
import com.google.gson.JsonParser;
import java.util.HashMap;
import me.TechsCode.EnderPermissions.base.storage.ReadCallback;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.Base64;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.base.storage.WriteCallback;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.base.fileconf.FileConfiguration;
import java.io.File;
import me.TechsCode.EnderPermissions.base.storage.StorageImplementation;

public class LocalFile extends StorageImplementation
{
    private File file;
    private FileConfiguration configuration;
    
    public LocalFile(final TechPlugin techPlugin, final String str) {
        super(techPlugin, str, false);
        this.file = new File(techPlugin.getPluginFolder().getAbsolutePath() + "/" + str + ".json");
        this.configuration = new FileConfiguration(techPlugin, this.file);
    }
    
    @Override
    public void destroy(final String s, final WriteCallback writeCallback) {
        synchronized (this.configuration) {
            this.configuration.set(s, null);
            this.configuration.save();
            writeCallback.onSuccess();
        }
    }
    
    @Override
    public void create(final String s, final JsonObject jsonObject, final WriteCallback writeCallback) {
        this.update(s, jsonObject, writeCallback);
    }
    
    @Override
    public void update(final String s, final JsonObject jsonObject, final WriteCallback writeCallback) {
        synchronized (this.configuration) {
            this.configuration.set(s, Base64.encodeBase64String(jsonObject.toString().getBytes(StandardCharsets.UTF_8)));
            this.configuration.save();
            writeCallback.onSuccess();
        }
    }
    
    @Override
    public void read(final String s, final ReadCallback readCallback) {
        synchronized (this.configuration) {
            final HashMap<String, JsonObject> hashMap = new HashMap<String, JsonObject>();
            final JsonParser jsonParser = new JsonParser();
            for (final String key : this.configuration.getKeys(false)) {
                String string = this.configuration.getString(key);
                if (Base64.isBase64(string)) {
                    string = new String(Base64.decodeBase64(string), StandardCharsets.UTF_8);
                }
                hashMap.put(key, (JsonObject)jsonParser.parse(string));
            }
            readCallback.onSuccess(hashMap);
        }
    }
}
