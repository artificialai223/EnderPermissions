

package me.TechsCode.EnderPermissions.base.loader;

import java.lang.reflect.InvocationTargetException;
import me.TechsCode.EnderPermissions.base.TechPlugin;

public class Loader
{
    private TechPlugin<?> techPlugin;
    
    public Loader(final Object o, final Class<? extends TechPlugin<?>> clazz, final ClassLoader classLoader) {
        final Class<?> techPluginClass = LoaderUtil.getTechPluginClass(clazz, classLoader);
        if (techPluginClass == null) {
            return;
        }
        try {
            this.techPlugin = (TechPlugin<?>)techPluginClass.getDeclaredConstructors()[0].newInstance(o);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            final Throwable t;
            t.printStackTrace();
        }
    }
    
    public void unload() {
        if (this.techPlugin != null) {
            this.techPlugin.disable();
        }
    }
}
