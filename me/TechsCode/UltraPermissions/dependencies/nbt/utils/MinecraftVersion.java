

package me.TechsCode.EnderPermissions.dependencies.nbt.utils;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import java.util.logging.Logger;

public enum MinecraftVersion
{
    UNKNOWN(Integer.MAX_VALUE), 
    MC1_7_R4(174), 
    MC1_8_R3(183), 
    MC1_9_R1(191), 
    MC1_9_R2(192), 
    MC1_10_R1(1101), 
    MC1_11_R1(1111), 
    MC1_12_R1(1121), 
    MC1_13_R1(1131), 
    MC1_13_R2(1132), 
    MC1_14_R1(1141), 
    MC1_15_R1(1151), 
    MC1_16_R1(1161), 
    MC1_16_R2(1162);
    
    private static MinecraftVersion version;
    private static Boolean hasGsonSupport;
    private static boolean bStatsDisabled;
    private static boolean disablePackageWarning;
    private static boolean updateCheckDisabled;
    public static final Logger logger;
    protected static final String VERSION = "2.5.0";
    private final int versionId;
    
    private MinecraftVersion(final int versionId) {
        this.versionId = versionId;
    }
    
    public int getVersionId() {
        return this.versionId;
    }
    
    public static boolean isAtLeastVersion(final MinecraftVersion minecraftVersion) {
        return getVersion().getVersionId() >= minecraftVersion.getVersionId();
    }
    
    public static MinecraftVersion getVersion() {
        if (MinecraftVersion.version != null) {
            return MinecraftVersion.version;
        }
        final String str = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        MinecraftVersion.logger.info("[NBTAPI] Found Spigot: " + str + "! Trying to find NMS support");
        try {
            MinecraftVersion.version = valueOf(str.replace("v", "MC"));
        }
        catch (IllegalArgumentException ex) {
            MinecraftVersion.version = MinecraftVersion.UNKNOWN;
        }
        if (MinecraftVersion.version != MinecraftVersion.UNKNOWN) {
            MinecraftVersion.logger.info("[NBTAPI] NMS support '" + MinecraftVersion.version.name() + "' loaded!");
        }
        else {
            MinecraftVersion.logger.warning("[NBTAPI] Wasn't able to find NMS Support! Some functions may not work!");
        }
        init();
        return MinecraftVersion.version;
    }
    
    private static void init() {
        try {
            if (hasGsonSupport() && !MinecraftVersion.bStatsDisabled) {
                new ApiMetricsLite();
            }
        }
        catch (Exception thrown) {
            MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI] Error enabling Metrics!", thrown);
        }
        if (hasGsonSupport() && !MinecraftVersion.updateCheckDisabled) {
            new Thread(() -> {
                try {
                    VersionChecker.checkForUpdates();
                }
                catch (Exception thrown2) {
                    MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI] Error while checking for updates!", thrown2);
                }
                return;
            }).start();
        }
        final String anObject = new String(new byte[] { 100, 101, 46, 116, 114, 55, 122, 119, 46, 99, 104, 97, 110, 103, 101, 109, 101, 46, 110, 98, 116, 97, 112, 105, 46, 117, 116, 105, 108, 115 });
        if (!MinecraftVersion.disablePackageWarning && MinecraftVersion.class.getPackage().getName().equals(anObject)) {
            MinecraftVersion.logger.warning("#########################################- NBTAPI -#########################################");
            MinecraftVersion.logger.warning("The NBT-API package has not been moved! This *will* cause problems with other plugins containing");
            MinecraftVersion.logger.warning("a different version of the api! Please read the guide on the plugin page on how to get the");
            MinecraftVersion.logger.warning("Maven Shade plugin to relocate the api to your personal location! If you are not the developer,");
            MinecraftVersion.logger.warning("please check your plugins and contact their developer, so he can fix this issue.");
            MinecraftVersion.logger.warning("#########################################- NBTAPI -#########################################");
        }
    }
    
    public static boolean hasGsonSupport() {
        if (MinecraftVersion.hasGsonSupport != null) {
            return MinecraftVersion.hasGsonSupport;
        }
        try {
            MinecraftVersion.logger.info("[NBTAPI] Found Gson: " + Class.forName("com.google.gson.Gson"));
            MinecraftVersion.hasGsonSupport = true;
        }
        catch (Exception ex) {
            MinecraftVersion.logger.info("[NBTAPI] Gson not found! This will not allow the usage of some methods!");
            MinecraftVersion.hasGsonSupport = false;
        }
        return MinecraftVersion.hasGsonSupport;
    }
    
    public static void disableBStats() {
        MinecraftVersion.bStatsDisabled = true;
    }
    
    public static void disableUpdateCheck() {
        MinecraftVersion.updateCheckDisabled = true;
    }
    
    public static void disablePackageWarning() {
        MinecraftVersion.disablePackageWarning = true;
    }
    
    static {
        MinecraftVersion.bStatsDisabled = false;
        MinecraftVersion.disablePackageWarning = false;
        MinecraftVersion.updateCheckDisabled = false;
        logger = Logger.getLogger("NBTAPI");
    }
}
