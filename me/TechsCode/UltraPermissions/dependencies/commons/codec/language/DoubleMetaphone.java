

package me.TechsCode.EnderPermissions.dependencies.commons.codec.language;

import java.util.Locale;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.StringUtils;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.EncoderException;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringEncoder;

public class DoubleMetaphone implements StringEncoder
{
    private static final String VOWELS = "AEIOUY";
    private static final String[] SILENT_START;
    private static final String[] L_R_N_M_B_H_F_V_W_SPACE;
    private static final String[] ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER;
    private static final String[] L_T_K_S_N_M_B_Z;
    private int maxCodeLen;
    
    public DoubleMetaphone() {
        this.maxCodeLen = 4;
    }
    
    public String doubleMetaphone(final String s) {
        return this.doubleMetaphone(s, false);
    }
    
    public String doubleMetaphone(String cleanInput, final boolean b) {
        cleanInput = this.cleanInput(cleanInput);
        if (cleanInput == null) {
            return null;
        }
        final boolean slavoGermanic = this.isSlavoGermanic(cleanInput);
        int index = this.isSilentStart(cleanInput) ? 1 : 0;
        final DoubleMetaphoneResult doubleMetaphoneResult = new DoubleMetaphoneResult(this.getMaxCodeLen());
        while (!doubleMetaphoneResult.isComplete() && index <= cleanInput.length() - 1) {
            switch (cleanInput.charAt(index)) {
                case 'A':
                case 'E':
                case 'I':
                case 'O':
                case 'U':
                case 'Y': {
                    index = this.handleAEIOUY(doubleMetaphoneResult, index);
                    continue;
                }
                case 'B': {
                    doubleMetaphoneResult.append('P');
                    index = ((this.charAt(cleanInput, index + 1) == 'B') ? (index + 2) : (index + 1));
                    continue;
                }
                case '\u00c7': {
                    doubleMetaphoneResult.append('S');
                    ++index;
                    continue;
                }
                case 'C': {
                    index = this.handleC(cleanInput, doubleMetaphoneResult, index);
                    continue;
                }
                case 'D': {
                    index = this.handleD(cleanInput, doubleMetaphoneResult, index);
                    continue;
                }
                case 'F': {
                    doubleMetaphoneResult.append('F');
                    index = ((this.charAt(cleanInput, index + 1) == 'F') ? (index + 2) : (index + 1));
                    continue;
                }
                case 'G': {
                    index = this.handleG(cleanInput, doubleMetaphoneResult, index, slavoGermanic);
                    continue;
                }
                case 'H': {
                    index = this.handleH(cleanInput, doubleMetaphoneResult, index);
                    continue;
                }
                case 'J': {
                    index = this.handleJ(cleanInput, doubleMetaphoneResult, index, slavoGermanic);
                    continue;
                }
                case 'K': {
                    doubleMetaphoneResult.append('K');
                    index = ((this.charAt(cleanInput, index + 1) == 'K') ? (index + 2) : (index + 1));
                    continue;
                }
                case 'L': {
                    index = this.handleL(cleanInput, doubleMetaphoneResult, index);
                    continue;
                }
                case 'M': {
                    doubleMetaphoneResult.append('M');
                    index = (this.conditionM0(cleanInput, index) ? (index + 2) : (index + 1));
                    continue;
                }
                case 'N': {
                    doubleMetaphoneResult.append('N');
                    index = ((this.charAt(cleanInput, index + 1) == 'N') ? (index + 2) : (index + 1));
                    continue;
                }
                case '\u00d1': {
                    doubleMetaphoneResult.append('N');
                    ++index;
                    continue;
                }
                case 'P': {
                    index = this.handleP(cleanInput, doubleMetaphoneResult, index);
                    continue;
                }
                case 'Q': {
                    doubleMetaphoneResult.append('K');
                    index = ((this.charAt(cleanInput, index + 1) == 'Q') ? (index + 2) : (index + 1));
                    continue;
                }
                case 'R': {
                    index = this.handleR(cleanInput, doubleMetaphoneResult, index, slavoGermanic);
                    continue;
                }
                case 'S': {
                    index = this.handleS(cleanInput, doubleMetaphoneResult, index, slavoGermanic);
                    continue;
                }
                case 'T': {
                    index = this.handleT(cleanInput, doubleMetaphoneResult, index);
                    continue;
                }
                case 'V': {
                    doubleMetaphoneResult.append('F');
                    index = ((this.charAt(cleanInput, index + 1) == 'V') ? (index + 2) : (index + 1));
                    continue;
                }
                case 'W': {
                    index = this.handleW(cleanInput, doubleMetaphoneResult, index);
                    continue;
                }
                case 'X': {
                    index = this.handleX(cleanInput, doubleMetaphoneResult, index);
                    continue;
                }
                case 'Z': {
                    index = this.handleZ(cleanInput, doubleMetaphoneResult, index, slavoGermanic);
                    continue;
                }
                default: {
                    ++index;
                    continue;
                }
            }
        }
        return b ? doubleMetaphoneResult.getAlternate() : doubleMetaphoneResult.getPrimary();
    }
    
