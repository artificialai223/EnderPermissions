

package me.TechsCode.EnderPermissions.dependencies.hikari.metrics;

import java.util.concurrent.atomic.AtomicLong;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.ClockSource;

public abstract class PoolStats
{
    private final ClockSource clock;
    private final AtomicLong reloadAt;
    private final long timeoutMs;
    protected volatile int totalConnections;
    protected volatile int idleConnections;
    protected volatile int activeConnections;
    protected volatile int pendingThreads;
    
    public PoolStats(final long timeoutMs) {
        this.timeoutMs = timeoutMs;
        this.reloadAt = new AtomicLong();
        this.clock = ClockSource.INSTANCE;
    }
    
    public int getTotalConnections() {
        if (this.shouldLoad()) {
            this.update();
        }
        return this.totalConnections;
    }
    
    public int getIdleConnections() {
        if (this.shouldLoad()) {
            this.update();
        }
        return this.idleConnections;
    }
    
    public int getActiveConnections() {
        if (this.shouldLoad()) {
            this.update();
        }
        return this.activeConnections;
    }
    
    public int getPendingThreads() {
        if (this.shouldLoad()) {
            this.update();
        }
        return this.pendingThreads;
    }
    
    protected abstract void update();
    
    private boolean shouldLoad() {
        while (true) {
            final long currentTime = this.clock.currentTime();
            final long value = this.reloadAt.get();
            if (value > currentTime) {
                return false;
            }
            if (this.reloadAt.compareAndSet(value, this.clock.plusMillis(currentTime, this.timeoutMs))) {
                return true;
            }
        }
    }
}
