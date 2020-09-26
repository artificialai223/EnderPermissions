

package me.TechsCode.EnderPermissions.base.mysql.credentialsTransfer;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import me.TechsCode.EnderPermissions.tpl.task.UpdateTime;
import me.TechsCode.EnderPermissions.tpl.task.UpdateEvent;
import me.TechsCode.EnderPermissions.base.messaging.QueuedMessage;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.base.mysql.MySQLManager;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.event.Listener;

public class SpigotMySQLSender implements Listener
{
    private SpigotTechPlugin plugin;
    private MySQLManager manager;
    
    public SpigotMySQLSender(final SpigotTechPlugin plugin, final MySQLManager manager) {
        this.plugin = plugin;
        this.manager = manager;
        this.sendMySQLCredentials(false);
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)((TechPlugin<Plugin>)plugin).getBootstrap());
    }
    
    private void sendMySQLCredentials(final boolean b) {
        if (this.manager.isEnabled()) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("force", Boolean.valueOf(b));
            jsonObject.add("state", (JsonElement)this.manager.getRegistry().getState());
            this.plugin.getMessagingService().send(new QueuedMessage("mysql", jsonObject) {
                @Override
                public void onSend() {
                }
            });
        }
    }
    
    @EventHandler
    public void send(final UpdateEvent updateEvent) {
        if (updateEvent.getUpdateTime() != UpdateTime.HALFMIN) {
            return;
        }
        this.sendMySQLCredentials(false);
    }
    
    @EventHandler
    public void join(final PlayerJoinEvent playerJoinEvent) {
        this.sendMySQLCredentials(false);
    }
    
    public void forceUpdateBungeeMySQLCredentials() {
        this.sendMySQLCredentials(true);
    }
}
