

package me.TechsCode.EnderPermissions.dependencies.nbt;

public enum NBTType
{
    NBTTagEnd(0), 
    NBTTagByte(1), 
    NBTTagShort(2), 
    NBTTagInt(3), 
    NBTTagLong(4), 
    NBTTagFloat(5), 
    NBTTagDouble(6), 
    NBTTagByteArray(7), 
    NBTTagIntArray(11), 
    NBTTagString(8), 
    NBTTagList(9), 
    NBTTagCompound(10);
    
    private final int id;
    
    private NBTType(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public static NBTType valueOf(final int n) {
        for (final NBTType nbtType : values()) {
            if (nbtType.getId() == n) {
                return nbtType;
            }
        }
        return NBTType.NBTTagEnd;
    }
}
