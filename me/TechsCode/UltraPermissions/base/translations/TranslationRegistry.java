

package me.TechsCode.EnderPermissions.base.translations;

import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.base.registry.RegistryStorable;

public class TranslationRegistry extends RegistryStorable
{
    private String language;
    
    public TranslationRegistry(final String language) {
        super("translation");
        this.language = language;
    }
    
    @Override
    public void setState(final JsonObject jsonObject) {
        this.language = jsonObject.get("language").getAsString();
    }
    
    @Override
    public JsonObject getState() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("language", this.language);
        return jsonObject;
    }
    
    public String getLanguage() {
        return this.language;
    }
    
    public void setLanguage(final String language) {
        this.language = language;
        this.sync();
    }
}
