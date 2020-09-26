

package me.TechsCode.EnderPermissions.base.source;

import java.util.Iterator;
import java.io.IOException;
import me.TechsCode.EnderPermissions.dependencies.commons.io.FileUtils;
import java.net.URL;
import java.util.Collection;
import java.io.File;
import me.TechsCode.EnderPermissions.base.TechPlugin;

public class LocalRepository
{
    private TechPlugin plugin;
    private File folder;
    
    public LocalRepository(final TechPlugin plugin) {
        this.plugin = plugin;
        this.folder = new File(plugin.getPluginFolder().getAbsolutePath() + "/Maven");
    }
    
    public File getIfPresent(final Dependency dependency) {
        final File file = new File(this.folder.getAbsolutePath() + "/" + dependency.toURL());
        return file.exists() ? file : null;
    }
    
    public boolean load(final Dependency dependency, final Collection<String> collection) {
        final File file = new File(this.folder.getAbsolutePath() + "/" + dependency.toURL());
        final Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()) {
            final String string = iterator.next() + "/" + dependency.toURL();
            try {
                FileUtils.copyURLToFile(new URL(string), file);
                return true;
            }
            catch (IOException ex) {
                continue;
            }
            break;
        }
        return false;
    }
}
