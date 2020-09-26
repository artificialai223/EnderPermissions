

package me.TechsCode.EnderPermissions.gui.permissionEditor;

import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.base.dialog.UserInput;
import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.base.gui.Model;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import me.TechsCode.EnderPermissions.storage.objects.Permission;
import java.util.List;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.storage.objects.PermissionHolder;
import me.TechsCode.EnderPermissions.EnderPermissions;

public abstract class FullPermissionListView extends PermissionListView
{
    private final EnderPermissions plugin;
    private final PermissionHolder holder;
    
    public FullPermissionListView(final Player player, final EnderPermissions plugin, final PermissionHolder holder) {
        super(player, plugin, holder);
        this.plugin = plugin;
        this.holder = holder;
    }
    
    @Override
    public String getTitle() {
        return this.holder.getName() + " > Added Permissions";
    }
    
    @Override
    public PermissionWithInfo[] getObjects() {
        return Stream.concat(this.holder.getPermissions().stream().map((Function<? super Permission, ?>)Permission::getName).distinct().collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).stream(), this.holder.getAdditionalPermissions().stream().map((Function<? super Permission, ?>)Permission::getName).distinct().collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).stream()).map(s -> new PermissionWithInfo(s, this.plugin.getPermissionDatabase().getInfo(s))).sorted(Comparator.comparing((Function<? super Object, ? extends Comparable>)PermissionWithInfo::getName)).toArray(PermissionWithInfo[]::new);
    }
    
    @Override
    protected void construct(final Model model) {
        super.construct(model);
        model.button(this.getRightOptionSlot(), this::TypeInButton);
    }
    
    private void TypeInButton(final Button button) {
        button.material(XMaterial.NAME_TAG).name(Animation.wave("Add Permission", Colors.Blue, Colors.White)).lore("§bLeft Click §7to add a Permission via Chat");
        button.action(actionType -> {
            if (actionType == ActionType.LEFT) {
                new UserInput(this.p, this.plugin, "§bAdd Permission", "§7Type in a Permission via Chat") {
                    @Override
                    public void onClose(final Player player) {
                        FullPermissionListView.this.reopen();
                    }
                    
                    @Override
                    public boolean onResult(final String s) {
                        FullPermissionListView.this.holder.newPermission(s).create();
                        new PermissionView(FullPermissionListView.this.p, FullPermissionListView.this.plugin, FullPermissionListView.this.holder, new PermissionWithInfo(s, FullPermissionListView.this.plugin.getPermissionDatabase().getInfo(s))) {
                            @Override
                            public void onBack() {
                                FullPermissionListView.this.reopen();
                            }
                        };
                        return true;
                    }
                };
            }
            return;
        });
        if (this.plugin.getMySQLManager().isEnabled() && this.plugin.isConnectedToNetwork()) {
            button.item().appendLore("§bRight Click §7to add §cBungee Permission");
            button.action(actionType2 -> {
                if (actionType2 == ActionType.RIGHT) {
                    new UserInput(this.p, this.plugin, "§bAdd Bungee Permission", "§7Type in a Permission via Chat") {
                        @Override
                        public void onClose(final Player player) {
                            FullPermissionListView.this.reopen();
                        }
                        
                        @Override
                        public boolean onResult(final String s) {
                            FullPermissionListView.this.holder.newPermission(s).setServer("BungeeCord").create();
                            FullPermissionListView.this.reopen();
                            return true;
                        }
                    };
                }
            });
        }
    }
}
