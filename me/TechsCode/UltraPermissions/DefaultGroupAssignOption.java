

package me.TechsCode.EnderPermissions;

public enum DefaultGroupAssignOption
{
    FIRST_JOIN("Only add default groups on the first join", "Default Groups will be only added on first join"), 
    NO_GROUP("If a player has no groups, assign all default groups", "Default Groups will be assigned if a player has no groups"), 
    ALWAYS_ASSIGN("Always force every player to all default groups", "Default Groups will always be assigned to all players");
    
    private final String description;
    private final String explanation;
    
    private DefaultGroupAssignOption(final String description, final String explanation) {
        this.description = description;
        this.explanation = explanation;
    }
    
    public DefaultGroupAssignOption next() {
        switch (this) {
            case FIRST_JOIN: {
                return DefaultGroupAssignOption.NO_GROUP;
            }
            case NO_GROUP: {
                return DefaultGroupAssignOption.ALWAYS_ASSIGN;
            }
            case ALWAYS_ASSIGN: {
                return DefaultGroupAssignOption.FIRST_JOIN;
            }
            default: {
                return null;
            }
        }
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getExplanation() {
        return this.explanation;
    }
}
