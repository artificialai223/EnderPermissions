

package me.TechsCode.EnderPermissions.base.storage.syncing;

import me.TechsCode.EnderPermissions.base.messaging.QueuedMessage;
import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.base.storage.Storage;
import me.TechsCode.EnderPermissions.base.messaging.Message;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.base.messaging.SpigotMessagingListener;

public class SpigotSyncingAgent extends SyncingAgent implements SpigotMessagingListener
{
    private SpigotTechPlugin plugin;
    
    public SpigotSyncingAgent(final SpigotTechPlugin plugin) {
        this.plugin = plugin;
        plugin.getMessagingService().register(this);
    }
    
    @Override
    public void onMessage(final Message message) {
        if (message.getKey().equals("cache")) {
            this.plugin.getScheduler().runAsync(() -> this.executeLocalSynchronization(message.getData().get("model").getAsString(), message.getData().get("dataKey").getAsString()));
        }
    }
    
    @Override
    public void notifyNewDataChanges(final Storage storage, final String s) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("model", storage.getModelName());
        jsonObject.addProperty("dataKey", s);
        this.plugin.getMessagingService().send(new QueuedMessage("cache", jsonObject) {
            @Override
            public void onSend() {
            }
        });
    }
}
