

package me.TechsCode.EnderPermissions.dependencies.slf4j;

import me.TechsCode.EnderPermissions.dependencies.slf4j.spi.SLF4JServiceProvider;
import me.TechsCode.EnderPermissions.dependencies.slf4j.helpers.BasicMarkerFactory;
import me.TechsCode.EnderPermissions.dependencies.slf4j.helpers.Util;

public class MarkerFactory
{
    static IMarkerFactory MARKER_FACTORY;
    
    private MarkerFactory() {
    }
    
    public static Marker getMarker(final String s) {
        return MarkerFactory.MARKER_FACTORY.getMarker(s);
    }
    
    public static Marker getDetachedMarker(final String s) {
        return MarkerFactory.MARKER_FACTORY.getDetachedMarker(s);
    }
    
    public static IMarkerFactory getIMarkerFactory() {
        return MarkerFactory.MARKER_FACTORY;
    }
    
    static {
        final SLF4JServiceProvider provider = LoggerFactory.getProvider();
        if (provider != null) {
            MarkerFactory.MARKER_FACTORY = provider.getMarkerFactory();
        }
        else {
            Util.report("Failed to find provider");
            Util.report("Defaulting to BasicMarkerFactory.");
            MarkerFactory.MARKER_FACTORY = new BasicMarkerFactory();
        }
    }
}
