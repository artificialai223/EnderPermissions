

package me.TechsCode.EnderPermissions.tpl.task;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Event;

public class UpdateEvent extends Event
{
    private UpdateTime time;
    private static final HandlerList handlers;
    
    public UpdateEvent(final UpdateTime time) {
        this.time = time;
    }
    
    public HandlerList getHandlers() {
        return UpdateEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return UpdateEvent.handlers;
    }
    
    public UpdateTime getUpdateTime() {
        return this.time;
    }
    
    static {
        handlers = new HandlerList();
    }
}
