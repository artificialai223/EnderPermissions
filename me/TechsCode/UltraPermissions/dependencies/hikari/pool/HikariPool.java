

package me.TechsCode.EnderPermissions.dependencies.hikari.pool;

import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import me.TechsCode.EnderPermissions.dependencies.slf4j.LoggerFactory;
import javax.sql.DataSource;
import me.TechsCode.EnderPermissions.dependencies.hikari.metrics.PoolStats;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import me.TechsCode.EnderPermissions.dependencies.hikari.metrics.dropwizard.CodahaleHealthChecker;
import com.codahale.metrics.health.HealthCheckRegistry;
import me.TechsCode.EnderPermissions.dependencies.hikari.metrics.MetricsTrackerFactory;
import me.TechsCode.EnderPermissions.dependencies.hikari.metrics.dropwizard.CodahaleMetricsTrackerFactory;
import com.codahale.metrics.MetricRegistry;
import java.util.concurrent.ExecutorService;
import java.sql.SQLTransientConnectionException;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.concurrent.ScheduledExecutorService;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.DefaultThreadFactory;
import java.util.concurrent.RejectedExecutionHandler;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.UtilityElf;
import java.util.concurrent.TimeUnit;
import me.TechsCode.EnderPermissions.dependencies.hikari.HikariConfig;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.SuspendResumeLock;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.ClockSource;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.ConcurrentBag;
import me.TechsCode.EnderPermissions.dependencies.hikari.HikariPoolMXBean;

public class HikariPool extends PoolBase implements HikariPoolMXBean, ConcurrentBag.IBagStateListener
{
    private static final Logger LOGGER;
    private static final ClockSource clockSource;
    private final long ALIVE_BYPASS_WINDOW_MS;
    private final long HOUSEKEEPING_PERIOD_MS;
    private final PoolEntryCreator POOL_ENTRY_CREATOR;
    private static final int POOL_NORMAL = 0;
    private static final int POOL_SUSPENDED = 1;
    private static final int POOL_SHUTDOWN = 2;
    private volatile int poolState;
    private final AtomicInteger totalConnections;
    private final ThreadPoolExecutor addConnectionExecutor;
    private final ThreadPoolExecutor closeConnectionExecutor;
    private final ScheduledThreadPoolExecutor houseKeepingExecutorService;
    private final ConcurrentBag<PoolEntry> connectionBag;
    private final ProxyLeakTask leakTask;
    private final SuspendResumeLock suspendResumeLock;
    private MetricsTrackerDelegate metricsTracker;
    private boolean isRecordMetrics;
    
