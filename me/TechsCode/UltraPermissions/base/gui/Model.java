

package me.TechsCode.EnderPermissions.base.gui;

import java.util.HashMap;

public class Model
{
    private String title;
    private int slots;
    private HashMap<Integer, Entry> entries;
    
    public Model() {
        this.title = "Unnamed Inventory";
        this.slots = 27;
        this.entries = new HashMap<Integer, Entry>();
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public void setSlots(final int slots) {
        this.slots = slots;
    }
    
    public void button(final Entry value, final int i) {
        this.entries.put(i, value);
    }
    
    public void button(final int i, final Entry value) {
        this.entries.put(i, value);
    }
    
    public void button(final Entry value, final int n, final int n2) {
        this.entries.put(this.format(n, n2), value);
    }
    
    public void button(final int n, final int n2, final Entry value) {
        this.entries.put(this.format(n, n2), value);
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public int getSlots() {
        return this.slots;
    }
    
    public HashMap<Integer, Entry> getEntries() {
        return this.entries;
    }
    
    private int format(final int n, final int n2) {
        return (n - 1) * 9 + n2;
    }
}
