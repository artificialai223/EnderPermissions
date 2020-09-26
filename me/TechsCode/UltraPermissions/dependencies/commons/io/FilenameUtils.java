

package me.TechsCode.EnderPermissions.dependencies.commons.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Iterator;
import java.util.Collection;

public class FilenameUtils
{
    private static final int NOT_FOUND = -1;
    public static final char EXTENSION_SEPARATOR = '.';
    public static final String EXTENSION_SEPARATOR_STR;
    private static final char UNIX_SEPARATOR = '/';
    private static final char WINDOWS_SEPARATOR = '\\';
    private static final char SYSTEM_SEPARATOR;
    private static final char OTHER_SEPARATOR;
    
    static boolean isSystemWindows() {
        return FilenameUtils.SYSTEM_SEPARATOR == '\\';
    }
    
    private static boolean isSeparator(final char c) {
        return c == '/' || c == '\\';
    }
    
    public static String normalize(final String s) {
        return doNormalize(s, FilenameUtils.SYSTEM_SEPARATOR, true);
    }
    
    public static String normalize(final String s, final boolean b) {
        return doNormalize(s, b ? '/' : '\\', true);
    }
    
    public static String normalizeNoEndSeparator(final String s) {
        return doNormalize(s, FilenameUtils.SYSTEM_SEPARATOR, false);
    }
    
    public static String normalizeNoEndSeparator(final String s, final boolean b) {
        return doNormalize(s, b ? '/' : '\\', false);
    }
    
