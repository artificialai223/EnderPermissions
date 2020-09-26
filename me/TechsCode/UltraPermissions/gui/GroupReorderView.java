

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.gui.SearchFeature;
import org.bukkit.enchantments.Enchantment;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.gui.Button;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.base.gui.PageableGUI;

public abstract class GroupReorderView extends PageableGUI<Group>
{
    private final EnderPermissions plugin;
    private Group selected;
    
    public GroupReorderView(final Player player, final EnderPermissions plugin) {
        super(player, plugin);
        this.plugin = plugin;
        int priority = 1;
        final Iterator<Group> iterator = plugin.getGroups().bestToWorst().iterator();
        while (iterator.hasNext()) {
            iterator.next().setPriority(priority);
            ++priority;
        }
    }
    
    @Override
    public void construct(final Button button, final Group selected) {
        button.material(selected.getIcon()).name(Animation.wave(selected.getName(), Colors.Blue, Colors.White));
        if (this.selected != null) {
            if (this.selected.equals(selected)) {
                button.item().appendLore("§7Click to §cdeselect §7this group");
                button.item().addEnchantment(Enchantment.LUCK, 1);
            }
            else {
                button.item().appendLore("§7Click to swap with §e" + this.selected.getName());
            }
        }
        else {
            button.item().appendLore("§7Click to §agrab §7this group");
        }
        final int priority;
        button.action(p1 -> {
            if (this.selected != null) {
                if (!this.selected.equals(selected)) {
                    this.selected.getPriority();
                    this.selected.setPriority(selected.getPriority());
                    selected.setPriority(priority);
                }
                this.selected = null;
            }
            else {
                this.selected = selected;
            }
        });
    }
    
    @Override
    public Group[] getObjects() {
        return this.plugin.getGroups().bestToWorst().toArray(new Group[0]);
    }
    
    @Override
    public String getTitle() {
        return "Group Sorting (High to Low)";
    }
    
    @Override
    public SearchFeature<Group> getSearch() {
        return null;
    }
}
