

package me.TechsCode.EnderPermissions.base.views.settings;

import me.TechsCode.EnderPermissions.base.gui.ActionType;
import org.bukkit.enchantments.Enchantment;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.gui.Button;
import java.util.Iterator;
import me.TechsCode.EnderPermissions.base.translations.TranslationManager;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Map;
import me.TechsCode.EnderPermissions.base.gui.Model;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.translations.TBase;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;

public class LanguagePane extends SettingsPane
{
    private SpigotTechPlugin plugin;
    
    public LanguagePane(final Player player, final SettingsView settingsView, final SpigotTechPlugin plugin) {
        super(player, settingsView);
        this.plugin = plugin;
    }
    
    @Override
    public String getName() {
        return TBase.GUI_SETTINGS_LANGUAGE_NAME.toString();
    }
    
    @Override
    public XMaterial getIcon() {
        return XMaterial.WRITABLE_BOOK;
    }
    
    @Override
    public void construct(final Model model) {
        final int[] innerContainerSlots = this.getInnerContainerSlots();
        final TranslationManager translationManager = this.plugin.getTranslationManager();
        final Map<Object, Object> map = Arrays.stream(translationManager.getAvailableLanguages()).collect(Collectors.toMap(o -> o, (Function<? super String, ?>)translationManager::getPhraseAmount));
        (int)Collections.max((Collection<?>)map.values());
        final Iterator<Object> iterator = Arrays.stream(translationManager.getAvailableLanguages()).iterator();
        for (final int n : innerContainerSlots) {
            if (iterator.hasNext()) {
                map.get(iterator.next());
                final String anObject;
                final int n2;
                final int n3;
                model.button(n, button2 -> this.LanguageButton(button2, anObject, n2, Math.round(n2 / (float)n3 * 100.0f), translationManager.getSelectedLanguage().equals(anObject)));
            }
            else {
                model.button(n, button -> button.material(XMaterial.WHITE_STAINED_GLASS_PANE).name("§f"));
            }
        }
    }
    
    private void LanguageButton(final Button button, final String selectedLanguage, final int i, final int j, final boolean b) {
        button.material(XMaterial.PAPER).name(Animation.wave(selectedLanguage, Colors.Orange, Colors.Yellow)).lore(b ? TBase.GUI_SETTINGS_LANG_SELECTED.get().toString() : TBase.GUI_SETTINGS_LANG_ACTION.get().toString(), "", TBase.GUI_SETTINGS_LANG_PHRASES + ": §e" + i, TBase.GUI_SETTINGS_LANG_COVERAGE + ": §a" + j + "%");
        if (b) {
            button.item().showEnchantments(false).addEnchantment(Enchantment.LUCK, 1);
        }
        button.action(p2 -> {
            if (!b) {
                this.plugin.getTranslationManager().setSelectedLanguage(selectedLanguage);
            }
        });
    }
}
