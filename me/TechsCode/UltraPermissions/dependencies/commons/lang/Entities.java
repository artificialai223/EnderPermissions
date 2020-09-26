

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import java.util.TreeMap;
import java.util.HashMap;
import java.util.Map;
import java.io.StringWriter;
import java.io.IOException;
import java.io.Writer;

class Entities
{
    private static final String[][] BASIC_ARRAY;
    private static final String[][] APOS_ARRAY;
    static final String[][] ISO8859_1_ARRAY;
    static final String[][] HTML40_ARRAY;
    public static final Entities XML;
    public static final Entities HTML32;
    public static final Entities HTML40;
    private final EntityMap map;
    
    static void fillWithHtml40Entities(final Entities entities) {
        entities.addEntities(Entities.BASIC_ARRAY);
        entities.addEntities(Entities.ISO8859_1_ARRAY);
        entities.addEntities(Entities.HTML40_ARRAY);
    }
    
    public Entities() {
        this.map = new LookupEntityMap();
    }
    
    Entities(final EntityMap map) {
        this.map = map;
    }
    
    public void addEntities(final String[][] array) {
        for (int i = 0; i < array.length; ++i) {
            this.addEntity(array[i][0], Integer.parseInt(array[i][1]));
        }
    }
    
    public void addEntity(final String s, final int n) {
        this.map.add(s, n);
    }
    
    public String entityName(final int n) {
        return this.map.name(n);
    }
    
    public int entityValue(final String s) {
        return this.map.value(s);
    }
    
    public String escape(final String s) {
        final StringWriter stringWriter = this.createStringWriter(s);
        try {
            this.escape(stringWriter, s);
        }
        catch (IOException ex) {
            throw new UnhandledException(ex);
        }
        return stringWriter.toString();
    }
    
    public void escape(final Writer writer, final String s) {
        for (int length = s.length(), i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            final String entityName = this.entityName(char1);
            if (entityName == null) {
                if (char1 > '\u007f') {
                    writer.write("&#");
                    writer.write(Integer.toString(char1, 10));
                    writer.write(59);
                }
                else {
                    writer.write(char1);
                }
            }
            else {
                writer.write(38);
                writer.write(entityName);
                writer.write(59);
            }
        }
    }
    
    public String unescape(final String s) {
        final int index = s.indexOf(38);
        if (index < 0) {
            return s;
        }
        final StringWriter stringWriter = this.createStringWriter(s);
        try {
            this.doUnescape(stringWriter, s, index);
        }
        catch (IOException ex) {
            throw new UnhandledException(ex);
        }
        return stringWriter.toString();
    }
    
    private StringWriter createStringWriter(final String s) {
        return new StringWriter((int)(s.length() + s.length() * 0.1));
    }
    
    public void unescape(final Writer writer, final String str) {
        final int index = str.indexOf(38);
        if (index < 0) {
            writer.write(str);
            return;
        }
        this.doUnescape(writer, str, index);
    }
    
    private void doUnescape(final Writer writer, final String str, final int len) {
        writer.write(str, 0, len);
        for (int length = str.length(), i = len; i < length; ++i) {
            final char char1 = str.charAt(i);
            if (char1 == '&') {
                final int n = i + 1;
                final int index = str.indexOf(59, n);
                if (index == -1) {
                    writer.write(char1);
                }
                else {
                    final int index2 = str.indexOf(38, i + 1);
                    if (index2 != -1 && index2 < index) {
                        writer.write(char1);
                    }
                    else {
                        final String substring = str.substring(n, index);
                        int c = -1;
                        final int length2 = substring.length();
                        if (length2 > 0) {
                            if (substring.charAt(0) == '#') {
                                if (length2 > 1) {
                                    final char char2 = substring.charAt(1);
                                    try {
                                        switch (char2) {
                                            case 88:
                                            case 120: {
                                                c = Integer.parseInt(substring.substring(2), 16);
                                                break;
                                            }
                                            default: {
                                                c = Integer.parseInt(substring.substring(1), 10);
                                                break;
                                            }
                                        }
                                        if (c > 65535) {
                                            c = -1;
                                        }
                                    }
                                    catch (NumberFormatException ex) {
                                        c = -1;
                                    }
                                }
                            }
                            else {
                                c = this.entityValue(substring);
                            }
                        }
                        if (c == -1) {
                            writer.write(38);
                            writer.write(substring);
                            writer.write(59);
                        }
                        else {
                            writer.write(c);
                        }
                        i = index;
                    }
                }
            }
            else {
                writer.write(char1);
            }
        }
    }
    
