

package me.TechsCode.EnderPermissions.internal.lookup;

import java.util.Iterator;
import me.TechsCode.EnderPermissions.internal.Node;

public abstract class PermissionLookup
{
    private final Node permission;
    private LookupOutcome outcome;
    
    public PermissionLookup(final String s) {
        this.permission = new Node(s);
        this.outcome = null;
    }
    
    public LookupOutcome getOutcome() {
        return this.outcome;
    }
    
    public LookupOutcome perform() {
        if (this.outcome != null) {
            return this.outcome;
        }
        final Iterator<String> iterator = this.permission.getSelfAndWildcards().iterator();
        while (iterator.hasNext()) {
            this.outcome = this.checkPermission(iterator.next());
            if (this.outcome != null) {
                return this.outcome;
            }
        }
        return new LookupOutcome(false, null);
    }
    
    private LookupOutcome checkPermission(final String s) {
        final LookupCheck[] checks = this.getChecks();
        for (int length = checks.length, i = 0; i < length; ++i) {
            final LookupOutcome perform = checks[i].perform(s);
            if (perform != null) {
                return perform;
            }
        }
        return null;
    }
    
    public abstract LookupCheck[] getChecks();
}
