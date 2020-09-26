

package me.TechsCode.EnderPermissions.base.translations;

import java.util.regex.Pattern;

public class Translation
{
    private TranslationHolder holder;
    private String phraseName;
    private ExtractedColoredString defaultTranslation;
    
    public Translation(final TranslationHolder holder, final String phraseName, final String s) {
        this.holder = holder;
        this.phraseName = phraseName;
        this.defaultTranslation = ExtractedColoredString.get(s);
    }
    
    public String translate() {
        String str = this.holder.getTranslationManager().getTranslation(this.holder.getTemplateName(), this.phraseName);
        if (this.defaultTranslation.isStartingWithColor()) {
            str = "**" + str;
        }
        final String[] colors = this.defaultTranslation.getColors();
        for (int length = colors.length, i = 0; i < length; ++i) {
            str = str.replaceFirst(Pattern.quote("**"), colors[i]);
        }
        return str;
    }
    
    @Override
    public String toString() {
        return this.translate();
    }
    
    public TranslationOptions options() {
        return new TranslationOptions(this.translate());
    }
    
    public TranslationHolder getHolder() {
        return this.holder;
    }
    
    public String getPhraseName() {
        return this.phraseName;
    }
    
    public ExtractedColoredString getDefaultTranslation() {
        return this.defaultTranslation;
    }
}
