

package me.TechsCode.EnderPermissions.base.visual;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import me.TechsCode.EnderPermissions.tpl.utils.MinecraftVersion;
import java.awt.Color;

public class HexColor
{
    private String colorCode;
    
    public static HexColor from(final String s) {
        return new HexColor(s);
    }
    
    public static HexColor from(final int r, final int g, final int b) {
        return from(new Color(r, g, b));
    }
    
    public static HexColor from(final Color color) {
        return from(Integer.toHexString(color.getRGB()).substring(2));
    }
    
    private HexColor(final String s) {
        this.colorCode = s.replace("#", "");
    }
    
    public String getTag() {
        return "{#" + this.colorCode + "}";
    }
    
    public String getColorCode() {
        return this.colorCode;
    }
    
    public String getAppliedColor() {
        if (MinecraftVersion.getServersVersion().isAboveOrEqual(MinecraftVersion.V1_16_R1)) {
            return "§x" + Arrays.stream(this.colorCode.split("")).map(str -> "§" + str).collect((Collector<? super Object, ?, String>)Collectors.joining());
        }
        return this.getClosestDefault().getAppliedChatColor();
    }
    
    public String getColorTag() {
        return "{#" + this.colorCode + "}";
    }
    
    public Color getColor() {
        return new Color(Integer.parseInt(this.colorCode, 16));
    }
    
    public MinecraftColor getClosestDefault() {
        final Color color = this.getColor();
        MinecraftColor minecraftColor = null;
        int n = 0;
        for (final MinecraftColor minecraftColor2 : MinecraftColor.values()) {
            final int difference = difference(color, minecraftColor2.getHexColor().getColor());
            if (minecraftColor == null || n > difference) {
                n = difference;
                minecraftColor = minecraftColor2;
            }
        }
        return minecraftColor;
    }
    
    private static int difference(final Color color, final Color color2) {
        return Math.abs(color.getRed() - color2.getRed()) + Math.abs(color.getGreen() - color2.getGreen()) + Math.abs(color.getBlue() - color2.getBlue());
    }
    
    @Override
    public String toString() {
        return this.getAppliedColor();
    }
}
