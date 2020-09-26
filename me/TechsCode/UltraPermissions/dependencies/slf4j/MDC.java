

package me.TechsCode.EnderPermissions.dependencies.slf4j;

import java.io.Closeable;
import me.TechsCode.EnderPermissions.dependencies.slf4j.spi.SLF4JServiceProvider;
import me.TechsCode.EnderPermissions.dependencies.slf4j.helpers.NOPMDCAdapter;
import me.TechsCode.EnderPermissions.dependencies.slf4j.helpers.Util;
import java.util.Map;
import me.TechsCode.EnderPermissions.dependencies.slf4j.spi.MDCAdapter;

public class MDC
{
    static final String NULL_MDCA_URL = "http://www.slf4j.org/codes.html#null_MDCA";
    static final String NO_STATIC_MDC_BINDER_URL = "http://www.slf4j.org/codes.html#no_static_mdc_binder";
    static MDCAdapter mdcAdapter;
    
    private MDC() {
    }
    
    public static void put(final String s, final String s2) {
        if (s == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }
        if (MDC.mdcAdapter == null) {
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        MDC.mdcAdapter.put(s, s2);
    }
    
    public static MDCCloseable putCloseable(final String s, final String s2) {
        put(s, s2);
        return new MDCCloseable(s);
    }
    
    public static String get(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }
        if (MDC.mdcAdapter == null) {
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        return MDC.mdcAdapter.get(s);
    }
    
    public static void remove(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }
        if (MDC.mdcAdapter == null) {
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        MDC.mdcAdapter.remove(s);
    }
    
    public static void clear() {
        if (MDC.mdcAdapter == null) {
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        MDC.mdcAdapter.clear();
    }
    
    public static Map<String, String> getCopyOfContextMap() {
        if (MDC.mdcAdapter == null) {
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        return MDC.mdcAdapter.getCopyOfContextMap();
    }
    
    public static void setContextMap(final Map<String, String> contextMap) {
        if (MDC.mdcAdapter == null) {
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        MDC.mdcAdapter.setContextMap(contextMap);
    }
    
    public static MDCAdapter getMDCAdapter() {
        return MDC.mdcAdapter;
    }
    
    static {
        final SLF4JServiceProvider provider = LoggerFactory.getProvider();
        if (provider != null) {
            MDC.mdcAdapter = provider.getMDCAdapter();
        }
        else {
            Util.report("Failed to find provider.");
            Util.report("Defaulting to no-operation MDCAdapter implementation.");
            MDC.mdcAdapter = new NOPMDCAdapter();
        }
    }
    
    public static class MDCCloseable implements Closeable
    {
        private final String key;
        
        private MDCCloseable(final String key) {
            this.key = key;
        }
        
        @Override
        public void close() {
            MDC.remove(this.key);
        }
    }
}
