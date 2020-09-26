

package me.TechsCode.EnderPermissions.base.views.settings;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.tpl.Common;
import org.bukkit.enchantments.Enchantment;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import java.util.Arrays;
import me.TechsCode.EnderPermissions.base.translations.TBase;
import me.TechsCode.EnderPermissions.base.gui.Model;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.base.gui.GUI;

public abstract class SettingsView extends GUI
{
    private final int[][] layouts;
    protected SpigotTechPlugin plugin;
    private int current;
    private SettingsPane[] panes;
    
    public SettingsView(final Player player, final SpigotTechPlugin plugin) {
        super(player, plugin);
        this.layouts = new int[][] { { 5 }, { 4, 6 }, { 4, 5, 6 }, { 3, 4, 6, 7 }, { 3, 4, 5, 6, 7 }, { 2, 3, 4, 6, 7, 8 }, { 2, 3, 4, 5, 6, 7, 8 }, { 1, 2, 3, 4, 6, 7, 8, 9 }, { 1, 2, 3, 4, 5, 6, 7, 8, 9 } };
        this.plugin = plugin;
        this.current = 0;
        this.panes = this.getPanes();
    }
    
    public abstract SettingsPane[] getPanes();
    
    public abstract void onBack();
    
    private SettingsPane getCurrent() {
        return this.panes[this.current];
    }
    
    @Override
    public int getCurrentSlots() {
        return 54;
    }
    
    @Override
    protected void construct(final Model model) {
        model.setTitle(TBase.SETTINGS + " > " + this.getCurrent().getName());
        model.setSlots(54);
        final SettingsPane settingsPane = this.panes[this.current];
        final int[] array2 = Arrays.stream(this.layouts).filter(array -> array.length == this.panes.length).findFirst().get();
        int n = 0;
        for (int length = this.panes.length, i = 0; i < length; ++i) {
            final SettingsPane[] array3;
            final SettingsPane settingsPane2;
            final boolean b;
            final int current;
            model.button(array2[n], button2 -> {
                settingsPane2 = array3[i];
                b = (n == this.current);
                button2.material(settingsPane2.getIcon()).name(Animation.wave(settingsPane2.getName(), Colors.Gold, Colors.Yellow));
                if (b) {
                    button2.item().lore(TBase.GUI_SETTINGS_PAGESELECTED.get().toString());
                    button2.item().addEnchantment(Enchantment.LUCK, 1).showEnchantments(false);
                }
                else {
                    button2.item().lore(TBase.GUI_SETTINGS_SELECTPAGE.get().toString());
                }
                button2.action(p1 -> this.current = current);
                return;
            });
            ++n;
        }
        settingsPane.construct(model);
        model.button(50, button -> Common.BackButton(button, p0 -> this.onBack()));
    }
}
