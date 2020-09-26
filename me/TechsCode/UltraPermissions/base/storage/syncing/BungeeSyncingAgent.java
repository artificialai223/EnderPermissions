

package me.TechsCode.EnderPermissions.base.storage.syncing;

import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.base.storage.Storage;
import java.util.ArrayList;
import net.md_5.bungee.api.ProxyServer;
import me.TechsCode.EnderPermissions.base.messaging.Message;
import net.md_5.bungee.api.config.ServerInfo;
import me.TechsCode.EnderPermissions.base.BungeeTechPlugin;
import me.TechsCode.EnderPermissions.base.messaging.BungeeMessagingListener;

public class BungeeSyncingAgent extends SyncingAgent implements BungeeMessagingListener
{
    private BungeeTechPlugin plugin;
    
    public BungeeSyncingAgent(final BungeeTechPlugin plugin) {
        this.plugin = plugin;
        plugin.getMessagingService().register(this);
    }
    
    @Override
    public void onMessage(final ServerInfo serverInfo, final Message message) {
        if (message.getKey().equals("cache")) {
            final ArrayList list = new ArrayList(ProxyServer.getInstance().getServers().values());
            list.remove(serverInfo);
            this.plugin.getMessagingService().send(message, (ServerInfo[])list.toArray(new ServerInfo[0]));
            this.executeLocalSynchronization(message.getData().get("model").getAsString(), message.getData().get("dataKey").getAsString());
        }
    }
    
    @Override
    public void notifyNewDataChanges(final Storage storage, final String s) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("model", storage.getModelName());
        jsonObject.addProperty("dataKey", s);
        this.plugin.getMessagingService().send(new Message("cache", jsonObject), new ServerInfo[0]);
    }
}
