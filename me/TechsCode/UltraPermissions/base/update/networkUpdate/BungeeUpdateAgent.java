

package me.TechsCode.EnderPermissions.base.update.networkUpdate;

import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.base.update.UpdateServer;
import java.util.ArrayList;
import net.md_5.bungee.api.ProxyServer;
import me.TechsCode.EnderPermissions.base.messaging.Message;
import net.md_5.bungee.api.config.ServerInfo;
import me.TechsCode.EnderPermissions.base.BungeeTechPlugin;
import me.TechsCode.EnderPermissions.base.messaging.BungeeMessagingListener;

public class BungeeUpdateAgent implements BungeeMessagingListener
{
    private BungeeTechPlugin plugin;
    
    public BungeeUpdateAgent(final BungeeTechPlugin plugin) {
        this.plugin = plugin;
        plugin.getMessagingService().register(this);
    }
    
    @Override
    public void onMessage(final ServerInfo serverInfo, final Message message) {
        if (message.getKey().equals("update")) {
            final ArrayList list = new ArrayList(ProxyServer.getInstance().getServers().values());
            list.remove(serverInfo);
            this.plugin.getMessagingService().send(message, (ServerInfo[])list.toArray(new ServerInfo[0]));
            final JsonObject data = message.getData();
            final String asString = data.get("updateServer").getAsString();
            final String asString2 = data.get("uid").getAsString();
            if (!this.plugin.getVersion().equals(data.get("version").getAsString())) {
                UpdateServer.requestAndPerform(this.plugin, asString, asString2);
            }
        }
    }
}
