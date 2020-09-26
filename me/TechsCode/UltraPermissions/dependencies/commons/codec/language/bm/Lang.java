

package me.TechsCode.EnderPermissions.dependencies.commons.codec.language.bm;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Collections;
import java.io.InputStream;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Lang
{
    private static final Map<NameType, Lang> Langs;
    private static final String LANGUAGE_RULES_RN = "me/TechsCode/EnderPermissions/dependencies/commons/codec/language/bm/%s_lang.txt";
    private final Languages languages;
    private final List<LangRule> rules;
    
    public static Lang instance(final NameType nameType) {
        return Lang.Langs.get(nameType);
    }
    
    public static Lang loadFromResource(final String s, final Languages languages) {
        final ArrayList<LangRule> list = new ArrayList<LangRule>();
        final InputStream resourceAsStream = Lang.class.getClassLoader().getResourceAsStream(s);
        if (resourceAsStream == null) {
            throw new IllegalStateException("Unable to resolve required resource:org/apache/commons/codec/language/bm/%s_lang.txt");
        }
        try (final Scanner scanner = new Scanner(resourceAsStream, "UTF-8")) {
            int n = 0;
            while (scanner.hasNextLine()) {
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
                    final String[] split = trim.split("\\s+");
                    if (split.length != 3) {
                        throw new IllegalArgumentException("Malformed line '" + str + "' in language resource '" + s + "'");
                    }
                    list.add(new LangRule(Pattern.compile(split[0]), (Set)new HashSet(Arrays.asList(split[1].split("\\+"))), split[2].equals("true")));
                }
            }
        }
        return new Lang(list, languages);
    }
    
    private Lang(final List<LangRule> list, final Languages languages) {
        this.rules = Collections.unmodifiableList((List<? extends LangRule>)list);
        this.languages = languages;
    }
    
    public String guessLanguage(final String s) {
        final Languages.LanguageSet guessLanguages = this.guessLanguages(s);
        return guessLanguages.isSingleton() ? guessLanguages.getAny() : "any";
    }
    
    public Languages.LanguageSet guessLanguages(final String s) {
        final String lowerCase = s.toLowerCase(Locale.ENGLISH);
        final HashSet<String> set = new HashSet<String>(this.languages.getLanguages());
        for (final LangRule langRule : this.rules) {
            if (langRule.matches(lowerCase)) {
                if (langRule.acceptOnMatch) {
                    set.retainAll(langRule.languages);
                }
                else {
                    set.removeAll(langRule.languages);
                }
            }
        }
        final Languages.LanguageSet from = Languages.LanguageSet.from(set);
        return from.equals(Languages.NO_LANGUAGES) ? Languages.ANY_LANGUAGE : from;
    }
    
    static {
        Langs = new EnumMap<NameType, Lang>(NameType.class);
        for (final NameType nameType : NameType.values()) {
            Lang.Langs.put(nameType, loadFromResource(String.format("me/TechsCode/EnderPermissions/dependencies/commons/codec/language/bm/%s_lang.txt", nameType.getName()), Languages.getInstance(nameType)));
        }
    }
    
    private static final class LangRule
    {
        private final boolean acceptOnMatch;
        private final Set<String> languages;
        private final Pattern pattern;
        
        private LangRule(final Pattern pattern, final Set<String> languages, final boolean acceptOnMatch) {
            this.pattern = pattern;
            this.languages = languages;
            this.acceptOnMatch = acceptOnMatch;
        }
        
        public boolean matches(final String input) {
            return this.pattern.matcher(input).find();
        }
    }
}
