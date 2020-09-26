

package me.TechsCode.EnderPermissions.base.gui;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.base.item.CustomItem;
import java.util.List;

public class Button
{
    private List<Action> actions;
    private CustomItem item;
    
    public Button() {
        this.actions = new ArrayList<Action>();
    }
    
    public void setItem(final CustomItem item) {
        this.item = item;
    }
    
    public CustomItem material(final XMaterial xMaterial) {
        return this.item = new CustomItem(xMaterial);
    }
    
    public CustomItem material(final Material material) {
        return this.item = new CustomItem(material);
    }
    
    public CustomItem stack(final ItemStack itemStack) {
        return this.item = new CustomItem(itemStack, false);
    }
    
    public CustomItem item() {
        return this.item;
    }
    
    public void action(final Action action) {
        this.actions.add(action);
    }
    
    public void action(final ActionType actionType, final Runnable runnable) {
        this.actions.add(actionType2 -> {
            if (actionType2 == actionType) {
                runnable.run();
            }
        });
    }
    
    public List<Action> getActions() {
        return this.actions;
    }
}
