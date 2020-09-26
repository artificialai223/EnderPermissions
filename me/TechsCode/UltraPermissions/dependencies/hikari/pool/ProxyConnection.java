

package me.TechsCode.EnderPermissions.dependencies.hikari.pool;

import java.lang.reflect.Proxy;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import java.util.HashSet;
import me.TechsCode.EnderPermissions.dependencies.slf4j.LoggerFactory;
import java.sql.Wrapper;
import java.util.concurrent.Executor;
import java.sql.Savepoint;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.FastList;
import me.TechsCode.EnderPermissions.dependencies.hikari.util.ClockSource;
import java.util.Set;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;
import java.sql.Connection;

public abstract class ProxyConnection implements Connection
{
    static final int DIRTY_BIT_READONLY = 1;
    static final int DIRTY_BIT_AUTOCOMMIT = 2;
    static final int DIRTY_BIT_ISOLATION = 4;
    static final int DIRTY_BIT_CATALOG = 8;
    static final int DIRTY_BIT_NETTIMEOUT = 16;
    private static final Logger LOGGER;
    private static final Set<String> SQL_ERRORS;
    private static final ClockSource clockSource;
    protected Connection delegate;
    private final PoolEntry poolEntry;
    private final ProxyLeakTask leakTask;
    private final FastList<Statement> openStatements;
    private int dirtyBits;
    private long lastAccess;
    private boolean isCommitStateDirty;
    private boolean isReadOnly;
    private boolean isAutoCommit;
    private int networkTimeout;
    private int transactionIsolation;
    private String dbcatalog;
    
    protected ProxyConnection(final PoolEntry poolEntry, final Connection delegate, final FastList<Statement> openStatements, final ProxyLeakTask leakTask, final long lastAccess, final boolean isReadOnly, final boolean isAutoCommit) {
        this.poolEntry = poolEntry;
        this.delegate = delegate;
        this.openStatements = openStatements;
        this.leakTask = leakTask;
        this.lastAccess = lastAccess;
        this.isReadOnly = isReadOnly;
        this.isAutoCommit = isAutoCommit;
    }
    
    @Override
    public final String toString() {
        return new StringBuilder(64).append(this.getClass().getSimpleName()).append('@').append(System.identityHashCode(this)).append(" wrapping ").append(this.delegate).toString();
    }
    
    final boolean getAutoCommitState() {
        return this.isAutoCommit;
    }
    
    final String getCatalogState() {
        return this.dbcatalog;
    }
    
    final int getTransactionIsolationState() {
        return this.transactionIsolation;
    }
    
    final boolean getReadOnlyState() {
        return this.isReadOnly;
    }
    
    final int getNetworkTimeoutState() {
        return this.networkTimeout;
    }
    
    final PoolEntry getPoolEntry() {
        return this.poolEntry;
    }
    
    final SQLException checkException(final SQLException ex) {
        final String sqlState = ex.getSQLState();
        if (sqlState != null) {
            if ((sqlState.startsWith("08") || ProxyConnection.SQL_ERRORS.contains(sqlState)) && this.delegate != ClosedConnection.CLOSED_CONNECTION) {
                ProxyConnection.LOGGER.warn("{} - Connection {} marked as broken because of SQLSTATE({}), ErrorCode({})", this.poolEntry.getPoolName(), this.delegate, sqlState, ex.getErrorCode(), ex);
                this.leakTask.cancel();
                this.poolEntry.evict("(connection broken)");
                this.delegate = ClosedConnection.CLOSED_CONNECTION;
            }
            else {
                final SQLException nextException = ex.getNextException();
                if (nextException != null && nextException != ex) {
                    this.checkException(nextException);
                }
            }
        }
        return ex;
    }
    
    final void untrackStatement(final Statement statement) {
        this.openStatements.remove(statement);
    }
    
    final void markCommitStateDirty() {
        if (this.isAutoCommit) {
            this.lastAccess = ProxyConnection.clockSource.currentTime();
        }
        else {
            this.isCommitStateDirty = true;
        }
    }
    
