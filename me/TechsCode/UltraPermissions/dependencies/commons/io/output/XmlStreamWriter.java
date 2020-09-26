

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import me.TechsCode.EnderPermissions.dependencies.commons.io.input.XmlStreamReader;
import java.util.regex.Matcher;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.util.regex.Pattern;
import java.io.StringWriter;
import java.io.OutputStream;
import java.io.Writer;

public class XmlStreamWriter extends Writer
{
    private static final int BUFFER_SIZE = 4096;
    private final OutputStream out;
    private final String defaultEncoding;
    private StringWriter xmlPrologWriter;
    private Writer writer;
    private String encoding;
    static final Pattern ENCODING_PATTERN;
    
    public XmlStreamWriter(final OutputStream outputStream) {
        this(outputStream, null);
    }
    
    public XmlStreamWriter(final OutputStream out, final String s) {
        this.xmlPrologWriter = new StringWriter(4096);
        this.out = out;
        this.defaultEncoding = ((s != null) ? s : "UTF-8");
    }
    
    public XmlStreamWriter(final File file) {
        this(file, null);
    }
    
    public XmlStreamWriter(final File file, final String s) {
        this(new FileOutputStream(file), s);
    }
    
    public String getEncoding() {
        return this.encoding;
    }
    
    public String getDefaultEncoding() {
        return this.defaultEncoding;
    }
    
    @Override
    public void close() {
        if (this.writer == null) {
            this.encoding = this.defaultEncoding;
            (this.writer = new OutputStreamWriter(this.out, this.encoding)).write(this.xmlPrologWriter.toString());
        }
        this.writer.close();
    }
    
    @Override
    public void flush() {
        if (this.writer != null) {
            this.writer.flush();
        }
    }
    
    private void detectEncoding(final char[] cbuf, final int off, final int n) {
        int len = n;
        final StringBuffer buffer = this.xmlPrologWriter.getBuffer();
        if (buffer.length() + n > 4096) {
            len = 4096 - buffer.length();
        }
        this.xmlPrologWriter.write(cbuf, off, len);
        if (buffer.length() >= 5) {
            if (buffer.substring(0, 5).equals("<?xml")) {
                final int index = buffer.indexOf("?>");
                if (index > 0) {
                    final Matcher matcher = XmlStreamWriter.ENCODING_PATTERN.matcher(buffer.substring(0, index));
                    if (matcher.find()) {
                        this.encoding = matcher.group(1).toUpperCase();
                        this.encoding = this.encoding.substring(1, this.encoding.length() - 1);
                    }
                    else {
                        this.encoding = this.defaultEncoding;
                    }
                }
                else if (buffer.length() >= 4096) {
                    this.encoding = this.defaultEncoding;
                }
            }
            else {
                this.encoding = this.defaultEncoding;
            }
            if (this.encoding != null) {
                this.xmlPrologWriter = null;
                (this.writer = new OutputStreamWriter(this.out, this.encoding)).write(buffer.toString());
                if (n > len) {
                    this.writer.write(cbuf, off + len, n - len);
                }
            }
        }
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) {
        if (this.xmlPrologWriter != null) {
            this.detectEncoding(array, n, n2);
        }
        else {
            this.writer.write(array, n, n2);
        }
    }
    
    static {
        ENCODING_PATTERN = XmlStreamReader.ENCODING_PATTERN;
    }
}
