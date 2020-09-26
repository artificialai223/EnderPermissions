

package me.TechsCode.EnderPermissions.base.mysql;

import java.sql.SQLException;
import java.sql.Connection;
import me.TechsCode.EnderPermissions.dependencies.hikari.HikariConfig;
import me.TechsCode.EnderPermissions.dependencies.hikari.HikariDataSource;

public class ConnectionFactory
{
    private HikariDataSource dataSource;
    private String error;
    
    public ConnectionFactory(final MySQLCredentials mySQLCredentials, final boolean b, final int minimumIdle, final int maximumPoolSize) {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + mySQLCredentials.getHostname() + ":" + mySQLCredentials.getPort() + "/" + mySQLCredentials.getDatabase() + "?useSSL=" + b + "&characterEncoding=utf-8");
        hikariConfig.setUsername(mySQLCredentials.getUsername());
        hikariConfig.setPassword(mySQLCredentials.getPassword());
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        try {
            (this.dataSource = new HikariDataSource(hikariConfig)).setMinimumIdle(minimumIdle);
            this.dataSource.setMaximumPoolSize(maximumPoolSize);
            this.dataSource.setMaxLifetime(1800000L);
            this.dataSource.setConnectionTimeout(5000L);
            this.dataSource.setLeakDetectionThreshold(48000L);
        }
        catch (Exception ex) {
            this.error = ex.getMessage();
        }
    }
    
    public Connection newConnection() {
        return (this.dataSource != null) ? this.dataSource.getConnection() : null;
    }
    
    public ConnectionTestResult testConnection() {
        if (this.error != null) {
            return new ConnectionTestResult(false, this.error);
        }
        try {
            this.newConnection();
            return new ConnectionTestResult(true, null);
        }
        catch (SQLException ex) {
            return new ConnectionTestResult(false, ex.getMessage());
        }
    }
    
    public void close() {
        if (this.dataSource != null) {
            this.dataSource.close();
        }
    }
}