    @Override
    public Object encode(final Object o) {
        if (!(o instanceof String)) {
            throw new EncoderException("DoubleMetaphone encode parameter is not of type String");
        }
        return this.doubleMetaphone((String)o);
    }
    
    @Override
    public String encode(final String s) {
        return this.doubleMetaphone(s);
    }
    
    public boolean isDoubleMetaphoneEqual(final String s, final String s2) {
        return this.isDoubleMetaphoneEqual(s, s2, false);
    }
    
    public boolean isDoubleMetaphoneEqual(final String s, final String s2, final boolean b) {
        return StringUtils.equals(this.doubleMetaphone(s, b), this.doubleMetaphone(s2, b));
    }
    
    public int getMaxCodeLen() {
        return this.maxCodeLen;
    }
    
    public void setMaxCodeLen(final int maxCodeLen) {
        this.maxCodeLen = maxCodeLen;
    }
    
    private int handleAEIOUY(final DoubleMetaphoneResult doubleMetaphoneResult, final int n) {
        if (n == 0) {
            doubleMetaphoneResult.append('A');
        }
        return n + 1;
    }
    
    private int handleC(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int handleCH) {
        if (this.conditionC0(s, handleCH)) {
            doubleMetaphoneResult.append('K');
            handleCH += 2;
        }
        else if (handleCH == 0 && contains(s, handleCH, 6, "CAESAR")) {
            doubleMetaphoneResult.append('S');
            handleCH += 2;
        }
        else if (contains(s, handleCH, 2, "CH")) {
            handleCH = this.handleCH(s, doubleMetaphoneResult, handleCH);
        }
        else if (contains(s, handleCH, 2, "CZ") && !contains(s, handleCH - 2, 4, "WICZ")) {
            doubleMetaphoneResult.append('S', 'X');
            handleCH += 2;
        }
        else if (contains(s, handleCH + 1, 3, "CIA")) {
            doubleMetaphoneResult.append('X');
            handleCH += 3;
        }
        else {
            if (contains(s, handleCH, 2, "CC") && (handleCH != 1 || this.charAt(s, 0) != 'M')) {
                return this.handleCC(s, doubleMetaphoneResult, handleCH);
            }
            if (contains(s, handleCH, 2, "CK", "CG", "CQ")) {
                doubleMetaphoneResult.append('K');
                handleCH += 2;
            }
            else if (contains(s, handleCH, 2, "CI", "CE", "CY")) {
                if (contains(s, handleCH, 3, "CIO", "CIE", "CIA")) {
                    doubleMetaphoneResult.append('S', 'X');
                }
                else {
                    doubleMetaphoneResult.append('S');
                }
                handleCH += 2;
            }
            else {
                doubleMetaphoneResult.append('K');
                if (contains(s, handleCH + 1, 2, " C", " Q", " G")) {
                    handleCH += 3;
                }
                else if (contains(s, handleCH + 1, 1, "C", "K", "Q") && !contains(s, handleCH + 1, 2, "CE", "CI")) {
                    handleCH += 2;
                }
                else {
                    ++handleCH;
                }
            }
        }
        return handleCH;
    }
    
