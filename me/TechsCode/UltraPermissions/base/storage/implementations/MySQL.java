

package me.TechsCode.EnderPermissions.base.storage.implementations;

import java.sql.Blob;
import java.sql.ResultSet;
import com.google.gson.JsonSyntaxException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.DecoderException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.Hex;
import java.util.HashMap;
import com.google.gson.JsonParser;
import me.TechsCode.EnderPermissions.base.storage.ReadCallback;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.Base64;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonObject;
import me.TechsCode.EnderPermissions.base.storage.WriteCallback;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import java.util.regex.Pattern;
import me.TechsCode.EnderPermissions.base.storage.StorageImplementation;

public class MySQL extends StorageImplementation
{
    private static final Pattern REGEX_PATTERN;
    private String tableName;
    private MySQLQueue queue;
    
    public MySQL(final TechPlugin techPlugin, final String str) {
        super(techPlugin, str, true);
        this.tableName = techPlugin.getName() + "_" + str;
        this.queue = new MySQLQueue(techPlugin);
        try {
            final Connection connection = techPlugin.getMySQLManager().newConnection();
            final PreparedStatement prepareStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `" + this.tableName + "` ( `key` VARCHAR(64), `value` MEDIUMBLOB);");
            prepareStatement.execute();
            prepareStatement.close();
            connection.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void destroy(final String str, final WriteCallback writeCallback) {
        this.queue.update("DELETE FROM " + this.tableName + " WHERE `key`='" + str + "';", writeCallback);
    }
    
    @Override
    public void update(final String str, final JsonObject jsonObject, final WriteCallback writeCallback) {
        this.queue.update("UPDATE " + this.tableName + " SET `value` = '" + this.jsonToString(jsonObject) + "' WHERE `key`='" + str + "';", writeCallback);
    }
    
    @Override
    public void create(final String str, final JsonObject jsonObject, final WriteCallback writeCallback) {
        this.destroy(str, new WriteCallback.EmptyWriteCallback());
        this.queue.update("INSERT INTO " + this.tableName + " (`key`, `value`) VALUES ('" + str + "', '" + this.jsonToString(jsonObject) + "');", writeCallback);
    }
    
    private String jsonToString(final JsonObject jsonObject) {
        return Base64.encodeBase64String(jsonObject.toString().replace("'", "\\'").getBytes(StandardCharsets.UTF_8));
    }
    
    @Override
    public void read(final String str, final ReadCallback readCallback) {
        final JsonParser jsonParser = new JsonParser();
        try {
            final HashMap<String, JsonObject> hashMap = new HashMap<String, JsonObject>();
            final Connection connection = this.plugin.getMySQLManager().newConnection();
            final ResultSet executeQuery = connection.prepareStatement("SELECT * FROM " + this.tableName + " WHERE " + (str.equals("*") ? "1" : ("`key`='" + str + "';"))).executeQuery();
            while (executeQuery.next()) {
                final String string = executeQuery.getString("key");
                final Blob blob = executeQuery.getBlob("value");
                String input = new String(blob.getBytes(1L, (int)blob.length()));
                if (Base64.isBase64(input)) {
                    input = new String(Base64.decodeBase64(input), StandardCharsets.UTF_8);
                }
                if (MySQL.REGEX_PATTERN.matcher(input).matches()) {
                    try {
                        input = new String(Hex.decodeHex(input), StandardCharsets.UTF_8);
                    }
                    catch (DecoderException ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    hashMap.put(string, (JsonObject)jsonParser.parse(input));
                }
                catch (JsonSyntaxException ex2) {
                    ex2.printStackTrace();
                }
            }
            executeQuery.close();
            connection.close();
            readCallback.onSuccess(hashMap);
        }
        catch (SQLException ex3) {
            readCallback.onFailure(ex3);
        }
    }
    
    static {
        REGEX_PATTERN = Pattern.compile("^\\p{XDigit}+$");
    }
}
