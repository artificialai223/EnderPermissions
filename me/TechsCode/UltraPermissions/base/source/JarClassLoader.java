

package me.TechsCode.EnderPermissions.base.source;

import java.net.URL;
import java.net.MalformedURLException;
import java.lang.reflect.InvocationTargetException;
import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import java.net.URLClassLoader;
import java.lang.reflect.Method;

public class JarClassLoader
{
    private static final Method ADD_URL_METHOD;
    private final URLClassLoader classLoader;
    
    public JarClassLoader(final SpigotTechPlugin spigotTechPlugin) {
        this.classLoader = (URLClassLoader)spigotTechPlugin.getBootstrap().getClass().getClassLoader();
    }
    
    public void load(final File file) {
        try {
            JarClassLoader.ADD_URL_METHOD.invoke(this.classLoader, file.toPath().toUri().toURL());
        }
        catch (IllegalAccessException | InvocationTargetException | MalformedURLException ex) {
            final Throwable t;
            t.printStackTrace();
        }
    }
    
    static {
        try {
            (ADD_URL_METHOD = URLClassLoader.class.getDeclaredMethod("addURL", URL.class)).setAccessible(true);
        }
        catch (NoSuchMethodException thrown) {
            throw new ExceptionInInitializerError(thrown);
        }
    }
}
