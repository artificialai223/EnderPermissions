

package me.TechsCode.EnderPermissions.dependencies.commons.codec.language;

import java.util.Locale;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.EncoderException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringEncoder;

public class ColognePhonetic implements StringEncoder
{
    private static final char[] AEIJOUY;
    private static final char[] SCZ;
    private static final char[] WFPV;
    private static final char[] GKQ;
    private static final char[] CKQ;
    private static final char[] AHKLOQRUX;
    private static final char[] SZ;
    private static final char[] AHOUKQX;
    private static final char[] TDX;
    
    private static boolean arrayContains(final char[] array, final char c) {
        for (int length = array.length, i = 0; i < length; ++i) {
            if (array[i] == c) {
                return true;
            }
        }
        return false;
    }
    
    public String colognePhonetic(final String s) {
        if (s == null) {
            return null;
        }
        final CologneInputBuffer cologneInputBuffer = new CologneInputBuffer(this.preprocess(s));
        final CologneOutputBuffer cologneOutputBuffer = new CologneOutputBuffer(cologneInputBuffer.length() * 2);
        char c = '-';
        char c2 = '/';
        while (cologneInputBuffer.length() > 0) {
            final char removeNext = cologneInputBuffer.removeNext();
            char nextChar;
            if (cologneInputBuffer.length() > 0) {
                nextChar = cologneInputBuffer.getNextChar();
            }
            else {
                nextChar = '-';
            }
            if (removeNext != 'H' && removeNext >= 'A') {
                if (removeNext > 'Z') {
                    continue;
                }
                char c3;
                if (arrayContains(ColognePhonetic.AEIJOUY, removeNext)) {
                    c3 = '0';
                }
                else if (removeNext == 'B' || (removeNext == 'P' && nextChar != 'H')) {
                    c3 = '1';
                }
                else if ((removeNext == 'D' || removeNext == 'T') && !arrayContains(ColognePhonetic.SCZ, nextChar)) {
                    c3 = '2';
                }
                else if (arrayContains(ColognePhonetic.WFPV, removeNext)) {
                    c3 = '3';
                }
                else if (arrayContains(ColognePhonetic.GKQ, removeNext)) {
                    c3 = '4';
                }
                else if (removeNext == 'X' && !arrayContains(ColognePhonetic.CKQ, c)) {
                    c3 = '4';
                    cologneInputBuffer.addLeft('S');
                }
                else if (removeNext == 'S' || removeNext == 'Z') {
                    c3 = '8';
                }
                else if (removeNext == 'C') {
                    if (c2 == '/') {
                        if (arrayContains(ColognePhonetic.AHKLOQRUX, nextChar)) {
                            c3 = '4';
                        }
                        else {
                            c3 = '8';
                        }
                    }
                    else if (arrayContains(ColognePhonetic.SZ, c) || !arrayContains(ColognePhonetic.AHOUKQX, nextChar)) {
                        c3 = '8';
                    }
                    else {
                        c3 = '4';
                    }
                }
                else if (arrayContains(ColognePhonetic.TDX, removeNext)) {
                    c3 = '8';
                }
                else if (removeNext == 'R') {
                    c3 = '7';
                }
                else if (removeNext == 'L') {
                    c3 = '5';
                }
                else if (removeNext == 'M' || removeNext == 'N') {
                    c3 = '6';
                }
                else {
                    c3 = removeNext;
                }
                if (c3 != '-' && ((c2 != c3 && (c3 != '0' || c2 == '/')) || c3 < '0' || c3 > '8')) {
                    cologneOutputBuffer.addRight(c3);
                }
                c = removeNext;
                c2 = c3;
            }
        }
        return cologneOutputBuffer.toString();
    }
    
    @Override
    public Object encode(final Object o) {
        if (!(o instanceof String)) {
            throw new EncoderException("This method's parameter was expected to be of the type " + String.class.getName() + ". But actually it was of the type " + o.getClass().getName() + ".");
        }
        return this.encode((String)o);
    }
    
    @Override
    public String encode(final String s) {
        return this.colognePhonetic(s);
    }
    
    public boolean isEncodeEqual(final String s, final String s2) {
        return this.colognePhonetic(s).equals(this.colognePhonetic(s2));
    }
    
    private char[] preprocess(final String s) {
        final char[] charArray = s.toUpperCase(Locale.GERMAN).toCharArray();
        for (int i = 0; i < charArray.length; ++i) {
            switch (charArray[i]) {
                case '\u00c4': {
                    charArray[i] = 'A';
                    break;
                }
                case '\u00dc': {
                    charArray[i] = 'U';
                    break;
                }
                case '\u00d6': {
                    charArray[i] = 'O';
                    break;
                }
            }
        }
        return charArray;
    }
    
    static {
        AEIJOUY = new char[] { 'A', 'E', 'I', 'J', 'O', 'U', 'Y' };
        SCZ = new char[] { 'S', 'C', 'Z' };
        WFPV = new char[] { 'W', 'F', 'P', 'V' };
        GKQ = new char[] { 'G', 'K', 'Q' };
        CKQ = new char[] { 'C', 'K', 'Q' };
        AHKLOQRUX = new char[] { 'A', 'H', 'K', 'L', 'O', 'Q', 'R', 'U', 'X' };
        SZ = new char[] { 'S', 'Z' };
        AHOUKQX = new char[] { 'A', 'H', 'O', 'U', 'K', 'Q', 'X' };
        TDX = new char[] { 'T', 'D', 'X' };
    }
    
    private abstract class CologneBuffer
    {
        protected final char[] data;
        protected int length;
        
        public CologneBuffer(final char[] data) {
            this.length = 0;
            this.data = data;
            this.length = data.length;
        }
        
        public CologneBuffer(final int n) {
            this.length = 0;
            this.data = new char[n];
            this.length = 0;
        }
        
        protected abstract char[] copyData(final int p0, final int p1);
        
        public int length() {
            return this.length;
        }
        
        @Override
        public String toString() {
            return new String(this.copyData(0, this.length));
        }
    }
    
    private class CologneOutputBuffer extends CologneBuffer
    {
        public CologneOutputBuffer(final int n) {
            super(n);
        }
        
        public void addRight(final char c) {
            this.data[this.length] = c;
            ++this.length;
        }
        
        @Override
        protected char[] copyData(final int n, final int n2) {
            final char[] array = new char[n2];
            System.arraycopy(this.data, n, array, 0, n2);
            return array;
        }
    }
    
    private class CologneInputBuffer extends CologneBuffer
    {
        public CologneInputBuffer(final char[] array) {
            super(array);
        }
        
        public void addLeft(final char c) {
            ++this.length;
            this.data[this.getNextPos()] = c;
        }
        
        @Override
        protected char[] copyData(final int n, final int n2) {
            final char[] array = new char[n2];
            System.arraycopy(this.data, this.data.length - this.length + n, array, 0, n2);
            return array;
        }
        
        public char getNextChar() {
            return this.data[this.getNextPos()];
        }
        
        protected int getNextPos() {
            return this.data.length - this.length;
        }
        
        public char removeNext() {
            final char nextChar = this.getNextChar();
            --this.length;
            return nextChar;
        }
    }
}