    private int handleCC(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (contains(s, n + 2, 1, "I", "E", "H") && !contains(s, n + 2, 2, "HU")) {
            if ((n == 1 && this.charAt(s, n - 1) == 'A') || contains(s, n - 1, 5, "UCCEE", "UCCES")) {
                doubleMetaphoneResult.append("KS");
            }
            else {
                doubleMetaphoneResult.append('X');
            }
            n += 3;
        }
        else {
            doubleMetaphoneResult.append('K');
            n += 2;
        }
        return n;
    }
    
    private int handleCH(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, final int n) {
        if (n > 0 && contains(s, n, 4, "CHAE")) {
            doubleMetaphoneResult.append('K', 'X');
            return n + 2;
        }
        if (this.conditionCH0(s, n)) {
            doubleMetaphoneResult.append('K');
            return n + 2;
        }
        if (this.conditionCH1(s, n)) {
            doubleMetaphoneResult.append('K');
            return n + 2;
        }
        if (n > 0) {
            if (contains(s, 0, 2, "MC")) {
                doubleMetaphoneResult.append('K');
            }
            else {
                doubleMetaphoneResult.append('X', 'K');
            }
        }
        else {
            doubleMetaphoneResult.append('X');
        }
        return n + 2;
    }
    
    private int handleD(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (contains(s, n, 2, "DG")) {
            if (contains(s, n + 2, 1, "I", "E", "Y")) {
                doubleMetaphoneResult.append('J');
                n += 3;
            }
            else {
                doubleMetaphoneResult.append("TK");
                n += 2;
            }
        }
        else if (contains(s, n, 2, "DT", "DD")) {
            doubleMetaphoneResult.append('T');
            n += 2;
        }
        else {
            doubleMetaphoneResult.append('T');
            ++n;
        }
        return n;
    }
    
    private int handleG(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int handleGH, final boolean b) {
        if (this.charAt(s, handleGH + 1) == 'H') {
            handleGH = this.handleGH(s, doubleMetaphoneResult, handleGH);
        }
        else if (this.charAt(s, handleGH + 1) == 'N') {
            if (handleGH == 1 && this.isVowel(this.charAt(s, 0)) && !b) {
                doubleMetaphoneResult.append("KN", "N");
            }
            else if (!contains(s, handleGH + 2, 2, "EY") && this.charAt(s, handleGH + 1) != 'Y' && !b) {
                doubleMetaphoneResult.append("N", "KN");
            }
            else {
                doubleMetaphoneResult.append("KN");
            }
            handleGH += 2;
        }
        else if (contains(s, handleGH + 1, 2, "LI") && !b) {
            doubleMetaphoneResult.append("KL", "L");
            handleGH += 2;
        }
        else if (handleGH == 0 && (this.charAt(s, handleGH + 1) == 'Y' || contains(s, handleGH + 1, 2, DoubleMetaphone.ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER))) {
            doubleMetaphoneResult.append('K', 'J');
            handleGH += 2;
        }
        else if ((contains(s, handleGH + 1, 2, "ER") || this.charAt(s, handleGH + 1) == 'Y') && !contains(s, 0, 6, "DANGER", "RANGER", "MANGER") && !contains(s, handleGH - 1, 1, "E", "I") && !contains(s, handleGH - 1, 3, "RGY", "OGY")) {
            doubleMetaphoneResult.append('K', 'J');
            handleGH += 2;
        }
        else if (contains(s, handleGH + 1, 1, "E", "I", "Y") || contains(s, handleGH - 1, 4, "AGGI", "OGGI")) {
            if (contains(s, 0, 4, "VAN ", "VON ") || contains(s, 0, 3, "SCH") || contains(s, handleGH + 1, 2, "ET")) {
                doubleMetaphoneResult.append('K');
            }
            else if (contains(s, handleGH + 1, 3, "IER")) {
                doubleMetaphoneResult.append('J');
            }
            else {
                doubleMetaphoneResult.append('J', 'K');
            }
            handleGH += 2;
        }
        else if (this.charAt(s, handleGH + 1) == 'G') {
            handleGH += 2;
            doubleMetaphoneResult.append('K');
        }
        else {
            ++handleGH;
            doubleMetaphoneResult.append('K');
        }
        return handleGH;
    }
    