    public HikariPool(final HikariConfig hikariConfig) {
        super(hikariConfig);
        this.ALIVE_BYPASS_WINDOW_MS = Long.getLong("me.TechsCode.EnderPermissions.dependencies.hikari.aliveBypassWindowMs", TimeUnit.MILLISECONDS.toMillis(500L));
        this.HOUSEKEEPING_PERIOD_MS = Long.getLong("me.TechsCode.EnderPermissions.dependencies.hikari.housekeeping.periodMs", TimeUnit.SECONDS.toMillis(30L));
        this.POOL_ENTRY_CREATOR = new PoolEntryCreator();
        this.connectionBag = new ConcurrentBag<PoolEntry>(this);
        this.totalConnections = new AtomicInteger();
        this.suspendResumeLock = (hikariConfig.isAllowPoolSuspension() ? new SuspendResumeLock() : SuspendResumeLock.FAUX_LOCK);
        this.addConnectionExecutor = UtilityElf.createThreadPoolExecutor(hikariConfig.getMaximumPoolSize(), "Hikari connection adder (pool " + this.poolName + ")", hikariConfig.getThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
        this.closeConnectionExecutor = UtilityElf.createThreadPoolExecutor(4, "Hikari connection closer (pool " + this.poolName + ")", hikariConfig.getThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        if (hikariConfig.getScheduledExecutorService() == null) {
            (this.houseKeepingExecutorService = new ScheduledThreadPoolExecutor(1, (hikariConfig.getThreadFactory() != null) ? hikariConfig.getThreadFactory() : new DefaultThreadFactory("Hikari housekeeper (pool " + this.poolName + ")", true), new ThreadPoolExecutor.DiscardPolicy())).setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
            this.houseKeepingExecutorService.setRemoveOnCancelPolicy(true);
        }
        else {
            this.houseKeepingExecutorService = hikariConfig.getScheduledExecutorService();
        }
        this.houseKeepingExecutorService.scheduleAtFixedRate(new HouseKeeper(), this.HOUSEKEEPING_PERIOD_MS, this.HOUSEKEEPING_PERIOD_MS, TimeUnit.MILLISECONDS);
        this.leakTask = new ProxyLeakTask(hikariConfig.getLeakDetectionThreshold(), this.houseKeepingExecutorService);
        if (hikariConfig.getMetricsTrackerFactory() != null) {
            this.setMetricsTrackerFactory(hikariConfig.getMetricsTrackerFactory());
        }
        else {
            this.setMetricRegistry(hikariConfig.getMetricRegistry());
        }
        this.setHealthCheckRegistry(hikariConfig.getHealthCheckRegistry());
        this.registerMBeans(this);
        this.initializeConnections();
    }
    
    public final Connection getConnection() {
        return this.getConnection(this.connectionTimeout);
    }
    
    public final Connection getConnection(final long n) {
        this.suspendResumeLock.acquire();
        final long currentTime = HikariPool.clockSource.currentTime();
        try {
            long n2 = n;
            do {
                final PoolEntry poolEntry = this.connectionBag.borrow(n2, TimeUnit.MILLISECONDS);
                if (poolEntry == null) {
                    break;
                }
                final long currentTime2 = HikariPool.clockSource.currentTime();
                if (!poolEntry.isMarkedEvicted() && (HikariPool.clockSource.elapsedMillis(poolEntry.lastAccessed, currentTime2) <= this.ALIVE_BYPASS_WINDOW_MS || this.isConnectionAlive(poolEntry.connection))) {
                    this.metricsTracker.recordBorrowStats(poolEntry, currentTime);
                    return poolEntry.createProxyConnection(this.leakTask.start(poolEntry), currentTime2);
                }
                this.closeConnection(poolEntry, "(connection evicted or dead)");
                n2 = n - HikariPool.clockSource.elapsedMillis(currentTime);
            } while (n2 > 0L);
        }
        catch (InterruptedException cause) {
            throw new SQLException(this.poolName + " - Interrupted during connection acquisition", cause);
        }
        finally {
            this.suspendResumeLock.release();
        }
        this.logPoolState("Timeout failure\t");
        String sqlState = null;
        final Throwable lastConnectionFailure = this.getLastConnectionFailure();
        if (lastConnectionFailure instanceof SQLException) {
            sqlState = ((SQLException)lastConnectionFailure).getSQLState();
        }
        final SQLTransientConnectionException ex = new SQLTransientConnectionException(this.poolName + " - Connection is not available, request timed out after " + HikariPool.clockSource.elapsedMillis(currentTime) + "ms.", sqlState, lastConnectionFailure);
        if (lastConnectionFailure instanceof SQLException) {
            ex.setNextException((SQLException)lastConnectionFailure);
        }
        throw ex;
    }
    
    public final synchronized void shutdown() {
        try {
            this.poolState = 2;
            HikariPool.LOGGER.info("{} - is closing down.", this.poolName);
            this.logPoolState("Before closing\t");
            this.softEvictConnections();
            this.addConnectionExecutor.shutdown();
            this.addConnectionExecutor.awaitTermination(5L, TimeUnit.SECONDS);
            if (this.config.getScheduledExecutorService() == null) {
                this.houseKeepingExecutorService.shutdown();
                this.houseKeepingExecutorService.awaitTermination(5L, TimeUnit.SECONDS);
            }
            this.connectionBag.close();
            final ThreadPoolExecutor threadPoolExecutor = UtilityElf.createThreadPoolExecutor(this.config.getMaximumPoolSize(), "Hikari connection assassin (pool " + this.poolName + ")", this.config.getThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
            try {
                final long currentTime = HikariPool.clockSource.currentTime();
                do {
                    this.abortActiveConnections(threadPoolExecutor);
                    this.softEvictConnections();
                } while (this.getTotalConnections() > 0 && HikariPool.clockSource.elapsedMillis(currentTime) < TimeUnit.SECONDS.toMillis(5L));
            }
            finally {
                threadPoolExecutor.shutdown();
                threadPoolExecutor.awaitTermination(5L, TimeUnit.SECONDS);
            }
            this.shutdownNetworkTimeoutExecutor();
            this.closeConnectionExecutor.shutdown();
            this.closeConnectionExecutor.awaitTermination(5L, TimeUnit.SECONDS);
        }
        finally {
            this.logPoolState("After closing\t");
            this.unregisterMBeans();
            this.metricsTracker.close();
        }
    }
    
    public final void evictConnection(final Connection connection) {
        final ProxyConnection proxyConnection = (ProxyConnection)connection;
        proxyConnection.cancelLeakTask();
        this.softEvictConnection(proxyConnection.getPoolEntry(), "(connection evicted by user)", true);
    }
    
    public void setMetricRegistry(final Object o) {
        this.isRecordMetrics = (o != null);
        if (this.isRecordMetrics) {
            this.setMetricsTrackerFactory(new CodahaleMetricsTrackerFactory((MetricRegistry)o));
        }
        else {
            this.setMetricsTrackerFactory(null);
        }
    }
    
    public void setMetricsTrackerFactory(final MetricsTrackerFactory metricsTrackerFactory) {
        this.isRecordMetrics = (metricsTrackerFactory != null);
        if (this.isRecordMetrics) {
            this.metricsTracker = new MetricsTrackerDelegate(metricsTrackerFactory.create(this.config.getPoolName(), this.getPoolStats()));
        }
        else {
            this.metricsTracker = new NopMetricsTrackerDelegate();
        }
    }
    
    public void setHealthCheckRegistry(final Object o) {
        if (o != null) {
            CodahaleHealthChecker.registerHealthChecks(this, this.config, (HealthCheckRegistry)o);
        }
    }
    
    @Override
    public Future<Boolean> addBagItem() {
        return this.addConnectionExecutor.submit((Callable<Boolean>)this.POOL_ENTRY_CREATOR);
    }
    
    @Override
    public final int getActiveConnections() {
        return this.connectionBag.getCount(1);
    }
    
    @Override
    public final int getIdleConnections() {
        return this.connectionBag.getCount(0);
    }
    
    @Override
    public final int getTotalConnections() {
        return this.connectionBag.size() - this.connectionBag.getCount(-1);
    }
    
    @Override
    public final int getThreadsAwaitingConnection() {
        return this.connectionBag.getPendingQueue();
    }
    
    @Override
    public void softEvictConnections() {
        final Iterator<PoolEntry> iterator = this.connectionBag.values().iterator();
        while (iterator.hasNext()) {
            this.softEvictConnection(iterator.next(), "(connection evicted by user)", false);
        }
    }
    
    @Override
    public final synchronized void suspendPool() {
        if (this.suspendResumeLock == SuspendResumeLock.FAUX_LOCK) {
            throw new IllegalStateException(this.poolName + " - is not suspendable");
        }
        if (this.poolState != 1) {
            this.suspendResumeLock.suspend();
            this.poolState = 1;
        }
    }
    
    @Override
    public final synchronized void resumePool() {
        if (this.poolState == 1) {
            this.poolState = 0;
            this.fillPool();
            this.suspendResumeLock.resume();
        }
    }
    
    final void logPoolState(final String... array) {
        if (HikariPool.LOGGER.isDebugEnabled()) {
            HikariPool.LOGGER.debug("{}pool {} stats (total={}, active={}, idle={}, waiting={})", (array.length > 0) ? array[0] : "", this.poolName, this.getTotalConnections(), this.getActiveConnections(), this.getIdleConnections(), this.getThreadsAwaitingConnection());
        }
    }
    
    @Override
    final void releaseConnection(final PoolEntry poolEntry) {
        this.metricsTracker.recordConnectionUsage(poolEntry);
        this.connectionBag.requite(poolEntry);
    }
    
    final void closeConnection(final PoolEntry poolEntry, final String s) {
        if (this.connectionBag.remove(poolEntry)) {
            final Connection connection = poolEntry.connection;
            poolEntry.close();
            final int decrementAndGet = this.totalConnections.decrementAndGet();
            if (decrementAndGet < 0) {
                HikariPool.LOGGER.warn("{} - Internal accounting inconsistency, totalConnections={}", this.poolName, decrementAndGet, new Exception());
            }
            this.closeConnectionExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    HikariPool.this.quietlyCloseConnection(connection, s);
                }
            });
        }
    }
    
