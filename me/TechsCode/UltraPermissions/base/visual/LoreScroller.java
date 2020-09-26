

package me.TechsCode.EnderPermissions.base.visual;

import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class LoreScroller
{
    public static String[] scroller(final String[] a, final int n, final long n2) {
        if (a.length <= n) {
            return a;
        }
        final ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(a));
        list.addAll(Arrays.asList(a));
        list.addAll(Arrays.asList(a));
        final int n3 = Math.round((float)((System.currentTimeMillis() - n2) / 150L)) % a.length;
        return (String[])list.subList(n3, n3 + n).toArray(new String[0]);
    }
}
