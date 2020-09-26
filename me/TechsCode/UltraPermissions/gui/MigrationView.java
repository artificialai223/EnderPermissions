

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Model;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.migration.MigrationAssistant;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.gui.GUI;

public class MigrationView extends GUI
{
    private final EnderPermissions plugin;
    private final MigrationAssistant assistant;
    
    public MigrationView(final Player player, final EnderPermissions plugin, final MigrationAssistant assistant) {
        super(player, plugin);
        this.plugin = plugin;
        this.assistant = assistant;
    }
    
    @Override
    protected void construct(final Model model) {
        model.setTitle("Import > " + this.assistant.getPluginName());
        model.setSlots(45);
        model.button(23, button -> {
            button.material(XMaterial.END_PORTAL_FRAME).name(Animation.wave("Import Data", Colors.LightPink, Colors.White)).lore("§7Click to §dconvert", "", "§7Your existing data will be overwritten.", "§7If you dont want to convert, delete " + this.assistant.getPluginName());
            button.action(p0 -> this.assistant.doMigration(this.plugin));
        });
    }
}
