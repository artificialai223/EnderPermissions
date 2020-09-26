

package me.TechsCode.EnderPermissions.visual;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.hooks.UpermsPlaceholder;
import java.util.Optional;
import me.TechsCode.EnderPermissions.base.visual.Text;
import me.TechsCode.EnderPermissions.base.networking.NServer;
import me.TechsCode.EnderPermissions.storage.objects.User;
import java.util.Iterator;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.tpl.task.UpdateTime;
import me.TechsCode.EnderPermissions.tpl.task.UpdateEvent;
import org.bukkit.event.EventHandler;
import me.TechsCode.EnderPermissions.migration.MigrationAssistant;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.EnderPermissions;
import org.bukkit.event.Listener;

public class VisualManager implements Listener
{
    private final EnderPermissions plugin;
    private final VisualRegistry registry;
    
    public VisualManager(final EnderPermissions plugin, final VisualRegistry registry) {
        this.plugin = plugin;
        this.registry = registry;
        final String format = registry.getFormat(VisualType.CHAT);
        if (format != null && !format.contains("{Message}")) {
            registry.setFormat(VisualType.CHAT, format + " §f{Message}");
        }
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)((TechPlugin<Plugin>)plugin).getBootstrap());
    }
    
    @EventHandler
    public void chat(final AsyncPlayerChatEvent asyncPlayerChatEvent) {
        if (MigrationAssistant.isMigrating()) {
            return;
        }
        final String format = this.registry.getFormat(VisualType.CHAT);
        if (format == null) {
            return;
        }
        asyncPlayerChatEvent.setFormat(this.replacePlaceholders(asyncPlayerChatEvent.getPlayer(), format).replace("%", "percent").replace("{Message}", "%2$s"));
    }
    
    @EventHandler
    public void update(final UpdateEvent updateEvent) {
        if (MigrationAssistant.isMigrating()) {
            return;
        }
        if (updateEvent.getUpdateTime() != UpdateTime.SLOWER) {
            return;
        }
        if (this.registry.getFormat(VisualType.TABLIST) == null && this.registry.getFormat(VisualType.DISPLAY_NAME) == null) {
            return;
        }
        final Iterator<Player> iterator;
        Player player;
        final String s;
        final String s2;
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)((TechPlugin<Plugin>)this.plugin).getBootstrap(), () -> {
            Bukkit.getOnlinePlayers().iterator();
            while (iterator.hasNext()) {
                player = iterator.next();
                this.registry.getFormat(VisualType.TABLIST);
                this.registry.getFormat(VisualType.DISPLAY_NAME);
                if (s != null) {
                    player.setPlayerListName(this.replacePlaceholders(player, s));
                }
                if (s2 != null) {
                    player.setDisplayName(this.replacePlaceholders(player, s2));
                }
            }
        });
    }
    
    private String replacePlaceholders(final Player player, String s) {
        final Optional<User> uuid = this.plugin.getUsers().uuid(player.getUniqueId());
        if (!uuid.isPresent()) {
            return s;
        }
        for (final UpermsPlaceholder upermsPlaceholder : EnderPermissions.placeholders) {
            s = s.replace(upermsPlaceholder.getNativePlaceholder(), upermsPlaceholder.get(uuid.get(), this.plugin.getThisServer().orElse(null)));
        }
        s = this.plugin.replacePlaceholders(player, s);
        s = s.replace("{Player}", player.getName());
        s = s.replace("{DisplayName}", player.getDisplayName());
        return Text.color(s.trim());
    }
}
