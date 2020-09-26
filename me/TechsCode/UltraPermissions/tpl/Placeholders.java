

package me.TechsCode.EnderPermissions.tpl;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class Placeholders extends HashMap<String, String>
{
    public String apply(final String s) {
        String replace = s;
        for (final Map.Entry<String, String> entry : this.entrySet()) {
            replace = replace.replace(entry.getKey(), entry.getValue());
        }
        return replace;
    }
    
    public static Placeholders c(final String key, final String value) {
        final Placeholders placeholders = new Placeholders();
        placeholders.put(key, value);
        return placeholders;
    }
}
