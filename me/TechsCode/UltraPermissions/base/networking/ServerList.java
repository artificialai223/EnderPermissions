

package me.TechsCode.EnderPermissions.base.networking;

import java.util.Iterator;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.util.ArrayList;

public class ServerList extends ArrayList<NServer>
{
    public ServerList() {
    }
    
    public ServerList(final JsonArray jsonArray) {
        jsonArray.forEach(jsonObject -> this.add(NServer.fromJsonObject(jsonObject)));
    }
    
    public JsonArray toJsonArray() {
        final JsonArray jsonArray = new JsonArray();
        final Iterator<NServer> iterator = this.iterator();
        while (iterator.hasNext()) {
            jsonArray.add((JsonElement)iterator.next().toJsonObject());
        }
        return jsonArray;
    }
}
