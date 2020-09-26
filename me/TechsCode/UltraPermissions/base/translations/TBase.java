

package me.TechsCode.EnderPermissions.base.translations;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Arrays;
import java.util.List;

public enum TBase implements TranslationHolder
{
    ENABLE("enable"), 
    ENABLED("enabled"), 
    DISABLE("disable"), 
    DISABLED("disabled"), 
    SETTINGS("Settings"), 
    DAY("Day"), 
    DAYS("Days"), 
    HOUR("Hour"), 
    HOURS("Hours"), 
    MINUTE("Minute"), 
    MINUTES("Minutes"), 
    SECOND("Second"), 
    SECONDS("Seconds"), 
    NONE("None"), 
    COMMAND_NOPERMISSION("§7You don't have §caccess§7 to that command"), 
    COMMAND_NOCONSOLE("§7This command cannot be run through the console"), 
    GUI_SETTINGS_SELECTPAGE("§7Click to show Settings"), 
    GUI_SETTINGS_PAGESELECTED("§7This page is currently §aselected§7"), 
    GUI_SETTINGS_LANGUAGE_NAME("Language"), 
    GUI_SETTINGS_MYSQL_NAME("MySQL Database"), 
    GUI_SETTINGS_MYSQL_EDITVALUE_ACTION("§7Click to edit this value"), 
    GUI_SETTINGS_MYSQL_CURRENTVALUE("§7Current Value:"), 
    GUI_SETTINGS_MYSQL_SETUP_TITLE_FIELDNAME("§bMySQL - %1"), 
    GUI_SETTINGS_MYSQL_SETUP_TITLE_TYPEINVALUE("§7Type in the value"), 
    GUI_SETTINGS_MYSQL_SETUP_TITLE("Setup MySQL"), 
    GUI_SETTINGS_MYSQL_SETUP_ACTION("§7Click to enable MySQL"), 
    GUI_SETTINGS_MYSQL_SETUP_INFO("This requires an external MySQL Database to connect to"), 
    GUI_SETTINGS_MYSQL_DISABLE_TITLE("Disable MySQL"), 
    GUI_SETTINGS_MYSQL_DISABLE_ACTION("§7Click to disable MySQL"), 
    GUI_SETTINGS_MYSQL_RESTARTREQUIRED("You will be asked to restart to apply the changes"), 
    GUI_SETTINGS_MYSQL_CANTCONNECT("§7Could not connect to your MySQL"), 
    GUI_SETTINGS_MYSQL_SAVE_TITLE("Test & Save"), 
    GUI_SETTINGS_MYSQL_SAVE_ACTION("§7Click to §etest§7 and §asave§7"), 
    GUI_SETTINGS_LANG_ACTION("§7Click to §eselect §7this language"), 
    GUI_SETTINGS_LANG_SELECTED("§7This language is §ecurrently§7 selected"), 
    GUI_SETTINGS_LANG_PHRASES("§7Phrases"), 
    GUI_SETTINGS_LANG_COVERAGE("§7Coverage"), 
    GUI_PAGEABLE_SEARCH_TITLE("Search"), 
    GUI_PAGEABLE_SEARCH_ACTION("§7Click to search"), 
    GUI_PAGEABLE_NEXT_TITLE("Next"), 
    GUI_PAGEABLE_NEXT_ACTION("§7Click to go to the next page"), 
    GUI_PAGEABLE_PREVIOUS_TITLE("Previous"), 
    GUI_PAGEABLE_PREVIOUS_ACTION("§7Click to go to the previous page"), 
    GUI_PAGEABLE_SEARCH_CLOSE_ACTION("§7Click to §cclose§7 the search"), 
    GUI_PAGEABLE_SEARCH_TYPEIN("§7Type in a search term"), 
    GUI_BACK_NAME("Back"), 
    GUI_BACK_ACTION("Click to go back");
    
    private String defTranslation;
    private TranslationManager translationManager;
    
    public static String enableDisable(final boolean b, final boolean b2) {
        return (b2 ? (b ? "§a" : "§c") : "") + (b ? TBase.ENABLE : TBase.DISABLE).toString();
    }
    
    public static String enabledDisabled(final boolean b, final boolean b2) {
        return (b2 ? (b ? "§a" : "§c") : "") + (b ? TBase.ENABLED : TBase.DISABLED).toString();
    }
    
    public static String enableDisable(final boolean b) {
        return enableDisable(b, false);
    }
    
    public static String enabledDisabled(final boolean b) {
        return enabledDisabled(b, false);
    }
    
    private TBase(final String defTranslation) {
        this.defTranslation = defTranslation;
    }
    
    @Override
    public String getTemplateName() {
        return "Base";
    }
    
    @Override
    public String getDefaultTemplateLanguage() {
        return "English";
    }
    
    @Override
    public Translation get() {
        return new Translation(this, this.name(), this.defTranslation);
    }
    
    @Override
    public List<Translation> getTranslations() {
        return Arrays.stream(values()).map((Function<? super TBase, ?>)TBase::get).collect((Collector<? super Object, ?, List<Translation>>)Collectors.toList());
    }
    
    @Override
    public void setTranslationManager(final TranslationManager translationManager) {
        this.translationManager = translationManager;
    }
    
    @Override
    public TranslationManager getTranslationManager() {
        return this.translationManager;
    }
    
    @Override
    public String toString() {
        return this.get().toString();
    }
}
