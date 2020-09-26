

package me.TechsCode.EnderPermissions.commands;

import me.TechsCode.EnderPermissions.base.command.Arguments;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import me.TechsCode.EnderPermissions.transfer.TransferAssistant;
import me.TechsCode.EnderPermissions.base.command.arguments.SpecificArguments;
import me.TechsCode.EnderPermissions.commands.requirements.MySQLEnabledRequirement;
import me.TechsCode.EnderPermissions.base.visual.Text;
import me.TechsCode.EnderPermissions.base.command.arguments.AnyArgument;
import me.TechsCode.EnderPermissions.storage.collection.UserList;
import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.util.Objects;
import me.TechsCode.EnderPermissions.storage.collection.GroupList;
import me.TechsCode.EnderPermissions.storage.objects.UserRankup;
import me.TechsCode.EnderPermissions.storage.collection.PermissionList;
import java.util.function.Consumer;
import me.TechsCode.EnderPermissions.storage.objects.Permission;
import me.TechsCode.EnderPermissions.commands.arguments.PermissionArgument;
import me.TechsCode.EnderPermissions.base.command.ArgumentValue;
import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.T;
import me.TechsCode.EnderPermissions.tpl.Tools;
import me.TechsCode.EnderPermissions.commands.arguments.GroupArgument;
import me.TechsCode.EnderPermissions.commands.arguments.UserArgument;
import me.TechsCode.EnderPermissions.base.command.EmptyReason;
import me.TechsCode.EnderPermissions.base.command.Requirement;
import me.TechsCode.EnderPermissions.commands.requirements.SuperadminRequirement;
import me.TechsCode.EnderPermissions.base.command.arguments.SpecificArgument;
import me.TechsCode.EnderPermissions.base.command.CommandNode;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.command.Command;

public class UPCCommand extends Command
{
    private EnderPermissions plugin;
    
    public UPCCommand(final EnderPermissions plugin) {
        super(plugin, "upc", new String[0]);
        this.plugin = plugin;
    }
    
    @Override
    public void define(final CommandNode<String, SpecificArgument> commandNode) {
        commandNode.require(new SuperadminRequirement(this.plugin));
        this.AddGroupCommand(commandNode);
        this.AddGroupPermission(commandNode);
        this.AddPlayerPermission(commandNode);
        this.AddSuperAdmin(commandNode);
        this.RemoveGroup(commandNode);
        this.RemoveGroupPermission(commandNode);
        this.RemovePlayerPermission(commandNode);
        this.RemoveSuperAdmin(commandNode);
        this.SetGroups(commandNode);
        this.SetPlayerPrefix(commandNode);
        this.SetPlayerSuffix(commandNode);
        this.Transfer(commandNode);
    }
    
    private void AddGroupCommand(final CommandNode<String, SpecificArgument> commandNode) {
        final ArgumentValue<User> argumentValue;
        final ArgumentValue<Group> argumentValue2;
        final long n2;
        long n;
        commandNode.newNode(new SpecificArgument("AddGroup"), commandNode2 -> commandNode2.newNode(new UserArgument(this.plugin, EmptyReason.INVALID), commandNode3 -> commandNode3.newNode(new GroupArgument(this.plugin, EmptyReason.INVALID), commandNode4 -> commandNode4.action((commandSender, p3, elements) -> {
            commandNode3.getValue(elements);
            commandNode4.getValue(elements);
            if (this.isValidUser(commandSender, argumentValue) && this.isValidGroup(commandSender, argumentValue2)) {
                Tools.getTimeSecondsFromString(String.join(" ", elements));
                n = ((n2 == 0L) ? 0L : (System.currentTimeMillis() + 1000L * n2));
                if (n == 0L) {
                    commandSender.sendMessage(this.plugin.getPrefix() + T.COMMAND_PLAYER_ADD_GROUP.get().options().vars(argumentValue2.get().getName(), argumentValue.get().getName()));
                }
                else {
                    commandSender.sendMessage(this.plugin.getPrefix() + T.COMMAND_PLAYER_ADD_GROUP_TIMED.get().options().vars(argumentValue2.get().getName(), argumentValue.get().getName(), Tools.getTimeString(n2)));
                }
                argumentValue.get().addGroup(argumentValue2.get(), n);
            }
        }))));
    }
    
