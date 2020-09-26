

package me.TechsCode.EnderPermissions.gui.permissionEditor;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import java.util.function.Function;
import org.bukkit.Material;
import org.bukkit.World;
import me.TechsCode.EnderPermissions.gui.WorldPickerView;
import me.TechsCode.EnderPermissions.base.networking.NServer;
import me.TechsCode.EnderPermissions.gui.ServerPickerView;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.storage.objects.Permission;
import org.bukkit.enchantments.Enchantment;
import me.TechsCode.EnderPermissions.tpl.Tools;
import java.util.concurrent.TimeUnit;
import me.TechsCode.EnderPermissions.PermissionColor;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.tpl.Common;
import me.TechsCode.EnderPermissions.base.gui.Model;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import java.util.List;
import me.TechsCode.EnderPermissions.storage.objects.PermissionHolder;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.gui.GUI;

public abstract class PermissionView extends GUI
{
    private final EnderPermissions plugin;
    private final PermissionHolder holder;
    private final PermissionWithInfo permissionWithInfo;
    private List<PermissionCopy> permissionCopies;
    private int currentCopy;
    
    public PermissionView(final Player player, final EnderPermissions plugin, final PermissionHolder holder, final PermissionWithInfo permissionWithInfo) {
        super(player, plugin);
        this.plugin = plugin;
        this.holder = holder;
        this.permissionWithInfo = permissionWithInfo;
        this.reloadPermissionCopies();
    }
    
    private void reloadPermissionCopies() {
        this.permissionCopies = new ArrayList<PermissionCopy>();
        this.holder.getPermissions().stream().filter(permission -> this.permissionWithInfo.isThisPermission(permission.getName())).forEach(permission2 -> this.permissionCopies.add(new PermissionCopy(permission2, false)));
        this.holder.getAdditionalPermissions().stream().filter(permission3 -> this.permissionWithInfo.isThisPermission(permission3.getName())).forEach(permission4 -> this.permissionCopies.add(new PermissionCopy(permission4, true)));
        this.currentCopy = Math.min(this.permissionCopies.size(), this.currentCopy + 1) - 1;
    }
    
    @Override
    protected void construct(final Model model) {
        model.setTitle(this.holder.getName() + " > " + this.permissionWithInfo.getName());
        model.setSlots(45);
        for (int i = 0; i <= 8; ++i) {
            final int n;
            model.button(i + 1, (this.permissionCopies.size() > i) ? (button2 -> this.CopyTabButton(button2, this.permissionCopies.get(n), n)) : this::PlaceholderTabButton);
        }
        if (this.permissionCopies.size() < 8) {
            model.button(9, this::CreateNewCopyButton);
        }
        if (!this.permissionCopies.isEmpty()) {
            if (this.permissionCopies.get(this.currentCopy).isInherited()) {
                final PermissionCopy permissionCopy;
                model.button(23, button3 -> this.JumpButton(button3, permissionCopy.getPermission()));
            }
            else {
                final PermissionCopy permissionCopy;
                model.button(20, button4 -> this.ServerButton(button4, permissionCopy.getPermission()));
                model.button(22, button5 -> this.WorldButton(button5, permissionCopy.getPermission()));
                model.button(24, button6 -> this.PolarityButton(button6, permissionCopy.getPermission()));
                model.button(26, button7 -> this.DeleteButton(button7, permissionCopy.getPermission()));
            }
        }
        model.button(41, button -> Common.BackButton(button, p0 -> this.onBack()));
    }
    
    private void CopyTabButton(final Button button, final PermissionCopy permissionCopy, final int currentCopy) {
        final Permission permission = permissionCopy.getPermission();
        final PermissionColor fromPermission = PermissionColor.fromPermission(permissionCopy);
        final boolean b = currentCopy == this.currentCopy;
        button.material(fromPermission.getGlassPane()).name((permission.isPositive() ? "" : "§c-") + fromPermission.getChatColor() + "§l" + permission.getName()).lore(b ? "§7This copy is currently §eselected" : "§7Click to §eselect§7 this copy", "", "§7Server: §e" + this.getServer(permission), "§7World: §e" + this.getWorld(permission), "", (permissionCopy.getPermission().getExpiration() != 0L) ? ("§7Expiring in §e" + Tools.getTimeString(permission.getExpiration(), TimeUnit.MILLISECONDS, 2)) : "§7This permission is §cpermanent §7and cannot expire");
        if (b) {
            button.item().addEnchantment(Enchantment.LUCK, 3);
        }
        button.action(p1 -> this.currentCopy = currentCopy);
    }
    
