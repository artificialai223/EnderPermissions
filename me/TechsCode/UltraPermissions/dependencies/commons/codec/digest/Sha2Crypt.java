

package me.TechsCode.EnderPermissions.dependencies.commons.codec.digest;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.Arrays;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.Charsets;
import java.util.Random;
import java.util.regex.Pattern;

public class Sha2Crypt
{
    private static final int ROUNDS_DEFAULT = 5000;
    private static final int ROUNDS_MAX = 999999999;
    private static final int ROUNDS_MIN = 1000;
    private static final String ROUNDS_PREFIX = "rounds=";
    private static final int SHA256_BLOCKSIZE = 32;
    static final String SHA256_PREFIX = "$5$";
    private static final int SHA512_BLOCKSIZE = 64;
    static final String SHA512_PREFIX = "$6$";
    private static final Pattern SALT_PATTERN;
    
    public static String sha256Crypt(final byte[] array) {
        return sha256Crypt(array, null);
    }
    
    public static String sha256Crypt(final byte[] array, String string) {
        if (string == null) {
            string = "$5$" + B64.getRandomSalt(8);
        }
        return sha2Crypt(array, string, "$5$", 32, "SHA-256");
    }
    
    public static String sha256Crypt(final byte[] array, String string, final Random random) {
        if (string == null) {
            string = "$5$" + B64.getRandomSalt(8, random);
        }
        return sha2Crypt(array, string, "$5$", 32, "SHA-256");
    }
    
