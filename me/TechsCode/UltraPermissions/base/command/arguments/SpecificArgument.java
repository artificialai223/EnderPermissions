

package me.TechsCode.EnderPermissions.base.command.arguments;

import java.util.Collections;
import java.util.List;
import me.TechsCode.EnderPermissions.base.command.EmptyReason;
import me.TechsCode.EnderPermissions.base.command.ArgumentValue;
import me.TechsCode.EnderPermissions.base.command.Argument;

public class SpecificArgument extends Argument<String>
{
    private String argument;
    
    public SpecificArgument(final String argument) {
        this.argument = argument;
    }
    
    @Override
    public String getUsage() {
        return this.argument;
    }
    
    @Override
    public ArgumentValue<String> get(final String anotherString) {
        return this.argument.equalsIgnoreCase(anotherString) ? ArgumentValue.of(anotherString, this.argument) : ArgumentValue.empty(anotherString, EmptyReason.NO_MATCH);
    }
    
    @Override
    public List<String> getTabCompletions() {
        return Collections.singletonList(this.argument);
    }
}
