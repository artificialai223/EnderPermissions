

package me.TechsCode.EnderPermissions.gui.settings;

import me.TechsCode.EnderPermissions.visual.VisualRegistry;
import java.util.Arrays;
import me.TechsCode.EnderPermissions.base.gui.ActionType;
import me.TechsCode.EnderPermissions.base.translations.TBase;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.gui.Button;
import me.TechsCode.EnderPermissions.base.gui.Model;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.T;
import me.TechsCode.EnderPermissions.base.views.settings.SettingsView;
import org.bukkit.entity.Player;
import me.TechsCode.EnderPermissions.DefaultGroupAssignOption;
import me.TechsCode.EnderPermissions.EnderPermissions;
import me.TechsCode.EnderPermissions.base.views.settings.SettingsPane;

public class MiscPane extends SettingsPane
{
    private EnderPermissions plugin;
    private DefaultGroupAssignOption selected;
    
    public MiscPane(final Player player, final SettingsView settingsView, final EnderPermissions plugin) {
        super(player, settingsView);
        this.plugin = plugin;
    }
    
    @Override
    public String getName() {
        return T.GUI_SETTINGS_MISC_NAME.toString();
    }
    
    @Override
    public XMaterial getIcon() {
        return XMaterial.BOWL;
    }
    
    @Override
    public void construct(final Model model) {
        model.button(21, this::DefaultPermissionToggle);
        model.button(23, this::DefaultGroupAssignToggle);
        model.button(25, this::NameTagsButton);
    }
    
    private void DefaultPermissionToggle(final Button button) {
        button.material(XMaterial.BEDROCK).name(Animation.wave(T.DEFAULT_PERMISSIONS.toString(), Colors.Orange, Colors.White)).lore("§7Click to " + TBase.enableDisable(!this.plugin.isDefaultPermissionsEnabled()) + " default permissions", "", "§7State: §e" + TBase.enabledDisabled(this.plugin.isDefaultPermissionsEnabled()) + " §8(" + T.RECOMMENDED + ")", "", "§7It is recommended to keep it §cdisabled", "§7to avoid unwanted side effects and to", "§7maintain full control", "", "§8Changes take effect on reconnect");
        button.action(p0 -> this.plugin.setDefaultPermissions(!this.plugin.isDefaultPermissionsEnabled()));
    }
    
    private void DefaultGroupAssignToggle(final Button button) {
        button.material(XMaterial.PISTON).name(Animation.wave("Default Group Assigning", Colors.Orange, Colors.White)).lore("§7Click to §etoggle");
        if (this.selected != null) {
            button.item().appendLore("§bPress Q §7to §asave §7changes");
            button.action(actionType -> {
                if (actionType == ActionType.Q) {
                    this.plugin.setDefaultGroupAssignOption(this.selected);
                    this.selected = null;
                }
                return;
            });
        }
        button.item().appendLore("");
        this.plugin.getDefaultGroupAssignOption();
        final DefaultGroupAssignOption defaultGroupAssignOption2;
        Arrays.stream(DefaultGroupAssignOption.values()).forEach(defaultGroupAssignOption -> button.item().appendLore(((this.selected == null && defaultGroupAssignOption2 == defaultGroupAssignOption) ? "§a" : ((this.selected == defaultGroupAssignOption) ? "§e" : "§f")) + "- §7" + defaultGroupAssignOption.getDescription()));
        button.item().appendLore("", "§7This could be a §cdestructive action§7, pay attention before confirming");
        final DefaultGroupAssignOption defaultGroupAssignOption3;
        button.action(actionType2 -> {
            if (actionType2 != ActionType.Q) {
                this.selected = ((this.selected != null) ? this.selected : defaultGroupAssignOption3).next();
            }
        });
    }
    
