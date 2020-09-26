

package me.TechsCode.EnderPermissions.dependencies.nbt;

import java.lang.invoke.SerializedLambda;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.annotations.AvaliableSince;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.annotations.FAUtil;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.annotations.CheckUtil;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.MinecraftVersion;
import org.bukkit.block.BlockState;

public class NBTTileEntity extends NBTCompound
{
    private final BlockState tile;
    
    public NBTTileEntity(final BlockState tile) {
        super(null, null);
        if (tile == null || (MinecraftVersion.isAtLeastVersion(MinecraftVersion.MC1_8_R3) && !tile.isPlaced())) {
            throw new NullPointerException("Tile can't be null/not placed!");
        }
        this.tile = tile;
    }
    
    @Override
    public Object getCompound() {
        return NBTReflectionUtil.getTileEntityNBTTagCompound(this.tile);
    }
    
    @Override
    protected void setCompound(final Object o) {
        NBTReflectionUtil.setTileEntityNBTTagCompound(this.tile, o);
    }
    
    @AvaliableSince(version = MinecraftVersion.MC1_14_R1)
    public NBTCompound getPersistentDataContainer() {
        FAUtil.check(this::getPersistentDataContainer, CheckUtil::isAvaliable);
        if (this.hasKey("PublicBukkitValues")) {
            return this.getCompound("PublicBukkitValues");
        }
        final NBTContainer nbtContainer = new NBTContainer();
        nbtContainer.addCompound("PublicBukkitValues").setString("__nbtapi", "Marker to make the PersistentDataContainer have content");
        this.mergeCompound(nbtContainer);
        return this.getCompound("PublicBukkitValues");
    }
}
