

package me.TechsCode.EnderPermissions.dependencies.hikari.metrics.dropwizard;

import me.TechsCode.EnderPermissions.dependencies.hikari.metrics.MetricsTracker;
import me.TechsCode.EnderPermissions.dependencies.hikari.metrics.PoolStats;
import com.codahale.metrics.MetricRegistry;
import me.TechsCode.EnderPermissions.dependencies.hikari.metrics.MetricsTrackerFactory;

public final class CodahaleMetricsTrackerFactory implements MetricsTrackerFactory
{
    private final MetricRegistry registry;
    
    public CodahaleMetricsTrackerFactory(final MetricRegistry registry) {
        this.registry = registry;
    }
    
    public MetricRegistry getRegistry() {
        return this.registry;
    }
    
    @Override
    public MetricsTracker create(final String s, final PoolStats poolStats) {
        return new CodaHaleMetricsTracker(s, poolStats, this.registry);
    }
}
