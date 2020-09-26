

package me.TechsCode.EnderPermissions.gui.permissionEditor;

import me.TechsCode.EnderPermissions.storage.collection.PermissionList;
import java.util.function.Supplier;
import me.TechsCode.EnderPermissions.permissionDatabase.PermissionInfoList;
import me.TechsCode.EnderPermissions.base.dialog.UserInput;
import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.PermissionColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.tpl.Common;
import me.TechsCode.EnderPermissions.base.gui.Model;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Function;
import org.bukkit.plugin.Plugin;
import java.util.Arrays;
import org.bukkit.Bukkit;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import org.bukkit.entity.Player;
import java.util.List;
import me.TechsCode.EnderPermissions.storage.objects.PermissionHolder;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.gui.GUI;

public abstract class PermissionEditorMainView extends GUI
{
    private final int[] layout;
    private final EnderPermissions plugin;
    private final PermissionHolder holder;
    private final long opened;
    private final List<PluginEntry> entries;
    private int page;
    
    public PermissionEditorMainView(final Player player, final EnderPermissions plugin, final PermissionHolder holder) {
        super(player, plugin);
        this.layout = new int[] { 11, 12, 13, 14, 15, 16, 17, 20, 21, 22, 23, 24, 25, 26, 29, 30, 31, 32, 33, 34, 35 };
        this.plugin = plugin;
        this.holder = holder;
        this.opened = System.currentTimeMillis();
        this.entries = Stream.concat(Arrays.stream(Bukkit.getPluginManager().getPlugins()).map((Function<? super Plugin, ?>)Plugin::getName), (Stream<?>)Arrays.stream((T[])new String[] { "Bukkit" })).sorted().map(s -> new PluginEntry(plugin, s)).filter(pluginEntry -> !pluginEntry.permissionsFromDatabase.isEmpty()).collect((Collector<? super Object, ?, List<PluginEntry>>)Collectors.toList());
        this.page = 0;
    }
    
    @Override
    public void reopen() {
        super.reopen();
        this.entries.forEach(PluginEntry::retrieveData);
    }
    
    @Override
    protected void construct(final Model model) {
        model.setTitle(this.holder.getName() + " > Permissions");
        model.setSlots(54);
        int n = this.page * this.layout.length;
        final int[] layout = this.layout;
        for (int length = layout.length, i = 0; i < length; ++i) {
            final PluginEntry pluginEntry;
            model.button(layout[i], (((this.entries.size() > n) ? this.entries.get(n) : null) != null) ? (button2 -> this.PluginWithPermissionsButton(button2, pluginEntry)) : this::PlaceholderButton);
            ++n;
        }
        if (this.page != 0) {
            model.button(10, this::PreviousButton);
        }
        if (this.entries.size() / this.layout.length > this.page) {
            model.button(18, this::NextButton);
        }
        model.button(47, this::SearchButton);
        model.button(50, button -> Common.BackButton(button, p0 -> this.onBack()));
        model.button(52, this::AddedPermissionButton);
        model.button(53, this::TypeInButton);
    }
    
    private void PluginWithPermissionsButton(final Button button, final PluginEntry pluginEntry) {
        if (pluginEntry.permissionCopies.isEmpty()) {
            button.material(XMaterial.WHITE_STAINED_GLASS_PANE);
        }
        else if (pluginEntry.permissionCopies.size() == 1) {
            button.material(PermissionColor.fromPermission(pluginEntry.permissionCopies.get(0)).getGlassPane());
        }
        else {
            button.material(PermissionColor.cycle(pluginEntry.permissionCopies).getGlassBlock());
        }
        button.item().amount(Math.max(1, pluginEntry.permissionCopies.size())).name(Animation.wave(pluginEntry.name, PermissionColor.ALL_SERVERS.getChatColor(), Colors.White)).lore("§7Click to view Permissions", "");
        if (!pluginEntry.permissionCopies.isEmpty()) {
            button.item().appendLore("§7Added Permissions:");
        }
        button.item().appendLore(PermissionListLore.get(this.opened, pluginEntry.permissionCopies));
        button.item().appendLore("", "§7Found §e" + pluginEntry.permissionsFromDatabase.size() + " Permissions §7for this plugin", "§7in the Permission Database");
        button.action(p1 -> new PluginPermissionListView(this.p, this.plugin, this.holder, pluginEntry) {
            @Override
            public void onBack() {
                PermissionEditorMainView.this.reopen();
            }
        });
    }
    
    private void PlaceholderButton(final Button button) {
        button.material(XMaterial.GRAY_STAINED_GLASS_PANE).name("§f");
    }
    
