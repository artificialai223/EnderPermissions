

package me.TechsCode.EnderPermissions;

import org.bukkit.entity.Player;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import org.bukkit.OfflinePlayer;
import org.bukkit.Bukkit;
import java.util.UUID;
import java.util.Set;

public class CachedOnlineChecker
{
    private Set<UUID> cache;
    
    public static CachedOnlineChecker scan() {
        return new CachedOnlineChecker((Set<UUID>)Bukkit.getOnlinePlayers().stream().map((Function<? super Object, ?>)OfflinePlayer::getUniqueId).collect((Collector<? super Object, ?, Set<Object>>)Collectors.toSet()));
    }
    
    private CachedOnlineChecker(final Set<UUID> cache) {
        this.cache = cache;
    }
    
    public boolean isOnline(final UUID uuid) {
        return this.cache.contains(uuid);
    }
    
    public boolean isOnline(final Player player) {
        return this.isOnline(player.getUniqueId());
    }
}
