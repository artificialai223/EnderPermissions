

package me.TechsCode.EnderPermissions.dependencies.commons.lang.enum;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.InvocationTargetException;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.ClassUtils;
import java.util.Collection;
import java.util.Iterator;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.StringUtils;
import java.util.Map;
import java.io.Serializable;

public abstract class Enum implements Comparable, Serializable
{
    private static final long serialVersionUID = -487045951170455942L;
    private static final Map EMPTY_MAP;
    private static Map cEnumClasses;
    private final String iName;
    private final transient int iHashCode;
    protected transient String iToString;
    static /* synthetic */ Class class$org$apache$commons$lang$enum$Enum;
    
    protected Enum(final String iName) {
        this.iToString = null;
        this.init(iName);
        this.iName = iName;
        this.iHashCode = 7 + this.getEnumClass().hashCode() + 3 * iName.hashCode();
    }
    
    private void init(final String str) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException("The Enum name must not be empty or null");
        }
        final Class enumClass = this.getEnumClass();
        if (enumClass == null) {
            throw new IllegalArgumentException("getEnumClass() must not be null");
        }
        Serializable s = this.getClass();
        boolean b = false;
        while (s != null && s != Enum.class && s != ValuedEnum.class) {
            if (s == enumClass) {
                b = true;
                break;
            }
            s = ((Class<? extends Enum>)s).getSuperclass();
        }
        if (!b) {
            throw new IllegalArgumentException("getEnumClass() must return a superclass of this class");
        }
        Class class$;
        Class class$org$apache$commons$lang$enum$Enum;
        if (Enum.class$org$apache$commons$lang$enum$Enum == null) {
            class$org$apache$commons$lang$enum$Enum = (Enum.class$org$apache$commons$lang$enum$Enum = (class$ = class$("me.TechsCode.EnderPermissions.dependencies.commons.lang.enum.Enum")));
        }
        else {
            class$ = (class$org$apache$commons$lang$enum$Enum = Enum.class$org$apache$commons$lang$enum$Enum);
        }
        final Class clazz = class$org$apache$commons$lang$enum$Enum;
        Entry entry;
        synchronized (class$) {
            entry = Enum.cEnumClasses.get(enumClass);
            if (entry == null) {
                entry = createEntry(enumClass);
                final WeakHashMap<Class<? extends Enum>, Entry> cEnumClasses = new WeakHashMap<Class<? extends Enum>, Entry>();
                cEnumClasses.putAll((Map<?, ?>)Enum.cEnumClasses);
                cEnumClasses.put(enumClass, entry);
                Enum.cEnumClasses = cEnumClasses;
            }
        }
        if (entry.map.containsKey(str)) {
            throw new IllegalArgumentException("The Enum name must be unique, '" + str + "' has already been added");
        }
        entry.map.put(str, this);
        entry.list.add(this);
    }
    
    protected Object readResolve() {
        final Entry entry = Enum.cEnumClasses.get(this.getEnumClass());
        if (entry == null) {
            return null;
        }
        return entry.map.get(this.getName());
    }
    
    protected static Enum getEnum(final Class clazz, final String s) {
        final Entry entry = getEntry(clazz);
        if (entry == null) {
            return null;
        }
        return (Enum)entry.map.get(s);
    }
    
    protected static Map getEnumMap(final Class clazz) {
        final Entry entry = getEntry(clazz);
        if (entry == null) {
            return Enum.EMPTY_MAP;
        }
        return entry.unmodifiableMap;
    }
    
    protected static List getEnumList(final Class clazz) {
        final Entry entry = getEntry(clazz);
        if (entry == null) {
            return Collections.EMPTY_LIST;
        }
        return entry.unmodifiableList;
    }
    
    protected static Iterator iterator(final Class clazz) {
        return getEnumList(clazz).iterator();
    }
    
    private static Entry getEntry(final Class clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("The Enum Class must not be null");
        }
        if (!Enum.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("The Class must be a subclass of Enum");
        }
        Entry entry = Enum.cEnumClasses.get(clazz);
        if (entry == null) {
            try {
                Class.forName(clazz.getName(), true, clazz.getClassLoader());
                entry = Enum.cEnumClasses.get(clazz);
            }
            catch (Exception ex) {}
        }
        return entry;
    }
    
    private static Entry createEntry(final Class clazz) {
        final Entry entry = new Entry();
        for (Class clazz2 = clazz.getSuperclass(); clazz2 != null && clazz2 != Enum.class && clazz2 != ValuedEnum.class; clazz2 = clazz2.getSuperclass()) {
            final Entry entry2 = Enum.cEnumClasses.get(clazz2);
            if (entry2 != null) {
                entry.list.addAll(entry2.list);
                entry.map.putAll(entry2.map);
                break;
            }
        }
        return entry;
    }
    
    public final String getName() {
        return this.iName;
    }
    
    public Class getEnumClass() {
        return this.getClass();
    }
    
    public final boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() == this.getClass()) {
            return this.iName.equals(((Enum)o).iName);
        }
        return o.getClass().getName().equals(this.getClass().getName()) && this.iName.equals(this.getNameInOtherClassLoader(o));
    }
    
    public final int hashCode() {
        return this.iHashCode;
    }
    
    public int compareTo(final Object o) {
        if (o == this) {
            return 0;
        }
        if (o.getClass() == this.getClass()) {
            return this.iName.compareTo(((Enum)o).iName);
        }
        if (o.getClass().getName().equals(this.getClass().getName())) {
            return this.iName.compareTo(this.getNameInOtherClassLoader(o));
        }
        throw new ClassCastException("Different enum class '" + ClassUtils.getShortClassName(o.getClass()) + "'");
    }
    
    private String getNameInOtherClassLoader(final Object obj) {
        try {
            return (String)obj.getClass().getMethod("getName", (Class<?>[])null).invoke(obj, (Object[])null);
        }
        catch (NoSuchMethodException ex) {}
        catch (IllegalAccessException ex2) {}
        catch (InvocationTargetException ex3) {}
        throw new IllegalStateException("This should not happen");
    }
    
    public String toString() {
        if (this.iToString == null) {
            this.iToString = ClassUtils.getShortClassName(this.getEnumClass()) + "[" + this.getName() + "]";
        }
        return this.iToString;
    }
    
    static /* synthetic */ Class class$(final String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    static {
        EMPTY_MAP = Collections.unmodifiableMap((Map<?, ?>)new HashMap<Object, Object>(0));
        Enum.cEnumClasses = new WeakHashMap();
    }
    
    private static class Entry
    {
        final Map map;
        final Map unmodifiableMap;
        final List list;
        final List unmodifiableList;
        
        protected Entry() {
            this.map = new HashMap();
            this.unmodifiableMap = Collections.unmodifiableMap((Map<?, ?>)this.map);
            this.list = new ArrayList(25);
            this.unmodifiableList = Collections.unmodifiableList((List<?>)this.list);
        }
    }
}
