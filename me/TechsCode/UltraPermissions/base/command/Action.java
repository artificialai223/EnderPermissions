

package me.TechsCode.EnderPermissions.base.command;

import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

public interface Action<T>
{
    void run(final CommandSender p0, final Player p1, final Arguments p2);
}
