

package me.TechsCode.EnderPermissions.base.dialog;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.TechsCode.EnderPermissions.tpl.titleAndActionbar.Title;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.base.scheduler.RecurringTask;
import me.TechsCode.EnderPermissions.tpl.titleAndActionbar.ActionBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class Dialog implements Listener, Runnable
{
    private final Player p;
    private final ActionBar actionBar;
    private String lastMainTitle;
    private String lastSubTitle;
    private final RecurringTask visualTask;
    
    public Dialog(final Player p2, final SpigotTechPlugin spigotTechPlugin) {
        this.p = p2;
        this.actionBar = new ActionBar(spigotTechPlugin);
        spigotTechPlugin.getScheduler().run(p2::closeInventory);
        this.visualTask = spigotTechPlugin.getScheduler().runTaskTimer(this, 1L, 1L);
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)((TechPlugin<Plugin>)spigotTechPlugin).getBootstrap());
    }
    
    public abstract void onClose(final Player p0);
    
    public abstract boolean onInput(final String p0);
    
    public abstract String getMainTitle();
    
    public abstract String getSubTitle();
    
    public abstract String getActionBar();
    
    public void run() {
        final String mainTitle = this.getMainTitle();
        final String subTitle = this.getSubTitle();
        final String actionBar = this.getActionBar();
        if (this.lastMainTitle == null || this.lastSubTitle == null || !this.lastMainTitle.equals(mainTitle) || !this.lastSubTitle.equals(subTitle)) {
            Title.sendTitle(this.p, 30, 6000, 0, mainTitle, subTitle);
            this.lastMainTitle = mainTitle;
            this.lastSubTitle = subTitle;
        }
        if (actionBar != null) {
            this.actionBar.sendActionBar(this.p, actionBar);
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(final AsyncPlayerChatEvent asyncPlayerChatEvent) {
        if (asyncPlayerChatEvent.getPlayer().equals(this.p)) {
            this.onInput(asyncPlayerChatEvent.getMessage());
            asyncPlayerChatEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void close(final PlayerQuitEvent playerQuitEvent) {
        if (playerQuitEvent.getPlayer().equals(this.p)) {
            this.close(false);
        }
    }
    
    @EventHandler
    public void close(final InventoryOpenEvent inventoryOpenEvent) {
        if (inventoryOpenEvent.getPlayer().equals(this.p)) {
            this.close(false);
        }
    }
    
    public void close(final boolean b) {
        HandlerList.unregisterAll((Listener)this);
        this.visualTask.stop();
        if (!b) {
            this.onClose(this.p);
        }
        Title.clearTitle(this.p);
        this.actionBar.sendActionBar(this.p, "");
    }
}