    private int handleGH(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (n > 0 && !this.isVowel(this.charAt(s, n - 1))) {
            doubleMetaphoneResult.append('K');
            n += 2;
        }
        else if (n == 0) {
            if (this.charAt(s, n + 2) == 'I') {
                doubleMetaphoneResult.append('J');
            }
            else {
                doubleMetaphoneResult.append('K');
            }
            n += 2;
        }
        else if ((n > 1 && contains(s, n - 2, 1, "B", "H", "D")) || (n > 2 && contains(s, n - 3, 1, "B", "H", "D")) || (n > 3 && contains(s, n - 4, 1, "B", "H"))) {
            n += 2;
        }
        else {
            if (n > 2 && this.charAt(s, n - 1) == 'U' && contains(s, n - 3, 1, "C", "G", "L", "R", "T")) {
                doubleMetaphoneResult.append('F');
            }
            else if (n > 0 && this.charAt(s, n - 1) != 'I') {
                doubleMetaphoneResult.append('K');
            }
            n += 2;
        }
        return n;
    }
    
    private int handleH(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if ((n == 0 || this.isVowel(this.charAt(s, n - 1))) && this.isVowel(this.charAt(s, n + 1))) {
            doubleMetaphoneResult.append('H');
            n += 2;
        }
        else {
            ++n;
        }
        return n;
    }
    
    private int handleJ(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n, final boolean b) {
        if (contains(s, n, 4, "JOSE") || contains(s, 0, 4, "SAN ")) {
            if ((n == 0 && this.charAt(s, n + 4) == ' ') || s.length() == 4 || contains(s, 0, 4, "SAN ")) {
                doubleMetaphoneResult.append('H');
            }
            else {
                doubleMetaphoneResult.append('J', 'H');
            }
            ++n;
        }
        else {
            if (n == 0 && !contains(s, n, 4, "JOSE")) {
                doubleMetaphoneResult.append('J', 'A');
            }
            else if (this.isVowel(this.charAt(s, n - 1)) && !b && (this.charAt(s, n + 1) == 'A' || this.charAt(s, n + 1) == 'O')) {
                doubleMetaphoneResult.append('J', 'H');
            }
            else if (n == s.length() - 1) {
                doubleMetaphoneResult.append('J', ' ');
            }
            else if (!contains(s, n + 1, 1, DoubleMetaphone.L_T_K_S_N_M_B_Z) && !contains(s, n - 1, 1, "S", "K", "L")) {
                doubleMetaphoneResult.append('J');
            }
            if (this.charAt(s, n + 1) == 'J') {
                n += 2;
            }
            else {
                ++n;
            }
        }
        return n;
    }
    
    private int handleL(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (this.charAt(s, n + 1) == 'L') {
            if (this.conditionL0(s, n)) {
                doubleMetaphoneResult.appendPrimary('L');
            }
            else {
                doubleMetaphoneResult.append('L');
            }
            n += 2;
        }
        else {
            ++n;
            doubleMetaphoneResult.append('L');
        }
        return n;
    }
    
    private int handleP(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (this.charAt(s, n + 1) == 'H') {
            doubleMetaphoneResult.append('F');
            n += 2;
        }
        else {
            doubleMetaphoneResult.append('P');
            n = (contains(s, n + 1, 1, "P", "B") ? (n + 2) : (n + 1));
        }
        return n;
    }
    
