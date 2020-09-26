

package me.TechsCode.EnderPermissions.dependencies.slf4j.nop;

import java.util.Map;
import me.TechsCode.EnderPermissions.dependencies.slf4j.spi.MDCAdapter;

public class NOPMDCAdapter implements MDCAdapter
{
    @Override
    public void clear() {
    }
    
    @Override
    public String get(final String s) {
        return null;
    }
    
    @Override
    public void put(final String s, final String s2) {
    }
    
    @Override
    public void remove(final String s) {
    }
    
    @Override
    public Map<String, String> getCopyOfContextMap() {
        return null;
    }
    
    @Override
    public void setContextMap(final Map<String, String> map) {
    }
}
