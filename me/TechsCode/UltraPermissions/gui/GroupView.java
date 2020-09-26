

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.base.views.ConfirmationView;
import me.TechsCode.EnderPermissions.base.views.MaterialPickerView;
import me.TechsCode.EnderPermissions.base.dialog.UserInput;
import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.visual.Text;
import me.TechsCode.EnderPermissions.storage.collection.GroupList;
import me.TechsCode.EnderPermissions.storage.objects.PermissionHolder;
import me.TechsCode.EnderPermissions.gui.permissionEditor.PermissionEditorMainView;
import me.TechsCode.EnderPermissions.base.translations.TBase;
import me.TechsCode.EnderPermissions.gui.permissionEditor.PermissionCopy;
import java.util.List;
import me.TechsCode.EnderPermissions.gui.permissionEditor.PermissionListLore;
import me.TechsCode.EnderPermissions.gui.permissionEditor.PermissionCopyList;
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
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.gui.GUI;

public abstract class GroupView extends GUI
{
    private final EnderPermissions plugin;
    private final Group group;
    private final long opened;
    
    public GroupView(final Player player, final EnderPermissions plugin, final Group group) {
        super(player, plugin);
        this.plugin = plugin;
        this.group = group;
        this.opened = System.currentTimeMillis();
    }
    
    public abstract void onBack();
    
    public boolean hasBackButton() {
        return true;
    }
    
    @Override
    public int getCurrentSlots() {
        return 54;
    }
    
    @Override
    public String getCurrentTitle() {
        return T.GROUPS + " > " + this.group.getName();
    }
    
    @Override
    protected void construct(final Model model) {
        model.button(this::SetDefaultButton, 13);
        model.button(this::ViewPermissionsButton, 15);
        model.button(this::ViewInheritanceButton, 20);
        model.button(this::PrefixAndSuffixButton, 26);
        model.button(this::RenameButton, 31);
        model.button(this::UsersButton, 33);
        model.button(this::ChangeIconButton, 38);
        model.button(this::DeleteButton, 44);
        if (this.hasBackButton()) {
            model.button(50, button -> Common.BackButton(button, p0 -> this.onBack()));
        }
    }
    
    private void SetDefaultButton(final Button button) {
        button.material(this.group.isDefault() ? XMaterial.LIME_STAINED_GLASS_PANE : XMaterial.WHITE_STAINED_GLASS_PANE).name(Animation.wave(T.GUI_GROUPVIEW_DEFAULT_TITLE.toString(), Colors.DeepSkyBlue, Colors.AliceBlue)).lore((this.group.isDefault() ? T.GUI_GROUPVIEW_DEFAULT_UNDEFAULT_ACTION : T.GUI_GROUPVIEW_DEFAULT_ACTION).get().toString(), "");
        button.item().appendLore("§7Who will get the default groups?", "§f" + this.plugin.getDefaultGroupAssignOption().getExplanation());
        button.item().appendLore("", "§7This could be a §cdestructive action§7, pay attention before changing");
        button.action(p0 -> this.group.setDefault(!this.group.isDefault()));
    }
    
    private void ViewPermissionsButton(final Button button) {
        button.material(XMaterial.WRITABLE_BOOK).name(Animation.wave(T.GUI_GROUPVIEW_VIEWPERMISSIONS_TITLE.toString(), Colors.DeepSkyBlue, Colors.AliceBlue)).lore(T.GUI_GROUPVIEW_VIEWPERMISSIONS_ACTION.get().toString(), "", "§7" + T.PERMISSION_NODES + ":");
        final PermissionCopyList list = new PermissionCopyList(this.group.getPermissions(), this.group.getInheritedPermissions());
        button.item().appendLore(PermissionListLore.get(this.opened, list));
        if (list.size() == 0) {
            button.item().appendLore("§7- §c" + TBase.NONE);
        }
        button.action(p0 -> new PermissionEditorMainView(this.p, this.plugin, this.group) {
            @Override
            public void onBack() {
                GroupView.this.reopen();
            }
        });
    }
    
    private void ViewInheritanceButton(final Button button) {
        button.material(XMaterial.PAPER).name(Animation.wave(T.GUI_GROUPVIEW_INHERITANCE_TITLE.toString(), Colors.DeepSkyBlue, Colors.AliceBlue)).lore(T.GUI_GROUPVIEW_INHERITANCE_ACTION.get().toString(), "", "§7" + T.INHERITED_GROUPS + ":");
        final GroupList activeInheritedGroups = this.group.getActiveInheritedGroups();
        activeInheritedGroups.forEach(group -> button.item().appendLore("§7- §e" + group.getName()));
        if (activeInheritedGroups.isEmpty()) {
            button.item().appendLore("§7- §c" + TBase.NONE);
        }
        button.action(p0 -> new InheritedGroupListView(this.p, this.plugin, this.group) {
            @Override
            public void onBack() {
                GroupView.this.reopen();
            }
        });
    }
    
