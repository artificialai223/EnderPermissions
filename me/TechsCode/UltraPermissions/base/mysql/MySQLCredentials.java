

package me.TechsCode.EnderPermissions.base.mysql;

public class MySQLCredentials
{
    private String hostname;
    private String port;
    private String database;
    private String username;
    private String password;
    
    public MySQLCredentials(final String hostname, final String port, final String database, final String username, final String password) {
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }
    
    public String getHostname() {
        return this.hostname;
    }
    
    public void setHostname(final String hostname) {
        this.hostname = hostname;
    }
    
    public String getPort() {
        return this.port;
    }
    
    public void setPort(final String port) {
        this.port = port;
    }
    
    public String getDatabase() {
        return this.database;
    }
    
    public void setDatabase(final String database) {
        this.database = database;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
}
