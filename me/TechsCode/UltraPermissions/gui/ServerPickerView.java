

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.base.networking.NetworkData;
import me.TechsCode.EnderPermissions.base.gui.ActionType;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.base.networking.NPlayer;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.base.gui.BasicSearch;
import me.TechsCode.EnderPermissions.base.gui.SearchFeature;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import java.util.Optional;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.networking.NServer;
import me.TechsCode.EnderPermissions.base.gui.PageableGUI;

public abstract class ServerPickerView extends PageableGUI<NServer>
{
    private EnderPermissions plugin;
    private Optional<NServer> thisServer;
    
    public ServerPickerView(final Player player, final EnderPermissions plugin) {
        super(player, plugin);
        this.plugin = plugin;
        this.thisServer = plugin.getThisServer();
    }
    
    public abstract void onSelect(final NServer p0);
    
    @Override
    public SearchFeature<NServer> getSearch() {
        return new BasicSearch<NServer>() {
            @Override
            public String[] getSearchableText(final NServer nServer) {
                return new String[] { nServer.getName() };
            }
        };
    }
    
    @Override
    public NServer[] getObjects() {
        return this.plugin.getNetworkManager().getData().map(networkData -> networkData.getServerList().toArray(new NServer[0])).orElseGet(() -> new NServer[0]);
    }
    
    @Override
    public void construct(final Button button, final NServer nServer) {
        final boolean b = this.thisServer.isPresent() && this.thisServer.get().getName().equals(nServer.getName());
        button.material(XMaterial.BLUE_STAINED_GLASS).name(Animation.wave(nServer.getName(), Colors.DarkBlue, Colors.White)).lore("§7Click to select");
        if (b) {
            button.item().appendLore("", "§aYou are currently on this Server");
        }
        button.item().appendLore("", "§7Address: §e" + nServer.getIp() + "§7:§e" + nServer.getPort(), "", "§7Online Players:");
        final Iterator<NPlayer> iterator = nServer.getPlayers().iterator();
        while (iterator.hasNext()) {
            button.item().appendLore("§7- §f" + iterator.next().getName());
        }
        if (nServer.getPlayers().isEmpty()) {
            button.item().appendLore("§cNone");
        }
        button.action(p1 -> this.onSelect(nServer));
    }
}
