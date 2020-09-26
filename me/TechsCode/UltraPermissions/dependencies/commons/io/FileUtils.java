

package me.TechsCode.EnderPermissions.dependencies.commons.io;

import java.nio.file.Files;
import me.TechsCode.EnderPermissions.dependencies.commons.io.output.NullOutputStream;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;
import java.util.zip.CRC32;
import java.util.Date;
import java.io.BufferedOutputStream;
import java.net.URLConnection;
import java.util.List;
import java.util.ArrayList;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.ByteBuffer;
import java.net.URL;
import java.io.Reader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.io.InputStream;
import me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter.SuffixFileFilter;
import me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter.TrueFileFilter;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter.FalseFileFilter;
import me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter.DirectoryFileFilter;
import me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter.FileFilterUtils;
import java.util.LinkedList;
import java.io.FileFilter;
import me.TechsCode.EnderPermissions.dependencies.commons.io.filefilter.IOFileFilter;
import java.util.Collection;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.File;
import java.math.BigInteger;

public class FileUtils
{
    public static final long ONE_KB = 1024L;
    public static final BigInteger ONE_KB_BI;
    public static final long ONE_MB = 1048576L;
    public static final BigInteger ONE_MB_BI;
    private static final long FILE_COPY_BUFFER_SIZE = 31457280L;
    public static final long ONE_GB = 1073741824L;
    public static final BigInteger ONE_GB_BI;
    public static final long ONE_TB = 1099511627776L;
    public static final BigInteger ONE_TB_BI;
    public static final long ONE_PB = 1125899906842624L;
    public static final BigInteger ONE_PB_BI;
    public static final long ONE_EB = 1152921504606846976L;
    public static final BigInteger ONE_EB_BI;
    public static final BigInteger ONE_ZB;
    public static final BigInteger ONE_YB;
    public static final File[] EMPTY_FILE_ARRAY;
    
    public static File getFile(final File file, final String... array) {
        if (file == null) {
            throw new NullPointerException("directory must not be null");
        }
        if (array == null) {
            throw new NullPointerException("names must not be null");
        }
        File parent = file;
        for (int length = array.length, i = 0; i < length; ++i) {
            parent = new File(parent, array[i]);
        }
        return parent;
    }
    
