

package me.TechsCode.EnderPermissions.dependencies.commons.lang.text;

import java.util.Map;

public abstract class StrLookup
{
    private static final StrLookup NONE_LOOKUP;
    private static final StrLookup SYSTEM_PROPERTIES_LOOKUP;
    
    public static StrLookup noneLookup() {
        return StrLookup.NONE_LOOKUP;
    }
    
    public static StrLookup systemPropertiesLookup() {
        return StrLookup.SYSTEM_PROPERTIES_LOOKUP;
    }
    
    public static StrLookup mapLookup(final Map map) {
        return new MapStrLookup(map);
    }
    
    protected StrLookup() {
    }
    
    public abstract String lookup(final String p0);
    
    static {
        NONE_LOOKUP = new MapStrLookup(null);
        StrLookup none_LOOKUP;
        try {
            none_LOOKUP = new MapStrLookup(System.getProperties());
        }
        catch (SecurityException ex) {
            none_LOOKUP = StrLookup.NONE_LOOKUP;
        }
        SYSTEM_PROPERTIES_LOOKUP = none_LOOKUP;
    }
    
    static class MapStrLookup extends StrLookup
    {
        private final Map map;
        
        MapStrLookup(final Map map) {
            this.map = map;
        }
        
        public String lookup(final String s) {
            if (this.map == null) {
                return null;
            }
            final Object value = this.map.get(s);
            if (value == null) {
                return null;
            }
            return value.toString();
        }
    }
}
