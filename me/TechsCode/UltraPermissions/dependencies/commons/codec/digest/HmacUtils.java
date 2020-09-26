

package me.TechsCode.EnderPermissions.dependencies.commons.codec.digest;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.nio.ByteBuffer;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.Hex;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.StringUtils;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;

public final class HmacUtils
{
    private static final int STREAM_BUFFER_LENGTH = 1024;
    private final Mac mac;
    
    public static boolean isAvailable(final String algorithm) {
        try {
            Mac.getInstance(algorithm);
            return true;
        }
        catch (NoSuchAlgorithmException ex) {
            return false;
        }
    }
    
    public static boolean isAvailable(final HmacAlgorithms hmacAlgorithms) {
        try {
            Mac.getInstance(hmacAlgorithms.getName());
            return true;
        }
        catch (NoSuchAlgorithmException ex) {
            return false;
        }
    }
    
    @Deprecated
    public static Mac getHmacMd5(final byte[] array) {
        return getInitializedMac(HmacAlgorithms.HMAC_MD5, array);
    }
    
    @Deprecated
    public static Mac getHmacSha1(final byte[] array) {
        return getInitializedMac(HmacAlgorithms.HMAC_SHA_1, array);
    }
    
    @Deprecated
    public static Mac getHmacSha256(final byte[] array) {
        return getInitializedMac(HmacAlgorithms.HMAC_SHA_256, array);
    }
    
    @Deprecated
    public static Mac getHmacSha384(final byte[] array) {
        return getInitializedMac(HmacAlgorithms.HMAC_SHA_384, array);
    }
    
    @Deprecated
    public static Mac getHmacSha512(final byte[] array) {
        return getInitializedMac(HmacAlgorithms.HMAC_SHA_512, array);
    }
    
    public static Mac getInitializedMac(final HmacAlgorithms hmacAlgorithms, final byte[] array) {
        return getInitializedMac(hmacAlgorithms.getName(), array);
    }
    
