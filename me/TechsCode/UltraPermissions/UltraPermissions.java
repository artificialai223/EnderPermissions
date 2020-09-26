

package me.TechsCode.EnderPermissions;

import me.TechsCode.EnderPermissions.hooks.placeholders.RankTimerPlaceholder;
import me.TechsCode.EnderPermissions.hooks.placeholders.RankPlaceholder;
import me.TechsCode.EnderPermissions.hooks.placeholders.SuffixPlaceholder;
import me.TechsCode.EnderPermissions.hooks.placeholders.SuffixesPlaceholder;
import me.TechsCode.EnderPermissions.hooks.placeholders.SecondarySuffixPlaceholder;
import me.TechsCode.EnderPermissions.hooks.placeholders.SecondaryPrefixPlaceholder;
import me.TechsCode.EnderPermissions.hooks.placeholders.PrefixPlaceholder;
import me.TechsCode.EnderPermissions.hooks.placeholders.PrefixesPlaceholder;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.permissionlogger.LoggedPermission;
import me.TechsCode.EnderPermissions.base.networking.NServer;
import java.util.Optional;
import me.TechsCode.EnderPermissions.storage.collection.PermissionList;
import me.TechsCode.EnderPermissions.storage.collection.UserList;
import me.TechsCode.EnderPermissions.storage.collection.GroupList;
import java.util.UUID;
import me.TechsCode.EnderPermissions.storage.PermissionCreator;
import me.TechsCode.EnderPermissions.storage.objects.Holder;
import me.TechsCode.EnderPermissions.storage.GroupCreator;
import java.util.function.Consumer;
import me.TechsCode.EnderPermissions.internal.ModifiedPermissible;
import java.util.Collection;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.base.storage.Storage;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.commands.UPCCommand;
import me.TechsCode.EnderPermissions.commands.MainCommand;
import me.TechsCode.EnderPermissions.visual.VisualManager;
import me.TechsCode.EnderPermissions.storage.SpigotUserStorage;
import me.TechsCode.EnderPermissions.storage.SpigotPermissionStorage;
import me.TechsCode.EnderPermissions.base.storage.StorageImplementation;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.storage.SpigotGroupStorage;
import me.TechsCode.EnderPermissions.base.storage.implementations.LocalFile;
import me.TechsCode.EnderPermissions.base.storage.implementations.MySQL;
import me.TechsCode.EnderPermissions.migration.ServerIdentifierMigration;
import me.TechsCode.EnderPermissions.base.registry.RegistrationChoice;
import me.TechsCode.EnderPermissions.base.translations.GithubTranslationLoader;
import me.TechsCode.EnderPermissions.base.translations.TranslationHolder;
import org.bukkit.plugin.java.JavaPlugin;
import me.TechsCode.EnderPermissions.hooks.PluginHookManager;
import me.TechsCode.EnderPermissions.permissionlogger.PermissionLogger;
import me.TechsCode.EnderPermissions.permissionDatabase.PermissionDatabase;
import me.TechsCode.EnderPermissions.storage.UserStorage;
import me.TechsCode.EnderPermissions.storage.PermissionStorage;
import me.TechsCode.EnderPermissions.storage.GroupStorage;
import me.TechsCode.EnderPermissions.base.networking.SpigotNetworkManager;
import me.TechsCode.EnderPermissions.visual.VisualRegistry;
import me.TechsCode.EnderPermissions.base.registry.SwitchableBiRegistry;
import me.TechsCode.EnderPermissions.hooks.UpermsPlaceholder;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;

public class EnderPermissions extends SpigotTechPlugin implements EnderPermissionsAPI, StorageController
{
    private static EnderPermissionsAPI api;
    public static final UpermsPlaceholder[] placeholders;
    private PublicRegistry publicRegistry;
    private PrivateRegistry privateRegistry;
    private SwitchableBiRegistry<VisualRegistry> visualRegistry;
    private SpigotNetworkManager networkManager;
    private GroupStorage groupStorage;
    private PermissionStorage permissionStorage;
    private UserStorage userStorage;
    private PermissionDatabase permissionDatabase;
    private PermissionLogger permissionLogger;
    private PluginHookManager pluginHookManager;
    private PluginEvents pluginEvents;
    
    public EnderPermissions(final JavaPlugin javaPlugin) {
        super(javaPlugin);
    }
    
