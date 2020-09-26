

package me.TechsCode.EnderPermissions.dependencies.nbt;

import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ReflectionMethod;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ClassWrapper;

public class NBTLongList extends NBTList<Long>
{
    protected NBTLongList(final NBTCompound nbtCompound, final String s, final NBTType nbtType, final Object o) {
        super(nbtCompound, s, nbtType, o);
    }
    
    @Override
    protected Object asTag(final Long obj) {
        try {
            final Constructor<?> declaredConstructor = ClassWrapper.NMS_NBTTAGLONG.getClazz().getDeclaredConstructor(Long.TYPE);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(obj);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            final Object o;
            throw new NbtApiException("Error while wrapping the Object " + obj + " to it's NMS object!", (Throwable)o);
        }
    }
    
    @Override
    public Long get(final int i) {
        try {
            return Long.valueOf(ReflectionMethod.LIST_GET.run(this.listObject, i).toString().replace("L", ""));
        }
        catch (NumberFormatException ex2) {
            return 0L;
        }
        catch (Exception ex) {
            throw new NbtApiException(ex);
        }
    }
}
