

package me.TechsCode.EnderPermissions.base.command;

import me.TechsCode.EnderPermissions.base.scheduler.RecurringTask;
import java.util.stream.Stream;
import java.util.Map;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.ArrayUtils;
import java.lang.reflect.Field;
import org.bukkit.command.CommandMap;
import org.bukkit.Bukkit;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.Arrays;
import org.bukkit.command.CommandSender;
import me.TechsCode.EnderPermissions.base.command.arguments.SpecificArgument;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.tpl.titleAndActionbar.ActionBar;
import org.bukkit.event.Listener;
import org.bukkit.command.defaults.BukkitCommand;

public abstract class Command extends BukkitCommand implements Listener
{
    private ActionBar actionBar;
    private boolean isCurrentlyRegistered;
    
    public Command(final SpigotTechPlugin spigotTechPlugin, final String str, final String... array) {
        this(spigotTechPlugin, str, "", "/" + str, array);
    }
    
    public Command(final SpigotTechPlugin p0, final String p1, final String p2, final String p3, final String... p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_2        
        //     2: aload_3        
        //     3: aload           4
        //     5: aload           5
        //     7: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //    10: invokespecial   org/bukkit/command/defaults/BukkitCommand.<init>:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V
        //    13: aload_0        
        //    14: new             Lme/TechsCode/EnderPermissions/tpl/titleAndActionbar/ActionBar;
        //    17: dup            
        //    18: aload_1        
        //    19: invokespecial   me/TechsCode/EnderPermissions/tpl/titleAndActionbar/ActionBar.<init>:(Lme/TechsCode/EnderPermissions/base/SpigotTechPlugin;)V
        //    22: putfield        me/TechsCode/EnderPermissions/base/command/Command.actionBar:Lme/TechsCode/EnderPermissions/tpl/titleAndActionbar/ActionBar;
        //    25: aload_0        
        //    26: invokevirtual   me/TechsCode/EnderPermissions/base/command/Command.isRegistered:()Z
        //    29: ifeq            36
        //    32: aload_0        
        //    33: invokespecial   me/TechsCode/EnderPermissions/base/command/Command.register:()V
        //    36: aload_1        
        //    37: invokevirtual   me/TechsCode/EnderPermissions/base/SpigotTechPlugin.getScheduler:()Lme/TechsCode/EnderPermissions/base/scheduler/Scheduler;
        //    40: aload_0        
        //    41: invokedynamic   BootstrapMethod #0, run:(Lme/TechsCode/EnderPermissions/base/command/Command;)Ljava/lang/Runnable;
        //    46: ldc2_w          20
        //    49: ldc2_w          20
        //    52: invokeinterface me/TechsCode/EnderPermissions/base/scheduler/Scheduler.runTaskTimer:(Ljava/lang/Runnable;JJ)Lme/TechsCode/EnderPermissions/base/scheduler/RecurringTask;
        //    57: astore          6
        //    59: aload_1        
        //    60: aload_0        
        //    61: aload           6
        //    63: invokedynamic   BootstrapMethod #1, run:(Lme/TechsCode/EnderPermissions/base/command/Command;Lme/TechsCode/EnderPermissions/base/scheduler/RecurringTask;)Ljava/lang/Runnable;
        //    68: invokevirtual   me/TechsCode/EnderPermissions/base/SpigotTechPlugin.addDisableHook:(Ljava/lang/Runnable;)V
        //    71: return         
        //    StackMapTable: 00 01 FF 00 24 00 06 07 00 02 07 00 3F 07 00 41 07 00 41 07 00 41 07 00 43 00 00
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Unsupported node type: com.strobel.decompiler.ast.BasicBlock
        //     at com.strobel.decompiler.ast.Error.unsupportedNode(Error.java:32)
        //     at com.strobel.decompiler.ast.GotoRemoval.exit(GotoRemoval.java:612)
        //     at com.strobel.decompiler.ast.GotoRemoval.transformLeaveStatements(GotoRemoval.java:625)
        //     at com.strobel.decompiler.ast.GotoRemoval.removeGotosCore(GotoRemoval.java:57)
        //     at com.strobel.decompiler.ast.GotoRemoval.removeGotos(GotoRemoval.java:53)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:276)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public abstract void define(final CommandNode<String, SpecificArgument> p0);
    
    public boolean execute(final CommandSender commandSender, final String s, final String[] a) {
        final Arguments arguments = new Arguments();
        arguments.add(this.getName());
        arguments.addAll(Arrays.asList(a));
        final Player player = (commandSender instanceof Player) ? commandSender : null;
        final CommandNode<String, SpecificArgument> create = CommandNode.create(this.getName());
        this.define(create);
        final Iterator<CommandNode<?, ?>> iterator;
        CommandNode commandNode;
        final Player player2;
        final List<String> list;
        String string;
        final StringBuilder sb;
        create.execute(commandSender, player, arguments, executeCallback -> {
            if (executeCallback.getResult() == ExecuteResult.NO_ACTION) {
                commandSender.sendMessage("");
                commandSender.sendMessage("§9§lUsage:");
                executeCallback.getLastNode().getChildren().iterator();
                while (iterator.hasNext()) {
                    commandNode = iterator.next();
                    if (commandNode.getAllRequirements().stream().allMatch(requirement -> requirement.isMatching(commandSender, player2, list))) {
                        new StringBuilder().append("§7- §e/").append(commandNode.getUsagePath());
                        if (commandNode.hasDescription()) {
                            string = " §7" + commandNode.getDescription();
                        }
                        else {
                            string = "";
                        }
                        commandSender.sendMessage(sb.append(string).toString());
                    }
                }
            }
            return;
        });
        return false;
    }
    
    public List<String> tabComplete(final CommandSender commandSender, final String s2, final String[] a) {
        final Arguments arguments = new Arguments();
        arguments.add(this.getName());
        arguments.addAll(Arrays.asList(a));
        final Player player = (commandSender instanceof Player) ? commandSender : null;
        final CommandNode<String, SpecificArgument> create = CommandNode.create(this.getName());
        this.define(create);
        final CommandNode<?, ?> currentNode = create.getCurrentNode(commandSender, player, arguments.subList(0, arguments.size() - 1));
        if (currentNode == null) {
            return new ArrayList<String>();
        }
        if (player != null) {
            final List<CommandNode<?, ?>> endNodes = currentNode.getEndNodes();
            if (endNodes.size() == 1) {
                this.actionBar.sendActionBar(player, this.getTabCompletionActionBar(create.getCurrentNode(commandSender, player, arguments), endNodes.get(0), arguments));
            }
        }
        return currentNode.getChildren().stream().flatMap(commandNode -> commandNode.getArgument().getTabCompletions().stream()).filter(s -> s.toLowerCase().startsWith(a[a.length - 1].toLowerCase())).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    private String getTabCompletionActionBar(final CommandNode<?, ?> obj, final CommandNode<?, ?> commandNode, final Arguments arguments) {
        final StringBuilder sb = new StringBuilder();
        int index = 0;
        final Iterator iterator = commandNode.getNodesOnPath().iterator();
        while (iterator.hasNext()) {
            final String usage = iterator.next().getArgument().getUsage();
            final String str = (index < arguments.size()) ? arguments.get(index) : null;
            if (index == arguments.size() - 1) {
                sb.append(str.isEmpty() ? " §a" : " §e").append(str.isEmpty() ? usage : str);
            }
            else if (index < arguments.size()) {
                sb.append(" §7").append(str);
            }
            else {
                sb.append(" §7").append(usage);
            }
            ++index;
        }
        final String s = arguments.get(arguments.size() - 1);
        return "§7/" + sb.toString().trim() + ((commandNode.equals(obj) && !s.isEmpty() && ((Argument)obj.getArgument()).hasMatch(s)) ? "§a§l \u2713" : "");
    }
    
    private void register() {
        try {
            final Field declaredField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            declaredField.setAccessible(true);
            ((CommandMap)declaredField.get(Bukkit.getServer())).register("command", (org.bukkit.command.Command)this);
            this.isCurrentlyRegistered = true;
        }
        catch (NoSuchFieldException | IllegalAccessException ex) {
            final Throwable t;
            t.printStackTrace();
        }
    }
    
    private void unregister() {
        try {
            final Field declaredField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            declaredField.setAccessible(true);
            final CommandMap commandMap = (CommandMap)declaredField.get(Bukkit.getServer());
            final Field field2 = Arrays.stream(ArrayUtils.addAll(commandMap.getClass().getDeclaredFields(), commandMap.getClass().getSuperclass().getDeclaredFields())).filter(field -> field.getName().equals("knownCommands")).findAny().get();
            field2.setAccessible(true);
            final Map value = (Map)field2.get(commandMap);
            value.remove(this.getName());
            field2.set(commandMap, value);
            this.isCurrentlyRegistered = false;
        }
        catch (NoSuchFieldException | IllegalAccessException ex) {
            final Throwable t;
            t.printStackTrace();
        }
    }
    
    public abstract boolean isRegistered();
}
