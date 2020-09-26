

package me.TechsCode.EnderPermissions.base;

import java.io.InputStream;
import me.TechsCode.EnderPermissions.base.loader.reloader.BungeePluginReloader;
import me.TechsCode.EnderPermissions.base.loader.reloader.PluginReloader;
import me.TechsCode.EnderPermissions.base.scheduler.Scheduler;
import net.md_5.bungee.api.ProxyServer;
import java.io.File;
import me.TechsCode.EnderPermissions.base.fileconf.FileConfiguration;
import me.TechsCode.EnderPermissions.base.update.networkUpdate.BungeeUpdateAgent;
import me.TechsCode.EnderPermissions.base.storage.syncing.BungeeSyncingAgent;
import me.TechsCode.EnderPermissions.base.storage.syncing.SyncingAgent;
import me.TechsCode.EnderPermissions.base.messaging.BungeeMessagingService;
import me.TechsCode.EnderPermissions.base.scheduler.BungeeScheduler;
import net.md_5.bungee.api.plugin.Plugin;

public abstract class BungeeTechPlugin extends TechPlugin<Plugin>
{
    private BungeeScheduler scheduler;
    private BungeeMessagingService messagingService;
    private SyncingAgent syncingAgent;
    
    public BungeeTechPlugin(final Plugin plugin) {
        super(plugin);
    }
    
    @Override
    protected void onInitialization() {
        this.scheduler = new BungeeScheduler(this);
        this.messagingService = new BungeeMessagingService(this);
        this.syncingAgent = new BungeeSyncingAgent(this);
        new BungeeUpdateAgent(this);
    }
    
    @Override
    protected void onPlatformEnable() {
    }
    
    @Override
    protected void onPlatformDisable() {
    }
    
    @Override
    public String getName() {
        return this.getBootstrap().getDescription().getName();
    }
    
    @Override
    public String getVersion() {
        return this.getBootstrap().getDescription().getVersion();
    }
    
    @Override
    public int getBuildNumber() {
        return new FileConfiguration(this, this.getBootstrap().getDescription().getFile()).getInt("build");
    }
    
    @Override
    public File getPluginFolder() {
        return this.getBootstrap().getDataFolder();
    }
    
    @Override
    public File getServerFolder() {
        return ProxyServer.getInstance().getPluginsFolder().getParentFile();
    }
    
    @Override
    public Scheduler getScheduler() {
        return this.scheduler;
    }
    
    public BungeeMessagingService getMessagingService() {
        return this.messagingService;
    }
    
    @Override
    public SyncingAgent getSyncingAgent() {
        return this.syncingAgent;
    }
    
    @Override
    public PluginReloader<?> getPluginReloader() {
        return new BungeePluginReloader(this);
    }
    
    @Override
    protected void sendConsole(final String msg) {
        this.getBootstrap().getLogger().info(msg);
    }
    
    @Override
    public InputStream getResource(final String s) {
        return this.getBootstrap().getResourceAsStream(s);
    }
}
