

package me.TechsCode.EnderPermissions.hooks.pluginHooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.TechsCode.EnderPermissions.hooks.UpermsPlaceholder;
import java.util.Optional;
import me.TechsCode.EnderPermissions.base.networking.NServer;
import me.TechsCode.EnderPermissions.storage.objects.User;
import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.TechsCode.EnderPermissions.EnderPermissions;

public class PlaceholderAPIHook
{
    private final EnderPermissions plugin;
    
    public PlaceholderAPIHook(final EnderPermissions plugin) {
        this.plugin = plugin;
        new PlaceholderExpansion() {
            public String getIdentifier() {
                return "uperms";
            }
            
            public String getAuthor() {
                return "TechsCode";
            }
            
            public String getVersion() {
                return "1.0.0";
            }
            
            public boolean persist() {
                return true;
            }
            
            public String onRequest(final OfflinePlayer offlinePlayer, final String s) {
                if (offlinePlayer == null) {
                    plugin.log("Plugin tried getting the placeholder %uperms_" + s + "% with no player assigned!");
                    return "";
                }
                final Optional<User> uuid = plugin.getUsers().uuid(offlinePlayer.getUniqueId());
                if (!uuid.isPresent()) {
                    return "";
                }
                for (final UpermsPlaceholder upermsPlaceholder : EnderPermissions.placeholders) {
                    if (upermsPlaceholder.getName().equalsIgnoreCase(s)) {
                        return upermsPlaceholder.get(uuid.get(), plugin.getThisServer().orElse(null));
                    }
                }
                return "";
            }
        }.register();
    }
    
    public String replace(final OfflinePlayer offlinePlayer, final String s) {
        return PlaceholderAPI.setPlaceholders(offlinePlayer, s);
    }
}
