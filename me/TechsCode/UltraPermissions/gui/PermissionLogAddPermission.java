

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.gui.BasicSearch;
import me.TechsCode.EnderPermissions.base.gui.SearchFeature;
import java.util.Collection;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.gui.permissionEditor.PermissionEditorMainView;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.T;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.EnderPermissions;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.storage.objects.PermissionHolder;
import me.TechsCode.EnderPermissions.base.gui.PageableGUI;

public abstract class PermissionLogAddPermission extends PageableGUI<PermissionHolder>
{
    private final Player p;
    private final EnderPermissions plugin;
    private final String permission;
    
    public PermissionLogAddPermission(final Player p3, final EnderPermissions plugin, final String permission) {
        super(p3, plugin);
        this.p = p3;
        this.plugin = plugin;
        this.permission = permission;
    }
    
    @Override
    public String getTitle() {
        return T.PERMISSION + " > " + this.permission;
    }
    
    @Override
    public void construct(final Button button, final PermissionHolder permissionHolder) {
        button.material(XMaterial.WHITE_STAINED_GLASS_PANE).name(Animation.wave(permissionHolder.getName(), Colors.Green, Colors.White)).lore("§7Click to §badd §7permission");
        button.action(p1 -> {
            permissionHolder.newPermission(this.permission).create();
            new PermissionEditorMainView(this.p, this.plugin, permissionHolder) {
                @Override
                public void onBack() {
                    PermissionLogAddPermission.this.onBack();
                }
            };
        });
    }
    
    @Override
    public PermissionHolder[] getObjects() {
        final ArrayList list = new ArrayList();
        list.addAll(this.plugin.getGroups());
        list.addAll(this.plugin.getUsers());
        return (PermissionHolder[])list.toArray(new PermissionHolder[0]);
    }
    
    @Override
    public SearchFeature<PermissionHolder> getSearch() {
        return new BasicSearch<PermissionHolder>() {
            @Override
            public String[] getSearchableText(final PermissionHolder permissionHolder) {
                return new String[] { permissionHolder.getName() };
            }
        };
    }
}
