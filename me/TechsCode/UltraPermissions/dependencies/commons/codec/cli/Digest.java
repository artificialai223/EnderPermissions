

package me.TechsCode.EnderPermissions.dependencies.commons.codec.cli;

import java.util.Arrays;
import java.nio.charset.Charset;
import java.io.File;
import java.util.Locale;
import java.security.MessageDigest;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.digest.DigestUtils;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.digest.MessageDigestAlgorithms;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.Hex;

public class Digest
{
    private final String algorithm;
    private final String[] args;
    private final String[] inputs;
    
    public static void main(final String[] array) {
        new Digest(array).run();
    }
    
    private Digest(final String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("args");
        }
        if (args.length == 0) {
            throw new IllegalArgumentException(String.format("Usage: java %s [algorithm] [FILE|DIRECTORY|string] ...", Digest.class.getName()));
        }
        this.args = args;
        this.algorithm = args[0];
        if (args.length <= 1) {
            this.inputs = null;
        }
        else {
            System.arraycopy(args, 1, this.inputs = new String[args.length - 1], 0, this.inputs.length);
        }
    }
    
    private void println(final String s, final byte[] array) {
        this.println(s, array, null);
    }
    
    private void println(final String str, final byte[] array, final String str2) {
        System.out.println(str + Hex.encodeHexString(array) + ((str2 != null) ? ("  " + str2) : ""));
    }
    
    private void run() {
        if (this.algorithm.equalsIgnoreCase("ALL") || this.algorithm.equals("*")) {
            this.run(MessageDigestAlgorithms.values());
            return;
        }
        final MessageDigest digest = DigestUtils.getDigest(this.algorithm, null);
        if (digest != null) {
            this.run("", digest);
        }
        else {
            this.run("", DigestUtils.getDigest(this.algorithm.toUpperCase(Locale.ROOT)));
        }
    }
    
    private void run(final String[] array) {
        for (final String str : array) {
            if (DigestUtils.isAvailable(str)) {
                this.run(str + " ", str);
            }
        }
    }
    
    private void run(final String s, final MessageDigest messageDigest) {
        if (this.inputs == null) {
            this.println(s, DigestUtils.digest(messageDigest, System.in));
            return;
        }
        for (final String pathname : this.inputs) {
            final File file = new File(pathname);
            if (file.isFile()) {
                this.println(s, DigestUtils.digest(messageDigest, file), pathname);
            }
            else if (file.isDirectory()) {
                final File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    this.run(s, messageDigest, listFiles);
                }
            }
            else {
                this.println(s, DigestUtils.digest(messageDigest, pathname.getBytes(Charset.defaultCharset())));
            }
        }
    }
    
    private void run(final String s, final MessageDigest messageDigest, final File[] array) {
        for (final File file : array) {
            if (file.isFile()) {
                this.println(s, DigestUtils.digest(messageDigest, file), file.getName());
            }
        }
    }
    
    private void run(final String s, final String s2) {
        this.run(s, DigestUtils.getDigest(s2));
    }
    
    @Override
    public String toString() {
        return String.format("%s %s", super.toString(), Arrays.toString(this.args));
    }
}
