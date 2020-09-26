

package me.TechsCode.EnderPermissions.dependencies.nbt;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ClassWrapper;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ReflectionMethod;

public class NBTStringList extends NBTList<String>
{
    protected NBTStringList(final NBTCompound nbtCompound, final String s, final NBTType nbtType, final Object o) {
        super(nbtCompound, s, nbtType, o);
    }
    
    @Override
    public String get(final int i) {
        try {
            return (String)ReflectionMethod.LIST_GET_STRING.run(this.listObject, i);
        }
        catch (Exception ex) {
            throw new NbtApiException(ex);
        }
    }
    
    @Override
    protected Object asTag(final String str) {
        try {
            final Constructor<?> declaredConstructor = ClassWrapper.NMS_NBTTAGSTRING.getClazz().getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(str);
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            final Object o;
            throw new NbtApiException("Error while wrapping the Object " + str + " to it's NMS object!", (Throwable)o);
        }
    }
}
