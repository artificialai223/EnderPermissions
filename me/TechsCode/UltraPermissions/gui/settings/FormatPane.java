

package me.TechsCode.EnderPermissions.gui.settings;

import me.TechsCode.EnderPermissions.hooks.UpermsPlaceholder;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.base.dialog.UserInput;
import java.util.Arrays;
import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.visual.VisualRegistry;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.tpl.Tools;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.visual.VisualType;
import me.TechsCode.EnderPermissions.base.gui.Model;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.T;
import me.TechsCode.EnderPermissions.base.views.settings.SettingsView;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.views.settings.SettingsPane;

public class FormatPane extends SettingsPane
{
    private EnderPermissions plugin;
    
    public FormatPane(final Player player, final SettingsView settingsView, final EnderPermissions plugin) {
        super(player, settingsView);
        this.plugin = plugin;
    }
    
    @Override
    public String getName() {
        return T.GUI_SETTINGS_FORMAT_NAME.toString();
    }
    
    @Override
    public XMaterial getIcon() {
        return XMaterial.NAME_TAG;
    }
    
    @Override
    public void construct(final Model model) {
        final VisualType[] values = VisualType.values();
        for (int length = values.length, i = 0; i < length; ++i) {
            final VisualType visualType;
            model.button(values[i].getGuiSlot() + 9, button -> this.FormatButton(button, visualType));
        }
    }
    
    private void FormatButton(final Button button, final VisualType visualType) {
        button.material(visualType.getMaterial()).name(Animation.wave(Tools.getEnumName(visualType), Colors.Orange, Colors.White)).lore("§bLeft Click §7to change format", "§bRight Click §7to §cdisable");
        final String format = this.plugin.getVisualRegistry().get().getFormat(visualType);
        if (format == null || !format.equals(visualType.getDefaultFormat())) {
            button.item().appendLore("§bQ §7to reset format to §adefault");
        }
        button.item().appendLore("", "§a§lFormat:", "§f" + ((format == null) ? "§cFeature disabled" : format).replace("§", "&"));
        button.action(actionType -> {
            if (actionType == ActionType.Q) {
                this.plugin.getVisualRegistry().get().resetFormat(visualType);
            }
            else if (actionType == ActionType.RIGHT) {
                this.plugin.getVisualRegistry().get().setFormat(visualType, "none");
            }
            else {
                this.p.sendMessage("");
                this.p.sendMessage("§a§lPlaceholders:");
                Arrays.stream(EnderPermissions.placeholders).forEach(upermsPlaceholder -> this.p.sendMessage("§9" + upermsPlaceholder.getNativePlaceholder() + " §e- §7" + upermsPlaceholder.getDescription()));
                this.p.sendMessage("§7and §9{Player} §7or §9{DisplayName}");
                this.p.sendMessage("");
                this.p.sendMessage("§6§lDefault: §r" + visualType.getDefaultFormat());
                new UserInput(this.p, this.plugin, "§bSet Format", "§7Type in a new format", "§7Use the placeholders in the chat") {
                    final /* synthetic */ VisualType val$all;
                    
                    @Override
                    public void onClose(final Player player) {
                        FormatPane.this.reopen();
                    }
                    
                    @Override
                    public boolean onResult(final String s) {
                        FormatPane.this.plugin.getVisualRegistry().get().setFormat(this.val$all, s);
                        FormatPane.this.reopen();
                        return true;
                    }
                };
            }
        });
    }
}
