

package me.TechsCode.EnderPermissions.base.legacySystemStorage;

import java.util.HashMap;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import me.TechsCode.EnderPermissions.base.fileconf.FileConfiguration;
import java.io.File;

public class LegacySystemStorage
{
    private File file;
    private FileConfiguration cfg;
    
    public LegacySystemStorage(final TechPlugin techPlugin) {
        this.file = new File(techPlugin.getPluginFolder().getAbsolutePath() + "/System.dat");
        if (this.file.exists()) {
            this.cfg = new FileConfiguration(techPlugin, this.file);
        }
    }
    
    public void wipe() {
        if (this.file.exists()) {
            this.file.delete();
        }
        this.cfg = null;
    }
    
    public Result get(final String s) {
        if (this.cfg == null) {
            return null;
        }
        return new Result(this.cfg.get(s));
    }
    
    public boolean contains(final String s) {
        return this.cfg != null && this.cfg.contains(s);
    }
    
    public LegacyMySQLSettings getMySQL() {
        if (this.cfg == null) {
            return null;
        }
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        final String s3;
        this.cfg.getKeys(true).stream().filter(s -> s.startsWith("mysql.")).forEach(s2 -> s3 = hashMap.put(s2.replace("mysql.", ""), this.cfg.get(s2).toString()));
        if (hashMap.size() != 0) {
            return LegacyMySQLSettings.deserialize(hashMap);
        }
        return null;
    }
}
