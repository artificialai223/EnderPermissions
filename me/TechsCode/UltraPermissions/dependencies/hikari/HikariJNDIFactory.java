

package me.TechsCode.EnderPermissions.dependencies.hikari;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.Enumeration;
import java.util.Set;
import javax.naming.RefAddr;
import java.util.Properties;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.PropertyElf;
import javax.naming.NamingException;
import javax.naming.Reference;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;

public class HikariJNDIFactory implements ObjectFactory
{
    @Override
    public Object getObjectInstance(final Object o, final Name name, final Context context, final Hashtable<?, ?> hashtable) {
        if (o == null || !(o instanceof Reference)) {
            return null;
        }
        final Reference reference = (Reference)o;
        if (!"javax.sql.DataSource".equals(reference.getClassName())) {
            throw new NamingException(reference.getClassName() + " is not a valid class name/type for this JNDI factory.");
        }
        final Set<String> propertyNames = PropertyElf.getPropertyNames(HikariConfig.class);
        final Properties properties = new Properties();
        final Enumeration<RefAddr> all = reference.getAll();
        while (all.hasMoreElements()) {
            final RefAddr refAddr = all.nextElement();
            final String type = refAddr.getType();
            if (type.startsWith("dataSource.") || propertyNames.contains(type)) {
                properties.setProperty(type, refAddr.getContent().toString());
            }
        }
        return this.createDataSource(properties, context);
    }
    
    private DataSource createDataSource(final Properties properties, final Context context) {
        if (properties.getProperty("dataSourceJNDI") != null) {
            return this.lookupJndiDataSource(properties, context);
        }
        return new HikariDataSource(new HikariConfig(properties));
    }
    
    private DataSource lookupJndiDataSource(final Properties properties, final Context context) {
        if (context == null) {
            throw new RuntimeException("dataSourceJNDI property is configured, but local JNDI context is null.");
        }
        final String property = properties.getProperty("dataSourceJNDI");
        DataSource dataSource = (DataSource)context.lookup(property);
        if (dataSource == null) {
            final InitialContext initialContext = new InitialContext();
            dataSource = (DataSource)initialContext.lookup(property);
            initialContext.close();
        }
        if (dataSource != null) {
            final HikariConfig hikariConfig = new HikariConfig(properties);
            hikariConfig.setDataSource(dataSource);
            return new HikariDataSource(hikariConfig);
        }
        return null;
    }
}
