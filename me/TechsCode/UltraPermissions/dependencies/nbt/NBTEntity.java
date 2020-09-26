

package me.TechsCode.EnderPermissions.dependencies.nbt;

import java.lang.invoke.SerializedLambda;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.MinecraftVersion;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.annotations.AvaliableSince;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.annotations.FAUtil;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.annotations.CheckUtil;
import org.bukkit.entity.Entity;

public class NBTEntity extends NBTCompound
{
    private final Entity ent;
    
    public NBTEntity(final Entity ent) {
        super(null, null);
        if (ent == null) {
            throw new NullPointerException("Entity can't be null!");
        }
        this.ent = ent;
    }
    
    @Override
    public Object getCompound() {
        return NBTReflectionUtil.getEntityNBTTagCompound(NBTReflectionUtil.getNMSEntity(this.ent));
    }
    
    @Override
    protected void setCompound(final Object o) {
        NBTReflectionUtil.setEntityNBTTag(o, NBTReflectionUtil.getNMSEntity(this.ent));
    }
    
    @AvaliableSince(version = MinecraftVersion.MC1_14_R1)
    public NBTCompound getPersistentDataContainer() {
        FAUtil.check(this::getPersistentDataContainer, CheckUtil::isAvaliable);
        if (this.hasKey("BukkitValues")) {
            return this.getCompound("BukkitValues");
        }
        final NBTContainer nbtContainer = new NBTContainer();
        nbtContainer.addCompound("BukkitValues").setString("__nbtapi", "Marker to make the PersistentDataContainer have content");
        this.mergeCompound(nbtContainer);
        return this.getCompound("BukkitValues");
    }
}
