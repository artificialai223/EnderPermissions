

package me.TechsCode.EnderPermissions.base.views;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import org.bukkit.event.EventHandler;
import java.util.Random;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.tpl.task.UpdateTime;
import me.TechsCode.EnderPermissions.tpl.task.UpdateEvent;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.gui.Button;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Model;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.base.gui.GUI;

public class RestartView extends GUI
{
    private boolean restarting;
    private ArrayList<Integer> red;
    private ArrayList<Integer> orange;
    private ArrayList<Integer> yellow;
    private ArrayList<Integer> green;
    
    public RestartView(final Player player, final SpigotTechPlugin spigotTechPlugin) {
        super(player, spigotTechPlugin);
        this.red = new ArrayList<Integer>();
        this.orange = new ArrayList<Integer>();
        this.yellow = new ArrayList<Integer>();
        this.green = new ArrayList<Integer>();
    }
    
    @Override
    protected void construct(final Model model) {
        model.setSlots(27);
        if (!this.restarting) {
            model.setTitle("Restart the Server");
            model.button(14, this::RestartButton);
            return;
        }
        model.setTitle("Restarting...");
        final Iterator<Integer> iterator = this.red.iterator();
        while (iterator.hasNext()) {
            model.button(iterator.next(), button -> this.TileButton(button, XMaterial.RED_STAINED_GLASS_PANE, Colors.Red));
        }
        final Iterator<Integer> iterator2 = this.orange.iterator();
        while (iterator2.hasNext()) {
            model.button(iterator2.next(), button2 -> this.TileButton(button2, XMaterial.ORANGE_STAINED_GLASS_PANE, Colors.Orange));
        }
        final Iterator<Integer> iterator3 = this.yellow.iterator();
        while (iterator3.hasNext()) {
            model.button(iterator3.next(), button3 -> this.TileButton(button3, XMaterial.YELLOW_STAINED_GLASS_PANE, Colors.Yellow));
        }
        final Iterator<Integer> iterator4 = this.green.iterator();
        while (iterator4.hasNext()) {
            model.button(iterator4.next(), button4 -> this.TileButton(button4, XMaterial.LIME_STAINED_GLASS_PANE, Colors.Lime));
        }
    }
    
    private void TileButton(final Button button, final XMaterial xMaterial, final HexColor hexColor) {
        button.material(xMaterial).name(Animation.wave("Restarting...", hexColor, Colors.White));
    }
    
    private void RestartButton(final Button button) {
        button.material(XMaterial.EMERALD_BLOCK).name(Animation.fading("Restart", Colors.Green, Colors.White)).lore("§7Click to restart the Server");
        button.action(p0 -> this.restarting = true);
    }
    
    @EventHandler
    public void update(final UpdateEvent updateEvent) {
        if (updateEvent.getUpdateTime() != UpdateTime.TICK) {
            return;
        }
        if (!this.restarting) {
            return;
        }
        if (this.green.size() == this.getCurrentSlots()) {
            Bukkit.shutdown();
            return;
        }
        int i;
        for (i = 0; i == 0 || this.green.contains(i); i = new Random().nextInt(this.getCurrentSlots()) + 1) {}
        if (this.yellow.contains(i)) {
            this.yellow.remove((Object)i);
            this.green.add(i);
            return;
        }
        if (this.orange.contains(i)) {
            this.orange.remove((Object)i);
            this.yellow.add(i);
            return;
        }
        if (this.red.contains(i)) {
            this.red.remove((Object)i);
            this.orange.add(i);
            return;
        }
        this.red.add(i);
    }
}
