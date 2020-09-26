

package me.TechsCode.EnderPermissions.dependencies.commons.codec.language.bm;

public enum NameType
{
    ASHKENAZI("ash"), 
    GENERIC("gen"), 
    SEPHARDIC("sep");
    
    private final String name;
    
    private NameType(final String name2) {
        this.name = name2;
    }
    
    public String getName() {
        return this.name;
    }
}
