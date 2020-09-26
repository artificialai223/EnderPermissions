

package me.TechsCode.EnderPermissions.dependencies.commons.io;

import java.io.PrintWriter;
import java.io.File;
import java.io.EOFException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Collection;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.util.List;
import me.TechsCode.EnderPermissions.dependencies.commons.io.output.StringBuilderWriter;
import java.io.CharArrayWriter;
import java.net.URL;
import java.net.URI;
import java.nio.charset.Charset;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import me.TechsCode.EnderPermissions.dependencies.commons.io.output.ByteArrayOutputStream;
import java.net.ServerSocket;
import java.nio.channels.Selector;
import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.Writer;
import java.io.Closeable;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URLConnection;

public class IOUtils
{
    public static final int EOF = -1;
    public static final char DIR_SEPARATOR_UNIX = '/';
    public static final char DIR_SEPARATOR_WINDOWS = '\\';
    public static final char DIR_SEPARATOR;
    public static final String LINE_SEPARATOR_UNIX = "\n";
    public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
    public static final String LINE_SEPARATOR;
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final int SKIP_BUFFER_SIZE = 2048;
    private static char[] SKIP_CHAR_BUFFER;
    private static byte[] SKIP_BYTE_BUFFER;
    
    public static void close(final URLConnection urlConnection) {
        if (urlConnection instanceof HttpURLConnection) {
            ((HttpURLConnection)urlConnection).disconnect();
        }
    }
    
    @Deprecated
    public static void closeQuietly(final Reader reader) {
        closeQuietly((Closeable)reader);
    }
    
    @Deprecated
    public static void closeQuietly(final Writer writer) {
        closeQuietly((Closeable)writer);
    }
    
    @Deprecated
    public static void closeQuietly(final InputStream inputStream) {
        closeQuietly((Closeable)inputStream);
    }
    
    @Deprecated
    public static void closeQuietly(final OutputStream outputStream) {
        closeQuietly((Closeable)outputStream);
    }
    
