

package me.TechsCode.EnderPermissions.gui.permissionEditor;

import java.util.function.Function;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import java.util.Optional;
import me.TechsCode.EnderPermissions.tpl.Tools;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.PermissionColor;
import java.util.List;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.base.gui.BasicSearch;
import me.TechsCode.EnderPermissions.base.gui.SearchFeature;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.storage.objects.PermissionHolder;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.gui.PageableGUI;

public abstract class PermissionListView extends PageableGUI<PermissionWithInfo>
{
    private final EnderPermissions plugin;
    private final PermissionHolder holder;
    private final long opened;
    
    public PermissionListView(final Player player, final EnderPermissions plugin, final PermissionHolder holder) {
        super(player, plugin);
        this.plugin = plugin;
        this.holder = holder;
        this.opened = System.currentTimeMillis();
    }
    
    @Override
    public SearchFeature<PermissionWithInfo> getSearch() {
        return new BasicSearch<PermissionWithInfo>() {
            @Override
            public String[] getSearchableText(final PermissionWithInfo permissionWithInfo) {
                return new String[] { permissionWithInfo.getName(), permissionWithInfo.hasInfo() ? permissionWithInfo.getInfo().getDescription() : "xxx" };
            }
        };
    }
    
    @Override
    public void construct(final Button button, final PermissionWithInfo permissionWithInfo) {
        final PermissionCopyList list = new PermissionCopyList(this.holder.getPermissions().name(permissionWithInfo.getName()), this.holder.getAdditionalPermissions().name(permissionWithInfo.getName()));
        if (list.size() == 0) {
            this.NotAddedPermission(button, permissionWithInfo);
        }
        else if (list.size() == 1) {
            if (list.inheritedCopies().size() == 1) {
                this.InheritedOnlyButton(button, permissionWithInfo, list.get(0));
            }
            else {
                this.SingleAddedPermissionButton(button, permissionWithInfo, list.get(0));
            }
        }
        else {
            this.MultiAddedPermissionButton(button, permissionWithInfo, list);
        }
        if (permissionWithInfo.hasInfo()) {
            button.item().appendLore("");
            button.item().appendLore(permissionWithInfo.getInfo().getLoreInfo());
        }
    }
    
    private void NotAddedPermission(final Button button, final PermissionWithInfo permissionWithInfo) {
        final PermissionColor not_ADDED = PermissionColor.NOT_ADDED;
        button.material(not_ADDED.getGlassPane()).name(not_ADDED.getChatColor() + "§l" + permissionWithInfo.getName().replace("[", "§7[").replace("]", "]" + not_ADDED.getChatColor() + "§l")).lore("§7Click to " + PermissionColor.ALL_SERVERS.getChatColor() + "Quick Add");
        if (permissionWithInfo.hasInfo() && permissionWithInfo.getInfo().hasPlaceholders()) {
            button.item().appendLore("", "§7Please note that");
            final String[] placeholders = permissionWithInfo.getInfo().getPlaceholders();
            for (int length = placeholders.length, i = 0; i < length; ++i) {
                button.item().appendLore("§7- §f[" + placeholders[i] + "] §7is a placeholder");
            }
        }
        button.action(actionType -> {
            if (actionType == ActionType.LEFT) {
                new PlaceholderFillDialog(this.p, this.plugin, permissionWithInfo.getName()) {
                    @Override
                    public void onBack() {
                        PermissionListView.this.reopen();
                    }
                    
                    @Override
                    public void onComplete(final String s) {
                        PermissionListView.this.holder.newPermission(s).create();
                        PermissionListView.this.reopen();
                    }
                };
            }
        });
    }
    
