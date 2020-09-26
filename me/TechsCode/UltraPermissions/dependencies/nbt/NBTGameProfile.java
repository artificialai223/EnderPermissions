

package me.TechsCode.EnderPermissions.dependencies.nbt;

import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ObjectCreator;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ReflectionMethod;
import com.mojang.authlib.GameProfile;

public class NBTGameProfile
{
    public static NBTCompound toNBT(final GameProfile gameProfile) {
        return new NBTContainer(ReflectionMethod.GAMEPROFILE_SERIALIZE.run(null, ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]), gameProfile));
    }
    
    public static GameProfile fromNBT(final NBTCompound nbtCompound) {
        return (GameProfile)ReflectionMethod.GAMEPROFILE_DESERIALIZE.run(null, NBTReflectionUtil.gettoCompount(nbtCompound.getCompound(), nbtCompound));
    }
}
