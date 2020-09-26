

package me.TechsCode.EnderPermissions.base.translations;

public class TranslationMeta
{
    private String templateName;
    private String defaultLanguage;
    
    public TranslationMeta(final String templateName, final String defaultLanguage) {
        this.templateName = templateName;
        this.defaultLanguage = defaultLanguage;
    }
    
    public String getTemplateName() {
        return this.templateName;
    }
    
    public String getDefaultLanguage() {
        return this.defaultLanguage;
    }
}
