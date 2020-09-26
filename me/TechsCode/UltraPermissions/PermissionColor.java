

package me.TechsCode.EnderPermissions;

import java.util.List;
import me.TechsCode.EnderPermissions.gui.permissionEditor.PermissionCopy;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.visual.MinecraftColor;

public enum PermissionColor
{
    NOT_ADDED(MinecraftColor.WHITE, XMaterial.WHITE_STAINED_GLASS_PANE, XMaterial.WHITE_STAINED_GLASS), 
    ALL_SERVERS(MinecraftColor.GREEN, XMaterial.LIME_STAINED_GLASS_PANE, XMaterial.LIME_STAINED_GLASS), 
    SPECIFIC_SERVER(MinecraftColor.BLUE, XMaterial.BLUE_STAINED_GLASS_PANE, XMaterial.BLUE_STAINED_GLASS), 
    BUNGEE_SERVER(MinecraftColor.RED, XMaterial.RED_STAINED_GLASS_PANE, XMaterial.RED_STAINED_GLASS), 
    INHERITED(MinecraftColor.YELLOW, XMaterial.YELLOW_STAINED_GLASS_PANE, XMaterial.YELLOW_STAINED_GLASS);
    
    private final MinecraftColor chatColor;
    private final XMaterial glassPane;
    private final XMaterial glassBlock;
    
    private PermissionColor(final MinecraftColor chatColor, final XMaterial glassPane, final XMaterial glassBlock) {
        this.chatColor = chatColor;
        this.glassPane = glassPane;
        this.glassBlock = glassBlock;
    }
    
    public HexColor getChatColor() {
        return this.chatColor.getHexColor();
    }
    
    public XMaterial getGlassPane() {
        return this.glassPane;
    }
    
    public XMaterial getGlassBlock() {
        return this.glassBlock;
    }
    
    public static PermissionColor fromPermission(final PermissionCopy permissionCopy) {
        if (permissionCopy.isInherited()) {
            return PermissionColor.INHERITED;
        }
        if (!permissionCopy.getPermission().getServer().isPresent()) {
            return PermissionColor.ALL_SERVERS;
        }
        if (permissionCopy.getPermission().getServer().get().equals("BungeeCord")) {
            return PermissionColor.BUNGEE_SERVER;
        }
        return PermissionColor.SPECIFIC_SERVER;
    }
    
    public static PermissionColor cycle(final List<PermissionCopy> list) {
        return fromPermission(list.get((int)(System.currentTimeMillis() / 1000L % list.size())));
    }
}
