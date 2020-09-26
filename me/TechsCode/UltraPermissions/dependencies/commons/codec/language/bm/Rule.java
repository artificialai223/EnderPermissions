

package me.TechsCode.EnderPermissions.dependencies.commons.codec.language.bm;

import java.util.Comparator;
import java.util.Collections;
import java.util.EnumMap;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.io.InputStream;
import java.util.Scanner;
import java.util.List;
import java.util.Map;

public class Rule
{
    public static final RPattern ALL_STRINGS_RMATCHER;
    public static final String ALL = "ALL";
    private static final String DOUBLE_QUOTE = "\"";
    private static final String HASH_INCLUDE = "#include";
    private static final Map<NameType, Map<RuleType, Map<String, Map<String, List<Rule>>>>> RULES;
    private final RPattern lContext;
    private final String pattern;
    private final PhonemeExpr phoneme;
    private final RPattern rContext;
    
    private static boolean contains(final CharSequence charSequence, final char c) {
        for (int i = 0; i < charSequence.length(); ++i) {
            if (charSequence.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }
    
    private static String createResourceName(final NameType nameType, final RuleType ruleType, final String s) {
        return String.format("me/TechsCode/EnderPermissions/dependencies/commons/codec/language/bm/%s_%s_%s.txt", nameType.getName(), ruleType.getName(), s);
    }
    
    private static Scanner createScanner(final NameType nameType, final RuleType ruleType, final String s) {
        final String resourceName = createResourceName(nameType, ruleType, s);
        final InputStream resourceAsStream = Languages.class.getClassLoader().getResourceAsStream(resourceName);
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("Unable to load resource: " + resourceName);
        }
        return new Scanner(resourceAsStream, "UTF-8");
    }
    
    private static Scanner createScanner(final String s) {
        final String format = String.format("me/TechsCode/EnderPermissions/dependencies/commons/codec/language/bm/%s.txt", s);
        final InputStream resourceAsStream = Languages.class.getClassLoader().getResourceAsStream(format);
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("Unable to load resource: " + format);
        }
        return new Scanner(resourceAsStream, "UTF-8");
    }
    
    private static boolean endsWith(final CharSequence charSequence, final CharSequence charSequence2) {
        if (charSequence2.length() > charSequence.length()) {
            return false;
        }
        int n = charSequence.length() - 1;
        for (int i = charSequence2.length() - 1; i >= 0; --i) {
            if (charSequence.charAt(n) != charSequence2.charAt(i)) {
                return false;
            }
            --n;
        }
        return true;
    }
    
    public static List<Rule> getInstance(final NameType nameType, final RuleType ruleType, final Languages.LanguageSet set) {
        final Map<String, List<Rule>> instanceMap = getInstanceMap(nameType, ruleType, set);
        final ArrayList<Object> list = (ArrayList<Object>)new ArrayList<Rule>();
        final Iterator<List<Rule>> iterator = instanceMap.values().iterator();
        while (iterator.hasNext()) {
            list.addAll(iterator.next());
        }
        return (List<Rule>)list;
    }
    
    public static List<Rule> getInstance(final NameType nameType, final RuleType ruleType, final String s) {
        return getInstance(nameType, ruleType, Languages.LanguageSet.from(new HashSet<String>(Arrays.asList(s))));
    }
    
    public static Map<String, List<Rule>> getInstanceMap(final NameType nameType, final RuleType ruleType, final Languages.LanguageSet set) {
        return set.isSingleton() ? getInstanceMap(nameType, ruleType, set.getAny()) : getInstanceMap(nameType, ruleType, "any");
    }
    
    public static Map<String, List<Rule>> getInstanceMap(final NameType nameType, final RuleType ruleType, final String s) {
        final Map<String, List<Rule>> map = Rule.RULES.get(nameType).get(ruleType).get(s);
        if (map == null) {
            throw new IllegalArgumentException(String.format("No rules found for %s, %s, %s.", nameType.getName(), ruleType.getName(), s));
        }
        return map;
    }
    
