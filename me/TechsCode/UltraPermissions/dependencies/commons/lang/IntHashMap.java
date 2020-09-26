

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

class IntHashMap
{
    private transient Entry[] table;
    private transient int count;
    private int threshold;
    private final float loadFactor;
    
    public IntHashMap() {
        this(20, 0.75f);
    }
    
    public IntHashMap(final int n) {
        this(n, 0.75f);
    }
    
    public IntHashMap(int i, final float n) {
        if (i < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + i);
        }
        if (n <= 0.0f) {
            throw new IllegalArgumentException("Illegal Load: " + n);
        }
        if (i == 0) {
            i = 1;
        }
        this.loadFactor = n;
        this.table = new Entry[i];
        this.threshold = (int)(i * n);
    }
    
    public int size() {
        return this.count;
    }
    
    public boolean isEmpty() {
        return this.count == 0;
    }
    
    public boolean contains(final Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        final Entry[] table = this.table;
        int length = table.length;
        while (length-- > 0) {
            for (Entry next = table[length]; next != null; next = next.next) {
                if (next.value.equals(obj)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean containsValue(final Object o) {
        return this.contains(o);
    }
    
    public boolean containsKey(final int n) {
        final Entry[] table = this.table;
        for (Entry next = table[(n & Integer.MAX_VALUE) % table.length]; next != null; next = next.next) {
            if (next.hash == n) {
                return true;
            }
        }
        return false;
    }
    
    public Object get(final int n) {
        final Entry[] table = this.table;
        for (Entry next = table[(n & Integer.MAX_VALUE) % table.length]; next != null; next = next.next) {
            if (next.hash == n) {
                return next.value;
            }
        }
        return null;
    }
    
    protected void rehash() {
        final int length = this.table.length;
        final Entry[] table = this.table;
        final int n = length * 2 + 1;
        final Entry[] table2 = new Entry[n];
        this.threshold = (int)(n * this.loadFactor);
        this.table = table2;
        int n2 = length;
        while (n2-- > 0) {
            Entry entry;
            int n3;
            for (Entry next = table[n2]; next != null; next = next.next, n3 = (entry.hash & Integer.MAX_VALUE) % n, entry.next = table2[n3], table2[n3] = entry) {
                entry = next;
            }
        }
    }
    
    public Object put(final int n, final Object value) {
        Entry[] array = this.table;
        int n2 = (n & Integer.MAX_VALUE) % array.length;
        for (Entry next = array[n2]; next != null; next = next.next) {
            if (next.hash == n) {
                final Object value2 = next.value;
                next.value = value;
                return value2;
            }
        }
        if (this.count >= this.threshold) {
            this.rehash();
            array = this.table;
            n2 = (n & Integer.MAX_VALUE) % array.length;
        }
        array[n2] = new Entry(n, n, value, array[n2]);
        ++this.count;
        return null;
    }
    
    public Object remove(final int n) {
        final Entry[] table = this.table;
        final int n2 = (n & Integer.MAX_VALUE) % table.length;
        Entry next = table[n2];
        Entry entry = null;
        while (next != null) {
            if (next.hash == n) {
                if (entry != null) {
                    entry.next = next.next;
                }
                else {
                    table[n2] = next.next;
                }
                --this.count;
                final Object value = next.value;
                next.value = null;
                return value;
            }
            entry = next;
            next = next.next;
        }
        return null;
    }
    
    public synchronized void clear() {
        final Entry[] table = this.table;
        int length = table.length;
        while (--length >= 0) {
            table[length] = null;
        }
        this.count = 0;
    }
    
    private static class Entry
    {
        final int hash;
        final int key;
        Object value;
        Entry next;
        
        protected Entry(final int hash, final int key, final Object value, final Entry next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
