

package me.TechsCode.EnderPermissions.dependencies.hikari.metrics.dropwizard;

import java.util.concurrent.TimeUnit;
import com.codahale.metrics.Metric;
import com.codahale.metrics.Gauge;
import me.TechsCode.EnderPermissions.dependencies.hikari.metrics.PoolStats;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Timer;
import me.TechsCode.EnderPermissions.dependencies.hikari.metrics.MetricsTracker;

public final class CodaHaleMetricsTracker extends MetricsTracker
{
    private final String poolName;
    private final Timer connectionObtainTimer;
    private final Histogram connectionUsage;
    private final MetricRegistry registry;
    
    public CodaHaleMetricsTracker(final String poolName, final PoolStats poolStats, final MetricRegistry registry) {
        this.poolName = poolName;
        this.registry = registry;
        this.connectionObtainTimer = registry.timer(MetricRegistry.name(poolName, new String[] { "pool", "Wait" }));
        this.connectionUsage = registry.histogram(MetricRegistry.name(poolName, new String[] { "pool", "Usage" }));
        registry.register(MetricRegistry.name(poolName, new String[] { "pool", "TotalConnections" }), (Metric)new Gauge<Integer>() {
            public Integer getValue() {
                return poolStats.getTotalConnections();
            }
        });
        registry.register(MetricRegistry.name(poolName, new String[] { "pool", "IdleConnections" }), (Metric)new Gauge<Integer>() {
            public Integer getValue() {
                return poolStats.getIdleConnections();
            }
        });
        registry.register(MetricRegistry.name(poolName, new String[] { "pool", "ActiveConnections" }), (Metric)new Gauge<Integer>() {
            public Integer getValue() {
                return poolStats.getActiveConnections();
            }
        });
        registry.register(MetricRegistry.name(poolName, new String[] { "pool", "PendingConnections" }), (Metric)new Gauge<Integer>() {
            public Integer getValue() {
                return poolStats.getPendingThreads();
            }
        });
    }
    
    @Override
    public void close() {
        this.registry.remove(MetricRegistry.name(this.poolName, new String[] { "pool", "Wait" }));
        this.registry.remove(MetricRegistry.name(this.poolName, new String[] { "pool", "Usage" }));
        this.registry.remove(MetricRegistry.name(this.poolName, new String[] { "pool", "TotalConnections" }));
        this.registry.remove(MetricRegistry.name(this.poolName, new String[] { "pool", "IdleConnections" }));
        this.registry.remove(MetricRegistry.name(this.poolName, new String[] { "pool", "ActiveConnections" }));
        this.registry.remove(MetricRegistry.name(this.poolName, new String[] { "pool", "PendingConnections" }));
    }
    
    @Override
    public void recordConnectionAcquiredNanos(final long n) {
        this.connectionObtainTimer.update(n, TimeUnit.NANOSECONDS);
    }
    
    @Override
    public void recordConnectionUsageMillis(final long n) {
        this.connectionUsage.update(n);
    }
    
    public Timer getConnectionAcquisitionTimer() {
        return this.connectionObtainTimer;
    }
    
    public Histogram getConnectionDurationHistogram() {
        return this.connectionUsage;
    }
}
