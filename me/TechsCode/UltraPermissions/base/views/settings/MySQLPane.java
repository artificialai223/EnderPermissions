

package me.TechsCode.EnderPermissions.base.views.settings;

import me.TechsCode.EnderPermissions.base.views.RestartView;
import me.TechsCode.EnderPermissions.base.mysql.ConnectionTestResult;
import me.TechsCode.EnderPermissions.base.mysql.ConnectionFactory;
import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.misc.Setter;
import me.TechsCode.EnderPermissions.base.misc.Getter;
import me.TechsCode.EnderPermissions.tpl.Tools;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.base.gui.Model;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.translations.TBase;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.base.mysql.MySQLCredentials;
import me.TechsCode.EnderPermissions.base.mysql.MySQLRegistry;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;

public class MySQLPane extends SettingsPane
{
    private final SpigotTechPlugin plugin;
    private final MySQLRegistry registry;
    private MySQLCredentials credentials;
    private boolean ssl;
    private int minimumIdle;
    private int maximumPoolSize;
    private boolean intro;
    private boolean loading;
    private int poolConfigurationNum;
    private SaveState state;
    private String error;
    
    public MySQLPane(final Player player, final SettingsView settingsView, final SpigotTechPlugin plugin) {
        super(player, settingsView);
        this.plugin = plugin;
        this.registry = plugin.getMySQLManager().getRegistry();
        this.credentials = this.registry.getCredentials();
        this.ssl = this.registry.hasSSL();
        this.minimumIdle = this.registry.getMinimumIdle();
        this.maximumPoolSize = this.registry.getMaximumPoolSize();
        this.intro = (this.credentials == null);
        this.state = SaveState.SAVED;
    }
    
    @Override
    public String getName() {
        return TBase.GUI_SETTINGS_MYSQL_NAME.toString();
    }
    
    @Override
    public XMaterial getIcon() {
        return XMaterial.ENDER_CHEST;
    }
    
    @Override
    public void construct(final Model model) {
        if (this.intro) {
            model.button(23, this::StartSetupButton);
        }
        else {
            model.button(20, this::CredentialsButton);
            model.button(21, this::SSLButton);
            model.button(22, this::PoolConfiguration);
            final boolean enabled = this.plugin.getMySQLManager().isEnabled();
            model.button(enabled ? 25 : 26, this.loading ? this::LoadingButton : this::ActionButton);
            if (enabled) {
                model.button(26, this::DisableButton);
            }
        }
    }
    
    private void StartSetupButton(final Button button) {
        button.material(XMaterial.EMERALD_BLOCK).name(Animation.wave(TBase.GUI_SETTINGS_MYSQL_SETUP_TITLE.toString(), Colors.Gold, Colors.Yellow)).lore(TBase.GUI_SETTINGS_MYSQL_SETUP_ACTION.get().toString(), "");
        button.item().appendLore(TBase.GUI_SETTINGS_MYSQL_SETUP_INFO.get().options().asTextBlock(25, "§7"));
        button.action(p0 -> this.intro = false);
    }
    
    private void CredentialsButton(final Button button) {
        button.material(XMaterial.PAPER).name(Animation.wave("Credentials", Colors.Gold, Colors.Yellow)).lore((this.credentials != null) ? "§bClick §7to §cclear §7MySQL Credentials" : "§bClick §7to enter MySQL Credentials", "", "§7Connection Url:", "§f" + ((this.credentials != null) ? this.credentials.getHostname() : "-") + ":" + ((this.credentials != null) ? this.credentials.getPort() : "-") + "/" + ((this.credentials != null) ? this.credentials.getDatabase() : "-"), "", "§7Username: §e" + ((this.credentials != null) ? this.credentials.getUsername() : "-"), "§7Password: §e" + ((this.credentials != null) ? Tools.getSecretPassword(this.credentials.getPassword()) : "-"), "", "§7These must be the same for §aevery §7Server");
        button.action(p0 -> {
            if (this.credentials != null) {
                this.credentials = null;
                this.state = SaveState.UNSAVED_CHANGES;
            }
            else {
                new MySQLSetup(this.p, this.plugin) {
                    final /* synthetic */ MySQLCredentials val$credentials;
                    
                    @Override
                    public void onClose() {
                        MySQLPane.this.reopen();
                    }
                    
                    @Override
                    public void onCompletion() {
                        MySQLPane.this.credentials = this.val$credentials;
                        MySQLPane.this.state = SaveState.UNSAVED_CHANGES;
                        MySQLPane.this.reopen();
                    }
                    
                    @Override
                    public SetupStage[] getStages() {
                        return new SetupStage[] { new SetupStage("Hostname", "localhost", this.val$credentials::setHostname), new SetupStage("Port", "3306", this.val$credentials::setPort), new SetupStage("Database", "database", this.val$credentials::setDatabase), new SetupStage("Username", "root", this.val$credentials::setUsername), new SetupStage("Password", "yourpassword", this.val$credentials::setPassword) };
                    }
                };
            }
        });
    }
    
