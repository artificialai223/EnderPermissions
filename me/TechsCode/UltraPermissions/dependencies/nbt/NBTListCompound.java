

package me.TechsCode.EnderPermissions.dependencies.nbt;

public class NBTListCompound extends NBTCompound
{
    private NBTList<?> owner;
    private Object compound;
    
    protected NBTListCompound(final NBTList<?> owner, final Object compound) {
        super(null, null);
        this.owner = owner;
        this.compound = compound;
    }
    
    public NBTList<?> getListParent() {
        return this.owner;
    }
    
    @Override
    public Object getCompound() {
        return this.compound;
    }
    
    @Override
    protected void setCompound(final Object compound) {
        this.compound = compound;
    }
    
    @Override
    protected void saveCompound() {
        this.owner.save();
    }
}
