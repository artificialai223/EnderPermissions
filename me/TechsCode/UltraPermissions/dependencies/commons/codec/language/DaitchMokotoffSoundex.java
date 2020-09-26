

package me.TechsCode.EnderPermissions.dependencies.commons.codec.language;

import java.util.Arrays;
import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.EncoderException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.StringEncoder;

public class DaitchMokotoffSoundex implements StringEncoder
{
    private static final String COMMENT = "//";
    private static final String DOUBLE_QUOTE = "\"";
    private static final String MULTILINE_COMMENT_END = "*/";
    private static final String MULTILINE_COMMENT_START = "/*";
    private static final String RESOURCE_FILE = "me/TechsCode/EnderPermissions/dependencies/commons/codec/language/dmrules.txt";
    private static final int MAX_LENGTH = 6;
    private static final Map<Character, List<Rule>> RULES;
    private static final Map<Character, Character> FOLDINGS;
    private final boolean folding;
    
    private static void parseRules(final Scanner scanner, final String s, final Map<Character, List<Rule>> map, final Map<Character, Character> map2) {
        int i = 0;
        int n = 0;
        while (scanner.hasNextLine()) {
            ++i;
            String s2;
            final String str = s2 = scanner.nextLine();
            if (n != 0) {
                if (!s2.endsWith("*/")) {
                    continue;
                }
                n = 0;
            }
            else if (s2.startsWith("/*")) {
                n = 1;
            }
            else {
                final int index = s2.indexOf("//");
                if (index >= 0) {
                    s2 = s2.substring(0, index);
                }
                final String trim = s2.trim();
                if (trim.length() == 0) {
                    continue;
                }
                if (trim.contains("=")) {
                    final String[] split = trim.split("=");
                    if (split.length != 2) {
                        throw new IllegalArgumentException("Malformed folding statement split into " + split.length + " parts: " + str + " in " + s);
                    }
                    final String s3 = split[0];
                    final String s4 = split[1];
                    if (s3.length() != 1 || s4.length() != 1) {
                        throw new IllegalArgumentException("Malformed folding statement - patterns are not single characters: " + str + " in " + s);
                    }
                    map2.put(s3.charAt(0), s4.charAt(0));
                }
                else {
                    final String[] split2 = trim.split("\\s+");
                    if (split2.length != 4) {
                        throw new IllegalArgumentException("Malformed rule statement split into " + split2.length + " parts: " + str + " in " + s);
                    }
                    try {
                        final Rule rule = new Rule(stripQuotes(split2[0]), stripQuotes(split2[1]), stripQuotes(split2[2]), stripQuotes(split2[3]));
                        final char char1 = rule.pattern.charAt(0);
                        List<Rule> list = map.get(char1);
                        if (list == null) {
                            list = new ArrayList<Rule>();
                            map.put(char1, list);
                        }
                        list.add(rule);
                    }
                    catch (IllegalArgumentException cause) {
                        throw new IllegalStateException("Problem parsing line '" + i + "' in " + s, cause);
                    }
                }
            }
        }
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
    
    public DaitchMokotoffSoundex() {
        this(true);
    }
    
    public DaitchMokotoffSoundex(final boolean folding) {
        this.folding = folding;
    }
    
    private String cleanup(final String s) {
        final StringBuilder sb = new StringBuilder();
        for (final char c : s.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                char c2 = Character.toLowerCase(c);
                if (this.folding && DaitchMokotoffSoundex.FOLDINGS.containsKey(c2)) {
                    c2 = DaitchMokotoffSoundex.FOLDINGS.get(c2);
                }
                sb.append(c2);
            }
        }
        return sb.toString();
    }
    
    @Override
    public Object encode(final Object o) {
        if (!(o instanceof String)) {
            throw new EncoderException("Parameter supplied to DaitchMokotoffSoundex encode is not of type java.lang.String");
        }
        return this.encode((String)o);
    }
    
    @Override
    public String encode(final String s) {
        if (s == null) {
            return null;
        }
        return this.soundex(s, false)[0];
    }
    
    public String soundex(final String s) {
        final String[] soundex = this.soundex(s, true);
        final StringBuilder sb = new StringBuilder();
        int n = 0;
        final String[] array = soundex;
        for (int length = array.length, i = 0; i < length; ++i) {
            sb.append(array[i]);
            if (++n < soundex.length) {
                sb.append('|');
            }
        }
        return sb.toString();
    }
    
