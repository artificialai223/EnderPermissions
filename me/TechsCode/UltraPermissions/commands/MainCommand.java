

package me.TechsCode.EnderPermissions.commands;

import me.TechsCode.EnderPermissions.base.command.Arguments;
import org.bukkit.command.CommandSender;
import me.TechsCode.EnderPermissions.base.command.ArgumentValue;
import me.TechsCode.EnderPermissions.base.update.Updater;
import me.TechsCode.EnderPermissions.gui.Overview;
import me.TechsCode.EnderPermissions.gui.MigrationView;
import me.TechsCode.EnderPermissions.migration.Migrations;
import me.TechsCode.EnderPermissions.gui.GroupView;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.commands.arguments.GroupArgument;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.gui.UserView;
import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.commands.arguments.UserArgument;
import me.TechsCode.EnderPermissions.base.command.EmptyReason;
import me.TechsCode.EnderPermissions.base.command.Requirement;
import me.TechsCode.EnderPermissions.commands.requirements.SuperadminRequirement;
import me.TechsCode.EnderPermissions.base.command.arguments.SpecificArgument;
import me.TechsCode.EnderPermissions.base.command.CommandNode;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.command.Command;

public class MainCommand extends Command
{
    private final EnderPermissions plugin;
    
    public MainCommand(final EnderPermissions plugin) {
        super(plugin, "uperms", new String[0]);
        this.plugin = plugin;
    }
    
    @Override
    public void define(final CommandNode<String, SpecificArgument> commandNode) {
        commandNode.require(new SuperadminRequirement(this.plugin));
        final ArgumentValue<User> argumentValue;
        commandNode.newNode(new UserArgument(this.plugin, EmptyReason.NO_MATCH), commandNode2 -> commandNode2.action((p1, player2, arguments) -> {
            commandNode2.getValue(arguments);
            if (argumentValue.isValid()) {
                new UserView(player2, this.plugin, (User)argumentValue.get()) {
                    @Override
                    public void onBack() {
                    }
                    
                    @Override
                    public boolean hasBackButton() {
                        return false;
                    }
                };
            }
        }));
        final ArgumentValue<Group> argumentValue2;
        commandNode.newNode(new GroupArgument(this.plugin, EmptyReason.NO_MATCH), commandNode3 -> commandNode3.action((p1, player3, arguments2) -> {
            commandNode3.getValue(arguments2);
            if (argumentValue2.isValid()) {
                new GroupView(player3, this.plugin, (Group)argumentValue2.get()) {
                    @Override
                    public void onBack() {
                    }
                    
                    @Override
                    public boolean hasBackButton() {
                        return false;
                    }
                };
            }
        }));
        commandNode.action((p0, player, p2) -> {
            if (Migrations.isAvailable()) {
                new MigrationView(player, this.plugin, Migrations.getMigrationAssistant());
            }
            else {
                Updater.suggestUpdateIfAvailable(this.plugin, player, "https://updates.techscode.de", p1 -> new Overview(player, this.plugin).reopen(), false);
            }
        });
    }
    
    @Override
    public boolean isRegistered() {
        return true;
    }
}
