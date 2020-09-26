

package me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.MinecraftVersion;

public enum ClassWrapper
{
    CRAFT_ITEMSTACK(PackageWrapper.CRAFTBUKKIT, "inventory.CraftItemStack"), 
    CRAFT_METAITEM(PackageWrapper.CRAFTBUKKIT, "inventory.CraftMetaItem"), 
    CRAFT_ENTITY(PackageWrapper.CRAFTBUKKIT, "entity.CraftEntity"), 
    CRAFT_WORLD(PackageWrapper.CRAFTBUKKIT, "CraftWorld"), 
    NMS_NBTBASE(PackageWrapper.NMS, "NBTBase"), 
    NMS_NBTTAGSTRING(PackageWrapper.NMS, "NBTTagString"), 
    NMS_NBTTAGINT(PackageWrapper.NMS, "NBTTagInt"), 
    NMS_NBTTAGFLOAT(PackageWrapper.NMS, "NBTTagFloat"), 
    NMS_NBTTAGDOUBLE(PackageWrapper.NMS, "NBTTagDouble"), 
    NMS_NBTTAGLONG(PackageWrapper.NMS, "NBTTagLong"), 
    NMS_ITEMSTACK(PackageWrapper.NMS, "ItemStack"), 
    NMS_NBTTAGCOMPOUND(PackageWrapper.NMS, "NBTTagCompound"), 
    NMS_NBTTAGLIST(PackageWrapper.NMS, "NBTTagList"), 
    NMS_NBTCOMPRESSEDSTREAMTOOLS(PackageWrapper.NMS, "NBTCompressedStreamTools"), 
    NMS_MOJANGSONPARSER(PackageWrapper.NMS, "MojangsonParser"), 
    NMS_TILEENTITY(PackageWrapper.NMS, "TileEntity"), 
    NMS_BLOCKPOSITION(PackageWrapper.NMS, "BlockPosition", MinecraftVersion.MC1_8_R3, (MinecraftVersion)null), 
    NMS_WORLDSERVER(PackageWrapper.NMS, "WorldServer"), 
    NMS_MINECRAFTSERVER(PackageWrapper.NMS, "MinecraftServer"), 
    NMS_WORLD(PackageWrapper.NMS, "World"), 
    NMS_ENTITY(PackageWrapper.NMS, "Entity"), 
    NMS_ENTITYTYPES(PackageWrapper.NMS, "EntityTypes"), 
    NMS_REGISTRYSIMPLE(PackageWrapper.NMS, "RegistrySimple", MinecraftVersion.MC1_11_R1, MinecraftVersion.MC1_12_R1), 
    NMS_REGISTRYMATERIALS(PackageWrapper.NMS, "RegistryMaterials"), 
    NMS_IREGISTRY(PackageWrapper.NMS, "IRegistry"), 
    NMS_MINECRAFTKEY(PackageWrapper.NMS, "MinecraftKey", MinecraftVersion.MC1_8_R3, (MinecraftVersion)null), 
    NMS_GAMEPROFILESERIALIZER(PackageWrapper.NMS, "GameProfileSerializer"), 
    NMS_IBLOCKDATA(PackageWrapper.NMS, "IBlockData", MinecraftVersion.MC1_8_R3, (MinecraftVersion)null), 
    GAMEPROFILE("com.mojang.authlib.GameProfile", MinecraftVersion.MC1_8_R3);
    
    private Class<?> clazz;
    private boolean enabled;
    
    private ClassWrapper(final PackageWrapper packageWrapper, final String s2) {
        this(packageWrapper, s2, null, null);
    }
    
    private ClassWrapper(final PackageWrapper packageWrapper, final String s, final MinecraftVersion minecraftVersion, final MinecraftVersion minecraftVersion2) {
        this.enabled = false;
        if (minecraftVersion != null && MinecraftVersion.getVersion().getVersionId() < minecraftVersion.getVersionId()) {
            return;
        }
        if (minecraftVersion2 != null && MinecraftVersion.getVersion().getVersionId() > minecraftVersion2.getVersionId()) {
            return;
        }
        this.enabled = true;
        try {
            this.clazz = Class.forName(packageWrapper.getUri() + "." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + s);
        }
        catch (Exception thrown) {
            MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI] Error while trying to resolve the class '" + s + "'!", thrown);
        }
    }
    
    private ClassWrapper(final String s, final MinecraftVersion minecraftVersion) {
        this.enabled = false;
        if (minecraftVersion != null && MinecraftVersion.getVersion().getVersionId() < minecraftVersion.getVersionId()) {
            return;
        }
        this.enabled = true;
        try {
            this.clazz = Class.forName(s);
        }
        catch (Exception thrown) {
            MinecraftVersion.logger.log(Level.WARNING, "[NBTAPI] Error while trying to resolve the class '" + s + "'!", thrown);
        }
    }
    
    public Class<?> getClazz() {
        return this.clazz;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
}