    private void PrefixAndSuffixButton(final Button button) {
        button.material(XMaterial.NAME_TAG).name(Animation.wave(T.GUI_GROUPVIEW_EDITPREFIXSUFFIX_TITLE.toString(), Colors.DeepSkyBlue, Colors.AliceBlue)).lore(T.GUI_GROUPVIEW_EDITPREFIXSUFFIX_LEFTCLICK.get().toString(), T.GUI_GROUPVIEW_EDITPREFIXSUFFIX_RIGHTCLICK.get().toString(), "", "§7" + T.PREFIX + ": §e" + Text.color(this.group.getPrefix().orElse("§c" + T.NONE)), "§7" + T.SUFFIX + ": §e" + Text.color(this.group.getSuffix().orElse("§c" + T.NONE)));
        final boolean b;
        button.action(actionType -> {
            b = (actionType == ActionType.LEFT);
            new UserInput(this.p, this.plugin, (b ? T.EDITING_PREFIX_EDIT : T.EDITING_SUFFIX_EDIT).get().toString(), (b ? T.EDITING_PREFIX_TYPEIN : T.EDITING_SUFFIX_TYPEIN).get().toString(), (b ? T.EDITING_PREFIX_TYPEINNONE : T.EDITING_SUFFIX_TYPEINNONE).get().toString()) {
                final /* synthetic */ ActionType val$type;
                
                @Override
                public void onClose(final Player player) {
                    GroupView.this.reopen();
                }
                
                @Override
                public boolean onResult(final String s) {
                    final String s2 = s.equalsIgnoreCase("none") ? null : s;
                    if (this.val$type == ActionType.LEFT) {
                        GroupView.this.group.setPrefix(s2);
                    }
                    else {
                        GroupView.this.group.setSuffix(s2);
                    }
                    GroupView.this.reopen();
                    return true;
                }
            };
        });
    }
    
    private void RenameButton(final Button button) {
        button.material(XMaterial.BOOK).name(Animation.wave(T.RENAME_GROUP_TITLE.toString(), Colors.DeepSkyBlue, Colors.AliceBlue)).lore(T.GUI_GROUPVIEW_RENAME_ACTION.get().toString());
        button.action(p0 -> new UserInput(this.p, this.plugin, "§b§l" + T.RENAME_GROUP_TITLE.get().toString(), T.RENAME_GROUP_SUBTITLE) {
            @Override
            public void onClose(final Player player) {
                GroupView.this.reopen();
            }
            
            @Override
            public boolean onResult(final String s) {
                GroupView.this.group.setName(s.replace(" ", ""));
                GroupView.this.reopen();
                return true;
            }
        });
    }
    
    private void ChangeIconButton(final Button button) {
        button.material(XMaterial.GRASS_BLOCK).name(Animation.wave(T.GUI_GROUPVIEW_CHANGEICON_TITLE.toString(), Colors.DeepSkyBlue, Colors.AliceBlue)).lore(T.GUI_GROUPVIEW_CHANGEICON_ACTION.get().toString());
        button.action(p0 -> new MaterialPickerView(this.p, this.plugin, this.group.getName() + " > " + T.GUI_GROUPVIEW_CHANGEICON_TITLE) {
            @Override
            public void choose(final Player player, final XMaterial icon) {
                GroupView.this.group.setIcon(icon);
                GroupView.this.reopen();
            }
            
            @Override
            public void onBack() {
                GroupView.this.reopen();
            }
        });
    }
    
    private void DeleteButton(final Button button) {
        button.material(XMaterial.REDSTONE_BLOCK).name(Animation.wave(T.GUI_GROUPVIEW_DELETE_TITLE.toString(), Colors.Red, Colors.AliceBlue)).lore(T.GUI_GROUPVIEW_DELETE_ACTION.get().toString(), "", T.GUI_GROUPVIEW_DELETE_PERMANENT.get().toString());
        button.action(p0 -> new ConfirmationView(this.p, this.plugin, T.CONFIRM_DELETION_OF.get().options().vars(this.group.getName()).get()) {
            @Override
            public void choose(final boolean b) {
                if (b) {
                    GroupView.this.group.remove();
                    new GroupListView(this.p, GroupView.this.plugin) {
                        @Override
                        public void onBack() {
                            GroupView.this.onBack();
                        }
                    };
                }
                else {
                    GroupView.this.reopen();
                }
            }
        });
    }
    
    private void UsersButton(final Button button) {
        button.material(XMaterial.PLAYER_HEAD).name(Animation.wave(T.USERS.toString(), Colors.DeepSkyBlue, Colors.AliceBlue)).lore("§7Click to view §eUsers", "", "§7Amount: §e" + this.plugin.getUsers().usersInGroup(this.group.toStored()).size());
        button.action(p0 -> new GroupUserListView(this.p, this.plugin, this.group) {
            @Override
            public void onBack() {
                GroupView.this.reopen();
            }
        });
    }
}
