

package me.TechsCode.EnderPermissions.dependencies.commons.io.monitor;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadFactory;
import java.util.List;

public final class FileAlterationMonitor implements Runnable
{
    private final long interval;
    private final List<FileAlterationObserver> observers;
    private Thread thread;
    private ThreadFactory threadFactory;
    private volatile boolean running;
    
    public FileAlterationMonitor() {
        this(10000L);
    }
    
    public FileAlterationMonitor(final long interval) {
        this.observers = new CopyOnWriteArrayList<FileAlterationObserver>();
        this.thread = null;
        this.running = false;
        this.interval = interval;
    }
    
    public FileAlterationMonitor(final long n, final FileAlterationObserver... array) {
        this(n);
        if (array != null) {
            for (int length = array.length, i = 0; i < length; ++i) {
                this.addObserver(array[i]);
            }
        }
    }
    
    public long getInterval() {
        return this.interval;
    }
    
    public synchronized void setThreadFactory(final ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }
    
    public void addObserver(final FileAlterationObserver fileAlterationObserver) {
        if (fileAlterationObserver != null) {
            this.observers.add(fileAlterationObserver);
        }
    }
    
    public void removeObserver(final FileAlterationObserver fileAlterationObserver) {
        if (fileAlterationObserver != null) {
            while (this.observers.remove(fileAlterationObserver)) {}
        }
    }
    
    public Iterable<FileAlterationObserver> getObservers() {
        return this.observers;
    }
    
    public synchronized void start() {
        if (this.running) {
            throw new IllegalStateException("Monitor is already running");
        }
        final Iterator<FileAlterationObserver> iterator = this.observers.iterator();
        while (iterator.hasNext()) {
            iterator.next().initialize();
        }
        this.running = true;
        if (this.threadFactory != null) {
            this.thread = this.threadFactory.newThread(this);
        }
        else {
            this.thread = new Thread(this);
        }
        this.thread.start();
    }
    
    public synchronized void stop() {
        this.stop(this.interval);
    }
    
    public synchronized void stop(final long millis) {
        if (!this.running) {
            throw new IllegalStateException("Monitor is not running");
        }
        this.running = false;
        try {
            this.thread.join(millis);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        final Iterator<FileAlterationObserver> iterator = this.observers.iterator();
        while (iterator.hasNext()) {
            iterator.next().destroy();
        }
    }
    
    @Override
    public void run() {
        while (this.running) {
            final Iterator<FileAlterationObserver> iterator = this.observers.iterator();
            while (iterator.hasNext()) {
                iterator.next().checkAndNotify();
            }
            if (!this.running) {
                break;
            }
            try {
                Thread.sleep(this.interval);
            }
            catch (InterruptedException ex) {}
        }
    }
}
