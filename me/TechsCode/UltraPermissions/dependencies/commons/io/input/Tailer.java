

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import me.TechsCode.EnderPermissions.dependencies.commons.io.FileUtils;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.File;
import java.nio.charset.Charset;

public class Tailer implements Runnable
{
    private static final int DEFAULT_DELAY_MILLIS = 1000;
    private static final String RAF_MODE = "r";
    private static final int DEFAULT_BUFSIZE = 4096;
    private static final Charset DEFAULT_CHARSET;
    private final byte[] inbuf;
    private final File file;
    private final Charset cset;
    private final long delayMillis;
    private final boolean end;
    private final TailerListener listener;
    private final boolean reOpen;
    private volatile boolean run;
    
    public Tailer(final File file, final TailerListener tailerListener) {
        this(file, tailerListener, 1000L);
    }
    
    public Tailer(final File file, final TailerListener tailerListener, final long n) {
        this(file, tailerListener, n, false);
    }
    
    public Tailer(final File file, final TailerListener tailerListener, final long n, final boolean b) {
        this(file, tailerListener, n, b, 4096);
    }
    
    public Tailer(final File file, final TailerListener tailerListener, final long n, final boolean b, final boolean b2) {
        this(file, tailerListener, n, b, b2, 4096);
    }
    
    public Tailer(final File file, final TailerListener tailerListener, final long n, final boolean b, final int n2) {
        this(file, tailerListener, n, b, false, n2);
    }
    
    public Tailer(final File file, final TailerListener tailerListener, final long n, final boolean b, final boolean b2, final int n2) {
        this(file, Tailer.DEFAULT_CHARSET, tailerListener, n, b, b2, n2);
    }
    
    public Tailer(final File file, final Charset cset, final TailerListener listener, final long delayMillis, final boolean end, final boolean reOpen, final int n) {
        this.run = true;
        this.file = file;
        this.delayMillis = delayMillis;
        this.end = end;
        this.inbuf = new byte[n];
        (this.listener = listener).init(this);
        this.reOpen = reOpen;
        this.cset = cset;
    }
    
    public static Tailer create(final File file, final TailerListener tailerListener, final long n, final boolean b, final int n2) {
        return create(file, tailerListener, n, b, false, n2);
    }
    
    public static Tailer create(final File file, final TailerListener tailerListener, final long n, final boolean b, final boolean b2, final int n2) {
        return create(file, Tailer.DEFAULT_CHARSET, tailerListener, n, b, b2, n2);
    }
    
    public static Tailer create(final File file, final Charset charset, final TailerListener tailerListener, final long n, final boolean b, final boolean b2, final int n2) {
        final Tailer target = new Tailer(file, charset, tailerListener, n, b, b2, n2);
        final Thread thread = new Thread(target);
        thread.setDaemon(true);
        thread.start();
        return target;
    }
    
    public static Tailer create(final File file, final TailerListener tailerListener, final long n, final boolean b) {
        return create(file, tailerListener, n, b, 4096);
    }
    
    public static Tailer create(final File file, final TailerListener tailerListener, final long n, final boolean b, final boolean b2) {
        return create(file, tailerListener, n, b, b2, 4096);
    }
    
    public static Tailer create(final File file, final TailerListener tailerListener, final long n) {
        return create(file, tailerListener, n, false);
    }
    
    public static Tailer create(final File file, final TailerListener tailerListener) {
        return create(file, tailerListener, 1000L, false);
    }
    
    public File getFile() {
        return this.file;
    }
    
    protected boolean getRun() {
        return this.run;
    }
    
    public long getDelay() {
        return this.delayMillis;
    }
    
    @Override
    public void run() {
        RandomAccessFile randomAccessFile = null;
        try {
            long n = 0L;
            long n2 = 0L;
            while (this.getRun() && randomAccessFile == null) {
                try {
                    randomAccessFile = new RandomAccessFile(this.file, "r");
                }
                catch (FileNotFoundException ex5) {
                    this.listener.fileNotFound();
                }
                if (randomAccessFile == null) {
                    Thread.sleep(this.delayMillis);
                }
                else {
                    n2 = (this.end ? this.file.length() : 0L);
                    n = this.file.lastModified();
                    randomAccessFile.seek(n2);
                }
            }
            while (this.getRun()) {
                final boolean fileNewer = FileUtils.isFileNewer(this.file, n);
                final long length = this.file.length();
                if (length < n2) {
                    this.listener.fileRotated();
                    try (final RandomAccessFile randomAccessFile2 = randomAccessFile) {
                        randomAccessFile = new RandomAccessFile(this.file, "r");
                        try {
                            this.readLines(randomAccessFile2);
                        }
                        catch (IOException ex) {
                            this.listener.handle(ex);
                        }
                        n2 = 0L;
                    }
                    catch (FileNotFoundException ex6) {
                        this.listener.fileNotFound();
                        Thread.sleep(this.delayMillis);
                    }
                }
                else {
                    if (length > n2) {
                        n2 = this.readLines(randomAccessFile);
                        n = this.file.lastModified();
                    }
                    else if (fileNewer) {
                        randomAccessFile.seek(0L);
                        n2 = this.readLines(randomAccessFile);
                        n = this.file.lastModified();
                    }
                    if (this.reOpen && randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                    Thread.sleep(this.delayMillis);
                    if (!this.getRun() || !this.reOpen) {
                        continue;
                    }
                    randomAccessFile = new RandomAccessFile(this.file, "r");
                    randomAccessFile.seek(n2);
                }
            }
        }
        catch (InterruptedException ex2) {
            Thread.currentThread().interrupt();
            this.listener.handle(ex2);
        }
        catch (Exception ex3) {
            this.listener.handle(ex3);
        }
        finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            }
            catch (IOException ex4) {
                this.listener.handle(ex4);
            }
            this.stop();
        }
    }
    
    public void stop() {
        this.run = false;
    }
    
    private long readLines(final RandomAccessFile randomAccessFile) {
        try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(64)) {
            long filePointer2;
            long filePointer = filePointer2 = randomAccessFile.getFilePointer();
            int n = 0;
            int read;
            while (this.getRun() && (read = randomAccessFile.read(this.inbuf)) != -1) {
                for (int i = 0; i < read; ++i) {
                    final byte b = this.inbuf[i];
                    switch (b) {
                        case 10: {
                            n = 0;
                            this.listener.handle(new String(byteArrayOutputStream.toByteArray(), this.cset));
                            byteArrayOutputStream.reset();
                            filePointer2 = filePointer + i + 1L;
                            break;
                        }
                        case 13: {
                            if (n != 0) {
                                byteArrayOutputStream.write(13);
                            }
                            n = 1;
                            break;
                        }
                        default: {
                            if (n != 0) {
                                n = 0;
                                this.listener.handle(new String(byteArrayOutputStream.toByteArray(), this.cset));
                                byteArrayOutputStream.reset();
                                filePointer2 = filePointer + i + 1L;
                            }
                            byteArrayOutputStream.write(b);
                            break;
                        }
                    }
                }
                filePointer = randomAccessFile.getFilePointer();
            }
            randomAccessFile.seek(filePointer2);
            if (this.listener instanceof TailerListenerAdapter) {
                ((TailerListenerAdapter)this.listener).endOfFileReached();
            }
            return filePointer2;
        }
    }
    
    static {
        DEFAULT_CHARSET = Charset.defaultCharset();
    }
}
