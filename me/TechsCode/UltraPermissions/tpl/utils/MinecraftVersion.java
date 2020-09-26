

package me.TechsCode.EnderPermissions.tpl.utils;

import org.bukkit.Bukkit;
import java.io.File;

public enum MinecraftVersion
{
    UNKNOWN, 
    V1_8_R1, 
    V1_8_R2, 
    V1_8_R3, 
    V1_9_R1, 
    V1_9_R2, 
    V1_10_R1, 
    V1_11_R1, 
    V1_12_R1, 
    V1_13_R1, 
    V1_13_R2, 
    V1_14_R1, 
    V1_15_R1, 
    V1_16_R1, 
    V1_16_R2;
    
    private static MinecraftVersion currentVersion;
    
    public boolean isAboveOrEqual(final MinecraftVersion minecraftVersion) {
        return this.ordinal() >= minecraftVersion.ordinal();
    }
    
    public static MinecraftVersion getServersVersion() {
        return MinecraftVersion.currentVersion;
    }
    
    static {
        try {
            if (new File("server.properties").exists()) {
                MinecraftVersion.currentVersion = valueOf(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].toUpperCase());
            }
            else {
                MinecraftVersion.currentVersion = MinecraftVersion.UNKNOWN;
            }
        }
        catch (Exception ex) {
            MinecraftVersion.currentVersion = MinecraftVersion.UNKNOWN;
        }
    }
}
