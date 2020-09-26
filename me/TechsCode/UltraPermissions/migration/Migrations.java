

package me.TechsCode.EnderPermissions.migration;

import org.bukkit.Bukkit;
import java.util.Arrays;

public enum Migrations
{
    LuckPerms((Class<? extends MigrationAssistant>)LuckPermsMigration.class), 
    PermissionsEx((Class<? extends MigrationAssistant>)PermissionsExMigration.class);
    
    private Class<? extends MigrationAssistant> migrationClass;
    
    private Migrations(final Class<? extends MigrationAssistant> migrationClass) {
        this.migrationClass = migrationClass;
    }
    
    public static MigrationAssistant getMigrationAssistant() {
        final Migrations migrations2 = Arrays.stream(values()).filter(migrations -> Bukkit.getPluginManager().isPluginEnabled(migrations.name())).findFirst().orElse(null);
        if (migrations2 == null) {
            return null;
        }
        try {
            return (MigrationAssistant)migrations2.migrationClass.newInstance();
        }
        catch (InstantiationException | IllegalAccessException ex) {
            final Throwable t;
            t.printStackTrace();
            return null;
        }
    }
    
    public static boolean isAvailable() {
        return Arrays.stream(values()).anyMatch(migrations -> Bukkit.getPluginManager().isPluginEnabled(migrations.name()));
    }
}