    static {
        BASIC_ARRAY = new String[][] { { "quot", "34" }, { "amp", "38" }, { "lt", "60" }, { "gt", "62" } };
        APOS_ARRAY = new String[][] { { "apos", "39" } };
        ISO8859_1_ARRAY = new String[][] { { "nbsp", "160" }, { "iexcl", "161" }, { "cent", "162" }, { "pound", "163" }, { "curren", "164" }, { "yen", "165" }, { "brvbar", "166" }, { "sect", "167" }, { "uml", "168" }, { "copy", "169" }, { "ordf", "170" }, { "laquo", "171" }, { "not", "172" }, { "shy", "173" }, { "reg", "174" }, { "macr", "175" }, { "deg", "176" }, { "plusmn", "177" }, { "sup2", "178" }, { "sup3", "179" }, { "acute", "180" }, { "micro", "181" }, { "para", "182" }, { "middot", "183" }, { "cedil", "184" }, { "sup1", "185" }, { "ordm", "186" }, { "raquo", "187" }, { "frac14", "188" }, { "frac12", "189" }, { "frac34", "190" }, { "iquest", "191" }, { "Agrave", "192" }, { "Aacute", "193" }, { "Acirc", "194" }, { "Atilde", "195" }, { "Auml", "196" }, { "Aring", "197" }, { "AElig", "198" }, { "Ccedil", "199" }, { "Egrave", "200" }, { "Eacute", "201" }, { "Ecirc", "202" }, { "Euml", "203" }, { "Igrave", "204" }, { "Iacute", "205" }, { "Icirc", "206" }, { "Iuml", "207" }, { "ETH", "208" }, { "Ntilde", "209" }, { "Ograve", "210" }, { "Oacute", "211" }, { "Ocirc", "212" }, { "Otilde", "213" }, { "Ouml", "214" }, { "times", "215" }, { "Oslash", "216" }, { "Ugrave", "217" }, { "Uacute", "218" }, { "Ucirc", "219" }, { "Uuml", "220" }, { "Yacute", "221" }, { "THORN", "222" }, { "szlig", "223" }, { "agrave", "224" }, { "aacute", "225" }, { "acirc", "226" }, { "atilde", "227" }, { "auml", "228" }, { "aring", "229" }, { "aelig", "230" }, { "ccedil", "231" }, { "egrave", "232" }, { "eacute", "233" }, { "ecirc", "234" }, { "euml", "235" }, { "igrave", "236" }, { "iacute", "237" }, { "icirc", "238" }, { "iuml", "239" }, { "eth", "240" }, { "ntilde", "241" }, { "ograve", "242" }, { "oacute", "243" }, { "ocirc", "244" }, { "otilde", "245" }, { "ouml", "246" }, { "divide", "247" }, { "oslash", "248" }, { "ugrave", "249" }, { "uacute", "250" }, { "ucirc", "251" }, { "uuml", "252" }, { "yacute", "253" }, { "thorn", "254" }, { "yuml", "255" } };
        HTML40_ARRAY = new String[][] { { "fnof", "402" }, { "Alpha", "913" }, { "Beta", "914" }, { "Gamma", "915" }, { "Delta", "916" }, { "Epsilon", "917" }, { "Zeta", "918" }, { "Eta", "919" }, { "Theta", "920" }, { "Iota", "921" }, { "Kappa", "922" }, { "Lambda", "923" }, { "Mu", "924" }, { "Nu", "925" }, { "Xi", "926" }, { "Omicron", "927" }, { "Pi", "928" }, { "Rho", "929" }, { "Sigma", "931" }, { "Tau", "932" }, { "Upsilon", "933" }, { "Phi", "934" }, { "Chi", "935" }, { "Psi", "936" }, { "Omega", "937" }, { "alpha", "945" }, { "beta", "946" }, { "gamma", "947" }, { "delta", "948" }, { "epsilon", "949" }, { "zeta", "950" }, { "eta", "951" }, { "theta", "952" }, { "iota", "953" }, { "kappa", "954" }, { "lambda", "955" }, { "mu", "956" }, { "nu", "957" }, { "xi", "958" }, { "omicron", "959" }, { "pi", "960" }, { "rho", "961" }, { "sigmaf", "962" }, { "sigma", "963" }, { "tau", "964" }, { "upsilon", "965" }, { "phi", "966" }, { "chi", "967" }, { "psi", "968" }, { "omega", "969" }, { "thetasym", "977" }, { "upsih", "978" }, { "piv", "982" }, { "bull", "8226" }, { "hellip", "8230" }, { "prime", "8242" }, { "Prime", "8243" }, { "oline", "8254" }, { "frasl", "8260" }, { "weierp", "8472" }, { "image", "8465" }, { "real", "8476" }, { "trade", "8482" }, { "alefsym", "8501" }, { "larr", "8592" }, { "uarr", "8593" }, { "rarr", "8594" }, { "darr", "8595" }, { "harr", "8596" }, { "crarr", "8629" }, { "lArr", "8656" }, { "uArr", "8657" }, { "rArr", "8658" }, { "dArr", "8659" }, { "hArr", "8660" }, { "forall", "8704" }, { "part", "8706" }, { "exist", "8707" }, { "empty", "8709" }, { "nabla", "8711" }, { "isin", "8712" }, { "notin", "8713" }, { "ni", "8715" }, { "prod", "8719" }, { "sum", "8721" }, { "minus", "8722" }, { "lowast", "8727" }, { "radic", "8730" }, { "prop", "8733" }, { "infin", "8734" }, { "ang", "8736" }, { "and", "8743" }, { "or", "8744" }, { "cap", "8745" }, { "cup", "8746" }, { "int", "8747" }, { "there4", "8756" }, { "sim", "8764" }, { "cong", "8773" }, { "asymp", "8776" }, { "ne", "8800" }, { "equiv", "8801" }, { "le", "8804" }, { "ge", "8805" }, { "sub", "8834" }, { "sup", "8835" }, { "sube", "8838" }, { "supe", "8839" }, { "oplus", "8853" }, { "otimes", "8855" }, { "perp", "8869" }, { "sdot", "8901" }, { "lceil", "8968" }, { "rceil", "8969" }, { "lfloor", "8970" }, { "rfloor", "8971" }, { "lang", "9001" }, { "rang", "9002" }, { "loz", "9674" }, { "spades", "9824" }, { "clubs", "9827" }, { "hearts", "9829" }, { "diams", "9830" }, { "OElig", "338" }, { "oelig", "339" }, { "Scaron", "352" }, { "scaron", "353" }, { "Yuml", "376" }, { "circ", "710" }, { "tilde", "732" }, { "ensp", "8194" }, { "emsp", "8195" }, { "thinsp", "8201" }, { "zwnj", "8204" }, { "zwj", "8205" }, { "lrm", "8206" }, { "rlm", "8207" }, { "ndash", "8211" }, { "mdash", "8212" }, { "lsquo", "8216" }, { "rsquo", "8217" }, { "sbquo", "8218" }, { "ldquo", "8220" }, { "rdquo", "8221" }, { "bdquo", "8222" }, { "dagger", "8224" }, { "Dagger", "8225" }, { "permil", "8240" }, { "lsaquo", "8249" }, { "rsaquo", "8250" }, { "euro", "8364" } };
        final Entities xml = new Entities();
        xml.addEntities(Entities.BASIC_ARRAY);
        xml.addEntities(Entities.APOS_ARRAY);
        XML = xml;
        final Entities html32 = new Entities();
        html32.addEntities(Entities.BASIC_ARRAY);
        html32.addEntities(Entities.ISO8859_1_ARRAY);
        HTML32 = html32;
        final Entities html33 = new Entities();
        fillWithHtml40Entities(html33);
        HTML40 = html33;
    }
    
