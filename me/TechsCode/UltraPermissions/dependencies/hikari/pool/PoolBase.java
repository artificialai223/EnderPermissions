

package me.TechsCode.EnderPermissions.dependencies.hikari.pool;

import me.TechsCode.EnderPermissions.dependencies.hikari.util.ClockSource;
import me.TechsCode.EnderPermissions.dependencies.hikari.metrics.MetricsTracker;
import java.util.concurrent.Executors;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.DefaultThreadFactory;
import java.util.Properties;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.DriverDataSource;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.PropertyElf;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.sql.Connection;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.UtilityElf;
import me.TechsCode.EnderPermissions.dependencies.slf4j.LoggerFactory;
import java.util.concurrent.atomic.AtomicReference;
import javax.sql.DataSource;
import java.util.concurrent.Executor;
import me.TechsCode.EnderPermissions.dependencies.hikari.HikariConfig;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;

abstract class PoolBase
{
    private final Logger LOGGER;
    protected final HikariConfig config;
    protected final String poolName;
    protected long connectionTimeout;
    private static final String[] RESET_STATES;
    private static final int UNINITIALIZED = -1;
    private static final int TRUE = 1;
    private static final int FALSE = 0;
    private int networkTimeout;
    private int transactionIsolation;
    private int isNetworkTimeoutSupported;
    private int isQueryTimeoutSupported;
    private Executor netTimeoutExecutor;
    private DataSource dataSource;
    private final String catalog;
    private final boolean isReadOnly;
    private final boolean isAutoCommit;
    private final boolean isUseJdbc4Validation;
    private final boolean isIsolateInternalQueries;
    private final AtomicReference<Throwable> lastConnectionFailure;
    private volatile boolean isValidChecked;
    
    PoolBase(final HikariConfig config) {
        this.LOGGER = LoggerFactory.getLogger(PoolBase.class);
        this.config = config;
        this.networkTimeout = -1;
        this.catalog = config.getCatalog();
        this.isReadOnly = config.isReadOnly();
        this.isAutoCommit = config.isAutoCommit();
        this.transactionIsolation = UtilityElf.getTransactionIsolation(config.getTransactionIsolation());
        this.isQueryTimeoutSupported = -1;
        this.isNetworkTimeoutSupported = -1;
        this.isUseJdbc4Validation = (config.getConnectionTestQuery() == null);
        this.isIsolateInternalQueries = config.isIsolateInternalQueries();
        this.poolName = config.getPoolName();
        this.connectionTimeout = config.getConnectionTimeout();
        this.lastConnectionFailure = new AtomicReference<Throwable>();
        this.initializeDataSource();
    }
    
    @Override
    public String toString() {
        return this.poolName;
    }
    
    abstract void releaseConnection(final PoolEntry p0);
    
    void quietlyCloseConnection(final Connection connection, final String s) {
        if (connection == null) {
            return;
        }
        try {
            this.LOGGER.debug("{} - Closing connection {}: {}", this.poolName, connection, s);
            try {
                this.setNetworkTimeout(connection, TimeUnit.SECONDS.toMillis(15L));
            }
            finally {
                connection.close();
            }
        }
        catch (Throwable t) {
            this.LOGGER.debug("{} - Closing connection {} failed", this.poolName, connection, t);
        }
    }
    
    boolean isConnectionAlive(final Connection connection) {
        try {
            final long validationTimeout = this.config.getValidationTimeout();
            if (this.isUseJdbc4Validation) {
                return connection.isValid((int)TimeUnit.MILLISECONDS.toSeconds(validationTimeout));
            }
            final int andSetNetworkTimeout = this.getAndSetNetworkTimeout(connection, validationTimeout);
            try (final Statement statement = connection.createStatement()) {
                if (this.isNetworkTimeoutSupported != 1) {
                    this.setQueryTimeout(statement, (int)TimeUnit.MILLISECONDS.toSeconds(validationTimeout));
                }
                statement.execute(this.config.getConnectionTestQuery());
            }
            if (this.isIsolateInternalQueries && !this.isReadOnly && !this.isAutoCommit) {
                connection.rollback();
            }
            this.setNetworkTimeout(connection, andSetNetworkTimeout);
            return true;
        }
        catch (SQLException newValue) {
            this.lastConnectionFailure.set(newValue);
            this.LOGGER.warn("{} - Connection {} failed alive test with exception {}", this.poolName, connection, newValue.getMessage());
            return false;
        }
    }
    
