

package me.TechsCode.EnderPermissions.dependencies.hikari.pool;

import java.sql.SQLXML;
import java.sql.NClob;
import java.sql.RowId;
import java.net.URL;
import java.util.Calendar;
import java.sql.Array;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.Ref;
import java.util.Map;
import java.io.Reader;
import java.sql.ResultSetMetaData;
import java.sql.SQLWarning;
import java.io.InputStream;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.sql.ResultSet;

public class HikariProxyResultSet extends ProxyResultSet implements ResultSet, Wrapper, AutoCloseable
{
    @Override
    public boolean next() {
        try {
            return super.delegate.next();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void close() {
        try {
            super.delegate.close();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean wasNull() {
        try {
            return super.delegate.wasNull();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public String getString(final int n) {
        try {
            return super.delegate.getString(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean getBoolean(final int n) {
        try {
            return super.delegate.getBoolean(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public byte getByte(final int n) {
        try {
            return super.delegate.getByte(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public short getShort(final int n) {
        try {
            return super.delegate.getShort(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getInt(final int n) {
        try {
            return super.delegate.getInt(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public long getLong(final int n) {
        try {
            return super.delegate.getLong(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public float getFloat(final int n) {
        try {
            return super.delegate.getFloat(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public double getDouble(final int n) {
        try {
            return super.delegate.getDouble(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public BigDecimal getBigDecimal(final int n, final int n2) {
        try {
            return super.delegate.getBigDecimal(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public byte[] getBytes(final int n) {
        try {
            return super.delegate.getBytes(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Date getDate(final int n) {
        try {
            return super.delegate.getDate(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Time getTime(final int n) {
        try {
            return super.delegate.getTime(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Timestamp getTimestamp(final int n) {
        try {
            return super.delegate.getTimestamp(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public InputStream getAsciiStream(final int n) {
        try {
            return super.delegate.getAsciiStream(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public InputStream getUnicodeStream(final int n) {
        try {
            return super.delegate.getUnicodeStream(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public InputStream getBinaryStream(final int n) {
        try {
            return super.delegate.getBinaryStream(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public String getString(final String s) {
        try {
            return super.delegate.getString(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean getBoolean(final String s) {
        try {
            return super.delegate.getBoolean(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public byte getByte(final String s) {
        try {
            return super.delegate.getByte(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public short getShort(final String s) {
        try {
            return super.delegate.getShort(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getInt(final String s) {
        try {
            return super.delegate.getInt(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public long getLong(final String s) {
        try {
            return super.delegate.getLong(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public float getFloat(final String s) {
        try {
            return super.delegate.getFloat(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public double getDouble(final String s) {
        try {
            return super.delegate.getDouble(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public BigDecimal getBigDecimal(final String s, final int n) {
        try {
            return super.delegate.getBigDecimal(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public byte[] getBytes(final String s) {
        try {
            return super.delegate.getBytes(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Date getDate(final String s) {
        try {
            return super.delegate.getDate(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Time getTime(final String s) {
        try {
            return super.delegate.getTime(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Timestamp getTimestamp(final String s) {
        try {
            return super.delegate.getTimestamp(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public InputStream getAsciiStream(final String s) {
        try {
            return super.delegate.getAsciiStream(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public InputStream getUnicodeStream(final String s) {
        try {
            return super.delegate.getUnicodeStream(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public InputStream getBinaryStream(final String s) {
        try {
            return super.delegate.getBinaryStream(s);
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
    public String getCursorName() {
        try {
            return super.delegate.getCursorName();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public ResultSetMetaData getMetaData() {
        try {
            return super.delegate.getMetaData();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Object getObject(final int n) {
        try {
            return super.delegate.getObject(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Object getObject(final String s) {
        try {
            return super.delegate.getObject(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int findColumn(final String s) {
        try {
            return super.delegate.findColumn(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Reader getCharacterStream(final int n) {
        try {
            return super.delegate.getCharacterStream(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Reader getCharacterStream(final String s) {
        try {
            return super.delegate.getCharacterStream(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public BigDecimal getBigDecimal(final int n) {
        try {
            return super.delegate.getBigDecimal(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public BigDecimal getBigDecimal(final String s) {
        try {
            return super.delegate.getBigDecimal(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isBeforeFirst() {
        try {
            return super.delegate.isBeforeFirst();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isAfterLast() {
        try {
            return super.delegate.isAfterLast();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isFirst() {
        try {
            return super.delegate.isFirst();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean isLast() {
        try {
            return super.delegate.isLast();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void beforeFirst() {
        try {
            super.delegate.beforeFirst();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void afterLast() {
        try {
            super.delegate.afterLast();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean first() {
        try {
            return super.delegate.first();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean last() {
        try {
            return super.delegate.last();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getRow() {
        try {
            return super.delegate.getRow();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean absolute(final int n) {
        try {
            return super.delegate.absolute(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean relative(final int n) {
        try {
            return super.delegate.relative(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean previous() {
        try {
            return super.delegate.previous();
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
    public int getType() {
        try {
            return super.delegate.getType();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public int getConcurrency() {
        try {
            return super.delegate.getConcurrency();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean rowUpdated() {
        try {
            return super.delegate.rowUpdated();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean rowInserted() {
        try {
            return super.delegate.rowInserted();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public boolean rowDeleted() {
        try {
            return super.delegate.rowDeleted();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateNull(final int n) {
        try {
            super.delegate.updateNull(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBoolean(final int n, final boolean b) {
        try {
            super.delegate.updateBoolean(n, b);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateByte(final int n, final byte b) {
        try {
            super.delegate.updateByte(n, b);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateShort(final int n, final short n2) {
        try {
            super.delegate.updateShort(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateInt(final int n, final int n2) {
        try {
            super.delegate.updateInt(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateLong(final int n, final long n2) {
        try {
            super.delegate.updateLong(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateFloat(final int n, final float n2) {
        try {
            super.delegate.updateFloat(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateDouble(final int n, final double n2) {
        try {
            super.delegate.updateDouble(n, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBigDecimal(final int n, final BigDecimal bigDecimal) {
        try {
            super.delegate.updateBigDecimal(n, bigDecimal);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateString(final int n, final String s) {
        try {
            super.delegate.updateString(n, s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBytes(final int n, final byte[] array) {
        try {
            super.delegate.updateBytes(n, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateDate(final int n, final Date date) {
        try {
            super.delegate.updateDate(n, date);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateTime(final int n, final Time time) {
        try {
            super.delegate.updateTime(n, time);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateTimestamp(final int n, final Timestamp timestamp) {
        try {
            super.delegate.updateTimestamp(n, timestamp);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateAsciiStream(final int n, final InputStream inputStream, final int n2) {
        try {
            super.delegate.updateAsciiStream(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBinaryStream(final int n, final InputStream inputStream, final int n2) {
        try {
            super.delegate.updateBinaryStream(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateCharacterStream(final int n, final Reader reader, final int n2) {
        try {
            super.delegate.updateCharacterStream(n, reader, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateObject(final int n, final Object o, final int n2) {
        try {
            super.delegate.updateObject(n, o, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateObject(final int n, final Object o) {
        try {
            super.delegate.updateObject(n, o);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateNull(final String s) {
        try {
            super.delegate.updateNull(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBoolean(final String s, final boolean b) {
        try {
            super.delegate.updateBoolean(s, b);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateByte(final String s, final byte b) {
        try {
            super.delegate.updateByte(s, b);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateShort(final String s, final short n) {
        try {
            super.delegate.updateShort(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateInt(final String s, final int n) {
        try {
            super.delegate.updateInt(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateLong(final String s, final long n) {
        try {
            super.delegate.updateLong(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateFloat(final String s, final float n) {
        try {
            super.delegate.updateFloat(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateDouble(final String s, final double n) {
        try {
            super.delegate.updateDouble(s, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBigDecimal(final String s, final BigDecimal bigDecimal) {
        try {
            super.delegate.updateBigDecimal(s, bigDecimal);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateString(final String s, final String s2) {
        try {
            super.delegate.updateString(s, s2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBytes(final String s, final byte[] array) {
        try {
            super.delegate.updateBytes(s, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateDate(final String s, final Date date) {
        try {
            super.delegate.updateDate(s, date);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateTime(final String s, final Time time) {
        try {
            super.delegate.updateTime(s, time);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateTimestamp(final String s, final Timestamp timestamp) {
        try {
            super.delegate.updateTimestamp(s, timestamp);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateAsciiStream(final String s, final InputStream inputStream, final int n) {
        try {
            super.delegate.updateAsciiStream(s, inputStream, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBinaryStream(final String s, final InputStream inputStream, final int n) {
        try {
            super.delegate.updateBinaryStream(s, inputStream, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateCharacterStream(final String s, final Reader reader, final int n) {
        try {
            super.delegate.updateCharacterStream(s, reader, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateObject(final String s, final Object o, final int n) {
        try {
            super.delegate.updateObject(s, o, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateObject(final String s, final Object o) {
        try {
            super.delegate.updateObject(s, o);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void insertRow() {
        try {
            super.insertRow();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateRow() {
        try {
            super.updateRow();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void deleteRow() {
        try {
            super.deleteRow();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void refreshRow() {
        try {
            super.delegate.refreshRow();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void cancelRowUpdates() {
        try {
            super.delegate.cancelRowUpdates();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void moveToInsertRow() {
        try {
            super.delegate.moveToInsertRow();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void moveToCurrentRow() {
        try {
            super.delegate.moveToCurrentRow();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Object getObject(final int n, final Map map) {
        try {
            return super.delegate.getObject(n, map);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Ref getRef(final int n) {
        try {
            return super.delegate.getRef(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Blob getBlob(final int n) {
        try {
            return super.delegate.getBlob(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Clob getClob(final int n) {
        try {
            return super.delegate.getClob(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Array getArray(final int n) {
        try {
            return super.delegate.getArray(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Object getObject(final String s, final Map map) {
        try {
            return super.delegate.getObject(s, map);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Ref getRef(final String s) {
        try {
            return super.delegate.getRef(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Blob getBlob(final String s) {
        try {
            return super.delegate.getBlob(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Clob getClob(final String s) {
        try {
            return super.delegate.getClob(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Array getArray(final String s) {
        try {
            return super.delegate.getArray(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Date getDate(final int n, final Calendar calendar) {
        try {
            return super.delegate.getDate(n, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Date getDate(final String s, final Calendar calendar) {
        try {
            return super.delegate.getDate(s, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Time getTime(final int n, final Calendar calendar) {
        try {
            return super.delegate.getTime(n, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Time getTime(final String s, final Calendar calendar) {
        try {
            return super.delegate.getTime(s, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Timestamp getTimestamp(final int n, final Calendar calendar) {
        try {
            return super.delegate.getTimestamp(n, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Timestamp getTimestamp(final String s, final Calendar calendar) {
        try {
            return super.delegate.getTimestamp(s, calendar);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public URL getURL(final int n) {
        try {
            return super.delegate.getURL(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public URL getURL(final String s) {
        try {
            return super.delegate.getURL(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateRef(final int n, final Ref ref) {
        try {
            super.delegate.updateRef(n, ref);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateRef(final String s, final Ref ref) {
        try {
            super.delegate.updateRef(s, ref);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBlob(final int n, final Blob blob) {
        try {
            super.delegate.updateBlob(n, blob);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBlob(final String s, final Blob blob) {
        try {
            super.delegate.updateBlob(s, blob);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateClob(final int n, final Clob clob) {
        try {
            super.delegate.updateClob(n, clob);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateClob(final String s, final Clob clob) {
        try {
            super.delegate.updateClob(s, clob);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateArray(final int n, final Array array) {
        try {
            super.delegate.updateArray(n, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateArray(final String s, final Array array) {
        try {
            super.delegate.updateArray(s, array);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public RowId getRowId(final int n) {
        try {
            return super.delegate.getRowId(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public RowId getRowId(final String s) {
        try {
            return super.delegate.getRowId(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateRowId(final int n, final RowId rowId) {
        try {
            super.delegate.updateRowId(n, rowId);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateRowId(final String s, final RowId rowId) {
        try {
            super.delegate.updateRowId(s, rowId);
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
    public boolean isClosed() {
        try {
            return super.delegate.isClosed();
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateNString(final int n, final String s) {
        try {
            super.delegate.updateNString(n, s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateNString(final String s, final String s2) {
        try {
            super.delegate.updateNString(s, s2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateNClob(final int n, final NClob nClob) {
        try {
            super.delegate.updateNClob(n, nClob);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateNClob(final String s, final NClob nClob) {
        try {
            super.delegate.updateNClob(s, nClob);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public NClob getNClob(final int n) {
        try {
            return super.delegate.getNClob(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public NClob getNClob(final String s) {
        try {
            return super.delegate.getNClob(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public SQLXML getSQLXML(final int n) {
        try {
            return super.delegate.getSQLXML(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public SQLXML getSQLXML(final String s) {
        try {
            return super.delegate.getSQLXML(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateSQLXML(final int n, final SQLXML sqlxml) {
        try {
            super.delegate.updateSQLXML(n, sqlxml);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateSQLXML(final String s, final SQLXML sqlxml) {
        try {
            super.delegate.updateSQLXML(s, sqlxml);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public String getNString(final int n) {
        try {
            return super.delegate.getNString(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public String getNString(final String s) {
        try {
            return super.delegate.getNString(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Reader getNCharacterStream(final int n) {
        try {
            return super.delegate.getNCharacterStream(n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Reader getNCharacterStream(final String s) {
        try {
            return super.delegate.getNCharacterStream(s);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateNCharacterStream(final int n, final Reader reader, final long n2) {
        try {
            super.delegate.updateNCharacterStream(n, reader, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateNCharacterStream(final String s, final Reader reader, final long n) {
        try {
            super.delegate.updateNCharacterStream(s, reader, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateAsciiStream(final int n, final InputStream inputStream, final long n2) {
        try {
            super.delegate.updateAsciiStream(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBinaryStream(final int n, final InputStream inputStream, final long n2) {
        try {
            super.delegate.updateBinaryStream(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateCharacterStream(final int n, final Reader reader, final long n2) {
        try {
            super.delegate.updateCharacterStream(n, reader, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateAsciiStream(final String s, final InputStream inputStream, final long n) {
        try {
            super.delegate.updateAsciiStream(s, inputStream, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBinaryStream(final String s, final InputStream inputStream, final long n) {
        try {
            super.delegate.updateBinaryStream(s, inputStream, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateCharacterStream(final String s, final Reader reader, final long n) {
        try {
            super.delegate.updateCharacterStream(s, reader, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBlob(final int n, final InputStream inputStream, final long n2) {
        try {
            super.delegate.updateBlob(n, inputStream, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBlob(final String s, final InputStream inputStream, final long n) {
        try {
            super.delegate.updateBlob(s, inputStream, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateClob(final int n, final Reader reader, final long n2) {
        try {
            super.delegate.updateClob(n, reader, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateClob(final String s, final Reader reader, final long n) {
        try {
            super.delegate.updateClob(s, reader, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateNClob(final int n, final Reader reader, final long n2) {
        try {
            super.delegate.updateNClob(n, reader, n2);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateNClob(final String s, final Reader reader, final long n) {
        try {
            super.delegate.updateNClob(s, reader, n);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateNCharacterStream(final int n, final Reader reader) {
        try {
            super.delegate.updateNCharacterStream(n, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateNCharacterStream(final String s, final Reader reader) {
        try {
            super.delegate.updateNCharacterStream(s, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateAsciiStream(final int n, final InputStream inputStream) {
        try {
            super.delegate.updateAsciiStream(n, inputStream);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBinaryStream(final int n, final InputStream inputStream) {
        try {
            super.delegate.updateBinaryStream(n, inputStream);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateCharacterStream(final int n, final Reader reader) {
        try {
            super.delegate.updateCharacterStream(n, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateAsciiStream(final String s, final InputStream inputStream) {
        try {
            super.delegate.updateAsciiStream(s, inputStream);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBinaryStream(final String s, final InputStream inputStream) {
        try {
            super.delegate.updateBinaryStream(s, inputStream);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateCharacterStream(final String s, final Reader reader) {
        try {
            super.delegate.updateCharacterStream(s, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBlob(final int n, final InputStream inputStream) {
        try {
            super.delegate.updateBlob(n, inputStream);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateBlob(final String s, final InputStream inputStream) {
        try {
            super.delegate.updateBlob(s, inputStream);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateClob(final int n, final Reader reader) {
        try {
            super.delegate.updateClob(n, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateClob(final String s, final Reader reader) {
        try {
            super.delegate.updateClob(s, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateNClob(final int n, final Reader reader) {
        try {
            super.delegate.updateNClob(n, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public void updateNClob(final String s, final Reader reader) {
        try {
            super.delegate.updateNClob(s, reader);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Object getObject(final int n, final Class clazz) {
        try {
            return super.delegate.getObject(n, (Class<Object>)clazz);
        }
        catch (SQLException ex) {
            throw this.checkException(ex);
        }
    }
    
    @Override
    public Object getObject(final String s, final Class clazz) {
        try {
            return super.delegate.getObject(s, (Class<Object>)clazz);
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
    
    protected HikariProxyResultSet(final ProxyConnection proxyConnection, final ProxyStatement proxyStatement, final ResultSet set) {
        super(proxyConnection, proxyStatement, set);
    }
}
