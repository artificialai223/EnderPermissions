

package me.TechsCode.EnderPermissions.base.update;

import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import java.util.concurrent.TimeUnit;
import me.TechsCode.EnderPermissions.tpl.task.UpdateTime;
import me.TechsCode.EnderPermissions.tpl.task.UpdateEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.util.UUID;
import me.TechsCode.EnderPermissions.base.misc.Callback;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.event.Listener;

public class UpdateProcess implements Listener
{
    private final SpigotTechPlugin plugin;
    private final Player p;
    private final String updateServer;
    private final String version;
    private final String uid;
    private final long start;
    private final Callback<CloseReason> callback;
    
    public UpdateProcess(final SpigotTechPlugin plugin, final Player p5, final String s, final String version, final Callback<CloseReason> callback) {
        this.plugin = plugin;
        this.p = p5;
        this.updateServer = s;
        this.version = version;
        this.uid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        this.start = System.currentTimeMillis();
        this.callback = callback;
        p5.sendMessage("");
        p5.sendMessage(plugin.getPrefix() + "§7Click the link below to authenticate using §bDiscord§7:");
        p5.sendMessage("§e" + s + "/authenticate?uid=" + this.uid);
        p5.sendMessage("");
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)((TechPlugin<Plugin>)plugin).getBootstrap());
    }
    
    @EventHandler
    public void check(final UpdateEvent updateEvent) {
        if (updateEvent.getUpdateTime() != UpdateTime.SLOW) {
            return;
        }
        if (TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - this.start) >= 15L) {
            this.close(CloseReason.TIME_OUT);
            return;
        }
        switch (UpdateServer.requestAndPerform(this.plugin, this.updateServer, this.uid).getType()) {
            case NOT_VERIFIED: {
                this.p.sendMessage(this.plugin.getPrefix() + "§7You are not verified on our §bDiscord");
                this.close(CloseReason.NO_REQUIREMENTS);
                break;
            }
            case NOT_PURCHASED: {
                this.p.sendMessage(this.plugin.getPrefix() + "§7You do not own this plugin. §ePurchase it§7 to unlock this feature");
                this.close(CloseReason.NO_REQUIREMENTS);
                break;
            }
            case SUCCESS: {
                this.p.sendMessage(this.plugin.getPrefix() + "§7Updated the plugin to §e" + this.version + "§7, reloading..");
                this.plugin.getUpdateAgent().sendUpdateRequestToBungee(this.updateServer, this.uid, this.version);
                this.close(CloseReason.COMPLETE);
                break;
            }
            case SERVER_OFFLINE: {
                this.p.sendMessage(this.plugin.getPrefix() + "§7There was an error communicating with our update Server");
                this.close(CloseReason.SERVER_OFFLINE);
                break;
            }
        }
    }
    
    @EventHandler
    public void leave(final PlayerQuitEvent playerQuitEvent) {
        this.close(CloseReason.QUIT);
    }
    
    private void close(final CloseReason closeReason) {
        HandlerList.unregisterAll((Listener)this);
        this.callback.run(closeReason);
    }
    
    enum CloseReason
    {
        QUIT, 
        COMPLETE, 
        NO_REQUIREMENTS, 
        TIME_OUT, 
        SERVER_OFFLINE;
    }
}
