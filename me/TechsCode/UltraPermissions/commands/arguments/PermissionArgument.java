

package me.TechsCode.EnderPermissions.commands.arguments;

import me.TechsCode.EnderPermissions.permissionDatabase.PermissionInfo;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import org.bukkit.plugin.Plugin;
import java.util.Arrays;
import org.bukkit.Bukkit;
import java.util.List;
import me.TechsCode.EnderPermissions.base.command.ArgumentValue;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.command.Argument;

public class PermissionArgument extends Argument<String>
{
    private EnderPermissions plugin;
    
    public PermissionArgument(final EnderPermissions plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public ArgumentValue<String> get(final String s) {
        return ArgumentValue.of(s, s);
    }
    
    @Override
    public String getUsage() {
        return "<Permission>";
    }
    
    @Override
    public List<String> getTabCompletions() {
        final List<? super Object> list = Arrays.stream(Bukkit.getPluginManager().getPlugins()).map((Function<? super Plugin, ?>)Plugin::getName).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
        list.add("Bukkit");
        return this.plugin.getPermissionDatabase().getAllPermissionInfos().plugins((List<String>)list).stream().map((Function<? super PermissionInfo, ?>)PermissionInfo::getPermission).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
}