    private int handleR(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, final int n, final boolean b) {
        if (n == s.length() - 1 && !b && contains(s, n - 2, 2, "IE") && !contains(s, n - 4, 2, "ME", "MA")) {
            doubleMetaphoneResult.appendAlternate('R');
        }
        else {
            doubleMetaphoneResult.append('R');
        }
        return (this.charAt(s, n + 1) == 'R') ? (n + 2) : (n + 1);
    }
    
    private int handleS(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int handleSC, final boolean b) {
        if (contains(s, handleSC - 1, 3, "ISL", "YSL")) {
            ++handleSC;
        }
        else if (handleSC == 0 && contains(s, handleSC, 5, "SUGAR")) {
            doubleMetaphoneResult.append('X', 'S');
            ++handleSC;
        }
        else if (contains(s, handleSC, 2, "SH")) {
            if (contains(s, handleSC + 1, 4, "HEIM", "HOEK", "HOLM", "HOLZ")) {
                doubleMetaphoneResult.append('S');
            }
            else {
                doubleMetaphoneResult.append('X');
            }
            handleSC += 2;
        }
        else if (contains(s, handleSC, 3, "SIO", "SIA") || contains(s, handleSC, 4, "SIAN")) {
            if (b) {
                doubleMetaphoneResult.append('S');
            }
            else {
                doubleMetaphoneResult.append('S', 'X');
            }
            handleSC += 3;
        }
        else if ((handleSC == 0 && contains(s, handleSC + 1, 1, "M", "N", "L", "W")) || contains(s, handleSC + 1, 1, "Z")) {
            doubleMetaphoneResult.append('S', 'X');
            handleSC = (contains(s, handleSC + 1, 1, "Z") ? (handleSC + 2) : (handleSC + 1));
        }
        else if (contains(s, handleSC, 2, "SC")) {
            handleSC = this.handleSC(s, doubleMetaphoneResult, handleSC);
        }
        else {
            if (handleSC == s.length() - 1 && contains(s, handleSC - 2, 2, "AI", "OI")) {
                doubleMetaphoneResult.appendAlternate('S');
            }
            else {
                doubleMetaphoneResult.append('S');
            }
            handleSC = (contains(s, handleSC + 1, 1, "S", "Z") ? (handleSC + 2) : (handleSC + 1));
        }
        return handleSC;
    }
    
    private int handleSC(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, final int n) {
        if (this.charAt(s, n + 2) == 'H') {
            if (contains(s, n + 3, 2, "OO", "ER", "EN", "UY", "ED", "EM")) {
                if (contains(s, n + 3, 2, "ER", "EN")) {
                    doubleMetaphoneResult.append("X", "SK");
                }
                else {
                    doubleMetaphoneResult.append("SK");
                }
            }
            else if (n == 0 && !this.isVowel(this.charAt(s, 3)) && this.charAt(s, 3) != 'W') {
                doubleMetaphoneResult.append('X', 'S');
            }
            else {
                doubleMetaphoneResult.append('X');
            }
        }
        else if (contains(s, n + 2, 1, "I", "E", "Y")) {
            doubleMetaphoneResult.append('S');
        }
        else {
            doubleMetaphoneResult.append("SK");
        }
        return n + 3;
    }
    
    private int handleT(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (contains(s, n, 4, "TION")) {
            doubleMetaphoneResult.append('X');
            n += 3;
        }
        else if (contains(s, n, 3, "TIA", "TCH")) {
            doubleMetaphoneResult.append('X');
            n += 3;
        }
        else if (contains(s, n, 2, "TH") || contains(s, n, 3, "TTH")) {
            if (contains(s, n + 2, 2, "OM", "AM") || contains(s, 0, 4, "VAN ", "VON ") || contains(s, 0, 3, "SCH")) {
                doubleMetaphoneResult.append('T');
            }
            else {
                doubleMetaphoneResult.append('0', 'T');
            }
            n += 2;
        }
        else {
            doubleMetaphoneResult.append('T');
            n = (contains(s, n + 1, 1, "T", "D") ? (n + 2) : (n + 1));
        }
        return n;
    }
    
