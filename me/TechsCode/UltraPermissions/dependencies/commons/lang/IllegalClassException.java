

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

public class IllegalClassException extends IllegalArgumentException
{
    private static final long serialVersionUID = 8063272569377254819L;
    
    public IllegalClassException(final Class clazz, final Object o) {
        super("Expected: " + safeGetClassName(clazz) + ", actual: " + ((o == null) ? "null" : o.getClass().getName()));
    }
    
    public IllegalClassException(final Class clazz, final Class clazz2) {
        super("Expected: " + safeGetClassName(clazz) + ", actual: " + safeGetClassName(clazz2));
    }
    
    public IllegalClassException(final String s) {
        super(s);
    }
    
    private static final String safeGetClassName(final Class clazz) {
        return (clazz == null) ? null : clazz.getName();
    }
}
