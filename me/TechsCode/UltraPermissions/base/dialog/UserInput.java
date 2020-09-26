

package me.TechsCode.EnderPermissions.base.dialog;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;

public abstract class UserInput extends Dialog
{
    private final Player p;
    private final String mainTitle;
    private final String subTitle;
    private String actionbar;
    private long lastTimeMoved;
    
    public UserInput(final Player p4, final SpigotTechPlugin spigotTechPlugin, final Object o, final Object o2) {
        super(p4, spigotTechPlugin);
        this.lastTimeMoved = 0L;
        this.p = p4;
        this.mainTitle = o.toString();
        this.subTitle = o2.toString();
    }
    
    public UserInput(final Player p5, final SpigotTechPlugin spigotTechPlugin, final Object o, final Object o2, final Object o3) {
        super(p5, spigotTechPlugin);
        this.lastTimeMoved = 0L;
        this.p = p5;
        this.mainTitle = o.toString();
        this.subTitle = o2.toString();
        this.actionbar = o3.toString();
    }
    
    public abstract boolean onResult(final String p0);
    
    @Override
    public boolean onInput(final String s) {
        if (this.onResult(s)) {
            this.close(true);
        }
        return true;
    }
    
    @EventHandler
    public void move(final PlayerMoveEvent playerMoveEvent) {
        if (playerMoveEvent.getPlayer().equals(this.p)) {
            this.lastTimeMoved = System.currentTimeMillis();
        }
    }
    
    @EventHandler
    public void close(final PlayerInteractEvent playerInteractEvent) {
        if (playerInteractEvent.getPlayer().equals(this.p) && (playerInteractEvent.getAction() == Action.LEFT_CLICK_AIR || playerInteractEvent.getAction() == Action.LEFT_CLICK_BLOCK)) {
            this.close(false);
        }
    }
    
    private boolean hasMoved() {
        return this.lastTimeMoved != 0L && System.currentTimeMillis() - this.lastTimeMoved < 3000L;
    }
    
    @Override
    public String getMainTitle() {
        return this.mainTitle;
    }
    
    @Override
    public String getSubTitle() {
        return this.hasMoved() ? "§eLeft Click §7to close" : this.subTitle;
    }
    
    @Override
    public String getActionBar() {
        return this.actionbar;
    }
}