    @Deprecated
    public static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        }
        catch (IOException ex) {}
    }
    
    @Deprecated
    public static void closeQuietly(final Closeable... array) {
        if (array == null) {
            return;
        }
        for (int length = array.length, i = 0; i < length; ++i) {
            closeQuietly(array[i]);
        }
    }
    
    @Deprecated
    public static void closeQuietly(final Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            }
            catch (IOException ex) {}
        }
    }
    
    @Deprecated
    public static void closeQuietly(final Selector selector) {
        if (selector != null) {
            try {
                selector.close();
            }
            catch (IOException ex) {}
        }
    }
    
    @Deprecated
    public static void closeQuietly(final ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            }
            catch (IOException ex) {}
        }
    }
    
    public static InputStream toBufferedInputStream(final InputStream inputStream) {
        return ByteArrayOutputStream.toBufferedInputStream(inputStream);
    }
    
    public static InputStream toBufferedInputStream(final InputStream inputStream, final int n) {
        return ByteArrayOutputStream.toBufferedInputStream(inputStream, n);
    }
    
    public static BufferedReader toBufferedReader(final Reader in) {
        return (BufferedReader)((in instanceof BufferedReader) ? in : new BufferedReader(in));
    }
    
    public static BufferedReader toBufferedReader(final Reader in, final int sz) {
        return (BufferedReader)((in instanceof BufferedReader) ? in : new BufferedReader(in, sz));
    }
    
    public static BufferedReader buffer(final Reader in) {
        return (BufferedReader)((in instanceof BufferedReader) ? in : new BufferedReader(in));
    }
    
    public static BufferedReader buffer(final Reader in, final int sz) {
        return (BufferedReader)((in instanceof BufferedReader) ? in : new BufferedReader(in, sz));
    }
    
    public static BufferedWriter buffer(final Writer out) {
        return (BufferedWriter)((out instanceof BufferedWriter) ? out : new BufferedWriter(out));
    }
    
    public static BufferedWriter buffer(final Writer out, final int sz) {
        return (BufferedWriter)((out instanceof BufferedWriter) ? out : new BufferedWriter(out, sz));
    }
    
    public static BufferedOutputStream buffer(final OutputStream out) {
        if (out == null) {
            throw new NullPointerException();
        }
        return (BufferedOutputStream)((out instanceof BufferedOutputStream) ? out : new BufferedOutputStream(out));
    }
    
    public static BufferedOutputStream buffer(final OutputStream out, final int size) {
        if (out == null) {
            throw new NullPointerException();
        }
        return (BufferedOutputStream)((out instanceof BufferedOutputStream) ? out : new BufferedOutputStream(out, size));
    }
    
    public static BufferedInputStream buffer(final InputStream in) {
        if (in == null) {
            throw new NullPointerException();
        }
        return (BufferedInputStream)((in instanceof BufferedInputStream) ? in : new BufferedInputStream(in));
    }
    
    public static BufferedInputStream buffer(final InputStream in, final int size) {
        if (in == null) {
            throw new NullPointerException();
        }
        return (BufferedInputStream)((in instanceof BufferedInputStream) ? in : new BufferedInputStream(in, size));
    }
    
    public static byte[] toByteArray(final InputStream inputStream) {
        try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            copy(inputStream, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }
    
    public static byte[] toByteArray(final InputStream inputStream, final long lng) {
        if (lng > 2147483647L) {
            throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + lng);
        }
        return toByteArray(inputStream, (int)lng);
    }
    
    public static byte[] toByteArray(final InputStream inputStream, final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + n);
        }
        if (n == 0) {
            return new byte[0];
        }
        byte[] b;
        int n2;
        int read;
        for (b = new byte[n], n2 = 0; n2 < n && (read = inputStream.read(b, n2, n - n2)) != -1; n2 += read) {}
        if (n2 != n) {
            throw new IOException("Unexpected read size. current: " + n2 + ", expected: " + n);
        }
        return b;
    }
    
    @Deprecated
    public static byte[] toByteArray(final Reader reader) {
        return toByteArray(reader, Charset.defaultCharset());
    }
    
    public static byte[] toByteArray(final Reader reader, final Charset charset) {
        try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            copy(reader, byteArrayOutputStream, charset);
            return byteArrayOutputStream.toByteArray();
        }
    }
    
    public static byte[] toByteArray(final Reader reader, final String s) {
        return toByteArray(reader, Charsets.toCharset(s));
    }
    
    @Deprecated
    public static byte[] toByteArray(final String s) {
        return s.getBytes(Charset.defaultCharset());
    }
    
    public static byte[] toByteArray(final URI uri) {
        return toByteArray(uri.toURL());
    }
    
    public static byte[] toByteArray(final URL url) {
        final URLConnection openConnection = url.openConnection();
        try {
            return toByteArray(openConnection);
        }
        finally {
            close(openConnection);
        }
    }
    
    public static byte[] toByteArray(final URLConnection urlConnection) {
        try (final InputStream inputStream = urlConnection.getInputStream()) {
            return toByteArray(inputStream);
        }
    }
    
    @Deprecated
    public static char[] toCharArray(final InputStream inputStream) {
        return toCharArray(inputStream, Charset.defaultCharset());
    }
    
    public static char[] toCharArray(final InputStream inputStream, final Charset charset) {
        final CharArrayWriter charArrayWriter = new CharArrayWriter();
        copy(inputStream, charArrayWriter, charset);
        return charArrayWriter.toCharArray();
    }
    
    public static char[] toCharArray(final InputStream inputStream, final String s) {
        return toCharArray(inputStream, Charsets.toCharset(s));
    }
    
    public static char[] toCharArray(final Reader reader) {
        final CharArrayWriter charArrayWriter = new CharArrayWriter();
        copy(reader, charArrayWriter);
        return charArrayWriter.toCharArray();
    }
    
    @Deprecated
    public static String toString(final InputStream inputStream) {
        return toString(inputStream, Charset.defaultCharset());
    }
    
    public static String toString(final InputStream inputStream, final Charset charset) {
        try (final StringBuilderWriter stringBuilderWriter = new StringBuilderWriter()) {
            copy(inputStream, stringBuilderWriter, charset);
            return stringBuilderWriter.toString();
        }
    }
    
    public static String toString(final InputStream inputStream, final String s) {
        return toString(inputStream, Charsets.toCharset(s));
    }
    
    public static String toString(final Reader reader) {
        try (final StringBuilderWriter stringBuilderWriter = new StringBuilderWriter()) {
            copy(reader, stringBuilderWriter);
            return stringBuilderWriter.toString();
        }
    }
    
    @Deprecated
    public static String toString(final URI uri) {
        return toString(uri, Charset.defaultCharset());
    }
    
    public static String toString(final URI uri, final Charset charset) {
        return toString(uri.toURL(), Charsets.toCharset(charset));
    }
    
    public static String toString(final URI uri, final String s) {
        return toString(uri, Charsets.toCharset(s));
    }
    
    @Deprecated
    public static String toString(final URL url) {
        return toString(url, Charset.defaultCharset());
    }
    
    public static String toString(final URL url, final Charset charset) {
        try (final InputStream openStream = url.openStream()) {
            return toString(openStream, charset);
        }
    }
    
    public static String toString(final URL url, final String s) {
        return toString(url, Charsets.toCharset(s));
    }
    
    @Deprecated
    public static String toString(final byte[] bytes) {
        return new String(bytes, Charset.defaultCharset());
    }
    
    public static String toString(final byte[] bytes, final String s) {
        return new String(bytes, Charsets.toCharset(s));
    }
    
    public static String resourceToString(final String s, final Charset charset) {
        return resourceToString(s, charset, null);
    }
    
    public static String resourceToString(final String s, final Charset charset, final ClassLoader classLoader) {
        return toString(resourceToURL(s, classLoader), charset);
    }
    
    public static byte[] resourceToByteArray(final String s) {
        return resourceToByteArray(s, null);
    }
    
    public static byte[] resourceToByteArray(final String s, final ClassLoader classLoader) {
        return toByteArray(resourceToURL(s, classLoader));
    }
    
    public static URL resourceToURL(final String s) {
        return resourceToURL(s, null);
    }
    
    public static URL resourceToURL(final String str, final ClassLoader classLoader) {
        final URL url = (classLoader == null) ? IOUtils.class.getResource(str) : classLoader.getResource(str);
        if (url == null) {
            throw new IOException("Resource not found: " + str);
        }
        return url;
    }
    
    @Deprecated
    public static List<String> readLines(final InputStream inputStream) {
        return readLines(inputStream, Charset.defaultCharset());
    }
    
    public static List<String> readLines(final InputStream in, final Charset charset) {
        return readLines(new InputStreamReader(in, Charsets.toCharset(charset)));
    }
    
    public static List<String> readLines(final InputStream inputStream, final String s) {
        return readLines(inputStream, Charsets.toCharset(s));
    }
    
    public static List<String> readLines(final Reader reader) {
        final BufferedReader bufferedReader = toBufferedReader(reader);
        final ArrayList<String> list = new ArrayList<String>();
        for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) {
            list.add(s);
        }
        return list;
    }
    
    public static LineIterator lineIterator(final Reader reader) {
        return new LineIterator(reader);
    }
    
    public static LineIterator lineIterator(final InputStream in, final Charset charset) {
        return new LineIterator(new InputStreamReader(in, Charsets.toCharset(charset)));
    }
    
    public static LineIterator lineIterator(final InputStream inputStream, final String s) {
        return lineIterator(inputStream, Charsets.toCharset(s));
    }
    
    @Deprecated
    public static InputStream toInputStream(final CharSequence charSequence) {
        return toInputStream(charSequence, Charset.defaultCharset());
    }
    
    public static InputStream toInputStream(final CharSequence charSequence, final Charset charset) {
        return toInputStream(charSequence.toString(), charset);
    }
    
    public static InputStream toInputStream(final CharSequence charSequence, final String s) {
        return toInputStream(charSequence, Charsets.toCharset(s));
    }
    
    @Deprecated
    public static InputStream toInputStream(final String s) {
        return toInputStream(s, Charset.defaultCharset());
    }
    
    public static InputStream toInputStream(final String s, final Charset charset) {
        return new ByteArrayInputStream(s.getBytes(Charsets.toCharset(charset)));
    }
    
    public static InputStream toInputStream(final String s, final String s2) {
        return new ByteArrayInputStream(s.getBytes(Charsets.toCharset(s2)));
    }
    
    public static void write(final byte[] b, final OutputStream outputStream) {
        if (b != null) {
            outputStream.write(b);
        }
    }
    
    public static void writeChunked(final byte[] b, final OutputStream outputStream) {
        if (b != null) {
            int min;
            for (int i = b.length, off = 0; i > 0; i -= min, off += min) {
                min = Math.min(i, 4096);
                outputStream.write(b, off, min);
            }
        }
    }
    
    @Deprecated
    public static void write(final byte[] array, final Writer writer) {
        write(array, writer, Charset.defaultCharset());
    }
    
    public static void write(final byte[] bytes, final Writer writer, final Charset charset) {
        if (bytes != null) {
            writer.write(new String(bytes, Charsets.toCharset(charset)));
        }
    }
    
    public static void write(final byte[] array, final Writer writer, final String s) {
        write(array, writer, Charsets.toCharset(s));
    }
    
    public static void write(final char[] cbuf, final Writer writer) {
        if (cbuf != null) {
            writer.write(cbuf);
        }
    }
    
    public static void writeChunked(final char[] array, final Writer writer) {
        if (array != null) {
            int min;
            for (int i = array.length, n = 0; i > 0; i -= min, n += min) {
                min = Math.min(i, 4096);
                writer.write(array, n, min);
            }
        }
    }
    
    @Deprecated
    public static void write(final char[] array, final OutputStream outputStream) {
        write(array, outputStream, Charset.defaultCharset());
    }
    
    public static void write(final char[] value, final OutputStream outputStream, final Charset charset) {
        if (value != null) {
            outputStream.write(new String(value).getBytes(Charsets.toCharset(charset)));
        }
    }
    
    public static void write(final char[] array, final OutputStream outputStream, final String s) {
        write(array, outputStream, Charsets.toCharset(s));
    }
    
    public static void write(final CharSequence charSequence, final Writer writer) {
        if (charSequence != null) {
            write(charSequence.toString(), writer);
        }
    }
    
    @Deprecated
    public static void write(final CharSequence charSequence, final OutputStream outputStream) {
        write(charSequence, outputStream, Charset.defaultCharset());
    }
    
    public static void write(final CharSequence charSequence, final OutputStream outputStream, final Charset charset) {
        if (charSequence != null) {
            write(charSequence.toString(), outputStream, charset);
        }
    }
    
    public static void write(final CharSequence charSequence, final OutputStream outputStream, final String s) {
        write(charSequence, outputStream, Charsets.toCharset(s));
    }
    
    public static void write(final String str, final Writer writer) {
        if (str != null) {
            writer.write(str);
        }
    }
    
    @Deprecated
    public static void write(final String s, final OutputStream outputStream) {
        write(s, outputStream, Charset.defaultCharset());
    }
    
    public static void write(final String s, final OutputStream outputStream, final Charset charset) {
        if (s != null) {
            outputStream.write(s.getBytes(Charsets.toCharset(charset)));
        }
    }
    
    public static void write(final String s, final OutputStream outputStream, final String s2) {
        write(s, outputStream, Charsets.toCharset(s2));
    }
    
    @Deprecated
    public static void write(final StringBuffer sb, final Writer writer) {
        if (sb != null) {
            writer.write(sb.toString());
        }
    }
    
    @Deprecated
    public static void write(final StringBuffer sb, final OutputStream outputStream) {
        write(sb, outputStream, null);
    }
    
    @Deprecated
    public static void write(final StringBuffer sb, final OutputStream outputStream, final String s) {
        if (sb != null) {
            outputStream.write(sb.toString().getBytes(Charsets.toCharset(s)));
        }
    }
    
    @Deprecated
    public static void writeLines(final Collection<?> collection, final String s, final OutputStream outputStream) {
        writeLines(collection, s, outputStream, Charset.defaultCharset());
    }
    
    public static void writeLines(final Collection<?> collection, String line_SEPARATOR, final OutputStream outputStream, final Charset charset) {
        if (collection == null) {
            return;
        }
        if (line_SEPARATOR == null) {
            line_SEPARATOR = IOUtils.LINE_SEPARATOR;
        }
        final Charset charset2 = Charsets.toCharset(charset);
        for (final Object next : collection) {
            if (next != null) {
                outputStream.write(next.toString().getBytes(charset2));
            }
            outputStream.write(line_SEPARATOR.getBytes(charset2));
        }
    }
    
    public static void writeLines(final Collection<?> collection, final String s, final OutputStream outputStream, final String s2) {
        writeLines(collection, s, outputStream, Charsets.toCharset(s2));
    }
    
    public static void writeLines(final Collection<?> collection, String line_SEPARATOR, final Writer writer) {
        if (collection == null) {
            return;
        }
        if (line_SEPARATOR == null) {
            line_SEPARATOR = IOUtils.LINE_SEPARATOR;
        }
        for (final Object next : collection) {
            if (next != null) {
                writer.write(next.toString());
            }
            writer.write(line_SEPARATOR);
        }
    }
    
    public static int copy(final InputStream inputStream, final OutputStream outputStream) {
        final long copyLarge = copyLarge(inputStream, outputStream);
        if (copyLarge > 2147483647L) {
            return -1;
        }
        return (int)copyLarge;
    }
    
    public static long copy(final InputStream inputStream, final OutputStream outputStream, final int n) {
        return copyLarge(inputStream, outputStream, new byte[n]);
    }
    
    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream) {
        return copy(inputStream, outputStream, 4096);
    }
    
    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream, final byte[] array) {
        long n = 0L;
        int read;
        while (-1 != (read = inputStream.read(array))) {
            outputStream.write(array, 0, read);
            n += read;
        }
        return n;
    }
    
    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream, final long n, final long n2) {
        return copyLarge(inputStream, outputStream, n, n2, new byte[4096]);
    }
    
    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream, final long n, final long n2, final byte[] array) {
        if (n > 0L) {
            skipFully(inputStream, n);
        }
        if (n2 == 0L) {
            return 0L;
        }
        int length;
        final int n3 = length = array.length;
        if (n2 > 0L && n2 < n3) {
            length = (int)n2;
        }
        long n4;
        int read;
        for (n4 = 0L; length > 0 && -1 != (read = inputStream.read(array, 0, length)); length = (int)Math.min(n2 - n4, n3)) {
            outputStream.write(array, 0, read);
            n4 += read;
            if (n2 > 0L) {}
        }
        return n4;
    }
    
    @Deprecated
    public static void copy(final InputStream inputStream, final Writer writer) {
        copy(inputStream, writer, Charset.defaultCharset());
    }
    
    public static void copy(final InputStream in, final Writer writer, final Charset charset) {
        copy(new InputStreamReader(in, Charsets.toCharset(charset)), writer);
    }
    
    public static void copy(final InputStream inputStream, final Writer writer, final String s) {
        copy(inputStream, writer, Charsets.toCharset(s));
    }
    
    public static int copy(final Reader reader, final Writer writer) {
        final long copyLarge = copyLarge(reader, writer);
        if (copyLarge > 2147483647L) {
            return -1;
        }
        return (int)copyLarge;
    }
    
    public static long copyLarge(final Reader reader, final Writer writer) {
        return copyLarge(reader, writer, new char[4096]);
    }
    
    public static long copyLarge(final Reader reader, final Writer writer, final char[] cbuf) {
        long n = 0L;
        int read;
        while (-1 != (read = reader.read(cbuf))) {
            writer.write(cbuf, 0, read);
            n += read;
        }
        return n;
    }
    
    public static long copyLarge(final Reader reader, final Writer writer, final long n, final long n2) {
        return copyLarge(reader, writer, n, n2, new char[4096]);
    }
    
    public static long copyLarge(final Reader reader, final Writer writer, final long n, final long n2, final char[] array) {
        if (n > 0L) {
            skipFully(reader, n);
        }
        if (n2 == 0L) {
            return 0L;
        }
        int length = array.length;
        if (n2 > 0L && n2 < array.length) {
            length = (int)n2;
        }
        long n3;
        int read;
        for (n3 = 0L; length > 0 && -1 != (read = reader.read(array, 0, length)); length = (int)Math.min(n2 - n3, array.length)) {
            writer.write(array, 0, read);
            n3 += read;
            if (n2 > 0L) {}
        }
        return n3;
    }
    
    @Deprecated
    public static void copy(final Reader reader, final OutputStream outputStream) {
        copy(reader, outputStream, Charset.defaultCharset());
    }
    
    public static void copy(final Reader reader, final OutputStream out, final Charset charset) {
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out, Charsets.toCharset(charset));
        copy(reader, outputStreamWriter);
        outputStreamWriter.flush();
    }
    
    public static void copy(final Reader reader, final OutputStream outputStream, final String s) {
        copy(reader, outputStream, Charsets.toCharset(s));
    }
    
    public static boolean contentEquals(InputStream in, InputStream in2) {
        if (in == in2) {
            return true;
        }
        if (!(in instanceof BufferedInputStream)) {
            in = new BufferedInputStream(in);
        }
        if (!(in2 instanceof BufferedInputStream)) {
            in2 = new BufferedInputStream(in2);
        }
        for (int n = in.read(); -1 != n; n = in.read()) {
            if (n != in2.read()) {
                return false;
            }
        }
        return in2.read() == -1;
    }
    
    public static boolean contentEquals(final Reader reader, final Reader reader2) {
        if (reader == reader2) {
            return true;
        }
        final BufferedReader bufferedReader = toBufferedReader(reader);
        final BufferedReader bufferedReader2 = toBufferedReader(reader2);
        for (int n = bufferedReader.read(); -1 != n; n = bufferedReader.read()) {
            if (n != bufferedReader2.read()) {
                return false;
            }
        }
        return bufferedReader2.read() == -1;
    }
    
    public static boolean contentEqualsIgnoreEOL(final Reader reader, final Reader reader2) {
        if (reader == reader2) {
            return true;
        }
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        String s;
        String s2;
        for (bufferedReader = toBufferedReader(reader), bufferedReader2 = toBufferedReader(reader2), s = bufferedReader.readLine(), s2 = bufferedReader2.readLine(); s != null && s2 != null && s.equals(s2); s = bufferedReader.readLine(), s2 = bufferedReader2.readLine()) {}
        return (s == null) ? (s2 == null) : s.equals(s2);
    }
    
    public static long skip(final InputStream inputStream, final long lng) {
        if (lng < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + lng);
        }
        if (IOUtils.SKIP_BYTE_BUFFER == null) {
            IOUtils.SKIP_BYTE_BUFFER = new byte[2048];
        }
        long a;
        long n;
        for (a = lng; a > 0L; a -= n) {
            n = inputStream.read(IOUtils.SKIP_BYTE_BUFFER, 0, (int)Math.min(a, 2048L));
            if (n < 0L) {
                break;
            }
        }
        return lng - a;
    }
    
    public static long skip(final ReadableByteChannel readableByteChannel, final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + n);
        }
        final ByteBuffer allocate = ByteBuffer.allocate((int)Math.min(n, 2048L));
        long a;
        int read;
        for (a = n; a > 0L; a -= read) {
            allocate.position(0);
            allocate.limit((int)Math.min(a, 2048L));
            read = readableByteChannel.read(allocate);
            if (read == -1) {
                break;
            }
        }
        return n - a;
    }
    
    public static long skip(final Reader reader, final long lng) {
        if (lng < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + lng);
        }
        if (IOUtils.SKIP_CHAR_BUFFER == null) {
            IOUtils.SKIP_CHAR_BUFFER = new char[2048];
        }
        long a;
        long n;
        for (a = lng; a > 0L; a -= n) {
            n = reader.read(IOUtils.SKIP_CHAR_BUFFER, 0, (int)Math.min(a, 2048L));
            if (n < 0L) {
                break;
            }
        }
        return lng - a;
    }
    
    public static void skipFully(final InputStream inputStream, final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + n);
        }
        final long skip = skip(inputStream, n);
        if (skip != n) {
            throw new EOFException("Bytes to skip: " + n + " actual: " + skip);
        }
    }
    
    public static void skipFully(final ReadableByteChannel readableByteChannel, final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + n);
        }
        final long skip = skip(readableByteChannel, n);
        if (skip != n) {
            throw new EOFException("Bytes to skip: " + n + " actual: " + skip);
        }
    }
    
    public static void skipFully(final Reader reader, final long lng) {
        final long skip = skip(reader, lng);
        if (skip != lng) {
            throw new EOFException("Chars to skip: " + lng + " actual: " + skip);
        }
    }
    
    public static int read(final Reader reader, final char[] array, final int n, final int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + i);
        }
        int j;
        int read;
        for (j = i; j > 0; j -= read) {
            read = reader.read(array, n + (i - j), j);
            if (-1 == read) {
                break;
            }
        }
        return i - j;
    }
    
    public static int read(final Reader reader, final char[] array) {
        return read(reader, array, 0, array.length);
    }
    
    public static int read(final InputStream inputStream, final byte[] b, final int n, final int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + i);
        }
        int j;
        int read;
        for (j = i; j > 0; j -= read) {
            read = inputStream.read(b, n + (i - j), j);
            if (-1 == read) {
                break;
            }
        }
        return i - j;
    }
    
    public static int read(final InputStream inputStream, final byte[] array) {
        return read(inputStream, array, 0, array.length);
    }
    
    public static int read(final ReadableByteChannel readableByteChannel, final ByteBuffer byteBuffer) {
        final int remaining = byteBuffer.remaining();
        while (byteBuffer.remaining() > 0 && -1 != readableByteChannel.read(byteBuffer)) {}
        return remaining - byteBuffer.remaining();
    }
    
    public static void readFully(final Reader reader, final char[] array, final int n, final int i) {
        final int read = read(reader, array, n, i);
        if (read != i) {
            throw new EOFException("Length to read: " + i + " actual: " + read);
        }
    }
    
    public static void readFully(final Reader reader, final char[] array) {
        readFully(reader, array, 0, array.length);
    }
    
    public static void readFully(final InputStream inputStream, final byte[] array, final int n, final int i) {
        final int read = read(inputStream, array, n, i);
        if (read != i) {
            throw new EOFException("Length to read: " + i + " actual: " + read);
        }
    }
    
    public static void readFully(final InputStream inputStream, final byte[] array) {
        readFully(inputStream, array, 0, array.length);
    }
    
    public static byte[] readFully(final InputStream inputStream, final int n) {
        final byte[] array = new byte[n];
        readFully(inputStream, array, 0, array.length);
        return array;
    }
    
    public static void readFully(final ReadableByteChannel readableByteChannel, final ByteBuffer byteBuffer) {
        final int remaining = byteBuffer.remaining();
        final int read = read(readableByteChannel, byteBuffer);
        if (read != remaining) {
            throw new EOFException("Length to read: " + remaining + " actual: " + read);
        }
    }
    
    static {
        DIR_SEPARATOR = File.separatorChar;
        try (final StringBuilderWriter out = new StringBuilderWriter(4);
             final PrintWriter printWriter = new PrintWriter(out)) {
            printWriter.println();
            LINE_SEPARATOR = out.toString();
        }
    }
}
