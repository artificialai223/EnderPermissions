

package me.TechsCode.EnderPermissions.base.legacySystemStorage;

import me.TechsCode.EnderPermissions.base.mysql.MySQLCredentials;
import java.util.HashMap;

public class LegacyMySQLSettings
{
    private String host;
    private String port;
    private String database;
    private String username;
    private String password;
    
    public LegacyMySQLSettings(final String host, final String port, final String database, final String username, final String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public String getPort() {
        return this.port;
    }
    
    public String getDatabase() {
        return this.database;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setHost(final String host) {
        this.host = host;
    }
    
    public void setPort(final String port) {
        this.port = port;
    }
    
    public void setDatabase(final String database) {
        this.database = database;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public void set(final String s, final String password) {
        switch (s) {
            case "host": {
                this.setHost(password);
                break;
            }
            case "port": {
                this.setPort(password);
                break;
            }
            case "database": {
                this.setDatabase(password);
                break;
            }
            case "username": {
                this.setUsername(password);
                break;
            }
            case "password": {
                this.setPassword(password);
                break;
            }
        }
    }
    
    public HashMap<String, String> serialize() {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("host", this.host);
        hashMap.put("port", this.port);
        hashMap.put("database", this.database);
        hashMap.put("username", this.username);
        hashMap.put("password", this.password);
        return hashMap;
    }
    
    public static LegacyMySQLSettings deserialize(final HashMap<String, String> hashMap) {
        return new LegacyMySQLSettings(hashMap.get("host"), hashMap.get("port"), hashMap.get("database"), hashMap.get("username"), hashMap.get("password"));
    }
    
    public MySQLCredentials asCredentials() {
        return new MySQLCredentials(this.host, this.port, this.database, this.username, this.password);
    }
}