    private int handleW(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (contains(s, n, 2, "WR")) {
            doubleMetaphoneResult.append('R');
            n += 2;
        }
        else if (n == 0 && (this.isVowel(this.charAt(s, n + 1)) || contains(s, n, 2, "WH"))) {
            if (this.isVowel(this.charAt(s, n + 1))) {
                doubleMetaphoneResult.append('A', 'F');
            }
            else {
                doubleMetaphoneResult.append('A');
            }
            ++n;
        }
        else if ((n == s.length() - 1 && this.isVowel(this.charAt(s, n - 1))) || contains(s, n - 1, 5, "EWSKI", "EWSKY", "OWSKI", "OWSKY") || contains(s, 0, 3, "SCH")) {
            doubleMetaphoneResult.appendAlternate('F');
            ++n;
        }
        else if (contains(s, n, 4, "WICZ", "WITZ")) {
            doubleMetaphoneResult.append("TS", "FX");
            n += 4;
        }
        else {
            ++n;
        }
        return n;
    }
    
    private int handleX(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (n == 0) {
            doubleMetaphoneResult.append('S');
            ++n;
        }
        else {
            if (n != s.length() - 1 || (!contains(s, n - 3, 3, "IAU", "EAU") && !contains(s, n - 2, 2, "AU", "OU"))) {
                doubleMetaphoneResult.append("KS");
            }
            n = (contains(s, n + 1, 1, "C", "X") ? (n + 2) : (n + 1));
        }
        return n;
    }
    
    private int handleZ(final String s, final DoubleMetaphoneResult doubleMetaphoneResult, int n, final boolean b) {
        if (this.charAt(s, n + 1) == 'H') {
            doubleMetaphoneResult.append('J');
            n += 2;
        }
        else {
            if (contains(s, n + 1, 2, "ZO", "ZI", "ZA") || (b && n > 0 && this.charAt(s, n - 1) != 'T')) {
                doubleMetaphoneResult.append("S", "TS");
            }
            else {
                doubleMetaphoneResult.append('S');
            }
            n = ((this.charAt(s, n + 1) == 'Z') ? (n + 2) : (n + 1));
        }
        return n;
    }
    
    private boolean conditionC0(final String s, final int n) {
        if (contains(s, n, 4, "CHIA")) {
            return true;
        }
        if (n <= 1) {
            return false;
        }
        if (this.isVowel(this.charAt(s, n - 2))) {
            return false;
        }
        if (!contains(s, n - 1, 3, "ACH")) {
            return false;
        }
        final char char1 = this.charAt(s, n + 2);
        return (char1 != 'I' && char1 != 'E') || contains(s, n - 2, 6, "BACHER", "MACHER");
    }
    
    private boolean conditionCH0(final String s, final int n) {
        return n == 0 && (contains(s, n + 1, 5, "HARAC", "HARIS") || contains(s, n + 1, 3, "HOR", "HYM", "HIA", "HEM")) && !contains(s, 0, 5, "CHORE");
    }
    
    private boolean conditionCH1(final String s, final int n) {
        return contains(s, 0, 4, "VAN ", "VON ") || contains(s, 0, 3, "SCH") || contains(s, n - 2, 6, "ORCHES", "ARCHIT", "ORCHID") || contains(s, n + 2, 1, "T", "S") || ((contains(s, n - 1, 1, "A", "O", "U", "E") || n == 0) && (contains(s, n + 2, 1, DoubleMetaphone.L_R_N_M_B_H_F_V_W_SPACE) || n + 1 == s.length() - 1));
    }
    
    private boolean conditionL0(final String s, final int n) {
        return (n == s.length() - 3 && contains(s, n - 1, 4, "ILLO", "ILLA", "ALLE")) || ((contains(s, s.length() - 2, 2, "AS", "OS") || contains(s, s.length() - 1, 1, "A", "O")) && contains(s, n - 1, 4, "ALLE"));
    }
    
    private boolean conditionM0(final String s, final int n) {
        return this.charAt(s, n + 1) == 'M' || (contains(s, n - 1, 3, "UMB") && (n + 1 == s.length() - 1 || contains(s, n + 2, 2, "ER")));
    }
    
