

package me.TechsCode.EnderPermissions.dependencies.commons.codec.digest;

import java.util.zip.Checksum;

public class XXHash32 implements Checksum
{
    private static final int BUF_SIZE = 16;
    private static final int ROTATE_BITS = 13;
    private static final int PRIME1 = -1640531535;
    private static final int PRIME2 = -2048144777;
    private static final int PRIME3 = -1028477379;
    private static final int PRIME4 = 668265263;
    private static final int PRIME5 = 374761393;
    private final byte[] oneByte;
    private final int[] state;
    private final byte[] buffer;
    private final int seed;
    private int totalLen;
    private int pos;
    
    public XXHash32() {
        this(0);
    }
    
    public XXHash32(final int seed) {
        this.oneByte = new byte[1];
        this.state = new int[4];
        this.buffer = new byte[16];
        this.seed = seed;
        this.initializeState();
    }
    
    @Override
    public void reset() {
        this.initializeState();
        this.totalLen = 0;
        this.pos = 0;
    }
    
    @Override
    public void update(final int n) {
        this.oneByte[0] = (byte)(n & 0xFF);
        this.update(this.oneByte, 0, 1);
    }
    
    @Override
    public void update(final byte[] array, int i, final int n) {
        if (n <= 0) {
            return;
        }
        this.totalLen += n;
        final int n2 = i + n;
        if (this.pos + n < 16) {
            System.arraycopy(array, i, this.buffer, this.pos, n);
            this.pos += n;
            return;
        }
        if (this.pos > 0) {
            final int n3 = 16 - this.pos;
            System.arraycopy(array, i, this.buffer, this.pos, n3);
            this.process(this.buffer, 0);
            i += n3;
        }
        while (i <= n2 - 16) {
            this.process(array, i);
            i += 16;
        }
        if (i < n2) {
            this.pos = n2 - i;
            System.arraycopy(array, i, this.buffer, 0, this.pos);
        }
    }
    
    @Override
    public long getValue() {
        int n;
        if (this.totalLen > 16) {
            n = Integer.rotateLeft(this.state[0], 1) + Integer.rotateLeft(this.state[1], 7) + Integer.rotateLeft(this.state[2], 12) + Integer.rotateLeft(this.state[3], 18);
        }
        else {
            n = this.state[2] + 374761393;
        }
        int n2 = n + this.totalLen;
        int i;
        for (i = 0; i <= this.pos - 4; i += 4) {
            n2 = Integer.rotateLeft(n2 + getInt(this.buffer, i) * -1028477379, 17) * 668265263;
        }
        while (i < this.pos) {
            n2 = Integer.rotateLeft(n2 + (this.buffer[i++] & 0xFF) * 374761393, 11) * -1640531535;
        }
        final int n3 = (n2 ^ n2 >>> 15) * -2048144777;
        final int n4 = (n3 ^ n3 >>> 13) * -1028477379;
        return (long)(n4 ^ n4 >>> 16) & 0xFFFFFFFFL;
    }
    
    private static int getInt(final byte[] array, final int n) {
        return (int)(fromLittleEndian(array, n, 4) & 0xFFFFFFFFL);
    }
    
    private void initializeState() {
        this.state[0] = this.seed - 1640531535 - 2048144777;
        this.state[1] = this.seed - 2048144777;
        this.state[2] = this.seed;
        this.state[3] = this.seed + 1640531535;
    }
    
    private void process(final byte[] array, final int n) {
        final int n2 = this.state[0];
        final int n3 = this.state[1];
        final int n4 = this.state[2];
        final int n5 = this.state[3];
        final int n6 = Integer.rotateLeft(n2 + getInt(array, n) * -2048144777, 13) * -1640531535;
        final int n7 = Integer.rotateLeft(n3 + getInt(array, n + 4) * -2048144777, 13) * -1640531535;
        final int n8 = Integer.rotateLeft(n4 + getInt(array, n + 8) * -2048144777, 13) * -1640531535;
        final int n9 = Integer.rotateLeft(n5 + getInt(array, n + 12) * -2048144777, 13) * -1640531535;
        this.state[0] = n6;
        this.state[1] = n7;
        this.state[2] = n8;
        this.state[3] = n9;
        this.pos = 0;
    }
    
    private static long fromLittleEndian(final byte[] array, final int n, final int n2) {
        if (n2 > 8) {
            throw new IllegalArgumentException("can't read more than eight bytes into a long value");
        }
        long n3 = 0L;
        for (int i = 0; i < n2; ++i) {
            n3 |= ((long)array[n + i] & 0xFFL) << 8 * i;
        }
        return n3;
    }
}