    private void AddGroupPermission(final CommandNode<String, SpecificArgument> commandNode) {
        final ArgumentValue<Group> argumentValue;
        CommandOptions commandOptions;
        final ArgumentValue<String> argumentValue2;
        commandNode.newNode(new SpecificArgument("AddGroupPermission"), commandNode2 -> commandNode2.newNode(new GroupArgument(this.plugin, EmptyReason.INVALID), commandNode3 -> commandNode3.newNode(new PermissionArgument(this.plugin), commandNode4 -> commandNode4.action((commandSender, p3, arguments) -> {
            commandNode3.getValue(arguments);
            commandNode4.getValue(arguments);
            if (this.isValidGroup(commandSender, argumentValue)) {
                commandOptions = new CommandOptions(this.plugin, commandNode4.getSpareArguments(arguments));
                if (argumentValue.get().getPermissions().name(argumentValue2.get()).servers(commandOptions.getServer() == null, commandOptions.getServer()).worlds(commandOptions.getWorld() == null, commandOptions.getWorld()).stream().anyMatch(permission -> permission.getExpiration() == commandOptions.getExpiration())) {
                    commandSender.sendMessage(this.plugin.getPrefix() + "§7The group §a" + argumentValue.get().getName() + " §7already has the permission §e" + argumentValue2.get());
                }
                else {
                    argumentValue.get().newPermission(argumentValue2.get()).setServer(commandOptions.getServer()).setWorld(commandOptions.getWorld()).setExpiration(commandOptions.getExpiration()).create();
                    commandSender.sendMessage(this.plugin.getPrefix() + T.COMMAND_PERMISSION_ADD.get().options().vars(argumentValue2.get(), argumentValue.get().getName()).get() + commandOptions.getCombinedInfo());
                }
            }
        }))));
    }
    
    private void AddPlayerPermission(final CommandNode<String, SpecificArgument> commandNode) {
        final ArgumentValue<User> argumentValue;
        CommandOptions commandOptions;
        final ArgumentValue<String> argumentValue2;
        commandNode.newNode(new SpecificArgument("AddPlayerPermission"), commandNode2 -> commandNode2.newNode(new UserArgument(this.plugin, EmptyReason.INVALID), commandNode3 -> commandNode3.newNode(new PermissionArgument(this.plugin), commandNode4 -> commandNode4.action((commandSender, p3, arguments) -> {
            commandNode3.getValue(arguments);
            commandNode4.getValue(arguments);
            if (this.isValidUser(commandSender, argumentValue)) {
                commandOptions = new CommandOptions(this.plugin, commandNode4.getSpareArguments(arguments));
                if (argumentValue.get().getPermissions().name(argumentValue2.get()).servers(commandOptions.getServer() == null, commandOptions.getServer()).worlds(commandOptions.getWorld() == null, commandOptions.getWorld()).stream().anyMatch(permission -> permission.getExpiration() == commandOptions.getExpiration())) {
                    commandSender.sendMessage(this.plugin.getPrefix() + "§7The player §a" + argumentValue.get().getName() + " §7already has the permission §e" + argumentValue2.get());
                }
                else {
                    argumentValue.get().newPermission(argumentValue2.get()).setServer(commandOptions.getServer()).setWorld(commandOptions.getWorld()).setExpiration(commandOptions.getExpiration()).create();
                    commandSender.sendMessage(this.plugin.getPrefix() + T.COMMAND_PERMISSION_ADD.get().options().vars(argumentValue2.get(), argumentValue.get().getName()) + commandOptions.getCombinedInfo());
                }
            }
        }))));
    }
    
    private void AddSuperAdmin(final CommandNode<String, SpecificArgument> commandNode) {
        final ArgumentValue<User> argumentValue;
        commandNode.newNode(new SpecificArgument("AddSuperAdmin"), commandNode2 -> commandNode2.newNode(new UserArgument(this.plugin, EmptyReason.INVALID), commandNode3 -> commandNode3.action((commandSender, p2, arguments) -> {
            commandNode3.getValue(arguments);
            if (this.isValidUser(commandSender, argumentValue)) {
                if (argumentValue.get().isSuperadmin()) {
                    commandSender.sendMessage(this.plugin.getPrefix() + T.COMMAND_PLAYER_SUPERADMIN_ALREADY.get().options().vars(argumentValue.get().getName()));
                }
                else {
                    argumentValue.get().setSuperadmin((boolean)(1 != 0));
                    commandSender.sendMessage(this.plugin.getPrefix() + T.COMMAND_PLAYER_SUPERADMIN_ADD.get().options().vars(argumentValue.get().getName()));
                }
            }
        })));
    }
    
