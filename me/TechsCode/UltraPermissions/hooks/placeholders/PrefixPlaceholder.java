

package me.TechsCode.EnderPermissions.hooks.placeholders;

import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.storage.objects.User;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.hooks.UpermsPlaceholder;

public class PrefixPlaceholder extends UpermsPlaceholder
{
    public PrefixPlaceholder() {
        super("prefix", "Player Prefix or Prefix of 1. Group");
    }
    
    @Override
    protected String replace(final Player player, final User user, final Group[] array) {
        if (user.getPrefix().isPresent()) {
            return user.getPrefix().get();
        }
        if (array.length != 0) {
            return array[0].getPrefix().orElse("");
        }
        return "";
    }
}