    private void SSLButton(final Button button) {
        button.material(XMaterial.REPEATER).name(Animation.wave("SSL", Colors.Gold, Colors.Yellow)).lore(this.ssl ? "§7Click to §adisable §7encryption" : "§7Click to §cenable §7encryption", "", "§7SSL: §e" + (this.ssl ? "enabled" : "disabled"));
        button.action(p0 -> {
            this.ssl = !this.ssl;
            this.state = SaveState.UNSAVED_CHANGES;
        });
    }
    
    private void PoolConfiguration(final Button button) {
        class 1PoolSetting
        {
            private final String name = () -> MySQLPane.this.minimumIdle;
            private final Getter<Integer> getValue = n -> MySQLPane.this.minimumIdle = n;
            private final Setter<Integer> setValue;
            
            1PoolSetting(final Getter<Integer> getValue, final Setter<Integer> setValue) {
                this.setValue = setValue;
            }
        }
        final 1PoolSetting[] array = { new 1PoolSetting("Minimum Idle Connections", n -> MySQLPane.this.minimumIdle = n), new 1PoolSetting("Maximum Active Connections", n2 -> this.maximumPoolSize = n2) };
        final 1PoolSetting obj = array[this.poolConfigurationNum];
        button.material(XMaterial.WATER_BUCKET).name(Animation.wave("Connection Limits", Colors.Gold, Colors.Yellow)).lore("§bLeft Click §7to §aincrease", "§bRight Click §7to §cdecrease", "§bPress Q §7to switch option", "");
        for (final 1PoolSetting 1PoolSetting : array) {
            if (1PoolSetting.equals(obj)) {
                button.item().appendLore("§a§l> §7" + 1PoolSetting.name + ": §e" + 1PoolSetting.getValue.get());
            }
            else {
                button.item().appendLore("§7" + 1PoolSetting.name + ": §e" + 1PoolSetting.getValue.get());
            }
        }
        button.item().appendLore("", "§7Do not change these limits if you do", "§7not know what they are!");
        final 1PoolSetting 1PoolSetting2;
        button.action(ActionType.LEFT, () -> {
            1PoolSetting2.setValue.set((int)1PoolSetting2.getValue.get() + 1);
            this.state = SaveState.UNSAVED_CHANGES;
            return;
        });
        final 1PoolSetting 1PoolSetting3;
        button.action(ActionType.RIGHT, () -> {
            1PoolSetting3.setValue.set(((int)1PoolSetting3.getValue.get() == 0) ? 0 : ((int)1PoolSetting3.getValue.get() - 1));
            this.state = SaveState.UNSAVED_CHANGES;
            return;
        });
        button.action(ActionType.Q, () -> {
            if (this.poolConfigurationNum < 1) {
                ++this.poolConfigurationNum;
            }
            else {
                this.poolConfigurationNum = 0;
            }
        });
    }
    
