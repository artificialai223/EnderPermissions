

package me.TechsCode.EnderPermissions.dependencies.nbt.utils;

import me.TechsCode.EnderPermissions.dependencies.nbt.NbtApiException;
import com.google.gson.Gson;

public class GsonWrapper
{
    private static final Gson gson;
    
    private GsonWrapper() {
    }
    
    public static String getString(final Object o) {
        return GsonWrapper.gson.toJson(o);
    }
    
    public static <T> T deserializeJson(final String s, final Class<T> clazz) {
        try {
            if (s == null) {
                return null;
            }
            return clazz.cast(GsonWrapper.gson.fromJson(s, (Class)clazz));
        }
        catch (Exception ex) {
            throw new NbtApiException("Error while converting json to " + clazz.getName(), ex);
        }
    }
    
    static {
        gson = new Gson();
    }
}
