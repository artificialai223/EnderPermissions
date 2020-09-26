

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import java.util.Optional;
import me.TechsCode.EnderPermissions.tpl.Tools;
import java.util.concurrent.TimeUnit;
import me.TechsCode.EnderPermissions.tpl.SkinTexture;
import me.TechsCode.EnderPermissions.base.gui.Button;
import java.util.Arrays;
import me.TechsCode.EnderPermissions.base.views.ConfirmationView;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Model;
import me.TechsCode.EnderPermissions.base.gui.BasicSearch;
import me.TechsCode.EnderPermissions.base.gui.SearchFeature;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.storage.objects.UserRankup;
import me.TechsCode.EnderPermissions.base.gui.PageableGUI;

public abstract class GroupUserListView extends PageableGUI<UserRankup>
{
    private final Group group;
    private final EnderPermissions plugin;
    
    public GroupUserListView(final Player player, final EnderPermissions plugin, final Group group) {
        super(player, plugin);
        this.group = group;
        this.plugin = plugin;
    }
    
    @Override
    public String getTitle() {
        return this.group.getName() + " > Users";
    }
    
    @Override
    public SearchFeature<UserRankup> getSearch() {
        return new BasicSearch<UserRankup>() {
            @Override
            public String[] getSearchableText(final UserRankup userRankup) {
                return new String[] { userRankup.getUser().getName() };
            }
        };
    }
    
    @Override
    public UserRankup[] getObjects() {
        return this.group.getRankups().toArray(new UserRankup[0]);
    }
    
    @Override
    protected void construct(final Model model) {
        super.construct(model);
        model.button(button -> {
            button.material(XMaterial.REDSTONE_BLOCK).name(Animation.fading("Wipe Users", Colors.DarkCyan, Colors.Cyan)).lore("§7Click to §cremove §7all Users from this Group");
            button.action(p0 -> new ConfirmationView(this.p, this.plugin, "Wipe all Users from " + this.group.getName()) {
                @Override
                public void choose(final boolean b) {
                    if (b) {
                        Arrays.stream(GroupUserListView.this.getObjects()).forEach(UserRankup::remove);
                    }
                    GroupUserListView.this.reopen();
                }
            });
        }, this.getRightOptionSlot());
    }
    
    @Override
    public void construct(final Button button, final UserRankup userRankup) {
        final Optional<SkinTexture> skinTexture = userRankup.getUser().getSkinTexture();
        if (skinTexture.isPresent()) {
            button.material(XMaterial.PLAYER_HEAD).setSkullTexture(skinTexture.get());
        }
        else {
            button.material(XMaterial.GRAY_STAINED_GLASS);
        }
        button.item().name(Animation.wave(userRankup.getUser().getName(), Colors.Blue, Colors.White)).lore("§7Click to §cremove §7User");
        if (userRankup.isPermanent()) {
            button.item().appendLore("", "§7This group is added §epermanently§7 to that user");
        }
        else {
            button.item().appendLore("").appendLore("§7Time until removal:").appendLore("§c" + Tools.getTimeString(userRankup.getExpiry() - System.currentTimeMillis(), TimeUnit.MILLISECONDS));
        }
        button.action(p1 -> userRankup.remove());
    }
}
