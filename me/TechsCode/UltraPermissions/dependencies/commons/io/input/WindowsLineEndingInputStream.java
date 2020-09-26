

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.io.InputStream;

public class WindowsLineEndingInputStream extends InputStream
{
    private boolean slashRSeen;
    private boolean slashNSeen;
    private boolean injectSlashN;
    private boolean eofSeen;
    private final InputStream target;
    private final boolean ensureLineFeedAtEndOfFile;
    
    public WindowsLineEndingInputStream(final InputStream target, final boolean ensureLineFeedAtEndOfFile) {
        this.slashRSeen = false;
        this.slashNSeen = false;
        this.injectSlashN = false;
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
        this.slashRSeen = (read == 13);
        this.slashNSeen = (read == 10);
        return read;
    }
    
    @Override
    public int read() {
        if (this.eofSeen) {
            return this.eofGame();
        }
        if (this.injectSlashN) {
            this.injectSlashN = false;
            return 10;
        }
        final boolean slashRSeen = this.slashRSeen;
        final int withUpdate = this.readWithUpdate();
        if (this.eofSeen) {
            return this.eofGame();
        }
        if (withUpdate == 10 && !slashRSeen) {
            this.injectSlashN = true;
            return 13;
        }
        return withUpdate;
    }
    
    private int eofGame() {
        if (!this.ensureLineFeedAtEndOfFile) {
            return -1;
        }
        if (!this.slashNSeen && !this.slashRSeen) {
            this.slashRSeen = true;
            return 13;
        }
        if (!this.slashNSeen) {
            this.slashRSeen = false;
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
        throw new UnsupportedOperationException("Mark not supported");
    }
}
