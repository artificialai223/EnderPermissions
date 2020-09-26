

package me.TechsCode.EnderPermissions.base.gui;

import java.util.Iterator;
import me.TechsCode.EnderPermissions.tpl.Common;
import me.TechsCode.EnderPermissions.base.dialog.UserInput;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.translations.TBase;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;

public abstract class PageableGUI<OBJ> extends GUI
{
    private final int maxObjectsPerPage = 36;
    private SpigotTechPlugin plugin;
    private int page;
    private int pages;
    private String searchTerm;
    
    public PageableGUI(final Player player, final SpigotTechPlugin plugin) {
        super(player, plugin);
        this.page = 0;
        this.searchTerm = null;
        this.plugin = plugin;
    }
    
    public abstract String getTitle();
    
    @Override
    protected void construct(final Model model) {
        model.setTitle(this.getTitle());
        model.setSlots(54);
        final SearchFeature<OBJ> search = this.getSearch();
        final SearchFeature<Object> searchFeature;
        final String[] array;
        int length;
        int i = 0;
        String s;
        final List<? super Object> list = Arrays.stream(this.getObjects()).filter(o -> {
            if (searchFeature == null) {
                return true;
            }
            else if (this.searchTerm == null) {
                return true;
            }
            else {
                searchFeature.getSearchableText(o);
                length = array.length;
                while (i < length) {
                    s = array[i];
                    if (s != null && s.toLowerCase().contains(this.searchTerm.toLowerCase())) {
                        return true;
                    }
                    else {
                        ++i;
                    }
                }
                return false;
            }
        }).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
        final int min = Math.min(list.size(), this.page * 36);
        final List<Object> subList = list.subList(min, Math.min(list.size(), min + 36));
        int n = 1;
        while (subList.iterator().hasNext()) {
            final Iterator<OBJ> iterator;
            model.button(button4 -> this.construct(button4, iterator.next()), n);
            ++n;
        }
        this.pages = (int)Math.round(Math.ceil(list.size() / 36.0));
        if (this.page != 0) {
            model.button(button -> {
                button.material(XMaterial.ARROW).name(Animation.fading(TBase.GUI_PAGEABLE_PREVIOUS_TITLE.toString(), Colors.DarkCyan, Colors.Cyan)).lore(TBase.GUI_PAGEABLE_PREVIOUS_ACTION.get().toString());
                button.action(p0 -> {
                    --this.page;
                    if (this.page < 0) {
                        this.page = 0;
                    }
                });
                return;
            }, 46);
        }
        if (this.page + 1 < this.pages) {
            model.button(button2 -> {
                button2.material(XMaterial.ARROW).name(Animation.fading(TBase.GUI_PAGEABLE_NEXT_TITLE.toString(), Colors.DarkCyan, Colors.Cyan)).lore(TBase.GUI_PAGEABLE_NEXT_ACTION.get().toString());
                button2.action(p0 -> ++this.page);
                return;
            }, 54);
        }
        if (search != null) {
            if (this.searchTerm == null) {
                final SearchFeature searchFeature2;
                model.button(button5 -> {
                    button5.material(searchFeature2.getIcon()).name(Animation.fading(searchFeature2.getTitle(), Colors.DarkCyan, Colors.Cyan)).lore("§7" + searchFeature2.getAction());
                    button5.action(p0 -> new UserInput(this.p, this.plugin, TBase.GUI_PAGEABLE_SEARCH_TITLE.toString(), TBase.GUI_PAGEABLE_SEARCH_TYPEIN.toString(), "") {
                        @Override
                        public void onClose(final Player player) {
                            PageableGUI.this.reopen();
                        }
                        
                        @Override
                        public boolean onResult(final String s) {
                            PageableGUI.this.searchTerm = s;
                            PageableGUI.this.page = 0;
                            PageableGUI.this.reopen();
                            return true;
                        }
                    });
                    return;
                }, this.getLeftOptionSlot());
            }
            else {
                model.button(button6 -> {
                    button6.material(XMaterial.REDSTONE_BLOCK).name(Animation.fading(search.getTitle(), Colors.Red, Colors.White)).lore(TBase.GUI_PAGEABLE_SEARCH_CLOSE_ACTION.get().toString());
                    button6.action(p0 -> {
                        this.searchTerm = null;
                        this.page = 0;
                    });
                    return;
                }, this.getLeftOptionSlot());
            }
        }
        if (this.hasBackButton()) {
            model.button(50, button3 -> Common.BackButton(button3, p0 -> this.onBack()));
        }
    }
    
    protected int getLeftOptionSlot() {
        return (this.pages <= 1) ? 48 : 47;
    }
    
    protected int getRightOptionSlot() {
        return (this.pages <= 1) ? 52 : 53;
    }
    
    public boolean hasBackButton() {
        return true;
    }
    
    public abstract void onBack();
    
    public abstract SearchFeature<OBJ> getSearch();
    
    public abstract OBJ[] getObjects();
    
    public abstract void construct(final Button p0, final OBJ p1);
}
