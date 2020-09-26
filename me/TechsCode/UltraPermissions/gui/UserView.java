

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.base.dialog.UserInput;
import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.visual.Text;
import me.TechsCode.EnderPermissions.storage.objects.PermissionHolder;
import me.TechsCode.EnderPermissions.gui.permissionEditor.PermissionEditorMainView;
import me.TechsCode.EnderPermissions.base.translations.TBase;
import me.TechsCode.EnderPermissions.gui.permissionEditor.PermissionCopy;
import java.util.List;
import me.TechsCode.EnderPermissions.gui.permissionEditor.PermissionListLore;
import me.TechsCode.EnderPermissions.gui.permissionEditor.PermissionCopyList;
import java.util.Optional;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.tpl.Tools;
import java.util.concurrent.TimeUnit;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.storage.objects.UserRankup;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.tpl.Common;
import me.TechsCode.EnderPermissions.base.gui.Model;
import me.TechsCode.EnderPermissions.T;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.gui.GUI;

public abstract class UserView extends GUI
{
    private final EnderPermissions plugin;
    private final User user;
    private final long opened;
    
    public UserView(final Player player, final EnderPermissions plugin, final User user) {
        super(player, plugin);
        this.plugin = plugin;
        this.user = user;
        this.opened = System.currentTimeMillis();
    }
    
    public boolean hasBackButton() {
        return true;
    }
    
    public abstract void onBack();
    
    @Override
    public int getCurrentSlots() {
        return 54;
    }
    
    @Override
    public String getCurrentTitle() {
        return T.USER + " > " + this.user.getName();
    }
    
    @Override
    protected void construct(final Model model) {
        model.button(this::GroupsButton, 20);
        model.button(this::PermissionsButton, 14);
        model.button(this::PrefixSuffixButton, 26);
        if (this.hasBackButton()) {
            model.button(50, button -> Common.BackButton(button, p0 -> this.onBack()));
        }
    }
    
    private void GroupsButton(final Button button) {
        button.material(XMaterial.BOOKSHELF).name(Animation.wave(T.GROUPS.toString(), Colors.DeepSkyBlue, Colors.AliceBlue)).lore(T.GUI_USERVIEW_VIEWGROUPS_ACTION.get().toString(), "", "§7" + T.GROUPS + ":");
        int n = 1;
        for (final UserRankup userRankup : this.user.getRankups()) {
            final Optional<Group> value = userRankup.getGroup().get();
            if (!value.isPresent()) {
                continue;
            }
            if (userRankup.isPermanent()) {
                button.item().appendLore("§7" + n + ". §e" + value.get().getName());
            }
            else {
                button.item().appendLore("§7" + n + ". §e" + value.get() + " §7" + T.TIME_FOR + " §c" + Tools.getTimeString(userRankup.getLeftDuration(), TimeUnit.MILLISECONDS));
            }
            ++n;
        }
        if (this.user.getGroups().size() == 0) {
            button.item().appendLore("§7- §cNone");
        }
        button.action(p0 -> new UserGroupListView(this.p, this.plugin, this.user) {
            @Override
            public void onBack() {
                UserView.this.reopen();
            }
        });
    }
    
    private void PermissionsButton(final Button button) {
        button.material(XMaterial.WRITABLE_BOOK).name(Animation.wave(T.GUI_USERVIEW_VIEWPERMISSIONS_TITLE.toString(), Colors.DeepSkyBlue, Colors.AliceBlue)).lore(T.GUI_USERVIEW_VIEWPERMISSIONS_ACTION.get().toString(), "", "§7" + T.PERMISSION_NODES + ":");
        final PermissionCopyList list = new PermissionCopyList(this.user.getPermissions(), this.user.getAdditionalPermissions());
        button.item().appendLore(PermissionListLore.get(this.opened, list));
        if (list.size() == 0) {
            button.item().appendLore("§7- §c" + TBase.NONE);
        }
        button.action(p0 -> new PermissionEditorMainView(this.p, this.plugin, this.user) {
            @Override
            public void onBack() {
                UserView.this.reopen();
            }
        });
    }
    
    private void PrefixSuffixButton(final Button button) {
        button.material(XMaterial.NAME_TAG).name(Animation.wave(T.GUI_USERVIEW_EDITPREFIXSUFFIX_TITLE.toString(), Colors.DeepSkyBlue, Colors.AliceBlue)).lore(T.GUI_USERVIEW_EDITPREFIXSUFFIX_LEFTCLICK.get().toString(), T.GUI_USERVIEW_EDITPREFIXSUFFIX_RIGHTCLICK.get().toString(), "", "§7" + T.PREFIX + ": §f" + Text.color(this.user.getPrefix().orElse("§cNone")), "§7" + T.SUFFIX + ": §f" + Text.color(this.user.getSuffix().orElse("§cNone")));
        button.action(actionType -> {
            if (actionType == ActionType.LEFT) {
                new UserInput(this.p, this.plugin, T.EDITING_PREFIX_EDIT.get().toString(), T.EDITING_PREFIX_TYPEIN.get().toString(), T.EDITING_PREFIX_TYPEINNONE.get().toString()) {
                    @Override
                    public void onClose(final Player player) {
                        UserView.this.reopen();
                    }
                    
                    @Override
                    public boolean onResult(final String s) {
                        UserView.this.user.setPrefix(s.equals("none") ? null : s);
                        UserView.this.reopen();
                        return true;
                    }
                };
            }
            return;
        });
        button.action(actionType2 -> {
            if (actionType2 == ActionType.RIGHT) {
                new UserInput(this.p, this.plugin, T.EDITING_SUFFIX_EDIT.get().toString(), T.EDITING_SUFFIX_TYPEIN.get().toString(), T.EDITING_SUFFIX_TYPEINNONE.get().toString()) {
                    @Override
                    public void onClose(final Player player) {
                        UserView.this.reopen();
                    }
                    
                    @Override
                    public boolean onResult(final String s) {
                        UserView.this.user.setSuffix(s.equals("none") ? null : s);
                        UserView.this.reopen();
                        return true;
                    }
                };
            }
        });
    }
}
