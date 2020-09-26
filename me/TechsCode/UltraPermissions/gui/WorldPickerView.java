

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Button;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.base.gui.BasicSearch;
import me.TechsCode.EnderPermissions.base.gui.SearchFeature;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import org.bukkit.World;
import me.TechsCode.EnderPermissions.base.gui.PageableGUI;

public abstract class WorldPickerView extends PageableGUI<World>
{
    public WorldPickerView(final Player player, final SpigotTechPlugin spigotTechPlugin) {
        super(player, spigotTechPlugin);
    }
    
    public abstract void onSelect(final World p0);
    
    @Override
    public SearchFeature<World> getSearch() {
        return new BasicSearch<World>() {
            @Override
            public String[] getSearchableText(final World world) {
                return new String[] { world.getName() };
            }
        };
    }
    
    @Override
    public World[] getObjects() {
        return Bukkit.getWorlds().toArray(new World[0]);
    }
    
    @Override
    public void construct(final Button button, final World world) {
        button.material(XMaterial.YELLOW_STAINED_GLASS).name(Animation.wave(world.getName(), Colors.Yellow, Colors.White)).lore("§7Click to select");
        button.action(p1 -> this.onSelect(world));
    }
}
