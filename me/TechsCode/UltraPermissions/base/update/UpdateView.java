

package me.TechsCode.EnderPermissions.base.update;

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

public abstract class UpdateView extends GUI
{
    private final String localVersion;
    private final String globalVersion;
    
    public UpdateView(final Player player, final SpigotTechPlugin spigotTechPlugin, final String localVersion, final String globalVersion) {
        super(player, spigotTechPlugin);
        this.localVersion = localVersion;
        this.globalVersion = globalVersion;
    }
    
    public abstract void onResponse(final boolean p0);
    
    @Override
    protected void construct(final Model model) {
        model.button(12, this::YesButton);
        model.button(16, this::NoButton);
    }
    
    @Override
    public int getCurrentSlots() {
        return 27;
    }
    
    @Override
    public String getCurrentTitle() {
        return "Update from " + this.localVersion + " to " + this.globalVersion;
    }
    
    private void YesButton(final Button button) {
        button.material(XMaterial.EMERALD_BLOCK).name(Animation.wave("Update", Colors.Green, Colors.White)).lore("§7Click to load §eupdate", "", "§7This feature requires §bDiscord §7to", "§7verify your purchase");
        button.action(p0 -> {
            this.p.closeInventory();
            this.onResponse(true);
        });
    }
    
    private void NoButton(final Button button) {
        button.material(XMaterial.REDSTONE_BLOCK).name(Animation.wave("Stay on this Version", Colors.Red, Colors.White)).lore("§7Click to stay on this Version", "", "§7Your Version: §c" + this.localVersion, "§7New Version: §a" + this.globalVersion);
        button.action(p0 -> {
            this.p.closeInventory();
            this.onResponse(false);
        });
    }
}
