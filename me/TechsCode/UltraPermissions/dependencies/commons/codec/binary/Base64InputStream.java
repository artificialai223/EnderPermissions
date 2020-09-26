

package me.TechsCode.EnderPermissions.dependencies.commons.codec.binary;

import java.io.InputStream;

public class Base64InputStream extends BaseNCodecInputStream
{
    public Base64InputStream(final InputStream inputStream) {
        this(inputStream, false);
    }
    
    public Base64InputStream(final InputStream inputStream, final boolean b) {
        super(inputStream, new Base64(false), b);
    }
    
    public Base64InputStream(final InputStream inputStream, final boolean b, final int n, final byte[] array) {
        super(inputStream, new Base64(n, array), b);
    }
}
