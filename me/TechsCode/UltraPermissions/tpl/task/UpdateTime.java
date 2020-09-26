

package me.TechsCode.EnderPermissions.tpl.task;

public enum UpdateTime
{
    TICK(1L), 
    TWOTICKS(2L), 
    FASTER(5L), 
    FAST(10L), 
    SEC(20L), 
    SLOW(60L), 
    SLOWER(100L), 
    HALFMIN(600L), 
    MIN(1200L), 
    QUARTER_HOUR(18000L);
    
    private long time;
    
    private UpdateTime(final long time) {
        this.time = time;
    }
    
    public long getTime() {
        return this.time;
    }
}
