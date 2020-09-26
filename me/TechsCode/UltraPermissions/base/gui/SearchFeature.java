

package me.TechsCode.EnderPermissions.base.gui;

import me.TechsCode.EnderPermissions.base.item.XMaterial;

public abstract class SearchFeature<OBJ>
{
    private XMaterial icon;
    private String title;
    private String action;
    
    public SearchFeature(final XMaterial icon, final String title, final String action) {
        this.icon = icon;
        this.title = title;
        this.action = action;
    }
    
    public abstract String[] getSearchableText(final OBJ p0);
    
    public XMaterial getIcon() {
        return this.icon;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getAction() {
        return this.action;
    }
}
