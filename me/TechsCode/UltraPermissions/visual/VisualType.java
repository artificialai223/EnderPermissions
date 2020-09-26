

package me.TechsCode.EnderPermissions.visual;

import me.TechsCode.EnderPermissions.base.item.XMaterial;

public enum VisualType
{
    CHAT("{Prefixes} §7{Player}§b: §f{Message}", XMaterial.PAPER, 12), 
    TABLIST("{Prefix} §f{Player}", XMaterial.ITEM_FRAME, 14), 
    DISPLAY_NAME("none", XMaterial.NAME_TAG, 16);
    
    private final String defaultFormat;
    private final XMaterial material;
    private final int guiSlot;
    
    private VisualType(final String defaultFormat, final XMaterial material, final int guiSlot) {
        this.defaultFormat = defaultFormat;
        this.material = material;
        this.guiSlot = guiSlot;
    }
    
    public XMaterial getMaterial() {
        return this.material;
    }
    
    public String getDefaultFormat() {
        return this.defaultFormat;
    }
    
    public int getGuiSlot() {
        return this.guiSlot;
    }
}
