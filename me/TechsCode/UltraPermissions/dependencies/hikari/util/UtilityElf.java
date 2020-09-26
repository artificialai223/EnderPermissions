

package me.TechsCode.EnderPermissions.dependencies.hikari.util;

import java.sql.Connection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

public final class UtilityElf
{
    public static String getNullIfEmpty(final String s) {
        return (s == null) ? null : (s.trim().isEmpty() ? null : s.trim());
    }
    
    public static void quietlySleep(final long n) {
        try {
            Thread.sleep(n);
        }
        catch (InterruptedException ex) {}
    }
    
    public static <T> T createInstance(final String name, final Class<T> clazz, final Object... initargs) {
        if (name == null) {
            return null;
        }
        try {
            final Class<?> loadClass = UtilityElf.class.getClassLoader().loadClass(name);
            final Class[] parameterTypes = new Class[initargs.length];
            for (int i = 0; i < initargs.length; ++i) {
                parameterTypes[i] = initargs[i].getClass();
            }
            if (initargs.length > 0) {
                return clazz.cast(loadClass.getConstructor((Class<?>[])parameterTypes).newInstance(initargs));
            }
            return clazz.cast(loadClass.newInstance());
        }
        catch (Exception cause) {
            throw new RuntimeException(cause);
        }
    }
    
    public static ThreadPoolExecutor createThreadPoolExecutor(final int capacity, final String s, ThreadFactory threadFactory, final RejectedExecutionHandler handler) {
        if (threadFactory == null) {
            threadFactory = new DefaultThreadFactory(s, true);
        }
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(capacity), threadFactory, handler);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }
    
    public static int getTransactionIsolation(final String s) {
        if (s != null) {
            try {
                final String upperCase = s.toUpperCase();
                if (upperCase.startsWith("TRANSACTION_")) {
                    return Connection.class.getField(upperCase).getInt(null);
                }
                final int int1 = Integer.parseInt(s);
                switch (int1) {
                    case 0:
                    case 1:
                    case 2:
                    case 4:
                    case 8: {
                        return int1;
                    }
                    default: {
                        throw new IllegalArgumentException();
                    }
                }
            }
            catch (Exception ex) {
                throw new IllegalArgumentException("Invalid transaction isolation value: " + s);
            }
        }
        return -1;
    }
}
