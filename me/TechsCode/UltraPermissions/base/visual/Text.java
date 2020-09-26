

package me.TechsCode.EnderPermissions.base.visual;

import java.util.Collection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import me.TechsCode.EnderPermissions.tpl.utils.MinecraftVersion;
import com.google.common.base.Preconditions;
import java.util.regex.Pattern;

public class Text
{
    private static final Pattern HEX_COLOR_PATTERN;
    private static final Pattern STRIP_CHAT_COLOR_PATTERN;
    
    public static String stripHexColor(final String s) {
        return s.replaceAll(Text.HEX_COLOR_PATTERN.pattern(), "");
    }
    
    public static String stripChatColor(final String s) {
        return s.replaceAll(Text.STRIP_CHAT_COLOR_PATTERN.pattern(), "");
    }
    
    public static String stripColor(final String s) {
        return stripHexColor(stripChatColor(s));
    }
    
    public static String color(final String s) {
        return chatColor(hexColor(s));
    }
    
    public static String chatColor(final String s) {
        Preconditions.checkArgument(s != null, (Object)"A text must be defined");
        final char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length - 1; ++i) {
            if (charArray[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(charArray[i + 1]) > -1) {
                charArray[i] = '§';
                charArray[i + 1] = Character.toLowerCase(charArray[i + 1]);
            }
        }
        return new String(charArray);
    }
    
    public static String hexColor(String replace) {
        Preconditions.checkArgument(replace != null, (Object)"A text must be defined");
        final Matcher matcher = Text.HEX_COLOR_PATTERN.matcher(replace);
        final boolean aboveOrEqual = MinecraftVersion.getServersVersion().isAboveOrEqual(MinecraftVersion.V1_16_R1);
        while (matcher.find()) {
            final String group = matcher.group();
            final HexColor from = HexColor.from(group.replace("{#", "").replace("}", ""));
            replace = replace.replace(group, aboveOrEqual ? from.getAppliedColor() : from.getClosestDefault().getAppliedChatColor());
        }
        return replace;
    }
    
    public static String gradient(final String s, final HexColor... array) {
        Preconditions.checkArgument(array.length > 1, (Object)"Define 2 or more colors");
        final int max = Math.max(1, s.length() / array.length);
        final ArrayList<HexColor> list = new ArrayList<HexColor>();
        int n = 0;
        for (final HexColor hexColor : array) {
            final HexColor hexColor2 = (array.length == n + 1) ? null : array[n + 1];
            if (hexColor2 != null) {
                list.addAll((Collection<?>)ColorCalculations.getColorsInbetween(hexColor, hexColor2, max));
            }
            ++n;
        }
        final StringBuilder sb = new StringBuilder();
        int n2 = 0;
        final char[] charArray = s.toCharArray();
        for (int length2 = charArray.length, j = 0; j < length2; ++j) {
            sb.append(((list.size() <= n2) ? list.get(list.size() - 1) : list.get(n2)).getAppliedColor()).append(charArray[j]);
            ++n2;
        }
        return sb.toString();
    }
    
    static {
        HEX_COLOR_PATTERN = Pattern.compile("\\{#[0-9A-Fa-f]{6}}");
        STRIP_CHAT_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");
    }
}
