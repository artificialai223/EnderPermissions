

package me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings;

import me.TechsCode.EnderPermissions.dependencies.nbt.NbtApiException;
import java.util.logging.Level;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.MinecraftVersion;
import java.lang.reflect.Constructor;

public enum ObjectCreator
{
    NMS_NBTTAGCOMPOUND((MinecraftVersion)null, (MinecraftVersion)null, ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[0]), 
    NMS_BLOCKPOSITION((MinecraftVersion)null, (MinecraftVersion)null, ClassWrapper.NMS_BLOCKPOSITION.getClazz(), (Class<?>[])new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE }), 
    NMS_COMPOUNDFROMITEM(MinecraftVersion.MC1_11_R1, (MinecraftVersion)null, ClassWrapper.NMS_ITEMSTACK.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() });
    
    private Constructor<?> construct;
    private Class<?> targetClass;
    
    private ObjectCreator(final MinecraftVersion minecraftVersion, final MinecraftVersion minecraftVersion2, final Class<?> targetClass, final Class<?>[] parameterTypes) {
        if (targetClass == null) {
            return;
        }
        if (minecraftVersion != null && MinecraftVersion.getVersion().getVersionId() < minecraftVersion.getVersionId()) {
            return;
        }
        if (minecraftVersion2 != null && MinecraftVersion.getVersion().getVersionId() > minecraftVersion2.getVersionId()) {
            return;
        }
        try {
            this.targetClass = targetClass;
            (this.construct = targetClass.getDeclaredConstructor(parameterTypes)).setAccessible(true);
        }
        catch (Exception thrown) {
            MinecraftVersion.logger.log(Level.SEVERE, "Unable to find the constructor for the class '" + targetClass.getName() + "'", thrown);
        }
    }
    
    public Object getInstance(final Object... initargs) {
        try {
            return this.construct.newInstance(initargs);
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while creating a new instance of '" + this.targetClass + "'", ex);
        }
    }
}
