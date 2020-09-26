

package me.TechsCode.EnderPermissions.base.gui;

import me.TechsCode.EnderPermissions.base.translations.TBase;
import me.TechsCode.EnderPermissions.base.item.XMaterial;

public abstract class BasicSearch<OBJ> extends SearchFeature<OBJ>
{
    public BasicSearch() {
        super(XMaterial.COMPASS, TBase.GUI_PAGEABLE_SEARCH_TITLE.toString(), TBase.GUI_PAGEABLE_SEARCH_ACTION.toString());
    }
}
