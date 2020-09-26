

package me.TechsCode.EnderPermissions.tpl.clickGUI;

import org.bukkit.entity.ArmorStand;

public abstract class FloatingText extends FloatingElement
{
    public abstract String getText();
    
    @Override
    public void apply(final ArmorStand armorStand) {
        final String text = this.getText();
        this.setShown(text != null);
        if (armorStand != null && text != null) {
            armorStand.setCustomName(text);
            armorStand.setCustomNameVisible(true);
        }
    }
    
    @Override
    public double getYOffset() {
        return 2.5;
    }
}
