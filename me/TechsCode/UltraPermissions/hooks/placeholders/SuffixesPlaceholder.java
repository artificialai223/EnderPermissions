

package me.TechsCode.EnderPermissions.hooks.placeholders;

import java.util.ArrayList;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.storage.objects.User;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.hooks.UpermsPlaceholder;

public class SuffixesPlaceholder extends UpermsPlaceholder
{
    public SuffixesPlaceholder() {
        super("suffixes", "Replaced with all group suffixes");
    }
    
    @Override
    protected String replace(final Player player, final User user, final Group[] array) {
        final ArrayList<String> elements = new ArrayList<String>();
        if (user.getSuffix().isPresent()) {
            elements.add(user.getSuffix().get());
        }
        for (final Group group : array) {
            if (group.getSuffix().isPresent()) {
                elements.add(group.getSuffix().get());
            }
        }
        return String.join(" ", elements);
    }
}
