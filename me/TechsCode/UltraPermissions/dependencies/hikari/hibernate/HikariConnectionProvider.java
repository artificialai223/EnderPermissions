

package me.TechsCode.EnderPermissions.dependencies.hikari.hibernate;

import me.TechsCode.EnderPermissions.dependencies.slf4j.LoggerFactory;
import org.hibernate.service.UnknownUnwrapTypeException;
import javax.sql.DataSource;
import java.sql.Connection;
import org.hibernate.HibernateException;
import java.util.Map;
import org.hibernate.Version;
import me.TechsCode.EnderPermissions.dependencies.hikari.HikariDataSource;
import me.TechsCode.EnderPermissions.dependencies.hikari.HikariConfig;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;
import org.hibernate.service.spi.Stoppable;
import org.hibernate.service.spi.Configurable;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class HikariConnectionProvider implements ConnectionProvider, Configurable, Stoppable
{
    private static final long serialVersionUID = -9131625057941275711L;
    private static final Logger LOGGER;
    private HikariConfig hcfg;
    private HikariDataSource hds;
    
    public HikariConnectionProvider() {
        this.hcfg = null;
        this.hds = null;
        if (Version.getVersionString().substring(0, 5).compareTo("4.3.6") >= 1) {
            HikariConnectionProvider.LOGGER.warn("me.TechsCode.EnderPermissions.dependencies.hikari.hibernate.HikariConnectionProvider has been deprecated for versions of Hibernate 4.3.6 and newer.  Please switch to org.hibernate.hikaricp.internal.HikariCPConnectionProvider.");
        }
    }
    
    public void configure(final Map map) {
        try {
            HikariConnectionProvider.LOGGER.debug("Configuring HikariCP");
            this.hcfg = HikariConfigurationUtil.loadConfiguration(map);
            this.hds = new HikariDataSource(this.hcfg);
        }
        catch (Exception ex) {
            throw new HibernateException((Throwable)ex);
        }
        HikariConnectionProvider.LOGGER.debug("HikariCP Configured");
    }
    
    public Connection getConnection() {
        Connection connection = null;
        if (this.hds != null) {
            connection = this.hds.getConnection();
        }
        return connection;
    }
    
    public void closeConnection(final Connection connection) {
        connection.close();
    }
    
    public boolean supportsAggressiveRelease() {
        return false;
    }
    
    public boolean isUnwrappableAs(final Class obj) {
        return ConnectionProvider.class.equals(obj) || HikariConnectionProvider.class.isAssignableFrom(obj);
    }
    
    public <T> T unwrap(final Class<T> obj) {
        if (ConnectionProvider.class.equals(obj) || HikariConnectionProvider.class.isAssignableFrom(obj)) {
            return (T)this;
        }
        if (DataSource.class.isAssignableFrom(obj)) {
            return (T)this.hds;
        }
        throw new UnknownUnwrapTypeException((Class)obj);
    }
    
    public void stop() {
        this.hds.close();
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(HikariConnectionProvider.class);
    }
}