    static class PrimitiveEntityMap implements EntityMap
    {
        private final Map mapNameToValue;
        private final IntHashMap mapValueToName;
        
        PrimitiveEntityMap() {
            this.mapNameToValue = new HashMap();
            this.mapValueToName = new IntHashMap();
        }
        
        public void add(final String s, final int value) {
            this.mapNameToValue.put(s, new Integer(value));
            this.mapValueToName.put(value, s);
        }
        
        public String name(final int n) {
            return (String)this.mapValueToName.get(n);
        }
        
        public int value(final String s) {
            final Integer value = this.mapNameToValue.get(s);
            if (value == null) {
                return -1;
            }
            return value;
        }
    }
    
    abstract static class MapIntMap implements EntityMap
    {
        protected final Map mapNameToValue;
        protected final Map mapValueToName;
        
        MapIntMap(final Map mapNameToValue, final Map mapValueToName) {
            this.mapNameToValue = mapNameToValue;
            this.mapValueToName = mapValueToName;
        }
        
        public void add(final String s, final int n) {
            this.mapNameToValue.put(s, new Integer(n));
            this.mapValueToName.put(new Integer(n), s);
        }
        
        public String name(final int value) {
            return this.mapValueToName.get(new Integer(value));
        }
        
        public int value(final String s) {
            final Integer value = this.mapNameToValue.get(s);
            if (value == null) {
                return -1;
            }
            return value;
        }
    }
    
