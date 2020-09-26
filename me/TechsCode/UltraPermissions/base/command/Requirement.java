

package me.TechsCode.EnderPermissions.base.command;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

public interface Requirement
{
    boolean isMatching(final CommandSender p0, final Player p1, final List<String> p2);
    
    void onUnmatched(final CommandSender p0, final Player p1);
}
