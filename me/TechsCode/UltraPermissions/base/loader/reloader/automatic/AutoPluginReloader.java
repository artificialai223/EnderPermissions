

package me.TechsCode.EnderPermissions.base.loader.reloader.automatic;

import java.io.UnsupportedEncodingException;
import java.io.File;
import java.net.URLDecoder;
import me.TechsCode.EnderPermissions.base.TechPlugin;

public class AutoPluginReloader extends Thread
{
    private TechPlugin<?> plugin;
    private FileChangeWatcher thread;
    private boolean anyChanges;
    private long lastChanges;
    
    public AutoPluginReloader(final TechPlugin<?> plugin) {
        this.plugin = plugin;
        this.anyChanges = false;
        this.lastChanges = 0L;
        try {
            this.thread = new FileChangeWatcher(new File(URLDecoder.decode(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8"))) {
                @Override
                public void onChanges() {
                    if (!AutoPluginReloader.this.anyChanges) {
                        plugin.log("Plugin Jar is currently being changed, once completed the plugin will reload");
                    }
                    AutoPluginReloader.this.anyChanges = true;
                    AutoPluginReloader.this.lastChanges = System.currentTimeMillis();
                }
            };
        }
        catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        plugin.getScheduler().runTaskTimer(() -> {
            if (this.lastChanges != 0L) {
                if (System.currentTimeMillis() - this.lastChanges > 250L) {
                    this.lastChanges = 0L;
                    plugin.getPluginReloader().unload();
                    plugin.getPluginReloader().load();
                }
            }
        }, 0L, 5L);
    }
    
    public void onDisable() {
        this.thread.stopThread();
    }
}
