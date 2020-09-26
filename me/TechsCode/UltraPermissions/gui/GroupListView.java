

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.base.gui.BasicSearch;
import me.TechsCode.EnderPermissions.base.gui.SearchFeature;
import java.util.function.Function;
import me.TechsCode.EnderPermissions.base.networking.NServer;
import me.TechsCode.EnderPermissions.base.dialog.UserInput;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Model;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.storage.collection.GroupList;
import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.visual.Text;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.T;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.EnderPermissions;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.base.gui.PageableGUI;

public abstract class GroupListView extends PageableGUI<Group>
{
    private final Player p;
    private final EnderPermissions plugin;
    private boolean showAll;
    private int allGroupsCount;
    private int applicableGroupsCount;
    
    public GroupListView(final Player p2, final EnderPermissions plugin) {
        super(p2, plugin);
        this.showAll = false;
        this.allGroupsCount = 0;
        this.applicableGroupsCount = 0;
        this.p = p2;
        this.plugin = plugin;
    }
    
    @Override
    public String getTitle() {
        if (!this.showAll) {
            return T.GROUPS + " > " + T.GUI_GROUPBROWSER_TITLE_RANGE.get().options().vars(this.applicableGroupsCount + "", this.allGroupsCount + "").get();
        }
        if (this.applicableGroupsCount == this.allGroupsCount) {
            return T.GROUPS + " > " + T.GUI_GROUPBROWSER_TITLE_LIST;
        }
        return T.GROUPS + " > " + T.GUI_GROUPBROWSER_TITLE_ALL;
    }
    
    @Override
    public void construct(final Button button, final Group group) {
        button.material(group.getIcon()).name(Animation.wave(group.getName(), Colors.Orange, Colors.Yellow, Colors.White));
        button.item().appendLore(T.GUI_GROUPBROWSER_LEFTCLICK_ACTION.get().toString()).appendLore(T.GUI_GROUPBROWSER_Q_ACTION.get().toString());
        if (group.isDefault() || group.getServer().isPresent() || group.getWorld().isPresent()) {
            button.item().appendLore("");
        }
        if (group.isDefault()) {
            button.item().appendLore(T.GUI_GROUPBROWSER_DEFAULT_GROUP_INDICATOR.get().toString());
        }
        if (group.getServer().isPresent()) {
            button.item().appendLore("§7" + T.SERVER + ": §6" + group.getServer().get());
        }
        if (group.getWorld().isPresent()) {
            button.item().appendLore("§7" + T.WORLD + ": §6" + group.getWorld().get());
        }
        final String s = group.getPrefix().orElse("§c" + T.NONE);
        final String s2 = group.getSuffix().orElse("§c" + T.NONE);
        final int size = group.getPermissions().size();
        button.item().appendLore("", "§7" + T.PREFIX + ": §f" + Text.color(s), "§7" + T.SUFFIX + ": §f" + Text.color(s2), "", "§7" + T.INHERITED_GROUPS + ":");
        final GroupList bestToWorst = group.getActiveInheritedGroups().bestToWorst();
        final Iterator<Group> iterator = bestToWorst.iterator();
        while (iterator.hasNext()) {
            button.item().appendLore("§7- §e" + iterator.next().getName());
        }
        if (bestToWorst.isEmpty()) {
            button.item().appendLore("§7- §c" + T.NONE);
        }
        button.item().appendLore("", "§a" + size + " §7" + T.PERMISSION_NODES);
        button.action(actionType -> {
            if (actionType == ActionType.Q) {
                new GroupStorageSettingsView(this.p, this.plugin, group) {
                    @Override
                    public void onBack() {
                        GroupListView.this.reopen();
                    }
                };
            }
            else {
                new GroupView(this.p, this.plugin, group) {
                    @Override
                    public void onBack() {
                        GroupListView.this.reopen();
                    }
                };
            }
        });
    }
    
    @Override
    protected void construct(final Model model) {
        super.construct(model);
        if (!this.showAll) {
            model.button(48, this::ShowAllButton);
        }
        model.button(52, this::ReorderButton);
        model.button(53, this::AddButton);
    }
    
    private void AddButton(final Button button) {
        button.material(XMaterial.ANVIL).name(Animation.fading(T.ADD.toString(), Colors.DarkCyan, Colors.Cyan)).lore(T.GUI_GROUPBROWSER_ADD_ACTION.get().toString());
        button.action(p0 -> new UserInput(this.p, this.plugin, T.CREATE_GROUP_TITLE.toString(), T.CREATE_GROUP_SUBTITLE.toString(), "") {
            @Override
            public void onClose(final Player player) {
                GroupListView.this.reopen();
            }
            
            @Override
            public boolean onResult(final String str) {
                if (str.contains(" ")) {
                    GroupListView.this.p.sendMessage(GroupListView.this.plugin.getPrefix() + T.CREATE_GROUP_NOSPACES);
                    return false;
                }
                if (str.contains("&")) {
                    GroupListView.this.p.sendMessage(GroupListView.this.plugin.getPrefix() + T.CREATE_GROUP_NOCOLORS);
                    return false;
                }
                if (GroupListView.this.plugin.getGroups().name(str).isPresent()) {
                    GroupListView.this.p.sendMessage(GroupListView.this.plugin.getPrefix() + T.CREATE_GROUP_NAMETAKEN);
                    return false;
                }
                GroupListView.this.plugin.newGroup(str).setPrefix("§a" + str).create();
                GroupListView.this.reopen();
                return true;
            }
        });
    }
    
    public void ReorderButton(final Button button) {
        button.material(XMaterial.ITEM_FRAME).name(Animation.fading(T.GUI_GROUPBROWSER_REORDER_TITLE.toString(), Colors.DarkCyan, Colors.Cyan)).lore(T.GUI_GROUPBROWSER_REORDER_ACTION.toString());
        button.action(p0 -> new GroupReorderView(this.p, this.plugin) {
            @Override
            public void onBack() {
                GroupListView.this.reopen();
            }
        });
    }
    
    public void ShowAllButton(final Button button) {
        button.material(XMaterial.ENDER_EYE).name(Animation.fading(T.GUI_GROUPBROWSER_SHOWALL_TITLE.toString(), Colors.DarkCyan, Colors.Cyan)).lore(T.GUI_GROUPBROWSER_SHOWALL_ACTION.get().options().asTextBlock(25, "§7"));
        button.action(p0 -> this.showAll = true);
    }
    
    @Override
    public Group[] getObjects() {
        final GroupList groups = this.plugin.getGroups();
        final GroupList worlds = groups.servers(true, this.plugin.getThisServer().map((Function<? super NServer, ? extends String>)NServer::getName).orElse(null)).worlds(true, this.p.getWorld().getName());
        this.allGroupsCount = groups.size();
        this.applicableGroupsCount = worlds.size();
        if (this.allGroupsCount == this.applicableGroupsCount) {
            this.showAll = true;
        }
        return (this.showAll ? groups : worlds).toArray(new Group[0]);
    }
    
    @Override
    public SearchFeature<Group> getSearch() {
        return new BasicSearch<Group>() {
            @Override
            public String[] getSearchableText(final Group group) {
                return new String[] { group.getName() };
            }
        };
    }
}
