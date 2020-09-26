

package me.TechsCode.EnderPermissions.dependencies.hikari.pool;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public abstract class ProxyPreparedStatement extends ProxyStatement implements PreparedStatement
{
    protected ProxyPreparedStatement(final ProxyConnection proxyConnection, final PreparedStatement preparedStatement) {
        super(proxyConnection, preparedStatement);
    }
    
    @Override
    public boolean execute() {
        this.connection.markCommitStateDirty();
        return ((PreparedStatement)this.delegate).execute();
    }
    
    @Override
    public ResultSet executeQuery() {
        this.connection.markCommitStateDirty();
        return ProxyFactory.getProxyResultSet(this.connection, this, ((PreparedStatement)this.delegate).executeQuery());
    }
    
    @Override
    public int executeUpdate() {
        this.connection.markCommitStateDirty();
        return ((PreparedStatement)this.delegate).executeUpdate();
    }
    
    @Override
    public long executeLargeUpdate() {
        this.connection.markCommitStateDirty();
        return ((PreparedStatement)this.delegate).executeLargeUpdate();
    }
}
