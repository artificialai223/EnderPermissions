

package me.TechsCode.EnderPermissions.dependencies.hikari.util;

import me.TechsCode.EnderPermissions.dependencies.slf4j.LoggerFactory;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.Enumeration;
import me.TechsCode.EnderPermissions.dependencies.hikari.HikariConfig;
import java.util.Properties;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;

public final class PropertyElf
{
    private static final Logger LOGGER;
    
    public static void setTargetFromProperties(final Object o, final Properties properties) {
        if (o == null || properties == null) {
            return;
        }
        final Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            final Object nextElement = propertyNames.nextElement();
            final String string = nextElement.toString();
            Object o2 = properties.getProperty(string);
            if (o2 == null) {
                o2 = properties.get(nextElement);
            }
            if (o instanceof HikariConfig && string.startsWith("dataSource.")) {
                ((HikariConfig)o).addDataSourceProperty(string.substring("dataSource.".length()), o2);
            }
            else {
                setProperty(o, string, o2);
            }
        }
    }
    
    public static Set<String> getPropertyNames(final Class<?> clazz) {
        final HashSet<String> set = new HashSet<String>();
        for (final Method method : clazz.getMethods()) {
            final String name = method.getName();
            if (name.matches("(get|is)[A-Z].+") && method.getParameterTypes().length == 0) {
                final String replaceFirst = name.replaceFirst("(get|is)", "");
                try {
                    if (clazz.getMethod("set" + replaceFirst, method.getReturnType()) != null) {
                        set.add(Character.toLowerCase(replaceFirst.charAt(0)) + replaceFirst.substring(1));
                    }
                }
                catch (Exception ex) {}
            }
        }
        return set;
    }
    
    public static Object getProperty(final String s, final Object o) {
        try {
            return o.getClass().getMethod("get" + s.substring(0, 1).toUpperCase() + s.substring(1), (Class<?>[])new Class[0]).invoke(o, new Object[0]);
        }
        catch (Exception ex) {
            try {
                return o.getClass().getMethod("is" + s.substring(0, 1).toUpperCase() + s.substring(1), (Class<?>[])new Class[0]).invoke(o, new Object[0]);
            }
            catch (Exception ex2) {
                return null;
            }
        }
    }
    
    public static Properties copyProperties(final Properties properties) {
        final Properties properties2 = new Properties();
        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            properties2.setProperty(entry.getKey().toString(), entry.getValue().toString());
        }
        return properties2;
    }
    
    private static void setProperty(final Object obj, final String s, final Object o) {
        Method method = null;
        final String string = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);
        final List<Method> list = Arrays.asList(obj.getClass().getMethods());
        for (final Method method2 : list) {
            if (method2.getName().equals(string) && method2.getParameterTypes().length == 1) {
                method = method2;
                break;
            }
        }
        if (method == null) {
            final String string2 = "set" + s.toUpperCase();
            for (final Method method3 : list) {
                if (method3.getName().equals(string2) && method3.getParameterTypes().length == 1) {
                    method = method3;
                    break;
                }
            }
        }
        if (method == null) {
            PropertyElf.LOGGER.error("Property {} does not exist on target {}", s, obj.getClass());
            throw new RuntimeException(String.format("Property %s does not exist on target %s", s, obj.getClass()));
        }
        try {
            final Class<?> clazz = method.getParameterTypes()[0];
            if (clazz == Integer.TYPE) {
                method.invoke(obj, Integer.parseInt(o.toString()));
            }
            else if (clazz == Long.TYPE) {
                method.invoke(obj, Long.parseLong(o.toString()));
            }
            else if (clazz == Boolean.TYPE || clazz == Boolean.class) {
                method.invoke(obj, Boolean.parseBoolean(o.toString()));
            }
            else if (clazz == String.class) {
                method.invoke(obj, o.toString());
            }
            else {
                method.invoke(obj, o);
            }
        }
        catch (Exception cause) {
            PropertyElf.LOGGER.error("Exception setting property {} on target {}", s, obj.getClass(), cause);
            throw new RuntimeException(cause);
        }
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(PropertyElf.class);
    }
}
