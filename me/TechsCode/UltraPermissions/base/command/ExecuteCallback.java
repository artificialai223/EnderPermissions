

package me.TechsCode.EnderPermissions.base.command;

public class ExecuteCallback
{
    private CommandNode<?, ?> lastNode;
    private ExecuteResult result;
    
    public ExecuteCallback(final CommandNode<?, ?> lastNode, final ExecuteResult result) {
        this.lastNode = lastNode;
        this.result = result;
    }
    
    public CommandNode<?, ?> getLastNode() {
        return this.lastNode;
    }
    
    public ExecuteResult getResult() {
        return this.result;
    }
}
