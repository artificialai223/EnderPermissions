

package me.TechsCode.EnderPermissions.dependencies.slf4j.helpers;

public final class Util
{
    private static ClassContextSecurityManager SECURITY_MANAGER;
    private static boolean SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED;
    
    private Util() {
    }
    
    public static String safeGetSystemProperty(final String key) {
        if (key == null) {
            throw new IllegalArgumentException("null input");
        }
        String property = null;
        try {
            property = System.getProperty(key);
        }
        catch (SecurityException ex) {}
        return property;
    }
    
    public static boolean safeGetBooleanSystemProperty(final String s) {
        final String safeGetSystemProperty = safeGetSystemProperty(s);
        return safeGetSystemProperty != null && safeGetSystemProperty.equalsIgnoreCase("true");
    }
    
    private static ClassContextSecurityManager getSecurityManager() {
        if (Util.SECURITY_MANAGER != null) {
            return Util.SECURITY_MANAGER;
        }
        if (Util.SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED) {
            return null;
        }
        Util.SECURITY_MANAGER = safeCreateSecurityManager();
        Util.SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED = true;
        return Util.SECURITY_MANAGER;
    }
    
    private static ClassContextSecurityManager safeCreateSecurityManager() {
        try {
            return new ClassContextSecurityManager();
        }
        catch (SecurityException ex) {
            return null;
        }
    }
    
    public static Class<?> getCallingClass() {
        final ClassContextSecurityManager securityManager = getSecurityManager();
        if (securityManager == null) {
            return null;
        }
        Class<?>[] classContext;
        String name;
        int n;
        for (classContext = securityManager.getClassContext(), name = Util.class.getName(), n = 0; n < classContext.length && !name.equals(classContext[n].getName()); ++n) {}
        if (n >= classContext.length || n + 2 >= classContext.length) {
            throw new IllegalStateException("Failed to find org.slf4j.helpers.Util or its caller in the stack; this should not happen");
        }
        return classContext[n + 2];
    }
    
    public static final void report(final String x, final Throwable t) {
        System.err.println(x);
        System.err.println("Reported exception:");
        t.printStackTrace();
    }
    
    public static final void report(final String str) {
        System.err.println("SLF4J: " + str);
    }
    
    static {
        Util.SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED = false;
    }
    
    private static final class ClassContextSecurityManager extends SecurityManager
    {
        @Override
        protected Class<?>[] getClassContext() {
            return super.getClassContext();
        }
    }
}