    void cancelLeakTask() {
        this.leakTask.cancel();
    }
    
    private final <T extends Statement> T trackStatement(final T t) {
        this.openStatements.add(t);
        return t;
    }
    
    private final void closeStatements() {
        final int size = this.openStatements.size();
        if (size > 0) {
            for (int n = 0; n < size && this.delegate != ClosedConnection.CLOSED_CONNECTION; ++n) {
                try {
                    final Statement statement = this.openStatements.get(n);
                    if (statement != null) {
                        statement.close();
                    }
                }
                catch (SQLException ex) {
                    this.checkException(ex);
                }
            }
            this.openStatements.clear();
        }
    }
    
    @Override
    public final void close() {
        this.closeStatements();
        if (this.delegate != ClosedConnection.CLOSED_CONNECTION) {
            this.leakTask.cancel();
            try {
                if (this.isCommitStateDirty && !this.isAutoCommit && !this.isReadOnly) {
                    this.delegate.rollback();
                    this.lastAccess = ProxyConnection.clockSource.currentTime();
                    ProxyConnection.LOGGER.debug("{} - Executed rollback on connection {} due to dirty commit state on close().", this.poolEntry.getPoolName(), this.delegate);
                }
                if (this.dirtyBits != 0) {
                    this.poolEntry.resetConnectionState(this, this.dirtyBits);
                    this.lastAccess = ProxyConnection.clockSource.currentTime();
                }
                this.delegate.clearWarnings();
            }
            catch (SQLException ex) {
                if (!this.poolEntry.isMarkedEvicted()) {
                    throw this.checkException(ex);
                }
            }
            finally {
                this.delegate = ClosedConnection.CLOSED_CONNECTION;
                this.poolEntry.recycle(this.lastAccess);
            }
        }
    }
    
    @Override
    public boolean isClosed() {
        return this.delegate == ClosedConnection.CLOSED_CONNECTION;
    }
    
    @Override
    public Statement createStatement() {
        return ProxyFactory.getProxyStatement(this, this.trackStatement(this.delegate.createStatement()));
    }
    
    @Override
    public Statement createStatement(final int n, final int n2) {
        return ProxyFactory.getProxyStatement(this, this.trackStatement(this.delegate.createStatement(n, n2)));
    }
    
    @Override
    public Statement createStatement(final int n, final int n2, final int n3) {
        return ProxyFactory.getProxyStatement(this, this.trackStatement(this.delegate.createStatement(n, n2, n3)));
    }
    
    @Override
    public CallableStatement prepareCall(final String s) {
        return ProxyFactory.getProxyCallableStatement(this, this.trackStatement(this.delegate.prepareCall(s)));
    }
    
    @Override
    public CallableStatement prepareCall(final String s, final int n, final int n2) {
        return ProxyFactory.getProxyCallableStatement(this, this.trackStatement(this.delegate.prepareCall(s, n, n2)));
    }
    
    @Override
    public CallableStatement prepareCall(final String s, final int n, final int n2, final int n3) {
        return ProxyFactory.getProxyCallableStatement(this, this.trackStatement(this.delegate.prepareCall(s, n, n2, n3)));
    }
    
    @Override
    public PreparedStatement prepareStatement(final String s) {
        return ProxyFactory.getProxyPreparedStatement(this, this.trackStatement(this.delegate.prepareStatement(s)));
    }
    
    @Override
    public PreparedStatement prepareStatement(final String s, final int n) {
        return ProxyFactory.getProxyPreparedStatement(this, this.trackStatement(this.delegate.prepareStatement(s, n)));
    }
    
    @Override
    public PreparedStatement prepareStatement(final String s, final int n, final int n2) {
        return ProxyFactory.getProxyPreparedStatement(this, this.trackStatement(this.delegate.prepareStatement(s, n, n2)));
    }
    
    @Override
    public PreparedStatement prepareStatement(final String s, final int n, final int n2, final int n3) {
        return ProxyFactory.getProxyPreparedStatement(this, this.trackStatement(this.delegate.prepareStatement(s, n, n2, n3)));
    }
    
