

package me.TechsCode.EnderPermissions.base.gui;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import java.util.List;
import java.util.HashMap;
import org.bukkit.inventory.Inventory;
import me.TechsCode.EnderPermissions.base.scheduler.RecurringTask;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class GUI implements Listener
{
    protected Player p;
    private final SpigotTechPlugin plugin;
    private boolean stopped;
    private RecurringTask recurringTask;
    private Inventory inventory;
    private HashMap<Integer, List<Action>> actions;
    
    public GUI(final Player p2, final SpigotTechPlugin plugin) {
        this.p = p2;
        this.plugin = plugin;
        Model model;
        int i = 0;
        final HashMap<Object, Object> hashMap;
        Button button;
        this.recurringTask = plugin.getScheduler().runTaskTimer(() -> {
            if (this.stopped) {
                this.recurringTask.stop();
                HandlerList.unregisterAll((Listener)this);
                return;
            }
            else {
                model = new Model();
                model.setTitle(this.getCurrentTitle());
                model.setSlots(this.getCurrentSlots());
                this.construct(model);
                if (this.inventory == null || !p2.getOpenInventory().getTitle().equals(model.getTitle()) || this.inventory.getSize() != model.getSlots()) {
                    p2.openInventory(this.inventory = Bukkit.createInventory((InventoryHolder)null, model.getSlots(), model.getTitle()));
                }
                model.getEntries();
                while (i <= this.inventory.getSize()) {
                    if (!hashMap.containsKey(i)) {
                        this.inventory.clear(i - 1);
                    }
                    ++i;
                }
                this.actions = new HashMap<Integer, List<Action>>();
                hashMap.forEach((n, entry) -> {
                    button = new Button();
                    entry.button(button);
                    this.inventory.setItem(n - 1, button.item().get());
                    this.actions.put(n - 1, button.getActions());
                    return;
                });
                p2.updateInventory();
                return;
            }
        }, 0L, 1L);
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)((TechPlugin<Plugin>)plugin).getBootstrap());
    }
    
    public void preventOpening() {
        this.stopped = true;
    }
    
    protected abstract void construct(final Model p0);
    
    public void reopen() {
        if (this.inventory != null) {
            return;
        }
        this.recurringTask.start();
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)((TechPlugin<Plugin>)this.plugin).getBootstrap());
    }
    
    private void close() {
        this.inventory = null;
        this.recurringTask.stop();
        HandlerList.unregisterAll((Listener)this);
        this.onClose();
    }
    
    @EventHandler
    public void click(final InventoryClickEvent inventoryClickEvent) {
        if (!inventoryClickEvent.getWhoClicked().equals(this.p)) {
            return;
        }
        if (inventoryClickEvent.getSlotType() == InventoryType.SlotType.OUTSIDE) {
            inventoryClickEvent.setCancelled(true);
            return;
        }
        inventoryClickEvent.setCancelled(true);
        if (!(inventoryClickEvent.getClickedInventory() instanceof PlayerInventory) && this.actions.containsKey(inventoryClickEvent.getSlot())) {
            this.actions.get(inventoryClickEvent.getSlot()).forEach(action -> action.onClick(ActionType.fromClickType(inventoryClickEvent.getClick())));
        }
    }
    
    @EventHandler
    public void drag(final InventoryDragEvent inventoryDragEvent) {
        if (!inventoryDragEvent.getWhoClicked().equals(this.p)) {
            return;
        }
        if (inventoryDragEvent.getInventory() instanceof PlayerInventory) {
            return;
        }
        if (inventoryDragEvent.getInventorySlots().size() > 1) {
            inventoryDragEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void leave(final PlayerQuitEvent playerQuitEvent) {
        if (playerQuitEvent.getPlayer().equals(this.p)) {
            this.close();
        }
    }
    
    @EventHandler
    public void close(final InventoryCloseEvent inventoryCloseEvent) {
        if (this.inventory != null && this.inventory.equals(inventoryCloseEvent.getInventory())) {
            this.close();
        }
    }
    
    public int getCurrentSlots() {
        return 27;
    }
    
    public String getCurrentTitle() {
        return "Unnamed Inventory";
    }
    
    public void onClose() {
    }
}