    private void RemoveGroup(final CommandNode<String, SpecificArgument> commandNode) {
        final ArgumentValue<User> argumentValue;
        final ArgumentValue<Group> argumentValue2;
        commandNode.newNode(new SpecificArgument("RemoveGroup"), commandNode2 -> commandNode2.newNode(new UserArgument(this.plugin, EmptyReason.INVALID), commandNode3 -> commandNode3.newNode(new GroupArgument(this.plugin, EmptyReason.INVALID), commandNode4 -> commandNode4.action((commandSender, p3, arguments) -> {
            commandNode3.getValue(arguments);
            commandNode4.getValue(arguments);
            if (this.isValidUser(commandSender, argumentValue) && this.isValidGroup(commandSender, argumentValue2)) {
                if (argumentValue.get().getGroups().contains(argumentValue2.get().toStored())) {
                    argumentValue.get().removeGroup(argumentValue2.get());
                    commandSender.sendMessage(this.plugin.getPrefix() + "§7Removed Group §e" + argumentValue2.get().getName() + " §7from §b" + argumentValue.get().getName());
                }
                else {
                    commandSender.sendMessage(this.plugin.getPrefix() + "§7The player §b" + argumentValue.get().getName() + " §7is not in the group §e" + argumentValue2.get().getName());
                }
            }
        }))));
    }
    
    private void RemoveGroupPermission(final CommandNode<String, SpecificArgument> commandNode) {
        final ArgumentValue<Group> argumentValue;
        final ArgumentValue<String> argumentValue2;
        final PermissionList list;
        commandNode.newNode(new SpecificArgument("RemoveGroupPermission"), commandNode2 -> commandNode2.newNode(new GroupArgument(this.plugin, EmptyReason.INVALID), commandNode3 -> commandNode3.newNode(new PermissionArgument(this.plugin), commandNode4 -> commandNode4.action((commandSender, p3, arguments) -> {
            commandNode3.getValue(arguments);
            commandNode4.getValue(arguments);
            if (this.isValidGroup(commandSender, argumentValue)) {
                argumentValue.get().getPermissions().name(argumentValue2.get());
                list.forEach(Permission::remove);
                if (list.size() == 0) {
                    commandSender.sendMessage(this.plugin.getPrefix() + "§7Didn't find permission §e" + argumentValue2.get() + " §7for §b" + argumentValue.get().getName());
                }
                else if (list.size() == 1) {
                    commandSender.sendMessage(this.plugin.getPrefix() + "§7Removed permission §e" + argumentValue2.get() + " §7from §b" + argumentValue.get().getName());
                }
                else {
                    commandSender.sendMessage(this.plugin.getPrefix() + "§7Removed " + list.size() + " copies of §e" + argumentValue2.get() + " §7from §b" + argumentValue.get().getName());
                }
            }
        }))));
    }
    
    private void RemovePlayerPermission(final CommandNode<String, SpecificArgument> commandNode) {
        final ArgumentValue<User> argumentValue;
        final ArgumentValue<String> argumentValue2;
        final PermissionList list;
        commandNode.newNode(new SpecificArgument("RemovePlayerPermission"), commandNode2 -> commandNode2.newNode(new UserArgument(this.plugin, EmptyReason.INVALID), commandNode3 -> commandNode3.newNode(new PermissionArgument(this.plugin), commandNode4 -> commandNode4.action((commandSender, p3, arguments) -> {
            commandNode3.getValue(arguments);
            commandNode4.getValue(arguments);
            if (this.isValidUser(commandSender, argumentValue)) {
                argumentValue.get().getPermissions().name(argumentValue2.get());
                list.forEach(Permission::remove);
                if (list.size() == 0) {
                    commandSender.sendMessage(this.plugin.getPrefix() + "§7Didn't find permission §e" + argumentValue2.get() + " §7for §b" + argumentValue.get().getName());
                }
                else if (list.size() == 1) {
                    commandSender.sendMessage(this.plugin.getPrefix() + "§7Removed permission §e" + argumentValue2.get() + " §7from §b" + argumentValue.get().getName());
                }
                else {
                    commandSender.sendMessage(this.plugin.getPrefix() + "§7Removed " + list.size() + " copies of §e" + argumentValue2.get() + " §7from §b" + argumentValue.get().getName());
                }
            }
        }))));
    }
    
