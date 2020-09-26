

package me.TechsCode.EnderPermissions.hooks.placeholders;

import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.storage.objects.User;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.hooks.UpermsPlaceholder;

public class RankPlaceholder extends UpermsPlaceholder
{
    public RankPlaceholder() {
        super("rank", "Group Name of 1. Group");
    }
    
    @Override
    protected String replace(final Player player, final User user, final Group[] array) {
        if (array.length == 0) {
            return "";
        }
        return array[0].getName();
    }
}
