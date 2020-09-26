

package me.TechsCode.EnderPermissions.base.networking;

import java.util.Arrays;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import java.util.Map;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.util.Objects;
import java.util.ArrayList;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.config.ServerInfo;
import me.TechsCode.EnderPermissions.base.messaging.Message;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import me.TechsCode.EnderPermissions.base.BungeeTechPlugin;
import java.util.List;

public class BungeeNetworkManager
{
    private static List<String> LOCALHOST_IPS;
    private BungeeTechPlugin plugin;
    private String ownIpAddress;
    
    public BungeeNetworkManager(final BungeeTechPlugin plugin) {
        this.plugin = plugin;
        this.ownIpAddress = this.retrieveOwnIpAddress();
        plugin.getScheduler().runTaskTimer(this::SendServerListInfoTask, 10L, 100L);
    }
    
    private String retrieveOwnIpAddress() {
        try {
            return new BufferedReader(new InputStreamReader(new URL("http://checkip.amazonaws.com").openStream())).readLine();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private void SendServerListInfoTask() {
        this.plugin.getMessagingService().send(new Message("networking", new NetworkData(this.plugin.getVersion(), this.fetchServerList()).toJsonObject()), new ServerInfo[0]);
    }
    
    private ServerList fetchServerList() {
        final InetSocketAddress inetSocketAddress;
        final InetAddress inetAddress;
        String ownIpAddress = null;
        final String s;
        final int n;
        final NServer nServer;
        return (ServerList)this.plugin.getBootstrap().getProxy().getServers().entrySet().stream().map(entry -> {
            entry.getValue().getName();
            entry.getValue().getAddress();
            if (inetSocketAddress == null) {
                return null;
            }
            else {
                inetSocketAddress.getAddress();
                if (inetAddress == null) {
                    return null;
                }
                else {
                    inetAddress.getHostAddress();
                    if (BungeeNetworkManager.LOCALHOST_IPS.contains(ownIpAddress) || ownIpAddress.startsWith("192.168")) {
                        ownIpAddress = this.ownIpAddress;
                    }
                    entry.getValue().getAddress().getPort();
                    new NServer(s, ownIpAddress, n, new ArrayList<NPlayer>());
                    entry.getValue().getPlayers().stream().map(proxiedPlayer -> new NPlayer(proxiedPlayer.getUniqueId(), proxiedPlayer.getName(), nServer)).forEach(nPlayer -> nServer.getPlayers().add(nPlayer));
                    return (NPlayer)nServer;
                }
            }
        }).filter(Objects::nonNull).collect(Collectors.toCollection(ServerList::new));
    }
    
    static {
        BungeeNetworkManager.LOCALHOST_IPS = Arrays.asList("localhost", "127.0.0.1");
    }
}
