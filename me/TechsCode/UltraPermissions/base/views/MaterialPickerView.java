

package me.TechsCode.EnderPermissions.base.views;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.gui.BasicSearch;
import me.TechsCode.EnderPermissions.base.gui.SearchFeature;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.gui.Button;
import java.util.Optional;
import org.bukkit.inventory.Inventory;
import java.util.function.Function;
import java.util.Comparator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.PageableGUI;

public abstract class MaterialPickerView extends PageableGUI<XMaterial>
{
    private String title;
    private XMaterial[] materials;
    
    public MaterialPickerView(final Player player, final SpigotTechPlugin spigotTechPlugin, final String title) {
        super(player, spigotTechPlugin);
        this.title = title;
        final ArrayList<Object> list = new ArrayList<Object>();
        final Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 9, "Testing");
        for (final XMaterial xMaterial : XMaterial.values()) {
            final Optional<ItemStack> asItemStack = xMaterial.getAsItemStack();
            if (asItemStack.isPresent()) {
                inventory.setItem(0, (ItemStack)asItemStack.get());
                if (inventory.getItem(0) != null) {
                    inventory.setItem(0, (ItemStack)null);
                    list.add(xMaterial);
                }
            }
        }
        this.materials = list.stream().sorted(Comparator.comparing((Function<? super Object, ? extends Comparable>)Enum::name)).toArray(XMaterial[]::new);
    }
    
    @Override
    public String getTitle() {
        return this.title;
    }
    
    @Override
    public void construct(final Button button, final XMaterial xMaterial) {
        button.material(xMaterial).name(Animation.wave(xMaterial.getHumanName(), Colors.Green, Colors.White)).lore("§bClick §7to choose");
        button.action(p1 -> this.choose(this.p, xMaterial));
    }
    
    @Override
    public XMaterial[] getObjects() {
        return this.materials;
    }
    
    public abstract void choose(final Player p0, final XMaterial p1);
    
    @Override
    public SearchFeature<XMaterial> getSearch() {
        return new BasicSearch<XMaterial>() {
            @Override
            public String[] getSearchableText(final XMaterial xMaterial) {
                return new String[] { xMaterial.getHumanName() };
            }
        };
    }
}
