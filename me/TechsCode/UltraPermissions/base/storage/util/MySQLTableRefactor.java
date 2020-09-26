

package me.TechsCode.EnderPermissions.base.storage.util;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import me.TechsCode.EnderPermissions.base.TechPlugin;

public abstract class MySQLTableRefactor
{
    public MySQLTableRefactor(final TechPlugin techPlugin) {
        if (!techPlugin.getMySQLManager().isEnabled()) {
            return;
        }
        try {
            final Connection connection = techPlugin.getMySQLManager().newConnection();
            for (final RefactorTable refactorTable : this.getRefactors()) {
                if (this.tableExist(connection, refactorTable.from)) {
                    if (this.tableExist(connection, refactorTable.to)) {
                        techPlugin.log("Archiving Table " + refactorTable.to);
                        this.tableRename(connection, refactorTable.to, "OLD_" + refactorTable.to);
                    }
                    techPlugin.log("Renaming Table " + refactorTable.from + " to " + refactorTable.to);
                    this.tableRename(connection, refactorTable.from, refactorTable.to);
                }
            }
            connection.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private boolean tableExist(final Connection connection, final String anObject) {
        boolean b = false;
        try (final ResultSet tables = connection.getMetaData().getTables(null, null, anObject, null)) {
            while (tables.next()) {
                final String string = tables.getString("TABLE_NAME");
                if (string != null && string.equals(anObject)) {
                    b = true;
                    break;
                }
            }
        }
        return b;
    }
    
    private void tableDelete(final Connection connection, final String str) {
        final Statement statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE " + str + ";");
        statement.close();
    }
    
    private void tableRename(final Connection connection, final String str, final String str2) {
        final Statement statement = connection.createStatement();
        statement.executeUpdate("RENAME TABLE " + str + " TO " + str2 + ";");
        statement.close();
    }
    
    public abstract RefactorTable[] getRefactors();
    
    public class RefactorTable
    {
        private String from;
        private String to;
        
        public RefactorTable(final String from, final String to) {
            this.from = from;
            this.to = to;
        }
    }
}