    private PoolEntry createPoolEntry() {
        try {
            final PoolEntry poolEntry = this.newPoolEntry();
            final long maxLifetime = this.config.getMaxLifetime();
            if (maxLifetime > 0L) {
                poolEntry.setFutureEol(this.houseKeepingExecutorService.schedule(new Runnable() {
                    @Override
                    public void run() {
                        HikariPool.this.softEvictConnection(poolEntry, "(connection reached maxLifetime)", false);
                    }
                }, maxLifetime - ((maxLifetime > 10000L) ? ThreadLocalRandom.current().nextLong(Math.max(10000L, maxLifetime / 40L)) : 0L), TimeUnit.MILLISECONDS));
            }
            HikariPool.LOGGER.debug("{} - Added connection {}", this.poolName, poolEntry.connection);
            return poolEntry;
        }
        catch (Exception ex) {
            if (this.poolState == 0) {
                HikariPool.LOGGER.debug("{} - Cannot acquire connection from data source", this.poolName, ex);
            }
            return null;
        }
    }
    
    private void fillPool() {
        final int min = Math.min(this.config.getMaximumPoolSize() - this.totalConnections.get(), this.config.getMinimumIdle() - this.getIdleConnections());
        for (int i = 0; i < min; ++i) {
            this.addBagItem();
        }
        if (min > 0 && HikariPool.LOGGER.isDebugEnabled()) {
            this.addConnectionExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    HikariPool.this.logPoolState("After adding\t");
                }
            });
        }
    }
    
    private void abortActiveConnections(final ExecutorService executorService) {
        for (final PoolEntry poolEntry : this.connectionBag.values(1)) {
            try {
                poolEntry.connection.abort(executorService);
            }
            catch (Throwable t) {
                this.quietlyCloseConnection(poolEntry.connection, "(connection aborted during shutdown)");
            }
            finally {
                poolEntry.close();
                if (this.connectionBag.remove(poolEntry)) {
                    this.totalConnections.decrementAndGet();
                }
            }
        }
    }
    
    private void initializeConnections() {
        if (this.config.isInitializationFailFast()) {
            try {
                final Connection connection = this.getConnection();
                if (this.config.getMinimumIdle() == 0) {
                    this.evictConnection(connection);
                }
                else {
                    connection.close();
                }
            }
            catch (Throwable t) {
                try {
                    this.shutdown();
                }
                catch (Throwable exception) {
                    t.addSuppressed(exception);
                }
                throw new PoolInitializationException(t);
            }
        }
        this.fillPool();
    }
    
    private void softEvictConnection(final PoolEntry poolEntry, final String s, final boolean b) {
        if (b || this.connectionBag.reserve(poolEntry)) {
            this.closeConnection(poolEntry, s);
        }
        else {
            poolEntry.markEvicted();
        }
    }
    
    private PoolStats getPoolStats() {
        return new PoolStats(TimeUnit.SECONDS.toMillis(1L)) {
            @Override
            protected void update() {
                this.pendingThreads = HikariPool.this.getThreadsAwaitingConnection();
                this.idleConnections = HikariPool.this.getIdleConnections();
                this.totalConnections = HikariPool.this.getTotalConnections();
                this.activeConnections = HikariPool.this.getActiveConnections();
            }
        };
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(HikariPool.class);
        clockSource = ClockSource.INSTANCE;
    }
    
    private class PoolEntryCreator implements Callable<Boolean>
    {
        @Override
        public Boolean call() {
            long min = 200L;
            while (HikariPool.this.poolState == 0 && HikariPool.this.totalConnections.get() < HikariPool.this.config.getMaximumPoolSize()) {
                final PoolEntry access$500 = HikariPool.this.createPoolEntry();
                if (access$500 != null) {
                    HikariPool.this.totalConnections.incrementAndGet();
                    HikariPool.this.connectionBag.add(access$500);
                    return Boolean.TRUE;
                }
                UtilityElf.quietlySleep(min);
                min = Math.min(HikariPool.this.connectionTimeout / 2L, (long)(min * 1.3));
            }
            return Boolean.FALSE;
        }
    }
    
    private class HouseKeeper implements Runnable
    {
        private volatile long previous;
        
        private HouseKeeper() {
            this.previous = HikariPool.clockSource.currentTime();
        }
        
        @Override
        public void run() {
            HikariPool.this.connectionTimeout = HikariPool.this.config.getConnectionTimeout();
            HikariPool.this.leakTask.updateLeakDetectionThreshold(HikariPool.this.config.getLeakDetectionThreshold());
            final long currentTime = HikariPool.clockSource.currentTime();
            final long idleTimeout = HikariPool.this.config.getIdleTimeout();
            if (currentTime < this.previous || currentTime > HikariPool.clockSource.plusMillis(this.previous, 2L * HikariPool.this.HOUSEKEEPING_PERIOD_MS)) {
                HikariPool.LOGGER.warn("{} - Unusual system clock change detected, soft-evicting connections from pool.", HikariPool.this.poolName);
                this.previous = currentTime;
                HikariPool.this.softEvictConnections();
                HikariPool.this.fillPool();
                return;
            }
            this.previous = currentTime;
            HikariPool.this.logPoolState("Before cleanup\t");
            if (idleTimeout > 0L) {
                final List<PoolEntry> values = (List<PoolEntry>)HikariPool.this.connectionBag.values(0);
                int n = values.size() - HikariPool.this.config.getMinimumIdle();
                if (n > 0) {
                    Collections.sort((List<Object>)values, (Comparator<? super Object>)PoolEntry.LASTACCESS_COMPARABLE);
                    final Iterator<PoolEntry> iterator = values.iterator();
                    do {
                        final PoolEntry poolEntry = iterator.next();
                        if (HikariPool.clockSource.elapsedMillis(poolEntry.lastAccessed, currentTime) > idleTimeout && HikariPool.this.connectionBag.reserve(poolEntry)) {
                            HikariPool.this.closeConnection(poolEntry, "(connection passed idleTimeout)");
                            --n;
                        }
                    } while (n > 0 && iterator.hasNext());
                }
            }
            HikariPool.this.logPoolState("After cleanup\t");
            HikariPool.this.fillPool();
        }
    }
    
    public static class PoolInitializationException extends RuntimeException
    {
        private static final long serialVersionUID = 929872118275916520L;
        
        public PoolInitializationException(final Throwable cause) {
            super("Exception during pool initialization: " + cause.getMessage(), cause);
        }
    }
}
