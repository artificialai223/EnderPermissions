

package me.TechsCode.EnderPermissions.dependencies.nbt;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import me.TechsCode.EnderPermissions.dependencies.nbt.utils.nmsmappings.ObjectCreator;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;

public class NBTFile extends NBTCompound
{
    private final File file;
    private Object nbt;
    
    public NBTFile(final File file) {
        super(null, null);
        if (file == null) {
            throw new NullPointerException("File can't be null!");
        }
        this.file = file;
        if (file.exists()) {
            this.nbt = NBTReflectionUtil.readNBT(new FileInputStream(file));
        }
        else {
            this.nbt = ObjectCreator.NMS_NBTTAGCOMPOUND.getInstance(new Object[0]);
            this.save();
        }
    }
    
    public void save() {
        try {
            this.getWriteLock().lock();
            if (!this.file.exists()) {
                this.file.getParentFile().mkdirs();
                if (!this.file.createNewFile()) {
                    throw new IOException("Unable to create file at " + this.file.getAbsolutePath());
                }
            }
            NBTReflectionUtil.writeNBT(this.nbt, new FileOutputStream(this.file));
        }
        finally {
            this.getWriteLock().unlock();
        }
    }
    
    public File getFile() {
        return this.file;
    }
    
    @Override
    public Object getCompound() {
        return this.nbt;
    }
    
    @Override
    protected void setCompound(final Object nbt) {
        this.nbt = nbt;
    }
}