    private ArrayList<String> permissionSettingsLore(final PermissionCopy permissionCopy) {
        final ArrayList<String> list = new ArrayList<String>();
        final Optional<String> server = permissionCopy.getPermission().getServer();
        if (server.isPresent()) {
            String str;
            if (server.get().equals("BungeeCord")) {
                str = PermissionColor.BUNGEE_SERVER.getChatColor() + "BungeeCord";
            }
            else {
                str = "Server '" + PermissionColor.SPECIFIC_SERVER.getChatColor() + server.get() + "§7'";
            }
            list.add("§f\u2022 §7Only applying to " + str);
        }
        else {
            list.add("§f\u2022 §7Permission available on all Servers");
        }
        if (permissionCopy.getPermission().getWorld().isPresent()) {
            list.add("§f\u2022 §7Exclusive to the World '§e" + permissionCopy.getPermission().getWorld().get() + "§7'");
        }
        else {
            list.add("§f\u2022 §7Permission applies to every World");
        }
        if (permissionCopy.getPermission().getExpiration() != 0L) {
            list.add("§f\u2022 §7Will expire in §c" + Tools.getTimeString(permissionCopy.getPermission().getExpiration() - System.currentTimeMillis(), TimeUnit.MILLISECONDS, 2));
        }
        return list;
    }
    
    private void SingleAddedPermissionButton(final Button button, final PermissionWithInfo permissionWithInfo, final PermissionCopy permissionCopy) {
        final PermissionColor fromPermission = PermissionColor.fromPermission(permissionCopy);
        button.material(fromPermission.getGlassPane()).name((permissionCopy.getPermission().isPositive() ? "" : "§c-") + Animation.wave(permissionWithInfo.getName(), fromPermission.getChatColor(), Colors.White)).lore("§bLeft Click §7to edit this Permission", "§bRight Click §7to §cdelete", "");
        button.item().appendLore("§7Settings:");
        button.item().appendLore(this.permissionSettingsLore(permissionCopy));
        button.action(actionType -> {
            if (actionType == ActionType.LEFT) {
                new PermissionView(this.p, this.plugin, this.holder, permissionWithInfo) {
                    @Override
                    public void onBack() {
                        PermissionListView.this.reopen();
                    }
                };
            }
            if (actionType == ActionType.RIGHT) {
                permissionCopy.getPermission().remove();
                this.reopen();
            }
        });
    }
    
    private void MultiAddedPermissionButton(final Button button, final PermissionWithInfo permissionWithInfo, final List<PermissionCopy> list) {
        button.material(PermissionColor.cycle(list).getGlassBlock()).name("§f§l" + permissionWithInfo.getName() + " §f(See below)").lore("§7Click to view all Permission Copies", "").amount(list.size());
        button.item().appendLore(PermissionListLore.get(this.opened, list));
        button.action(p1 -> new PermissionView(this.p, this.plugin, this.holder, permissionWithInfo) {
            @Override
            public void onBack() {
                PermissionListView.this.reopen();
            }
        });
    }
    
    private void InheritedOnlyButton(final Button button, final PermissionWithInfo permissionWithInfo, final PermissionCopy permissionCopy) {
        button.material(PermissionColor.INHERITED.getGlassPane()).name(Animation.wave(permissionWithInfo.getName(), Colors.Yellow, Colors.White)).lore("§bLeft Click §7to view this Permission");
        button.action(p1 -> new PermissionView(this.p, this.plugin, this.holder, permissionWithInfo) {
            @Override
            public void onBack() {
                PermissionListView.this.reopen();
            }
        });
        if (permissionCopy.getPermission().isPositive()) {
            button.item().appendLore("§bRight Click §7to §cQuick-Negate §7it");
            button.action(actionType -> {
                if (actionType == ActionType.RIGHT) {
                    this.holder.newPermission(permissionWithInfo.getName()).setPositive(false).setServer(permissionCopy.getPermission().getServer().orElse(null)).setWorld(permissionCopy.getPermission().getWorld().orElse(null)).create();
                    this.reopen();
                }
                return;
            });
        }
        button.item().appendLore("", "§7This Permission originates from §a" + permissionCopy.getPermission().getHolder().get().map((Function<? super PermissionHolder, ? extends String>)PermissionHolder::getName).orElse("INVALID"), "", "§7Settings:");
        button.item().appendLore(this.permissionSettingsLore(permissionCopy));
    }
}
