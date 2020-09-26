

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.lang.reflect.Proxy;
import java.io.ObjectStreamClass;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ClassLoaderObjectInputStream extends ObjectInputStream
{
    private final ClassLoader classLoader;
    
    public ClassLoaderObjectInputStream(final ClassLoader classLoader, final InputStream in) {
        super(in);
        this.classLoader = classLoader;
    }
    
    @Override
    protected Class<?> resolveClass(final ObjectStreamClass desc) {
        try {
            return Class.forName(desc.getName(), false, this.classLoader);
        }
        catch (ClassNotFoundException ex) {
            return super.resolveClass(desc);
        }
    }
    
    @Override
    protected Class<?> resolveProxyClass(final String[] interfaces) {
        final Class[] interfaces2 = new Class[interfaces.length];
        for (int i = 0; i < interfaces.length; ++i) {
            interfaces2[i] = Class.forName(interfaces[i], false, this.classLoader);
        }
        try {
            return Proxy.getProxyClass(this.classLoader, (Class<?>[])interfaces2);
        }
        catch (IllegalArgumentException ex) {
            return super.resolveProxyClass(interfaces);
        }
    }
}
