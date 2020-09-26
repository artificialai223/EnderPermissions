

package me.TechsCode.EnderPermissions.dependencies.hikari.pool;

import java.sql.Wrapper;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

public abstract class ProxyResultSet implements ResultSet
{
    protected final ProxyConnection connection;
    protected final ProxyStatement statement;
    protected final ResultSet delegate;
    
    protected ProxyResultSet(final ProxyConnection connection, final ProxyStatement statement, final ResultSet delegate) {
        this.connection = connection;
        this.statement = statement;
        this.delegate = delegate;
    }
    
    final SQLException checkException(final SQLException ex) {
        return this.connection.checkException(ex);
    }
    
    @Override
    public String toString() {
        return new StringBuilder(64).append(this.getClass().getSimpleName()).append('@').append(System.identityHashCode(this)).append(" wrapping ").append(this.delegate).toString();
    }
    
    @Override
    public final Statement getStatement() {
        return this.statement;
    }
    
    @Override
    public void updateRow() {
        this.connection.markCommitStateDirty();
        this.delegate.updateRow();
    }
    
    @Override
    public void insertRow() {
        this.connection.markCommitStateDirty();
        this.delegate.insertRow();
    }
    
    @Override
    public void deleteRow() {
        this.connection.markCommitStateDirty();
        this.delegate.deleteRow();
    }
    
    @Override
    public final <T> T unwrap(final Class<T> obj) {
        if (obj.isInstance(this.delegate)) {
            return (T)this.delegate;
        }
        if (this.delegate instanceof Wrapper) {
            return this.delegate.unwrap(obj);
        }
        throw new SQLException("Wrapped ResultSet is not an instance of " + obj);
    }
}
