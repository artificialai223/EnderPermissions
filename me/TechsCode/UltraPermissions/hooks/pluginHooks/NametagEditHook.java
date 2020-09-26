

package me.TechsCode.EnderPermissions.hooks.pluginHooks;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import org.bukkit.ChatColor;
import me.TechsCode.EnderPermissions.events.group.GroupSuffixChangeEvent;
import me.TechsCode.EnderPermissions.events.group.GroupPrefixChangeEvent;
import me.TechsCode.EnderPermissions.events.user.UserSuffixChangeEvent;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.events.user.UserPrefixChangeEvent;
import org.bukkit.event.EventHandler;
import java.util.Optional;
import me.TechsCode.EnderPermissions.storage.objects.User;
import org.bukkit.event.player.PlayerJoinEvent;
import me.TechsCode.EnderPermissions.visual.VisualRegistry;
import com.nametagedit.plugin.api.INametagApi;
import com.nametagedit.plugin.NametagEdit;
import me.TechsCode.EnderPermissions.hooks.placeholders.SuffixPlaceholder;
import me.TechsCode.EnderPermissions.hooks.placeholders.PrefixPlaceholder;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.base.networking.NServer;
import me.TechsCode.EnderPermissions.EnderPermissions;
import org.bukkit.event.Listener;

public class NametagEditHook implements Listener
{
    private final EnderPermissions plugin;
    private final NServer thisServer;
    
    public NametagEditHook(final EnderPermissions plugin) {
        this.plugin = plugin;
        this.thisServer = plugin.getThisServer().orElse(null);
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)((TechPlugin<Plugin>)plugin).getBootstrap());
        final String str;
        String string;
        final String str2;
        String string2;
        final INametagApi nametagApi;
        final String s;
        plugin.getScheduler().runTaskLater(() -> plugin.getUsers().stream().filter(user -> Bukkit.getPlayer(user.getUuid()) != null).forEach(user2 -> {
            if (!(!this.isEnabled())) {
                this.color(new PrefixPlaceholder().get(user2, this.thisServer));
                this.color(new SuffixPlaceholder().get(user2, this.thisServer));
                NametagEdit.getApi();
                user2.getName();
                if (str.equals("")) {
                    string = "";
                }
                else {
                    string = str + " ";
                }
                if (str2.equals("")) {
                    string2 = "";
                }
                else {
                    string2 = " " + str2;
                }
                nametagApi.setNametag(s, string, string2);
            }
        }), 5L);
    }
    
    private boolean isEnabled() {
        return this.plugin.getVisualRegistry().get().isEditingNametags();
    }
    
    @EventHandler
    public void onJoin(final PlayerJoinEvent playerJoinEvent) {
        if (!this.isEnabled()) {
            return;
        }
        if (!this.plugin.getUserStorage().getUsers().uuid(playerJoinEvent.getPlayer().getUniqueId()).isPresent()) {
            return;
        }
        final Optional<User> optional;
        final String str;
        String string;
        final String str2;
        String string2;
        final INametagApi nametagApi;
        final String s;
        this.plugin.getScheduler().runTaskLater(() -> {
            this.color(new PrefixPlaceholder().get(optional.get(), this.thisServer));
            this.color(new SuffixPlaceholder().get(optional.get(), this.thisServer));
            NametagEdit.getApi();
            optional.get().getName();
            if (str.equals("")) {
                string = "";
            }
            else {
                string = str + " ";
            }
            if (str2.equals("")) {
                string2 = "";
            }
            else {
                string2 = " " + str2;
            }
            nametagApi.setNametag(s, string, string2);
        }, 5L);
    }
    
    @EventHandler
    public void onUserPrefixChange(final UserPrefixChangeEvent userPrefixChangeEvent) {
        if (!this.isEnabled()) {
            return;
        }
        final Optional<Player> ofNullable = Optional.ofNullable(Bukkit.getPlayer(userPrefixChangeEvent.getUser().getUuid()));
        final String color = this.color(new PrefixPlaceholder().get(userPrefixChangeEvent.getUser(), this.thisServer));
        if (ofNullable.isPresent()) {
            NametagEdit.getApi().setPrefix(userPrefixChangeEvent.getUser().getName(), color.equals("") ? "" : (color + " "));
        }
    }
    
    @EventHandler
    public void onUserSuffixChange(final UserSuffixChangeEvent userSuffixChangeEvent) {
        if (!this.isEnabled()) {
            return;
        }
        final Optional<Player> ofNullable = Optional.ofNullable(Bukkit.getPlayer(userSuffixChangeEvent.getUser().getUuid()));
        final String color = this.color(new SuffixPlaceholder().get(userSuffixChangeEvent.getUser(), this.thisServer));
        if (ofNullable.isPresent()) {
            NametagEdit.getApi().setSuffix(userSuffixChangeEvent.getUser().getName(), color.equals("") ? "" : (" " + color));
        }
    }
    
    @EventHandler
    public void onGroupPrefixChange(final GroupPrefixChangeEvent groupPrefixChangeEvent) {
        if (!this.isEnabled()) {
            return;
        }
        final String str;
        String string;
        final INametagApi nametagApi;
        groupPrefixChangeEvent.getGroup().getUsers().forEach(user -> Optional.ofNullable(Bukkit.getPlayer(user.getUuid())).ifPresent(player -> {
            this.color(new PrefixPlaceholder().get(user, this.thisServer));
            NametagEdit.getApi();
            if (str.equals("")) {
                string = "";
            }
            else {
                string = str + " ";
            }
            nametagApi.setPrefix(player, string);
        }));
    }
    
    @EventHandler
    public void onGroupSuffixChange(final GroupSuffixChangeEvent groupSuffixChangeEvent) {
        if (!this.isEnabled()) {
            return;
        }
        final String str;
        String string;
        final INametagApi nametagApi;
        groupSuffixChangeEvent.getGroup().getUsers().forEach(user -> Optional.ofNullable(Bukkit.getPlayer(user.getUuid())).ifPresent(player -> {
            this.color(new SuffixPlaceholder().get(user, this.thisServer));
            NametagEdit.getApi();
            if (str.equals("")) {
                string = "";
            }
            else {
                string = " " + str;
            }
            nametagApi.setSuffix(player, string);
        }));
    }
    
    private String color(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
