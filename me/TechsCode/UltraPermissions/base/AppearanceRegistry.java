

package me.TechsCode.EnderPermissions.base;

import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.tpl.Tools;
import me.TechsCode.EnderPermissions.base.registry.RegistryStorable;

public class AppearanceRegistry extends RegistryStorable
{
    private String prefix;
    private final String defaultPrefix;
    
    public AppearanceRegistry(final TechPlugin<?> techPlugin) {
        super("appearance");
        this.prefix = "§9" + String.join(" ", (CharSequence[])Tools.splitCamelCase(techPlugin.getName())) + ">";
        this.defaultPrefix = this.prefix;
    }
    
    @Override
    public void setState(final JsonObject jsonObject) {
        this.prefix = jsonObject.get("prefix").getAsString();
    }
    
    @Override
    public JsonObject getState() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("prefix", this.prefix.trim());
        return jsonObject;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
        this.sync();
    }
    
    public boolean isPrefixModified() {
        return !this.prefix.equals(this.defaultPrefix);
    }
    
    public void resetPrefix() {
        this.prefix = this.defaultPrefix;
        this.sync();
    }
}
