

package me.TechsCode.EnderPermissions.dependencies.hikari.util;

import java.util.concurrent.Semaphore;

public class SuspendResumeLock
{
    public static final SuspendResumeLock FAUX_LOCK;
    private static final int MAX_PERMITS = 10000;
    private final Semaphore acquisitionSemaphore;
    
    public SuspendResumeLock() {
        this(true);
    }
    
    private SuspendResumeLock(final boolean b) {
        this.acquisitionSemaphore = (b ? new Semaphore(10000, true) : null);
    }
    
    public void acquire() {
        this.acquisitionSemaphore.acquireUninterruptibly();
    }
    
    public void release() {
        this.acquisitionSemaphore.release();
    }
    
    public void suspend() {
        this.acquisitionSemaphore.acquireUninterruptibly(10000);
    }
    
    public void resume() {
        this.acquisitionSemaphore.release(10000);
    }
    
    static {
        FAUX_LOCK = new SuspendResumeLock(false) {
            @Override
            public void acquire() {
            }
            
            @Override
            public void release() {
            }
            
            @Override
            public void suspend() {
            }
            
            @Override
            public void resume() {
            }
        };
    }
}
