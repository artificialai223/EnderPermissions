

package me.TechsCode.EnderPermissions.commands.arguments;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.List;
import me.TechsCode.EnderPermissions.base.command.ArgumentValue;
import me.TechsCode.EnderPermissions.base.command.EmptyReason;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.base.command.Argument;

public class UserArgument extends Argument<User>
{
    private EnderPermissions plugin;
    private EmptyReason emptyReason;
    
    public UserArgument(final EnderPermissions plugin, final EmptyReason emptyReason) {
        this.plugin = plugin;
        this.emptyReason = emptyReason;
    }
    
    @Override
    public ArgumentValue<User> get(final String s) {
        return this.plugin.getUsers().name(s).map(user -> ArgumentValue.of(s, user)).orElseGet(() -> ArgumentValue.empty(s, this.emptyReason));
    }
    
    @Override
    public String getUsage() {
        return "<User>";
    }
    
    @Override
    public List<String> getTabCompletions() {
        return this.plugin.getUsers().stream().map((Function<? super User, ?>)User::getName).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
}
