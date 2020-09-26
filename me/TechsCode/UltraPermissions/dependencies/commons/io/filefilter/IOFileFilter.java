

package me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.FileFilter;

public interface IOFileFilter extends FileFilter, FilenameFilter
{
    boolean accept(final File p0);
    
    boolean accept(final File p0, final String p1);
}
