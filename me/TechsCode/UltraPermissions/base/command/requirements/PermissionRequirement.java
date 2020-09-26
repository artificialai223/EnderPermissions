

package me.TechsCode.EnderPermissions.base.command.requirements;

import me.TechsCode.EnderPermissions.base.translations.TBase;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.base.command.Requirement;

public class PermissionRequirement implements Requirement
{
    private SpigotTechPlugin plugin;
    private String permission;
    
    public PermissionRequirement(final SpigotTechPlugin plugin, final String permission) {
        this.plugin = plugin;
        this.permission = permission;
    }
    
    @Override
    public boolean isMatching(final CommandSender commandSender, final Player player, final List<String> list) {
        return commandSender.hasPermission(this.permission);
    }
    
    @Override
    public void onUnmatched(final CommandSender commandSender, final Player player) {
        player.sendMessage(this.plugin.getPrefix() + TBase.COMMAND_NOPERMISSION);
        this.plugin.log("Player " + player.getName() + " could not access a command because he does not have " + this.permission);
    }
}
