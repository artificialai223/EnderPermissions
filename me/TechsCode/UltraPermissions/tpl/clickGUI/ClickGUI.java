

package me.TechsCode.EnderPermissions.tpl.clickGUI;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.util.Arrays;
import org.bukkit.Location;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.tpl.utils.Armorstands;
import org.bukkit.util.Vector;
import java.util.Map;
import org.bukkit.event.EventHandler;
import me.TechsCode.EnderPermissions.tpl.task.UpdateTime;
import me.TechsCode.EnderPermissions.tpl.task.UpdateEvent;
import org.bukkit.entity.ArmorStand;
import java.util.HashMap;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class ClickGUI implements Listener
{
    protected Player p;
    private SpigotTechPlugin plugin;
    private HashMap<FloatingElement, ArmorStand> items;
    private boolean opened;
    private double offsetDegree;
    private FloatingElement currentlyHovering;
    
    public ClickGUI(final Player p2, final SpigotTechPlugin plugin) {
        this.p = p2;
        this.plugin = plugin;
        this.items = new HashMap<FloatingElement, ArmorStand>();
        this.opened = false;
    }
    
    @EventHandler
    public void draw(final UpdateEvent updateEvent) {
        if (updateEvent.getUpdateTime() != UpdateTime.TWOTICKS) {
            return;
        }
        this.redraw();
    }
    
    protected void redraw() {
        if (this.isFixed()) {
            this.offsetDegree = this.p.getLocation().getYaw();
        }
        double n = 0.0;
        FloatingElement currentlyHovering = null;
        for (final Map.Entry<FloatingElement, ArmorStand> entry : this.items.entrySet()) {
            final FloatingElement floatingElement3 = entry.getKey();
            final Location eyeLocation = this.p.getEyeLocation();
            eyeLocation.add(this.p.getEyeLocation().getDirection().multiply(floatingElement3.getRadius()));
            final double radians = Math.toRadians(this.offsetDegree + 90.0 + floatingElement3.getX() * 10.0);
            final double cos = Math.cos(radians);
            final double y = floatingElement3.getY();
            final Vector vector = new Vector(cos, 0.0, Math.sin(radians));
            vector.multiply(floatingElement3.getRadius());
            final Location eyeLocation2 = this.p.getEyeLocation();
            eyeLocation2.add(vector);
            eyeLocation2.subtract(0.0, y, 0.0);
            if (floatingElement3.isShown()) {
                final double distance = eyeLocation2.distance(eyeLocation);
                if (currentlyHovering == null || distance < n) {
                    n = distance;
                    currentlyHovering = entry.getKey();
                }
            }
            eyeLocation2.subtract(0.0, floatingElement3.getYOffset(), 0.0);
            ArmorStand value = entry.getValue();
            if (entry.getKey().isShown()) {
                if (value == null) {
                    value = (ArmorStand)eyeLocation2.getWorld().spawn(eyeLocation2, (Class)ArmorStand.class);
                    value.setGravity(false);
                    value.setVisible(false);
                    value.setBasePlate(false);
                    entry.setValue(value);
                }
                else {
                    Armorstands.move(value, eyeLocation2);
                }
            }
            else if (value != null) {
                if (value.getPassenger() != null) {
                    value.getPassenger().remove();
                }
                value.remove();
                entry.setValue(null);
            }
            entry.getKey().apply(value);
        }
        if (n < 0.5) {
            (this.currentlyHovering = currentlyHovering).hovering(true);
        }
        else {
            this.currentlyHovering = null;
        }
        this.items.keySet().stream().filter(floatingElement2 -> this.currentlyHovering == null || !floatingElement2.equals(this.currentlyHovering)).forEach(floatingElement -> floatingElement.hovering(false));
    }
    
    protected void addItem(final FloatingElement key) {
        this.items.put(key, null);
    }
    
    protected void removeItem(final FloatingElement floatingElement) {
        final ArmorStand armorStand = this.items.get(floatingElement);
        if (armorStand != null) {
            if (armorStand.getPassenger() != null) {
                armorStand.getPassenger().remove();
            }
            armorStand.remove();
        }
        this.items.remove(floatingElement);
    }
    
    protected FloatingElement[] getItems() {
        return this.items.keySet().toArray(new FloatingElement[this.items.size()]);
    }
    
    protected void clear() {
        Arrays.stream(this.getItems()).forEach(floatingElement -> this.removeItem(floatingElement));
    }
    
    protected void open() {
        if (this.opened) {
            return;
        }
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)((TechPlugin<Plugin>)this.plugin).getBootstrap());
        this.opened = true;
        this.offsetDegree = this.p.getLocation().getYaw();
    }
    
    protected void close() {
        if (!this.opened) {
            return;
        }
        HandlerList.unregisterAll((Listener)this);
        Arrays.stream(this.getItems()).forEach(floatingElement -> this.removeItem(floatingElement));
    }
    
    @EventHandler
    public void interact(final PlayerInteractEvent playerInteractEvent) {
        if (!playerInteractEvent.getPlayer().equals(this.p)) {
            return;
        }
        if (playerInteractEvent.getAction() == Action.LEFT_CLICK_AIR && this.currentlyHovering != null) {
            this.currentlyHovering.click();
        }
    }
    
    protected FloatingElement getCursor() {
        return this.currentlyHovering;
    }
    
    public abstract boolean isFixed();
}
