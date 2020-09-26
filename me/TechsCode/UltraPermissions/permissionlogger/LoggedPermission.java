

package me.TechsCode.EnderPermissions.permissionlogger;

import org.bukkit.entity.Player;

public class LoggedPermission
{
    private final Player p;
    private final String permission;
    private final boolean outcome;
    private final long time;
    private final String source;
    
    public LoggedPermission(final Player p5, final String permission, final boolean outcome, final long time, final String source) {
        this.p = p5;
        this.permission = permission;
        this.outcome = outcome;
        this.time = time;
        this.source = source;
    }
    
    public Player getPlayer() {
        return this.p;
    }
    
    public String getPermission() {
        return this.permission;
    }
    
    public boolean getOutcome() {
        return this.outcome;
    }
    
    public String getSource() {
        return this.source;
    }
    
    public long getTime() {
        return this.time;
    }
}
