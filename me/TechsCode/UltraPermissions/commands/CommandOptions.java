

package me.TechsCode.EnderPermissions.commands;

import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.World;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.tpl.Tools;
import java.util.function.Function;
import me.TechsCode.EnderPermissions.base.networking.NServer;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.ArrayUtils;
import java.util.List;
import me.TechsCode.EnderPermissions.EnderPermissions;

public class CommandOptions
{
    private String world;
    private String server;
    private long expiration;
    private final long secs;
    
    public CommandOptions(final EnderPermissions EnderPermissions, final List<String> list) {
        this(EnderPermissions, list.toArray(new String[0]));
    }
    
    public CommandOptions(final EnderPermissions EnderPermissions, final String[] elements) {
        this.world = null;
        this.server = null;
        this.expiration = 0L;
        if (ArrayUtils.contains(elements, "local")) {
            this.server = EnderPermissions.getThisServer().map((Function<? super NServer, ? extends String>)NServer::getName).orElse(null);
        }
        if (ArrayUtils.contains(elements, "bungee")) {
            this.server = "BungeeCord";
        }
        this.secs = Tools.getTimeSecondsFromString(String.join(" ", (CharSequence[])elements));
        if (this.secs != 0L) {
            this.expiration = System.currentTimeMillis() + this.secs * 1000L;
        }
        for (final World world : Bukkit.getWorlds()) {
            if (ArrayUtils.contains(elements, world.getName())) {
                this.world = world.getName();
            }
        }
    }
    
    public String getWorld() {
        return this.world;
    }
    
    public String getServer() {
        return this.server;
    }
    
    public long getExpiration() {
        return this.expiration;
    }
    
    public long getSecs() {
        return this.secs;
    }
    
    public String getCombinedInfo() {
        final ArrayList<String> elements = new ArrayList<String>();
        if (this.expiration != 0L) {
            elements.add(Tools.getTimeString(this.secs));
        }
        if (this.server != null) {
            elements.add(this.server);
        }
        if (this.world != null) {
            elements.add(this.world);
        }
        return elements.isEmpty() ? "" : (" §7(§e" + String.join("§8 | §e", elements) + "§7)");
    }
}
