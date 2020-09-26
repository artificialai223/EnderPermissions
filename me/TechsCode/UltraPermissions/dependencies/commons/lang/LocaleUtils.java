

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import java.util.HashMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.List;

public class LocaleUtils
{
    private static List cAvailableLocaleList;
    private static Set cAvailableLocaleSet;
    private static final Map cLanguagesByCountry;
    private static final Map cCountriesByLanguage;
    
    public static Locale toLocale(final String s) {
        if (s == null) {
            return null;
        }
        final int length = s.length();
        if (length != 2 && length != 5 && length < 7) {
            throw new IllegalArgumentException("Invalid locale format: " + s);
        }
        final char char1 = s.charAt(0);
        final char char2 = s.charAt(1);
        if (char1 < 'a' || char1 > 'z' || char2 < 'a' || char2 > 'z') {
            throw new IllegalArgumentException("Invalid locale format: " + s);
        }
        if (length == 2) {
            return new Locale(s, "");
        }
        if (s.charAt(2) != '_') {
            throw new IllegalArgumentException("Invalid locale format: " + s);
        }
        final char char3 = s.charAt(3);
        if (char3 == '_') {
            return new Locale(s.substring(0, 2), "", s.substring(4));
        }
        final char char4 = s.charAt(4);
        if (char3 < 'A' || char3 > 'Z' || char4 < 'A' || char4 > 'Z') {
            throw new IllegalArgumentException("Invalid locale format: " + s);
        }
        if (length == 5) {
            return new Locale(s.substring(0, 2), s.substring(3, 5));
        }
        if (s.charAt(5) != '_') {
            throw new IllegalArgumentException("Invalid locale format: " + s);
        }
        return new Locale(s.substring(0, 2), s.substring(3, 5), s.substring(6));
    }
    
    public static List localeLookupList(final Locale locale) {
        return localeLookupList(locale, locale);
    }
    
    public static List localeLookupList(final Locale locale, final Locale locale2) {
        final ArrayList<Locale> list = new ArrayList<Locale>(4);
        if (locale != null) {
            list.add(locale);
            if (locale.getVariant().length() > 0) {
                list.add(new Locale(locale.getLanguage(), locale.getCountry()));
            }
            if (locale.getCountry().length() > 0) {
                list.add(new Locale(locale.getLanguage(), ""));
            }
            if (!list.contains(locale2)) {
                list.add(locale2);
            }
        }
        return Collections.unmodifiableList((List<?>)list);
    }
    
    public static List availableLocaleList() {
        if (LocaleUtils.cAvailableLocaleList == null) {
            initAvailableLocaleList();
        }
        return LocaleUtils.cAvailableLocaleList;
    }
    
    private static synchronized void initAvailableLocaleList() {
        if (LocaleUtils.cAvailableLocaleList == null) {
            LocaleUtils.cAvailableLocaleList = Collections.unmodifiableList((List<?>)Arrays.asList(Locale.getAvailableLocales()));
        }
    }
    
    public static Set availableLocaleSet() {
        if (LocaleUtils.cAvailableLocaleSet == null) {
            initAvailableLocaleSet();
        }
        return LocaleUtils.cAvailableLocaleSet;
    }
    
    private static synchronized void initAvailableLocaleSet() {
        if (LocaleUtils.cAvailableLocaleSet == null) {
            LocaleUtils.cAvailableLocaleSet = Collections.unmodifiableSet((Set<?>)new HashSet<Object>(availableLocaleList()));
        }
    }
    
    public static boolean isAvailableLocale(final Locale locale) {
        return availableLocaleList().contains(locale);
    }
    
    public static List languagesByCountry(final String s) {
        List<Object> list = LocaleUtils.cLanguagesByCountry.get(s);
        if (list == null) {
            if (s != null) {
                final ArrayList<Locale> list2 = new ArrayList<Locale>();
                final List availableLocaleList = availableLocaleList();
                for (int i = 0; i < availableLocaleList.size(); ++i) {
                    final Locale locale = availableLocaleList.get(i);
                    if (s.equals(locale.getCountry()) && locale.getVariant().length() == 0) {
                        list2.add(locale);
                    }
                }
                list = Collections.unmodifiableList((List<?>)list2);
            }
            else {
                list = (List<Object>)Collections.EMPTY_LIST;
            }
            LocaleUtils.cLanguagesByCountry.put(s, list);
        }
        return list;
    }
    
    public static List countriesByLanguage(final String s) {
        List<Object> list = LocaleUtils.cCountriesByLanguage.get(s);
        if (list == null) {
            if (s != null) {
                final ArrayList<Locale> list2 = new ArrayList<Locale>();
                final List availableLocaleList = availableLocaleList();
                for (int i = 0; i < availableLocaleList.size(); ++i) {
                    final Locale locale = availableLocaleList.get(i);
                    if (s.equals(locale.getLanguage()) && locale.getCountry().length() != 0 && locale.getVariant().length() == 0) {
                        list2.add(locale);
                    }
                }
                list = Collections.unmodifiableList((List<?>)list2);
            }
            else {
                list = (List<Object>)Collections.EMPTY_LIST;
            }
            LocaleUtils.cCountriesByLanguage.put(s, list);
        }
        return list;
    }
    
    static {
        cLanguagesByCountry = Collections.synchronizedMap(new HashMap<Object, Object>());
        cCountriesByLanguage = Collections.synchronizedMap(new HashMap<Object, Object>());
    }
}
