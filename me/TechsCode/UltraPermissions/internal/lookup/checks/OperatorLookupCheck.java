

package me.TechsCode.EnderPermissions.internal.lookup.checks;

import me.TechsCode.EnderPermissions.internal.lookup.LookupOutcome;
import org.bukkit.OfflinePlayer;
import me.TechsCode.EnderPermissions.internal.lookup.LookupCheck;

public class OperatorLookupCheck implements LookupCheck
{
    private final OfflinePlayer offlinePlayer;
    
    public OperatorLookupCheck(final OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }
    
    @Override
    public LookupOutcome perform(final String s) {
        if (this.offlinePlayer.isOp()) {
            return new LookupOutcome(true, "Operator Rights");
        }
        return null;
    }
}
