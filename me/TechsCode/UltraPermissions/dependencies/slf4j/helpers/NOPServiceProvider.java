

package me.TechsCode.EnderPermissions.dependencies.slf4j.helpers;

import me.TechsCode.EnderPermissions.dependencies.slf4j.spi.MDCAdapter;
import me.TechsCode.EnderPermissions.dependencies.slf4j.IMarkerFactory;
import me.TechsCode.EnderPermissions.dependencies.slf4j.ILoggerFactory;
import me.TechsCode.EnderPermissions.dependencies.slf4j.spi.SLF4JServiceProvider;

public class NOPServiceProvider implements SLF4JServiceProvider
{
    public static String REQUESTED_API_VERSION;
    private ILoggerFactory loggerFactory;
    private IMarkerFactory markerFactory;
    private MDCAdapter mdcAdapter;
    
    public NOPServiceProvider() {
        this.loggerFactory = new NOPLoggerFactory();
        this.markerFactory = new BasicMarkerFactory();
        this.mdcAdapter = new NOPMDCAdapter();
    }
    
    @Override
    public ILoggerFactory getLoggerFactory() {
        return this.loggerFactory;
    }
    
    @Override
    public IMarkerFactory getMarkerFactory() {
        return this.markerFactory;
    }
    
    @Override
    public MDCAdapter getMDCAdapter() {
        return this.mdcAdapter;
    }
    
    @Override
    public String getRequesteApiVersion() {
        return NOPServiceProvider.REQUESTED_API_VERSION;
    }
    
    @Override
    public void initialize() {
    }
    
    static {
        NOPServiceProvider.REQUESTED_API_VERSION = "1.8.99";
    }
}
