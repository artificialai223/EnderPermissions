

package me.TechsCode.EnderPermissions.dependencies.commons.codec.language;

import me.TechsCode.EnderPermissions.dependencies.commons.codec.EncoderException;
import java.util.Locale;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringEncoder;

public class MatchRatingApproachEncoder implements StringEncoder
{
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int SIX = 6;
    private static final int SEVEN = 7;
    private static final int ELEVEN = 11;
    private static final int TWELVE = 12;
    private static final String PLAIN_ASCII = "AaEeIiOoUuAaEeIiOoUuYyAaEeIiOoUuYyAaOoNnAaEeIiOoUuYyAaCcOoUu";
    private static final String UNICODE = "\u00c0\u00e0\u00c8\u00e8\u00cc\u00ec\u00d2\u00f2\u00d9\u00f9\u00c1\u00e1\u00c9\u00e9\u00cd\u00ed\u00d3\u00f3\u00da\u00fa\u00dd\u00fd\u00c2\u00e2\u00ca\u00ea\u00ce\u00ee\u00d4\u00f4\u00db\u00fb\u0176\u0177\u00c3\u00e3\u00d5\u00f5\u00d1\u00f1\u00c4\u00e4\u00cb\u00eb\u00cf\u00ef\u00d6\u00f6\u00dc\u00fc\u0178\u00ff\u00c5\u00e5\u00c7\u00e7\u0150\u0151\u0170\u0171";
    private static final String[] DOUBLE_CONSONANT;
    
    String cleanName(final String s) {
        String s2 = s.toUpperCase(Locale.ENGLISH);
        final String[] array = { "\\-", "[&]", "\\'", "\\.", "[\\,]" };
        for (int length = array.length, i = 0; i < length; ++i) {
            s2 = s2.replaceAll(array[i], "");
        }
        return this.removeAccents(s2).replaceAll("\\s+", "");
    }
    
    @Override
    public final Object encode(final Object o) {
        if (!(o instanceof String)) {
            throw new EncoderException("Parameter supplied to Match Rating Approach encoder is not of type java.lang.String");
        }
        return this.encode((String)o);
    }
    
    @Override
    public final String encode(String s) {
        if (s == null || "".equalsIgnoreCase(s) || " ".equalsIgnoreCase(s) || s.length() == 1) {
            return "";
        }
        s = this.cleanName(s);
        s = this.removeVowels(s);
        s = this.removeDoubleConsonants(s);
        s = this.getFirst3Last3(s);
        return s;
    }
    
    String getFirst3Last3(final String s) {
        final int length = s.length();
        if (length > 6) {
            return s.substring(0, 3) + s.substring(length - 3, length);
        }
        return s;
    }
    
    int getMinRating(final int n) {
        int n2;
        if (n <= 4) {
            n2 = 5;
        }
        else if (n <= 7) {
            n2 = 4;
        }
        else if (n <= 11) {
            n2 = 3;
        }
        else if (n == 12) {
            n2 = 2;
        }
        else {
            n2 = 1;
        }
        return n2;
    }
    
    public boolean isEncodeEquals(String s, String anotherString) {
        if (s == null || "".equalsIgnoreCase(s) || " ".equalsIgnoreCase(s)) {
            return false;
        }
        if (anotherString == null || "".equalsIgnoreCase(anotherString) || " ".equalsIgnoreCase(anotherString)) {
            return false;
        }
        if (s.length() == 1 || anotherString.length() == 1) {
            return false;
        }
        if (s.equalsIgnoreCase(anotherString)) {
            return true;
        }
        s = this.cleanName(s);
        anotherString = this.cleanName(anotherString);
        s = this.removeVowels(s);
        anotherString = this.removeVowels(anotherString);
        s = this.removeDoubleConsonants(s);
        anotherString = this.removeDoubleConsonants(anotherString);
        s = this.getFirst3Last3(s);
        anotherString = this.getFirst3Last3(anotherString);
        return Math.abs(s.length() - anotherString.length()) < 3 && this.leftToRightThenRightToLeftProcessing(s, anotherString) >= this.getMinRating(Math.abs(s.length() + anotherString.length()));
    }
    