    public static File getFile(final String... array) {
        if (array == null) {
            throw new NullPointerException("names must not be null");
        }
        File parent = null;
        for (final String s : array) {
            if (parent == null) {
                parent = new File(s);
            }
            else {
                parent = new File(parent, s);
            }
        }
        return parent;
    }
    
    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }
    
    public static File getTempDirectory() {
        return new File(getTempDirectoryPath());
    }
    
    public static String getUserDirectoryPath() {
        return System.getProperty("user.home");
    }
    
    public static File getUserDirectory() {
        return new File(getUserDirectoryPath());
    }
    
    public static FileInputStream openInputStream(final File file) {
        if (!file.exists()) {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        if (file.isDirectory()) {
            throw new IOException("File '" + file + "' exists but is a directory");
        }
        if (!file.canRead()) {
            throw new IOException("File '" + file + "' cannot be read");
        }
        return new FileInputStream(file);
    }
    
    public static FileOutputStream openOutputStream(final File file) {
        return openOutputStream(file, false);
    }
    
    public static FileOutputStream openOutputStream(final File file, final boolean append) {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        }
        else {
            final File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.mkdirs() && !parentFile.isDirectory()) {
                throw new IOException("Directory '" + parentFile + "' could not be created");
            }
        }
        return new FileOutputStream(file, append);
    }
    
    public static String byteCountToDisplaySize(final BigInteger obj) {
        String s;
        if (obj.divide(FileUtils.ONE_EB_BI).compareTo(BigInteger.ZERO) > 0) {
            s = String.valueOf(obj.divide(FileUtils.ONE_EB_BI)) + " EB";
        }
        else if (obj.divide(FileUtils.ONE_PB_BI).compareTo(BigInteger.ZERO) > 0) {
            s = String.valueOf(obj.divide(FileUtils.ONE_PB_BI)) + " PB";
        }
        else if (obj.divide(FileUtils.ONE_TB_BI).compareTo(BigInteger.ZERO) > 0) {
            s = String.valueOf(obj.divide(FileUtils.ONE_TB_BI)) + " TB";
        }
        else if (obj.divide(FileUtils.ONE_GB_BI).compareTo(BigInteger.ZERO) > 0) {
            s = String.valueOf(obj.divide(FileUtils.ONE_GB_BI)) + " GB";
        }
        else if (obj.divide(FileUtils.ONE_MB_BI).compareTo(BigInteger.ZERO) > 0) {
            s = String.valueOf(obj.divide(FileUtils.ONE_MB_BI)) + " MB";
        }
        else if (obj.divide(FileUtils.ONE_KB_BI).compareTo(BigInteger.ZERO) > 0) {
            s = String.valueOf(obj.divide(FileUtils.ONE_KB_BI)) + " KB";
        }
        else {
            s = String.valueOf(obj) + " bytes";
        }
        return s;
    }
    
    public static String byteCountToDisplaySize(final long val) {
        return byteCountToDisplaySize(BigInteger.valueOf(val));
    }
    
    public static void touch(final File obj) {
        if (!obj.exists()) {
            openOutputStream(obj).close();
        }
        if (!obj.setLastModified(System.currentTimeMillis())) {
            throw new IOException("Unable to set the last modification time for " + obj);
        }
    }
    
    public static File[] convertFileCollectionToFileArray(final Collection<File> collection) {
        return collection.toArray(new File[collection.size()]);
    }
    
    private static void innerListFiles(final Collection<File> collection, final File file, final IOFileFilter filter, final boolean b) {
        final File[] listFiles = file.listFiles((FileFilter)filter);
        if (listFiles != null) {
            for (final File file2 : listFiles) {
                if (file2.isDirectory()) {
                    if (b) {
                        collection.add(file2);
                    }
                    innerListFiles(collection, file2, filter, b);
                }
                else {
                    collection.add(file2);
                }
            }
        }
    }
    
    public static Collection<File> listFiles(final File file, final IOFileFilter upEffectiveFileFilter, final IOFileFilter upEffectiveDirFilter) {
        validateListFilesParameters(file, upEffectiveFileFilter);
        final IOFileFilter setUpEffectiveFileFilter = setUpEffectiveFileFilter(upEffectiveFileFilter);
        final IOFileFilter setUpEffectiveDirFilter = setUpEffectiveDirFilter(upEffectiveDirFilter);
        final LinkedList<File> list = new LinkedList<File>();
        innerListFiles(list, file, FileFilterUtils.or(setUpEffectiveFileFilter, setUpEffectiveDirFilter), false);
        return list;
    }
    
    private static void validateListFilesParameters(final File obj, final IOFileFilter ioFileFilter) {
        if (!obj.isDirectory()) {
            throw new IllegalArgumentException("Parameter 'directory' is not a directory: " + obj);
        }
        if (ioFileFilter == null) {
            throw new NullPointerException("Parameter 'fileFilter' is null");
        }
    }
    
    private static IOFileFilter setUpEffectiveFileFilter(final IOFileFilter ioFileFilter) {
        return FileFilterUtils.and(ioFileFilter, FileFilterUtils.notFileFilter(DirectoryFileFilter.INSTANCE));
    }
    
    private static IOFileFilter setUpEffectiveDirFilter(final IOFileFilter ioFileFilter) {
        return (ioFileFilter == null) ? FalseFileFilter.INSTANCE : FileFilterUtils.and(ioFileFilter, DirectoryFileFilter.INSTANCE);
    }
    
    public static Collection<File> listFilesAndDirs(final File file, final IOFileFilter upEffectiveFileFilter, final IOFileFilter upEffectiveDirFilter) {
        validateListFilesParameters(file, upEffectiveFileFilter);
        final IOFileFilter setUpEffectiveFileFilter = setUpEffectiveFileFilter(upEffectiveFileFilter);
        final IOFileFilter setUpEffectiveDirFilter = setUpEffectiveDirFilter(upEffectiveDirFilter);
        final LinkedList<File> list = new LinkedList<File>();
        if (file.isDirectory()) {
            list.add(file);
        }
        innerListFiles(list, file, FileFilterUtils.or(setUpEffectiveFileFilter, setUpEffectiveDirFilter), true);
        return list;
    }
    
    public static Iterator<File> iterateFiles(final File file, final IOFileFilter ioFileFilter, final IOFileFilter ioFileFilter2) {
        return listFiles(file, ioFileFilter, ioFileFilter2).iterator();
    }
    
    public static Iterator<File> iterateFilesAndDirs(final File file, final IOFileFilter ioFileFilter, final IOFileFilter ioFileFilter2) {
        return listFilesAndDirs(file, ioFileFilter, ioFileFilter2).iterator();
    }
    
    private static String[] toSuffixes(final String[] array) {
        final String[] array2 = new String[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = "." + array[i];
        }
        return array2;
    }
    
    public static Collection<File> listFiles(final File file, final String[] array, final boolean b) {
        IOFileFilter instance;
        if (array == null) {
            instance = TrueFileFilter.INSTANCE;
        }
        else {
            instance = new SuffixFileFilter(toSuffixes(array));
        }
        return listFiles(file, instance, b ? TrueFileFilter.INSTANCE : FalseFileFilter.INSTANCE);
    }
    
    public static Iterator<File> iterateFiles(final File file, final String[] array, final boolean b) {
        return listFiles(file, array, b).iterator();
    }
    
    public static boolean contentEquals(final File file, final File file2) {
        final boolean exists = file.exists();
        if (exists != file2.exists()) {
            return false;
        }
        if (!exists) {
            return true;
        }
        if (file.isDirectory() || file2.isDirectory()) {
            throw new IOException("Can't compare directories, only files");
        }
        if (file.length() != file2.length()) {
            return false;
        }
        if (file.getCanonicalFile().equals(file2.getCanonicalFile())) {
            return true;
        }
        try (final FileInputStream fileInputStream = new FileInputStream(file);
             final FileInputStream fileInputStream2 = new FileInputStream(file2)) {
            return IOUtils.contentEquals(fileInputStream, fileInputStream2);
        }
    }
    
    public static boolean contentEqualsIgnoreEOL(final File file, final File file2, final String s) {
        final boolean exists = file.exists();
        if (exists != file2.exists()) {
            return false;
        }
        if (!exists) {
            return true;
        }
        if (file.isDirectory() || file2.isDirectory()) {
            throw new IOException("Can't compare directories, only files");
        }
        if (file.getCanonicalFile().equals(file2.getCanonicalFile())) {
            return true;
        }
        try (final InputStreamReader inputStreamReader = (s == null) ? new InputStreamReader(new FileInputStream(file), Charset.defaultCharset()) : new InputStreamReader(new FileInputStream(file), s)) {
            if (s == null) {
                final FileInputStream in;
                final InputStreamReader inputStreamReader2 = new InputStreamReader(in, Charset.defaultCharset());
                in = new FileInputStream(file2);
            }
            else {
                final FileInputStream in2;
                final InputStreamReader inputStreamReader2 = new InputStreamReader(in2, s);
                in2 = new FileInputStream(file2);
            }
            InputStreamReader inputStreamReader2;
            try (final InputStreamReader inputStreamReader3 = inputStreamReader2) {
                return IOUtils.contentEqualsIgnoreEOL(inputStreamReader, inputStreamReader3);
            }
        }
    }
    
    public static File toFile(final URL url) {
        if (url == null || !"file".equalsIgnoreCase(url.getProtocol())) {
            return null;
        }
        return new File(decodeUrl(url.getFile().replace('/', File.separatorChar)));
    }
    
    static String decodeUrl(final String s) {
        String string = s;
        if (s != null && s.indexOf(37) >= 0) {
            final int length = s.length();
            final StringBuilder sb = new StringBuilder();
            final ByteBuffer allocate = ByteBuffer.allocate(length);
            int i = 0;
            while (i < length) {
                if (s.charAt(i) == '%') {
                    try {
                        do {
                            allocate.put((byte)Integer.parseInt(s.substring(i + 1, i + 3), 16));
                            i += 3;
                        } while (i < length && s.charAt(i) == '%');
                        continue;
                    }
                    catch (RuntimeException ex) {}
                    finally {
                        if (allocate.position() > 0) {
                            allocate.flip();
                            sb.append(StandardCharsets.UTF_8.decode(allocate).toString());
                            allocate.clear();
                        }
                    }
                }
                sb.append(s.charAt(i++));
            }
            string = sb.toString();
        }
        return string;
    }
    
    public static File[] toFiles(final URL[] array) {
        if (array == null || array.length == 0) {
            return FileUtils.EMPTY_FILE_ARRAY;
        }
        final File[] array2 = new File[array.length];
        for (int i = 0; i < array.length; ++i) {
            final URL obj = array[i];
            if (obj != null) {
                if (!obj.getProtocol().equals("file")) {
                    throw new IllegalArgumentException("URL could not be converted to a File: " + obj);
                }
                array2[i] = toFile(obj);
            }
        }
        return array2;
    }
    
    public static URL[] toURLs(final File[] array) {
        final URL[] array2 = new URL[array.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = array[i].toURI().toURL();
        }
        return array2;
    }
    
    public static void copyFileToDirectory(final File file, final File file2) {
        copyFileToDirectory(file, file2, true);
    }
    
    public static void copyFileToDirectory(final File file, final File file2, final boolean b) {
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (file2.exists() && !file2.isDirectory()) {
            throw new IllegalArgumentException("Destination '" + file2 + "' is not a directory");
        }
        copyFile(file, new File(file2, file.getName()), b);
    }
    
    public static void copyFile(final File file, final File file2) {
        copyFile(file, file2, true);
    }
    
    public static void copyFile(final File file, final File file2, final boolean b) {
        checkFileRequirements(file, file2);
        if (file.isDirectory()) {
            throw new IOException("Source '" + file + "' exists but is a directory");
        }
        if (file.getCanonicalPath().equals(file2.getCanonicalPath())) {
            throw new IOException("Source '" + file + "' and destination '" + file2 + "' are the same");
        }
        final File parentFile = file2.getParentFile();
        if (parentFile != null && !parentFile.mkdirs() && !parentFile.isDirectory()) {
            throw new IOException("Destination '" + parentFile + "' directory cannot be created");
        }
        if (file2.exists() && !file2.canWrite()) {
            throw new IOException("Destination '" + file2 + "' exists but is read-only");
        }
        doCopyFile(file, file2, b);
    }
    
    public static long copyFile(final File file, final OutputStream outputStream) {
        try (final FileInputStream fileInputStream = new FileInputStream(file)) {
            return IOUtils.copyLarge(fileInputStream, outputStream);
        }
    }
    
    private static void doCopyFile(final File file, final File obj, final boolean b) {
        if (obj.exists() && obj.isDirectory()) {
            throw new IOException("Destination '" + obj + "' exists but is a directory");
        }
        try (final FileInputStream fileInputStream = new FileInputStream(file);
             final FileChannel channel = fileInputStream.getChannel();
             final FileOutputStream fileOutputStream = new FileOutputStream(obj);
             final FileChannel channel2 = fileOutputStream.getChannel()) {
            long transfer;
            for (long size = channel.size(), n = 0L; n < size; n += transfer) {
                final long n2 = size - n;
                transfer = channel2.transferFrom(channel, n, (n2 > 31457280L) ? 31457280L : n2);
                if (transfer == 0L) {
                    break;
                }
            }
        }
        final long length = file.length();
        final long length2 = obj.length();
        if (length != length2) {
            throw new IOException("Failed to copy full contents from '" + file + "' to '" + obj + "' Expected length: " + length + " Actual: " + length2);
        }
        if (b) {
            obj.setLastModified(file.lastModified());
        }
    }
    
    public static void copyDirectoryToDirectory(final File file, final File parent) {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file.exists() && !file.isDirectory()) {
            throw new IllegalArgumentException("Source '" + parent + "' is not a directory");
        }
        if (parent == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (parent.exists() && !parent.isDirectory()) {
            throw new IllegalArgumentException("Destination '" + parent + "' is not a directory");
        }
        copyDirectory(file, new File(parent, file.getName()), true);
    }
    
    public static void copyDirectory(final File file, final File file2) {
        copyDirectory(file, file2, true);
    }
    
    public static void copyDirectory(final File file, final File file2, final boolean b) {
        copyDirectory(file, file2, null, b);
    }
    
    public static void copyDirectory(final File file, final File file2, final FileFilter fileFilter) {
        copyDirectory(file, file2, fileFilter, true);
    }
    
    public static void copyDirectory(final File file, final File file2, final FileFilter filter, final boolean b) {
        checkFileRequirements(file, file2);
        if (!file.isDirectory()) {
            throw new IOException("Source '" + file + "' exists but is not a directory");
        }
        if (file.getCanonicalPath().equals(file2.getCanonicalPath())) {
            throw new IOException("Source '" + file + "' and destination '" + file2 + "' are the same");
        }
        ArrayList list = null;
        if (file2.getCanonicalPath().startsWith(file.getCanonicalPath())) {
            final File[] array = (filter == null) ? file.listFiles() : file.listFiles(filter);
            if (array != null && array.length > 0) {
                list = new ArrayList<String>(array.length);
                final File[] array2 = array;
                for (int length = array2.length, i = 0; i < length; ++i) {
                    list.add(new File(file2, array2[i].getName()).getCanonicalPath());
                }
            }
        }
        doCopyDirectory(file, file2, filter, b, (List<String>)list);
    }
    
    private static void checkFileRequirements(final File obj, final File file) {
        if (obj == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!obj.exists()) {
            throw new FileNotFoundException("Source '" + obj + "' does not exist");
        }
    }
    
    private static void doCopyDirectory(final File obj, final File file, final FileFilter filter, final boolean b, final List<String> list) {
        final File[] array = (filter == null) ? obj.listFiles() : obj.listFiles(filter);
        if (array == null) {
            throw new IOException("Failed to list contents of " + obj);
        }
        if (file.exists()) {
            if (!file.isDirectory()) {
                throw new IOException("Destination '" + file + "' exists but is not a directory");
            }
        }
        else if (!file.mkdirs() && !file.isDirectory()) {
            throw new IOException("Destination '" + file + "' directory cannot be created");
        }
        if (!file.canWrite()) {
            throw new IOException("Destination '" + file + "' cannot be written to");
        }
        for (final File file2 : array) {
            final File file3 = new File(file, file2.getName());
            if (list == null || !list.contains(file2.getCanonicalPath())) {
                if (file2.isDirectory()) {
                    doCopyDirectory(file2, file3, filter, b, list);
                }
                else {
                    doCopyFile(file2, file3, b);
                }
            }
        }
        if (b) {
            file.setLastModified(obj.lastModified());
        }
    }
    
    public static void copyURLToFile(final URL url, final File file) {
        copyInputStreamToFile(url.openStream(), file);
    }
    
    public static void copyURLToFile(final URL url, final File file, final int connectTimeout, final int readTimeout) {
        final URLConnection openConnection = url.openConnection();
        openConnection.setConnectTimeout(connectTimeout);
        openConnection.setReadTimeout(readTimeout);
        copyInputStreamToFile(openConnection.getInputStream(), file);
    }
    
    public static void copyInputStreamToFile(final InputStream inputStream, final File file) {
        Throwable t = null;
        try {
            copyToFile(inputStream, file);
        }
        catch (Throwable t2) {
            t = t2;
            throw t2;
        }
        finally {
            if (inputStream != null) {
                if (t != null) {
                    try {
                        inputStream.close();
                    }
                    catch (Throwable exception) {
                        t.addSuppressed(exception);
                    }
                }
                else {
                    inputStream.close();
                }
            }
        }
    }
    
    public static void copyToFile(final InputStream inputStream, final File file) {
        Throwable t = null;
        try (final FileOutputStream openOutputStream = openOutputStream(file)) {
            IOUtils.copy(inputStream, openOutputStream);
        }
        catch (Throwable t4) {
            t = t4;
            throw t4;
        }
        finally {
            if (inputStream != null) {
                if (t != null) {
                    try {
                        inputStream.close();
                    }
                    catch (Throwable exception2) {
                        t.addSuppressed(exception2);
                    }
                }
                else {
                    inputStream.close();
                }
            }
        }
    }
    
    public static void copyToDirectory(final File obj, final File file) {
        if (obj == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (obj.isFile()) {
            copyFileToDirectory(obj, file);
        }
        else {
            if (!obj.isDirectory()) {
                throw new IOException("The source " + obj + " does not exist");
            }
            copyDirectoryToDirectory(obj, file);
        }
    }
    
    public static void copyToDirectory(final Iterable<File> iterable, final File file) {
        if (iterable == null) {
            throw new NullPointerException("Sources must not be null");
        }
        final Iterator<File> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            copyFileToDirectory(iterator.next(), file);
        }
    }
    
    public static void deleteDirectory(final File obj) {
        if (!obj.exists()) {
            return;
        }
        if (!isSymlink(obj)) {
            cleanDirectory(obj);
        }
        if (!obj.delete()) {
            throw new IOException("Unable to delete directory " + obj + ".");
        }
    }
    
    public static boolean deleteQuietly(final File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        }
        catch (Exception ex) {}
        try {
            return file.delete();
        }
        catch (Exception ex2) {
            return false;
        }
    }
    
    public static boolean directoryContains(final File obj, final File file) {
        if (obj == null) {
            throw new IllegalArgumentException("Directory must not be null");
        }
        if (!obj.isDirectory()) {
            throw new IllegalArgumentException("Not a directory: " + obj);
        }
        return file != null && obj.exists() && file.exists() && FilenameUtils.directoryContains(obj.getCanonicalPath(), file.getCanonicalPath());
    }
    
    public static void cleanDirectory(final File file) {
        final File[] verifiedListFiles = verifiedListFiles(file);
        IOException ex = null;
        for (final File file2 : verifiedListFiles) {
            try {
                forceDelete(file2);
            }
            catch (IOException ex2) {
                ex = ex2;
            }
        }
        if (null != ex) {
            throw ex;
        }
    }
    
    private static File[] verifiedListFiles(final File obj) {
        if (!obj.exists()) {
            throw new IllegalArgumentException(obj + " does not exist");
        }
        if (!obj.isDirectory()) {
            throw new IllegalArgumentException(obj + " is not a directory");
        }
        final File[] listFiles = obj.listFiles();
        if (listFiles == null) {
            throw new IOException("Failed to list contents of " + obj);
        }
        return listFiles;
    }
    
    public static boolean waitFor(final File file, final int n) {
        final long n2 = System.currentTimeMillis() + n * 1000L;
        boolean b = false;
        try {
            while (!file.exists()) {
                final long b2 = n2 - System.currentTimeMillis();
                if (b2 < 0L) {
                    return false;
                }
                try {
                    Thread.sleep(Math.min(100L, b2));
                }
                catch (InterruptedException ex) {
                    b = true;
                }
                catch (Exception ex2) {
                    break;
                }
            }
        }
        finally {
            if (b) {
                Thread.currentThread().interrupt();
            }
        }
        return true;
    }
    
    public static String readFileToString(final File file, final Charset charset) {
        try (final FileInputStream openInputStream = openInputStream(file)) {
            return IOUtils.toString(openInputStream, Charsets.toCharset(charset));
        }
    }
    
    public static String readFileToString(final File file, final String s) {
        return readFileToString(file, Charsets.toCharset(s));
    }
    
    @Deprecated
    public static String readFileToString(final File file) {
        return readFileToString(file, Charset.defaultCharset());
    }
    
    public static byte[] readFileToByteArray(final File file) {
        try (final FileInputStream openInputStream = openInputStream(file)) {
            final long length = file.length();
            return (length > 0L) ? IOUtils.toByteArray(openInputStream, length) : IOUtils.toByteArray(openInputStream);
        }
    }
    
    public static List<String> readLines(final File file, final Charset charset) {
        try (final FileInputStream openInputStream = openInputStream(file)) {
            return IOUtils.readLines(openInputStream, Charsets.toCharset(charset));
        }
    }
    
    public static List<String> readLines(final File file, final String s) {
        return readLines(file, Charsets.toCharset(s));
    }
    
    @Deprecated
    public static List<String> readLines(final File file) {
        return readLines(file, Charset.defaultCharset());
    }
    
    public static LineIterator lineIterator(final File file, final String s) {
        InputStream openInputStream = null;
        try {
            openInputStream = openInputStream(file);
            return IOUtils.lineIterator(openInputStream, s);
        }
        catch (IOException | RuntimeException ex3) {
            final RuntimeException ex2;
            final RuntimeException ex = ex2;
            try {
                if (openInputStream != null) {
                    openInputStream.close();
                }
            }
            catch (IOException exception) {
                ex.addSuppressed(exception);
            }
            throw ex;
        }
    }
    
    public static LineIterator lineIterator(final File file) {
        return lineIterator(file, null);
    }
    
    public static void writeStringToFile(final File file, final String s, final Charset charset) {
        writeStringToFile(file, s, charset, false);
    }
    
    public static void writeStringToFile(final File file, final String s, final String s2) {
        writeStringToFile(file, s, s2, false);
    }
    
    public static void writeStringToFile(final File file, final String s, final Charset charset, final boolean b) {
        try (final FileOutputStream openOutputStream = openOutputStream(file, b)) {
            IOUtils.write(s, openOutputStream, charset);
        }
    }
    
    public static void writeStringToFile(final File file, final String s, final String s2, final boolean b) {
        writeStringToFile(file, s, Charsets.toCharset(s2), b);
    }
    
    @Deprecated
    public static void writeStringToFile(final File file, final String s) {
        writeStringToFile(file, s, Charset.defaultCharset(), false);
    }
    
    @Deprecated
    public static void writeStringToFile(final File file, final String s, final boolean b) {
        writeStringToFile(file, s, Charset.defaultCharset(), b);
    }
    
    @Deprecated
    public static void write(final File file, final CharSequence charSequence) {
        write(file, charSequence, Charset.defaultCharset(), false);
    }
    
    @Deprecated
    public static void write(final File file, final CharSequence charSequence, final boolean b) {
        write(file, charSequence, Charset.defaultCharset(), b);
    }
    
    public static void write(final File file, final CharSequence charSequence, final Charset charset) {
        write(file, charSequence, charset, false);
    }
    
    public static void write(final File file, final CharSequence charSequence, final String s) {
        write(file, charSequence, s, false);
    }
    
    public static void write(final File file, final CharSequence charSequence, final Charset charset, final boolean b) {
        writeStringToFile(file, (charSequence == null) ? null : charSequence.toString(), charset, b);
    }
    
    public static void write(final File file, final CharSequence charSequence, final String s, final boolean b) {
        write(file, charSequence, Charsets.toCharset(s), b);
    }
    
    public static void writeByteArrayToFile(final File file, final byte[] array) {
        writeByteArrayToFile(file, array, false);
    }
    
    public static void writeByteArrayToFile(final File file, final byte[] array, final boolean b) {
        writeByteArrayToFile(file, array, 0, array.length, b);
    }
    
    public static void writeByteArrayToFile(final File file, final byte[] array, final int n, final int n2) {
        writeByteArrayToFile(file, array, n, n2, false);
    }
    
    public static void writeByteArrayToFile(final File file, final byte[] b, final int off, final int len, final boolean b2) {
        try (final FileOutputStream openOutputStream = openOutputStream(file, b2)) {
            openOutputStream.write(b, off, len);
        }
    }
    
    public static void writeLines(final File file, final String s, final Collection<?> collection) {
        writeLines(file, s, collection, null, false);
    }
    
    public static void writeLines(final File file, final String s, final Collection<?> collection, final boolean b) {
        writeLines(file, s, collection, null, b);
    }
    
    public static void writeLines(final File file, final Collection<?> collection) {
        writeLines(file, null, collection, null, false);
    }
    
    public static void writeLines(final File file, final Collection<?> collection, final boolean b) {
        writeLines(file, null, collection, null, b);
    }
    
    public static void writeLines(final File file, final String s, final Collection<?> collection, final String s2) {
        writeLines(file, s, collection, s2, false);
    }
    
    public static void writeLines(final File file, final String s, final Collection<?> collection, final String s2, final boolean b) {
        try (final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(openOutputStream(file, b))) {
            IOUtils.writeLines(collection, s2, bufferedOutputStream, s);
        }
    }
    
    public static void writeLines(final File file, final Collection<?> collection, final String s) {
        writeLines(file, null, collection, s, false);
    }
    
    public static void writeLines(final File file, final Collection<?> collection, final String s, final boolean b) {
        writeLines(file, null, collection, s, b);
    }
    
    public static void forceDelete(final File file) {
        if (file.isDirectory()) {
            deleteDirectory(file);
        }
        else {
            final boolean exists = file.exists();
            if (!file.delete()) {
                if (!exists) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                throw new IOException("Unable to delete file: " + file);
            }
        }
    }
    
    public static void forceDeleteOnExit(final File file) {
        if (file.isDirectory()) {
            deleteDirectoryOnExit(file);
        }
        else {
            file.deleteOnExit();
        }
    }
    
    private static void deleteDirectoryOnExit(final File file) {
        if (!file.exists()) {
            return;
        }
        file.deleteOnExit();
        if (!isSymlink(file)) {
            cleanDirectoryOnExit(file);
        }
    }
    
    private static void cleanDirectoryOnExit(final File file) {
        final File[] verifiedListFiles = verifiedListFiles(file);
        IOException ex = null;
        for (final File file2 : verifiedListFiles) {
            try {
                forceDeleteOnExit(file2);
            }
            catch (IOException ex2) {
                ex = ex2;
            }
        }
        if (null != ex) {
            throw ex;
        }
    }
    
    public static void forceMkdir(final File file) {
        if (file.exists()) {
            if (!file.isDirectory()) {
                throw new IOException("File " + file + " exists and is not a directory. Unable to create directory.");
            }
        }
        else if (!file.mkdirs() && !file.isDirectory()) {
            throw new IOException("Unable to create directory " + file);
        }
    }
    
    public static void forceMkdirParent(final File file) {
        final File parentFile = file.getParentFile();
        if (parentFile == null) {
            return;
        }
        forceMkdir(parentFile);
    }
    
    public static long sizeOf(final File obj) {
        if (!obj.exists()) {
            throw new IllegalArgumentException(obj + " does not exist");
        }
        if (obj.isDirectory()) {
            return sizeOfDirectory0(obj);
        }
        return obj.length();
    }
    
    public static BigInteger sizeOfAsBigInteger(final File obj) {
        if (!obj.exists()) {
            throw new IllegalArgumentException(obj + " does not exist");
        }
        if (obj.isDirectory()) {
            return sizeOfDirectoryBig0(obj);
        }
        return BigInteger.valueOf(obj.length());
    }
    
    public static long sizeOfDirectory(final File file) {
        checkDirectory(file);
        return sizeOfDirectory0(file);
    }
    
    private static long sizeOfDirectory0(final File file) {
        final File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return 0L;
        }
        long n = 0L;
        for (final File file2 : listFiles) {
            try {
                if (!isSymlink(file2)) {
                    n += sizeOf0(file2);
                    if (n < 0L) {
                        break;
                    }
                }
            }
            catch (IOException ex) {}
        }
        return n;
    }
    
    private static long sizeOf0(final File file) {
        if (file.isDirectory()) {
            return sizeOfDirectory0(file);
        }
        return file.length();
    }
    
    public static BigInteger sizeOfDirectoryAsBigInteger(final File file) {
        checkDirectory(file);
        return sizeOfDirectoryBig0(file);
    }
    
    private static BigInteger sizeOfDirectoryBig0(final File file) {
        final File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return BigInteger.ZERO;
        }
        BigInteger bigInteger = BigInteger.ZERO;
        for (final File file2 : listFiles) {
            try {
                if (!isSymlink(file2)) {
                    bigInteger = bigInteger.add(sizeOfBig0(file2));
                }
            }
            catch (IOException ex) {}
        }
        return bigInteger;
    }
    
    private static BigInteger sizeOfBig0(final File file) {
        if (file.isDirectory()) {
            return sizeOfDirectoryBig0(file);
        }
        return BigInteger.valueOf(file.length());
    }
    
    private static void checkDirectory(final File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(file + " is not a directory");
        }
    }
    
    public static boolean isFileNewer(final File file, final File obj) {
        if (obj == null) {
            throw new IllegalArgumentException("No specified reference file");
        }
        if (!obj.exists()) {
            throw new IllegalArgumentException("The reference file '" + obj + "' doesn't exist");
        }
        return isFileNewer(file, obj.lastModified());
    }
    
    public static boolean isFileNewer(final File file, final Date date) {
        if (date == null) {
            throw new IllegalArgumentException("No specified date");
        }
        return isFileNewer(file, date.getTime());
    }
    
    public static boolean isFileNewer(final File file, final long n) {
        if (file == null) {
            throw new IllegalArgumentException("No specified file");
        }
        return file.exists() && file.lastModified() > n;
    }
    
    public static boolean isFileOlder(final File file, final File obj) {
        if (obj == null) {
            throw new IllegalArgumentException("No specified reference file");
        }
        if (!obj.exists()) {
            throw new IllegalArgumentException("The reference file '" + obj + "' doesn't exist");
        }
        return isFileOlder(file, obj.lastModified());
    }
    
    public static boolean isFileOlder(final File file, final Date date) {
        if (date == null) {
            throw new IllegalArgumentException("No specified date");
        }
        return isFileOlder(file, date.getTime());
    }
    
    public static boolean isFileOlder(final File file, final long n) {
        if (file == null) {
            throw new IllegalArgumentException("No specified file");
        }
        return file.exists() && file.lastModified() < n;
    }
    
    public static long checksumCRC32(final File file) {
        final CRC32 crc32 = new CRC32();
        checksum(file, crc32);
        return crc32.getValue();
    }
    
    public static Checksum checksum(final File file, final Checksum cksum) {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("Checksums can't be computed on directories");
        }
        try (final CheckedInputStream checkedInputStream = new CheckedInputStream(new FileInputStream(file), cksum)) {
            IOUtils.copy(checkedInputStream, new NullOutputStream());
        }
        return cksum;
    }
    
    public static void moveDirectory(final File file, final File file2) {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file2 == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!file.exists()) {
            throw new FileNotFoundException("Source '" + file + "' does not exist");
        }
        if (!file.isDirectory()) {
            throw new IOException("Source '" + file + "' is not a directory");
        }
        if (file2.exists()) {
            throw new FileExistsException("Destination '" + file2 + "' already exists");
        }
        if (!file.renameTo(file2)) {
            if (file2.getCanonicalPath().startsWith(file.getCanonicalPath() + File.separator)) {
                throw new IOException("Cannot move directory: " + file + " to a subdirectory of itself: " + file2);
            }
            copyDirectory(file, file2);
            deleteDirectory(file);
            if (file.exists()) {
                throw new IOException("Failed to delete original directory '" + file + "' after copy to '" + file2 + "'");
            }
        }
    }
    
    public static void moveDirectoryToDirectory(final File file, final File parent, final boolean b) {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (parent == null) {
            throw new NullPointerException("Destination directory must not be null");
        }
        if (!parent.exists() && b) {
            parent.mkdirs();
        }
        if (!parent.exists()) {
            throw new FileNotFoundException("Destination directory '" + parent + "' does not exist [createDestDir=" + b + "]");
        }
        if (!parent.isDirectory()) {
            throw new IOException("Destination '" + parent + "' is not a directory");
        }
        moveDirectory(file, new File(parent, file.getName()));
    }
    
    public static void moveFile(final File obj, final File file) {
        if (obj == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!obj.exists()) {
            throw new FileNotFoundException("Source '" + obj + "' does not exist");
        }
        if (obj.isDirectory()) {
            throw new IOException("Source '" + obj + "' is a directory");
        }
        if (file.exists()) {
            throw new FileExistsException("Destination '" + file + "' already exists");
        }
        if (file.isDirectory()) {
            throw new IOException("Destination '" + file + "' is a directory");
        }
        if (!obj.renameTo(file)) {
            copyFile(obj, file);
            if (!obj.delete()) {
                deleteQuietly(file);
                throw new IOException("Failed to delete original file '" + obj + "' after copy to '" + file + "'");
            }
        }
    }
    
    public static void moveFileToDirectory(final File file, final File parent, final boolean b) {
        if (file == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (parent == null) {
            throw new NullPointerException("Destination directory must not be null");
        }
        if (!parent.exists() && b) {
            parent.mkdirs();
        }
        if (!parent.exists()) {
            throw new FileNotFoundException("Destination directory '" + parent + "' does not exist [createDestDir=" + b + "]");
        }
        if (!parent.isDirectory()) {
            throw new IOException("Destination '" + parent + "' is not a directory");
        }
        moveFile(file, new File(parent, file.getName()));
    }
    
    public static void moveToDirectory(final File obj, final File file, final boolean b) {
        if (obj == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (file == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!obj.exists()) {
            throw new FileNotFoundException("Source '" + obj + "' does not exist");
        }
        if (obj.isDirectory()) {
            moveDirectoryToDirectory(obj, file, b);
        }
        else {
            moveFileToDirectory(obj, file, b);
        }
    }
    
    public static boolean isSymlink(final File file) {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }
        return Files.isSymbolicLink(file.toPath());
    }
    
    static {
        ONE_KB_BI = BigInteger.valueOf(1024L);
        ONE_MB_BI = FileUtils.ONE_KB_BI.multiply(FileUtils.ONE_KB_BI);
        ONE_GB_BI = FileUtils.ONE_KB_BI.multiply(FileUtils.ONE_MB_BI);
        ONE_TB_BI = FileUtils.ONE_KB_BI.multiply(FileUtils.ONE_GB_BI);
        ONE_PB_BI = FileUtils.ONE_KB_BI.multiply(FileUtils.ONE_TB_BI);
        ONE_EB_BI = FileUtils.ONE_KB_BI.multiply(FileUtils.ONE_PB_BI);
        ONE_ZB = BigInteger.valueOf(1024L).multiply(BigInteger.valueOf(1152921504606846976L));
        ONE_YB = FileUtils.ONE_KB_BI.multiply(FileUtils.ONE_ZB);
        EMPTY_FILE_ARRAY = new File[0];
    }
}
