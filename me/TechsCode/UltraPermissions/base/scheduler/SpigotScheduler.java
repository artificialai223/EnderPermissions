

package me.TechsCode.EnderPermissions.base.scheduler;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;

public class SpigotScheduler implements Scheduler
{
    private SpigotTechPlugin plugin;
    
    public SpigotScheduler(final SpigotTechPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void run(final Runnable runnable) {
        Bukkit.getScheduler().runTask((Plugin)((TechPlugin<Plugin>)this.plugin).getBootstrap(), runnable);
    }
    
    @Override
    public void runAsync(final Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)((TechPlugin<Plugin>)this.plugin).getBootstrap(), runnable);
    }
    
    @Override
    public Task runTaskLater(final Runnable runnable, final long n) {
        return this.toGenericTask(Bukkit.getScheduler().runTaskLater((Plugin)((TechPlugin<Plugin>)this.plugin).getBootstrap(), runnable, n));
    }
    
    @Override
    public RecurringTask runTaskTimer(final Runnable runnable, final long n, final long n2) {
        return this.toRecurringTask(() -> Bukkit.getScheduler().runTaskTimer((Plugin)((TechPlugin<Plugin>)this.plugin).getBootstrap(), runnable, n, n2));
    }
    
    @Override
    public Task runTaskLaterAsync(final Runnable runnable, final long n) {
        return this.toGenericTask(Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)((TechPlugin<Plugin>)this.plugin).getBootstrap(), runnable, n));
    }
    
    @Override
    public RecurringTask runTaskTimerAsync(final Runnable runnable, final long n, final long n2) {
        return this.toRecurringTask(() -> Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin)((TechPlugin<Plugin>)this.plugin).getBootstrap(), runnable, n, n2));
    }
    
    private Task toGenericTask(final BukkitTask bukkitTask) {
        return new Task() {
            @Override
            public void cancel() {
                bukkitTask.cancel();
            }
        };
    }
    
    private RecurringTask toRecurringTask(final TaskCreator taskCreator) {
        return new RecurringTask() {
            private BukkitTask bukkitTask = taskCreator.build();
            
            @Override
            public void stop() {
                if (this.bukkitTask != null) {
                    this.bukkitTask.cancel();
                    this.bukkitTask = null;
                }
            }
            
            @Override
            public void start() {
                if (this.bukkitTask == null) {
                    this.bukkitTask = taskCreator.build();
                }
            }
        };
    }
    
    interface TaskCreator
    {
        BukkitTask build();
    }
}
