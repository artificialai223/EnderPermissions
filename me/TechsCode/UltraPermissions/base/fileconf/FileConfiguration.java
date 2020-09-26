

package me.TechsCode.EnderPermissions.base.fileconf;

import java.util.Map;
import java.util.Set;
import java.lang.reflect.InvocationTargetException;
import me.TechsCode.EnderPermissions.base.fileconf.impl.BungeeFileConfiguration;
import me.TechsCode.EnderPermissions.base.BungeeTechPlugin;
import me.TechsCode.EnderPermissions.base.fileconf.impl.SpigotFileConfiguration;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import java.io.File;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import java.util.HashMap;

public class FileConfiguration implements Configuration
{
    private HashMap<Class<? extends TechPlugin>, Class<? extends Configuration>> implementations;
    private Configuration configuration;
    
    public FileConfiguration(final TechPlugin techPlugin, final File file) {
        (this.implementations = new HashMap<Class<? extends TechPlugin>, Class<? extends Configuration>>()).put(SpigotTechPlugin.class, SpigotFileConfiguration.class);
        this.implementations.put(BungeeTechPlugin.class, BungeeFileConfiguration.class);
        final Class<Configuration> clazz = this.implementations.entrySet().stream().filter(entry2 -> entry2.getKey().isInstance(techPlugin)).map(entry -> entry.getValue()).findFirst().orElse(null);
        if (clazz == null) {
            techPlugin.log("§cCould not load " + file.getAbsolutePath() + " because of missing File Implementation");
            return;
        }
        try {
            this.configuration = clazz.getDeclaredConstructor(File.class).newInstance(file);
        }
        catch (InstantiationException ex) {
            ex.printStackTrace();
        }
        catch (IllegalAccessException ex2) {
            ex2.printStackTrace();
        }
        catch (InvocationTargetException ex3) {
            ex3.printStackTrace();
        }
        catch (NoSuchMethodException ex4) {
            ex4.printStackTrace();
        }
    }
    
    @Override
    public Object get(final String s) {
        return this.configuration.get(s);
    }
    
    @Override
    public int getInt(final String s) {
        return this.configuration.getInt(s);
    }
    
    @Override
    public String getString(final String s) {
        return this.configuration.getString(s);
    }
    
    @Override
    public boolean getBoolean(final String s) {
        return this.configuration.getBoolean(s);
    }
    
    @Override
    public Set<String> getKeys(final boolean b) {
        return this.configuration.getKeys(b);
    }
    
    @Override
    public boolean contains(final String s) {
        return this.configuration.contains(s);
    }
    
    @Override
    public void set(final String s, final Object o) {
        this.configuration.set(s, o);
    }
    
    @Override
    public void save() {
        this.configuration.save();
    }
}