    private void NameTagsButton(final Button p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ldc_w           "NametagEdit"
        //     6: invokeinterface org/bukkit/plugin/PluginManager.isPluginEnabled:(Ljava/lang/String;)Z
        //    11: istore_2       
        //    12: aload_0        
        //    13: getfield        me/TechsCode/EnderPermissions/gui/settings/MiscPane.plugin:Lme/TechsCode/EnderPermissions/EnderPermissions;
        //    16: invokevirtual   me/TechsCode/EnderPermissions/EnderPermissions.getVisualRegistry:()Lme/TechsCode/EnderPermissions/base/registry/SwitchableBiRegistry;
        //    19: invokevirtual   me/TechsCode/EnderPermissions/base/registry/SwitchableBiRegistry.get:()Lme/TechsCode/EnderPermissions/base/registry/RegistryStorable;
        //    22: checkcast       Lme/TechsCode/EnderPermissions/visual/VisualRegistry;
        //    25: invokevirtual   me/TechsCode/EnderPermissions/visual/VisualRegistry.isEditingNametags:()Z
        //    28: istore_3       
        //    29: aload_1        
        //    30: getstatic       me/TechsCode/EnderPermissions/base/item/XMaterial.NAME_TAG:Lme/TechsCode/EnderPermissions/base/item/XMaterial;
        //    33: invokevirtual   me/TechsCode/EnderPermissions/base/gui/Button.material:(Lme/TechsCode/EnderPermissions/base/item/XMaterial;)Lme/TechsCode/EnderPermissions/base/item/CustomItem;
        //    36: ldc_w           "Name Tags"
        //    39: iconst_2       
        //    40: anewarray       Lme/TechsCode/EnderPermissions/base/visual/HexColor;
        //    43: dup            
        //    44: iconst_0       
        //    45: getstatic       me/TechsCode/EnderPermissions/base/visual/Colors.Orange:Lme/TechsCode/EnderPermissions/base/visual/HexColor;
        //    48: aastore        
        //    49: dup            
        //    50: iconst_1       
        //    51: getstatic       me/TechsCode/EnderPermissions/base/visual/Colors.White:Lme/TechsCode/EnderPermissions/base/visual/HexColor;
        //    54: aastore        
        //    55: invokestatic    me/TechsCode/EnderPermissions/base/visual/Animation.wave:(Ljava/lang/String;[Lme/TechsCode/EnderPermissions/base/visual/HexColor;)Ljava/lang/String;
        //    58: invokevirtual   me/TechsCode/EnderPermissions/base/item/CustomItem.name:(Ljava/lang/String;)Lme/TechsCode/EnderPermissions/base/item/CustomItem;
        //    61: iconst_5       
        //    62: anewarray       Ljava/lang/String;
        //    65: dup            
        //    66: iconst_0       
        //    67: iload_2        
        //    68: ifeq            87
        //    71: iload_3        
        //    72: ifeq            81
        //    75: ldc_w           "§7Click to §cdisable §7this feature"
        //    78: goto            90
        //    81: ldc_w           "§7Click to §eenable §7this feature"
        //    84: goto            90
        //    87: ldc_w           "§7The Plugin §cNameTagEdit §7is required for this feature"
        //    90: aastore        
        //    91: dup            
        //    92: iconst_1       
        //    93: ldc             ""
        //    95: aastore        
        //    96: dup            
        //    97: iconst_2       
        //    98: new             Ljava/lang/StringBuilder;
        //   101: dup            
        //   102: invokespecial   java/lang/StringBuilder.<init>:()V
        //   105: ldc_w           "§7Edited Nametags: "
        //   108: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   111: iload_2        
        //   112: ifeq            131
        //   115: iload_3        
        //   116: ifeq            125
        //   119: ldc_w           "§aEnabled"
        //   122: goto            134
        //   125: ldc_w           "§cDisabled"
        //   128: goto            134
        //   131: ldc_w           "§cUnavailable"
        //   134: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   137: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   140: aastore        
        //   141: dup            
        //   142: iconst_3       
        //   143: ldc             ""
        //   145: aastore        
        //   146: dup            
        //   147: iconst_4       
        //   148: ldc_w           "§7Show §ePrefix§7/§eSuffix §7in the Nametag above other players"
        //   151: aastore        
        //   152: invokevirtual   me/TechsCode/EnderPermissions/base/item/CustomItem.lore:([Ljava/lang/String;)Lme/TechsCode/EnderPermissions/base/item/CustomItem;
        //   155: pop            
        //   156: aload_1        
        //   157: aload_0        
        //   158: iload_2        
        //   159: iload_3        
        //   160: invokedynamic   BootstrapMethod #7, onClick:(Lme/TechsCode/EnderPermissions/gui/settings/MiscPane;ZZ)Lme/TechsCode/EnderPermissions/base/gui/Action;
        //   165: invokevirtual   me/TechsCode/EnderPermissions/base/gui/Button.action:(Lme/TechsCode/EnderPermissions/base/gui/Action;)V
        //   168: return         
        //    StackMapTable: 00 06 FF 00 51 00 04 07 00 02 07 00 4F 01 01 00 04 07 00 69 07 00 82 07 00 82 01 FF 00 05 00 04 07 00 02 07 00 4F 01 01 00 04 07 00 69 07 00 82 07 00 82 01 FF 00 02 00 04 07 00 02 07 00 4F 01 01 00 05 07 00 69 07 00 82 07 00 82 01 07 00 6F FF 00 22 00 04 07 00 02 07 00 4F 01 01 00 05 07 00 69 07 00 82 07 00 82 01 07 00 71 FF 00 05 00 04 07 00 02 07 00 4F 01 01 00 05 07 00 69 07 00 82 07 00 82 01 07 00 71 FF 00 02 00 04 07 00 02 07 00 4F 01 01 00 06 07 00 69 07 00 82 07 00 82 01 07 00 71 07 00 6F
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:264)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:198)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:276)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
