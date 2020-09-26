

package me.TechsCode.EnderPermissions.base.command.arguments;

import java.util.Collections;
import java.util.List;
import me.TechsCode.EnderPermissions.base.command.ArgumentValue;
import me.TechsCode.EnderPermissions.base.command.EmptyReason;
import me.TechsCode.EnderPermissions.base.command.Argument;

public class AnyIntegerArgument extends Argument<Integer>
{
    private String usage;
    private EmptyReason emptyReason;
    
    public AnyIntegerArgument(final String usage, final EmptyReason emptyReason) {
        this.usage = usage;
        this.emptyReason = emptyReason;
    }
    
    @Override
    public ArgumentValue<Integer> get(final String s) {
        try {
            return ArgumentValue.of(s, Integer.parseInt(s));
        }
        catch (NumberFormatException ex) {
            return ArgumentValue.empty(s, this.emptyReason);
        }
    }
    
    @Override
    public String getUsage() {
        return this.usage;
    }
    
    @Override
    public List<String> getTabCompletions() {
        return Collections.emptyList();
    }
}
