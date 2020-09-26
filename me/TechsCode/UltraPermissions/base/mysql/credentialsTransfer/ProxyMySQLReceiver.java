

package me.TechsCode.EnderPermissions.base.mysql.credentialsTransfer;

import me.TechsCode.EnderPermissions.base.mysql.MySQLRegistry;
import com.google.gson.JsonParseException;
import me.TechsCode.EnderPermissions.base.messaging.Message;
import net.md_5.bungee.api.config.ServerInfo;
import me.TechsCode.EnderPermissions.base.mysql.MySQLManager;
import me.TechsCode.EnderPermissions.base.BungeeTechPlugin;
import me.TechsCode.EnderPermissions.base.messaging.BungeeMessagingListener;

public class ProxyMySQLReceiver implements BungeeMessagingListener
{
    private BungeeTechPlugin plugin;
    private MySQLManager manager;
    
    public ProxyMySQLReceiver(final BungeeTechPlugin plugin, final MySQLManager manager) {
        this.plugin = plugin;
        this.manager = manager;
        plugin.getMessagingService().register(this);
    }
    
    @Override
    public void onMessage(final ServerInfo serverInfo, final Message obj) {
        if (!obj.getKey().equals("mysql")) {
            return;
        }
        final boolean asBoolean = obj.getData().get("force").getAsBoolean();
        final MySQLRegistry registry = this.manager.getRegistry();
        if (!asBoolean) {
            if (registry.hasCredentials()) {
                return;
            }
        }
        try {
            registry.updateState(obj.getData().getAsJsonObject("state"));
            if (!asBoolean) {
                this.manager.setup();
            }
        }
        catch (JsonParseException ex) {
            this.plugin.log("Can not process received MySQL Credentials.. is everything up-to-date?");
            this.plugin.log("Error Message: " + ex.getMessage());
            this.plugin.log("Content (May Contain Sensitive Information): " + obj);
        }
    }
}
