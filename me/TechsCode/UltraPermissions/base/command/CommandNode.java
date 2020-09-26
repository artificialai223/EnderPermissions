

package me.TechsCode.EnderPermissions.base.command;

import java.util.stream.Stream;
import java.util.function.Predicate;
import java.util.Objects;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import me.TechsCode.EnderPermissions.base.misc.Callback;
import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import me.TechsCode.EnderPermissions.base.misc.Setter;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.base.command.arguments.SpecificArgument;
import java.util.List;

public class CommandNode<T, ARGUMENT extends Argument<T>>
{
    private final CommandNode<?, ?> parent;
    private final ARGUMENT argument;
    private final List<CommandNode<?, ?>> children;
    private final List<Requirement> requirements;
    private String description;
    private Action<T> action;
    
    public static CommandNode<String, SpecificArgument> create(final String s) {
        return new CommandNode<String, SpecificArgument>(null, new SpecificArgument(s));
    }
    
    public CommandNode(final CommandNode<?, ?> parent, final ARGUMENT argument) {
        this.parent = parent;
        this.children = new ArrayList<CommandNode<?, ?>>();
        this.argument = argument;
        this.requirements = new ArrayList<Requirement>();
        this.description = null;
        this.action = null;
    }
    
    public <NEW_T, NEW_ARGUMENT extends Argument<NEW_T>> CommandNode<T, ARGUMENT> newNode(final NEW_ARGUMENT new_ARGUMENT, final Setter<CommandNode<NEW_T, NEW_ARGUMENT>> setter) {
        final CommandNode<NEW_T, NEW_ARGUMENT> commandNode = new CommandNode<NEW_T, NEW_ARGUMENT>(this, new_ARGUMENT);
        setter.set(commandNode);
        this.children.add(commandNode);
        return this;
    }
    
    public void description(final String description) {
        this.description = description;
    }
    
    public void require(final Requirement requirement) {
        this.requirements.add(requirement);
    }
    
    public void action(final Action<T> action) {
        this.action = action;
    }
    
    public CommandNode<?, ?> getCurrentNode(final CommandSender commandSender, final Player player, final List<String> list) {
        return this.getCurrentNode(commandSender, player, list, 0);
    }
    
    private CommandNode<?, ?> getCurrentNode(final CommandSender commandSender, final Player player, final List<String> list, final int n) {
        if (list.size() == n) {
            return null;
        }
        if (this.argument.hasMatch(list.get(n))) {
            final Iterator<Requirement> iterator = this.requirements.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().isMatching(commandSender, player, list)) {
                    return null;
                }
            }
            if (list.size() - (n + 1) == 0) {
                return this;
            }
            final Iterator<CommandNode<?, ?>> iterator2 = this.children.iterator();
            while (iterator2.hasNext()) {
                final CommandNode<?, ?> currentNode = iterator2.next().getCurrentNode(commandSender, player, list, n + 1);
                if (currentNode != null) {
                    return currentNode;
                }
            }
        }
        return null;
    }
    
    public void execute(final CommandSender commandSender, final Player player, final Arguments arguments, final Callback<ExecuteCallback> callback) {
        this.execute(commandSender, player, arguments, 0, callback);
    }
    
    private boolean execute(final CommandSender commandSender, final Player player, final Arguments arguments, final int index, final Callback<ExecuteCallback> callback) {
        if (arguments.size() == index) {
            return false;
        }
        if (this.argument.hasMatch(arguments.get(index))) {
            for (final Requirement requirement : this.requirements) {
                if (!requirement.isMatching(commandSender, player, arguments)) {
                    requirement.onUnmatched(commandSender, player);
                    return true;
                }
            }
            final Iterator<CommandNode<?, ?>> iterator2 = this.children.iterator();
            while (iterator2.hasNext()) {
                if (iterator2.next().execute(commandSender, player, arguments, index + 1, callback)) {
                    return true;
                }
            }
            if (this.action != null) {
                this.action.run(commandSender, player, arguments);
                callback.run(new ExecuteCallback(this, ExecuteResult.SUCCESSFUL));
            }
            else {
                callback.run(new ExecuteCallback(this, ExecuteResult.NO_ACTION));
            }
            return true;
        }
        return false;
    }
    
    public String getUsagePath() {
        return this.getNodesOnPath().stream().map(commandNode -> commandNode.argument.getUsage()).collect((Collector<? super Object, ?, String>)Collectors.joining(" "));
    }
    
    public ArgumentValue<T> getValue(final Arguments arguments) {
        int index = 0;
        for (CommandNode<?, ?> commandNode = this.parent; commandNode != null; commandNode = commandNode.parent, ++index) {}
        return this.argument.get(arguments.get(index));
    }
    
    public List<String> getSpareArguments(final Arguments arguments) {
        int fromIndex = 0;
        for (CommandNode<?, ?> parent = this; parent != null; parent = parent.parent, ++fromIndex) {}
        return arguments.subList(fromIndex, arguments.size());
    }
    
    public List<CommandNode<?, ?>> getEndNodes() {
        if (this.children.size() == 0) {
            return (List<CommandNode<?, ?>>)Collections.singletonList(this);
        }
        final ArrayList<Object> list = (ArrayList<Object>)new ArrayList<CommandNode<?, ?>>();
        final Iterator<CommandNode<?, ?>> iterator = this.children.iterator();
        while (iterator.hasNext()) {
            list.addAll(iterator.next().getEndNodes());
        }
        return (List<CommandNode<?, ?>>)list;
    }
    
    public List<CommandNode<?, ?>> getNodesOnPath() {
        final ArrayList<CommandNode> list = (ArrayList<CommandNode>)new ArrayList<CommandNode<?, ?>>();
        list.add(this);
        CommandNode<?, ?> parent = this;
        while (parent != null) {
            parent = parent.parent;
            if (parent != null) {
                list.add(parent);
            }
        }
        Collections.reverse(list);
        return (List<CommandNode<?, ?>>)list;
    }
    
    public List<CommandNode<?, ?>> getChildren() {
        return this.children;
    }
    
    public boolean hasDescription() {
        return this.description != null;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public ARGUMENT getArgument() {
        return this.argument;
    }
    
    public List<Requirement> getRequirements() {
        return this.requirements;
    }
    
    public List<Requirement> getAllRequirements() {
        return this.getNodesOnPath().stream().flatMap(commandNode -> commandNode.getRequirements().stream()).filter(Objects::nonNull).collect((Collector<? super Object, ?, List<Requirement>>)Collectors.toList());
    }
}
