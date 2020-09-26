

package me.TechsCode.EnderPermissions.internal.lookup.checks;

import java.util.stream.Stream;
import org.bukkit.permissions.PermissionAttachment;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import me.TechsCode.EnderPermissions.internal.lookup.LookupOutcome;
import me.TechsCode.EnderPermissions.internal.lookup.LookupCheck;

public abstract class AttachedPermissionCheck implements LookupCheck
{
    @Override
    public LookupOutcome perform(final String anotherString) {
        final List<Object> list = new ArrayList<Object>(this.getAttachments()).stream().flatMap(permissionAttachment -> permissionAttachment.getPermissions().entrySet().stream()).collect((Collector<? super Object, ?, List<Object>>)Collectors.toList());
        boolean b = false;
        boolean b2 = false;
        for (final Map.Entry<String, V> entry : list) {
            if (entry.getKey().equalsIgnoreCase(anotherString)) {
                if (entry.getValue()) {
                    b = true;
                }
                else {
                    b2 = true;
                }
            }
        }
        if (b2) {
            return new LookupOutcome(false, "Negated by Permission Attachment");
        }
        if (b) {
            return new LookupOutcome(true, "Allowed by Permission Attachment");
        }
        return null;
    }
    
    public abstract List<PermissionAttachment> getAttachments();
}
