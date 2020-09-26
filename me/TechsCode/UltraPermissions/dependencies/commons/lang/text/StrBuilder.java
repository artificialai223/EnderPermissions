

package me.TechsCode.EnderPermissions.dependencies.commons.lang.text;

import java.util.List;
import java.io.Writer;
import java.io.Reader;
import java.util.Iterator;
import java.util.Collection;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.SystemUtils;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.ArrayUtils;

public class StrBuilder implements Cloneable
{
    static final int CAPACITY = 32;
    private static final long serialVersionUID = 7628716375283629643L;
    protected char[] buffer;
    protected int size;
    private String newLine;
    private String nullText;
    
    public StrBuilder() {
        this(32);
    }
    
    public StrBuilder(int n) {
        if (n <= 0) {
            n = 32;
        }
        this.buffer = new char[n];
    }
    
    public StrBuilder(final String s) {
        if (s == null) {
            this.buffer = new char[32];
        }
        else {
            this.buffer = new char[s.length() + 32];
            this.append(s);
        }
    }
    
    public String getNewLineText() {
        return this.newLine;
    }
    
    public StrBuilder setNewLineText(final String newLine) {
        this.newLine = newLine;
        return this;
    }
    
    public String getNullText() {
        return this.nullText;
    }
    
    public StrBuilder setNullText(String nullText) {
        if (nullText != null && nullText.length() == 0) {
            nullText = null;
        }
        this.nullText = nullText;
        return this;
    }
    
    public int length() {
        return this.size;
    }
    
    public StrBuilder setLength(final int size) {
        if (size < 0) {
            throw new StringIndexOutOfBoundsException(size);
        }
        if (size < this.size) {
            this.size = size;
        }
        else if (size > this.size) {
            this.ensureCapacity(size);
            final int size2 = this.size;
            this.size = size;
            for (int i = size2; i < size; ++i) {
                this.buffer[i] = '\0';
            }
        }
        return this;
    }
    
    public int capacity() {
        return this.buffer.length;
    }
    
    public StrBuilder ensureCapacity(final int n) {
        if (n > this.buffer.length) {
            System.arraycopy(this.buffer, 0, this.buffer = new char[n * 2], 0, this.size);
        }
        return this;
    }
    
    public StrBuilder minimizeCapacity() {
        if (this.buffer.length > this.length()) {
            System.arraycopy(this.buffer, 0, this.buffer = new char[this.length()], 0, this.size);
        }
        return this;
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public StrBuilder clear() {
        this.size = 0;
        return this;
    }
    
    public char charAt(final int index) {
        if (index < 0 || index >= this.length()) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return this.buffer[index];
    }
    
    public StrBuilder setCharAt(final int index, final char c) {
        if (index < 0 || index >= this.length()) {
            throw new StringIndexOutOfBoundsException(index);
        }
        this.buffer[index] = c;
        return this;
    }
    
    public StrBuilder deleteCharAt(final int index) {
        if (index < 0 || index >= this.size) {
            throw new StringIndexOutOfBoundsException(index);
        }
        this.deleteImpl(index, index + 1, 1);
        return this;
    }
    
    public char[] toCharArray() {
        if (this.size == 0) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] array = new char[this.size];
        System.arraycopy(this.buffer, 0, array, 0, this.size);
        return array;
    }
    
