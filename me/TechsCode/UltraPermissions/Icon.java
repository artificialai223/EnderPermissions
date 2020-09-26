

package me.TechsCode.EnderPermissions;

public class Icon
{
    public static String OnlineIndicator(final EnderPermissions EnderPermissions, final String s) {
        return EnderPermissions.getNetworkManager().getServerFromName(s).isPresent() ? "Online" : "§cOffline";
    }
}
