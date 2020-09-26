

package me.TechsCode.EnderPermissions.dependencies.hikari.pool;

import me.TechsCode.EnderPermissions.dependencies.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;

class ProxyLeakTask implements Runnable
{
    private static final Logger LOGGER;
    private static final ProxyLeakTask NO_LEAK;
    private ScheduledExecutorService executorService;
    private long leakDetectionThreshold;
    private ScheduledFuture<?> scheduledFuture;
    private String connectionName;
    private Exception exception;
    
    ProxyLeakTask(final long leakDetectionThreshold, final ScheduledExecutorService executorService) {
        this.executorService = executorService;
        this.leakDetectionThreshold = leakDetectionThreshold;
    }
    
    private ProxyLeakTask(final ProxyLeakTask proxyLeakTask, final PoolEntry poolEntry) {
        this.exception = new Exception("Apparent connection leak detected");
        this.connectionName = poolEntry.connection.toString();
        this.scheduledFuture = proxyLeakTask.executorService.schedule(this, proxyLeakTask.leakDetectionThreshold, TimeUnit.MILLISECONDS);
    }
    
    private ProxyLeakTask() {
    }
    
    ProxyLeakTask start(final PoolEntry poolEntry) {
        return (this.leakDetectionThreshold == 0L) ? ProxyLeakTask.NO_LEAK : new ProxyLeakTask(this, poolEntry);
    }
    
    void updateLeakDetectionThreshold(final long leakDetectionThreshold) {
        this.leakDetectionThreshold = leakDetectionThreshold;
    }
    
    @Override
    public void run() {
        final StackTraceElement[] stackTrace = this.exception.getStackTrace();
        final StackTraceElement[] stackTrace2 = new StackTraceElement[stackTrace.length - 5];
        System.arraycopy(stackTrace, 5, stackTrace2, 0, stackTrace2.length);
        this.exception.setStackTrace(stackTrace2);
        ProxyLeakTask.LOGGER.warn("Connection leak detection triggered for connection {}, stack trace follows", this.connectionName, this.exception);
    }
    
    void cancel() {
        this.scheduledFuture.cancel(false);
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(ProxyLeakTask.class);
        NO_LEAK = new ProxyLeakTask() {
            public void cancel() {
            }
        };
    }
}
