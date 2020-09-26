

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.gui.settings.UpermsSettingsView;
import java.util.concurrent.TimeUnit;
import me.TechsCode.EnderPermissions.base.visual.LoreScroller;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.ColorPalette;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.T;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.base.gui.Model;
import java.util.Optional;
import me.TechsCode.EnderPermissions.base.visual.Text;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.util.Objects;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.tpl.SkinTexture;
import java.util.List;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.gui.GUI;

public class Overview extends GUI
{
    private final EnderPermissions plugin;
    private final long start;
    private boolean expanded;
    private List<SkinTexture> skinTextures;
    private List<String> playerList;
    private List<String> groupList;
    
    public Overview(final Player player, final EnderPermissions plugin) {
        super(player, plugin);
        this.expanded = false;
        this.plugin = plugin;
        this.start = System.currentTimeMillis();
        this.onOpen();
    }
    
    private void onOpen() {
        this.skinTextures = this.plugin.getUsers().stream().map(user -> user.getSkinTexture().orElse(null)).filter(Objects::nonNull).collect((Collector<? super Object, ?, List<SkinTexture>>)Collectors.toList());
        String s = null;
        final Optional<Group> optional;
        this.playerList = this.plugin.getUsers().stream().map(user2 -> {
            if (user2.getPrefix().isPresent()) {
                s = user2.getPrefix().get() + " ";
            }
            if (s.isEmpty()) {
                user2.getActiveGroups().bestToWorst().stream().findFirst();
                if (optional.isPresent() && optional.get().getPrefix().isPresent()) {
                    s = optional.get().getPrefix().get() + " ";
                }
            }
            return ("§7- " + Text.color(s) + "§f" + user2.getName()).trim();
        }).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
        this.groupList = this.plugin.getGroups().stream().map(group -> ("§7- §f" + Text.color(group.getPrefix().orElse("")) + " §f" + group.getName() + " §f" + Text.color(group.getSuffix().orElse(""))).trim()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    @Override
    public void reopen() {
        this.onOpen();
        super.reopen();
    }
    
    public void construct(final Model model) {
        model.setTitle("Ultra Permissions v" + this.plugin.getVersion());
        model.setSlots(this.expanded ? 54 : 45);
        model.button(this::UsersButton, this.expanded ? 12 : 21);
        model.button(this::GroupsButton, this.expanded ? 16 : 25);
        if (this.expanded) {
            model.button(this::PermissionLogButton, 31);
            model.button(this::SettingsButton, 33);
            model.button(this::ReduceButton, 50);
        }
        else {
            model.button(this::ExpandButton, 41);
        }
    }
    
    private void UsersButton(final Button button) {
        button.material(XMaterial.PLAYER_HEAD).name(Animation.wave(T.USERS.toString(), ColorPalette.MAIN, Colors.White)).lore(T.GUI_OVERVIEW_USERS_ACTION.get().toString());
        button.item().appendLore("", "");
        final String[] scroller = LoreScroller.scroller(this.playerList.toArray(new String[0]), 8, this.start);
        for (int length = scroller.length, i = 0; i < length; ++i) {
            button.item().appendLore(scroller[i]);
        }
        button.item().appendLore("");
        button.item().appendLore("§8§o" + this.playerList.size() + " Players registered so far");
        if (!this.skinTextures.isEmpty()) {
            button.item().setSkullTexture(this.skinTextures.get((int)(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) % this.skinTextures.size())));
        }
        button.action(p0 -> new UserListView(this.p, this.plugin) {
            @Override
            public void onBack() {
                Overview.this.reopen();
            }
        });
    }
    
    private void GroupsButton(final Button button) {
        button.material(XMaterial.BOOKSHELF).name(Animation.wave(T.GROUPS.toString(), ColorPalette.MAIN, Colors.White)).lore(T.GUI_OVERVIEW_GROUPS_ACTION.get().toString());
        button.item().appendLore("", "");
        final String[] scroller = LoreScroller.scroller(this.groupList.toArray(new String[0]), 8, this.start);
        for (int length = scroller.length, i = 0; i < length; ++i) {
            button.item().appendLore(scroller[i]);
        }
        button.item().appendLore("");
        button.item().appendLore("§8§o" + this.groupList.size() + " Groups created so far");
        button.action(p0 -> new GroupListView(this.p, this.plugin) {
            @Override
            public void onBack() {
                Overview.this.reopen();
            }
        });
    }
    
    private void PermissionLogButton(final Button button) {
        button.material(XMaterial.WRITABLE_BOOK).name(Animation.wave(T.PERMISSIONLOG.toString(), ColorPalette.MAIN, Colors.White)).lore(T.GUI_OVERVIEW_PERMISSIONLOG_ACTION.get().toString(), "", T.GUI_OVERVIEW_PERMISSIONLOG_EXPLANATION.get().toString());
        button.action(p0 -> new PermissionLogViewer(this.p, this.plugin) {
            @Override
            public void onBack() {
                Overview.this.reopen();
            }
        });
    }
    
    private void SettingsButton(final Button button) {
        button.material(XMaterial.COMMAND_BLOCK).name(Animation.wave(T.SETTINGS.toString(), ColorPalette.MAIN, Colors.White)).lore(T.GUI_OVERVIEW_SETTINGS_ACTION.get().toString(), "", T.GUI_OVERVIEW_SETTINGS_EXPLANATION.get().toString());
        button.action(p0 -> new UpermsSettingsView(this.p, this.plugin) {
            @Override
            public void onBack() {
                Overview.this.reopen();
            }
        });
    }
    
    private void ReduceButton(final Button button) {
        button.material(XMaterial.TRIPWIRE_HOOK).name(Animation.wave(T.GUI_OVERVIEW_REDUCE_TITLE.toString(), ColorPalette.MAIN, Colors.White)).lore(T.GUI_OVERVIEW_REDUCE_ACTION.get().toString(), "", T.GUI_OVERVIEW_REDUCE_EXPLANATION.get().toString());
        button.action(p0 -> this.expanded = false);
    }
    
    private void ExpandButton(final Button button) {
        button.material(XMaterial.TRIPWIRE_HOOK).name(Animation.wave(T.GUI_OVERVIEW_EXPAND_TITLE.toString(), ColorPalette.MAIN, Colors.White)).lore(T.GUI_OVERVIEW_EXPAND_ACTION.get().toString(), "", T.GUI_OVERVIEW_EXPAND_EXPLANATION.get().toString());
        button.action(p0 -> this.expanded = true);
    }
}
