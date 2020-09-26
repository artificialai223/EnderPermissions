

package me.TechsCode.EnderPermissions;

import java.util.Arrays;
import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Button;

public class UPCommon
{
    public static void NotAvailableButton(final Button button, final String s, final String... array) {
        button.material(XMaterial.RED_STAINED_GLASS_PANE).name(Animation.wave(s, Colors.Red, Colors.White)).lore("§7Feature not available because");
        Arrays.stream(array).forEach(str -> button.item().appendLore("§7" + str));
    }
}
