

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.util.Iterator;
import java.util.Collections;
import java.util.Arrays;
import java.io.InputStream;
import java.util.Comparator;
import me.TechsCode.EnderPermissions.dependencies.commons.io.ByteOrderMark;
import java.util.List;

public class BOMInputStream extends ProxyInputStream
{
    private final boolean include;
    private final List<ByteOrderMark> boms;
    private ByteOrderMark byteOrderMark;
    private int[] firstBytes;
    private int fbLength;
    private int fbIndex;
    private int markFbIndex;
    private boolean markedAtStart;
    private static final Comparator<ByteOrderMark> ByteOrderMarkLengthComparator;
    
    public BOMInputStream(final InputStream inputStream) {
        this(inputStream, false, new ByteOrderMark[] { ByteOrderMark.UTF_8 });
    }
    
    public BOMInputStream(final InputStream inputStream, final boolean b) {
        this(inputStream, b, new ByteOrderMark[] { ByteOrderMark.UTF_8 });
    }
    
    public BOMInputStream(final InputStream inputStream, final ByteOrderMark... array) {
        this(inputStream, false, array);
    }
    
    public BOMInputStream(final InputStream inputStream, final boolean include, final ByteOrderMark... a) {
        super(inputStream);
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("No BOMs specified");
        }
        this.include = include;
        final List<ByteOrderMark> list = Arrays.asList(a);
        Collections.sort((List<Object>)list, (Comparator<? super Object>)BOMInputStream.ByteOrderMarkLengthComparator);
        this.boms = list;
    }
    
    public boolean hasBOM() {
        return this.getBOM() != null;
    }
    
    public boolean hasBOM(final ByteOrderMark obj) {
        if (!this.boms.contains(obj)) {
            throw new IllegalArgumentException("Stream not configure to detect " + obj);
        }
        this.getBOM();
        return this.byteOrderMark != null && this.byteOrderMark.equals(obj);
    }
    
    public ByteOrderMark getBOM() {
        if (this.firstBytes == null) {
            this.fbLength = 0;
            this.firstBytes = new int[this.boms.get(0).length()];
            for (int i = 0; i < this.firstBytes.length; ++i) {
                this.firstBytes[i] = this.in.read();
                ++this.fbLength;
                if (this.firstBytes[i] < 0) {
                    break;
                }
            }
            this.byteOrderMark = this.find();
            if (this.byteOrderMark != null && !this.include) {
                if (this.byteOrderMark.length() < this.firstBytes.length) {
                    this.fbIndex = this.byteOrderMark.length();
                }
                else {
                    this.fbLength = 0;
                }
            }
        }
        return this.byteOrderMark;
    }
    
    public String getBOMCharsetName() {
        this.getBOM();
        return (this.byteOrderMark == null) ? null : this.byteOrderMark.getCharsetName();
    }
    
    private int readFirstBytes() {
        this.getBOM();
        return (this.fbIndex < this.fbLength) ? this.firstBytes[this.fbIndex++] : -1;
    }
    
    private ByteOrderMark find() {
        for (final ByteOrderMark byteOrderMark : this.boms) {
            if (this.matches(byteOrderMark)) {
                return byteOrderMark;
            }
        }
        return null;
    }
    
    private boolean matches(final ByteOrderMark byteOrderMark) {
        for (int i = 0; i < byteOrderMark.length(); ++i) {
            if (byteOrderMark.get(i) != this.firstBytes[i]) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int read() {
        final int firstBytes = this.readFirstBytes();
        return (firstBytes >= 0) ? firstBytes : this.in.read();
    }
    
    @Override
    public int read(final byte[] b, int off, int len) {
        int n = 0;
        for (int firstBytes = 0; len > 0 && firstBytes >= 0; --len, ++n) {
            firstBytes = this.readFirstBytes();
            if (firstBytes >= 0) {
                b[off++] = (byte)(firstBytes & 0xFF);
            }
        }
        final int read = this.in.read(b, off, len);
        return (read < 0) ? ((n > 0) ? n : -1) : (n + read);
    }
    
    @Override
    public int read(final byte[] array) {
        return this.read(array, 0, array.length);
    }
    
    @Override
    public synchronized void mark(final int readlimit) {
        this.markFbIndex = this.fbIndex;
        this.markedAtStart = (this.firstBytes == null);
        this.in.mark(readlimit);
    }
    
    @Override
    public synchronized void reset() {
        this.fbIndex = this.markFbIndex;
        if (this.markedAtStart) {
            this.firstBytes = null;
        }
        this.in.reset();
    }
    
    @Override
    public long skip(final long n) {
        int n2;
        for (n2 = 0; n > n2 && this.readFirstBytes() >= 0; ++n2) {}
        return this.in.skip(n - n2) + n2;
    }
    
    static {
        ByteOrderMarkLengthComparator = new Comparator<ByteOrderMark>() {
            @Override
            public int compare(final ByteOrderMark byteOrderMark, final ByteOrderMark byteOrderMark2) {
                final int length = byteOrderMark.length();
                final int length2 = byteOrderMark2.length();
                if (length > length2) {
                    return -1;
                }
                if (length2 > length) {
                    return 1;
                }
                return 0;
            }
        };
    }
}