    private static Phoneme parsePhoneme(final String s) {
        final int index = s.indexOf("[");
        if (index < 0) {
            return new Phoneme(s, Languages.ANY_LANGUAGE);
        }
        if (!s.endsWith("]")) {
            throw new IllegalArgumentException("Phoneme expression contains a '[' but does not end in ']'");
        }
        return new Phoneme(s.substring(0, index), Languages.LanguageSet.from(new HashSet<String>(Arrays.asList(s.substring(index + 1, s.length() - 1).split("[+]")))));
    }
    
    private static PhonemeExpr parsePhonemeExpr(final String s) {
        if (!s.startsWith("(")) {
            return parsePhoneme(s);
        }
        if (!s.endsWith(")")) {
            throw new IllegalArgumentException("Phoneme starts with '(' so must end with ')'");
        }
        final ArrayList<Phoneme> list = new ArrayList<Phoneme>();
        final String substring = s.substring(1, s.length() - 1);
        final String[] split = substring.split("[|]");
        for (int length = split.length, i = 0; i < length; ++i) {
            list.add(parsePhoneme(split[i]));
        }
        if (substring.startsWith("|") || substring.endsWith("|")) {
            list.add(new Phoneme("", Languages.ANY_LANGUAGE));
        }
        return new PhonemeList(list);
    }
    
    private static Map<String, List<Rule>> parseRules(final Scanner scanner, final String s) {
        final HashMap<String, List<Rule>> hashMap = new HashMap<String, List<Rule>>();
        int i = 0;
        int n = 0;
        while (scanner.hasNextLine()) {
            ++i;
            String s3;
            final String s2 = s3 = scanner.nextLine();
            if (n != 0) {
                if (!s3.endsWith("*/")) {
                    continue;
                }
                n = 0;
            }
            else if (s3.startsWith("/*")) {
                n = 1;
            }
            else {
                final int index = s3.indexOf("//");
                if (index >= 0) {
                    s3 = s3.substring(0, index);
                }
                final String trim = s3.trim();
                if (trim.length() == 0) {
                    continue;
                }
                if (trim.startsWith("#include")) {
                    final String trim2 = trim.substring("#include".length()).trim();
                    if (trim2.contains(" ")) {
                        throw new IllegalArgumentException("Malformed import statement '" + s2 + "' in " + s);
                    }
                    try (final Scanner scanner2 = createScanner(trim2)) {
                        hashMap.putAll((Map<?, ?>)parseRules(scanner2, s + "->" + trim2));
                    }
                }
                else {
                    final String[] split = trim.split("\\s+");
                    if (split.length != 4) {
                        throw new IllegalArgumentException("Malformed rule statement split into " + split.length + " parts: " + s2 + " in " + s);
                    }
                    try {
                        final String stripQuotes = stripQuotes(split[0]);
                        final String stripQuotes2 = stripQuotes(split[1]);
                        final String stripQuotes3 = stripQuotes(split[2]);
                        final Rule rule = new Rule(stripQuotes, stripQuotes2, stripQuotes3, parsePhonemeExpr(stripQuotes(split[3]))) {
                            private final int myLine = i;
                            private final String loc = s;
                            
                            @Override
                            public String toString() {
                                final StringBuilder sb = new StringBuilder();
                                sb.append("Rule");
                                sb.append("{line=").append(this.myLine);
                                sb.append(", loc='").append(this.loc).append('\'');
                                sb.append(", pat='").append(stripQuotes).append('\'');
                                sb.append(", lcon='").append(stripQuotes2).append('\'');
                                sb.append(", rcon='").append(stripQuotes3).append('\'');
                                sb.append('}');
                                return sb.toString();
                            }
                        };
                        final String substring = rule.pattern.substring(0, 1);
                        List<Rule> list = hashMap.get(substring);
                        if (list == null) {
                            list = new ArrayList<Rule>();
                            hashMap.put(substring, list);
                        }
                        list.add(rule);
                    }
                    catch (IllegalArgumentException cause) {
                        throw new IllegalStateException("Problem parsing line '" + i + "' in " + s, cause);
                    }
                }
            }
        }
        return hashMap;
    }
    
