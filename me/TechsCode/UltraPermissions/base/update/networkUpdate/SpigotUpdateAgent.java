

package me.TechsCode.EnderPermissions.base.update.networkUpdate;

import me.TechsCode.EnderPermissions.base.messaging.QueuedMessage;
import java.util.Iterator;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.base.update.ResponseType;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.base.update.UpdateServer;
import me.TechsCode.EnderPermissions.base.messaging.Message;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.base.messaging.SpigotMessagingListener;

public class SpigotUpdateAgent implements SpigotMessagingListener
{
    private SpigotTechPlugin plugin;
    
    public SpigotUpdateAgent(final SpigotTechPlugin plugin) {
        this.plugin = plugin;
        plugin.getMessagingService().register(this);
    }
    
    @Override
    public void onMessage(final Message message) {
        if (message.getKey().equals("update")) {
            final JsonObject data = message.getData();
            final String asString = data.get("updateServer").getAsString();
            final String asString2 = data.get("uid").getAsString();
            final String asString3 = data.get("version").getAsString();
            if (!this.plugin.getVersion().equals(asString3) && UpdateServer.requestAndPerform(this.plugin, asString, asString2).getType() == ResponseType.SUCCESS) {
                for (final Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("*")) {
                        player.sendMessage(this.plugin.getPrefix() + "§7Updated from §c" + this.plugin.getVersion() + " §7to §a" + asString3);
                    }
                }
            }
        }
    }
    
    public void sendUpdateRequestToBungee(final String s, final String s2, final String s3) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("updateServer", s);
        jsonObject.addProperty("uid", s2);
        jsonObject.addProperty("version", s3);
        this.plugin.getMessagingService().send(new QueuedMessage("update", jsonObject) {
            @Override
            public void onSend() {
            }
        });
    }
}