    public char[] toCharArray(final int n, int validateRange) {
        validateRange = this.validateRange(n, validateRange);
        final int n2 = validateRange - n;
        if (n2 == 0) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] array = new char[n2];
        System.arraycopy(this.buffer, n, array, 0, n2);
        return array;
    }
    
    public char[] getChars(char[] array) {
        final int length = this.length();
        if (array == null || array.length < length) {
            array = new char[length];
        }
        System.arraycopy(this.buffer, 0, array, 0, length);
        return array;
    }
    
    public void getChars(final int index, final int index2, final char[] array, final int n) {
        if (index < 0) {
            throw new StringIndexOutOfBoundsException(index);
        }
        if (index2 < 0 || index2 > this.length()) {
            throw new StringIndexOutOfBoundsException(index2);
        }
        if (index > index2) {
            throw new StringIndexOutOfBoundsException("end < start");
        }
        System.arraycopy(this.buffer, index, array, n, index2 - index);
    }
    
    public StrBuilder appendNewLine() {
        if (this.newLine == null) {
            this.append(SystemUtils.LINE_SEPARATOR);
            return this;
        }
        return this.append(this.newLine);
    }
    
    public StrBuilder appendNull() {
        if (this.nullText == null) {
            return this;
        }
        return this.append(this.nullText);
    }
    
    public StrBuilder append(final Object o) {
        if (o == null) {
            return this.appendNull();
        }
        return this.append(o.toString());
    }
    
    public StrBuilder append(final String s) {
        if (s == null) {
            return this.appendNull();
        }
        final int length = s.length();
        if (length > 0) {
            final int length2 = this.length();
            this.ensureCapacity(length2 + length);
            s.getChars(0, length, this.buffer, length2);
            this.size += length;
        }
        return this;
    }
    
    public StrBuilder append(final String s, final int srcBegin, final int n) {
        if (s == null) {
            return this.appendNull();
        }
        if (srcBegin < 0 || srcBegin > s.length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        }
        if (n < 0 || srcBegin + n > s.length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        }
        if (n > 0) {
            final int length = this.length();
            this.ensureCapacity(length + n);
            s.getChars(srcBegin, srcBegin + n, this.buffer, length);
            this.size += n;
        }
        return this;
    }
    
    public StrBuilder append(final StringBuffer sb) {
        if (sb == null) {
            return this.appendNull();
        }
        final int length = sb.length();
        if (length > 0) {
            final int length2 = this.length();
            this.ensureCapacity(length2 + length);
            sb.getChars(0, length, this.buffer, length2);
            this.size += length;
        }
        return this;
    }
    
    public StrBuilder append(final StringBuffer sb, final int srcBegin, final int n) {
        if (sb == null) {
            return this.appendNull();
        }
        if (srcBegin < 0 || srcBegin > sb.length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        }
        if (n < 0 || srcBegin + n > sb.length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        }
        if (n > 0) {
            final int length = this.length();
            this.ensureCapacity(length + n);
            sb.getChars(srcBegin, srcBegin + n, this.buffer, length);
            this.size += n;
        }
        return this;
    }
    
    public StrBuilder append(final StrBuilder strBuilder) {
        if (strBuilder == null) {
            return this.appendNull();
        }
        final int length = strBuilder.length();
        if (length > 0) {
            final int length2 = this.length();
            this.ensureCapacity(length2 + length);
            System.arraycopy(strBuilder.buffer, 0, this.buffer, length2, length);
            this.size += length;
        }
        return this;
    }
    
    public StrBuilder append(final StrBuilder strBuilder, final int n, final int n2) {
        if (strBuilder == null) {
            return this.appendNull();
        }
        if (n < 0 || n > strBuilder.length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        }
        if (n2 < 0 || n + n2 > strBuilder.length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        }
        if (n2 > 0) {
            final int length = this.length();
            this.ensureCapacity(length + n2);
            strBuilder.getChars(n, n + n2, this.buffer, length);
            this.size += n2;
        }
        return this;
    }
    
    public StrBuilder append(final char[] array) {
        if (array == null) {
            return this.appendNull();
        }
        final int length = array.length;
        if (length > 0) {
            final int length2 = this.length();
            this.ensureCapacity(length2 + length);
            System.arraycopy(array, 0, this.buffer, length2, length);
            this.size += length;
        }
        return this;
    }
    
    public StrBuilder append(final char[] array, final int n, final int n2) {
        if (array == null) {
            return this.appendNull();
        }
        if (n < 0 || n > array.length) {
            throw new StringIndexOutOfBoundsException("Invalid startIndex: " + n2);
        }
        if (n2 < 0 || n + n2 > array.length) {
            throw new StringIndexOutOfBoundsException("Invalid length: " + n2);
        }
        if (n2 > 0) {
            final int length = this.length();
            this.ensureCapacity(length + n2);
            System.arraycopy(array, n, this.buffer, length, n2);
            this.size += n2;
        }
        return this;
    }
    
    public StrBuilder append(final boolean b) {
        if (b) {
            this.ensureCapacity(this.size + 4);
            this.buffer[this.size++] = 't';
            this.buffer[this.size++] = 'r';
            this.buffer[this.size++] = 'u';
            this.buffer[this.size++] = 'e';
        }
        else {
            this.ensureCapacity(this.size + 5);
            this.buffer[this.size++] = 'f';
            this.buffer[this.size++] = 'a';
            this.buffer[this.size++] = 'l';
            this.buffer[this.size++] = 's';
            this.buffer[this.size++] = 'e';
        }
        return this;
    }
    
    public StrBuilder append(final char c) {
        this.ensureCapacity(this.length() + 1);
        this.buffer[this.size++] = c;
        return this;
    }
    
    public StrBuilder append(final int i) {
        return this.append(String.valueOf(i));
    }
    
    public StrBuilder append(final long l) {
        return this.append(String.valueOf(l));
    }
    
    public StrBuilder append(final float f) {
        return this.append(String.valueOf(f));
    }
    
    public StrBuilder append(final double d) {
        return this.append(String.valueOf(d));
    }
    
    public StrBuilder appendln(final Object o) {
        return this.append(o).appendNewLine();
    }
    
    public StrBuilder appendln(final String s) {
        return this.append(s).appendNewLine();
    }
    
    public StrBuilder appendln(final String s, final int n, final int n2) {
        return this.append(s, n, n2).appendNewLine();
    }
    
    public StrBuilder appendln(final StringBuffer sb) {
        return this.append(sb).appendNewLine();
    }
    
    public StrBuilder appendln(final StringBuffer sb, final int n, final int n2) {
        return this.append(sb, n, n2).appendNewLine();
    }
    
    public StrBuilder appendln(final StrBuilder strBuilder) {
        return this.append(strBuilder).appendNewLine();
    }
    
    public StrBuilder appendln(final StrBuilder strBuilder, final int n, final int n2) {
        return this.append(strBuilder, n, n2).appendNewLine();
    }
    
    public StrBuilder appendln(final char[] array) {
        return this.append(array).appendNewLine();
    }
    
    public StrBuilder appendln(final char[] array, final int n, final int n2) {
        return this.append(array, n, n2).appendNewLine();
    }
    
    public StrBuilder appendln(final boolean b) {
        return this.append(b).appendNewLine();
    }
    
    public StrBuilder appendln(final char c) {
        return this.append(c).appendNewLine();
    }
    
    public StrBuilder appendln(final int n) {
        return this.append(n).appendNewLine();
    }
    
    public StrBuilder appendln(final long n) {
        return this.append(n).appendNewLine();
    }
    
    public StrBuilder appendln(final float n) {
        return this.append(n).appendNewLine();
    }
    
    public StrBuilder appendln(final double n) {
        return this.append(n).appendNewLine();
    }
    
    public StrBuilder appendAll(final Object[] array) {
        if (array != null && array.length > 0) {
            for (int i = 0; i < array.length; ++i) {
                this.append(array[i]);
            }
        }
        return this;
    }
    
    public StrBuilder appendAll(final Collection collection) {
        if (collection != null && collection.size() > 0) {
            final Iterator<Object> iterator = collection.iterator();
            while (iterator.hasNext()) {
                this.append(iterator.next());
            }
        }
        return this;
    }
    
    public StrBuilder appendAll(final Iterator iterator) {
        if (iterator != null) {
            while (iterator.hasNext()) {
                this.append(iterator.next());
            }
        }
        return this;
    }
    
    public StrBuilder appendWithSeparators(final Object[] array, String s) {
        if (array != null && array.length > 0) {
            s = ((s == null) ? "" : s);
            this.append(array[0]);
            for (int i = 1; i < array.length; ++i) {
                this.append(s);
                this.append(array[i]);
            }
        }
        return this;
    }
    
    public StrBuilder appendWithSeparators(final Collection collection, String s) {
        if (collection != null && collection.size() > 0) {
            s = ((s == null) ? "" : s);
            final Iterator<Object> iterator = collection.iterator();
            while (iterator.hasNext()) {
                this.append(iterator.next());
                if (iterator.hasNext()) {
                    this.append(s);
                }
            }
        }
        return this;
    }
    
    public StrBuilder appendWithSeparators(final Iterator iterator, String s) {
        if (iterator != null) {
            s = ((s == null) ? "" : s);
            while (iterator.hasNext()) {
                this.append(iterator.next());
                if (iterator.hasNext()) {
                    this.append(s);
                }
            }
        }
        return this;
    }
    
    public StrBuilder appendSeparator(final String s) {
        return this.appendSeparator(s, null);
    }
    
    public StrBuilder appendSeparator(final String s, final String s2) {
        final String s3 = this.isEmpty() ? s2 : s;
        if (s3 != null) {
            this.append(s3);
        }
        return this;
    }
    
    public StrBuilder appendSeparator(final char c) {
        if (this.size() > 0) {
            this.append(c);
        }
        return this;
    }
    
    public StrBuilder appendSeparator(final char c, final char c2) {
        if (this.size() > 0) {
            this.append(c);
        }
        else {
            this.append(c2);
        }
        return this;
    }
    
    public StrBuilder appendSeparator(final String s, final int n) {
        if (s != null && n > 0) {
            this.append(s);
        }
        return this;
    }
    
    public StrBuilder appendSeparator(final char c, final int n) {
        if (n > 0) {
            this.append(c);
        }
        return this;
    }
    
    public StrBuilder appendPadding(final int n, final char c) {
        if (n >= 0) {
            this.ensureCapacity(this.size + n);
            for (int i = 0; i < n; ++i) {
                this.buffer[this.size++] = c;
            }
        }
        return this;
    }
    
    public StrBuilder appendFixedWidthPadLeft(final Object o, final int n, final char c) {
        if (n > 0) {
            this.ensureCapacity(this.size + n);
            String s = (o == null) ? this.getNullText() : o.toString();
            if (s == null) {
                s = "";
            }
            final int length = s.length();
            if (length >= n) {
                s.getChars(length - n, length, this.buffer, this.size);
            }
            else {
                final int n2 = n - length;
                for (int i = 0; i < n2; ++i) {
                    this.buffer[this.size + i] = c;
                }
                s.getChars(0, length, this.buffer, this.size + n2);
            }
            this.size += n;
        }
        return this;
    }
    
    public StrBuilder appendFixedWidthPadLeft(final int i, final int n, final char c) {
        return this.appendFixedWidthPadLeft(String.valueOf(i), n, c);
    }
    
    public StrBuilder appendFixedWidthPadRight(final Object o, final int srcEnd, final char c) {
        if (srcEnd > 0) {
            this.ensureCapacity(this.size + srcEnd);
            String s = (o == null) ? this.getNullText() : o.toString();
            if (s == null) {
                s = "";
            }
            final int length = s.length();
            if (length >= srcEnd) {
                s.getChars(0, srcEnd, this.buffer, this.size);
            }
            else {
                final int n = srcEnd - length;
                s.getChars(0, length, this.buffer, this.size);
                for (int i = 0; i < n; ++i) {
                    this.buffer[this.size + length + i] = c;
                }
            }
            this.size += srcEnd;
        }
        return this;
    }
    
    public StrBuilder appendFixedWidthPadRight(final int i, final int n, final char c) {
        return this.appendFixedWidthPadRight(String.valueOf(i), n, c);
    }
    
    public StrBuilder insert(final int n, final Object o) {
        if (o == null) {
            return this.insert(n, this.nullText);
        }
        return this.insert(n, o.toString());
    }
    
    public StrBuilder insert(final int dstBegin, String nullText) {
        this.validateIndex(dstBegin);
        if (nullText == null) {
            nullText = this.nullText;
        }
        final int srcEnd = (nullText == null) ? 0 : nullText.length();
        if (srcEnd > 0) {
            final int size = this.size + srcEnd;
            this.ensureCapacity(size);
            System.arraycopy(this.buffer, dstBegin, this.buffer, dstBegin + srcEnd, this.size - dstBegin);
            this.size = size;
            nullText.getChars(0, srcEnd, this.buffer, dstBegin);
        }
        return this;
    }
    
    public StrBuilder insert(final int n, final char[] array) {
        this.validateIndex(n);
        if (array == null) {
            return this.insert(n, this.nullText);
        }
        final int length = array.length;
        if (length > 0) {
            this.ensureCapacity(this.size + length);
            System.arraycopy(this.buffer, n, this.buffer, n + length, this.size - n);
            System.arraycopy(array, 0, this.buffer, n, length);
            this.size += length;
        }
        return this;
    }
    
    public StrBuilder insert(final int n, final char[] array, final int i, final int j) {
        this.validateIndex(n);
        if (array == null) {
            return this.insert(n, this.nullText);
        }
        if (i < 0 || i > array.length) {
            throw new StringIndexOutOfBoundsException("Invalid offset: " + i);
        }
        if (j < 0 || i + j > array.length) {
            throw new StringIndexOutOfBoundsException("Invalid length: " + j);
        }
        if (j > 0) {
            this.ensureCapacity(this.size + j);
            System.arraycopy(this.buffer, n, this.buffer, n + j, this.size - n);
            System.arraycopy(array, i, this.buffer, n, j);
            this.size += j;
        }
        return this;
    }
    
    public StrBuilder insert(int n, final boolean b) {
        this.validateIndex(n);
        if (b) {
            this.ensureCapacity(this.size + 4);
            System.arraycopy(this.buffer, n, this.buffer, n + 4, this.size - n);
            this.buffer[n++] = 't';
            this.buffer[n++] = 'r';
            this.buffer[n++] = 'u';
            this.buffer[n] = 'e';
            this.size += 4;
        }
        else {
            this.ensureCapacity(this.size + 5);
            System.arraycopy(this.buffer, n, this.buffer, n + 5, this.size - n);
            this.buffer[n++] = 'f';
            this.buffer[n++] = 'a';
            this.buffer[n++] = 'l';
            this.buffer[n++] = 's';
            this.buffer[n] = 'e';
            this.size += 5;
        }
        return this;
    }
    
    public StrBuilder insert(final int n, final char c) {
        this.validateIndex(n);
        this.ensureCapacity(this.size + 1);
        System.arraycopy(this.buffer, n, this.buffer, n + 1, this.size - n);
        this.buffer[n] = c;
        ++this.size;
        return this;
    }
    
    public StrBuilder insert(final int n, final int i) {
        return this.insert(n, String.valueOf(i));
    }
    
    public StrBuilder insert(final int n, final long l) {
        return this.insert(n, String.valueOf(l));
    }
    
    public StrBuilder insert(final int n, final float f) {
        return this.insert(n, String.valueOf(f));
    }
    
    public StrBuilder insert(final int n, final double d) {
        return this.insert(n, String.valueOf(d));
    }
    
    private void deleteImpl(final int n, final int n2, final int n3) {
        System.arraycopy(this.buffer, n2, this.buffer, n, this.size - n2);
        this.size -= n3;
    }
    
    public StrBuilder delete(final int n, int validateRange) {
        validateRange = this.validateRange(n, validateRange);
        final int n2 = validateRange - n;
        if (n2 > 0) {
            this.deleteImpl(n, validateRange, n2);
        }
        return this;
    }
    
    public StrBuilder deleteAll(final char c) {
        for (int i = 0; i < this.size; ++i) {
            if (this.buffer[i] == c) {
                final int n = i;
                while (++i < this.size && this.buffer[i] == c) {}
                final int n2 = i - n;
                this.deleteImpl(n, i, n2);
                i -= n2;
            }
        }
        return this;
    }
    
    public StrBuilder deleteFirst(final char c) {
        for (int i = 0; i < this.size; ++i) {
            if (this.buffer[i] == c) {
                this.deleteImpl(i, i + 1, 1);
                break;
            }
        }
        return this;
    }
    
    public StrBuilder deleteAll(final String s) {
        final int n = (s == null) ? 0 : s.length();
        if (n > 0) {
            for (int i = this.indexOf(s, 0); i >= 0; i = this.indexOf(s, i)) {
                this.deleteImpl(i, i + n, n);
            }
        }
        return this;
    }
    
    public StrBuilder deleteFirst(final String s) {
        final int n = (s == null) ? 0 : s.length();
        if (n > 0) {
            final int index = this.indexOf(s, 0);
            if (index >= 0) {
                this.deleteImpl(index, index + n, n);
            }
        }
        return this;
    }
    
    public StrBuilder deleteAll(final StrMatcher strMatcher) {
        return this.replace(strMatcher, null, 0, this.size, -1);
    }
    
    public StrBuilder deleteFirst(final StrMatcher strMatcher) {
        return this.replace(strMatcher, null, 0, this.size, 1);
    }
    
    private void replaceImpl(final int dstBegin, final int n, final int n2, final String s, final int srcEnd) {
        final int size = this.size - n2 + srcEnd;
        if (srcEnd != n2) {
            this.ensureCapacity(size);
            System.arraycopy(this.buffer, n, this.buffer, dstBegin + srcEnd, this.size - n);
            this.size = size;
        }
        if (srcEnd > 0) {
            s.getChars(0, srcEnd, this.buffer, dstBegin);
        }
    }
    
    public StrBuilder replace(final int n, int validateRange, final String s) {
        validateRange = this.validateRange(n, validateRange);
        this.replaceImpl(n, validateRange, validateRange - n, s, (s == null) ? 0 : s.length());
        return this;
    }
    
    public StrBuilder replaceAll(final char c, final char c2) {
        if (c != c2) {
            for (int i = 0; i < this.size; ++i) {
                if (this.buffer[i] == c) {
                    this.buffer[i] = c2;
                }
            }
        }
        return this;
    }
    
    public StrBuilder replaceFirst(final char c, final char c2) {
        if (c != c2) {
            for (int i = 0; i < this.size; ++i) {
                if (this.buffer[i] == c) {
                    this.buffer[i] = c2;
                    break;
                }
            }
        }
        return this;
    }
    
    public StrBuilder replaceAll(final String s, final String s2) {
        final int n = (s == null) ? 0 : s.length();
        if (n > 0) {
            for (int n2 = (s2 == null) ? 0 : s2.length(), i = this.indexOf(s, 0); i >= 0; i = this.indexOf(s, i + n2)) {
                this.replaceImpl(i, i + n, n, s2, n2);
            }
        }
        return this;
    }
    
    public StrBuilder replaceFirst(final String s, final String s2) {
        final int n = (s == null) ? 0 : s.length();
        if (n > 0) {
            final int index = this.indexOf(s, 0);
            if (index >= 0) {
                this.replaceImpl(index, index + n, n, s2, (s2 == null) ? 0 : s2.length());
            }
        }
        return this;
    }
    
    public StrBuilder replaceAll(final StrMatcher strMatcher, final String s) {
        return this.replace(strMatcher, s, 0, this.size, -1);
    }
    
    public StrBuilder replaceFirst(final StrMatcher strMatcher, final String s) {
        return this.replace(strMatcher, s, 0, this.size, 1);
    }
    
    public StrBuilder replace(final StrMatcher strMatcher, final String s, final int n, int validateRange, final int n2) {
        validateRange = this.validateRange(n, validateRange);
        return this.replaceImpl(strMatcher, s, n, validateRange, n2);
    }
    
    private StrBuilder replaceImpl(final StrMatcher strMatcher, final String s, final int n, int n2, int n3) {
        if (strMatcher == null || this.size == 0) {
            return this;
        }
        final int n4 = (s == null) ? 0 : s.length();
        final char[] buffer = this.buffer;
        for (int n5 = n; n5 < n2 && n3 != 0; ++n5) {
            final int match = strMatcher.isMatch(buffer, n5, n, n2);
            if (match > 0) {
                this.replaceImpl(n5, n5 + match, match, s, n4);
                n2 = n2 - match + n4;
                n5 = n5 + n4 - 1;
                if (n3 > 0) {
                    --n3;
                }
            }
        }
        return this;
    }
    
    public StrBuilder reverse() {
        if (this.size == 0) {
            return this;
        }
        final int n = this.size / 2;
        final char[] buffer = this.buffer;
        for (int i = 0, n2 = this.size - 1; i < n; ++i, --n2) {
            final char c = buffer[i];
            buffer[i] = buffer[n2];
            buffer[n2] = c;
        }
        return this;
    }
    
    public StrBuilder trim() {
        if (this.size == 0) {
            return this;
        }
        int size;
        char[] buffer;
        int n;
        for (size = this.size, buffer = this.buffer, n = 0; n < size && buffer[n] <= ' '; ++n) {}
        while (n < size && buffer[size - 1] <= ' ') {
            --size;
        }
        if (size < this.size) {
            this.delete(size, this.size);
        }
        if (n > 0) {
            this.delete(0, n);
        }
        return this;
    }
    
    public boolean startsWith(final String s) {
        if (s == null) {
            return false;
        }
        final int length = s.length();
        if (length == 0) {
            return true;
        }
        if (length > this.size) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (this.buffer[i] != s.charAt(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean endsWith(final String s) {
        if (s == null) {
            return false;
        }
        final int length = s.length();
        if (length == 0) {
            return true;
        }
        if (length > this.size) {
            return false;
        }
        for (int n = this.size - length, i = 0; i < length; ++i, ++n) {
            if (this.buffer[n] != s.charAt(i)) {
                return false;
            }
        }
        return true;
    }
    
    public String substring(final int n) {
        return this.substring(n, this.size);
    }
    
    public String substring(final int offset, int validateRange) {
        validateRange = this.validateRange(offset, validateRange);
        return new String(this.buffer, offset, validateRange - offset);
    }
    
    public String leftString(final int count) {
        if (count <= 0) {
            return "";
        }
        if (count >= this.size) {
            return new String(this.buffer, 0, this.size);
        }
        return new String(this.buffer, 0, count);
    }
    
    public String rightString(final int count) {
        if (count <= 0) {
            return "";
        }
        if (count >= this.size) {
            return new String(this.buffer, 0, this.size);
        }
        return new String(this.buffer, this.size - count, count);
    }
    
    public String midString(int n, final int count) {
        if (n < 0) {
            n = 0;
        }
        if (count <= 0 || n >= this.size) {
            return "";
        }
        if (this.size <= n + count) {
            return new String(this.buffer, n, this.size - n);
        }
        return new String(this.buffer, n, count);
    }
    
    public boolean contains(final char c) {
        final char[] buffer = this.buffer;
        for (int i = 0; i < this.size; ++i) {
            if (buffer[i] == c) {
                return true;
            }
        }
        return false;
    }
    
    public boolean contains(final String s) {
        return this.indexOf(s, 0) >= 0;
    }
    
    public boolean contains(final StrMatcher strMatcher) {
        return this.indexOf(strMatcher, 0) >= 0;
    }
    
    public int indexOf(final char c) {
        return this.indexOf(c, 0);
    }
    
    public int indexOf(final char c, int n) {
        n = ((n < 0) ? 0 : n);
        if (n >= this.size) {
            return -1;
        }
        final char[] buffer = this.buffer;
        for (int i = n; i < this.size; ++i) {
            if (buffer[i] == c) {
                return i;
            }
        }
        return -1;
    }
    
    public int indexOf(final String s) {
        return this.indexOf(s, 0);
    }
    
    public int indexOf(final String s, int n) {
        n = ((n < 0) ? 0 : n);
        if (s == null || n >= this.size) {
            return -1;
        }
        final int length = s.length();
        if (length == 1) {
            return this.indexOf(s.charAt(0), n);
        }
        if (length == 0) {
            return n;
        }
        if (length > this.size) {
            return -1;
        }
        final char[] buffer = this.buffer;
        final int n2 = this.size - length + 1;
        int i = n;
    Label_0080:
        while (i < n2) {
            for (int j = 0; j < length; ++j) {
                if (s.charAt(j) != buffer[i + j]) {
                    ++i;
                    continue Label_0080;
                }
            }
            return i;
        }
        return -1;
    }
    
    public int indexOf(final StrMatcher strMatcher) {
        return this.indexOf(strMatcher, 0);
    }
    
    public int indexOf(final StrMatcher strMatcher, int n) {
        n = ((n < 0) ? 0 : n);
        if (strMatcher == null || n >= this.size) {
            return -1;
        }
        final int size = this.size;
        final char[] buffer = this.buffer;
        for (int i = n; i < size; ++i) {
            if (strMatcher.isMatch(buffer, i, n, size) > 0) {
                return i;
            }
        }
        return -1;
    }
    
    public int lastIndexOf(final char c) {
        return this.lastIndexOf(c, this.size - 1);
    }
    
    public int lastIndexOf(final char c, int n) {
        n = ((n >= this.size) ? (this.size - 1) : n);
        if (n < 0) {
            return -1;
        }
        for (int i = n; i >= 0; --i) {
            if (this.buffer[i] == c) {
                return i;
            }
        }
        return -1;
    }
    
    public int lastIndexOf(final String s) {
        return this.lastIndexOf(s, this.size - 1);
    }
    
    public int lastIndexOf(final String s, int n) {
        n = ((n >= this.size) ? (this.size - 1) : n);
        if (s == null || n < 0) {
            return -1;
        }
        final int length = s.length();
        if (length > 0 && length <= this.size) {
            if (length == 1) {
                return this.lastIndexOf(s.charAt(0), n);
            }
            int i = n - length + 1;
        Label_0069:
            while (i >= 0) {
                for (int j = 0; j < length; ++j) {
                    if (s.charAt(j) != this.buffer[i + j]) {
                        --i;
                        continue Label_0069;
                    }
                }
                return i;
            }
        }
        else if (length == 0) {
            return n;
        }
        return -1;
    }
    
    public int lastIndexOf(final StrMatcher strMatcher) {
        return this.lastIndexOf(strMatcher, this.size);
    }
    
    public int lastIndexOf(final StrMatcher strMatcher, int n) {
        n = ((n >= this.size) ? (this.size - 1) : n);
        if (strMatcher == null || n < 0) {
            return -1;
        }
        final char[] buffer = this.buffer;
        final int n2 = n + 1;
        for (int i = n; i >= 0; --i) {
            if (strMatcher.isMatch(buffer, i, 0, n2) > 0) {
                return i;
            }
        }
        return -1;
    }
    
    public StrTokenizer asTokenizer() {
        return new StrBuilderTokenizer();
    }
    
    public Reader asReader() {
        return new StrBuilderReader();
    }
    
    public Writer asWriter() {
        return new StrBuilderWriter();
    }
    
    public boolean equalsIgnoreCase(final StrBuilder strBuilder) {
        if (this == strBuilder) {
            return true;
        }
        if (this.size != strBuilder.size) {
            return false;
        }
        final char[] buffer = this.buffer;
        final char[] buffer2 = strBuilder.buffer;
        for (int i = this.size - 1; i >= 0; --i) {
            final char ch = buffer[i];
            final char ch2 = buffer2[i];
            if (ch != ch2 && Character.toUpperCase(ch) != Character.toUpperCase(ch2)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean equals(final StrBuilder strBuilder) {
        if (this == strBuilder) {
            return true;
        }
        if (this.size != strBuilder.size) {
            return false;
        }
        final char[] buffer = this.buffer;
        final char[] buffer2 = strBuilder.buffer;
        for (int i = this.size - 1; i >= 0; --i) {
            if (buffer[i] != buffer2[i]) {
                return false;
            }
        }
        return true;
    }
    
    public boolean equals(final Object o) {
        return o instanceof StrBuilder && this.equals((StrBuilder)o);
    }
    
    public int hashCode() {
        final char[] buffer = this.buffer;
        int n = 0;
        for (int i = this.size - 1; i >= 0; --i) {
            n = 31 * n + buffer[i];
        }
        return n;
    }
    
    public String toString() {
        return new String(this.buffer, 0, this.size);
    }
    
    public StringBuffer toStringBuffer() {
        return new StringBuffer(this.size).append(this.buffer, 0, this.size);
    }
    
    public Object clone() {
        final StrBuilder strBuilder = (StrBuilder)super.clone();
        strBuilder.buffer = new char[this.buffer.length];
        System.arraycopy(this.buffer, 0, strBuilder.buffer, 0, this.buffer.length);
        return strBuilder;
    }
    
    protected int validateRange(final int index, int size) {
        if (index < 0) {
            throw new StringIndexOutOfBoundsException(index);
        }
        if (size > this.size) {
            size = this.size;
        }
        if (index > size) {
            throw new StringIndexOutOfBoundsException("end < start");
        }
        return size;
    }
    
    protected void validateIndex(final int index) {
        if (index < 0 || index > this.size) {
            throw new StringIndexOutOfBoundsException(index);
        }
    }
    
    class StrBuilderTokenizer extends StrTokenizer
    {
        protected List tokenize(final char[] array, final int n, final int n2) {
            if (array == null) {
                return super.tokenize(StrBuilder.this.buffer, 0, StrBuilder.this.size());
            }
            return super.tokenize(array, n, n2);
        }
        
        public String getContent() {
            final String content = super.getContent();
            if (content == null) {
                return StrBuilder.this.toString();
            }
            return content;
        }
    }
    
    class StrBuilderReader extends Reader
    {
        private int pos;
        private int mark;
        
        public void close() {
        }
        
        public int read() {
            if (!this.ready()) {
                return -1;
            }
            return StrBuilder.this.charAt(this.pos++);
        }
        
        public int read(final char[] array, final int n, int n2) {
            if (n < 0 || n2 < 0 || n > array.length || n + n2 > array.length || n + n2 < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (n2 == 0) {
                return 0;
            }
            if (this.pos >= StrBuilder.this.size()) {
                return -1;
            }
            if (this.pos + n2 > StrBuilder.this.size()) {
                n2 = StrBuilder.this.size() - this.pos;
            }
            StrBuilder.this.getChars(this.pos, this.pos + n2, array, n);
            this.pos += n2;
            return n2;
        }
        
        public long skip(long n) {
            if (this.pos + n > StrBuilder.this.size()) {
                n = StrBuilder.this.size() - this.pos;
            }
            if (n < 0L) {
                return 0L;
            }
            this.pos += (int)n;
            return n;
        }
        
        public boolean ready() {
            return this.pos < StrBuilder.this.size();
        }
        
        public boolean markSupported() {
            return true;
        }
        
        public void mark(final int n) {
            this.mark = this.pos;
        }
        
        public void reset() {
            this.pos = this.mark;
        }
    }
    
    class StrBuilderWriter extends Writer
    {
        public void close() {
        }
        
        public void flush() {
        }
        
        public void write(final int n) {
            StrBuilder.this.append((char)n);
        }
        
        public void write(final char[] array) {
            StrBuilder.this.append(array);
        }
        
        public void write(final char[] array, final int n, final int n2) {
            StrBuilder.this.append(array, n, n2);
        }
        
        public void write(final String s) {
            StrBuilder.this.append(s);
        }
        
        public void write(final String s, final int n, final int n2) {
            StrBuilder.this.append(s, n, n2);
        }
    }
}
