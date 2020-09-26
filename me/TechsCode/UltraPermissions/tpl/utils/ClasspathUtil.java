

package me.TechsCode.EnderPermissions.tpl.utils;

import java.util.zip.ZipEntry;
import java.security.CodeSource;
import java.io.IOException;
import java.util.zip.ZipInputStream;
import java.util.ArrayList;

public class ClasspathUtil
{
    public static String[] getResourceFileNames() {
        final ArrayList<String> list = new ArrayList<String>();
        try {
            final CodeSource codeSource = ClasspathUtil.class.getProtectionDomain().getCodeSource();
            if (codeSource != null) {
                final ZipInputStream zipInputStream = new ZipInputStream(codeSource.getLocation().openStream());
                while (true) {
                    final ZipEntry nextEntry = zipInputStream.getNextEntry();
                    if (nextEntry == null) {
                        break;
                    }
                    list.add(nextEntry.getName());
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return list.toArray(new String[0]);
    }
}
