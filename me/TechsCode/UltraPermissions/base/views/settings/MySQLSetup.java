

package me.TechsCode.EnderPermissions.base.views.settings;

import me.TechsCode.EnderPermissions.base.dialog.UserInput;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;

abstract class MySQLSetup
{
    private final Player p;
    private final SpigotTechPlugin plugin;
    private int stageNum;
    
    public MySQLSetup(final Player p2, final SpigotTechPlugin plugin) {
        this.p = p2;
        this.plugin = plugin;
        this.stageNum = -1;
        this.nextStage();
    }
    
    public void nextStage() {
        ++this.stageNum;
        final SetupStage[] stages = this.getStages();
        if (this.stageNum >= stages.length) {
            this.onCompletion();
            return;
        }
        final SetupStage setupStage = stages[this.stageNum];
        new UserInput(this.p, this.plugin, "§f" + setupStage.getFieldName() + " §7(§eMySQL Setup§7)", "§7Enter the Credentials in Chat", "§7Default: §a" + setupStage.getDefaultValue()) {
            @Override
            public void onClose(final Player player) {
                MySQLSetup.this.onClose();
            }
            
            @Override
            public boolean onResult(final String s) {
                setupStage.getSetFunction().set(s);
                MySQLSetup.this.nextStage();
                return true;
            }
        };
    }
    
    public abstract void onClose();
    
    public abstract void onCompletion();
    
    public abstract SetupStage[] getStages();
}