    public static Mac getInitializedMac(final String s, final byte[] key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key");
        }
        try {
            final SecretKeySpec key2 = new SecretKeySpec(key, s);
            final Mac instance = Mac.getInstance(s);
            instance.init(key2);
            return instance;
        }
        catch (NoSuchAlgorithmException cause) {
            throw new IllegalArgumentException(cause);
        }
        catch (InvalidKeyException cause2) {
            throw new IllegalArgumentException(cause2);
        }
    }
    
    @Deprecated
    public static byte[] hmacMd5(final byte[] array, final byte[] array2) {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, array).hmac(array2);
    }
    
    @Deprecated
    public static byte[] hmacMd5(final byte[] array, final InputStream inputStream) {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, array).hmac(inputStream);
    }
    
    @Deprecated
    public static byte[] hmacMd5(final String s, final String s2) {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, s).hmac(s2);
    }
    
    @Deprecated
    public static String hmacMd5Hex(final byte[] array, final byte[] array2) {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, array).hmacHex(array2);
    }
    
    @Deprecated
    public static String hmacMd5Hex(final byte[] array, final InputStream inputStream) {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, array).hmacHex(inputStream);
    }
    
    @Deprecated
    public static String hmacMd5Hex(final String s, final String s2) {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, s).hmacHex(s2);
    }
    
    @Deprecated
    public static byte[] hmacSha1(final byte[] array, final byte[] array2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, array).hmac(array2);
    }
    
    @Deprecated
    public static byte[] hmacSha1(final byte[] array, final InputStream inputStream) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, array).hmac(inputStream);
    }
    
    @Deprecated
    public static byte[] hmacSha1(final String s, final String s2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, s).hmac(s2);
    }
    
    @Deprecated
    public static String hmacSha1Hex(final byte[] array, final byte[] array2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, array).hmacHex(array2);
    }
    
    @Deprecated
    public static String hmacSha1Hex(final byte[] array, final InputStream inputStream) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, array).hmacHex(inputStream);
    }
    
    @Deprecated
    public static String hmacSha1Hex(final String s, final String s2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, s).hmacHex(s2);
    }
    
    @Deprecated
    public static byte[] hmacSha256(final byte[] array, final byte[] array2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, array).hmac(array2);
    }
    
    @Deprecated
    public static byte[] hmacSha256(final byte[] array, final InputStream inputStream) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, array).hmac(inputStream);
    }
    
    @Deprecated
    public static byte[] hmacSha256(final String s, final String s2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, s).hmac(s2);
    }
    
    @Deprecated
    public static String hmacSha256Hex(final byte[] array, final byte[] array2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, array).hmacHex(array2);
    }
    
    @Deprecated
    public static String hmacSha256Hex(final byte[] array, final InputStream inputStream) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, array).hmacHex(inputStream);
    }
    
    @Deprecated
    public static String hmacSha256Hex(final String s, final String s2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, s).hmacHex(s2);
    }
    
    @Deprecated
    public static byte[] hmacSha384(final byte[] array, final byte[] array2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, array).hmac(array2);
    }
    
    @Deprecated
    public static byte[] hmacSha384(final byte[] array, final InputStream inputStream) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, array).hmac(inputStream);
    }
    
    @Deprecated
    public static byte[] hmacSha384(final String s, final String s2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, s).hmac(s2);
    }
    
    @Deprecated
    public static String hmacSha384Hex(final byte[] array, final byte[] array2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, array).hmacHex(array2);
    }
    
    @Deprecated
    public static String hmacSha384Hex(final byte[] array, final InputStream inputStream) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, array).hmacHex(inputStream);
    }
    
    @Deprecated
    public static String hmacSha384Hex(final String s, final String s2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, s).hmacHex(s2);
    }
    
    @Deprecated
    public static byte[] hmacSha512(final byte[] array, final byte[] array2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, array).hmac(array2);
    }
    
    @Deprecated
    public static byte[] hmacSha512(final byte[] array, final InputStream inputStream) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, array).hmac(inputStream);
    }
    
    @Deprecated
    public static byte[] hmacSha512(final String s, final String s2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, s).hmac(s2);
    }
    
    @Deprecated
    public static String hmacSha512Hex(final byte[] array, final byte[] array2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, array).hmacHex(array2);
    }
    
    @Deprecated
    public static String hmacSha512Hex(final byte[] array, final InputStream inputStream) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, array).hmacHex(inputStream);
    }
    
    @Deprecated
    public static String hmacSha512Hex(final String s, final String s2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, s).hmacHex(s2);
    }
    
    public static Mac updateHmac(final Mac mac, final byte[] input) {
        mac.reset();
        mac.update(input);
        return mac;
    }
    
    public static Mac updateHmac(final Mac mac, final InputStream inputStream) {
        mac.reset();
        final byte[] b = new byte[1024];
        for (int i = inputStream.read(b, 0, 1024); i > -1; i = inputStream.read(b, 0, 1024)) {
            mac.update(b, 0, i);
        }
        return mac;
    }
    
    public static Mac updateHmac(final Mac mac, final String s) {
        mac.reset();
        mac.update(StringUtils.getBytesUtf8(s));
        return mac;
    }
    
    @Deprecated
    public HmacUtils() {
        this(null);
    }
    
    private HmacUtils(final Mac mac) {
        this.mac = mac;
    }
    
    public HmacUtils(final String s, final byte[] array) {
        this(getInitializedMac(s, array));
    }
    
    public HmacUtils(final String s, final String s2) {
        this(s, StringUtils.getBytesUtf8(s2));
    }
    
    public HmacUtils(final HmacAlgorithms hmacAlgorithms, final String s) {
        this(hmacAlgorithms.getName(), StringUtils.getBytesUtf8(s));
    }
    
    public HmacUtils(final HmacAlgorithms hmacAlgorithms, final byte[] array) {
        this(hmacAlgorithms.getName(), array);
    }
    
    public byte[] hmac(final byte[] input) {
        return this.mac.doFinal(input);
    }
    
    public String hmacHex(final byte[] array) {
        return Hex.encodeHexString(this.hmac(array));
    }
    
    public byte[] hmac(final String s) {
        return this.mac.doFinal(StringUtils.getBytesUtf8(s));
    }
    
    public String hmacHex(final String s) {
        return Hex.encodeHexString(this.hmac(s));
    }
    
    public byte[] hmac(final ByteBuffer input) {
        this.mac.update(input);
        return this.mac.doFinal();
    }
    
    public String hmacHex(final ByteBuffer byteBuffer) {
        return Hex.encodeHexString(this.hmac(byteBuffer));
    }
    
    public byte[] hmac(final InputStream inputStream) {
        final byte[] array = new byte[1024];
        int read;
        while ((read = inputStream.read(array, 0, 1024)) > -1) {
            this.mac.update(array, 0, read);
        }
        return this.mac.doFinal();
    }
    
    public String hmacHex(final InputStream inputStream) {
        return Hex.encodeHexString(this.hmac(inputStream));
    }
    
    public byte[] hmac(final File file) {
        try (final BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            return this.hmac(bufferedInputStream);
        }
    }
    
    public String hmacHex(final File file) {
        return Hex.encodeHexString(this.hmac(file));
    }
}
