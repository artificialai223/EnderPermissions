

package me.TechsCode.EnderPermissions.dependencies.hikari.pool;

import me.TechsCode.EnderPermissions.dependencies.hikari.util.FastList;
import java.util.concurrent.Executor;
import java.sql.Struct;
import java.sql.Array;
import java.util.Properties;
import java.sql.SQLXML;
import java.sql.NClob;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Savepoint;
import java.util.Map;
import java.sql.SQLWarning;
import java.sql.DatabaseMetaData;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Wrapper;

public class HikariProxyConnection extends ProxyConnection implements Wrapper, Connection, AutoCloseable
{
    @Override
    public Statement createStatement() {
        try {
            return super.createStatement();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String s) {
        try {
            return super.prepareStatement(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public CallableStatement prepareCall(final String s) {
        try {
            return super.prepareCall(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public String nativeSQL(final String s) {
        try {
            return super.delegate.nativeSQL(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setAutoCommit(final boolean autoCommit) {
        try {
            super.setAutoCommit(autoCommit);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean getAutoCommit() {
        try {
            return super.delegate.getAutoCommit();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void commit() {
        try {
            super.commit();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void rollback() {
        try {
            super.rollback();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isClosed() {
        try {
            return super.isClosed();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public DatabaseMetaData getMetaData() {
        try {
            return super.delegate.getMetaData();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setReadOnly(final boolean readOnly) {
        try {
            super.setReadOnly(readOnly);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isReadOnly() {
        try {
            return super.delegate.isReadOnly();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setCatalog(final String catalog) {
        try {
            super.setCatalog(catalog);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public String getCatalog() {
        try {
            return super.delegate.getCatalog();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setTransactionIsolation(final int transactionIsolation) {
        try {
            super.setTransactionIsolation(transactionIsolation);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getTransactionIsolation() {
        try {
            return super.delegate.getTransactionIsolation();
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
    public Statement createStatement(final int n, final int n2) {
        try {
            return super.createStatement(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String s, final int n, final int n2) {
        try {
            return super.prepareStatement(s, n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public CallableStatement prepareCall(final String s, final int n, final int n2) {
        try {
            return super.prepareCall(s, n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Map getTypeMap() {
        try {
            return super.delegate.getTypeMap();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setTypeMap(final Map typeMap) {
        try {
            super.delegate.setTypeMap(typeMap);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setHoldability(final int holdability) {
        try {
            super.delegate.setHoldability(holdability);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getHoldability() {
        try {
            return super.delegate.getHoldability();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Savepoint setSavepoint() {
        try {
            return super.delegate.setSavepoint();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Savepoint setSavepoint(final String savepoint) {
        try {
            return super.delegate.setSavepoint(savepoint);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void rollback(final Savepoint savepoint) {
        try {
            super.rollback(savepoint);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void releaseSavepoint(final Savepoint savepoint) {
        try {
            super.delegate.releaseSavepoint(savepoint);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Statement createStatement(final int n, final int n2, final int n3) {
        try {
            return super.createStatement(n, n2, n3);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String s, final int n, final int n2, final int n3) {
        try {
            return super.prepareStatement(s, n, n2, n3);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public CallableStatement prepareCall(final String s, final int n, final int n2, final int n3) {
        try {
            return super.prepareCall(s, n, n2, n3);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String s, final int n) {
        try {
            return super.prepareStatement(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String s, final int[] array) {
        try {
            return super.prepareStatement(s, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String s, final String[] array) {
        try {
            return super.prepareStatement(s, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Clob createClob() {
        try {
            return super.delegate.createClob();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Blob createBlob() {
        try {
            return super.delegate.createBlob();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public NClob createNClob() {
        try {
            return super.delegate.createNClob();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public SQLXML createSQLXML() {
        try {
            return super.delegate.createSQLXML();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isValid(final int n) {
        try {
            return super.delegate.isValid(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setClientInfo(final String s, final String s2) {
        super.delegate.setClientInfo(s, s2);
    }
    
    @Override
    public void setClientInfo(final Properties clientInfo) {
        super.delegate.setClientInfo(clientInfo);
    }
    
    @Override
    public String getClientInfo(final String s) {
        try {
            return super.delegate.getClientInfo(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Properties getClientInfo() {
        try {
            return super.delegate.getClientInfo();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Array createArrayOf(final String s, final Object[] array) {
        try {
            return super.delegate.createArrayOf(s, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Struct createStruct(final String s, final Object[] array) {
        try {
            return super.delegate.createStruct(s, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setSchema(final String schema) {
        try {
            super.delegate.setSchema(schema);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public String getSchema() {
        try {
            return super.delegate.getSchema();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void abort(final Executor executor) {
        try {
            super.delegate.abort(executor);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNetworkTimeout(final Executor executor, final int n) {
        try {
            super.setNetworkTimeout(executor, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getNetworkTimeout() {
        try {
            return super.delegate.getNetworkTimeout();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    protected HikariProxyConnection(final PoolEntry poolEntry, final Connection connection, final FastList list, final ProxyLeakTask proxyLeakTask, final long n, final boolean b, final boolean b2) {
        super(poolEntry, connection, list, proxyLeakTask, n, b, b2);
    }
}
