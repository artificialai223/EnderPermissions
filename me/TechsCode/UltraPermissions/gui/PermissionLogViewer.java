

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.gui.BasicSearch;
import me.TechsCode.EnderPermissions.base.gui.SearchFeature;
import java.util.Arrays;
import me.TechsCode.EnderPermissions.permissionDatabase.PermissionInfo;
import me.TechsCode.EnderPermissions.tpl.Tools;
import java.util.concurrent.TimeUnit;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.T;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.EnderPermissions;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.permissionlogger.LoggedPermission;
import me.TechsCode.EnderPermissions.base.gui.PageableGUI;

public abstract class PermissionLogViewer extends PageableGUI<LoggedPermission>
{
    private final Player p;
    private final EnderPermissions plugin;
    
    public PermissionLogViewer(final Player p2, final EnderPermissions plugin) {
        super(p2, plugin);
        this.p = p2;
        this.plugin = plugin;
    }
    
    @Override
    public String getTitle() {
        return T.OVERVIEW + " > " + T.PERMISSIONLOG;
    }
    
    @Override
    public void construct(final Button button, final LoggedPermission loggedPermission) {
        button.material(loggedPermission.getOutcome() ? XMaterial.LIME_STAINED_GLASS_PANE : XMaterial.RED_STAINED_GLASS_PANE).name(Animation.wave(loggedPermission.getPermission(), loggedPermission.getOutcome() ? Colors.Green : Colors.Red, Colors.White)).lore(T.GUI_PERMISSIONLOG_ACTION.get().toString(), "", T.GUI_PERMISSIONLOG_RESULT.get().options().vars(loggedPermission.getOutcome() ? ("§a" + T.PASSED) : ("§c" + T.FAILED)).get(), "§7" + T.TIME + ": §c" + Tools.getTimeString(System.currentTimeMillis() - loggedPermission.getTime(), TimeUnit.MILLISECONDS));
        if (loggedPermission.getOutcome()) {
            button.item().appendLore("§7" + T.SOURCE + ": §e" + loggedPermission.getSource());
        }
        button.item().appendLore("");
        final PermissionInfo info = this.plugin.getPermissionDatabase().getInfo(loggedPermission.getPermission());
        if (info != null) {
            button.item().appendLore(info.getLoreInfo());
        }
        button.action(p1 -> new PermissionLogAddPermission(this.p, this.plugin, loggedPermission.getPermission()) {
            @Override
            public void onBack() {
                PermissionLogViewer.this.reopen();
            }
        });
    }
    
    @Override
    public LoggedPermission[] getObjects() {
        return Arrays.stream(this.plugin.getLoggedPermissionChecks()).filter(loggedPermission -> loggedPermission.getPlayer().equals(this.p)).toArray(LoggedPermission[]::new);
    }
    
    @Override
    public SearchFeature<LoggedPermission> getSearch() {
        return new BasicSearch<LoggedPermission>() {
            @Override
            public String[] getSearchableText(final LoggedPermission loggedPermission) {
                return new String[] { loggedPermission.getPermission(), loggedPermission.getSource() };
            }
        };
    }
}
