

package me.TechsCode.EnderPermissions.dependencies.commons.codec.language;

import me.TechsCode.EnderPermissions.dependencies.commons.codec.EncoderException;
import java.util.regex.Pattern;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringEncoder;

public class Nysiis implements StringEncoder
{
    private static final char[] CHARS_A;
    private static final char[] CHARS_AF;
    private static final char[] CHARS_C;
    private static final char[] CHARS_FF;
    private static final char[] CHARS_G;
    private static final char[] CHARS_N;
    private static final char[] CHARS_NN;
    private static final char[] CHARS_S;
    private static final char[] CHARS_SSS;
    private static final Pattern PAT_MAC;
    private static final Pattern PAT_KN;
    private static final Pattern PAT_K;
    private static final Pattern PAT_PH_PF;
    private static final Pattern PAT_SCH;
    private static final Pattern PAT_EE_IE;
    private static final Pattern PAT_DT_ETC;
    private static final char SPACE = ' ';
    private static final int TRUE_LENGTH = 6;
    private final boolean strict;
    
    private static boolean isVowel(final char c) {
        return c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }
    
    private static char[] transcodeRemaining(final char c, final char c2, final char c3, final char c4) {
        if (c2 == 'E' && c3 == 'V') {
            return Nysiis.CHARS_AF;
        }
        if (isVowel(c2)) {
            return Nysiis.CHARS_A;
        }
        if (c2 == 'Q') {
            return Nysiis.CHARS_G;
        }
        if (c2 == 'Z') {
            return Nysiis.CHARS_S;
        }
        if (c2 == 'M') {
            return Nysiis.CHARS_N;
        }
        if (c2 == 'K') {
            if (c3 == 'N') {
                return Nysiis.CHARS_NN;
            }
            return Nysiis.CHARS_C;
        }
        else {
            if (c2 == 'S' && c3 == 'C' && c4 == 'H') {
                return Nysiis.CHARS_SSS;
            }
            if (c2 == 'P' && c3 == 'H') {
                return Nysiis.CHARS_FF;
            }
            if (c2 == 'H' && (!isVowel(c) || !isVowel(c3))) {
                return new char[] { c };
            }
            if (c2 == 'W' && isVowel(c)) {
                return new char[] { c };
            }
            return new char[] { c2 };
        }
    }
    
    public Nysiis() {
        this(true);
    }
    
    public Nysiis(final boolean strict) {
        this.strict = strict;
    }
    
    @Override
    public Object encode(final Object o) {
        if (!(o instanceof String)) {
            throw new EncoderException("Parameter supplied to Nysiis encode is not of type java.lang.String");
        }
        return this.nysiis((String)o);
    }
    
    @Override
    public String encode(final String s) {
        return this.nysiis(s);
    }
    
    public boolean isStrict() {
        return this.strict;
    }
    
    public String nysiis(String input) {
        if (input == null) {
            return null;
        }
        input = SoundexUtils.clean(input);
        if (input.length() == 0) {
            return input;
        }
        input = Nysiis.PAT_MAC.matcher(input).replaceFirst("MCC");
        input = Nysiis.PAT_KN.matcher(input).replaceFirst("NN");
        input = Nysiis.PAT_K.matcher(input).replaceFirst("C");
        input = Nysiis.PAT_PH_PF.matcher(input).replaceFirst("FF");
        input = Nysiis.PAT_SCH.matcher(input).replaceFirst("SSS");
        input = Nysiis.PAT_EE_IE.matcher(input).replaceFirst("Y");
        input = Nysiis.PAT_DT_ETC.matcher(input).replaceFirst("D");
        final StringBuilder sb = new StringBuilder(input.length());
        sb.append(input.charAt(0));
        final char[] charArray = input.toCharArray();
        for (int length = charArray.length, i = 1; i < length; ++i) {
            final char[] transcodeRemaining = transcodeRemaining(charArray[i - 1], charArray[i], (i < length - 1) ? charArray[i + 1] : ' ', (i < length - 2) ? charArray[i + 2] : ' ');
            System.arraycopy(transcodeRemaining, 0, charArray, i, transcodeRemaining.length);
            if (charArray[i] != charArray[i - 1]) {
                sb.append(charArray[i]);
            }
        }
        if (sb.length() > 1) {
            char c = sb.charAt(sb.length() - 1);
            if (c == 'S') {
                sb.deleteCharAt(sb.length() - 1);
                c = sb.charAt(sb.length() - 1);
            }
            if (sb.length() > 2 && sb.charAt(sb.length() - 2) == 'A' && c == 'Y') {
                sb.deleteCharAt(sb.length() - 2);
            }
            if (c == 'A') {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        final String string = sb.toString();
        return this.isStrict() ? string.substring(0, Math.min(6, string.length())) : string;
    }
    
    static {
        CHARS_A = new char[] { 'A' };
        CHARS_AF = new char[] { 'A', 'F' };
        CHARS_C = new char[] { 'C' };
        CHARS_FF = new char[] { 'F', 'F' };
        CHARS_G = new char[] { 'G' };
        CHARS_N = new char[] { 'N' };
        CHARS_NN = new char[] { 'N', 'N' };
        CHARS_S = new char[] { 'S' };
        CHARS_SSS = new char[] { 'S', 'S', 'S' };
        PAT_MAC = Pattern.compile("^MAC");
        PAT_KN = Pattern.compile("^KN");
        PAT_K = Pattern.compile("^K");
        PAT_PH_PF = Pattern.compile("^(PH|PF)");
        PAT_SCH = Pattern.compile("^SCH");
        PAT_EE_IE = Pattern.compile("(EE|IE)$");
        PAT_DT_ETC = Pattern.compile("(DT|RT|RD|NT|ND)$");
    }
}
