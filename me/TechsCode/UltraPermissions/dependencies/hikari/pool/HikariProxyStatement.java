

package me.TechsCode.EnderPermissions.dependencies.hikari.pool;

import java.sql.Connection;
import java.sql.SQLWarning;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Wrapper;
import java.sql.Statement;

public class HikariProxyStatement extends ProxyStatement implements Statement, Wrapper, AutoCloseable
{
    @Override
    public ResultSet executeQuery(final String s) {
        try {
            return super.executeQuery(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int executeUpdate(final String s) {
        try {
            return super.executeUpdate(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getMaxFieldSize() {
        try {
            return super.delegate.getMaxFieldSize();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setMaxFieldSize(final int maxFieldSize) {
        try {
            super.delegate.setMaxFieldSize(maxFieldSize);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getMaxRows() {
        try {
            return super.delegate.getMaxRows();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setMaxRows(final int maxRows) {
        try {
            super.delegate.setMaxRows(maxRows);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setEscapeProcessing(final boolean escapeProcessing) {
        try {
            super.delegate.setEscapeProcessing(escapeProcessing);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getQueryTimeout() {
        try {
            return super.delegate.getQueryTimeout();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setQueryTimeout(final int queryTimeout) {
        try {
            super.delegate.setQueryTimeout(queryTimeout);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void cancel() {
        try {
            super.delegate.cancel();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public SQLWarning getWarnings() {
        try {
            return super.delegate.getWarnings();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void clearWarnings() {
        try {
            super.delegate.clearWarnings();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setCursorName(final String cursorName) {
        try {
            super.delegate.setCursorName(cursorName);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean execute(final String s) {
        try {
            return super.execute(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public ResultSet getResultSet() {
        try {
            return super.getResultSet();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getUpdateCount() {
        try {
            return super.delegate.getUpdateCount();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean getMoreResults() {
        try {
            return super.delegate.getMoreResults();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setFetchDirection(final int fetchDirection) {
        try {
            super.delegate.setFetchDirection(fetchDirection);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getFetchDirection() {
        try {
            return super.delegate.getFetchDirection();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setFetchSize(final int fetchSize) {
        try {
            super.delegate.setFetchSize(fetchSize);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getFetchSize() {
        try {
            return super.delegate.getFetchSize();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getResultSetConcurrency() {
        try {
            return super.delegate.getResultSetConcurrency();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getResultSetType() {
        try {
            return super.delegate.getResultSetType();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void addBatch(final String s) {
        try {
            super.delegate.addBatch(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void clearBatch() {
        try {
            super.delegate.clearBatch();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int[] executeBatch() {
        try {
            return super.executeBatch();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Connection getConnection() {
        try {
            return super.getConnection();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean getMoreResults(final int n) {
        try {
            return super.delegate.getMoreResults(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public ResultSet getGeneratedKeys() {
        try {
            return super.delegate.getGeneratedKeys();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int executeUpdate(final String s, final int n) {
        try {
            return super.executeUpdate(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int executeUpdate(final String s, final int[] array) {
        try {
            return super.executeUpdate(s, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int executeUpdate(final String s, final String[] array) {
        try {
            return super.executeUpdate(s, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean execute(final String s, final int n) {
        try {
            return super.execute(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean execute(final String s, final int[] array) {
        try {
            return super.execute(s, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean execute(final String s, final String[] array) {
        try {
            return super.execute(s, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getResultSetHoldability() {
        try {
            return super.delegate.getResultSetHoldability();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isClosed() {
        try {
            return super.delegate.isClosed();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setPoolable(final boolean poolable) {
        try {
            super.delegate.setPoolable(poolable);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isPoolable() {
        try {
            return super.delegate.isPoolable();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void closeOnCompletion() {
        try {
            super.delegate.closeOnCompletion();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isCloseOnCompletion() {
        try {
            return super.delegate.isCloseOnCompletion();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isWrapperFor(final Class clazz) {
        try {
            return super.delegate.isWrapperFor(clazz);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    protected HikariProxyStatement(final ProxyConnection proxyConnection, final Statement statement) {
        super(proxyConnection, statement);
    }
}
