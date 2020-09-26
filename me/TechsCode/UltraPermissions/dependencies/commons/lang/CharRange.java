

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import java.util.NoSuchElementException;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.text.StrBuilder;
import java.io.Serializable;

public final class CharRange implements Serializable
{
    private static final long serialVersionUID = 8270183163158333422L;
    private final char start;
    private final char end;
    private final boolean negated;
    private transient String iToString;
    
    public static CharRange is(final char c) {
        return new CharRange(c, c, false);
    }
    
    public static CharRange isNot(final char c) {
        return new CharRange(c, c, true);
    }
    
    public static CharRange isIn(final char c, final char c2) {
        return new CharRange(c, c2, false);
    }
    
    public static CharRange isNotIn(final char c, final char c2) {
        return new CharRange(c, c2, true);
    }
    
    public CharRange(final char c) {
        this(c, c, false);
    }
    
    public CharRange(final char c, final boolean b) {
        this(c, c, b);
    }
    
    public CharRange(final char c, final char c2) {
        this(c, c2, false);
    }
    
    public CharRange(char start, char end, final boolean negated) {
        if (start > end) {
            final char c = start;
            start = end;
            end = c;
        }
        this.start = start;
        this.end = end;
        this.negated = negated;
    }
    
    public char getStart() {
        return this.start;
    }
    
    public char getEnd() {
        return this.end;
    }
    
    public boolean isNegated() {
        return this.negated;
    }
    
    public boolean contains(final char c) {
        return (c >= this.start && c <= this.end) != this.negated;
    }
    
    public boolean contains(final CharRange charRange) {
        if (charRange == null) {
            throw new IllegalArgumentException("The Range must not be null");
        }
        if (this.negated) {
            if (charRange.negated) {
                return this.start >= charRange.start && this.end <= charRange.end;
            }
            return charRange.end < this.start || charRange.start > this.end;
        }
        else {
            if (charRange.negated) {
                return this.start == '\0' && this.end == '\uffff';
            }
            return this.start <= charRange.start && this.end >= charRange.end;
        }
    }
    
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CharRange)) {
            return false;
        }
        final CharRange charRange = (CharRange)o;
        return this.start == charRange.start && this.end == charRange.end && this.negated == charRange.negated;
    }
    
    public int hashCode() {
        return 'S' + this.start + '\u0007' * this.end + (this.negated ? 1 : 0);
    }
    
    public String toString() {
        if (this.iToString == null) {
            final StrBuilder strBuilder = new StrBuilder(4);
            if (this.isNegated()) {
                strBuilder.append('^');
            }
            strBuilder.append(this.start);
            if (this.start != this.end) {
                strBuilder.append('-');
                strBuilder.append(this.end);
            }
            this.iToString = strBuilder.toString();
        }
        return this.iToString;
    }
    
    public Iterator iterator() {
        return new CharacterIterator(this);
    }
    
    private static class CharacterIterator implements Iterator
    {
        private char current;
        private final CharRange range;
        private boolean hasNext;
        
        private CharacterIterator(final CharRange range) {
            this.range = range;
            this.hasNext = true;
            if (this.range.negated) {
                if (this.range.start == '\0') {
                    if (this.range.end == '\uffff') {
                        this.hasNext = false;
                    }
                    else {
                        this.current = (char)(this.range.end + '\u0001');
                    }
                }
                else {
                    this.current = '\0';
                }
            }
            else {
                this.current = this.range.start;
            }
        }
        
        private void prepareNext() {
            if (this.range.negated) {
                if (this.current == '\uffff') {
                    this.hasNext = false;
                }
                else if (this.current + '\u0001' == this.range.start) {
                    if (this.range.end == '\uffff') {
                        this.hasNext = false;
                    }
                    else {
                        this.current = (char)(this.range.end + '\u0001');
                    }
                }
                else {
                    ++this.current;
                }
            }
            else if (this.current < this.range.end) {
                ++this.current;
            }
            else {
                this.hasNext = false;
            }
        }
        
        public boolean hasNext() {
            return this.hasNext;
        }
        
        public Object next() {
            if (!this.hasNext) {
                throw new NoSuchElementException();
            }
            final char current = this.current;
            this.prepareNext();
            return new Character(current);
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