    private static String doNormalize(final String s, final char c, final boolean b) {
        if (s == null) {
            return null;
        }
        failIfNullBytePresent(s);
        int length = s.length();
        if (length == 0) {
            return s;
        }
        final int prefixLength = getPrefixLength(s);
        if (prefixLength < 0) {
            return null;
        }
        final char[] array = new char[length + 2];
        s.getChars(0, s.length(), array, 0);
        final char c2 = (c == FilenameUtils.SYSTEM_SEPARATOR) ? FilenameUtils.OTHER_SEPARATOR : FilenameUtils.SYSTEM_SEPARATOR;
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == c2) {
                array[i] = c;
            }
        }
        boolean b2 = true;
        if (array[length - 1] != c) {
            array[length++] = c;
            b2 = false;
        }
        for (int j = prefixLength + 1; j < length; ++j) {
            if (array[j] == c && array[j - 1] == c) {
                System.arraycopy(array, j, array, j - 1, length - j);
                --length;
                --j;
            }
        }
        for (int k = prefixLength + 1; k < length; ++k) {
            if (array[k] == c && array[k - 1] == '.' && (k == prefixLength + 1 || array[k - 2] == c)) {
                if (k == length - 1) {
                    b2 = true;
                }
                System.arraycopy(array, k + 1, array, k - 1, length - k);
                length -= 2;
                --k;
            }
        }
    Label_0468:
        for (int l = prefixLength + 2; l < length; ++l) {
            if (array[l] == c && array[l - 1] == '.' && array[l - 2] == '.' && (l == prefixLength + 2 || array[l - 3] == c)) {
                if (l == prefixLength + 2) {
                    return null;
                }
                if (l == length - 1) {
                    b2 = true;
                }
                for (int n = l - 4; n >= prefixLength; --n) {
                    if (array[n] == c) {
                        System.arraycopy(array, l + 1, array, n + 1, length - l);
                        length -= l - n;
                        l = n + 1;
                        continue Label_0468;
                    }
                }
                System.arraycopy(array, l + 1, array, prefixLength, length - l);
                length -= l + 1 - prefixLength;
                l = prefixLength + 1;
            }
        }
        if (length <= 0) {
            return "";
        }
        if (length <= prefixLength) {
            return new String(array, 0, length);
        }
        if (b2 && b) {
            return new String(array, 0, length);
        }
        return new String(array, 0, length - 1);
    }
    
    public static String concat(final String s, final String s2) {
        final int prefixLength = getPrefixLength(s2);
        if (prefixLength < 0) {
            return null;
        }
        if (prefixLength > 0) {
            return normalize(s2);
        }
        if (s == null) {
            return null;
        }
        final int length = s.length();
        if (length == 0) {
            return normalize(s2);
        }
        if (isSeparator(s.charAt(length - 1))) {
            return normalize(s + s2);
        }
        return normalize(s + '/' + s2);
    }
    
    public static boolean directoryContains(final String s, final String s2) {
        if (s == null) {
            throw new IllegalArgumentException("Directory must not be null");
        }
        return s2 != null && !IOCase.SYSTEM.checkEquals(s, s2) && IOCase.SYSTEM.checkStartsWith(s2, s);
    }
    
    public static String separatorsToUnix(final String s) {
        if (s == null || s.indexOf(92) == -1) {
            return s;
        }
        return s.replace('\\', '/');
    }
    
    public static String separatorsToWindows(final String s) {
        if (s == null || s.indexOf(47) == -1) {
            return s;
        }
        return s.replace('/', '\\');
    }
    
    public static String separatorsToSystem(final String s) {
        if (s == null) {
            return null;
        }
        if (isSystemWindows()) {
            return separatorsToWindows(s);
        }
        return separatorsToUnix(s);
    }
    
    public static int getPrefixLength(final String s) {
        if (s == null) {
            return -1;
        }
        final int length = s.length();
        if (length == 0) {
            return 0;
        }
        final char char1 = s.charAt(0);
        if (char1 == ':') {
            return -1;
        }
        if (length == 1) {
            if (char1 == '~') {
                return 2;
            }
            return isSeparator(char1) ? 1 : 0;
        }
        else if (char1 == '~') {
            final int index = s.indexOf(47, 1);
            final int index2 = s.indexOf(92, 1);
            if (index == -1 && index2 == -1) {
                return length + 1;
            }
            final int a = (index == -1) ? index2 : index;
            return Math.min(a, (index2 == -1) ? a : index2) + 1;
        }
        else {
            final char char2 = s.charAt(1);
            if (char2 == ':') {
                final char upperCase = Character.toUpperCase(char1);
                if (upperCase >= 'A' && upperCase <= 'Z') {
                    if (length == 2 || !isSeparator(s.charAt(2))) {
                        return 2;
                    }
                    return 3;
                }
                else {
                    if (upperCase == '/') {
                        return 1;
                    }
                    return -1;
                }
            }
            else {
                if (!isSeparator(char1) || !isSeparator(char2)) {
                    return isSeparator(char1) ? 1 : 0;
                }
                final int index3 = s.indexOf(47, 2);
                final int index4 = s.indexOf(92, 2);
                if ((index3 == -1 && index4 == -1) || index3 == 2 || index4 == 2) {
                    return -1;
                }
                final int a2 = (index3 == -1) ? index4 : index3;
                return Math.min(a2, (index4 == -1) ? a2 : index4) + 1;
            }
        }
    }
    
    public static int indexOfLastSeparator(final String s) {
        if (s == null) {
            return -1;
        }
        return Math.max(s.lastIndexOf(47), s.lastIndexOf(92));
    }
    
    public static int indexOfExtension(final String s) {
        if (s == null) {
            return -1;
        }
        final int lastIndex = s.lastIndexOf(46);
        return (indexOfLastSeparator(s) > lastIndex) ? -1 : lastIndex;
    }
    
    public static String getPrefix(final String s) {
        if (s == null) {
            return null;
        }
        final int prefixLength = getPrefixLength(s);
        if (prefixLength < 0) {
            return null;
        }
        if (prefixLength > s.length()) {
            failIfNullBytePresent(s + '/');
            return s + '/';
        }
        final String substring = s.substring(0, prefixLength);
        failIfNullBytePresent(substring);
        return substring;
    }
    
    public static String getPath(final String s) {
        return doGetPath(s, 1);
    }
    
    public static String getPathNoEndSeparator(final String s) {
        return doGetPath(s, 0);
    }
    
    private static String doGetPath(final String s, final int n) {
        if (s == null) {
            return null;
        }
        final int prefixLength = getPrefixLength(s);
        if (prefixLength < 0) {
            return null;
        }
        final int indexOfLastSeparator = indexOfLastSeparator(s);
        final int endIndex = indexOfLastSeparator + n;
        if (prefixLength >= s.length() || indexOfLastSeparator < 0 || prefixLength >= endIndex) {
            return "";
        }
        final String substring = s.substring(prefixLength, endIndex);
        failIfNullBytePresent(substring);
        return substring;
    }
    
    public static String getFullPath(final String s) {
        return doGetFullPath(s, true);
    }
    
    public static String getFullPathNoEndSeparator(final String s) {
        return doGetFullPath(s, false);
    }
    
    private static String doGetFullPath(final String s, final boolean b) {
        if (s == null) {
            return null;
        }
        final int prefixLength = getPrefixLength(s);
        if (prefixLength < 0) {
            return null;
        }
        if (prefixLength >= s.length()) {
            if (b) {
                return getPrefix(s);
            }
            return s;
        }
        else {
            final int indexOfLastSeparator = indexOfLastSeparator(s);
            if (indexOfLastSeparator < 0) {
                return s.substring(0, prefixLength);
            }
            int endIndex = indexOfLastSeparator + (b ? 1 : 0);
            if (endIndex == 0) {
                ++endIndex;
            }
            return s.substring(0, endIndex);
        }
    }
    
    public static String getName(final String s) {
        if (s == null) {
            return null;
        }
        failIfNullBytePresent(s);
        return s.substring(indexOfLastSeparator(s) + 1);
    }
    
    private static void failIfNullBytePresent(final String s) {
        for (int length = s.length(), i = 0; i < length; ++i) {
            if (s.charAt(i) == '\0') {
                throw new IllegalArgumentException("Null byte present in file/path name. There are no known legitimate use cases for such data, but several injection attacks may use it");
            }
        }
    }
    
    public static String getBaseName(final String s) {
        return removeExtension(getName(s));
    }
    
    public static String getExtension(final String s) {
        if (s == null) {
            return null;
        }
        final int indexOfExtension = indexOfExtension(s);
        if (indexOfExtension == -1) {
            return "";
        }
        return s.substring(indexOfExtension + 1);
    }
    
    public static String removeExtension(final String s) {
        if (s == null) {
            return null;
        }
        failIfNullBytePresent(s);
        final int indexOfExtension = indexOfExtension(s);
        if (indexOfExtension == -1) {
            return s;
        }
        return s.substring(0, indexOfExtension);
    }
    
    public static boolean equals(final String s, final String s2) {
        return equals(s, s2, false, IOCase.SENSITIVE);
    }
    
    public static boolean equalsOnSystem(final String s, final String s2) {
        return equals(s, s2, false, IOCase.SYSTEM);
    }
    
    public static boolean equalsNormalized(final String s, final String s2) {
        return equals(s, s2, true, IOCase.SENSITIVE);
    }
    
    public static boolean equalsNormalizedOnSystem(final String s, final String s2) {
        return equals(s, s2, true, IOCase.SYSTEM);
    }
    
    public static boolean equals(String normalize, String normalize2, final boolean b, IOCase sensitive) {
        if (normalize == null || normalize2 == null) {
            return normalize == null && normalize2 == null;
        }
        if (b) {
            normalize = normalize(normalize);
            normalize2 = normalize(normalize2);
            if (normalize == null || normalize2 == null) {
                throw new NullPointerException("Error normalizing one or both of the file names");
            }
        }
        if (sensitive == null) {
            sensitive = IOCase.SENSITIVE;
        }
        return sensitive.checkEquals(normalize, normalize2);
    }
    
    public static boolean isExtension(final String s, final String anObject) {
        if (s == null) {
            return false;
        }
        failIfNullBytePresent(s);
        if (anObject == null || anObject.isEmpty()) {
            return indexOfExtension(s) == -1;
        }
        return getExtension(s).equals(anObject);
    }
    
    public static boolean isExtension(final String s, final String[] array) {
        if (s == null) {
            return false;
        }
        failIfNullBytePresent(s);
        if (array == null || array.length == 0) {
            return indexOfExtension(s) == -1;
        }
        final String extension = getExtension(s);
        for (int length = array.length, i = 0; i < length; ++i) {
            if (extension.equals(array[i])) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isExtension(final String s, final Collection<String> collection) {
        if (s == null) {
            return false;
        }
        failIfNullBytePresent(s);
        if (collection == null || collection.isEmpty()) {
            return indexOfExtension(s) == -1;
        }
        final String extension = getExtension(s);
        final Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (extension.equals(iterator.next())) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean wildcardMatch(final String s, final String s2) {
        return wildcardMatch(s, s2, IOCase.SENSITIVE);
    }
    
    public static boolean wildcardMatchOnSystem(final String s, final String s2) {
        return wildcardMatch(s, s2, IOCase.SYSTEM);
    }
    
    public static boolean wildcardMatch(final String s, final String s2, IOCase sensitive) {
        if (s == null && s2 == null) {
            return true;
        }
        if (s == null || s2 == null) {
            return false;
        }
        if (sensitive == null) {
            sensitive = IOCase.SENSITIVE;
        }
        final String[] splitOnTokens = splitOnTokens(s2);
        int n = 0;
        int n2 = 0;
        int i = 0;
        final Stack<int[]> stack = new Stack<int[]>();
        do {
            if (stack.size() > 0) {
                final int[] array = stack.pop();
                i = array[0];
                n2 = array[1];
                n = 1;
            }
            while (i < splitOnTokens.length) {
                if (splitOnTokens[i].equals("?")) {
                    if (++n2 > s.length()) {
                        break;
                    }
                    n = 0;
                }
                else if (splitOnTokens[i].equals("*")) {
                    n = 1;
                    if (i == splitOnTokens.length - 1) {
                        n2 = s.length();
                    }
                }
                else {
                    if (n != 0) {
                        n2 = sensitive.checkIndexOf(s, n2, splitOnTokens[i]);
                        if (n2 == -1) {
                            break;
                        }
                        final int checkIndex = sensitive.checkIndexOf(s, n2 + 1, splitOnTokens[i]);
                        if (checkIndex >= 0) {
                            stack.push(new int[] { i, checkIndex });
                        }
                    }
                    else if (!sensitive.checkRegionMatches(s, n2, splitOnTokens[i])) {
                        break;
                    }
                    n2 += splitOnTokens[i].length();
                    n = 0;
                }
                ++i;
            }
            if (i == splitOnTokens.length && n2 == s.length()) {
                return true;
            }
        } while (stack.size() > 0);
        return false;
    }
    
    static String[] splitOnTokens(final String s) {
        if (s.indexOf(63) == -1 && s.indexOf(42) == -1) {
            return new String[] { s };
        }
        final char[] charArray = s.toCharArray();
        final ArrayList<String> list = new ArrayList<String>();
        final StringBuilder sb = new StringBuilder();
        int n = 0;
        for (final char c : charArray) {
            if (c == '?' || c == '*') {
                if (sb.length() != 0) {
                    list.add(sb.toString());
                    sb.setLength(0);
                }
                if (c == '?') {
                    list.add("?");
                }
                else if (n != 42) {
                    list.add("*");
                }
            }
            else {
                sb.append(c);
            }
            n = c;
        }
        if (sb.length() != 0) {
            list.add(sb.toString());
        }
        return list.toArray(new String[list.size()]);
    }
    
    static {
        EXTENSION_SEPARATOR_STR = Character.toString('.');
        SYSTEM_SEPARATOR = File.separatorChar;
        if (isSystemWindows()) {
            OTHER_SEPARATOR = '/';
        }
        else {
            OTHER_SEPARATOR = '\\';
        }
    }
}
