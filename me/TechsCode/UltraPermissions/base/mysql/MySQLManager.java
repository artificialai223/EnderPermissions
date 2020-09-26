

package me.TechsCode.EnderPermissions.base.mysql;

import java.sql.Connection;
import me.TechsCode.EnderPermissions.base.legacySystemStorage.LegacyMySQLSettings;
import me.TechsCode.EnderPermissions.base.mysql.credentialsTransfer.SpigotMySQLSender;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.base.mysql.credentialsTransfer.ProxyMySQLReceiver;
import me.TechsCode.EnderPermissions.base.BungeeTechPlugin;
import me.TechsCode.EnderPermissions.base.registry.RegistryStorable;
import me.TechsCode.EnderPermissions.base.registry.RegistrationChoice;
import me.TechsCode.EnderPermissions.base.TechPlugin;

public class MySQLManager
{
    private TechPlugin plugin;
    private MySQLRegistry registry;
    private ConnectionFactory factory;
    
    public MySQLManager(final TechPlugin plugin) {
        this.plugin = plugin;
        this.registry = (MySQLRegistry)plugin.getRegistry().register((Class<? extends RegistryStorable>)MySQLRegistry.class, RegistrationChoice.LOCAL);
        final LegacyMySQLSettings mySQL = plugin.getLegacySystemStorage().getMySQL();
        if (mySQL != null) {
            this.registry.setCredentials(mySQL.asCredentials());
        }
        if (plugin instanceof BungeeTechPlugin) {
            new ProxyMySQLReceiver((BungeeTechPlugin)plugin, this);
        }
        else if (plugin instanceof SpigotTechPlugin) {
            new SpigotMySQLSender((SpigotTechPlugin)plugin, this);
        }
        this.setup();
    }
    
    public void setup() {
        this.close();
        if (this.registry.hasCredentials()) {
            this.factory = new ConnectionFactory(this.registry.getCredentials(), this.registry.hasSSL(), this.registry.getMinimumIdle(), this.registry.getMaximumPoolSize());
            final ConnectionTestResult testConnection = this.factory.testConnection();
            if (!testConnection.isValid()) {
                this.factory = null;
                this.plugin.log("§cCould not contact MySQL Server:");
                this.plugin.log("§c" + testConnection.getError());
            }
        }
        else {
            this.factory = null;
        }
    }
    
    public MySQLRegistry getRegistry() {
        return this.registry;
    }
    
    public boolean isEnabled() {
        return this.factory != null;
    }
    
    public Connection newConnection() {
        return (this.factory != null) ? this.factory.newConnection() : null;
    }
    
    public void close() {
        if (this.factory != null) {
            this.factory.close();
            this.factory = null;
        }
    }
}
