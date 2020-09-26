

package me.TechsCode.EnderPermissions.base.update;

import java.util.ArrayList;
import java.io.IOException;
import me.TechsCode.EnderPermissions.dependencies.commons.io.IOUtils;
import java.net.URL;
import me.TechsCode.EnderPermissions.base.misc.Callback;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import java.util.List;

public class Updater
{
    private static final List<Player> askedPlayers;
    
    public static void suggestUpdateIfAvailable(final SpigotTechPlugin spigotTechPlugin, final Player player, final String s, final Callback<Player> callback, final boolean b) {
        if (!b && Updater.askedPlayers.contains(player)) {
            return;
        }
        final UpdateState updateState;
        final String s2;
        final String s3;
        spigotTechPlugin.getScheduler().runAsync(() -> {
            getUpdateState(spigotTechPlugin, s);
            if (updateState != UpdateState.UP_TO_DATE && updateState != UpdateState.UNKNOWN) {
                Updater.askedPlayers.add(player);
                spigotTechPlugin.getScheduler().run(() -> {
                    spigotTechPlugin.getVersion();
                    getNewestVersionOnServer(spigotTechPlugin, s);
                    new UpdateView(player, spigotTechPlugin, s2, s3) {
                        final /* synthetic */ SpigotTechPlugin val$plugin;
                        final /* synthetic */ String val$updateServer;
                        final /* synthetic */ String val$globalVersion;
                        final /* synthetic */ Callback val$onStay;
                        
                        @Override
                        public void onResponse(final boolean b) {
                            if (b) {
                                new UpdateProcess(this.val$plugin, this.p, this.val$updateServer, this.val$globalVersion, closeReason -> {
                                    if (closeReason == UpdateProcess.CloseReason.NO_REQUIREMENTS) {
                                        Updater.askedPlayers.remove(this.p);
                                    }
                                });
                            }
                            else {
                                this.val$onStay.run(this.p);
                                this.p.closeInventory();
                            }
                        }
                    };
                });
            }
        });
    }
    
    private static UpdateState getUpdateState(final SpigotTechPlugin spigotTechPlugin, final String s) {
        final String version = spigotTechPlugin.getVersion();
        final String newestVersionOnServer = getNewestVersionOnServer(spigotTechPlugin, s);
        if (newestVersionOnServer == null) {
            return UpdateState.UNKNOWN;
        }
        return version.equalsIgnoreCase(newestVersionOnServer) ? UpdateState.UP_TO_DATE : UpdateState.OUTDATED;
    }
    
    private static String getNewestVersionOnServer(final SpigotTechPlugin spigotTechPlugin, final String str) {
        final String string = str + "/" + spigotTechPlugin.getName() + "/version";
        try {
            final String string2 = IOUtils.toString(new URL(string), "UTF-8");
            if (string2.contains(".")) {
                return string2;
            }
            return null;
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    static {
        askedPlayers = new ArrayList<Player>();
    }
    
    enum UpdateState
    {
        UP_TO_DATE, 
        OUTDATED, 
        UNKNOWN;
    }
}
