

package me.TechsCode.EnderPermissions.base.views.settings;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.visual.Text;
import me.TechsCode.EnderPermissions.base.AppearanceRegistry;
import me.TechsCode.EnderPermissions.base.dialog.UserInput;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.base.gui.Model;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;

public class CustomizationPane extends SettingsPane
{
    private SpigotTechPlugin plugin;
    
    public CustomizationPane(final Player player, final SettingsView settingsView, final SpigotTechPlugin plugin) {
        super(player, settingsView);
        this.plugin = plugin;
    }
    
    @Override
    public String getName() {
        return "Customization";
    }
    
    @Override
    public XMaterial getIcon() {
        return XMaterial.CRAFTING_TABLE;
    }
    
    @Override
    public void construct(final Model model) {
        model.button(23, this::PrefixButton);
    }
    
    private void PrefixButton(final Button button) {
        final AppearanceRegistry appearanceRegistry = this.plugin.getAppearanceRegistry();
        button.material(XMaterial.NAME_TAG).name(Animation.wave("Prefix", Colors.Gold, Colors.Yellow)).lore(appearanceRegistry.isPrefixModified() ? "§7Click to §creset §7the prefix" : "§7Click to §achange§7 the prefix", "", "§7Prefix: §f" + appearanceRegistry.getPrefix());
        final AppearanceRegistry appearanceRegistry2;
        button.action(p1 -> {
            if (appearanceRegistry2.isPrefixModified()) {
                appearanceRegistry2.resetPrefix();
            }
            else {
                new UserInput(this.p, this.plugin, "§bPrefix", "§7Type in a prefix") {
                    final /* synthetic */ AppearanceRegistry val$registry;
                    
                    @Override
                    public void onClose(final Player player) {
                        CustomizationPane.this.reopen();
                    }
                    
                    @Override
                    public boolean onResult(final String s) {
                        this.val$registry.setPrefix(Text.chatColor(s));
                        CustomizationPane.this.reopen();
                        return true;
                    }
                };
            }
        });
    }
}
