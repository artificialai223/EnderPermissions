

package me.TechsCode.EnderPermissions.storage.collection;

import java.util.stream.Collector;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.base.storage.Stored;
import java.util.Optional;
import java.util.UUID;
import me.TechsCode.EnderPermissions.storage.objects.User;
import java.util.ArrayList;

public class UserList extends ArrayList<User>
{
    public Optional<User> uuid(final UUID obj) {
        return this.stream().filter(user -> user.getUuid().equals(obj)).findFirst();
    }
    
    public Optional<User> name(final String anotherString) {
        return this.stream().filter(user -> user.getName().equalsIgnoreCase(anotherString)).findFirst();
    }
    
    public UserList usersInGroup(final Stored<Group> stored) {
        return this.stream().filter(user -> user.getGroups().contains(stored)).collect((Collector<? super User, ?, UserList>)Collectors.toCollection((Supplier<R>)UserList::new));
    }
}
