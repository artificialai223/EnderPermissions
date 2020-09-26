

package me.TechsCode.EnderPermissions.hooks.placeholders;

import me.TechsCode.EnderPermissions.tpl.Tools;
import java.util.concurrent.TimeUnit;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.storage.objects.User;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.hooks.UpermsPlaceholder;

public class RankTimerPlaceholder extends UpermsPlaceholder
{
    public RankTimerPlaceholder() {
        super("rank_timer", "Rank Timer of next highest timed group");
    }
    
    @Override
    protected String replace(final Player player, final User user, final Group[] array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            final long groupExpiry = user.getGroupExpiry(array[i].toStored());
            if (groupExpiry != 0L) {
                return Tools.getTimeString(groupExpiry - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            }
        }
        return "Permanent";
    }
}
