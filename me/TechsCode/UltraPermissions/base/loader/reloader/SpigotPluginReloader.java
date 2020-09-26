

package me.TechsCode.EnderPermissions.base.loader.reloader;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import org.bukkit.plugin.RegisteredListener;
import java.util.SortedSet;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.InvalidDescriptionException;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.net.URLDecoder;
import java.util.Iterator;
import java.lang.reflect.Field;
import java.io.IOException;
import java.net.URLClassLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import java.util.Map;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;

public class SpigotPluginReloader extends PluginReloader<SpigotTechPlugin>
{
    public SpigotPluginReloader(final SpigotTechPlugin spigotTechPlugin) {
        super(spigotTechPlugin);
    }
    
    @Override
    public void unload() {
        Bukkit.getPluginManager().disablePlugin((Plugin)((TechPlugin<Plugin>)this.plugin).getBootstrap());
        try {
            final Field declaredField = Bukkit.getPluginManager().getClass().getDeclaredField("listeners");
            declaredField.setAccessible(true);
            ((Map)declaredField.get(Bukkit.getPluginManager())).values().forEach(set -> set.removeIf(registeredListener -> registeredListener.getPlugin() == ((TechPlugin<Plugin>)this.plugin).getBootstrap()));
        }
        catch (Exception ex2) {}
        try {
            final Field declaredField2 = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            declaredField2.setAccessible(true);
            final SimpleCommandMap obj = (SimpleCommandMap)declaredField2.get(Bukkit.getPluginManager());
            final Field declaredField3 = SimpleCommandMap.class.getDeclaredField("knownCommands");
            declaredField3.setAccessible(true);
            final Iterator<Map.Entry<K, PluginCommand>> iterator = ((Map)declaredField3.get(obj)).entrySet().iterator();
            while (iterator.hasNext()) {
                final Map.Entry<K, PluginCommand> entry = iterator.next();
                if (entry.getValue() instanceof PluginCommand) {
                    final PluginCommand pluginCommand = entry.getValue();
                    if (pluginCommand.getPlugin() != ((TechPlugin<Plugin>)this.plugin).getBootstrap()) {
                        continue;
                    }
                    pluginCommand.unregister((CommandMap)obj);
                    iterator.remove();
                }
            }
        }
        catch (NoSuchFieldException | IllegalAccessException ex3) {
            final Throwable t;
            t.printStackTrace();
        }
        final ClassLoader classLoader = ((SpigotTechPlugin)this.plugin).getBootstrap().getClass().getClassLoader();
        if (classLoader instanceof URLClassLoader) {
            try {
                final Field declaredField4 = ((URLClassLoader)classLoader).getClass().getDeclaredField("plugin");
                declaredField4.setAccessible(true);
                declaredField4.set(classLoader, null);
                final Field declaredField5 = ((URLClassLoader)classLoader).getClass().getDeclaredField("pluginInit");
                declaredField5.setAccessible(true);
                declaredField5.set(classLoader, null);
            }
            catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex4) {
                final Throwable t2;
                t2.printStackTrace();
            }
            try {
                ((URLClassLoader)classLoader).close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.gc();
    }
    
    @Override
    public void load() {
        try {
            final Plugin loadPlugin = Bukkit.getPluginManager().loadPlugin(new File(URLDecoder.decode(((SpigotTechPlugin)this.plugin).getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")));
            loadPlugin.onLoad();
            Bukkit.getPluginManager().enablePlugin(loadPlugin);
        }
        catch (UnsupportedEncodingException | InvalidDescriptionException | InvalidPluginException ex) {
            final Throwable t;
            t.printStackTrace();
        }
    }
}
