

package me.TechsCode.EnderPermissions.dependencies.commons.io;

import java.io.OutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.List;
import java.io.IOException;
import java.io.File;

@Deprecated
public class FileSystemUtils
{
    private static final FileSystemUtils INSTANCE;
    private static final int INIT_PROBLEM = -1;
    private static final int OTHER = 0;
    private static final int WINDOWS = 1;
    private static final int UNIX = 2;
    private static final int POSIX_UNIX = 3;
    private static final int OS;
    private static final String DF;
    
    @Deprecated
    public static long freeSpace(final String s) {
        return FileSystemUtils.INSTANCE.freeSpaceOS(s, FileSystemUtils.OS, false, -1L);
    }
    
    @Deprecated
    public static long freeSpaceKb(final String s) {
        return freeSpaceKb(s, -1L);
    }
    
    @Deprecated
    public static long freeSpaceKb(final String s, final long n) {
        return FileSystemUtils.INSTANCE.freeSpaceOS(s, FileSystemUtils.OS, true, n);
    }
    
    @Deprecated
    public static long freeSpaceKb() {
        return freeSpaceKb(-1L);
    }
    
    @Deprecated
    public static long freeSpaceKb(final long n) {
        return freeSpaceKb(new File(".").getAbsolutePath(), n);
    }
    
    long freeSpaceOS(final String s, final int n, final boolean b, final long n2) {
        if (s == null) {
            throw new IllegalArgumentException("Path must not be null");
        }
        switch (n) {
            case 1: {
                return b ? (this.freeSpaceWindows(s, n2) / 1024L) : this.freeSpaceWindows(s, n2);
            }
            case 2: {
                return this.freeSpaceUnix(s, b, false, n2);
            }
            case 3: {
                return this.freeSpaceUnix(s, b, true, n2);
            }
            case 0: {
                throw new IllegalStateException("Unsupported operating system");
            }
            default: {
                throw new IllegalStateException("Exception caught when determining operating system");
            }
        }
    }
    
    long freeSpaceWindows(final String s, final long n) {
        String str = FilenameUtils.normalize(s, false);
        if (str == null) {
            throw new IllegalArgumentException(s);
        }
        if (str.length() > 0 && str.charAt(0) != '\"') {
            str = "\"" + str + "\"";
        }
        final List<String> performCommand = this.performCommand(new String[] { "cmd.exe", "/C", "dir /a /-c " + str }, Integer.MAX_VALUE, n);
        for (int i = performCommand.size() - 1; i >= 0; --i) {
            final String s2 = performCommand.get(i);
            if (s2.length() > 0) {
                return this.parseDir(s2, str);
            }
        }
        throw new IOException("Command line 'dir /-c' did not return any info for path '" + str + "'");
    }
    
    long parseDir(final String s, final String str) {
        int beginIndex = 0;
        int endIndex = 0;
        int i;
        for (i = s.length() - 1; i >= 0; --i) {
            if (Character.isDigit(s.charAt(i))) {
                endIndex = i + 1;
                break;
            }
        }
        while (i >= 0) {
            final char char1 = s.charAt(i);
            if (!Character.isDigit(char1) && char1 != ',' && char1 != '.') {
                beginIndex = i + 1;
                break;
            }
            --i;
        }
        if (i < 0) {
            throw new IOException("Command line 'dir /-c' did not return valid info for path '" + str + "'");
        }
        final StringBuilder sb = new StringBuilder(s.substring(beginIndex, endIndex));
        for (int j = 0; j < sb.length(); ++j) {
            if (sb.charAt(j) == ',' || sb.charAt(j) == '.') {
                sb.deleteCharAt(j--);
            }
        }
        return this.parseBytes(sb.toString(), str);
    }
    
