

package me.TechsCode.EnderPermissions.base.networking;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class NetworkData
{
    private String proxyPluginVersion;
    private ServerList serverList;
    
    public NetworkData(final String proxyPluginVersion, final ServerList serverList) {
        this.proxyPluginVersion = proxyPluginVersion;
        this.serverList = serverList;
    }
    
    public String getProxyPluginVersion() {
        return this.proxyPluginVersion;
    }
    
    public ServerList getServerList() {
        return this.serverList;
    }
    
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("proxyVersion", this.proxyPluginVersion);
        jsonObject.add("serverList", (JsonElement)this.serverList.toJsonArray());
        return jsonObject;
    }
    
    public static NetworkData fromJsonObject(final JsonObject jsonObject) {
        return new NetworkData(jsonObject.get("proxyVersion").getAsString(), new ServerList(jsonObject.getAsJsonArray("serverList")));
    }
}
