

package me.TechsCode.EnderPermissions.dependencies.hikari.pool;

import java.util.Map;
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
import java.sql.CallableStatement;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class HikariProxyCallableStatement extends ProxyCallableStatement implements PreparedStatement, Statement, CallableStatement, Wrapper, AutoCloseable
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
            ((CallableStatement)super.delegate).setNull(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBoolean(final int n, final boolean b) {
        try {
            ((CallableStatement)super.delegate).setBoolean(n, b);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setByte(final int n, final byte b) {
        try {
            ((CallableStatement)super.delegate).setByte(n, b);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setShort(final int n, final short n2) {
        try {
            ((CallableStatement)super.delegate).setShort(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setInt(final int n, final int n2) {
        try {
            ((CallableStatement)super.delegate).setInt(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setLong(final int n, final long n2) {
        try {
            ((CallableStatement)super.delegate).setLong(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setFloat(final int n, final float n2) {
        try {
            ((CallableStatement)super.delegate).setFloat(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setDouble(final int n, final double n2) {
        try {
            ((CallableStatement)super.delegate).setDouble(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBigDecimal(final int n, final BigDecimal bigDecimal) {
        try {
            ((CallableStatement)super.delegate).setBigDecimal(n, bigDecimal);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setString(final int n, final String s) {
        try {
            ((CallableStatement)super.delegate).setString(n, s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBytes(final int n, final byte[] array) {
        try {
            ((CallableStatement)super.delegate).setBytes(n, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setDate(final int n, final Date date) {
        try {
            ((CallableStatement)super.delegate).setDate(n, date);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setTime(final int n, final Time time) {
        try {
            ((CallableStatement)super.delegate).setTime(n, time);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setTimestamp(final int n, final Timestamp timestamp) {
        try {
            ((CallableStatement)super.delegate).setTimestamp(n, timestamp);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setAsciiStream(final int n, final InputStream inputStream, final int n2) {
        try {
            ((CallableStatement)super.delegate).setAsciiStream(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setUnicodeStream(final int n, final InputStream inputStream, final int n2) {
        try {
            ((CallableStatement)super.delegate).setUnicodeStream(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBinaryStream(final int n, final InputStream inputStream, final int n2) {
        try {
            ((CallableStatement)super.delegate).setBinaryStream(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void clearParameters() {
        try {
            ((CallableStatement)super.delegate).clearParameters();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setObject(final int n, final Object o, final int n2) {
        try {
            ((CallableStatement)super.delegate).setObject(n, o, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setObject(final int n, final Object o) {
        try {
            ((CallableStatement)super.delegate).setObject(n, o);
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
            ((CallableStatement)super.delegate).addBatch();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setCharacterStream(final int n, final Reader reader, final int n2) {
        try {
            ((CallableStatement)super.delegate).setCharacterStream(n, reader, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setRef(final int n, final Ref ref) {
        try {
            ((CallableStatement)super.delegate).setRef(n, ref);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBlob(final int n, final Blob blob) {
        try {
            ((CallableStatement)super.delegate).setBlob(n, blob);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setClob(final int n, final Clob clob) {
        try {
            ((CallableStatement)super.delegate).setClob(n, clob);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setArray(final int n, final Array array) {
        try {
            ((CallableStatement)super.delegate).setArray(n, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public ResultSetMetaData getMetaData() {
        try {
            return ((CallableStatement)super.delegate).getMetaData();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setDate(final int n, final Date date, final Calendar calendar) {
        try {
            ((CallableStatement)super.delegate).setDate(n, date, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setTime(final int n, final Time time, final Calendar calendar) {
        try {
            ((CallableStatement)super.delegate).setTime(n, time, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setTimestamp(final int n, final Timestamp timestamp, final Calendar calendar) {
        try {
            ((CallableStatement)super.delegate).setTimestamp(n, timestamp, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNull(final int n, final int n2, final String s) {
        try {
            ((CallableStatement)super.delegate).setNull(n, n2, s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setURL(final int n, final URL url) {
        try {
            ((CallableStatement)super.delegate).setURL(n, url);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public ParameterMetaData getParameterMetaData() {
        try {
            return ((CallableStatement)super.delegate).getParameterMetaData();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setRowId(final int n, final RowId rowId) {
        try {
            ((CallableStatement)super.delegate).setRowId(n, rowId);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNString(final int n, final String s) {
        try {
            ((CallableStatement)super.delegate).setNString(n, s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNCharacterStream(final int n, final Reader reader, final long n2) {
        try {
            ((CallableStatement)super.delegate).setNCharacterStream(n, reader, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNClob(final int n, final NClob nClob) {
        try {
            ((CallableStatement)super.delegate).setNClob(n, nClob);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setClob(final int n, final Reader reader, final long n2) {
        try {
            ((CallableStatement)super.delegate).setClob(n, reader, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBlob(final int n, final InputStream inputStream, final long n2) {
        try {
            ((CallableStatement)super.delegate).setBlob(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNClob(final int n, final Reader reader, final long n2) {
        try {
            ((CallableStatement)super.delegate).setNClob(n, reader, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setSQLXML(final int n, final SQLXML sqlxml) {
        try {
            ((CallableStatement)super.delegate).setSQLXML(n, sqlxml);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setObject(final int n, final Object o, final int n2, final int n3) {
        try {
            ((CallableStatement)super.delegate).setObject(n, o, n2, n3);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setAsciiStream(final int n, final InputStream inputStream, final long n2) {
        try {
            ((CallableStatement)super.delegate).setAsciiStream(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBinaryStream(final int n, final InputStream inputStream, final long n2) {
        try {
            ((CallableStatement)super.delegate).setBinaryStream(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setCharacterStream(final int n, final Reader reader, final long n2) {
        try {
            ((CallableStatement)super.delegate).setCharacterStream(n, reader, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setAsciiStream(final int n, final InputStream inputStream) {
        try {
            ((CallableStatement)super.delegate).setAsciiStream(n, inputStream);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBinaryStream(final int n, final InputStream inputStream) {
        try {
            ((CallableStatement)super.delegate).setBinaryStream(n, inputStream);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setCharacterStream(final int n, final Reader reader) {
        try {
            ((CallableStatement)super.delegate).setCharacterStream(n, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNCharacterStream(final int n, final Reader reader) {
        try {
            ((CallableStatement)super.delegate).setNCharacterStream(n, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setClob(final int n, final Reader reader) {
        try {
            ((CallableStatement)super.delegate).setClob(n, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBlob(final int n, final InputStream inputStream) {
        try {
            ((CallableStatement)super.delegate).setBlob(n, inputStream);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNClob(final int n, final Reader reader) {
        try {
            ((CallableStatement)super.delegate).setNClob(n, reader);
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
            return ((CallableStatement)super.delegate).getMaxFieldSize();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setMaxFieldSize(final int maxFieldSize) {
        try {
            ((CallableStatement)super.delegate).setMaxFieldSize(maxFieldSize);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getMaxRows() {
        try {
            return ((CallableStatement)super.delegate).getMaxRows();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setMaxRows(final int maxRows) {
        try {
            ((CallableStatement)super.delegate).setMaxRows(maxRows);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setEscapeProcessing(final boolean escapeProcessing) {
        try {
            ((CallableStatement)super.delegate).setEscapeProcessing(escapeProcessing);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getQueryTimeout() {
        try {
            return ((CallableStatement)super.delegate).getQueryTimeout();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setQueryTimeout(final int queryTimeout) {
        try {
            ((CallableStatement)super.delegate).setQueryTimeout(queryTimeout);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void cancel() {
        try {
            ((CallableStatement)super.delegate).cancel();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public SQLWarning getWarnings() {
        try {
            return ((CallableStatement)super.delegate).getWarnings();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void clearWarnings() {
        try {
            ((CallableStatement)super.delegate).clearWarnings();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setCursorName(final String cursorName) {
        try {
            ((CallableStatement)super.delegate).setCursorName(cursorName);
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
            return ((CallableStatement)super.delegate).getUpdateCount();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean getMoreResults() {
        try {
            return ((CallableStatement)super.delegate).getMoreResults();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setFetchDirection(final int fetchDirection) {
        try {
            ((CallableStatement)super.delegate).setFetchDirection(fetchDirection);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getFetchDirection() {
        try {
            return ((CallableStatement)super.delegate).getFetchDirection();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setFetchSize(final int fetchSize) {
        try {
            ((CallableStatement)super.delegate).setFetchSize(fetchSize);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getFetchSize() {
        try {
            return ((CallableStatement)super.delegate).getFetchSize();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getResultSetConcurrency() {
        try {
            return ((CallableStatement)super.delegate).getResultSetConcurrency();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getResultSetType() {
        try {
            return ((CallableStatement)super.delegate).getResultSetType();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void addBatch(final String s) {
        try {
            ((CallableStatement)super.delegate).addBatch(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void clearBatch() {
        try {
            ((CallableStatement)super.delegate).clearBatch();
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
            return ((CallableStatement)super.delegate).getMoreResults(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public ResultSet getGeneratedKeys() {
        try {
            return ((CallableStatement)super.delegate).getGeneratedKeys();
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
            return ((CallableStatement)super.delegate).getResultSetHoldability();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isClosed() {
        try {
            return ((CallableStatement)super.delegate).isClosed();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setPoolable(final boolean poolable) {
        try {
            ((CallableStatement)super.delegate).setPoolable(poolable);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isPoolable() {
        try {
            return ((CallableStatement)super.delegate).isPoolable();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void closeOnCompletion() {
        try {
            ((CallableStatement)super.delegate).closeOnCompletion();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isCloseOnCompletion() {
        try {
            return ((CallableStatement)super.delegate).isCloseOnCompletion();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void registerOutParameter(final int n, final int n2) {
        try {
            ((CallableStatement)super.delegate).registerOutParameter(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void registerOutParameter(final int n, final int n2, final int n3) {
        try {
            ((CallableStatement)super.delegate).registerOutParameter(n, n2, n3);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean wasNull() {
        try {
            return ((CallableStatement)super.delegate).wasNull();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public String getString(final int n) {
        try {
            return ((CallableStatement)super.delegate).getString(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean getBoolean(final int n) {
        try {
            return ((CallableStatement)super.delegate).getBoolean(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public byte getByte(final int n) {
        try {
            return ((CallableStatement)super.delegate).getByte(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public short getShort(final int n) {
        try {
            return ((CallableStatement)super.delegate).getShort(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getInt(final int n) {
        try {
            return ((CallableStatement)super.delegate).getInt(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public long getLong(final int n) {
        try {
            return ((CallableStatement)super.delegate).getLong(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public float getFloat(final int n) {
        try {
            return ((CallableStatement)super.delegate).getFloat(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public double getDouble(final int n) {
        try {
            return ((CallableStatement)super.delegate).getDouble(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public BigDecimal getBigDecimal(final int n, final int n2) {
        try {
            return ((CallableStatement)super.delegate).getBigDecimal(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public byte[] getBytes(final int n) {
        try {
            return ((CallableStatement)super.delegate).getBytes(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Date getDate(final int n) {
        try {
            return ((CallableStatement)super.delegate).getDate(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Time getTime(final int n) {
        try {
            return ((CallableStatement)super.delegate).getTime(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Timestamp getTimestamp(final int n) {
        try {
            return ((CallableStatement)super.delegate).getTimestamp(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Object getObject(final int n) {
        try {
            return ((CallableStatement)super.delegate).getObject(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public BigDecimal getBigDecimal(final int n) {
        try {
            return ((CallableStatement)super.delegate).getBigDecimal(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Object getObject(final int n, final Map map) {
        try {
            return ((CallableStatement)super.delegate).getObject(n, map);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Ref getRef(final int n) {
        try {
            return ((CallableStatement)super.delegate).getRef(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Blob getBlob(final int n) {
        try {
            return ((CallableStatement)super.delegate).getBlob(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Clob getClob(final int n) {
        try {
            return ((CallableStatement)super.delegate).getClob(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Array getArray(final int n) {
        try {
            return ((CallableStatement)super.delegate).getArray(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Date getDate(final int n, final Calendar calendar) {
        try {
            return ((CallableStatement)super.delegate).getDate(n, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Time getTime(final int n, final Calendar calendar) {
        try {
            return ((CallableStatement)super.delegate).getTime(n, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Timestamp getTimestamp(final int n, final Calendar calendar) {
        try {
            return ((CallableStatement)super.delegate).getTimestamp(n, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void registerOutParameter(final int n, final int n2, final String s) {
        try {
            ((CallableStatement)super.delegate).registerOutParameter(n, n2, s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void registerOutParameter(final String s, final int n) {
        try {
            ((CallableStatement)super.delegate).registerOutParameter(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void registerOutParameter(final String s, final int n, final int n2) {
        try {
            ((CallableStatement)super.delegate).registerOutParameter(s, n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void registerOutParameter(final String s, final int n, final String s2) {
        try {
            ((CallableStatement)super.delegate).registerOutParameter(s, n, s2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public URL getURL(final int n) {
        try {
            return ((CallableStatement)super.delegate).getURL(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setURL(final String s, final URL url) {
        try {
            ((CallableStatement)super.delegate).setURL(s, url);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNull(final String s, final int n) {
        try {
            ((CallableStatement)super.delegate).setNull(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBoolean(final String s, final boolean b) {
        try {
            ((CallableStatement)super.delegate).setBoolean(s, b);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setByte(final String s, final byte b) {
        try {
            ((CallableStatement)super.delegate).setByte(s, b);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setShort(final String s, final short n) {
        try {
            ((CallableStatement)super.delegate).setShort(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setInt(final String s, final int n) {
        try {
            ((CallableStatement)super.delegate).setInt(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setLong(final String s, final long n) {
        try {
            ((CallableStatement)super.delegate).setLong(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setFloat(final String s, final float n) {
        try {
            ((CallableStatement)super.delegate).setFloat(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setDouble(final String s, final double n) {
        try {
            ((CallableStatement)super.delegate).setDouble(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBigDecimal(final String s, final BigDecimal bigDecimal) {
        try {
            ((CallableStatement)super.delegate).setBigDecimal(s, bigDecimal);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setString(final String s, final String s2) {
        try {
            ((CallableStatement)super.delegate).setString(s, s2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBytes(final String s, final byte[] array) {
        try {
            ((CallableStatement)super.delegate).setBytes(s, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setDate(final String s, final Date date) {
        try {
            ((CallableStatement)super.delegate).setDate(s, date);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setTime(final String s, final Time time) {
        try {
            ((CallableStatement)super.delegate).setTime(s, time);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setTimestamp(final String s, final Timestamp timestamp) {
        try {
            ((CallableStatement)super.delegate).setTimestamp(s, timestamp);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setAsciiStream(final String s, final InputStream inputStream, final int n) {
        try {
            ((CallableStatement)super.delegate).setAsciiStream(s, inputStream, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBinaryStream(final String s, final InputStream inputStream, final int n) {
        try {
            ((CallableStatement)super.delegate).setBinaryStream(s, inputStream, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setObject(final String s, final Object o, final int n, final int n2) {
        try {
            ((CallableStatement)super.delegate).setObject(s, o, n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setObject(final String s, final Object o, final int n) {
        try {
            ((CallableStatement)super.delegate).setObject(s, o, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setObject(final String s, final Object o) {
        try {
            ((CallableStatement)super.delegate).setObject(s, o);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setCharacterStream(final String s, final Reader reader, final int n) {
        try {
            ((CallableStatement)super.delegate).setCharacterStream(s, reader, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setDate(final String s, final Date date, final Calendar calendar) {
        try {
            ((CallableStatement)super.delegate).setDate(s, date, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setTime(final String s, final Time time, final Calendar calendar) {
        try {
            ((CallableStatement)super.delegate).setTime(s, time, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setTimestamp(final String s, final Timestamp timestamp, final Calendar calendar) {
        try {
            ((CallableStatement)super.delegate).setTimestamp(s, timestamp, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNull(final String s, final int n, final String s2) {
        try {
            ((CallableStatement)super.delegate).setNull(s, n, s2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public String getString(final String s) {
        try {
            return ((CallableStatement)super.delegate).getString(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean getBoolean(final String s) {
        try {
            return ((CallableStatement)super.delegate).getBoolean(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public byte getByte(final String s) {
        try {
            return ((CallableStatement)super.delegate).getByte(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public short getShort(final String s) {
        try {
            return ((CallableStatement)super.delegate).getShort(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getInt(final String s) {
        try {
            return ((CallableStatement)super.delegate).getInt(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public long getLong(final String s) {
        try {
            return ((CallableStatement)super.delegate).getLong(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public float getFloat(final String s) {
        try {
            return ((CallableStatement)super.delegate).getFloat(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public double getDouble(final String s) {
        try {
            return ((CallableStatement)super.delegate).getDouble(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public byte[] getBytes(final String s) {
        try {
            return ((CallableStatement)super.delegate).getBytes(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Date getDate(final String s) {
        try {
            return ((CallableStatement)super.delegate).getDate(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Time getTime(final String s) {
        try {
            return ((CallableStatement)super.delegate).getTime(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Timestamp getTimestamp(final String s) {
        try {
            return ((CallableStatement)super.delegate).getTimestamp(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Object getObject(final String s) {
        try {
            return ((CallableStatement)super.delegate).getObject(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public BigDecimal getBigDecimal(final String s) {
        try {
            return ((CallableStatement)super.delegate).getBigDecimal(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Object getObject(final String s, final Map map) {
        try {
            return ((CallableStatement)super.delegate).getObject(s, map);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Ref getRef(final String s) {
        try {
            return ((CallableStatement)super.delegate).getRef(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Blob getBlob(final String s) {
        try {
            return ((CallableStatement)super.delegate).getBlob(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Clob getClob(final String s) {
        try {
            return ((CallableStatement)super.delegate).getClob(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Array getArray(final String s) {
        try {
            return ((CallableStatement)super.delegate).getArray(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Date getDate(final String s, final Calendar calendar) {
        try {
            return ((CallableStatement)super.delegate).getDate(s, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Time getTime(final String s, final Calendar calendar) {
        try {
            return ((CallableStatement)super.delegate).getTime(s, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Timestamp getTimestamp(final String s, final Calendar calendar) {
        try {
            return ((CallableStatement)super.delegate).getTimestamp(s, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public URL getURL(final String s) {
        try {
            return ((CallableStatement)super.delegate).getURL(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public RowId getRowId(final int n) {
        try {
            return ((CallableStatement)super.delegate).getRowId(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public RowId getRowId(final String s) {
        try {
            return ((CallableStatement)super.delegate).getRowId(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setRowId(final String s, final RowId rowId) {
        try {
            ((CallableStatement)super.delegate).setRowId(s, rowId);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNString(final String s, final String s2) {
        try {
            ((CallableStatement)super.delegate).setNString(s, s2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNCharacterStream(final String s, final Reader reader, final long n) {
        try {
            ((CallableStatement)super.delegate).setNCharacterStream(s, reader, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNClob(final String s, final NClob nClob) {
        try {
            ((CallableStatement)super.delegate).setNClob(s, nClob);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setClob(final String s, final Reader reader, final long n) {
        try {
            ((CallableStatement)super.delegate).setClob(s, reader, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBlob(final String s, final InputStream inputStream, final long n) {
        try {
            ((CallableStatement)super.delegate).setBlob(s, inputStream, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNClob(final String s, final Reader reader, final long n) {
        try {
            ((CallableStatement)super.delegate).setNClob(s, reader, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public NClob getNClob(final int n) {
        try {
            return ((CallableStatement)super.delegate).getNClob(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public NClob getNClob(final String s) {
        try {
            return ((CallableStatement)super.delegate).getNClob(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setSQLXML(final String s, final SQLXML sqlxml) {
        try {
            ((CallableStatement)super.delegate).setSQLXML(s, sqlxml);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public SQLXML getSQLXML(final int n) {
        try {
            return ((CallableStatement)super.delegate).getSQLXML(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public SQLXML getSQLXML(final String s) {
        try {
            return ((CallableStatement)super.delegate).getSQLXML(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public String getNString(final int n) {
        try {
            return ((CallableStatement)super.delegate).getNString(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public String getNString(final String s) {
        try {
            return ((CallableStatement)super.delegate).getNString(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Reader getNCharacterStream(final int n) {
        try {
            return ((CallableStatement)super.delegate).getNCharacterStream(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Reader getNCharacterStream(final String s) {
        try {
            return ((CallableStatement)super.delegate).getNCharacterStream(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Reader getCharacterStream(final int n) {
        try {
            return ((CallableStatement)super.delegate).getCharacterStream(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Reader getCharacterStream(final String s) {
        try {
            return ((CallableStatement)super.delegate).getCharacterStream(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBlob(final String s, final Blob blob) {
        try {
            ((CallableStatement)super.delegate).setBlob(s, blob);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setClob(final String s, final Clob clob) {
        try {
            ((CallableStatement)super.delegate).setClob(s, clob);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setAsciiStream(final String s, final InputStream inputStream, final long n) {
        try {
            ((CallableStatement)super.delegate).setAsciiStream(s, inputStream, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBinaryStream(final String s, final InputStream inputStream, final long n) {
        try {
            ((CallableStatement)super.delegate).setBinaryStream(s, inputStream, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setCharacterStream(final String s, final Reader reader, final long n) {
        try {
            ((CallableStatement)super.delegate).setCharacterStream(s, reader, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setAsciiStream(final String s, final InputStream inputStream) {
        try {
            ((CallableStatement)super.delegate).setAsciiStream(s, inputStream);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBinaryStream(final String s, final InputStream inputStream) {
        try {
            ((CallableStatement)super.delegate).setBinaryStream(s, inputStream);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setCharacterStream(final String s, final Reader reader) {
        try {
            ((CallableStatement)super.delegate).setCharacterStream(s, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNCharacterStream(final String s, final Reader reader) {
        try {
            ((CallableStatement)super.delegate).setNCharacterStream(s, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setClob(final String s, final Reader reader) {
        try {
            ((CallableStatement)super.delegate).setClob(s, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setBlob(final String s, final InputStream inputStream) {
        try {
            ((CallableStatement)super.delegate).setBlob(s, inputStream);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void setNClob(final String s, final Reader reader) {
        try {
            ((CallableStatement)super.delegate).setNClob(s, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Object getObject(final int n, final Class clazz) {
        try {
            return ((CallableStatement)super.delegate).getObject(n, (Class<Object>)clazz);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Object getObject(final String s, final Class clazz) {
        try {
            return ((CallableStatement)super.delegate).getObject(s, (Class<Object>)clazz);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isWrapperFor(final Class clazz) {
        try {
            return ((CallableStatement)super.delegate).isWrapperFor(clazz);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    protected HikariProxyCallableStatement(final ProxyConnection proxyConnection, final CallableStatement callableStatement) {
        super(proxyConnection, callableStatement);
    }
}
