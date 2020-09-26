

package me.TechsCode.EnderPermissions.base.scheduler;

public interface Scheduler
{
    void run(final Runnable p0);
    
    void runAsync(final Runnable p0);
    
    Task runTaskLater(final Runnable p0, final long p1);
    
    RecurringTask runTaskTimer(final Runnable p0, final long p1, final long p2);
    
    Task runTaskLaterAsync(final Runnable p0, final long p1);
    
    RecurringTask runTaskTimerAsync(final Runnable p0, final long p1, final long p2);
}
