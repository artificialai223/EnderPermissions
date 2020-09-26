

package me.TechsCode.EnderPermissions.gui.permissionEditor;

import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.base.dialog.UserInput;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.StringUtils;
import me.TechsCode.EnderPermissions.EnderPermissions;
import org.bukkit.entity.Player;

public abstract class PlaceholderFillDialog
{
    private final Player p;
    private final EnderPermissions plugin;
    private String permission;
    
    public PlaceholderFillDialog(final Player p3, final EnderPermissions plugin, final String permission) {
        this.p = p3;
        this.plugin = plugin;
        this.permission = permission;
        this.run();
    }
    
    public abstract void onBack();
    
    private void run() {
        final String[] substringsBetween = StringUtils.substringsBetween(this.permission, "[", "]");
        if (substringsBetween == null || substringsBetween.length == 0) {
            this.onComplete(this.permission);
            return;
        }
        final String str = substringsBetween[0];
        new UserInput(this.p, this.plugin, "§f" + this.permission.replace("[", "§7[").replace("]", "]§f"), "§7Type in a value for §e[" + str + "]") {
            @Override
            public void onClose(final Player player) {
                PlaceholderFillDialog.this.onBack();
            }
            
            @Override
            public boolean onResult(final String replacement) {
                PlaceholderFillDialog.this.permission = PlaceholderFillDialog.this.permission.replace("[" + str + "]", replacement);
                this.run();
                return true;
            }
        };
    }
    
    public abstract void onComplete(final String p0);
}
