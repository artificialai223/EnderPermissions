

package me.TechsCode.EnderPermissions.dependencies.commons.io;

import java.io.StringReader;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.io.Reader;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.Writer;
import java.io.OutputStream;

@Deprecated
public class CopyUtils
{
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    
    public static void copy(final byte[] b, final OutputStream outputStream) {
        outputStream.write(b);
    }
    
    @Deprecated
    public static void copy(final byte[] buf, final Writer writer) {
        copy(new ByteArrayInputStream(buf), writer);
    }
    
    public static void copy(final byte[] buf, final Writer writer, final String s) {
        copy(new ByteArrayInputStream(buf), writer, s);
    }
    
    public static int copy(final InputStream inputStream, final OutputStream outputStream) {
        final byte[] array = new byte[4096];
        int n = 0;
        int read;
        while (-1 != (read = inputStream.read(array))) {
            outputStream.write(array, 0, read);
            n += read;
        }
        return n;
    }
    
    public static int copy(final Reader reader, final Writer writer) {
        final char[] cbuf = new char[4096];
        int n = 0;
        int read;
        while (-1 != (read = reader.read(cbuf))) {
            writer.write(cbuf, 0, read);
            n += read;
        }
        return n;
    }
    
    @Deprecated
    public static void copy(final InputStream in, final Writer writer) {
        copy(new InputStreamReader(in, Charset.defaultCharset()), writer);
    }
    
    public static void copy(final InputStream in, final Writer writer, final String charsetName) {
        copy(new InputStreamReader(in, charsetName), writer);
    }
    
    @Deprecated
    public static void copy(final Reader reader, final OutputStream out) {
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out, Charset.defaultCharset());
        copy(reader, outputStreamWriter);
        outputStreamWriter.flush();
    }
    
    public static void copy(final Reader reader, final OutputStream out, final String charsetName) {
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out, charsetName);
        copy(reader, outputStreamWriter);
        outputStreamWriter.flush();
    }
    
    @Deprecated
    public static void copy(final String s, final OutputStream out) {
        final StringReader stringReader = new StringReader(s);
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out, Charset.defaultCharset());
        copy(stringReader, outputStreamWriter);
        outputStreamWriter.flush();
    }
    
    public static void copy(final String s, final OutputStream out, final String charsetName) {
        final StringReader stringReader = new StringReader(s);
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out, charsetName);
        copy(stringReader, outputStreamWriter);
        outputStreamWriter.flush();
    }
    
    public static void copy(final String str, final Writer writer) {
        writer.write(str);
    }
}
