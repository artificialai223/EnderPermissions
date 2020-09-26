

package me.TechsCode.EnderPermissions.dependencies.commons.lang.math;

import java.util.Random;

public final class JVMRandom extends Random
{
    private static final long serialVersionUID = 1L;
    private static final Random SHARED_RANDOM;
    private boolean constructed;
    
    public JVMRandom() {
        this.constructed = false;
        this.constructed = true;
    }
    
    public synchronized void setSeed(final long n) {
        if (this.constructed) {
            throw new UnsupportedOperationException();
        }
    }
    
    public synchronized double nextGaussian() {
        throw new UnsupportedOperationException();
    }
    
    public void nextBytes(final byte[] array) {
        throw new UnsupportedOperationException();
    }
    
    public int nextInt() {
        return this.nextInt(Integer.MAX_VALUE);
    }
    
    public int nextInt(final int bound) {
        return JVMRandom.SHARED_RANDOM.nextInt(bound);
    }
    
    public long nextLong() {
        return nextLong(Long.MAX_VALUE);
    }
    
    public static long nextLong(final long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException("Upper bound for nextInt must be positive");
        }
        if ((n & -n) == n) {
            return next63bits() >> 63 - bitsRequired(n - 1L);
        }
        long next63bits;
        long n2;
        do {
            next63bits = next63bits();
            n2 = next63bits % n;
        } while (next63bits - n2 + (n - 1L) < 0L);
        return n2;
    }
    
    public boolean nextBoolean() {
        return JVMRandom.SHARED_RANDOM.nextBoolean();
    }
    
    public float nextFloat() {
        return JVMRandom.SHARED_RANDOM.nextFloat();
    }
    
    public double nextDouble() {
        return JVMRandom.SHARED_RANDOM.nextDouble();
    }
    
    private static long next63bits() {
        return JVMRandom.SHARED_RANDOM.nextLong() & Long.MAX_VALUE;
    }
    
    private static int bitsRequired(long n) {
        long n2 = n;
        int n3 = 0;
        while (n >= 0L) {
            if (n2 == 0L) {
                return n3;
            }
            ++n3;
            n <<= 1;
            n2 >>= 1;
        }
        return 64 - n3;
    }
    
    static {
        SHARED_RANDOM = new Random();
    }
}
