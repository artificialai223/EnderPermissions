

package me.TechsCode.EnderPermissions.base.views.settings;

import me.TechsCode.EnderPermissions.base.gui.Model;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import org.bukkit.entity.Player;

public abstract class SettingsPane
{
    protected Player p;
    private SettingsView view;
    
    public SettingsPane(final Player p2, final SettingsView view) {
        this.p = p2;
        this.view = view;
    }
    
    public abstract String getName();
    
    public abstract XMaterial getIcon();
    
    public abstract void construct(final Model p0);
    
    public void reopen() {
        this.view.reopen();
    }
    
    public int[] getInnerContainerSlots() {
        return new int[] { 20, 21, 22, 23, 24, 25, 26, 29, 30, 31, 32, 33, 34, 35 };
    }
}
