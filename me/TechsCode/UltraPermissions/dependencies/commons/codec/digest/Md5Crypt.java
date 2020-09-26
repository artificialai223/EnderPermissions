

package me.TechsCode.EnderPermissions.dependencies.commons.codec.digest;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.security.SecureRandom;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.Charsets;
import java.util.Random;

public class Md5Crypt
{
    static final String APR1_PREFIX = "$apr1$";
    private static final int BLOCKSIZE = 16;
    static final String MD5_PREFIX = "$1$";
    private static final int ROUNDS = 1000;
    
    public static String apr1Crypt(final byte[] array) {
        return apr1Crypt(array, "$apr1$" + B64.getRandomSalt(8));
    }
    
    public static String apr1Crypt(final byte[] array, final Random random) {
        return apr1Crypt(array, "$apr1$" + B64.getRandomSalt(8, random));
    }
    
    public static String apr1Crypt(final byte[] array, String string) {
        if (string != null && !string.startsWith("$apr1$")) {
            string = "$apr1$" + string;
        }
        return md5Crypt(array, string, "$apr1$");
    }
    
    public static String apr1Crypt(final String s) {
        return apr1Crypt(s.getBytes(Charsets.UTF_8));
    }
    
    public static String apr1Crypt(final String s, final String s2) {
        return apr1Crypt(s.getBytes(Charsets.UTF_8), s2);
    }
    
    public static String md5Crypt(final byte[] array) {
        return md5Crypt(array, "$1$" + B64.getRandomSalt(8));
    }
    
    public static String md5Crypt(final byte[] array, final Random random) {
        return md5Crypt(array, "$1$" + B64.getRandomSalt(8, random));
    }
    
    public static String md5Crypt(final byte[] array, final String s) {
        return md5Crypt(array, s, "$1$");
    }
    
    public static String md5Crypt(final byte[] array, final String s, final String s2) {
        return md5Crypt(array, s, s2, new SecureRandom());
    }
    
    public static String md5Crypt(final byte[] a, final String s, final String str, final Random random) {
        final int length = a.length;
        String str2;
        if (s == null) {
            str2 = B64.getRandomSalt(8, random);
        }
        else {
            final Matcher matcher = Pattern.compile("^" + str.replace("$", "\\$") + "([\\.\\/a-zA-Z0-9]{1,8}).*").matcher(s);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Invalid salt value: " + s);
            }
            str2 = matcher.group(1);
        }
        final byte[] bytes = str2.getBytes(Charsets.UTF_8);
        final MessageDigest md5Digest = DigestUtils.getMd5Digest();
        md5Digest.update(a);
        md5Digest.update(str.getBytes(Charsets.UTF_8));
        md5Digest.update(bytes);
        MessageDigest messageDigest = DigestUtils.getMd5Digest();
        messageDigest.update(a);
        messageDigest.update(bytes);
        messageDigest.update(a);
        final byte[] digest = messageDigest.digest();
        for (int i = length; i > 0; i -= 16) {
            md5Digest.update(digest, 0, (i > 16) ? 16 : i);
        }
        Arrays.fill(digest, (byte)0);
        for (int j = length; j > 0; j >>= 1) {
            if ((j & 0x1) == 0x1) {
                md5Digest.update(digest[0]);
            }
            else {
                md5Digest.update(a[0]);
            }
        }
        final StringBuilder sb = new StringBuilder(str + str2 + "$");
        byte[] a2 = md5Digest.digest();
        for (int k = 0; k < 1000; ++k) {
            messageDigest = DigestUtils.getMd5Digest();
            if ((k & 0x1) != 0x0) {
                messageDigest.update(a);
            }
            else {
                messageDigest.update(a2, 0, 16);
            }
            if (k % 3 != 0) {
                messageDigest.update(bytes);
            }
            if (k % 7 != 0) {
                messageDigest.update(a);
            }
            if ((k & 0x1) != 0x0) {
                messageDigest.update(a2, 0, 16);
            }
            else {
                messageDigest.update(a);
            }
            a2 = messageDigest.digest();
        }
        B64.b64from24bit(a2[0], a2[6], a2[12], 4, sb);
        B64.b64from24bit(a2[1], a2[7], a2[13], 4, sb);
        B64.b64from24bit(a2[2], a2[8], a2[14], 4, sb);
        B64.b64from24bit(a2[3], a2[9], a2[15], 4, sb);
        B64.b64from24bit(a2[4], a2[10], a2[5], 4, sb);
        B64.b64from24bit((byte)0, (byte)0, a2[11], 2, sb);
        md5Digest.reset();
        messageDigest.reset();
        Arrays.fill(a, (byte)0);
        Arrays.fill(bytes, (byte)0);
        Arrays.fill(a2, (byte)0);
        return sb.toString();
    }
}
