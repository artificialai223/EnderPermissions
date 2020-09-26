

package me.TechsCode.EnderPermissions.base.command.requirements;

import me.TechsCode.EnderPermissions.base.translations.TBase;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.base.command.Requirement;

public class PlayerRequirement implements Requirement
{
    private SpigotTechPlugin plugin;
    
    public PlayerRequirement(final SpigotTechPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean isMatching(final CommandSender commandSender, final Player player, final List<String> list) {
        return player != null;
    }
    
    @Override
    public void onUnmatched(final CommandSender commandSender, final Player player) {
        commandSender.sendMessage(this.plugin.getPrefix() + TBase.COMMAND_NOCONSOLE);
    }
}
