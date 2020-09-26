

package me.TechsCode.EnderPermissions.hooks.placeholders;

import java.util.ArrayList;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.storage.objects.User;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.hooks.UpermsPlaceholder;

public class PrefixesPlaceholder extends UpermsPlaceholder
{
    public PrefixesPlaceholder() {
        super("prefixes", "Replaced with player prefix & group prefixes");
    }
    
    @Override
    protected String replace(final Player player, final User user, final Group[] array) {
        final ArrayList<String> elements = new ArrayList<String>();
        if (user.getPrefix().isPresent()) {
            elements.add(user.getPrefix().get());
        }
        for (final Group group : array) {
            if (group.getPrefix().isPresent()) {
                elements.add(group.getPrefix().get());
            }
        }
        return String.join(" ", elements);
    }
}
