

package me.TechsCode.EnderPermissions.base.networking;

import me.TechsCode.EnderPermissions.base.messaging.Message;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Optional;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;

public class SpigotNetworkManager
{
    private SpigotTechPlugin plugin;
    private String ownIpAddress;
    private NetworkData networkData;
    
    public SpigotNetworkManager(final SpigotTechPlugin plugin) {
        this.plugin = plugin;
        this.ownIpAddress = this.retrieveOwnIpAddress();
        plugin.getMessagingService().register(message -> {
            if (message.getKey().equals("networking")) {
                this.networkData = NetworkData.fromJsonObject(message.getData());
            }
        });
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
    
    public Optional<NetworkData> getData() {
        return Optional.ofNullable(this.networkData);
    }
    
    public Optional<NServer> getThisServer() {
        if (this.networkData == null) {
            return Optional.empty();
        }
        final int n;
        return this.networkData.getServerList().stream().filter(nServer -> {
            this.plugin.getBootstrap().getServer().getPort();
            return nServer.getIp().equals(this.ownIpAddress) && nServer.getPort() == n;
        }).findFirst();
    }
    
    public Optional<NServer> getServerFromName(final String anotherString) {
        if (this.networkData == null) {
            return Optional.empty();
        }
        return this.networkData.getServerList().stream().filter(nServer -> nServer.getName().equalsIgnoreCase(anotherString)).findFirst();
    }
}
