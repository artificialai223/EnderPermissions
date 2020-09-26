

package me.TechsCode.EnderPermissions.dependencies.nbt;

import java.util.Set;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.GsonWrapper;
import java.util.ArrayDeque;
import org.bukkit.block.BlockState;
import java.util.Map;
import org.bukkit.inventory.meta.ItemMeta;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.MinecraftVersion;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ObjectCreator;
import java.io.OutputStream;
import java.io.InputStream;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ClassWrapper;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ReflectionMethod;
import org.bukkit.entity.Entity;
import java.lang.reflect.Field;

public class NBTReflectionUtil
{
    private static Field field_unhandledTags;
    
    private NBTReflectionUtil() {
    }
    
    public static Object getNMSEntity(final Entity obj) {
        try {
            return ReflectionMethod.CRAFT_ENTITY_GET_HANDLE.run(ClassWrapper.CRAFT_ENTITY.getClazz().cast(obj), new Object[0]);
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while getting the NMS Entity from a Bukkit Entity!", ex);
        }
    }
    
    public static Object readNBT(final InputStream inputStream) {
        try {
            return ReflectionMethod.NBTFILE_READ.run(null, inputStream);
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while reading a NBT File!", ex);
        }
    }
    
    public static Object writeNBT(final Object o, final OutputStream outputStream) {
        try {
            return ReflectionMethod.NBTFILE_WRITE.run(null, o, outputStream);
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while writing NBT!", ex);
        }
    }
    
    public static void writeApiNBT(final NBTCompound nbtCompound, final OutputStream outputStream) {
        try {
            Object o = nbtCompound.getCompound();
            if (o == null) {
                o = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
            }
            if (!valideCompound(nbtCompound)) {
                return;
            }
            ReflectionMethod.NBTFILE_WRITE.run(null, gettoCompount(o, nbtCompound), outputStream);
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while writing NBT!", ex);
        }
    }
    
    public static Object getItemRootNBTTagCompound(final Object o) {
        try {
            final Object run = ReflectionMethod.NMSITEM_GETTAG.run(o, new Object[0]);
            return (run != null) ? run : ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while getting an Itemstack's NBTCompound!", ex);
        }
    }
    
