

package me.TechsCode.EnderPermissions.utility;

import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;

public class ChildrenResolver
{
    private static HashMap<String, Set<String>> cache;
    private static boolean isSpigot;
    
    public static Set<String> retrieveChildPermissions(final String s) {
        if (ChildrenResolver.cache.containsKey(s)) {
            return ChildrenResolver.cache.get(s);
        }
        HashSet<String> retrieveChildPermissions;
        if (!ChildrenResolver.isSpigot) {
            retrieveChildPermissions = new HashSet<String>();
        }
        else {
            retrieveChildPermissions = BukkitChildrenResolver.getInstance().retrieveChildPermissions(s);
        }
        retrieveChildPermissions.add(s);
        ChildrenResolver.cache.put(s, retrieveChildPermissions);
        return retrieveChildPermissions;
    }
    
    static {
        ChildrenResolver.cache = new HashMap<String, Set<String>>();
        try {
            Class.forName("org.bukkit.entity.Player$Spigot");
            ChildrenResolver.isSpigot = true;
        }
        catch (ClassNotFoundException ex) {
            ChildrenResolver.isSpigot = false;
        }
    }
}
