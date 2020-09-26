

package me.TechsCode.EnderPermissions.base.loader;

import me.TechsCode.EnderPermissions.tpl.utils.ClasspathUtil;

public class LoaderUtil
{
    public static Class<?> getTechPluginClass(final Class<?> clazz, final ClassLoader classLoader) {
        for (final String s : ClasspathUtil.getResourceFileNames()) {
            if (s.contains(".class")) {
                final String replace = s.replace("/", ".").replace(".class", "");
                if (replace.startsWith("me.TechsCode")) {
                    try {
                        final Class<?> loadClass = classLoader.loadClass(replace);
                        if (clazz.isAssignableFrom(loadClass) && !loadClass.getName().equals(clazz.getName())) {
                            return loadClass;
                        }
                    }
                    catch (NoClassDefFoundError noClassDefFoundError) {}
                    catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
