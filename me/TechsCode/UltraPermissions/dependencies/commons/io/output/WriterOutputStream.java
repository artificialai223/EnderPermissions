

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.nio.charset.CoderResult;
import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.Charset;
import java.nio.CharBuffer;
import java.nio.ByteBuffer;
import java.nio.charset.CharsetDecoder;
import java.io.Writer;
import java.io.OutputStream;

public class WriterOutputStream extends OutputStream
{
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final Writer writer;
    private final CharsetDecoder decoder;
    private final boolean writeImmediately;
    private final ByteBuffer decoderIn;
    private final CharBuffer decoderOut;
    
    public WriterOutputStream(final Writer writer, final CharsetDecoder charsetDecoder) {
        this(writer, charsetDecoder, 1024, false);
    }
    
    public WriterOutputStream(final Writer writer, final CharsetDecoder decoder, final int capacity, final boolean writeImmediately) {
        this.decoderIn = ByteBuffer.allocate(128);
        checkIbmJdkWithBrokenUTF16(decoder.charset());
        this.writer = writer;
        this.decoder = decoder;
        this.writeImmediately = writeImmediately;
        this.decoderOut = CharBuffer.allocate(capacity);
    }
    
    public WriterOutputStream(final Writer writer, final Charset charset, final int n, final boolean b) {
        this(writer, charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).replaceWith("?"), n, b);
    }
    
    public WriterOutputStream(final Writer writer, final Charset charset) {
        this(writer, charset, 1024, false);
    }
    
    public WriterOutputStream(final Writer writer, final String charsetName, final int n, final boolean b) {
        this(writer, Charset.forName(charsetName), n, b);
    }
    
    public WriterOutputStream(final Writer writer, final String s) {
        this(writer, s, 1024, false);
    }
    
    @Deprecated
    public WriterOutputStream(final Writer writer) {
        this(writer, Charset.defaultCharset(), 1024, false);
    }
    
    @Override
    public void write(final byte[] src, int offset, int i) {
        while (i > 0) {
            final int min = Math.min(i, this.decoderIn.remaining());
            this.decoderIn.put(src, offset, min);
            this.processInput(false);
            i -= min;
            offset += min;
        }
        if (this.writeImmediately) {
            this.flushOutput();
        }
    }
    
    @Override
    public void write(final byte[] array) {
        this.write(array, 0, array.length);
    }
    
    @Override
    public void write(final int n) {
        this.write(new byte[] { (byte)n }, 0, 1);
    }
    
    @Override
    public void flush() {
        this.flushOutput();
        this.writer.flush();
    }
    
    @Override
    public void close() {
        this.processInput(true);
        this.flushOutput();
        this.writer.close();
    }
    
    private void processInput(final boolean endOfInput) {
        this.decoderIn.flip();
        CoderResult decode;
        while (true) {
            decode = this.decoder.decode(this.decoderIn, this.decoderOut, endOfInput);
            if (!decode.isOverflow()) {
                break;
            }
            this.flushOutput();
        }
        if (decode.isUnderflow()) {
            this.decoderIn.compact();
            return;
        }
        throw new IOException("Unexpected coder result");
    }
    
    private void flushOutput() {
        if (this.decoderOut.position() > 0) {
            this.writer.write(this.decoderOut.array(), 0, this.decoderOut.position());
            this.decoderOut.rewind();
        }
    }
    
    private static void checkIbmJdkWithBrokenUTF16(final Charset charset) {
        if (!"UTF-16".equals(charset.name())) {
            return;
        }
        final byte[] bytes = "v\u00e9s".getBytes(charset);
        final CharsetDecoder decoder = charset.newDecoder();
        final ByteBuffer allocate = ByteBuffer.allocate(16);
        final CharBuffer allocate2 = CharBuffer.allocate("v\u00e9s".length());
        for (int length = bytes.length, i = 0; i < length; ++i) {
            allocate.put(bytes[i]);
            allocate.flip();
            try {
                decoder.decode(allocate, allocate2, i == length - 1);
            }
            catch (IllegalArgumentException ex) {
                throw new UnsupportedOperationException("UTF-16 requested when runninng on an IBM JDK with broken UTF-16 support. Please find a JDK that supports UTF-16 if you intend to use UF-16 with WriterOutputStream");
            }
            allocate.compact();
        }
        allocate2.rewind();
        if (!"v\u00e9s".equals(allocate2.toString())) {
            throw new UnsupportedOperationException("UTF-16 requested when runninng on an IBM JDK with broken UTF-16 support. Please find a JDK that supports UTF-16 if you intend to use UF-16 with WriterOutputStream");
        }
    }
}
