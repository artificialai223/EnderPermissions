

package me.TechsCode.EnderPermissions.dependencies.hikari.util;

import java.util.concurrent.TimeUnit;

public interface ClockSource
{
    public static final ClockSource INSTANCE = create();
    
    long currentTime();
    
    long toMillis(final long p0);
    
    long elapsedMillis(final long p0);
    
    long elapsedMillis(final long p0, final long p1);
    
    long elapsedNanos(final long p0);
    
    long elapsedNanos(final long p0, final long p1);
    
    long plusMillis(final long p0, final long p1);
    
    TimeUnit getSourceTimeUnit();
    
    public static class Factory
    {
        private static ClockSource create() {
            if ("Mac OS X".equals(System.getProperty("os.name"))) {
                return new MillisecondClockSource();
            }
            return new NanosecondClockSource();
        }
    }
    
    public static final class MillisecondClockSource implements ClockSource
    {
        @Override
        public long currentTime() {
            return System.currentTimeMillis();
        }
        
        @Override
        public long elapsedMillis(final long n) {
            return System.currentTimeMillis() - n;
        }
        
        @Override
        public long elapsedMillis(final long n, final long n2) {
            return n2 - n;
        }
        
        @Override
        public long elapsedNanos(final long n) {
            return TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis() - n);
        }
        
        @Override
        public long elapsedNanos(final long n, final long n2) {
            return TimeUnit.MILLISECONDS.toNanos(n2 - n);
        }
        
        @Override
        public long toMillis(final long n) {
            return n;
        }
        
        @Override
        public long plusMillis(final long n, final long n2) {
            return n + n2;
        }
        
        @Override
        public TimeUnit getSourceTimeUnit() {
            return TimeUnit.MILLISECONDS;
        }
    }
    
    public static final class NanosecondClockSource implements ClockSource
    {
        @Override
        public long currentTime() {
            return System.nanoTime();
        }
        
        @Override
        public final long toMillis(final long duration) {
            return TimeUnit.NANOSECONDS.toMillis(duration);
        }
        
        @Override
        public long elapsedMillis(final long n) {
            return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - n);
        }
        
        @Override
        public long elapsedMillis(final long n, final long n2) {
            return TimeUnit.NANOSECONDS.toMillis(n2 - n);
        }
        
        @Override
        public long elapsedNanos(final long n) {
            return System.nanoTime() - n;
        }
        
        @Override
        public long elapsedNanos(final long n, final long n2) {
            return n2 - n;
        }
        
        @Override
        public long plusMillis(final long n, final long duration) {
            return n + TimeUnit.MILLISECONDS.toNanos(duration);
        }
        
        @Override
        public TimeUnit getSourceTimeUnit() {
            return TimeUnit.NANOSECONDS;
        }
    }
}
