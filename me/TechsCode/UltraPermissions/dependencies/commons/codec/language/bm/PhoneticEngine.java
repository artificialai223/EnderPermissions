

package me.TechsCode.EnderPermissions.dependencies.commons.codec.language.bm;

import java.util.LinkedHashSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.EnumMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.List;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;

public class PhoneticEngine
{
    private static final Map<NameType, Set<String>> NAME_PREFIXES;
    private static final int DEFAULT_MAX_PHONEMES = 20;
    private final Lang lang;
    private final NameType nameType;
    private final RuleType ruleType;
    private final boolean concat;
    private final int maxPhonemes;
    
    private static String join(final Iterable<String> iterable, final String str) {
        final StringBuilder sb = new StringBuilder();
        final Iterator<String> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            sb.append(iterator.next());
        }
        while (iterator.hasNext()) {
            sb.append(str).append(iterator.next());
        }
        return sb.toString();
    }
    
    public PhoneticEngine(final NameType nameType, final RuleType ruleType, final boolean b) {
        this(nameType, ruleType, b, 20);
    }
    
    public PhoneticEngine(final NameType nameType, final RuleType ruleType, final boolean concat, final int maxPhonemes) {
        if (ruleType == RuleType.RULES) {
            throw new IllegalArgumentException("ruleType must not be " + RuleType.RULES);
        }
        this.nameType = nameType;
        this.ruleType = ruleType;
        this.concat = concat;
        this.lang = Lang.instance(nameType);
        this.maxPhonemes = maxPhonemes;
    }
    
    private PhonemeBuilder applyFinalRules(final PhonemeBuilder phonemeBuilder, final Map<String, List<Rule>> map) {
        if (map == null) {
            throw new NullPointerException("finalRules can not be null");
        }
        if (map.isEmpty()) {
            return phonemeBuilder;
        }
        final TreeMap<Object, Rule.Phoneme> treeMap = new TreeMap<Object, Rule.Phoneme>((Comparator<? super Object>)Rule.Phoneme.COMPARATOR);
        for (final Rule.Phoneme phoneme : phonemeBuilder.getPhonemes()) {
            PhonemeBuilder phonemeBuilder2 = PhonemeBuilder.empty(phoneme.getLanguages());
            final String string = phoneme.getPhonemeText().toString();
            RulesApplication invoke;
            for (int i = 0; i < string.length(); i = invoke.getI()) {
                invoke = new RulesApplication(map, string, phonemeBuilder2, i, this.maxPhonemes).invoke();
                final boolean found = invoke.isFound();
                phonemeBuilder2 = invoke.getPhonemeBuilder();
                if (!found) {
                    phonemeBuilder2.append(string.subSequence(i, i + 1));
                }
            }
            for (final Rule.Phoneme phoneme2 : phonemeBuilder2.getPhonemes()) {
                if (treeMap.containsKey(phoneme2)) {
                    final Rule.Phoneme mergeWithLanguage = treeMap.remove(phoneme2).mergeWithLanguage(phoneme2.getLanguages());
                    treeMap.put(mergeWithLanguage, mergeWithLanguage);
                }
                else {
                    treeMap.put(phoneme2, phoneme2);
                }
            }
        }
        return new PhonemeBuilder((Set)treeMap.keySet());
    }
    
    public String encode(final String s) {
        return this.encode(s, this.lang.guessLanguages(s));
    }
    
    public String encode(String s, final Languages.LanguageSet set) {
        final Map<String, List<Rule>> instanceMap = Rule.getInstanceMap(this.nameType, RuleType.RULES, set);
        final Map<String, List<Rule>> instanceMap2 = Rule.getInstanceMap(this.nameType, this.ruleType, "common");
        final Map<String, List<Rule>> instanceMap3 = Rule.getInstanceMap(this.nameType, this.ruleType, set);
        s = s.toLowerCase(Locale.ENGLISH).replace('-', ' ').trim();
        if (this.nameType == NameType.GENERIC) {
            if (s.length() >= 2 && s.substring(0, 2).equals("d'")) {
                final String substring = s.substring(2);
                return "(" + this.encode(substring) + ")-(" + this.encode("d" + substring) + ")";
            }
            for (final String s2 : PhoneticEngine.NAME_PREFIXES.get(this.nameType)) {
                if (s.startsWith(s2 + " ")) {
                    final String substring2 = s.substring(s2.length() + 1);
                    return "(" + this.encode(substring2) + ")-(" + this.encode(s2 + substring2) + ")";
                }
            }
        }
        final List<String> list = Arrays.asList(s.split("\\s+"));
        final ArrayList<String> list2 = new ArrayList<String>();
        switch (this.nameType) {
            case SEPHARDIC: {
                final Iterator<String> iterator2 = list.iterator();
                while (iterator2.hasNext()) {
                    final String[] split = iterator2.next().split("'");
                    list2.add(split[split.length - 1]);
                }
                list2.removeAll(PhoneticEngine.NAME_PREFIXES.get(this.nameType));
                break;
            }
            case ASHKENAZI: {
                list2.addAll((Collection<?>)list);
                list2.removeAll(PhoneticEngine.NAME_PREFIXES.get(this.nameType));
                break;
            }
            case GENERIC: {
                list2.addAll((Collection<?>)list);
                break;
            }
            default: {
                throw new IllegalStateException("Unreachable case: " + this.nameType);
            }
        }
        if (this.concat) {
            s = join(list2, " ");
        }
        else {
            if (list2.size() != 1) {
                final StringBuilder sb = new StringBuilder();
                final Iterator<Object> iterator3 = list2.iterator();
                while (iterator3.hasNext()) {
                    sb.append("-").append(this.encode(iterator3.next()));
                }
                return sb.substring(1);
            }
            s = list.iterator().next();
        }
        PhonemeBuilder phonemeBuilder = PhonemeBuilder.empty(set);
        RulesApplication invoke;
        for (int i = 0; i < s.length(); i = invoke.getI(), phonemeBuilder = invoke.getPhonemeBuilder()) {
            invoke = new RulesApplication(instanceMap, s, phonemeBuilder, i, this.maxPhonemes).invoke();
        }
        return this.applyFinalRules(this.applyFinalRules(phonemeBuilder, instanceMap2), instanceMap3).makeString();
    }
    
    public Lang getLang() {
        return this.lang;
    }
    
    public NameType getNameType() {
        return this.nameType;
    }
    
    public RuleType getRuleType() {
        return this.ruleType;
    }
    
    public boolean isConcat() {
        return this.concat;
    }
    
    public int getMaxPhonemes() {
        return this.maxPhonemes;
    }
    
    static {
        (NAME_PREFIXES = new EnumMap<NameType, Set<String>>(NameType.class)).put(NameType.ASHKENAZI, Collections.unmodifiableSet((Set<? extends String>)new HashSet<String>(Arrays.asList("bar", "ben", "da", "de", "van", "von"))));
        PhoneticEngine.NAME_PREFIXES.put(NameType.SEPHARDIC, Collections.unmodifiableSet((Set<? extends String>)new HashSet<String>(Arrays.asList("al", "el", "da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von"))));
        PhoneticEngine.NAME_PREFIXES.put(NameType.GENERIC, Collections.unmodifiableSet((Set<? extends String>)new HashSet<String>(Arrays.asList("da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von"))));
    }
    
    static final class PhonemeBuilder
    {
        private final Set<Rule.Phoneme> phonemes;
        
        public static PhonemeBuilder empty(final Languages.LanguageSet set) {
            return new PhonemeBuilder(new Rule.Phoneme("", set));
        }
        
        private PhonemeBuilder(final Rule.Phoneme phoneme) {
            (this.phonemes = new LinkedHashSet<Rule.Phoneme>()).add(phoneme);
        }
        
        private PhonemeBuilder(final Set<Rule.Phoneme> phonemes) {
            this.phonemes = phonemes;
        }
        
        public void append(final CharSequence charSequence) {
            final Iterator<Rule.Phoneme> iterator = this.phonemes.iterator();
            while (iterator.hasNext()) {
                iterator.next().append(charSequence);
            }
        }
        
        public void apply(final Rule.PhonemeExpr phonemeExpr, final int initialCapacity) {
            final LinkedHashSet<Rule.Phoneme> set = new LinkedHashSet<Rule.Phoneme>(initialCapacity);
        Label_0153:
            for (final Rule.Phoneme phoneme : this.phonemes) {
                for (final Rule.Phoneme phoneme2 : phonemeExpr.getPhonemes()) {
                    final Languages.LanguageSet restrictTo = phoneme.getLanguages().restrictTo(phoneme2.getLanguages());
                    if (!restrictTo.isEmpty()) {
                        final Rule.Phoneme phoneme3 = new Rule.Phoneme(phoneme, phoneme2, restrictTo);
                        if (set.size() >= initialCapacity) {
                            continue;
                        }
                        set.add(phoneme3);
                        if (set.size() >= initialCapacity) {
                            break Label_0153;
                        }
                        continue;
                    }
                }
            }
            this.phonemes.clear();
            this.phonemes.addAll(set);
        }
        
        public Set<Rule.Phoneme> getPhonemes() {
            return this.phonemes;
        }
        
        public String makeString() {
            final StringBuilder sb = new StringBuilder();
            for (final Rule.Phoneme phoneme : this.phonemes) {
                if (sb.length() > 0) {
                    sb.append("|");
                }
                sb.append(phoneme.getPhonemeText());
            }
            return sb.toString();
        }
    }
    
    private static final class RulesApplication
    {
        private final Map<String, List<Rule>> finalRules;
        private final CharSequence input;
        private final PhonemeBuilder phonemeBuilder;
        private int i;
        private final int maxPhonemes;
        private boolean found;
        
        public RulesApplication(final Map<String, List<Rule>> finalRules, final CharSequence input, final PhonemeBuilder phonemeBuilder, final int i, final int maxPhonemes) {
            if (finalRules == null) {
                throw new NullPointerException("The finalRules argument must not be null");
            }
            this.finalRules = finalRules;
            this.phonemeBuilder = phonemeBuilder;
            this.input = input;
            this.i = i;
            this.maxPhonemes = maxPhonemes;
        }
        
        public int getI() {
            return this.i;
        }
        
        public PhonemeBuilder getPhonemeBuilder() {
            return this.phonemeBuilder;
        }
        
        public RulesApplication invoke() {
            this.found = false;
            int length = 1;
            final List<Rule> list = this.finalRules.get(this.input.subSequence(this.i, this.i + length));
            if (list != null) {
                for (final Rule rule : list) {
                    length = rule.getPattern().length();
                    if (rule.patternAndContextMatches(this.input, this.i)) {
                        this.phonemeBuilder.apply(rule.getPhoneme(), this.maxPhonemes);
                        this.found = true;
                        break;
                    }
                }
            }
            if (!this.found) {
                length = 1;
            }
            this.i += length;
            return this;
        }
        
        public boolean isFound() {
            return this.found;
        }
    }
}
