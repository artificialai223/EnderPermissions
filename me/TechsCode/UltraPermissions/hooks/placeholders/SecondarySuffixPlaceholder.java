

package me.TechsCode.EnderPermissions.hooks.placeholders;

import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.storage.objects.User;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.hooks.UpermsPlaceholder;

public class SecondarySuffixPlaceholder extends UpermsPlaceholder
{
    public SecondarySuffixPlaceholder() {
        super("secondarysuffix", "Suffix of 2. Group");
    }
    
    @Override
    protected String replace(final Player player, final User user, final Group[] array) {
        if (array.length >= 2 && array[1].getSuffix().isPresent()) {
            return array[1].getSuffix().get();
        }
        return "";
    }
}
