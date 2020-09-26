

package me.TechsCode.EnderPermissions.dependencies.commons.codec.digest;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.Hex;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.StringUtils;
import java.security.NoSuchAlgorithmException;
import java.io.InputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class DigestUtils
{
    private static final int STREAM_BUFFER_LENGTH = 1024;
    private final MessageDigest messageDigest;
    
    public static byte[] digest(final MessageDigest messageDigest, final byte[] input) {
        return messageDigest.digest(input);
    }
    
    public static byte[] digest(final MessageDigest messageDigest, final ByteBuffer input) {
        messageDigest.update(input);
        return messageDigest.digest();
    }
    
    public static byte[] digest(final MessageDigest messageDigest, final File file) {
        return updateDigest(messageDigest, file).digest();
    }
    
    public static byte[] digest(final MessageDigest messageDigest, final InputStream inputStream) {
        return updateDigest(messageDigest, inputStream).digest();
    }
    
    public static MessageDigest getDigest(final String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException cause) {
            throw new IllegalArgumentException(cause);
        }
    }
    
    public static MessageDigest getDigest(final String algorithm, final MessageDigest messageDigest) {
        try {
            return MessageDigest.getInstance(algorithm);
        }
        catch (Exception ex) {
            return messageDigest;
        }
    }
    
    public static MessageDigest getMd2Digest() {
        return getDigest("MD2");
    }
    
    public static MessageDigest getMd5Digest() {
        return getDigest("MD5");
    }
    
    public static MessageDigest getSha1Digest() {
        return getDigest("SHA-1");
    }
    
    public static MessageDigest getSha256Digest() {
        return getDigest("SHA-256");
    }
    
    public static MessageDigest getSha3_224Digest() {
        return getDigest("SHA3-224");
    }
    
    public static MessageDigest getSha3_256Digest() {
        return getDigest("SHA3-256");
    }
    
    public static MessageDigest getSha3_384Digest() {
        return getDigest("SHA3-384");
    }
    
    public static MessageDigest getSha3_512Digest() {
        return getDigest("SHA3-512");
    }
    
    public static MessageDigest getSha384Digest() {
        return getDigest("SHA-384");
    }
    
    public static MessageDigest getSha512Digest() {
        return getDigest("SHA-512");
    }
    
    @Deprecated
    public static MessageDigest getShaDigest() {
        return getSha1Digest();
    }
    
    public static boolean isAvailable(final String s) {
        return getDigest(s, null) != null;
    }
    
    public static byte[] md2(final byte[] input) {
        return getMd2Digest().digest(input);
    }
    
    public static byte[] md2(final InputStream inputStream) {
        return digest(getMd2Digest(), inputStream);
    }
    
    public static byte[] md2(final String s) {
        return md2(StringUtils.getBytesUtf8(s));
    }
    
    public static String md2Hex(final byte[] array) {
        return Hex.encodeHexString(md2(array));
    }
    
    public static String md2Hex(final InputStream inputStream) {
        return Hex.encodeHexString(md2(inputStream));
    }
    
    public static String md2Hex(final String s) {
        return Hex.encodeHexString(md2(s));
    }
    
    public static byte[] md5(final byte[] input) {
        return getMd5Digest().digest(input);
    }
    
    public static byte[] md5(final InputStream inputStream) {
        return digest(getMd5Digest(), inputStream);
    }
    
    public static byte[] md5(final String s) {
        return md5(StringUtils.getBytesUtf8(s));
    }
    
    public static String md5Hex(final byte[] array) {
        return Hex.encodeHexString(md5(array));
    }
    
    public static String md5Hex(final InputStream inputStream) {
        return Hex.encodeHexString(md5(inputStream));
    }
    
    public static String md5Hex(final String s) {
        return Hex.encodeHexString(md5(s));
    }
    
    @Deprecated
    public static byte[] sha(final byte[] array) {
        return sha1(array);
    }
    
    @Deprecated
    public static byte[] sha(final InputStream inputStream) {
        return sha1(inputStream);
    }
    
    @Deprecated
    public static byte[] sha(final String s) {
        return sha1(s);
    }
    
    public static byte[] sha1(final byte[] input) {
        return getSha1Digest().digest(input);
    }
    
    public static byte[] sha1(final InputStream inputStream) {
        return digest(getSha1Digest(), inputStream);
    }
    
    public static byte[] sha1(final String s) {
        return sha1(StringUtils.getBytesUtf8(s));
    }
    
    public static String sha1Hex(final byte[] array) {
        return Hex.encodeHexString(sha1(array));
    }
    
    public static String sha1Hex(final InputStream inputStream) {
        return Hex.encodeHexString(sha1(inputStream));
    }
    
    public static String sha1Hex(final String s) {
        return Hex.encodeHexString(sha1(s));
    }
    
    public static byte[] sha256(final byte[] input) {
        return getSha256Digest().digest(input);
    }
    
    public static byte[] sha256(final InputStream inputStream) {
        return digest(getSha256Digest(), inputStream);
    }
    
    public static byte[] sha256(final String s) {
        return sha256(StringUtils.getBytesUtf8(s));
    }
    
    public static String sha256Hex(final byte[] array) {
        return Hex.encodeHexString(sha256(array));
    }
    
    public static String sha256Hex(final InputStream inputStream) {
        return Hex.encodeHexString(sha256(inputStream));
    }
    
    public static String sha256Hex(final String s) {
        return Hex.encodeHexString(sha256(s));
    }
    
    public static byte[] sha3_224(final byte[] input) {
        return getSha3_224Digest().digest(input);
    }
    
    public static byte[] sha3_224(final InputStream inputStream) {
        return digest(getSha3_224Digest(), inputStream);
    }
    
    public static byte[] sha3_224(final String s) {
        return sha3_224(StringUtils.getBytesUtf8(s));
    }
    
    public static String sha3_224Hex(final String s) {
        return Hex.encodeHexString(sha3_224(s));
    }
    
    public static byte[] sha3_256(final byte[] input) {
        return getSha3_256Digest().digest(input);
    }
    
    public static byte[] sha3_256(final InputStream inputStream) {
        return digest(getSha3_256Digest(), inputStream);
    }
    
    public static byte[] sha3_256(final String s) {
        return sha3_256(StringUtils.getBytesUtf8(s));
    }
    
    public static String sha3_256Hex(final String s) {
        return Hex.encodeHexString(sha3_256(s));
    }
    
    public static byte[] sha3_384(final byte[] input) {
        return getSha3_384Digest().digest(input);
    }
    
    public static byte[] sha3_384(final InputStream inputStream) {
        return digest(getSha3_384Digest(), inputStream);
    }
    
    public static byte[] sha3_384(final String s) {
        return sha3_384(StringUtils.getBytesUtf8(s));
    }
    
    public static String sha3_384Hex(final String s) {
        return Hex.encodeHexString(sha3_384(s));
    }
    
    public static byte[] sha3_512(final byte[] input) {
        return getSha3_512Digest().digest(input);
    }
    
    public static byte[] sha3_512(final InputStream inputStream) {
        return digest(getSha3_512Digest(), inputStream);
    }
    
    public static byte[] sha3_512(final String s) {
        return sha3_512(StringUtils.getBytesUtf8(s));
    }
    
    public static String sha3_512Hex(final String s) {
        return Hex.encodeHexString(sha3_512(s));
    }
    
    public static byte[] sha384(final byte[] input) {
        return getSha384Digest().digest(input);
    }
    
    public static byte[] sha384(final InputStream inputStream) {
        return digest(getSha384Digest(), inputStream);
    }
    
    public static byte[] sha384(final String s) {
        return sha384(StringUtils.getBytesUtf8(s));
    }
    
    public static String sha384Hex(final byte[] array) {
        return Hex.encodeHexString(sha384(array));
    }
    
    public static String sha384Hex(final InputStream inputStream) {
        return Hex.encodeHexString(sha384(inputStream));
    }
    
    public static String sha384Hex(final String s) {
        return Hex.encodeHexString(sha384(s));
    }
    
    public static byte[] sha512(final byte[] input) {
        return getSha512Digest().digest(input);
    }
    
    public static byte[] sha512(final InputStream inputStream) {
        return digest(getSha512Digest(), inputStream);
    }
    
    public static byte[] sha512(final String s) {
        return sha512(StringUtils.getBytesUtf8(s));
    }
    
    public static String sha512Hex(final byte[] array) {
        return Hex.encodeHexString(sha512(array));
    }
    
    public static String sha3_224Hex(final byte[] array) {
        return Hex.encodeHexString(sha3_224(array));
    }
    
    public static String sha3_256Hex(final byte[] array) {
        return Hex.encodeHexString(sha3_256(array));
    }
    
    public static String sha3_384Hex(final byte[] array) {
        return Hex.encodeHexString(sha3_384(array));
    }
    
    public static String sha3_512Hex(final byte[] array) {
        return Hex.encodeHexString(sha3_512(array));
    }
    
    public static String sha512Hex(final InputStream inputStream) {
        return Hex.encodeHexString(sha512(inputStream));
    }
    
    public static String sha3_224Hex(final InputStream inputStream) {
        return Hex.encodeHexString(sha3_224(inputStream));
    }
    
    public static String sha3_256Hex(final InputStream inputStream) {
        return Hex.encodeHexString(sha3_256(inputStream));
    }
    
    public static String sha3_384Hex(final InputStream inputStream) {
        return Hex.encodeHexString(sha3_384(inputStream));
    }
    
    public static String sha3_512Hex(final InputStream inputStream) {
        return Hex.encodeHexString(sha3_512(inputStream));
    }
    
    public static String sha512Hex(final String s) {
        return Hex.encodeHexString(sha512(s));
    }
    
    @Deprecated
    public static String shaHex(final byte[] array) {
        return sha1Hex(array);
    }
    
    @Deprecated
    public static String shaHex(final InputStream inputStream) {
        return sha1Hex(inputStream);
    }
    
    @Deprecated
    public static String shaHex(final String s) {
        return sha1Hex(s);
    }
    
    public static MessageDigest updateDigest(final MessageDigest messageDigest, final byte[] input) {
        messageDigest.update(input);
        return messageDigest;
    }
    
    public static MessageDigest updateDigest(final MessageDigest messageDigest, final ByteBuffer input) {
        messageDigest.update(input);
        return messageDigest;
    }
    
    public static MessageDigest updateDigest(final MessageDigest messageDigest, final File file) {
        try (final BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            return updateDigest(messageDigest, bufferedInputStream);
        }
    }
    
    public static MessageDigest updateDigest(final MessageDigest messageDigest, final InputStream inputStream) {
        final byte[] b = new byte[1024];
        for (int i = inputStream.read(b, 0, 1024); i > -1; i = inputStream.read(b, 0, 1024)) {
            messageDigest.update(b, 0, i);
        }
        return messageDigest;
    }
    
    public static MessageDigest updateDigest(final MessageDigest messageDigest, final String s) {
        messageDigest.update(StringUtils.getBytesUtf8(s));
        return messageDigest;
    }
    
    @Deprecated
    public DigestUtils() {
        this.messageDigest = null;
    }
    
    public DigestUtils(final MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }
    
    public DigestUtils(final String s) {
        this(getDigest(s));
    }
    
    public byte[] digest(final byte[] array) {
        return updateDigest(this.messageDigest, array).digest();
    }
    
    public byte[] digest(final ByteBuffer byteBuffer) {
        return updateDigest(this.messageDigest, byteBuffer).digest();
    }
    
    public byte[] digest(final File file) {
        return updateDigest(this.messageDigest, file).digest();
    }
    
    public byte[] digest(final InputStream inputStream) {
        return updateDigest(this.messageDigest, inputStream).digest();
    }
    
    public byte[] digest(final String s) {
        return updateDigest(this.messageDigest, s).digest();
    }
    
    public String digestAsHex(final byte[] array) {
        return Hex.encodeHexString(this.digest(array));
    }
    
    public String digestAsHex(final ByteBuffer byteBuffer) {
        return Hex.encodeHexString(this.digest(byteBuffer));
    }
    
    public String digestAsHex(final File file) {
        return Hex.encodeHexString(this.digest(file));
    }
    
    public String digestAsHex(final InputStream inputStream) {
        return Hex.encodeHexString(this.digest(inputStream));
    }
    
    public String digestAsHex(final String s) {
        return Hex.encodeHexString(this.digest(s));
    }
    
    public MessageDigest getMessageDigest() {
        return this.messageDigest;
    }
}
