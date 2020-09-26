

package me.TechsCode.EnderPermissions.base.mysql;

public class ConnectionTestResult
{
    private boolean valid;
    private String error;
    
    public ConnectionTestResult(final boolean valid, final String error) {
        this.valid = valid;
        this.error = error;
    }
    
    public boolean isValid() {
        return this.valid;
    }
    
    public String getError() {
        return this.error;
    }
}
