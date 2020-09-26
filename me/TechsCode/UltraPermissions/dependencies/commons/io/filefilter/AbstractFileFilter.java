

package me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter;

import java.io.File;

public abstract class AbstractFileFilter implements IOFileFilter
{
    @Override
    public boolean accept(final File file) {
        return this.accept(file.getParentFile(), file.getName());
    }
    
    @Override
    public boolean accept(final File parent, final String child) {
        return this.accept(new File(parent, child));
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
