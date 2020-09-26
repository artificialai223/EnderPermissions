

package me.TechsCode.EnderPermissions.base.command.arguments;

import java.util.List;
import me.TechsCode.EnderPermissions.base.command.EmptyReason;
import java.util.Arrays;
import me.TechsCode.EnderPermissions.base.command.ArgumentValue;
import me.TechsCode.EnderPermissions.base.command.Argument;

public class SpecificArguments extends Argument<String>
{
    private final String[] arguments;
    
    public SpecificArguments(final String[] arguments) {
        this.arguments = arguments;
    }
    
    @Override
    public ArgumentValue<String> get(final String anotherString) {
        return Arrays.stream(this.arguments).filter(s -> s.equalsIgnoreCase(anotherString)).findFirst().map(s2 -> ArgumentValue.of(anotherString, s2)).orElse(ArgumentValue.empty(anotherString, EmptyReason.NO_MATCH));
    }
    
    @Override
    public String getUsage() {
        return "<" + String.join(" / ", (CharSequence[])this.arguments) + ">";
    }
    
    @Override
    public List<String> getTabCompletions() {
        return Arrays.asList(this.arguments);
    }
}
