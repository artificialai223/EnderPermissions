

package me.TechsCode.EnderPermissions.dependencies.hikari.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.atomic.AtomicLong;

public interface Sequence
{
    void increment();
    
    long get();
    
    public static final class Factory
    {
        public static Sequence create() {
            try {
                if (Sequence.class.getClassLoader().loadClass("java.util.concurrent.atomic.LongAdder") != null && !Boolean.getBoolean("me.TechsCode.EnderPermissions.dependencies.hikari.useAtomicLongSequence")) {
                    return new Java8Sequence();
                }
            }
            catch (ClassNotFoundException ex) {
                try {
                    return new DropwizardSequence(Sequence.class.getClassLoader().loadClass("com.codahale.metrics.LongAdder"));
                }
                catch (Exception ex2) {}
            }
            return new Java7Sequence();
        }
    }
    
    public static final class Java7Sequence extends AtomicLong implements Sequence
    {
        @Override
        public void increment() {
            this.incrementAndGet();
        }
    }
    
    public static final class Java8Sequence extends LongAdder implements Sequence
    {
        @Override
        public long get() {
            return this.sum();
        }
    }
    
    public static final class DropwizardSequence implements Sequence
    {
        private final Object longAdder;
        private final Method increment;
        private final Method sum;
        
        public DropwizardSequence(final Class<?> clazz) {
            final Constructor<?> constructor = (Constructor<?>)clazz.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            (this.increment = clazz.getMethod("increment", (Class[])new Class[0])).setAccessible(true);
            (this.sum = clazz.getMethod("sum", (Class[])new Class[0])).setAccessible(true);
            this.longAdder = constructor.newInstance(new Object[0]);
        }
        
        @Override
        public void increment() {
            try {
                this.increment.invoke(this.longAdder, new Object[0]);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                final Object cause;
                throw new RuntimeException((Throwable)cause);
            }
        }
        
        @Override
        public long get() {
            try {
                return (long)this.sum.invoke(this.longAdder, new Object[0]);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                final Object cause;
                throw new RuntimeException((Throwable)cause);
            }
        }
    }
}
