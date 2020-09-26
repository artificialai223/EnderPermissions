

package me.TechsCode.EnderPermissions.dependencies.hikari;

import me.TechsCode.EnderPermissions.dependencies.slf4j.LoggerFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.util.Iterator;
import java.util.Collection;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.UtilityElf;
import com.codahale.metrics.health.HealthCheckRegistry;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import com.codahale.metrics.MetricRegistry;
import java.util.Map;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.PropertyElf;
import me.TechsCode.EnderPermissions.dependencies.hikari.metrics.MetricsTrackerFactory;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.Properties;
import javax.sql.DataSource;
import java.util.concurrent.atomic.AtomicInteger;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;

public class HikariConfig implements HikariConfigMXBean
{
    private static final Logger LOGGER;
    private static final long CONNECTION_TIMEOUT;
    private static final long VALIDATION_TIMEOUT;
    private static final long IDLE_TIMEOUT;
    private static final long MAX_LIFETIME;
    private static final AtomicInteger POOL_NUMBER;
    private static boolean unitTest;
    private volatile long connectionTimeout;
    private volatile long validationTimeout;
    private volatile long idleTimeout;
    private volatile long leakDetectionThreshold;
    private volatile long maxLifetime;
    private volatile int maxPoolSize;
    private volatile int minIdle;
    private String catalog;
    private String connectionInitSql;
    private String connectionTestQuery;
    private String dataSourceClassName;
    private String dataSourceJndiName;
    private String driverClassName;
    private String jdbcUrl;
    private String password;
    private String poolName;
    private String transactionIsolationName;
    private String username;
    private boolean isAutoCommit;
    private boolean isReadOnly;
    private boolean isInitializationFailFast;
    private boolean isIsolateInternalQueries;
    private boolean isRegisterMbeans;
    private boolean isAllowPoolSuspension;
    private DataSource dataSource;
    private Properties dataSourceProperties;
    private ThreadFactory threadFactory;
    private ScheduledThreadPoolExecutor scheduledExecutor;
    private MetricsTrackerFactory metricsTrackerFactory;
    private Object metricRegistry;
    private Object healthCheckRegistry;
    private Properties healthCheckProperties;
    
    public HikariConfig() {
        this.dataSourceProperties = new Properties();
        this.healthCheckProperties = new Properties();
        this.connectionTimeout = HikariConfig.CONNECTION_TIMEOUT;
        this.validationTimeout = HikariConfig.VALIDATION_TIMEOUT;
        this.idleTimeout = HikariConfig.IDLE_TIMEOUT;
        this.isAutoCommit = true;
        this.isInitializationFailFast = true;
        this.minIdle = -1;
        this.maxPoolSize = 10;
        this.maxLifetime = HikariConfig.MAX_LIFETIME;
        final String property = System.getProperty("hikaricp.configurationFile");
        if (property != null) {
            this.loadProperties(property);
        }
    }
    
    public HikariConfig(final Properties properties) {
        this();
        PropertyElf.setTargetFromProperties(this, properties);
    }
    
    public HikariConfig(final String s) {
        this();
        this.loadProperties(s);
    }
    
    public String getCatalog() {
        return this.catalog;
    }
    
    public void setCatalog(final String catalog) {
        this.catalog = catalog;
    }
    
    public String getConnectionTestQuery() {
        return this.connectionTestQuery;
    }
    
    public void setConnectionTestQuery(final String connectionTestQuery) {
        this.connectionTestQuery = connectionTestQuery;
    }
    
    public String getConnectionInitSql() {
        return this.connectionInitSql;
    }
    
    public void setConnectionInitSql(final String connectionInitSql) {
        this.connectionInitSql = connectionInitSql;
    }
    
    @Override
    public long getConnectionTimeout() {
        return this.connectionTimeout;
    }
    
