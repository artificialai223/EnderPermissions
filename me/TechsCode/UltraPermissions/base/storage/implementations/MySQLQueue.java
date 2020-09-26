

package me.TechsCode.EnderPermissions.base.storage.implementations;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import me.TechsCode.EnderPermissions.base.storage.WriteCallback;
import java.util.ArrayList;
import java.util.List;
import me.TechsCode.EnderPermissions.base.TechPlugin;

public class MySQLQueue
{
    private TechPlugin plugin;
    private boolean querying;
    private List<QueueEntry> queue;
    
    public MySQLQueue(final TechPlugin plugin) {
        this.plugin = plugin;
        this.querying = false;
        this.queue = new ArrayList<QueueEntry>();
    }
    
    public void update(final String s, final WriteCallback writeCallback) {
        this.queue.add(new QueueEntry(s, writeCallback));
        if (!this.querying) {
            this.processQueue(null);
        }
    }
    
    private void processQueue(final Connection connection) {
        this.querying = true;
        Connection connection2 = connection;
        try {
            if (connection2 == null) {
                connection2 = this.plugin.getMySQLManager().newConnection();
            }
        }
        catch (SQLException ex) {
            this.plugin.log("§cCould not process MySQL Queue:");
            this.plugin.log("§c" + ex.getMessage());
            return;
        }
        final QueueEntry queueEntry;
        final Connection connection3;
        final PreparedStatement preparedStatement;
        this.plugin.getScheduler().runAsync(() -> {
            queueEntry = this.queue.get(0);
            this.queue.remove(0);
            try {
                connection3.prepareStatement(queueEntry.sql);
                preparedStatement.execute();
                preparedStatement.close();
                queueEntry.writeCallback.onSuccess();
                if (this.queue.isEmpty()) {
                    connection3.close();
                    this.querying = false;
                    return;
                }
            }
            catch (SQLException ex2) {
                queueEntry.writeCallback.onFailure(ex2);
            }
            this.processQueue(connection3);
        });
    }
    
    public class QueueEntry
    {
        String sql;
        WriteCallback writeCallback;
        
        public QueueEntry(final String sql, final WriteCallback writeCallback) {
            this.sql = sql;
            this.writeCallback = writeCallback;
        }
    }
}
