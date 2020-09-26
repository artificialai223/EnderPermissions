

package me.TechsCode.EnderPermissions.base.messaging;

import com.google.gson.JsonObject;

public abstract class QueuedMessage extends Message
{
    public QueuedMessage(final String s, final JsonObject jsonObject) {
        super(s, jsonObject);
    }
    
    public abstract void onSend();
}