    @Override
    public void setConnectionTimeout(final long n) {
        if (n == 0L) {
            this.connectionTimeout = 2147483647L;
        }
        else {
            if (n < 1000L) {
                throw new IllegalArgumentException("connectionTimeout cannot be less than 1000ms");
            }
            this.connectionTimeout = n;
        }
        if (this.validationTimeout > n && n > 0L) {
            this.validationTimeout = n;
        }
    }
    
    @Override
    public long getValidationTimeout() {
        return this.validationTimeout;
    }
    
    @Override
    public void setValidationTimeout(final long validationTimeout) {
        if (validationTimeout < 1000L) {
            throw new IllegalArgumentException("validationTimeout cannot be less than 1000ms");
        }
        this.validationTimeout = validationTimeout;
        if (this.validationTimeout > this.connectionTimeout) {
            this.validationTimeout = this.connectionTimeout;
        }
    }
    
    public DataSource getDataSource() {
        return this.dataSource;
    }
    
    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public String getDataSourceClassName() {
        return this.dataSourceClassName;
    }
    
    public void setDataSourceClassName(final String dataSourceClassName) {
        this.dataSourceClassName = dataSourceClassName;
    }
    
    public void addDataSourceProperty(final String key, final Object value) {
        this.dataSourceProperties.put(key, value);
    }
    
    public String getDataSourceJNDI() {
        return this.dataSourceJndiName;
    }
    
    public void setDataSourceJNDI(final String dataSourceJndiName) {
        this.dataSourceJndiName = dataSourceJndiName;
    }
    
    public Properties getDataSourceProperties() {
        return this.dataSourceProperties;
    }
    
    public void setDataSourceProperties(final Properties t) {
        this.dataSourceProperties.putAll(t);
    }
    
    public String getDriverClassName() {
        return this.driverClassName;
    }
    
    public void setDriverClassName(final String str) {
        try {
            this.getClass().getClassLoader().loadClass(str).newInstance();
            this.driverClassName = str;
        }
        catch (Exception cause) {
            throw new RuntimeException("Could not load class of driverClassName " + str, cause);
        }
    }
    
    @Override
    public long getIdleTimeout() {
        return this.idleTimeout;
    }
    
    @Override
    public void setIdleTimeout(final long idleTimeout) {
        if (idleTimeout < 0L) {
            throw new IllegalArgumentException("idleTimeout cannot be negative");
        }
        this.idleTimeout = idleTimeout;
    }
    
    public String getJdbcUrl() {
        return this.jdbcUrl;
    }
    
    public void setJdbcUrl(final String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }
    
    public boolean isAutoCommit() {
        return this.isAutoCommit;
    }
    
    public void setAutoCommit(final boolean isAutoCommit) {
        this.isAutoCommit = isAutoCommit;
    }
    
    public boolean isAllowPoolSuspension() {
        return this.isAllowPoolSuspension;
    }
    
    public void setAllowPoolSuspension(final boolean isAllowPoolSuspension) {
        this.isAllowPoolSuspension = isAllowPoolSuspension;
    }
    
    public boolean isInitializationFailFast() {
        return this.isInitializationFailFast;
    }
    
    public void setInitializationFailFast(final boolean isInitializationFailFast) {
        this.isInitializationFailFast = isInitializationFailFast;
    }
    
    public boolean isIsolateInternalQueries() {
        return this.isIsolateInternalQueries;
    }
    
    public void setIsolateInternalQueries(final boolean isIsolateInternalQueries) {
        this.isIsolateInternalQueries = isIsolateInternalQueries;
    }
    
    @Deprecated
    public boolean isJdbc4ConnectionTest() {
        return false;
    }
    
    @Deprecated
    public void setJdbc4ConnectionTest(final boolean b) {
        HikariConfig.LOGGER.warn("The jdbcConnectionTest property is now deprecated, see the documentation for connectionTestQuery");
    }
    
