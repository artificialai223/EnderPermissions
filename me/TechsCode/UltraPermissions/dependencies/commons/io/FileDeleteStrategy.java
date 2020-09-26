

package me.TechsCode.EnderPermissions.dependencies.commons.io;

import java.io.IOException;
import java.io.File;

public class FileDeleteStrategy
{
    public static final FileDeleteStrategy NORMAL;
    public static final FileDeleteStrategy FORCE;
    private final String name;
    
    protected FileDeleteStrategy(final String name) {
        this.name = name;
    }
    
    public boolean deleteQuietly(final File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        try {
            return this.doDelete(file);
        }
        catch (IOException ex) {
            return false;
        }
    }
    
    public void delete(final File obj) {
        if (obj.exists() && !this.doDelete(obj)) {
            throw new IOException("Deletion failed: " + obj);
        }
    }
    
    protected boolean doDelete(final File file) {
        return file.delete();
    }
    
    @Override
    public String toString() {
        return "FileDeleteStrategy[" + this.name + "]";
    }
    
    static {
        NORMAL = new FileDeleteStrategy("Normal");
        FORCE = new ForceFileDeleteStrategy();
    }
    
    static class ForceFileDeleteStrategy extends FileDeleteStrategy
    {
        ForceFileDeleteStrategy() {
            super("Force");
        }
        
        @Override
        protected boolean doDelete(final File file) {
            FileUtils.forceDelete(file);
            return true;
        }
    }
}