    private void NextButton(final Button button) {
        button.material(XMaterial.ARROW).name(Animation.wave("Next", Colors.DarkCyan, Colors.Cyan)).lore("§7Click to go to the next page");
        button.action(p0 -> ++this.page);
    }
    
    private void PreviousButton(final Button button) {
        button.material(XMaterial.ARROW).name(Animation.wave("Previous", Colors.DarkCyan, Colors.Cyan)).lore("§7Click to go to the previous page");
        button.action(p0 -> --this.page);
    }
    
    private void AddedPermissionButton(final Button button) {
        button.material(XMaterial.PAPER).name(Animation.wave("All Permissions", Colors.DarkCyan, Colors.Cyan)).lore("§7Click to show all added Permissions");
        button.item().amount(Math.min(64, this.holder.getPermissions().size() + this.holder.getAdditionalPermissions().size()));
        button.action(p0 -> new FullPermissionListView(this.p, this.plugin, this.holder) {
            @Override
            public void onBack() {
                PermissionEditorMainView.this.reopen();
            }
        });
    }
    
    private void TypeInButton(final Button button) {
        button.material(XMaterial.NAME_TAG).name(Animation.wave("Add Permission", Colors.DarkCyan, Colors.Cyan)).lore("§bLeft Click §7to add a Permission via Chat");
        button.action(actionType -> {
            if (actionType == ActionType.LEFT) {
                new UserInput(this.p, this.plugin, "§bAdd Permission", "§7Type in a Permission via Chat") {
                    @Override
                    public void onClose(final Player player) {
                        PermissionEditorMainView.this.reopen();
                    }
                    
                    @Override
                    public boolean onResult(final String s) {
                        PermissionEditorMainView.this.holder.newPermission(s).create();
                        new PermissionView(PermissionEditorMainView.this.p, PermissionEditorMainView.this.plugin, PermissionEditorMainView.this.holder, new PermissionWithInfo(s, PermissionEditorMainView.this.plugin.getPermissionDatabase().getInfo(s))) {
                            @Override
                            public void onBack() {
                                new FullPermissionListView(this.p, PermissionEditorMainView.this.plugin, PermissionEditorMainView.this.holder) {
                                    @Override
                                    public void onBack() {
                                        PermissionEditorMainView.this.reopen();
                                    }
                                };
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
                            PermissionEditorMainView.this.reopen();
                        }
                        
                        @Override
                        public boolean onResult(final String s) {
                            PermissionEditorMainView.this.holder.newPermission(s).setServer("BungeeCord").create();
                            new FullPermissionListView(PermissionEditorMainView.this.p, PermissionEditorMainView.this.plugin, PermissionEditorMainView.this.holder) {
                                @Override
                                public void onBack() {
                                    PermissionEditorMainView.this.reopen();
                                }
                            };
                            return true;
                        }
                    };
                }
            });
        }
    }
    
    private void SearchButton(final Button button) {
        button.material(XMaterial.COMPASS).name(Animation.wave("Search a Permission", Colors.DarkCyan, Colors.Cyan)).lore("§7Click to search for a Permission");
        button.action(p0 -> new UserInput(this.p, this.plugin, "§bSearch for Permission", "§7Type in a §eSearch Term §7in Chat to search") {
            @Override
            public void onClose(final Player player) {
                PermissionEditorMainView.this.reopen();
            }
            
            @Override
            public boolean onResult(final String s) {
                new SearchPermissionListView(PermissionEditorMainView.this.p, PermissionEditorMainView.this.plugin, PermissionEditorMainView.this.holder, s) {
                    @Override
                    public void onBack() {
                        PermissionEditorMainView.this.reopen();
                    }
                };
                return true;
            }
        });
    }
    
    public abstract void onBack();
    
    class PluginEntry
    {
        private final EnderPermissions plugin;
        public final String name;
        public PermissionInfoList permissionsFromDatabase;
        public PermissionCopyList permissionCopies;
        
        public PluginEntry(final EnderPermissions plugin, final String name) {
            this.plugin = plugin;
            this.name = name;
            this.retrieveData();
        }
        
        public void retrieveData() {
            final PermissionList permissions = PermissionEditorMainView.this.holder.getPermissions();
            final PermissionList additionalPermissions = PermissionEditorMainView.this.holder.getAdditionalPermissions();
            this.permissionsFromDatabase = PermissionEditorMainView.this.plugin.getPermissionDatabase().getPermissionInfosFromPlugin(this.name);
            this.permissionCopies = new PermissionCopyList(permissions, additionalPermissions).stream().filter(permissionCopy -> this.permissionsFromDatabase.find(permissionCopy.getPermission().getName()).isPresent()).collect((Collector<? super PermissionCopy, ?, PermissionCopyList>)Collectors.toCollection((Supplier<R>)PermissionCopyList::new));
        }
    }
}