    public MetricsTrackerFactory getMetricsTrackerFactory() {
        return this.metricsTrackerFactory;
    }
    
    public void setMetricsTrackerFactory(final MetricsTrackerFactory metricsTrackerFactory) {
        if (this.metricRegistry != null) {
            throw new IllegalStateException("cannot use setMetricsTrackerFactory() and setMetricRegistry() together");
        }
        this.metricsTrackerFactory = metricsTrackerFactory;
    }
    
    public Object getMetricRegistry() {
        return this.metricRegistry;
    }
    
    public void setMetricRegistry(Object metricRegistry) {
        if (this.metricsTrackerFactory != null) {
            throw new IllegalStateException("cannot use setMetricRegistry() and setMetricsTrackerFactory() together");
        }
        if (metricRegistry != null) {
            if (metricRegistry instanceof String) {
                try {
                    metricRegistry = new InitialContext().lookup((String)metricRegistry);
                }
                catch (NamingException cause) {
                    throw new IllegalArgumentException(cause);
                }
            }
            if (!(metricRegistry instanceof MetricRegistry)) {
                throw new IllegalArgumentException("Class must be an instance of com.codahale.metrics.MetricRegistry");
            }
        }
        this.metricRegistry = metricRegistry;
    }
    
    public Object getHealthCheckRegistry() {
        return this.healthCheckRegistry;
    }
    
    public void setHealthCheckRegistry(Object healthCheckRegistry) {
        if (healthCheckRegistry != null) {
            if (healthCheckRegistry instanceof String) {
                try {
                    healthCheckRegistry = new InitialContext().lookup((String)healthCheckRegistry);
                }
                catch (NamingException cause) {
                    throw new IllegalArgumentException(cause);
                }
            }
            if (!(healthCheckRegistry instanceof HealthCheckRegistry)) {
                throw new IllegalArgumentException("Class must be an instance of com.codahale.metrics.health.HealthCheckRegistry");
            }
        }
        this.healthCheckRegistry = healthCheckRegistry;
    }
    
    public Properties getHealthCheckProperties() {
        return this.healthCheckProperties;
    }
    
    public void setHealthCheckProperties(final Properties t) {
        this.healthCheckProperties.putAll(t);
    }
    
    public void addHealthCheckProperty(final String key, final String value) {
        this.healthCheckProperties.setProperty(key, value);
    }
    
    public boolean isReadOnly() {
        return this.isReadOnly;
    }
    
    public void setReadOnly(final boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }
    
    public boolean isRegisterMbeans() {
        return this.isRegisterMbeans;
    }
    
    public void setRegisterMbeans(final boolean isRegisterMbeans) {
        this.isRegisterMbeans = isRegisterMbeans;
    }
    
    @Override
    public long getLeakDetectionThreshold() {
        return this.leakDetectionThreshold;
    }
    
    @Override
    public void setLeakDetectionThreshold(final long leakDetectionThreshold) {
        this.leakDetectionThreshold = leakDetectionThreshold;
    }
    
    @Override
    public long getMaxLifetime() {
        return this.maxLifetime;
    }
    
    @Override
    public void setMaxLifetime(final long maxLifetime) {
        this.maxLifetime = maxLifetime;
    }
    
    @Override
    public int getMaximumPoolSize() {
        return this.maxPoolSize;
    }
    
    @Override
    public void setMaximumPoolSize(final int maxPoolSize) {
        if (maxPoolSize < 1) {
            throw new IllegalArgumentException("maxPoolSize cannot be less than 1");
        }
        this.maxPoolSize = maxPoolSize;
    }
    
    @Override
    public int getMinimumIdle() {
        return this.minIdle;
    }
    
