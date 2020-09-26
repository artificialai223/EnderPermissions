

package me.TechsCode.EnderPermissions.dependencies.nbt;

import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ReflectionMethod;
import java.io.InputStream;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ClassWrapper;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ObjectCreator;

public class NBTContainer extends NBTCompound
{
    private Object nbt;
    
    public NBTContainer() {
        super(null, null);
        this.nbt = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
    }
    
    public NBTContainer(final Object nbt) {
        super(null, null);
        if (nbt == null) {
            throw new NullPointerException("The NBT-Object can't be null!");
        }
        if (!ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().isAssignableFrom(nbt.getClass())) {
            throw new NbtApiException("The object '" + nbt.getClass() + "' is not a valid NBT-Object!");
        }
        this.nbt = nbt;
    }
    
    public NBTContainer(final InputStream inputStream) {
        super(null, null);
        this.nbt = NBTReflectionUtil.readNBT(inputStream);
    }
    
    public NBTContainer(final String s) {
        super(null, null);
        if (s == null) {
            throw new NullPointerException("The String can't be null!");
        }
        try {
            this.nbt = ReflectionMethod.PARSE_NBT.run(null, s);
        }
        catch (Exception ex) {
            throw new NbtApiException("Unable to parse Malformed Json!", ex);
        }
    }
    
    @Override
    public Object getCompound() {
        return this.nbt;
    }
    
    public void setCompound(final Object nbt) {
        this.nbt = nbt;
    }
}
