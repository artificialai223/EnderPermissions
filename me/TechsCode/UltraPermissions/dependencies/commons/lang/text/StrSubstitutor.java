

package me.TechsCode.EnderPermissions.dependencies.commons.lang.text;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map;

public class StrSubstitutor
{
    public static final char DEFAULT_ESCAPE = '$';
    public static final StrMatcher DEFAULT_PREFIX;
    public static final StrMatcher DEFAULT_SUFFIX;
    private char escapeChar;
    private StrMatcher prefixMatcher;
    private StrMatcher suffixMatcher;
    private StrLookup variableResolver;
    private boolean enableSubstitutionInVariables;
    
    public static String replace(final Object o, final Map map) {
        return new StrSubstitutor(map).replace(o);
    }
    
    public static String replace(final Object o, final Map map, final String s, final String s2) {
        return new StrSubstitutor(map, s, s2).replace(o);
    }
    
    public static String replace(final Object o, final Properties properties) {
        if (properties == null) {
            return o.toString();
        }
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        final Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            final String key = (String)propertyNames.nextElement();
            hashMap.put(key, properties.getProperty(key));
        }
        return replace(o, hashMap);
    }
    
    public static String replaceSystemProperties(final Object o) {
        return new StrSubstitutor(StrLookup.systemPropertiesLookup()).replace(o);
    }
    
    public StrSubstitutor() {
        this(null, StrSubstitutor.DEFAULT_PREFIX, StrSubstitutor.DEFAULT_SUFFIX, '$');
    }
    
    public StrSubstitutor(final Map map) {
        this(StrLookup.mapLookup(map), StrSubstitutor.DEFAULT_PREFIX, StrSubstitutor.DEFAULT_SUFFIX, '$');
    }
    
    public StrSubstitutor(final Map map, final String s, final String s2) {
        this(StrLookup.mapLookup(map), s, s2, '$');
    }
    
    public StrSubstitutor(final Map map, final String s, final String s2, final char c) {
        this(StrLookup.mapLookup(map), s, s2, c);
    }
    
    public StrSubstitutor(final StrLookup strLookup) {
        this(strLookup, StrSubstitutor.DEFAULT_PREFIX, StrSubstitutor.DEFAULT_SUFFIX, '$');
    }
    
    public StrSubstitutor(final StrLookup variableResolver, final String variablePrefix, final String variableSuffix, final char escapeChar) {
        this.setVariableResolver(variableResolver);
        this.setVariablePrefix(variablePrefix);
        this.setVariableSuffix(variableSuffix);
        this.setEscapeChar(escapeChar);
    }
    
    public StrSubstitutor(final StrLookup variableResolver, final StrMatcher variablePrefixMatcher, final StrMatcher variableSuffixMatcher, final char escapeChar) {
        this.setVariableResolver(variableResolver);
        this.setVariablePrefixMatcher(variablePrefixMatcher);
        this.setVariableSuffixMatcher(variableSuffixMatcher);
        this.setEscapeChar(escapeChar);
    }
    
    public String replace(final String s) {
        if (s == null) {
            return null;
        }
        final StrBuilder strBuilder = new StrBuilder(s);
        if (!this.substitute(strBuilder, 0, s.length())) {
            return s;
        }
        return strBuilder.toString();
    }
    
    public String replace(final String s, final int beginIndex, final int n) {
        if (s == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder(n).append(s, beginIndex, n);
        if (!this.substitute(append, 0, n)) {
            return s.substring(beginIndex, beginIndex + n);
        }
        return append.toString();
    }
    
    public String replace(final char[] array) {
        if (array == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder(array.length).append(array);
        this.substitute(append, 0, array.length);
        return append.toString();
    }
    
    public String replace(final char[] array, final int n, final int n2) {
        if (array == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder(n2).append(array, n, n2);
        this.substitute(append, 0, n2);
        return append.toString();
    }
    
    public String replace(final StringBuffer sb) {
        if (sb == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder(sb.length()).append(sb);
        this.substitute(append, 0, append.length());
        return append.toString();
    }
    
    public String replace(final StringBuffer sb, final int n, final int n2) {
        if (sb == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder(n2).append(sb, n, n2);
        this.substitute(append, 0, n2);
        return append.toString();
    }
    
    public String replace(final StrBuilder strBuilder) {
        if (strBuilder == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder(strBuilder.length()).append(strBuilder);
        this.substitute(append, 0, append.length());
        return append.toString();
    }
    
    public String replace(final StrBuilder strBuilder, final int n, final int n2) {
        if (strBuilder == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder(n2).append(strBuilder, n, n2);
        this.substitute(append, 0, n2);
        return append.toString();
    }
    
    public String replace(final Object o) {
        if (o == null) {
            return null;
        }
        final StrBuilder append = new StrBuilder().append(o);
        this.substitute(append, 0, append.length());
        return append.toString();
    }
    
    public boolean replaceIn(final StringBuffer sb) {
        return sb != null && this.replaceIn(sb, 0, sb.length());
    }
    
    public boolean replaceIn(final StringBuffer sb, final int start, final int n) {
        if (sb == null) {
            return false;
        }
        final StrBuilder append = new StrBuilder(n).append(sb, start, n);
        if (!this.substitute(append, 0, n)) {
            return false;
        }
        sb.replace(start, start + n, append.toString());
        return true;
    }
    
    public boolean replaceIn(final StrBuilder strBuilder) {
        return strBuilder != null && this.substitute(strBuilder, 0, strBuilder.length());
    }
    
    public boolean replaceIn(final StrBuilder strBuilder, final int n, final int n2) {
        return strBuilder != null && this.substitute(strBuilder, n, n2);
    }
    
    protected boolean substitute(final StrBuilder strBuilder, final int n, final int n2) {
        return this.substitute(strBuilder, n, n2, null) > 0;
    }
    
    private int substitute(final StrBuilder strBuilder, final int offset, final int count, List list) {
        final StrMatcher variablePrefixMatcher = this.getVariablePrefixMatcher();
        final StrMatcher variableSuffixMatcher = this.getVariableSuffixMatcher();
        final char escapeChar = this.getEscapeChar();
        final boolean b = list == null;
        int n = 0;
        int n2 = 0;
        char[] array = strBuilder.buffer;
        int n3 = offset + count;
        int i = offset;
        while (i < n3) {
            final int match = variablePrefixMatcher.isMatch(array, i, offset, n3);
            if (match == 0) {
                ++i;
            }
            else if (i > offset && array[i - 1] == escapeChar) {
                strBuilder.deleteCharAt(i - 1);
                array = strBuilder.buffer;
                --n2;
                n = 1;
                --n3;
            }
            else {
                final int n4 = i;
                i += match;
                int n5 = 0;
                while (i < n3) {
                    final int match2;
                    if (this.isEnableSubstitutionInVariables() && (match2 = variablePrefixMatcher.isMatch(array, i, offset, n3)) != 0) {
                        ++n5;
                        i += match2;
                    }
                    else {
                        final int match3 = variableSuffixMatcher.isMatch(array, i, offset, n3);
                        if (match3 == 0) {
                            ++i;
                        }
                        else {
                            if (n5 == 0) {
                                String string = new String(array, n4 + match, i - n4 - match);
                                if (this.isEnableSubstitutionInVariables()) {
                                    final StrBuilder strBuilder2 = new StrBuilder(string);
                                    this.substitute(strBuilder2, 0, strBuilder2.length());
                                    string = strBuilder2.toString();
                                }
                                final int n6;
                                i = (n6 = i + match3);
                                if (list == null) {
                                    list = new ArrayList<Object>();
                                    list.add(new String(array, offset, count));
                                }
                                this.checkCyclicSubstitution(string, list);
                                list.add(string);
                                final String resolveVariable = this.resolveVariable(string, strBuilder, n4, n6);
                                if (resolveVariable != null) {
                                    final int length = resolveVariable.length();
                                    strBuilder.replace(n4, n6, resolveVariable);
                                    n = 1;
                                    final int n7 = this.substitute(strBuilder, n4, length, list) + (length - (n6 - n4));
                                    i += n7;
                                    n3 += n7;
                                    n2 += n7;
                                    array = strBuilder.buffer;
                                }
                                list.remove(list.size() - 1);
                                break;
                            }
                            --n5;
                            i += match3;
                        }
                    }
                }
            }
        }
        if (b) {
            return n;
        }
        return n2;
    }
    
    private void checkCyclicSubstitution(final String s, final List list) {
        if (!list.contains(s)) {
            return;
        }
        final StrBuilder strBuilder = new StrBuilder(256);
        strBuilder.append("Infinite loop in property interpolation of ");
        strBuilder.append(list.remove(0));
        strBuilder.append(": ");
        strBuilder.appendWithSeparators(list, "->");
        throw new IllegalStateException(strBuilder.toString());
    }
    
    protected String resolveVariable(final String s, final StrBuilder strBuilder, final int n, final int n2) {
        final StrLookup variableResolver = this.getVariableResolver();
        if (variableResolver == null) {
            return null;
        }
        return variableResolver.lookup(s);
    }
    
    public char getEscapeChar() {
        return this.escapeChar;
    }
    
    public void setEscapeChar(final char escapeChar) {
        this.escapeChar = escapeChar;
    }
    
    public StrMatcher getVariablePrefixMatcher() {
        return this.prefixMatcher;
    }
    
    public StrSubstitutor setVariablePrefixMatcher(final StrMatcher prefixMatcher) {
        if (prefixMatcher == null) {
            throw new IllegalArgumentException("Variable prefix matcher must not be null!");
        }
        this.prefixMatcher = prefixMatcher;
        return this;
    }
    
    public StrSubstitutor setVariablePrefix(final char c) {
        return this.setVariablePrefixMatcher(StrMatcher.charMatcher(c));
    }
    
    public StrSubstitutor setVariablePrefix(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("Variable prefix must not be null!");
        }
        return this.setVariablePrefixMatcher(StrMatcher.stringMatcher(s));
    }
    
    public StrMatcher getVariableSuffixMatcher() {
        return this.suffixMatcher;
    }
    
    public StrSubstitutor setVariableSuffixMatcher(final StrMatcher suffixMatcher) {
        if (suffixMatcher == null) {
            throw new IllegalArgumentException("Variable suffix matcher must not be null!");
        }
        this.suffixMatcher = suffixMatcher;
        return this;
    }
    
    public StrSubstitutor setVariableSuffix(final char c) {
        return this.setVariableSuffixMatcher(StrMatcher.charMatcher(c));
    }
    
    public StrSubstitutor setVariableSuffix(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("Variable suffix must not be null!");
        }
        return this.setVariableSuffixMatcher(StrMatcher.stringMatcher(s));
    }
    
    public StrLookup getVariableResolver() {
        return this.variableResolver;
    }
    
    public void setVariableResolver(final StrLookup variableResolver) {
        this.variableResolver = variableResolver;
    }
    
    public boolean isEnableSubstitutionInVariables() {
        return this.enableSubstitutionInVariables;
    }
    
    public void setEnableSubstitutionInVariables(final boolean enableSubstitutionInVariables) {
        this.enableSubstitutionInVariables = enableSubstitutionInVariables;
    }
    
    static {
        DEFAULT_PREFIX = StrMatcher.stringMatcher("${");
        DEFAULT_SUFFIX = StrMatcher.stringMatcher("}");
    }
}
