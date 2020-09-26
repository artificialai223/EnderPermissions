

package me.TechsCode.EnderPermissions.dependencies.hikari.util;

import me.TechsCode.EnderPermissions.dependencies.slf4j.LoggerFactory;
import java.sql.SQLFeatureNotSupportedException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Iterator;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Map;
import java.sql.Driver;
import java.util.Properties;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;
import javax.sql.DataSource;

public final class DriverDataSource implements DataSource
{
    private static final Logger LOGGER;
    private final String jdbcUrl;
    private final Properties driverProperties;
    private Driver driver;
    
    public DriverDataSource(final String s, final String str, final Properties properties, final String defaultValue, final String defaultValue2) {
        this.jdbcUrl = s;
        this.driverProperties = new Properties();
        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            this.driverProperties.setProperty(entry.getKey().toString(), entry.getValue().toString());
        }
        if (defaultValue != null) {
            this.driverProperties.put("user", this.driverProperties.getProperty("user", defaultValue));
        }
        if (defaultValue2 != null) {
            this.driverProperties.put("password", this.driverProperties.getProperty("password", defaultValue2));
        }
        if (str != null) {
            final Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                final Driver driver = drivers.nextElement();
                if (driver.getClass().getName().equals(str)) {
                    this.driver = driver;
                    break;
                }
            }
            if (this.driver == null) {
                DriverDataSource.LOGGER.warn("Registered driver with driverClassName={} was not found, trying direct instantiation.", str);
                try {
                    this.driver = (Driver)this.getClass().getClassLoader().loadClass(str).newInstance();
                }
                catch (Exception ex) {
                    DriverDataSource.LOGGER.warn("Could not instantiate instance of driver class {}, trying JDBC URL resolution", str, ex);
                }
            }
        }
        try {
            if (this.driver == null) {
                this.driver = DriverManager.getDriver(s);
            }
            else if (!this.driver.acceptsURL(s)) {
                throw new RuntimeException("Driver " + str + " claims to not accept JDBC URL " + s);
            }
        }
        catch (SQLException cause) {
            throw new RuntimeException("Unable to get driver instance for jdbcUrl=" + s, cause);
        }
    }
    
    @Override
    public Connection getConnection() {
        return this.driver.connect(this.jdbcUrl, this.driverProperties);
    }
    
    @Override
    public Connection getConnection(final String s, final String s2) {
        return this.getConnection();
    }
    
    @Override
    public PrintWriter getLogWriter() {
        throw new SQLFeatureNotSupportedException();
    }
    
    @Override
    public void setLogWriter(final PrintWriter printWriter) {
        throw new SQLFeatureNotSupportedException();
    }
    
    @Override
    public void setLoginTimeout(final int loginTimeout) {
        DriverManager.setLoginTimeout(loginTimeout);
    }
    
    @Override
    public int getLoginTimeout() {
        return DriverManager.getLoginTimeout();
    }
    
    @Override
    public java.util.logging.Logger getParentLogger() {
        return this.driver.getParentLogger();
    }
    
    @Override
    public <T> T unwrap(final Class<T> clazz) {
        throw new SQLFeatureNotSupportedException();
    }
    
    @Override
    public boolean isWrapperFor(final Class<?> clazz) {
        return false;
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(DriverDataSource.class);
    }
}
