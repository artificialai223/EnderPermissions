

package me.TechsCode.EnderPermissions.tpl.titleAndActionbar;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.ChatMessageType;
import me.TechsCode.EnderPermissions.base.visual.Text;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;

public class ActionBar
{
    private static SpigotTechPlugin plugin;
    private static String nmsver;
    private static boolean useOldMethods;
    
    public ActionBar(final SpigotTechPlugin plugin) {
        ActionBar.plugin = plugin;
        if (ActionBar.nmsver == null) {
            ActionBar.nmsver = Bukkit.getServer().getClass().getPackage().getName();
            ActionBar.nmsver = ActionBar.nmsver.substring(ActionBar.nmsver.lastIndexOf(".") + 1);
            if (ActionBar.nmsver.equalsIgnoreCase("v1_8_R1") || ActionBar.nmsver.startsWith("v1_7_")) {
                ActionBar.useOldMethods = true;
            }
        }
    }
    
    public void sendActionBar(final Player player, final String s) {
        this.sendActionBarNoColorReplacement(player, Text.color(s));
    }
    
    public void sendActionBarNoColorReplacement(final Player obj, final String str) {
        try {
            obj.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(str));
        }
        catch (NoSuchMethodError noSuchMethodError) {
            try {
                Class.forName("net.minecraft.server." + ActionBar.nmsver + ".IChatBaseComponent");
            }
            catch (ClassNotFoundException ex) {
                return;
            }
            try {
                final Class<?> forName = Class.forName("org.bukkit.craftbukkit." + ActionBar.nmsver + ".entity.CraftPlayer");
                final Object cast = forName.cast(obj);
                final Class<?> forName2 = Class.forName("net.minecraft.server." + ActionBar.nmsver + ".PacketPlayOutChat");
                final Class<?> forName3 = Class.forName("net.minecraft.server." + ActionBar.nmsver + ".Packet");
                Object o;
                if (ActionBar.useOldMethods) {
                    final Class<?> forName4 = Class.forName("net.minecraft.server." + ActionBar.nmsver + ".ChatSerializer");
                    final Class<?> forName5 = Class.forName("net.minecraft.server." + ActionBar.nmsver + ".IChatBaseComponent");
                    o = forName2.getConstructor(forName5, Byte.TYPE).newInstance(forName5.cast(forName4.getDeclaredMethod("a", String.class).invoke(forName4, "{\"text\": \"" + str + "\"}")), 2);
                }
                else {
                    final Class<?> forName6 = Class.forName("net.minecraft.server." + ActionBar.nmsver + ".ChatComponentText");
                    final Class<?> forName7 = Class.forName("net.minecraft.server." + ActionBar.nmsver + ".IChatBaseComponent");
                    try {
                        final Class<?> forName8 = Class.forName("net.minecraft.server." + ActionBar.nmsver + ".ChatMessageType");
                        final Object[] array = (Object[])forName8.getEnumConstants();
                        Object o2 = null;
                        for (final Object o3 : array) {
                            if (o3.toString().equals("GAME_INFO")) {
                                o2 = o3;
                            }
                        }
                        o = forName2.getConstructor(forName7, forName8).newInstance(forName6.getConstructor(String.class).newInstance(str), o2);
                    }
                    catch (ClassNotFoundException ex2) {
                        o = forName2.getConstructor(forName7, Byte.TYPE).newInstance(forName6.getConstructor(String.class).newInstance(str), 2);
                    }
                }
                final Object invoke = forName.getDeclaredMethod("getHandle", (Class<?>[])new Class[0]).invoke(cast, new Object[0]);
                final Object value = invoke.getClass().getDeclaredField("playerConnection").get(invoke);
                value.getClass().getDeclaredMethod("sendPacket", forName3).invoke(value, o);
            }
            catch (Exception ex3) {
                noSuchMethodError.printStackTrace();
            }
        }
    }
    
    public void sendActionBar(final Player player, final String s, int i) {
        this.sendActionBar(player, s);
        if (i >= 0) {
            new BukkitRunnable() {
                public void run() {
                    ActionBar.this.sendActionBar(player, "");
                }
            }.runTaskLater((Plugin)((TechPlugin<Plugin>)ActionBar.plugin).getBootstrap(), (long)(i + 1));
        }
        while (i > 40) {
            i -= 40;
            new BukkitRunnable() {
                public void run() {
                    ActionBar.this.sendActionBar(player, s);
                }
            }.runTaskLater((Plugin)((TechPlugin<Plugin>)ActionBar.plugin).getBootstrap(), (long)i);
        }
    }
    
    public void sendActionBarToAllPlayers(final String s) {
        this.sendActionBarToAllPlayers(s, -1);
    }
    
    public void sendActionBarToAllPlayers(final String s, final int n) {
        final Iterator<Player> iterator = Bukkit.getOnlinePlayers().iterator();
        while (iterator.hasNext()) {
            this.sendActionBar(iterator.next(), s, n);
        }
    }
}
