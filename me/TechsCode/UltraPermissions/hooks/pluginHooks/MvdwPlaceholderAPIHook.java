

package me.TechsCode.EnderPermissions.hooks.pluginHooks;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.storage.objects.User;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.hooks.UpermsPlaceholder;
import me.TechsCode.EnderPermissions.base.networking.NServer;
import java.util.Optional;
import be.maximvdw.placeholderapi.PlaceholderAPI;
import org.bukkit.plugin.Plugin;
import me.TechsCode.EnderPermissions.EnderPermissions;

public class MvdwPlaceholderAPIHook
{
    public MvdwPlaceholderAPIHook(final EnderPermissions EnderPermissions) {
        final Optional<NServer> thisServer = EnderPermissions.getThisServer();
        for (final UpermsPlaceholder upermsPlaceholder : EnderPermissions.placeholders) {
            PlaceholderAPI.registerPlaceholder((Plugin)((TechPlugin<Plugin>)EnderPermissions).getBootstrap(), "uperms_" + upermsPlaceholder.getName(), placeholderReplaceEvent -> {
                final Optional<User> uuid = EnderPermissions.getUsers().uuid(placeholderReplaceEvent.getOfflinePlayer().getUniqueId());
                if (uuid.isPresent()) {
                    return upermsPlaceholder.get(uuid.get(), thisServer.orElse(null));
                }
                return "";
            });
        }
    }
    
    public String replace(final Player player, final String s) {
        return PlaceholderAPI.replacePlaceholders((OfflinePlayer)player, s);
    }
}
