

package me.TechsCode.EnderPermissions.dependencies.nbt;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.NotImplementedException;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ReflectionMethod;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.MinecraftVersion;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ClassWrapper;

public class NBTCompoundList extends NBTList<NBTListCompound>
{
    protected NBTCompoundList(final NBTCompound nbtCompound, final String s, final NBTType nbtType, final Object o) {
        super(nbtCompound, s, nbtType, o);
    }
    
    public NBTListCompound addCompound() {
        return (NBTListCompound)this.addCompound(null);
    }
    
    public NBTCompound addCompound(final NBTCompound nbtCompound) {
        try {
            final Object instance = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
            if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_14_R1.getVersionId()) {
                ReflectionMethod.LIST_ADD.run(this.listObject, this.size(), instance);
            }
            else {
                ReflectionMethod.LEGACY_LIST_ADD.run(this.listObject, instance);
            }
            this.getParent().saveCompound();
            final NBTListCompound nbtListCompound = new NBTListCompound(this, instance);
            if (nbtCompound != null) {
                nbtListCompound.mergeCompound(nbtCompound);
            }
            return nbtListCompound;
        }
        catch (Exception ex) {
            throw new NbtApiException(ex);
        }
    }
    
    @Deprecated
    @Override
    public boolean add(final NBTListCompound nbtListCompound) {
        return this.addCompound(nbtListCompound) != null;
    }
    
    @Override
    public void add(final int i, final NBTListCompound nbtListCompound) {
        if (nbtListCompound != null) {
            throw new NotImplementedException("You need to pass null! ListCompounds from other lists won't work.");
        }
        try {
            final Object instance = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
            if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_14_R1.getVersionId()) {
                ReflectionMethod.LIST_ADD.run(this.listObject, i, instance);
            }
            else {
                ReflectionMethod.LEGACY_LIST_ADD.run(this.listObject, instance);
            }
            super.getParent().saveCompound();
        }
        catch (Exception ex) {
            throw new NbtApiException(ex);
        }
    }
    
    @Override
    public NBTListCompound get(final int i) {
        try {
            return new NBTListCompound(this, ReflectionMethod.LIST_GET_COMPOUND.run(this.listObject, i));
        }
        catch (Exception ex) {
            throw new NbtApiException(ex);
        }
    }
    
    @Override
    public NBTListCompound set(final int n, final NBTListCompound nbtListCompound) {
        throw new NotImplementedException("This method doesn't work in the ListCompound context.");
    }
    
    @Override
    protected Object asTag(final NBTListCompound nbtListCompound) {
        return null;
    }
}
