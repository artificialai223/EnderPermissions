

package me.TechsCode.EnderPermissions.base.fileconf.impl;

import java.util.Iterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import java.io.IOException;
import java.io.File;
import me.TechsCode.EnderPermissions.base.fileconf.Configuration;

public class BungeeFileConfiguration implements Configuration
{
    private File file;
    private net.md_5.bungee.config.Configuration configuration;
    
    public BungeeFileConfiguration(final File file) {
        this.file = file;
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            this.configuration = ConfigurationProvider.getProvider((Class)YamlConfiguration.class).load(file);
        }
        catch (IOException ex2) {
            ex2.printStackTrace();
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
        final HashSet<Object> set = new HashSet<Object>();
        if (b) {
            this.recursiveKeys((Set<String>)set, "", this.configuration);
        }
        else {
            set.addAll(this.configuration.getKeys());
        }
        return (Set<String>)set;
    }
    
    private void recursiveKeys(final Set<String> set, final String s, final net.md_5.bungee.config.Configuration configuration) {
        set.addAll(configuration.getKeys().stream().map(str -> s + str).collect((Collector<? super Object, ?, Collection<? extends String>>)Collectors.toList()));
        for (final String str2 : configuration.getKeys()) {
            try {
                final net.md_5.bungee.config.Configuration section = configuration.getSection(str2);
                if (section == null) {
                    continue;
                }
                this.recursiveKeys(set, s + str2 + ".", section);
            }
            catch (ClassCastException ex) {}
        }
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
        try {
            ConfigurationProvider.getProvider((Class)YamlConfiguration.class).save(this.configuration, this.file);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