    private void RemoveSuperAdmin(final CommandNode<String, SpecificArgument> commandNode) {
        final ArgumentValue<User> argumentValue;
        commandNode.newNode(new SpecificArgument("RemoveSuperAdmin"), commandNode2 -> commandNode2.newNode(new UserArgument(this.plugin, EmptyReason.INVALID), commandNode3 -> commandNode3.action((commandSender, p2, arguments) -> {
            commandNode3.getValue(arguments);
            if (this.isValidUser(commandSender, argumentValue)) {
                if (argumentValue.get().isSuperadmin()) {
                    argumentValue.get().setSuperadmin((boolean)(0 != 0));
                    commandSender.sendMessage(this.plugin.getPrefix() + T.COMMAND_PLAYER_SUPERADMIN_REMOVE.get().options().vars(argumentValue.get().getName()));
                }
                else {
                    commandSender.sendMessage(this.plugin.getPrefix() + T.COMMAND_PLAYER_NOTSUPERADMIN.get().options().vars(argumentValue.get().getName()));
                }
            }
        })));
    }
    
    private void SetGroups(final CommandNode<String, SpecificArgument> commandNode) {
        final ArgumentValue<User> argumentValue;
        GroupList list;
        final Iterator<Group> iterator;
        commandNode.newNode(new SpecificArgument("SetGroups"), commandNode2 -> commandNode2.newNode(new UserArgument(this.plugin, EmptyReason.INVALID), commandNode3 -> commandNode3.action((commandSender, p2, arguments) -> {
            commandNode3.getValue(arguments);
            if (this.isValidUser(commandSender, argumentValue)) {
                argumentValue.get().getRankups().forEach(UserRankup::remove);
                list = commandNode3.getSpareArguments(arguments).stream().map(s -> this.plugin.getGroups().name(s).orElse(null)).filter(Objects::nonNull).collect((Collector<? super Object, ?, GroupList>)Collectors.toCollection((Supplier<R>)GroupList::new));
                list.iterator();
                while (iterator.hasNext()) {
                    argumentValue.get().addGroup(iterator.next());
                }
                if (list.size() == 0) {
                    commandSender.sendMessage(this.plugin.getPrefix() + "§7Cleared all groups of §e" + argumentValue.get().getName());
                }
                else {
                    commandSender.sendMessage(this.plugin.getPrefix() + "§7Set the groups §e" + list.stream().map((Function<? super Group, ?>)Group::getName).collect((Collector<? super Object, ?, String>)Collectors.joining("§7, §e")) + " §7for §b" + argumentValue.get().getName());
                }
            }
        })));
    }
    
    private void SetPlayerPrefix(final CommandNode<String, SpecificArgument> commandNode) {
        final UserList list;
        final ArgumentValue<User> argumentValue;
        final ArgumentValue<User> argumentValue2;
        final ArgumentValue<String> argumentValue3;
        commandNode.newNode(new SpecificArgument("SetPlayerPrefix"), commandNode2 -> {
            commandNode2.newNode(new SpecificArgument("clearAll"), commandNode3 -> commandNode3.action((commandSender, p1, p2) -> {
                list = this.plugin.getUsers().stream().filter(User::hasPrefix).collect((Collector<? super User, ?, UserList>)Collectors.toCollection((Supplier<R>)UserList::new));
                list.forEach(user -> user.setPrefix(null));
                commandSender.sendMessage(this.plugin.getPrefix() + "§7Successfully cleared §e" + list.size() + " prefixes");
            }));
            commandNode2.newNode(new UserArgument(this.plugin, EmptyReason.INVALID), commandNode4 -> {
                commandNode4.newNode(new SpecificArgument("none"), commandNode5 -> commandNode5.action((commandSender2, p2, arguments) -> {
                    commandNode4.getValue(arguments);
                    if (this.isValidUser(commandSender2, argumentValue)) {
                        argumentValue.get().setPrefix(null);
                        commandSender2.sendMessage(this.plugin.getPrefix() + "§7Cleared the prefix of §b" + argumentValue.get().getName());
                    }
                }));
                commandNode4.newNode(new AnyArgument("<Prefix>"), commandNode6 -> commandNode6.action((commandSender3, p3, arguments2) -> {
                    commandNode4.getValue(arguments2);
                    commandNode6.getValue(arguments2);
                    if (this.isValidUser(commandSender3, argumentValue2)) {
                        argumentValue2.get().setPrefix(Text.color(argumentValue3.get()));
                        commandSender3.sendMessage(this.plugin.getPrefix() + "§7Set the prefix of §b" + argumentValue2.get().getName() + " §7to §b" + Text.color(argumentValue3.get()));
                    }
                }));
            });
        });
    }
    