    private static String sha2Crypt(final byte[] array, final String s, final String str, final int n, final String s2) {
        final int length = array.length;
        int max = 5000;
        boolean b = false;
        if (s == null) {
            throw new IllegalArgumentException("Salt must not be null");
        }
        final Matcher matcher = Sha2Crypt.SALT_PATTERN.matcher(s);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid salt value: " + s);
        }
        if (matcher.group(3) != null) {
            max = Math.max(1000, Math.min(999999999, Integer.parseInt(matcher.group(3))));
            b = true;
        }
        final String group = matcher.group(4);
        final byte[] bytes = group.getBytes(Charsets.UTF_8);
        final int length2 = bytes.length;
        MessageDigest messageDigest = DigestUtils.getDigest(s2);
        messageDigest.update(array);
        messageDigest.update(bytes);
        final MessageDigest digest = DigestUtils.getDigest(s2);
        digest.update(array);
        digest.update(bytes);
        digest.update(array);
        final byte[] digest2 = digest.digest();
        int i;
        for (i = array.length; i > n; i -= n) {
            messageDigest.update(digest2, 0, n);
        }
        messageDigest.update(digest2, 0, i);
        for (int j = array.length; j > 0; j >>= 1) {
            if ((j & 0x1) != 0x0) {
                messageDigest.update(digest2, 0, n);
            }
            else {
                messageDigest.update(array);
            }
        }
        byte[] array2 = messageDigest.digest();
        final MessageDigest digest3 = DigestUtils.getDigest(s2);
        for (int k = 1; k <= length; ++k) {
            digest3.update(array);
        }
        final byte[] digest4 = digest3.digest();
        final byte[] array3 = new byte[length];
        int l;
        for (l = 0; l < length - n; l += n) {
            System.arraycopy(digest4, 0, array3, l, n);
        }
        System.arraycopy(digest4, 0, array3, l, length - l);
        final MessageDigest digest5 = DigestUtils.getDigest(s2);
        for (int n2 = 1; n2 <= 16 + (array2[0] & 0xFF); ++n2) {
            digest5.update(bytes);
        }
        final byte[] digest6 = digest5.digest();
        final byte[] array4 = new byte[length2];
        int n3;
        for (n3 = 0; n3 < length2 - n; n3 += n) {
            System.arraycopy(digest6, 0, array4, n3, n);
        }
        System.arraycopy(digest6, 0, array4, n3, length2 - n3);
        for (int n4 = 0; n4 <= max - 1; ++n4) {
            messageDigest = DigestUtils.getDigest(s2);
            if ((n4 & 0x1) != 0x0) {
                messageDigest.update(array3, 0, length);
            }
            else {
                messageDigest.update(array2, 0, n);
            }
            if (n4 % 3 != 0) {
                messageDigest.update(array4, 0, length2);
            }
            if (n4 % 7 != 0) {
                messageDigest.update(array3, 0, length);
            }
            if ((n4 & 0x1) != 0x0) {
                messageDigest.update(array2, 0, n);
            }
            else {
                messageDigest.update(array3, 0, length);
            }
            array2 = messageDigest.digest();
        }
        final StringBuilder sb = new StringBuilder(str);
        if (b) {
            sb.append("rounds=");
            sb.append(max);
            sb.append("$");
        }
        sb.append(group);
        sb.append("$");
        if (n == 32) {
            B64.b64from24bit(array2[0], array2[10], array2[20], 4, sb);
            B64.b64from24bit(array2[21], array2[1], array2[11], 4, sb);
            B64.b64from24bit(array2[12], array2[22], array2[2], 4, sb);
            B64.b64from24bit(array2[3], array2[13], array2[23], 4, sb);
            B64.b64from24bit(array2[24], array2[4], array2[14], 4, sb);
            B64.b64from24bit(array2[15], array2[25], array2[5], 4, sb);
            B64.b64from24bit(array2[6], array2[16], array2[26], 4, sb);
            B64.b64from24bit(array2[27], array2[7], array2[17], 4, sb);
            B64.b64from24bit(array2[18], array2[28], array2[8], 4, sb);
            B64.b64from24bit(array2[9], array2[19], array2[29], 4, sb);
            B64.b64from24bit((byte)0, array2[31], array2[30], 3, sb);
        }
        else {
            B64.b64from24bit(array2[0], array2[21], array2[42], 4, sb);
            B64.b64from24bit(array2[22], array2[43], array2[1], 4, sb);
            B64.b64from24bit(array2[44], array2[2], array2[23], 4, sb);
            B64.b64from24bit(array2[3], array2[24], array2[45], 4, sb);
            B64.b64from24bit(array2[25], array2[46], array2[4], 4, sb);
            B64.b64from24bit(array2[47], array2[5], array2[26], 4, sb);
            B64.b64from24bit(array2[6], array2[27], array2[48], 4, sb);
            B64.b64from24bit(array2[28], array2[49], array2[7], 4, sb);
            B64.b64from24bit(array2[50], array2[8], array2[29], 4, sb);
            B64.b64from24bit(array2[9], array2[30], array2[51], 4, sb);
            B64.b64from24bit(array2[31], array2[52], array2[10], 4, sb);
            B64.b64from24bit(array2[53], array2[11], array2[32], 4, sb);
            B64.b64from24bit(array2[12], array2[33], array2[54], 4, sb);
            B64.b64from24bit(array2[34], array2[55], array2[13], 4, sb);
            B64.b64from24bit(array2[56], array2[14], array2[35], 4, sb);
            B64.b64from24bit(array2[15], array2[36], array2[57], 4, sb);
            B64.b64from24bit(array2[37], array2[58], array2[16], 4, sb);
            B64.b64from24bit(array2[59], array2[17], array2[38], 4, sb);
            B64.b64from24bit(array2[18], array2[39], array2[60], 4, sb);
            B64.b64from24bit(array2[40], array2[61], array2[19], 4, sb);
            B64.b64from24bit(array2[62], array2[20], array2[41], 4, sb);
            B64.b64from24bit((byte)0, (byte)0, array2[63], 2, sb);
        }
        Arrays.fill(digest6, (byte)0);
        Arrays.fill(array3, (byte)0);
        Arrays.fill(array4, (byte)0);
        messageDigest.reset();
        digest5.reset();
        Arrays.fill(array, (byte)0);
        Arrays.fill(bytes, (byte)0);
        return sb.toString();
    }
    
    public static String sha512Crypt(final byte[] array) {
        return sha512Crypt(array, null);
    }
    
    public static String sha512Crypt(final byte[] array, String string) {
        if (string == null) {
            string = "$6$" + B64.getRandomSalt(8);
        }
        return sha2Crypt(array, string, "$6$", 64, "SHA-512");
    }
    
    public static String sha512Crypt(final byte[] array, String string, final Random random) {
        if (string == null) {
            string = "$6$" + B64.getRandomSalt(8, random);
        }
        return sha2Crypt(array, string, "$6$", 64, "SHA-512");
    }
    
    static {
        SALT_PATTERN = Pattern.compile("^\\$([56])\\$(rounds=(\\d+)\\$)?([\\.\\/a-zA-Z0-9]{1,16}).*");
    }
}
