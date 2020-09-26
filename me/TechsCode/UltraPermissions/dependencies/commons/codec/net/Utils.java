

package me.TechsCode.EnderPermissions.dependencies.commons.codec.net;

import me.TechsCode.EnderPermissions.dependencies.commons.codec.DecoderException;

class Utils
{
    private static final int RADIX = 16;
    
    static int digit16(final byte i) {
        final int digit = Character.digit((char)i, 16);
        if (digit == -1) {
            throw new DecoderException("Invalid URL encoding: not a valid digit (radix 16): " + i);
        }
        return digit;
    }
    
    static char hexDigit(final int n) {
        return Character.toUpperCase(Character.forDigit(n & 0xF, 16));
    }
}
