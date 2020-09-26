

package me.TechsCode.EnderPermissions.base.command.arguments;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import org.bukkit.OfflinePlayer;
import java.util.List;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.base.command.ArgumentValue;
import me.TechsCode.EnderPermissions.base.command.EmptyReason;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.base.command.Argument;

public class OnlinePlayerArgument extends Argument<Player>
{
    private String usage;
    private EmptyReason emptyReason;
    
    public OnlinePlayerArgument(final String usage, final EmptyReason emptyReason) {
        this.usage = usage;
        this.emptyReason = emptyReason;
    }
    
    @Override
    public ArgumentValue<Player> get(final String s) {
        final Player player = Bukkit.getPlayer(s);
        return (player != null && player.isOnline()) ? ArgumentValue.of(s, player) : ArgumentValue.empty(s, this.emptyReason);
    }
    
    @Override
    public String getUsage() {
        return this.usage;
    }
    
    @Override
    public List<String> getTabCompletions() {
        return Bukkit.getOnlinePlayers().stream().map((Function<? super Object, ?>)OfflinePlayer::getName).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
}
