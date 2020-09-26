

package me.TechsCode.EnderPermissions.base;

import me.TechsCode.EnderPermissions.base.visual.Text;
import me.TechsCode.EnderPermissions.base.loader.reloader.PluginReloader;
import java.io.InputStream;
import me.TechsCode.EnderPermissions.base.storage.syncing.SyncingAgent;
import me.TechsCode.EnderPermissions.base.scheduler.Scheduler;
import java.io.File;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.base.registry.RegistrationChoice;
import java.util.List;
import me.TechsCode.EnderPermissions.base.translations.TranslationManager;
import me.TechsCode.EnderPermissions.base.mysql.MySQLManager;
import me.TechsCode.EnderPermissions.base.legacySystemStorage.LegacySystemStorage;
import me.TechsCode.EnderPermissions.base.registry.Registry;

public abstract class TechPlugin<BOOTSTRAP>
{
    private final BOOTSTRAP bootstrap;
    private Registry registry;
    private LegacySystemStorage legacySystemStorage;
    private MySQLManager mySQLManager;
    private AppearanceRegistry appearanceRegistry;
    private TranslationManager translationManager;
    private List<Runnable> disableHooks;
    
    public TechPlugin(final BOOTSTRAP bootstrap) {
        this.bootstrap = bootstrap;
        this.log("Loading Plugin...");
        final long currentTimeMillis = System.currentTimeMillis();
        this.onInitialization();
        if (!this.onGlobalEnable()) {
            return;
        }
        this.onPlatformEnable();
        this.onEnable();
        this.log("Successfully loaded in " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
    }
    
    public void disable() {
        this.onDisable();
        this.onPlatformDisable();
        this.onGlobalDisable();
    }
    
    private boolean onGlobalEnable() {
        this.registry = new Registry(this);
        this.legacySystemStorage = new LegacySystemStorage(this);
        this.getScheduler().runTaskLater(this.legacySystemStorage::wipe, 80L);
        this.mySQLManager = new MySQLManager(this);
        this.appearanceRegistry = this.registry.register(new AppearanceRegistry(this), RegistrationChoice.GLOBAL_IF_AVAILABLE);
        this.translationManager = new TranslationManager(this);
        this.disableHooks = new ArrayList<Runnable>();
        return true;
    }
    
    private void onGlobalDisable() {
        this.disableHooks.forEach(Runnable::run);
    }
    
    protected abstract void onEnable();
    
    protected abstract void onDisable();
    
    protected abstract void onInitialization();
    
    protected abstract void onPlatformEnable();
    
    protected abstract void onPlatformDisable();
    
    public BOOTSTRAP getBootstrap() {
        return this.bootstrap;
    }
    
    public abstract String getName();
    
    public abstract String getVersion();
    
    public abstract int getBuildNumber();
    
    public abstract File getPluginFolder();
    
    public abstract File getServerFolder();
    
    public abstract Scheduler getScheduler();
    
    public abstract SyncingAgent getSyncingAgent();
    
    public abstract InputStream getResource(final String p0);
    
    public abstract PluginReloader<?> getPluginReloader();
    
    protected abstract void sendConsole(final String p0);
    
    public LegacySystemStorage getLegacySystemStorage() {
        return this.legacySystemStorage;
    }
    
    public Registry getRegistry() {
        return this.registry;
    }
    
    public AppearanceRegistry getAppearanceRegistry() {
        return this.appearanceRegistry;
    }
    
    public String getPrefix() {
        return Text.chatColor(this.appearanceRegistry.getPrefix()) + " ";
    }
    
    public void log(final String str) {
        this.sendConsole("§b[§7" + this.getName() + "§b] §r" + str);
    }
    
    public MySQLManager getMySQLManager() {
        return this.mySQLManager;
    }
    
    public void addDisableHook(final Runnable runnable) {
        this.disableHooks.add(runnable);
    }
    
    public boolean isAllowingInvalidMySQL() {
        return false;
    }
    
    public TranslationManager getTranslationManager() {
        return this.translationManager;
    }
}
