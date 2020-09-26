

package me.TechsCode.EnderPermissions.dependencies.hikari.pool;

import me.TechsCode.EnderPermissions.dependencies.slf4j.LoggerFactory;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.ClockSource;
import java.util.concurrent.atomic.AtomicInteger;
import java.sql.Statement;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.FastList;
import java.util.concurrent.ScheduledFuture;
import java.sql.Connection;
import java.util.Comparator;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.ConcurrentBag;

final class PoolEntry implements ConcurrentBag.IConcurrentBagEntry
{
    private static final Logger LOGGER;
    static final Comparator<PoolEntry> LASTACCESS_COMPARABLE;
    Connection connection;
    long lastAccessed;
    long lastBorrowed;
    private volatile boolean evict;
    private volatile ScheduledFuture<?> endOfLife;
    private final FastList<Statement> openStatements;
    private final HikariPool hikariPool;
    private final AtomicInteger state;
    private final boolean isReadOnly;
    private final boolean isAutoCommit;
    
    PoolEntry(final Connection connection, final PoolBase poolBase, final boolean isReadOnly, final boolean isAutoCommit) {
        this.connection = connection;
        this.hikariPool = (HikariPool)poolBase;
        this.isReadOnly = isReadOnly;
        this.isAutoCommit = isAutoCommit;
        this.state = new AtomicInteger();
        this.lastAccessed = ClockSource.INSTANCE.currentTime();
        this.openStatements = new FastList<Statement>(Statement.class, 16);
    }
    
    void recycle(final long lastAccessed) {
        this.lastAccessed = lastAccessed;
        this.hikariPool.releaseConnection(this);
    }
    
    void setFutureEol(final ScheduledFuture<?> endOfLife) {
        this.endOfLife = endOfLife;
    }
    
    Connection createProxyConnection(final ProxyLeakTask proxyLeakTask, final long n) {
        return ProxyFactory.getProxyConnection(this, this.connection, this.openStatements, proxyLeakTask, n, this.isReadOnly, this.isAutoCommit);
    }
    
    void resetConnectionState(final ProxyConnection proxyConnection, final int n) {
        this.hikariPool.resetConnectionState(this.connection, proxyConnection, n);
    }
    
    String getPoolName() {
        return this.hikariPool.toString();
    }
    
    boolean isMarkedEvicted() {
        return this.evict;
    }
    
    void markEvicted() {
        this.evict = true;
    }
    
    void evict(final String s) {
        this.hikariPool.closeConnection(this, s);
    }
    
    long getMillisSinceBorrowed() {
        return ClockSource.INSTANCE.elapsedMillis(this.lastBorrowed);
    }
    
    @Override
    public String toString() {
        final long currentTime = ClockSource.INSTANCE.currentTime();
        return this.connection + ", borrowed " + ClockSource.INSTANCE.elapsedMillis(this.lastBorrowed, currentTime) + "ms ago, " + ", accessed " + ClockSource.INSTANCE.elapsedMillis(this.lastAccessed, currentTime) + "ms ago, " + this.stateToString();
    }
    
    @Override
    public int getState() {
        return this.state.get();
    }
    
    @Override
    public boolean compareAndSet(final int expectedValue, final int newValue) {
        return this.state.compareAndSet(expectedValue, newValue);
    }
    
    @Override
    public void lazySet(final int newValue) {
        this.state.lazySet(newValue);
    }
    
    void close() {
        if (this.endOfLife != null && !this.endOfLife.isDone() && !this.endOfLife.cancel(false)) {
            PoolEntry.LOGGER.warn("{} - maxLifeTime expiration task cancellation unexpectedly returned false for connection {}", this.getPoolName(), this.connection);
        }
        this.endOfLife = null;
        this.connection = null;
    }
    
    private String stateToString() {
        switch (this.state.get()) {
            case 1: {
                return "IN_USE";
            }
            case 0: {
                return "NOT_IN_USE";
            }
            case -1: {
                return "REMOVED";
            }
            case -2: {
                return "RESERVED";
            }
            default: {
                return "Invalid";
            }
        }
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(PoolEntry.class);
        LASTACCESS_COMPARABLE = new Comparator<PoolEntry>() {
            @Override
            public int compare(final PoolEntry poolEntry, final PoolEntry poolEntry2) {
                return Long.compare(poolEntry.lastAccessed, poolEntry2.lastAccessed);
            }
        };
    }
}
