

package me.TechsCode.EnderPermissions.base.mysql;

import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.base.registry.RegistryStorable;

public class MySQLRegistry extends RegistryStorable
{
    private String hostname;
    private String port;
    private String database;
    private String username;
    private String password;
    private boolean ssl;
    private int minimumIdle;
    private int maximumPoolSize;
    
    public MySQLRegistry() {
        super("mysql");
        this.hostname = "";
        this.port = "";
        this.database = "";
        this.username = "";
        this.password = "";
        this.ssl = false;
        this.minimumIdle = 10;
        this.maximumPoolSize = 10;
    }
    
    @Override
    public void setState(final JsonObject jsonObject) {
        this.hostname = jsonObject.get("hostname").getAsString();
        this.port = jsonObject.get("port").getAsString();
        this.database = jsonObject.get("database").getAsString();
        this.username = jsonObject.get("username").getAsString();
        this.password = jsonObject.get("password").getAsString();
        this.ssl = (jsonObject.has("ssl") && jsonObject.get("ssl").getAsBoolean());
        this.minimumIdle = (jsonObject.has("minimumIdle") ? jsonObject.get("minimumIdle").getAsInt() : 10);
        this.maximumPoolSize = (jsonObject.has("maximumPoolSize") ? jsonObject.get("maximumPoolSize").getAsInt() : 10);
    }
    
    @Override
    public JsonObject getState() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("hostname", this.hostname);
        jsonObject.addProperty("port", this.port);
        jsonObject.addProperty("database", this.database);
        jsonObject.addProperty("username", this.username);
        jsonObject.addProperty("password", this.password);
        jsonObject.addProperty("ssl", Boolean.valueOf(this.ssl));
        jsonObject.addProperty("minimumIdle", (Number)this.minimumIdle);
        jsonObject.addProperty("maximumPoolSize", (Number)this.maximumPoolSize);
        return jsonObject;
    }
    
    public void updateState(final JsonObject state) {
        this.setState(state);
        this.sync();
    }
    
    public boolean hasCredentials() {
        return !this.hostname.isEmpty() && !this.port.isEmpty() && !this.database.isEmpty() && !this.username.isEmpty() && !this.password.isEmpty();
    }
    
    public void setCredentials(final MySQLCredentials mySQLCredentials) {
        if (mySQLCredentials == null) {
            this.clearCredentials();
            return;
        }
        this.hostname = mySQLCredentials.getHostname();
        this.port = mySQLCredentials.getPort();
        this.database = mySQLCredentials.getDatabase();
        this.username = mySQLCredentials.getUsername();
        this.password = mySQLCredentials.getPassword();
        this.sync();
    }
    
    public void clearCredentials() {
        this.hostname = "";
        this.port = "";
        this.database = "";
        this.username = "";
        this.password = "";
        this.sync();
    }
    
    public MySQLCredentials getCredentials() {
        return this.hasCredentials() ? new MySQLCredentials(this.hostname, this.port, this.database, this.username, this.password) : null;
    }
    
    public boolean hasSSL() {
        return this.ssl;
    }
    
    public void setSSL(final boolean ssl) {
        this.ssl = ssl;
        this.sync();
    }
    
    public int getMinimumIdle() {
        return this.minimumIdle;
    }
    
    public void setMinimumIdle(final int minimumIdle) {
        this.minimumIdle = minimumIdle;
        this.sync();
    }
    
    public int getMaximumPoolSize() {
        return this.maximumPoolSize;
    }
    
    public void setMaximumPoolSize(final int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
        this.sync();
    }
}
