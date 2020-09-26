

package me.TechsCode.EnderPermissions.visual;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import me.TechsCode.EnderPermissions.base.registry.Switchable;
import me.TechsCode.EnderPermissions.base.registry.RegistryStorable;

public class VisualRegistry extends RegistryStorable implements Switchable
{
    private boolean switchedTo;
    private Map<VisualType, String> formats;
    private boolean editingNametags;
    
    public VisualRegistry() {
        super("visual");
        this.switchedTo = false;
        this.formats = new HashMap<VisualType, String>();
        this.editingNametags = true;
        for (final VisualType visualType : VisualType.values()) {
            this.formats.put(visualType, visualType.getDefaultFormat());
        }
    }
    
    @Override
    public void setState(final JsonObject jsonObject) {
        this.switchedTo = jsonObject.get("switchedTo").getAsBoolean();
        this.formats = new HashMap<VisualType, String>();
        final String s;
        jsonObject.getAsJsonObject("formats").entrySet().forEach(entry -> s = this.formats.put(VisualType.valueOf(entry.getKey()), ((JsonElement)entry.getValue()).getAsString()));
        this.editingNametags = (!jsonObject.has("editingNametags") || jsonObject.get("editingNametags").getAsBoolean());
    }
    
    @Override
    public JsonObject getState() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("switchedTo", Boolean.valueOf(this.switchedTo));
        final JsonObject jsonObject2 = new JsonObject();
        this.formats.forEach((visualType, s) -> jsonObject2.addProperty(visualType.name(), s));
        jsonObject.add("formats", (JsonElement)jsonObject2);
        jsonObject.addProperty("editingNametags", Boolean.valueOf(this.editingNametags));
        return jsonObject;
    }
    
    @Override
    public boolean isSwitchedTo() {
        return this.switchedTo;
    }
    
    @Override
    public void setSwitchedTo(final boolean switchedTo) {
        this.switchedTo = switchedTo;
        this.sync();
    }
    
    public void setFormat(final VisualType visualType, final String s) {
        this.formats.put(visualType, s);
        this.sync();
    }
    
    public String getFormat(final VisualType visualType) {
        final String s = this.formats.get(visualType);
        return s.equalsIgnoreCase("none") ? null : s;
    }
    
    public void resetFormat(final VisualType visualType) {
        this.setFormat(visualType, visualType.getDefaultFormat());
    }
    
    public boolean isEditingNametags() {
        return this.editingNametags;
    }
    
    public void setEditingNametags(final boolean editingNametags) {
        this.editingNametags = editingNametags;
        this.sync();
    }
}