    Throwable getLastConnectionFailure() {
        return this.lastConnectionFailure.getAndSet(null);
    }
    
    public DataSource getUnwrappedDataSource() {
        return this.dataSource;
    }
    
    PoolEntry newPoolEntry() {
        return new PoolEntry(this.newConnection(), this, this.isReadOnly, this.isAutoCommit);
    }
    
    void resetConnectionState(final Connection connection, final ProxyConnection proxyConnection, final int n) {
        int n2 = 0;
        if ((n & 0x1) != 0x0 && proxyConnection.getReadOnlyState() != this.isReadOnly) {
            connection.setReadOnly(this.isReadOnly);
            n2 |= 0x1;
        }
        if ((n & 0x2) != 0x0 && proxyConnection.getAutoCommitState() != this.isAutoCommit) {
            connection.setAutoCommit(this.isAutoCommit);
            n2 |= 0x2;
        }
        if ((n & 0x4) != 0x0 && proxyConnection.getTransactionIsolationState() != this.transactionIsolation) {
            connection.setTransactionIsolation(this.transactionIsolation);
            n2 |= 0x4;
        }
        if ((n & 0x8) != 0x0 && this.catalog != null && !this.catalog.equals(proxyConnection.getCatalogState())) {
            connection.setCatalog(this.catalog);
            n2 |= 0x8;
        }
        if ((n & 0x10) != 0x0 && proxyConnection.getNetworkTimeoutState() != this.networkTimeout) {
            this.setNetworkTimeout(connection, this.networkTimeout);
            n2 |= 0x10;
        }
        if (this.LOGGER.isDebugEnabled()) {
            this.LOGGER.debug("{} - Reset ({}) on connection {}", this.poolName, (n2 != 0) ? this.stringFromResetBits(n2) : "nothing", connection);
        }
    }
    
    void shutdownNetworkTimeoutExecutor() {
        if (this.netTimeoutExecutor != null && this.netTimeoutExecutor instanceof ThreadPoolExecutor) {
            ((ThreadPoolExecutor)this.netTimeoutExecutor).shutdownNow();
        }
    }
    
    void registerMBeans(final HikariPool hikariPool) {
        if (!this.config.isRegisterMbeans()) {
            return;
        }
        try {
            final MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
            final ObjectName objectName = new ObjectName("me.TechsCode.EnderPermissions.dependencies.hikari:type=PoolConfig (" + this.poolName + ")");
            final ObjectName objectName2 = new ObjectName("me.TechsCode.EnderPermissions.dependencies.hikari:type=Pool (" + this.poolName + ")");
            if (!platformMBeanServer.isRegistered(objectName)) {
                platformMBeanServer.registerMBean(this.config, objectName);
                platformMBeanServer.registerMBean(hikariPool, objectName2);
            }
            else {
                this.LOGGER.error("{} - You cannot use the same pool name for separate pool instances.", this.poolName);
            }
        }
        catch (Exception ex) {
            this.LOGGER.warn("{} - Unable to register management beans.", this.poolName, ex);
        }
    }
    
    void unregisterMBeans() {
        if (!this.config.isRegisterMbeans()) {
            return;
        }
        try {
            final MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
            final ObjectName objectName = new ObjectName("me.TechsCode.EnderPermissions.dependencies.hikari:type=PoolConfig (" + this.poolName + ")");
            final ObjectName objectName2 = new ObjectName("me.TechsCode.EnderPermissions.dependencies.hikari:type=Pool (" + this.poolName + ")");
            if (platformMBeanServer.isRegistered(objectName)) {
                platformMBeanServer.unregisterMBean(objectName);
                platformMBeanServer.unregisterMBean(objectName2);
            }
        }
        catch (Exception ex) {
            this.LOGGER.warn("{} - Unable to unregister management beans.", this.poolName, ex);
        }
    }
    
