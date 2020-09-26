

package me.TechsCode.EnderPermissions.gui.permissionEditor;

import me.TechsCode.EnderPermissions.permissionDatabase.PermissionInfo;
import me.TechsCode.EnderPermissions.storage.collection.PermissionList;
import me.TechsCode.EnderPermissions.storage.objects.Permission;
import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.base.gui.Model;
import me.TechsCode.EnderPermissions.EnderPermissions;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.storage.objects.PermissionHolder;

public abstract class PluginPermissionListView extends PermissionListView
{
    private PermissionHolder holder;
    private final PermissionEditorMainView.PluginEntry sourcePlugin;
    
    public PluginPermissionListView(final Player player, final EnderPermissions EnderPermissions, final PermissionHolder holder, final PermissionEditorMainView.PluginEntry sourcePlugin) {
        super(player, EnderPermissions, holder);
        this.holder = holder;
        this.sourcePlugin = sourcePlugin;
    }
    
    @Override
    public void reopen() {
        super.reopen();
        this.sourcePlugin.retrieveData();
    }
    
    @Override
    public PermissionWithInfo[] getObjects() {
        return this.sourcePlugin.permissionsFromDatabase.stream().map(permissionInfo -> new PermissionWithInfo(permissionInfo.getPermission(), permissionInfo)).distinct().toArray(PermissionWithInfo[]::new);
    }
    
    @Override
    public String getTitle() {
        return this.sourcePlugin.name + " > Permissions";
    }
    
    @Override
    protected void construct(final Model model) {
        super.construct(model);
        model.button(this.getRightOptionSlot(), this::AllPermissionsButton);
    }
    
    private void AllPermissionsButton(final Button button) {
        button.material(XMaterial.BUCKET).name(Animation.wave("All Permissions", Colors.Green, Colors.White)).lore("§bLeft Click §7to §aadd §7all Permissions", "§bRight Click §7to §cremove §7all Permissions");
        final PermissionWithInfo[] array;
        int length;
        int i = 0;
        PermissionWithInfo permissionWithInfo;
        final PermissionList list;
        final PermissionList list2;
        final PermissionWithInfo[] array2;
        int length2;
        int j = 0;
        final PermissionList list3;
        button.action(actionType -> {
            if (actionType == ActionType.LEFT) {
                this.holder.getPermissions();
                this.holder.getAdditionalPermissions();
                this.getObjects();
                for (length = array.length; i < length; ++i) {
                    permissionWithInfo = array[i];
                    if (list.name(permissionWithInfo.getName()).isEmpty() && list2.name(permissionWithInfo.getName()).isEmpty()) {
                        this.holder.newPermission(permissionWithInfo.getName()).create();
                    }
                }
            }
            if (actionType == ActionType.RIGHT) {
                this.holder.getPermissions();
                this.getObjects();
                for (length2 = array2.length; j < length2; ++j) {
                    list3.name(array2[j].getName()).forEach(Permission::remove);
                }
            }
        });
    }
}
