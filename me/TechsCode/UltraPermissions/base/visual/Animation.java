

package me.TechsCode.EnderPermissions.base.visual;

import java.util.Iterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import com.google.common.base.Preconditions;

public class Animation
{
    public static String wave(final String s, final HexColor... array) {
        return wave(s, true, 5, 10, array);
    }
    
    public static String wave(final String s, final boolean b, final int n, final int n2, final HexColor... array) {
        Preconditions.checkArgument(array.length > 1, (Object)"Not enough colors provided");
        final ArrayList<String> list = new ArrayList<String>();
        int n3 = 0;
        for (final HexColor hexColor : array) {
            final HexColor hexColor2 = array[(array.length == n3 + 1) ? 0 : (n3 + 1)];
            list.addAll((Collection<?>)Collections.nCopies(n, hexColor.getAppliedColor() + (b ? "§l" : "") + s));
            final ArrayList<String> list2 = new ArrayList<String>();
            list2.addAll((Collection<?>)Collections.nCopies(s.length(), hexColor.getAppliedColor()));
            list2.addAll((Collection<?>)ColorCalculations.getColorsInbetween(hexColor, hexColor2, n2).stream().map((Function<? super Object, ?>)HexColor::getAppliedColor).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
            list2.addAll((Collection<?>)Collections.nCopies(s.length(), hexColor2.getAppliedColor()));
            for (int j = 0; j <= list2.size() - s.length(); ++j) {
                final StringBuilder sb = new StringBuilder();
                int n4 = 0;
                final char[] charArray = s.toCharArray();
                for (int length2 = charArray.length, k = 0; k < length2; ++k) {
                    sb.append(list2.get(n4 + j)).append(b ? "§l" : "").append(charArray[k]);
                    ++n4;
                }
                list.add(sb.toString());
            }
            list.addAll((Collection<?>)Collections.nCopies(n, hexColor2.getAppliedColor() + (b ? "§l" : "") + s));
            ++n3;
        }
        return currentFrame(list);
    }
    
    public static String fading(final String s, final HexColor... array) {
        return fading(s, true, 10, 20, array);
    }
    
    public static String fading(final String s, final boolean b, final int n, final int n2, final HexColor... array) {
        final ArrayList<String> list = new ArrayList<String>();
        int n3 = 0;
        for (final HexColor hexColor : array) {
            final HexColor hexColor2 = array[(array.length == n3 + 1) ? 0 : (n3 + 1)];
            list.addAll((Collection<?>)Collections.nCopies(n, hexColor.getAppliedColor() + (b ? "§l" : "") + s));
            final Iterator<HexColor> iterator = ColorCalculations.getColorsInbetween(hexColor, hexColor2, n2).iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next().getAppliedColor() + (b ? "§l" : "") + s);
            }
            ++n3;
        }
        return currentFrame(list);
    }
    
    private static String currentFrame(final List<String> list) {
        return list.get((int)(System.currentTimeMillis() / 50L % list.size()));
    }
}
