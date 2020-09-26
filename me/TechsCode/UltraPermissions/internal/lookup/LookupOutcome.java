

package me.TechsCode.EnderPermissions.internal.lookup;

public class LookupOutcome
{
    private boolean result;
    private String source;
    
    public LookupOutcome(final boolean result, final String source) {
        this.result = result;
        this.source = source;
    }
    
    public boolean getResult() {
        return this.result;
    }
    
    public boolean hasSource() {
        return this.source != null;
    }
    
    public String getSource() {
        return this.source;
    }
}
