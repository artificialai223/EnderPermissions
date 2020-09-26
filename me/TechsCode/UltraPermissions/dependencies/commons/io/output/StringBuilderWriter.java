

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.Serializable;
import java.io.Writer;

public class StringBuilderWriter extends Writer implements Serializable
{
    private static final long serialVersionUID = -146927496096066153L;
    private final StringBuilder builder;
    
    public StringBuilderWriter() {
        this.builder = new StringBuilder();
    }
    
    public StringBuilderWriter(final int capacity) {
        this.builder = new StringBuilder(capacity);
    }
    
    public StringBuilderWriter(final StringBuilder sb) {
        this.builder = ((sb != null) ? sb : new StringBuilder());
    }
    
    @Override
    public Writer append(final char c) {
        this.builder.append(c);
        return this;
    }
    
    @Override
    public Writer append(final CharSequence s) {
        this.builder.append(s);
        return this;
    }
    
    @Override
    public Writer append(final CharSequence s, final int start, final int end) {
        this.builder.append(s, start, end);
        return this;
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public void write(final String str) {
        if (str != null) {
            this.builder.append(str);
        }
    }
    
    @Override
    public void write(final char[] str, final int offset, final int len) {
        if (str != null) {
            this.builder.append(str, offset, len);
        }
    }
    
    public StringBuilder getBuilder() {
        return this.builder;
    }
    
    @Override
    public String toString() {
        return this.builder.toString();
    }
}
