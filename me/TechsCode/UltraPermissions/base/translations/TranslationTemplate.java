

package me.TechsCode.EnderPermissions.base.translations;

import java.util.Set;
import java.util.HashMap;
import java.util.Map;

public class TranslationTemplate
{
    private String name;
    private String nativeLanguage;
    private Map<String, Map<String, String>> phrases;
    
    public TranslationTemplate(final String name, final String nativeLanguage) {
        this.name = name;
        this.nativeLanguage = nativeLanguage;
        this.phrases = new HashMap<String, Map<String, String>>();
    }
    
    public void clearPhrases() {
        this.phrases.clear();
    }
    
    public void addPhrases(final String s, final Map<String, String> map) {
        this.phrases.put(s, map);
    }
    
    public String getTranslation(final String str, final String s) {
        if (this.phrases.containsKey(s)) {
            final Map<String, String> map = this.phrases.get(s);
            if (map.containsKey(str)) {
                return map.get(str);
            }
        }
        if (!s.equals(this.nativeLanguage)) {
            return this.getTranslation(str, this.nativeLanguage);
        }
        try {
            throw new Exception("Could not find a translation for the phrase " + str + " in template " + this.name);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public int getPhraseAmount(final String s) {
        return this.phrases.containsKey(s) ? this.phrases.get(s).size() : 0;
    }
    
    public Set<String> getAvailableLanguages() {
        return this.phrases.keySet();
    }
    
    public String getNativeLanguage() {
        return this.nativeLanguage;
    }
}
