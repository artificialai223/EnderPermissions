

package me.TechsCode.EnderPermissions.base.views;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.base.gui.Model;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.base.gui.GUI;

public abstract class ConfirmationView extends GUI
{
    private String title;
    
    public ConfirmationView(final Player player, final SpigotTechPlugin spigotTechPlugin, final String title) {
        super(player, spigotTechPlugin);
        this.title = title;
    }
    
    @Override
    protected void construct(final Model model) {
        model.setTitle(this.title);
        model.setSlots(27);
        model.button(12, this::ConfirmButton);
        model.button(16, this::DenyButton);
    }
    
    private void ConfirmButton(final Button button) {
        button.material(XMaterial.EMERALD_BLOCK).name(Animation.wave("Confirm", Colors.Green, Colors.White)).lore("§7Click to confirm");
        button.action(p0 -> this.choose(true));
    }
    
    private void DenyButton(final Button button) {
        button.material(XMaterial.REDSTONE_BLOCK).name(Animation.wave("Abort", Colors.Red, Colors.White)).lore("§7Click to abort");
        button.action(p0 -> this.choose(false));
    }
    
    public abstract void choose(final boolean p0);
}