    static class HashEntityMap extends MapIntMap
    {
        public HashEntityMap() {
            super(new HashMap(), new HashMap());
        }
    }
    
    static class TreeEntityMap extends MapIntMap
    {
        public TreeEntityMap() {
            super(new TreeMap(), new TreeMap());
        }
    }
    
    static class LookupEntityMap extends PrimitiveEntityMap
    {
        private String[] lookupTable;
        private static final int LOOKUP_TABLE_SIZE = 256;
        
        public String name(final int n) {
            if (n < 256) {
                return this.lookupTable()[n];
            }
            return super.name(n);
        }
        
        private String[] lookupTable() {
            if (this.lookupTable == null) {
                this.createLookupTable();
            }
            return this.lookupTable;
        }
        
        private void createLookupTable() {
            this.lookupTable = new String[256];
            for (int i = 0; i < 256; ++i) {
                this.lookupTable[i] = super.name(i);
            }
        }
    }
    
    static class ArrayEntityMap implements EntityMap
    {
        protected final int growBy;
        protected int size;
        protected String[] names;
        protected int[] values;
        
        public ArrayEntityMap() {
            this.size = 0;
            this.growBy = 100;
            this.names = new String[this.growBy];
            this.values = new int[this.growBy];
        }
        
        public ArrayEntityMap(final int growBy) {
            this.size = 0;
            this.growBy = growBy;
            this.names = new String[growBy];
            this.values = new int[growBy];
        }
        
        public void add(final String s, final int n) {
            this.ensureCapacity(this.size + 1);
            this.names[this.size] = s;
            this.values[this.size] = n;
            ++this.size;
        }
        
        protected void ensureCapacity(final int a) {
            if (a > this.names.length) {
                final int max = Math.max(a, this.size + this.growBy);
                final String[] names = new String[max];
                System.arraycopy(this.names, 0, names, 0, this.size);
                this.names = names;
                final int[] values = new int[max];
                System.arraycopy(this.values, 0, values, 0, this.size);
                this.values = values;
            }
        }
        
        public String name(final int n) {
            for (int i = 0; i < this.size; ++i) {
                if (this.values[i] == n) {
                    return this.names[i];
                }
            }
            return null;
        }
        
        public int value(final String anObject) {
            for (int i = 0; i < this.size; ++i) {
                if (this.names[i].equals(anObject)) {
                    return this.values[i];
                }
            }
            return -1;
        }
    }
    
    static class BinaryEntityMap extends ArrayEntityMap
    {
        public BinaryEntityMap() {
        }
        
        public BinaryEntityMap(final int n) {
            super(n);
        }
        
        private int binarySearch(final int n) {
            int i = 0;
            int n2 = this.size - 1;
            while (i <= n2) {
                final int n3 = i + n2 >>> 1;
                final int n4 = this.values[n3];
                if (n4 < n) {
                    i = n3 + 1;
                }
                else {
                    if (n4 <= n) {
                        return n3;
                    }
                    n2 = n3 - 1;
                }
            }
            return -(i + 1);
        }
        
        public void add(final String s, final int n) {
            this.ensureCapacity(this.size + 1);
            final int binarySearch = this.binarySearch(n);
            if (binarySearch > 0) {
                return;
            }
            final int n2 = -(binarySearch + 1);
            System.arraycopy(this.values, n2, this.values, n2 + 1, this.size - n2);
            this.values[n2] = n;
            System.arraycopy(this.names, n2, this.names, n2 + 1, this.size - n2);
            this.names[n2] = s;
            ++this.size;
        }
        
        public String name(final int n) {
            final int binarySearch = this.binarySearch(n);
            if (binarySearch < 0) {
                return null;
            }
            return this.names[binarySearch];
        }
    }
    
    interface EntityMap
    {
        void add(final String p0, final int p1);
        
        String name(final int p0);
        
        int value(final String p0);
    }
}
