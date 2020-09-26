

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.io.EOFException;
import me.TechsCode.EnderPermissions.dependencies.commons.io.EndianUtils;
import java.io.InputStream;
import java.io.DataInput;

public class SwappedDataInputStream extends ProxyInputStream implements DataInput
{
    public SwappedDataInputStream(final InputStream inputStream) {
        super(inputStream);
    }
    
    @Override
    public boolean readBoolean() {
        return 0 != this.readByte();
    }
    
    @Override
    public byte readByte() {
        return (byte)this.in.read();
    }
    
    @Override
    public char readChar() {
        return (char)this.readShort();
    }
    
    @Override
    public double readDouble() {
        return EndianUtils.readSwappedDouble(this.in);
    }
    
    @Override
    public float readFloat() {
        return EndianUtils.readSwappedFloat(this.in);
    }
    
    @Override
    public void readFully(final byte[] array) {
        this.readFully(array, 0, array.length);
    }
    
    @Override
    public void readFully(final byte[] array, final int n, final int n2) {
        int read;
        for (int i = n2; i > 0; i -= read) {
            read = this.read(array, n + n2 - i, i);
            if (-1 == read) {
                throw new EOFException();
            }
        }
    }
    
    @Override
    public int readInt() {
        return EndianUtils.readSwappedInteger(this.in);
    }
    
    @Override
    public String readLine() {
        throw new UnsupportedOperationException("Operation not supported: readLine()");
    }
    
    @Override
    public long readLong() {
        return EndianUtils.readSwappedLong(this.in);
    }
    
    @Override
    public short readShort() {
        return EndianUtils.readSwappedShort(this.in);
    }
    
    @Override
    public int readUnsignedByte() {
        return this.in.read();
    }
    
    @Override
    public int readUnsignedShort() {
        return EndianUtils.readSwappedUnsignedShort(this.in);
    }
    
    @Override
    public String readUTF() {
        throw new UnsupportedOperationException("Operation not supported: readUTF()");
    }
    
    @Override
    public int skipBytes(final int n) {
        return (int)this.in.skip(n);
    }
}
