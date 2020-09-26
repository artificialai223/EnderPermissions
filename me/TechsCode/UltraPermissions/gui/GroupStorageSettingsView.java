

package me.TechsCode.EnderPermissions.gui;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.networking.NServer;
import java.util.Optional;
import org.bukkit.World;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.tpl.Common;
import me.TechsCode.EnderPermissions.UPCommon;
import me.TechsCode.EnderPermissions.base.gui.Model;
import me.TechsCode.EnderPermissions.T;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.gui.GUI;

public abstract class GroupStorageSettingsView extends GUI
{
    private final EnderPermissions plugin;
    private final Group group;
    
    public GroupStorageSettingsView(final Player player, final EnderPermissions plugin, final Group group) {
        super(player, plugin);
        this.plugin = plugin;
        this.group = group;
    }
    
    public abstract void onBack();
    
    @Override
    public int getCurrentSlots() {
        return 36;
    }
    
    @Override
    public String getCurrentTitle() {
        return this.group.getName() + " > " + T.GUI_STORAGESETTINGS_TITLE;
    }
    
    @Override
    protected void construct(final Model model) {
        model.button(12, this::WorldButton);
        if (this.plugin.isConnectedToNetwork()) {
            model.button(16, this::ServerButton);
        }
        else {
            model.button(16, button -> UPCommon.NotAvailableButton(button, T.SERVER.toString(), "it is only available for Networks"));
        }
        model.button(32, button2 -> Common.BackButton(button2, p0 -> this.onBack()));
    }
    
    private void WorldButton(final Button button) {
        final Optional<String> world = this.group.getWorld();
        button.material(world.isPresent() ? XMaterial.YELLOW_STAINED_GLASS : XMaterial.GRAY_STAINED_GLASS_PANE).name(Animation.wave(T.WORLD.toString(), Colors.Yellow, Colors.White)).lore(world.isPresent() ? "§7Click to allow this Group on all Worlds" : "§7Click to restrict this Group to a certain World");
        world.ifPresent(str -> button.item().appendLore("", "§7World: §e" + str));
        button.action(p1 -> {
            if (world.isPresent()) {
                this.group.setWorld(null);
            }
            else {
                new WorldPickerView(this.p, this.plugin) {
                    @Override
                    public void onSelect(final World world) {
                        GroupStorageSettingsView.this.group.setWorld(world.getName());
                        this.onBack();
                    }
                    
                    @Override
                    public String getTitle() {
                        return GroupStorageSettingsView.this.group.getName() + " > World";
                    }
                    
                    @Override
                    public void onBack() {
                        GroupStorageSettingsView.this.reopen();
                    }
                };
            }
        });
    }
    
    private void ServerButton(final Button button) {
        final Optional<String> server = this.group.getServer();
        button.material(server.isPresent() ? XMaterial.BLUE_STAINED_GLASS : XMaterial.GRAY_STAINED_GLASS_PANE).name(Animation.wave(T.SERVER.toString(), Colors.DarkBlue, Colors.White)).lore(server.isPresent() ? "§7Click to allow this Group on all Servers" : "§7Click to restrict this Group to a certain Server");
        server.ifPresent(str -> button.item().appendLore("", "§7Server: §9" + str));
        button.action(p1 -> {
            if (server.isPresent()) {
                this.group.setServer(null);
            }
            else {
                new ServerPickerView(this.p, this.plugin) {
                    @Override
                    public void onSelect(final NServer nServer) {
                        GroupStorageSettingsView.this.group.setServer(nServer.getName());
                        this.onBack();
                    }
                    
                    @Override
                    public String getTitle() {
                        return GroupStorageSettingsView.this.group.getName() + " > Server";
                    }
                    
                    @Override
                    public void onBack() {
                        GroupStorageSettingsView.this.reopen();
                    }
                };
            }
        });
    }
}
