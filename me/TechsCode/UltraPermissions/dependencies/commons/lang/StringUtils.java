

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import java.util.Locale;
import java.util.Collection;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.text.StrBuilder;
import java.util.ArrayList;

public class StringUtils
{
    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
    private static final int PAD_LIMIT = 8192;
    
    public static boolean isEmpty(final String s) {
        return s == null || s.length() == 0;
    }
    
    public static boolean isNotEmpty(final String s) {
        return !isEmpty(s);
    }
    
    public static boolean isBlank(final String s) {
        final int length;
        if (s == null || (length = s.length()) == 0) {
            return true;
        }
        for (int i = 0; i < length; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNotBlank(final String s) {
        return !isBlank(s);
    }
    
    public static String clean(final String s) {
        return (s == null) ? "" : s.trim();
    }
    
    public static String trim(final String s) {
        return (s == null) ? null : s.trim();
    }
    
    public static String trimToNull(final String s) {
        final String trim = trim(s);
        return isEmpty(trim) ? null : trim;
    }
    
    public static String trimToEmpty(final String s) {
        return (s == null) ? "" : s.trim();
    }
    
    public static String strip(final String s) {
        return strip(s, null);
    }
    
    public static String stripToNull(String strip) {
        if (strip == null) {
            return null;
        }
        strip = strip(strip, null);
        return (strip.length() == 0) ? null : strip;
    }
    
    public static String stripToEmpty(final String s) {
        return (s == null) ? "" : strip(s, null);
    }
    
    public static String strip(String stripStart, final String s) {
        if (isEmpty(stripStart)) {
            return stripStart;
        }
        stripStart = stripStart(stripStart, s);
        return stripEnd(stripStart, s);
    }
    
    public static String stripStart(final String s, final String s2) {
        final int length;
        if (s == null || (length = s.length()) == 0) {
            return s;
        }
        int beginIndex = 0;
        if (s2 == null) {
            while (beginIndex != length && Character.isWhitespace(s.charAt(beginIndex))) {
                ++beginIndex;
            }
        }
        else {
            if (s2.length() == 0) {
                return s;
            }
            while (beginIndex != length && s2.indexOf(s.charAt(beginIndex)) != -1) {
                ++beginIndex;
            }
        }
        return s.substring(beginIndex);
    }
    
    public static String stripEnd(final String s, final String s2) {
        int length;
        if (s == null || (length = s.length()) == 0) {
            return s;
        }
        if (s2 == null) {
            while (length != 0 && Character.isWhitespace(s.charAt(length - 1))) {
                --length;
            }
        }
        else {
            if (s2.length() == 0) {
                return s;
            }
            while (length != 0 && s2.indexOf(s.charAt(length - 1)) != -1) {
                --length;
            }
        }
        return s.substring(0, length);
    }
    
    public static String[] stripAll(final String[] array) {
        return stripAll(array, null);
    }
    
    public static String[] stripAll(final String[] array, final String s) {
        final int length;
        if (array == null || (length = array.length) == 0) {
            return array;
        }
        final String[] array2 = new String[length];
        for (int i = 0; i < length; ++i) {
            array2[i] = strip(array[i], s);
        }
        return array2;
    }
    
    public static boolean equals(final String s, final String anObject) {
        return (s == null) ? (anObject == null) : s.equals(anObject);
    }
    
    public static boolean equalsIgnoreCase(final String s, final String anotherString) {
        return (s == null) ? (anotherString == null) : s.equalsIgnoreCase(anotherString);
    }
    
    public static int indexOf(final String s, final char ch) {
        if (isEmpty(s)) {
            return -1;
        }
        return s.indexOf(ch);
    }
    
    public static int indexOf(final String s, final char ch, final int fromIndex) {
        if (isEmpty(s)) {
            return -1;
        }
        return s.indexOf(ch, fromIndex);
    }
    
    public static int indexOf(final String s, final String str) {
        if (s == null || str == null) {
            return -1;
        }
        return s.indexOf(str);
    }
    
    public static int ordinalIndexOf(final String s, final String s2, final int n) {
        return ordinalIndexOf(s, s2, n, false);
    }
    
    private static int ordinalIndexOf(final String s, final String s2, final int n, final boolean b) {
        if (s == null || s2 == null || n <= 0) {
            return -1;
        }
        if (s2.length() == 0) {
            return b ? s.length() : 0;
        }
        int n2 = 0;
        int n3 = b ? s.length() : -1;
        do {
            if (b) {
                n3 = s.lastIndexOf(s2, n3 - 1);
            }
            else {
                n3 = s.indexOf(s2, n3 + 1);
            }
            if (n3 < 0) {
                return n3;
            }
        } while (++n2 < n);
        return n3;
    }
    
    public static int indexOf(final String s, final String str, final int fromIndex) {
        if (s == null || str == null) {
            return -1;
        }
        if (str.length() == 0 && fromIndex >= s.length()) {
            return s.length();
        }
        return s.indexOf(str, fromIndex);
    }
    
    public static int indexOfIgnoreCase(final String s, final String s2) {
        return indexOfIgnoreCase(s, s2, 0);
    }
    
    public static int indexOfIgnoreCase(final String s, final String other, int n) {
        if (s == null || other == null) {
            return -1;
        }
        if (n < 0) {
            n = 0;
        }
        final int n2 = s.length() - other.length() + 1;
        if (n > n2) {
            return -1;
        }
        if (other.length() == 0) {
            return n;
        }
        for (int i = n; i < n2; ++i) {
            if (s.regionMatches(true, i, other, 0, other.length())) {
                return i;
            }
        }
        return -1;
    }
    
    public static int lastIndexOf(final String s, final char ch) {
        if (isEmpty(s)) {
            return -1;
        }
        return s.lastIndexOf(ch);
    }
    
    public static int lastIndexOf(final String s, final char ch, final int fromIndex) {
        if (isEmpty(s)) {
            return -1;
        }
        return s.lastIndexOf(ch, fromIndex);
    }
    
    public static int lastIndexOf(final String s, final String str) {
        if (s == null || str == null) {
            return -1;
        }
        return s.lastIndexOf(str);
    }
    
    public static int lastOrdinalIndexOf(final String s, final String s2, final int n) {
        return ordinalIndexOf(s, s2, n, true);
    }
    
    public static int lastIndexOf(final String s, final String str, final int fromIndex) {
        if (s == null || str == null) {
            return -1;
        }
        return s.lastIndexOf(str, fromIndex);
    }
    
    public static int lastIndexOfIgnoreCase(final String s, final String s2) {
        if (s == null || s2 == null) {
            return -1;
        }
        return lastIndexOfIgnoreCase(s, s2, s.length());
    }
    
    public static int lastIndexOfIgnoreCase(final String s, final String other, int n) {
        if (s == null || other == null) {
            return -1;
        }
        if (n > s.length() - other.length()) {
            n = s.length() - other.length();
        }
        if (n < 0) {
            return -1;
        }
        if (other.length() == 0) {
            return n;
        }
        for (int i = n; i >= 0; --i) {
            if (s.regionMatches(true, i, other, 0, other.length())) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean contains(final String s, final char ch) {
        return !isEmpty(s) && s.indexOf(ch) >= 0;
    }
    
    public static boolean contains(final String s, final String str) {
        return s != null && str != null && s.indexOf(str) >= 0;
    }
    
    public static boolean containsIgnoreCase(final String s, final String other) {
        if (s == null || other == null) {
            return false;
        }
        final int length = other.length();
        for (int n = s.length() - length, i = 0; i <= n; ++i) {
            if (s.regionMatches(true, i, other, 0, length)) {
                return true;
            }
        }
        return false;
    }
    
    public static int indexOfAny(final String s, final char[] array) {
        if (isEmpty(s) || ArrayUtils.isEmpty(array)) {
            return -1;
        }
        final int length = s.length();
        final int n = length - 1;
        final int length2 = array.length;
        final int n2 = length2 - 1;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            for (int j = 0; j < length2; ++j) {
                if (array[j] == char1) {
                    if (i >= n || j >= n2 || !CharUtils.isHighSurrogate(char1)) {
                        return i;
                    }
                    if (array[j + 1] == s.charAt(i + 1)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    public static int indexOfAny(final String s, final String s2) {
        if (isEmpty(s) || isEmpty(s2)) {
            return -1;
        }
        return indexOfAny(s, s2.toCharArray());
    }
    
    public static boolean containsAny(final String s, final char[] array) {
        if (isEmpty(s) || ArrayUtils.isEmpty(array)) {
            return false;
        }
        final int length = s.length();
        final int length2 = array.length;
        final int n = length - 1;
        final int n2 = length2 - 1;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            for (int j = 0; j < length2; ++j) {
                if (array[j] == char1) {
                    if (!CharUtils.isHighSurrogate(char1)) {
                        return true;
                    }
                    if (j == n2) {
                        return true;
                    }
                    if (i < n && array[j + 1] == s.charAt(i + 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean containsAny(final String s, final String s2) {
        return s2 != null && containsAny(s, s2.toCharArray());
    }
    
    public static int indexOfAnyBut(final String s, final char[] array) {
        if (isEmpty(s) || ArrayUtils.isEmpty(array)) {
            return -1;
        }
        final int length = s.length();
        final int n = length - 1;
        final int length2 = array.length;
        final int n2 = length2 - 1;
        int i = 0;
    Label_0038:
        while (i < length) {
            final char char1 = s.charAt(i);
            for (int j = 0; j < length2; ++j) {
                if (array[j] == char1 && (i >= n || j >= n2 || !CharUtils.isHighSurrogate(char1) || array[j + 1] == s.charAt(i + 1))) {
                    ++i;
                    continue Label_0038;
                }
            }
            return i;
        }
        return -1;
    }
    
    public static int indexOfAnyBut(final String s, final String s2) {
        if (isEmpty(s) || isEmpty(s2)) {
            return -1;
        }
        for (int length = s.length(), i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            final boolean b = s2.indexOf(char1) >= 0;
            if (i + 1 < length && CharUtils.isHighSurrogate(char1)) {
                final char char2 = s.charAt(i + 1);
                if (b && s2.indexOf(char2) < 0) {
                    return i;
                }
            }
            else if (!b) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean containsOnly(final String s, final char[] array) {
        return array != null && s != null && (s.length() == 0 || (array.length != 0 && indexOfAnyBut(s, array) == -1));
    }
    
    public static boolean containsOnly(final String s, final String s2) {
        return s != null && s2 != null && containsOnly(s, s2.toCharArray());
    }
    
    public static boolean containsNone(final String s, final char[] array) {
        if (s == null || array == null) {
            return true;
        }
        final int length = s.length();
        final int n = length - 1;
        final int length2 = array.length;
        final int n2 = length2 - 1;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            for (int j = 0; j < length2; ++j) {
                if (array[j] == char1) {
                    if (!CharUtils.isHighSurrogate(char1)) {
                        return false;
                    }
                    if (j == n2) {
                        return false;
                    }
                    if (i < n && array[j + 1] == s.charAt(i + 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public static boolean containsNone(final String s, final String s2) {
        return s == null || s2 == null || containsNone(s, s2.toCharArray());
    }
    
    public static int indexOfAny(final String s, final String[] array) {
        if (s == null || array == null) {
            return -1;
        }
        final int length = array.length;
        int n = Integer.MAX_VALUE;
        for (final String str : array) {
            if (str != null) {
                final int index = s.indexOf(str);
                if (index != -1) {
                    if (index < n) {
                        n = index;
                    }
                }
            }
        }
        return (n == Integer.MAX_VALUE) ? -1 : n;
    }
    
    public static int lastIndexOfAny(final String s, final String[] array) {
        if (s == null || array == null) {
            return -1;
        }
        final int length = array.length;
        int n = -1;
        for (final String str : array) {
            if (str != null) {
                final int lastIndex = s.lastIndexOf(str);
                if (lastIndex > n) {
                    n = lastIndex;
                }
            }
        }
        return n;
    }
    
    public static String substring(final String s, int beginIndex) {
        if (s == null) {
            return null;
        }
        if (beginIndex < 0) {
            beginIndex += s.length();
        }
        if (beginIndex < 0) {
            beginIndex = 0;
        }
        if (beginIndex > s.length()) {
            return "";
        }
        return s.substring(beginIndex);
    }
    
    public static String substring(final String s, int beginIndex, int length) {
        if (s == null) {
            return null;
        }
        if (length < 0) {
            length += s.length();
        }
        if (beginIndex < 0) {
            beginIndex += s.length();
        }
        if (length > s.length()) {
            length = s.length();
        }
        if (beginIndex > length) {
            return "";
        }
        if (beginIndex < 0) {
            beginIndex = 0;
        }
        if (length < 0) {
            length = 0;
        }
        return s.substring(beginIndex, length);
    }
    
    public static String left(final String s, final int endIndex) {
        if (s == null) {
            return null;
        }
        if (endIndex < 0) {
            return "";
        }
        if (s.length() <= endIndex) {
            return s;
        }
        return s.substring(0, endIndex);
    }
    
    public static String right(final String s, final int n) {
        if (s == null) {
            return null;
        }
        if (n < 0) {
            return "";
        }
        if (s.length() <= n) {
            return s;
        }
        return s.substring(s.length() - n);
    }
    
    public static String mid(final String s, int n, final int n2) {
        if (s == null) {
            return null;
        }
        if (n2 < 0 || n > s.length()) {
            return "";
        }
        if (n < 0) {
            n = 0;
        }
        if (s.length() <= n + n2) {
            return s.substring(n);
        }
        return s.substring(n, n + n2);
    }
    
    public static String substringBefore(final String s, final String str) {
        if (isEmpty(s) || str == null) {
            return s;
        }
        if (str.length() == 0) {
            return "";
        }
        final int index = s.indexOf(str);
        if (index == -1) {
            return s;
        }
        return s.substring(0, index);
    }
    
    public static String substringAfter(final String s, final String str) {
        if (isEmpty(s)) {
            return s;
        }
        if (str == null) {
            return "";
        }
        final int index = s.indexOf(str);
        if (index == -1) {
            return "";
        }
        return s.substring(index + str.length());
    }
    
    public static String substringBeforeLast(final String s, final String str) {
        if (isEmpty(s) || isEmpty(str)) {
            return s;
        }
        final int lastIndex = s.lastIndexOf(str);
        if (lastIndex == -1) {
            return s;
        }
        return s.substring(0, lastIndex);
    }
    
    public static String substringAfterLast(final String s, final String str) {
        if (isEmpty(s)) {
            return s;
        }
        if (isEmpty(str)) {
            return "";
        }
        final int lastIndex = s.lastIndexOf(str);
        if (lastIndex == -1 || lastIndex == s.length() - str.length()) {
            return "";
        }
        return s.substring(lastIndex + str.length());
    }
    
    public static String substringBetween(final String s, final String s2) {
        return substringBetween(s, s2, s2);
    }
    
    public static String substringBetween(final String s, final String str, final String str2) {
        if (s == null || str == null || str2 == null) {
            return null;
        }
        final int index = s.indexOf(str);
        if (index != -1) {
            final int index2 = s.indexOf(str2, index + str.length());
            if (index2 != -1) {
                return s.substring(index + str.length(), index2);
            }
        }
        return null;
    }
    
    public static String[] substringsBetween(final String s, final String str, final String str2) {
        if (s == null || isEmpty(str) || isEmpty(str2)) {
            return null;
        }
        final int length = s.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final int length2 = str2.length();
        final int length3 = str.length();
        final ArrayList list = new ArrayList<String>();
        int index2;
        for (int i = 0; i < length - length2; i = index2 + length2) {
            final int index = s.indexOf(str, i);
            if (index < 0) {
                break;
            }
            final int n = index + length3;
            index2 = s.indexOf(str2, n);
            if (index2 < 0) {
                break;
            }
            list.add(s.substring(n, index2));
        }
        if (list.isEmpty()) {
            return null;
        }
        return (String[])list.toArray(new String[list.size()]);
    }
    
    public static String getNestedString(final String s, final String s2) {
        return substringBetween(s, s2, s2);
    }
    
    public static String getNestedString(final String s, final String s2, final String s3) {
        return substringBetween(s, s2, s3);
    }
    
    public static String[] split(final String s) {
        return split(s, null, -1);
    }
    
    public static String[] split(final String s, final char c) {
        return splitWorker(s, c, false);
    }
    
    public static String[] split(final String s, final String s2) {
        return splitWorker(s, s2, -1, false);
    }
    
    public static String[] split(final String s, final String s2, final int n) {
        return splitWorker(s, s2, n, false);
    }
    
    public static String[] splitByWholeSeparator(final String s, final String s2) {
        return splitByWholeSeparatorWorker(s, s2, -1, false);
    }
    
    public static String[] splitByWholeSeparator(final String s, final String s2, final int n) {
        return splitByWholeSeparatorWorker(s, s2, n, false);
    }
    
    public static String[] splitByWholeSeparatorPreserveAllTokens(final String s, final String s2) {
        return splitByWholeSeparatorWorker(s, s2, -1, true);
    }
    
    public static String[] splitByWholeSeparatorPreserveAllTokens(final String s, final String s2, final int n) {
        return splitByWholeSeparatorWorker(s, s2, n, true);
    }
    
    private static String[] splitByWholeSeparatorWorker(final String s, final String s2, final int n, final boolean b) {
        if (s == null) {
            return null;
        }
        final int length = s.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        if (s2 == null || "".equals(s2)) {
            return splitWorker(s, null, n, b);
        }
        final int length2 = s2.length();
        final ArrayList list = new ArrayList<String>();
        int n2 = 0;
        int beginIndex = 0;
        int i = 0;
        while (i < length) {
            i = s.indexOf(s2, beginIndex);
            if (i > -1) {
                if (i > beginIndex) {
                    if (++n2 == n) {
                        i = length;
                        list.add(s.substring(beginIndex));
                    }
                    else {
                        list.add(s.substring(beginIndex, i));
                        beginIndex = i + length2;
                    }
                }
                else {
                    if (b) {
                        if (++n2 == n) {
                            i = length;
                            list.add(s.substring(beginIndex));
                        }
                        else {
                            list.add("");
                        }
                    }
                    beginIndex = i + length2;
                }
            }
            else {
                list.add(s.substring(beginIndex));
                i = length;
            }
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static String[] splitPreserveAllTokens(final String s) {
        return splitWorker(s, null, -1, true);
    }
    
    public static String[] splitPreserveAllTokens(final String s, final char c) {
        return splitWorker(s, c, true);
    }
    
    private static String[] splitWorker(final String s, final char c, final boolean b) {
        if (s == null) {
            return null;
        }
        final int length = s.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final ArrayList list = new ArrayList<String>();
        int i = 0;
        int n = 0;
        int n2 = 0;
        boolean b2 = false;
        while (i < length) {
            if (s.charAt(i) == c) {
                if (n2 != 0 || b) {
                    list.add(s.substring(n, i));
                    n2 = 0;
                    b2 = true;
                }
                n = ++i;
            }
            else {
                b2 = false;
                n2 = 1;
                ++i;
            }
        }
        if (n2 != 0 || (b && b2)) {
            list.add(s.substring(n, i));
        }
        return (String[])list.toArray(new String[list.size()]);
    }
    
    public static String[] splitPreserveAllTokens(final String s, final String s2) {
        return splitWorker(s, s2, -1, true);
    }
    
    public static String[] splitPreserveAllTokens(final String s, final String s2, final int n) {
        return splitWorker(s, s2, n, true);
    }
    
    private static String[] splitWorker(final String s, final String s2, final int n, final boolean b) {
        if (s == null) {
            return null;
        }
        final int length = s.length();
        if (length == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final ArrayList list = new ArrayList<String>();
        int n2 = 1;
        int i = 0;
        int n3 = 0;
        int n4 = 0;
        boolean b2 = false;
        if (s2 == null) {
            while (i < length) {
                if (Character.isWhitespace(s.charAt(i))) {
                    if (n4 != 0 || b) {
                        b2 = true;
                        if (n2++ == n) {
                            i = length;
                            b2 = false;
                        }
                        list.add(s.substring(n3, i));
                        n4 = 0;
                    }
                    n3 = ++i;
                }
                else {
                    b2 = false;
                    n4 = 1;
                    ++i;
                }
            }
        }
        else if (s2.length() == 1) {
            final char char1 = s2.charAt(0);
            while (i < length) {
                if (s.charAt(i) == char1) {
                    if (n4 != 0 || b) {
                        b2 = true;
                        if (n2++ == n) {
                            i = length;
                            b2 = false;
                        }
                        list.add(s.substring(n3, i));
                        n4 = 0;
                    }
                    n3 = ++i;
                }
                else {
                    b2 = false;
                    n4 = 1;
                    ++i;
                }
            }
        }
        else {
            while (i < length) {
                if (s2.indexOf(s.charAt(i)) >= 0) {
                    if (n4 != 0 || b) {
                        b2 = true;
                        if (n2++ == n) {
                            i = length;
                            b2 = false;
                        }
                        list.add(s.substring(n3, i));
                        n4 = 0;
                    }
                    n3 = ++i;
                }
                else {
                    b2 = false;
                    n4 = 1;
                    ++i;
                }
            }
        }
        if (n4 != 0 || (b && b2)) {
            list.add(s.substring(n3, i));
        }
        return (String[])list.toArray(new String[list.size()]);
    }
    
    public static String[] splitByCharacterType(final String s) {
        return splitByCharacterType(s, false);
    }
    
    public static String[] splitByCharacterTypeCamelCase(final String s) {
        return splitByCharacterType(s, true);
    }
    
    private static String[] splitByCharacterType(final String s, final boolean b) {
        if (s == null) {
            return null;
        }
        if (s.length() == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        final char[] charArray = s.toCharArray();
        final ArrayList list = new ArrayList<String>();
        int offset = 0;
        int type = Character.getType(charArray[offset]);
        for (int i = offset + 1; i < charArray.length; ++i) {
            final int type2 = Character.getType(charArray[i]);
            if (type2 != type) {
                if (b && type2 == 2 && type == 1) {
                    final int n = i - 1;
                    if (n != offset) {
                        list.add(new String(charArray, offset, n - offset));
                        offset = n;
                    }
                }
                else {
                    list.add(new String(charArray, offset, i - offset));
                    offset = i;
                }
                type = type2;
            }
        }
        list.add(new String(charArray, offset, charArray.length - offset));
        return (String[])list.toArray(new String[list.size()]);
    }
    
    public static String concatenate(final Object[] array) {
        return join(array, null);
    }
    
    public static String join(final Object[] array) {
        return join(array, null);
    }
    
    public static String join(final Object[] array, final char c) {
        if (array == null) {
            return null;
        }
        return join(array, c, 0, array.length);
    }
    
    public static String join(final Object[] array, final char c, final int n, final int n2) {
        if (array == null) {
            return null;
        }
        final int n3 = n2 - n;
        if (n3 <= 0) {
            return "";
        }
        final StrBuilder strBuilder = new StrBuilder(n3 * (((array[n] == null) ? 16 : array[n].toString().length()) + 1));
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                strBuilder.append(c);
            }
            if (array[i] != null) {
                strBuilder.append(array[i]);
            }
        }
        return strBuilder.toString();
    }
    
    public static String join(final Object[] array, final String s) {
        if (array == null) {
            return null;
        }
        return join(array, s, 0, array.length);
    }
    
    public static String join(final Object[] array, String s, final int n, final int n2) {
        if (array == null) {
            return null;
        }
        if (s == null) {
            s = "";
        }
        final int n3 = n2 - n;
        if (n3 <= 0) {
            return "";
        }
        final StrBuilder strBuilder = new StrBuilder(n3 * (((array[n] == null) ? 16 : array[n].toString().length()) + s.length()));
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                strBuilder.append(s);
            }
            if (array[i] != null) {
                strBuilder.append(array[i]);
            }
        }
        return strBuilder.toString();
    }
    
    public static String join(final Iterator iterator, final char c) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return "";
        }
        final Object next = iterator.next();
        if (!iterator.hasNext()) {
            return ObjectUtils.toString(next);
        }
        final StrBuilder strBuilder = new StrBuilder(256);
        if (next != null) {
            strBuilder.append(next);
        }
        while (iterator.hasNext()) {
            strBuilder.append(c);
            final Object next2 = iterator.next();
            if (next2 != null) {
                strBuilder.append(next2);
            }
        }
        return strBuilder.toString();
    }
    
    public static String join(final Iterator iterator, final String s) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return "";
        }
        final Object next = iterator.next();
        if (!iterator.hasNext()) {
            return ObjectUtils.toString(next);
        }
        final StrBuilder strBuilder = new StrBuilder(256);
        if (next != null) {
            strBuilder.append(next);
        }
        while (iterator.hasNext()) {
            if (s != null) {
                strBuilder.append(s);
            }
            final Object next2 = iterator.next();
            if (next2 != null) {
                strBuilder.append(next2);
            }
        }
        return strBuilder.toString();
    }
    
    public static String join(final Collection collection, final char c) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), c);
    }
    
    public static String join(final Collection collection, final String s) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), s);
    }
    
    public static String deleteSpaces(final String s) {
        if (s == null) {
            return null;
        }
        return CharSetUtils.delete(s, " \t\r\n\b");
    }
    
    public static String deleteWhitespace(final String s) {
        if (isEmpty(s)) {
            return s;
        }
        final int length = s.length();
        final char[] value = new char[length];
        int count = 0;
        for (int i = 0; i < length; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                value[count++] = s.charAt(i);
            }
        }
        if (count == length) {
            return s;
        }
        return new String(value, 0, count);
    }
    
    public static String removeStart(final String s, final String prefix) {
        if (isEmpty(s) || isEmpty(prefix)) {
            return s;
        }
        if (s.startsWith(prefix)) {
            return s.substring(prefix.length());
        }
        return s;
    }
    
    public static String removeStartIgnoreCase(final String s, final String s2) {
        if (isEmpty(s) || isEmpty(s2)) {
            return s;
        }
        if (startsWithIgnoreCase(s, s2)) {
            return s.substring(s2.length());
        }
        return s;
    }
    
    public static String removeEnd(final String s, final String suffix) {
        if (isEmpty(s) || isEmpty(suffix)) {
            return s;
        }
        if (s.endsWith(suffix)) {
            return s.substring(0, s.length() - suffix.length());
        }
        return s;
    }
    
    public static String removeEndIgnoreCase(final String s, final String s2) {
        if (isEmpty(s) || isEmpty(s2)) {
            return s;
        }
        if (endsWithIgnoreCase(s, s2)) {
            return s.substring(0, s.length() - s2.length());
        }
        return s;
    }
    
    public static String remove(final String s, final String s2) {
        if (isEmpty(s) || isEmpty(s2)) {
            return s;
        }
        return replace(s, s2, "", -1);
    }
    
    public static String remove(final String s, final char ch) {
        if (isEmpty(s) || s.indexOf(ch) == -1) {
            return s;
        }
        final char[] charArray = s.toCharArray();
        int count = 0;
        for (int i = 0; i < charArray.length; ++i) {
            if (charArray[i] != ch) {
                charArray[count++] = charArray[i];
            }
        }
        return new String(charArray, 0, count);
    }
    
    public static String replaceOnce(final String s, final String s2, final String s3) {
        return replace(s, s2, s3, 1);
    }
    
    public static String replace(final String s, final String s2, final String s3) {
        return replace(s, s2, s3, -1);
    }
    
    public static String replace(final String s, final String s2, final String s3, int n) {
        if (isEmpty(s) || isEmpty(s2) || s3 == null || n == 0) {
            return s;
        }
        int n2 = 0;
        int i = s.indexOf(s2, n2);
        if (i == -1) {
            return s;
        }
        final int length = s2.length();
        final int n3 = s3.length() - length;
        final StrBuilder strBuilder = new StrBuilder(s.length() + ((n3 < 0) ? 0 : n3) * ((n < 0) ? 16 : ((n > 64) ? 64 : n)));
        while (i != -1) {
            strBuilder.append(s.substring(n2, i)).append(s3);
            n2 = i + length;
            if (--n == 0) {
                break;
            }
            i = s.indexOf(s2, n2);
        }
        strBuilder.append(s.substring(n2));
        return strBuilder.toString();
    }
    
    public static String replaceEach(final String s, final String[] array, final String[] array2) {
        return replaceEach(s, array, array2, false, 0);
    }
    
    public static String replaceEachRepeatedly(final String s, final String[] array, final String[] array2) {
        return replaceEach(s, array, array2, true, (array == null) ? 0 : array.length);
    }
    
    private static String replaceEach(final String str, final String[] array, final String[] array2, final boolean b, final int i) {
        if (str == null || str.length() == 0 || array == null || array.length == 0 || array2 == null || array2.length == 0) {
            return str;
        }
        if (i < 0) {
            throw new IllegalStateException("TimeToLive of " + i + " is less than 0: " + str);
        }
        final int length = array.length;
        final int length2 = array2.length;
        if (length != length2) {
            throw new IllegalArgumentException("Search and Replace array lengths don't match: " + length + " vs " + length2);
        }
        final boolean[] array3 = new boolean[length];
        int j = -1;
        int n = -1;
        for (int k = 0; k < length; ++k) {
            if (!array3[k] && array[k] != null && array[k].length() != 0) {
                if (array2[k] != null) {
                    final int index = str.indexOf(array[k]);
                    if (index == -1) {
                        array3[k] = true;
                    }
                    else if (j == -1 || index < j) {
                        j = index;
                        n = k;
                    }
                }
            }
        }
        if (j == -1) {
            return str;
        }
        int fromIndex = 0;
        int a = 0;
        for (int l = 0; l < array.length; ++l) {
            if (array[l] != null) {
                if (array2[l] != null) {
                    final int n2 = array2[l].length() - array[l].length();
                    if (n2 > 0) {
                        a += 3 * n2;
                    }
                }
            }
        }
        final StrBuilder strBuilder = new StrBuilder(str.length() + Math.min(a, str.length() / 5));
        while (j != -1) {
            for (int index2 = fromIndex; index2 < j; ++index2) {
                strBuilder.append(str.charAt(index2));
            }
            strBuilder.append(array2[n]);
            fromIndex = j + array[n].length();
            j = -1;
            n = -1;
            for (int n3 = 0; n3 < length; ++n3) {
                if (!array3[n3] && array[n3] != null && array[n3].length() != 0) {
                    if (array2[n3] != null) {
                        final int index3 = str.indexOf(array[n3], fromIndex);
                        if (index3 == -1) {
                            array3[n3] = true;
                        }
                        else if (j == -1 || index3 < j) {
                            j = index3;
                            n = n3;
                        }
                    }
                }
            }
        }
        for (int length3 = str.length(), index4 = fromIndex; index4 < length3; ++index4) {
            strBuilder.append(str.charAt(index4));
        }
        final String string = strBuilder.toString();
        if (!b) {
            return string;
        }
        return replaceEach(string, array, array2, b, i - 1);
    }
    
    public static String replaceChars(final String s, final char oldChar, final char newChar) {
        if (s == null) {
            return null;
        }
        return s.replace(oldChar, newChar);
    }
    
    public static String replaceChars(final String s, final String s2, String s3) {
        if (isEmpty(s) || isEmpty(s2)) {
            return s;
        }
        if (s3 == null) {
            s3 = "";
        }
        boolean b = false;
        final int length = s3.length();
        final int length2 = s.length();
        final StrBuilder strBuilder = new StrBuilder(length2);
        for (int i = 0; i < length2; ++i) {
            final char char1 = s.charAt(i);
            final int index = s2.indexOf(char1);
            if (index >= 0) {
                b = true;
                if (index < length) {
                    strBuilder.append(s3.charAt(index));
                }
            }
            else {
                strBuilder.append(char1);
            }
        }
        if (b) {
            return strBuilder.toString();
        }
        return s;
    }
    
    public static String overlayString(final String s, final String s2, final int endIndex, final int beginIndex) {
        return new StrBuilder(endIndex + s2.length() + s.length() - beginIndex + 1).append(s.substring(0, endIndex)).append(s2).append(s.substring(beginIndex)).toString();
    }
    
    public static String overlay(final String s, String s2, int endIndex, int beginIndex) {
        if (s == null) {
            return null;
        }
        if (s2 == null) {
            s2 = "";
        }
        final int length = s.length();
        if (endIndex < 0) {
            endIndex = 0;
        }
        if (endIndex > length) {
            endIndex = length;
        }
        if (beginIndex < 0) {
            beginIndex = 0;
        }
        if (beginIndex > length) {
            beginIndex = length;
        }
        if (endIndex > beginIndex) {
            final int n = endIndex;
            endIndex = beginIndex;
            beginIndex = n;
        }
        return new StrBuilder(length + endIndex - beginIndex + s2.length() + 1).append(s.substring(0, endIndex)).append(s2).append(s.substring(beginIndex)).toString();
    }
    
    public static String chomp(final String s) {
        if (isEmpty(s)) {
            return s;
        }
        if (s.length() != 1) {
            int n = s.length() - 1;
            final char char1 = s.charAt(n);
            if (char1 == '\n') {
                if (s.charAt(n - 1) == '\r') {
                    --n;
                }
            }
            else if (char1 != '\r') {
                ++n;
            }
            return s.substring(0, n);
        }
        final char char2 = s.charAt(0);
        if (char2 == '\r' || char2 == '\n') {
            return "";
        }
        return s;
    }
    
    public static String chomp(final String s, final String suffix) {
        if (isEmpty(s) || suffix == null) {
            return s;
        }
        if (s.endsWith(suffix)) {
            return s.substring(0, s.length() - suffix.length());
        }
        return s;
    }
    
    public static String chompLast(final String s) {
        return chompLast(s, "\n");
    }
    
    public static String chompLast(final String s, final String s2) {
        if (s.length() == 0) {
            return s;
        }
        if (s2.equals(s.substring(s.length() - s2.length()))) {
            return s.substring(0, s.length() - s2.length());
        }
        return s;
    }
    
    public static String getChomp(final String s, final String str) {
        final int lastIndex = s.lastIndexOf(str);
        if (lastIndex == s.length() - str.length()) {
            return str;
        }
        if (lastIndex != -1) {
            return s.substring(lastIndex);
        }
        return "";
    }
    
    public static String prechomp(final String s, final String str) {
        final int index = s.indexOf(str);
        if (index == -1) {
            return s;
        }
        return s.substring(index + str.length());
    }
    
    public static String getPrechomp(final String s, final String str) {
        final int index = s.indexOf(str);
        if (index == -1) {
            return "";
        }
        return s.substring(0, index + str.length());
    }
    
    public static String chop(final String s) {
        if (s == null) {
            return null;
        }
        final int length = s.length();
        if (length < 2) {
            return "";
        }
        final int n = length - 1;
        final String substring = s.substring(0, n);
        if (s.charAt(n) == '\n' && substring.charAt(n - 1) == '\r') {
            return substring.substring(0, n - 1);
        }
        return substring;
    }
    
    public static String chopNewline(final String s) {
        int n = s.length() - 1;
        if (n <= 0) {
            return "";
        }
        if (s.charAt(n) == '\n') {
            if (s.charAt(n - 1) == '\r') {
                --n;
            }
        }
        else {
            ++n;
        }
        return s.substring(0, n);
    }
    
    public static String escape(final String s) {
        return StringEscapeUtils.escapeJava(s);
    }
    
    public static String repeat(final String s, final int n) {
        if (s == null) {
            return null;
        }
        if (n <= 0) {
            return "";
        }
        final int length = s.length();
        if (n == 1 || length == 0) {
            return s;
        }
        if (length == 1 && n <= 8192) {
            return padding(n, s.charAt(0));
        }
        final int n2 = length * n;
        switch (length) {
            case 1: {
                final char char1 = s.charAt(0);
                final char[] value = new char[n2];
                for (int i = n - 1; i >= 0; --i) {
                    value[i] = char1;
                }
                return new String(value);
            }
            case 2: {
                final char char2 = s.charAt(0);
                final char char3 = s.charAt(1);
                final char[] value2 = new char[n2];
                for (int j = n * 2 - 2; j >= 0; --j, --j) {
                    value2[j] = char2;
                    value2[j + 1] = char3;
                }
                return new String(value2);
            }
            default: {
                final StrBuilder strBuilder = new StrBuilder(n2);
                for (int k = 0; k < n; ++k) {
                    strBuilder.append(s);
                }
                return strBuilder.toString();
            }
        }
    }
    
    public static String repeat(final String str, final String str2, final int n) {
        if (str == null || str2 == null) {
            return repeat(str, n);
        }
        return removeEnd(repeat(str + str2, n), str2);
    }
    
    private static String padding(final int i, final char c) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + i);
        }
        final char[] value = new char[i];
        for (int j = 0; j < value.length; ++j) {
            value[j] = c;
        }
        return new String(value);
    }
    
    public static String rightPad(final String s, final int n) {
        return rightPad(s, n, ' ');
    }
    
    public static String rightPad(final String s, final int n, final char c) {
        if (s == null) {
            return null;
        }
        final int n2 = n - s.length();
        if (n2 <= 0) {
            return s;
        }
        if (n2 > 8192) {
            return rightPad(s, n, String.valueOf(c));
        }
        return s.concat(padding(n2, c));
    }
    
    public static String rightPad(final String s, final int n, String str) {
        if (s == null) {
            return null;
        }
        if (isEmpty(str)) {
            str = " ";
        }
        final int length = str.length();
        final int endIndex = n - s.length();
        if (endIndex <= 0) {
            return s;
        }
        if (length == 1 && endIndex <= 8192) {
            return rightPad(s, n, str.charAt(0));
        }
        if (endIndex == length) {
            return s.concat(str);
        }
        if (endIndex < length) {
            return s.concat(str.substring(0, endIndex));
        }
        final char[] value = new char[endIndex];
        final char[] charArray = str.toCharArray();
        for (int i = 0; i < endIndex; ++i) {
            value[i] = charArray[i % length];
        }
        return s.concat(new String(value));
    }
    
    public static String leftPad(final String s, final int n) {
        return leftPad(s, n, ' ');
    }
    
    public static String leftPad(final String str, final int n, final char c) {
        if (str == null) {
            return null;
        }
        final int n2 = n - str.length();
        if (n2 <= 0) {
            return str;
        }
        if (n2 > 8192) {
            return leftPad(str, n, String.valueOf(c));
        }
        return padding(n2, c).concat(str);
    }
    
    public static String leftPad(final String str, final int n, String s) {
        if (str == null) {
            return null;
        }
        if (isEmpty(s)) {
            s = " ";
        }
        final int length = s.length();
        final int endIndex = n - str.length();
        if (endIndex <= 0) {
            return str;
        }
        if (length == 1 && endIndex <= 8192) {
            return leftPad(str, n, s.charAt(0));
        }
        if (endIndex == length) {
            return s.concat(str);
        }
        if (endIndex < length) {
            return s.substring(0, endIndex).concat(str);
        }
        final char[] value = new char[endIndex];
        final char[] charArray = s.toCharArray();
        for (int i = 0; i < endIndex; ++i) {
            value[i] = charArray[i % length];
        }
        return new String(value).concat(str);
    }
    
    public static int length(final String s) {
        return (s == null) ? 0 : s.length();
    }
    
    public static String center(final String s, final int n) {
        return center(s, n, ' ');
    }
    
    public static String center(String s, final int n, final char c) {
        if (s == null || n <= 0) {
            return s;
        }
        final int length = s.length();
        final int n2 = n - length;
        if (n2 <= 0) {
            return s;
        }
        s = leftPad(s, length + n2 / 2, c);
        s = rightPad(s, n, c);
        return s;
    }
    
    public static String center(String s, final int n, String s2) {
        if (s == null || n <= 0) {
            return s;
        }
        if (isEmpty(s2)) {
            s2 = " ";
        }
        final int length = s.length();
        final int n2 = n - length;
        if (n2 <= 0) {
            return s;
        }
        s = leftPad(s, length + n2 / 2, s2);
        s = rightPad(s, n, s2);
        return s;
    }
    
    public static String upperCase(final String s) {
        if (s == null) {
            return null;
        }
        return s.toUpperCase();
    }
    
    public static String upperCase(final String s, final Locale locale) {
        if (s == null) {
            return null;
        }
        return s.toUpperCase(locale);
    }
    
    public static String lowerCase(final String s) {
        if (s == null) {
            return null;
        }
        return s.toLowerCase();
    }
    
    public static String lowerCase(final String s, final Locale locale) {
        if (s == null) {
            return null;
        }
        return s.toLowerCase(locale);
    }
    
    public static String capitalize(final String s) {
        final int length;
        if (s == null || (length = s.length()) == 0) {
            return s;
        }
        return new StrBuilder(length).append(Character.toTitleCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    
    public static String capitalise(final String s) {
        return capitalize(s);
    }
    
    public static String uncapitalize(final String s) {
        final int length;
        if (s == null || (length = s.length()) == 0) {
            return s;
        }
        return new StrBuilder(length).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    
    public static String uncapitalise(final String s) {
        return uncapitalize(s);
    }
    
    public static String swapCase(final String s) {
        final int length;
        if (s == null || (length = s.length()) == 0) {
            return s;
        }
        final StrBuilder strBuilder = new StrBuilder(length);
        for (int i = 0; i < length; ++i) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c)) {
                c = Character.toLowerCase(c);
            }
            else if (Character.isTitleCase(c)) {
                c = Character.toLowerCase(c);
            }
            else if (Character.isLowerCase(c)) {
                c = Character.toUpperCase(c);
            }
            strBuilder.append(c);
        }
        return strBuilder.toString();
    }
    
    public static String capitaliseAllWords(final String s) {
        return WordUtils.capitalize(s);
    }
    
    public static int countMatches(final String s, final String str) {
        if (isEmpty(s) || isEmpty(str)) {
            return 0;
        }
        int n = 0;
        int index;
        for (int fromIndex = 0; (index = s.indexOf(str, fromIndex)) != -1; fromIndex = index + str.length()) {
            ++n;
        }
        return n;
    }
    
    public static boolean isAlpha(final String s) {
        if (s == null) {
            return false;
        }
        for (int length = s.length(), i = 0; i < length; ++i) {
            if (!Character.isLetter(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isAlphaSpace(final String s) {
        if (s == null) {
            return false;
        }
        for (int length = s.length(), i = 0; i < length; ++i) {
            if (!Character.isLetter(s.charAt(i)) && s.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isAlphanumeric(final String s) {
        if (s == null) {
            return false;
        }
        for (int length = s.length(), i = 0; i < length; ++i) {
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isAlphanumericSpace(final String s) {
        if (s == null) {
            return false;
        }
        for (int length = s.length(), i = 0; i < length; ++i) {
            if (!Character.isLetterOrDigit(s.charAt(i)) && s.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isAsciiPrintable(final String s) {
        if (s == null) {
            return false;
        }
        for (int length = s.length(), i = 0; i < length; ++i) {
            if (!CharUtils.isAsciiPrintable(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNumeric(final String s) {
        if (s == null) {
            return false;
        }
        for (int length = s.length(), i = 0; i < length; ++i) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNumericSpace(final String s) {
        if (s == null) {
            return false;
        }
        for (int length = s.length(), i = 0; i < length; ++i) {
            if (!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isWhitespace(final String s) {
        if (s == null) {
            return false;
        }
        for (int length = s.length(), i = 0; i < length; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isAllLowerCase(final String s) {
        if (s == null || isEmpty(s)) {
            return false;
        }
        for (int length = s.length(), i = 0; i < length; ++i) {
            if (!Character.isLowerCase(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isAllUpperCase(final String s) {
        if (s == null || isEmpty(s)) {
            return false;
        }
        for (int length = s.length(), i = 0; i < length; ++i) {
            if (!Character.isUpperCase(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static String defaultString(final String s) {
        return (s == null) ? "" : s;
    }
    
    public static String defaultString(final String s, final String s2) {
        return (s == null) ? s2 : s;
    }
    
    public static String defaultIfBlank(final String s, final String s2) {
        return isBlank(s) ? s2 : s;
    }
    
    public static String defaultIfEmpty(final String s, final String s2) {
        return isEmpty(s) ? s2 : s;
    }
    
    public static String reverse(final String s) {
        if (s == null) {
            return null;
        }
        return new StrBuilder(s).reverse().toString();
    }
    
    public static String reverseDelimited(final String s, final char c) {
        if (s == null) {
            return null;
        }
        final String[] split = split(s, c);
        ArrayUtils.reverse(split);
        return join(split, c);
    }
    
    public static String reverseDelimitedString(final String s, final String s2) {
        if (s == null) {
            return null;
        }
        final String[] split = split(s, s2);
        ArrayUtils.reverse(split);
        if (s2 == null) {
            return join(split, ' ');
        }
        return join(split, s2);
    }
    
    public static String abbreviate(final String s, final int n) {
        return abbreviate(s, 0, n);
    }
    
    public static String abbreviate(final String s, int length, final int n) {
        if (s == null) {
            return null;
        }
        if (n < 4) {
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        }
        if (s.length() <= n) {
            return s;
        }
        if (length > s.length()) {
            length = s.length();
        }
        if (s.length() - length < n - 3) {
            length = s.length() - (n - 3);
        }
        if (length <= 4) {
            return s.substring(0, n - 3) + "...";
        }
        if (n < 7) {
            throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
        }
        if (length + (n - 3) < s.length()) {
            return "..." + abbreviate(s.substring(length), n - 3);
        }
        return "..." + s.substring(s.length() - (n - 3));
    }
    
    public static String abbreviateMiddle(final String s, final String s2, final int n) {
        if (isEmpty(s) || isEmpty(s2)) {
            return s;
        }
        if (n >= s.length() || n < s2.length() + 2) {
            return s;
        }
        final int n2 = n - s2.length();
        final int endIndex = n2 / 2 + n2 % 2;
        final int beginIndex = s.length() - n2 / 2;
        final StrBuilder strBuilder = new StrBuilder(n);
        strBuilder.append(s.substring(0, endIndex));
        strBuilder.append(s2);
        strBuilder.append(s.substring(beginIndex));
        return strBuilder.toString();
    }
    
    public static String difference(final String s, final String s2) {
        if (s == null) {
            return s2;
        }
        if (s2 == null) {
            return s;
        }
        final int indexOfDifference = indexOfDifference(s, s2);
        if (indexOfDifference == -1) {
            return "";
        }
        return s2.substring(indexOfDifference);
    }
    
    public static int indexOfDifference(final String s, final String s2) {
        if (s == s2) {
            return -1;
        }
        if (s == null || s2 == null) {
            return 0;
        }
        int n;
        for (n = 0; n < s.length() && n < s2.length() && s.charAt(n) == s2.charAt(n); ++n) {}
        if (n < s2.length() || n < s.length()) {
            return n;
        }
        return -1;
    }
    
    public static int indexOfDifference(final String[] array) {
        if (array == null || array.length <= 1) {
            return -1;
        }
        boolean b = false;
        boolean b2 = true;
        final int length = array.length;
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (int i = 0; i < length; ++i) {
            if (array[i] == null) {
                b = true;
                min = 0;
            }
            else {
                b2 = false;
                min = Math.min(array[i].length(), min);
                max = Math.max(array[i].length(), max);
            }
        }
        if (b2 || (max == 0 && !b)) {
            return -1;
        }
        if (min == 0) {
            return 0;
        }
        int n = -1;
        for (int j = 0; j < min; ++j) {
            final char char1 = array[0].charAt(j);
            for (int k = 1; k < length; ++k) {
                if (array[k].charAt(j) != char1) {
                    n = j;
                    break;
                }
            }
            if (n != -1) {
                break;
            }
        }
        if (n == -1 && min != max) {
            return min;
        }
        return n;
    }
    
    public static String getCommonPrefix(final String[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        final int indexOfDifference = indexOfDifference(array);
        if (indexOfDifference == -1) {
            if (array[0] == null) {
                return "";
            }
            return array[0];
        }
        else {
            if (indexOfDifference == 0) {
                return "";
            }
            return array[0].substring(0, indexOfDifference);
        }
    }
    
    public static int getLevenshteinDistance(String s, String s2) {
        if (s == null || s2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int length = s.length();
        int n = s2.length();
        if (length == 0) {
            return n;
        }
        if (n == 0) {
            return length;
        }
        if (length > n) {
            final String s3 = s;
            s = s2;
            s2 = s3;
            length = n;
            n = s2.length();
        }
        int[] array = new int[length + 1];
        int[] array2 = new int[length + 1];
        for (int i = 0; i <= length; ++i) {
            array[i] = i;
        }
        for (int j = 1; j <= n; ++j) {
            final char char1 = s2.charAt(j - 1);
            array2[0] = j;
            for (int k = 1; k <= length; ++k) {
                array2[k] = Math.min(Math.min(array2[k - 1] + 1, array[k] + 1), array[k - 1] + ((s.charAt(k - 1) != char1) ? 1 : 0));
            }
            final int[] array3 = array;
            array = array2;
            array2 = array3;
        }
        return array[length];
    }
    
    public static boolean startsWith(final String s, final String s2) {
        return startsWith(s, s2, false);
    }
    
    public static boolean startsWithIgnoreCase(final String s, final String s2) {
        return startsWith(s, s2, true);
    }
    
    private static boolean startsWith(final String s, final String other, final boolean ignoreCase) {
        if (s == null || other == null) {
            return s == null && other == null;
        }
        return other.length() <= s.length() && s.regionMatches(ignoreCase, 0, other, 0, other.length());
    }
    
    public static boolean startsWithAny(final String s, final String[] array) {
        if (isEmpty(s) || ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            if (startsWith(s, array[i])) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean endsWith(final String s, final String s2) {
        return endsWith(s, s2, false);
    }
    
    public static boolean endsWithIgnoreCase(final String s, final String s2) {
        return endsWith(s, s2, true);
    }
    
    private static boolean endsWith(final String s, final String other, final boolean ignoreCase) {
        if (s == null || other == null) {
            return s == null && other == null;
        }
        return other.length() <= s.length() && s.regionMatches(ignoreCase, s.length() - other.length(), other, 0, other.length());
    }
    
    public static String normalizeSpace(String strip) {
        strip = strip(strip);
        if (strip == null || strip.length() <= 2) {
            return strip;
        }
        final StrBuilder strBuilder = new StrBuilder(strip.length());
        for (int i = 0; i < strip.length(); ++i) {
            final char char1 = strip.charAt(i);
            if (Character.isWhitespace(char1)) {
                if (i > 0 && !Character.isWhitespace(strip.charAt(i - 1))) {
                    strBuilder.append(' ');
                }
            }
            else {
                strBuilder.append(char1);
            }
        }
        return strBuilder.toString();
    }
    
    public static boolean endsWithAny(final String s, final String[] array) {
        if (isEmpty(s) || ArrayUtils.isEmpty(array)) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            if (endsWith(s, array[i])) {
                return true;
            }
        }
        return false;
    }
}
