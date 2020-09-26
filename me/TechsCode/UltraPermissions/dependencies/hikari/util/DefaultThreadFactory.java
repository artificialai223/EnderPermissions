

package me.TechsCode.EnderPermissions.dependencies.hikari.util;

import java.util.concurrent.ThreadFactory;

public class DefaultThreadFactory implements ThreadFactory
{
    private final String threadName;
    private final boolean daemon;
    
    public DefaultThreadFactory(final String threadName, final boolean daemon) {
        this.threadName = threadName;
        this.daemon = daemon;
    }
    
    @Override
    public Thread newThread(final Runnable target) {
        final Thread thread = new Thread(target, this.threadName);
        thread.setDaemon(this.daemon);
        return thread;
    }
}
