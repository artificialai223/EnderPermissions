

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.Writer;

public class NullWriter extends Writer
{
    public static final NullWriter NULL_WRITER;
    
    @Override
    public Writer append(final char c) {
        return this;
    }
    
    @Override
    public Writer append(final CharSequence charSequence, final int n, final int n2) {
        return this;
    }
    
    @Override
    public Writer append(final CharSequence charSequence) {
        return this;
    }
    
    @Override
    public void write(final int n) {
    }
    
    @Override
    public void write(final char[] array) {
    }
    
    @Override
    public void write(final char[] array, final int n, final int n2) {
    }
    
    @Override
    public void write(final String s) {
    }
    
    @Override
    public void write(final String s, final int n, final int n2) {
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public void close() {
    }
    
    static {
        NULL_WRITER = new NullWriter();
    }
}