    private String[] soundex(final String s, final boolean b) {
        if (s == null) {
            return null;
        }
        final String cleanup = this.cleanup(s);
        final LinkedHashSet<Branch> set = new LinkedHashSet<Branch>();
        set.add(new Branch());
        int n = 0;
        for (int i = 0; i < cleanup.length(); ++i) {
            final char char1 = cleanup.charAt(i);
            if (!Character.isWhitespace(char1)) {
                final String substring = cleanup.substring(i);
                final List<Rule> list = DaitchMokotoffSoundex.RULES.get(char1);
                if (list != null) {
                    final List<Branch> list2 = b ? new ArrayList<Branch>() : Collections.EMPTY_LIST;
                    for (final Rule rule : list) {
                        if (rule.matches(substring)) {
                            if (b) {
                                list2.clear();
                            }
                            final String[] replacements = rule.getReplacements(substring, n == 0);
                            final boolean b2 = replacements.length > 1 && b;
                            for (final Branch branch : set) {
                                for (final String s2 : replacements) {
                                    final Branch branch2 = b2 ? branch.createBranch() : branch;
                                    branch2.processNextReplacement(s2, (n == 109 && char1 == 'n') || (n == 110 && char1 == 'm'));
                                    if (!b) {
                                        break;
                                    }
                                    list2.add((Object)branch2);
                                }
                            }
                            if (b) {
                                set.clear();
                                set.addAll((Collection<?>)list2);
                            }
                            i += rule.getPatternLength() - 1;
                            break;
                        }
                    }
                    n = char1;
                }
            }
        }
        final String[] array2 = new String[set.size()];
        int n2 = 0;
        for (final Branch branch3 : set) {
            branch3.finish();
            array2[n2++] = branch3.toString();
        }
        return array2;
    }
    
    static {
        RULES = new HashMap<Character, List<Rule>>();
        FOLDINGS = new HashMap<Character, Character>();
        final InputStream resourceAsStream = DaitchMokotoffSoundex.class.getClassLoader().getResourceAsStream("me/TechsCode/EnderPermissions/dependencies/commons/codec/language/dmrules.txt");
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("Unable to load resource: org/apache/commons/codec/language/dmrules.txt");
        }
        try (final Scanner scanner = new Scanner(resourceAsStream, "UTF-8")) {
            parseRules(scanner, "me/TechsCode/EnderPermissions/dependencies/commons/codec/language/dmrules.txt", DaitchMokotoffSoundex.RULES, DaitchMokotoffSoundex.FOLDINGS);
        }
        final Iterator<Map.Entry<Character, List<Rule>>> iterator = DaitchMokotoffSoundex.RULES.entrySet().iterator();
        while (iterator.hasNext()) {
            Collections.sort(iterator.next().getValue(), new Comparator<Rule>() {
                @Override
                public int compare(final Rule rule, final Rule rule2) {
                    return rule2.getPatternLength() - rule.getPatternLength();
                }
            });
        }
    }
    
    private static final class Branch
    {
        private final StringBuilder builder;
        private String cachedString;
        private String lastReplacement;
        
        private Branch() {
            this.builder = new StringBuilder();
            this.lastReplacement = null;
            this.cachedString = null;
        }
        
        public Branch createBranch() {
            final Branch branch = new Branch();
            branch.builder.append(this.toString());
            branch.lastReplacement = this.lastReplacement;
            return branch;
        }
        
        @Override
        public boolean equals(final Object o) {
            return this == o || (o instanceof Branch && this.toString().equals(((Branch)o).toString()));
        }
        
        public void finish() {
            while (this.builder.length() < 6) {
                this.builder.append('0');
                this.cachedString = null;
            }
        }
        
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
        
        public void processNextReplacement(final String lastReplacement, final boolean b) {
            if ((this.lastReplacement == null || this.lastReplacement.endsWith(lastReplacement) || b) && this.builder.length() < 6) {
                this.builder.append(lastReplacement);
                if (this.builder.length() > 6) {
                    this.builder.delete(6, this.builder.length());
                }
                this.cachedString = null;
            }
            this.lastReplacement = lastReplacement;
        }
        
        @Override
        public String toString() {
            if (this.cachedString == null) {
                this.cachedString = this.builder.toString();
            }
            return this.cachedString;
        }
    }
    
    private static final class Rule
    {
        private final String pattern;
        private final String[] replacementAtStart;
        private final String[] replacementBeforeVowel;
        private final String[] replacementDefault;
        
        protected Rule(final String pattern, final String s, final String s2, final String s3) {
            this.pattern = pattern;
            this.replacementAtStart = s.split("\\|");
            this.replacementBeforeVowel = s2.split("\\|");
            this.replacementDefault = s3.split("\\|");
        }
        
        public int getPatternLength() {
            return this.pattern.length();
        }
        
        public String[] getReplacements(final String s, final boolean b) {
            if (b) {
                return this.replacementAtStart;
            }
            final int patternLength = this.getPatternLength();
            if (patternLength < s.length() && this.isVowel(s.charAt(patternLength))) {
                return this.replacementBeforeVowel;
            }
            return this.replacementDefault;
        }
        
        private boolean isVowel(final char c) {
            return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
        }
        
        public boolean matches(final String s) {
            return s.startsWith(this.pattern);
        }
        
        @Override
        public String toString() {
            return String.format("%s=(%s,%s,%s)", this.pattern, Arrays.asList(this.replacementAtStart), Arrays.asList(this.replacementBeforeVowel), Arrays.asList(this.replacementDefault));
        }
    }
}
