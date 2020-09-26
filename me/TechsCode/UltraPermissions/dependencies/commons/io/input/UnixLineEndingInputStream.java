

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.io.InputStream;

public class UnixLineEndingInputStream extends InputStream
{
    private boolean slashNSeen;
    private boolean slashRSeen;
    private boolean eofSeen;
    private final InputStream target;
    private final boolean ensureLineFeedAtEndOfFile;
    
    public UnixLineEndingInputStream(final InputStream target, final boolean ensureLineFeedAtEndOfFile) {
        this.slashNSeen = false;
        this.slashRSeen = false;
        this.eofSeen = false;
        this.target = target;
        this.ensureLineFeedAtEndOfFile = ensureLineFeedAtEndOfFile;
    }
    
    private int readWithUpdate() {
        final int read = this.target.read();
        this.eofSeen = (read == -1);
        if (this.eofSeen) {
            return read;
        }
        this.slashNSeen = (read == 10);
        this.slashRSeen = (read == 13);
        return read;
    }
    
    @Override
    public int read() {
        final boolean slashRSeen = this.slashRSeen;
        if (this.eofSeen) {
            return this.eofGame(slashRSeen);
        }
        final int withUpdate = this.readWithUpdate();
        if (this.eofSeen) {
            return this.eofGame(slashRSeen);
        }
        if (this.slashRSeen) {
            return 10;
        }
        if (slashRSeen && this.slashNSeen) {
            return this.read();
        }
        return withUpdate;
    }
    
    private int eofGame(final boolean b) {
        if (b || !this.ensureLineFeedAtEndOfFile) {
            return -1;
        }
        if (!this.slashNSeen) {
            this.slashNSeen = true;
            return 10;
        }
        return -1;
    }
    
    @Override
    public void close() {
        super.close();
        this.target.close();
    }
    
    @Override
    public synchronized void mark(final int n) {
        throw new UnsupportedOperationException("Mark notsupported");
    }
}
