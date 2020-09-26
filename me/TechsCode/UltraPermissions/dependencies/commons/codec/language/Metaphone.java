

package me.TechsCode.EnderPermissions.dependencies.commons.codec.language;

import me.TechsCode.EnderPermissions.dependencies.commons.codec.EncoderException;
import java.util.Locale;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringEncoder;

public class Metaphone implements StringEncoder
{
    private static final String VOWELS = "AEIOU";
    private static final String FRONTV = "EIY";
    private static final String VARSON = "CSPTG";
    private int maxCodeLen;
    
    public Metaphone() {
        this.maxCodeLen = 4;
    }
    
    public String metaphone(final String s) {
        final int length;
        if (s == null || (length = s.length()) == 0) {
            return "";
        }
        if (length == 1) {
            return s.toUpperCase(Locale.ENGLISH);
        }
        final char[] charArray = s.toUpperCase(Locale.ENGLISH).toCharArray();
        final StringBuilder sb = new StringBuilder(40);
        final StringBuilder sb2 = new StringBuilder(10);
        switch (charArray[0]) {
            case 'G':
            case 'K':
            case 'P': {
                if (charArray[1] == 'N') {
                    sb.append(charArray, 1, charArray.length - 1);
                    break;
                }
                sb.append(charArray);
                break;
            }
            case 'A': {
                if (charArray[1] == 'E') {
                    sb.append(charArray, 1, charArray.length - 1);
                    break;
                }
                sb.append(charArray);
                break;
            }
            case 'W': {
                if (charArray[1] == 'R') {
                    sb.append(charArray, 1, charArray.length - 1);
                    break;
                }
                if (charArray[1] == 'H') {
                    sb.append(charArray, 1, charArray.length - 1);
                    sb.setCharAt(0, 'W');
                    break;
                }
                sb.append(charArray);
                break;
            }
            case 'X': {
                charArray[0] = 'S';
                sb.append(charArray);
                break;
            }
            default: {
                sb.append(charArray);
                break;
            }
        }
        final int length2 = sb.length();
        int n = 0;
        while (sb2.length() < this.getMaxCodeLen() && n < length2) {
            final char char1 = sb.charAt(n);
            if (char1 != 'C' && this.isPreviousChar(sb, n, char1)) {
                ++n;
            }
            else {
                switch (char1) {
                    case 'A':
                    case 'E':
                    case 'I':
                    case 'O':
                    case 'U': {
                        if (n == 0) {
                            sb2.append(char1);
                            break;
                        }
                        break;
                    }
                    case 'B': {
                        if (this.isPreviousChar(sb, n, 'M') && this.isLastChar(length2, n)) {
                            break;
                        }
                        sb2.append(char1);
                        break;
                    }
                    case 'C': {
                        if (this.isPreviousChar(sb, n, 'S') && !this.isLastChar(length2, n) && "EIY".indexOf(sb.charAt(n + 1)) >= 0) {
                            break;
                        }
                        if (this.regionMatch(sb, n, "CIA")) {
                            sb2.append('X');
                            break;
                        }
                        if (!this.isLastChar(length2, n) && "EIY".indexOf(sb.charAt(n + 1)) >= 0) {
                            sb2.append('S');
                            break;
                        }
                        if (this.isPreviousChar(sb, n, 'S') && this.isNextChar(sb, n, 'H')) {
                            sb2.append('K');
                            break;
                        }
                        if (!this.isNextChar(sb, n, 'H')) {
                            sb2.append('K');
                            break;
                        }
                        if (n == 0 && length2 >= 3 && this.isVowel(sb, 2)) {
                            sb2.append('K');
                            break;
                        }
                        sb2.append('X');
                        break;
                    }
                    case 'D': {
                        if (!this.isLastChar(length2, n + 1) && this.isNextChar(sb, n, 'G') && "EIY".indexOf(sb.charAt(n + 2)) >= 0) {
                            sb2.append('J');
                            n += 2;
                            break;
                        }
                        sb2.append('T');
                        break;
                    }
                    case 'G': {
                        if (this.isLastChar(length2, n + 1) && this.isNextChar(sb, n, 'H')) {
                            break;
                        }
                        if (!this.isLastChar(length2, n + 1) && this.isNextChar(sb, n, 'H') && !this.isVowel(sb, n + 2)) {
                            break;
                        }
                        if (n > 0) {
                            if (this.regionMatch(sb, n, "GN")) {
                                break;
                            }
                            if (this.regionMatch(sb, n, "GNED")) {
                                break;
                            }
                        }
                        final boolean previousChar = this.isPreviousChar(sb, n, 'G');
                        if (!this.isLastChar(length2, n) && "EIY".indexOf(sb.charAt(n + 1)) >= 0 && !previousChar) {
                            sb2.append('J');
                            break;
                        }
                        sb2.append('K');
                        break;
                    }
                    case 'H': {
                        if (this.isLastChar(length2, n)) {
                            break;
                        }
                        if (n > 0 && "CSPTG".indexOf(sb.charAt(n - 1)) >= 0) {
                            break;
                        }
                        if (this.isVowel(sb, n + 1)) {
                            sb2.append('H');
                            break;
                        }
                        break;
                    }
                    case 'F':
                    case 'J':
                    case 'L':
                    case 'M':
                    case 'N':
                    case 'R': {
                        sb2.append(char1);
                        break;
                    }
                    case 'K': {
                        if (n <= 0) {
                            sb2.append(char1);
                            break;
                        }
                        if (!this.isPreviousChar(sb, n, 'C')) {
                            sb2.append(char1);
                            break;
                        }
                        break;
                    }
                    case 'P': {
                        if (this.isNextChar(sb, n, 'H')) {
                            sb2.append('F');
                            break;
                        }
                        sb2.append(char1);
                        break;
                    }
                    case 'Q': {
                        sb2.append('K');
                        break;
                    }
                    case 'S': {
                        if (this.regionMatch(sb, n, "SH") || this.regionMatch(sb, n, "SIO") || this.regionMatch(sb, n, "SIA")) {
                            sb2.append('X');
                            break;
                        }
                        sb2.append('S');
                        break;
                    }
                    case 'T': {
                        if (this.regionMatch(sb, n, "TIA") || this.regionMatch(sb, n, "TIO")) {
                            sb2.append('X');
                            break;
                        }
                        if (this.regionMatch(sb, n, "TCH")) {
                            break;
                        }
                        if (this.regionMatch(sb, n, "TH")) {
                            sb2.append('0');
                            break;
                        }
                        sb2.append('T');
                        break;
                    }
                    case 'V': {
                        sb2.append('F');
                        break;
                    }
                    case 'W':
                    case 'Y': {
                        if (!this.isLastChar(length2, n) && this.isVowel(sb, n + 1)) {
                            sb2.append(char1);
                            break;
                        }
                        break;
                    }
                    case 'X': {
                        sb2.append('K');
                        sb2.append('S');
                        break;
                    }
                    case 'Z': {
                        sb2.append('S');
                        break;
                    }
                }
                ++n;
            }
            if (sb2.length() > this.getMaxCodeLen()) {
                sb2.setLength(this.getMaxCodeLen());
            }
        }
        return sb2.toString();
    }
    
