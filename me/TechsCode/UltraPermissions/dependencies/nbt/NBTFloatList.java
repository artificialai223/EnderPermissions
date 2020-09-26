

package me.TechsCode.EnderPermissions.dependencies.nbt;

import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ReflectionMethod;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ClassWrapper;

public class NBTFloatList extends NBTList<Float>
{
    protected NBTFloatList(final NBTCompound nbtCompound, final String s, final NBTType nbtType, final Object o) {
        super(nbtCompound, s, nbtType, o);
    }
    
    @Override
    protected Object asTag(final Float obj) {
        try {
            final Constructor<?> declaredConstructor = ClassWrapper.NMS_NBTTAGFLOAT.getClazz().getDeclaredConstructor(Float.TYPE);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(obj);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            final Object o;
            throw new NbtApiException("Error while wrapping the Object " + obj + " to it's NMS object!", (Throwable)o);
        }
    }
    
    @Override
    public Float get(final int i) {
        try {
            return Float.valueOf(ReflectionMethod.LIST_GET.run(this.listObject, i).toString());
        }
        catch (NumberFormatException ex2) {
            return 0.0f;
        }
        catch (Exception ex) {
            throw new NbtApiException(ex);
        }
    }
}
