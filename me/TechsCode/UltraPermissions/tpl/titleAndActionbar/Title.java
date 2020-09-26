

package me.TechsCode.EnderPermissions.tpl.titleAndActionbar;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.ChatColor;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Title
{
    public static void sendPacket(final Player obj, final Object o) {
        try {
            final Object invoke = obj.getClass().getMethod("getHandle", (Class<?>[])new Class[0]).invoke(obj, new Object[0]);
            final Object value = invoke.getClass().getField("playerConnection").get(invoke);
            value.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(value, o);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Class<?> getNMSClass(final String str) {
        final String str2 = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + str2 + "." + str);
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void sendTitle(final Player player, final Integer n, final Integer n2, final Integer n3, final String s) {
        sendTitle(player, n, n2, n3, s, null);
    }
    
    public static void sendSubtitle(final Player player, final Integer n, final Integer n2, final Integer n3, final String s) {
        sendTitle(player, n, n2, n3, null, s);
    }
    
    public static void sendFullTitle(final Player player, final Integer n, final Integer n2, final Integer n3, final String s, final String s2) {
        sendTitle(player, n, n2, n3, s, s2);
    }
    
    public static void sendTitle(final Player obj, final Integer n, final Integer n2, final Integer n3, String str, String str2) {
        try {
            final Method declaredMethod = obj.getClass().getDeclaredMethod("sendTitle", String.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE);
            if (declaredMethod != null) {
                declaredMethod.invoke(obj, str, str2, n, n2, n3);
                return;
            }
        }
        catch (NoSuchMethodException ex2) {}
        catch (InvocationTargetException ex3) {}
        catch (IllegalAccessException ex4) {}
        try {
            if (str != null) {
                str = ChatColor.translateAlternateColorCodes('&', str);
                str = str.replaceAll("%player%", obj.getDisplayName());
                sendPacket(obj, getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE).newInstance(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null), getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + str + "\"}"), n, n2, n3));
                sendPacket(obj, getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent")).newInstance(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + str + "\"}")));
            }
            if (str2 != null) {
                str2 = ChatColor.translateAlternateColorCodes('&', str2);
                str2 = str2.replaceAll("%player%", obj.getDisplayName());
                sendPacket(obj, getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE).newInstance(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null), getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + str + "\"}"), n, n2, n3));
                sendPacket(obj, getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE).newInstance(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + str2 + "\"}"), n, n2, n3));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void clearTitle(final Player player) {
        sendTitle(player, 0, 1, 0, "", "");
    }
    
    public static void sendTabTitle(final Player player, String str, String str2) {
        if (str == null) {
            str = "";
        }
        str = ChatColor.translateAlternateColorCodes('&', str);
        if (str2 == null) {
            str2 = "";
        }
        str2 = ChatColor.translateAlternateColorCodes('&', str2);
        str = str.replaceAll("%player%", player.getDisplayName());
        str2 = str2.replaceAll("%player%", player.getDisplayName());
        try {
            final Object invoke = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + str + "\"}");
            final Object invoke2 = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + str2 + "\"}");
            final Object instance = getNMSClass("PacketPlayOutPlayerListHeaderFooter").getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            final Field declaredField = instance.getClass().getDeclaredField("a");
            declaredField.setAccessible(true);
            declaredField.set(instance, invoke);
            final Field declaredField2 = instance.getClass().getDeclaredField("b");
            declaredField2.setAccessible(true);
            declaredField2.set(instance, invoke2);
            sendPacket(player, instance);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
