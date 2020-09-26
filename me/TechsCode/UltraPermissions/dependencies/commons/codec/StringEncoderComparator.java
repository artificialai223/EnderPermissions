

package me.TechsCode.EnderPermissions.dependencies.commons.codec;

import java.util.Comparator;

public class StringEncoderComparator implements Comparator
{
    private final StringEncoder stringEncoder;
    
    @Deprecated
    public StringEncoderComparator() {
        this.stringEncoder = null;
    }
    
    public StringEncoderComparator(final StringEncoder stringEncoder) {
        this.stringEncoder = stringEncoder;
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        int compareTo;
        try {
            compareTo = ((Comparable)this.stringEncoder.encode(o)).compareTo(this.stringEncoder.encode(o2));
        }
        catch (EncoderException ex) {
            compareTo = 0;
        }
        return compareTo;
    }
}
