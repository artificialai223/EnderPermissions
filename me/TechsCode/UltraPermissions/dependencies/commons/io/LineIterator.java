

package me.TechsCode.EnderPermissions.dependencies.commons.io;

import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.Closeable;
import java.util.Iterator;

public class LineIterator implements Iterator<String>, Closeable
{
    private final BufferedReader bufferedReader;
    private String cachedLine;
    private boolean finished;
    
    public LineIterator(final Reader in) {
        this.finished = false;
        if (in == null) {
            throw new IllegalArgumentException("Reader must not be null");
        }
        if (in instanceof BufferedReader) {
            this.bufferedReader = (BufferedReader)in;
        }
        else {
            this.bufferedReader = new BufferedReader(in);
        }
    }
    
    @Override
    public boolean hasNext() {
        if (this.cachedLine != null) {
            return true;
        }
        if (this.finished) {
            return false;
        }
        Label_0018: {
            break Label_0018;
            try {
                while (true) {
                    final String line = this.bufferedReader.readLine();
                    if (line == null) {
                        this.finished = true;
                        return false;
                    }
                    if (this.isValidLine(line)) {
                        this.cachedLine = line;
                        return true;
                    }
                }
            }
            catch (IOException cause) {
                try {
                    this.close();
                }
                catch (IOException exception) {
                    cause.addSuppressed(exception);
                }
                throw new IllegalStateException(cause);
            }
        }
    }
    
    protected boolean isValidLine(final String s) {
        return true;
    }
    
    @Override
    public String next() {
        return this.nextLine();
    }
    
    public String nextLine() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("No more lines");
        }
        final String cachedLine = this.cachedLine;
        this.cachedLine = null;
        return cachedLine;
    }
    
    @Override
    public void close() {
        this.finished = true;
        this.cachedLine = null;
        if (this.bufferedReader != null) {
            this.bufferedReader.close();
        }
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove unsupported on LineIterator");
    }
    
    @Deprecated
    public static void closeQuietly(final LineIterator lineIterator) {
        try {
            if (lineIterator != null) {
                lineIterator.close();
            }
        }
        catch (IOException ex) {}
    }
}
