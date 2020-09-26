

package me.TechsCode.EnderPermissions.hooks.pluginHooks;

import me.TechsCode.EnderPermissions.storage.objects.Group;
import java.util.Optional;
import java.util.function.Function;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.hooks.placeholders.SuffixPlaceholder;
import me.TechsCode.EnderPermissions.hooks.placeholders.PrefixPlaceholder;
import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.base.networking.NServer;
import net.milkbowl.vault.permission.Permission;
import java.util.HashMap;
import me.TechsCode.EnderPermissions.EnderPermissions;
import net.milkbowl.vault.chat.Chat;

public class VaultChatHook extends Chat
{
    private final EnderPermissions plugin;
    private HashMap<String, String> prefixesCache;
    private HashMap<String, String> suffixesCache;
    
    public VaultChatHook(final Permission permission, final EnderPermissions plugin) {
        super(permission);
        this.plugin = plugin;
        this.loadInCache().run();
    }
    
    public String getName() {
        return "EnderPermissions";
    }
    
    public boolean isEnabled() {
        return true;
    }
    
    public void clearCache() {
        this.plugin.getScheduler().runAsync(this.loadInCache());
    }
    
    private Runnable loadInCache() {
        final NServer nServer;
        final Iterator<User> iterator;
        User user;
        return () -> {
            this.prefixesCache = new HashMap<String, String>();
            this.suffixesCache = new HashMap<String, String>();
            nServer = this.plugin.getThisServer().orElse(null);
            this.plugin.getUsers().iterator();
            while (iterator.hasNext()) {
                user = iterator.next();
                this.prefixesCache.put(user.getName(), new PrefixPlaceholder().get(user, nServer));
                this.suffixesCache.put(user.getName(), new SuffixPlaceholder().get(user, nServer));
            }
        };
    }
    
    public String getPlayerPrefix(final String s, final String key) {
        return this.prefixesCache.getOrDefault(key, null);
    }
    
    public void setPlayerPrefix(final String s, final String s2, final String prefix) {
        this.user(s2).ifPresent(user -> user.setPrefix(prefix));
    }
    
    public String getPlayerSuffix(final String s, final String key) {
        return this.suffixesCache.getOrDefault(key, null);
    }
    
    public void setPlayerSuffix(final String s, final String s2, final String suffix) {
        this.user(s2).ifPresent(user -> user.setSuffix(suffix));
    }
    
    public String getGroupPrefix(final String s, final String s2) {
        return this.group(s2).map(group -> group.getPrefix().orElse(null)).orElse(null);
    }
    
    public void setGroupPrefix(final String s, final String s2, final String prefix) {
        this.group(s2).ifPresent(group -> group.setPrefix(prefix));
    }
    
    public String getGroupSuffix(final String s, final String s2) {
        return this.group(s2).map(group -> group.getSuffix().orElse(null)).orElse(null);
    }
    
    public void setGroupSuffix(final String s, final String s2, final String suffix) {
        this.group(s2).ifPresent(group -> group.setSuffix(suffix));
    }
    
    public int getPlayerInfoInteger(final String s, final String s2, final String s3, final int n) {
        return this.user(s2).map(user -> user.getPermissions().name(s3).servers(true, this.plugin.getThisServer().map((Function<? super NServer, ? extends String>)NServer::getName).orElse(null)).worlds(true, s).stream().filter(me.TechsCode.EnderPermissions.storage.objects.Permission::isPositive).map(permission -> permission.getName().split(".")).filter(array -> array.length > 1).mapToInt(array2 -> this.convertOrDefault(array2[array2.length - 1], n)).max().orElse(n)).orElse(n);
    }
    
    private int convertOrDefault(final String s, final int n) {
        try {
            return Integer.valueOf(s);
        }
        catch (NumberFormatException ex) {
            return n;
        }
    }
    
    public void setPlayerInfoInteger(final String s, final String s2, final String s3, final int n) {
    }
    
    public int getGroupInfoInteger(final String s, final String s2, final String s3, final int n) {
        return this.group(s2).map(group -> group.getPermissions().name(s3).servers(true, this.plugin.getThisServer().map((Function<? super NServer, ? extends String>)NServer::getName).orElse(null)).worlds(true, s).stream().filter(me.TechsCode.EnderPermissions.storage.objects.Permission::isPositive).map(permission -> permission.getName().split(".")).filter(array -> array.length > 1).mapToInt(array2 -> this.convertOrDefault(array2[array2.length - 1], n)).max().orElse(n)).orElse(n);
    }
    
    public void setGroupInfoInteger(final String s, final String s2, final String s3, final int n) {
    }
    
    public double getPlayerInfoDouble(final String s, final String s2, final String s3, final double n) {
        return 0.0;
    }
    
    public void setPlayerInfoDouble(final String s, final String s2, final String s3, final double n) {
    }
    
    public double getGroupInfoDouble(final String s, final String s2, final String s3, final double n) {
        return 0.0;
    }
    
    public void setGroupInfoDouble(final String s, final String s2, final String s3, final double n) {
    }
    
    public boolean getPlayerInfoBoolean(final String s, final String s2, final String s3, final boolean b) {
        return false;
    }
    
    public void setPlayerInfoBoolean(final String s, final String s2, final String s3, final boolean b) {
    }
    
    public boolean getGroupInfoBoolean(final String s, final String s2, final String s3, final boolean b) {
        return false;
    }
    
    public void setGroupInfoBoolean(final String s, final String s2, final String s3, final boolean b) {
    }
    
    public String getPlayerInfoString(final String s, final String s2, final String s3, final String s4) {
        return null;
    }
    
    public void setPlayerInfoString(final String s, final String s2, final String s3, final String s4) {
    }
    
    public String getGroupInfoString(final String s, final String s2, final String s3, final String s4) {
        return null;
    }
    
    public void setGroupInfoString(final String s, final String s2, final String s3, final String s4) {
    }
    
    public Optional<User> user(final String s) {
        return this.plugin.getUsers().name(s);
    }
    
    public Optional<Group> group(final String s) {
        return this.plugin.getGroups().name(s);
    }
}
