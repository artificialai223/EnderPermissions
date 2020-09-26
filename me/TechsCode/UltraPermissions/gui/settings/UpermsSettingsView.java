

package me.TechsCode.EnderPermissions.gui.settings;

import me.TechsCode.EnderPermissions.base.views.settings.MySQLPane;
import me.TechsCode.EnderPermissions.base.views.settings.LanguagePane;
import me.TechsCode.EnderPermissions.base.views.settings.CustomizationPane;
import me.TechsCode.EnderPermissions.base.views.settings.SettingsPane;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.EnderPermissions;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.base.views.settings.SettingsView;

public abstract class UpermsSettingsView extends SettingsView
{
    public UpermsSettingsView(final Player player, final EnderPermissions EnderPermissions) {
        super(player, EnderPermissions);
    }
    
    @Override
    public SettingsPane[] getPanes() {
        return new SettingsPane[] { new CustomizationPane(this.p, this, this.plugin), new FormatPane(this.p, this, (EnderPermissions)this.plugin), new LanguagePane(this.p, this, this.plugin), new MiscPane(this.p, this, (EnderPermissions)this.plugin), new MySQLPane(this.p, this, this.plugin) };
    }
}