    @Override
    public void setMinimumIdle(final int minIdle) {
        if (minIdle < 0) {
            throw new IllegalArgumentException("minimumIdle cannot be negative");
        }
        this.minIdle = minIdle;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    @Override
    public void setPassword(final String password) {
        this.password = password;
    }
    
    @Override
    public String getPoolName() {
        return this.poolName;
    }
    
    public void setPoolName(final String poolName) {
        this.poolName = poolName;
    }
    
    public ScheduledThreadPoolExecutor getScheduledExecutorService() {
        return this.scheduledExecutor;
    }
    
    public void setScheduledExecutorService(final ScheduledThreadPoolExecutor scheduledExecutor) {
        this.scheduledExecutor = scheduledExecutor;
    }
    
    public String getTransactionIsolation() {
        return this.transactionIsolationName;
    }
    
    public void setTransactionIsolation(final String transactionIsolationName) {
        this.transactionIsolationName = transactionIsolationName;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    @Override
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public ThreadFactory getThreadFactory() {
        return this.threadFactory;
    }
    
    public void setThreadFactory(final ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }
    
    public void validate() {
        this.validateNumerics();
        this.catalog = UtilityElf.getNullIfEmpty(this.catalog);
        this.connectionInitSql = UtilityElf.getNullIfEmpty(this.connectionInitSql);
        this.connectionTestQuery = UtilityElf.getNullIfEmpty(this.connectionTestQuery);
        this.transactionIsolationName = UtilityElf.getNullIfEmpty(this.transactionIsolationName);
        this.dataSourceClassName = UtilityElf.getNullIfEmpty(this.dataSourceClassName);
        this.dataSourceJndiName = UtilityElf.getNullIfEmpty(this.dataSourceJndiName);
        this.driverClassName = UtilityElf.getNullIfEmpty(this.driverClassName);
        this.jdbcUrl = UtilityElf.getNullIfEmpty(this.jdbcUrl);
        if (this.poolName == null) {
            this.poolName = "HikariPool-" + HikariConfig.POOL_NUMBER.getAndIncrement();
        }
        if (this.poolName.contains(":") && this.isRegisterMbeans) {
            throw new IllegalArgumentException("poolName cannot contain ':' when used with JMX");
        }
        if (this.driverClassName != null && this.jdbcUrl == null) {
            HikariConfig.LOGGER.error("jdbcUrl is required with driverClassName");
            throw new IllegalArgumentException("jdbcUrl is required with driverClassName");
        }
        if (this.driverClassName != null && this.dataSourceClassName != null) {
            HikariConfig.LOGGER.error("cannot use driverClassName and dataSourceClassName together");
            throw new IllegalArgumentException("cannot use driverClassName and dataSourceClassName together");
        }
        if (this.jdbcUrl != null && this.dataSourceClassName != null) {
            HikariConfig.LOGGER.warn("using dataSourceClassName and ignoring jdbcUrl");
        }
        else if (this.jdbcUrl == null) {
            if (this.dataSource == null && this.dataSourceClassName == null) {
                HikariConfig.LOGGER.error("either dataSource or dataSourceClassName is required");
                throw new IllegalArgumentException("either dataSource or dataSourceClassName is required");
            }
            if (this.dataSource != null && this.dataSourceClassName != null) {
                HikariConfig.LOGGER.warn("using dataSource and ignoring dataSourceClassName");
            }
        }
        if (HikariConfig.LOGGER.isDebugEnabled() || HikariConfig.unitTest) {
            this.logConfiguration();
        }
    }
    
    private void validateNumerics() {
        if (this.validationTimeout > this.connectionTimeout && this.connectionTimeout != 2147483647L) {
            this.validationTimeout = this.connectionTimeout;
        }
        if (this.minIdle < 0) {
            this.minIdle = this.maxPoolSize;
        }
        else if (this.minIdle > this.maxPoolSize) {
            this.maxPoolSize = this.minIdle;
        }
        if (this.maxLifetime < 0L) {
            HikariConfig.LOGGER.error("maxLifetime cannot be negative.");
            throw new IllegalArgumentException("maxLifetime cannot be negative.");
        }
        if (this.maxLifetime > 0L && this.maxLifetime < TimeUnit.SECONDS.toMillis(30L)) {
            HikariConfig.LOGGER.warn("maxLifetime is less than 30000ms, setting to default {}ms.", (Object)HikariConfig.MAX_LIFETIME);
            this.maxLifetime = HikariConfig.MAX_LIFETIME;
        }
        if (this.idleTimeout != 0L && this.idleTimeout < TimeUnit.SECONDS.toMillis(10L)) {
            HikariConfig.LOGGER.warn("idleTimeout is less than 10000ms, setting to default {}ms.", (Object)HikariConfig.IDLE_TIMEOUT);
            this.idleTimeout = HikariConfig.IDLE_TIMEOUT;
        }
        if (this.idleTimeout + TimeUnit.SECONDS.toMillis(1L) > this.maxLifetime && this.maxLifetime > 0L) {
            HikariConfig.LOGGER.warn("idleTimeout is close to or greater than maxLifetime, disabling it.");
            this.maxLifetime = this.idleTimeout;
            this.idleTimeout = 0L;
        }
        if (this.maxLifetime == 0L && this.idleTimeout == 0L) {
            HikariConfig.LOGGER.warn("setting idleTimeout to {}ms.", (Object)HikariConfig.IDLE_TIMEOUT);
            this.idleTimeout = HikariConfig.IDLE_TIMEOUT;
        }
        if (this.leakDetectionThreshold != 0L && this.leakDetectionThreshold < TimeUnit.SECONDS.toMillis(2L) && !HikariConfig.unitTest) {
            HikariConfig.LOGGER.warn("leakDetectionThreshold is less than 2000ms, setting to minimum 2000ms.");
            this.leakDetectionThreshold = 2000L;
        }
    }
    
    private void logConfiguration() {
        HikariConfig.LOGGER.debug("{} - configuration:", this.poolName);
        for (final String s : new TreeSet<String>(PropertyElf.getPropertyNames(HikariConfig.class))) {
            try {
                Object property = PropertyElf.getProperty(s, this);
                if ("dataSourceProperties".equals(s)) {
                    final Properties copyProperties = PropertyElf.copyProperties(this.dataSourceProperties);
                    copyProperties.setProperty("password", "<masked>");
                    property = copyProperties;
                }
                final String s2 = (String)(s.contains("password") ? "<masked>" : property);
                HikariConfig.LOGGER.debug((s + "................................................").substring(0, 32) + (Object)((s2 != null) ? s2 : ""));
            }
            catch (Exception ex) {}
        }
    }
    
    protected void loadProperties(final String str) {
        final File file = new File(str);
        try (final InputStream inStream = file.isFile() ? new FileInputStream(file) : this.getClass().getResourceAsStream(str)) {
            if (inStream == null) {
                throw new IllegalArgumentException("Property file " + str + " was not found.");
            }
            final Properties properties = new Properties();
            properties.load(inStream);
            PropertyElf.setTargetFromProperties(this, properties);
        }
        catch (IOException cause) {
            throw new RuntimeException("Error loading properties file", cause);
        }
    }
    
    public void copyState(final HikariConfig obj) {
        for (final Field field : HikariConfig.class.getDeclaredFields()) {
            if (!Modifier.isFinal(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    field.set(obj, field.get(this));
                }
                catch (Exception cause) {
                    throw new RuntimeException("Exception copying HikariConfig state: " + cause.getMessage(), cause);
                }
            }
        }
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(HikariConfig.class);
        CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(30L);
        VALIDATION_TIMEOUT = TimeUnit.SECONDS.toMillis(5L);
        IDLE_TIMEOUT = TimeUnit.MINUTES.toMillis(10L);
        MAX_LIFETIME = TimeUnit.MINUTES.toMillis(30L);
        POOL_NUMBER = new AtomicInteger();
    }
}
