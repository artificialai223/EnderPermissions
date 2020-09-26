

package me.TechsCode.EnderPermissions.tpl.clickGUI;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.ArmorStand;
import me.TechsCode.EnderPermissions.base.item.CustomItem;

public abstract class FloatingItem extends FloatingElement
{
    public abstract CustomItem getItem();
    
    @Override
    public void apply(final ArmorStand armorStand) {
        final CustomItem item = this.getItem();
        this.setShown(item != null);
        if (armorStand != null && item != null) {
            final ItemStack value = item.get();
            if (armorStand.getPassenger() == null || !((Item)armorStand.getPassenger()).getItemStack().isSimilar(value)) {
                if (armorStand.getPassenger() != null) {
                    armorStand.getPassenger().remove();
                }
                final Item dropItem = armorStand.getLocation().getWorld().dropItem(armorStand.getLocation(), value);
                dropItem.setGravity(false);
                dropItem.setPickupDelay(Integer.MAX_VALUE);
                armorStand.setPassenger((Entity)dropItem);
            }
        }
    }
    
    @Override
    public double getYOffset() {
        return 2.0;
    }
}
