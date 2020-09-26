

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.IOException;
import java.io.Writer;
import java.io.FilterWriter;

public class ProxyWriter extends FilterWriter
{
    public ProxyWriter(final Writer out) {
        super(out);
    }
    
    @Override
    public Writer append(final char c) {
        try {
            this.beforeWrite(1);
            this.out.append(c);
            this.afterWrite(1);
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
        return this;
    }
    
    @Override
    public Writer append(final CharSequence csq, final int start, final int end) {
        try {
            this.beforeWrite(end - start);
            this.out.append(csq, start, end);
            this.afterWrite(end - start);
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
        return this;
    }
    
    @Override
    public Writer append(final CharSequence csq) {
        try {
            int length = 0;
            if (csq != null) {
                length = csq.length();
            }
            this.beforeWrite(length);
            this.out.append(csq);
            this.afterWrite(length);
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
        return this;
    }
    
    @Override
    public void write(final int c) {
        try {
            this.beforeWrite(1);
            this.out.write(c);
            this.afterWrite(1);
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
    }
    
    @Override
    public void write(final char[] cbuf) {
        try {
            int length = 0;
            if (cbuf != null) {
                length = cbuf.length;
            }
            this.beforeWrite(length);
            this.out.write(cbuf);
            this.afterWrite(length);
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) {
        try {
            this.beforeWrite(n2);
            this.out.write(array, n, n2);
            this.afterWrite(n2);
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
    }
    
    @Override
    public void write(final String str) {
        try {
            int length = 0;
            if (str != null) {
                length = str.length();
            }
            this.beforeWrite(length);
            this.out.write(str);
            this.afterWrite(length);
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
    }
    
    @Override
    public void write(final String str, final int off, final int len) {
        try {
            this.beforeWrite(len);
            this.out.write(str, off, len);
            this.afterWrite(len);
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
    }
    
    @Override
    public void flush() {
        try {
            this.out.flush();
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
    }
    
    @Override
    public void close() {
        try {
            this.out.close();
        }
        catch (IOException ex) {
            this.handleIOException(ex);
        }
    }
    
    protected void beforeWrite(final int n) {
    }
    
    protected void afterWrite(final int n) {
    }
    
    protected void handleIOException(final IOException ex) {
        throw ex;
    }
}