    @Override
    public PreparedStatement prepareStatement(final String s, final int[] array) {
        return ProxyFactory.getProxyPreparedStatement(this, this.trackStatement(this.delegate.prepareStatement(s, array)));
    }
    
    @Override
    public PreparedStatement prepareStatement(final String s, final String[] array) {
        return ProxyFactory.getProxyPreparedStatement(this, this.trackStatement(this.delegate.prepareStatement(s, array)));
    }
    
    @Override
    public void commit() {
        this.delegate.commit();
        this.isCommitStateDirty = false;
        this.lastAccess = ProxyConnection.clockSource.currentTime();
    }
    
    @Override
    public void rollback() {
        this.delegate.rollback();
        this.isCommitStateDirty = false;
        this.lastAccess = ProxyConnection.clockSource.currentTime();
    }
    
    @Override
    public void rollback(final Savepoint savepoint) {
        this.delegate.rollback(savepoint);
        this.isCommitStateDirty = false;
        this.lastAccess = ProxyConnection.clockSource.currentTime();
    }
    
    @Override
    public void setAutoCommit(final boolean b) {
        this.delegate.setAutoCommit(b);
        this.isAutoCommit = b;
        this.dirtyBits |= 0x2;
    }
    
    @Override
    public void setReadOnly(final boolean b) {
        this.delegate.setReadOnly(b);
        this.isReadOnly = b;
        this.dirtyBits |= 0x1;
    }
    
    @Override
    public void setTransactionIsolation(final int n) {
        this.delegate.setTransactionIsolation(n);
        this.transactionIsolation = n;
        this.dirtyBits |= 0x4;
    }
    
    @Override
    public void setCatalog(final String s) {
        this.delegate.setCatalog(s);
        this.dbcatalog = s;
        this.dirtyBits |= 0x8;
    }
    
    @Override
    public void setNetworkTimeout(final Executor executor, final int networkTimeout) {
        this.delegate.setNetworkTimeout(executor, networkTimeout);
        this.networkTimeout = networkTimeout;
        this.dirtyBits |= 0x10;
    }
    
    @Override
    public final boolean isWrapperFor(final Class<?> clazz) {
        return clazz.isInstance(this.delegate) || (this.delegate instanceof Wrapper && this.delegate.isWrapperFor(clazz));
    }
    
    @Override
    public final <T> T unwrap(final Class<T> obj) {
        if (obj.isInstance(this.delegate)) {
            return (T)this.delegate;
        }
        if (this.delegate instanceof Wrapper) {
            return this.delegate.unwrap(obj);
        }
        throw new SQLException("Wrapped connection is not an instance of " + obj);
    }
    
    static {
        LOGGER = LoggerFactory.getLogger(ProxyConnection.class);
        clockSource = ClockSource.INSTANCE;
        (SQL_ERRORS = new HashSet<String>()).add("57P01");
        ProxyConnection.SQL_ERRORS.add("57P02");
        ProxyConnection.SQL_ERRORS.add("57P03");
        ProxyConnection.SQL_ERRORS.add("01002");
        ProxyConnection.SQL_ERRORS.add("JZ0C0");
        ProxyConnection.SQL_ERRORS.add("JZ0C1");
    }
    
    private static final class ClosedConnection
    {
        static final Connection CLOSED_CONNECTION;
        
        private static Connection getClosedConnection() {
            return (Connection)Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class[] { Connection.class }, new InvocationHandler() {
                @Override
                public Object invoke(final Object o, final Method method, final Object[] array) {
                    final String name = method.getName();
                    if ("abort".equals(name)) {
                        return Void.TYPE;
                    }
                    if ("isValid".equals(name)) {
                        return Boolean.FALSE;
                    }
                    if ("toString".equals(name)) {
                        return ClosedConnection.class.getCanonicalName();
                    }
                    throw new SQLException("Connection is closed");
                }
            });
        }
        
        static {
            CLOSED_CONNECTION = getClosedConnection();
        }
    }
}
