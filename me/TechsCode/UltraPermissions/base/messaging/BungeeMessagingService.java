

package me.TechsCode.EnderPermissions.base.messaging;

import java.io.IOException;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.api.config.ServerInfo;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.ProxyServer;
import java.util.ArrayList;
import java.util.List;
import me.TechsCode.EnderPermissions.base.BungeeTechPlugin;
import net.md_5.bungee.api.plugin.Listener;

public class BungeeMessagingService implements Listener
{
    private BungeeTechPlugin plugin;
    private List<BungeeMessagingListener> listeners;
    
    public BungeeMessagingService(final BungeeTechPlugin plugin) {
        this.plugin = plugin;
        this.listeners = new ArrayList<BungeeMessagingListener>();
        ProxyServer.getInstance().getPluginManager().registerListener((Plugin)plugin.getBootstrap(), (Listener)this);
        ProxyServer.getInstance().registerChannel("BungeeCord");
    }
    
    public void register(final BungeeMessagingListener bungeeMessagingListener) {
        this.listeners.add(bungeeMessagingListener);
    }
    
    @EventHandler
    public void message(final PluginMessageEvent pluginMessageEvent) {
        if (pluginMessageEvent.getSender() instanceof Server) {
            try {
                final ByteArrayDataInput dataInput = ByteStreams.newDataInput(pluginMessageEvent.getData());
                ((Server)pluginMessageEvent.getSender()).getInfo();
                if (dataInput.readUTF().startsWith(this.plugin.getName() + "//")) {
                    final ServerInfo serverInfo;
                    final String s;
                    this.listeners.forEach(bungeeMessagingListener -> bungeeMessagingListener.onMessage(serverInfo, Message.decode(s.replace(this.plugin.getName() + "//", ""))));
                }
            }
            catch (IllegalStateException ex) {}
        }
    }
    
    public void send(final Message message, ServerInfo... array) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(out);
        try {
            dataOutputStream.writeUTF(this.plugin.getName() + "//" + message.encode());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        if (array.length == 0) {
            array = (ServerInfo[])ProxyServer.getInstance().getServers().values().toArray(new ServerInfo[0]);
        }
        final ServerInfo[] array2 = array;
        for (int length = array2.length, i = 0; i < length; ++i) {
            array2[i].sendData("BungeeCord", out.toByteArray());
        }
    }
}
