

package me.TechsCode.EnderPermissions.internal;

import me.TechsCode.EnderPermissions.utility.ChildrenResolver;
import java.util.Set;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node
{
    private String permission;
    
    public Node(final String permission) {
        this.permission = permission;
    }
    
    public List<String> getWildcards() {
        final ArrayList<String> list = new ArrayList<String>(Arrays.asList("*"));
        String str = "";
        for (final String str2 : this.permission.split("\\.")) {
            if (!str.equals("")) {
                str += ".";
            }
            str += str2;
            list.add(str + ".*");
        }
        return list;
    }
    
    public List<String> getSelfAndWildcards() {
        final ArrayList<Object> list = new ArrayList<Object>();
        list.add(this.permission);
        list.addAll(this.getWildcards());
        return (List<String>)list;
    }
    
    public Set<String> getSelfAndContainedPermissions() {
        return ChildrenResolver.retrieveChildPermissions(this.permission);
    }
}
