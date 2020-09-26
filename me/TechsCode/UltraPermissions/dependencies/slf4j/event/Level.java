

package me.TechsCode.EnderPermissions.dependencies.slf4j.event;

public enum Level
{
    ERROR(40, "ERROR"), 
    WARN(30, "WARN"), 
    INFO(20, "INFO"), 
    DEBUG(10, "DEBUG"), 
    TRACE(0, "TRACE");
    
    private int levelInt;
    private String levelStr;
    
    private Level(final int levelInt, final String levelStr) {
        this.levelInt = levelInt;
        this.levelStr = levelStr;
    }
    
    public int toInt() {
        return this.levelInt;
    }
    
    public static Level intToLevel(final int i) {
        switch (i) {
            case 0: {
                return Level.TRACE;
            }
            case 10: {
                return Level.DEBUG;
            }
            case 20: {
                return Level.INFO;
            }
            case 30: {
                return Level.WARN;
            }
            case 40: {
                return Level.ERROR;
            }
            default: {
                throw new IllegalArgumentException("Level integer [" + i + "] not recognized.");
            }
        }
    }
    
    @Override
    public String toString() {
        return this.levelStr;
    }
}