    long freeSpaceUnix(final String s, final boolean b, final boolean b2, final long n) {
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Path must not be empty");
        }
        String s2 = "-";
        if (b) {
            s2 += "k";
        }
        if (b2) {
            s2 += "P";
        }
        final List<String> performCommand = this.performCommand((s2.length() > 1) ? new String[] { FileSystemUtils.DF, s2, s } : new String[] { FileSystemUtils.DF, s }, 3, n);
        if (performCommand.size() < 2) {
            throw new IOException("Command line '" + FileSystemUtils.DF + "' did not return info as expected for path '" + s + "'- response was " + performCommand);
        }
        StringTokenizer stringTokenizer = new StringTokenizer(performCommand.get(1), " ");
        if (stringTokenizer.countTokens() < 4) {
            if (stringTokenizer.countTokens() != 1 || performCommand.size() < 3) {
                throw new IOException("Command line '" + FileSystemUtils.DF + "' did not return data as expected for path '" + s + "'- check path is valid");
            }
            stringTokenizer = new StringTokenizer(performCommand.get(2), " ");
        }
        else {
            stringTokenizer.nextToken();
        }
        stringTokenizer.nextToken();
        stringTokenizer.nextToken();
        return this.parseBytes(stringTokenizer.nextToken(), s);
    }
    
    long parseBytes(final String s, final String s2) {
        try {
            final long long1 = Long.parseLong(s);
            if (long1 < 0L) {
                throw new IOException("Command line '" + FileSystemUtils.DF + "' did not find free space in response for path '" + s2 + "'- check path is valid");
            }
            return long1;
        }
        catch (NumberFormatException cause) {
            throw new IOException("Command line '" + FileSystemUtils.DF + "' did not return numeric data as expected for path '" + s2 + "'- check path is valid", cause);
        }
    }
    
    List<String> performCommand(final String[] a, final int n, final long lng) {
        final ArrayList<String> list = new ArrayList<String>(20);
        Process openProcess = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        InputStream errorStream = null;
        BufferedReader bufferedReader = null;
        try {
            final Thread start = ThreadMonitor.start(lng);
            openProcess = this.openProcess(a);
            inputStream = openProcess.getInputStream();
            outputStream = openProcess.getOutputStream();
            errorStream = openProcess.getErrorStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
            for (String s = bufferedReader.readLine(); s != null && list.size() < n; s = bufferedReader.readLine()) {
                list.add(s.toLowerCase(Locale.ENGLISH).trim());
            }
            openProcess.waitFor();
            ThreadMonitor.stop(start);
            if (openProcess.exitValue() != 0) {
                throw new IOException("Command line returned OS error code '" + openProcess.exitValue() + "' for command " + Arrays.asList(a));
            }
            if (list.isEmpty()) {
                throw new IOException("Command line did not return any info for command " + Arrays.asList(a));
            }
            bufferedReader.close();
            bufferedReader = null;
            inputStream.close();
            inputStream = null;
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            if (errorStream != null) {
                errorStream.close();
                errorStream = null;
            }
            return list;
        }
        catch (InterruptedException cause) {
            throw new IOException("Command line threw an InterruptedException for command " + Arrays.asList(a) + " timeout=" + lng, cause);
        }
        finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(errorStream);
            IOUtils.closeQuietly(bufferedReader);
            if (openProcess != null) {
                openProcess.destroy();
            }
        }
    }
    
    Process openProcess(final String[] cmdarray) {
        return Runtime.getRuntime().exec(cmdarray);
    }
    
    static {
        INSTANCE = new FileSystemUtils();
        String df = "df";
        int os;
        try {
            final String property = System.getProperty("os.name");
            if (property == null) {
                throw new IOException("os.name not found");
            }
            final String lowerCase = property.toLowerCase(Locale.ENGLISH);
            if (lowerCase.contains("windows")) {
                os = 1;
            }
            else if (lowerCase.contains("linux") || lowerCase.contains("mpe/ix") || lowerCase.contains("freebsd") || lowerCase.contains("openbsd") || lowerCase.contains("irix") || lowerCase.contains("digital unix") || lowerCase.contains("unix") || lowerCase.contains("mac os x")) {
                os = 2;
            }
            else if (lowerCase.contains("sun os") || lowerCase.contains("sunos") || lowerCase.contains("solaris")) {
                os = 3;
                df = "/usr/xpg4/bin/df";
            }
            else if (lowerCase.contains("hp-ux") || lowerCase.contains("aix")) {
                os = 3;
            }
            else {
                os = 0;
            }
        }
        catch (Exception ex) {
            os = -1;
        }
        OS = os;
        DF = df;
    }
}