    private boolean isVowel(final StringBuilder sb, final int n) {
        return "AEIOU".indexOf(sb.charAt(n)) >= 0;
    }
    
    private boolean isPreviousChar(final StringBuilder sb, final int n, final char c) {
        boolean b = false;
        if (n > 0 && n < sb.length()) {
            b = (sb.charAt(n - 1) == c);
        }
        return b;
    }
    
    private boolean isNextChar(final StringBuilder sb, final int n, final char c) {
        boolean b = false;
        if (n >= 0 && n < sb.length() - 1) {
            b = (sb.charAt(n + 1) == c);
        }
        return b;
    }
    
    private boolean regionMatch(final StringBuilder sb, final int n, final String anObject) {
        boolean equals = false;
        if (n >= 0 && n + anObject.length() - 1 < sb.length()) {
            equals = sb.substring(n, n + anObject.length()).equals(anObject);
        }
        return equals;
    }
    
    private boolean isLastChar(final int n, final int n2) {
        return n2 + 1 == n;
    }
    
    @Override
    public Object encode(final Object o) {
        if (!(o instanceof String)) {
            throw new EncoderException("Parameter supplied to Metaphone encode is not of type java.lang.String");
        }
        return this.metaphone((String)o);
    }
    
    @Override
    public String encode(final String s) {
        return this.metaphone(s);
    }
    
    public boolean isMetaphoneEqual(final String s, final String s2) {
        return this.metaphone(s).equals(this.metaphone(s2));
    }
    
    public int getMaxCodeLen() {
        return this.maxCodeLen;
    }
    
    public void setMaxCodeLen(final int maxCodeLen) {
        this.maxCodeLen = maxCodeLen;
    }
}
