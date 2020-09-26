

package me.TechsCode.EnderPermissions.base.storage;

import com.google.gson.JsonObject;
import java.util.HashMap;

public interface ReadCallback
{
    void onSuccess(final HashMap<String, JsonObject> p0);
    
    void onFailure(final Exception p0);
}
