

package me.TechsCode.EnderPermissions.dependencies.nbt;

import java.io.OutputStream;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.MinecraftVersion;
import java.util.Set;
import java.util.UUID;
import org.bukkit.inventory.ItemStack;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ReflectionMethod;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class NBTCompound
{
    private final ReadWriteLock readWriteLock;
    private final Lock readLock;
    private final Lock writeLock;
    private String compundName;
    private NBTCompound parent;
    
    protected NBTCompound(final NBTCompound parent, final String compundName) {
        this.readWriteLock = new ReentrantReadWriteLock();
        this.readLock = this.readWriteLock.readLock();
        this.writeLock = this.readWriteLock.writeLock();
        this.compundName = compundName;
        this.parent = parent;
    }
    
    protected Lock getReadLock() {
        return this.readLock;
    }
    
    protected Lock getWriteLock() {
        return this.writeLock;
    }
    
    protected void saveCompound() {
        if (this.parent != null) {
            this.parent.saveCompound();
        }
    }
    
    public String getName() {
        return this.compundName;
    }
    
    public Object getCompound() {
        return this.parent.getCompound();
    }
    
    protected void setCompound(final Object compound) {
        this.parent.setCompound(compound);
    }
    
    public NBTCompound getParent() {
        return this.parent;
    }
    
    public void mergeCompound(final NBTCompound nbtCompound) {
        try {
            this.writeLock.lock();
            NBTReflectionUtil.mergeOtherNBTCompound(this, nbtCompound);
            this.saveCompound();
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public void setString(final String s, final String s2) {
        try {
            this.writeLock.lock();
            NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_STRING, s, s2);
            this.saveCompound();
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public String getString(final String s) {
        try {
            this.readLock.lock();
            return (String)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_STRING, s);
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    protected String getContent(final String s) {
        return NBTReflectionUtil.getContent(this, s);
    }
    
    public void setInteger(final String s, final Integer n) {
        try {
            this.writeLock.lock();
            NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_INT, s, n);
            this.saveCompound();
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public Integer getInteger(final String s) {
        try {
            this.readLock.lock();
            return (Integer)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_INT, s);
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public void setDouble(final String s, final Double n) {
        try {
            this.writeLock.lock();
            NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_DOUBLE, s, n);
            this.saveCompound();
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public Double getDouble(final String s) {
        try {
            this.readLock.lock();
            return (Double)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_DOUBLE, s);
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public void setByte(final String s, final Byte b) {
        try {
            this.writeLock.lock();
            NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_BYTE, s, b);
            this.saveCompound();
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public Byte getByte(final String s) {
        try {
            this.readLock.lock();
            return (Byte)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_BYTE, s);
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public void setShort(final String s, final Short n) {
        try {
            this.writeLock.lock();
            NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_SHORT, s, n);
            this.saveCompound();
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public Short getShort(final String s) {
        try {
            this.readLock.lock();
            return (Short)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_SHORT, s);
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public void setLong(final String s, final Long n) {
        try {
            this.writeLock.lock();
            NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_LONG, s, n);
            this.saveCompound();
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public Long getLong(final String s) {
        try {
            this.readLock.lock();
            return (Long)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_LONG, s);
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public void setFloat(final String s, final Float n) {
        try {
            this.writeLock.lock();
            NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_FLOAT, s, n);
            this.saveCompound();
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public Float getFloat(final String s) {
        try {
            this.readLock.lock();
            return (Float)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_FLOAT, s);
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public void setByteArray(final String s, final byte[] array) {
        try {
            this.writeLock.lock();
            NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_BYTEARRAY, s, array);
            this.saveCompound();
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public byte[] getByteArray(final String s) {
        try {
            this.readLock.lock();
            return (byte[])NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_BYTEARRAY, s);
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public void setIntArray(final String s, final int[] array) {
        try {
            this.writeLock.lock();
            NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_INTARRAY, s, array);
            this.saveCompound();
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public int[] getIntArray(final String s) {
        try {
            this.readLock.lock();
            return (int[])NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_INTARRAY, s);
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public void setBoolean(final String s, final Boolean b) {
        try {
            this.writeLock.lock();
            NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_BOOLEAN, s, b);
            this.saveCompound();
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    protected void set(final String s, final Object o) {
        NBTReflectionUtil.set(this, s, o);
        this.saveCompound();
    }
    
    public Boolean getBoolean(final String s) {
        try {
            this.readLock.lock();
            return (Boolean)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_BOOLEAN, s);
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public void setObject(final String s, final Object o) {
        try {
            this.writeLock.lock();
            NBTReflectionUtil.setObject(this, s, o);
            this.saveCompound();
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public <T> T getObject(final String s, final Class<T> clazz) {
        try {
            this.readLock.lock();
            return NBTReflectionUtil.getObject(this, s, clazz);
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public void setItemStack(final String s, final ItemStack itemStack) {
        try {
            this.writeLock.lock();
            this.removeKey(s);
            this.addCompound(s).mergeCompound(NBTItem.convertItemtoNBT(itemStack));
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public ItemStack getItemStack(final String s) {
        try {
            this.readLock.lock();
            return NBTItem.convertNBTtoItem(this.getCompound(s));
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public void setUUID(final String s, final UUID uuid) {
        try {
            this.writeLock.lock();
            NBTReflectionUtil.setData(this, ReflectionMethod.COMPOUND_SET_UUID, s, uuid);
            this.saveCompound();
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public UUID getUUID(final String s) {
        try {
            this.readLock.lock();
            return (UUID)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_UUID, s);
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public Boolean hasKey(final String s) {
        try {
            this.readLock.lock();
            final Boolean b = (Boolean)NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_HAS_KEY, s);
            if (b == null) {
                return false;
            }
            return b;
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public void removeKey(final String s) {
        try {
            this.writeLock.lock();
            NBTReflectionUtil.remove(this, s);
            this.saveCompound();
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public Set<String> getKeys() {
        try {
            this.readLock.lock();
            return NBTReflectionUtil.getKeys(this);
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public NBTCompound addCompound(final String s) {
        try {
            this.writeLock.lock();
            if (this.getType(s) == NBTType.NBTTagCompound) {
                return this.getCompound(s);
            }
            NBTReflectionUtil.addNBTTagCompound(this, s);
            final NBTCompound compound = this.getCompound(s);
            if (compound == null) {
                throw new NbtApiException("Error while adding Compound, got null!");
            }
            this.saveCompound();
            return compound;
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public NBTCompound getCompound(final String s) {
        try {
            this.readLock.lock();
            if (this.getType(s) != NBTType.NBTTagCompound) {
                return null;
            }
            final NBTCompound nbtCompound = new NBTCompound(this, s);
            if (NBTReflectionUtil.valideCompound(nbtCompound)) {
                return nbtCompound;
            }
            return null;
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public NBTList<String> getStringList(final String s) {
        try {
            this.writeLock.lock();
            final NBTList<String> list = NBTReflectionUtil.getList(this, s, NBTType.NBTTagString, String.class);
            this.saveCompound();
            return list;
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public NBTList<Integer> getIntegerList(final String s) {
        try {
            this.writeLock.lock();
            final NBTList<Integer> list = NBTReflectionUtil.getList(this, s, NBTType.NBTTagInt, Integer.class);
            this.saveCompound();
            return list;
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public NBTList<Float> getFloatList(final String s) {
        try {
            this.writeLock.lock();
            final NBTList<Float> list = NBTReflectionUtil.getList(this, s, NBTType.NBTTagFloat, Float.class);
            this.saveCompound();
            return list;
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public NBTList<Double> getDoubleList(final String s) {
        try {
            this.writeLock.lock();
            final NBTList<Double> list = NBTReflectionUtil.getList(this, s, NBTType.NBTTagDouble, Double.class);
            this.saveCompound();
            return list;
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public NBTList<Long> getLongList(final String s) {
        try {
            this.writeLock.lock();
            final NBTList<Long> list = NBTReflectionUtil.getList(this, s, NBTType.NBTTagLong, Long.class);
            this.saveCompound();
            return list;
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public NBTCompoundList getCompoundList(final String s) {
        try {
            this.writeLock.lock();
            final NBTCompoundList list = (NBTCompoundList)NBTReflectionUtil.getList(this, s, NBTType.NBTTagCompound, NBTListCompound.class);
            this.saveCompound();
            return list;
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    public NBTType getType(final String s) {
        try {
            this.readLock.lock();
            if (MinecraftVersion.getVersion() == MinecraftVersion.MC1_7_R4) {
                final Object data = NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET, s);
                if (data == null) {
                    return null;
                }
                return NBTType.valueOf((byte)ReflectionMethod.COMPOUND_OWN_TYPE.run(data, new Object[0]));
            }
            else {
                final Object data2 = NBTReflectionUtil.getData(this, ReflectionMethod.COMPOUND_GET_TYPE, s);
                if (data2 == null) {
                    return null;
                }
                return NBTType.valueOf((byte)data2);
            }
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    public void writeCompound(final OutputStream outputStream) {
        try {
            this.writeLock.lock();
            NBTReflectionUtil.writeApiNBT(this, outputStream);
        }
        finally {
            this.writeLock.unlock();
        }
    }
    
    @Override
    public String toString() {
        return this.asNBTString();
    }
    
    @Deprecated
    public String toString(final String s) {
        return this.asNBTString();
    }
    
    @Deprecated
    public String asNBTString() {
        try {
            this.readLock.lock();
            final Object gettoCompount = NBTReflectionUtil.gettoCompount(this.getCompound(), this);
            if (gettoCompount == null) {
                return "{}";
            }
            return gettoCompount.toString();
        }
        finally {
            this.readLock.unlock();
        }
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.toString().equals(o.toString()));
    }
}
