

package me.TechsCode.EnderPermissions.commands.arguments;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.List;
import me.TechsCode.EnderPermissions.base.command.ArgumentValue;
import me.TechsCode.EnderPermissions.base.command.EmptyReason;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.base.command.Argument;

public class GroupArgument extends Argument<Group>
{
    private EnderPermissions plugin;
    private EmptyReason emptyReason;
    
    public GroupArgument(final EnderPermissions plugin, final EmptyReason emptyReason) {
        this.plugin = plugin;
        this.emptyReason = emptyReason;
    }
    
    @Override
    public ArgumentValue<Group> get(final String s) {
        return this.plugin.getGroups().name(s).map(group -> ArgumentValue.of(s, group)).orElseGet(() -> ArgumentValue.empty(s, this.emptyReason));
    }
    
    @Override
    public String getUsage() {
        return "<Group>";
    }
    
    @Override
    public List<String> getTabCompletions() {
        return this.plugin.getGroups().stream().map((Function<? super Group, ?>)Group::getName).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
}
