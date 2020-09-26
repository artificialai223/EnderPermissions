

package me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings;

import java.io.OutputStream;
import java.io.InputStream;
import org.bukkit.inventory.ItemStack;
import java.util.UUID;
import me.TechsCode.EnderPermissions.dependencies.nbt.NbtApiException;
import java.lang.reflect.Method;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.MinecraftVersion;

public enum ReflectionMethod
{
    COMPOUND_SET_FLOAT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class, Float.TYPE }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "setFloat") }), 
    COMPOUND_SET_STRING(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class, String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "setString") }), 
    COMPOUND_SET_INT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class, Integer.TYPE }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "setInt") }), 
    COMPOUND_SET_BYTEARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class, byte[].class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "setByteArray") }), 
    COMPOUND_SET_INTARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class, int[].class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "setIntArray") }), 
    COMPOUND_SET_LONG(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class, Long.TYPE }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "setLong") }), 
    COMPOUND_SET_SHORT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class, Short.TYPE }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "setShort") }), 
    COMPOUND_SET_BYTE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class, Byte.TYPE }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "setByte") }), 
    COMPOUND_SET_DOUBLE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class, Double.TYPE }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "setDouble") }), 
    COMPOUND_SET_BOOLEAN(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class, Boolean.TYPE }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "setBoolean") }), 
    COMPOUND_SET_UUID(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class, UUID.class }, MinecraftVersion.MC1_16_R1, new Since[] { new Since(MinecraftVersion.MC1_16_R1, "a") }), 
    COMPOUND_MERGE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() }, MinecraftVersion.MC1_8_R3, new Since[] { new Since(MinecraftVersion.MC1_8_R3, "a") }), 
    COMPOUND_SET(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class, ClassWrapper.NMS_NBTBASE.getClazz() }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "set") }), 
    COMPOUND_GET(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "get") }), 
    COMPOUND_GET_LIST(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class, Integer.TYPE }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getList") }), 
    COMPOUND_OWN_TYPE(ClassWrapper.NMS_NBTBASE.getClazz(), (Class<?>[])new Class[0], MinecraftVersion.MC1_7_R4, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getTypeId") }), 
    COMPOUND_GET_FLOAT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getFloat") }), 
    COMPOUND_GET_STRING(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getString") }), 
    COMPOUND_GET_INT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getInt") }), 
    COMPOUND_GET_BYTEARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getByteArray") }), 
    COMPOUND_GET_INTARRAY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getIntArray") }), 
    COMPOUND_GET_LONG(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getLong") }), 
    COMPOUND_GET_SHORT(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getShort") }), 
    COMPOUND_GET_BYTE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getByte") }), 
    COMPOUND_GET_DOUBLE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getDouble") }), 
    COMPOUND_GET_BOOLEAN(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getBoolean") }), 
    COMPOUND_GET_UUID(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_16_R1, new Since[] { new Since(MinecraftVersion.MC1_16_R1, "a") }), 
    COMPOUND_GET_COMPOUND(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getCompound") }), 
    NMSITEM_GETTAG(ClassWrapper.NMS_ITEMSTACK.getClazz(), (Class<?>[])new Class[0], MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getTag") }), 
    NMSITEM_SAVE(ClassWrapper.NMS_ITEMSTACK.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "save") }), 
    NMSITEM_CREATESTACK(ClassWrapper.NMS_ITEMSTACK.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() }, MinecraftVersion.MC1_7_R4, MinecraftVersion.MC1_10_R1, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "createStack") }), 
    COMPOUND_REMOVE_KEY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "remove") }), 
    COMPOUND_HAS_KEY(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "hasKey") }), 
    COMPOUND_GET_TYPE(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_8_R3, new Since[] { new Since(MinecraftVersion.MC1_8_R3, "b"), new Since(MinecraftVersion.MC1_9_R1, "d"), new Since(MinecraftVersion.MC1_15_R1, "e"), new Since(MinecraftVersion.MC1_16_R1, "d") }), 
    COMPOUND_GET_KEYS(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[0], MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "c"), new Since(MinecraftVersion.MC1_13_R1, "getKeys") }), 
    LISTCOMPOUND_GET_KEYS(ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), (Class<?>[])new Class[0], MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "c"), new Since(MinecraftVersion.MC1_13_R1, "getKeys") }), 
    LIST_REMOVE_KEY(ClassWrapper.NMS_NBTTAGLIST.getClazz(), (Class<?>[])new Class[] { Integer.TYPE }, MinecraftVersion.MC1_8_R3, new Since[] { new Since(MinecraftVersion.MC1_8_R3, "a"), new Since(MinecraftVersion.MC1_9_R1, "remove") }), 
    LIST_SIZE(ClassWrapper.NMS_NBTTAGLIST.getClazz(), (Class<?>[])new Class[0], MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "size") }), 
    LIST_SET(ClassWrapper.NMS_NBTTAGLIST.getClazz(), (Class<?>[])new Class[] { Integer.TYPE, ClassWrapper.NMS_NBTBASE.getClazz() }, MinecraftVersion.MC1_8_R3, new Since[] { new Since(MinecraftVersion.MC1_8_R3, "a"), new Since(MinecraftVersion.MC1_13_R1, "set") }), 
    LEGACY_LIST_ADD(ClassWrapper.NMS_NBTTAGLIST.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_NBTBASE.getClazz() }, MinecraftVersion.MC1_7_R4, MinecraftVersion.MC1_13_R2, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "add") }), 
    LIST_ADD(ClassWrapper.NMS_NBTTAGLIST.getClazz(), (Class<?>[])new Class[] { Integer.TYPE, ClassWrapper.NMS_NBTBASE.getClazz() }, MinecraftVersion.MC1_14_R1, new Since[] { new Since(MinecraftVersion.MC1_14_R1, "add") }), 
    LIST_GET_STRING(ClassWrapper.NMS_NBTTAGLIST.getClazz(), (Class<?>[])new Class[] { Integer.TYPE }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getString") }), 
    LIST_GET_COMPOUND(ClassWrapper.NMS_NBTTAGLIST.getClazz(), (Class<?>[])new Class[] { Integer.TYPE }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "get") }), 
    LIST_GET(ClassWrapper.NMS_NBTTAGLIST.getClazz(), (Class<?>[])new Class[] { Integer.TYPE }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "get"), new Since(MinecraftVersion.MC1_8_R3, "g"), new Since(MinecraftVersion.MC1_9_R1, "h"), new Since(MinecraftVersion.MC1_12_R1, "i"), new Since(MinecraftVersion.MC1_13_R1, "get") }), 
    ITEMSTACK_SET_TAG(ClassWrapper.NMS_ITEMSTACK.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "setTag") }), 
    ITEMSTACK_NMSCOPY(ClassWrapper.CRAFT_ITEMSTACK.getClazz(), (Class<?>[])new Class[] { ItemStack.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "asNMSCopy") }), 
    ITEMSTACK_BUKKITMIRROR(ClassWrapper.CRAFT_ITEMSTACK.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_ITEMSTACK.getClazz() }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "asCraftMirror") }), 
    CRAFT_WORLD_GET_HANDLE(ClassWrapper.CRAFT_WORLD.getClazz(), (Class<?>[])new Class[0], MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getHandle") }), 
    NMS_WORLD_GET_TILEENTITY(ClassWrapper.NMS_WORLDSERVER.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_BLOCKPOSITION.getClazz() }, MinecraftVersion.MC1_8_R3, new Since[] { new Since(MinecraftVersion.MC1_8_R3, "getTileEntity") }), 
    NMS_WORLD_SET_TILEENTITY(ClassWrapper.NMS_WORLDSERVER.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_BLOCKPOSITION.getClazz(), ClassWrapper.NMS_TILEENTITY.getClazz() }, MinecraftVersion.MC1_8_R3, new Since[] { new Since(MinecraftVersion.MC1_8_R3, "setTileEntity") }), 
    NMS_WORLD_REMOVE_TILEENTITY(ClassWrapper.NMS_WORLDSERVER.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_BLOCKPOSITION.getClazz() }, MinecraftVersion.MC1_8_R3, new Since[] { new Since(MinecraftVersion.MC1_8_R3, "t"), new Since(MinecraftVersion.MC1_9_R1, "s"), new Since(MinecraftVersion.MC1_13_R1, "n"), new Since(MinecraftVersion.MC1_14_R1, "removeTileEntity") }), 
    NMS_WORLD_GET_TILEENTITY_1_7_10(ClassWrapper.NMS_WORLDSERVER.getClazz(), (Class<?>[])new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE }, MinecraftVersion.MC1_7_R4, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getTileEntity") }), 
    TILEENTITY_LOAD_LEGACY191(ClassWrapper.NMS_TILEENTITY.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_MINECRAFTSERVER.getClazz(), ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() }, MinecraftVersion.MC1_9_R1, MinecraftVersion.MC1_9_R1, new Since[] { new Since(MinecraftVersion.MC1_9_R1, "a") }), 
    TILEENTITY_LOAD_LEGACY183(ClassWrapper.NMS_TILEENTITY.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() }, MinecraftVersion.MC1_8_R3, MinecraftVersion.MC1_9_R2, new Since[] { new Since(MinecraftVersion.MC1_8_R3, "c"), new Since(MinecraftVersion.MC1_9_R1, "a"), new Since(MinecraftVersion.MC1_9_R2, "c") }), 
    TILEENTITY_LOAD_LEGACY1121(ClassWrapper.NMS_TILEENTITY.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_WORLD.getClazz(), ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() }, MinecraftVersion.MC1_10_R1, MinecraftVersion.MC1_12_R1, new Since[] { new Since(MinecraftVersion.MC1_10_R1, "a"), new Since(MinecraftVersion.MC1_12_R1, "create") }), 
    TILEENTITY_LOAD_LEGACY1151(ClassWrapper.NMS_TILEENTITY.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() }, MinecraftVersion.MC1_13_R1, MinecraftVersion.MC1_15_R1, new Since[] { new Since(MinecraftVersion.MC1_12_R1, "create") }), 
    TILEENTITY_LOAD(ClassWrapper.NMS_TILEENTITY.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_IBLOCKDATA.getClazz(), ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() }, MinecraftVersion.MC1_16_R1, new Since[] { new Since(MinecraftVersion.MC1_16_R1, "create") }), 
    TILEENTITY_GET_NBT(ClassWrapper.NMS_TILEENTITY.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "b"), new Since(MinecraftVersion.MC1_9_R1, "save") }), 
    TILEENTITY_SET_NBT_LEGACY1151(ClassWrapper.NMS_TILEENTITY.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() }, MinecraftVersion.MC1_7_R4, MinecraftVersion.MC1_15_R1, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "a"), new Since(MinecraftVersion.MC1_12_R1, "load") }), 
    TILEENTITY_SET_NBT(ClassWrapper.NMS_TILEENTITY.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_IBLOCKDATA.getClazz(), ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() }, MinecraftVersion.MC1_16_R1, new Since[] { new Since(MinecraftVersion.MC1_16_R1, "load") }), 
    TILEENTITY_GET_BLOCKDATA(ClassWrapper.NMS_TILEENTITY.getClazz(), (Class<?>[])new Class[0], MinecraftVersion.MC1_16_R1, new Since[] { new Since(MinecraftVersion.MC1_16_R1, "getBlock") }), 
    CRAFT_ENTITY_GET_HANDLE(ClassWrapper.CRAFT_ENTITY.getClazz(), (Class<?>[])new Class[0], MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "getHandle") }), 
    NMS_ENTITY_SET_NBT(ClassWrapper.NMS_ENTITY.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "f"), new Since(MinecraftVersion.MC1_16_R1, "load") }), 
    NMS_ENTITY_GET_NBT(ClassWrapper.NMS_ENTITY.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "e"), new Since(MinecraftVersion.MC1_12_R1, "save") }), 
    NMS_ENTITY_GETSAVEID(ClassWrapper.NMS_ENTITY.getClazz(), (Class<?>[])new Class[0], MinecraftVersion.MC1_14_R1, new Since[] { new Since(MinecraftVersion.MC1_14_R1, "getSaveID") }), 
    NBTFILE_READ(ClassWrapper.NMS_NBTCOMPRESSEDSTREAMTOOLS.getClazz(), (Class<?>[])new Class[] { InputStream.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "a") }), 
    NBTFILE_WRITE(ClassWrapper.NMS_NBTCOMPRESSEDSTREAMTOOLS.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), OutputStream.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "a") }), 
    PARSE_NBT(ClassWrapper.NMS_MOJANGSONPARSER.getClazz(), (Class<?>[])new Class[] { String.class }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "parse") }), 
    REGISTRY_KEYSET(ClassWrapper.NMS_REGISTRYSIMPLE.getClazz(), (Class<?>[])new Class[0], MinecraftVersion.MC1_11_R1, MinecraftVersion.MC1_13_R1, new Since[] { new Since(MinecraftVersion.MC1_11_R1, "keySet") }), 
    REGISTRY_GET(ClassWrapper.NMS_REGISTRYSIMPLE.getClazz(), (Class<?>[])new Class[] { Object.class }, MinecraftVersion.MC1_11_R1, MinecraftVersion.MC1_13_R1, new Since[] { new Since(MinecraftVersion.MC1_11_R1, "get") }), 
    REGISTRY_SET(ClassWrapper.NMS_REGISTRYSIMPLE.getClazz(), (Class<?>[])new Class[] { Object.class, Object.class }, MinecraftVersion.MC1_11_R1, MinecraftVersion.MC1_13_R1, new Since[] { new Since(MinecraftVersion.MC1_11_R1, "a") }), 
    REGISTRY_GET_INVERSE(ClassWrapper.NMS_REGISTRYMATERIALS.getClazz(), (Class<?>[])new Class[] { Object.class }, MinecraftVersion.MC1_11_R1, MinecraftVersion.MC1_13_R1, new Since[] { new Since(MinecraftVersion.MC1_11_R1, "b") }), 
    REGISTRYMATERIALS_KEYSET(ClassWrapper.NMS_REGISTRYMATERIALS.getClazz(), (Class<?>[])new Class[0], MinecraftVersion.MC1_13_R1, new Since[] { new Since(MinecraftVersion.MC1_13_R1, "keySet") }), 
    REGISTRYMATERIALS_GET(ClassWrapper.NMS_REGISTRYMATERIALS.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_MINECRAFTKEY.getClazz() }, MinecraftVersion.MC1_13_R1, new Since[] { new Since(MinecraftVersion.MC1_13_R1, "get") }), 
    REGISTRYMATERIALS_GETKEY(ClassWrapper.NMS_REGISTRYMATERIALS.getClazz(), (Class<?>[])new Class[] { Object.class }, MinecraftVersion.MC1_13_R2, new Since[] { new Since(MinecraftVersion.MC1_13_R2, "getKey") }), 
    GAMEPROFILE_DESERIALIZE(ClassWrapper.NMS_GAMEPROFILESERIALIZER.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz() }, MinecraftVersion.MC1_7_R4, new Since[] { new Since(MinecraftVersion.MC1_7_R4, "deserialize") }), 
    GAMEPROFILE_SERIALIZE(ClassWrapper.NMS_GAMEPROFILESERIALIZER.getClazz(), (Class<?>[])new Class[] { ClassWrapper.NMS_NBTTAGCOMPOUND.getClazz(), ClassWrapper.GAMEPROFILE.getClazz() }, MinecraftVersion.MC1_8_R3, new Since[] { new Since(MinecraftVersion.MC1_8_R3, "serialize") });
    
    private MinecraftVersion removedAfter;
    private Since targetVersion;
    private Method method;
    private boolean loaded;
    private boolean compatible;
    private String methodName;
    
    private ReflectionMethod(final Class<?> clazz, final Class<?>[] parameterTypes, final MinecraftVersion o, final MinecraftVersion removedAfter, final Since[] array) {
        this.loaded = false;
        this.compatible = false;
        this.methodName = null;
        this.removedAfter = removedAfter;
        final MinecraftVersion version = MinecraftVersion.getVersion();
        if (version.compareTo(o) < 0 || (this.removedAfter != null && version.getVersionId() > this.removedAfter.getVersionId())) {
            return;
        }
        this.compatible = true;
        Since targetVersion = array[0];
        for (final Since since : array) {
            if (since.version.getVersionId() <= version.getVersionId() && targetVersion.version.getVersionId() < since.version.getVersionId()) {
                targetVersion = since;
            }
        }
        this.targetVersion = targetVersion;
        try {
            (this.method = clazz.getMethod(this.targetVersion.name, parameterTypes)).setAccessible(true);
            this.loaded = true;
            this.methodName = this.targetVersion.name;
        }
        catch (NullPointerException | NoSuchMethodException | SecurityException ex) {
            System.out.println("[NBTAPI] Unable to find the method '" + this.targetVersion.name + "' in '" + ((clazz == null) ? "null" : clazz.getSimpleName()) + "' Enum: " + this);
        }
    }
    
    private ReflectionMethod(final Class<?> clazz, final Class<?>[] array, final MinecraftVersion minecraftVersion, final Since[] array2) {
        this(clazz, array, minecraftVersion, (MinecraftVersion)null, array2);
    }
    
    public Object run(final Object obj, final Object... args) {
        if (this.method == null) {
            throw new NbtApiException("Method not loaded! '" + this + "'");
        }
        try {
            return this.method.invoke(obj, args);
        }
        catch (Exception ex) {
            throw new NbtApiException("Error while calling the method '" + this.methodName + "', loaded: " + this.loaded + ", Enum: " + this, ex);
        }
    }
    
    public String getMethodName() {
        return this.methodName;
    }
    
    public boolean isLoaded() {
        return this.loaded;
    }
    
    public boolean isCompatible() {
        return this.compatible;
    }
    
    protected static class Since
    {
        public final MinecraftVersion version;
        public final String name;
        
        public Since(final MinecraftVersion version, final String name) {
            this.version = version;
            this.name = name;
        }
    }
}
