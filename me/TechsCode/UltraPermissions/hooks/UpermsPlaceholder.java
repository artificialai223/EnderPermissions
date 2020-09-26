

package me.TechsCode.EnderPermissions.hooks;

import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.base.networking.NServer;
import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.tpl.Tools;

public abstract class UpermsPlaceholder
{
    private final String name;
    private final String description;
    
    public UpermsPlaceholder(final String name, final String description) {
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getNativePlaceholder() {
        return "{" + Tools.firstUpperCase(this.name) + "}";
    }
    
    public String get(final User user, final NServer nServer) {
        if (user == null) {
            return "";
        }
        Player player = Bukkit.getPlayer(user.getUuid());
        if (player != null && !player.isOnline()) {
            player = null;
        }
        return this.replace(player, user, user.getActiveGroups().bestToWorst().servers(true, (nServer != null) ? nServer.getName() : null).worlds(true, (player != null) ? player.getWorld().getName() : "*").toArray(new Group[0]));
    }
    
    protected abstract String replace(final Player p0, final User p1, final Group[] p2);
}
