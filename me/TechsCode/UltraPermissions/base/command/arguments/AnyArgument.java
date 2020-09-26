

package me.TechsCode.EnderPermissions.base.command.arguments;

import java.util.Collections;
import java.util.List;
import me.TechsCode.EnderPermissions.base.command.ArgumentValue;
import me.TechsCode.EnderPermissions.base.command.Argument;

public class AnyArgument extends Argument<String>
{
    private String usage;
    
    public AnyArgument(final String usage) {
        this.usage = usage;
    }
    
    @Override
    public String getUsage() {
        return this.usage;
    }
    
    @Override
    public ArgumentValue<String> get(final String s) {
        return ArgumentValue.of(s, s);
    }
    
    @Override
    public List<String> getTabCompletions() {
        return Collections.emptyList();
    }
}