    private void initializeDataSource() {
        final String jdbcUrl = this.config.getJdbcUrl();
        final String username = this.config.getUsername();
        final String password = this.config.getPassword();
        final String dataSourceClassName = this.config.getDataSourceClassName();
        final String driverClassName = this.config.getDriverClassName();
        final Properties dataSourceProperties = this.config.getDataSourceProperties();
        DataSource dataSource = this.config.getDataSource();
        if (dataSourceClassName != null && dataSource == null) {
            dataSource = UtilityElf.createInstance(dataSourceClassName, DataSource.class, new Object[0]);
            PropertyElf.setTargetFromProperties(dataSource, dataSourceProperties);
        }
        else if (jdbcUrl != null && dataSource == null) {
            dataSource = new DriverDataSource(jdbcUrl, driverClassName, dataSourceProperties, username, password);
        }
        if (dataSource != null) {
            this.setLoginTimeout(dataSource, this.connectionTimeout);
            this.createNetworkTimeoutExecutor(dataSource, dataSourceClassName, jdbcUrl);
        }
        this.dataSource = dataSource;
    }
    
    private Connection newConnection() {
        Connection connection = null;
        try {
            final String username = this.config.getUsername();
            final String password = this.config.getPassword();
            connection = ((username == null) ? this.dataSource.getConnection() : this.dataSource.getConnection(username, password));
            this.setupConnection(connection);
            this.lastConnectionFailure.set(null);
            return connection;
        }
        catch (Exception newValue) {
            this.lastConnectionFailure.set(newValue);
            this.quietlyCloseConnection(connection, "(exception during connection creation)");
            throw newValue;
        }
    }
    
    private void setupConnection(final Connection connection) {
        this.networkTimeout = this.getAndSetNetworkTimeout(connection, this.connectionTimeout);
        this.checkValidationMode(connection);
        connection.setAutoCommit(this.isAutoCommit);
        connection.setReadOnly(this.isReadOnly);
        final int transactionIsolation = connection.getTransactionIsolation();
        this.transactionIsolation = ((this.transactionIsolation < 0 || transactionIsolation == 0) ? transactionIsolation : this.transactionIsolation);
        if (this.transactionIsolation != transactionIsolation) {
            connection.setTransactionIsolation(this.transactionIsolation);
        }
        if (this.catalog != null) {
            connection.setCatalog(this.catalog);
        }
        this.executeSql(connection, this.config.getConnectionInitSql(), this.isIsolateInternalQueries && !this.isAutoCommit, false);
        this.setNetworkTimeout(connection, this.networkTimeout);
    }
    
    private void checkValidationMode(final Connection connection) {
        if (!this.isValidChecked) {
            Label_0106: {
                if (this.isUseJdbc4Validation) {
                    try {
                        connection.isValid(1);
                        break Label_0106;
                    }
                    catch (Throwable t) {
                        this.LOGGER.debug("{} - Connection.isValid() is not supported, configure connection test query. ({})", this.poolName, t.getMessage());
                        throw t;
                    }
                }
                try {
                    this.executeSql(connection, this.config.getConnectionTestQuery(), false, this.isIsolateInternalQueries && !this.isAutoCommit);
                }
                catch (Throwable t2) {
                    this.LOGGER.debug("{} - Exception during executing connection test query. ({})", this.poolName, t2.getMessage());
                    throw t2;
                }
            }
            this.isValidChecked = true;
        }
    }
    
    private void setQueryTimeout(final Statement statement, final int queryTimeout) {
        if (this.isQueryTimeoutSupported != 0) {
            try {
                statement.setQueryTimeout(queryTimeout);
                this.isQueryTimeoutSupported = 1;
            }
            catch (Throwable t) {
                if (this.isQueryTimeoutSupported == -1) {
                    this.isQueryTimeoutSupported = 0;
                    this.LOGGER.debug("{} - Statement.setQueryTimeout() is not supported ({})", this.poolName, t.getMessage());
                }
            }
        }
    }
    
