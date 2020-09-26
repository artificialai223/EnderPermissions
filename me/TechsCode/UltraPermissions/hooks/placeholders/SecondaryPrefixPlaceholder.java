

package me.TechsCode.EnderPermissions.hooks.placeholders;

import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.storage.objects.User;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.hooks.UpermsPlaceholder;

public class SecondaryPrefixPlaceholder extends UpermsPlaceholder
{
    public SecondaryPrefixPlaceholder() {
        super("secondaryprefix", "Prefix of 2. Group");
    }
    
    @Override
    protected String replace(final Player player, final User user, final Group[] array) {
        if (array.length >= 2 && array[1].getPrefix().isPresent()) {
            return array[1].getPrefix().get();
        }
        return "";
    }
}