    private static RPattern pattern(final String s) {
        final int startsWith = s.startsWith("^") ? 1 : 0;
        final boolean endsWith = s.endsWith("$");
        final String substring = s.substring(startsWith, endsWith ? (s.length() - 1) : s.length());
        if (!substring.contains("[")) {
            if (startsWith && endsWith) {
                if (substring.length() == 0) {
                    return new RPattern() {
                        @Override
                        public boolean isMatch(final CharSequence charSequence) {
                            return charSequence.length() == 0;
                        }
                    };
                }
                return new RPattern() {
                    @Override
                    public boolean isMatch(final CharSequence charSequence) {
                        return charSequence.equals(substring);
                    }
                };
            }
            else {
                if ((startsWith || endsWith) && substring.length() == 0) {
                    return Rule.ALL_STRINGS_RMATCHER;
                }
                if (startsWith != 0) {
                    return new RPattern() {
                        @Override
                        public boolean isMatch(final CharSequence charSequence) {
                            return startsWith(charSequence, substring);
                        }
                    };
                }
                if (endsWith) {
                    return new RPattern() {
                        @Override
                        public boolean isMatch(final CharSequence charSequence) {
                            return endsWith(charSequence, substring);
                        }
                    };
                }
            }
        }
        else {
            final boolean startsWith2 = substring.startsWith("[");
            final boolean endsWith2 = substring.endsWith("]");
            if (startsWith2 && endsWith2) {
                String s2 = substring.substring(1, substring.length() - 1);
                if (!s2.contains("[")) {
                    final boolean startsWith3 = s2.startsWith("^");
                    if (startsWith3) {
                        s2 = s2.substring(1);
                    }
                    final String s3 = s2;
                    final boolean b = !startsWith3;
                    if (startsWith && endsWith) {
                        return new RPattern() {
                            @Override
                            public boolean isMatch(final CharSequence charSequence) {
                                return charSequence.length() == 1 && contains(s3, charSequence.charAt(0)) == b;
                            }
                        };
                    }
                    if (startsWith != 0) {
                        return new RPattern() {
                            @Override
                            public boolean isMatch(final CharSequence charSequence) {
                                return charSequence.length() > 0 && contains(s3, charSequence.charAt(0)) == b;
                            }
                        };
                    }
                    if (endsWith) {
                        return new RPattern() {
                            @Override
                            public boolean isMatch(final CharSequence charSequence) {
                                return charSequence.length() > 0 && contains(s3, charSequence.charAt(charSequence.length() - 1)) == b;
                            }
                        };
                    }
                }
            }
        }
        return new RPattern() {
            Pattern pattern = Pattern.compile(s);
            
            @Override
            public boolean isMatch(final CharSequence input) {
                return this.pattern.matcher(input).find();
            }
        };
    }
    
