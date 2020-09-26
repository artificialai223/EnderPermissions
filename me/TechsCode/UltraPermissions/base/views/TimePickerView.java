

package me.TechsCode.EnderPermissions.base.views;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.tpl.Tools;
import me.TechsCode.EnderPermissions.base.gui.Model;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.base.gui.GUI;

public abstract class TimePickerView extends GUI
{
    private long time;
    private String titlePrefix;
    private String noTimePhrase;
    private boolean noTimeContinue;
    
    public TimePickerView(final Player player, final SpigotTechPlugin spigotTechPlugin, final String titlePrefix, final String noTimePhrase, final boolean noTimeContinue) {
        super(player, spigotTechPlugin);
        this.time = 0L;
        this.titlePrefix = titlePrefix;
        this.noTimePhrase = noTimePhrase;
        this.noTimeContinue = noTimeContinue;
    }
    
    @Override
    protected void construct(final Model model) {
        model.setTitle(this.titlePrefix + " " + ((this.time == 0L) ? this.noTimePhrase : Tools.getTimeString(this.time)));
        model.setSlots(54);
        for (final Step step : Step.values()) {
            if (this.time + step.baseIncrement >= 0L) {
                model.button(step.slot, (step.baseIncrement > 0L) ? (button -> this.IncrementButton(button, step)) : (button2 -> this.DecrementButton(button2, step)));
            }
        }
        if (this.time != 0L || this.noTimeContinue) {
            model.button(23, this::ConfirmButton);
        }
    }
    
    private void IncrementButton(final Button button, final Step step) {
        button.material(XMaterial.STONE_BUTTON).name(Animation.wave(step.name, Colors.Green, Colors.White)).lore("§7Click to add");
        button.action(p1 -> this.time += step.baseIncrement);
    }
    
    private void DecrementButton(final Button button, final Step step) {
        button.material(XMaterial.OAK_BUTTON).name(Animation.wave(step.name, Colors.Red, Colors.White)).lore("§7Click to remove");
        button.action(p1 -> this.time += step.baseIncrement);
    }
    
    private void ConfirmButton(final Button button) {
        button.material(XMaterial.EMERALD_BLOCK).name(Animation.wave("Confirm", Colors.Green, Colors.White)).lore("§7Click to confirm");
        button.action(p0 -> this.onResult(this.time));
    }
    
    public abstract void onResult(final long p0);
    
    public enum Step
    {
        REMOVE_SECOND("Remove 1 Second", -1L, 11), 
        REMOVE_MINUTE("Remove 1 Minute", -60L, 20), 
        REMOVE_HOUR("Remove 1 Hour", -3600L, 29), 
        REMOVE_DAY("Remove 1 Day", -86400L, 38), 
        ADD_SECOND("Add 1 Second", 1L, 17), 
        ADD_MINUTE("Add 1 Minute", 60L, 26), 
        ADD_HOUR("Add 1 Hour", 3600L, 35), 
        ADD_DAY("Add 1 Day", 86400L, 44);
        
        String name;
        long baseIncrement;
        int slot;
        
        private Step(final String name2, final long baseIncrement, final int slot) {
            this.name = name2;
            this.baseIncrement = baseIncrement;
            this.slot = slot;
        }
    }
}
