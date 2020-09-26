

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

import java.io.InputStream;
import java.security.MessageDigest;

public class MessageDigestCalculatingInputStream extends ObservableInputStream
{
    private final MessageDigest messageDigest;
    
    public MessageDigestCalculatingInputStream(final InputStream inputStream, final MessageDigest messageDigest) {
        super(inputStream);
        this.messageDigest = messageDigest;
        this.add(new MessageDigestMaintainingObserver(messageDigest));
    }
    
    public MessageDigestCalculatingInputStream(final InputStream inputStream, final String algorithm) {
        this(inputStream, MessageDigest.getInstance(algorithm));
    }
    
    public MessageDigestCalculatingInputStream(final InputStream inputStream) {
        this(inputStream, MessageDigest.getInstance("MD5"));
    }
    
    public MessageDigest getMessageDigest() {
        return this.messageDigest;
    }
    
    public static class MessageDigestMaintainingObserver extends Observer
    {
        private final MessageDigest md;
        
        public MessageDigestMaintainingObserver(final MessageDigest md) {
            this.md = md;
        }
        
        @Override
        void data(final int n) {
            this.md.update((byte)n);
        }
        
        @Override
        void data(final byte[] input, final int offset, final int len) {
            this.md.update(input, offset, len);
        }
    }
}
