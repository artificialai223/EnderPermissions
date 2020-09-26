

package me.TechsCode.EnderPermissions.base.visual;

import java.util.ArrayList;
import java.util.List;

public class ColorCalculations
{
    public static List<HexColor> getColorsInbetween(final HexColor hexColor, final HexColor hexColor2, final int n) {
        final double n2 = (hexColor2.getColor().getRed() - hexColor.getColor().getRed()) / (double)n;
        final double n3 = (hexColor2.getColor().getGreen() - hexColor.getColor().getGreen()) / (double)n;
        final double n4 = (hexColor2.getColor().getBlue() - hexColor.getColor().getBlue()) / (double)n;
        final ArrayList<HexColor> list = new ArrayList<HexColor>();
        for (int i = 1; i <= n; ++i) {
            list.add(HexColor.from((int)Math.round(hexColor.getColor().getRed() + n2 * i), (int)Math.round(hexColor.getColor().getGreen() + n3 * i), (int)Math.round(hexColor.getColor().getBlue() + n4 * i)));
        }
        return list;
    }
}
