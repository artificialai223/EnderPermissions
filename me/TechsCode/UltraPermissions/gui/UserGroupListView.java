

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.gui.BasicSearch;
import me.TechsCode.EnderPermissions.base.gui.SearchFeature;
import java.util.Optional;
import me.TechsCode.EnderPermissions.tpl.Tools;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Model;
import me.TechsCode.EnderPermissions.T;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.EnderPermissions;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.storage.objects.UserRankup;
import me.TechsCode.EnderPermissions.base.gui.PageableGUI;

public abstract class UserGroupListView extends PageableGUI<UserRankup>
{
    private Player p;
    private final EnderPermissions plugin;
    private final User user;
    
    public UserGroupListView(final Player p3, final EnderPermissions plugin, final User user) {
        super(p3, plugin);
        this.p = p3;
        this.plugin = plugin;
        this.user = user;
    }
    
    @Override
    public String getTitle() {
        return this.user.getName() + " > " + T.GROUPS;
    }
    
    @Override
    protected void construct(final Model model) {
        super.construct(model);
        model.button(button -> {
            button.material(XMaterial.ANVIL).name(Animation.fading(T.ADD.toString(), Colors.DarkCyan, Colors.Cyan)).lore("§7Click to add group");
            button.action(p0 -> new UserGroupAddView(this.p, this.plugin, this.user) {
                @Override
                public void onBack() {
                    UserGroupListView.this.reopen();
                }
            });
        }, this.getRightOptionSlot());
    }
    
    @Override
    public void construct(final Button button, final UserRankup userRankup) {
        final Optional<Group> value = userRankup.getGroup().get();
        button.material(value.map((Function<? super Group, ?>)Group::getIcon).orElse(XMaterial.STONE)).name(Animation.wave(value.map((Function<? super Group, ?>)Group::getName).orElse("INVALID"), Colors.Yellow, Colors.White)).lore("§7Click to §cremove §7group");
        if (!userRankup.isPermanent()) {
            button.item().appendLore("").appendLore("§7Time until removal:").appendLore("§c" + Tools.getTimeString(userRankup.getExpiry() - System.currentTimeMillis(), TimeUnit.MILLISECONDS));
        }
        button.action(p1 -> userRankup.remove());
    }
    
    @Override
    public UserRankup[] getObjects() {
        return this.user.getRankups().toArray(new UserRankup[0]);
    }
    
    @Override
    public SearchFeature<UserRankup> getSearch() {
        return new BasicSearch<UserRankup>() {
            @Override
            public String[] getSearchableText(final UserRankup userRankup) {
                return new String[] { userRankup.getGroup().get().map((Function<? super Group, ? extends String>)Group::getName).orElse("xxx") };
            }
        };
    }
}