    private static boolean startsWith(final CharSequence charSequence, final CharSequence charSequence2) {
        if (charSequence2.length() > charSequence.length()) {
            return false;
        }
        for (int i = 0; i < charSequence2.length(); ++i) {
            if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                return false;
            }
        }
        return true;
    }
    
    private static String stripQuotes(String s) {
        if (s.startsWith("\"")) {
            s = s.substring(1);
        }
        if (s.endsWith("\"")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
    
    public Rule(final String pattern, final String str, final String str2, final PhonemeExpr phoneme) {
        this.pattern = pattern;
        this.lContext = pattern(str + "$");
        this.rContext = pattern("^" + str2);
        this.phoneme = phoneme;
    }
    
    public RPattern getLContext() {
        return this.lContext;
    }
    
    public String getPattern() {
        return this.pattern;
    }
    
    public PhonemeExpr getPhoneme() {
        return this.phoneme;
    }
    
    public RPattern getRContext() {
        return this.rContext;
    }
    
    public boolean patternAndContextMatches(final CharSequence charSequence, final int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Can not match pattern at negative indexes");
        }
        final int n2 = n + this.pattern.length();
        return n2 <= charSequence.length() && charSequence.subSequence(n, n2).equals(this.pattern) && this.rContext.isMatch(charSequence.subSequence(n2, charSequence.length())) && this.lContext.isMatch(charSequence.subSequence(0, n));
    }
    
    static {
        ALL_STRINGS_RMATCHER = new RPattern() {
            @Override
            public boolean isMatch(final CharSequence charSequence) {
                return true;
            }
        };
        RULES = new EnumMap<NameType, Map<RuleType, Map<String, Map<String, List<Rule>>>>>(NameType.class);
        for (final NameType nameType : NameType.values()) {
            final EnumMap<RuleType, Map<String, Map<String, List<Rule>>>> m = new EnumMap<RuleType, Map<String, Map<String, List<Rule>>>>(RuleType.class);
            for (final RuleType ruleType : RuleType.values()) {
                final HashMap<String, Map<String, List<Rule>>> k = new HashMap<String, Map<String, List<Rule>>>();
                for (final String s : Languages.getInstance(nameType).getLanguages()) {
                    try (final Scanner scanner = createScanner(nameType, ruleType, s)) {
                        k.put(s, parseRules(scanner, createResourceName(nameType, ruleType, s)));
                    }
                    catch (IllegalStateException cause) {
                        throw new IllegalStateException("Problem processing " + createResourceName(nameType, ruleType, s), cause);
                    }
                }
                if (!ruleType.equals(RuleType.RULES)) {
                    try (final Scanner scanner2 = createScanner(nameType, ruleType, "common")) {
                        k.put("common", parseRules(scanner2, createResourceName(nameType, ruleType, "common")));
                    }
                }
                m.put(ruleType, Collections.unmodifiableMap((Map<?, ?>)k));
            }
            Rule.RULES.put(nameType, (Map<RuleType, Map<String, Map<String, List<Rule>>>>)Collections.unmodifiableMap((Map<?, ?>)m));
        }
    }
    
    public static final class Phoneme implements PhonemeExpr
    {
        public static final Comparator<Phoneme> COMPARATOR;
        private final StringBuilder phonemeText;
        private final Languages.LanguageSet languages;
        
        public Phoneme(final CharSequence seq, final Languages.LanguageSet languages) {
            this.phonemeText = new StringBuilder(seq);
            this.languages = languages;
        }
        
        public Phoneme(final Phoneme phoneme, final Phoneme phoneme2) {
            this(phoneme.phonemeText, phoneme.languages);
            this.phonemeText.append((CharSequence)phoneme2.phonemeText);
        }
        
        public Phoneme(final Phoneme phoneme, final Phoneme phoneme2, final Languages.LanguageSet set) {
            this(phoneme.phonemeText, set);
            this.phonemeText.append((CharSequence)phoneme2.phonemeText);
        }
        
        public Phoneme append(final CharSequence s) {
            this.phonemeText.append(s);
            return this;
        }
        
        public Languages.LanguageSet getLanguages() {
            return this.languages;
        }
        
        @Override
        public Iterable<Phoneme> getPhonemes() {
            return Collections.singleton(this);
        }
        
        public CharSequence getPhonemeText() {
            return this.phonemeText;
        }
        
        @Deprecated
        public Phoneme join(final Phoneme phoneme) {
            return new Phoneme(this.phonemeText.toString() + phoneme.phonemeText.toString(), this.languages.restrictTo(phoneme.languages));
        }
        
        public Phoneme mergeWithLanguage(final Languages.LanguageSet set) {
            return new Phoneme(this.phonemeText.toString(), this.languages.merge(set));
        }
        
        @Override
        public String toString() {
            return this.phonemeText.toString() + "[" + this.languages + "]";
        }
        
        static {
            COMPARATOR = new Comparator<Phoneme>() {
                @Override
                public int compare(final Phoneme phoneme, final Phoneme phoneme2) {
                    for (int i = 0; i < phoneme.phonemeText.length(); ++i) {
                        if (i >= phoneme2.phonemeText.length()) {
                            return 1;
                        }
                        final int n = phoneme.phonemeText.charAt(i) - phoneme2.phonemeText.charAt(i);
                        if (n != 0) {
                            return n;
                        }
                    }
                    if (phoneme.phonemeText.length() < phoneme2.phonemeText.length()) {
                        return -1;
                    }
                    return 0;
                }
            };
        }
    }
    
    public static final class PhonemeList implements PhonemeExpr
    {
        private final List<Phoneme> phonemes;
        
        public PhonemeList(final List<Phoneme> phonemes) {
            this.phonemes = phonemes;
        }
        
        @Override
        public List<Phoneme> getPhonemes() {
            return this.phonemes;
        }
    }
    
    public interface RPattern
    {
        boolean isMatch(final CharSequence p0);
    }
    
    public interface PhonemeExpr
    {
        Iterable<Phoneme> getPhonemes();
    }
}
