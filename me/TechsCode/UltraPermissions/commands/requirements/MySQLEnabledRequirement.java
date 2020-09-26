

package me.TechsCode.EnderPermissions.commands.requirements;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.command.Requirement;

public class MySQLEnabledRequirement implements Requirement
{
    private EnderPermissions plugin;
    
    public MySQLEnabledRequirement(final EnderPermissions plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean isMatching(final CommandSender commandSender, final Player player, final List<String> list) {
        return this.plugin.getMySQLManager().isEnabled();
    }
    
    @Override
    public void onUnmatched(final CommandSender commandSender, final Player player) {
        commandSender.sendMessage(this.plugin.getPrefix() + "§7Command not available if MySQL is not configured");
    }
}