    public static Object convertNBTCompoundtoNMSItem(final NBTCompound nbtCompound) {
        try {
            final Object gettoCompount = gettoCompount(nbtCompound.getCompound(), nbtCompound);
            if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_11_R1.getVersionId()) {
                return ObjectCreator.NMS_COMPOUNDFROMITEM.getInstance(gettoCompount);
            }
            return ReflectionMethod.NMSITEM_CREATESTACK.run(null, gettoCompount);
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while converting NBTCompound to NMS ItemStack!", ex);
        }
    }
    
    public static NBTContainer convertNMSItemtoNBTCompound(final Object o) {
        try {
            return new NBTContainer(ReflectionMethod.NMSITEM_SAVE.run(o, ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0])));
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while converting NMS ItemStack to NBTCompound!", ex);
        }
    }
    
    public static Map<String, Object> getUnhandledNBTTags(final ItemMeta obj) {
        try {
            return (Map<String, Object>)NBTReflectionUtil.field_unhandledTags.get(obj);
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while getting unhandled tags from ItemMeta!", ex);
        }
    }
    
    public static Object getEntityNBTTagCompound(final Object o) {
        try {
            final Object instance = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
            Object run = ReflectionMethod.NMS_ENTITY_GET_NBT.run(o, instance);
            if (run == null) {
                run = instance;
            }
            return run;
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while getting NBTCompound from NMS Entity!", ex);
        }
    }
    
    public static Object setEntityNBTTag(final Object o, final Object o2) {
        try {
            ReflectionMethod.NMS_ENTITY_SET_NBT.run(o2, o);
            return o2;
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while setting the NBTCompound of an Entity", ex);
        }
    }
    
    public static Object getTileEntityNBTTagCompound(final BlockState blockState) {
        try {
            final Object run = ReflectionMethod.CRAFT_WORLD_GET_HANDLE.run(ClassWrapper.CRAFT_WORLD.getClazz().cast(blockState.getWorld()), new Object[0]);
            Object o;
            if (MinecraftVersion.getVersion() == MinecraftVersion.MC1_7_R4) {
                o = ReflectionMethod.NMS_WORLD_GET_TILEENTITY_1_7_10.run(run, blockState.getX(), blockState.getY(), blockState.getZ());
            }
            else {
                o = ReflectionMethod.NMS_WORLD_GET_TILEENTITY.run(run, ObjectCreator.NMS_BLOCKPOSITION.getInstance(blockState.getX(), blockState.getY(), blockState.getZ()));
            }
            final Object instance = ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance();
            Object run2 = ReflectionMethod.TILEENTITY_GET_NBT.run(o, instance);
            if (run2 == null) {
                run2 = instance;
            }
            return run2;
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while getting NBTCompound from TileEntity!", ex);
        }
    }
    
    public static void setTileEntityNBTTagCompound(final BlockState blockState, final Object o) {
        try {
            final Object run = ReflectionMethod.CRAFT_WORLD_GET_HANDLE.run(ClassWrapper.CRAFT_WORLD.getClazz().cast(blockState.getWorld()), new Object[0]);
            Object o2;
            if (MinecraftVersion.getVersion() == MinecraftVersion.MC1_7_R4) {
                o2 = ReflectionMethod.NMS_WORLD_GET_TILEENTITY_1_7_10.run(run, blockState.getX(), blockState.getY(), blockState.getZ());
            }
            else {
                o2 = ReflectionMethod.NMS_WORLD_GET_TILEENTITY.run(run, ObjectCreator.NMS_BLOCKPOSITION.getInstance(blockState.getX(), blockState.getY(), blockState.getZ()));
            }
            if (MinecraftVersion.getVersion().getVersionId() >= MinecraftVersion.MC1_16_R1.getVersionId()) {
                ReflectionMethod.TILEENTITY_SET_NBT.run(o2, ReflectionMethod.TILEENTITY_GET_BLOCKDATA.run(o2, new Object[0]), o);
            }
            else {
                ReflectionMethod.TILEENTITY_SET_NBT_LEGACY1151.run(o2, o);
            }
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while setting NBTData for a TileEntity!", ex);
        }
    }
    
    public static Object getSubNBTTagCompound(final Object obj, final String str) {
        try {
            if (ReflectionMethod.COMPOUND_HAS_KEY.run(obj, str)) {
                return ReflectionMethod.COMPOUND_GET_COMPOUND.run(obj, str);
            }
            throw new NbtApiException("Tried getting invalide compound '" + str + "' from '" + obj + "'!");
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while getting NBT subcompounds!", ex);
        }
    }
    
    public static void addNBTTagCompound(final NBTCompound nbtCompound, final String s) {
        if (s == null) {
            remove(nbtCompound, s);
            return;
        }
        Object compound = nbtCompound.getCompound();
        if (compound == null) {
            compound = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!valideCompound(nbtCompound)) {
            return;
        }
        final Object gettoCompount = gettoCompount(compound, nbtCompound);
        try {
            ReflectionMethod.COMPOUND_SET.run(gettoCompount, s, ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz().newInstance());
            nbtCompound.setCompound(compound);
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while adding a Compound!", ex);
        }
    }
    
    public static Boolean valideCompound(final NBTCompound nbtCompound) {
        Object o = nbtCompound.getCompound();
        if (o == null) {
            o = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        return gettoCompount(o, nbtCompound) != null;
    }
    
    protected static Object gettoCompount(Object subNBTTagCompound, NBTCompound parent) {
        final ArrayDeque<String> arrayDeque = new ArrayDeque<String>();
        while (parent.getParent() != null) {
            arrayDeque.add(parent.getName());
            parent = parent.getParent();
        }
        while (!arrayDeque.isEmpty()) {
            final String str = arrayDeque.pollLast();
            subNBTTagCompound = getSubNBTTagCompound(subNBTTagCompound, str);
            if (subNBTTagCompound == null) {
                throw new NbtApiException("Unable to find tag '" + str + "' in " + subNBTTagCompound);
            }
        }
        return subNBTTagCompound;
    }
    
    public static void mergeOtherNBTCompound(final NBTCompound nbtCompound, final NBTCompound nbtCompound2) {
        Object compound = nbtCompound.getCompound();
        if (compound == null) {
            compound = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!valideCompound(nbtCompound)) {
            throw new NbtApiException("The Compound wasn't able to be linked back to the root!");
        }
        final Object gettoCompount = gettoCompount(compound, nbtCompound);
        Object o = nbtCompound2.getCompound();
        if (o == null) {
            o = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!valideCompound(nbtCompound2)) {
            throw new NbtApiException("The Compound wasn't able to be linked back to the root!");
        }
        final Object gettoCompount2 = gettoCompount(o, nbtCompound2);
        try {
            ReflectionMethod.COMPOUND_MERGE.run(gettoCompount, gettoCompount2);
            nbtCompound.setCompound(compound);
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while merging two NBTCompounds!", ex);
        }
    }
    
    public static String getContent(final NBTCompound nbtCompound, final String str) {
        Object o = nbtCompound.getCompound();
        if (o == null) {
            o = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!valideCompound(nbtCompound)) {
            throw new NbtApiException("The Compound wasn't able to be linked back to the root!");
        }
        final Object gettoCompount = gettoCompount(o, nbtCompound);
        try {
            return ReflectionMethod.COMPOUND_GET.run(gettoCompount, str).toString();
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while getting the Content for key '" + str + "'!", ex);
        }
    }
    
    public static void set(final NBTCompound nbtCompound, final String str, final Object obj) {
        if (obj == null) {
            remove(nbtCompound, str);
            return;
        }
        Object compound = nbtCompound.getCompound();
        if (compound == null) {
            compound = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!valideCompound(nbtCompound)) {
            throw new NbtApiException("The Compound wasn't able to be linked back to the root!");
        }
        final Object gettoCompount = gettoCompount(compound, nbtCompound);
        try {
            ReflectionMethod.COMPOUND_SET.run(gettoCompount, str, obj);
            nbtCompound.setCompound(compound);
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while setting key '" + str + "' to '" + obj + "'!", ex);
        }
    }
    
    public static <T> NBTList<T> getList(final NBTCompound nbtCompound, final String s, final NBTType obj, final Class<T> clazz) {
        Object o = nbtCompound.getCompound();
        if (o == null) {
            o = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!valideCompound(nbtCompound)) {
            return null;
        }
        final Object gettoCompount = gettoCompount(o, nbtCompound);
        try {
            final Object run = ReflectionMethod.COMPOUND_GET_LIST.run(gettoCompount, s, obj.getId());
            if (clazz == String.class) {
                return (NBTList<T>)new NBTStringList(nbtCompound, s, obj, run);
            }
            if (clazz == NBTListCompound.class) {
                return (NBTList<T>)new NBTCompoundList(nbtCompound, s, obj, run);
            }
            if (clazz == Integer.class) {
                return (NBTList<T>)new NBTIntegerList(nbtCompound, s, obj, run);
            }
            if (clazz == Float.class) {
                return (NBTList<T>)new NBTFloatList(nbtCompound, s, obj, run);
            }
            if (clazz == Double.class) {
                return (NBTList<T>)new NBTDoubleList(nbtCompound, s, obj, run);
            }
            if (clazz == Long.class) {
                return (NBTList<T>)new NBTLongList(nbtCompound, s, obj, run);
            }
            return null;
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while getting a list with the type '" + obj + "'!", ex);
        }
    }
    
    public static void setObject(final NBTCompound nbtCompound, final String s, final Object obj) {
        if (!MinecraftVersion.hasGsonSupport()) {
            return;
        }
        try {
            setData(nbtCompound, ReflectionMethod.COMPOUND_SET_STRING, s, GsonWrapper.getString(obj));
        }
        catch (Exception ex) {
            throw new NbtApiException("Exception while setting the Object '" + obj + "'!", ex);
        }
    }
    
    public static <T> T getObject(final NBTCompound nbtCompound, final String s, final Class<T> clazz) {
        if (!MinecraftVersion.hasGsonSupport()) {
            return null;
        }
        final String s2 = (String)getData(nbtCompound, ReflectionMethod.COMPOUND_GET_STRING, s);
        if (s2 == null) {
            return null;
        }
        return GsonWrapper.deserializeJson(s2, clazz);
    }
    
    public static void remove(final NBTCompound nbtCompound, final String s) {
        Object compound = nbtCompound.getCompound();
        if (compound == null) {
            compound = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!valideCompound(nbtCompound)) {
            return;
        }
        ReflectionMethod.COMPOUND_REMOVE_KEY.run(gettoCompount(compound, nbtCompound), s);
        nbtCompound.setCompound(compound);
    }
    
    public static Set<String> getKeys(final NBTCompound nbtCompound) {
        Object o = nbtCompound.getCompound();
        if (o == null) {
            o = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!valideCompound(nbtCompound)) {
            throw new NbtApiException("The Compound wasn't able to be linked back to the root!");
        }
        return (Set<String>)ReflectionMethod.COMPOUND_GET_KEYS.run(gettoCompount(o, nbtCompound), new Object[0]);
    }
    
    public static void setData(final NBTCompound nbtCompound, final ReflectionMethod reflectionMethod, final String s, final Object o) {
        if (o == null) {
            remove(nbtCompound, s);
            return;
        }
        Object compound = nbtCompound.getCompound();
        if (compound == null) {
            compound = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
        }
        if (!valideCompound(nbtCompound)) {
            throw new NbtApiException("The Compound wasn't able to be linked back to the root!");
        }
        reflectionMethod.run(gettoCompount(compound, nbtCompound), s, o);
        nbtCompound.setCompound(compound);
    }
    
    public static Object getData(final NBTCompound nbtCompound, final ReflectionMethod reflectionMethod, final String s) {
        final Object compound = nbtCompound.getCompound();
        if (compound == null) {
            return null;
        }
        if (!valideCompound(nbtCompound)) {
            throw new NbtApiException("The Compound wasn't able to be linked back to the root!");
        }
        return reflectionMethod.run(gettoCompount(compound, nbtCompound), s);
    }
    
    static {
        NBTReflectionUtil.field_unhandledTags = null;
        try {
            (NBTReflectionUtil.field_unhandledTags = ClassWrapper.CRAFT_METAITEM.getClazz().getDeclaredField("unhandledTags")).setAccessible(true);
        }
        catch (NoSuchFieldException ex) {}
    }
}