    private void CreateNewCopyButton(final Button button) {
        button.material(XMaterial.ANVIL).name(Animation.wave("Create new Copy", Colors.Orange, Colors.White)).lore("§7Click to create a new copy of this permission");
        button.action(p0 -> {
            this.holder.newPermission(this.permissionWithInfo.getName()).create();
            this.reloadPermissionCopies();
        });
    }
    
    private void PlaceholderTabButton(final Button button) {
        button.material(XMaterial.GRAY_STAINED_GLASS_PANE).name("§f");
    }
    
    private void ServerButton(final Button button, final Permission permission) {
        button.material(XMaterial.ENDER_CHEST).name(Animation.wave("Server", Colors.Orange, Colors.White)).lore(permission.getServer().isPresent() ? "§7Click to §aallow §7permission on all Servers" : "§7Click to §crestrict §7permission to a certain Server", "", permission.getServer().isPresent() ? ("§7Permission only available on Server §e" + permission.getServer().get()) : "§7Permission is available to all Servers");
        button.action(p1 -> {
            if (permission.getServer().isPresent()) {
                permission.setServer(null);
            }
            else {
                new ServerPickerView(this.p, this.plugin) {
                    final /* synthetic */ Permission val$permission;
                    
                    @Override
                    public void onSelect(final NServer nServer) {
                        this.val$permission.setServer(nServer.getName());
                        this.onBack();
                    }
                    
                    @Override
                    public String getTitle() {
                        return this.val$permission.getName() + " > Server";
                    }
                    
                    @Override
                    public void onBack() {
                        PermissionView.this.reopen();
                    }
                };
            }
        });
    }
    
    private void WorldButton(final Button button, final Permission permission) {
        button.material(XMaterial.CHEST).name(Animation.wave("World", Colors.Orange, Colors.White)).lore(permission.getWorld().isPresent() ? "§7Click to §aallow §7permission on all Worlds" : "§7Click to §crestrict §7permission to a certain World", "", permission.getWorld().isPresent() ? ("§7Permission only available on World §e" + permission.getWorld().get()) : "§7Permission is available to all Worlds");
        button.action(p1 -> {
            if (permission.getWorld().isPresent()) {
                permission.setWorld(null);
            }
            else {
                new WorldPickerView(this.p, this.plugin) {
                    final /* synthetic */ Permission val$permission;
                    
                    @Override
                    public void onSelect(final World world) {
                        this.val$permission.setWorld(world.getName());
                        this.onBack();
                    }
                    
                    @Override
                    public String getTitle() {
                        return this.val$permission.getName() + " > World";
                    }
                    
                    @Override
                    public void onBack() {
                        PermissionView.this.reopen();
                    }
                };
            }
        });
    }
    
    private void PolarityButton(final Button button, final Permission permission) {
        button.material(XMaterial.REPEATER).name(Animation.wave("Polarity", Colors.Orange, Colors.White)).lore(permission.isPositive() ? "§7Click to make this Permission §cnegative" : "§7Click to make this a §aregular §7Permission", "", "§7This Permissions is currently " + (permission.isPositive() ? "positve" : "negative"));
        button.action(p1 -> permission.setPositive(!permission.isPositive()));
    }
    
    private void DeleteButton(final Button button, final Permission permission) {
        button.material(Material.REDSTONE_BLOCK).name(Animation.wave("Delete", Colors.Red, Colors.White)).lore("§7Click to delete this copy");
        button.action(p1 -> {
            permission.remove();
            if (this.permissionCopies.size() == 1) {
                this.onBack();
            }
            else {
                this.reloadPermissionCopies();
            }
        });
    }
    
    private void JumpButton(final Button button, final Permission permission) {
        button.material(XMaterial.STICKY_PISTON).name(Animation.wave("Permission Inherited", Colors.Orange, Colors.White)).lore("§7Click to jump to Group", "", "§7This permission is inherited from §e" + permission.getHolder().get().map((Function<? super PermissionHolder, ? extends String>)PermissionHolder::getName).orElse("INVALID"), "§7and can be only edited when jumping to that group");
        button.action(p1 -> permission.getHolder().get().ifPresent(permissionHolder -> new PermissionEditorMainView(this.p, this.plugin, permissionHolder) {
            @Override
            public void onBack() {
                PermissionView.this.reopen();
            }
        }));
    }
    
    private String getServer(final Permission permission) {
        if (!permission.getServer().isPresent()) {
            return "All Servers";
        }
        if (permission.getServer().get().equals("BungeeCord")) {
            return "§cBungeeCord";
        }
        if (permission.getServer().get().equals(this.plugin.getThisServer().map((Function<? super NServer, ?>)NServer::getName).orElse(null))) {
            return "This Server";
        }
        return permission.getServer().get();
    }
    
    private String getWorld(final Permission permission) {
        return permission.getWorld().orElse("All Worlds");
    }
    
    public abstract void onBack();
}
