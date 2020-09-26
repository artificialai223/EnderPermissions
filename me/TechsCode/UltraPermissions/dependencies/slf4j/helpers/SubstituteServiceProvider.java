

package me.TechsCode.EnderPermissions.dependencies.slf4j.helpers;

import me.TechsCode.EnderPermissions.dependencies.slf4j.ILoggerFactory;
import me.TechsCode.EnderPermissions.dependencies.slf4j.spi.MDCAdapter;
import me.TechsCode.EnderPermissions.dependencies.slf4j.IMarkerFactory;
import me.TechsCode.EnderPermissions.dependencies.slf4j.spi.SLF4JServiceProvider;

public class SubstituteServiceProvider implements SLF4JServiceProvider
{
    private SubstituteLoggerFactory loggerFactory;
    private IMarkerFactory markerFactory;
    private MDCAdapter mdcAdapter;
    
    public SubstituteServiceProvider() {
        this.loggerFactory = new SubstituteLoggerFactory();
        this.markerFactory = new BasicMarkerFactory();
        this.mdcAdapter = new BasicMDCAdapter();
    }
    
    @Override
    public ILoggerFactory getLoggerFactory() {
        return this.loggerFactory;
    }
    
    public SubstituteLoggerFactory getSubstituteLoggerFactory() {
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
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void initialize() {
    }
}
