

package me.TechsCode.EnderPermissions.tpl;

import me.TechsCode.EnderPermissions.base.visual.Animation;
import me.TechsCode.EnderPermissions.base.visual.Colors;
import me.TechsCode.EnderPermissions.base.visual.HexColor;
import me.TechsCode.EnderPermissions.base.translations.TBase;
import me.TechsCode.EnderPermissions.base.item.XMaterial;
import me.TechsCode.EnderPermissions.base.gui.Action;
import me.TechsCode.EnderPermissions.base.gui.Button;

public class Common
{
    public static void BackButton(final Button button, final Action action) {
        button.material(XMaterial.OAK_SIGN).name(Animation.fading(TBase.GUI_BACK_NAME.toString(), Colors.DarkCyan, Colors.Cyan)).lore("§7" + TBase.GUI_BACK_ACTION);
        button.action(action);
    }
}
