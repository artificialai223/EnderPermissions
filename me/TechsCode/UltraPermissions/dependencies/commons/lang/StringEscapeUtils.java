

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.exception.NestableRuntimeException;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.text.StrBuilder;
import java.util.Locale;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class StringEscapeUtils
{
    private static final char CSV_DELIMITER = ',';
    private static final char CSV_QUOTE = '\"';
    private static final String CSV_QUOTE_STR;
    private static final char[] CSV_SEARCH_CHARS;
    
    public static String escapeJava(final String s) {
        return escapeJavaStyleString(s, false, false);
    }
    
    public static void escapeJava(final Writer writer, final String s) {
        escapeJavaStyleString(writer, s, false, false);
    }
    
    public static String escapeJavaScript(final String s) {
        return escapeJavaStyleString(s, true, true);
    }
    
    public static void escapeJavaScript(final Writer writer, final String s) {
        escapeJavaStyleString(writer, s, true, true);
    }
    
    private static String escapeJavaStyleString(final String s, final boolean b, final boolean b2) {
        if (s == null) {
            return null;
        }
        try {
            final StringWriter stringWriter = new StringWriter(s.length() * 2);
            escapeJavaStyleString(stringWriter, s, b, b2);
            return stringWriter.toString();
        }
        catch (IOException ex) {
            throw new UnhandledException(ex);
        }
    }
    
    private static void escapeJavaStyleString(final Writer writer, final String s, final boolean b, final boolean b2) {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null");
        }
        if (s == null) {
            return;
        }
        for (int length = s.length(), i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 > '\u0fff') {
                writer.write("\\u" + hex(char1));
            }
            else if (char1 > '\u00ff') {
                writer.write("\\u0" + hex(char1));
            }
            else if (char1 > '\u007f') {
                writer.write("\\u00" + hex(char1));
            }
            else if (char1 < ' ') {
                switch (char1) {
                    case '\b': {
                        writer.write(92);
                        writer.write(98);
                        break;
                    }
                    case '\n': {
                        writer.write(92);
                        writer.write(110);
                        break;
                    }
                    case '\t': {
                        writer.write(92);
                        writer.write(116);
                        break;
                    }
                    case '\f': {
                        writer.write(92);
                        writer.write(102);
                        break;
                    }
                    case '\r': {
                        writer.write(92);
                        writer.write(114);
                        break;
                    }
                    default: {
                        if (char1 > '\u000f') {
                            writer.write("\\u00" + hex(char1));
                            break;
                        }
                        writer.write("\\u000" + hex(char1));
                        break;
                    }
                }
            }
            else {
                switch (char1) {
                    case '\'': {
                        if (b) {
                            writer.write(92);
                        }
                        writer.write(39);
                        break;
                    }
                    case '\"': {
                        writer.write(92);
                        writer.write(34);
                        break;
                    }
                    case '\\': {
                        writer.write(92);
                        writer.write(92);
                        break;
                    }
                    case '/': {
                        if (b2) {
                            writer.write(92);
                        }
                        writer.write(47);
                        break;
                    }
                    default: {
                        writer.write(char1);
                        break;
                    }
                }
            }
        }
    }
    
    private static String hex(final char i) {
        return Integer.toHexString(i).toUpperCase(Locale.ENGLISH);
    }
    
    public static String unescapeJava(final String s) {
        if (s == null) {
            return null;
        }
        try {
            final StringWriter stringWriter = new StringWriter(s.length());
            unescapeJava(stringWriter, s);
            return stringWriter.toString();
        }
        catch (IOException ex) {
            throw new UnhandledException(ex);
        }
    }
    
    public static void unescapeJava(final Writer writer, final String s) {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null");
        }
        if (s == null) {
            return;
        }
        final int length = s.length();
        final StrBuilder obj = new StrBuilder(4);
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (n2 != 0) {
                obj.append(char1);
                if (obj.length() != 4) {
                    continue;
                }
                try {
                    writer.write((char)Integer.parseInt(obj.toString(), 16));
                    obj.setLength(0);
                    n2 = 0;
                    n = 0;
                    continue;
                }
                catch (NumberFormatException ex) {
                    throw new NestableRuntimeException("Unable to parse unicode value: " + obj, ex);
                }
            }
            if (n != 0) {
                n = 0;
                switch (char1) {
                    case '\\': {
                        writer.write(92);
                        break;
                    }
                    case '\'': {
                        writer.write(39);
                        break;
                    }
                    case '\"': {
                        writer.write(34);
                        break;
                    }
                    case 'r': {
                        writer.write(13);
                        break;
                    }
                    case 'f': {
                        writer.write(12);
                        break;
                    }
                    case 't': {
                        writer.write(9);
                        break;
                    }
                    case 'n': {
                        writer.write(10);
                        break;
                    }
                    case 'b': {
                        writer.write(8);
                        break;
                    }
                    case 'u': {
                        n2 = 1;
                        break;
                    }
                    default: {
                        writer.write(char1);
                        break;
                    }
                }
            }
            else if (char1 == '\\') {
                n = 1;
            }
            else {
                writer.write(char1);
            }
        }
        if (n != 0) {
            writer.write(92);
        }
    }
    
    public static String unescapeJavaScript(final String s) {
        return unescapeJava(s);
    }
    
    public static void unescapeJavaScript(final Writer writer, final String s) {
        unescapeJava(writer, s);
    }
    
    public static String escapeHtml(final String s) {
        if (s == null) {
            return null;
        }
        try {
            final StringWriter stringWriter = new StringWriter((int)(s.length() * 1.5));
            escapeHtml(stringWriter, s);
            return stringWriter.toString();
        }
        catch (IOException ex) {
            throw new UnhandledException(ex);
        }
    }
    
    public static void escapeHtml(final Writer writer, final String s) {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        }
        if (s == null) {
            return;
        }
        Entities.HTML40.escape(writer, s);
    }
    
    public static String unescapeHtml(final String s) {
        if (s == null) {
            return null;
        }
        try {
            final StringWriter stringWriter = new StringWriter((int)(s.length() * 1.5));
            unescapeHtml(stringWriter, s);
            return stringWriter.toString();
        }
        catch (IOException ex) {
            throw new UnhandledException(ex);
        }
    }
    
    public static void unescapeHtml(final Writer writer, final String s) {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        }
        if (s == null) {
            return;
        }
        Entities.HTML40.unescape(writer, s);
    }
    
    public static void escapeXml(final Writer writer, final String s) {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        }
        if (s == null) {
            return;
        }
        Entities.XML.escape(writer, s);
    }
    
    public static String escapeXml(final String s) {
        if (s == null) {
            return null;
        }
        return Entities.XML.escape(s);
    }
    
    public static void unescapeXml(final Writer writer, final String s) {
        if (writer == null) {
            throw new IllegalArgumentException("The Writer must not be null.");
        }
        if (s == null) {
            return;
        }
        Entities.XML.unescape(writer, s);
    }
    
    public static String unescapeXml(final String s) {
        if (s == null) {
            return null;
        }
        return Entities.XML.unescape(s);
    }
    
    public static String escapeSql(final String s) {
        if (s == null) {
            return null;
        }
        return StringUtils.replace(s, "'", "''");
    }
    
    public static String escapeCsv(final String s) {
        if (StringUtils.containsNone(s, StringEscapeUtils.CSV_SEARCH_CHARS)) {
            return s;
        }
        try {
            final StringWriter stringWriter = new StringWriter();
            escapeCsv(stringWriter, s);
            return stringWriter.toString();
        }
        catch (IOException ex) {
            throw new UnhandledException(ex);
        }
    }
    
    public static void escapeCsv(final Writer writer, final String str) {
        if (StringUtils.containsNone(str, StringEscapeUtils.CSV_SEARCH_CHARS)) {
            if (str != null) {
                writer.write(str);
            }
            return;
        }
        writer.write(34);
        for (int i = 0; i < str.length(); ++i) {
            final char char1 = str.charAt(i);
            if (char1 == '\"') {
                writer.write(34);
            }
            writer.write(char1);
        }
        writer.write(34);
    }
    
    public static String unescapeCsv(final String s) {
        if (s == null) {
            return null;
        }
        try {
            final StringWriter stringWriter = new StringWriter();
            unescapeCsv(stringWriter, s);
            return stringWriter.toString();
        }
        catch (IOException ex) {
            throw new UnhandledException(ex);
        }
    }
    
    public static void unescapeCsv(final Writer writer, String replace) {
        if (replace == null) {
            return;
        }
        if (replace.length() < 2) {
            writer.write(replace);
            return;
        }
        if (replace.charAt(0) != '\"' || replace.charAt(replace.length() - 1) != '\"') {
            writer.write(replace);
            return;
        }
        final String substring = replace.substring(1, replace.length() - 1);
        if (StringUtils.containsAny(substring, StringEscapeUtils.CSV_SEARCH_CHARS)) {
            replace = StringUtils.replace(substring, StringEscapeUtils.CSV_QUOTE_STR + StringEscapeUtils.CSV_QUOTE_STR, StringEscapeUtils.CSV_QUOTE_STR);
        }
        writer.write(replace);
    }
    
    static {
        CSV_QUOTE_STR = String.valueOf('\"');
        CSV_SEARCH_CHARS = new char[] { ',', '\"', '\r', '\n' };
    }
}
