

package me.TechsCode.EnderPermissions;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Arrays;
import java.util.List;
import me.TechsCode.EnderPermissions.base.translations.Translation;
import me.TechsCode.EnderPermissions.base.translations.TranslationManager;
import me.TechsCode.EnderPermissions.base.translations.TranslationHolder;

public enum T implements TranslationHolder
{
    OVERVIEW("Overview"), 
    PERMISSION("Permission"), 
    POSITIVE("Positive"), 
    NEGATIVE("Negative"), 
    MIXED("Mixed"), 
    BUNGEE_PERMISSION("Bungee Permission"), 
    PERMISSION_NODES("Permission Nodes"), 
    PERMISSIONS("Permissions"), 
    DEFAULT_PERMISSIONS("Default Permissions"), 
    GROUP("Group"), 
    GROUPS("Groups"), 
    DEFAULT_GROUP("Default Group"), 
    INHERITED_GROUPS("Inherited Groups"), 
    PLAYER("Player"), 
    SERVER("Server"), 
    SELECTED("Selected"), 
    WORLD("World"), 
    USERS("Users"), 
    USER("User"), 
    TIME("Time"), 
    PREFIX("Prefix"), 
    SUFFIX("Suffix"), 
    OPTIONS("Options"), 
    SERVERNAME("Server Name"), 
    DONATIONS("Donations"), 
    ADVANTAGES("Advantages"), 
    RECOMMENDED("Recommended"), 
    SETTINGS("Settings"), 
    PERMISSIONLOG("Permission Log"), 
    EDITING("Editing"), 
    ADD("Add"), 
    TIME_FOR("for"), 
    NONE("None"), 
    DURATION("Duration"), 
    COPY("Copy"), 
    PASSED("Passed"), 
    FAILED("Failed"), 
    SOURCE("Source"), 
    TIME_TILL_REMOVAL("Time till removal:"), 
    COMMAND_INVALID_ARGS("§cInvalid Arguments"), 
    COMMAND_NOTFOUND("§cCommand not found"), 
    COMMAND_HINT_HELP("§7Type in §e/upc§7 to get a list of commands"), 
    COMMAND_HELP_TITLE("§aUltra Permissions Commands"), 
    COMMAND_HELP_FOOTER("§7Visit §b%1§7 for more info"), 
    COMMAND_GROUP_NOTFOUND("§7Cannot find the group §e%1§7"), 
    COMMAND_PLAYER_NOTFOUND("§7Cannot find the player §e%1§7"), 
    COMMAND_PLAYER_NOTSUPERADMIN("§7§e%1§7 is not a superadmin"), 
    COMMAND_PLAYER_INGROUP("§7The player §e%1§7 is already in this group"), 
    COMMAND_PLAYER_SUPERADMIN_ALREADY("§7§e%1§7 is already a superadmin"), 
    COMMAND_PLAYER_SUPERADMIN_ADD("§7Successfully added §e%1§7 as a superadmin"), 
    COMMAND_PLAYER_SUPERADMIN_REMOVE("§7Successfully removed §e%1§7 from the superadmins"), 
    COMMAND_PLAYER_ADD_GROUP("§7Added Group §e%1§7 to §b%2§7"), 
    COMMAND_PLAYER_ADD_GROUP_TIMED("§7Added Group §e%1§7 to §b%2§7 for §c%3§7"), 
    COMMAND_PERMISSION_ADD("§7Added permission §a%1§7 to §b%2§7"), 
    GUI_OVERVIEW_USERS_ACTION("§7Click to view all §aUsers§7"), 
    GUI_OVERVIEW_GROUPS_ACTION("§7Click to view all §aGroups§7"), 
    GUI_OVERVIEW_DONATIONS_ACTION("Click to show Donations"), 
    GUI_OVERVIEW_REDUCE_TITLE("Reduce"), 
    GUI_OVERVIEW_REDUCE_ACTION("§7Click to hide advanced features"), 
    GUI_OVERVIEW_REDUCE_EXPLANATION("§7This will hide the advanced options"), 
    GUI_OVERVIEW_EXPAND_TITLE("Expand"), 
    GUI_OVERVIEW_EXPAND_ACTION("§7§bClick§7 to show more"), 
    GUI_OVERVIEW_EXPAND_EXPLANATION("§7This will show you more advanced options"), 
    GUI_OVERVIEW_PERMISSIONLOG_ACTION("§7§bClick§7 to open the Permission Log"), 
    GUI_OVERVIEW_PERMISSIONLOG_EXPLANATION("§7Here you can take a look at all recent permission checks"), 
    GUI_OVERVIEW_SETTINGS_ACTION("§7§bClick§7 to view Settings"), 
    GUI_OVERVIEW_SETTINGS_EXPLANATION("§7Adjust all settings of the plugin"), 
    GUI_GROUPBROWSER_TITLE_LIST("List"), 
    GUI_GROUPBROWSER_TITLE_ALL("Showing all Groups"), 
    GUI_GROUPBROWSER_TITLE_RANGE("Showing %1 / %2 Groups"), 
    GUI_GROUPBROWSER_ADD_ACTION("§7Click to create group"), 
    GUI_GROUPBROWSER_REORDER_TITLE("Change Order"), 
    GUI_GROUPBROWSER_REORDER_ACTION("§7Click to modify the order"), 
    GUI_GROUPBROWSER_LEFTCLICK_ACTION("§7§bLeft Click§7 to open"), 
    GUI_GROUPBROWSER_Q_ACTION("§7§bPress Q§7 to view Storage Settings"), 
    GUI_GROUPBROWSER_DEFAULT_GROUP_INDICATOR("§aThis is a default group"), 
    GUI_GROUPBROWSER_SHOWALL_TITLE("Show all"), 
    GUI_GROUPBROWSER_SHOWALL_ACTION("Click to show all groups from all Servers & Worlds"), 
    GUI_USERBROWSER_ACTION("§7§bClick§7 to view"), 
    GUI_USERBROWSER_DELETE_ACTION("§7§bPress Q§7 to §cdelete§7"), 
    GUI_USERBROWSER_SUPERADMIN_INDICATOR("§aThis user is a superadmin"), 
    GUI_USERBROWSER_SHOWOFFLINE_TITLE("Show Offline Users"), 
    GUI_USERBROWSER_HIDEOFFLINE_TITLE("Hide Offline Users"), 
    GUI_USERBROWSER_SHOWOFFLINE_ACTION("§7Click to show §boffline users§7"), 
    GUI_USERBROWSER_HIDEOOFFLINE_ACTION("§7Click to hide §boffline users§7"), 
    GUI_USERVIEW_VIEWPERMISSIONS_TITLE("View Permissions"), 
    GUI_USERVIEW_VIEWPERMISSIONS_ACTION("§7§bClick§7 to view Permissions"), 
    GUI_USERVIEW_VIEWGROUPS_ACTION("§7§bClick§7 to view Groups"), 
    GUI_USERVIEW_EDITPREFIXSUFFIX_TITLE("Edit Prefix and Suffix"), 
    GUI_USERVIEW_EDITPREFIXSUFFIX_LEFTCLICK("§7§bLeft Click§7 to edit prefix"), 
    GUI_USERVIEW_EDITPREFIXSUFFIX_RIGHTCLICK("§7§bRight Click§7 to edit suffix"), 
    GUI_GROUPVIEW_VIEWPERMISSIONS_TITLE("View Permissions"), 
    GUI_GROUPVIEW_VIEWPERMISSIONS_ACTION("§7§bClick§7 to view Permissions"), 
    GUI_GROUPVIEW_CHANGEICON_TITLE("Change Icon"), 
    GUI_GROUPVIEW_CHANGEICON_ACTION("§7§bClick§7 to change"), 
    GUI_GROUPVIEW_EDITPREFIXSUFFIX_TITLE("Edit Prefix and Suffix"), 
    GUI_GROUPVIEW_EDITPREFIXSUFFIX_LEFTCLICK("§7§bLeft Click§7 to edit prefix"), 
    GUI_GROUPVIEW_EDITPREFIXSUFFIX_RIGHTCLICK("§7§bRight Click§7 to edit suffix"), 
    GUI_GROUPVIEW_RENAME_ACTION("§7§bClick§7 to rename group"), 
    GUI_GROUPVIEW_DELETE_TITLE("Delete Group"), 
    GUI_GROUPVIEW_DELETE_ACTION("§7§bClick§7 to §cdelete§7 this Group"), 
    GUI_GROUPVIEW_DELETE_PERMANENT("§7This action is §cpermanent§7"), 
    GUI_GROUPVIEW_DEFAULT_TITLE("Default Group"), 
    GUI_GROUPVIEW_DEFAULT_ACTION("§7§bClick§7 to make this group §edefault§7"), 
    GUI_GROUPVIEW_DEFAULT_UNDEFAULT_ACTION("§7§bClick§7 to §eundefault§7 this group"), 
    GUI_GROUPVIEW_DEFAULT_EXPLANATION("If enabled, every new player will receive this group on the first join"), 
    GUI_GROUPVIEW_INHERITANCE_TITLE("Inheritances"), 
    GUI_GROUPVIEW_INHERITANCE_ACTION("§7§bClick§7 to edit inheritances"), 
    GUI_PERMEDITOR_SELECTALL_TITLE("Select All"), 
    GUI_PERMEDITOR_SELECTALL_ACTION("§7Click to §dselect§7 all permissions"), 
    GUI_PERMEDITOR_INHERITED_NEGATE_ACTION("§7§bLeft Click§7 to §cnegate§7 permission"), 
    GUI_PERMEDITOR_INHERITED_JUMP_ACTION("§7§bRight Click§7 to jump to §e%1§7"), 
    GUI_PERMEDITOR_INHERITED_EXPLANATION("§7Permission added from §e%1§7"), 
    GUI_PERMEDITOR_SELECTED_ACTION("§7§bClick§7 to §dunselect§7"), 
    GUI_PERMEDITOR_SELECTED_EXPLANATION("You §dselected§7 this permission"), 
    GUI_PERMEDITOR_NORMAL_LEFTCLICK("§7§bLeft Click§7 to edit"), 
    GUI_PERMEDITOR_NORMAL_RIGHTCLICK("§7§bRight Click§7 to §dselect§7"), 
    GUI_PERMEDITOR_NORMAL_PRESSQ("§7§bPress Q§7 to delete"), 
    GUI_PERMEDITOR_ADDPERMISSION_TITLE("Add Permission"), 
    GUI_PERMEDITOR_PLUGIN_ACTION("§7Click to view Permissions of §e%1§7"), 
    GUI_PERMEDITOR_BUNGEEINDICATOR("§7This is a §cBungeeCord§7 Permission"), 
    GUI_PERMEDITOR_AVAILABILITY_ALLSERVERS("§7Available on §ball§7 Servers"), 
    GUI_PERMEDITOR_AVAILABILITY_INWORLD("§7in the World §b%1§7"), 
    GUI_PERMEDITOR_AVAILABILITY_APPLICABLEIN("§7Permission applicable in %1"), 
    GUI_PERMEDITOR_AVAILABILITY_ONLYTHISSERVER("§7Only available on §athis§7 Server"), 
    GUI_PERMEDITOR_AVAILABILITY_SPECIFICSERVER("§7Available on §f%1§7 Server"), 
    GUI_PERMEDITOR_ADD_LEFTACTION("§7§bLeft Click§7 to add Permission"), 
    GUI_PERMEDITOR_ADD_RIGHTACTION("§7§bRight Click§7 to add §cBungee Permission§7"), 
    GUI_PERMEDITOR_ADD_TYPEIN("Type in a permission"), 
    GUI_PERMEDITOR_EDIT_TITLE("Edit %1"), 
    GUI_PERMEDITOR_EDIT_ACTION("§7Click to edit §d%1§7"), 
    GUI_PERMVIEW_EDITPERM_TITLE("Edit Permission"), 
    GUI_PERMVIEW_EDITPERM_ACTION("§7Click to edit Permission"), 
    GUI_PERMVIEW_EDITPERM_TYPEIN("Type in the new permission"), 
    GUI_PERMVIEW_COPY_ACTION("§7Click to select a §atarget§7"), 
    GUI_PERMVIEW_NOMULTINAMEEDIT("you cannot rename multiple permissions at once"), 
    GUI_STORAGESETTINGS_TITLE("Storage Settings"), 
    GUI_STORAGESETTINGS_TYPEINWORLD("Type in a world name"), 
    GUI_STORAGESETTINGS_NOWORLD_ACTION("§7Click to §6restrict§7 this group to a specific World"), 
    GUI_STORAGESETTINGS_WORLD_ACTION("§7Click to allow this group in all Worlds"), 
    GUI_STORAGESETTINGS_NOSERVER_ACTION("§7Click to §6restrict §7this group to this Server"), 
    GUI_STORAGESETTINGS_SERVER_ACTION("§7Click to allow this group on all Servers"), 
    GUI_STORAGESETTINGS_RESTRICTED_TO("§7Restricted to §6%1§7"), 
    GUI_PASTESELECTION_PASTE_ACTION("§7Click to §bpaste§7 %1"), 
    GUI_PERMEDITOR_MANUAL_TITLE("Type in Chat"), 
    GUI_PERMEDITOR_MANUAL_ACTION("§7§bClick§7 to type in the Permission"), 
    GUI_PERMEDITOR_MANUAL_DIALOG_SUBTITLE("§7Type in a permission"), 
    GUI_INHERITVIEW_ADD_ACTION("§7Click to §eadd§7"), 
    GUI_INHERITVIEW_ADD_EXPLANATION("§7This will add all permissions from §e%1§7 to §b%2§7"), 
    GUI_INHERITVIEW_REMOVE_ACTION("§7Click to §cremove§7"), 
    GUI_INHERITVIEW_REMOVE_EXPLANATION("§7All Permissions from §e%1§7 will be added to §b%2§7"), 
    GUI_CHANGEORDER_TITLE("Edit Order"), 
    GUI_CHANGEORDER_INCREASE("§7§bRight Click§7 to increase"), 
    GUI_CHANGEORDER_DECREASE("§7§bLeft Click§7 to decrease"), 
    GUI_PERMISSIONLOG_ACTION("§7Click to add this permission to a §bgroup§7 or §buser§7"), 
    GUI_PERMISSIONLOG_RESULT("§7Result: %1"), 
    GUI_SETTINGS_FORMAT_NAME("Chat & Tablist Format"), 
    GUI_SETTINGS_MISC_NAME("Miscellaneous"), 
    EDITING_PREFIX_EDIT("§7§bEdit Prefix"), 
    EDITING_PREFIX_TYPEIN("§7Type in a new prefix"), 
    EDITING_PREFIX_TYPEINNONE("§cType in §cnone§7 to have no prefix"), 
    EDITING_SUFFIX_EDIT("§7§bEdit Suffix"), 
    EDITING_SUFFIX_TYPEIN("§7Type in a new suffix"), 
    EDITING_SUFFIX_TYPEINNONE("§7Type in §cnone§7 to have no suffix"), 
    CREATE_GROUP_TITLE("Create Group"), 
    CREATE_GROUP_SUBTITLE("Type in a group name"), 
    CREATE_GROUP_NOSPACES("§7The Group Name shouldnt contain spaces"), 
    CREATE_GROUP_NOCOLORS("§7You should not use chatcolors in the group name"), 
    CREATE_GROUP_NAMETAKEN("§7This group name is already in use"), 
    RENAME_GROUP_TITLE("Rename Group"), 
    RENAME_GROUP_SUBTITLE("§7Type in a new name"), 
    CONFIRM_DELETION_OF("Confirm deletion of %1");
    
    private final String defTranslation;
    private TranslationManager translationManager;
    
    public static T permission(final int n) {
        return (n == 1) ? T.PERMISSION : T.PERMISSIONS;
    }
    
    private T(final String defTranslation) {
        this.translationManager = null;
        this.defTranslation = defTranslation;
    }
    
    @Override
    public String getTemplateName() {
        return "EnderPermissions";
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
        return Arrays.stream(values()).map((Function<? super T, ?>)T::get).collect((Collector<? super Object, ?, List<Translation>>)Collectors.toList());
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
