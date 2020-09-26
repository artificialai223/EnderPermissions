

package me.TechsCode.EnderPermissions.base.loader.reloader.automatic;

import java.util.Iterator;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.FileSystems;
import java.io.File;

public abstract class FileChangeWatcher extends Thread
{
    private File file;
    private boolean running;
    
    public FileChangeWatcher(final File file) {
        this.file = file;
        this.running = true;
        this.start();
    }
    
    public void stopThread() {
        this.running = false;
    }
    
    public abstract void onChanges();
    
    @Override
    public void run() {
        try {
            final WatchService watchService = FileSystems.getDefault().newWatchService();
            this.file.getParentFile().toPath().register(watchService, (WatchEvent.Kind<?>[])new WatchEvent.Kind[] { StandardWatchEventKinds.ENTRY_MODIFY });
            while (this.running) {
                final WatchKey take = watchService.take();
                final Iterator<WatchEvent<?>> iterator = take.pollEvents().iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().context().toFile().getName().equals(this.file.getName())) {
                        this.onChanges();
                    }
                }
                take.reset();
            }
        }
        catch (IOException | InterruptedException ex) {
            final Throwable t;
            t.printStackTrace();
        }
    }
}
