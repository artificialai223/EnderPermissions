

package me.TechsCode.EnderPermissions.dependencies.commons.io.comparator;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.util.Comparator;

abstract class AbstractFileComparator implements Comparator<File>
{
    public File[] sort(final File... a) {
        if (a != null) {
            Arrays.sort(a, this);
        }
        return a;
    }
    
    public List<File> sort(final List<File> list) {
        if (list != null) {
            Collections.sort((List<Object>)list, (Comparator<? super Object>)this);
        }
        return list;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
