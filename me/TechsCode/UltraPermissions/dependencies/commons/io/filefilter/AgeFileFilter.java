

package me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter;

import me.TechsCode.EnderPermissions.dependencies.commons.io.FileUtils;
import java.io.File;
import java.util.Date;
import java.io.Serializable;

public class AgeFileFilter extends AbstractFileFilter implements Serializable
{
    private static final long serialVersionUID = -2132740084016138541L;
    private final long cutoff;
    private final boolean acceptOlder;
    
    public AgeFileFilter(final long n) {
        this(n, true);
    }
    
    public AgeFileFilter(final long cutoff, final boolean acceptOlder) {
        this.acceptOlder = acceptOlder;
        this.cutoff = cutoff;
    }
    
    public AgeFileFilter(final Date date) {
        this(date, true);
    }
    
    public AgeFileFilter(final Date date, final boolean b) {
        this(date.getTime(), b);
    }
    
    public AgeFileFilter(final File file) {
        this(file, true);
    }
    
    public AgeFileFilter(final File file, final boolean b) {
        this(file.lastModified(), b);
    }
    
    @Override
    public boolean accept(final File file) {
        final boolean fileNewer = FileUtils.isFileNewer(file, this.cutoff);
        return this.acceptOlder ? (!fileNewer) : fileNewer;
    }
    
    @Override
    public String toString() {
        return super.toString() + "(" + (this.acceptOlder ? "<=" : ">") + this.cutoff + ")";
    }
}
