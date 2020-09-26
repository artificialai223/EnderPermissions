

package me.TechsCode.EnderPermissions.base.scheduler;

import net.md_5.bungee.api.scheduler.ScheduledTask;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.ProxyServer;
import me.TechsCode.EnderPermissions.base.BungeeTechPlugin;

public class BungeeScheduler implements Scheduler
{
    private BungeeTechPlugin plugin;
    
    public BungeeScheduler(final BungeeTechPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void run(final Runnable runnable) {
        runnable.run();
    }
    
    @Override
    public void runAsync(final Runnable runnable) {
        ProxyServer.getInstance().getScheduler().runAsync((Plugin)this.plugin.getBootstrap(), runnable);
    }
    
    @Override
    public Task runTaskLater(final Runnable runnable, final long n) {
        return new Task() {
            final /* synthetic */ ScheduledTask val$t = ProxyServer.getInstance().getScheduler().schedule((Plugin)BungeeScheduler.this.plugin.getBootstrap(), runnable, n * 1000L / 20L, TimeUnit.MILLISECONDS);
            
            @Override
            public void cancel() {
                this.val$t.cancel();
            }
        };
    }
    
    @Override
    public RecurringTask runTaskTimer(final Runnable runnable, final long n, final long n2) {
        return new RecurringTask() {
            private boolean cancelled;
            final /* synthetic */ ScheduledTask val$t = ProxyServer.getInstance().getScheduler().schedule((Plugin)BungeeScheduler.this.plugin.getBootstrap(), runnable, n * 1000L / 20L, n2 * 1000L / 20L, TimeUnit.MILLISECONDS);
            
            @Override
            public void stop() {
                this.val$t.cancel();
                this.cancelled = true;
            }
            
            @Override
            public void start() {
                if (this.cancelled) {
                    BungeeScheduler.this.runTaskTimer(runnable, n, n2);
                }
            }
        };
    }
    
    @Override
    public Task runTaskLaterAsync(final Runnable runnable, final long n) {
        throw new UnsupportedOperationException("The method runTaskLaterAsync is not defined for BungeeCord");
    }
    
    @Override
    public RecurringTask runTaskTimerAsync(final Runnable runnable, final long n, final long n2) {
        throw new UnsupportedOperationException("The method runTaskTimerAsync is not defined for BungeeCord");
    }
}
