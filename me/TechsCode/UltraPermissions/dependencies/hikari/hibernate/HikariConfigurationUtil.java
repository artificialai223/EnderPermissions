

package me.TechsCode.EnderPermissions.dependencies.hikari.hibernate;

import java.util.Iterator;
import java.util.Properties;
import me.TechsCode.EnderPermissions.dependencies.hikari.HikariConfig;
import java.util.Map;

public class HikariConfigurationUtil
{
    public static final String CONFIG_PREFIX = "hibernate.hikari.";
    public static final String CONFIG_PREFIX_DATASOURCE = "hibernate.hikari.dataSource.";
    
    public static HikariConfig loadConfiguration(final Map map) {
        final Properties properties = new Properties();
        copyProperty("hibernate.connection.isolation", map, "transactionIsolation", properties);
        copyProperty("hibernate.connection.autocommit", map, "autoCommit", properties);
        copyProperty("hibernate.connection.driver_class", map, "driverClassName", properties);
        copyProperty("hibernate.connection.url", map, "jdbcUrl", properties);
        copyProperty("hibernate.connection.username", map, "username", properties);
        copyProperty("hibernate.connection.password", map, "password", properties);
        for (final String s : map.keySet()) {
            if (s.startsWith("hibernate.hikari.")) {
                properties.setProperty(s.substring("hibernate.hikari.".length()), (String)map.get(s));
            }
        }
        return new HikariConfig(properties);
    }
    
    private static void copyProperty(final String s, final Map map, final String key, final Properties properties) {
        if (map.containsKey(s)) {
            properties.setProperty(key, map.get(s));
        }
    }
}
