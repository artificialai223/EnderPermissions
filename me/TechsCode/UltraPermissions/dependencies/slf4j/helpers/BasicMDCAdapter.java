

package me.TechsCode.EnderPermissions.dependencies.slf4j.helpers;

import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import me.TechsCode.EnderPermissions.dependencies.slf4j.spi.MDCAdapter;

public class BasicMDCAdapter implements MDCAdapter
{
    private InheritableThreadLocal<Map<String, String>> inheritableThreadLocal;
    
    public BasicMDCAdapter() {
        this.inheritableThreadLocal = new InheritableThreadLocal<Map<String, String>>() {
            @Override
            protected Map<String, String> childValue(final Map<String, String> m) {
                if (m == null) {
                    return null;
                }
                return new HashMap<String, String>(m);
            }
        };
    }
    
    @Override
    public void put(final String s, final String s2) {
        if (s == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        Map<String, String> value = this.inheritableThreadLocal.get();
        if (value == null) {
            value = new HashMap<String, String>();
            this.inheritableThreadLocal.set(value);
        }
        value.put(s, s2);
    }
    
    @Override
    public String get(final String s) {
        final Map<String, String> map = this.inheritableThreadLocal.get();
        if (map != null && s != null) {
            return map.get(s);
        }
        return null;
    }
    
    @Override
    public void remove(final String s) {
        final Map map = this.inheritableThreadLocal.get();
        if (map != null) {
            map.remove(s);
        }
    }
    
    @Override
    public void clear() {
        final Map map = this.inheritableThreadLocal.get();
        if (map != null) {
            map.clear();
            this.inheritableThreadLocal.remove();
        }
    }
    
    public Set<String> getKeys() {
        final Map<String, String> map = this.inheritableThreadLocal.get();
        if (map != null) {
            return map.keySet();
        }
        return null;
    }
    
    @Override
    public Map<String, String> getCopyOfContextMap() {
        final Map<? extends String, ? extends String> m = this.inheritableThreadLocal.get();
        if (m != null) {
            return new HashMap<String, String>(m);
        }
        return null;
    }
    
    @Override
    public void setContextMap(final Map<String, String> m) {
        this.inheritableThreadLocal.set(new HashMap<String, String>(m));
    }
}
