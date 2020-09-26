

package me.TechsCode.EnderPermissions.internal.lookup.checks;

import org.bukkit.permissions.Permission;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.internal.lookup.LookupOutcome;
import org.bukkit.OfflinePlayer;
import me.TechsCode.EnderPermissions.internal.lookup.LookupCheck;

public class DefaultPermissionCheck implements LookupCheck
{
    private OfflinePlayer offlinePlayer;
    
    public DefaultPermissionCheck(final OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }
    
    @Override
    public LookupOutcome perform(final String s) {
        final Permission permission = Bukkit.getPluginManager().getPermission(s);
        if (permission != null && permission.getDefault().getValue(this.offlinePlayer.isOp())) {
            return new LookupOutcome(true, "Default Permission");
        }
        return null;
    }
}
