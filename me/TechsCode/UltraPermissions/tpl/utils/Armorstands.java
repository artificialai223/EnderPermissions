

package me.TechsCode.EnderPermissions.tpl.utils;

import java.util.function.Supplier;
import org.bukkit.Bukkit;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import java.lang.reflect.Method;

public class Armorstands
{
    private static final Method[] methods;
    
    public static void move(final ArmorStand obj, final Location location) {
        try {
            Armorstands.methods[1].invoke(Armorstands.methods[0].invoke(obj, new Object[0]), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        }
        catch (IllegalAccessException | InvocationTargetException ex) {
            final Throwable t;
            t.printStackTrace();
        }
    }
    
    static {
        final Method method;
        methods = ((Supplier<Method[]>)(() -> {
            try {
                Class.forName(Bukkit.getServer().getClass().getPackage().getName() + ".entity.CraftEntity").getDeclaredMethod("getHandle", (Class<?>[])new Class[0]);
                return new Method[] { method, method.getReturnType().getDeclaredMethod("setPositionRotation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE) };
            }
            catch (Exception ex) {
                return null;
            }
        })).get();
    }
}
