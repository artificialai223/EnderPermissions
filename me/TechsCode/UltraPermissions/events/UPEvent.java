

package me.TechsCode.EnderPermissions.events;

import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Event;

public abstract class UPEvent extends Event
{
    private static final HandlerList HANDLERS;
    
    public HandlerList getHandlers() {
        return UPEvent.HANDLERS;
    }
    
    public static HandlerList getHandlerList() {
        return UPEvent.HANDLERS;
    }
    
    public void call(final TechPlugin techPlugin, final Event event) {
        techPlugin.getScheduler().run(() -> Bukkit.getPluginManager().callEvent(event));
    }
    
    static {
        HANDLERS = new HandlerList();
    }
}
