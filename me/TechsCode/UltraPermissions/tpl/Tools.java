

package me.TechsCode.EnderPermissions.tpl;

import java.util.function.Consumer;
import java.util.stream.IntStream;
import me.TechsCode.EnderPermissions.base.visual.Text;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import me.TechsCode.EnderPermissions.base.SpigotTechPlugin;
import me.TechsCode.EnderPermissions.base.translations.TBase;
import java.util.concurrent.TimeUnit;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class Tools
{
    public static String getNumberString(final long number) {
        return new DecimalFormat("#,###").format(number);
    }
    
    public static String getNumberString(final double number) {
        return new DecimalFormat("#,###").format(number);
    }
    
    public static String getNumberString(final float n) {
        return new DecimalFormat("#,###").format(n);
    }
    
    public static String readableFileSize(final long n) {
        if (n <= 0L) {
            return "0";
        }
        final String[] array = { "B", "kB", "MB", "GB", "TB" };
        final int n2 = (int)(Math.log10((double)n) / Math.log10(1024.0));
        return new DecimalFormat("#,##0.#").format(n / Math.pow(1024.0, n2)) + " " + array[n2];
    }
    
    public static String[] splitCamelCase(final String s) {
        return s.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
    }
    
    public static String firstUpperCase(final String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    
    public static String[] lineSplitter(final String s, final int n) {
        final ArrayList<String> list = new ArrayList<String>();
        String string = "";
        for (final String str : s.split(" ")) {
            String trim = string.trim();
            if (trim.length() + str.length() > n) {
                list.add(trim);
                trim = "";
            }
            string = trim + " " + str;
        }
        final String trim2 = string.trim();
        if (trim2.length() != 0) {
            list.add(trim2);
        }
        return list.toArray(new String[0]);
    }
    
    public static String removeEofsAndLineBreakers(final String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        final int length = s.length();
        final char[] value = new char[length];
        int n = 0;
        int n2 = 0;
        boolean b = true;
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (!Character.isWhitespace(char1)) {
                b = false;
                value[n++] = ((char1 == ' ') ? ' ' : char1);
                n2 = 0;
            }
            else {
                if (n2 == 0 && !b) {
                    value[n++] = " ".charAt(0);
                }
                ++n2;
            }
        }
        if (b) {
            return "";
        }
        return new String(value, 0, n - ((n2 > 0) ? 1 : 0)).trim();
    }
    
    public static String getEnumName(final Enum<?> enum1) {
        return String.join(" ", Arrays.stream(enum1.name().split("_")).map(s -> s.toUpperCase().charAt(0) + s.toLowerCase().substring(1)).collect((Collector<? super Object, ?, Iterable<? extends CharSequence>>)Collectors.toCollection((Supplier<R>)ArrayList::new)));
    }
    
    public static String getSecretPassword(final String s) {
        return String.join("", Collections.nCopies(s.length(), "*"));
    }
    
    public static String getTimeString(final long n) {
        return getTimeString(n, TimeUnit.SECONDS);
    }
    
    public static String getTimeString(final long n, final TimeUnit timeUnit) {
        return getTimeString(n, timeUnit, 0);
    }
    
    public static String getTimeString(long seconds, final TimeUnit timeUnit, final int n) {
        seconds = timeUnit.toSeconds(seconds);
        if (seconds < 1L) {
            return "Now";
        }
        final long days = TimeUnit.SECONDS.toDays(seconds);
        seconds -= TimeUnit.DAYS.toSeconds(days);
        final long hours = TimeUnit.SECONDS.toHours(seconds);
        seconds -= TimeUnit.HOURS.toSeconds(hours);
        final long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        seconds -= TimeUnit.MINUTES.toSeconds(minutes);
        final long seconds2 = TimeUnit.SECONDS.toSeconds(seconds);
        final ArrayList<CharSequence> elements = new ArrayList<CharSequence>();
        if (days != 0L) {
            if (days == 1L) {
                elements.add(days + " " + TBase.DAY);
            }
            else {
                elements.add(days + " " + TBase.DAYS);
            }
        }
        if (hours != 0L) {
            if (hours == 1L) {
                elements.add(hours + " " + TBase.HOUR);
            }
            else {
                elements.add(hours + " " + TBase.HOURS);
            }
        }
        if (minutes != 0L) {
            if (minutes == 1L) {
                elements.add(minutes + " " + TBase.MINUTE);
            }
            else {
                elements.add(minutes + " " + TBase.MINUTES);
            }
        }
        if (seconds2 != 0L) {
            if (seconds2 == 1L) {
                elements.add(seconds2 + " " + TBase.SECOND);
            }
            else {
                elements.add(seconds2 + " " + TBase.SECONDS);
            }
        }
        if (n != 0) {
            while (elements.size() > n) {
                elements.remove(elements.size() - 1);
            }
        }
        return String.join(", ", elements);
    }
    
    public static File exportFile(final String str, final boolean b, final SpigotTechPlugin spigotTechPlugin) {
        final File file = new File(spigotTechPlugin.getPluginFolder() + "/" + str);
        if (b || !file.exists()) {
            spigotTechPlugin.getBootstrap().saveResource(str, b);
        }
        return file;
    }
    
    public static String getProgressBar(final int n, final int n2, final int n3, final String s, final String s2, final String s3) {
        final int endExclusive = (int)(n3 * (n / (float)n2));
        final int endExclusive2 = n3 - endExclusive;
        final StringBuilder sb = new StringBuilder();
        sb.append(Text.chatColor(s2));
        IntStream.range(0, endExclusive).mapToObj(p1 -> s).forEach((Consumer<? super Object>)sb::append);
        sb.append(Text.chatColor(s3));
        IntStream.range(0, endExclusive2).mapToObj(p1 -> s).forEach((Consumer<? super Object>)sb::append);
        return sb.toString();
    }
    
    public static long getTimeSecondsFromString(final String s) {
        long n = 0L;
        final String[] split = s.split(" ");
        for (int length = split.length, i = 0; i < length; ++i) {
            n += getTimeFromWord(split[i]);
        }
        return n;
    }
    
    private static long getTimeFromWord(final String s) {
        try {
            if (s.length() < 2) {
                return 0L;
            }
            new StringBuilder().append("").append(s.toCharArray()[s.length() - 1]).toString();
            final int intValue = Integer.valueOf(s.substring(0, s.length() - 1));
            final String prefix;
            final TimeUnit timeUnit2 = Arrays.stream(new TimeUnit[] { TimeUnit.DAYS, TimeUnit.HOURS, TimeUnit.MINUTES, TimeUnit.SECONDS }).filter(timeUnit -> timeUnit.toString().toLowerCase().startsWith(prefix)).findFirst().orElse(null);
            if (timeUnit2 == null) {
                return 0L;
            }
            return timeUnit2.toSeconds(intValue);
        }
        catch (NumberFormatException ex) {
            return 0L;
        }
    }
}
