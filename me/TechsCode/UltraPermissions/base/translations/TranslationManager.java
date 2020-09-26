

package me.TechsCode.EnderPermissions.base.translations;

import java.util.stream.Stream;
import java.util.List;
import java.util.Arrays;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.ArrayUtils;
import me.TechsCode.EnderPermissions.tpl.utils.ClassInstanceCreator;
import java.io.IOException;
import me.TechsCode.EnderPermissions.dependencies.commons.io.FileUtils;
import java.nio.charset.Charset;
import java.util.Collection;
import me.TechsCode.EnderPermissions.base.registry.RegistrationChoice;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import me.TechsCode.EnderPermissions.base.TechPlugin;

public class TranslationManager
{
    private final String SEPERATOR = ": ";
    private TechPlugin plugin;
    private File folder;
    private Map<String, TranslationTemplate> templates;
    private TranslationRegistry translationRegistry;
    
    public TranslationManager(final TechPlugin plugin) {
        this.plugin = plugin;
        (this.folder = new File(plugin.getPluginFolder().getAbsolutePath() + "/Languages")).mkdirs();
        this.templates = new HashMap<String, TranslationTemplate>();
        this.registerTranslationHolder(TBase.class);
        this.translationRegistry = plugin.getRegistry().register(new TranslationRegistry(this.templates.entrySet().iterator().next().getValue().getNativeLanguage()), RegistrationChoice.GLOBAL_IF_AVAILABLE);
    }
    
    public String getTranslation(final String s, final String s2) {
        return this.templates.get(s).getTranslation(s2, this.getSelectedLanguage());
    }
    
    public String getSelectedLanguage() {
        return this.translationRegistry.getLanguage();
    }
    
    public void setSelectedLanguage(final String language) {
        this.translationRegistry.setLanguage(language);
    }
    
    public String[] getAvailableLanguages() {
        return this.templates.values().stream().flatMap(translationTemplate -> translationTemplate.getAvailableLanguages().stream()).distinct().sorted().toArray(String[]::new);
    }
    
    public int getPhraseAmount(final String s) {
        return this.templates.values().stream().mapToInt(translationTemplate -> translationTemplate.getPhraseAmount(s)).sum();
    }
    
    public Collection<String> getTemplateNames() {
        return this.templates.keySet();
    }
    
    private void loadTranslationFiles() {
        this.templates.values().stream().forEach(translationTemplate -> translationTemplate.clearPhrases());
        for (final File file : this.folder.listFiles()) {
            if (file.getName().contains("_")) {
                if (file.getName().endsWith(".lang")) {
                    final String replace = file.getName().replace(".lang", "");
                    final String s4 = replace.split("_")[0];
                    final String s5 = replace.split("_")[1];
                    if (this.templates.containsKey(s4)) {
                        final TranslationTemplate translationTemplate2 = this.templates.get(s4);
                        try {
                            final HashMap<String, String> hashMap = new HashMap<String, String>();
                            final Map<String, String> map;
                            final String[] array;
                            FileUtils.readLines(file, Charset.forName("UTF-8")).stream().filter(s -> !s.startsWith("#")).filter(s2 -> s2.contains(": ")).forEach(s3 -> {
                                s3.split(": ");
                                map.put(array[0], array[1]);
                                return;
                            });
                            translationTemplate2.addPhrases(s5, hashMap);
                        }
                        catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    
    public void reloadTranslationFiles() {
        this.loadTranslationFiles();
    }
    
    public void registerTranslationHolder(final Class<? extends TranslationHolder> clazz) {
        final TranslationHolder translationHolder = (TranslationHolder)ClassInstanceCreator.create(clazz);
        final String templateName = translationHolder.getTemplateName();
        final String defaultTemplateLanguage = translationHolder.getDefaultTemplateLanguage();
        final List<Translation> translations = translationHolder.getTranslations();
        translations.stream().forEach(translation -> translation.getHolder().setTranslationManager(this));
        final File file = new File(this.folder.getAbsolutePath() + "/" + templateName + "_" + defaultTemplateLanguage + ".lang");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileUtils.writeLines(file, Arrays.asList(ArrayUtils.addAll(new String[] { "#", "# Official Language File", "# To make your own language file, copy this file and edit the copy", "#", "# Changes made to this file will be reset!", "# Do not edit it!", "#", "" }, translations.stream().map(translation2 -> translation2.getPhraseName() + ": " + translation2.getDefaultTranslation().getColorlessString()).toArray(String[]::new))));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        this.templates.put(templateName, new TranslationTemplate(templateName, defaultTemplateLanguage));
        this.loadTranslationFiles();
    }
    
    public File getFolder() {
        return this.folder;
    }
}
