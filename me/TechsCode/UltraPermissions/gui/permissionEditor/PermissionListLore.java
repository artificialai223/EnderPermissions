

package me.TechsCode.EnderPermissions.gui.permissionEditor;

import java.util.Optional;
import me.TechsCode.EnderPermissions.PermissionColor;
import me.TechsCode.EnderPermissions.storage.objects.PermissionHolder;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import me.TechsCode.EnderPermissions.base.visual.LoreScroller;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.List;

public class PermissionListLore
{
    public static String[] get(final long n, final List<PermissionCopy> list) {
        final List<? super Object> list2 = list.stream().map((Function<? super Object, ?>)PermissionListLore::permissionListEntry).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
        if (list.size() > 12) {
            list2.add("");
        }
        final ArrayList list3 = new ArrayList<String>(Arrays.asList(LoreScroller.scroller(list2.toArray(new String[0]), 12, n)));
        if (list.size() > 12) {
            list3.add("");
            list3.add("§7> §f" + list.size() + " Total Permissions");
        }
        return list3.toArray(new String[0]);
    }
    
    private static String permissionListEntry(final PermissionCopy permissionCopy) {
        final StringBuilder sb = new StringBuilder(simpleListEntry(permissionCopy));
        if (permissionCopy.isInherited()) {
            sb.append(" §7from §e").append(permissionCopy.getPermission().getHolder().get().map((Function<? super PermissionHolder, ? extends String>)PermissionHolder::getName).orElse("INVALID"));
        }
        else {
            final Optional<String> server = permissionCopy.getPermission().getServer();
            if (server.isPresent()) {
                sb.append(" §8| ");
                if (server.get().equals("BungeeCord")) {
                    sb.append(PermissionColor.BUNGEE_SERVER.getChatColor()).append(server.get());
                }
                else {
                    sb.append("§7Server '").append(PermissionColor.SPECIFIC_SERVER.getChatColor()).append(server.get()).append("§7'");
                }
            }
            if (permissionCopy.getPermission().getWorld().isPresent()) {
                sb.append(" §8| §7World '§e").append(permissionCopy.getPermission().getWorld().get()).append("§7'");
            }
        }
        return sb.toString();
    }
    
    private static String simpleListEntry(final PermissionCopy permissionCopy) {
        final PermissionColor fromPermission = PermissionColor.fromPermission(permissionCopy);
        return "§7- " + (permissionCopy.getPermission().isPositive() ? fromPermission.getChatColor() : ("§c-" + fromPermission.getChatColor())) + permissionCopy.getPermission().getName() + "§7";
    }
}