    private boolean isSlavoGermanic(final String s) {
        return s.indexOf(87) > -1 || s.indexOf(75) > -1 || s.indexOf("CZ") > -1 || s.indexOf("WITZ") > -1;
    }
    
    private boolean isVowel(final char ch) {
        return "AEIOUY".indexOf(ch) != -1;
    }
    
    private boolean isSilentStart(final String s) {
        boolean b = false;
        final String[] silent_START = DoubleMetaphone.SILENT_START;
        for (int length = silent_START.length, i = 0; i < length; ++i) {
            if (s.startsWith(silent_START[i])) {
                b = true;
                break;
            }
        }
        return b;
    }
    
    private String cleanInput(String trim) {
        if (trim == null) {
            return null;
        }
        trim = trim.trim();
        if (trim.length() == 0) {
            return null;
        }
        return trim.toUpperCase(Locale.ENGLISH);
    }
    
    protected char charAt(final String s, final int index) {
        if (index < 0 || index >= s.length()) {
            return '\0';
        }
        return s.charAt(index);
    }
    
    protected static boolean contains(final String s, final int beginIndex, final int n, final String... array) {
        boolean b = false;
        if (beginIndex >= 0 && beginIndex + n <= s.length()) {
            final String substring = s.substring(beginIndex, beginIndex + n);
            for (int length = array.length, i = 0; i < length; ++i) {
                if (substring.equals(array[i])) {
                    b = true;
                    break;
                }
            }
        }
        return b;
    }
    
    static {
        SILENT_START = new String[] { "GN", "KN", "PN", "WR", "PS" };
        L_R_N_M_B_H_F_V_W_SPACE = new String[] { "L", "R", "N", "M", "B", "H", "F", "V", "W", " " };
        ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER = new String[] { "ES", "EP", "EB", "EL", "EY", "IB", "IL", "IN", "IE", "EI", "ER" };
        L_T_K_S_N_M_B_Z = new String[] { "L", "T", "K", "S", "N", "M", "B", "Z" };
    }
    
    public class DoubleMetaphoneResult
    {
        private final StringBuilder primary;
        private final StringBuilder alternate;
        private final int maxLength;
        
        public DoubleMetaphoneResult(final int maxLength) {
            this.primary = new StringBuilder(DoubleMetaphone.this.getMaxCodeLen());
            this.alternate = new StringBuilder(DoubleMetaphone.this.getMaxCodeLen());
            this.maxLength = maxLength;
        }
        
        public void append(final char c) {
            this.appendPrimary(c);
            this.appendAlternate(c);
        }
        
        public void append(final char c, final char c2) {
            this.appendPrimary(c);
            this.appendAlternate(c2);
        }
        
        public void appendPrimary(final char c) {
            if (this.primary.length() < this.maxLength) {
                this.primary.append(c);
            }
        }
        
        public void appendAlternate(final char c) {
            if (this.alternate.length() < this.maxLength) {
                this.alternate.append(c);
            }
        }
        
        public void append(final String s) {
            this.appendPrimary(s);
            this.appendAlternate(s);
        }
        
        public void append(final String s, final String s2) {
            this.appendPrimary(s);
            this.appendAlternate(s2);
        }
        
        public void appendPrimary(final String str) {
            final int endIndex = this.maxLength - this.primary.length();
            if (str.length() <= endIndex) {
                this.primary.append(str);
            }
            else {
                this.primary.append(str.substring(0, endIndex));
            }
        }
        
        public void appendAlternate(final String str) {
            final int endIndex = this.maxLength - this.alternate.length();
            if (str.length() <= endIndex) {
                this.alternate.append(str);
            }
            else {
                this.alternate.append(str.substring(0, endIndex));
            }
        }
        
        public String getPrimary() {
            return this.primary.toString();
        }
        
        public String getAlternate() {
            return this.alternate.toString();
        }
        
        public boolean isComplete() {
            return this.primary.length() >= this.maxLength && this.alternate.length() >= this.maxLength;
        }
    }
}
