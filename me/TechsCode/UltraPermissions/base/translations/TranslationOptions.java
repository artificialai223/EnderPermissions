

package me.TechsCode.EnderPermissions.base.translations;

import java.util.ArrayList;
import me.TechsCode.EnderPermissions.dependencies.commons.lang.WordUtils;

public class TranslationOptions
{
    private String translatedString;
    
    public TranslationOptions(final String translatedString) {
        this.translatedString = translatedString;
    }
    
    public TranslationOptions firstUpper() {
        this.translatedString = WordUtils.capitalize(this.translatedString);
        return this;
    }
    
    public TranslationOptions vars(final String... array) {
        int i = 1;
        for (int length = array.length, j = 0; j < length; ++j) {
            this.translatedString = this.translatedString.replace("%" + i, array[j]);
            ++i;
        }
        return this;
    }
    
    public String[] asTextBlock(final int n) {
        return this.asTextBlock(n, "");
    }
    
    public String[] asTextBlock(final int n, final String s) {
        final ArrayList<String> list = new ArrayList<String>();
        String string = "";
        for (final String str : this.get().split(" ")) {
            String trim = string.trim();
            if (trim.length() + str.length() > n) {
                list.add(s + trim);
                trim = "";
            }
            string = trim + " " + str;
        }
        final String trim2 = string.trim();
        if (trim2.length() != 0) {
            list.add(s + trim2);
        }
        return list.toArray(new String[0]);
    }
    
    public String get() {
        return this.translatedString;
    }
    
    @Override
    public String toString() {
        return this.get();
    }
}
