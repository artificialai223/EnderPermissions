

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.gui.BasicSearch;
import me.TechsCode.EnderPermissions.base.gui.SearchFeature;
import me.TechsCode.EnderPermissions.base.views.TimePickerView;
import me.TechsCode.EnderPermissions.T;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.base.gui.PageableGUI;

public abstract class UserGroupAddView extends PageableGUI<Group>
{
    private final EnderPermissions plugin;
    private final User user;
    
    public UserGroupAddView(final Player player, final EnderPermissions plugin, final User user) {
        super(player, plugin);
        this.plugin = plugin;
        this.user = user;
    }
    
    @Override
    public Group[] getObjects() {
        return this.plugin.getGroups().stream().filter(group -> !this.user.getGroups().contains(group.toStored())).toArray(Group[]::new);
    }
    
    @Override
    public String getTitle() {
        return this.user.getName() + " > Add Group";
    }
    
    @Override
    public void construct(final Button button, final Group group) {
        button.material(group.getIcon()).name(Animation.wave(group.getName(), Colors.Green, Colors.White)).lore("§7Click to §aadd §7to §e" + this.user.getName());
        button.action(p1 -> new TimePickerView(this.p, this.plugin, T.DURATION + " >", "Permanent", true) {
            final /* synthetic */ Group val$group;
            
            @Override
            public void onResult(final long n) {
                if (n == 0L) {
                    UserGroupAddView.this.user.addGroup(this.val$group);
                }
                else {
                    UserGroupAddView.this.user.addGroup(this.val$group, System.currentTimeMillis() + n * 1000L);
                }
                UserGroupAddView.this.onBack();
            }
        });
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
