

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.util.Iterator;
import java.io.IOException;
import java.util.ArrayList;
import java.io.InputStream;
import java.util.List;

public class ObservableInputStream extends ProxyInputStream
{
    private final List<Observer> observers;
    
    public ObservableInputStream(final InputStream inputStream) {
        super(inputStream);
        this.observers = new ArrayList<Observer>();
    }
    
    public void add(final Observer observer) {
        this.observers.add(observer);
    }
    
    public void remove(final Observer observer) {
        this.observers.remove(observer);
    }
    
    public void removeAllObservers() {
        this.observers.clear();
    }
    
    @Override
    public int read() {
        int read = 0;
        IOException ex = null;
        try {
            read = super.read();
        }
        catch (IOException ex2) {
            ex = ex2;
        }
        if (ex != null) {
            this.noteError(ex);
        }
        else if (read == -1) {
            this.noteFinished();
        }
        else {
            this.noteDataByte(read);
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array) {
        int read = 0;
        IOException ex = null;
        try {
            read = super.read(array);
        }
        catch (IOException ex2) {
            ex = ex2;
        }
        if (ex != null) {
            this.noteError(ex);
        }
        else if (read == -1) {
            this.noteFinished();
        }
        else if (read > 0) {
            this.noteDataBytes(array, 0, read);
        }
        return read;
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) {
        int read = 0;
        IOException ex = null;
        try {
            read = super.read(array, n, n2);
        }
        catch (IOException ex2) {
            ex = ex2;
        }
        if (ex != null) {
            this.noteError(ex);
        }
        else if (read == -1) {
            this.noteFinished();
        }
        else if (read > 0) {
            this.noteDataBytes(array, n, read);
        }
        return read;
    }
    
    protected void noteDataBytes(final byte[] array, final int n, final int n2) {
        final Iterator<Observer> iterator = this.getObservers().iterator();
        while (iterator.hasNext()) {
            iterator.next().data(array, n, n2);
        }
    }
    
    protected void noteFinished() {
        final Iterator<Observer> iterator = this.getObservers().iterator();
        while (iterator.hasNext()) {
            iterator.next().finished();
        }
    }
    
    protected void noteDataByte(final int n) {
        final Iterator<Observer> iterator = this.getObservers().iterator();
        while (iterator.hasNext()) {
            iterator.next().data(n);
        }
    }
    
    protected void noteError(final IOException ex) {
        final Iterator<Observer> iterator = this.getObservers().iterator();
        while (iterator.hasNext()) {
            iterator.next().error(ex);
        }
    }
    
    protected void noteClosed() {
        final Iterator<Observer> iterator = this.getObservers().iterator();
        while (iterator.hasNext()) {
            iterator.next().closed();
        }
    }
    
    protected List<Observer> getObservers() {
        return this.observers;
    }
    
    @Override
    public void close() {
        IOException ex = null;
        try {
            super.close();
        }
        catch (IOException ex2) {
            ex = ex2;
        }
        if (ex == null) {
            this.noteClosed();
        }
        else {
            this.noteError(ex);
        }
    }
    
    public void consume() {
        while (this.read(new byte[8192]) != -1) {}
    }
    
    public abstract static class Observer
    {
        void data(final int n) {
        }
        
        void data(final byte[] array, final int n, final int n2) {
        }
        
        void finished() {
        }
        
        void closed() {
        }
        
        void error(final IOException ex) {
            throw ex;
        }
    }
}