    boolean isVowel(final String s) {
        return s.equalsIgnoreCase("E") || s.equalsIgnoreCase("A") || s.equalsIgnoreCase("O") || s.equalsIgnoreCase("I") || s.equalsIgnoreCase("U");
    }
    
    int leftToRightThenRightToLeftProcessing(final String s, final String s2) {
        final char[] charArray = s.toCharArray();
        final char[] charArray2 = s2.toCharArray();
        final int n = s.length() - 1;
        for (int n2 = s2.length() - 1, n3 = 0; n3 < charArray.length && n3 <= n2; ++n3) {
            final String substring = s.substring(n3, n3 + 1);
            final String substring2 = s.substring(n - n3, n - n3 + 1);
            final String substring3 = s2.substring(n3, n3 + 1);
            final String substring4 = s2.substring(n2 - n3, n2 - n3 + 1);
            if (substring.equals(substring3)) {
                charArray2[n3] = (charArray[n3] = ' ');
            }
            if (substring2.equals(substring4)) {
                charArray2[n2 - n3] = (charArray[n - n3] = ' ');
            }
        }
        final String replaceAll = new String(charArray).replaceAll("\\s+", "");
        final String replaceAll2 = new String(charArray2).replaceAll("\\s+", "");
        if (replaceAll.length() > replaceAll2.length()) {
            return Math.abs(6 - replaceAll.length());
        }
        return Math.abs(6 - replaceAll2.length());
    }
    
    String removeAccents(final String s) {
        if (s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for (int length = s.length(), i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            final int index = "\u00c0\u00e0\u00c8\u00e8\u00cc\u00ec\u00d2\u00f2\u00d9\u00f9\u00c1\u00e1\u00c9\u00e9\u00cd\u00ed\u00d3\u00f3\u00da\u00fa\u00dd\u00fd\u00c2\u00e2\u00ca\u00ea\u00ce\u00ee\u00d4\u00f4\u00db\u00fb\u0176\u0177\u00c3\u00e3\u00d5\u00f5\u00d1\u00f1\u00c4\u00e4\u00cb\u00eb\u00cf\u00ef\u00d6\u00f6\u00dc\u00fc\u0178\u00ff\u00c5\u00e5\u00c7\u00e7\u0150\u0151\u0170\u0171".indexOf(char1);
            if (index > -1) {
                sb.append("AaEeIiOoUuAaEeIiOoUuYyAaEeIiOoUuYyAaOoNnAaEeIiOoUuYyAaCcOoUu".charAt(index));
            }
            else {
                sb.append(char1);
            }
        }
        return sb.toString();
    }
    
    String removeDoubleConsonants(final String s) {
        String s2 = s.toUpperCase(Locale.ENGLISH);
        for (final String s3 : MatchRatingApproachEncoder.DOUBLE_CONSONANT) {
            if (s2.contains(s3)) {
                s2 = s2.replace(s3, s3.substring(0, 1));
            }
        }
        return s2;
    }
    
    String removeVowels(String str) {
        final String substring = str.substring(0, 1);
        str = str.replaceAll("A", "");
        str = str.replaceAll("E", "");
        str = str.replaceAll("I", "");
        str = str.replaceAll("O", "");
        str = str.replaceAll("U", "");
        str = str.replaceAll("\\s{2,}\\b", " ");
        if (this.isVowel(substring)) {
            return substring + str;
        }
        return str;
    }
    
    static {
        DOUBLE_CONSONANT = new String[] { "BB", "CC", "DD", "FF", "GG", "HH", "JJ", "KK", "LL", "MM", "NN", "PP", "QQ", "RR", "SS", "TT", "VV", "WW", "XX", "YY", "ZZ" };
    }
}