    private void SetPlayerSuffix(final CommandNode<String, SpecificArgument> commandNode) {
        final UserList list;
        final ArgumentValue<User> argumentValue;
        final ArgumentValue<User> argumentValue2;
        final ArgumentValue<String> argumentValue3;
        commandNode.newNode(new SpecificArgument("SetPlayerSuffix"), commandNode2 -> {
            commandNode2.newNode(new SpecificArgument("clearAll"), commandNode3 -> commandNode3.action((commandSender, p1, p2) -> {
                list = this.plugin.getUsers().stream().filter(User::hasSuffix).collect((Collector<? super User, ?, UserList>)Collectors.toCollection((Supplier<R>)UserList::new));
                list.forEach(user -> user.setSuffix(null));
                commandSender.sendMessage(this.plugin.getPrefix() + "§7Successfully cleared §e" + list.size() + " suffixes");
            }));
            commandNode2.newNode(new UserArgument(this.plugin, EmptyReason.INVALID), commandNode4 -> {
                commandNode4.newNode(new SpecificArgument("none"), commandNode5 -> commandNode5.action((commandSender2, p2, arguments) -> {
                    commandNode4.getValue(arguments);
                    if (this.isValidUser(commandSender2, argumentValue)) {
                        argumentValue.get().setSuffix(null);
                        commandSender2.sendMessage(this.plugin.getPrefix() + "§7Cleared the suffix of §b" + argumentValue.get().getName());
                    }
                }));
                commandNode4.newNode(new AnyArgument("<Suffix>"), commandNode6 -> commandNode6.action((commandSender3, p3, arguments2) -> {
                    commandNode4.getValue(arguments2);
                    commandNode6.getValue(arguments2);
                    if (this.isValidUser(commandSender3, argumentValue2)) {
                        argumentValue2.get().setSuffix(Text.color(argumentValue3.get()));
                        commandSender3.sendMessage(this.plugin.getPrefix() + "§7Set the suffix of §b" + argumentValue2.get().getName() + " §7to §b" + Text.color(argumentValue3.get()));
                    }
                }));
            });
        });
    }
    
    private void Transfer(final CommandNode<String, SpecificArgument> commandNode) {
        final SpecificArguments specificArguments;
        final SpecificArguments specificArguments2;
        final String s;
        final String anotherString;
        commandNode.newNode(new SpecificArgument("Transfer"), commandNode2 -> {
            commandNode2.require(new MySQLEnabledRequirement(this.plugin));
            new SpecificArguments(new String[] { "File", "MySQL" });
            commandNode2.newNode(specificArguments, commandNode3 -> {
                new SpecificArguments(new String[] { "File", "MySQL" });
                commandNode3.newNode(specificArguments2, commandNode4 -> commandNode4.action((commandSender, p3, arguments) -> {
                    s = commandNode3.getValue(arguments).get();
                    anotherString = commandNode4.getValue(arguments).get();
                    if (s.equalsIgnoreCase(anotherString)) {
                        commandSender.sendMessage(this.plugin.getPrefix() + "§7From and To should not be equal!");
                    }
                    if (s.equals("File") && anotherString.equals("MySQL")) {
                        new TransferAssistant(this.plugin, (boolean)(1 != 0));
                    }
                    if (s.equals("MySQL") && anotherString.equals("File")) {
                        new TransferAssistant(this.plugin, (boolean)(0 != 0));
                    }
                }));
            });
        });
    }
    
    @Override
    public boolean isRegistered() {
        return true;
    }
    
    private boolean isValidUser(final CommandSender commandSender, final ArgumentValue<User> argumentValue) {
        if (!argumentValue.isValid()) {
            commandSender.sendMessage(this.plugin.getPrefix() + "§7Could not find the user §e" + argumentValue.getRaw());
        }
        return argumentValue.isValid();
    }
    
    private boolean isValidGroup(final CommandSender commandSender, final ArgumentValue<Group> argumentValue) {
        if (!argumentValue.isValid()) {
            commandSender.sendMessage(this.plugin.getPrefix() + "§7Could not find the group §e" + argumentValue.getRaw());
        }
        return argumentValue.isValid();
    }
}
