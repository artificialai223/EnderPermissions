

package me.TechsCode.EnderPermissions.hooks.placeholders;

import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.storage.objects.User;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.hooks.UpermsPlaceholder;

public class SuffixPlaceholder extends UpermsPlaceholder
{
    public SuffixPlaceholder() {
        super("suffix", "Player Suffix or Suffix of 1. Group");
    }
    
    @Override
    protected String replace(final Player player, final User user, final Group[] array) {
        if (user.getSuffix().isPresent()) {
            return user.getSuffix().get();
        }
        if (array.length != 0) {
            return array[0].getSuffix().orElse("");
        }
        return "";
    }
}
