

package me.TechsCode.EnderPermissions.base.fileconf.impl;

import java.io.IOException;
import java.util.Set;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import me.TechsCode.EnderPermissions.base.fileconf.Configuration;

public class SpigotFileConfiguration implements Configuration
{
    private File file;
    private YamlConfiguration cfg;
    
    public SpigotFileConfiguration(final File file) {
        this.file = file;
        this.cfg = YamlConfiguration.loadConfiguration(file);
    }
    
    @Override
    public Object get(final String s) {
        return this.cfg.get(s);
    }
    
    @Override
    public int getInt(final String s) {
        return this.cfg.getInt(s);
    }
    
    @Override
    public String getString(final String s) {
        return this.cfg.getString(s);
    }
    
    @Override
    public boolean getBoolean(final String s) {
        return this.cfg.getBoolean(s);
    }
    
    @Override
    public Set<String> getKeys(final boolean b) {
        return (Set<String>)this.cfg.getKeys(b);
    }
    
    @Override
    public boolean contains(final String s) {
        return this.cfg.contains(s);
    }
    
    @Override
    public void set(final String s, final Object o) {
        this.cfg.set(s, o);
    }
    
    @Override
    public void save() {
        try {
            this.cfg.save(this.file);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
