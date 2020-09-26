

package me.TechsCode.EnderPermissions.gui.permissionEditor;

import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.base.gui.SearchFeature;
import java.util.Comparator;
import me.TechsCode.EnderPermissions.permissionDatabase.PermissionInfo;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import org.bukkit.plugin.Plugin;
import java.util.Arrays;
import org.bukkit.Bukkit;
import java.util.List;
import me.TechsCode.EnderPermissions.storage.objects.PermissionHolder;
import me.TechsCode.EnderPermissions.EnderPermissions;
import org.bukkit.entity.Player;

public abstract class SearchPermissionListView extends PermissionListView
{
    private final String searchTerm;
    private final PermissionWithInfo[] permissions;
    
    public SearchPermissionListView(final Player player, final EnderPermissions EnderPermissions, final PermissionHolder permissionHolder, final String searchTerm) {
        super(player, EnderPermissions, permissionHolder);
        this.searchTerm = searchTerm;
        this.permissions = EnderPermissions.getPermissionDatabase().getAllPermissionInfos().stream().filter(permissionInfo2 -> Arrays.stream(Bukkit.getPluginManager().getPlugins()).map((Function<? super Plugin, ?>)Plugin::getName).map((Function<? super Object, ?>)String::toLowerCase).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).contains(permissionInfo2.getPlugin().toLowerCase()) || permissionInfo2.getPlugin().toLowerCase().equals("bukkit")).filter(permissionInfo3 -> permissionInfo3.getPermission().toLowerCase().contains(searchTerm.toLowerCase())).sorted(Comparator.comparing((Function<? super PermissionInfo, ? extends Comparable>)PermissionInfo::getPermission)).map(permissionInfo -> new PermissionWithInfo(permissionInfo.getPermission(), permissionInfo)).toArray(PermissionWithInfo[]::new);
    }
    
    @Override
    public String getTitle() {
        return "Permissions with '" + this.searchTerm + "'";
    }
    
    @Override
    public PermissionWithInfo[] getObjects() {
        return this.permissions;
    }
    
    @Override
    public SearchFeature<PermissionWithInfo> getSearch() {
        return null;
    }
}
