

package me.TechsCode.EnderPermissions.permissionDatabase;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import java.util.Map;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Iterator;
import java.io.IOException;
import me.TechsCode.EnderPermissions.dependencies.commons.io.FileUtils;
import java.net.URL;
import java.io.File;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.EnderPermissions;
import java.util.HashMap;

public class PermissionDatabase
{
    private HashMap<String, PermissionInfo> map;
    public static final String githubLink = "https://raw.githubusercontent.com/TechsCode/PluginResources/master/Permissions%20Database/Database.updb";
    
    public PermissionDatabase(final EnderPermissions EnderPermissions) {
        this.map = new HashMap<String, PermissionInfo>();
        final Plugin[] array;
        int length;
        int i = 0;
        Plugin plugin;
        final Iterator<Permission> iterator;
        Permission permission;
        final File file2;
        boolean b = false;
        final Iterator<String> iterator2;
        String s;
        final String[] array2;
        String s2;
        String s3;
        String[] split;
        String s4 = null;
        HashMap<String, PermissionInfo> map;
        final PermissionInfo value;
        final Object key;
        int n = 0;
        Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)((TechPlugin<Plugin>)EnderPermissions).getBootstrap(), () -> {
            EnderPermissions.log("Loading Permission Database...");
            Bukkit.getPluginManager().getPlugins();
            for (length = array.length; i < length; ++i) {
                plugin = array[i];
                plugin.getDescription().getPermissions().iterator();
                while (iterator.hasNext()) {
                    permission = iterator.next();
                    this.map.put(permission.getName(), new PermissionInfo(permission.getName(), plugin.getName(), permission.getDescription(), new String[0], "Extracted from .jar"));
                }
            }
            EnderPermissions.log("- " + this.map.size() + " Permissions extracted from .jars");
            new File(EnderPermissions.getPluginFolder().getAbsolutePath() + "/Main.updb");
            try {
                FileUtils.copyURLToFile(new URL("https://raw.githubusercontent.com/TechsCode/PluginResources/master/Permissions%20Database/Database.updb"), file2);
                b = true;
            }
            catch (IOException ex2) {
                EnderPermissions.log("§cFailed to load Permissions from the Cloud");
            }
            if (file2.exists()) {
                try {
                    FileUtils.readLines(file2, "UTF-8").iterator();
                    while (iterator2.hasNext()) {
                        s = iterator2.next();
                        if (!s.startsWith("#")) {
                            s.split("[+]");
                            if (array2.length < 2) {
                                EnderPermissions.log("Invalid line in " + file2.getName() + ":");
                                EnderPermissions.log(s);
                            }
                            else {
                                s2 = array2[0];
                                s3 = array2[1];
                                split = new String[0];
                                if (array2.length >= 3) {
                                    s4 = array2[2];
                                }
                                if (array2.length >= 4) {
                                    split = array2[3].split(",");
                                }
                                map = this.map;
                                new PermissionInfo(s3, s2, s4, split, "Permission Database (" + file2.getName() + ")");
                                map.put((String)key, value);
                                ++n;
                            }
                        }
                    }
                    if (b) {
                        EnderPermissions.log("- " + n + " Permissions downloaded from the Cloud");
                    }
                    else {
                        EnderPermissions.log("- " + n + " Permissions loaded from the Cache");
                    }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            else {
                EnderPermissions.log("§cConnect this Server to the internet to load the Permission Database");
            }
        }, 20L);
    }
    
    public PermissionInfo getInfo(final String s) {
        if (this.map.containsKey(s)) {
            return this.map.get(s);
        }
        for (final PermissionInfo permissionInfo : this.map.values()) {
            if (permissionInfo.isThisPermission(s)) {
                return permissionInfo;
            }
        }
        return null;
    }
    
    public List<String> getSupportedPlugins() {
        return this.map.entrySet().stream().map(entry -> entry.getValue().getPlugin()).distinct().collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    public PermissionInfoList getPermissionInfosFromPlugin(final String anotherString) {
        return this.map.values().stream().filter(permissionInfo -> permissionInfo.getPlugin().equalsIgnoreCase(anotherString)).collect((Collector<? super PermissionInfo, ?, PermissionInfoList>)Collectors.toCollection((Supplier<R>)PermissionInfoList::new));
    }
    
    public PermissionInfoList getAllPermissionInfos() {
        return new PermissionInfoList(this.map.values());
    }
}
