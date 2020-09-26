

package me.TechsCode.EnderPermissions.dependencies.hikari.util;

import me.TechsCode.EnderPermissions.dependencies.slf4j.LoggerFactory;
import java.util.Iterator;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;

public class ConcurrentBag<T extends IConcurrentBagEntry> implements AutoCloseable
{
    private static final Logger LOGGER;
    private final QueuedSequenceSynchronizer synchronizer;
    private final CopyOnWriteArrayList<T> sharedList;
    private final boolean weakThreadLocals;
    private final ThreadLocal<List> threadList;
    private final IBagStateListener listener;
    private final AtomicInteger waiters;
    private volatile boolean closed;
    
    public ConcurrentBag(final IBagStateListener listener) {
        this.listener = listener;
        this.weakThreadLocals = this.useWeakThreadLocals();
        this.waiters = new AtomicInteger();
        this.sharedList = new CopyOnWriteArrayList<T>();
        this.synchronizer = new QueuedSequenceSynchronizer();
        if (this.weakThreadLocals) {
            this.threadList = new ThreadLocal<List>();
        }
        else {
            this.threadList = new ThreadLocal<List>() {
                @Override
                protected List initialValue() {
                    return new FastList(IConcurrentBagEntry.class, 16);
                }
            };
        }
    }
    
    public T borrow(long nanos, final TimeUnit timeUnit) {
        Object value = this.threadList.get();
        if (this.weakThreadLocals && value == null) {
            value = new ArrayList<WeakReference<Object>>(16);
            this.threadList.set((List)value);
        }
        for (int i = ((List)value).size() - 1; i >= 0; --i) {
            final IConcurrentBagEntry concurrentBagEntry = (T)(this.weakThreadLocals ? ((List<WeakReference<Object>>)value).remove(i).get() : ((List<WeakReference<Object>>)value).remove(i));
            if (concurrentBagEntry != null && concurrentBagEntry.compareAndSet(0, 1)) {
                return (T)concurrentBagEntry;
            }
        }
        nanos = timeUnit.toNanos(nanos);
        Future<Boolean> addBagItem = null;
        final long nanoTime = System.nanoTime();
        final long n = nanos;
        this.waiters.incrementAndGet();
        try {
            while (true) {
                final long currentSequence = this.synchronizer.currentSequence();
                for (final IConcurrentBagEntry concurrentBagEntry2 : this.sharedList) {
                    if (concurrentBagEntry2.compareAndSet(0, 1)) {
                        if (this.waiters.get() > 1 && addBagItem == null) {
                            this.listener.addBagItem();
                        }
                        return (T)concurrentBagEntry2;
                    }
                }
                if (currentSequence >= this.synchronizer.currentSequence()) {
                    if (addBagItem == null || addBagItem.isDone()) {
                        addBagItem = this.listener.addBagItem();
                    }
                    nanos = n - (System.nanoTime() - nanoTime);
                    if (nanos <= 10000L || !this.synchronizer.waitUntilSequenceExceeded(currentSequence, nanos)) {
                        break;
                    }
                    continue;
                }
            }
        }
        finally {
            this.waiters.decrementAndGet();
        }
        return null;
    }
    
    public void requite(final T referent) {
        referent.lazySet(0);
        final List<Object> list = this.threadList.get();
        if (list != null) {
            list.add(this.weakThreadLocals ? new WeakReference((T)referent) : referent);
        }
        this.synchronizer.signal();
    }
    
    public void add(final T e) {
        if (this.closed) {
            ConcurrentBag.LOGGER.info("ConcurrentBag has been closed, ignoring add()");
            throw new IllegalStateException("ConcurrentBag has been closed, ignoring add()");
        }
        this.sharedList.add(e);
        this.synchronizer.signal();
    }
    
    public boolean remove(final T o) {
        if (!o.compareAndSet(1, -1) && !o.compareAndSet(-2, -1) && !this.closed) {
            ConcurrentBag.LOGGER.warn("Attempt to remove an object from the bag that was not borrowed or reserved: {}", o);
            return false;
        }
        final boolean remove = this.sharedList.remove(o);
        if (!remove && !this.closed) {
            ConcurrentBag.LOGGER.warn("Attempt to remove an object from the bag that does not exist: {}", o);
        }
        return remove;
    }
    
    @Override
    public void close() {
        this.closed = true;
    }
    
    public List<T> values(final int n) {
        final ArrayList<IConcurrentBagEntry> list = (ArrayList<IConcurrentBagEntry>)new ArrayList<T>(this.sharedList.size());
        for (final IConcurrentBagEntry e : this.sharedList) {
            if (e.getState() == n) {
                list.add(e);
            }
        }
        return (List<T>)list;
    }
    
    public List<T> values() {
        return (List<T>)this.sharedList.clone();
    }
    
    public boolean reserve(final T t) {
        return t.compareAndSet(0, -2);
    }
    
    public void unreserve(final T t) {
        if (t.compareAndSet(-2, 0)) {
            this.synchronizer.signal();
        }
        else {
            ConcurrentBag.LOGGER.warn("Attempt to relinquish an object to the bag that was not reserved: {}", t);
        }
    }
    
    public int getPendingQueue() {
        return this.synchronizer.getQueueLength();
    }
    
    public int getCount(final int n) {
        int n2 = 0;
        final Iterator<T> iterator = this.sharedList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getState() == n) {
                ++n2;
            }
        }
        return n2;
    }
    
    public int size() {
        return this.sharedList.size();
    }
    
    public void dumpState() {
        final Iterator<T> iterator = this.sharedList.iterator();
        while (iterator.hasNext()) {
            ConcurrentBag.LOGGER.info(iterator.next().toString());
        }
    }
    
    private boolean useWeakThreadLocals() {
        try {
            if (System.getProperty("me.TechsCode.EnderPermissions.dependencies.hikari.useWeakReferences") != null) {
                return Boolean.getBoolean("me.TechsCode.EnderPermissions.dependencies.hikari.useWeakReferences");
            }
            return this.getClass().getClassLoader() != ClassLoader.getSystemClassLoader();
        }
        catch (SecurityException ex) {
            return true;
        }
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(ConcurrentBag.class);
    }
    
    public interface IBagStateListener
    {
        Future<Boolean> addBagItem();
    }
    
    public interface IConcurrentBagEntry
    {
        public static final int STATE_NOT_IN_USE = 0;
        public static final int STATE_IN_USE = 1;
        public static final int STATE_REMOVED = -1;
        public static final int STATE_RESERVED = -2;
        
        boolean compareAndSet(final int p0, final int p1);
        
        void lazySet(final int p0);
        
        int getState();
    }
}