    private int getAndSetNetworkTimeout(final Connection connection, final long n) {
        if (this.isNetworkTimeoutSupported != 0) {
            try {
                final int networkTimeout = connection.getNetworkTimeout();
                connection.setNetworkTimeout(this.netTimeoutExecutor, (int)n);
                this.isNetworkTimeoutSupported = 1;
                return networkTimeout;
            }
            catch (Throwable t) {
                if (this.isNetworkTimeoutSupported == -1) {
                    this.isNetworkTimeoutSupported = 0;
                    this.LOGGER.debug("{} - Connection.setNetworkTimeout() is not supported ({})", this.poolName, t.getMessage());
                }
            }
        }
        return 0;
    }
    
    private void setNetworkTimeout(final Connection connection, final long n) {
        if (this.isNetworkTimeoutSupported == 1) {
            connection.setNetworkTimeout(this.netTimeoutExecutor, (int)n);
        }
    }
    
    private void executeSql(final Connection connection, final String s, final boolean b, final boolean b2) {
        if (s != null) {
            try (final Statement statement = connection.createStatement()) {
                statement.execute(s);
                if (!this.isReadOnly) {
                    if (b) {
                        connection.commit();
                    }
                    else if (b2) {
                        connection.rollback();
                    }
                }
            }
        }
    }
    
    private void createNetworkTimeoutExecutor(final DataSource dataSource, final String s, final String s2) {
        if ((s != null && s.contains("Mysql")) || (s2 != null && s2.contains("mysql")) || (dataSource != null && dataSource.getClass().getName().contains("Mysql"))) {
            this.netTimeoutExecutor = new SynchronousExecutor();
        }
        else {
            final ThreadPoolExecutor netTimeoutExecutor = (ThreadPoolExecutor)Executors.newCachedThreadPool((this.config.getThreadFactory() != null) ? this.config.getThreadFactory() : new DefaultThreadFactory("Hikari JDBC-timeout executor", true));
            netTimeoutExecutor.allowCoreThreadTimeOut(true);
            netTimeoutExecutor.setKeepAliveTime(15L, TimeUnit.SECONDS);
            this.netTimeoutExecutor = netTimeoutExecutor;
        }
    }
    
    private void setLoginTimeout(final DataSource dataSource, final long b) {
        if (b != 2147483647L) {
            try {
                dataSource.setLoginTimeout((int)TimeUnit.MILLISECONDS.toSeconds(Math.max(1000L, b)));
            }
            catch (Throwable t) {
                this.LOGGER.warn("{} - Unable to set DataSource login timeout", this.poolName, t);
            }
        }
    }
    
    private String stringFromResetBits(final int n) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < PoolBase.RESET_STATES.length; ++i) {
            if ((n & 1 << i) != 0x0) {
                sb.append(PoolBase.RESET_STATES[i]).append(", ");
            }
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
    
    static {
        RESET_STATES = new String[] { "readOnly", "autoCommit", "isolation", "catalog", "netTimeout" };
    }
    
    private static class SynchronousExecutor implements Executor
    {
        @Override
        public void execute(final Runnable runnable) {
            try {
                runnable.run();
            }
            catch (Throwable t) {
                LoggerFactory.getLogger(PoolBase.class).debug("Exception executing {}", runnable, t);
            }
        }
    }
    
    static class MetricsTrackerDelegate implements AutoCloseable
    {
        final MetricsTracker tracker;
        
        protected MetricsTrackerDelegate() {
            this.tracker = null;
        }
        
        MetricsTrackerDelegate(final MetricsTracker tracker) {
            this.tracker = tracker;
        }
        
        @Override
        public void close() {
            this.tracker.close();
        }
        
        void recordConnectionUsage(final PoolEntry poolEntry) {
            this.tracker.recordConnectionUsageMillis(poolEntry.getMillisSinceBorrowed());
        }
        
        void recordBorrowStats(final PoolEntry poolEntry, final long n) {
            final long currentTime = ClockSource.INSTANCE.currentTime();
            poolEntry.lastBorrowed = currentTime;
            this.tracker.recordConnectionAcquiredNanos(ClockSource.INSTANCE.elapsedNanos(n, currentTime));
        }
    }
    
    static final class NopMetricsTrackerDelegate extends MetricsTrackerDelegate
    {
        @Override
        void recordConnectionUsage(final PoolEntry poolEntry) {
        }
        
        @Override
        public void close() {
        }
        
        @Override
        void recordBorrowStats(final PoolEntry poolEntry, final long n) {
        }
    }
}
