

package me.TechsCode.EnderPermissions.dependencies.slf4j.spi;

import me.TechsCode.EnderPermissions.dependencies.slf4j.IMarkerFactory;

public interface MarkerFactoryBinder
{
    IMarkerFactory getMarkerFactory();
    
    String getMarkerFactoryClassStr();
}