    private void ActionButton(final Button button) {
        switch (this.state) {
            case SAVED: {
                button.material(XMaterial.STONE).name(Animation.wave("Everything Saved", Colors.Gray, Colors.White)).lore("§7There are no changes to be saved");
                break;
            }
            case UNSAVED_CHANGES: {
                button.material(XMaterial.EMERALD_ORE).name(Animation.wave("Test Changes", Colors.Green, Colors.White)).lore("§7Click to test §echanges", "", "§7Hostname: §f" + ((this.credentials != null) ? this.credentials.getHostname() : "-"), "§7Port: §f" + ((this.credentials != null) ? this.credentials.getPort() : "-"), "§7Database: §f" + ((this.credentials != null) ? this.credentials.getDatabase() : "-"), "§7Username: §f" + ((this.credentials != null) ? this.credentials.getUsername() : "-"), "§7Password: §f" + ((this.credentials != null) ? Tools.getSecretPassword(this.credentials.getPassword()) : "-"), "", "§7SSL: §f" + (this.ssl ? "enabled" : "disabled"), "", "§7Minimum Idle: §f" + this.minimumIdle, "§7Maximum Pool Size: §f" + this.maximumPoolSize);
                ConnectionFactory connectionFactory;
                final ConnectionTestResult connectionTestResult;
                button.action(p0 -> this.plugin.getScheduler().runAsync(() -> {
                    if (this.credentials == null) {
                        this.state = SaveState.INVALID_UNSAVED_CHANGES;
                        this.error = "No credentials provided.";
                    }
                    else {
                        this.loading = true;
                        connectionFactory = new ConnectionFactory(this.credentials, this.ssl, this.minimumIdle, this.maximumPoolSize);
                        connectionFactory.testConnection();
                        connectionFactory.close();
                        this.loading = false;
                        if (connectionTestResult.isValid()) {
                            this.state = SaveState.CORRECT_UNSAVED_CHANGES;
                        }
                        else {
                            this.state = SaveState.INVALID_UNSAVED_CHANGES;
                            this.error = connectionTestResult.getError();
                        }
                    }
                }));
                break;
            }
            case CORRECT_UNSAVED_CHANGES: {
                button.material(XMaterial.EMERALD_BLOCK).name(Animation.wave("Save", Colors.Green, Colors.White)).lore("§7Click to §asave §7changes");
                button.action(p0 -> {
                    this.registry.setCredentials(this.credentials);
                    this.registry.setSSL(this.ssl);
                    this.registry.setMinimumIdle(this.minimumIdle);
                    this.registry.setMaximumPoolSize(this.maximumPoolSize);
                    this.state = SaveState.SAVED;
                    return;
                });
                break;
            }
            case INVALID_UNSAVED_CHANGES: {
                button.material(XMaterial.REDSTONE_BLOCK).name(Animation.wave("Could not connect", Colors.Red, Colors.White)).lore("§7Check if your settings are correct & if the database is accessible", "", "§7Connection Error: ");
                final String[] lineSplitter = Tools.lineSplitter(this.error, 60);
                for (int length = lineSplitter.length, i = 0; i < length; ++i) {
                    button.item().appendLore("§f" + lineSplitter[i]);
                }
                break;
            }
        }
    }
    
    private void LoadingButton(final Button button) {
        button.material(XMaterial.EXPERIENCE_BOTTLE).name(Animation.fading("Testing Connection", true, 3, 6, Colors.Yellow, Colors.White)).lore("§7Please wait while the plugin runs all connection tests");
    }
    
    private void DisableButton(final Button button) {
        button.material(XMaterial.RED_STAINED_GLASS).name(Animation.wave("Deactivate", Colors.Red, Colors.White)).lore(TBase.GUI_SETTINGS_MYSQL_DISABLE_ACTION.get().toString(), "");
        button.item().appendLore(TBase.GUI_SETTINGS_MYSQL_RESTARTREQUIRED.get().options().asTextBlock(30, "§7"));
        button.action(p0 -> {
            this.credentials = null;
            this.registry.clearCredentials();
            this.state = SaveState.SAVED;
            new RestartView(this.p, this.plugin);
        });
    }
    
    public enum SaveState
    {
        SAVED, 
        UNSAVED_CHANGES, 
        CORRECT_UNSAVED_CHANGES, 
        INVALID_UNSAVED_CHANGES;
    }
}
