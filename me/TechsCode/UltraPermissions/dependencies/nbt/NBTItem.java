

package me.TechsCode.EnderPermissions.dependencies.nbt;

import org.bukkit.inventory.meta.ItemMeta;
import java.util.Map;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ReflectionMethod;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NBTItem extends NBTCompound
{
    private ItemStack bukkitItem;
    private boolean directApply;
    private ItemStack originalSrcStack;
    
    public NBTItem(final ItemStack itemStack) {
        this(itemStack, false);
    }
    
    public NBTItem(final ItemStack originalSrcStack, final boolean directApply) {
        super(null, null);
        this.originalSrcStack = null;
        if (originalSrcStack == null || originalSrcStack.getType() == Material.AIR) {
            throw new NullPointerException("ItemStack can't be null/Air!");
        }
        this.directApply = directApply;
        this.bukkitItem = originalSrcStack.clone();
        if (directApply) {
            this.originalSrcStack = originalSrcStack;
        }
    }
    
    @Override
    public Object getCompound() {
        return NBTReflectionUtil.getItemRootNBTTagCompound(ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, this.bukkitItem));
    }
    
    @Override
    protected void setCompound(final Object o) {
        final Object run = ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, this.bukkitItem);
        ReflectionMethod.ITEMSTACK_SET_TAG.run(run, o);
        this.bukkitItem = (ItemStack)ReflectionMethod.ITEMSTACK_BUKKITMIRROR.run(null, run);
    }
    
    public void applyNBT(final ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            throw new NullPointerException("ItemStack can't be null/Air!");
        }
        final NBTItem nbtItem = new NBTItem(new ItemStack(itemStack.getType()));
        nbtItem.mergeCompound(this);
        itemStack.setItemMeta(nbtItem.getItem().getItemMeta());
    }
    
    public void mergeNBT(final ItemStack itemStack) {
        final NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.mergeCompound(this);
        itemStack.setItemMeta(nbtItem.getItem().getItemMeta());
    }
    
    public void mergeCustomNBT(final ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            throw new NullPointerException("ItemStack can't be null/Air!");
        }
        final ItemMeta itemMeta = itemStack.getItemMeta();
        NBTReflectionUtil.getUnhandledNBTTags(itemMeta).putAll(NBTReflectionUtil.getUnhandledNBTTags(this.bukkitItem.getItemMeta()));
        itemStack.setItemMeta(itemMeta);
    }
    
    public void clearCustomNBT() {
        final ItemMeta itemMeta = this.bukkitItem.getItemMeta();
        NBTReflectionUtil.getUnhandledNBTTags(itemMeta).clear();
        this.bukkitItem.setItemMeta(itemMeta);
    }
    
    public ItemStack getItem() {
        return this.bukkitItem;
    }
    
    protected void setItem(final ItemStack bukkitItem) {
        this.bukkitItem = bukkitItem;
    }
    
    public boolean hasNBTData() {
        return this.getCompound() != null;
    }
    
    public static NBTContainer convertItemtoNBT(final ItemStack itemStack) {
        return NBTReflectionUtil.convertNMSItemtoNBTCompound(ReflectionMethod.ITEMSTACK_NMSCOPY.run(null, itemStack));
    }
    
    public static ItemStack convertNBTtoItem(final NBTCompound nbtCompound) {
        return (ItemStack)ReflectionMethod.ITEMSTACK_BUKKITMIRROR.run(null, NBTReflectionUtil.convertNBTCompoundtoNMSItem(nbtCompound));
    }
    
    @Override
    protected void saveCompound() {
        if (this.directApply) {
            this.applyNBT(this.originalSrcStack);
        }
    }
}
