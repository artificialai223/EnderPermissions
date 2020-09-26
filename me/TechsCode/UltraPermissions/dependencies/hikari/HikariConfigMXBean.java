

package me.TechsCode.EnderPermissions.dependencies.hikari;

public interface HikariConfigMXBean
{
    long getConnectionTimeout();
    
    void setConnectionTimeout(final long p0);
    
    long getValidationTimeout();
    
    void setValidationTimeout(final long p0);
    
    long getIdleTimeout();
    
    void setIdleTimeout(final long p0);
    
    long getLeakDetectionThreshold();
    
    void setLeakDetectionThreshold(final long p0);
    
    long getMaxLifetime();
    
    void setMaxLifetime(final long p0);
    
    int getMinimumIdle();
    
    void setMinimumIdle(final int p0);
    
    int getMaximumPoolSize();
    
    void setMaximumPoolSize(final int p0);
    
    void setPassword(final String p0);
    
    void setUsername(final String p0);
    
    String getPoolName();
}
