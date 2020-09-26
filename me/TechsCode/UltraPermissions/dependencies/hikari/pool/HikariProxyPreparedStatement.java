

package me.TechsCode.EnderPermissions.dependencies.hikari.pool;

import java.sql.Connection;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.NClob;
import java.sql.RowId;
import java.sql.ParameterMetaData;
import java.net.URL;
import java.util.Calendar;
import java.sql.ResultSetMetaData;
import java.sql.Array;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.Ref;
import java.io.Reader;
import java.io.InputStream;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Wrapper;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class HikariProxyPreparedStatement extends ProxyPreparedStatement implements PreparedStatement, Statement, Wrapper, AutoCloseable
{
    @Override
    public ResultSet executeQuery() {
        try {
            return super.executeQuery();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int executeUpdate() {
        try {
            return super.executeUpdate();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNull(final int n, final int n2) {
        try {
            ((PreparedStatement)super.delegate).setNull(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBoolean(final int n, final boolean b) {
        try {
            ((PreparedStatement)super.delegate).setBoolean(n, b);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setByte(final int n, final byte b) {
        try {
            ((PreparedStatement)super.delegate).setByte(n, b);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setShort(final int n, final short n2) {
        try {
            ((PreparedStatement)super.delegate).setShort(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setInt(final int n, final int n2) {
        try {
            ((PreparedStatement)super.delegate).setInt(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setLong(final int n, final long n2) {
        try {
            ((PreparedStatement)super.delegate).setLong(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setFloat(final int n, final float n2) {
        try {
            ((PreparedStatement)super.delegate).setFloat(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setDouble(final int n, final double n2) {
        try {
            ((PreparedStatement)super.delegate).setDouble(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBigDecimal(final int n, final BigDecimal bigDecimal) {
        try {
            ((PreparedStatement)super.delegate).setBigDecimal(n, bigDecimal);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setString(final int n, final String s) {
        try {
            ((PreparedStatement)super.delegate).setString(n, s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBytes(final int n, final byte[] array) {
        try {
            ((PreparedStatement)super.delegate).setBytes(n, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setDate(final int n, final Date date) {
        try {
            ((PreparedStatement)super.delegate).setDate(n, date);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setTime(final int n, final Time time) {
        try {
            ((PreparedStatement)super.delegate).setTime(n, time);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setTimestamp(final int n, final Timestamp timestamp) {
        try {
            ((PreparedStatement)super.delegate).setTimestamp(n, timestamp);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setAsciiStream(final int n, final InputStream inputStream, final int n2) {
        try {
            ((PreparedStatement)super.delegate).setAsciiStream(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setUnicodeStream(final int n, final InputStream inputStream, final int n2) {
        try {
            ((PreparedStatement)super.delegate).setUnicodeStream(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBinaryStream(final int n, final InputStream inputStream, final int n2) {
        try {
            ((PreparedStatement)super.delegate).setBinaryStream(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void clearParameters() {
        try {
            ((PreparedStatement)super.delegate).clearParameters();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setObject(final int n, final Object o, final int n2) {
        try {
            ((PreparedStatement)super.delegate).setObject(n, o, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setObject(final int n, final Object o) {
        try {
            ((PreparedStatement)super.delegate).setObject(n, o);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean execute() {
        try {
            return super.execute();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void addBatch() {
        try {
            ((PreparedStatement)super.delegate).addBatch();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setCharacterStream(final int n, final Reader reader, final int n2) {
        try {
            ((PreparedStatement)super.delegate).setCharacterStream(n, reader, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setRef(final int n, final Ref ref) {
        try {
            ((PreparedStatement)super.delegate).setRef(n, ref);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBlob(final int n, final Blob blob) {
        try {
            ((PreparedStatement)super.delegate).setBlob(n, blob);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setClob(final int n, final Clob clob) {
        try {
            ((PreparedStatement)super.delegate).setClob(n, clob);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setArray(final int n, final Array array) {
        try {
            ((PreparedStatement)super.delegate).setArray(n, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public ResultSetMetaData getMetaData() {
        try {
            return ((PreparedStatement)super.delegate).getMetaData();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setDate(final int n, final Date date, final Calendar calendar) {
        try {
            ((PreparedStatement)super.delegate).setDate(n, date, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setTime(final int n, final Time time, final Calendar calendar) {
        try {
            ((PreparedStatement)super.delegate).setTime(n, time, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setTimestamp(final int n, final Timestamp timestamp, final Calendar calendar) {
        try {
            ((PreparedStatement)super.delegate).setTimestamp(n, timestamp, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNull(final int n, final int n2, final String s) {
        try {
            ((PreparedStatement)super.delegate).setNull(n, n2, s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setURL(final int n, final URL url) {
        try {
            ((PreparedStatement)super.delegate).setURL(n, url);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public ParameterMetaData getParameterMetaData() {
        try {
            return ((PreparedStatement)super.delegate).getParameterMetaData();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setRowId(final int n, final RowId rowId) {
        try {
            ((PreparedStatement)super.delegate).setRowId(n, rowId);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNString(final int n, final String s) {
        try {
            ((PreparedStatement)super.delegate).setNString(n, s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNCharacterStream(final int n, final Reader reader, final long n2) {
        try {
            ((PreparedStatement)super.delegate).setNCharacterStream(n, reader, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNClob(final int n, final NClob nClob) {
        try {
            ((PreparedStatement)super.delegate).setNClob(n, nClob);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setClob(final int n, final Reader reader, final long n2) {
        try {
            ((PreparedStatement)super.delegate).setClob(n, reader, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBlob(final int n, final InputStream inputStream, final long n2) {
        try {
            ((PreparedStatement)super.delegate).setBlob(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNClob(final int n, final Reader reader, final long n2) {
        try {
            ((PreparedStatement)super.delegate).setNClob(n, reader, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setSQLXML(final int n, final SQLXML sqlxml) {
        try {
            ((PreparedStatement)super.delegate).setSQLXML(n, sqlxml);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setObject(final int n, final Object o, final int n2, final int n3) {
        try {
            ((PreparedStatement)super.delegate).setObject(n, o, n2, n3);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setAsciiStream(final int n, final InputStream inputStream, final long n2) {
        try {
            ((PreparedStatement)super.delegate).setAsciiStream(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBinaryStream(final int n, final InputStream inputStream, final long n2) {
        try {
            ((PreparedStatement)super.delegate).setBinaryStream(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setCharacterStream(final int n, final Reader reader, final long n2) {
        try {
            ((PreparedStatement)super.delegate).setCharacterStream(n, reader, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setAsciiStream(final int n, final InputStream inputStream) {
        try {
            ((PreparedStatement)super.delegate).setAsciiStream(n, inputStream);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBinaryStream(final int n, final InputStream inputStream) {
        try {
            ((PreparedStatement)super.delegate).setBinaryStream(n, inputStream);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setCharacterStream(final int n, final Reader reader) {
        try {
            ((PreparedStatement)super.delegate).setCharacterStream(n, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNCharacterStream(final int n, final Reader reader) {
        try {
            ((PreparedStatement)super.delegate).setNCharacterStream(n, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setClob(final int n, final Reader reader) {
        try {
            ((PreparedStatement)super.delegate).setClob(n, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBlob(final int n, final InputStream inputStream) {
        try {
            ((PreparedStatement)super.delegate).setBlob(n, inputStream);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNClob(final int n, final Reader reader) {
        try {
            ((PreparedStatement)super.delegate).setNClob(n, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
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
            return ((PreparedStatement)super.delegate).getMaxFieldSize();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setMaxFieldSize(final int maxFieldSize) {
        try {
            ((PreparedStatement)super.delegate).setMaxFieldSize(maxFieldSize);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getMaxRows() {
        try {
            return ((PreparedStatement)super.delegate).getMaxRows();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setMaxRows(final int maxRows) {
        try {
            ((PreparedStatement)super.delegate).setMaxRows(maxRows);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setEscapeProcessing(final boolean escapeProcessing) {
        try {
            ((PreparedStatement)super.delegate).setEscapeProcessing(escapeProcessing);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getQueryTimeout() {
        try {
            return ((PreparedStatement)super.delegate).getQueryTimeout();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setQueryTimeout(final int queryTimeout) {
        try {
            ((PreparedStatement)super.delegate).setQueryTimeout(queryTimeout);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void cancel() {
        try {
            ((PreparedStatement)super.delegate).cancel();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public SQLWarning getWarnings() {
        try {
            return ((PreparedStatement)super.delegate).getWarnings();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void clearWarnings() {
        try {
            ((PreparedStatement)super.delegate).clearWarnings();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setCursorName(final String cursorName) {
        try {
            ((PreparedStatement)super.delegate).setCursorName(cursorName);
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
            return ((PreparedStatement)super.delegate).getUpdateCount();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean getMoreResults() {
        try {
            return ((PreparedStatement)super.delegate).getMoreResults();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setFetchDirection(final int fetchDirection) {
        try {
            ((PreparedStatement)super.delegate).setFetchDirection(fetchDirection);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getFetchDirection() {
        try {
            return ((PreparedStatement)super.delegate).getFetchDirection();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setFetchSize(final int fetchSize) {
        try {
            ((PreparedStatement)super.delegate).setFetchSize(fetchSize);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getFetchSize() {
        try {
            return ((PreparedStatement)super.delegate).getFetchSize();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getResultSetConcurrency() {
        try {
            return ((PreparedStatement)super.delegate).getResultSetConcurrency();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getResultSetType() {
        try {
            return ((PreparedStatement)super.delegate).getResultSetType();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void addBatch(final String s) {
        try {
            ((PreparedStatement)super.delegate).addBatch(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void clearBatch() {
        try {
            ((PreparedStatement)super.delegate).clearBatch();
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
            return ((PreparedStatement)super.delegate).getMoreResults(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public ResultSet getGeneratedKeys() {
        try {
            return ((PreparedStatement)super.delegate).getGeneratedKeys();
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
            return ((PreparedStatement)super.delegate).getResultSetHoldability();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isClosed() {
        try {
            return ((PreparedStatement)super.delegate).isClosed();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setPoolable(final boolean poolable) {
        try {
            ((PreparedStatement)super.delegate).setPoolable(poolable);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isPoolable() {
        try {
            return ((PreparedStatement)super.delegate).isPoolable();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void closeOnCompletion() {
        try {
            ((PreparedStatement)super.delegate).closeOnCompletion();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isCloseOnCompletion() {
        try {
            return ((PreparedStatement)super.delegate).isCloseOnCompletion();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isWrapperFor(final Class clazz) {
        try {
            return ((PreparedStatement)super.delegate).isWrapperFor(clazz);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    protected HikariProxyPreparedStatement(final ProxyConnection proxyConnection, final PreparedStatement preparedStatement) {
        super(proxyConnection, preparedStatement);
    }
}
