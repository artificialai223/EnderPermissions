

package me.TechsCode.EnderPermissions;

import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.base.registry.RegistryStorable;

public class PublicRegistry extends RegistryStorable
{
    private boolean defaultPermissions;
    private DefaultGroupAssignOption defaultGroupAssignOption;
    
    public PublicRegistry() {
        super("public");
        this.defaultPermissions = false;
        this.defaultGroupAssignOption = DefaultGroupAssignOption.FIRST_JOIN;
    }
    
    @Override
    public void setState(final JsonObject jsonObject) {
        this.defaultPermissions = jsonObject.get("defaultPermissions").getAsBoolean();
        this.defaultGroupAssignOption = DefaultGroupAssignOption.valueOf(jsonObject.get("defaultAssigner").getAsString());
    }
    
    @Override
    public JsonObject getState() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("defaultPermissions", Boolean.valueOf(this.defaultPermissions));
        jsonObject.addProperty("defaultAssigner", this.defaultGroupAssignOption.name());
        return jsonObject;
    }
    
    public boolean isDefaultPermissions() {
        return this.defaultPermissions;
    }
    
    public void setDefaultPermissions(final boolean defaultPermissions) {
        this.defaultPermissions = defaultPermissions;
        this.sync();
    }
    
    public DefaultGroupAssignOption getDefaultAssignOption() {
        return this.defaultGroupAssignOption;
    }
    
    public void setDefaultGroupAssignOption(final DefaultGroupAssignOption defaultGroupAssignOption) {
        this.defaultGroupAssignOption = defaultGroupAssignOption;
        this.sync();
    }
}