    public void onEnable() {
        EnderPermissions.api = this;
        this.getTranslationManager().registerTranslationHolder(T.class);
        GithubTranslationLoader.loadAll(this, "TechsCode", "PluginResources", "Translations");
        this.publicRegistry = this.getRegistry().register((Class<? extends PublicRegistry>)PublicRegistry.class, RegistrationChoice.GLOBAL_IF_AVAILABLE);
        this.privateRegistry = this.getRegistry().register((Class<? extends PrivateRegistry>)PrivateRegistry.class, RegistrationChoice.LOCAL);
        this.visualRegistry = new SwitchableBiRegistry<VisualRegistry>(this.getRegistry().register((Class<? extends VisualRegistry>)VisualRegistry.class));
        this.networkManager = new SpigotNetworkManager(this);
        ServerIdentifierMigration.migrate(this, this.privateRegistry, this.networkManager);
        final boolean enabled = this.getMySQLManager().isEnabled();
        this.groupStorage = new SpigotGroupStorage(this, (Class<? extends StorageImplementation>)(enabled ? MySQL.class : LocalFile.class));
        this.permissionStorage = new SpigotPermissionStorage(this, (Class<? extends StorageImplementation>)(enabled ? MySQL.class : LocalFile.class));
        this.userStorage = new SpigotUserStorage(this, (Class<? extends StorageImplementation>)(enabled ? MySQL.class : LocalFile.class));
        this.permissionDatabase = new PermissionDatabase(this);
        this.permissionLogger = new PermissionLogger(this);
        this.pluginHookManager = new PluginHookManager(this);
        new VisualManager(this, this.visualRegistry.get());
        new HeadPreloader(this);
        new MainCommand(this);
        new UPCCommand(this);
        this.pluginEvents = new PluginEvents(this, this.permissionLogger);
        this.getScheduler().runTaskTimer(() -> {
            this.getUsers().forEach(User::clean);
            this.getGroups().forEach(Group::clean);
        }, 20L, 6000L);
    }
    
    public void issueManualSynchronization() {
        this.getSyncingAgent().notifyNewDataChanges(this.groupStorage, "*");
        this.getSyncingAgent().notifyNewDataChanges(this.permissionStorage, "*");
        this.getSyncingAgent().notifyNewDataChanges(this.userStorage, "*");
    }
    
    @Override
    public void onDataModification() {
        if (this.pluginEvents != null) {
            new ArrayList(this.pluginEvents.getPermissibles()).forEach((Consumer)ModifiedPermissible::clearCache);
        }
        if (this.pluginHookManager != null) {
            this.pluginHookManager.clearCaches();
        }
    }
    
    public SwitchableBiRegistry<VisualRegistry> getVisualRegistry() {
        return this.visualRegistry;
    }
    
    @Override
    protected void onDisable() {
    }
    
    public static EnderPermissionsAPI getAPI() {
        return EnderPermissions.api;
    }
    
    public DefaultGroupAssignOption getDefaultGroupAssignOption() {
        return this.publicRegistry.getDefaultAssignOption();
    }
    
    public void setDefaultGroupAssignOption(final DefaultGroupAssignOption defaultGroupAssignOption) {
        this.publicRegistry.setDefaultGroupAssignOption(defaultGroupAssignOption);
    }
    
    public void setDefaultPermissions(final boolean defaultPermissions) {
        this.publicRegistry.setDefaultPermissions(defaultPermissions);
    }
    
    public boolean isDefaultPermissionsEnabled() {
        return this.publicRegistry.isDefaultPermissions();
    }
    
    @Override
    public GroupCreator newGroup(final String s) {
        return this.groupStorage.newGroup(s);
    }
    
    @Override
    public PermissionCreator newPermission(final String s, final Holder holder) {
        return this.permissionStorage.newPermission(s, holder);
    }
    
    public User registerUser(final UUID uuid, final String s, final boolean b) {
        return this.userStorage.registerUser(uuid, s, b);
    }
    
    @Override
    public GroupList getGroups() {
        return this.groupStorage.getGroups();
    }
    
    @Override
    public UserList getUsers() {
        return this.userStorage.getUsers();
    }
    
    @Override
    public PermissionList getPermissions() {
        return this.permissionStorage.getPermissions();
    }
    
    public Optional<NServer> getThisServer() {
        return this.networkManager.getThisServer();
    }
    
    public PermissionDatabase getPermissionDatabase() {
        return this.permissionDatabase;
    }
    
    public LoggedPermission[] getLoggedPermissionChecks() {
        return this.permissionLogger.getLoggedPermissions();
    }
    
    public String replacePlaceholders(final Player player, final String s) {
        return this.pluginHookManager.replacePlaceholders(player, s);
    }
    
    @Override
    public GroupStorage getGroupStorage() {
        return this.groupStorage;
    }
    
    @Override
    public PermissionStorage getPermissionStorage() {
        return this.permissionStorage;
    }
    
    @Override
    public UserStorage getUserStorage() {
        return this.userStorage;
    }
    
    public boolean isConnectedToNetwork() {
        return this.networkManager.getData().isPresent();
    }
    
    public SpigotNetworkManager getNetworkManager() {
        return this.networkManager;
    }
    
    static {
        placeholders = new UpermsPlaceholder[] { new PrefixesPlaceholder(), new PrefixPlaceholder(), new SecondaryPrefixPlaceholder(), new SecondarySuffixPlaceholder(), new SuffixesPlaceholder(), new SuffixPlaceholder(), new RankPlaceholder(), new RankTimerPlaceholder() };
    }
}
