

package me.TechsCode.EnderPermissions.dependencies.nbt;

import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ReflectionMethod;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ClassWrapper;

public class NBTIntegerList extends NBTList<Integer>
{
    protected NBTIntegerList(final NBTCompound nbtCompound, final String s, final NBTType nbtType, final Object o) {
        super(nbtCompound, s, nbtType, o);
    }
    
    @Override
    protected Object asTag(final Integer obj) {
        try {
            final Constructor<?> declaredConstructor = ClassWrapper.NMS_NBTTAGINT.getClazz().getDeclaredConstructor(Integer.TYPE);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(obj);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            final Object o;
            throw new NbtApiException("Error while wrapping the Object " + obj + " to it's NMS object!", (Throwable)o);
        }
    }
    
    @Override
    public Integer get(final int i) {
        try {
            return Integer.valueOf(ReflectionMethod.LIST_GET.run(this.listObject, i).toString());
        }
        catch (NumberFormatException ex2) {
            return 0;
        }
        catch (Exception ex) {
            throw new NbtApiException(ex);
        }
    }
}
