

package me.TechsCode.EnderPermissions.tpl.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Location;

public class LocationUtils
{
    public static Location getValidSpawnPoint(final Location location) {
        return getValidSpawnPoint(location, 1);
    }
    
    private static Location getValidSpawnPoint(final Location location, final int n) {
        for (int i = location.getBlockX() - n; i <= location.getBlockX() + n; ++i) {
            for (int j = location.getBlockY() - n; j <= location.getBlockY() + n; ++j) {
                for (int k = location.getBlockZ() - n; k <= location.getBlockZ() + n; ++k) {
                    final Location location2 = new Location(location.getWorld(), i + 0.5, (double)j, k + 0.5);
                    final Location add = location2.clone().add(0.0, 1.0, 0.0);
                    if (location2.getBlock().getType() == Material.AIR && add.getBlock().getType() == Material.AIR) {
                        return location2;
                    }
                }
            }
        }
        return getValidSpawnPoint(location, n + 1);
    }
    
    public static String serialize(final Location location) {
        return location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ();
    }
    
    public static Location deserialize(final String s) {
        final String[] split = s.split(";");
        return new Location(Bukkit.getWorld(split[0]), (double)Integer.valueOf(split[1]), (double)Integer.valueOf(split[2]), (double)Integer.valueOf(split[3]));
    }
}
