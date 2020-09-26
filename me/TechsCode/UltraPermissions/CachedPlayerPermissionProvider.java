

package me.TechsCode.EnderPermissions;

import java.util.List;
import java.util.Collection;
import me.TechsCode.EnderPermissions.storage.objects.Holder;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.storage.objects.User;
import java.util.function.Function;
import me.TechsCode.EnderPermissions.storage.collection.PermissionList;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map;
import me.TechsCode.EnderPermissions.base.networking.NServer;
import java.util.Optional;

public class CachedPlayerPermissionProvider
{
    private static Optional<NServer> thisServer;
    private EnderPermissions plugin;
    private Map<UUID, CacheEntry> cache;
    
    public CachedPlayerPermissionProvider(final EnderPermissions plugin) {
        CachedPlayerPermissionProvider.thisServer = plugin.getThisServer();
        this.plugin = plugin;
        this.cache = new HashMap<UUID, CacheEntry>();
    }
    
    public PermissionList retrieve(final UUID uuid, final String anObject) {
        if ((CachedPlayerPermissionProvider.thisServer == null || !CachedPlayerPermissionProvider.thisServer.isPresent()) && this.plugin.getThisServer().isPresent()) {
            CachedPlayerPermissionProvider.thisServer = this.plugin.getThisServer();
            this.cache.clear();
        }
        if (this.cache.containsKey(uuid)) {
            final CacheEntry cacheEntry = this.cache.get(uuid);
            if (cacheEntry.world.equals(anObject)) {
                return cacheEntry.list;
            }
        }
        final Optional<User> uuid2 = this.plugin.getUsers().uuid(uuid);
        if (!uuid2.isPresent()) {
            return new PermissionList();
        }
        final String s = CachedPlayerPermissionProvider.thisServer.map((Function<? super NServer, ? extends String>)NServer::getName).orElse(null);
        final ArrayList list = new ArrayList<Holder>(uuid2.get().getActiveGroups().servers(true, s).worlds(true, anObject).getWithRecursiveInherits().servers(true, s).worlds(true, anObject).holders());
        list.add(Holder.fromUser(uuid2.get()));
        final PermissionList worlds = this.plugin.getPermissions().holder((List<Holder>)list).servers(true, s).worlds(true, anObject);
        this.cache.put(uuid, new CacheEntry(worlds, anObject));
        return worlds;
    }
    
    public void clearCache() {
        this.cache = new HashMap<UUID, CacheEntry>();
    }
    
    public class CacheEntry
    {
        final PermissionList list;
        final String world;
        
        public CacheEntry(final PermissionList list, final String world) {
            this.list = list;
            this.world = world;
        }
    }
}
