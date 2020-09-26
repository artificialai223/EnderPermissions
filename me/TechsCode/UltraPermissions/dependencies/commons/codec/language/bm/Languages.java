

package me.TechsCode.EnderPermissions.dependencies.commons.codec.language.bm;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.EnumMap;
import java.io.InputStream;
import java.util.Collections;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

public class Languages
{
    public static final String ANY = "any";
    private static final Map<NameType, Languages> LANGUAGES;
    private final Set<String> languages;
    public static final LanguageSet NO_LANGUAGES;
    public static final LanguageSet ANY_LANGUAGE;
    
    public static Languages getInstance(final NameType nameType) {
        return Languages.LANGUAGES.get(nameType);
    }
    
    public static Languages getInstance(final String s) {
        final HashSet<String> s2 = new HashSet<String>();
        final InputStream resourceAsStream = Languages.class.getClassLoader().getResourceAsStream(s);
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("Unable to resolve required resource: " + s);
        }
        try (final Scanner scanner = new Scanner(resourceAsStream, "UTF-8")) {
            int n = 0;
            while (scanner.hasNextLine()) {
                final String trim = scanner.nextLine().trim();
                if (n != 0) {
                    if (!trim.endsWith("*/")) {
                        continue;
                    }
                    n = 0;
                }
                else if (trim.startsWith("/*")) {
                    n = 1;
                }
                else {
                    if (trim.length() <= 0) {
                        continue;
                    }
                    s2.add(trim);
                }
            }
        }
        return new Languages((Set<String>)Collections.unmodifiableSet((Set<?>)s2));
    }
    
    private static String langResourceName(final NameType nameType) {
        return String.format("me/TechsCode/EnderPermissions/dependencies/commons/codec/language/bm/%s_languages.txt", nameType.getName());
    }
    
    private Languages(final Set<String> languages) {
        this.languages = languages;
    }
    
    public Set<String> getLanguages() {
        return this.languages;
    }
    
    static {
        LANGUAGES = new EnumMap<NameType, Languages>(NameType.class);
        for (final NameType nameType : NameType.values()) {
            Languages.LANGUAGES.put(nameType, getInstance(langResourceName(nameType)));
        }
        NO_LANGUAGES = new LanguageSet() {
            @Override
            public boolean contains(final String s) {
                return false;
            }
            
            @Override
            public String getAny() {
                throw new NoSuchElementException("Can't fetch any language from the empty language set.");
            }
            
            @Override
            public boolean isEmpty() {
                return true;
            }
            
            @Override
            public boolean isSingleton() {
                return false;
            }
            
            @Override
            public LanguageSet restrictTo(final LanguageSet set) {
                return this;
            }
            
            public LanguageSet merge(final LanguageSet set) {
                return set;
            }
            
            @Override
            public String toString() {
                return "NO_LANGUAGES";
            }
        };
        ANY_LANGUAGE = new LanguageSet() {
            @Override
            public boolean contains(final String s) {
                return true;
            }
            
            @Override
            public String getAny() {
                throw new NoSuchElementException("Can't fetch any language from the any language set.");
            }
            
            @Override
            public boolean isEmpty() {
                return false;
            }
            
            @Override
            public boolean isSingleton() {
                return false;
            }
            
            @Override
            public LanguageSet restrictTo(final LanguageSet set) {
                return set;
            }
            
            public LanguageSet merge(final LanguageSet set) {
                return set;
            }
            
            @Override
            public String toString() {
                return "ANY_LANGUAGE";
            }
        };
    }
    
    public abstract static class LanguageSet
    {
        public static LanguageSet from(final Set<String> set) {
            return set.isEmpty() ? Languages.NO_LANGUAGES : new SomeLanguages((Set)set);
        }
        
        public abstract boolean contains(final String p0);
        
        public abstract String getAny();
        
        public abstract boolean isEmpty();
        
        public abstract boolean isSingleton();
        
        public abstract LanguageSet restrictTo(final LanguageSet p0);
        
        abstract LanguageSet merge(final LanguageSet p0);
    }
    
    public static final class SomeLanguages extends LanguageSet
    {
        private final Set<String> languages;
        
        private SomeLanguages(final Set<String> s) {
            this.languages = Collections.unmodifiableSet((Set<? extends String>)s);
        }
        
        @Override
        public boolean contains(final String s) {
            return this.languages.contains(s);
        }
        
        @Override
        public String getAny() {
            return this.languages.iterator().next();
        }
        
        public Set<String> getLanguages() {
            return this.languages;
        }
        
        @Override
        public boolean isEmpty() {
            return this.languages.isEmpty();
        }
        
        @Override
        public boolean isSingleton() {
            return this.languages.size() == 1;
        }
        
        @Override
        public LanguageSet restrictTo(final LanguageSet set) {
            if (set == Languages.NO_LANGUAGES) {
                return set;
            }
            if (set == Languages.ANY_LANGUAGE) {
                return this;
            }
            final SomeLanguages someLanguages = (SomeLanguages)set;
            final HashSet set2 = new HashSet<String>(Math.min(this.languages.size(), someLanguages.languages.size()));
            for (final String s : this.languages) {
                if (someLanguages.languages.contains(s)) {
                    set2.add(s);
                }
            }
            return LanguageSet.from((Set<String>)set2);
        }
        
        public LanguageSet merge(final LanguageSet set) {
            if (set == Languages.NO_LANGUAGES) {
                return this;
            }
            if (set == Languages.ANY_LANGUAGE) {
                return set;
            }
            final SomeLanguages someLanguages = (SomeLanguages)set;
            final HashSet<String> set2 = new HashSet<String>(this.languages);
            final Iterator<String> iterator = someLanguages.languages.iterator();
            while (iterator.hasNext()) {
                set2.add(iterator.next());
            }
            return LanguageSet.from(set2);
        }
        
        @Override
        public String toString() {
            return "Languages(" + this.languages.toString() + ")";
        }
    }
}
