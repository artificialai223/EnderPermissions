

package me.TechsCode.EnderPermissions.dependencies.commons.codec.digest;

import java.util.Random;
import java.security.SecureRandom;

class B64
{
    static final String B64T = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    static void b64from24bit(final byte b, final byte b2, final byte b3, final int n, final StringBuilder sb) {
        int n2 = (b << 16 & 0xFFFFFF) | (b2 << 8 & 0xFFFF) | (b3 & 0xFF);
        int n3 = n;
        while (n3-- > 0) {
            sb.append("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(n2 & 0x3F));
            n2 >>= 6;
        }
    }
    
    static String getRandomSalt(final int n) {
        return getRandomSalt(n, new SecureRandom());
    }
    
    static String getRandomSalt(final int capacity, final Random random) {
        final StringBuilder sb = new StringBuilder(capacity);
        for (int i = 1; i <= capacity; ++i) {
            sb.append("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(random.nextInt("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".length())));
        }
        return sb.toString();
    }
}
