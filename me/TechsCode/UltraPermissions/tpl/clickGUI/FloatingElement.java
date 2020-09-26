

package me.TechsCode.EnderPermissions.tpl.clickGUI;

import org.bukkit.entity.ArmorStand;

public abstract class FloatingElement
{
    private boolean shown;
    private boolean hovering;
    
    public FloatingElement() {
        this.shown = true;
    }
    
    public void hovering(final boolean hovering) {
        if (this.hovering == hovering) {
            return;
        }
        this.onHoverChange(this.hovering = hovering);
    }
    
    public boolean isHovering() {
        return this.hovering;
    }
    
    public boolean isShown() {
        return this.shown;
    }
    
    public void setShown(final boolean shown) {
        if (this.shown == shown) {
            return;
        }
        this.shown = shown;
    }
    
    public abstract void apply(final ArmorStand p0);
    
    public abstract double getYOffset();
    
    public abstract double getX();
    
    public abstract double getY();
    
    public abstract double getRadius();
    
    public abstract void click();
    
    protected abstract void onHoverChange(final boolean p0);
}
