

package me.TechsCode.EnderPermissions.base.translations;

import java.util.List;

public interface TranslationHolder
{
    String getTemplateName();
    
    String getDefaultTemplateLanguage();
    
    Translation get();
    
    List<Translation> getTranslations();
    
    void setTranslationManager(final TranslationManager p0);
    
    TranslationManager getTranslationManager();
}
