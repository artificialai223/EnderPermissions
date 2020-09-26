

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.base.gui.SearchFeature;
import me.TechsCode.EnderPermissions.base.gui.Model;
import java.util.Iterator;
import java.util.Optional;
import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.tpl.Tools;
import java.util.concurrent.TimeUnit;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.storage.objects.UserRankup;
import me.TechsCode.EnderPermissions.T;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.tpl.SkinTexture;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Button;
import java.util.Arrays;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.CachedOnlineChecker;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.base.gui.PageableGUI;

public abstract class UserListView extends PageableGUI<User>
{
    private EnderPermissions plugin;
    public boolean onlineOnly;
    private CachedOnlineChecker onlineChecker;
    
    public UserListView(final Player player, final EnderPermissions plugin) {
        super(player, plugin);
        this.plugin = plugin;
        this.onlineOnly = true;
        this.onlineChecker = CachedOnlineChecker.scan();
    }
    
    @Override
    public String getTitle() {
        return "Overview > Users";
    }
    
    public User[] getUsers() {
        return this.plugin.getUsers().toArray(new User[0]);
    }
    
    @Override
    public User[] getObjects() {
        return Arrays.stream(this.getUsers()).filter(user -> !this.onlineOnly || this.onlineChecker.isOnline(user.getUuid())).toArray(User[]::new);
    }
    
    @Override
    public void construct(final Button button, final User user) {
        final Optional<SkinTexture> skinTexture = user.getSkinTexture();
        if (skinTexture.isPresent()) {
            button.material(XMaterial.PLAYER_HEAD).setSkullTexture(skinTexture.get());
        }
        else {
            button.material(XMaterial.GRAY_STAINED_GLASS);
        }
        button.item().name(Animation.wave(user.getName(), Colors.Orange, Colors.Yellow, Colors.White)).appendLore(T.GUI_USERBROWSER_ACTION.get().toString());
        if (!this.onlineChecker.isOnline(user.getUuid())) {
            button.item().appendLore(T.GUI_USERBROWSER_DELETE_ACTION.get().toString());
        }
        if (user.isSuperadmin()) {
            button.item().appendLore("", T.GUI_USERBROWSER_SUPERADMIN_INDICATOR.get().toString());
        }
        button.item().appendLore("", "§7" + T.PREFIX + ": §f" + user.getPrefix().orElse("§c" + T.NONE), "§7" + T.SUFFIX + ": §f" + user.getSuffix().orElse("§c" + T.NONE), "", "§7" + T.GROUPS + ":");
        int n = 1;
        for (final UserRankup userRankup : user.getRankups()) {
            final Optional<Group> value = userRankup.getGroup().get();
            if (!value.isPresent()) {
                continue;
            }
            if (userRankup.isPermanent()) {
                button.item().appendLore("§7" + n + ". §e" + value.get().getName());
            }
            else {
                button.item().appendLore("§7" + n + ". §e" + value.get().getName() + " §7" + T.TIME_FOR + " §c" + Tools.getTimeString(userRankup.getLeftDuration(), TimeUnit.MILLISECONDS, 2));
            }
            ++n;
        }
        if (n == 1) {
            button.item().appendLore("§7- §c" + T.NONE);
        }
        if (!skinTexture.isPresent()) {
            button.item().appendLore("", "§8Head will be shown after the next connect");
        }
        button.action(actionType -> {
            if (actionType == ActionType.Q && !this.onlineChecker.isOnline(user.getUuid())) {
                user.remove();
            }
            else {
                new UserView(this.p, this.plugin, user) {
                    @Override
                    public void onBack() {
                        UserListView.this.reopen();
                    }
                };
            }
        });
    }
    
    @Override
    protected void construct(final Model model) {
        super.construct(model);
        model.button(this.getRightOptionSlot(), button -> {
            button.material(this.onlineOnly ? XMaterial.ENDER_EYE : XMaterial.ENDER_PEARL).name(Animation.wave("Visibility", Colors.Blue, Colors.White)).lore("§7Click to §e" + (this.onlineOnly ? "show" : "hide") + " §7Offline Users");
            button.action(p0 -> this.onlineOnly = !this.onlineOnly);
        });
    }
    
    @Override
    public int getCurrentSlots() {
        return 54;
    }
    
    @Override
    public SearchFeature<User> getSearch() {
        return new SearchFeature<User>(XMaterial.NAME_TAG, "Search User", "§7Click to type in a name") {
            @Override
            public String[] getSearchableText(final User user) {
                return new String[] { user.getName() };
            }
        };
    }
}
