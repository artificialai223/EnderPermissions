

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.nio.charset.Charset;
import java.util.Enumeration;
import java.io.SequenceInputStream;
import java.util.Collection;
import java.util.Collections;
import java.io.ByteArrayInputStream;
import me.TechsCode.EnderPermissions.dependencies.commons.io.input.ClosedInputStream;
import java.util.Iterator;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.OutputStream;

public class ByteArrayOutputStream extends OutputStream
{
    static final int DEFAULT_SIZE = 1024;
    private static final byte[] EMPTY_BYTE_ARRAY;
    private final List<byte[]> buffers;
    private int currentBufferIndex;
    private int filledBufferSum;
    private byte[] currentBuffer;
    private int count;
    private boolean reuseBuffers;
    
    public ByteArrayOutputStream() {
        this(1024);
    }
    
    public ByteArrayOutputStream(final int i) {
        this.buffers = new ArrayList<byte[]>();
        this.reuseBuffers = true;
        if (i < 0) {
            throw new IllegalArgumentException("Negative initial size: " + i);
        }
        synchronized (this) {
            this.needNewBuffer(i);
        }
    }
    
    private void needNewBuffer(final int n) {
        if (this.currentBufferIndex < this.buffers.size() - 1) {
            this.filledBufferSum += this.currentBuffer.length;
            ++this.currentBufferIndex;
            this.currentBuffer = this.buffers.get(this.currentBufferIndex);
        }
        else {
            int max;
            if (this.currentBuffer == null) {
                max = n;
                this.filledBufferSum = 0;
            }
            else {
                max = Math.max(this.currentBuffer.length << 1, n - this.filledBufferSum);
                this.filledBufferSum += this.currentBuffer.length;
            }
            ++this.currentBufferIndex;
            this.currentBuffer = new byte[max];
            this.buffers.add(this.currentBuffer);
        }
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) {
        if (n < 0 || n > array.length || n2 < 0 || n + n2 > array.length || n + n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return;
        }
        synchronized (this) {
            final int count = this.count + n2;
            int i = n2;
            int n3 = this.count - this.filledBufferSum;
            while (i > 0) {
                final int min = Math.min(i, this.currentBuffer.length - n3);
                System.arraycopy(array, n + n2 - i, this.currentBuffer, n3, min);
                i -= min;
                if (i > 0) {
                    this.needNewBuffer(count);
                    n3 = 0;
                }
            }
            this.count = count;
        }
    }
    
    @Override
    public synchronized void write(final int n) {
        int n2 = this.count - this.filledBufferSum;
        if (n2 == this.currentBuffer.length) {
            this.needNewBuffer(this.count + 1);
            n2 = 0;
        }
        this.currentBuffer[n2] = (byte)n;
        ++this.count;
    }
    
    public synchronized int write(final InputStream inputStream) {
        int n = 0;
        for (int n2 = this.count - this.filledBufferSum, i = inputStream.read(this.currentBuffer, n2, this.currentBuffer.length - n2); i != -1; i = inputStream.read(this.currentBuffer, n2, this.currentBuffer.length - n2)) {
            n += i;
            n2 += i;
            this.count += i;
            if (n2 == this.currentBuffer.length) {
                this.needNewBuffer(this.currentBuffer.length);
                n2 = 0;
            }
        }
        return n;
    }
    
    public synchronized int size() {
        return this.count;
    }
    
    @Override
    public void close() {
    }
    
    public synchronized void reset() {
        this.count = 0;
        this.filledBufferSum = 0;
        this.currentBufferIndex = 0;
        if (this.reuseBuffers) {
            this.currentBuffer = this.buffers.get(this.currentBufferIndex);
        }
        else {
            this.currentBuffer = null;
            final int length = this.buffers.get(0).length;
            this.buffers.clear();
            this.needNewBuffer(length);
            this.reuseBuffers = true;
        }
    }
    
    public synchronized void writeTo(final OutputStream outputStream) {
        int count = this.count;
        for (final byte[] b : this.buffers) {
            final int min = Math.min(b.length, count);
            outputStream.write(b, 0, min);
            count -= min;
            if (count == 0) {
                break;
            }
        }
    }
    
    public static InputStream toBufferedInputStream(final InputStream inputStream) {
        return toBufferedInputStream(inputStream, 1024);
    }
    
    public static InputStream toBufferedInputStream(final InputStream inputStream, final int n) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(n);
        byteArrayOutputStream.write(inputStream);
        return byteArrayOutputStream.toInputStream();
    }
    
    public synchronized InputStream toInputStream() {
        int count = this.count;
        if (count == 0) {
            return new ClosedInputStream();
        }
        final ArrayList<Object> c = new ArrayList<Object>(this.buffers.size());
        for (final byte[] buf : this.buffers) {
            final int min = Math.min(buf.length, count);
            c.add(new ByteArrayInputStream(buf, 0, min));
            count -= min;
            if (count == 0) {
                break;
            }
        }
        this.reuseBuffers = false;
        return new SequenceInputStream((Enumeration<? extends InputStream>)Collections.enumeration(c));
    }
    
    public synchronized byte[] toByteArray() {
        int count = this.count;
        if (count == 0) {
            return ByteArrayOutputStream.EMPTY_BYTE_ARRAY;
        }
        final byte[] array = new byte[count];
        int n = 0;
        for (final byte[] array2 : this.buffers) {
            final int min = Math.min(array2.length, count);
            System.arraycopy(array2, 0, array, n, min);
            n += min;
            count -= min;
            if (count == 0) {
                break;
            }
        }
        return array;
    }
    
    @Deprecated
    @Override
    public String toString() {
        return new String(this.toByteArray(), Charset.defaultCharset());
    }
    
    public String toString(final String charsetName) {
        return new String(this.toByteArray(), charsetName);
    }
    
    public String toString(final Charset charset) {
        return new String(this.toByteArray(), charset);
    }
    
    static {
        EMPTY_BYTE_ARRAY = new byte[0];
    }
}
