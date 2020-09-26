

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import me.TechsCode.EnderPermissions.dependencies.commons.io.Charsets;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.io.Closeable;

public class ReversedLinesFileReader implements Closeable
{
    private final int blockSize;
    private final Charset encoding;
    private final RandomAccessFile randomAccessFile;
    private final long totalByteLength;
    private final long totalBlockCount;
    private final byte[][] newLineSequences;
    private final int avoidNewlineSplitBufferSize;
    private final int byteDecrement;
    private FilePart currentFilePart;
    private boolean trailingNewlineOfFileSkipped;
    
    @Deprecated
    public ReversedLinesFileReader(final File file) {
        this(file, 4096, Charset.defaultCharset());
    }
    
    public ReversedLinesFileReader(final File file, final Charset charset) {
        this(file, 4096, charset);
    }
    
    public ReversedLinesFileReader(final File file, final int blockSize, final Charset charset) {
        this.trailingNewlineOfFileSkipped = false;
        this.blockSize = blockSize;
        this.encoding = charset;
        final Charset charset2 = Charsets.toCharset(charset);
        if (charset2.newEncoder().maxBytesPerChar() == 1.0f) {
            this.byteDecrement = 1;
        }
        else if (charset2 == StandardCharsets.UTF_8) {
            this.byteDecrement = 1;
        }
        else if (charset2 == Charset.forName("Shift_JIS") || charset2 == Charset.forName("windows-31j") || charset2 == Charset.forName("x-windows-949") || charset2 == Charset.forName("gbk") || charset2 == Charset.forName("x-windows-950")) {
            this.byteDecrement = 1;
        }
        else if (charset2 == StandardCharsets.UTF_16BE || charset2 == StandardCharsets.UTF_16LE) {
            this.byteDecrement = 2;
        }
        else {
            if (charset2 == StandardCharsets.UTF_16) {
                throw new UnsupportedEncodingException("For UTF-16, you need to specify the byte order (use UTF-16BE or UTF-16LE)");
            }
            throw new UnsupportedEncodingException("Encoding " + charset + " is not supported yet (feel free to submit a patch)");
        }
        this.newLineSequences = new byte[][] { "\r\n".getBytes(charset), "\n".getBytes(charset), "\r".getBytes(charset) };
        this.avoidNewlineSplitBufferSize = this.newLineSequences[0].length;
        this.randomAccessFile = new RandomAccessFile(file, "r");
        this.totalByteLength = this.randomAccessFile.length();
        int n = (int)(this.totalByteLength % blockSize);
        if (n > 0) {
            this.totalBlockCount = this.totalByteLength / blockSize + 1L;
        }
        else {
            this.totalBlockCount = this.totalByteLength / blockSize;
            if (this.totalByteLength > 0L) {
                n = blockSize;
            }
        }
        this.currentFilePart = new FilePart(this.totalBlockCount, n, (byte[])null);
    }
    
    public ReversedLinesFileReader(final File file, final int n, final String s) {
        this(file, n, Charsets.toCharset(s));
    }
    
    public String readLine() {
        String anObject;
        for (anObject = this.currentFilePart.readLine(); anObject == null; anObject = this.currentFilePart.readLine()) {
            this.currentFilePart = this.currentFilePart.rollOver();
            if (this.currentFilePart == null) {
                break;
            }
        }
        if ("".equals(anObject) && !this.trailingNewlineOfFileSkipped) {
            this.trailingNewlineOfFileSkipped = true;
            anObject = this.readLine();
        }
        return anObject;
    }
    
    @Override
    public void close() {
        this.randomAccessFile.close();
    }
    
    private class FilePart
    {
        private final long no;
        private final byte[] data;
        private byte[] leftOver;
        private int currentLastBytePos;
        
        private FilePart(final long no, final int len, final byte[] array) {
            this.no = no;
            this.data = new byte[len + ((array != null) ? array.length : 0)];
            final long pos = (no - 1L) * ReversedLinesFileReader.this.blockSize;
            if (no > 0L) {
                ReversedLinesFileReader.this.randomAccessFile.seek(pos);
                if (ReversedLinesFileReader.this.randomAccessFile.read(this.data, 0, len) != len) {
                    throw new IllegalStateException("Count of requested bytes and actually read bytes don't match");
                }
            }
            if (array != null) {
                System.arraycopy(array, 0, this.data, len, array.length);
            }
            this.currentLastBytePos = this.data.length - 1;
            this.leftOver = null;
        }
        
        private FilePart rollOver() {
            if (this.currentLastBytePos > -1) {
                throw new IllegalStateException("Current currentLastCharPos unexpectedly positive... last readLine() should have returned something! currentLastCharPos=" + this.currentLastBytePos);
            }
            if (this.no > 1L) {
                return new FilePart(this.no - 1L, ReversedLinesFileReader.this.blockSize, this.leftOver);
            }
            if (this.leftOver != null) {
                throw new IllegalStateException("Unexpected leftover of the last block: leftOverOfThisFilePart=" + new String(this.leftOver, ReversedLinesFileReader.this.encoding));
            }
            return null;
        }
        
        private String readLine() {
            String s = null;
            final boolean b = this.no == 1L;
            int i = this.currentLastBytePos;
            while (i > -1) {
                if (!b && i < ReversedLinesFileReader.this.avoidNewlineSplitBufferSize) {
                    this.createLeftOver();
                    break;
                }
                final int newLineMatchByteCount;
                if ((newLineMatchByteCount = this.getNewLineMatchByteCount(this.data, i)) > 0) {
                    final int n = i + 1;
                    final int j = this.currentLastBytePos - n + 1;
                    if (j < 0) {
                        throw new IllegalStateException("Unexpected negative line length=" + j);
                    }
                    final byte[] bytes = new byte[j];
                    System.arraycopy(this.data, n, bytes, 0, j);
                    s = new String(bytes, ReversedLinesFileReader.this.encoding);
                    this.currentLastBytePos = i - newLineMatchByteCount;
                    break;
                }
                else {
                    i -= ReversedLinesFileReader.this.byteDecrement;
                    if (i < 0) {
                        this.createLeftOver();
                        break;
                    }
                    continue;
                }
            }
            if (b && this.leftOver != null) {
                s = new String(this.leftOver, ReversedLinesFileReader.this.encoding);
                this.leftOver = null;
            }
            return s;
        }
        
        private void createLeftOver() {
            final int n = this.currentLastBytePos + 1;
            if (n > 0) {
                this.leftOver = new byte[n];
                System.arraycopy(this.data, 0, this.leftOver, 0, n);
            }
            else {
                this.leftOver = null;
            }
            this.currentLastBytePos = -1;
        }
        
        private int getNewLineMatchByteCount(final byte[] array, final int n) {
            for (final byte[] array2 : ReversedLinesFileReader.this.newLineSequences) {
                boolean b = true;
                for (int j = array2.length - 1; j >= 0; --j) {
                    final int n2 = n + j - (array2.length - 1);
                    b &= (n2 >= 0 && array[n2] == array2[j]);
                }
                if (b) {
                    return array2.length;
                }
            }
            return 0;
        }
    }
}
