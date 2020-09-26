

package me.TechsCode.EnderPermissions.base.visual;

public enum MinecraftColor
{
    DARK_RED("&4", "#AA0000"), 
    RED("&c", "#FF5555"), 
    GOLD("&6", "#FFAA00"), 
    YELLOW("&e", "#FFFF55"), 
    DARK_GREEN("&2", "#00AA00"), 
    GREEN("&a", "#55FF55"), 
    AQUA("&b", "#55FFFF"), 
    DARK_AQUA("&3", "#00AAAA"), 
    DARK_BLUE("&1", "#0000AA"), 
    BLUE("&9", "#5555FF"), 
    LIGHT_PURPLE("&d", "#FF55FF"), 
    DARK_PURPLE("&5", "#AA00AA"), 
    WHITE("&f", "#FFFFFF"), 
    GRAY("&7", "#AAAAAA"), 
    DARK_GRAY("&8", "#555555"), 
    BLACK("&0", "#000000");
    
    private final String chatColor;
    private final HexColor hexColor;
    
    private MinecraftColor(final String chatColor, final String s) {
        this.chatColor = chatColor;
        this.hexColor = HexColor.from(s);
    }
    
    public String getName() {
        return this.name().toLowerCase();
    }
    
    public HexColor getHexColor() {
        return this.hexColor;
    }
    
    public String getChatColor() {
        return this.chatColor;
    }
    
    public String getAppliedChatColor() {
        return this.chatColor.replace("&", "§");
    }
    
    @Override
    public String toString() {
        return this.getAppliedChatColor();
    }
}
