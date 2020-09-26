

package me.TechsCode.EnderPermissions.tpl.visual;

import org.bukkit.ChatColor;

public class CharacterSequence
{
    private String string;
    
    public CharacterSequence(final String s) {
        this.string = ChatColor.translateAlternateColorCodes('&', s);
    }
    
    public int getPixelLength() {
        int n = 0;
        int n2 = 0;
        boolean b = false;
        for (final char c : this.string.toCharArray()) {
            if (c == '§') {
                n2 = 1;
            }
            else if (n2 == 1) {
                n2 = 0;
                b = (c == 'l' || c == 'L');
            }
            else {
                final Letter letter = Letter.getLetter(c);
                n += (b ? letter.getBoldLength() : letter.getLength());
                ++n;
            }
        }
        return n;
    }
    
    public String getAlignedString(final int n, final AlignmentPosition alignmentPosition) {
        int n2 = n - this.getPixelLength();
        if (alignmentPosition == AlignmentPosition.CENTER) {
            n2 /= 2;
        }
        final int n3 = Letter.SPACE.getLength() + 1;
        int i = 0;
        final StringBuilder sb = new StringBuilder();
        while (i < n2) {
            sb.append(" ");
            i += n3;
        }
        if (alignmentPosition == AlignmentPosition.END || alignmentPosition == AlignmentPosition.CENTER) {
            return sb.toString() + this.string;
        }
        return this.string + sb.toString();
    }
    
    public enum AlignmentPosition
    {
        START, 
        CENTER, 
        END;
    }
}
