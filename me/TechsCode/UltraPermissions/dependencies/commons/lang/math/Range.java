

package me.TechsCode.EnderPermissions.dependencies.commons.lang.math;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.text.StrBuilder;

public abstract class Range
{
    public abstract Number getMinimumNumber();
    
    public long getMinimumLong() {
        return this.getMinimumNumber().longValue();
    }
    
    public int getMinimumInteger() {
        return this.getMinimumNumber().intValue();
    }
    
    public double getMinimumDouble() {
        return this.getMinimumNumber().doubleValue();
    }
    
    public float getMinimumFloat() {
        return this.getMinimumNumber().floatValue();
    }
    
    public abstract Number getMaximumNumber();
    
    public long getMaximumLong() {
        return this.getMaximumNumber().longValue();
    }
    
    public int getMaximumInteger() {
        return this.getMaximumNumber().intValue();
    }
    
    public double getMaximumDouble() {
        return this.getMaximumNumber().doubleValue();
    }
    
    public float getMaximumFloat() {
        return this.getMaximumNumber().floatValue();
    }
    
    public abstract boolean containsNumber(final Number p0);
    
    public boolean containsLong(final Number n) {
        return n != null && this.containsLong(n.longValue());
    }
    
    public boolean containsLong(final long n) {
        return n >= this.getMinimumLong() && n <= this.getMaximumLong();
    }
    
    public boolean containsInteger(final Number n) {
        return n != null && this.containsInteger(n.intValue());
    }
    
    public boolean containsInteger(final int n) {
        return n >= this.getMinimumInteger() && n <= this.getMaximumInteger();
    }
    
    public boolean containsDouble(final Number n) {
        return n != null && this.containsDouble(n.doubleValue());
    }
    
    public boolean containsDouble(final double n) {
        final int compare = NumberUtils.compare(this.getMinimumDouble(), n);
        final int compare2 = NumberUtils.compare(this.getMaximumDouble(), n);
        return compare <= 0 && compare2 >= 0;
    }
    
    public boolean containsFloat(final Number n) {
        return n != null && this.containsFloat(n.floatValue());
    }
    
    public boolean containsFloat(final float n) {
        final int compare = NumberUtils.compare(this.getMinimumFloat(), n);
        final int compare2 = NumberUtils.compare(this.getMaximumFloat(), n);
        return compare <= 0 && compare2 >= 0;
    }
    
    public boolean containsRange(final Range range) {
        return range != null && this.containsNumber(range.getMinimumNumber()) && this.containsNumber(range.getMaximumNumber());
    }
    
    public boolean overlapsRange(final Range range) {
        return range != null && (range.containsNumber(this.getMinimumNumber()) || range.containsNumber(this.getMaximumNumber()) || this.containsNumber(range.getMinimumNumber()));
    }
    
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        final Range range = (Range)o;
        return this.getMinimumNumber().equals(range.getMinimumNumber()) && this.getMaximumNumber().equals(range.getMaximumNumber());
    }
    
    public int hashCode() {
        return 37 * (37 * (37 * 17 + this.getClass().hashCode()) + this.getMinimumNumber().hashCode()) + this.getMaximumNumber().hashCode();
    }
    
    public String toString() {
        final StrBuilder strBuilder = new StrBuilder(32);
        strBuilder.append("Range[");
        strBuilder.append(this.getMinimumNumber());
        strBuilder.append(',');
        strBuilder.append(this.getMaximumNumber());
        strBuilder.append(']');
        return strBuilder.toString();
    }
}
