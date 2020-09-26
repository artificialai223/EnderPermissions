

package me.TechsCode.EnderPermissions.dependencies.hikari.util;

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

public final class QueuedSequenceSynchronizer
{
    private final Sequence sequence;
    private final Synchronizer synchronizer;
    
    public QueuedSequenceSynchronizer() {
        this.synchronizer = new Synchronizer();
        this.sequence = Sequence.Factory.create();
    }
    
    public void signal() {
        this.synchronizer.releaseShared(1L);
    }
    
    public long currentSequence() {
        return this.sequence.get();
    }
    
    public boolean waitUntilSequenceExceeded(final long arg, final long nanosTimeout) {
        return this.synchronizer.tryAcquireSharedNanos(arg, nanosTimeout);
    }
    
    public boolean hasQueuedThreads() {
        return this.synchronizer.hasQueuedThreads();
    }
    
    public int getQueueLength() {
        return this.synchronizer.getQueueLength();
    }
    
    private final class Synchronizer extends AbstractQueuedLongSynchronizer
    {
        private static final long serialVersionUID = 104753538004341218L;
        
        @Override
        protected long tryAcquireShared(final long n) {
            return QueuedSequenceSynchronizer.this.sequence.get() - (n + 1L);
        }
        
        @Override
        protected boolean tryReleaseShared(final long n) {
            QueuedSequenceSynchronizer.this.sequence.increment();
            return true;
        }
    }
}
