

package me.TechsCode.EnderPermissions.dependencies.nbt.utils.annotations;

import me.TechsCode.EnderPermissions.dependencies.nbt.NbtApiException;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.MinecraftVersion;
import java.lang.reflect.Method;

public class CheckUtil
{
    public static boolean isAvaliable(final Method method) {
        if (MinecraftVersion.getVersion().getVersionId() < method.getAnnotation(AvaliableSince.class).version().getVersionId()) {
            throw new NbtApiException("The Method '" + method.getName() + "' is only avaliable for the Versions " + method.getAnnotation(AvaliableSince.class).version() + "+, but still got called!");
        }
        return true;
    }
}
