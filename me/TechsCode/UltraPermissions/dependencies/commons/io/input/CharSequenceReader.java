

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.io.Serializable;
import java.io.Reader;

public class CharSequenceReader extends Reader implements Serializable
{
    private static final long serialVersionUID = 3724187752191401220L;
    private final CharSequence charSequence;
    private int idx;
    private int mark;
    
    public CharSequenceReader(final CharSequence charSequence) {
        this.charSequence = ((charSequence != null) ? charSequence : "");
    }
    
    @Override
    public void close() {
        this.idx = 0;
        this.mark = 0;
    }
    
    @Override
    public void mark(final int n) {
        this.mark = this.idx;
    }
    
    @Override
    public boolean markSupported() {
        return true;
    }
    
    @Override
    public int read() {
        if (this.idx >= this.charSequence.length()) {
            return -1;
        }
        return this.charSequence.charAt(this.idx++);
    }
    
    @Override
    public int read(final char[] array, final int i, final int j) {
        if (this.idx >= this.charSequence.length()) {
            return -1;
        }
        if (array == null) {
            throw new NullPointerException("Character array is missing");
        }
        if (j < 0 || i < 0 || i + j > array.length) {
            throw new IndexOutOfBoundsException("Array Size=" + array.length + ", offset=" + i + ", length=" + j);
        }
        int n = 0;
        for (int k = 0; k < j; ++k) {
            final int read = this.read();
            if (read == -1) {
                return n;
            }
            array[i + k] = (char)read;
            ++n;
        }
        return n;
    }
    
    @Override
    public void reset() {
        this.idx = this.mark;
    }
    
    @Override
    public long skip(final long lng) {
        if (lng < 0L) {
            throw new IllegalArgumentException("Number of characters to skip is less than zero: " + lng);
        }
        if (this.idx >= this.charSequence.length()) {
            return -1L;
        }
        final int idx = (int)Math.min(this.charSequence.length(), this.idx + lng);
        final int n = idx - this.idx;
        this.idx = idx;
        return n;
    }
    
    @Override
    public String toString() {
        return this.charSequence.toString();
    }
}
