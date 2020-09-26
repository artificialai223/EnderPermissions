

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.gui.SearchFeature;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.T;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.base.gui.PageableGUI;

public abstract class InheritedGroupListView extends PageableGUI<Group>
{
    private final EnderPermissions plugin;
    private final Group target;
    
    public InheritedGroupListView(final Player player, final EnderPermissions plugin, final Group target) {
        super(player, plugin);
        this.plugin = plugin;
        this.target = target;
    }
    
    @Override
    public String getTitle() {
        return this.target.getName() + " > " + T.INHERITED_GROUPS;
    }
    
    @Override
    public void construct(final Button button, final Group group) {
        if (this.target.getInheritedGroups().contains(group.toStored())) {
            this.InheritedButton(button, group);
        }
        else {
            this.NotInheritedButton(button, group);
        }
    }
    
    @Override
    public Group[] getObjects() {
        return this.plugin.getGroups().servers(true, this.target.getServer().orElse(null)).worlds(true, this.target.getWorld().orElse(null)).stream().filter(group -> !group.equals(this.target)).toArray(Group[]::new);
    }
    
    private void InheritedButton(final Button button, final Group group) {
        button.material(XMaterial.YELLOW_STAINED_GLASS_PANE).name(Animation.wave(group.getName(), Colors.Yellow, Colors.White)).lore(T.GUI_INHERITVIEW_REMOVE_ACTION.get().toString(), "", T.GUI_INHERITVIEW_REMOVE_EXPLANATION.get().options().vars(group.getName(), this.target.getName()).get());
        button.action(p1 -> this.target.removeInheritance(group));
    }
    
    private void NotInheritedButton(final Button button, final Group group) {
        button.material(XMaterial.GRAY_STAINED_GLASS_PANE).name(Animation.wave(group.getName(), Colors.Gray, Colors.White)).lore(T.GUI_INHERITVIEW_ADD_ACTION.get().toString(), "", T.GUI_INHERITVIEW_ADD_EXPLANATION.get().options().vars(group.getName(), this.target.getName()).get());
        button.action(p1 -> this.target.addInheritance(group));
    }
    
    @Override
    public SearchFeature<Group> getSearch() {
        return null;
    }
}
