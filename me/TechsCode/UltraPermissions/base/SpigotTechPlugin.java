

package me.TechsCode.EnderPermissions.base;

import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.base.loader.reloader.SpigotPluginReloader;
import me.TechsCode.EnderPermissions.base.loader.reloader.PluginReloader;
import me.TechsCode.EnderPermissions.base.scheduler.Scheduler;
import java.io.File;
import java.io.InputStream;
import java.util.function.Function;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.TechsCode.EnderPermissions.tpl.task.TaskManager;
import me.TechsCode.EnderPermissions.base.source.Maven;
import me.TechsCode.EnderPermissions.base.storage.syncing.SpigotSyncingAgent;
import me.TechsCode.EnderPermissions.base.loader.reloader.automatic.AutoPluginReloader;
import me.TechsCode.EnderPermissions.base.update.networkUpdate.SpigotUpdateAgent;
import me.TechsCode.EnderPermissions.base.storage.syncing.SyncingAgent;
import me.TechsCode.EnderPermissions.base.messaging.SpigotMessagingService;
import me.TechsCode.EnderPermissions.base.scheduler.SpigotScheduler;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SpigotTechPlugin extends TechPlugin<JavaPlugin>
{
    private SpigotScheduler scheduler;
    private SpigotMessagingService messagingService;
    private SyncingAgent syncingAgent;
    private SpigotUpdateAgent updateAgent;
    private AutoPluginReloader autoPluginReloader;
    
    public SpigotTechPlugin(final JavaPlugin javaPlugin) {
        super(javaPlugin);
    }
    
    @Override
    protected void onInitialization() {
        this.scheduler = new SpigotScheduler(this);
        this.messagingService = new SpigotMessagingService(this);
        this.syncingAgent = new SpigotSyncingAgent(this);
        this.updateAgent = new SpigotUpdateAgent(this);
    }
    
    @Override
    protected void onPlatformEnable() {
        Maven.initialize(this);
        Maven.addRepository("https://repo.everit.biz/artifactory/public-release");
        new TaskManager(this);
        this.autoPluginReloader = new AutoPluginReloader(this);
        Logger.getLogger("NBTAPI").setLevel(Level.OFF);
    }
    
    @Override
    protected void onPlatformDisable() {
        this.messagingService.onDisable();
        this.autoPluginReloader.onDisable();
    }
    
    @Override
    public String getName() {
        return this.getBootstrap().getName();
    }
    
    @Override
    public String getVersion() {
        return this.getBootstrap().getDescription().getVersion();
    }
    
    @Override
    public int getBuildNumber() {
        final InputStream resource = this.getBootstrap().getResource("plugin.yml");
        if (resource == null) {
            return 0;
        }
        return new BufferedReader(new InputStreamReader(resource)).lines().filter(s -> s.startsWith("build: ")).map(s2 -> s2.replace("build: ", "")).map((Function<? super Object, ?>)Integer::parseInt).findFirst().orElse(0);
    }
    
    @Override
    public File getPluginFolder() {
        return this.getBootstrap().getDataFolder();
    }
    
    @Override
    public File getServerFolder() {
        return new File(".");
    }
    
    @Override
    public Scheduler getScheduler() {
        return this.scheduler;
    }
    
    public SpigotMessagingService getMessagingService() {
        return this.messagingService;
    }
    
    @Override
    public SyncingAgent getSyncingAgent() {
        return this.syncingAgent;
    }
    
    @Override
    public PluginReloader<?> getPluginReloader() {
        return new SpigotPluginReloader(this);
    }
    
    @Override
    protected void sendConsole(final String s) {
        Bukkit.getConsoleSender().sendMessage(s);
    }
    
    public SpigotUpdateAgent getUpdateAgent() {
        return this.updateAgent;
    }
    
    @Override
    public InputStream getResource(final String s) {
        return this.getBootstrap().getResource(s);
    }
}
