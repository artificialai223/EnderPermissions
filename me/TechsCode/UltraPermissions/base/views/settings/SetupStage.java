

package me.TechsCode.EnderPermissions.base.views.settings;

import me.TechsCode.EnderPermissions.base.misc.Setter;

class SetupStage
{
    private final String fieldName;
    private final String defaultValue;
    private final Setter<String> setFunction;
    
    public SetupStage(final String fieldName, final String defaultValue, final Setter<String> setFunction) {
        this.fieldName = fieldName;
        this.defaultValue = defaultValue;
        this.setFunction = setFunction;
    }
    
    public String getFieldName() {
        return this.fieldName;
    }
    
    public String getDefaultValue() {
        return this.defaultValue;
    }
    
    public Setter<String> getSetFunction() {
        return this.setFunction;
    }
}
