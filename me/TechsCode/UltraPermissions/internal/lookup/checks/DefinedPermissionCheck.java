

package me.TechsCode.EnderPermissions.internal.lookup.checks;

import java.util.Iterator;
import me.TechsCode.EnderPermissions.internal.lookup.LookupOutcome;
import me.TechsCode.EnderPermissions.storage.objects.Permission;
import java.util.List;
import me.TechsCode.EnderPermissions.internal.lookup.LookupCheck;

public abstract class DefinedPermissionCheck implements LookupCheck
{
    private List<Permission> definedPermissions;
    
    public DefinedPermissionCheck() {
        this.definedPermissions = this.getDefinedPermissions();
    }
    
    @Override
    public LookupOutcome perform(final String anotherString) {
        boolean b = false;
        Permission permission = null;
        for (final Permission permission2 : this.definedPermissions) {
            final Iterator<String> iterator2 = permission2.asNode().getSelfAndContainedPermissions().iterator();
            while (iterator2.hasNext()) {
                if (iterator2.next().equalsIgnoreCase(anotherString)) {
                    if (!permission2.isPositive()) {
                        return new LookupOutcome(false, "Negated from -" + permission2.getName());
                    }
                    b = true;
                    permission = permission2;
                }
            }
        }
        if (b) {
            return new LookupOutcome(true, "Through Permission '" + permission.getName() + "'");
        }
        return null;
    }
    
    public abstract List<Permission> getDefinedPermissions();
}
