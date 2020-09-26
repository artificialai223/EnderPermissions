

package me.TechsCode.EnderPermissions.base.networking;

import java.util.ArrayList;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;

public class NServer
{
    private String name;
    private String ip;
    private int port;
    private List<NPlayer> players;
    
    public NServer(final String name, final String ip, final int port, final List<NPlayer> players) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.players = players;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getIp() {
        return this.ip;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public List<NPlayer> getPlayers() {
        return this.players;
    }
    
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", this.name);
        jsonObject.addProperty("ip", this.ip);
        jsonObject.addProperty("port", (Number)this.port);
        final JsonArray jsonArray = new JsonArray();
        this.players.forEach(nPlayer -> jsonArray.add((JsonElement)nPlayer.toJsonObject()));
        jsonObject.add("players", (JsonElement)jsonArray);
        return jsonObject;
    }
    
    public static NServer fromJsonObject(final JsonObject jsonObject) {
        final NServer nServer = new NServer(jsonObject.get("name").getAsString(), jsonObject.get("ip").getAsString(), jsonObject.get("port").getAsInt(), new ArrayList<NPlayer>());
        final NServer nServer2;
        jsonObject.get("players").getAsJsonArray().forEach(jsonObject2 -> nServer2.players.add(NPlayer.fromJsonObject(jsonObject2, nServer2)));
        return nServer;
    }
}
