

package me.TechsCode.EnderPermissions.internal.lookup.checks;

import me.TechsCode.EnderPermissions.internal.lookup.LookupOutcome;
import me.TechsCode.EnderPermissions.internal.lookup.LookupCheck;

public class BlankCheck implements LookupCheck
{
    @Override
    public LookupOutcome perform(final String s) {
        return null;
    }
}
