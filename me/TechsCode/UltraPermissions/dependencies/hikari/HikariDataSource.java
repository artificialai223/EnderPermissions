

package me.TechsCode.EnderPermissions.dependencies.hikari;

import me.TechsCode.EnderPermissions.dependencies.slf4j.LoggerFactory;
import me.TechsCode.EnderPermissions.dependencies.hikari.metrics.MetricsTrackerFactory;
import java.io.PrintWriter;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLException;
import java.sql.Connection;
import me.TechsCode.EnderPermissions.dependencies.hikari.pool.HikariPool;
import java.util.concurrent.atomic.AtomicBoolean;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;
import java.io.Closeable;
import javax.sql.DataSource;

public class HikariDataSource extends HikariConfig implements DataSource, Closeable
{
    private static final Logger LOGGER;
    private final AtomicBoolean isShutdown;
    private final HikariPool fastPathPool;
    private volatile HikariPool pool;
    
    public HikariDataSource() {
        this.isShutdown = new AtomicBoolean();
        this.fastPathPool = null;
    }
    
    public HikariDataSource(final HikariConfig hikariConfig) {
        this.isShutdown = new AtomicBoolean();
        hikariConfig.validate();
        hikariConfig.copyState(this);
        HikariDataSource.LOGGER.info("{} - is starting.", hikariConfig.getPoolName());
        final HikariPool hikariPool = new HikariPool(this);
        this.fastPathPool = hikariPool;
        this.pool = hikariPool;
    }
    
    @Override
    public Connection getConnection() {
        if (this.isClosed()) {
            throw new SQLException("HikariDataSource " + this + " has been closed.");
        }
        if (this.fastPathPool != null) {
            return this.fastPathPool.getConnection();
        }
        HikariPool hikariPool = this.pool;
        if (hikariPool == null) {
            synchronized (this) {
                hikariPool = this.pool;
                if (hikariPool == null) {
                    this.validate();
                    HikariDataSource.LOGGER.info("{} - is starting.", this.getPoolName());
                    hikariPool = (this.pool = new HikariPool(this));
                }
            }
        }
        return hikariPool.getConnection();
    }
    
    @Override
    public Connection getConnection(final String s, final String s2) {
        throw new SQLFeatureNotSupportedException();
    }
    
    @Override
    public PrintWriter getLogWriter() {
        return (this.pool != null) ? this.pool.getUnwrappedDataSource().getLogWriter() : null;
    }
    
    @Override
    public void setLogWriter(final PrintWriter logWriter) {
        if (this.pool != null) {
            this.pool.getUnwrappedDataSource().setLogWriter(logWriter);
        }
    }
    
    @Override
    public void setLoginTimeout(final int loginTimeout) {
        if (this.pool != null) {
            this.pool.getUnwrappedDataSource().setLoginTimeout(loginTimeout);
        }
    }
    
    @Override
    public int getLoginTimeout() {
        return (this.pool != null) ? this.pool.getUnwrappedDataSource().getLoginTimeout() : 0;
    }
    
    @Override
    public java.util.logging.Logger getParentLogger() {
        throw new SQLFeatureNotSupportedException();
    }
    
    @Override
    public <T> T unwrap(final Class<T> obj) {
        if (obj.isInstance(this)) {
            return (T)this;
        }
        if (this.pool != null) {
            if (obj.isInstance(this.pool.getUnwrappedDataSource())) {
                return (T)this.pool.getUnwrappedDataSource();
            }
            if (this.pool.getUnwrappedDataSource() != null) {
                return this.pool.getUnwrappedDataSource().unwrap(obj);
            }
        }
        throw new SQLException("Wrapped DataSource is not an instance of " + obj);
    }
    
    @Override
    public boolean isWrapperFor(final Class<?> clazz) {
        if (clazz.isInstance(this)) {
            return true;
        }
        if (this.pool != null) {
            if (clazz.isInstance(this.pool.getUnwrappedDataSource())) {
                return true;
            }
            if (this.pool.getUnwrappedDataSource() != null) {
                return this.pool.getUnwrappedDataSource().isWrapperFor(clazz);
            }
        }
        return false;
    }
    
    @Override
    public void setMetricRegistry(final Object metricRegistry) {
        final boolean b = this.getMetricRegistry() != null;
        super.setMetricRegistry(metricRegistry);
        if (this.pool != null) {
            if (b) {
                throw new IllegalStateException("MetricRegistry can only be set one time");
            }
            this.pool.setMetricRegistry(super.getMetricRegistry());
        }
    }
    
    @Override
    public void setMetricsTrackerFactory(final MetricsTrackerFactory metricsTrackerFactory) {
        final boolean b = this.getMetricsTrackerFactory() != null;
        super.setMetricsTrackerFactory(metricsTrackerFactory);
        if (this.pool != null) {
            if (b) {
                throw new IllegalStateException("MetricsTrackerFactory can only be set one time");
            }
            this.pool.setMetricsTrackerFactory(super.getMetricsTrackerFactory());
        }
    }
    
    @Override
    public void setHealthCheckRegistry(final Object healthCheckRegistry) {
        final boolean b = this.getHealthCheckRegistry() != null;
        super.setHealthCheckRegistry(healthCheckRegistry);
        if (this.pool != null) {
            if (b) {
                throw new IllegalStateException("HealthCheckRegistry can only be set one time");
            }
            this.pool.setHealthCheckRegistry(super.getHealthCheckRegistry());
        }
    }
    
    public void evictConnection(final Connection connection) {
        if (!this.isClosed() && this.pool != null && connection.getClass().getName().startsWith("me.TechsCode.EnderPermissions.dependencies.hikari")) {
            this.pool.evictConnection(connection);
        }
    }
    
    public void suspendPool() {
        if (!this.isClosed() && this.pool != null) {
            this.pool.suspendPool();
        }
    }
    
    public void resumePool() {
        if (!this.isClosed() && this.pool != null) {
            this.pool.resumePool();
        }
    }
    
    @Override
    public void close() {
        if (this.isShutdown.getAndSet(true)) {
            return;
        }
        if (this.pool != null) {
            try {
                this.pool.shutdown();
            }
            catch (InterruptedException ex) {
                HikariDataSource.LOGGER.warn("Interrupted during closing", ex);
            }
        }
    }
    
    public boolean isClosed() {
        return this.isShutdown.get();
    }
    
    @Deprecated
    public void shutdown() {
        HikariDataSource.LOGGER.warn("The shutdown() method has been deprecated, please use the close() method instead");
        this.close();
    }
    
    @Override
    public String toString() {
        return "HikariDataSource (" + this.pool + ")";
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(HikariDataSource.class);
    }
}
