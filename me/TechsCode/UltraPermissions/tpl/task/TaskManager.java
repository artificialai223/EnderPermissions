

package me.TechsCode.EnderPermissions.tpl.task;

import org.bukkit.event.Event;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;

public class TaskManager
{
    public TaskManager(final SpigotTechPlugin spigotTechPlugin) {
        final UpdateTime[] values = UpdateTime.values();
        for (int length = values.length, i = 0; i < length; ++i) {
            final UpdateTime updateTime;
            spigotTechPlugin.getScheduler().runTaskTimer(() -> Bukkit.getPluginManager().callEvent((Event)new UpdateEvent(updateTime)), 0L, values[i].getTime());
        }
    }
}
