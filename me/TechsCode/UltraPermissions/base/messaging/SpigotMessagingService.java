

package me.TechsCode.EnderPermissions.base.messaging;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import java.util.Collections;
import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.ByteArrayInputStream;
import com.google.common.io.ByteArrayDataOutput;
import java.util.Iterator;
import com.google.common.io.ByteStreams;
import com.google.common.collect.Iterables;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.base.scheduler.RecurringTask;
import java.util.List;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class SpigotMessagingService implements PluginMessageListener
{
    private SpigotTechPlugin plugin;
    private List<QueuedMessage> queuedMessages;
    private RecurringTask sendTask;
    private List<SpigotMessagingListener> listeners;
    
    public SpigotMessagingService(final SpigotTechPlugin plugin) {
        this.plugin = plugin;
        this.queuedMessages = new ArrayList<QueuedMessage>();
        this.listeners = new ArrayList<SpigotMessagingListener>();
        this.sendTask = plugin.getScheduler().runTaskTimer(this::SendTask, 1L, 1L);
        Bukkit.getMessenger().registerOutgoingPluginChannel((Plugin)((TechPlugin<Plugin>)plugin).getBootstrap(), "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel((Plugin)((TechPlugin<Plugin>)plugin).getBootstrap(), "BungeeCord", (PluginMessageListener)this);
    }
    
    public void onDisable() {
        this.sendTask.stop();
    }
    
    private void SendTask() {
        final Player player = (Player)Iterables.getFirst((Iterable)Bukkit.getOnlinePlayers(), (Object)null);
        final Iterator<QueuedMessage> iterator = this.queuedMessages.iterator();
        if (iterator.hasNext() && player != null) {
            final QueuedMessage queuedMessage = iterator.next();
            final ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
            dataOutput.writeUTF(this.plugin.getName() + "//" + queuedMessage.encode());
            player.sendPluginMessage((Plugin)((TechPlugin<Plugin>)this.plugin).getBootstrap(), "BungeeCord", dataOutput.toByteArray());
            queuedMessage.onSend();
            this.queuedMessages.remove(queuedMessage);
        }
    }
    
    public void register(final SpigotMessagingListener spigotMessagingListener) {
        this.listeners.add(spigotMessagingListener);
    }
    
    public void onPluginMessageReceived(final String s, final Player player, final byte[] buf) {
        try {
            final DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(buf));
            if (dataInputStream.available() == 0) {
                return;
            }
            if (dataInputStream.readUTF().startsWith(this.plugin.getName() + "//")) {
                final String s2;
                this.listeners.forEach(spigotMessagingListener -> spigotMessagingListener.onMessage(Message.decode(s2.replace(this.plugin.getName() + "//", ""))));
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void send(final QueuedMessage queuedMessage) {
        final Iterator<QueuedMessage> iterator = Collections.unmodifiableList((List<? extends QueuedMessage>)this.queuedMessages).iterator();
        while (iterator.hasNext()) {
            if (iterator.next().encode().equals(queuedMessage.encode())) {
                return;
            }
        }
        this.queuedMessages.add(queuedMessage);
    }
}
