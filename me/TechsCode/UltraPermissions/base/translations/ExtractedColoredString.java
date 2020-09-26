

package me.TechsCode.EnderPermissions.base.translations;

import java.util.ArrayList;
import java.util.HashMap;

public class ExtractedColoredString
{
    private static HashMap<String, ExtractedColoredString> cache;
    private String originalString;
    private boolean startsWithColor;
    private String colorlessString;
    private String[] colors;
    
    public static ExtractedColoredString get(final String key) {
        if (ExtractedColoredString.cache.containsKey(key)) {
            return ExtractedColoredString.cache.get(key);
        }
        final ExtractedColoredString extract = extract(key);
        ExtractedColoredString.cache.put(key, extract);
        return extract;
    }
    
    public static ExtractedColoredString extract(final String s) {
        final ArrayList<String> list = new ArrayList<String>();
        final char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length - 1; ++i) {
            if ((charArray[i] == '&' || charArray[i] == '§') && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(charArray[i + 1]) > -1) {
                list.add(charArray[i] + "" + charArray[i + 1]);
                charArray[i + 1] = (charArray[i] = '*');
            }
        }
        String substring = new String(charArray);
        final boolean startsWith = substring.startsWith("**");
        if (startsWith) {
            substring = substring.substring(2);
        }
        return new ExtractedColoredString(s, startsWith, substring, list.toArray(new String[0]));
    }
    
    private ExtractedColoredString(final String originalString, final boolean startsWithColor, final String colorlessString, final String[] colors) {
        this.originalString = originalString;
        this.startsWithColor = startsWithColor;
        this.colorlessString = colorlessString;
        this.colors = colors;
    }
    
    public String getOriginalString() {
        return this.originalString;
    }
    
    public boolean isStartingWithColor() {
        return this.startsWithColor;
    }
    
    public String getColorlessString() {
        return this.colorlessString;
    }
    
    public String[] getColors() {
        return this.colors;
    }
    
    static {
        ExtractedColoredString.cache = new HashMap<String, ExtractedColoredString>();
    }
}
