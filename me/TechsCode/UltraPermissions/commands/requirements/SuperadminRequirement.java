

package me.TechsCode.EnderPermissions.commands.requirements;

import me.TechsCode.EnderPermissions.base.translations.TBase;
import java.util.function.Function;
import me.TechsCode.EnderPermissions.storage.objects.User;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.command.Requirement;

public class SuperadminRequirement implements Requirement
{
    private EnderPermissions plugin;
    
    public SuperadminRequirement(final EnderPermissions plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean isMatching(final CommandSender commandSender, final Player player, final List<String> list) {
        return player == null || this.plugin.getUsers().uuid(player.getUniqueId()).map((Function<? super User, ? extends Boolean>)User::isSuperadmin).orElse(false);
    }
    
    @Override
    public void onUnmatched(final CommandSender commandSender, final Player player) {
        commandSender.sendMessage(this.plugin.getPrefix() + TBase.COMMAND_NOPERMISSION.get().toString());
    }
}
