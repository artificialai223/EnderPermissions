

package me.TechsCode.EnderPermissions.permissionDatabase;

import me.TechsCode.EnderPermissions.tpl.Tools;
import java.util.function.Consumer;
import java.util.Arrays;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.StringUtils;

public class PermissionInfo
{
    private final String permission;
    private final String plugin;
    private final String description;
    private final String[] commands;
    private final String source;
    private String[] placeholders;
    
    public PermissionInfo(final String permission, final String plugin, final String description, final String[] commands, final String source) {
        this.permission = permission;
        this.plugin = plugin;
        this.description = description;
        this.commands = commands;
        this.source = source;
        this.placeholders = StringUtils.substringsBetween(permission, "[", "]");
        this.placeholders = ((this.placeholders == null) ? new String[0] : this.placeholders);
    }
    
    public String getPermission() {
        return this.permission;
    }
    
    public String getPlugin() {
        return this.plugin;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String[] getCommands() {
        return this.commands;
    }
    
    public String getSource() {
        return this.source;
    }
    
    public boolean hasPlaceholders() {
        return this.placeholders.length != 0;
    }
    
    public String[] getPlaceholders() {
        return this.placeholders;
    }
    
    public String[] getLoreInfo() {
        final ArrayList<Object> list = new ArrayList<Object>();
        if (this.getCommands().length != 0) {
            list.add("§7Commands:");
            Arrays.stream(this.getCommands()).map(s -> "§7- §e/" + s.replace("/", "")).forEach(list::add);
            list.add("");
        }
        list.add("§7Description:");
        Arrays.stream(Tools.lineSplitter(this.getDescription(), 60)).map(str -> "  §e" + str).forEach(list::add);
        return list.toArray(new String[list.size()]);
    }
    
    public boolean isThisPermission(final String s) {
        if (s.equalsIgnoreCase(this.permission)) {
            return true;
        }
        if (this.hasPlaceholders()) {
            String s2 = this.permission;
            final String[] placeholders = this.placeholders;
            for (int length = placeholders.length, i = 0; i < length; ++i) {
                s2 = s2.replace("[" + placeholders[i] + "]", "$$$");
            }
            final String[] split = s2.split("[$$$]");
            for (int length2 = split.length, j = 0; j < length2; ++j) {
                if (!s.contains(split[j])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
