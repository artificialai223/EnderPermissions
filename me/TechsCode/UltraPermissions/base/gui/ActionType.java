

package me.TechsCode.EnderPermissions.base.gui;

import java.util.Arrays;
import org.bukkit.event.inventory.ClickType;

public enum ActionType
{
    RIGHT(ClickType.RIGHT), 
    LEFT(ClickType.LEFT), 
    MIDDLE(ClickType.MIDDLE), 
    Q(ClickType.DROP), 
    SHIFT_RIGHT(ClickType.SHIFT_RIGHT), 
    SHIFT_LEFT(ClickType.SHIFT_LEFT);
    
    private final ClickType nativeType;
    
    private ActionType(final ClickType nativeType) {
        this.nativeType = nativeType;
    }
    
    public static ActionType fromClickType(final ClickType clickType) {
        return Arrays.stream(values()).filter(actionType -> actionType.nativeType == clickType).findFirst().orElse(null);
    }
}
