

package me.TechsCode.EnderPermissions.internal;

import org.bukkit.Server;
import org.bukkit.Bukkit;
import java.lang.reflect.Field;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.entity.Player;

public class PermissibleInjector
{
    private static final String CRAFTBUKKIT_PREFIX = "org.bukkit.craftbukkit";
    private static final String VERSION;
    protected static final String className;
    protected static final String fieldName;
    
    public static String getCBClassName(final String str) {
        if (PermissibleInjector.VERSION == null) {
            return null;
        }
        return "org.bukkit.craftbukkit" + PermissibleInjector.VERSION + str;
    }
    
    public static void inject(final Player obj, final PermissibleBase value) {
        Field permissibleField = null;
        try {
            permissibleField = getPermissibleField(obj);
        }
        catch (Exception ex) {}
        if (permissibleField == null) {
            return;
        }
        permissibleField.set(obj, value);
    }
    
    private static Field getPermissibleField(final Player player) {
        Class<?> forName;
        try {
            forName = Class.forName(PermissibleInjector.className);
        }
        catch (ClassNotFoundException ex) {
            throw new Exception("Invalid Server Jar");
        }
        if (!forName.isAssignableFrom(player.getClass())) {
            System.out.println("§cFailed to inject permissions for §e" + player.getName());
            return null;
        }
        final Field declaredField = forName.getDeclaredField(PermissibleInjector.fieldName);
        declaredField.setAccessible(true);
        return declaredField;
    }
    
    static {
        final Class<? extends Server> class1 = Bukkit.getServer().getClass();
        if (!class1.getSimpleName().equals("CraftServer")) {
            VERSION = null;
        }
        else if (class1.getName().equals("org.bukkit.craftbukkit.CraftServer")) {
            VERSION = ".";
        }
        else {
            final String substring = class1.getName().substring("org.bukkit.craftbukkit".length());
            VERSION = substring.substring(0, substring.length() - "CraftServer".length());
        }
        className = getCBClassName("entity.CraftHumanEntity");
        fieldName = "perm";
    }
}
