

package me.TechsCode.EnderPermissions.tpl.utils;

import me.TechsCode.EnderPermissions.tpl.titleAndActionbar.Reflection;
import java.lang.reflect.InvocationTargetException;
import com.mojang.authlib.GameProfile;
import org.bukkit.entity.Player;

public class PlayerUtils
{
    private static Class<?> CRAFTPLAYER;
    
    private static Object getCraftPlayer(final Player obj) {
        return PlayerUtils.CRAFTPLAYER.cast(obj);
    }
    
    public static GameProfile getGameProfile(final Player player) {
        final Object craftPlayer = getCraftPlayer(player);
        try {
            return (GameProfile)PlayerUtils.CRAFTPLAYER.getMethod("getProfile", (Class<?>[])new Class[0]).invoke(craftPlayer, new Object[0]);
        }
        catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        catch (InvocationTargetException ex2) {
            ex2.printStackTrace();
        }
        catch (NoSuchMethodException ex3) {
            ex3.printStackTrace();
        }
        return null;
    }
    
    static {
        PlayerUtils.CRAFTPLAYER = Reflection.getCraftClass("entity.CraftPlayer");
    }
}
